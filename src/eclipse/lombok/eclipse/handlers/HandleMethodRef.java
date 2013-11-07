package lombok.eclipse.handlers;

import lombok.MethodRef;
import lombok.core.AnnotationValues;
import lombok.core.handlers.MethodRefHandler;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.EclipseHandlerUtil.MemberExistsResult;
import lombok.eclipse.handlers.ast.EclipseMethod;
import lombok.eclipse.handlers.ast.EclipseType;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

@ProviderFor(EclipseAnnotationHandler.class)
public class HandleMethodRef extends EclipseAnnotationHandler<MethodRef> {
	
	@Override
	public void handle(AnnotationValues<MethodRef> annotation, Annotation ast,
			EclipseNode annotationNode) {
		final EclipseType type = EclipseType.typeOf(annotationNode, ast);
		final EclipseMethod method = EclipseMethod.methodOf(annotationNode, ast);
		
		boolean getThisExists = EclipseHandlerUtil.methodExists("__getThis", annotationNode, false, 0) != MemberExistsResult.NOT_EXISTS;
		
		new MethodRefHandler(type, method, annotationNode, getThisExists).handle(annotation);
	}

}
