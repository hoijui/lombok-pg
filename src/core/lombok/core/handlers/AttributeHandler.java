package lombok.core.handlers;

import static lombok.ast.AST.Assign;
import static lombok.ast.AST.Block;
import static lombok.ast.AST.ClassDecl;
import static lombok.ast.AST.EnumConstant;
import static lombok.ast.AST.Field;
import static lombok.ast.AST.FieldDecl;
import static lombok.ast.AST.If;
import static lombok.ast.AST.MethodDecl;
import static lombok.ast.AST.Name;
import static lombok.ast.AST.New;
import static lombok.ast.AST.NewArray;
import static lombok.ast.AST.Not;
import static lombok.ast.AST.Number;
import static lombok.ast.AST.Return;
import static lombok.ast.AST.Synchronized;
import static lombok.ast.AST.True;
import static lombok.ast.AST.Type;
import static lombok.core.TransformationsUtil.toAllGetterNames;
import static lombok.core.TransformationsUtil.toGetterName;
import static lombok.core.util.ErrorMessages.canBeUsedOnClassOnly;
import static lombok.core.util.ErrorMessages.canBeUsedOnConcreteClassOnly;
import static lombok.core.util.ErrorMessages.canBeUsedOnFieldOnly;
import static lombok.core.util.ErrorMessages.canBeUsedOnInitializedFieldOnly;
import static lombok.core.util.ErrorMessages.canBeUsedOnPrivateFinalFieldOnly;
import static lombok.core.util.ErrorMessages.requiresDefaultOrNoArgumentConstructor;

import java.util.ArrayList;
import java.util.List;

import com.doctusoft.bean.Attribute;
import com.doctusoft.bean.AttributeImpl;
import com.doctusoft.bean.Attributes;

import lombok.AccessLevel;
import lombok.LazyGetter;
import lombok.RequiredArgsConstructor;
import lombok.Singleton;
import lombok.ast.AST;
import lombok.ast.Argument;
import lombok.ast.Call;
import lombok.ast.Expression;
import lombok.ast.FieldDecl;
import lombok.ast.IMethod;
import lombok.ast.IMethod.ArgumentStyle;
import lombok.ast.IType;
import lombok.ast.InstanceOf;
import lombok.ast.New;
import lombok.ast.NullLiteral;
import lombok.ast.NumberLiteral;
import lombok.ast.StringLiteral;
import lombok.ast.TypeRef;
import lombok.ast.WrappedExpression;
import lombok.ast.WrappedTypeRef;
import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.DiagnosticsReceiver;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
public final class AttributeHandler<TYPE_TYPE extends IType<METHOD_TYPE, ?, ?, ?, ?, ?>, METHOD_TYPE extends IMethod<TYPE_TYPE, ?, ?, ?>> {
	private final TYPE_TYPE type;
	private final METHOD_TYPE method;
	private final DiagnosticsReceiver diagnosticsReceiver;

	public void handle() {
		createAttribute(type, method);
	}

	private void createAttribute(final TYPE_TYPE type, final METHOD_TYPE method) {
		String fieldName = method.name();
		
		if( fieldName.substring(0, 3).equals("get")) { // TODO is
			final String attributeName = "_" + Character.toLowerCase(fieldName.charAt(3) ) + fieldName.substring(4);
			
			final TypeRef returnType = method.returns();
			final Call createAttribute = new Call( Name( Attributes.class), "of" )
				.withArgument(AST.ClassLiteral(type.qualifiedName()))
				.withArgument(new StringLiteral(attributeName))
				.withArgument(AST.ClassLiteral(returnType.getTypeName()));
			
			
			type.editor().injectField( FieldDecl(Type(Attribute.class)
					.withTypeArgument(Type(type.qualifiedName()))
					.withTypeArgument(returnType), attributeName)
					.withInitialization(createAttribute)
					.makeFinal()
					.makeStatic()
					.makePublic());
			
		}
		else {
			diagnosticsReceiver.addError("Nem getteren van!!!");
		}
	}

}
