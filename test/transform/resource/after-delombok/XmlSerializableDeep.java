import java.sql.Timestamp;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.XmlSerializable;
@XmlSerializable
class DataObject<T> implements lombok.core.xml.XmlSerializable {
		
	private long dataObjectId;
	private String name;
	private String description;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private RelatedObject relatedObject;
	private List<RelatedObject> relatedObjects;
	private java.util.List<T> otherObjects;
	private List<Integer> integers;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement("DataObject");
		this.appendElements(element);
		return element;
	}
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public void appendElements(final lombok.core.xml.XElement element) {
		element.appendChild(new lombok.core.xml.XAttribute("dataObjectId", this.dataObjectId));
		element.appendChild(new lombok.core.xml.XAttribute("name", this.name));
		element.appendChild(new lombok.core.xml.XAttribute("description", this.description));
		element.appendChild(new lombok.core.xml.XAttribute("createdDate", this.createdDate));
		element.appendChild(new lombok.core.xml.XAttribute("updatedDate", this.updatedDate));
		lombok.core.xml.XmlUtils.append(element, this.relatedObject, "relatedObject", true);
		lombok.core.xml.XmlUtils.append(element, this.relatedObjects, "relatedObjects", true);
		lombok.core.xml.XmlUtils.append(element, this.otherObjects, "otherObjects", true);
		lombok.core.xml.XmlUtils.append(element, this.integers, "integers", true);
	}
}

@XmlSerializable
class RelatedObject implements lombok.core.xml.XmlSerializable {
	
	private long relatedObjectId;
	private String name;
	private DataObject<Integer> parent;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement("RelatedObject");
		this.appendElements(element);
		return element;
	}
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public void appendElements(final lombok.core.xml.XElement element) {
		element.appendChild(new lombok.core.xml.XAttribute("relatedObjectId", this.relatedObjectId));
		element.appendChild(new lombok.core.xml.XAttribute("name", this.name));
		lombok.core.xml.XmlUtils.append(element, this.parent, "parent", true);
	}
}