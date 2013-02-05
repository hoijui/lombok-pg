package lombok;

public interface ReferencedBy<T> {
	void setRelatedId(T item, Long id);
}
