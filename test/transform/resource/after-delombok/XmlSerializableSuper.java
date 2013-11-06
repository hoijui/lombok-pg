import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.XmlSerializable;
@XmlSerializable
@XmlRootElement(name = DataObject.ROOT_NAME)
class DataObject extends OtherObject implements lombok.core.xml.XmlSerializable {
	
	public static final String ROOT_NAME = "do";
	private static final String DESC = "desc";
	@XmlElement(name = "id")
	private long dataObjectId;
	@XmlTransient
	private String name;
	@XmlAttribute(name = DESC)
	private String description;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement(DataObject.ROOT_NAME);
		this.appendElements(element);
		return element;
	}
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public void appendElements(final lombok.core.xml.XElement element) {
		super.appendElements(element);
		element.appendChild(new lombok.core.xml.XElement("id", this.dataObjectId));
		element.appendChild(new lombok.core.xml.XAttribute(DESC, this.description));
	}
}
@XmlSerializable
class OtherObject implements lombok.core.xml.XmlSerializable {
	
	@XmlAttribute
	private long id;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement("OtherObject");
		this.appendElements(element);
		return element;
	}
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public void appendElements(final lombok.core.xml.XElement element) {
		element.appendChild(new lombok.core.xml.XAttribute("id", this.id));
	}
}