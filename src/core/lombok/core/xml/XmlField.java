package lombok.core.xml;

import lombok.ast.pg.Expression;

public class XmlField {
	public Expression<?> name;
	public boolean isAttribute;
	public String adapter;
	
	@Override
	public String toString() {
		return "name: " + name + ", isAttribute: " + isAttribute + ", adapter: " + adapter;
	}
}
