package org.xiaoniu.suafe.exceptions;

import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Exception thrown when a general error occurs.
 * 
 * @author Shaun Johnson
 */
public class AppException extends Exception {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -7928884482694610184L;
	
	/**
	 * Constructor that accepts a message key.
	 * 
	 * @param key Error message key.
	 */
	public AppException(String key) {
		super(ResourceUtil.getString(key));
	}
	
	/**
	 * Constructor that accepts a message key and argument.
	 * 
	 * @param key Error message key.
	 * @param argument Argument value
	 */
	public AppException(String key, Object argument) {
		super(ResourceUtil.getFormattedString(key, argument));
	}
	
	/**
	 * Constructor that accepts a message key and argument.
	 * 
	 * @param key Error message key.
	 * @param argument Argument value
	 */
	public AppException(String key, Object argument1, Object argument2) {
		super(ResourceUtil.getFormattedString(key, argument1, argument2));
	}
	
	/**
	 * Constructor that accepts a message key and arguments.
	 * 
	 * @param key Error message key.
	 * @param arguments Argument values
	 */
	public AppException(String key, Object[] arguments) {
		super(ResourceUtil.getFormattedString(key, arguments));
	}
}
