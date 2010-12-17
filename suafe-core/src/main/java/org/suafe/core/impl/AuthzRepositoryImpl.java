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
import org.suafe.core.AuthzRepository;

/**
 * Authz repository object implementation.
 * 
 * @since 2.0
 */
public final class AuthzRepositoryImpl extends AuthzAbstractNamedImpl implements AuthzRepository {
	/** Serialization ID. */
	private static final long serialVersionUID = 1252145167842473309L;

	/**
	 * Constructor.
	 * 
	 * @param name User name
	 */
	protected AuthzRepositoryImpl(final String name) {
		super(name);
	}

	/**
	 * Compares this object with the provided AuthzRepository object for equality.
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
		final AuthzRepositoryImpl other = (AuthzRepositoryImpl) object;
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

	/**
	 * Calculates hashCode value of this repository.
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
	 * Creates a string representation of this repository.
	 * 
	 * @return String representation of this repository
	 */
	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("name", getName());

		return toStringBuilder.toString();
	}
}
