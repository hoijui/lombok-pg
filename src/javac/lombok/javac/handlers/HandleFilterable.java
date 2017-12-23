package lombok.javac.handlers;

import lombok.Filterable;
import lombok.core.AnnotationValues;
import lombok.core.handlers.FilterableHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacField;
import lombok.javac.handlers.ast.JavacType;

import org.mangosdk.spi.ProviderFor;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

/**
 * Handles the {@link Filterable} annotation for eclipse.
 */
@ProviderFor(JavacAnnotationHandler.class)
public class HandleFilterable extends JavacAnnotationHandler<Filterable> {

	@Override
	public void handle(final AnnotationValues<Filterable> annotation, final JCAnnotation source, final JavacNode annotationNode) {
		FilterableHandler<JavacType, JavacField> handler = new FilterableHandler<JavacType, JavacField>();
		
		JavacNode node = annotationNode.up();
		handler.addFilterMethod(JavacType.typeOf(node.up(), source), JavacField.fieldOf(node, source));
	}
}
