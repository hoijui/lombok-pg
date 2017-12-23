package lombok.core.handlers;

import static lombok.ast.pg.AST.*;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.ast.pg.AST;
import lombok.ast.pg.Call;
import lombok.ast.pg.Expression;
import lombok.ast.pg.IField;
import lombok.ast.pg.IMethod;
import lombok.ast.pg.IType;
import lombok.ast.pg.StringLiteral;
import lombok.ast.pg.TypeRef;
import lombok.core.DiagnosticsReceiver;

import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@RequiredArgsConstructor
public final class PropertyAttributeHandler<TYPE_TYPE extends IType<METHOD_TYPE, ?, ?, ?, ?, ?>, METHOD_TYPE extends IMethod<TYPE_TYPE, ?, ?, ?>, FIELD_TYPE extends IField<TYPE_TYPE, ?, ?, ?>> {
	private final TYPE_TYPE type;
	private final METHOD_TYPE method;
	private final FIELD_TYPE field;
	private final DiagnosticsReceiver diagnosticsReceiver;
	
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
		try {
			JCVariableDecl fd = (JCVariableDecl) field.get();
			returnType = fd.getType().toString();
		} catch (Throwable t) {
			// do nothing, it's probably because JCMethodDecl is not found under Eclipse, and it's normal
		}		
		injectAttribute(type, field.type(), returnType, field.filteredName());
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
		try {
			JCMethodDecl md = (JCMethodDecl) method.get();
			returnType = md.restype.toString();
		} catch (Throwable t) {
			// do nothing, it's probably because JCMethodDecl is not found under Eclipse, and it's normal
		}		
		injectAttribute(type, method.returns(), returnType, attributeName);
	}
	
	private void injectAttribute( final TYPE_TYPE type, final TypeRef valueTypeRef, String _attributeTypeName, final String attributeName) {
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
		
		// TODO handle boolean attributes
		String getterPrefix = "get";
		if (_attributeTypeName.equals("boolean")) {
			getterPrefix = "is";
		}
		String getterName = getterPrefix + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
		String setterName = "set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
		
		TypeRef hostTypeRef = Type(type.qualifiedName());
		final TypeRef attributeTypeRef = Type("com.doctusoft.common.core.bean.Attribute")
				.withTypeArgument(hostTypeRef)
				.withTypeArgument(mappedValueTypeRef);
		
		// TODO: return correct type literals from getParent and getType
		// TODO: add proper type arguments to the return types of getParent and getType that work with Eclipse Indigo
		Expression<?> initialization = New(attributeTypeRef).withTypeDeclaration(
				ClassDecl("").makeAnonymous().makeLocal()
					.withMethod(MethodDecl(mappedValueTypeRef, "getValue").makePublic().withAnnotation(Annotation(Type(Override.class)))
							.withArgument(Arg(hostTypeRef, "instance"))
							.withStatement(Return(Call(Name("instance"), getterName)))
							)
					.withMethod(MethodDecl(Type("void"), "setValue").makePublic().withAnnotation(Annotation(Type(Override.class)))
							.withArgument(Arg(hostTypeRef, "instance"))
							.withArgument(Arg(mappedValueTypeRef, "value"))
							.withStatement(Call(Name("instance"), setterName).withArgument(Name("value")))
							)
					// if I add typearguments to the class, eclipse indigo throws InvalidArgumentExceptions in dom.ASTNode.setSourceRange()
					.withMethod(MethodDecl(Type("Class"), "getParent").makePublic().withAnnotation(Annotation(Type(Override.class)))
							.withStatement(Return(Null()))
							)
					.withMethod(MethodDecl(Type("Class"), "getType").makePublic().withAnnotation(Annotation(Type(Override.class)))
							.withStatement(Return(Null()))
							)
//					.withMethod(MethodDecl(Type("Class").withTypeArgument(hostTypeRef), "getParent").makePublic().withAnnotation(Annotation(Type(Override.class)))
//							.withStatement(Return(hostTypeRef))
//							)
//					.withMethod(MethodDecl(Type("Class").withTypeArgument(mappedValueTypeRef), "getType").makePublic().withAnnotation(Annotation(Type(Override.class)))
//							.withStatement(Return(Null()))
//							)
					.withMethod(MethodDecl(Type("String"), "getName").makePublic().withAnnotation(Annotation(Type(Override.class)))
							.withStatement(Return(String(attributeName)))
							)
				);
		type.editor().injectField( FieldDecl(attributeTypeRef, "_" + attributeName)
				.makeFinal().makeStatic().makePublic()
				.withInitialization(initialization)
				);
	}
	
	private String getAttributeTypeName( final String typeName) {
		if( primitives.containsKey(typeName) ) {
			final String attributeTypeName = primitives.get(typeName);
			if( attributeTypeName != null) {
				return attributeTypeName;
			}
		}
		return typeName;
	}
}
