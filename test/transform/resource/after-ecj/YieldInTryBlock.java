import static lombok.Yield.yield;
import java.lang.Iterable;
class YieldTryBlock {
  YieldTryBlock() {
    super();
  }
  public @java.lang.SuppressWarnings("all") Iterable<String> test() {
    final class $YielderTest implements java.util.Iterator<String>, java.lang.Iterable<String>, java.io.Closeable {
      private boolean b;
      private RuntimeException e;
      private java.lang.Throwable $yieldException1;
      private int $state1;
      private int $state;
      private boolean $hasNext;
      private boolean $nextDefined;
      private String $next;
      private $YielderTest() {
        super();
      }
      public @java.lang.Override java.util.Iterator<String> iterator() {
        if (($state == 0))
            {
              $state = 1;
              return this;
            }
        return new $YielderTest();
      }
      public @java.lang.Override boolean hasNext() {
        if ((! $nextDefined))
            {
              $hasNext = getNext();
              $nextDefined = true;
            }
        return $hasNext;
      }
      public @java.lang.Override String next() {
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
        do
          switch ($state) {
          case 5 : ;
              $state1 = 6;
              $state = 5;
              continue ;
          default : ;
              $state = 6;
              return ;
          }
while (getNext());
      }
      private boolean getNext() {
        java.lang.Throwable $yieldException;
        while (true)          {
            try 
              {
                switch ($state) {
                case 0 : ;
                    $state = 1;
                case 1 : ;
                    b = true;
                case 2 : ;
                    $yieldException1 = null;
                    $state1 = 2;
                    $state = 3;
                case 3 : ;
                    if (b)
                        {
                          throw new RuntimeException();
                        }
                    $next = "bar";
                    $state = 5;
                    return true;
                case 4 : ;
                    $next = "foo";
                    $state = 5;
                    return true;
                case 5 : ;
                    {
                      b = (! b);
                    }
                    if (($yieldException1 != null))
                        {
                          $yieldException = $yieldException1;
                          break ;
                        }
                    $state = $state1;
                    continue ;
                case 6 : ;
                default : ;
                    return false;
                }
              }
            catch (final java.lang.Throwable $yieldExceptionCaught)               {
                $yieldException = $yieldExceptionCaught;
              }
            switch ($state) {
            case 3 : ;
                if (($yieldException instanceof RuntimeException))
                    {
                      e = (RuntimeException) $yieldException;
                      $state = 4;
                      continue ;
                    }
            case 4 : ;
                $yieldException1 = $yieldException;
                $state = 5;
                continue ;
            default : ;
                $state = 6;
                java.util.ConcurrentModificationException $yieldExceptionUnhandled = new java.util.ConcurrentModificationException();
                $yieldExceptionUnhandled.initCause($yieldException);
                throw $yieldExceptionUnhandled;
            }
          }
      }
    }
    return new $YielderTest();
  }
}