package lombok.eclipse.handlers;

import java.util.Arrays;
import java.util.List;

import lombok.Cloneable;
import lombok.Singleton;
import lombok.core.AnnotationValues;
import lombok.core.handlers.CloneableHandler;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.ast.EclipseField;
import lombok.eclipse.handlers.ast.EclipseType;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

/**
 * Handles the {@link Singleton} annotation for eclipse.
 */
@ProviderFor(EclipseAnnotationHandler.class)
public class HandleCloneable extends EclipseAnnotationHandler<Cloneable> {

	@Override
	public void handle(final AnnotationValues<Cloneable> annotation, final Annotation source, final EclipseNode annotationNode) {
		Cloneable instance = annotation.getInstance();
		List<String> excludes = Arrays.asList(instance.exclude());
		List<String> of = Arrays.asList(instance.of());
		
		if (of.size() == 0) {
			of = null;
		} else {
			excludes = null;
		}
		
		new CloneableHandler<EclipseType, EclipseField>().addCloneableMethod(EclipseType.typeOf(annotationNode.up(), source), of, excludes);
	}
}
