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

import org.suafe.core.enums.AuthzAccessLevel;

/**
 * The Interface AuthzAccessRule.
 */
public interface AuthzAccessRule extends Comparable<AuthzAccessRule>, Serializable {

	/**
	 * Gets the access level.
	 * 
	 * @return the access level
	 */
	AuthzAccessLevel getAccessLevel();

	/**
	 * Gets the path.
	 * 
	 * @return the path
	 */
	AuthzPath getPath();

	/**
	 * Gets the permissionable.
	 * 
	 * @return the permissionable
	 */
	AuthzPermissionable getPermissionable();
}
