package lombok.javac.handlers;

import static lombok.javac.handlers.Javac.deleteImport;
import static lombok.javac.handlers.JavacHandlerUtil.deleteAnnotationIfNeccessary;
import lombok.AccessLevel;
import lombok.Attribute;
import lombok.MethodRef;
import lombok.core.AnnotationValues;
import lombok.core.handlers.AttributeHandler;
import lombok.core.handlers.MethodRefHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.JavacHandlerUtil.MemberExistsResult;
import lombok.javac.handlers.ast.JavacField;
import lombok.javac.handlers.ast.JavacMethod;
import lombok.javac.handlers.ast.JavacType;

import org.mangosdk.spi.ProviderFor;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

@ProviderFor(JavacAnnotationHandler.class)
public class HandleMethodRef extends JavacAnnotationHandler<MethodRef> {
	
	@Override
	public void handle(AnnotationValues<MethodRef> annotation,
			JCAnnotation ast, JavacNode annotationNode) {
		final JavacType type = JavacType.typeOf(annotationNode, ast);
		final JavacMethod method = JavacMethod.methodOf(annotationNode, ast);
		
		boolean getThisExists = JavacHandlerUtil.methodExists("__getThis", annotationNode, false, 0) != MemberExistsResult.NOT_EXISTS;
		
		new MethodRefHandler<JavacType, JavacMethod, JavacField>(type, method, annotationNode, getThisExists).handle(annotation);
		deleteAnnotationIfNeccessary(annotationNode, MethodRef.class);
		deleteImport(annotationNode, AccessLevel.class);
	}

}
