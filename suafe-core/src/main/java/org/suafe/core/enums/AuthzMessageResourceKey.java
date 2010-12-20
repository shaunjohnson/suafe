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
 * Resource key enumeration used for messages.
 * 
 * @since 2.0
 */
public enum AuthzMessageResourceKey {

	/** The APPLICATION_FILE_HEADER. */
	APPLICATION_FILE_HEADER("applicationFileHeader");

	/** The key. */
	private final String key;

	/**
	 * Private constructor used to create enumeration values.
	 * 
	 * @param key Resource file key
	 */
	private AuthzMessageResourceKey(final String key) {
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
