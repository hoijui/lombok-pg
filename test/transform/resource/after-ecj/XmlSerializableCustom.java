import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.XmlSerializable;
@XmlSerializable @XmlRootElement(name = DataObject.ROOT_NAME) class DataObject implements lombok.core.xml.XmlSerializable {
  public static final String ROOT_NAME = "do";
  private static final String DESC = "desc";
  private @XmlElement(name = "id") long dataObjectId;
  private @XmlTransient String name;
  private @XmlAttribute(name = DESC) String description;
  private @XmlJavaTypeAdapter(TimestampAdapter.class) Timestamp createdDate;
  private @XmlJavaTypeAdapter(TimestampAdapter.class) Timestamp updatedDate;
  <clinit>() {
  }
  DataObject() {
    super();
  }
  public @java.lang.Override @java.lang.SuppressWarnings("all") lombok.core.xml.XElement toXml() {
    final lombok.core.xml.XElement element = new lombok.core.xml.XElement(DataObject.ROOT_NAME);
    this.appendElements(element);
    return element;
  }
  
  public @java.lang.Override @java.lang.SuppressWarnings("all") void appendElements(final lombok.core.xml.XElement element) {
    element.appendChild(new lombok.core.xml.XElement("id", this.dataObjectId));
    element.appendChild(new lombok.core.xml.XAttribute(DESC, this.description));
    element.appendChild(new lombok.core.xml.XAttribute("createdDate", new TimestampAdapter().marshal(this.createdDate)));
    element.appendChild(new lombok.core.xml.XAttribute("updatedDate", new TimestampAdapter().marshal(this.updatedDate)));
  }
}
class TimestampAdapter extends XmlAdapter<Long, Timestamp> {
  TimestampAdapter() {
    super();
  }
  public @Override Timestamp unmarshal(Long v) {
    return new Timestamp(v);
  }
  public @Override Long marshal(Timestamp v) {
    return v.getTime();
  }
}