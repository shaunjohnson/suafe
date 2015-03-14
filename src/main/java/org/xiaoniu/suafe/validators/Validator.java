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
package org.xiaoniu.suafe.validators;

import org.xiaoniu.suafe.api.SubversionConstants;
import org.xiaoniu.suafe.exceptions.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class containing application data validators.
 *
 * @author Shaun Johnson
 */
public final class Validator {

    /**
     * Prevent instantiation.
     */
    private Validator() {
        super();
    }

    /**
     * Validates group names.
     *
     * @param groupName The Group name to validate.
     * @throws ValidatorException
     */
    public static void validateGroupName(String groupName) throws ValidatorException {
        if (groupName == null) {
            throw new ValidatorException("application.error.groupinvalid");
        }

        String value = groupName.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.groupinvalid");
        }

        // Check group name for invalid characters
        Pattern pattern = Pattern.compile("=");
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.groupinvalidcharacters");
        }
    }

    /**
     * Validates the level of access.
     *
     * @param level Level of access to validate.
     * @throws ValidatorException
     */
    public static void validateLevelOfAccess(String level) throws ValidatorException {
        if (level == null ||
                (
                        !level.equals(SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS) &&
                                !level.equals(SubversionConstants.SVN_ACCESS_LEVEL_READONLY) &&
                                !level.equals(SubversionConstants.SVN_ACCESS_LEVEL_READWRITE)
                )
                ) {
            throw new ValidatorException("application.error.levelinvalid");
        }
    }

    /**
     * Validates relative paths.
     *
     * @param relativePath The relative path name to validate.
     * @throws ValidatorException
     */
//	public static void validateRelativePath(String relativePath) throws ValidatorException {
//		if (relativePath == null) {
//			throw new ValidatorException("Invalid path");
//		}
//		
//		String value = relativePath.trim();
//			
//		if (value.length() == 0) {
//			throw new ValidatorException("Invalid path");
//		}
//	}

    /**
     * Validates repository names.
     *
     * @param repositoryName The Repository name to validate.
     * @throws ValidatorException
     */
    public static void validateRepositoryName(String repositoryName) throws ValidatorException {
        if (repositoryName == null) {
            throw new ValidatorException("application.error.repositoryinvalid");
        }

        String value = repositoryName.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.repositoryinvalid");
        }

        // Check repository name for invalid characters
        Pattern pattern = Pattern.compile(":");
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.repositoryinvalidcharacters");
        }
    }

    /**
     * Validates aliases.
     *
     * @param alias The User alias to validate.
     * @throws ValidatorException
     */
    public static void validateAlias(String alias) throws ValidatorException {
        if (alias == null) {
            throw new ValidatorException("application.error.aliasinvalid");
        }

        String value = alias.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.aliasinvalid");
        }

        // Check user name for invalid characters
        Pattern pattern = Pattern.compile("=");
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.aliasinvalidcharacters");
        }
    }

    /**
     * Validates user names.
     *
     * @param userName The User name to validate.
     * @throws ValidatorException
     */
    public static void validateUserName(String userName) throws ValidatorException {
        if (userName == null) {
            throw new ValidatorException("application.error.userinvalid");
        }

        String value = userName.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.userinvalid");
        }

        // Check user name for invalid characters
        Pattern pattern = Pattern.compile("=");
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.userinvalidcharacters");
        }
    }

    /**
     * Validates that the value is not null and not an empty String.
     *
     * @param value The value to validate.
     * @throws ValidatorException
     */
    public static void validateNotEmptyString(String field, String value) throws ValidatorException {
        if (value == null || value.trim().length() == 0) {
            throw new ValidatorException("application.error.fieldrequired", field);
        }
    }

    /**
     * Validates that the provided value for the field is not null.
     *
     * @param fieldName Name of field being validated
     * @param value     Field value
     * @throws ValidatorException
     */
    public static void validateNotNull(String fieldName, Object value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException("validation.error.fieldrequired", fieldName);
        }
    }

    /**
     * @param path
     * @throws ValidatorException
     */
    public static void validatePath(String path) throws ValidatorException {
        if (path == null) {
            throw new ValidatorException("application.error.pathinvalid");
        }

        String value = path.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.pathinvalid");
        }

        // Check path for invalid characters
        Pattern pattern = Pattern.compile("=");
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.pathinvalidcharacters");
        }

        if (value.charAt(0) != '/') {
            throw new ValidatorException("application.error.pathinvalid.startslash");
        }

        if (value.length() > 1 && value.charAt(value.length() - 1) == '/') {
            throw new ValidatorException("application.error.pathinvalid.endslash");
        }
    }
}
