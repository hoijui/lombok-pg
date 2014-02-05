package lombok.core.handlers;

import static lombok.ast.AST.Annotation;
import static lombok.ast.AST.Arg;
import static lombok.ast.AST.Call;
import static lombok.ast.AST.ClassDecl;
import static lombok.ast.AST.Field;
import static lombok.ast.AST.FieldDecl;
import static lombok.ast.AST.MethodDecl;
import static lombok.ast.AST.Name;
import static lombok.ast.AST.New;
import static lombok.ast.AST.Return;
import static lombok.ast.AST.String;
import static lombok.ast.AST.Type;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.ast.AST;
import lombok.ast.Block;
import lombok.ast.ClassDecl;
import lombok.ast.Expression;
import lombok.ast.FieldDecl;
import lombok.ast.IField;
import lombok.ast.IMethod;
import lombok.ast.IType;
import lombok.ast.TypeRef;
import lombok.core.DiagnosticsReceiver;

import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@RequiredArgsConstructor
public final class AttributeHandler<TYPE_TYPE extends IType<METHOD_TYPE, ?, ?, ?, ?, ?>, METHOD_TYPE extends IMethod<TYPE_TYPE, ?, ?, ?>, FIELD_TYPE extends IField<TYPE_TYPE, ?, ?, ?>> {
	private final TYPE_TYPE type;
	private final METHOD_TYPE method;
	private final FIELD_TYPE field;
	private final DiagnosticsReceiver diagnosticsReceiver;
	private final boolean observable;
	private final boolean staticField;
	
	private static final Map<String,String> primitives;
	
	static {
		primitives = new HashMap<String,String>(9);
		primitives.put("boolean", "Boolean");
		primitives.put("byte", "Byte");
		primitives.put("char", "Character");
		primitives.put("double", "Double");
		primitives.put("float", "Float");
		primitives.put("int", "Integer");
		primitives.put("long", "Long");
		primitives.put("short", "Short");
		primitives.put("void", "Void");
	}
	
	public void handle() {
		
		if( field != null ) {
			createAttribute(type, field);
		}
		else if (method != null ) {
			createAttribute(type, method);
		}
		else {
			diagnosticsReceiver.addError("Ismeretlen hiba!");
		}
		
	}

	private void createAttribute( final TYPE_TYPE type, final FIELD_TYPE field ) {
		String returnType = field.type().getTypeName();
		Object wrappedJavacAttrType = null;
		try {
			JCVariableDecl fd = (JCVariableDecl) field.get();
			returnType = fd.getType().toString();
			wrappedJavacAttrType = fd.vartype.type;
//			System.out.println(returnType + " wrapped: " + wrappedJavacAttrType);
		} catch (Throwable t) {
			// do nothing, it's probably because JCMethodDecl is not found under Eclipse, and it's normal
		}		
		injectAttribute(type, field.type(), returnType, field.filteredName(), wrappedJavacAttrType);
	}
	
	private void createAttribute(final TYPE_TYPE type, final METHOD_TYPE method) {
		final String fieldName = method.name();
		
		String attributeName;
		if( fieldName.substring(0, 3).equals("get")) {
			attributeName = Character.toLowerCase(fieldName.charAt(3) ) + fieldName.substring(4);
		}
		else if (fieldName.substring(0, 2).equals("is")) {
			attributeName = Character.toLowerCase(fieldName.charAt(2) ) + fieldName.substring(3);
		}
		else {
			diagnosticsReceiver.addError("Nem getter metóduson van.");
			return;
		}

		String returnType = method.returns().getTypeName();
		Object wrappedJavacAttrType = null;
		try {
			JCMethodDecl md = (JCMethodDecl) method.get();
			wrappedJavacAttrType = md.restype.type;
			returnType = md.restype.toString();
		} catch (Throwable t) {
			// do nothing, it's probably because JCMethodDecl is not found under Eclipse, and it's normal
		}		
		injectAttribute(type, method.returns(), returnType, attributeName, wrappedJavacAttrType);
	}
	
	private void injectAttribute( final TYPE_TYPE type, final TypeRef valueTypeRef, String _attributeTypeName, final String attributeName, final Object wrappedJavacAttrType) {
		final boolean isQualified = _attributeTypeName.contains(".");
		_attributeTypeName = _attributeTypeName.replaceAll("<.*>", "");
		String attributeTypeName = _attributeTypeName;
		TypeRef mappedValueTypeRef = valueTypeRef;
		// for non-primitives we retain the original typeref because it contains the type arguments properly (eg List<String>)
		if (primitives.containsKey(attributeTypeName)) {
			// for primitives, we have to map the type to it's object equivalent. This will not contain any type-argument of course
			mappedValueTypeRef = AST.Type(getAttributeTypeName(attributeTypeName));
			// we map primitives types to their object equivalents. Under javac, I could not easily create a classliteral for primitive types,
			// so I decided to use the object equivalent in all cases.
			attributeTypeName = getAttributeTypeName(attributeTypeName);
		}
		
		String getterPrefix = "get";
		if (_attributeTypeName.equals("boolean")) {
			getterPrefix = "is";
		}
		String getterName = getterPrefix + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
		String setterName = "set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
		
		String qualifiedHostTypeName = type.name();
		if (qualifiedHostTypeName.contains("$")) {
			qualifiedHostTypeName = qualifiedHostTypeName.substring(qualifiedHostTypeName.lastIndexOf('$') + 1);
		}
		TypeRef hostTypeRef = Type(qualifiedHostTypeName);
		String attrFieldClassName = observable?"com.doctusoft.common.core.bean.ObservableAttribute":"com.doctusoft.common.core.bean.Attribute";
		final TypeRef attributeTypeRef = Type(attrFieldClassName)
				.withTypeArgument(hostTypeRef)
				.withTypeArgument(mappedValueTypeRef);
		
		// TODO: add "rawtypes" to suppresswarnings. This needs ArrayLiteral or something like that
		// TODO: add proper type arguments to the return types of getParent and getType that work with Eclipse Indigo
		// adding typearguments to the class, eclipse indigo throws InvalidArgumentExceptions in dom.ASTNode.setSourceRange()
		// TODO: leave setValue empty if there's no setter for that attribute
		String valueTypeString = mappedValueTypeRef.toString().replaceAll("<.*>", "");
		ClassDecl classDecl = ClassDecl("").makeAnonymous().makeLocal()
				.withMethod(MethodDecl(mappedValueTypeRef, "getValue").makePublic().withAnnotation(Annotation(Type(Override.class)))
						.withArgument(Arg(hostTypeRef, "instance"))
						.withStatement(Return(AST.Cast(valueTypeRef, Call(Name("instance"), getterName))))
						)
				.withMethod(MethodDecl(Type("void"), "setValue").makePublic().withAnnotation(Annotation(Type(Override.class)))
						.withArgument(Arg(hostTypeRef, "instance"))
						.withArgument(Arg(mappedValueTypeRef, "value"))
						.withStatement(Call(Name("instance"), setterName).withArgument(Name("value")))
						)
//the version without typearguments							
//				.withMethod(MethodDecl(Type("Class"), "getParent").makePublic().withAnnotation(Annotation(Type(Override.class)))
//						.withStatement(Return(AST.Null()))
//						)
//				.withMethod(MethodDecl(Type("Class"), "getType").makePublic().withAnnotation(Annotation(Type(Override.class)))
//						.withStatement(Return(AST.Null()))
//						)
				.withMethod(MethodDecl(Type("Class").withTypeArgument(hostTypeRef), "getParent").makePublic().withAnnotation(Annotation(Type(Override.class)))
						.withStatement(Return(AST.ClassLiteral(hostTypeRef.toString(), type.get())))
						)
				.withMethod(MethodDecl(Type("String"), "getName").makePublic().withAnnotation(Annotation(Type(Override.class)))
						.withStatement(Return(String(attributeName)))
						);
		if (staticField) {
			classDecl.withMethod(MethodDecl(Type("Class").withTypeArgument(mappedValueTypeRef), "getType").makePublic()
					.withAnnotation(Annotation(Type(Override.class)))
					.withAnnotation(Annotation(Type(SuppressWarnings.class)).withValue(AST.String("unchecked")))
					.withStatement(Return(AST.Cast(Type(Class.class), AST.ClassLiteral(valueTypeString, wrappedJavacAttrType))))
					);
		} else {
			// when not a static field, we probably have type parameters, so we skip returning a class literal
			// TODO this is not too nice this way
			classDecl.withMethod(MethodDecl(Type("Class").withTypeArgument(mappedValueTypeRef), "getType").makePublic()
					.withAnnotation(Annotation(Type(Override.class)))
					.withAnnotation(Annotation(Type(SuppressWarnings.class)).withValue(AST.String("unchecked")))
					.withStatement(Return(AST.Null()))
					);
		}
		// replace or insert the setter method
		Block setterBody = AST.Block()
				.withStatement(AST.Assign(Field(AST.This(), attributeName), Name(attributeName)));

		if (observable) {
			// TODO check if the container type is an interface - we cannot implement this stuff on interfaces
			TypeRef attributeListenerTypeRef = Type("com.doctusoft.common.core.bean.internal.AttributeListeners").withTypeArgument(mappedValueTypeRef);
			String listenerFieldName = "$" + attributeName + "$listeners";
			type.editor().injectField(FieldDecl(attributeListenerTypeRef, listenerFieldName)
						.makePrivate()
						.withInitialization(New(attributeListenerTypeRef)));
			classDecl.withMethod(MethodDecl(Type("com.doctusoft.common.core.bean.ListenerRegistration"), "addChangeListener")
						.makePublic().withAnnotation(Annotation(Type(Override.class)))
						.withArgument(Arg(hostTypeRef, "object"))
						.withArgument(Arg(Type("com.doctusoft.common.core.bean.ValueChangeListener").withTypeArgument(mappedValueTypeRef), "valueChangeListener"))
						.withStatement(Return(
									Call(Field(Name("object"), listenerFieldName), "addListener")
										.withArgument(Name("valueChangeListener"))
								)));
			setterBody.withStatement(Call(Name(listenerFieldName), "fireListeners").withArgument(Name(attributeName)));
		}
		// replace or insert the setter
		boolean setterFound = false;
		for (METHOD_TYPE method : type.methods()) {
			if (setterName.equals(method.name())) {
				if (observable) {
					method.editor().replaceBody(setterBody);	// replace the setter to an observing setter
				}
				setterFound = true;
				break;
			}
		}
		if (!setterFound) {
			type.editor().injectMethod(MethodDecl(Type("void"), setterName).makePublic()
					.withArgument(Arg(valueTypeRef, attributeName))
					.withStatements(setterBody.getStatements()));
		}
		// insert the getter if not found
		boolean getterFound = false;
		for (METHOD_TYPE method : type.methods()) {
			if (getterName.equals(method.name())) {
				getterFound = true;
				break;
			}
		}
		if (!getterFound) {
			type.editor().injectMethod(MethodDecl(valueTypeRef, getterName).makePublic()
						.withStatement(Return(Name(attributeName))));
		}
		
		
		Expression<?> initialization = New(attributeTypeRef).withTypeDeclaration(classDecl);
		FieldDecl attributeFieldDecl = FieldDecl(attributeTypeRef, "_" + attributeName)
				.makeFinal().makePublic()
				.withInitialization(initialization);
		
		if (staticField) {
			attributeFieldDecl.makeStatic();
		}
		
		type.editor().injectField( attributeFieldDecl
				);
	}
	
	public static String getAttributeTypeName( final String typeName) {
		if( primitives.containsKey(typeName) ) {
			final String attributeTypeName = primitives.get(typeName);
			if( attributeTypeName != null) {
				return attributeTypeName;
			}
		}
		return typeName;
	}
}
