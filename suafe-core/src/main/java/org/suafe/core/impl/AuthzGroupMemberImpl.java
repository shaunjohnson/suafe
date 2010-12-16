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
import org.suafe.core.AuthzAccessRule;
import org.suafe.core.AuthzGroup;
import org.suafe.core.AuthzGroupMember;
import org.suafe.core.exceptions.AuthzAccessRuleAlreadyAppliedException;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;

import com.google.common.base.Preconditions;

/**
 * Authz group member object implementation. Instances of this class or its subclasses are eligible to be a member of a
 * group.
 * 
 * @since 2.0
 */
public abstract class AuthzGroupMemberImpl extends AuthzAbstractNamedImpl implements AuthzGroupMember {
	/** Logger handle. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzGroupMemberImpl.class);

	/** Serialization ID. */
	private static final long serialVersionUID = -4348242302006857451L;

	/** Collection of access rules that apply to this member. */
	private final List<AuthzAccessRule> accessRules = new ArrayList<AuthzAccessRule>();

	/** Collection of groups of which is a member. */
	private final List<AuthzGroup> groups = new ArrayList<AuthzGroup>();

	/**
	 * Constructor.
	 * 
	 * @param name User name
	 */
	protected AuthzGroupMemberImpl(final String name) {
		super(name);
	}

	/**
	 * Adds access rule to collection access rules.
	 * 
	 * @param accessRule Access rule to add to collection
	 * @return True if access rule added
	 * @throws AuthzAccessRuleAlreadyAppliedException If the access rule is already applied to the member
	 */
	protected boolean addAccessRule(final AuthzAccessRule accessRule) throws AuthzAccessRuleAlreadyAppliedException {
		LOGGER.debug("addAccessRule() entered. accessRule={}", accessRule);

		Preconditions.checkNotNull(accessRule, "Access Rule is null");

		if (accessRules.contains(accessRule)) {
			LOGGER.error("addAccessRule() already a member of group");

			throw new AuthzAccessRuleAlreadyAppliedException();
		}

		if (accessRules.add(accessRule)) {
			Collections.sort(accessRules);

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds group to collection of groups.
	 * 
	 * @param group Group to add to collection
	 * @return True if group added
	 * @throws AuthzAlreadyMemberOfGroupException If this object is already a member of the group
	 */
	protected final boolean addGroup(final AuthzGroup group) throws AuthzAlreadyMemberOfGroupException {
		LOGGER.debug("addGroup() entered. group={}", group);

		Preconditions.checkNotNull(group, "Group is null");

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

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzGroupMemberIF#getAccessRules()
	 */
	@Override
	public final Collection<AuthzAccessRule> getAccessRules() {
		return Collections.unmodifiableCollection(accessRules);
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzGroupMemberIF#getGroups()
	 */
	@Override
	public final Collection<AuthzGroup> getGroups() {
		return Collections.unmodifiableCollection(groups);
	}

	/**
	 * Remove a group from the collection of groups.
	 * 
	 * @param group Group to remove from the collection
	 * @return True if group is removed
	 * @throws AuthzNotMemberOfGroupException If this object is not a member of the provided group
	 */
	protected final boolean removeGroup(final AuthzGroup group) throws AuthzNotMemberOfGroupException {
		LOGGER.debug("removeGroup() entered. group={}", group);

		Preconditions.checkNotNull(group, "Group is null");

		if (!groups.contains(group)) {
			LOGGER.error("removeGroup() this object is not a member of the group");

			throw new AuthzNotMemberOfGroupException();
		}

		return groups.remove(group);
	}
}
