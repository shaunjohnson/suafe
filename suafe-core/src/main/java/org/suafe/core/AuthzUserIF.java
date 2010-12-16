package org.suafe.core;

public interface AuthzUserIF extends AuthzGroupMemberIF {

	/**
	 * Gets the user alias.
	 * 
	 * @return User alias
	 */
	String getAlias();
}
