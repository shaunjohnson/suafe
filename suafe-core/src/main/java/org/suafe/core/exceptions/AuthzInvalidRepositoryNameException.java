package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Invalid repository exception.
 * 
 * @since 2.0
 */
public class AuthzInvalidRepositoryNameException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = -3054546921304701329L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzInvalidRepositoryNameException() {
        super(AuthzErrorResourceKey.INVALID_REPOSITORY_NAME);
    }
}
