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
	public static final String HEADER_0_4_1 = "# Created by Suafe 0.4.1 (http://suafe.xiaoniu.org)";
	public static final String HEADER_1_0_0_RC = "# Created by Suafe 1.0.0 RC (http://suafe.xiaoniu.org)";
	
	public static final String HEADER_CURRENT = HEADER_1_0_0_RC;
	
	// Actions
	public static final String DELETE_ACCESS_RULE_ACTION = "DeleteAccessRule";

	public static final String EDIT_ACCESS_RULE_ACTION = "EditAccessRule";

	public static final String DELETE_REPOSITORY_ACTION = "DeleteRepository";

	public static final String EDIT_REPOSITORY_ACTION = "EditRepository";

	public static final String DELETE_PATH_ACTION = "DeletePath";

	public static final String EDIT_PATH_ACTION = "EditPath";

	public static final String ADD_REMOVE_MEMBERS_ACTION = "AddRemoveMembers";

	public static final String DELETE_GROUP_ACTION = "DeleteGroup";

	public static final String CLONE_GROUP_ACTION = "CloneGroup";

	public static final String EDIT_GROUP_ACTION = "EditGroup";

	public static final String CHANGE_MEMBERSHIP_ACTION = "ChangeMembership";

	public static final String DELETE_USER_ACTION = "DeleteUser";

	public static final String CLONE_USER_ACTION = "CloneUser";

	public static final String EDIT_USER_ACTION = "EditUser";

	public static final String LICENSE_ACTION = "License";

	public static final String PRINT_ACTION = "Print";

	public static final String ADD_ACCESS_RULE_ACTION = "AddAccessRule";

	public static final String PREVIEW_ACTION = "Preview";

	public static final String ADD_GROUP_ACTION = "AddGroup";

	public static final String ADD_USER_ACTION = "AddUser";

	public static final String ABOUT_ACTION = "About";

	public static final String EXIT_ACTION = "Exit";

	public static final String HELP_ACTION = "Help";

	public static final String SAVE_FILE_AS_ACTION = "SaveFileAs";

	public static final String SAVE_FILE_ACTION = "SaveFile";

	public static final String OPEN_FILE_ACTION = "OpenFile";

	public static final String NEW_FILE_ACTION = "NewFile";
}
