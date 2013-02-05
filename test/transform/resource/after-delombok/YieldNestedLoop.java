import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class YieldNestedLoop<T, K, V> {
	private Map<T, Map<K, V>> map = new HashMap<T, Map<K, V>>();
	
	@java.lang.SuppressWarnings("all")
	public Iterable<V> values() {
		
		final class $YielderValues implements java.util.Iterator<V>, java.lang.Iterable<V>, java.io.Closeable {
			private Map.Entry<T, Map<K, V>> entry;
			private Map.Entry<K, V> subEntry;
			@java.lang.SuppressWarnings("all")
			private java.util.Iterator<Map.Entry<T, Map<K, V>>> $entryIter;
			@java.lang.SuppressWarnings("all")
			private java.util.Iterator<Map.Entry<K, V>> $subEntryIter;
			private int $state;
			private boolean $hasNext;
			private boolean $nextDefined;
			private V $next;
			
			private $YielderValues() {
			}

			@java.lang.Override
			public java.util.Iterator<V> iterator() {
				if ($state == 0) {
					$state = 1;
					return this;
				}
				return new $YielderValues();
			}

			@java.lang.Override
			public boolean hasNext() {
				if (!$nextDefined) {
					$hasNext = getNext();
					$nextDefined = true;
				}
				return $hasNext;
			}

			@java.lang.Override
			public V next() {
				if (!hasNext()) {
					throw new java.util.NoSuchElementException();
				}
				$nextDefined = false;
				return $next;
			}

			@java.lang.Override
			public void remove() {
				throw new java.lang.UnsupportedOperationException();
			}

			@java.lang.Override
			public void close() {
				$state = 4;
			}
			
			private boolean getNext() {
				while (true) switch ($state) {
				case 0: 
					$state = 1;
				case 1: 
					$entryIter = map.entrySet().iterator();
				case 2: 
					if (!$entryIter.hasNext()) {
						$state = 4;
						continue;
					}
					entry = $entryIter.next();
					$subEntryIter = entry.getValue().entrySet().iterator();
				case 3: 
					if (!$subEntryIter.hasNext()) {
						$state = 2;
						continue;
					}
					subEntry = $subEntryIter.next();
					$next = subEntry.getValue();
					$state = 3;
					return true;
				case 4: 
				default: 
					return false;
				
				}
			}
		}
		return new $YielderValues();
	}
}