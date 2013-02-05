import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@XmlRootElement(name = DataObject.ROOT_NAME)
class DataObject implements lombok.core.xml.XmlSerializable {
	
	public static final String ROOT_NAME = "do";
	private static final String DESC = "desc";
	@XmlElement(name = "id")
	private long dataObjectId;
	@XmlTransient
	private String name;
	@XmlAttribute(name = DESC)
	private String description;
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	private Timestamp createdDate;
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	private Timestamp updatedDate;
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public lombok.core.xml.XElement toXml() {
		final lombok.core.xml.XElement element = new lombok.core.xml.XElement(DataObject.ROOT_NAME);
		element.appendChild(new lombok.core.xml.XElement("id", this.dataObjectId));
		element.appendChild(new lombok.core.xml.XAttribute(DESC, this.description));
		element.appendChild(new lombok.core.xml.XAttribute("createdDate", new TimestampAdapter().marshal(this.createdDate)));
		element.appendChild(new lombok.core.xml.XAttribute("updatedDate", new TimestampAdapter().marshal(this.updatedDate)));
		return element;
	}
}
class TimestampAdapter extends XmlAdapter<Long, Timestamp> {
	@Override
	public Timestamp unmarshal(Long v) {
		return new Timestamp(v);
	}

	@Override
	public Long marshal(Timestamp v) {
		return v.getTime();
	}
}