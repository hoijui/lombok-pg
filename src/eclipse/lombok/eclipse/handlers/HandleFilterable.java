package lombok.eclipse.handlers;

import lombok.Filterable;
import lombok.core.AnnotationValues;
import lombok.core.handlers.FilterableHandler;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.ast.EclipseField;
import lombok.eclipse.handlers.ast.EclipseType;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

/**
 * Handles the {@link Filterable} annotation for eclipse.
 */
@ProviderFor(EclipseAnnotationHandler.class)
public class HandleFilterable extends EclipseAnnotationHandler<Filterable> {

	@Override
	public void handle(final AnnotationValues<Filterable> annotation, final Annotation source, final EclipseNode annotationNode) {
		FilterableHandler<EclipseType, EclipseField> handler = new FilterableHandler<EclipseType, EclipseField>();
		
		EclipseNode node = annotationNode.up();
		handler.addFilterMethod(EclipseType.typeOf(node.up(), source), EclipseField.fieldOf(node, source));
	}
}
