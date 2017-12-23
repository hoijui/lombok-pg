package lombok.core.xml;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Represents an XML text node
 * @author Jason Blackwell
 *
 */
public class XText extends XObject {
	private String text;
	
	/**
	 * Initializes a new instance with the specified text.
	 * @param text The text content.
	 */
	public XText(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the text of the node.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Sets the text of the node.
	 * @param text The text content.
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	protected void buildXml(Node parent) throws ParserConfigurationException {
		Text text = parent.getOwnerDocument().createTextNode(this.text);
		parent.appendChild(text);
	}
}
