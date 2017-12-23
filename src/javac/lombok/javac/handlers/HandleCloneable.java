package lombok.javac.handlers;

import java.util.Arrays;
import java.util.List;

import lombok.Cloneable;
import lombok.Singleton;
import lombok.core.AnnotationValues;
import lombok.core.handlers.CloneableHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacField;
import lombok.javac.handlers.ast.JavacType;

import org.mangosdk.spi.ProviderFor;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

/**
 * Handles the {@link Singleton} annotation for eclipse.
 */
@ProviderFor(JavacAnnotationHandler.class)
public class HandleCloneable extends JavacAnnotationHandler<Cloneable> {

	@Override
	public void handle(final AnnotationValues<Cloneable> annotation, final JCAnnotation source, final JavacNode annotationNode) {
		Cloneable instance = annotation.getInstance();
		List<String> excludes = Arrays.asList(instance.exclude());
		List<String> of = Arrays.asList(instance.of());
		
		if (of.size() == 0) {
			of = null;
		} else {
			excludes = null;
		}
		
		new CloneableHandler<JavacType, JavacField>().addCloneableMethod(JavacType.typeOf(annotationNode.up(), source), of, excludes);
	}
}
