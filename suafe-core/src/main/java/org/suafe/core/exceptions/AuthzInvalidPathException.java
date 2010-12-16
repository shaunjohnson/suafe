package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Invalid path exception.
 * 
 * @since 2.0
 */
public final class AuthzInvalidPathException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = 8571977492960412772L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzInvalidPathException() {
        super(AuthzErrorResourceKey.INVALID_PATH);
    }
}
