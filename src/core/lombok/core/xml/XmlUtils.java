package lombok.core.xml;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class XmlUtils {
	public static void append(XElement parent, Map<?, ?> map, String elementName, boolean useAttributes) {
		if (map != null && !map.isEmpty()) {
			XElement element = new XElement(elementName);
			for (Entry<?, ?> kvp : map.entrySet()) {
				Object key = kvp.getKey();
				Object value = kvp.getValue();
				
				String keyString = (key == null ? "null" : key.toString());
				
				element.appendChild((useAttributes ? new XAttribute(keyString, value) : new XElement(keyString, value)));
			}
			
			parent.appendChild(element);
		}
	}
	
	public static void append(XElement parent, Iterable<?> col, String elementName, @SuppressWarnings("unused") boolean useAttribute) {
		if (col != null) {
			Iterator<?> iterator = col.iterator();
			if (iterator.hasNext()) {				
				Object firstItem = iterator.next();
				
				if (firstItem instanceof XmlSerializable) {
					XElement element = new XElement(elementName);
					element.appendChild(((XmlSerializable) firstItem).toXml());
					
					while (iterator.hasNext()) {
						element.appendChild(((XmlSerializable) iterator.next()).toXml());
					}
					
					parent.appendChild(element);
				} else {
					parent.appendChild(new XElement(elementName, firstItem));
					while (iterator.hasNext()) {
						parent.appendChild(new XElement(elementName, iterator.next()));
					}
				}
			}
		}
	}
	
	public static void append(XElement parent, Object value, String elementName, boolean useAttribute) {
		if (value != null) {
			if (value instanceof XmlSerializable) {
				parent.appendChild(((XmlSerializable) value).toXml());
			} else if (value instanceof Iterable<?>) {
				append(parent, (Iterable<?>) value, elementName, useAttribute);
			} else if (value instanceof Map<?, ?>) {
				append(parent, (Map<?, ?>) value, elementName, useAttribute);
			}else {
				parent.appendChild((useAttribute ? new XAttribute(elementName, value) : new XElement(elementName, value)));
			}
		}
	}
}
