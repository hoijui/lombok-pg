import java.util.List;
class MatchGroup extends BaseObject {
	private List<Long> orders;

	public void removeOrder(long orderId) {
		if (BaseObject.contains(orders, orderId)) {
			orders.remove(orderId);
		}
	}
}

class BaseObject {
	public static <T> boolean contains(final Iterable<T> col, final T item) {
		return false;
	}
}