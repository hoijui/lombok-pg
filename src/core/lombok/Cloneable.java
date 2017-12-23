package lombok;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Cloneable {	
	/**
	 * Any fields listed here will not be printed in the generated {@code clone} implementation.
	 * Mutually exclusive with {@link #of()}.
	 */
	String[] exclude() default {};
	
	/**
	 * If present, explicitly lists the fields that are to be printed.
	 * Normally, all non-static fields are printed.
	 * <p>
	 * Mutually exclusive with {@link #exclude()}.
	 */
	String[] of() default {};
}
