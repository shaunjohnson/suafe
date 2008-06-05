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
package org.xiaoniu.suafe.exceptions;

/**
 * Exception thrown when a general error occurs.
 * 
 * @author Shaun Johnson
 */
public class ApplicationException extends Exception {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -6917550556439119411L;

	/**
	 * Default constructor.
	 */
	public ApplicationException() {
		super();
	}
	
	/**
	 * Constructor that accepts a message.
	 * 
	 * @param message Error message.
	 */
	public ApplicationException(String message) {
		super(message);
	}
}
