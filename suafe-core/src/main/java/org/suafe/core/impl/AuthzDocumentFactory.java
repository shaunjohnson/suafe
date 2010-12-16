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

import org.suafe.core.AuthzDocumentFactoryIF;
import org.suafe.core.AuthzDocumentIF;

/**
 * A factory for creating AuthzDocument objects.
 */
public final class AuthzDocumentFactory implements AuthzDocumentFactoryIF {

	/** The Constant INSTANCE. */
	private static final AuthzDocumentFactoryIF INSTANCE = new AuthzDocumentFactory();

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6403023091737261902L;

	/**
	 * Gets the single instance of AuthzDocumentFactory.
	 * 
	 * @return single instance of AuthzDocumentFactory
	 */
	public static AuthzDocumentFactoryIF getInstance() {
		return INSTANCE;
	}

	/**
	 * Instantiates a new authz document factory.
	 */
	private AuthzDocumentFactory() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentFactoryIF#create()
	 */
	@Override
	public AuthzDocumentIF create() {
		return new AuthzDocument();
	}
}
