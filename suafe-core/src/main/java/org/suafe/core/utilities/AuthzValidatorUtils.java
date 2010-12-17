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

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AuthzValidatorUtils.
 */
public final class AuthzValidatorUtils {

	/** Logger handle. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzValidatorUtils.class);

	/** Regular expression pattern for matching valid path values. */
	private static final Pattern VALID_PATH_PATTERN = Pattern.compile("^(/)|(/.*[^/])$");

	/**
	 * Checks group name for validity.
	 * 
	 * @param name Group name to check
	 * @return True if group name is valid, otherwise false
	 */
	public static boolean isValidGroupName(final String name) {
		LOGGER.debug("isValidGroupName() entered. name=\"{}\"", name);

		final boolean isValidGroupName = StringUtils.isNotBlank(name);

		LOGGER.debug("isValidGroupName() exited, returning {}", isValidGroupName);

		return isValidGroupName;
	}

	/**
	 * Checks path for validity. Paths must start with a slash (/), but must not end with a slash (/), except for when
	 * path consists of a single slash (/).
	 * 
	 * @param path Path to validate
	 * @return True if path is valid, otherwise false
	 */
	public static boolean isValidPath(final String path) {
		LOGGER.debug("isValidPath() entered. path=\"{}\"", path);

		final boolean isValidPath = VALID_PATH_PATTERN.matcher(StringUtils.trimToEmpty(path)).matches();

		LOGGER.debug("isValidPath() exited. returning {}", isValidPath);

		return isValidPath;
	}

	/**
	 * Checks repository name for validity.
	 * 
	 * @param name Repository name to check
	 * @return True if repository name is valid, otherwise false
	 */
	public static boolean isValidRepositoryName(final String name) {
		LOGGER.debug("isValidRepositoryName() entered. name=\"{}\"", name);

		final boolean isValidRepositoryName = StringUtils.isNotBlank(name);

		LOGGER.debug("isValidRepositoryName() exited, returning {}", isValidRepositoryName);

		return isValidRepositoryName;
	}

	/**
	 * Checks user alias for validity.
	 * 
	 * @param alias User alias to check
	 * @return True if user alias is valid, otherwise false
	 */
	public static boolean isValidUserAlias(final String alias) {
		LOGGER.debug("isValidUserAlias() entered. alias=\"{}\"", alias);

		final boolean isValidUserAlias = StringUtils.isNotBlank(alias);

		LOGGER.debug("isValidUserAlias() exited, returning {}", isValidUserAlias);

		return isValidUserAlias;
	}

	/**
	 * Checks user name for validity.
	 * 
	 * @param name User name to check
	 * @return True if user name is valid, otherwise false
	 */
	public static boolean isValidUserName(final String name) {
		LOGGER.debug("isValidUserName() entered. name=\"{}\"", name);

		final boolean isValidUserName = StringUtils.isNotBlank(name);

		LOGGER.debug("isValidUserName() exited, returning {}", isValidUserName);

		return isValidUserName;
	}

	/**
	 * Instantiates a new authz validator utils.
	 */
	private AuthzValidatorUtils() {
		throw new AssertionError();
	}
}
