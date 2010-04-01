package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Not member of group exception.
 * 
 * @since 2.0
 */
public class AuthzNotMemberOfGroupException extends AuthzException {
	private static final long serialVersionUID = -5972142980829711323L;

	/**
	 * Create exception with message text loaded using messageKey
	 * 
	 * @param messageKey Message key for the message test
	 */
	public AuthzNotMemberOfGroupException() {
		super(AuthzErrorResourceKey.NOT_MEMBER_OF_GROUP);
	}
}
