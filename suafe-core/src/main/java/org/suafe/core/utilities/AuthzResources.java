/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.suafe.core.utilities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.impl.AuthzDocumentImpl;

import com.google.common.base.Preconditions;

/**
 * Authz resource bundle interface.
 * 
 * @since 2.0
 */
public final class AuthzResources {
	/** Logger handle. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzDocumentImpl.class);

	/** Resource bundle. */
	private static final ResourceBundle RESOURCE_BUNDLE;

	/**
	 * Gets the Suafe resource bundle.
	 * 
	 * @return ResourceBundle object
	 */
	static {
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = ResourceBundle.getBundle("org.suafe.core.nl.suafe-core-resources");
		}
		catch (final MissingResourceException e) {
			LOGGER.error("Unable to load resource bundle: {}", e.getMessage());
		}

		RESOURCE_BUNDLE = resourceBundle;
	}

	/**
	 * Load string from resource bundle using provided key.
	 * 
	 * @param messageKey Resource bundle key
	 * @return String value for the provided key
	 */
	public static String getString(final AuthzResourceKeyIF messageKey) {
		Preconditions.checkNotNull(messageKey, "Message key is null");

		try {
			return RESOURCE_BUNDLE.getString(messageKey.toString());
		}
		catch (final MissingResourceException e) {
			LOGGER.error("getString(AuthzResourceKeyIF)" + " Unable to locate string with key {}",
					messageKey.toString());
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
