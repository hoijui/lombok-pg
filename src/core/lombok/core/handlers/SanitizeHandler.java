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
package lombok.core.handlers;

import static lombok.ast.pg.AST.*;
import static lombok.core.util.ErrorMessages.*;
import lombok.*;
import lombok.ast.pg.*;
import lombok.core.DiagnosticsReceiver;

@RequiredArgsConstructor
public class SanitizeHandler<METHOD_TYPE extends IMethod<?, ?, ?, ?>> {
	private final METHOD_TYPE method;
	private final DiagnosticsReceiver diagnosticsReceiver;

	public void handle(final IParameterSanitizer<METHOD_TYPE> sanitizer) {
		if (method == null) {
			diagnosticsReceiver.addError(canBeUsedOnMethodOnly(Sanitize.class));
			return;
		}

		if (method.isAbstract() || method.isEmpty()) {
			diagnosticsReceiver.addError(canBeUsedOnConcreteMethodOnly(Sanitize.class));
			return;
		}

		method.editor().replaceBody(Block().posHint(method.get()) //
				.withStatements(sanitizer.sanitizeParameterOf(method)) //
				.withStatements(method.statements()));
		method.editor().rebuild();
	}
}
