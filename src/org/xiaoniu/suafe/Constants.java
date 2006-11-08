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
 * Constant values used throught the application.
 *  
 * @author Shaun Johnson
 */
public class Constants {
	//
	// Paths
	//
	public static final String RESOURCE_DIR = "org/xiaoniu/suafe/resources";
	
	public static final String FULL_RESOURCE_DIR = "/" + RESOURCE_DIR;
	
	public static final String HELP_DIR = FULL_RESOURCE_DIR + "/help";
	
	public static final String IMAGE_DIR = FULL_RESOURCE_DIR + "/images";
	
	public static final String RESOURCE_BUNDLE = RESOURCE_DIR + "/Resources";
	
	//
	// Access Levels
	//
	public static final String ACCESS_LEVEL_READONLY = "r";
	
	public static final String ACCESS_LEVEL_READWRITE = "rw";
	
	public static final String ACCESS_LEVEL_DENY_ACCESS = "";     
	
	public static final String ALL_USERS = "*";
	
	public static final String newline = System.getProperty("line.separator");
	
	public static final String PATH_SEPARATOR = "/";
	
	public static final String DEFAULT_PATH = PATH_SEPARATOR;
	
	public static final int ACCESS_RULE_TABLE_ROW_HEIGHT = 18;
	
	//
	// File headers
	//
	public static final String HEADER_0_2 = "# Created by Suafe 0.2 (http://suafe.xiaoniu.org)";
	
	public static final String HEADER_0_3 = "# Created by Suafe 0.3 (http://suafe.xiaoniu.org)";
	
	public static final String HEADER_0_4 = "# Created by Suafe 0.4 (http://suafe.xiaoniu.org)";
	
	public static final String HEADER_0_4_1 = "# Created by Suafe 0.4.1 (http://suafe.xiaoniu.org)";
	
	public static final String HEADER_1_0_0_RC = "# Created by Suafe 1.0.0 RC (http://suafe.xiaoniu.org)";
	
	public static final String HEADER_CURRENT = HEADER_1_0_0_RC;
	
	//
	// Actions
	//
	public static final String ADD_REPOSITORY_ACTION = "ADD_REPOSITORY_ACTION";
	
	public static final String DELETE_ACCESS_RULE_ACTION = "DELETE_ACCESS_RULE_ACTION";

	public static final String EDIT_ACCESS_RULE_ACTION = "EDIT_ACCESS_RULE_ACTION";

	public static final String DELETE_REPOSITORY_ACTION = "DELETE_REPOSITORY_ACTION";

	public static final String EDIT_REPOSITORY_ACTION = "EDIT_REPOSITORY_ACTION";

	public static final String DELETE_PATH_ACTION = "DELETE_PATH_ACTION";

	public static final String EDIT_PATH_ACTION = "EDIT_PATH_ACTION";

	public static final String ADD_REMOVE_MEMBERS_ACTION = "ADD_REMOVE_MEMBERS_ACTION";

	public static final String DELETE_GROUP_ACTION = "DELETE_GROUP_ACTION";

	public static final String CLONE_GROUP_ACTION = "CLONE_GROUP_ACTION";

	public static final String EDIT_GROUP_ACTION = "EDIT_GROUP_ACTION";

	public static final String CHANGE_MEMBERSHIP_ACTION = "CHANGE_MEMBERSHIP_ACTION";

	public static final String DELETE_USER_ACTION = "DELETE_USER_ACTION";

	public static final String CLONE_USER_ACTION = "CLONE_USER_ACTION";

	public static final String EDIT_USER_ACTION = "EDIT_USER_ACTION";

	public static final String LICENSE_ACTION = "LICENSE_ACTION";

	public static final String PRINT_ACTION = "PRINT_ACTION";

	public static final String ADD_ACCESS_RULE_ACTION = "ADD_ACCESS_RULE_ACTION";

	public static final String PREVIEW_ACTION = "PREVIEW_ACTION";

	public static final String ADD_GROUP_ACTION = "ADD_GROUP_ACTION";

	public static final String ADD_USER_ACTION = "ADD_USER_ACTION";

	public static final String ABOUT_ACTION = "ABOUT_ACTION";

	public static final String EXIT_ACTION = "EXIT_ACTION";

	public static final String HELP_ACTION = "HELP_ACTION";

	public static final String SAVE_FILE_AS_ACTION = "SAVE_FILE_AS_ACTION";

	public static final String SAVE_FILE_ACTION = "SAVE_FILE_ACTION";

	public static final String OPEN_FILE_ACTION = "OPEN_FILE_ACTION";

	public static final String NEW_FILE_ACTION = "NEW_FILE_ACTION";
	
	public static final String OK_ACTION = "OK_ACTION";
	
	public static final String ADD_ACTION = "ADD_ACTION";
	
	public static final String CANCEL_ACTION = "CANCEL_ACTION";
	
	public static final String SAVE_ACTION = "SAVE_ACTION";
	
	public static final String ASSIGN_ACTION = "ASSIGN_ACTION";
	
	public static final String UNASSIGN_ACTION = "UNASSIGN_ACTION";
}
