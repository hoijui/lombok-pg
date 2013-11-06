package lombok.core.xml;

public interface XmlSerializable {
	XElement toXml();
	void appendElements(final XElement element);
}
