package lombok.core.xml;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

/**
 * Represents a text now that contains CDATA
 * 
 * @author Jason Blackwell
 *
 */
public class XCData extends XText {
	
	/**
	 * Initializes a new instance with the specified text
	 * @param text The value of the CDATA node
	 */
	public XCData(String text) {
		super(text);
	}

	@Override
	protected void buildXml(Node parent) throws ParserConfigurationException {
		CDATASection cdata = getDocument().getXmlDocument().createCDATASection(getText());
		parent.appendChild(cdata);
	}	
}