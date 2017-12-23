package lombok.core.handlers;

import static lombok.ast.pg.AST.Annotation;
import static lombok.ast.pg.AST.Arg;
import static lombok.ast.pg.AST.ArrayRef;
import static lombok.ast.pg.AST.Block;
import static lombok.ast.pg.AST.Call;
import static lombok.ast.pg.AST.Cast;
import static lombok.ast.pg.AST.ClassDecl;
import static lombok.ast.pg.AST.MethodDecl;
import static lombok.ast.pg.AST.Name;
import static lombok.ast.pg.AST.New;
import static lombok.ast.pg.AST.Null;
import static lombok.ast.pg.AST.Number;
import static lombok.ast.pg.AST.Return;
import static lombok.ast.pg.AST.This;
import static lombok.ast.pg.AST.Type;

import java.util.List;

import org.eclipse.jdt.internal.ui.search.MethodExitsFinder;

import lombok.MethodRef;
import lombok.RequiredArgsConstructor;
import lombok.ast.pg.AST;
import lombok.ast.pg.Argument;
import lombok.ast.pg.Call;
import lombok.ast.pg.Expression;
import lombok.ast.pg.FieldDecl;
import lombok.ast.pg.IField;
import lombok.ast.pg.IMethod;
import lombok.ast.pg.IType;
import lombok.ast.pg.Statement;
import lombok.ast.pg.TypeRef;
import lombok.core.AnnotationValues;
import lombok.core.DiagnosticsReceiver;

@RequiredArgsConstructor
public class MethodRefHandler<TYPE_TYPE extends IType<METHOD_TYPE, ?, ?, ?, ?, ?>, METHOD_TYPE extends IMethod<TYPE_TYPE, ?, ?, ?>, FIELD_TYPE extends IField<TYPE_TYPE, ?, ?, ?>>  {
	private final TYPE_TYPE type;
	private final METHOD_TYPE method;
	private final DiagnosticsReceiver diagnosticsReceiver;
	private final boolean getThisExists;
	
	public void handle(AnnotationValues<MethodRef> annotation) {
		String staticFieldName = "__" + method.name();
		String fieldName = "_" + method.name();
		boolean isInterface = type.isInterface();

		TypeRef classTypeRef = Type(type.qualifiedName());
		TypeRef returns = method.returns(); 	// TODO map primitive types and void
		boolean isVoid = false;
		// check toString too, to make javac work. In javac, getTypeName() returns "unresolved"
		if (returns.getTypeName().equals("void") || returns.toString().equals("void")) {
			returns = Type(Void.class);	// map simple 'void' to 'Void'
			isVoid = true;
		} else {
			returns = mapPrimitiveTypes(returns);
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
				TypeRef argType = mapPrimitiveTypes(argument.getType());
				staticFieldTypeRef.withTypeArgument(argType);
				fieldTypeRef.withTypeArgument(argType);
			}
			// TODO in case of these references, we can also generate the invoke() method so that parameter names also match
		}
		
		Call call = Call(Name("object"), method.name());
		int index = 0;
		for (Argument argument :  method.arguments()) {
			TypeRef argumentType = mapPrimitiveTypes(argument.getType());
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

		if (!isInterface) {
			if (!getThisExists) {
				/*
				 * I could just use This() as parameter of the .on() invocation below, but that raises 'illegal forward refernce' when compiled with Javac.
				 * The exact same code written in pure java works, but this code generation might have some hack.
				 * The "__getThis()" method is a workaround for this;
				 */
				type.editor().injectMethod(AST.MethodDecl(classTypeRef, "__getThis").withStatement(
							Return(This())).makePrivate());
			}
			
			
			type.editor().injectField(new FieldDecl(fieldTypeRef, fieldName)
					.makeFinal().makePublic()
					.withInitialization(Call(Name(staticFieldName), "on").withArgument(Call("__getThis")))
					);
		}
	}
	
	public static TypeRef mapPrimitiveTypes(TypeRef sourceRef) {
		String typeName = sourceRef.toString();
		return Type(AttributeHandler.getAttributeTypeName(typeName));
	}
}
