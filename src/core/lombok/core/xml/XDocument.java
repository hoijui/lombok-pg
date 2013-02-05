package lombok.core.xml;


/**
 * Represents an XML document
 * 
 * @author Jason Blackwell
 *
 */
public class XDocument extends XElement {	
	/**
	 * Initializes a new instance with the given name as the root now.
	 * @param name The name of the root element 
	 * @param children Any children belonging to the document
	 */
	public XDocument(String name, XObject... children) {
		super(name, children);
	}
	
	/**
	 * Returns the unformatted xml for the document
	 */
	@Override
	public String toString() {
		return toString(false);
	}
	
	/**
	 * Returns the formatted xml for the document
	 */
	public String prettyToString() {
		return toString(true);
	}
}
