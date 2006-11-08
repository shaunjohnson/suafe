/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://suafe.xiaoniu.org.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://suafe.xiaoniu.org/.
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
public class ParserException extends ApplicationException {
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 3638514503455900657L;

	/**
	 * Constructor that an error message.
	 * 
	 * @param message Error message
	 */
	public ParserException(String message) {
		super(message);
	}
	
	/**
	 * Exception factory method that generates ParserException objects.
	 * The line number and error message may be displayed to the user
	 * so that he knows where the error occurred.
	 * 
	 * @param lineNumber Line number where error occurred.
	 * @param message Error message
	 * @return ParserException with localized error message.
	 */
	public static ParserException generateException(int lineNumber, String message) {
		String completeMessage = ResourceUtil.getFormattedString(
				"parser.exception", 
				lineNumber, 
				message);
		
		return new ParserException(completeMessage);
	}
}
