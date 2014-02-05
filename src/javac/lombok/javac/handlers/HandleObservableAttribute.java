package lombok.javac.handlers;

import static lombok.javac.handlers.Javac.deleteImport;
import static lombok.javac.handlers.JavacHandlerUtil.deleteAnnotationIfNeccessary;
import lombok.AccessLevel;
import lombok.ObservableAttribute;
import lombok.core.AnnotationValues;
import lombok.core.handlers.AttributeHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacField;
import lombok.javac.handlers.ast.JavacMethod;
import lombok.javac.handlers.ast.JavacType;

import org.mangosdk.spi.ProviderFor;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

@ProviderFor(JavacAnnotationHandler.class)
public class HandleObservableAttribute extends JavacAnnotationHandler<ObservableAttribute> {

	@Override
	public void handle(final AnnotationValues<ObservableAttribute> annotation, final JCAnnotation ast, final JavacNode annotationNode) {
		final JavacType type = JavacType.typeOf(annotationNode, ast);
		final JavacMethod method = JavacMethod.methodOf(annotationNode, ast);
		final JavacField field = JavacField.fieldOf(annotationNode, ast);
		final boolean staticField = !"false".equals(annotation.getRawExpression("staticField"));
		
		new AttributeHandler<JavacType, JavacMethod, JavacField>(type, method, field, annotationNode, true, staticField).handle();
		deleteAnnotationIfNeccessary(annotationNode, ObservableAttribute.class);
		deleteImport(annotationNode, AccessLevel.class);
	}
}
