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
import static lombok.ast.AST.Arg;
import static lombok.ast.AST.Binary;
import static lombok.ast.AST.Call;
import static lombok.ast.AST.ClassDecl;
import static lombok.ast.AST.Equal;
import static lombok.ast.AST.Field;
import static lombok.ast.AST.MethodDecl;
import static lombok.ast.AST.Name;
import static lombok.ast.AST.New;
import static lombok.ast.AST.Not;
import static lombok.ast.AST.Or;
import static lombok.ast.AST.Return;
import static lombok.ast.AST.Type;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Functions.Function1;
import lombok.Predicates.Predicate1;
import lombok.ast.Expression;
import lombok.ast.IField;
import lombok.ast.IType;
import lombok.ast.ITypeEditor;
import lombok.ast.MethodDecl;
import lombok.ast.TypeRef;

public class FilterableHandler<TYPE_TYPE extends IType<?, FIELD_TYPE, ?, ?, ?, ?>, FIELD_TYPE extends IField<?, ?, ?, ?>> {
	public void addFilterMethods(final TYPE_TYPE type) {		
		List<FIELD_TYPE> fields = new ArrayList<FIELD_TYPE>();
				
		for (FIELD_TYPE field : type.fields()) {
			if (field.isFinal()) continue;
			if (field.isStatic()) continue;
			if (field.filteredName().startsWith("$")) continue;
			fields.add(field);
		}

		for (FIELD_TYPE field : fields) {		
			addFilterMethod(type, field, false);
		}
		
		type.editor().rebuild();
	}

	public void addFilterMethod(TYPE_TYPE type, FIELD_TYPE field) {
		addFilterMethod(type, field, true);
	}
	
	private void addFilterMethod(TYPE_TYPE type, FIELD_TYPE field, boolean rebuildType) {		
		TypeRef predicateType = Type(Predicate1.class.getName().replace("$", "."))
				.withTypeArgument(Type(type.name()));
		
		TypeRef fieldType = field.type();
		
		List<MethodDecl> methods = null;
		if (isType(fieldType, Long.class, long.class, Integer.class, int.class, Double.class, double.class, Float.class, float.class)) {
			methods = addNumberFilterMethods(type, predicateType, field);
		} else if (isType(fieldType, Boolean.class, boolean.class)) {
			methods = addBooleanMethods(type, predicateType, field);
		} else if (isType(fieldType, String.class)) {
			methods = addStringFilterMethods(type, predicateType, field);
		} else if (isType(fieldType, Timestamp.class)) {
			methods = addTimestampFilterMethods(type, predicateType, field);
		} else {
			methods = addObjectFilterMethods(type, predicateType, field);
		}
		
		ITypeEditor<?, ?, ?, ?> editor = type.editor();
		for (MethodDecl method : methods) {
			editor.injectMethod(method);
		}
		
		TypeRef functionType = Type(Function1.class.getName().replace("$", "."))
				.withTypeArgument(Type(type.name()))
				.withTypeArgument(fixFieldType(fieldType));
		
		editor.injectMethod(MethodDecl(functionType, field.name())
				.makePublic()
				.makeStatic()
				.implementing()
				.withStatement(
						Return(New(functionType).withTypeDeclaration(ClassDecl("")
								.makeAnonymous()
								.makeLocal()
								.withMethod(MethodDecl(fixFieldType(fieldType), "apply")
										.makePublic()
										.withAnnotation(Annotation(Type(Override.class)))
										.withArgument(Arg(Type(type.name()), "item"))
										.implementing()
										.withStatement(Return(Field(Name("item"), field.name())))
								)
						))
				));
		
		if (rebuildType) {
			editor.rebuild();
		}
	}
	
	private List<MethodDecl> addBooleanMethods(TYPE_TYPE type, TypeRef predicateType, FIELD_TYPE field) {
		TypeRef typeRef = Type(type.name());	
		final String argName = "_" + field.name();
		
		Expression<?> equalsBody = null;
		if (isType(field.type(), Boolean.class)) {
			equalsBody = Call(Field(Name("item"), field.name()), "equals").withArgument(Name(argName));
		} else {
			equalsBody = Equal(Field(Name("item"), field.name()), Name(argName));
		}
		
		return Arrays.asList(
				createMethod(
						predicateType, 
						typeRef,
						field,
						field.name() + "Equals",
						equalsBody
				),
				MethodDecl(predicateType, field.name() + "IsTrue")
					.makePublic()
					.makeStatic()
					.implementing()
					.withStatement(
							Return(New(predicateType).withTypeDeclaration(ClassDecl("")
									.makeAnonymous()
									.makeLocal()
									.withMethod(MethodDecl(Type(boolean.class), "evaluate")
											.makePublic()
											.withAnnotation(Annotation(Type(Override.class)))
											.withArgument(Arg(Type(type.name()), "item"))
											.implementing()
											.withStatement(Return(Field(Name("item"), field.name())))
									)
							))
					),
			MethodDecl(predicateType, field.name() + "IsFalse")
					.makePublic()
					.makeStatic()
					.withStatement(
							Return(New(predicateType).withTypeDeclaration(ClassDecl("")
									.makeAnonymous()
									.makeLocal()
									.withMethod(MethodDecl(Type(boolean.class), "evaluate")
											.makePublic()
											.withAnnotation(Annotation(Type(Override.class)))
											.withArgument(Arg(Type(type.name()), "item"))
											.withStatement(Return(Not(Field(Name("item"), field.name()))))
									)
							))
					)
			);
			
						
	}

	private List<MethodDecl> addObjectFilterMethods(TYPE_TYPE type, TypeRef predicateType, FIELD_TYPE field) {
		TypeRef typeRef = Type(type.name());	
		final String argName = "_" + field.name();
		
		return Arrays.asList(
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "Equals",
						Call(Field(Name("item"), field.name()), "equals").withArgument(Name(argName))
				)
		);
	}

	private List<MethodDecl> addTimestampFilterMethods(TYPE_TYPE type, TypeRef predicateType, FIELD_TYPE field) {
		TypeRef typeRef = Type(type.name());	
		final String argName = "_" + field.name();
		
		return Arrays.asList(
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "Equals",
						Call(Field(Name("item"), field.name()), "equals").withArgument(Name(argName))
				),
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "GreaterThan",
						Call(Field(Name("item"), field.name()), "after").withArgument(Name(argName))
				),
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "GreaterThanEqual",
						Or(
								Call(Field(Name("item"), field.name()), "after").withArgument(Name(argName)),
								Call(Field(Name("item"), field.name()), "equals").withArgument(Name(argName))
						)
				),
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "LessThan",
						Call(Field(Name("item"), field.name()), "before").withArgument(Name(argName))
				),
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "LessThanEqual",
						Or(
								Call(Field(Name("item"), field.name()), "before").withArgument(Name(argName)),
								Call(Field(Name("item"), field.name()), "equals").withArgument(Name(argName))
						)
				)
		);
	}

	private List<MethodDecl> addStringFilterMethods(TYPE_TYPE type, TypeRef predicateType, FIELD_TYPE field) {	
		TypeRef typeRef = Type(type.name());	
		final String argName = "_" + field.name();
		
		return Arrays.asList(
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "Equals",
						Call(Field(Name("item"), field.name()), "equals").withArgument(Name(argName))
				),
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "Contains",
						Call(Field(Name("item"), field.name()), "contains").withArgument(Name(argName))
				),
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "StartsWith",
						Call(Field(Name("item"), field.name()), "startsWith").withArgument(Name(argName))
				),
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "EndsWith",
						Call(Field(Name("item"), field.name()), "endsWith").withArgument(Name(argName))
				)
		);
		
	}

	private List<MethodDecl> addNumberFilterMethods(TYPE_TYPE type, TypeRef predicateType, FIELD_TYPE field) {
		TypeRef typeRef = Type(type.name());
		final String argName = "_" + field.name();
		
		Expression<?> equalsBody = null;
		if (isType(field.type(), Long.class, Integer.class, Double.class, Float.class)) {
			equalsBody = Call(Field(Name("item"), field.name()), "equals").withArgument(Name(argName));
		} else {
			equalsBody = Equal(Field(Name("item"), field.name()), Name(argName));
		}
		
		return Arrays.asList(
				createMethod(
						predicateType, 
						typeRef, 
						field, 
						field.name() + "Equals", 
						equalsBody
				),
				createMethod(
						predicateType,
						typeRef,
						field, 
						field.name() + "GreaterThan",
						Binary(Field(Name("item"), field.name()), ">", Name(argName))
				),
				createMethod(
						predicateType,
						typeRef,
						field, 
						field.name() + "GreaterThanEqual",
						Binary(Field(Name("item"), field.name()), ">=", Name(argName))
				),
				createMethod(
						predicateType,
						typeRef,
						field, 
						field.name() + "LessThan",
						Binary(Field(Name("item"), field.name()), "<", Name(argName))
				),
				createMethod(
						predicateType,
						typeRef,
						field, 
						field.name() + "LessThanEqual",
						Binary(Field(Name("item"), field.name()), "<=", Name(argName))
				)
		);
	}
	
	private MethodDecl createMethod(TypeRef predicateType, TypeRef type, FIELD_TYPE field, String name, Expression<?> body) {
		return MethodDecl(predicateType, name)
				.makePublic()
				.makeStatic()
				.withArgument(Arg(field.type(), "_" + field.name()).makeFinal())
				.withStatement(
						Return(New(predicateType).withTypeDeclaration(ClassDecl("")
								.makeAnonymous()
								.makeLocal()
								.withMethod(MethodDecl(Type(boolean.class), "evaluate")
										.makePublic()
										.withAnnotation(Annotation(Type(Override.class)))
										.withArgument(Arg(type, "item"))
										.withStatement(Return(body))
								)
						))
				);
								
	}
	
	private static boolean isType(TypeRef ref, Class<?>... classes) {
		for (Class<?> clazz : classes) {
			if (ref.toString().equals(clazz.getName()) || ref.toString().equals(clazz.getSimpleName())) {
				return true;
			}
		}
		
		return false;
	}
	
	private static TypeRef fixFieldType(TypeRef ref) {
		if (isType(ref, long.class)) {
			return Type(Long.class);
		} else if (isType(ref, int.class)) {
			return Type(Integer.class);
		} else if (isType(ref, double.class)) {
			return Type(Double.class);
		} else if (isType(ref, float.class)) {
			return Type(Float.class);
		} else if (isType(ref, boolean.class)) {
			return Type(Boolean.class);
		}
		
		return ref;
	}
}
