package org.suafe.core;

import org.suafe.core.constants.AuthzAccessLevelIF;

public interface AuthzAccessRuleIF extends Comparable<AuthzAccessRuleIF> {

	/**
	 * Gets the access level.
	 * 
	 * @return the access level
	 */
	AuthzAccessLevelIF getAccessLevel();

	/**
	 * Gets the group.
	 * 
	 * @return the group
	 */
	AuthzGroupIF getGroup();

	/**
	 * Gets the path.
	 * 
	 * @return the path
	 */
	AuthzPathIF getPath();

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	AuthzUserIF getUser();
}
