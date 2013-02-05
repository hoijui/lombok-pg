import java.sql.Timestamp;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
class DataObject implements lombok.core.xml.XmlSerializable {
		
	private long dataObjectId;
	private String name;
	private String description;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private RelatedObject relatedObject;
	private MyList<RelatedObject> relatedObjects;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement("DataObject");
		element.appendChild(new lombok.core.xml.XAttribute("dataObjectId", this.dataObjectId));
		element.appendChild(new lombok.core.xml.XAttribute("name", this.name));
		element.appendChild(new lombok.core.xml.XAttribute("description", this.description));
		element.appendChild(new lombok.core.xml.XAttribute("createdDate", this.createdDate));
		element.appendChild(new lombok.core.xml.XAttribute("updatedDate", this.updatedDate));
		lombok.core.xml.XmlUtils.append(element, this.relatedObject, "relatedObject", true);
		lombok.core.xml.XmlUtils.append(element, this.relatedObjects, "relatedObjects", true);
		return element;
	}
}

class RelatedObject implements lombok.core.xml.XmlSerializable {
	
	private long relatedObjectId;
	private String name;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement("RelatedObject");
		element.appendChild(new lombok.core.xml.XAttribute("relatedObjectId", this.relatedObjectId));
		element.appendChild(new lombok.core.xml.XAttribute("name", this.name));
		return element;
	}
}

class MyList<T> extends ArrayList<T> {
	
	private static final long serialVersionUID = 0L;
}