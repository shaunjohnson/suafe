package org.xiaoniu.suafe;

/**
 * Contains constant values used throught the application.
 *  
 * @author Shaun Johnson
 */
public class Constants {
	// Access Levels
	public static final String ACCESS_LEVEL_READONLY = "r";
	public static final String ACCESS_LEVEL_READWRITE = "rw";
	public static final String ACCESS_LEVEL_DENY_ACCESS = "";     
	
	public static final String ACCESS_LEVEL_READONLY_FULL = "Read only";
	public static final String ACCESS_LEVEL_READWRITE_FULL = "Read/Write";
	public static final String ACCESS_LEVEL_DENY_ACCESS_FULL = "Deny access";
	
	public static final String newline = System.getProperty("line.separator");
	public static final String PATH_SEPARATOR = "/";
	public static final String DEFAULT_PATH = PATH_SEPARATOR;
	
	public static final int ACCESS_RULE_TABLE_ROW_HEIGHT = 18;
}
