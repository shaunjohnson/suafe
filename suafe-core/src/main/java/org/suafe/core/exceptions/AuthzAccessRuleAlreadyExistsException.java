package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Access rule already exists exception.
 * 
 * @since 2.0
 */
public class AuthzAccessRuleAlreadyExistsException extends AuthzException {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4158583329289976731L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzAccessRuleAlreadyExistsException() {
        super(AuthzErrorResourceKey.ACCESS_RULE_ALREADY_EXISTS);
    }
}
