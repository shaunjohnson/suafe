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
package org.suafe.core.enums;

/**
 * Resource key enumeration used for error messages.
 * 
 * @since 2.0
 */
public enum AuthzErrorResourceKey {

	/** The ACCESS_RULE_ALREADY_APPLIED. */
	ACCESS_RULE_ALREADY_APPLIED("errorAccessRuleAlreadyApplied"),

	/** The ACCESS_RULE_ALREADY_EXISTS. */
	ACCESS_RULE_ALREADY_EXISTS("errorAccessRuleAlreadyExists"),

	/** The ALREADY_MEMBER_OF_GROUP. */
	ALREADY_MEMBER_OF_GROUP("errorAlreadyMemberOfGroup"),

	/** The GROUP_ALREADY_EXISTS. */
	GROUP_ALREADY_EXISTS("errorGroupAlreadyExists"),

	/** The GROUP_MEMBER_ALREADY_EXISTS. */
	GROUP_MEMBER_ALREADY_EXISTS("errorGroupMemberAlreadyExists"),

	/** The INVALID_GROUP_NAME. */
	INVALID_GROUP_NAME("errorInvalidGroupName"),

	/** The INVALID_PATH. */
	INVALID_PATH("errorInvalidPath"),

	/** The INVALID_REPOSITORY_NAME. */
	INVALID_REPOSITORY_NAME("errorInvalidRepositoryName"),

	/** The INVALID_USER_ALIAS. */
	INVALID_USER_ALIAS("errorInvalidUserAlias"),

	/** The INVALID_USER_NAME. */
	INVALID_USER_NAME("errorInvalidUserName"),

	/** The NOT_GROUP_MEMBER. */
	NOT_GROUP_MEMBER("errorNotGroupMember"),

	/** The NOT_MEMBER_OF_GROUP. */
	NOT_MEMBER_OF_GROUP("errorNotMemberOfGroup"),

	/** The PATH_ALREADY_EXISTS. */
	PATH_ALREADY_EXISTS("errorPathAlreadyExists"),

	/** The REPOSITORY_ALREADY_EXISTS. */
	REPOSITORY_ALREADY_EXISTS("errorRepositoryAlreadyExists"),

	/** The USER_ALIAS_ALREADY_EXISTS. */
	USER_ALIAS_ALREADY_EXISTS("errorUserAliasAlreadyExists"),

	/** The USER_ALREADY_EXISTS. */
	USER_ALREADY_EXISTS("errorUserAlreadyExists");

	/** The key. */
	private final String key;

	/**
	 * Private constructor used to create enumeration values.
	 * 
	 * @param key Resource file key
	 */
	private AuthzErrorResourceKey(final String key) {
		this.key = key;
	}

	/**
	 * Creates a string representation of this resource key.
	 * 
	 * @return String representation of this resource key
	 */
	@Override
	public String toString() {
		return key;
	}
}
