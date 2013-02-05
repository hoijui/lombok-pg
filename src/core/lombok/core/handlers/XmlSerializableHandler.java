/*
 * Copyright © 2011-2012 Philipp Eichhorn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lombok.core.handlers;

import static lombok.ast.AST.Annotation;
import static lombok.ast.AST.Call;
import static lombok.ast.AST.False;
import static lombok.ast.AST.Field;
import static lombok.ast.AST.LocalDecl;
import static lombok.ast.AST.MethodDecl;
import static lombok.ast.AST.Name;
import static lombok.ast.AST.New;
import static lombok.ast.AST.Return;
import static lombok.ast.AST.String;
import static lombok.ast.AST.True;
import static lombok.ast.AST.Type;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.ast.Annotation;
import lombok.ast.Expression;
import lombok.ast.IField;
import lombok.ast.IType;
import lombok.ast.MethodDecl;
import lombok.ast.TypeRef;
import lombok.core.xml.XAttribute;
import lombok.core.xml.XElement;
import lombok.core.xml.XmlField;
import lombok.core.xml.XmlSerializable;
import lombok.core.xml.XmlUtils;

public abstract class XmlSerializableHandler<TYPE_TYPE extends IType<?, FIELD_TYPE, ?, ?, ?, ?>, FIELD_TYPE extends IField<?, ?, ?, ?>> {
	private static final Class<?>[] KNOWN_TYPES = new Class<?>[] {
		int.class,
		Integer.class,
		long.class,
		Long.class,
		String.class,
		double.class,
		Double.class,
		float.class,
		Float.class,
		BigDecimal.class,
		Timestamp.class,
		Date.class
	};
	
	public void addToXmlMethod(final TYPE_TYPE type) {
		TypeRef typeRef = Type(XElement.class);
		
		Expression<?> rootName = String(type.name());
		
		Annotation root = findAnnotation(type, XmlRootElement.class.getSimpleName());
		if (root != null && root.getValues().containsKey("name")) {
			rootName = getAnnotationValue(root.getValues().get("name"));
		}
		
		MethodDecl method = MethodDecl(typeRef, "toXml")
				.withAnnotation(Annotation(Type(Override.class)))
				.makePublic()
				.implementing();
		
		method.withStatement(
				LocalDecl(typeRef, "element").makeFinal()
				.withInitialization(New(typeRef).withArgument(rootName)));
		
		List<FIELD_TYPE> fields = new ArrayList<FIELD_TYPE>();
				
		for (FIELD_TYPE field : type.fields()) {
			if (field.isStatic()) continue;
			if (field.name().startsWith("$")) continue;
			if (hasXmlTrasient(field)) continue;
			fields.add(field);
		}

		TypeRef innerType;
		for (FIELD_TYPE field : fields) {
			XmlField xmlField = getXmlField(field);
			
			if (xmlField.isAttribute) {
				innerType = Type(XAttribute.class);
			} else {
				innerType = Type(XElement.class);
			}
			
			if (xmlField.adapter != null) {
				method.withStatement(
						Call(Name("element"), "appendChild")
						.withArgument(
								New(innerType)
								.withArgument(xmlField.name)
								.withArgument(
										Call(
												New(Type(xmlField.adapter)),
												"marshal"
										)
										.withArgument(Field(field.name())))));
			} else if (isKnownType(field.boxedType())) {
				method.withStatement(Call(Name("element"), "appendChild").withArgument(New(innerType).withArgument(xmlField.name).withArgument(Field(field.name()))));
			} else {
				method.withStatement(
						Call(Name(XmlUtils.class), "append")
						.withArgument(Name("element"))
						.withArgument(Field(field.name()))
						.withArgument(xmlField.name)
						.withArgument((xmlField.isAttribute ? True() : False())));
			}
		}
		
		method.withStatement(Return(Name("element")));
		
		type.editor().implementing(Type(XmlSerializable.class));
		type.editor().injectMethod(method);
	}
	
	private boolean hasXmlTrasient(FIELD_TYPE field) {
		for (Annotation anno : field.annotations()) {
			if (anno.getType().toString().contains(XmlTransient.class.getSimpleName())) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean isKnownType(TypeRef type) {
		for (Class<?> clazz : KNOWN_TYPES) {
			if (clazz.getName().equals(type.toString()) || clazz.getSimpleName().equals(type.toString())) {
				return true;
			}
		}
		
		return false;
	}
	
	private Annotation findAnnotation(TYPE_TYPE type, String name) {
		if (type.annotations() != null) {
			for (Annotation anno : type.annotations()) {
				if (anno.getType().toString().contains(name)) {
					return anno;
				}
			}
		}
		
		return null;
	}
	
	private XmlField getXmlField(FIELD_TYPE field) {
		XmlField xmlField = new XmlField();
		xmlField.name = String(field.name());
		xmlField.isAttribute = true;
		
		if (field.annotations() != null) {
			for (Annotation anno : field.annotations()) {
				String annoType = anno.getType().toString();
								
				if (annoType.contains(XmlElement.class.getSimpleName())) {
					xmlField.isAttribute = false;
				} else if (annoType.contains(XmlJavaTypeAdapter.class.getSimpleName())) {
					xmlField.adapter = anno.getValues().get("value").toString().replace(".class", "");
				}
				
				if (annoType.contains(XmlElement.class.getSimpleName()) || annoType.contains(XmlAttribute.class.getSimpleName())) {
					Map<String, Expression<?>> values = anno.getValues();
					
					if (values.containsKey("name")) {
						xmlField.name = getAnnotationValue(values.get("name"));
					}
				}
			}
		}
		
		return xmlField;
	}
	
	protected abstract Expression<?> getAnnotationValue(Expression<?> value);
}
