package org.suafe.core;

import java.util.Collection;

/**
 * Object that may have permissions associated with it.
 * 
 * @since 2.0
 */
public interface AuthzPermissionable extends AuthzNamed {
	/**
	 * Returns an immutable collection of AuthzAccessRule objects.
	 * 
	 * @return Immutable collection of AuthzAccessRule object.
	 */
	Collection<AuthzAccessRule> getAccessRules();
}
