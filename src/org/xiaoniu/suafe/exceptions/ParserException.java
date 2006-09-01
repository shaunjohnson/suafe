package org.xiaoniu.suafe.exceptions;

/**
 * @author Shaun Johnson
 */
public class ParserException extends ApplicationException {
	public ParserException(int lineNumber, String message) {
		super("Line: " +lineNumber + "\n" + message);
	}
}
