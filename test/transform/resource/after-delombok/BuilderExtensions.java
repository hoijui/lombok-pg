class BuilderExtensions {
	private final String text;
	private final int id;
	private String optionalVal1;
	private java.util.List<java.lang.Long> optionalVal2;
	private long optionalVal3;
	
	@java.lang.SuppressWarnings("all")
	private BuilderExtensions(final $Builder builder) {
		this.text = builder.text;
		this.id = builder.id;
		this.optionalVal1 = builder.optionalVal1;
		this.optionalVal2 = builder.optionalVal2;
	}
	
	@java.lang.SuppressWarnings("all")
	public static TextDef builderExtensions() {
		return new $Builder();
	}
	
	@java.lang.SuppressWarnings("all")
	public interface TextDef {
		IdDef text(final String text);
		
		@java.lang.SuppressWarnings("all")
		OptionalDef idAndText(final int id, final String text);
		
		@java.lang.SuppressWarnings("all")
		OptionalDef idAsStringAndText(final String id, final String text);
	}
	
	@java.lang.SuppressWarnings("all")
	public interface IdDef {
		OptionalDef id(final int id);
	}
	
	@java.lang.SuppressWarnings("all")
	public interface OptionalDef {
		OptionalDef optionalVal1(final String optionalVal1);
		
		OptionalDef optionalVal2(final java.util.List<java.lang.Long> optionalVal2);
		
		BuilderExtensions build();
		
		@java.lang.SuppressWarnings("all")
		OptionalDef optionalVal1(final Class<?> clazz);
	}
	
	@java.lang.SuppressWarnings("all")
	private static final class $Builder implements TextDef, IdDef, OptionalDef {
		private String text;
		private int id;
		private String optionalVal1;
		private java.util.List<java.lang.Long> optionalVal2;
		
		public IdDef text(final String text) {
			this.text = text;
			return this;
		}
		
		public OptionalDef id(final int id) {
			this.id = id;
			return this;
		}
		
		public OptionalDef optionalVal1(final String optionalVal1) {
			this.optionalVal1 = optionalVal1;
			return this;
		}
		
		public OptionalDef optionalVal2(final java.util.List<java.lang.Long> optionalVal2) {
			this.optionalVal2 = optionalVal2;
			return this;
		}
		
		public BuilderExtensions build() {
			return new BuilderExtensions(this);
		}
		
		private $Builder() {
		}
		
		@java.lang.SuppressWarnings("all")
		public OptionalDef idAndText(final int id, final String text) {
			this.id = id;
			this.text = text;
			return this;
		}
		
		@java.lang.SuppressWarnings("all")
		public OptionalDef idAsStringAndText(final String id, final String text) {
			this.id = java.lang.Integer.valueOf(id);
			this.text = text;
			return this;
		}
		
		@java.lang.SuppressWarnings("all")
		public OptionalDef optionalVal1(final Class<?> clazz) {
			this.optionalVal1 = clazz.getSimpleName();
			return this;
		}
	}
}