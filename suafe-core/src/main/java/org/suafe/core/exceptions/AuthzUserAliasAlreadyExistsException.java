package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * User alias already exists exception.
 * 
 * @since 2.0
 */
public final class AuthzUserAliasAlreadyExistsException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = -5682770671359094033L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzUserAliasAlreadyExistsException() {
        super(AuthzErrorResourceKey.USER_ALIAS_ALREADY_EXISTS);
    }
}
