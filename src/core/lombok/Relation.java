package lombok;


/**
 * Base class for relations.
 * @author Jason Blackwell
 *
 * @param <E> The load option type
 */
class Relation<E extends Enum<E>> {
	private E fetchOption;
	
	public Relation() {}
	
	public Relation(E fetchOption) {
		this.fetchOption = fetchOption;
	}
	
	public E getFetchOption() {
		return fetchOption;
	}
}
