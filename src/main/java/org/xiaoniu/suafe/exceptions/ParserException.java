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

import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Exception thrown when a parser error occurs.
 * 
 * @author Shaun Johnson
 */
public final class ParserException extends AppException {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 3638514503455900657L;

	/**
	 * Constructor that an error message.
	 * 
	 * @param message
	 *            Error message
	 */
	private ParserException(String key, Object argument1, Object argument2) {
		super(key, argument1, argument2);
	}

	/**
	 * Exception factory method that generates ParserException objects. The line
	 * number and error message may be displayed to the user so that he knows
	 * where the error occurred.
	 * 
	 * @param lineNumber
	 *            Line number where error occurred.
	 * @param key
	 *            Error message key
	 * @return ParserException with localized error message.
	 */
	public static ParserException generateException(int lineNumber, String key) {
		return new ParserException("parser.exception", lineNumber, ResourceUtil
				.getString(key));
	}

	/**
	 * Exception factory method that generates ParserException objects. The line
	 * number and error message may be displayed to the user so that he knows
	 * where the error occurred.
	 * 
	 * @param lineNumber
	 *            Line number where error occurred.
	 * @param key
	 *            Error message key
	 * @param argument
	 *            Argument value
	 * @return ParserException with localized error message.
	 */
	public static ParserException generateException(int lineNumber, String key,
			String argument) {
		return new ParserException("parser.exception", lineNumber, ResourceUtil
				.getFormattedString(key, argument));
	}

	/**
	 * Exception factory method that generates ParserException objects. The line
	 * number and error message may be displayed to the user so that he knows
	 * where the error occurred.
	 * 
	 * @param lineNumber
	 *            Line number where error occurred.
	 * @param key
	 *            Error message key
	 * @param arguments
	 *            Argument values
	 * @return ParserException with localized error message.
	 */
	public static ParserException generateException(int lineNumber, String key,
			Object[] arguments) {
		return new ParserException("parser.exception", lineNumber, ResourceUtil
				.getFormattedString(key, arguments));
	}

	/**
	 * Exception factory method that generates ParserException objects. The line
	 * number and error message may be displayed to the user so that he knows
	 * where the error occurred.
	 * 
	 * @param lineNumber
	 *            Line number where error occurred.
	 * @param key
	 *            Error message key
	 * @return ParserException with localized error message.
	 */
	public static ParserException generateException(int lineNumber, Exception e) {
		return new ParserException("parser.exception", lineNumber, e.getMessage());
	}
}
