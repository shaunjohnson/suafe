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
 * Access level enumeration.
 * 
 * @since 2.0
 */
public enum AuthzAccessLevel {

	/** The Constant DENY_ACCESS. */
	DENY_ACCESS(""),

	/** The Constant READ_ONLY. */
	READ_ONLY("r"),

	/** The Constant READ_WRITE. */
	READ_WRITE("rw");

	/**
	 * Gets AuthzAccessLevel by access level code.
	 * 
	 * @param code access level code of enum to get
	 * @return Enum with provided code
	 */
	public static AuthzAccessLevel getAccessLevelByCode(final String code) {
		if (code.equals(DENY_ACCESS.accessLevelCode)) {
			return DENY_ACCESS;
		}
		else if (code.equals(READ_ONLY.accessLevelCode)) {
			return READ_ONLY;
		}
		else if (code.equals(READ_WRITE.accessLevelCode)) {
			return READ_WRITE;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	/** The access level code. */
	private final String accessLevelCode;

	/**
	 * Private constructor used to create access level enumeration values.
	 * 
	 * @param accessLevelCode Access level value code
	 */
	private AuthzAccessLevel(final String accessLevelCode) {
		this.accessLevelCode = accessLevelCode;
	}

	/**
	 * Gets the access level code.
	 * 
	 * @return the access level code
	 */
	public String getAccessLevelCode() {
		return accessLevelCode;
	}

	/**
	 * Creates a string representation of this access level.
	 * 
	 * @return String representation of this access level
	 */
	@Override
	public String toString() {
		return accessLevelCode;
	}
}
