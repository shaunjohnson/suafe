package org.xiaoniu.suafe.validators;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.exceptions.ValidatorException;

/**
 * Home to all validator helper methods.
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
			throw new ValidatorException("Invalid group name");
		}
		
		String value = groupName.trim();
			
		if (value.length() == 0) {
			throw new ValidatorException("Invalid group name");
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
	public static void validateRelativePath(String relativePath) throws ValidatorException {
		if (relativePath == null) {
			throw new ValidatorException("Invalid path");
		}
		
		String value = relativePath.trim();
			
		if (value.length() == 0) {
			throw new ValidatorException("Invalid path");
		}
	}
	
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
	 * @param string
	 * @param group
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
	}
}
