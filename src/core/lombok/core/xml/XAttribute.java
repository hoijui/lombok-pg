package lombok.core.xml;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents and XML attribute
 * 
 * @author Jason Blackwell
 *
 */
public class XAttribute extends XNode {
	private Object value;
	
	public XAttribute(String name) {
		super(name);
	}
	
	/**
	 * Initializes a new instance from the specified name and value
	 * @param name The name of the attribute
	 * @param value The value of the attribute
	 */
	public XAttribute(String name, Object value) {
		this(name);
		this.value = value;
	}
	
	/**
	 * Gets the value of the attribute
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Sets the value of the attribute
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	protected void buildXml(Node parent) throws ParserConfigurationException {
		if (!(parent instanceof Element)) {
			throw new IllegalArgumentException("XAttribute have to be children of XElement or XDocument");
		}
		
		if (value != null) {
			((Element) parent).setAttribute(getName(), value.toString());
		}
	}
}
