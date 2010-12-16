package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Group already exists exception.
 * 
 * @since 2.0
 */
public final class AuthzGroupAlreadyExistsException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = 805914537115462506L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzGroupAlreadyExistsException() {
        super(AuthzErrorResourceKey.GROUP_ALREADY_EXISTS);
    }
}
