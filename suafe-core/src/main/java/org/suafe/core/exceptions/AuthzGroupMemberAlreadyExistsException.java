package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Group member already exists exception.
 * 
 * @since 2.0
 */
public final class AuthzGroupMemberAlreadyExistsException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = 3059355374678547921L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzGroupMemberAlreadyExistsException() {
        super(AuthzErrorResourceKey.GROUP_MEMBER_ALREADY_EXISTS);
    }
}
