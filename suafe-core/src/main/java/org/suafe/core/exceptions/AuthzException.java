package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzResourceKeyIF;
import org.suafe.core.utilities.AuthzResources;

/**
 * Generic authz exception.
 * 
 * @since 2.0
 */
public abstract class AuthzException extends Exception {
    private static final long serialVersionUID = 1657007960837193130L;

    /**
     * Constructor that accepts exception message text.
     * 
     * @param messageKey Resource key for message text
     */
    public AuthzException(final AuthzResourceKeyIF messageKey) {
        super(AuthzResources.getString(messageKey));
    }
}
