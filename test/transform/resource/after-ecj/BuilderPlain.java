import java.util.Map;
import java.util.HashMap;
@lombok.Builder(prefix = "with") class BuilderPlain {
  public @java.lang.SuppressWarnings("all") interface OptionalDef {
    public OptionalDef withOptionalVal1(final String optionalVal1);
    public OptionalDef withOptionalVal2(final java.lang.Long arg0);
    public OptionalDef withOptionalVal2(final java.util.Collection<? extends java.lang.Long> arg0);
    public OptionalDef withOptionalVal3(final java.lang.String arg0, final java.lang.Long arg1);
    public OptionalDef withOptionalVal3(final java.util.Map<? extends java.lang.String, ? extends java.lang.Long> arg0);
    public OptionalDef withData(final byte[] data);
    public BuilderPlain build();
  }
  private static final @java.lang.SuppressWarnings("all") class $Builder implements OptionalDef {
    private String optionalVal1 = $optionalVal1Default();
    private java.util.List<java.lang.Long> optionalVal2 = $optionalVal2Default();
    private Map<java.lang.String, java.lang.Long> optionalVal3 = $optionalVal3Default();
    private byte[] data;
    static String $optionalVal1Default() {
      return DEFAULT;
    }
    static java.util.List<java.lang.Long> $optionalVal2Default() {
      return new java.util.ArrayList<java.lang.Long>();
    }
    static Map<java.lang.String, java.lang.Long> $optionalVal3Default() {
      return new HashMap<java.lang.String, java.lang.Long>();
    }
    public OptionalDef withOptionalVal1(final String optionalVal1) {
      this.optionalVal1 = optionalVal1;
      return this;
    }
    public OptionalDef withOptionalVal2(final java.lang.Long arg0) {
      this.optionalVal2.add(arg0);
      return this;
    }
    public OptionalDef withOptionalVal2(final java.util.Collection<? extends java.lang.Long> arg0) {
      this.optionalVal2.addAll(arg0);
      return this;
    }
    public OptionalDef withOptionalVal3(final java.lang.String arg0, final java.lang.Long arg1) {
      this.optionalVal3.put(arg0, arg1);
      return this;
    }
    public OptionalDef withOptionalVal3(final java.util.Map<? extends java.lang.String, ? extends java.lang.Long> arg0) {
      this.optionalVal3.putAll(arg0);
      return this;
    }
    public OptionalDef withData(final byte[] data) {
      this.data = ((data != null) ? data.clone() : data);
      return this;
    }
    public BuilderPlain build() {
      return new BuilderPlain(this);
    }
    private $Builder() {
      super();
    }
  }
  public static final String DEFAULT = "default";
  public static final int IGNORE = 2;
  private String optionalVal1 = $Builder.$optionalVal1Default();
  private java.util.List<java.lang.Long> optionalVal2 = $Builder.$optionalVal2Default();
  private Map<java.lang.String, java.lang.Long> optionalVal3 = $Builder.$optionalVal3Default();
  private byte[] data;
  <clinit>() {
  }
  private @java.lang.SuppressWarnings("all") BuilderPlain(final $Builder builder) {
    super();
    this.optionalVal1 = builder.optionalVal1;
    this.optionalVal2 = builder.optionalVal2;
    this.optionalVal3 = builder.optionalVal3;
    this.data = builder.data;
  }
  public static @java.lang.SuppressWarnings("all") OptionalDef builderPlain() {
    return new $Builder();
  }
}