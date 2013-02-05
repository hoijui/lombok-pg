package lombok.core.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents an XML element.
 * 
 * @author Jason Blackwell
 *
 */
public class XElement extends XNode {
	private List<XObject> children = new ArrayList<XObject>();
	
	/**
	 * Initializes a new instance with the specified name and content
	 * @param name The name of the element
	 * @param children The initial content of the element.
	 */
	public XElement(String name, XObject... children) {
		super(name);
		this.children.addAll(Arrays.asList(children));
	}
	
	/**
	 * Initializes a new instance with the specified name and context.
	 * @param name The name of the element.
	 * @param value The initial content of the element.
	 */
	public XElement(String name, Object value) {
		this(name);
		if (value != null) {
			this.children.add(new XText(value.toString()));
		}
	}
	
	/**
	 * Gets all this element's child elements.
	 */
	public List<XObject> getChildren() {
		return this.children;
	}

	@Override
	protected void buildXml(Node parent) throws ParserConfigurationException {
		Document doc = getXmlDocument();
		
		Element element = doc.createElement(getName());
		
		if (!children.isEmpty()) {
			for (XObject child : children) {
				child.setDocument(doc);
				child.buildXml(element);
			}
		}
		
		parent.appendChild(element);
	}
	
	public void appendChild(XObject child) {
		this.children.add(child);
	}
	
	/**
	 * Adds the specified children to the element .
	 * @param children The children to add.
	 */
	public void appendChildren(XObject... children) {
		for (XObject child : children) {
			this.children.add(child);
		}
	}
}
