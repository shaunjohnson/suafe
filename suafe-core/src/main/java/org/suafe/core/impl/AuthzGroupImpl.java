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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.AuthzGroup;
import org.suafe.core.AuthzGroupMember;
import org.suafe.core.AuthzUser;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Authz group object implementation.
 * 
 * @since 2.0
 */
public final class AuthzGroupImpl extends AuthzGroupMemberImpl implements AuthzGroup {
	/** Logger handle. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzGroupImpl.class);

	/** Serialization ID. */
	private static final long serialVersionUID = 7033919638521713150L;

	/** The groups. */
	private final List<AuthzGroup> groups = new ArrayList<AuthzGroup>();

	/** The users. */
	private final List<AuthzUser> users = new ArrayList<AuthzUser>();

	/**
	 * Constructor.
	 * 
	 * @param name User name
	 */
	protected AuthzGroupImpl(final String name) {
		super(name);
	}

	/**
	 * Adds a group member.
	 * 
	 * @param group AuthzGroup member to add
	 * @return True if member added
	 * @throws AuthzGroupMemberAlreadyExistsException If group member already exists
	 */
	protected boolean addMember(final AuthzGroup group) throws AuthzGroupMemberAlreadyExistsException {
		assert groups != null;

		LOGGER.debug("addMember(AuthzGroup) entered. group={}", group);

		Preconditions.checkNotNull(group, "Group is null");

		if (groups.contains(group)) {
			LOGGER.error("addMember(AuthzGroup) group member already exists");

			throw new AuthzGroupMemberAlreadyExistsException();
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
	 * Adds a member.
	 * 
	 * @param member AuthzGroupMember member to add
	 * @return True if member added
	 * @throws AuthzGroupMemberAlreadyExistsException If user member already exists
	 */
	protected boolean addMember(final AuthzGroupMember member) throws AuthzGroupMemberAlreadyExistsException {
		if (member instanceof AuthzGroup) {
			return addMember((AuthzGroup) member);
		}
		else if (member instanceof AuthzUser) {
			return addMember((AuthzUser) member);
		}
		else {
			return false;
		}
	}

	/**
	 * Adds a user member.
	 * 
	 * @param user AuthzUser member to add
	 * @return True if member added
	 * @throws AuthzGroupMemberAlreadyExistsException If user member already exists
	 */
	protected boolean addMember(final AuthzUser user) throws AuthzGroupMemberAlreadyExistsException {
		assert users != null;

		LOGGER.debug("addMember(AuthzUser) entered. user={}", user);

		Preconditions.checkNotNull(user, "User is null");

		if (users.contains(user)) {
			LOGGER.error("addMember(AuthzUser) user member already exists");

			throw new AuthzGroupMemberAlreadyExistsException();
		}

		if (users.add(user)) {
			Collections.sort(users);

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Compares this object with the provided AuthzGroup object for equality.
	 * 
	 * @param object Object to compare
	 * @return True if this object matches the provided object, otherwise false
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		final AuthzGroupImpl other = (AuthzGroupImpl) object;
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		}
		else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzGroup#getGroupMembers()
	 */
	@Override
	public List<AuthzGroup> getGroupMembers() {
		assert groups != null;

		return new ImmutableList.Builder<AuthzGroup>().addAll(groups).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzGroupIF#getMembers()
	 */
	@Override
	public List<AuthzGroupMember> getMembers() {
		assert groups != null;
		assert users != null;

		final Collection<AuthzGroupMember> members = new ArrayList<AuthzGroupMember>(groups.size() + users.size());

		members.addAll(groups);
		members.addAll(users);

		return new ImmutableList.Builder<AuthzGroupMember>().addAll(members).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzGroup#getUserMembers()
	 */
	@Override
	public List<AuthzUser> getUserMembers() {
		assert users != null;

		return new ImmutableList.Builder<AuthzUser>().addAll(users).build();
	}

	/**
	 * Calculates hashCode value of this group.
	 * 
	 * @return Hashcode of this object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getName() == null ? 0 : getName().hashCode());
		return result;
	}

	/**
	 * Remove group member.
	 * 
	 * @param group Member group to remove
	 * @return True if member removed
	 * @throws AuthzNotGroupMemberException If provided member object is not a member of this group.
	 */
	protected boolean removeMember(final AuthzGroup group) throws AuthzNotGroupMemberException {
		assert groups != null;

		LOGGER.debug("removeMember(AuthzGroup) entered. group={}", group);

		Preconditions.checkNotNull(group, "Group is null");

		if (!groups.contains(group)) {
			LOGGER.error("removeMember(AuthzGroup) group is not a member of this group");

			throw new AuthzNotGroupMemberException();
		}

		return groups.remove(group);
	}

	/**
	 * Removes a member.
	 * 
	 * @param member AuthzGroupMember member to remove
	 * @return True if member removed
	 * @throws AuthzNotGroupMemberException If user member already exists
	 */
	protected boolean removeMember(final AuthzGroupMember member) throws AuthzNotGroupMemberException {
		if (member instanceof AuthzGroup) {
			return removeMember((AuthzGroup) member);
		}
		else if (member instanceof AuthzUser) {
			return removeMember((AuthzUser) member);
		}
		else {
			return false;
		}
	}

	/**
	 * Remove user member.
	 * 
	 * @param user Member user to remove
	 * @return True if member removed
	 * @throws AuthzNotGroupMemberException If provided member object is not a member of this group.
	 */
	protected boolean removeMember(final AuthzUser user) throws AuthzNotGroupMemberException {
		assert users != null;

		LOGGER.debug("removeMember(AuthzUser) entered. user={}", user);

		Preconditions.checkNotNull(user, "User is null");

		if (!users.contains(user)) {
			LOGGER.error("removeMember(AuthzUser) user is not a member of this group");

			throw new AuthzNotGroupMemberException();
		}

		return users.remove(user);
	}

	/**
	 * Creates a string representation of this group.
	 * 
	 * @return String representation of this group
	 */
	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("name", getName());

		return toStringBuilder.toString();
	}
}
