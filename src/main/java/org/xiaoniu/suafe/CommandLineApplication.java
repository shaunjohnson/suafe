package org.xiaoniu.suafe;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.reports.StatisticsReport;
import org.xiaoniu.suafe.reports.SummaryReport;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.utils.ArgumentParser;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;

public final class CommandLineApplication {
	private static final String ARGS_ACCESS = "access";

	private static final String ARGS_ADD_GROUP = "addgroup";

	private static final String ARGS_ADD_GROUPS = "addgroups";

	private static final String ARGS_ADD_MEMBERS = "addmembers";

	private static final String ARGS_ADD_RULE = "addrule";

	private static final String ARGS_CLONE_GROUP = "clonegroup";

	private static final String ARGS_CLONE_USER = "cloneuser";

	private static final String ARGS_COUNT_GROUPS = "countgroups";

	private static final String ARGS_COUNT_REPOS = "countrepos";

	private static final String ARGS_COUNT_RULES = "countrules";

	private static final String ARGS_COUNT_USERS = "countusers";

	private static final String ARGS_DELETE_GROUP = "deletegroup";

	private static final String ARGS_DELETE_REPOS = "deleterepos";

	private static final String ARGS_DELETE_RULE = "deleterule";

	private static final String ARGS_DELETE_USER = "deleteuser";

	private static final String ARGS_EDIT_RULE = "editrule";

	private static final String ARGS_GET_GROUP_GROUP_MEMBERS = "getgroupgroupmembers";

	private static final String ARGS_GET_GROUP_MEMBERS = "getgroupmembers";

	private static final String ARGS_GET_GROUP_RULES = "getgrouprules";

	private static final String ARGS_GET_GROUP_USER_MEMBERS = "getgroupusermembers";

	private static final String ARGS_GET_GROUPS = "getgroups";

	private static final String ARGS_GET_REPOS = "getrepos";

	private static final String ARGS_GET_REPOS_RULES = "getreposrules";

	private static final String ARGS_GET_RULES = "getrules";

	private static final String ARGS_GET_USER_GROUPS = "getusergroups";

	private static final String ARGS_GET_USER_RULES = "getuserrules";

	private static final String ARGS_GET_USERS = "getusers";

	private static final String ARGS_GROUP = "group";

	private static final String ARGS_GROUPS = "groups";

	private static final String ARGS_HELP = "help";

	private static final char ARGS_HELP_SHORTFLAG = 'h';

	private static final String ARGS_INPUT_FILE = "input file";

	private static final String ARGS_INPUT_FILE_LONGFLAG = "input";

	private static final char ARGS_INPUT_FILE_SHORTFLAG = 'i';

	private static final String ARGS_NAME = "name";

	private static final String ARGS_NEW_ACCESS = "newaccess";

	private static final String ARGS_NEW_GROUP = "newgroup";

	private static final String ARGS_NEW_NAME = "newname";

	private static final String ARGS_NEW_PATH = "newpath";

	private static final String ARGS_NEW_REPOS = "newrepos";

	private static final String ARGS_NEW_USER = "newuser";

	private static final String ARGS_OUTPUT_FILE = "output file";

	private static final String ARGS_OUTPUT_FILE_LONGFLAG = "output";

	private static final char ARGS_OUTPUT_FILE_SHORTFLAG = 'o';

	private static final String ARGS_PATH = "path";

	private static final String ARGS_REMOVE_GROUPS = "removegroups";

	private static final String ARGS_REMOVE_MEMBERS = "removemembers";

	private static final String ARGS_RENAME_GROUP = "renamegroup";

	private static final String ARGS_RENAME_REPOS = "renamerepos";

	private static final String ARGS_RENAME_USER = "renameuser";

	private static final String ARGS_REPOS = "repos";

	private static final String ARGS_STATISTICS_REPORT = "statisticsreport";

	private static final String ARGS_SUMMARY_REPORT = "summaryreport";

	private static final String ARGS_USER = "user";

	private static final String ARGS_USERS = "users";

	private static final String ARGS_VERBOSE_HELP = "verbosehelp";

	private static final String ARGS_VERSION = "version";

	private static final char ARGS_VERSION_SHORTFLAG = 'v';

	/**
	 * Adds a new group.
	 * 
	 * @param groupName Name of new group
	 * @throws AppException Error occurred
	 */
	private String addGroup(Document document, String groupName) throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		document.addGroup(groupName);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Add user to groups.
	 * 
	 * @param userName Name of user
	 * @param groupNames List of groups
	 * @throws AppException, AppException Error occurred
	 */
	private String addGroups(Document document, String userName, String[] groupNames) throws AppException {
		if (userName == null) {
			throw new AppException("application.error.userrequired");
		}

		if (groupNames == null || groupNames.length < 1) {
			throw new AppException("application.error.grouplistrequired");
		}

		User user = document.findUser(userName);

		if (user == null) {
			user = document.addUser(userName);
		}

		for (String groupName : groupNames) {
			Group group = document.findGroup(groupName);

			if (group == null) {
				throw new AppException("application.error.unabletofindgroup", groupName);
			}

			user.addGroup(group);
			group.addUserMember(user);
		}

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Add new user and/or group members to an existing group. New user and group members may be added at the same time.
	 * At least one user or group name is required.
	 * 
	 * @param groupName Group name to be updated
	 * @param userNames User names of new members
	 * @param groupNames Group names of new members
	 * @throws AppException, AppException Error occurred
	 */
	private String addMembers(Document document, String groupName, String[] userNames, String[] groupNames)
			throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		if ((userNames == null || userNames.length < 1) && (groupNames == null || groupNames.length < 1)) {
			throw new AppException("application.error.userorgrouplistrequired");
		}

		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		for (String memberUserName : userNames) {
			User memberUser = document.findUser(memberUserName);

			if (memberUser == null) {
				// Add user since it doesn't exist.
				memberUser = document.addUser(memberUserName);
			}

			group.addUserMember(memberUser);
		}

		for (String memberGroupName : groupNames) {
			Group memberGroup = document.findGroup(memberGroupName);

			if (memberGroup == null) {
				throw new AppException("application.error.unabletofindgroup", memberGroupName);
			}

			group.addGroupMember(memberGroup);
		}

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Add a new access rule. Either user or group name, but not both must be specified. All other arguments are
	 * required.
	 * 
	 * @param repositoryName Repository name
	 * @param path Relative path string
	 * @param userName User name
	 * @param groupName Group name
	 * @param access Access level
	 * @throws AppException, AppException Error occurred
	 */
	private String addRule(Document document, String repositoryName, String path, String userName, String groupName,
			String access) throws AppException, AppException {
		if (path == null) {
			throw new AppException("application.error.pathrequired");
		}

		if (userName == null && groupName == null) {
			throw new AppException("application.error.userorgrouprequired");
		}

		if (userName != null && groupName != null) {
			throw new AppException("application.error.userorgrouprequired");
		}

		if (access == null) {
			throw new AppException("application.error.accessrequired");
		}

		// Convert "none" to "" if necessary
		access = access.equals(SubversionConstants.SVN_ACCESS_LEVEL_NONE) ? SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS
				: access;

		if (userName != null) {
			if (repositoryName == null) {
				document.addAccessRuleForUser(null, path, document.addUser(userName), access);
			}
			else {
				document.addAccessRuleForUser(document.addRepository(repositoryName), path, document.addUser(userName),
						access);
			}
		}
		else if (groupName != null) {
			if (repositoryName == null) {
				document.addAccessRuleForGroup(null, path, document.addGroup(groupName), access);
			}
			else {
				document.addAccessRuleForGroup(document.addRepository(repositoryName), path, document
						.addGroup(groupName), access);
			}
		}
		else {
			throw new AppException("application.error.userorgrouprequired");
		}

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Clone existing group.
	 * 
	 * @param groupName Name of group to clone
	 * @param cloneName Clone name
	 * @throws AppException, AppException Error occurred
	 */
	private String cloneGroup(Document document, String groupName, String cloneName) throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		if (cloneName == null) {
			throw new AppException("application.error.clonerequired");
		}

		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		document.cloneGroup(group, cloneName);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Clone existing user.
	 * 
	 * @param userName Name of user to be cloned
	 * @param cloneName Name for the clone
	 * @throws AppException, AppException Error occurred
	 */
	private String cloneUser(Document document, String userName, String cloneName) throws AppException, AppException {
		if (userName == null) {
			throw new AppException("application.error.userrequired");
		}

		if (cloneName == null) {
			throw new AppException("application.error.clonerequired");
		}

		User user = document.findUser(userName);

		if (user == null) {
			throw new AppException("application.error.unabletofinduser", userName);
		}

		document.cloneUser(user, cloneName);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Outputs the number of groups.
	 * 
	 * @param out Output stream
	 */
	private String countGroups(Document document) {
		return Integer.toString(document.getGroupObjects().length) + "\n";
	}

	/**
	 * Counts number of repositories.
	 * 
	 * @param out Output stream
	 */
	private String countRepositories(Document document) {
		return Integer.toString(document.getRepositories().size()) + "\n";
	}

	/**
	 * Counts number of rules.
	 * 
	 * @param out Output stream
	 */
	private String countRules(Document document) {
		return Integer.toString(document.getAccessRules().size()) + "\n";
	}

	/**
	 * Counts number of users.
	 * 
	 * @param out Output stream
	 */
	private String countUsers(Document document) {
		return Integer.toString(document.getUserObjects().length) + "\n";
	}

	/**
	 * Deletes an existing group.
	 * 
	 * @param groupName Name of group to delete
	 * @throws AppException, AppException Error occurred
	 */
	private String deleteGroup(Document document, String groupName) throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		document.deleteGroup(group);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Deletes an existing repository.
	 * 
	 * @param repositoryName Name of repository
	 * @throws AppException, AppException Error occurred
	 */
	private String deleteRepository(Document document, String repositoryName) throws AppException, AppException {
		if (repositoryName == null) {
			throw new AppException("application.error.repositoryrequired");
		}

		Repository repository = document.findRepository(repositoryName);

		if (repository == null) {
			throw new AppException("application.error.unabletofindrepository", repositoryName);
		}

		document.deleteRepository(repository);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Deletes an existing access rule. Either user or group name, but not both is required. All other arguments are
	 * required. Arguments are used to identify an existing access rule.
	 * 
	 * @param repositoryName Repository name
	 * @param path Relative path string
	 * @param userName User name
	 * @param groupName Group name
	 * @throws AppException, AppException Error occurred
	 */
	private String deleteRule(Document document, String repositoryName, String path, String userName, String groupName)
			throws AppException, AppException {
		if (path == null) {
			throw new AppException("application.error.pathrequired");
		}

		if (userName == null && groupName == null) {
			throw new AppException("application.error.userorgrouprequired");
		}

		if (userName != null && groupName != null) {
			throw new AppException("application.error.userorgrouprequired");
		}

		if (userName != null) {
			User user = document.findUser(userName);

			if (user == null) {
				throw new AppException("application.error.unabletofinduser", userName);
			}

			Repository repository = null;

			if (repositoryName != null) {
				repository = document.findRepository(repositoryName);

				if (repository == null) {
					throw new AppException("application.error.unabletofindrepository", repositoryName);
				}
			}

			document.deleteAccessRule(repositoryName, path, null, user);
		}
		else if (groupName != null) {
			Group group = document.findGroup(groupName);

			if (group == null) {
				throw new AppException("application.error.unabletofindgroup", groupName);
			}

			Repository repository = null;

			if (repositoryName != null) {
				repository = document.findRepository(repositoryName);

				if (repository == null) {
					throw new AppException("application.error.unabletofindrepository", repositoryName);
				}
			}

			document.deleteAccessRule(repositoryName, path, group, null);
		}
		else {
			throw new AppException("application.error.userorgrouprequired");
		}

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Deletes an existing user
	 * 
	 * @param userName Name of user
	 * @throws AppException, AppException Error occurred
	 */
	private String deleteUser(Document document, String userName) throws AppException, AppException {
		if (userName == null) {
			throw new AppException("application.error.userrequired");
		}

		User user = document.findUser(userName);

		if (user == null) {
			throw new AppException("application.error.unabletofinduser", userName);
		}

		document.deleteUser(user);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Outputs basic command line usage.
	 * 
	 * @param stream Output stream
	 * @param jsap JSAP results
	 */
	private void displayUsage(PrintStream stream, JSAP jsap) {
		stream.println();
		stream.println(ResourceUtil.getString("application.nameversion"));
		stream.println();
		stream.println(ResourceUtil.getString("application.args.usage"));
		stream.println("  " + jsap.getUsage());
		stream.println();
		stream.println(ResourceUtil.getString("application.args.detailedusage"));
		stream.println(jsap.getHelp());
		stream.println();
	}

	/**
	 * Outputs verbose/detailed command line usage.
	 * 
	 * @param out Output stream
	 * @param jsap JSAP results.
	 */
	private void displayVerboseHelp(PrintStream out, JSAP jsap) {
		final String SUAFE_EXECUTABLE = ResourceUtil.getString("application.args.executablepath");

		displayUsage(System.out, jsap);

		out.println(ResourceUtil.getString("application.args.verbosehelp"));
		out.println(ResourceUtil.getString("application.args.verbosehelp.summary"));

		Object[] args = new Object[11];
		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_INPUT_FILE_LONGFLAG;
		args[2] = ARGS_OUTPUT_FILE_LONGFLAG;
		args[3] = ARGS_CLONE_USER;
		args[4] = ARGS_NAME;
		args[5] = ARGS_NEW_NAME;
		args[6] = ARGS_COUNT_USERS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.inputoutput", args));

		out.println(ResourceUtil.getString("application.args.verbose.adduser"));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_CLONE_USER;
		args[2] = ARGS_NAME;
		args[3] = ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.cloneuser", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_RENAME_USER;
		args[2] = ARGS_NAME;
		args[3] = ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.renameuser", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_DELETE_USER;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deleteuser", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_ADD_GROUPS;
		args[2] = ARGS_NAME;
		args[3] = ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addgroups", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_REMOVE_GROUPS;
		args[2] = ARGS_NAME;
		args[3] = ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.removegroups", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_COUNT_USERS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countusers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_USERS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getusers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_USER_GROUPS;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getusergroups", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_USER_RULES;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getuserrules", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_USER_RULES;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addgroup", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_CLONE_GROUP;
		args[2] = ARGS_NAME;
		args[3] = ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.clonegroup", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_RENAME_GROUP;
		args[2] = ARGS_NAME;
		args[3] = ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.renamegroup", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_DELETE_GROUP;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deletegroup", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_ADD_MEMBERS;
		args[2] = ARGS_USERS;
		args[3] = ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addmembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_REMOVE_MEMBERS;
		args[2] = ARGS_USERS;
		args[3] = ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.removemembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_COUNT_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countgroups", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroups", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_GROUP_MEMBERS;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroupmembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_GROUP_GROUP_MEMBERS;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroupgroupmembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_GROUP_USER_MEMBERS;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroupusermembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_GROUP_RULES;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgrouprules", args));

		out.println(ResourceUtil.getString("application.args.verbose.addrepos"));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_RENAME_REPOS;
		args[2] = ARGS_NAME;
		args[3] = ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.renamerepos", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_DELETE_REPOS;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deleterepos", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_COUNT_REPOS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countrepos", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_REPOS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getrepos", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_REPOS_RULES;
		args[2] = ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getreposrules", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_ADD_RULE;
		args[2] = ARGS_REPOS;
		args[3] = ARGS_PATH;
		args[4] = ARGS_USER;
		args[5] = ARGS_GROUP;
		args[6] = ARGS_ACCESS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addrule", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_EDIT_RULE;
		args[2] = ARGS_REPOS;
		args[3] = ARGS_PATH;
		args[4] = ARGS_USER;
		args[5] = ARGS_GROUP;
		args[6] = ARGS_NEW_REPOS;
		args[7] = ARGS_NEW_PATH;
		args[8] = ARGS_NEW_USER;
		args[9] = ARGS_NEW_GROUP;
		args[10] = ARGS_NEW_ACCESS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.editrule", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_DELETE_RULE;
		args[2] = ARGS_REPOS;
		args[3] = ARGS_PATH;
		args[4] = ARGS_USER;
		args[5] = ARGS_GROUP;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deleterule", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_COUNT_RULES;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countrules", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_GET_RULES;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getrules", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_STATISTICS_REPORT;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.statisticsreport", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = ARGS_SUMMARY_REPORT;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.summaryreport", args));
	}

	/**
	 * Outputs application name and version information.
	 * 
	 * @param stream Output stream
	 */
	private void displayVersion(PrintStream stream) {
		stream.println(ResourceUtil.getString("application.nameversion"));
	}

	/**
	 * Edits an existing access rule. Either user or group name, but not both must be specified. Arguments prefixed with
	 * "new" indicate new values for the access rule. Multiple changes may be specified at once.
	 * 
	 * @param repositoryName Repository name
	 * @param path Relative path string
	 * @param userName User name
	 * @param groupName Group name
	 * @param newRepositoryName New repository name
	 * @param newPathString New relative path string
	 * @param newUserName New user name
	 * @param newGroupName New group name
	 * @param newAccess New access level
	 * @throws AppException, AppException Error occurred
	 */
	private String editRule(Document document, String repositoryName, String path, String userName, String groupName,
			String newRepositoryName, String newPathString, String newUserName, String newGroupName, String newAccess)
			throws AppException, AppException {
		if (path == null) {
			throw new AppException("application.error.pathrequired");
		}

		if (userName == null && groupName == null) {
			throw new AppException("application.error.userorgrouprequired");
		}

		if (userName != null && groupName != null) {
			throw new AppException("application.error.userorgrouprequired");
		}

		if (newUserName != null && newGroupName != null) {
			throw new AppException("application.error.newuserorgrouprequired");
		}

		AccessRule rule = null;

		if (userName != null) {
			User user = document.findUser(userName);

			if (user == null) {
				throw new AppException("application.error.unabletofinduser", userName);
			}

			Repository repository = null;

			if (repositoryName != null) {
				repository = document.findRepository(repositoryName);

				if (repository == null) {
					throw new AppException("application.error.unabletofindrepository", repositoryName);
				}
			}

			rule = document.findUserAccessRule(repository, path, user);

			if (rule == null) {
				throw new AppException("application.error.unabletofindrule");
			}
		}
		else if (groupName != null) {
			Group group = document.findGroup(groupName);

			if (group == null) {
				throw new AppException("application.error.unabletofindgroup", groupName);
			}

			Repository repository = null;

			if (repositoryName != null) {
				repository = document.findRepository(repositoryName);

				if (repository == null) {
					throw new AppException("application.error.unabletofindrepository", repositoryName);
				}
			}

			rule = document.findGroupAccessRule(repository, path, group);

			if (rule == null) {
				throw new AppException("application.error.unabletofindrule");
			}
		}
		else {
			throw new AppException("application.error.userorgrouprequired");
		}

		if (newRepositoryName != null) {
			Repository newRepository = document.findRepository(newRepositoryName);

			if (newRepository == null) {
				throw new AppException("application.error.unabletofindrepository", repositoryName);
			}

			rule.getPath().setRepository(newRepository);
		}

		if (newPathString != null) {
			Path newPath = document.addPath(rule.getPath().getRepository(), newPathString);

			rule.setPath(newPath);
		}

		if (newUserName != null) {
			User newUser = document.addUser(newUserName);

			if (newUser == null) {
				throw new AppException("application.error.unabletofinduser", userName);
			}

			rule.setUser(newUser);
			rule.setGroup(null);
		}

		if (newGroupName != null) {
			Group newGroup = document.findGroup(newGroupName);

			if (newGroup == null) {
				throw new AppException("application.error.unabletofindgroup", groupName);
			}

			rule.setUser(null);
			rule.setGroup(newGroup);
		}

		if (newAccess != null) {
			String newAccessLevel = (newAccess.equals(SubversionConstants.SVN_ACCESS_LEVEL_NONE)) ? SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS
					: newAccess;

			rule.setLevel(newAccessLevel);
		}

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Executes commands specified as application arguments parsed by JSAP. Output is directed to System.out by default
	 * or to an output file if one is specified.
	 * 
	 * Application help, verbose help and version commands are processed immediately. All remaining arguments are
	 * ignored.
	 * 
	 * If neither of these commands are specified then the arguments are checked for an input file directive. If no
	 * input file was specified then input is pulled from System.in. Finally, all remaining commands are executed
	 * against the specified input stream.
	 * 
	 * @param jsap JSAP results
	 * @param config JSAP configuration
	 */
	private void executeCommands(JSAP jsap, JSAPResult config) {
		Document document = null;
		PrintStream out = System.out;

		try {
			if (config.getBoolean(ARGS_HELP)) {
				displayUsage(out, jsap);
				System.exit(0);
			}
			else if (config.getBoolean(ARGS_VERBOSE_HELP)) {
				displayVerboseHelp(out, jsap);
				System.exit(0);
			}
			else if (config.getBoolean(ARGS_VERSION)) {
				displayVersion(out);
				System.exit(0);
			}

			// Parse input from file or stdin
			if (config.getString(ARGS_INPUT_FILE) == null) {
				document = new FileParser().parse(System.in);
			}
			else {
				document = new FileParser().parse(new File(config.getString(ARGS_INPUT_FILE)));
			}

			// Initialize the output stream
			if (config.getString(ARGS_OUTPUT_FILE) != null) {
				out = Utilities.openOutputFile(config.getString(ARGS_OUTPUT_FILE));
			}

			// Process the specified command
			String result = processCommands(document, config);

			// Print output
			out.print(result);

			// Close the output stream
			if (config.getString(ARGS_OUTPUT_FILE) != null) {
				out.close();
				out = null;
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Get list of groups that are a member of a group.
	 * 
	 * @param out Output stream
	 * @param groupName Name of group
	 * @throws AppException, AppException Error occurred
	 */
	private String getGroupGroupMembers(Document document, String groupName) throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		StringBuilder builder = new StringBuilder();
		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		List<Group> groupMembers = group.getGroupMembers();

		for (Group groupMember : groupMembers) {
			builder.append(groupMember.getName());
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Get list of members of a group. User and group members are returned.
	 * 
	 * @param out Output stream
	 * @param groupName Name of group
	 * @throws AppException, AppException Error occurred
	 */
	private String getGroupMembers(Document document, String groupName) throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		StringBuilder builder = new StringBuilder();
		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		List<Group> groupMembers = group.getGroupMembers();

		if (groupMembers != null && groupMembers.size() > 0) {
			builder.append(ResourceUtil.getString("application.groups"));
			builder.append("\n");

			for (Group groupMember : groupMembers) {
				builder.append(groupMember.getName());
				builder.append("\n");
			}
		}

		List<User> userMembers = group.getUserMembers();

		if (groupMembers != null && userMembers.size() > 0) {
			builder.append(ResourceUtil.getString("application.users"));
			builder.append("\n");

			for (User userMember : userMembers) {
				builder.append(userMember.getName());
				builder.append("\n");
			}
		}

		return builder.toString();
	}

	/**
	 * Get access rules for a group.
	 * 
	 * @param out Output stream
	 * @param groupName Name of group
	 * @throws AppException, AppException Error occurred
	 */
	private String getGroupRules(Document document, String groupName) throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		StringBuilder builder = new StringBuilder();
		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		Object[][] accessRules = document.getGroupAccessRules(group);

		for (Object[] accessRule : accessRules) {
			Repository repository = (Repository) accessRule[0];
			Path path = (Path) accessRule[1];
			String accessLevel = (String) accessRule[2];

			builder.append(repository.getName());
			builder.append(" ");
			builder.append(path.getPath());
			builder.append(" ");
			builder.append(SubversionConstants.SVN_GROUP_REFERENCE_PREFIX + groupName);
			builder.append(" ");
			builder.append(accessLevel);
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Gets list of all groups.
	 * 
	 * @param out Output stream
	 */
	private String getGroups(Document document) {
		StringBuilder builder = new StringBuilder();
		Object[] groupNames = document.getGroupNames();

		for (Object groupName : groupNames) {
			builder.append((String) groupName);
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Get list of users that are a member of a group.
	 * 
	 * @param out Output strea
	 * @param groupName Name of group
	 * @throws AppException, AppException Exception occurred
	 */
	private String getGroupUserMembers(Document document, String groupName) throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		StringBuilder builder = new StringBuilder();
		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		List<User> userMembers = group.getUserMembers();

		for (User userMember : userMembers) {
			builder.append(userMember.getName());
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Gets list of all repositories.
	 * 
	 * @param out Output stream
	 */
	private String getRepositories(Document document) {
		StringBuilder builder = new StringBuilder();
		Object[] repositoryNames = document.getRepositoryNames();

		for (Object repositoryName : repositoryNames) {
			builder.append((String) repositoryName);
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Get access rules for a repository.
	 * 
	 * @param out Ouptput stream
	 * @param repositoryName Name of repository
	 * @throws AppException, AppException Error occurred
	 */
	private String getRepositoryRules(Document document, String repositoryName) throws AppException, AppException {
		if (repositoryName == null) {
			throw new AppException("application.error.repositoryrequired");
		}

		StringBuilder builder = new StringBuilder();
		Repository repository = document.findRepository(repositoryName);

		if (repository == null) {
			throw new AppException("application.error.unabletofindrepository", repositoryName);
		}

		Object[][] accessRules = document.getRepositoryAccessRules(repository);

		for (Object[] accessRule : accessRules) {
			Path path = (Path) accessRule[0];
			String accessLevel = (String) accessRule[2];
			String name = null;

			if (accessRule[1] instanceof Group) {
				name = SubversionConstants.SVN_GROUP_REFERENCE_PREFIX + ((Group) accessRule[1]).getName();
			}
			else if (accessRule[1] instanceof User) {
				name = ((User) accessRule[1]).getName();
			}
			else {
				throw new AppException("application.erroroccurred");
			}

			builder.append(repositoryName);
			builder.append(" ");
			builder.append(path.getPath());
			builder.append(" ");
			builder.append(name);
			builder.append(" ");
			builder.append(accessLevel);
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Get all access rules.
	 * 
	 * @param out Ouptput stream
	 * @throws AppException, AppException Error occurred
	 */
	private String getRules(Document document) throws AppException, AppException {
		StringBuilder builder = new StringBuilder();
		List<AccessRule> rules = document.getAccessRules();

		for (AccessRule rule : rules) {
			Path path = rule.getPath();
			String accessLevel = rule.getLevelFullName();
			String name = null;
			String repositoryName = "";

			if (path.getRepository() != null) {
				repositoryName = path.getRepository().getName();
			}

			if (rule.getGroup() != null) {
				name = SubversionConstants.SVN_GROUP_REFERENCE_PREFIX + rule.getGroup().getName();
			}
			else if (rule.getUser() != null) {
				name = rule.getUser().getName();
			}
			else {
				throw new AppException("application.erroroccurred");
			}

			builder.append(repositoryName);
			builder.append(" ");
			builder.append(path.getPath());
			builder.append(" ");
			builder.append(name);
			builder.append(" ");
			builder.append(accessLevel);
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Get list of groups in which the user is a member.
	 * 
	 * @param out Output stream
	 * @param userName Name of user
	 * @throws AppException, AppException Error occurred
	 */
	private String getUserGroups(Document document, String userName) throws AppException, AppException {
		if (userName == null) {
			throw new AppException("application.error.userrequired");
		}

		if (document.findUser(userName) == null) {
			throw new AppException("application.error.unabletofinduser", userName);
		}

		StringBuilder builder = new StringBuilder();
		Object[] groupNames = document.getUserGroupNames(userName);

		for (Object groupName : groupNames) {
			builder.append((String) groupName);
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Gets access rules for a user.
	 * 
	 * @param out Output stream
	 * @param userName Name of user
	 * @throws AppException, AppException Error occurred
	 */
	private String getUserRules(Document document, String userName) throws AppException, AppException {
		if (userName == null) {
			throw new AppException("application.error.userrequired");
		}

		if (document.findUser(userName) == null) {
			throw new AppException("application.error.unabletofinduser", userName);
		}

		StringBuilder builder = new StringBuilder();
		Object[][] accessRules = document.getUserAccessRules(userName);

		for (Object[] accessRule : accessRules) {
			Repository repository = (Repository) accessRule[0];
			Path path = (Path) accessRule[1];
			String accessLevel = (String) accessRule[2];

			builder.append(repository.getName());
			builder.append(" ");
			builder.append(path.getPath());
			builder.append(" ");
			builder.append(userName);
			builder.append(" ");
			builder.append(accessLevel);
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Gets list of all users.
	 * 
	 * @param out Output stream
	 */
	private String getUsers(Document document) {
		StringBuilder builder = new StringBuilder();
		Object[] userNames = document.getUserNames();

		for (Object userName : userNames) {
			builder.append((String) userName);
			builder.append("\n");
		}

		return builder.toString();
	}

	private String processCommands(Document document, JSAPResult config) throws AppException, AppException {
		String retval = null;

		if (config.getBoolean(ARGS_STATISTICS_REPORT)) {
			retval = new StatisticsReport(document).generate();
		}
		else if (config.getBoolean(ARGS_SUMMARY_REPORT)) {
			retval = new SummaryReport(document).generate();
		}
		else if (config.getBoolean(ARGS_CLONE_USER)) {
			retval = cloneUser(document, config.getString(ARGS_NAME), config.getString(ARGS_NEW_NAME));
		}
		else if (config.getBoolean(ARGS_RENAME_USER)) {
			retval = renameUser(document, config.getString(ARGS_NAME), config.getString(ARGS_NEW_NAME));
		}
		else if (config.getBoolean(ARGS_DELETE_USER)) {
			retval = deleteUser(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_ADD_GROUPS)) {
			retval = addGroups(document, config.getString(ARGS_NAME), config.getStringArray(ARGS_GROUPS));
		}
		else if (config.getBoolean(ARGS_REMOVE_GROUPS)) {
			retval = removeGroups(document, config.getString(ARGS_NAME), config.getStringArray(ARGS_GROUPS));
		}
		else if (config.getBoolean(ARGS_COUNT_USERS)) {
			retval = countUsers(document);
		}
		else if (config.getBoolean(ARGS_GET_USERS)) {
			retval = getUsers(document);
		}
		else if (config.getBoolean(ARGS_GET_USER_GROUPS)) {
			retval = getUserGroups(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_GET_USER_RULES)) {
			retval = getUserRules(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_ADD_GROUP)) {
			retval = addGroup(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_CLONE_GROUP)) {
			retval = cloneGroup(document, config.getString(ARGS_NAME), config.getString(ARGS_NEW_NAME));
		}
		else if (config.getBoolean(ARGS_RENAME_GROUP)) {
			retval = renameGroup(document, config.getString(ARGS_NAME), config.getString(ARGS_NEW_NAME));
		}
		else if (config.getBoolean(ARGS_DELETE_GROUP)) {
			retval = deleteGroup(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_ADD_MEMBERS)) {
			retval = addMembers(document, config.getString(ARGS_NAME), config.getStringArray(ARGS_USERS), config
					.getStringArray(ARGS_GROUPS));
		}
		else if (config.getBoolean(ARGS_REMOVE_MEMBERS)) {
			retval = removeMembers(document, config.getString(ARGS_NAME), config.getStringArray(ARGS_USERS), config
					.getStringArray(ARGS_GROUPS));
		}
		else if (config.getBoolean(ARGS_COUNT_GROUPS)) {
			retval = countGroups(document);
		}
		else if (config.getBoolean(ARGS_GET_GROUPS)) {
			retval = getGroups(document);
		}
		else if (config.getBoolean(ARGS_GET_GROUP_MEMBERS)) {
			retval = getGroupMembers(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_GET_GROUP_GROUP_MEMBERS)) {
			retval = getGroupGroupMembers(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_GET_GROUP_USER_MEMBERS)) {
			retval = getGroupUserMembers(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_GET_GROUP_RULES)) {
			retval = getGroupRules(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_RENAME_REPOS)) {
			retval = renameRepository(document, config.getString(ARGS_NAME), config.getString(ARGS_NEW_NAME));
		}
		else if (config.getBoolean(ARGS_DELETE_REPOS)) {
			retval = deleteRepository(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_COUNT_REPOS)) {
			retval = countRepositories(document);
		}
		else if (config.getBoolean(ARGS_GET_REPOS)) {
			retval = getRepositories(document);
		}
		else if (config.getBoolean(ARGS_GET_REPOS_RULES)) {
			retval = getRepositoryRules(document, config.getString(ARGS_NAME));
		}
		else if (config.getBoolean(ARGS_ADD_RULE)) {
			retval = addRule(document, config.getString(ARGS_REPOS), config.getString(ARGS_PATH), config
					.getString(ARGS_USER), config.getString(ARGS_GROUP), config.getString(ARGS_ACCESS));
		}
		else if (config.getBoolean(ARGS_EDIT_RULE)) {
			retval = editRule(document, config.getString(ARGS_REPOS), config.getString(ARGS_PATH), config
					.getString(ARGS_USER), config.getString(ARGS_GROUP), config.getString(ARGS_NEW_REPOS), config
					.getString(ARGS_NEW_PATH), config.getString(ARGS_NEW_USER), config.getString(ARGS_NEW_GROUP),
					config.getString(ARGS_NEW_ACCESS));
		}
		else if (config.getBoolean(ARGS_DELETE_RULE)) {
			retval = deleteRule(document, config.getString(ARGS_REPOS), config.getString(ARGS_PATH), config
					.getString(ARGS_USER), config.getString(ARGS_GROUP));
		}
		else if (config.getBoolean(ARGS_COUNT_RULES)) {
			retval = countRules(document);
		}
		else if (config.getBoolean(ARGS_GET_RULES)) {
			retval = getRules(document);
		}

		return retval;
	}

	/**
	 * Removes user from groups.
	 * 
	 * @param userName Name of user
	 * @param groupNames List of groups
	 * @throws AppException, AppException Error occurred
	 */
	private String removeGroups(Document document, String userName, String[] groupNames) throws AppException,
			AppException {
		if (userName == null) {
			throw new AppException("application.error.userrequired");
		}

		if (groupNames == null || groupNames.length < 1) {
			throw new AppException("application.error.grouplistrequired");
		}

		User user = document.findUser(userName);

		if (user == null) {
			throw new AppException("application.error.unabletofinduser", userName);
		}

		for (String groupName : groupNames) {
			Group group = document.findGroup(groupName);

			if (group == null) {
				throw new AppException("application.error.unabletofindgroup", groupName);
			}

			user.removeGroup(group);
			group.removeUserMember(user);
		}

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Remove members from an existing group. User and group members may be removed at the same time. At least one user
	 * or group name must be specified.
	 * 
	 * @param groupName Name of group to be updated
	 * @param userNames User names of users to be removed
	 * @param groupNames Group names of groups to be removed
	 * @throws AppException, AppException Error occurred.
	 */
	private String removeMembers(Document document, String groupName, String[] userNames, String[] groupNames)
			throws AppException, AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		if ((userNames == null || userNames.length < 1) && (groupNames == null || groupNames.length < 1)) {
			throw new AppException("application.error.userorgrouplistrequired");
		}

		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		for (String memberUserName : userNames) {
			User memberUser = document.findUser(memberUserName);

			if (memberUser == null) {
				throw new AppException("application.error.unabletofinduser", memberUserName);
			}

			group.removeUserMember(memberUser);
		}

		for (String memberGroupName : groupNames) {
			Group memberGroup = document.findGroup(memberGroupName);

			if (memberGroup == null) {
				throw new AppException("application.error.unabletofindgroup", memberGroupName);
			}

			group.removeGroupMember(memberGroup);
		}

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Renames existing group name.
	 * 
	 * @param groupName Name of group to rename
	 * @param newGroupName New name for group
	 * @throws AppException, AppException Error occurred
	 */
	private String renameGroup(Document document, String groupName, String newGroupName) throws AppException,
			AppException {
		if (groupName == null) {
			throw new AppException("application.error.grouprequired");
		}

		if (newGroupName == null) {
			throw new AppException("application.error.newgrouprequired");
		}

		Group group = document.findGroup(groupName);

		if (group == null) {
			throw new AppException("application.error.unabletofindgroup", groupName);
		}

		group.setName(newGroupName);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Renames an existing repository.
	 * 
	 * @param repositoryName Name of repository
	 * @param newRepositoryName Repository new name
	 * @throws AppException, AppException Error occurred
	 */
	private String renameRepository(Document document, String repositoryName, String newRepositoryName)
			throws AppException, AppException {
		if (repositoryName == null) {
			throw new AppException("application.error.repositoryrequired");
		}

		if (newRepositoryName == null) {
			throw new AppException("application.error.newrepositoryrequired");
		}

		Repository repository = document.findRepository(repositoryName);

		if (repository == null) {
			throw new AppException("application.error.unabletofindrepository", repositoryName);
		}

		repository.setName(newRepositoryName);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * Renames an existing user.
	 * 
	 * @param userName Name of user
	 * @param newUserName User new name
	 * @throws AppException, AppException
	 */
	private String renameUser(Document document, String userName, String newUserName) throws AppException, AppException {
		if (userName == null) {
			throw new AppException("application.error.userrequired");
		}

		if (newUserName == null) {
			throw new AppException("application.error.newuserrequired");
		}

		User user = document.findUser(userName);

		if (user == null) {
			throw new AppException("application.error.unabletofinduser", userName);
		}

		user.setName(newUserName);

		return new FileGenerator(document).generate(true);
	}

	/**
	 * 
	 * @param args User specified arguments.
	 */
	public void run(String[] args) {
		try {
			ArgumentParser jsap = new ArgumentParser();

			// Input and Output Options
			jsap.addStringOption(ARGS_INPUT_FILE, ARGS_INPUT_FILE_SHORTFLAG, ARGS_INPUT_FILE_LONGFLAG, "inputfile");
			jsap.addStringOption(ARGS_OUTPUT_FILE, ARGS_OUTPUT_FILE_SHORTFLAG, ARGS_OUTPUT_FILE_LONGFLAG, "outputfile");

			// Help Options
			jsap.addSwitchOption(ARGS_HELP, ARGS_HELP_SHORTFLAG, ARGS_HELP, "help");
			jsap.addSwitchOption(ARGS_VERBOSE_HELP, null, ARGS_VERBOSE_HELP, "verbose");

			// Miscellaneous Options
			jsap.addSwitchOption(ARGS_VERSION, ARGS_VERSION_SHORTFLAG, ARGS_VERSION, "version");
			jsap.addSwitchOption(ARGS_NAME, null, ARGS_NAME, "name");

			// List of Values Options
			jsap.addListOption(ARGS_GROUPS, null, ARGS_GROUPS, "groups");
			jsap.addListOption(ARGS_USERS, null, ARGS_USERS, "users");

			// Single Value Options
			jsap.addStringOption(ARGS_REPOS, null, ARGS_REPOS, "repos");
			jsap.addStringOption(ARGS_PATH, null, ARGS_PATH, "path");
			jsap.addStringOption(ARGS_USER, null, ARGS_USER, "user");
			jsap.addStringOption(ARGS_GROUP, null, ARGS_GROUP, "group");
			jsap.addStringOption(ARGS_ACCESS, null, ARGS_ACCESS, "access");

			jsap.addStringOption(ARGS_NEW_NAME, null, ARGS_NEW_NAME, "newname");
			jsap.addStringOption(ARGS_NEW_REPOS, null, ARGS_NEW_REPOS, "newrepos");
			jsap.addStringOption(ARGS_NEW_PATH, null, ARGS_NEW_PATH, "newpath");
			jsap.addStringOption(ARGS_NEW_USER, null, ARGS_NEW_USER, "newuser");
			jsap.addStringOption(ARGS_NEW_GROUP, null, ARGS_NEW_GROUP, "newgroup");
			jsap.addStringOption(ARGS_NEW_ACCESS, null, ARGS_NEW_ACCESS, "newaccess");

			// User Actions
			jsap.addSwitchOption(ARGS_CLONE_USER, null, ARGS_CLONE_USER, "cloneuser");
			jsap.addSwitchOption(ARGS_RENAME_USER, null, ARGS_RENAME_USER, "renameuser");
			jsap.addSwitchOption(ARGS_DELETE_USER, null, ARGS_DELETE_USER, "deleteuser");
			jsap.addSwitchOption(ARGS_ADD_GROUPS, null, ARGS_ADD_GROUPS, "addgroups");
			jsap.addSwitchOption(ARGS_COUNT_USERS, null, ARGS_COUNT_USERS, "countusers");
			jsap.addSwitchOption(ARGS_GET_USERS, null, ARGS_GET_USERS, "getusers");
			jsap.addSwitchOption(ARGS_REMOVE_GROUPS, null, ARGS_REMOVE_GROUPS, "removegroups");
			jsap.addSwitchOption(ARGS_GET_USER_GROUPS, null, ARGS_GET_USER_GROUPS, "getusergroups");
			jsap.addSwitchOption(ARGS_GET_USER_RULES, null, ARGS_GET_USER_RULES, "getuserrules");

			// Group Actions
			jsap.addSwitchOption(ARGS_ADD_GROUP, null, ARGS_ADD_GROUP, "addgroup");
			jsap.addSwitchOption(ARGS_CLONE_GROUP, null, ARGS_CLONE_GROUP, "clonegroup");
			jsap.addSwitchOption(ARGS_RENAME_GROUP, null, ARGS_RENAME_GROUP, "renamegroup");
			jsap.addSwitchOption(ARGS_DELETE_GROUP, null, ARGS_DELETE_GROUP, "deletegroup");
			jsap.addSwitchOption(ARGS_ADD_MEMBERS, null, ARGS_ADD_MEMBERS, "addmembers");
			jsap.addSwitchOption(ARGS_REMOVE_MEMBERS, null, ARGS_REMOVE_MEMBERS, "removemembers");
			jsap.addSwitchOption(ARGS_COUNT_GROUPS, null, ARGS_COUNT_GROUPS, "countgroups");
			jsap.addSwitchOption(ARGS_GET_GROUPS, null, ARGS_GET_GROUPS, "getgroups");
			jsap.addSwitchOption(ARGS_GET_GROUP_MEMBERS, null, ARGS_GET_GROUP_MEMBERS, "getgroupmembers");
			jsap.addSwitchOption(ARGS_GET_GROUP_GROUP_MEMBERS, null, ARGS_GET_GROUP_GROUP_MEMBERS,
					"getgroupgroupmembers");
			jsap.addSwitchOption(ARGS_GET_GROUP_USER_MEMBERS, null, ARGS_GET_GROUP_USER_MEMBERS, "getgroupusermembers");
			jsap.addSwitchOption(ARGS_GET_GROUP_RULES, null, ARGS_GET_GROUP_RULES, "getgrouprules");

			// Repository Actions
			jsap.addSwitchOption(ARGS_RENAME_REPOS, null, ARGS_RENAME_REPOS, "renamerepos");
			jsap.addSwitchOption(ARGS_DELETE_REPOS, null, ARGS_DELETE_REPOS, "deleterepos");
			jsap.addSwitchOption(ARGS_COUNT_REPOS, null, ARGS_COUNT_REPOS, "countrepos");
			jsap.addSwitchOption(ARGS_GET_REPOS, null, ARGS_GET_REPOS, "getrepos");
			jsap.addSwitchOption(ARGS_GET_REPOS_RULES, null, ARGS_GET_REPOS_RULES, "getreposrules");

			// Access Rule Actions
			jsap.addSwitchOption(ARGS_ADD_RULE, null, ARGS_ADD_RULE, "addrule");
			jsap.addSwitchOption(ARGS_EDIT_RULE, null, ARGS_EDIT_RULE, "editrule");
			jsap.addSwitchOption(ARGS_DELETE_RULE, null, ARGS_DELETE_RULE, "deleterule");
			jsap.addSwitchOption(ARGS_COUNT_RULES, null, ARGS_COUNT_RULES, "countrules");
			jsap.addSwitchOption(ARGS_GET_RULES, null, ARGS_GET_RULES, "getrules");

			// Report Actions
			jsap.addSwitchOption(ARGS_STATISTICS_REPORT, null, ARGS_STATISTICS_REPORT, "statisticsreport");
			jsap.addSwitchOption(ARGS_SUMMARY_REPORT, null, ARGS_SUMMARY_REPORT, "summaryreport");

			// Process arguments
			JSAPResult config = jsap.parse(args);

			// Display usage if parsing was unsuccessful
			if (!config.success()) {
				System.err.println();
				System.err.println(ResourceUtil.getString("application.args.invalidsyntax"));
				System.err.println();

				System.exit(1);
			}

			// Arguments parsed properly, continue onto command execution
			executeCommands(jsap, config);
		}
		catch (JSAPException e) {
			System.err.println();
			System.err.println(ResourceUtil.getString("application.args.invalidsyntax"));
			System.err.println();
		}
	}
}
