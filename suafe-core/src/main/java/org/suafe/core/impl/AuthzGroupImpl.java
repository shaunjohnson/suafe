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
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;

import com.google.common.base.Preconditions;

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

	/** Collection of members. */
	private final List<AuthzGroupMember> members = new ArrayList<AuthzGroupMember>();

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
	 * @param member AuthzGroupMember member to add
	 * @return True if member added
	 * @throws AuthzGroupMemberAlreadyExistsException If group member already exists
	 */
	protected boolean addMember(final AuthzGroupMember member) throws AuthzGroupMemberAlreadyExistsException {
		LOGGER.debug("addMember() entered. member={}", member);

		Preconditions.checkNotNull(member, "Member is null");

		if (members.contains(member)) {
			LOGGER.error("addMember() group member already exists");

			throw new AuthzGroupMemberAlreadyExistsException();
		}

		if (members.add(member)) {
			Collections.sort(members);

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
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzGroupIF#getMembers()
	 */
	@Override
	public Collection<AuthzGroupMember> getMembers() {
		return Collections.unmodifiableCollection(members);
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
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Remove group member.
	 * 
	 * @param member Member to remove
	 * @return True if member removed
	 * @throws AuthzNotGroupMemberException If provided member object is not a member of this group.
	 */
	protected boolean removeMember(final AuthzGroupMember member) throws AuthzNotGroupMemberException {
		LOGGER.debug("removeMember() entered. member={}", member);

		Preconditions.checkNotNull(member, "Member is null");

		if (!members.contains(member)) {
			LOGGER.error("removeMember() member is not a member of this group");

			throw new AuthzNotGroupMemberException();
		}

		return members.remove(member);
	}

	/**
	 * Creates a string representation of this group.
	 * 
	 * @return String representation of this group
	 */
	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("name", name);

		return toStringBuilder.toString();
	}
}
