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

import static com.sun.tools.javac.code.TypeTags.*;
import static com.sun.tools.javac.code.Flags.*;
import static lombok.ast.pg.AST.*;
import static lombok.javac.Javac.*;
import static lombok.javac.handlers.Javac.methodNodeOf;
import static lombok.javac.handlers.Javac.typeNodeOf;
import static lombok.javac.handlers.JavacHandlerUtil.setGeneratedBy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.core.util.As;
import lombok.core.util.Cast;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;
import lombok.javac.JavacTreeMaker.TreeTag;
import lombok.javac.JavacTreeMaker.TypeTag;

import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCArrayAccess;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCBinary;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCBreak;
import com.sun.tools.javac.tree.JCTree.JCCase;
import com.sun.tools.javac.tree.JCTree.JCCatch;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCContinue;
import com.sun.tools.javac.tree.JCTree.JCDoWhileLoop;
import com.sun.tools.javac.tree.JCTree.JCEnhancedForLoop;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCIf;
import com.sun.tools.javac.tree.JCTree.JCInstanceOf;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCNewArray;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import com.sun.tools.javac.tree.JCTree.JCReturn;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCSwitch;
import com.sun.tools.javac.tree.JCTree.JCSynchronized;
import com.sun.tools.javac.tree.JCTree.JCThrow;
import com.sun.tools.javac.tree.JCTree.JCTry;
import com.sun.tools.javac.tree.JCTree.JCTypeCast;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCUnary;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.JCTree.JCWhileLoop;
import com.sun.tools.javac.tree.JCTree.JCWildcard;
import com.sun.tools.javac.tree.JCTree.TypeBoundKind;
import com.sun.tools.javac.tree.TreeCopier;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;

@RequiredArgsConstructor
public final class JavacASTMaker implements lombok.ast.pg.ASTVisitor<JCTree, Void> {
	private static final Map<String, TreeTag> UNARY_OPERATORS = new HashMap<String, TreeTag>();
	static {
		UNARY_OPERATORS.put("+", CTC_POS); 
		UNARY_OPERATORS.put("-", CTC_NEG);
		UNARY_OPERATORS.put("!", CTC_NOT);
		UNARY_OPERATORS.put("~", CTC_COMPL);
		UNARY_OPERATORS.put("++X", CTC_PREINC);
		UNARY_OPERATORS.put("--X", CTC_PREDEC);
		UNARY_OPERATORS.put("X++", CTC_POSTINC);
		UNARY_OPERATORS.put("X--", CTC_POSTDEC);
	}
	private static final Map<String, TreeTag> BINARY_OPERATORS = new HashMap<String, TreeTag>();
	static {
		BINARY_OPERATORS.put("||", CTC_OR);
		BINARY_OPERATORS.put("&&", CTC_AND);
		BINARY_OPERATORS.put("==", CTC_EQ);
		BINARY_OPERATORS.put("!=", CTC_NE);
		BINARY_OPERATORS.put("<", CTC_LT);
		BINARY_OPERATORS.put(">",  CTC_GT);
		BINARY_OPERATORS.put("<=",  CTC_LE);
		BINARY_OPERATORS.put(">=",  CTC_GE);
		BINARY_OPERATORS.put("|",  CTC_BITOR);
		BINARY_OPERATORS.put("^",  CTC_BITXOR);
		BINARY_OPERATORS.put("&", CTC_BITAND);
		BINARY_OPERATORS.put("<<", CTC_SIGNED_SHIFT_LEFT);
		BINARY_OPERATORS.put(">>", CTC_SIGNED_SHIFT_RIGHT);
		BINARY_OPERATORS.put(">>>", CTC_UNSIGNED_SHIFT_RIGHT);
		BINARY_OPERATORS.put("+", CTC_PLUS);
		BINARY_OPERATORS.put("-", CTC_MINUS);
		BINARY_OPERATORS.put("*", CTC_MUL);
		BINARY_OPERATORS.put("/", CTC_DIV);
		BINARY_OPERATORS.put("%", CTC_MOD);
	}
	private static final Map<String, TypeTag> TYPES = new HashMap<String, TypeTag>();
	static {
		TYPES.put("none", CTC_NONE);
		TYPES.put("null", CTC_BOT);
		TYPES.put("void", CTC_VOID);
		TYPES.put("int", CTC_INT);
		TYPES.put("long", CTC_LONG);
		TYPES.put("short", CTC_SHORT);
		TYPES.put("boolean", CTC_BOOLEAN);
		TYPES.put("byte", CTC_BYTE);
		TYPES.put("char", CTC_CHAR);
		TYPES.put("float", CTC_FLOAT);
		TYPES.put("double", CTC_DOUBLE);
	}

	private final JavacNode sourceNode;
	private final JCTree source;

	public <T extends JCTree> T build(final lombok.ast.pg.Node<?> node) {
		return this.<T> build(node, null);
	}

	public <T extends JCTree> T build(final lombok.ast.pg.Node<?> node, final Class<T> extectedType) {
		if (node == null) return null;
		JCTree tree = node.accept(this, null);
		if ((JCStatement.class == extectedType) && (tree instanceof JCExpression)) {
			tree = M(node).Exec((JCExpression) tree);
		}
		return Cast.<T> uncheckedCast(tree);
	}

	public <T extends JCTree> List<T> build(final java.util.List<? extends lombok.ast.pg.Node<?>> nodes) {
		return this.<T> build(nodes, null);
	}

	public <T extends JCTree> List<T> build(final java.util.List<? extends lombok.ast.pg.Node<?>> nodes, final Class<T> extectedType) {
		if (nodes == null) return null;
		ListBuffer<T> list = ListBuffer.lb();
		for (lombok.ast.pg.Node<?> node : nodes) {
			list.append(build(node, extectedType));
		}
		return list.toList();
	}

	private JavacTreeMaker M(final lombok.ast.pg.Node<?> node) {
		final int pos;
		if ((node.upTo(lombok.ast.pg.EnumConstant.class) != null) || (node.upTo(lombok.ast.pg.FieldDecl.class) != null)) {
			pos = -1;
		} else {
			final JCTree posHint = node.posHint();
			pos = posHint == null ? source.pos : posHint.pos;
		}
		return sourceNode.getTreeMaker().at(pos);
	}

	private Name name(final String name) {
		return sourceNode.toName(name);
	}

	private JCExpression chainDots(final lombok.ast.pg.Node<?> node, final String name) {
		String[] elements = name.split("\\.");
		JCExpression e = M(node).Ident(name(elements[0]));
		for (int i = 1, iend = elements.length; i < iend; i++) {
			e = M(node).Select(e, name(elements[i]));
		}
		return e;
	}

	private JCExpression fixLeadingDot(final lombok.ast.pg.Node<?> node, final JCExpression expr) {
		if (expr instanceof JCFieldAccess) {
			JCFieldAccess fieldAccess = (JCFieldAccess) expr;
			JCExpression selExpr = fieldAccess.selected;
			if (selExpr instanceof JCIdent) {
				if ("".equals(selExpr.toString())) {
					return M(node).Ident(fieldAccess.name);
				}
			} else if (selExpr instanceof JCFieldAccess) {
				fieldAccess.selected = fixLeadingDot(node, selExpr);
			}
		}
		return expr;
	}

	private long flagsFor(final Set<lombok.ast.pg.Modifier> modifiers) {
		long flags = 0;
		flags |= modifiers.contains(lombok.ast.pg.Modifier.FINAL) ? FINAL : 0;
		flags |= modifiers.contains(lombok.ast.pg.Modifier.PRIVATE) ? PRIVATE : 0;
		flags |= modifiers.contains(lombok.ast.pg.Modifier.PROTECTED) ? PROTECTED : 0;
		flags |= modifiers.contains(lombok.ast.pg.Modifier.PUBLIC) ? PUBLIC : 0;
		flags |= modifiers.contains(lombok.ast.pg.Modifier.STATIC) ? STATIC : 0;
		flags |= modifiers.contains(lombok.ast.pg.Modifier.TRANSIENT) ? TRANSIENT : 0;
		flags |= modifiers.contains(lombok.ast.pg.Modifier.VOLATILE) ? VOLATILE : 0;
		return flags;
	}

	private JCTree withJavaDoc(final JCTree target, final lombok.ast.pg.JavaDoc javaDoc) {
		final JCLiteral javadocExp = build(javaDoc, JCLiteral.class);
		if (javadocExp != null) {
			final JCCompilationUnit compilationUnit = (JCCompilationUnit) sourceNode.top().get();
			compilationUnit.docComments.put(target, As.string(javadocExp.getValue()));
		}
		return target;
	}

	@Override
	public JCTree visitAnnotation(final lombok.ast.pg.Annotation node, final Void p) {
		final ListBuffer<JCExpression> args = ListBuffer.lb();
		for (Entry<String, lombok.ast.pg.Expression<?>> entry : node.getValues().entrySet()) {
			args.append(build(Assign(Name(entry.getKey()), entry.getValue()), JCExpression.class));
		}		
		// TODO: Where to get context from?
		final JCAnnotation annotation = setGeneratedBy(M(node).Annotation(build(node.getType()), args.toList()), source, sourceNode.getContext());
		return annotation;
	}

	@Override
	public JCTree visitArgument(final lombok.ast.pg.Argument node, final Void p) {
		final JCModifiers mods = setGeneratedBy(M(node).Modifiers(flagsFor(node.getModifiers()), build(node.getAnnotations(), JCAnnotation.class)), source, sourceNode.getContext());
		final JCVariableDecl argument = setGeneratedBy(M(node).VarDef(mods, name(node.getName()), build(node.getType(), JCExpression.class), null), source, sourceNode.getContext());
		return argument;
	}

	@Override
	public JCTree visitArrayRef(final lombok.ast.pg.ArrayRef node, final Void p) {
		final JCArrayAccess arrayAccess = setGeneratedBy(M(node).Indexed(build(node.getIndexed(), JCExpression.class), build(node.getIndex(), JCExpression.class)), source, sourceNode.getContext());
		return arrayAccess;
	}

	@Override
	public JCTree visitAssignment(final lombok.ast.pg.Assignment node, final Void p) {
		final JCAssign assignment = setGeneratedBy(M(node).Assign(build(node.getLeft(), JCExpression.class), build(node.getRight(), JCExpression.class)), source, sourceNode.getContext());
		return assignment;
	}

	@Override
	public JCTree visitBinary(final lombok.ast.pg.Binary node, final Void p) {
		final String operator = node.getOperator();
		final TreeTag opCode;
		if (BINARY_OPERATORS.containsKey(operator)) {
			opCode = BINARY_OPERATORS.get(operator);
		} else {
			throw new IllegalStateException(String.format("Unknown binary operator '%s'", operator));
		}
		JCBinary binary = setGeneratedBy(M(node).Binary(opCode, build(node.getLeft(), JCExpression.class), build(node.getRight(), JCExpression.class)), source, sourceNode.getContext());
		return binary;
	}

	@Override
	public JCTree visitBlock(final lombok.ast.pg.Block node, final Void p) {
		final JCBlock block = setGeneratedBy(M(node).Block(0, build(node.getStatements(), JCStatement.class)), source, sourceNode.getContext());
		return block;
	}

	@Override
	public JCTree visitBooleanLiteral(final lombok.ast.pg.BooleanLiteral node, final Void p) {
		final JCLiteral literal = setGeneratedBy(M(node).Literal(TYPES.get("boolean"), node.isTrue() ? 1 : 0), source, sourceNode.getContext());
		return literal;
	}

	@Override
	public JCTree visitBreak(final lombok.ast.pg.Break node, final Void p) {
		final JCBreak breakStatement = setGeneratedBy(M(node).Break(node.getLabel() == null ? null : name(node.getLabel())), source, sourceNode.getContext());
		return breakStatement;
	}

	@Override
	public JCTree visitCall(final lombok.ast.pg.Call node, final Void p) {
		final JCExpression fn;
		if (node.getReceiver() == null) {
			fn = M(node).Ident(name(node.getName()));
		} else {
			fn = M(node).Select(build(node.getReceiver(), JCExpression.class), name(node.getName()));
		}
		final JCMethodInvocation methodInvocation = setGeneratedBy(M(node).Apply(build(node.getTypeArgs(), JCExpression.class), fn, build(node.getArgs(), JCExpression.class)),
				source, sourceNode.getContext());
		return methodInvocation;
	}

	@Override
	public JCTree visitCase(final lombok.ast.pg.Case node, final Void p) {
		final JCCase caze = setGeneratedBy(M(node).Case(build(node.getPattern(), JCExpression.class), build(node.getStatements(), JCStatement.class)), source, sourceNode.getContext());
		return caze;
	}

	@Override
	public JCTree visitCast(final lombok.ast.pg.Cast node, final Void p) {
		final JCTypeCast cast = setGeneratedBy(M(node).TypeCast(build(node.getType()), build(node.getExpression(), JCExpression.class)), source, sourceNode.getContext());
		return cast;
	}

	@Override
	public JCTree visitCharLiteral(final lombok.ast.pg.CharLiteral node, final Void p) {
		final JCLiteral literal = setGeneratedBy(M(node).Literal(node.getCharacter().charAt(0)), source, sourceNode.getContext());
		return literal;
	}

	@Override
	public JCTree visitClassDecl(final lombok.ast.pg.ClassDecl node, final Void p) {
		final JCModifiers mods = setGeneratedBy(M(node).Modifiers(flagsFor(node.getModifiers()), build(node.getAnnotations(), JCAnnotation.class)), source, sourceNode.getContext());
		if (node.isInterface()) mods.flags |= Flags.INTERFACE;
		final ListBuffer<JCTree> defs = ListBuffer.lb();
		defs.appendList(build(node.getFields()));
		defs.appendList(build(node.getMethods()));
		defs.appendList(build(node.getMemberTypes()));
		final List<JCTypeParameter> typarams = build(node.getTypeParameters());
		final JCExpression extending = build(node.getSuperclass());
		final List<JCExpression> implementing = build(node.getSuperInterfaces());
		final JCClassDecl classDecl = setGeneratedBy(createClassDef(node, mods, name(node.getName()), typarams, extending, implementing, defs.toList()), source, sourceNode.getContext());
		return classDecl;
	}

	// to support both:
	// javac 1.6 - M(node).ClassDef(JCModifiers, Name, List<JCTypeParameter>, JCTree, List<JCExpression>, List<JCTree>)
	// and javac 1.7 - M(node).ClassDef(JCModifiers, Name, List<JCTypeParameter>, JCExpression, List<JCExpression>,
	// List<JCTree>)
	private JCClassDecl createClassDef(final lombok.ast.pg.Node<?> node, final JCModifiers mods, final Name name, final List<JCTypeParameter> typarams, final JCExpression extending,
			final List<JCExpression> implementing, final List<JCTree> defs) {
		try {
			Method classDefMethod = null;
			for (Method method : TreeMaker.class.getMethods()) {
				if ("ClassDef".equals(method.getName())) {
					classDefMethod = method;
					break;
				}
			}
			if (classDefMethod == null) throw new IllegalStateException();
			return (JCClassDecl) classDefMethod.invoke(M(node), mods, name, typarams, extending, implementing, defs);
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public JCTree visitConstructorDecl(final lombok.ast.pg.ConstructorDecl node, final Void p) {
		final JCModifiers mods = setGeneratedBy(M(node).Modifiers(flagsFor(node.getModifiers()), build(node.getAnnotations(), JCAnnotation.class)), source, sourceNode.getContext());
		List<JCStatement> statements = build(node.getStatements(), JCStatement.class);
		if (node.implicitSuper()) {
			statements = statements.prepend(build(Call("super"), JCStatement.class));
		}
		final List<JCTypeParameter> typarams = build(node.getTypeParameters());
		final List<JCVariableDecl> params = build(node.getArguments());
		final List<JCExpression> thrown = build(node.getThrownExceptions());
		final JCBlock body = setGeneratedBy(M(node).Block(0, statements), source, sourceNode.getContext());
		final JCMethodDecl constructor = setGeneratedBy(M(node).MethodDef(mods, name("<init>"), null, typarams, params, thrown, body, null), source, sourceNode.getContext());
		return withJavaDoc(constructor, node.getJavaDoc());
	}

	@Override
	public JCTree visitContinue(final lombok.ast.pg.Continue node, final Void p) {
		final JCContinue continueStatement = setGeneratedBy(M(node).Continue(node.getLabel() == null ? null : name(node.getLabel())), source, sourceNode.getContext());
		return continueStatement;
	}

	@Override
	public JCTree visitDefaultValue(final lombok.ast.pg.DefaultValue node, final Void p) {
		lombok.ast.pg.Expression<?> defaultValue = Null();
		final JCExpression type = build(node.getType());
		if (type instanceof JCPrimitiveTypeTree) {
			JCPrimitiveTypeTree primitiveType = (JCPrimitiveTypeTree) type;
			if (primitiveType.typetag == VOID) {
				defaultValue = null;
			} else {
				defaultValue = Expr(M(node).getUnderlyingTreeMaker().Literal(primitiveType.typetag, 0));
			}
		}
		return build(defaultValue);
	}

	@Override
	public JCTree visitDoWhile(final lombok.ast.pg.DoWhile node, final Void p) {
		final JCDoWhileLoop doStatement = setGeneratedBy(M(node).DoLoop(build(node.getAction(), JCStatement.class), build(node.getCondition(), JCExpression.class)), source, sourceNode.getContext());
		return doStatement;
	}

	@Override
	public JCTree visitEnumConstant(final lombok.ast.pg.EnumConstant node, final Void p) {
		final JCModifiers mods = setGeneratedBy(M(node).Modifiers(ENUM | STATIC | FINAL | PUBLIC, build(node.getAnnotations(), JCAnnotation.class)), source, sourceNode.getContext());
		lombok.ast.pg.ClassDecl enumClassDecl = node.upTo(lombok.ast.pg.ClassDecl.class);
		final JCExpression varType;
		if (enumClassDecl == null) {
			varType = build(Type(typeNodeOf(sourceNode).getName()));
		} else {
			varType = chainDots(node, enumClassDecl.getName());
		}
		final List<JCExpression> nilExp = List.nil();
		final List<JCExpression> args = build(node.getArgs());
		final JCNewClass init = setGeneratedBy(M(node).NewClass(null, nilExp, varType, args, null), source, sourceNode.getContext());
		final JCVariableDecl enumContant = setGeneratedBy(M(node).VarDef(mods, name(node.getName()), varType, init), source, sourceNode.getContext());
		return withJavaDoc(enumContant, node.getJavaDoc());
	}

	@Override
	public JCTree visitFieldDecl(final lombok.ast.pg.FieldDecl node, final Void p) {
		final JCModifiers mods = setGeneratedBy(M(node).Modifiers(flagsFor(node.getModifiers()), build(node.getAnnotations(), JCAnnotation.class)), source, sourceNode.getContext());
		final JCExpression vartype = build(node.getType());
		final JCExpression init = build(node.getInitialization());
		final JCVariableDecl field = setGeneratedBy(M(node).VarDef(mods, name(node.getName()), vartype, init), source, sourceNode.getContext());
		return withJavaDoc(field, node.getJavaDoc());
	}

	@Override
	public JCTree visitFieldRef(final lombok.ast.pg.FieldRef node, final Void p) {
		final Name fieldName = name(node.getName());
		if (node.getReceiver() == null) {
			return setGeneratedBy(M(node).Ident(fieldName), source, sourceNode.getContext());
		} else {
			return setGeneratedBy(M(node).Select(build(node.getReceiver(), JCExpression.class), fieldName), source, sourceNode.getContext());
		}
	}

	@Override
	public JCTree visitForeach(final lombok.ast.pg.Foreach node, final Void p) {
		final JCVariableDecl var = build(node.getElementVariable());
		final JCExpression expr = build(node.getCollection());
		final JCStatement body = build(node.getAction(), JCStatement.class);
		final JCEnhancedForLoop foreach = setGeneratedBy(M(node).ForeachLoop(var, expr, body), source, sourceNode.getContext());
		return foreach;
	}

	@Override
	public JCTree visitIf(final lombok.ast.pg.If node, final Void p) {
		final JCExpression cond = build(node.getCondition());
		final JCStatement thenpart = build(node.getThenStatement(), JCStatement.class);
		final JCStatement elsepart = build(node.getElseStatement(), JCStatement.class);
		final JCIf ifStatement = setGeneratedBy(M(node).If(cond, thenpart, elsepart), source, sourceNode.getContext());
		return ifStatement;
	}

	@Override
	public JCTree visitInitializer(lombok.ast.pg.Initializer node, Void p) {
		final JCBlock block = setGeneratedBy(M(node).Block(flagsFor(node.getModifiers()), build(node.getStatements(), JCStatement.class)), source, sourceNode.getContext());
		return block;
	}

	@Override
	public JCTree visitInstanceOf(final lombok.ast.pg.InstanceOf node, final Void p) {
		final JCInstanceOf instanceOf = setGeneratedBy(M(node).TypeTest(build(node.getExpression(), JCExpression.class), build(node.getType())), source, sourceNode.getContext());
		return instanceOf;
	}

	@Override
	public JCTree visitJavaDoc(final lombok.ast.pg.JavaDoc node, final Void p) {
		final StringBuilder javadoc = new StringBuilder();
		if (node.getMessage() != null) javadoc.append(node.getMessage()).append("\n");
		for (Map.Entry<String, String> argumentReference : node.getArgumentReferences().entrySet()) {
			javadoc.append("@param ").append(argumentReference.getKey()).append(" ").append(argumentReference.getValue()).append("\n");
		}
		for (Map.Entry<String, String> paramTypeReference : node.getParamTypeReferences().entrySet()) {
			javadoc.append("@param <").append(paramTypeReference.getKey()).append("> ").append(paramTypeReference.getValue()).append("\n");
		}
		for (Map.Entry<lombok.ast.pg.TypeRef, String> exceptionReference : node.getExceptionReferences().entrySet()) {
			javadoc.append("@throws ").append(exceptionReference.getKey().getTypeName()).append(" ").append(exceptionReference.getValue()).append("\n");
		}
		if (node.getReturnMessage() != null) javadoc.append("@return ").append(node.getReturnMessage()).append("\n");
		return M(node).Literal(javadoc.toString());
	}

	@Override
	public JCTree visitLocalDecl(final lombok.ast.pg.LocalDecl node, final Void p) {
		final JCModifiers mods = setGeneratedBy(M(node).Modifiers(flagsFor(node.getModifiers()), build(node.getAnnotations(), JCAnnotation.class)), source, sourceNode.getContext());
		final JCExpression vartype = build(node.getType());
		final JCExpression init = build(node.getInitialization());
		final JCVariableDecl local = setGeneratedBy(M(node).VarDef(mods, name(node.getName()), vartype, init), source, sourceNode.getContext());
		return local;
	}

	@Override
	public JCTree visitMethodDecl(final lombok.ast.pg.MethodDecl node, final Void p) {
		final JCModifiers mods = setGeneratedBy(M(node).Modifiers(flagsFor(node.getModifiers()), build(node.getAnnotations(), JCAnnotation.class)), source, sourceNode.getContext());
		final JCExpression restype = build(node.getReturnType());
		final List<JCTypeParameter> typarams = build(node.getTypeParameters());
		final List<JCVariableDecl> params = build(node.getArguments());
		final List<JCExpression> thrown = build(node.getThrownExceptions());
		JCBlock body = null;
		if (!node.noBody() && ((mods.flags & Flags.ABSTRACT) == 0)) {
			body = setGeneratedBy(M(node).Block(0, build(node.getStatements(), JCStatement.class)), source, sourceNode.getContext());
		}
		final JCMethodDecl method = setGeneratedBy(M(node).MethodDef(mods, name(node.getName()), restype, typarams, params, thrown, body, null), source, sourceNode.getContext());
		return withJavaDoc(method, node.getJavaDoc());
	}

	@Override
	public JCTree visitNameRef(final lombok.ast.pg.NameRef node, final Void p) {
		return setGeneratedBy(chainDots(node, node.getName()), source, sourceNode.getContext());
	}

	@Override
	public JCTree visitNew(final lombok.ast.pg.New node, final Void p) {
		final List<JCExpression> typeargs = build(node.getTypeArgs());
		final JCExpression clazz = build(node.getType());
		final List<JCExpression> args = build(node.getArgs());
		final JCClassDecl def = build(node.getAnonymousType());
		final JCNewClass newClass = setGeneratedBy(M(node).NewClass(null, typeargs, clazz, args, def), source, sourceNode.getContext());
		return newClass;
	}

	@Override
	public JCTree visitNewArray(final lombok.ast.pg.NewArray node, final Void p) {
		final ListBuffer<JCExpression> dims = ListBuffer.lb();
		dims.appendList(build(node.getDimensionExpressions(), JCExpression.class));
		final JCExpression elemtype = build(node.getType());
		final List<JCExpression> initializerExpressions = build(node.getInitializerExpressions(), JCExpression.class);
		JCNewArray newClass = setGeneratedBy(M(node).NewArray(elemtype, dims.toList(), initializerExpressions.isEmpty() ? null : initializerExpressions), source, sourceNode.getContext());
		return newClass;
	}

	@Override
	public JCTree visitNullLiteral(final lombok.ast.pg.NullLiteral node, final Void p) {
		final JCLiteral literal = setGeneratedBy(M(node).Literal(TYPES.get("null"), null), source, sourceNode.getContext());
		return literal;
	}

	@Override
	public JCTree visitNumberLiteral(final lombok.ast.pg.NumberLiteral node, final Void p) {
		final JCLiteral literal = setGeneratedBy(M(node).Literal(node.getNumber()), source, sourceNode.getContext());
		return literal;
	}

	@Override
	public JCTree visitReturn(final lombok.ast.pg.Return node, final Void p) {
		final JCReturn returnStatement = setGeneratedBy(M(node).Return(build(node.getExpression(), JCExpression.class)), source, sourceNode.getContext());
		return returnStatement;
	}

	@Override
	public JCTree visitReturnDefault(final lombok.ast.pg.ReturnDefault node, final Void p) {
		lombok.ast.pg.TypeRef returnType = node.upTo(lombok.ast.pg.MethodDecl.class).getReturnType();
		if (returnType == null) {
			returnType = Type(methodNodeOf(sourceNode).getName());
		}
		return build(Return(DefaultValue(returnType)));
	}

	@Override
	public JCTree visitStringLiteral(final lombok.ast.pg.StringLiteral node, final Void p) {
		final JCLiteral literal = setGeneratedBy(M(node).Literal(node.getString()), source, sourceNode.getContext());
		return literal;
	}
	
	@Override
	public JCTree visitSuper(final lombok.ast.pg.Super node, final Void p) {
		final Name superName = name("super");
		if (node.getType() == null) {
			return setGeneratedBy(M(node).Ident(superName), source, sourceNode.getContext());
		} else {
			return setGeneratedBy(M(node).Select(build(node.getType(), JCExpression.class), superName), source, sourceNode.getContext());
		}
	}

	@Override
	public JCTree visitSwitch(final lombok.ast.pg.Switch node, final Void p) {
		final JCSwitch switchStatement = setGeneratedBy(M(node).Switch(build(node.getExpression(), JCExpression.class), build(node.getCases(), JCCase.class)), source, sourceNode.getContext());
		return switchStatement;
	}

	@Override
	public JCTree visitSynchronized(final lombok.ast.pg.Synchronized node, final Void p) {
		final JCBlock block = setGeneratedBy(M(node).Block(0, build(node.getStatements(), JCStatement.class)), source, sourceNode.getContext());
		final JCSynchronized synchronizedStatemenet = setGeneratedBy(M(node).Synchronized(build(node.getLock(), JCExpression.class), block), source, sourceNode.getContext());
		return synchronizedStatemenet;
	}
	
	@Override
	public JCTree visitTernary(final lombok.ast.pg.Ternary node, final Void p) {
		return setGeneratedBy(M(node).Conditional(
				build(node.getTest(), JCExpression.class),
				build(node.getIfTrue(), JCExpression.class),
				build(node.getIfFalse(), JCExpression.class)), source, sourceNode.getContext());
	}

	@Override
	public JCTree visitThis(final lombok.ast.pg.This node, final Void p) {
		final Name thisName = name("this");
		if (node.getType() == null) {
			return setGeneratedBy(M(node).Ident(thisName), source, sourceNode.getContext());
		} else {
			return setGeneratedBy(M(node).Select(build(node.getType(), JCExpression.class), thisName), source, sourceNode.getContext());
		}
	}

	@Override
	public JCTree visitThrow(final lombok.ast.pg.Throw node, final Void p) {
		final JCThrow throwStatement = setGeneratedBy(M(node).Throw(build(node.getExpression(), JCExpression.class)), source, sourceNode.getContext());
		return throwStatement;
	}

	@Override
	public JCTree visitTry(final lombok.ast.pg.Try node, final Void p) {
		final ListBuffer<JCCatch> catchers = ListBuffer.lb();
		final Iterator<lombok.ast.pg.Argument> iter = node.getCatchArguments().iterator();
		for (lombok.ast.pg.Block catchBlock : node.getCatchBlocks()) {
			lombok.ast.pg.Argument catchArgument = iter.next();
			catchers.append(M(node).Catch(build(catchArgument, JCVariableDecl.class), build(catchBlock, JCBlock.class)));
		}
		final JCTry tryStatement = setGeneratedBy(M(node).Try(build(node.getTryBlock(), JCBlock.class), catchers.toList(), build(node.getFinallyBlock(), JCBlock.class)), source, sourceNode.getContext());
		return tryStatement;
	}

	@Override
	public JCTree visitTypeParam(final lombok.ast.pg.TypeParam node, final Void p) {
		JCTypeParameter typeParam = setGeneratedBy(M(node).TypeParameter(name(node.getName()), build(node.getBounds(), JCExpression.class)), source, sourceNode.getContext());
		return typeParam;
	}

	@Override
	public JCTree visitTypeRef(final lombok.ast.pg.TypeRef node, final Void p) {
		JCExpression typeRef;
		final String typeName = node.getTypeName();
		if (TYPES.containsKey(typeName)) {
			typeRef = M(node).TypeIdent(TYPES.get(typeName));
			typeRef = setGeneratedBy(typeRef, source, sourceNode.getContext());
			if ("void".equals(typeName)) return typeRef;
		} else {
			typeRef = chainDots(node, node.getTypeName());
			typeRef = setGeneratedBy(typeRef, source, sourceNode.getContext());
			if (!node.getTypeArgs().isEmpty()) {
				typeRef = M(node).TypeApply(typeRef, build(node.getTypeArgs(), JCExpression.class));
				typeRef = setGeneratedBy(typeRef, source, sourceNode.getContext());
			}
		}
		for (int i = 0; i < node.getDims(); i++) {
			typeRef = setGeneratedBy(M(node).TypeArray(typeRef), source, sourceNode.getContext());
		}
		return typeRef;
	}

	@Override
	public JCTree visitUnary(final lombok.ast.pg.Unary node, final Void p) {
		final String operator = node.getOperator();
		final TreeTag opCode;
		if (UNARY_OPERATORS.containsKey(operator)) {
			opCode = UNARY_OPERATORS.get(operator);
		} else {
			throw new IllegalStateException(String.format("Unknown unary operator '%s'", operator));
		}
		JCUnary unary = setGeneratedBy(M(node).Unary(opCode, build(node.getExpression(), JCExpression.class)), source, sourceNode.getContext());
		return unary;
	}

	@Override
	public JCTree visitWhile(final lombok.ast.pg.While node, final Void p) {
		final JCWhileLoop whileLoop = setGeneratedBy(M(node).WhileLoop(build(node.getCondition(), JCExpression.class), build(node.getAction(), JCStatement.class)), source, sourceNode.getContext());
		return whileLoop;
	}

	@Override
	public JCTree visitWildcard(final lombok.ast.pg.Wildcard node, final Void p) {
		BoundKind boundKind = BoundKind.UNBOUND;
		if (node.getBound() != null) {
			switch (node.getBound()) {
			case SUPER:
				boundKind = BoundKind.SUPER;
				break;
			default:
			case EXTENDS:
				boundKind = BoundKind.EXTENDS;
			}
		}
		final TypeBoundKind kind = setGeneratedBy(M(node).TypeBoundKind(boundKind), source, sourceNode.getContext());
		final JCWildcard wildcard = setGeneratedBy(M(node).Wildcard(kind, build(node.getType(), JCExpression.class)), source, sourceNode.getContext());
		return wildcard;
	}

	@Override
	public JCTree visitWrappedExpression(final lombok.ast.pg.WrappedExpression node, final Void p) {
		final JCExpression expression = new TreeCopier<Void>(M(node).getUnderlyingTreeMaker()).copy((JCExpression) node.getWrappedObject());
		return expression;
	}

	@Override
	public JCTree visitWrappedMethodDecl(final lombok.ast.pg.WrappedMethodDecl node, final Void p) {
		MethodSymbol methodSymbol = (MethodSymbol) node.getWrappedObject();
		Type mtype = methodSymbol.type;

		if (node.getReturnType() == null) {
			node.withReturnType(Type(fixLeadingDot(node, M(node).Type(mtype.getReturnType()))));
		}
		if (node.getThrownExceptions().isEmpty()) for (JCExpression expr : M(node).getUnderlyingTreeMaker().Types(mtype.getThrownTypes())) {
			node.withThrownException(Type(fixLeadingDot(node, expr)));
		}
		if (node.getArguments().isEmpty()) for (JCVariableDecl param : M(node).getUnderlyingTreeMaker().Params(mtype.getParameterTypes(), methodSymbol)) {
			node.withArgument(Arg(Type(fixLeadingDot(node, param.vartype)), As.string(param.name)));
		}
		if (node.getTypeParameters().isEmpty()) for (JCTypeParameter typaram : M(node).getUnderlyingTreeMaker().TypeParams(mtype.getTypeArguments())) {
			final lombok.ast.pg.TypeParam typeParam = TypeParam(As.string(typaram.name));
			for (JCExpression expr : typaram.bounds) {
				typeParam.withBound(Type(fixLeadingDot(node, expr)));
			}
			node.withTypeParameter(typeParam);
		}

		final JCModifiers mods = M(node).Modifiers(methodSymbol.flags() & (~Flags.ABSTRACT), build(node.getAnnotations(), JCAnnotation.class));
		final JCExpression restype = build(node.getReturnType());
		final Name name = methodSymbol.name;
		final List<JCExpression> thrown = build(node.getThrownExceptions(), JCExpression.class);
		final List<JCTypeParameter> typarams = build(node.getTypeParameters(), JCTypeParameter.class);
		final List<JCVariableDecl> params = build(node.getArguments(), JCVariableDecl.class);
		JCBlock body = null;
		if (!node.noBody()) {
			body = M(node).Block(0, build(node.getStatements(), JCStatement.class));
		}
		final JCMethodDecl method = M(node).MethodDef(mods, name, restype, typarams, params, thrown, body, null);
		return method;
	}

	@Override
	public JCTree visitWrappedStatement(final lombok.ast.pg.WrappedStatement node, final Void p) {
		final JCStatement statement = new TreeCopier<Void>(M(node).getUnderlyingTreeMaker()).copy((JCStatement) node.getWrappedObject());
		return statement;
	}

	@Override
	public JCTree visitWrappedTypeRef(final lombok.ast.pg.WrappedTypeRef node, final Void p) {
		JCExpression typeRef = null;
		if (node.getWrappedObject() instanceof Type) {
			typeRef = fixLeadingDot(node, M(node).Type((Type) node.getWrappedObject()));
		} else if (node.getWrappedObject() instanceof JCExpression) {
			typeRef = new TreeCopier<Void>(M(node).getUnderlyingTreeMaker()).copy((JCExpression) node.getWrappedObject());
		}
		for (int i = 0; i < node.getDims(); i++) {
			typeRef = setGeneratedBy(M(node).TypeArray(typeRef), source, sourceNode.getContext());
		}
		return typeRef;
	}
}
