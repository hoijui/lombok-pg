package lombok.core.xml;

/**
 * Represents an XML node that has a name, either an element or a node
 * @author Jason Blackwell
 *
 */
public abstract class XNode extends XObject {
	private String name;
	
	/**
	 * Initializes a new instance with the specified name
	 * @param name The name of the node
	 */
	protected XNode(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name of the element
	 */
	public String getName() {
		return this.name;
	}
}
