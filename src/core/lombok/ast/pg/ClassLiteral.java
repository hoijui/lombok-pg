package lombok.ast.pg;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ClassLiteral extends Expression<ClassLiteral> {
	
	private final String typeName;
	private final Object wrappedObject;
	
	@Override
	public <RETURN_TYPE, PARAMETER_TYPE> RETURN_TYPE accept(
			ASTVisitor<RETURN_TYPE, PARAMETER_TYPE> v, PARAMETER_TYPE p) {
		return v.visitClassLiteral(this, p);
	}
	
}
