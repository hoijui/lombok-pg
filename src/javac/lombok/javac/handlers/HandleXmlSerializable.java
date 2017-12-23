package lombok.javac.handlers;

import lombok.Singleton;
import lombok.XmlSerializable;
import lombok.ast.pg.Expression;
import lombok.core.AnnotationValues;
import lombok.core.handlers.XmlSerializableHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacField;
import lombok.javac.handlers.ast.JavacType;

import org.mangosdk.spi.ProviderFor;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

/**
 * Handles the {@link Singleton} annotation for javac.
 */
@ProviderFor(JavacAnnotationHandler.class)
public class HandleXmlSerializable extends JavacAnnotationHandler<XmlSerializable> {

	@Override
	public void handle(final AnnotationValues<XmlSerializable> annotation, final JCAnnotation source, final JavacNode annotationNode) {
		new JavacXmlSerializableHandler().addToXmlMethod(JavacType.typeOf(annotationNode.up(), source));
	}
	
	private class JavacXmlSerializableHandler extends XmlSerializableHandler<JavacType, JavacField> {
		@Override
		protected Expression<?> getAnnotationValue(Expression<?> value) {
			return value;
		}

		@Override
		protected boolean isSuperObject(JavacType type) {
			if (type.get().getExtendsClause() == null) {
				return true;
			}
			
			return type.get().getExtendsClause().type.getKind().name().equals(Object.class.getName());
		}		
	}
}
