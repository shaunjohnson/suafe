package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Path already exists exception.
 * 
 * @since 2.0
 */
public class AuthzPathAlreadyExistsException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = 6140476864243483019L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzPathAlreadyExistsException() {
        super(AuthzErrorResourceKey.PATH_ALREADY_EXISTS);
    }
}
