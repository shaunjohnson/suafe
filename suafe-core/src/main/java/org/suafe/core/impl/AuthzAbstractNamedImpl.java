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

import org.suafe.core.AuthzNamed;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

/**
 * The Class AuthzAbstractNamedImpl.
 */
public abstract class AuthzAbstractNamedImpl implements AuthzNamed {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1113521141460005479L;

	/** Name of this user. */
	private final String name;

	/**
	 * Constructor.
	 * 
	 * @param name User name
	 */
	protected AuthzAbstractNamedImpl(final String name) {
		super();

		Preconditions.checkNotNull(name, "Name is null");

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public final int compareTo(final AuthzNamed that) {
		return ComparisonChain.start().compare(this.name, that.getName()).result();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzGroupMemberIF#getName()
	 */
	@Override
	public final String getName() {
		return name;
	}
}
