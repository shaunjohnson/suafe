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
package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Invalid user exception.
 * 
 * @since 2.0
 */
public final class AuthzInvalidUserNameException extends AuthzException {
	/** Serialization ID. */
	private static final long serialVersionUID = -1997616239658102044L;

	/**
	 * Create exception with message text loaded using messageKey.
	 */
	public AuthzInvalidUserNameException() {
		super(AuthzErrorResourceKey.INVALID_USER_NAME);
	}
}
