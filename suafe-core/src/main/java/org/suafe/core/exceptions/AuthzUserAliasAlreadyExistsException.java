package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * User alias already exists exception.
 * 
 * @since 2.0
 */
public class AuthzUserAliasAlreadyExistsException extends AuthzException {
    private static final long serialVersionUID = -5682770671359094033L;

    /**
     * Create exception with message text loaded using messageKey
     * 
     * @param messageKey Message key for the message test
     */
    public AuthzUserAliasAlreadyExistsException() {
        super(AuthzErrorResourceKey.USER_ALIAS_ALREADY_EXISTS);
    }
}
