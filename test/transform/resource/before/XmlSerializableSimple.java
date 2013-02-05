import java.sql.Timestamp;
import lombok.XmlSerializable;
@XmlSerializable
class DataObject {
	private long dataObjectId;
	private String name;
	private String description;
	private Timestamp createdDate;
	private Timestamp updatedDate;
}