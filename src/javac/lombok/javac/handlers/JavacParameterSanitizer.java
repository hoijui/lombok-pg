/*
 * Copyright © 2011-2012 Philipp Eichhorn
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

import static lombok.core.util.Names.*;
import static lombok.javac.handlers.Javac.*;
import static lombok.javac.handlers.JavacHandlerUtil.*;

import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

import lombok.Sanitize;
import lombok.core.handlers.IParameterSanitizer;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacMethod;

public class JavacParameterSanitizer implements IParameterSanitizer<JavacMethod> {
	@Override
	public List<lombok.ast.pg.Statement<?>> sanitizeParameterOf(final JavacMethod method) {
		deleteImport(method.node(), Sanitize.class);
		for (SanitizerStrategy sanitizerStrategy : SanitizerStrategy.IN_ORDER) {
			deleteImport(method.node(), sanitizerStrategy.getType());
		}
		final List<lombok.ast.pg.Statement<?>> sanitizeStatements = new ArrayList<lombok.ast.pg.Statement<?>>();
		for (JCVariableDecl argument : method.get().params) {
			final String argumentName = argument.name.toString();
			final String newArgumentName = camelCase("sanitized", argumentName);
			for (SanitizerStrategy sanitizerStrategy : SanitizerStrategy.IN_ORDER) {
				final JCAnnotation ann = getAnnotation(sanitizerStrategy.getType(), argument.mods);
				if (ann == null) continue;
				final JavacNode annotationNode = method.node().getNodeFor(ann);
				final java.lang.annotation.Annotation annotation = createAnnotation(sanitizerStrategy.getType(), annotationNode).getInstance();
				sanitizeStatements.add(sanitizerStrategy.getStatementFor(argument.vartype, argumentName, newArgumentName, annotation));
				method.editor().replaceVariableName(argumentName, newArgumentName);
				argument.mods.flags |= Flags.FINAL;
				argument.mods.annotations = remove(argument.mods.annotations, ann);
				break;
			}
		}
		for (lombok.ast.pg.Statement<?> sanitizeStatement : sanitizeStatements) {
			sanitizeStatement.posHint(method.get());
		}
		return sanitizeStatements;
	}
}
