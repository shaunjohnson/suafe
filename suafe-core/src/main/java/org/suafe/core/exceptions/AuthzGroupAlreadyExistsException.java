package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Group already exists exception.
 * 
 * @since 2.0
 */
public class AuthzGroupAlreadyExistsException extends AuthzException {
	private static final long serialVersionUID = 805914537115462506L;

	/**
	 * Create exception with message text loaded using messageKey
	 * 
	 * @param messageKey Message key for the message test
	 */
	public AuthzGroupAlreadyExistsException() {
		super(AuthzErrorResourceKey.GROUP_ALREADY_EXISTS);
	}
}
