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
