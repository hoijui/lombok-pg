import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BuilderGeneric<K extends Comparable<K>, V extends List<K>> {
	private final String foo;
	private final Map<K, V> bar = $Builder.$barDefault();
	
	@java.lang.SuppressWarnings("all")
	private BuilderGeneric(final $Builder<K, V> builder) {
		this.foo = builder.foo;
		this.bar.putAll(builder.bar);
	}
	
	@java.lang.SuppressWarnings("all")
	public static <K extends Comparable<K>, V extends List<K>> FooDef<K, V> builderGeneric() {
		return new $Builder<K, V>();
	}
	
	@java.lang.SuppressWarnings("all")
	public interface FooDef<K extends Comparable<K>, V extends List<K>> {
		
		OptionalDef<K, V> foo(final String foo);
		
		@java.lang.SuppressWarnings("all")
		OptionalDef<K, V> foo(final Class<?> clazz);
	}
	
	@java.lang.SuppressWarnings("all")
	public interface OptionalDef<K extends Comparable<K>, V extends List<K>> {
		
		OptionalDef<K, V> bar(final K arg0, final V arg1);
		
		OptionalDef<K, V> bar(final java.util.Map<? extends K, ? extends V> arg0);
		
		BuilderGeneric<K, V> build();
	}
	
	@java.lang.SuppressWarnings("all")
	private static final class $Builder<K extends Comparable<K>, V extends List<K>> implements FooDef<K, V>, OptionalDef<K, V> {
		private String foo;
		private Map<K, V> bar = $barDefault();

		static <K extends Comparable<K>, V extends List<K>> Map<K, V> $barDefault() {
			return new HashMap<K, V>();
		}
		
		public OptionalDef<K, V> foo(final String foo) {
			this.foo = foo;
			return this;
		}
		
		public OptionalDef<K, V> bar(final K arg0, final V arg1) {
			this.bar.put(arg0, arg1);
			return this;
		}
		
		public OptionalDef<K, V> bar(final java.util.Map<? extends K, ? extends V> arg0) {
			this.bar.putAll(arg0);
			return this;
		}
		
		public BuilderGeneric<K, V> build() {
			return new BuilderGeneric<K, V>(this);
		}
		
		private $Builder() {
		}
		
		@java.lang.SuppressWarnings("all")
		public OptionalDef<K, V> foo(final Class<?> clazz) {
			this.foo = clazz.getSimpleName();
			return this;
		}
	}
}
class DomainContainer<D> {
	private final int a;
	private final D domain;
	
	@java.lang.SuppressWarnings("all")
	private DomainContainer(final $Builder<D> builder) {
		this.a = builder.a;
		this.domain = builder.domain;
	}
	
	@java.lang.SuppressWarnings("all")
	public static <D> ADef<D> domainContainer() {
		return new $Builder<D>();
	}
	
	@java.lang.SuppressWarnings("all")
	public interface ADef<D> {
		
		DomainDef<D> a(final int a);
	}
	
	@java.lang.SuppressWarnings("all")
	public interface DomainDef<D> {
		
		OptionalDef<D> domain(final D domain);
	}
	
	@java.lang.SuppressWarnings("all")
	public interface OptionalDef<D> {
		
		DomainContainer<D> build();
	}
	
	@java.lang.SuppressWarnings("all")
	private static final class $Builder<D> implements ADef<D>, DomainDef<D>, OptionalDef<D> {
		private int a;
		private D domain;
		
		public DomainDef<D> a(final int a) {
			this.a = a;
			return this;
		}
		
		public OptionalDef<D> domain(final D domain) {
			this.domain = domain;
			return this;
		}
		
		public DomainContainer<D> build() {
			return new DomainContainer<D>(this);
		}
		
		private $Builder() {
		}
	}
}