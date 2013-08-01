package lombok.core.handlers;

import static lombok.ast.AST.FieldDecl;
import static lombok.ast.AST.Name;
import static lombok.ast.AST.Type;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.ast.AST;
import lombok.ast.Call;
import lombok.ast.IField;
import lombok.ast.IMethod;
import lombok.ast.IType;
import lombok.ast.StringLiteral;
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
	
	private void injectAttribute( final TYPE_TYPE type, final TypeRef valueTypeRef, String attributeTypeName, final String attributeName) {
		final boolean isQualified = attributeTypeName.contains(".");
		attributeTypeName = attributeTypeName.replaceAll("<.*>", "");
		TypeRef mappedValueTypeRef = valueTypeRef;
		// for non-primitives we retain the original typeref because it contains the type arguments properly (eg List<String>)
		if (primitives.containsKey(attributeTypeName)) {
			// for primitives, we have to map the type to it's object equivalent. This will not contain any type-argument of course
			mappedValueTypeRef = AST.Type(getAttributeTypeName(attributeTypeName));
			// we map primitives types to their object equivalents. Under javac, I could not easily create a classliteral for primitive types,
			// so I decided to use the object equivalent in all cases.
			attributeTypeName = getAttributeTypeName(attributeTypeName);
		}
		String attributesClassName = "com.doctusoft.common.core.bean.Attributes";
		final Call createAttribute = new Call( Name( attributesClassName ), "of" )
			.withArgument(AST.Cast(AST.Type(Class.class), new Call( Name( attributesClassName ), "uncheckedForName").withArgument(new StringLiteral(type.qualifiedName()))))
			.withArgument(new StringLiteral(attributeName))
			.withArgument(isQualified?
						AST.Cast(AST.Type(Class.class), new Call( Name( attributesClassName ), "uncheckedForName").withArgument(new StringLiteral(attributeTypeName)))
						:AST.ClassLiteral(attributeTypeName, null));
		
		final TypeRef attributeTypeRef = Type("com.doctusoft.common.core.bean.Attribute")
				.withTypeArgument(Type(type.qualifiedName()))
				.withTypeArgument(mappedValueTypeRef);
		
		type.editor().injectField( FieldDecl(attributeTypeRef, "_" + attributeName)
				.withInitialization(createAttribute)
				.makeFinal().makeStatic().makePublic());
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
