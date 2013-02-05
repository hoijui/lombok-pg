import java.util.List;
@lombok.ExtensionMethod(BaseObject.class)
class MatchGroup extends BaseObject {
	private List<OrderData> orders;

	public void removeOrder(Long orderId) {
		OrderData orderToBeRemoved = null;
		for (OrderData order : orders) {
			if (order.getOrderId().equals(orderId)) {
				orderToBeRemoved = order;
				break;
			}
		}
		orders.remove(orderToBeRemoved);
	}
}

class BaseObject {		
	@Override
	public boolean equals(final Object obj) {
		return false;
	}
}
class OrderData {
	public Long getOrderId() {
		return 0L;
	}
}