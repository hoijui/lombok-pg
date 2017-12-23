class BuilderCustomConstructor {
	private final String name;
	private final String surname;
	
	private BuilderCustomConstructor(final $Builder builder) {
		this.name = builder.name.trim();
		this.surname = builder.surname.trim();
	}
	
	@java.lang.SuppressWarnings("all")
	public static NameDef builderCustomConstructor() {
		return new $Builder();
	}
	
	@java.lang.SuppressWarnings("all")
	public interface NameDef {
		
		SurnameDef name(final String name);
	}
	
	@java.lang.SuppressWarnings("all")
	public interface SurnameDef {
		
		OptionalDef surname(final String surname);
	}
	
	@java.lang.SuppressWarnings("all")
	public interface OptionalDef {
		
		BuilderCustomConstructor build();
	}
	
	@java.lang.SuppressWarnings("all")
	private static final class $Builder implements NameDef, SurnameDef, OptionalDef {
		private String name;
		private String surname;
		
		public SurnameDef name(final String name) {
			this.name = name;
			return this;
		}
		
		public OptionalDef surname(final String surname) {
			this.surname = surname;
			return this;
		}
		
		public BuilderCustomConstructor build() {
			return new BuilderCustomConstructor(this);
		}
		
		private $Builder() {
		}
	}
}