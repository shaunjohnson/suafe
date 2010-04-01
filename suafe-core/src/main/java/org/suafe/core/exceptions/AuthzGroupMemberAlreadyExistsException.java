package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Group member already exists exception.
 * 
 * @since 2.0
 */
public class AuthzGroupMemberAlreadyExistsException extends AuthzException {
	private static final long serialVersionUID = 3059355374678547921L;

	/**
	 * Create exception with message text loaded using messageKey
	 * 
	 * @param messageKey Message key for the message test
	 */
	public AuthzGroupMemberAlreadyExistsException() {
		super(AuthzErrorResourceKey.GROUP_MEMBER_ALREADY_EXISTS);
	}
}
