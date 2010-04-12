package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Already member of group exception.
 * 
 * @since 2.0
 */
public class AuthzAlreadyMemberOfGroupException extends AuthzException {
    private static final long serialVersionUID = 8656328413483715250L;

    /**
     * Create exception with message text loaded using messageKey
     * 
     * @param messageKey Message key for the message test
     */
    public AuthzAlreadyMemberOfGroupException() {
        super(AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP);
    }
}
