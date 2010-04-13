package org.suafe.core.utilities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.AuthzDocument;

/**
 * Authz resource bundle interface.
 * 
 * @since 2.0
 */
public final class AuthzResources {
    /** Logger handle. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AuthzDocument.class);

    /** Resource bundle. */
    private final static ResourceBundle resourceBundle;

    /**
     * Gets the Suafe resource bundle.
     * 
     * @return ResourceBundle object
     */
    static {
        ResourceBundle tempResourceBundle = null;

        try {
            tempResourceBundle = ResourceBundle
                    .getBundle("org.suafe.core.nl.suafe-core-resources");
        }
        catch (final MissingResourceException e) {
            LOGGER.error("Unable to load resource bundle: {}", e.getMessage());
        }

        resourceBundle = tempResourceBundle;
    }

    /**
     * Load string from resource bundle using provided key.
     * 
     * @param messageKey Resource bundle key
     * @return String value for the provided key
     */
    public static String getString(final AuthzResourceKeyIF messageKey) {
        if (messageKey == null) {
            LOGGER.error("getString(AuthzResourceKeyIF) messageKey is null");

            throw new NullPointerException("messageKey is null");
        }

        try {
            return resourceBundle.getString(messageKey.toString());
        }
        catch (final MissingResourceException e) {
            LOGGER.error("getString(AuthzResourceKeyIF)"
                    + " Unable to locate string with key {}", messageKey
                    .toString());
        }

        return null;
    }

    /**
     * Private constructor.
     */
    private AuthzResources() {
        super();
    }
}
