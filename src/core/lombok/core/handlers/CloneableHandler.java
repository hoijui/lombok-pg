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

import java.util.ArrayList;
import java.util.List;

import lombok.ast.pg.IField;
import lombok.ast.pg.IType;
import lombok.ast.pg.MethodDecl;
import lombok.ast.pg.TypeRef;

public class CloneableHandler<TYPE_TYPE extends IType<?, FIELD_TYPE, ?, ?, ?, ?>, FIELD_TYPE extends IField<?, ?, ?, ?>> {
	public void addCloneableMethod(final TYPE_TYPE type, final List<String> of, final List<String> excludes) {
		TypeRef typeRef = Type(type.name());
		
		MethodDecl method = MethodDecl(typeRef, "clone")
				.withAnnotation(Annotation(Type(Override.class)))
				.makePublic()
				.implementing();
		
		method.withStatement(
				LocalDecl(typeRef, "this$" + type.name()).makeFinal()
				.withInitialization(New(typeRef)));
		
		List<FIELD_TYPE> fields = new ArrayList<FIELD_TYPE>();
				
		for (FIELD_TYPE field : type.fields()) {
			if (field.isFinal()) continue;
			if (field.isStatic()) continue;
			if (field.filteredName().startsWith("$")) continue;
			if (of != null && !of.contains(field.name())) continue;
			if (excludes != null && excludes.contains(field.name())) continue;
			fields.add(field);
		}

		for (FIELD_TYPE field : fields) {		
			method.withStatement(
					Assign(Field(Name("this$" + type.name()), field.name()), Field(field.name())));
		}
		
		method.withStatement(Return(Name("this$" + type.name())));
		
		type.editor().implementing(Type(Cloneable.class));
		type.editor().injectMethod(method);
	}
}
