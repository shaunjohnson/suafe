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
package org.suafe.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.AuthzGroupIF;
import org.suafe.core.AuthzGroupMemberIF;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

/**
 * Authz group member object. Instances of this class or its subclasses are eligible to be a member of a group.
 * 
 * @since 2.0
 */
public abstract class AuthzGroupMember implements AuthzGroupMemberIF {
	/** Logger handle. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzGroupMember.class);

	/** Serialization ID. */
	private static final long serialVersionUID = -4348242302006857451L;

	/** Collection of groups of which is a member. */
	private final List<AuthzGroupIF> groups = new ArrayList<AuthzGroupIF>();

	/** Name of this user */
	protected final String name;

	/**
	 * Constructor.
	 * 
	 * @param name User name
	 * @param alias User alias
	 */
	protected AuthzGroupMember(final String name) {
		super();

		Preconditions.checkNotNull(name, "Name is null");

		this.name = name;
	}

	/**
	 * Adds group to collection of groups.
	 * 
	 * @param group Group to add to collection
	 * @return True if group added
	 * @throws AuthzAlreadyMemberOfGroupException If this object is already a member of the group
	 */
	protected final boolean addGroup(final AuthzGroupIF group) throws AuthzAlreadyMemberOfGroupException {
		LOGGER.debug("addGroup() entered. group={}", group);

		if (group == null) {
			LOGGER.error("addGroup() group is null");

			throw new NullPointerException("Group is null");
		}

		if (groups.contains(group)) {
			LOGGER.error("addGroup() already a member of group");

			throw new AuthzAlreadyMemberOfGroupException();
		}

		if (groups.add(group)) {
			Collections.sort(groups);

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Compares this object with the provided AuthzGroupMember object.
	 * 
	 * @param that AuthzGroupMember to compare
	 * @return Returns 0 if members are equal, less than 0 if this member is less than the other or greater than 0 if
	 *         this repository is greater
	 */
	@Override
	public int compareTo(final AuthzGroupMemberIF that) {
		return ComparisonChain.start().compare(this.name, that.getName()).result();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzGroupMemberIF#getGroups()
	 */
	@Override
	public final Collection<AuthzGroupIF> getGroups() {
		return Collections.unmodifiableCollection(groups);
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzGroupMemberIF#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Remove a group from the collection of groups.
	 * 
	 * @param group Group to remove from the collection
	 * @return True if group is removed
	 * @throws AuthzNotMemberOfGroupException If this object is not a member of the provided group
	 */
	protected final boolean removeGroup(final AuthzGroupIF group) throws AuthzNotMemberOfGroupException {
		LOGGER.debug("removeGroup() entered. group={}", group);

		if (group == null) {
			LOGGER.error("removeGroup() group is null");

			throw new NullPointerException("Group is null");
		}

		if (!groups.contains(group)) {
			LOGGER.error("removeGroup() this object is not a member of the group");

			throw new AuthzNotMemberOfGroupException();
		}

		return groups.remove(group);
	}
}
