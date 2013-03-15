import java.util.List;

import lombok.Extension;
@lombok.ExtensionMethod(BaseObject.class)
class MatchGroup extends BaseObject {
	private List<Long> orders;

	public void removeOrder(long orderId) {
		if (orders.contains(orderId)) {
			orders.remove(orderId);
		}
	}
}

class BaseObject {
	@Extension
	public static <T> boolean contains(final Iterable<T> col, final T item) {
		return false;
	}
}