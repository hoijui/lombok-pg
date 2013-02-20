import java.util.Iterator;
import java.lang.Iterable;

class YieldPlain {
	
	@java.lang.SuppressWarnings("all")
	public Iterator<String> simple() {
		
		final class $YielderSimple implements java.util.Iterator<java.lang.String>, java.io.Closeable {
			private int $state;
			private boolean $hasNext;
			private boolean $nextDefined;
			private java.lang.String $next;
			
			private $YielderSimple() {
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
			public java.lang.String next() {
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
				$state = 2;
			}

			@java.lang.SuppressWarnings("all")
			private boolean getNext() {
				while (true) switch ($state) {
				case 0: 
					$state = 1;
				case 1: 
					$next = "A String";
					$state = 2;
					return true;
				case 2: 
				default: 
					return false;
				}
			}
		}
		return new $YielderSimple();
	}
	
	@java.lang.SuppressWarnings("all")
	public Iterator<Long> fib_while() {
		
		final class $YielderFibWhile implements java.util.Iterator<java.lang.Long>, java.io.Closeable {
			private long a;
			private long b;
			private long c;
			private int $state;
			private boolean $hasNext;
			private boolean $nextDefined;
			private java.lang.Long $next;
			
			private $YielderFibWhile() {
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
			public java.lang.Long next() {
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
				$state = 5;
			}

			@java.lang.SuppressWarnings("all")
			private boolean getNext() {
				while (true) switch ($state) {
				case 0: 
					$state = 1;
				case 1: 
					a = 0;
					b = 1;
				case 2: 
					$next = a;
					$state = 3;
					return true;
				case 3: 
					c = a + b;
					if (!(c < 0)) {
						$state = 4;
						continue;
					}
					$state = 5;
					continue;
				case 4: 
					a = b;
					b = c;
					$state = 2;
					continue;
				case 5: 
				default: 
					return false;
				}
			}
		}
		return new $YielderFibWhile();
	}
	
	@java.lang.SuppressWarnings("all")
	public Iterator<Long> fib_while_2() {
		
		final class $YielderFibWhile2 implements java.util.Iterator<java.lang.Long>, java.io.Closeable {
			private long a;
			private long b;
			private long c;
			private int $state;
			private boolean $hasNext;
			private boolean $nextDefined;
			private java.lang.Long $next;
			
			private $YielderFibWhile2() {
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
			public java.lang.Long next() {
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

			@java.lang.SuppressWarnings("all")
			private boolean getNext() {
				while (true) switch ($state) {
				case 0: 
					$state = 1;
				case 1: 
					a = 0;
					b = 1;
				case 2: 
					if (!(b >= 0)) {
						$state = 4;
						continue;
					}
					$next = a;
					$state = 3;
					return true;
				case 3: 
					c = a + b;
					a = b;
					b = c;
					$state = 2;
					continue;
				case 4: 
				default: 
					return false;
				}
			}
		}
		return new $YielderFibWhile2();
	}
	
	@java.lang.SuppressWarnings("all")
	public Iterable<Long> fib_for() {
		
		final class $YielderFibFor implements java.util.Iterator<java.lang.Long>, java.lang.Iterable<java.lang.Long>, java.io.Closeable {
			private long a;
			private long b;
			private long c;
			private int $state;
			private boolean $hasNext;
			private boolean $nextDefined;
			private java.lang.Long $next;
			
			private $YielderFibFor() {
			}

			@java.lang.Override
			public java.util.Iterator<java.lang.Long> iterator() {
				if ($state == 0) {
					$state = 1;
					return this;
				}
				return new $YielderFibFor();
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
			public java.lang.Long next() {
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

			@java.lang.SuppressWarnings("all")
			private boolean getNext() {
				while (true) switch ($state) {
				case 0: 
					$state = 1;
				case 1: 
					a = 0;
					b = 1;
				case 2: 
					if (!(b >= 0)) {
						$state = 4;
						continue;
					}
					$next = a;
					$state = 3;
					return true;
				case 3: 
					c = a + b;
					a = b;
					b = c;
					$state = 2;
					continue;
				case 4: 
				default: 
					return false;
				}
			}
		}
		return new $YielderFibFor();
	}
	
	@java.lang.SuppressWarnings("all")
	public Iterable<String> complex_foreach(final Iterable<Object> objects) {
		
		final class $YielderComplexForeach implements java.util.Iterator<java.lang.String>, java.lang.Iterable<java.lang.String>, java.io.Closeable {
			private Object object;
			private Class<?> c;
			@java.lang.SuppressWarnings("all")
			private java.util.Iterator<Object> $objectIter;
			private int $state;
			private boolean $hasNext;
			private boolean $nextDefined;
			private java.lang.String $next;
			
			private $YielderComplexForeach() {
			}

			@java.lang.Override
			public java.util.Iterator<java.lang.String> iterator() {
				if ($state == 0) {
					$state = 1;
					return this;
				}
				return new $YielderComplexForeach();
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
			public java.lang.String next() {
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
				$state = 6;
			}

			@java.lang.SuppressWarnings("all")
			private boolean getNext() {
				while (true) switch ($state) {
				case 0: 
					$state = 1;
				case 1: 
					$objectIter = objects.iterator();
				case 2: 
					if (!$objectIter.hasNext()) {
						$state = 5;
						continue;
					}
					object = $objectIter.next();
					if (!(object instanceof Class<?>)) {
						$state = 4;
						continue;
					}
					c = (Class<?>)object;
					$next = "A String";
					$state = 3;
					return true;
				case 3: 
					$next = c.getName();
					$state = 5;
					return true;
				case 4: 
					$next = object.toString();
					$state = 2;
					return true;
				case 5: 
					$next = "Another String";
					$state = 6;
					return true;
				case 6: 
				default: 
					return false;
				}
			}
		}
		return new $YielderComplexForeach();
	}
	
	@java.lang.SuppressWarnings("all")
	public Iterator<String> complex(final Iterator<Object> objects) {
		
		final class $YielderComplex implements java.util.Iterator<java.lang.String>, java.io.Closeable {
			private Object object;
			private Class<?> c;
			private int $state;
			private boolean $hasNext;
			private boolean $nextDefined;
			private java.lang.String $next;
			
			private $YielderComplex() {
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
			public java.lang.String next() {
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
				$state = 6;
			}

			@java.lang.SuppressWarnings("all")
			private boolean getNext() {
				while (true) switch ($state) {
				case 0: 
					$state = 1;
				case 1: 
					$next = "Another String";
					$state = 2;
					return true;
				case 2: 
					if (!(objects.hasNext())) {
						$state = 5;
						continue;
					}
					object = objects.next();
					if (!(object instanceof Class<?>)) {
						$state = 4;
						continue;
					}
					c = (Class<?>)object;
					$next = "A String";
					$state = 3;
					return true;
				case 3: 
					$next = c.getName();
					$state = 5;
					return true;
				case 4: 
					$next = object.toString();
					$state = 2;
					return true;
				case 5: 
					$next = "Another String";
					$state = 6;
					return true;
				case 6: 
				default: 
					return false;
				}
			}
		}
		return new $YielderComplex();
	}
}