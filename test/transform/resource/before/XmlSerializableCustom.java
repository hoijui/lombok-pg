import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.XmlSerializable;
@XmlSerializable
@XmlRootElement(name=DataObject.ROOT_NAME)
class DataObject {
	public static final String ROOT_NAME = "do";
	private static final String DESC = "desc";
	@XmlElement(name="id")
	private long dataObjectId;
	@XmlTransient
	private String name;
	@XmlAttribute(name=DESC)
	private String description;
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	private Timestamp createdDate;
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	private Timestamp updatedDate;
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