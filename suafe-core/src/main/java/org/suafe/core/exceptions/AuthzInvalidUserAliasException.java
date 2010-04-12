package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Invalid user alias exception.
 * 
 * @since 2.0
 */
public class AuthzInvalidUserAliasException extends AuthzException {
    private static final long serialVersionUID = -6049137782831395610L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzInvalidUserAliasException() {
        super(AuthzErrorResourceKey.INVALID_USER_ALIAS);
    }
}
