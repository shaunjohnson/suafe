package org.suafe.core;

import java.util.Collection;

public interface AuthzGroupMemberIF extends Comparable<AuthzGroupMemberIF> {

	/**
	 * Returns an immutable collection of AuthzGroup objects.
	 * 
	 * @return Immutable collection of AuthzGroup object.
	 */
	Collection<AuthzGroupIF> getGroups();

	/**
	 * Gets the user name.
	 * 
	 * @return User name
	 */
	String getName();
}
