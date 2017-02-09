/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.xiaoniu.suafe.validators;

import org.xiaoniu.suafe.api.SubversionConstants;
import org.xiaoniu.suafe.exceptions.ValidatorException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class containing application data validators.
 *
 * @author Shaun Johnson
 */
public final class Validator {
    /**
     * Validates group names.
     *
     * @param groupName The Group name to validate.
     * @throws ValidatorException if group name is invalid
     */
    public static void validateGroupName(@Nullable final String groupName) throws ValidatorException {
        if (groupName == null) {
            throw new ValidatorException("application.error.groupinvalid");
        }

        final String value = groupName.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.groupinvalid");
        }

        // Check group name for invalid characters
        final Pattern pattern = Pattern.compile("=");
        final Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.groupinvalidcharacters");
        }
    }

    /**
     * Validates the level of access.
     *
     * @param level Level of access to validate.
     * @throws ValidatorException if level of access is invalid
     */
    public static void validateLevelOfAccess(@Nullable final String level) throws ValidatorException {
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
     * @throws ValidatorException if relative path is invalid
     */
//	public static void validateRelativePath(@Nullable final String relativePath) throws ValidatorException {
//		if (relativePath == null) {
//			throw new ValidatorException("Invalid path");
//		}
//		
//		final String value = relativePath.trim();
//			
//		if (value.length() == 0) {
//			throw new ValidatorException("Invalid path");
//		}
//	}

    /**
     * Validates repository names.
     *
     * @param repositoryName The Repository name to validate.
     * @throws ValidatorException if repository name is invalid
     */
    public static void validateRepositoryName(@Nullable final String repositoryName) throws ValidatorException {
        if (repositoryName == null) {
            throw new ValidatorException("application.error.repositoryinvalid");
        }

        final String value = repositoryName.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.repositoryinvalid");
        }

        // Check repository name for invalid characters
        final Pattern pattern = Pattern.compile(":");
        final Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.repositoryinvalidcharacters");
        }
    }

    /**
     * Validates aliases.
     *
     * @param alias The User alias to validate.
     * @throws ValidatorException if alias is invalid
     */
    public static void validateAlias(@Nullable final String alias) throws ValidatorException {
        if (alias == null) {
            throw new ValidatorException("application.error.aliasinvalid");
        }

        final String value = alias.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.aliasinvalid");
        }

        // Check user name for invalid characters
        final Pattern pattern = Pattern.compile("=");
        final Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.aliasinvalidcharacters");
        }
    }

    /**
     * Validates user names.
     *
     * @param userName The User name to validate.
     * @throws ValidatorException if user name is invalid
     */
    public static void validateUserName(@Nullable final String userName) throws ValidatorException {
        if (userName == null) {
            throw new ValidatorException("application.error.userinvalid");
        }

        final String value = userName.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.userinvalid");
        }

        // Check user name for invalid characters
        final Pattern pattern = Pattern.compile("=");
        final Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            throw new ValidatorException("application.error.userinvalidcharacters");
        }
    }

    /**
     * Validates that the value is not null and not an empty String.
     *
     * @param value The value to validate.
     * @throws ValidatorException if string value is blank
     */
    public static void validateNotEmptyString(@Nonnull final String field, @Nullable final String value)
            throws ValidatorException {
        if (value == null || value.trim().length() == 0) {
            throw new ValidatorException("application.error.fieldrequired", field);
        }
    }

    /**
     * Validates that the provided value for the field is not null.
     *
     * @param fieldName Name of field being validated
     * @param value     Field value
     * @throws ValidatorException if value is null
     */
    public static void validateNotNull(@Nonnull final String fieldName, @Nullable final Object value)
            throws ValidatorException {
        if (value == null) {
            throw new ValidatorException("validation.error.fieldrequired", fieldName);
        }
    }

    /**
     * @param path
     * @throws ValidatorException
     */
    public static void validatePath(@Nullable final String path) throws ValidatorException {
        if (path == null) {
            throw new ValidatorException("application.error.pathinvalid");
        }

        final String value = path.trim();

        if (value.length() == 0) {
            throw new ValidatorException("application.error.pathinvalid");
        }

        // Check path for invalid characters
        final Pattern pattern = Pattern.compile("=");
        final Matcher matcher = pattern.matcher(value);

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

    /**
     * Prevent instantiation.
     */
    private Validator() {
        // Deliberately left blank
    }
}
