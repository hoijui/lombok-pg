import static lombok.Yield.yield;
import java.lang.Iterable;
import java.util.List;
import lombok.Functions.Function1;
import lombok.val;
class YieldAndVal {
  YieldAndVal() {
    super();
  }
  public static @java.lang.SuppressWarnings("all") <S, T>Iterable<T> needsMoreVal(final Iterable<S> values, final Function1<S, List<T>> selector) {
    final class $YielderNeedsMoreVal implements java.util.Iterator<T>, java.lang.Iterable<T>, java.io.Closeable {
      private S item;
      private java.util.List<T> subItems;
      private T subItem;
      private @java.lang.SuppressWarnings("all") java.util.Iterator<S> $itemIter;
      private @java.lang.SuppressWarnings("all") java.util.Iterator<T> $subItemIter;
      private int $state;
      private boolean $hasNext;
      private boolean $nextDefined;
      private T $next;
      private $YielderNeedsMoreVal() {
        super();
      }
      public @java.lang.Override java.util.Iterator<T> iterator() {
        if (($state == 0))
            {
              $state = 1;
              return this;
            }
        return new $YielderNeedsMoreVal();
      }
      public @java.lang.Override boolean hasNext() {
        if ((! $nextDefined))
            {
              $hasNext = getNext();
              $nextDefined = true;
            }
        return $hasNext;
      }
      public @java.lang.Override T next() {
        if ((! hasNext()))
            {
              throw new java.util.NoSuchElementException();
            }
        $nextDefined = false;
        return $next;
      }
      public @java.lang.Override void remove() {
        throw new java.lang.UnsupportedOperationException();
      }
      public @java.lang.Override void close() {
        $state = 4;
      }
      private @java.lang.SuppressWarnings("all") boolean getNext() {
        while (true)          switch ($state) {
          case 0 : ;
              $state = 1;
          case 1 : ;
              $itemIter = values.iterator();
          case 2 : ;
              if ((! $itemIter.hasNext()))
                  {
                    $state = 4;
                    continue ;
                  }
              item = $itemIter.next();
              subItems = selector.apply(item);
              if ((! (subItems != null)))
                  {
                    $state = 2;
                    continue ;
                  }
              $subItemIter = subItems.iterator();
          case 3 : ;
              if ((! $subItemIter.hasNext()))
                  {
                    $state = 2;
                    continue ;
                  }
              subItem = $subItemIter.next();
              $next = subItem;
              $state = 3;
              return true;
          case 4 : ;
          default : ;
              return false;
          }
      }
    }
    return new $YielderNeedsMoreVal();
  }
}