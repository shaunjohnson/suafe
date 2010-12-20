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

import java.util.List;

/**
 * The Interface AuthzGroup.
 */
public interface AuthzGroup extends AuthzGroupMember {

	/**
	 * Returns an immutable list of AuthzGroup objects.
	 * 
	 * @return Immutable list of AuthzGroup objects
	 */
	List<AuthzGroup> getGroupMembers();

	/**
	 * Returns an immutable list of AuthzGroupMember objects.
	 * 
	 * @return Immutable list of AuthzGroupMember objects
	 */
	List<AuthzGroupMember> getMembers();

	/**
	 * Returns an immutable list of AuthzUser objects.
	 * 
	 * @return Immutable list of AuthzUser objects
	 */
	List<AuthzUser> getUserMembers();
}
