import java.sql.Timestamp;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.XmlSerializable;
@XmlSerializable
class DataObject {
	private long dataObjectId;
	private String name;
	private String description;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private RelatedObject relatedObject;
	private MyList<RelatedObject> relatedObjects;
}

@XmlSerializable
class RelatedObject {
	private long relatedObjectId;
	private String name;
}

class MyList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 0L;
}