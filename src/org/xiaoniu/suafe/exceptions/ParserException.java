/*
 * Created on Aug 25, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.exceptions;

/**
 * @author spjohnso
 */
public class ParserException extends ApplicationException {
	public ParserException(int lineNumber, String message) {
		super("Line: " +lineNumber + "\n" + message);
	}
}
