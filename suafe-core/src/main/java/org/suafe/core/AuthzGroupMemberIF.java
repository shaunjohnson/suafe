package org.suafe.core;

import java.io.Serializable;
import java.util.Collection;

public interface AuthzGroupMemberIF extends Comparable<AuthzGroupMemberIF>, Serializable {

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
