/*
 * Copyright © 2010-2012 Philipp Eichhorn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lombok.javac.handlers;

import static lombok.core.util.ErrorMessages.canBeUsedOnClassOnly;
import static lombok.core.util.ErrorMessages.canBeUsedOnConcreteMethodOnly;
import static lombok.core.util.ErrorMessages.canBeUsedOnMethodOnly;
import static lombok.core.util.Names.decapitalize;
import static lombok.javac.handlers.JavacHandlerUtil.createAnnotation;
import static lombok.javac.handlers.JavacHandlerUtil.deleteAnnotationIfNeccessary;
import static lombok.javac.handlers.JavacHandlerUtil.inNetbeansEditor;
import static lombok.javac.handlers.JavacHandlerUtil.methodExists;
import lombok.Builder;
import lombok.BuilderExtension;
import lombok.core.AnnotationValues;
import lombok.core.handlers.BuilderAndExtensionHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacField;
import lombok.javac.handlers.ast.JavacMethod;
import lombok.javac.handlers.ast.JavacType;

import org.mangosdk.spi.ProviderFor;

import com.sun.tools.javac.code.Type.ArrayType;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;

public class HandleBuilderAndExtension {

	/**
	 * Handles the {@code lombok.Builder} annotation for javac.
	 */
	@ProviderFor(JavacAnnotationHandler.class)
	public static class HandleBuilder extends JavacAnnotationHandler<Builder> {

		@Override
		public void handle(final AnnotationValues<Builder> annotation, final JCAnnotation source, final JavacNode annotationNode) {
			deleteAnnotationIfNeccessary(annotationNode, Builder.class);
			final JavacType type = JavacType.typeOf(annotationNode, source);

			if (type.isInterface() || type.isEnum() || type.isAnnotation()) {
				annotationNode.addError(canBeUsedOnClassOnly(Builder.class));
				return;
			}

			switch (methodExists(decapitalize(type.name()), type.node(), false, 0)) {
			case EXISTS_BY_LOMBOK:
				return;
			case EXISTS_BY_USER:
				final String message = "Not generating 'public static %s %s()' A method with that name already exists";
				annotationNode.addWarning(String.format(message, BuilderAndExtensionHandler.BUILDER, decapitalize(type.name())));
				return;
			default:
			case NOT_EXISTS:
				// continue with creating the builder
			}

			new BuilderAndExtensionHandler<JavacType, JavacMethod, JavacField>() {
				@Override
				protected boolean isArray(JavacField field) {
					return (field.get().vartype.type instanceof ArrayType);
				}
			}.handleBuilder(type, annotation.getInstance());
		}
	}

	/**
	 * Handles the {@code lombok.Builder.Extension} annotation for javac.
	 */
	@ProviderFor(JavacAnnotationHandler.class)
	public static class HandleBuilderExtension extends JavacAnnotationHandler<BuilderExtension> {

		@Override
		public void handle(final AnnotationValues<BuilderExtension> annotation, final JCAnnotation source, final JavacNode annotationNode) {
			if (inNetbeansEditor(annotationNode)) return;
			deleteAnnotationIfNeccessary(annotationNode, BuilderExtension.class);

			final JavacMethod method = JavacMethod.methodOf(annotationNode, source);

			if (method == null) {
				annotationNode.addError(canBeUsedOnMethodOnly(BuilderExtension.class));
				return;
			}
			if (method.isAbstract() || method.isEmpty()) {
				annotationNode.addError(canBeUsedOnConcreteMethodOnly(BuilderExtension.class));
				return;
			}

			JavacType type = JavacType.typeOf(annotationNode, source);
			JavacNode builderNode = type.getAnnotation(Builder.class);

			if (builderNode == null) {
				annotationNode.addError("@Builder.Extension is only allowed in types annotated with @Builder");
				return;
			}
			AnnotationValues<Builder> builderAnnotation = createAnnotation(Builder.class, builderNode);

			if (!type.hasMethod(decapitalize(type.name()))) {
				new HandleBuilder().handle(builderAnnotation, (JCAnnotation) builderNode.get(), builderNode);
			}

			new BuilderAndExtensionHandler<JavacType, JavacMethod, JavacField>() {
				@Override
				protected boolean isArray(JavacField field) {
					return (field.get().vartype.type instanceof ArrayType);
				}				
			}.handleExtension(type, method, new JavacParameterValidator(), new JavacParameterSanitizer(), builderAnnotation.getInstance(), annotation.getInstance());
		}
	}
}
