package lombok.core.handlers;

import static lombok.ast.AST.*;

import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.IntLiteral;

import lombok.MethodRef;
import lombok.RequiredArgsConstructor;
import lombok.ast.Argument;
import lombok.ast.Call;
import lombok.ast.Expression;
import lombok.ast.FieldDecl;
import lombok.ast.IField;
import lombok.ast.IMethod;
import lombok.ast.IType;
import lombok.ast.NumberLiteral;
import lombok.ast.Return;
import lombok.ast.Statement;
import lombok.ast.TypeRef;
import lombok.core.AnnotationValues;
import lombok.core.DiagnosticsReceiver;

@RequiredArgsConstructor
public class MethodRefHandler<TYPE_TYPE extends IType<METHOD_TYPE, ?, ?, ?, ?, ?>, METHOD_TYPE extends IMethod<TYPE_TYPE, ?, ?, ?>, FIELD_TYPE extends IField<TYPE_TYPE, ?, ?, ?>>  {
	private final TYPE_TYPE type;
	private final METHOD_TYPE method;
	private final DiagnosticsReceiver diagnosticsReceiver;

	public void handle(AnnotationValues<MethodRef> annotation) {
		String staticFieldName = "__" + method.name();
		String fieldName = "_" + method.name();

		TypeRef classTypeRef = Type(type.qualifiedName());
		TypeRef returns = method.returns(); 	// TODO map primitive types and void
		boolean isVoid = false;
		if (returns.getTypeName().equals("void")) {
			returns = new TypeRef(Void.class);	// map simple 'void' to 'Void'
			isVoid = true;
		}
		
		List<Argument> arguments = method.arguments();
		String staticRefTypeName = "com.doctusoft.common.core.bean.ClassMethodReference";
		String objectRefTypeName = "com.doctusoft.common.core.bean.ObjectMethodReference";
		int argumentsSize = arguments.size();
		if (argumentsSize <= 2) {
			staticRefTypeName = "com.doctusoft.common.core.bean.ParametricClassMethodReferences.ClassMethodReference" + argumentsSize;
			objectRefTypeName = "com.doctusoft.common.core.bean.ParametricObjectMethodReferences.ObjectMethodReference" + argumentsSize;
		}
		final TypeRef staticFieldTypeRef = Type(staticRefTypeName)
				.withTypeArgument(classTypeRef)
				.withTypeArgument(returns);

		final TypeRef fieldTypeRef = Type(objectRefTypeName)
				.withTypeArgument(Type(type.qualifiedName()))
				.withTypeArgument(returns);
		
		if (argumentsSize <= 2) {
			// add parameter arguments
			for (Argument argument : arguments) {
				staticFieldTypeRef.withTypeArgument(argument.getType());
				fieldTypeRef.withTypeArgument(argument.getType());
			}
			// TODO in case of these references, we can also generate the invoke() method so that parameter names also match
		}
		
		Call call = Call(Name("object"), method.name());
		int index = 0;
		for (Argument argument :  method.arguments()) {
			TypeRef argumentType = argument.getType();	// TODO handle primitive types
			call.withArgument(Cast(argumentType, ArrayRef(Name("arguments"), Number(index))));
			index ++;
		}
		Statement<?> statement = isVoid?Block().withStatement(call).withStatement(Return(Null())):Return(call);
		Expression<?> initialization = New(staticFieldTypeRef).withTypeDeclaration(
				ClassDecl("").makeAnonymous().makeLocal()
					.withMethod(MethodDecl(returns, "applyInner").makePublic().withAnnotation(Annotation(Type(Override.class)))
						.withArgument(Arg(Type(type.qualifiedName()), "object"))
						.withArgument(Arg(Type(Object.class).withDimensions(1), "arguments"))
						.withStatement(statement)
						)); 
		
		type.editor().injectField( new FieldDecl(staticFieldTypeRef, staticFieldName)
					.makeStatic().makeFinal().makePublic()
					.withInitialization(initialization));
		
		type.editor().injectField(new FieldDecl(fieldTypeRef, fieldName)
					.makeFinal().makePublic()
					.withInitialization(Call(Name(staticFieldName), "on").withArgument(This()))
					);
	}
}
