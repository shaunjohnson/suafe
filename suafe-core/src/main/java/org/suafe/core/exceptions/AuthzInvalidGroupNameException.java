package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Invalid group exception.
 * 
 * @since 2.0
 */
public class AuthzInvalidGroupNameException extends AuthzException {
	private static final long serialVersionUID = 5730475688736835597L;

	/**
	 * Create exception with message text loaded using messageKey.
	 */
	public AuthzInvalidGroupNameException() {
		super(AuthzErrorResourceKey.INVALID_GROUP_NAME);
	}
}
