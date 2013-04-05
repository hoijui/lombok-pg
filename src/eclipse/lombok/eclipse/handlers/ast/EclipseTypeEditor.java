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
package lombok.eclipse.handlers.ast;

import static lombok.core.util.Arrays.resize;
import static org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccEnum;
import static org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccPrivate;
import static org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccProtected;
import static org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccPublic;
import static org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccStatic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.ast.pg.TypeRef;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.Eclipse;
import lombok.eclipse.handlers.EclipseHandlerUtil;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;

public final class EclipseTypeEditor implements lombok.ast.pg.ITypeEditor<EclipseMethod, ASTNode, TypeDeclaration, AbstractMethodDeclaration> {
	private final EclipseType type;
	private final EclipseASTMaker builder;

	EclipseTypeEditor(final EclipseType type, final ASTNode source) {
		this.type = type;
		builder = new EclipseASTMaker(type.node(), source);
	}

	public TypeDeclaration get() {
		return type.get();
	}

	public EclipseNode node() {
		return type.node();
	}

	@Override
	public <T extends ASTNode> T build(final lombok.ast.pg.Node<?> node) {
		return builder.<T> build(node);
	}

	@Override
	public <T extends ASTNode> T build(final lombok.ast.pg.Node<?> node, final Class<T> extectedType) {
		return builder.build(node, extectedType);
	}

	@Override
	public <T extends ASTNode> List<T> build(final List<? extends lombok.ast.pg.Node<?>> nodes) {
		return builder.build(nodes);
	}

	@Override
	public <T extends ASTNode> List<T> build(final List<? extends lombok.ast.pg.Node<?>> nodes, final Class<T> extectedType) {
		return builder.build(nodes, extectedType);
	}

	@Override
	public void injectInitializer(final lombok.ast.pg.Initializer initializer) {
		final Initializer initializerBlock = builder.build(initializer);
		Eclipse.injectInitializer(node(), initializerBlock);
	}

	@Override
	public void injectField(final lombok.ast.pg.FieldDecl fieldDecl) {
		final FieldDeclaration field = builder.build(fieldDecl);
		EclipseHandlerUtil.injectField(node(), field);
	}

	@Override
	public void injectField(final lombok.ast.pg.EnumConstant enumConstant) {
		final FieldDeclaration field = builder.build(enumConstant);
		EclipseHandlerUtil.injectField(node(), field);
	}

	@Override
	public AbstractMethodDeclaration injectMethod(final lombok.ast.pg.MethodDecl methodDecl) {
		return injectMethodImpl(methodDecl);
	}

	@Override
	public AbstractMethodDeclaration injectConstructor(final lombok.ast.pg.ConstructorDecl constructorDecl) {
		return injectMethodImpl(constructorDecl);
	}

	private AbstractMethodDeclaration injectMethodImpl(final lombok.ast.pg.AbstractMethodDecl<?> methodDecl) {
		final AbstractMethodDeclaration method = builder.build(methodDecl, MethodDeclaration.class);
		EclipseHandlerUtil.injectMethod(node(), method);

		TypeDeclaration type = get();
		if (type.scope != null && method.scope == null) {
			boolean aboutToBeResolved = false;
			for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
				if ("org.eclipse.jdt.internal.compiler.lookup.ClassScope".equals(elem.getClassName()) && "buildFieldsAndMethods".equals(elem.getMethodName())) {
					aboutToBeResolved = true;
					break;
				}
			}
			if (!aboutToBeResolved) {
				MethodScope scope = new MethodScope(type.scope, method, methodDecl.getModifiers().contains(lombok.ast.pg.Modifier.STATIC));
				MethodBinding methodBinding = null;
				try {
					methodBinding = (MethodBinding) Reflection.methodScopeCreateMethodMethod.invoke(scope, method);
				} catch (final Exception e) {
					// See 'Reflection' class for why we ignore this exception.
				}
				if (methodBinding != null) {
					SourceTypeBinding sourceType = type.scope.referenceContext.binding;
					MethodBinding[] methods = sourceType.methods();
					methods = resize(methods, methods.length + 1);
					methods[methods.length - 1] = methodBinding;
					sourceType.setMethods(methods);
					sourceType.resolveTypesFor(methodBinding);
				}
			}
		}
		return method;
	}

	@Override
	public void injectType(final lombok.ast.pg.ClassDecl typeDecl) {
		final TypeDeclaration type = builder.build(typeDecl);
		Eclipse.injectType(node(), type);
	}

	@Override
	public void removeMethod(final EclipseMethod method) {
		TypeDeclaration type = get();
		List<AbstractMethodDeclaration> methods = new ArrayList<AbstractMethodDeclaration>();
		for (AbstractMethodDeclaration decl : type.methods) {
			if (!decl.equals(method.get())) {
				methods.add(decl);
			}
		}
		type.methods = methods.toArray(new AbstractMethodDeclaration[0]);
		node().removeChild(method.node());
	}

	@Override
	public void makeEnum() {
		get().modifiers |= AccEnum;
	}

	@Override
	public void makePrivate() {
		makePackagePrivate();
		get().modifiers |= AccPrivate;
	}

	@Override
	public void makePackagePrivate() {
		get().modifiers &= ~(AccPrivate | AccProtected | AccPublic);
	}

	@Override
	public void makeProtected() {
		makePackagePrivate();
		get().modifiers |= AccProtected;
	}

	@Override
	public void makePublic() {
		makePackagePrivate();
		get().modifiers |= AccPublic;
	}

	@Override
	public void makeStatic() {
		get().modifiers |= AccStatic;
	}

	@Override
	public void rebuild() {
		node().rebuild();
	}

	@Override
	public String toString() {
		return get().toString();
	}

	private static final class Reflection {
		public static final Method methodScopeCreateMethodMethod;
		static {
			Method m = null;
			try {
				m = MethodScope.class.getDeclaredMethod("createMethod", AbstractMethodDeclaration.class);
				m.setAccessible(true);
			} catch (final Exception e) {
				// well can't do anything about it then
			}
			methodScopeCreateMethodMethod = m;
		}
	}

	@Override
	public void implementing(TypeRef type) {
		TypeDeclaration typeDecl = get();		
		TypeReference typeRef = builder.build(type);
		
		if (typeDecl.superInterfaces == null) {
			typeDecl.superInterfaces = new TypeReference[1];
			typeDecl.superInterfaces[0] = typeRef;
		} else {
			for (TypeReference ref : typeDecl.superInterfaces) {
				if (EclipseHandlerUtil.typeEquals(ref.getTypeName(), type.getTypeName())) {
					return;
				}
			}
			

			TypeReference[] interfaces = new TypeReference[typeDecl.superInterfaces.length + 1];
			System.arraycopy(typeDecl.superInterfaces, 0, interfaces, 0, typeDecl.superInterfaces.length);
			typeDecl.superInterfaces = interfaces;
			typeDecl.superInterfaces[typeDecl.superInterfaces.length - 1] = typeRef;
		}
	}
}
