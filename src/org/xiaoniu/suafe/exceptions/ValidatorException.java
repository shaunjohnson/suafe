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

/**
 * Exception that is thrown when a validation fails.
 * 
 * @author Shaun Johnson
 */
public class ValidatorException extends ApplicationException {	
	
	private static final long serialVersionUID = -2790951968306188250L;

	public ValidatorException(String message) {
		super(message);
	}
}
