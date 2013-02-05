import java.util.List;
@lombok.ExtensionMethod(BaseObject.class) class MatchGroup extends BaseObject {
  private List<OrderData> orders;
  MatchGroup() {
    super();
  }
  public void removeOrder(Long orderId) {
    OrderData orderToBeRemoved = null;
    for (OrderData order : orders) 
      {
        if (order.getOrderId().equals(orderId))
            {
              orderToBeRemoved = order;
              break ;
            }
      }
    orders.remove(orderToBeRemoved);
  }
}
class BaseObject {
  BaseObject() {
    super();
  }
  public @Override boolean equals(final Object obj) {
    return false;
  }
}
class OrderData {
  OrderData() {
    super();
  }
  public Long getOrderId() {
    return 0L;
  }
}