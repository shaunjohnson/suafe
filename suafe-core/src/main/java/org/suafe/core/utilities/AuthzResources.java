package org.suafe.core.utilities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Authz resource bundle interface
 * 
 * @since 2.0
 */
public final class AuthzResources {
    /**
     * Load string from resource bundle using provided key.
     * 
     * @param messageKey Resource bundle key
     * @return String value for the provided key
     */
    public static String getString(final AuthzResourceKeyIF messageKey) {
        try {
            final ResourceBundle resourceBundle = ResourceBundle
                    .getBundle("org.suafe.core.nl.suafe-core-resources");
            return resourceBundle.getString(messageKey.toString());
        }
        catch (final MissingResourceException e) {
            // TODO
        }

        return null;
    }

    /**
     * Private constructor
     */
    private AuthzResources() {
        super();
    }
}
