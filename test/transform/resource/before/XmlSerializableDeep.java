import java.sql.Timestamp;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.XmlSerializable;

@XmlSerializable
class DataObject<T> {
	private long dataObjectId;
	private String name;
	private String description;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private RelatedObject relatedObject;
	private List<RelatedObject> relatedObjects;
	private java.util.List<T> otherObjects;
	private List<Integer> integers;
}

@XmlSerializable
class RelatedObject {
	private long relatedObjectId;
	private String name;
	private DataObject<Integer> parent;
}