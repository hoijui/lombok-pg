package lombok;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creates an inline com.doctusoft.common.core.bean.Attribute implementation that relies on invoking the propiate getter and setter.
 * The generated code compiles with GWT, becuase it doesn't rely on reflection through apache-common-beanutils 
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface PropertyAttribute {

}
