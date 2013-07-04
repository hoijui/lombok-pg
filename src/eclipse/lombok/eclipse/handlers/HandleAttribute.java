package lombok.eclipse.handlers;

import lombok.Attribute;
import lombok.LazyGetter;
import lombok.core.AnnotationValues;
import lombok.core.handlers.AttributeHandler;
import lombok.core.handlers.LazyGetterHandler;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.ast.EclipseField;
import lombok.eclipse.handlers.ast.EclipseMethod;
import lombok.eclipse.handlers.ast.EclipseType;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

@ProviderFor(EclipseAnnotationHandler.class)
public class HandleAttribute extends EclipseAnnotationHandler<Attribute> {

	public void handle(final AnnotationValues<Attribute> annotation, final Annotation ast, final EclipseNode annotationNode) {
		final EclipseType type = EclipseType.typeOf(annotationNode, ast);
		final EclipseMethod method = EclipseMethod.methodOf(annotationNode, ast);	
		final EclipseField field = EclipseField.fieldOf(annotationNode, ast);
		
		new AttributeHandler<EclipseType, EclipseMethod, EclipseField>(type, method, field, annotationNode).handle();
	}

}
