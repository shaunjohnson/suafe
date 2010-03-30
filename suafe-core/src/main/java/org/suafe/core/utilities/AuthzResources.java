package org.suafe.core.utilities;

/**
 * Authz resource bundle interface
 * 
 * @since 2.0
 */
public class AuthzResources {
	/**
	 * Load string from resource bundle using provided key.
	 * 
	 * @param messageKey Resource bundle key
	 * @return String value for the provided key
	 */
	public static String getString(final AuthzResourceKeyIF messageKey) {
		return messageKey.toString();
	}
}
