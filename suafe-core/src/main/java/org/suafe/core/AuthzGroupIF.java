package org.suafe.core;

import java.util.Collection;

public interface AuthzGroupIF extends AuthzGroupMemberIF {

	/**
	 * Returns an immutable collection of AuthzGroupMember objects.
	 * 
	 * @return Immutable collection of AuthzGroupMember objects
	 */
	Collection<AuthzGroupMemberIF> getMembers();
}
