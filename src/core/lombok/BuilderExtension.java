package lombok;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * Use this on methods in a {@link Builder @Builder}-annotated class to specify extensions for the generated
 * builder. To decide if an extension initializes required fields or not, {@link BuilderExtension @BuilderExtension}
 * will look at the method name an read the fields like this:
 * <pre>
 * withFullnameAndAge(final String fullname, final int age)
 * 
 * ([&lt;PREFIX&gt;]&lt;FIELD_NAME1&gt;And&lt;FIELD_NAME2&gt;And..&lt;FIELD_NAMEN&gt;())
 * </pre>
 * </p>
 * <p>
 * To allow different method names, for example if you want to prevent ambiguous method signatures, you need to
 * specify the affected fields like this:
 * <pre>
 * &#64;Builder(fields={"fullname", "age"});
 * private void customMethodName(final String fullname, final int age)
 * </pre>
 * </p>
 * <p>
 * <b>Note:</b> For this to work, the methods annotated by {@link BuilderExtension @BuilderExtension}, need to be
 * private and must return void. And if you want to initialize a required field value in you own extension, you need
 * to set all other required initializes values too.
 * </p>
 */
@Target(METHOD)
@Retention(SOURCE)
public @interface BuilderExtension {
	/**
	 * <p>List of fields that are affected by this extension.</p>
	 * <p>This list is used to determine whether a extension initializes all required fields or not.</p>
	 * <p>If no list is provided the method name is analyzed.</p>
	 */
	public String[] fields() default {};
}
