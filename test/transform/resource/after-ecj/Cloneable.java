import lombok.Cloneable;
@Cloneable class DataObject implements java.lang.Cloneable {
  private String name;
  private int age;
  DataObject() {
    super();
  }
  public @java.lang.Override @java.lang.SuppressWarnings("all") DataObject clone() {
    final DataObject this$DataObject = new DataObject();
    this$DataObject.name = this.name;
    this$DataObject.age = this.age;
    return this$DataObject;
  }
}