package lombok.eclipse.handlers;

import lombok.PropertyAttribute;
import lombok.core.AnnotationValues;
import lombok.core.handlers.PropertyAttributeHandler;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.ast.EclipseField;
import lombok.eclipse.handlers.ast.EclipseMethod;
import lombok.eclipse.handlers.ast.EclipseType;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

@ProviderFor(EclipseAnnotationHandler.class)
public class HandlePropertyAttribute extends EclipseAnnotationHandler<PropertyAttribute> {

	public void handle(final AnnotationValues<PropertyAttribute> annotation, final Annotation ast, final EclipseNode annotationNode) {
		final EclipseType type = EclipseType.typeOf(annotationNode, ast);
		final EclipseMethod method = EclipseMethod.methodOf(annotationNode, ast);	
		final EclipseField field = EclipseField.fieldOf(annotationNode, ast);
		
		new PropertyAttributeHandler<EclipseType, EclipseMethod, EclipseField>(type, method, field, annotationNode).handle();
	}

}
