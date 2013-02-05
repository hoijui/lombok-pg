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
package lombok.javac.handlers.ast;

import static com.sun.tools.javac.code.Flags.ENUM;
import static com.sun.tools.javac.code.Flags.PRIVATE;
import static com.sun.tools.javac.code.Flags.PROTECTED;
import static com.sun.tools.javac.code.Flags.PUBLIC;
import static com.sun.tools.javac.code.Flags.STATIC;

import java.util.List;

import lombok.ast.TypeRef;
import lombok.javac.JavacNode;
import lombok.javac.handlers.Javac;
import lombok.javac.handlers.JavacHandlerUtil;

import com.sun.tools.javac.code.Symbol.ClassSymbol;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.ListBuffer;

public final class JavacTypeEditor implements lombok.ast.ITypeEditor<JavacMethod, JCTree, JCClassDecl, JCMethodDecl> {
	private final JavacType type;
	private final JavacASTMaker builder;

	JavacTypeEditor(final JavacType type, final JCTree source) {
		this.type = type;
		builder = new JavacASTMaker(type.node(), source);
	}

	JCClassDecl get() {
		return type.get();
	}

	JavacNode node() {
		return type.node();
	}

	@Override
	public <T extends JCTree> T build(final lombok.ast.Node<?> node) {
		return builder.<T> build(node);
	}

	@Override
	public <T extends JCTree> T build(final lombok.ast.Node<?> node, final Class<T> extectedType) {
		return builder.build(node, extectedType);
	}

	@Override
	public <T extends JCTree> List<T> build(final List<? extends lombok.ast.Node<?>> nodes) {
		return builder.build(nodes);
	}

	@Override
	public <T extends JCTree> List<T> build(final List<? extends lombok.ast.Node<?>> nodes, final Class<T> extectedType) {
		return builder.build(nodes, extectedType);
	}

	@Override
	public void injectInitializer(final lombok.ast.Initializer initializer) {
		final JCBlock initializerBlock = builder.build(initializer);
		Javac.injectInitializer(node(), initializerBlock);
	}

	@Override
	public void injectField(final lombok.ast.FieldDecl fieldDecl) {
		final JCVariableDecl field = builder.build(fieldDecl);
		JavacHandlerUtil.injectField(node(), field);
	}

	@Override
	public void injectField(final lombok.ast.EnumConstant enumConstant) {
		final JCVariableDecl field = builder.build(enumConstant);
		JavacHandlerUtil.injectField(node(), field);
	}

	@Override
	public JCMethodDecl injectMethod(final lombok.ast.MethodDecl methodDecl) {
		return injectMethodImpl(methodDecl);
	}

	@Override
	public JCMethodDecl injectConstructor(final lombok.ast.ConstructorDecl constructorDecl) {
		return injectMethodImpl(constructorDecl);
	}

	private JCMethodDecl injectMethodImpl(final lombok.ast.AbstractMethodDecl<?> methodDecl) {
		final JCMethodDecl method = builder.build(methodDecl, JCMethodDecl.class);
		JavacHandlerUtil.injectMethod(node(), method);
		if (methodDecl instanceof lombok.ast.WrappedMethodDecl) {
			lombok.ast.WrappedMethodDecl node = (lombok.ast.WrappedMethodDecl) methodDecl;
			MethodSymbol methodSymbol = (MethodSymbol) node.getWrappedObject();
			JCClassDecl tree = get();
			ClassSymbol c = tree.sym;
			c.members_field.enter(methodSymbol, c.members_field, methodSymbol.enclClass().members_field);
			method.sym = methodSymbol;
		}
		return method;
	}

	@Override
	public void injectType(final lombok.ast.ClassDecl typeDecl) {
		final JCClassDecl type = builder.build(typeDecl);
		Javac.injectType(node(), type);
	}

	@Override
	public void removeMethod(final JavacMethod method) {
		JCClassDecl type = get();
		ListBuffer<JCTree> defs = ListBuffer.lb();
		for (JCTree def : type.defs) {
			if (!def.equals(method.get())) {
				defs.append(def);
			}
		}
		type.defs = defs.toList();
		node().removeChild(method.node());
	}

	@Override
	public void makeEnum() {
		get().mods.flags |= ENUM;
	}

	@Override
	public void makePrivate() {
		makePackagePrivate();
		get().mods.flags |= PRIVATE;
	}

	@Override
	public void makePackagePrivate() {
		get().mods.flags &= ~(PRIVATE | PROTECTED | PUBLIC);
	}

	@Override
	public void makeProtected() {
		makePackagePrivate();
		get().mods.flags |= PROTECTED;
	}

	@Override
	public void makePublic() {
		makePackagePrivate();
		get().mods.flags |= PUBLIC;
	}

	@Override
	public void makeStatic() {
		get().mods.flags |= STATIC;
	}

	@Override
	public void rebuild() {
		node().rebuild();
	}

	@Override
	public String toString() {
		return get().toString();
	}

	@Override
	public void implementing(TypeRef type) {
		JCClassDecl clazz = get();
		JCExpression interfaceType = builder.build(type);
		
		ListBuffer<JCExpression> interfaces = ListBuffer.lb();
		boolean containsInterface = false;
		if (clazz.implementing != null) {
			for (JCExpression exp : clazz.implementing) {
				if (exp.toString().equals(type.getTypeName())) {
					containsInterface = true;
					break;
				}
				
				interfaces.append(exp);
			}
		}
		
		if (!containsInterface) {
			interfaces.append(interfaceType);
			clazz.implementing = interfaces.toList();
		}
	}
}
