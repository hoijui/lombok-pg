package lombok.core.xml;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Represents a node or an attribute in an XML tree.
 * @author Jason Blackwell
 *
 */
public abstract class XObject {
	private XDocument document;
	private Document realDocument;
	private String toStringValue;
	
	/**
	 * Sets the document this element belongs to.
	 * @param document The parent document.
	 */
	protected void setDocument(Document document) {
		this.realDocument = document;
	}
	
	/**
	 * Gets the document this element belongs to.
	 */
	public XDocument getDocument() {
		return document;
	}
	
	/**
	 * Gets the org.w3c.dom.Document used to build this document
	 * @throws ParserConfigurationException
	 */
	protected Document getXmlDocument() throws ParserConfigurationException {
		if (realDocument == null) {
			realDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		}
		
		return realDocument;
	}
	
	/**
	 * Creates the underlying elements and appends them to the parent.
	 * @param parent The parent of the element.
	 * @throws ParserConfigurationException
	 */
	protected abstract void buildXml(Node parent) throws ParserConfigurationException;
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean pretty) {
		if (toStringValue == null) {
			try {
				this.buildXml(getXmlDocument());
				
				StringWriter sw = new StringWriter();
				try {
					TransformerFactory factory = TransformerFactory.newInstance();
					factory.setAttribute("indent-number", 4);
					Transformer t = factory.newTransformer();
					t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					if (pretty) {
						t.setOutputProperty(OutputKeys.INDENT, "yes");
					}
					t.transform(new DOMSource(getXmlDocument()), new StreamResult(sw));
				} catch (TransformerException te) {
					System.out.println("nodeToString Transformer Exception");
				}
				toStringValue = sw.toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return toStringValue;
	}
}
