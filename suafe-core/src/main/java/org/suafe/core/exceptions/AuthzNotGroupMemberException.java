package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Not a group member exception.
 * 
 * @since 2.0
 */
public class AuthzNotGroupMemberException extends AuthzException {
	private static final long serialVersionUID = 1593261396944136358L;

	/**
	 * Create exception with message text loaded using messageKey
	 * 
	 * @param messageKey Message key for the message test
	 */
	public AuthzNotGroupMemberException() {
		super(AuthzErrorResourceKey.NOT_GROUP_MEMBER);
	}
}
