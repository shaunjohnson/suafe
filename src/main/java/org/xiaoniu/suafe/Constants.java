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
package org.xiaoniu.suafe;

import java.awt.Font;

/**
 * Constant values used throughout the application.
 *  
 * @author Shaun Johnson
 */
public class Constants {
	//
	// Defaults
	//
	public static final int DEFAULT_WIDTH = 840;
	
	public static final int DEFAULT_HEIGHT = 700;
	
	public static final String DEFAULT_DIVIDER_LOCATION = "400";
	
	//
	// Paths
	//
	public static final String RESOURCE_DIR = "org/xiaoniu/suafe/resources";
	
	public static final String FULL_RESOURCE_DIR = "/" + RESOURCE_DIR;
	
	public static final String HELP_DIR = RESOURCE_DIR + "/help";
	
	public static final String IMAGE_DIR = FULL_RESOURCE_DIR + "/images";
	
	public static final String RESOURCE_BUNDLE = RESOURCE_DIR + "/Resources";
	
	//
	// Access Levels
	//
	public static final String ACCESS_LEVEL_READONLY = "r";
	
	public static final String ACCESS_LEVEL_READWRITE = "rw";
	
	public static final String ACCESS_LEVEL_DENY_ACCESS = "";
	
	public static final String ACCESS_LEVEL_NONE = "none";
	
	public static final String ALL_USERS = "*";
	
	public static final String GROUP_PREFIX = "@";
	
	public static final String newline = System.getProperty("line.separator");
	
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	
	public static final String PATH_SEPARATOR = "/";
	
	public static final String DEFAULT_PATH = PATH_SEPARATOR;
	
	public static final int ACCESS_RULE_TABLE_ROW_HEIGHT = 18;
	
	//
	// File headers
	//
	public static final String HEADER_1_1_0 = "# Created using Suafe 1.1.0 (http://code.google.com/p/suafe/)";
	
	public static final String HEADER_CURRENT = HEADER_1_1_0;
	
	//
	// Content types
	//
	public static final String MIME_HTML = "text/html";
	
	public static final String MIME_TEXT = "text/plain";
	
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
	
	public static final String ADD_PROJECT_ACCESS_RULES_ACTION = "ADD_PROJECT_ACCESS_RULES_ACTION";

	public static final String PREVIEW_ACTION = "PREVIEW_ACTION";
	
	public static final String STATISTICS_REPORT_ACTION = "STATISTICS_REPORT_ACTION";
	
	public static final String SUMMARY_REPORT_ACTION = "SUMMARY_REPORT_ACTION";

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
	
	public static final String CLOSE_ACTION = "CLOSE_ACTION";
	
	public static final String ADD_ACTION = "ADD_ACTION";
	
	public static final String CANCEL_ACTION = "CANCEL_ACTION";
	
	public static final String RELOAD_ACTION = "RELOAD_ACTION";
	
	public static final String SAVE_ACTION = "SAVE_ACTION";
	
	public static final String ASSIGN_ACTION = "ASSIGN_ACTION";
	
	public static final String UNASSIGN_ACTION = "UNASSIGN_ACTION";
	
	public static final String OPEN_LAST_EDITED_FILE_ACTION = "OPEN_LAST_EDITED_FILE_ACTION";
	
	public static final String MONOSPACED_ACTION = "MONOSPACED_ACTION";
	
	public static final String SANS_SERIF_ACTION = "SANS_SERIF_ACTION";
	
	public static final String SERIF_ACTION = "SERIF_ACTION";
	
	public static final String CLEAR_RECENT_FILES_ACTION = "CLEAR_RECENT_FILES_ACTION";
	
	public static final String RESET_SETTINGS_ACTION = "RESET_SETTINGS_ACTION";
	
	public static final String VIEW_USERS_ACTION = "VIEW_USERS_ACTION";
	
	public static final String VIEW_GROUPS_ACTION = "VIEW_GROUPS_ACTION";
	
	public static final String VIEW_RULES_ACTION = "VIEW_RULES_ACTION";
	
	// General actions	
	public static final String ARGS_INPUT_FILE = "input file";
	public static final char ARGS_INPUT_FILE_SHORTFLAG = 'i';
	public static final String ARGS_INPUT_FILE_LONGFLAG = "input";
	
	public static final String ARGS_OUTPUT_FILE = "output file";
	public static final char ARGS_OUTPUT_FILE_SHORTFLAG = 'o';
	public static final String ARGS_OUTPUT_FILE_LONGFLAG = "output";
	
	public static final String ARGS_HELP = "help";
	public static final char ARGS_HELP_SHORTFLAG = 'h';
	
	public static final String ARGS_VERBOSE_HELP = "verbosehelp";
	
	public static final String ARGS_VERSION = "version";
	public static final char ARGS_VERSION_SHORTFLAG = 'v';
	
	// User actions
	public static final String ARGS_CLONE_USER = "cloneuser";
	public static final String ARGS_EDIT_USER = "edituser";
	public static final String ARGS_DELETE_USER = "deleteuser";
	public static final String ARGS_ADD_GROUPS = "addgroups";
	public static final String ARGS_REMOVE_GROUPS = "removegroups";
	public static final String ARGS_COUNT_USERS = "countusers";
	public static final String ARGS_GET_USERS = "getusers";
	public static final String ARGS_GET_USER_GROUPS = "getusergroups";
	public static final String ARGS_GET_USER_RULES = "getuserrules";
	
	// Group actions	
	public static final String ARGS_ADD_GROUP = "addgroup";
	public static final String ARGS_CLONE_GROUP = "clonegroup";
	public static final String ARGS_EDIT_GROUP = "editgroup";
	public static final String ARGS_DELETE_GROUP = "deletegroup"; 
	public static final String ARGS_ADD_MEMBERS = "addmembers";
	public static final String ARGS_REMOVE_MEMBERS = "removemembers"; 
	public static final String ARGS_COUNT_GROUPS = "countgroups";
	public static final String ARGS_GET_GROUPS = "getgroups";
	public static final String ARGS_GET_GROUP_MEMBERS = "getgroupmembers";
	public static final String ARGS_GET_GROUP_GROUP_MEMBERS = "getgroupgroupmembers"; 
	public static final String ARGS_GET_GROUP_USER_MEMBERS = "getgroupusermembers"; 
	public static final String ARGS_GET_GROUP_RULES = "getgrouprules";
	
	public static final String ARGS_EDIT_REPOS = "editrepos";
	public static final String ARGS_DELETE_REPOS = "deleterepos";
	public static final String ARGS_COUNT_REPOS = "countrepos";
	public static final String ARGS_GET_REPOS = "getrepos";
	public static final String ARGS_GET_REPOS_RULES = "getreposrules";
	 
	// Access rule actions
	public static final String ARGS_ADD_RULE = "addrule";
	public static final String ARGS_EDIT_RULE = "editrule";
	public static final String ARGS_DELETE_RULE = "deleterule";
	public static final String ARGS_COUNT_RULES = "countrules";
	public static final String ARGS_GET_RULES = "getrules";
	
	// Generic arguments
	public static final String ARGS_NAME = "name";	
	public static final String ARGS_GROUPS = "groups";
	public static final String ARGS_USERS = "users";
	public static final String ARGS_REPOS = "repos";
	public static final String ARGS_PATH = "path";
	public static final String ARGS_USER = "user";
	public static final String ARGS_GROUP = "group";
	public static final String ARGS_ACCESS = "access";
	public static final String ARGS_NEW_NAME = "newname";
	public static final String ARGS_NEW_REPOS = "newrepos";
	public static final String ARGS_NEW_PATH = "newpath";
	public static final String ARGS_NEW_USER = "newuser";
	public static final String ARGS_NEW_GROUP = "newgroup";
	public static final String ARGS_NEW_ACCESS = "newaccess";
	
	// Other
	public static final String ARGS_STATISTICS_REPORT = "statisticsreport";
	public static final String ARGS_SUMMARY_REPORT = "summaryreport";
	
	//
	// Font styles
	//
	public static final String FONT_MONOSPACED = "Monospaced";
	
	public static final String FONT_SERIF = "Serif";
	
	public static final String FONT_SANS_SERIF = "SansSerif";
	
	//
	// Font instances
	//
	public static final Font FONT_PLAIN = new Font(null, Font.PLAIN, 12);
}