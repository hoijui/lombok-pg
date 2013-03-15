import java.util.List;

import lombok.Extension;
@lombok.ExtensionMethod(BaseObject.class) class MatchGroup extends BaseObject {
  private List<Long> orders;
  MatchGroup() {
    super();
  }
  public void removeOrder(long orderId) {
    if (orders.contains(orderId))
        {
          orders.remove(orderId);
        }
  }
}
class BaseObject {
  BaseObject() {
    super();
  }
  public static @Extension <T>boolean contains(final Iterable<T> col, final T item) {
    return false;
  }
}