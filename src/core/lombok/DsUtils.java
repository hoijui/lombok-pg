package lombok;

public class DsUtils {

	public static Class uncheckedForName(final String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
