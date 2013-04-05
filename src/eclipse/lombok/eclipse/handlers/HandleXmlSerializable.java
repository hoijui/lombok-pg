package lombok.eclipse.handlers;

import lombok.Singleton;
import lombok.XmlSerializable;
import lombok.ast.pg.AST;
import lombok.ast.pg.Expression;
import lombok.core.AnnotationValues;
import lombok.core.handlers.XmlSerializableHandler;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.ast.EclipseASTMaker;
import lombok.eclipse.handlers.ast.EclipseField;
import lombok.eclipse.handlers.ast.EclipseType;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.StringLiteral;
import org.mangosdk.spi.ProviderFor;

/**
 * Handles the {@link Singleton} annotation for eclipse.
 */
@ProviderFor(EclipseAnnotationHandler.class)
public class HandleXmlSerializable extends EclipseAnnotationHandler<XmlSerializable> {
	private EclipseASTMaker builder;

	@Override
	public void handle(final AnnotationValues<XmlSerializable> annotation, final Annotation source, final EclipseNode annotationNode) {
		builder = new EclipseASTMaker(annotationNode.up(), source);
		
		new EclipseXmlSerializableHandler().addToXmlMethod(EclipseType.typeOf(annotationNode.up(), source));
	}
	
	private class EclipseXmlSerializableHandler extends XmlSerializableHandler<EclipseType, EclipseField> {
		@Override
		protected Expression<?> getAnnotationValue(Expression<?> value) {
			org.eclipse.jdt.internal.compiler.ast.Expression exp = builder.build(value);
			
			if (exp instanceof StringLiteral) {
				return AST.String(new String(((StringLiteral) exp).source()));
			} else if (exp instanceof SingleNameReference || exp instanceof QualifiedNameReference) {
				String name = "";
				if (exp instanceof SingleNameReference) {
					name = new String(((SingleNameReference) exp).token);
				} else {
					char[][] c = ((QualifiedNameReference) exp).tokens;
					for (int i = 0; i < c.length; i++) {
						if (i > 0) {
							name += ".";
						}
						
						name += new String(c[i]);						
					}
				}
				
				return AST.Name(name);
			}
			
			return value;
		}		
	}
}
