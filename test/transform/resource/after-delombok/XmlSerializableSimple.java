import java.sql.Timestamp;

import lombok.XmlSerializable;
@XmlSerializable
class DataObject implements lombok.core.xml.XmlSerializable {
	
	private long dataObjectId;
	private String name;
	private String description;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement("DataObject");
		element.appendChild(new lombok.core.xml.XAttribute("dataObjectId", this.dataObjectId));
		element.appendChild(new lombok.core.xml.XAttribute("name", this.name));
		element.appendChild(new lombok.core.xml.XAttribute("description", this.description));
		element.appendChild(new lombok.core.xml.XAttribute("createdDate", this.createdDate));
		element.appendChild(new lombok.core.xml.XAttribute("updatedDate", this.updatedDate));
		return element;
	}
}