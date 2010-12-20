package org.suafe.core;

import java.util.List;

/**
 * Object that may have permissions associated with it.
 * 
 * @since 2.0
 */
public interface AuthzPermissionable extends AuthzNamed {
	/**
	 * Returns an immutable list of AuthzAccessRule objects.
	 * 
	 * @return Immutable list of AuthzAccessRule object.
	 */
	List<AuthzAccessRule> getAccessRules();
}
