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
	
	public static final String ALL_USERS = "*";
	public static final String newline = System.getProperty("line.separator");
	public static final String PATH_SEPARATOR = "/";
	public static final String DEFAULT_PATH = PATH_SEPARATOR;
	
	public static final int ACCESS_RULE_TABLE_ROW_HEIGHT = 18;
	
	public static final String HEADER_0_2 = "# Created by Suafe 0.2 (http://suafe.xiaoniu.org)";
	public static final String HEADER_0_3 = "# Created by Suafe 0.3 (http://suafe.xiaoniu.org)";
	public static final String HEADER_0_4 = "# Created by Suafe 0.4 (http://suafe.xiaoniu.org)";
	
	public static final String HEADER_CURRENT = HEADER_0_4;
}
