/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.suafe.core;

import java.io.Serializable;

import org.suafe.core.constants.AuthzAccessLevelIF;

/**
 * The Interface AuthzAccessRuleIF.
 */
public interface AuthzAccessRuleIF extends Comparable<AuthzAccessRuleIF>, Serializable {

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
