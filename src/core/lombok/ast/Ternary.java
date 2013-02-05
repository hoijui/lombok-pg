package lombok.ast;

import lombok.Getter;

@Getter
public class Ternary extends Expression<Ternary> {
	private Expression<?> test;
	private Expression<?> ifTrue;
	private Expression<?> ifFalse;
	
	public Ternary(Expression<?> test, Expression<?> ifTrue, Expression<?> ifFalse) {
		this.test = test;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}

	@Override
	public <RETURN_TYPE, PARAMETER_TYPE> RETURN_TYPE accept(ASTVisitor<RETURN_TYPE, PARAMETER_TYPE> v, PARAMETER_TYPE p) {
		return v.visitTernary(this, p);
	}
}
