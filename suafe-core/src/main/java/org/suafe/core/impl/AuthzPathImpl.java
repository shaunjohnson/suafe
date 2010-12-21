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
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.AuthzAccessRule;
import org.suafe.core.AuthzPath;
import org.suafe.core.AuthzRepository;
import org.suafe.core.exceptions.AuthzAccessRuleAlreadyAppliedException;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

/**
 * Authz path object implementation.
 * 
 * @since 2.0
 */
public final class AuthzPathImpl implements AuthzPath {
	/** Logger handle. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzPathImpl.class);

	/** Serialization ID. */
	private static final long serialVersionUID = 9125579229041836584L;

	/** The access rules. */
	private final List<AuthzAccessRule> accessRules = new ArrayList<AuthzAccessRule>();

	/** Path string. */
	private final String path;

	/** Repository object. */
	private final AuthzRepository repository;

	/**
	 * Constructor.
	 * 
	 * @param repository Repository
	 * @param path Path
	 */
	protected AuthzPathImpl(final AuthzRepository repository, final String path) {
		super();

		Preconditions.checkNotNull(path);

		this.repository = repository;
		this.path = path;
	}

	/**
	 * Adds access rule to collection access rules.
	 * 
	 * @param accessRule Access rule to add to collection
	 * @return True if access rule added
	 * @throws AuthzAccessRuleAlreadyAppliedException If the access rule is already applied to the member
	 */
	protected final boolean addAccessRule(final AuthzAccessRule accessRule)
			throws AuthzAccessRuleAlreadyAppliedException {
		assert accessRules != null;

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
	 * Compares this object with the provided AuthzPath object.
	 * 
	 * @param that AuthzPath to compare
	 * @return Returns 0 if paths are equal, less than 0 if this path is less than the other or greater than 0 if this
	 *         path is greater
	 */
	@Override
	public int compareTo(final AuthzPath that) {
		return ComparisonChain.start().compare(this.repository, that.getRepository(), Ordering.natural().nullsLast())
				.compare(this.path, that.getPath()).result();
	}

	/**
	 * Compares this object with the provided AuthzPath object for equality.
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
		final AuthzPathImpl other = (AuthzPathImpl) object;
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		}
		else if (!path.equals(other.path)) {
			return false;
		}
		if (repository == null) {
			if (other.repository != null) {
				return false;
			}
		}
		else if (!repository.equals(other.repository)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzPath#getAccessRules()
	 */
	@Override
	public List<AuthzAccessRule> getAccessRules() {
		assert accessRules != null;

		Collections.unmodifiableCollection(accessRules);

		return new ImmutableList.Builder<AuthzAccessRule>().addAll(accessRules).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzPathIF#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzPathIF#getRepository()
	 */
	@Override
	public AuthzRepository getRepository() {
		return repository;
	}

	/**
	 * Calculates hashCode value of this path.
	 * 
	 * @return Hashcode of this object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (path == null ? 0 : path.hashCode());
		result = prime * result + (repository == null ? 0 : repository.hashCode());
		return result;
	}

	/**
	 * Removes an access rule from the collection of access rules.
	 * 
	 * @param accessRule Access rule to remove
	 * @return True if access rule removed
	 */
	protected final boolean removeAccessRule(final AuthzAccessRule accessRule) {
		assert accessRule != null;

		LOGGER.debug("addAccessRule() entered. accessRule={}", accessRule);

		final boolean removed = accessRules.remove(accessRule);

		LOGGER.debug("addAccessRule() exited, returning {}", removed);

		return removed;
	}

	/**
	 * Creates a string representation of this user.
	 * 
	 * @return String representation of this user
	 */
	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("repository", repository).append("path", path);

		return toStringBuilder.toString();
	}
}
