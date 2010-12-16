package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * User already exists exception.
 * 
 * @since 2.0
 */
public final class AuthzUserAlreadyExistsException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = 1654342842804875089L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzUserAlreadyExistsException() {
        super(AuthzErrorResourceKey.USER_ALREADY_EXISTS);
    }
}
