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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.exceptions.ValidatorException;

/**
 * Utility class containing application data validators.
 * 
 * @author Shaun Johnson
 */
public class Validator {

	/**
	 * Validates group names.
	 * 
	 * @param groupName The Group name to validate.
	 * @throws ValidatorException
	 */
	public static void validateGroupName(String groupName) throws ValidatorException {
		if (groupName == null) {
			throw new ValidatorException("Invalid group name: Name is null");
		}
		
		String value = groupName.trim();
			
		if (value.length() == 0) {
			throw new ValidatorException("Invalid group name: Name too short");
		}
		
		// Check group name for invalid characters
		Pattern pattern = Pattern.compile("=");
		Matcher matcher = pattern.matcher(value);
		
		if (matcher.find()) {
			throw new ValidatorException("Invalid group name: Name may not contain '='");
		}
	}
	
	/**
	 * Validates the level of access.
	 * 
	 * @param level Level of access to validate.
	 * @throws ValidatorException
	 */
	public static void validateLevelOfAccess(String level) throws ValidatorException {
		if (	level == null ||
				(
					!level.equals(Constants.ACCESS_LEVEL_DENY_ACCESS) &&
					!level.equals(Constants.ACCESS_LEVEL_READONLY) &&
					!level.equals(Constants.ACCESS_LEVEL_READWRITE)
				)
			) {
			throw new ValidatorException("Invalid level of access");
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
			throw new ValidatorException("Invalid repository name");
		}
		
		String value = repositoryName.trim();
			
		if (value.length() == 0) {
			throw new ValidatorException("Invalid repository name");
		}
		
		// Check repository name for invalid characters
		Pattern pattern = Pattern.compile("=");
		Matcher matcher = pattern.matcher(value);
		
		if (matcher.find()) {
			throw new ValidatorException("Invalid repository name: Name may not contain '='");
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
			throw new ValidatorException("Invalid user name");
		}
		
		String value = userName.trim();
			
		if (value.length() == 0) {
			throw new ValidatorException("Invalid user name");
		}
		
		// Check user name for invalid characters
		Pattern pattern = Pattern.compile("=");
		Matcher matcher = pattern.matcher(value);
		
		if (matcher.find()) {
			throw new ValidatorException("Invalid user name: Name may not contain '='");
		}
	}

	/**
	 * Validates that the value is not null and not an empty String.
	 * 
	 * @param value The value to validate.
	 * @throws ValidatorException
	 */
	public static void validateNotEmptyString(String value) throws ValidatorException {
		if (value == null || value.trim().length() == 0) {
			throw new ValidatorException("Value is required");
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
			throw new ValidatorException("Field \"" + field + "\" is required");
		}
	}

	/**
	 * @param field
	 * @param value
	 * @throws ValidatorException
	 */
	public static void validateNotNull(String field, Object value) throws ValidatorException {
		if (value == null) {
			throw new ValidatorException("Field \"" + field + "\" is required");
		}
	}

	/**
	 * @param path
	 * @throws ValidatorException
	 */
	public static void validatePath(String path) throws ValidatorException {
		if (path == null) {
			throw new ValidatorException("Invalid path");
		}
		
		String value = path.trim();
			
		if (value.length() == 0) {
			throw new ValidatorException("Invalid path");
		}
		
		// Check path for invalid characters
		Pattern pattern = Pattern.compile("=");
		Matcher matcher = pattern.matcher(value);
		
		if (matcher.find()) {
			throw new ValidatorException("Invalid path: Path may not contain '='");
		}		
		
		if (value.charAt(0) != '/') {
			throw new ValidatorException("Invalid path: Path must start with '/'");
		}
		
		if (value.length() > 1 && value.charAt(value.length() - 1) == '/') {
			throw new ValidatorException("Invalid path: Path may not end with '/'");
		}
	}
}
