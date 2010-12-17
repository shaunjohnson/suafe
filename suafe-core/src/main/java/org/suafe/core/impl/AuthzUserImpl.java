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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.suafe.core.AuthzUser;

/**
 * Authz user object implementation.
 * 
 * @since 2.0
 */
public final class AuthzUserImpl extends AuthzGroupMemberImpl implements AuthzUser {
	/** Serialization ID. */
	private static final long serialVersionUID = 7672296029756141807L;

	/** Alias of this user. */
	private String alias;

	/**
	 * Constructor.
	 * 
	 * @param name User name
	 * @param alias User alias
	 */
	protected AuthzUserImpl(final String name, final String alias) {
		super(name);

		this.alias = alias;
	}

	/**
	 * Compares this object with the provided AuthzUser object for equality.
	 * 
	 * @param object Object to compare
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
		final AuthzUserImpl other = (AuthzUserImpl) object;
		if (alias == null) {
			if (other.alias != null) {
				return false;
			}
		}
		else if (!alias.equals(other.alias)) {
			return false;
		}
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
	 * @see org.suafe.core.impl.AuthzUserIF#getAlias()
	 */
	@Override
	public String getAlias() {
		return alias;
	}

	/**
	 * Calculates hashCode value of this user.
	 * 
	 * @return Hashcode of this object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alias == null ? 0 : alias.hashCode());
		result = prime * result + (getName() == null ? 0 : getName().hashCode());
		return result;
	}

	/**
	 * Sets the alias.
	 */
	protected void setAlias(final String alias) {
		this.alias = alias;
	}

	/**
	 * Creates a string representation of this user.
	 * 
	 * @return String representation of this user
	 */
	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("name", getName()).append("alias", alias);

		return toStringBuilder.toString();
	}
}
