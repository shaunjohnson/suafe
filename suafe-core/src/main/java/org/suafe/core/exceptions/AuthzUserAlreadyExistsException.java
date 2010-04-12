package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * User already exists exception.
 * 
 * @since 2.0
 */
public class AuthzUserAlreadyExistsException extends AuthzException {
    private static final long serialVersionUID = 1654342842804875089L;

    /**
     * Create exception with message text loaded using messageKey
     * 
     * @param messageKey Message key for the message test
     */
    public AuthzUserAlreadyExistsException() {
        super(AuthzErrorResourceKey.USER_ALREADY_EXISTS);
    }
}
