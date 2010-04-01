package org.suafe.core;

import java.io.Serializable;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;

/**
 * Authz group member object. Instances of this class or its subclasses are eligible to be a member of a group.
 * 
 * @since 2.0
 */
public abstract class AuthzGroupMember implements Serializable {
	private static final long serialVersionUID = -4348242302006857451L;

	private final Vector<AuthzGroup> groups = new Vector<AuthzGroup>();

	private final Logger logger = LoggerFactory.getLogger(AuthzGroupMember.class);

	/**
	 * Adds group to collection of groups.
	 * 
	 * @param group Group to add to collection
	 * @return True if group added
	 * @throws AuthzAlreadyMemberOfGroupException If this object is already a member of the group
	 */
	public boolean addGroup(final AuthzGroup group) throws AuthzAlreadyMemberOfGroupException {
		logger.debug("addGroup() entered. group={}", group);

		if (groups.contains(group)) {
			logger.error("addGroup() already a member of group");

			throw new AuthzAlreadyMemberOfGroupException();
		}

		return groups.add(group);
	}

	public boolean removeGroup(final AuthzGroup group) {
		return groups.remove(group);
	}
}