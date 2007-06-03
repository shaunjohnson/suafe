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
package org.xiaoniu.suafe.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;

/**
 * Reprenents a single Subversion user authentication file.
 * 
 * @author Shaun Johnson
 */
public class Document {

	/**
	 * List of all AccessRules.
	 */
	protected static List<AccessRule> accessRules = null;

	/**
	 * Authentication file being edited.
	 */
	protected static File file = null;

	/**
	 * List of all Groups.
	 */
	protected static List<Group> groups = null;

	/**
	 * List of all Paths.
	 */
	protected static List<Path> paths = null;

	/**
	 * List of all Repositories.
	 */
	protected static List<Repository> repositories = null;

	/**
	 * List of all Users.
	 */
	protected static List<User> users = null;
	
	/**
	 * Indicates whether the document has unsaved changes.
	 */
	protected static boolean unsavedChanges = false;

	/**
	 * Resets all data stored within the document.
	 */
	public static void initialize() {
		file = null;
		unsavedChanges = false;

		users = new ArrayList<User>();
		groups = new ArrayList<Group>();
		repositories = new ArrayList<Repository>();
		accessRules = new ArrayList<AccessRule>();
		paths = new ArrayList<Path>();
	}
	
	/**
	 * Adds a new AccessRule specifying Group authorization.
	 * 
	 * @param path The Path in which the Group will have access.
	 * @param groupName The name of the Group that will have access.
	 * @param level The level of access.
	 * @return The newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addAccessRuleForGroup(Path path, String groupName, String level) throws ApplicationException {
		if (path == null) {
			throw new ValidatorException("Path is missing.");
		}
		
		Validator.validateGroupName(groupName);
		Validator.validateLevelOfAccess(level);		
	
		Group group = findGroup(groupName);				
		
		setUnsavedChanges();

		return addAccessRuleForGroup(path, group, level);
	}
	
	/**
	 * Adds a new AccessRule specifying group authorization.
	 * 
	 * @param path The Path in which the Group will have access.
	 * @param group The Group that will have access.
	 * @param level The level of access.
	 * @return The newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addAccessRuleForGroup(Path path, Group group, String level) throws ApplicationException {
		if (path == null) {
			throw new ValidatorException("Path is missing.");
		}
		
		Validator.validateLevelOfAccess(level);		
	
		if (group == null) {
			throw new ValidatorException("Group is missing.");
		}
		
		AccessRule accessRule = new AccessRule(path, group, level);
		
		group.addAccessRule(accessRule);
		
		if (path != null) {
			path.addAccessRule(accessRule);
		}
			
		accessRules.add(accessRule);
	
		setUnsavedChanges();
		
		return accessRule;
	}
	
	/**
	 * Adds a new AccessRule specifying group authorization.
	 * 
	 * @param repository Respository to which the rule is applied.
	 * @param pathString The Path in which the Group will have access.
	 * @param group The Group that will have access.
	 * @param level The level of access.
	 * @return The newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addAccessRuleForGroup(Repository repository, String pathString, Group group, String level) throws ApplicationException {
		Path path = addPath(repository, pathString);

		setUnsavedChanges();
		
		return addAccessRuleForGroup(path, group, level);
	}
	
	/**
	 * Adds a new AccessRule specifying User authorization.
	 * 
	 * @param path The Path in which the User will have access.
	 * @param userName The name of the User that will have access.
	 * @param level The level of access.
	 * @return The newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addAccessRuleForUser(Path path, String userName, String level) throws ApplicationException {
		if (path == null) {
			throw new ValidatorException("Path is missing.");
		}
		
		Validator.validateUserName(userName);
		Validator.validateLevelOfAccess(level);
	
		User user = addUser(userName);
		
		setUnsavedChanges();
				
		return addAccessRuleForUser(path, user, level);
	}
	
	/**
	 * Adds a new AccessRule specifying User authorization.
	 * 
	 * @param path The Path in which the User will have access.
	 * @param user The User that will have access.
	 * @param level The level of access.
	 * @return The newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addAccessRuleForUser(Path path, User user, String level) throws ApplicationException {
		if (path == null) {
			throw new ValidatorException("Path is missing.");
		}
		
		Validator.validateLevelOfAccess(level);
				
		AccessRule accessRule = new AccessRule(path, user, level);
		
		user.addAccessRule(accessRule);
		
		if (path != null) {
			path.addAccessRule(accessRule);
		}

		accessRules.add(accessRule);
	
		setUnsavedChanges();
		
		return accessRule;
	}

	/**
	 * Adds a new AccessRule specifying User authorization.
	 * 
	 * @param repository Respository to which the rule is applied
	 * @param pathString The Path in which the User will have access.
	 * @param user The User that will have access.
	 * @param level The level of access.
	 * @return The newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addAccessRuleForUser(Repository repository, String pathString, User user, String level) throws ApplicationException {
		Path path = addPath(repository, pathString);
		
		setUnsavedChanges();
		
		return addAccessRuleForUser(path, user, level);
	}
		
	/**
	 * Adds a new Group if one by the same name doesn't already exist.
	 * If an existing group exists it will be returned intact.
	 * 
	 * @param groupName Name of the Group.
	 * @param groupMembers Group members of the new Group.
	 * @param userMembers User members of the new Group.
	 * @return Newly created or found Group.
	 * @throws ApplicationException
	 */
	public static Group addGroup(String groupName, List groupMembers, List userMembers) throws ApplicationException {
		Validator.validateGroupName(groupName);		
		
		Group group = findGroup(groupName);
	
		if (group == null) {
			List<Group> groupMemberList = new ArrayList<Group>();
			List<User> userMemberList = new ArrayList<User>();
			
			group = new Group(groupName, groupMemberList, userMemberList);
	
			// Add Group members
			if (groupMembers != null) {
				for (Object object : groupMembers) {
					Group member = null;
					
					if (object instanceof Group) {
						member = (Group)object;
					}
					else {
						member = addGroup((String)object, null, null);
					}
					
					member.addGroup(group);
					groupMemberList.add(member);
				}
			}

			// Add User members
			if (userMembers != null) {
				
				for (Object object : userMembers) {
					User member = null;
					
					if (object instanceof User) {
						member = (User)object;
					}
					else {
						member = addUser((String)object);	
					}
					
					member.addGroup(group);
					userMemberList.add(member);
				}
			}
			
			groups.add(group);
		}
		
		setUnsavedChanges();
		
		return group;
	}

	/**
	 * Adds a new Path if one by in the same repository and path doesn't exist.
	 * Returns the found path if a matching one is found.
	 * 
	 * @param repository Repository where the path exists.
	 * @param relativePath Relative path within the Repository.
	 * @return The newly create or found Path.
	 * @throws ApplicationException
	 */
	public static Path addPath(Repository repository, String relativePath) throws ApplicationException {
		Validator.validatePath(relativePath);
		
		Path path = findPath(repository, relativePath);
		
		if (path == null) {
			path = new Path(repository, relativePath);
			
			paths.add(path);
			
			if (repository != null) {
				repository.addPath(path);
			}
		}
		
		setUnsavedChanges();
		
		return path;
	}

	/**
	 * Adds a new Repository if one by the same name doesn't exist.
	 * Returns the found Repository if a matching one is found.
	 * 
	 * @param repositoryName Name of the Repository.
	 * @return The newly created or found Repository.
	 * @throws ApplicationException
	 */
	public static Repository addRepository(String repositoryName) throws ApplicationException {
		Validator.validateRepositoryName(repositoryName);
		
		Repository repository = findRepository(repositoryName);
	
		if (repository == null) {
			repository = new Repository(repositoryName);
	
			repositories.add(repository);
		}
	
		setUnsavedChanges();
		
		return repository;
	}

	/**
	 * Adds a new server level AccessRule specifying Group authorization.
	 * 
	 * @param groupName Name of the Group that will have access.
	 * @param level The level of access that is allowed.
	 * @return Newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addServerAccessRuleForGroup(String groupName, String level) throws ApplicationException {
		Validator.validateGroupName(groupName);
		Validator.validateLevelOfAccess(level);
		
		Group group = findGroup(groupName);
		
		if (group == null) {
			throw new ValidatorException("Group is missing.");
		}
					
		Path path = addPath(null, "/");
		
		AccessRule accessRule = new AccessRule(path, group, level);
		
		path.addAccessRule(accessRule);
		
		accessRules.add(accessRule);
		
		setUnsavedChanges();
	
		return accessRule;
	}
	
	/**
	 * Adds a new server level AccessRule specifying User authorization.
	 * 
	 * @param userName Name of the User that will have access.
	 * @param level The level of access that is allowed.
	 * @return Newly created AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule addServerAccessRuleForUser(String userName, String level) throws ApplicationException {
		Validator.validateUserName(userName);
		Validator.validateLevelOfAccess(level);
		
		User user = addUser(userName);
							
		Path path = addPath(null, "/");
		
		AccessRule accessRule = new AccessRule(path, user, level);
		
		path.addAccessRule(accessRule);
		
		accessRules.add(accessRule);
		
		setUnsavedChanges();
	
		return accessRule;
	}

	/**
	 * Adds a new User to the document.
	 * 
	 * @param userName Name of the new user.
	 * @return Newly created User.
	 * @throws ApplicationException
	 */
	public static User addUser(String userName) throws ApplicationException {
		Validator.validateUserName(userName);
		
		User user = findUser(userName);
	
		if (user == null) {
			user = new User(userName);
	
			users.add(user);
		}
		
		setUnsavedChanges();
	
		return user;
	}
	
	/**
	 * Deletes an existing AccessRule. Either group or user must be specified,
	 * but in no instances should both be provided.
	 * 
	 * @param repositoryName Name of Repository to which the rule applies.
	 * @param pathString Path in the repository to which the rule applies.
	 * @param group Group to which the rule applies.
	 * @param user User to which the rule applies.
	 * @throws ApplicationException
	 */
	public static void deleteAccessRule(String repositoryName, String pathString, Group group, User user) throws ApplicationException {
		Repository repository = null;	
		AccessRule accessRule = null;
		
		if (repositoryName != null) {
			repository = findRepository(repositoryName);
		}
		
		if (group != null) {
			accessRule = findGroupAccessRule(repository, pathString, group);
			group.removeAccessRule(accessRule);
		}
		else if (user != null) {
			accessRule = findUserAccessRule(repository, pathString, user);
			
			if (accessRule == null) {
				throw new ApplicationException(ResourceUtil.getString("application.error.unabletofindrule"));
			}
			
			user.removeAccessRule(accessRule);
		}
		
		accessRule.getPath().removeAccessRule(accessRule);
		accessRules.remove(accessRule);
		
		setUnsavedChanges();
	}

	/**
	 * Deletes an existing Group. Deletes all Group AccessRules.
	 * 
	 * @param group Group to be removed.
	 * @throws ApplicationException
	 */
	public static void deleteGroup(Group group) throws ApplicationException {
		deleteGroupAccessRules(group);
		removeGroupMembers(group);
		groups.remove(group);
		
		setUnsavedChanges();
	}

	/**
	 * Deletes all AccessRules that apply to a specific Group.
	 * 
	 * @param group Group whose AccessRules are to be deleted.
	 */
	private static void deleteGroupAccessRules(Group group) {
		List<AccessRule> deleteList = new ArrayList<AccessRule>();
		
		for (AccessRule rule : accessRules) {
			if (rule.getGroup() != null && rule.getGroup().equals(group)) {
				deleteList.add(rule);
			}
		}
		
		accessRules.removeAll(deleteList);
		
		setUnsavedChanges();
	}

	/**
	 * Delete all groups in array.
	 * 
	 * @param groups Array of Groups to be deleted.
	 * @throws ApplicationException
	 */
	public static void deleteGroups(Object[] groups) throws ApplicationException {		
		for (int i = 0; i < groups.length; i++) {
			deleteGroup((Group)groups[i]);
		}
		
		setUnsavedChanges();
	}

	/**
	 * Deletes an existing path.
	 * 
	 * @param path Path to be deleted.
	 * @throws ApplicationException
	 */
	public static void deletePath(Path path) throws ApplicationException {
		deletePathAccessRules(path);	
		
		if (path.getRepository() != null) {
			path.getRepository().removePath(path);
		}
		
		paths.remove(path);
		
		setUnsavedChanges();
	}

	/**
	 * Deletes all AccessRules for a path.
	 * 
	 * @param path Path whose AccessRules are to be deleted.
	 */
	private static void deletePathAccessRules(Path path) {
		List<AccessRule> deleteList = new ArrayList<AccessRule>();

		for (AccessRule rule : accessRules) {	
			if (rule.getPath() != null && rule.getPath().equals(path)) {
				deleteList.add(rule);
				
				if (rule.getGroup() != null) {
					rule.getGroup().removeAccessRule(rule);
				}
				
				if (rule.getUser() != null) {
					rule.getUser().removeAccessRule(rule);
				}
			}
		}
		
		accessRules.removeAll(deleteList);
		
		setUnsavedChanges();
	}

	/**
	 * Delete an existing repository.
	 * 
	 * @param repository Repository to be deleted.
	 * @throws ApplicationException
	 */
	public static void deleteRepository(Repository repository) throws ApplicationException {
		deleteRepositoryAccessRules(repository);
		deleteRepositoryPaths(repository);
		repositories.remove(repository);
		
		setUnsavedChanges();
	}

	/**
	 * Deletes all AccessRules for a Repository.
	 * 
	 * @param repository Repository whose AccessRules are to be deleted.
	 */
	private static void deleteRepositoryAccessRules(Repository repository) {
		List<AccessRule> deleteList = new ArrayList<AccessRule>();

		for (AccessRule rule : accessRules) {	
			if (rule.getPath().getRepository()!= null && rule.getPath().getRepository().equals(repository)) {
				deleteList.add(rule);
				
				if (rule.getGroup() != null) {
					rule.getGroup().removeAccessRule(rule);
				}
				
				if (rule.getUser() != null) {
					rule.getUser().removeAccessRule(rule);
				}
			}
		}
		
		accessRules.removeAll(deleteList);
		
		setUnsavedChanges();
	}

	/** 
	 * Deletes all Paths for a Repository.
	 * 
	 * @param repository Repository whose Paths are to be deleted.
	 * @throws ApplicationException
	 */
	private static void deleteRepositoryPaths(Repository repository) throws ApplicationException {
		List<Path> deleteList = new ArrayList<Path>();
		
		for (Path path : repository.getPaths()) {
			deleteList.add(path);
			deletePathAccessRules(path);
		}
		
		paths.removeAll(deleteList);
		
		setUnsavedChanges();
	}
	
	/**
	 * Deletes array of Repositories.
	 * 
	 * @param repositories Repositories to be deleted.
	 * @throws ApplicationException
	 */
	public static void deleteRepositories(Object[] repositories) throws ApplicationException {
		for (int i = 0; i < repositories.length; i++) {
			deleteRepository((Repository)repositories[i]);
		}
		
		setUnsavedChanges();
	}
	
	/**
	 * Deletes an existing User.
	 * 
	 * @param user User to be deleted.
	 * @throws ApplicationException
	 */
	public static void deleteUser(User user) throws ApplicationException {
		deleteUserAccessRules(user);		
		removeUserFromAssignedGroups(user);
		users.remove(user);
		
		setUnsavedChanges();
	}

	/**
	 * Deletes all AccessRules for a user.
	 * 
	 * @param user User whose AccessRules are to be deleted.
	 */
	private static void deleteUserAccessRules(User user) {
		List<AccessRule> deleteList = new ArrayList<AccessRule>();

		for (AccessRule rule : accessRules) {	
			if (rule.getUser() != null && rule.getUser().equals(user)) {
				// Remove access rule from path
				rule.getPath().removeAccessRule(rule);
				
				// Add rule to list for deletion1
				deleteList.add(rule);
			}
		}
		
		accessRules.removeAll(deleteList);
		
		setUnsavedChanges();
	}
	
	/**
	 * Deletes an array of Users.
	 * 
	 * @param users Users to be deleted.
	 * @throws ApplicationException
	 */
	public static void deleteUsers(Object[] users) throws ApplicationException {
		for (int i = 0; i < users.length; i++) {
			deleteUser((User)users[i]);
		}
		
		setUnsavedChanges();
	}
	
	/**
	 * Locates an existing Group by name.
	 * 
	 * @param groupName Name of Group to find.
	 * @return Found group.
	 * @throws ApplicationException
	 */
	public static Group findGroup(String groupName) throws ApplicationException {
		Validator.validateGroupName(groupName);
		
		Group group = null;
		boolean found = false;
	
		if (groups != null) {
			int size = groups.size();
	
			for (int i = 0; i < size; i++) {
				group = (Group) groups.get(i);
	
				if (group.getName().equals(groupName)) {
					found = true;
					break;
				}
			}
	
			if (!found) {
				group = null;
			}
		}
	
		return group;
	}

	/**
	 * Locates an AccessRules by Repository, path and Group.
	 * 
	 * @param repository Respository referenced by AccessRule.
	 * @param pathString Path referenced by AccessRule.
	 * @param group Group referenced by AccessRule.
	 * @return Found AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule findGroupAccessRule(Repository repository, String pathString, Group group) throws ApplicationException {
		if (accessRules == null) {
			return null;
		}
		
		Path path = findPath(repository, pathString);
		
		if (path == null || group == null || group.getAccessRules() == null) {
			return null;
		}
		
		AccessRule foundRule = null;
		
		for (AccessRule rule : group.getAccessRules()) {
			if (rule.getPath() == path) {
				foundRule = rule;
				break;
			}
		}
		
		return foundRule;
	}
	
	/**
	 * Locates an AccessRule by Repository, path and User
	 * 
	 * @param repository Repository referenced by AccessRule.
	 * @param pathString Path referenced by AccessRule.
	 * @param user User referenced by AccessRule.
	 * @return Found AccessRule.
	 * @throws ApplicationException
	 */
	public static AccessRule findUserAccessRule(Repository repository, String pathString, User user) throws ApplicationException {
		if (accessRules == null) {
			return null;
		}
		
		Path path = findPath(repository, pathString);
		
		if (path == null || user == null || user.getAccessRules() == null) {
			return null;
		}
		
		AccessRule foundRule = null;
		
		for (AccessRule rule : user.getAccessRules()) {	
			if (rule.getPath() == path) {
				foundRule = rule;
				break;
			}
		}
		
		return foundRule;
	}
	
	/**
	 * Locates a Path by repository and path.
	 * 
	 * @param repositoryName Name of repository referenced by Path.
	 * @param pathString Path referenced by Path.
	 * @return Found Path.
	 * @throws ApplicationException
	 */
	public static Path findPath(String repositoryName, String pathString) throws ApplicationException {		
		Repository repository = findRepository(repositoryName);
	
		return findPath(repository, pathString);
	}

	/**
	 * Locates a Path by Repository and path.
	 * 
	 * @param repository Repository referenced by Path.
	 * @param pathString Path referenced by Path.
	 * @return Found Path.
	 * @throws ApplicationException
	 */
	public static Path findPath(Repository repository, String pathString) throws ApplicationException {
		Validator.validatePath(pathString);
		
		Path path = null;
		boolean found = false;
	
		if (paths != null) {
			int size = paths.size();
	
			for (int i = 0; i < size; i++) {
				path = (Path)paths.get(i);
	
				if (path.getRepository() == repository && path.getPath().equals(pathString)) {
					found = true;
					break;
				}
			}
	
			if (!found) {
				path = null;
			}
		}
	
		return path;
	}

	/**
	 * Locates a server level Path.
	 * 
	 * @param pathString Path referenced by the Path.
	 * @return Found Path.
	 * @throws ApplicationException
	 */
	public static Path findServerPath(String pathString) throws ApplicationException {
		Path path = null;
		boolean found = false;
	
		if (paths != null) {
			int size = paths.size();
	
			for (int i = 0; i < size; i++) {
				path = (Path)paths.get(i);
	
				if (path.getRepository() == null && path.getPath().equals(pathString)) {
					found = true;
					break;
				}
			}
	
			if (!found) {
				path = null;
			}
		}
	
		return path;
	}
	
	/**
	 * Locates a repository by name.
	 * 
	 * @param repositoryName Name of Repository to locate.
	 * @return Found Repository.
	 * @throws ApplicationException
	 */
	public static Repository findRepository(String repositoryName) throws ApplicationException {
		Validator.validateRepositoryName(repositoryName);
		
		Repository repository = null;
		boolean found = false;
	
		if (repositories != null) {
			int size = repositories.size();
	
			for (int i = 0; i < size; i++) {
				repository = (Repository) repositories.get(i);
	
				if (repository.getName().equals(repositoryName)) {
					found = true;
					break;
				}
			}
	
			if (!found) {
				repository = null;
			}
		}
	
		return repository;
	}

	/**
	 * Finds a User by name.
	 * 
	 * @param userName Name of User to locate.
	 * @return Found User.
	 * @throws ApplicationException
	 */
	public static User findUser(String userName) throws ApplicationException {
		Validator.validateUserName(userName);
		
		User user = null;
		boolean found = false;
	
		if (users != null) {
			int size = users.size();
	
			for (int i = 0; i < size; i++) {
				user = (User) users.get(i);
	
				if (user.getName().equals(userName)) {
					found = true;
					break;
				}
			}
	
			if (!found) {
				user = null;
			}
		}
	
		return user;
	}
	
	/**
	 * @return Returns the file.
	 */
	public static File getFile() {
		return file;
	}
	
	/**
	 * Retrieves a list of all AccessRule objects.
	 * 
	 * @return List of AccessRule objects
	 */
	public static List<AccessRule> getAccessRules() {
		return accessRules;
	}

	/**
	 * Gets an array of AccessRules data in which the Group is referenced.
	 * The two dimensional array returned contains the Repository name,
	 * relative path and level of access for each AccessRule in which the
	 * Group is referenced.
	 * 
	 * @param group Group.
	 * @return Object array of AccessRule information.
	 * @throws ApplicationException
	 */
	public static Object[][] getGroupAccessRules(Group group) throws ApplicationException {
		if (group == null || group.getAccessRules() == null) {
			return null;
		} else {
			List accessRules = group.getAccessRules(); 
			int size = accessRules.size();
			Object[][] accessRulesList = new Object[size][3];
	
			for (int i = 0; i < size; i++) {
				AccessRule rule = (AccessRule) accessRules.get(i);
	
				accessRulesList[i][0] = rule.getPath().getRepository();
				accessRulesList[i][1] = rule.getPath();
				accessRulesList[i][2] = rule.getLevelFullName();
			}
	
			return accessRulesList;
		}
	}

	/**
	 * Gets an array of all Group names.
	 * 
	 * @return Object array of Group names.
	 */
	public static Object[] getGroupNames() {
		if (groups == null) {
			return null;
		} else {
			int size = groups.size();
			Object[] groupNames = new Object[size];
	
			for (int i = 0; i < size; i++) {
				groupNames[i] = ((Group) groups.get(i)).getName();
			}
	
			Arrays.sort(groupNames);
	
			return groupNames;
		}
	}
	
	/**
	 * Gets an array of all Groups.
	 * 
	 * @return Group array.
	 */
	public static Group[] getGroupsArray() {
		if (groups == null) {
			return null;
		} else {	
			Collections.sort(groups);
	
			return groups.toArray(new Group[0]);
		}
	}
	
	/**
	 * Gets an array of all Group objects.
	 * 
	 * @return Group object array.
	 */
	public static Object[] getGroupObjects() {
		if (groups == null) {
			return null;
		} else {	
			Collections.sort(groups);
	
			return (Object[]) groups.toArray();
		}
	}

	/**
	 * Gets names of all Groups that are a member of a Group.
	 * 
	 * @param groupName Name of Group whose member names are to be returned.
	 * @return Names of all Groups members of Group
	 * @throws ApplicationException
	 */
	public static Object[] getGroupMemberGroupNames(String groupName) throws ApplicationException {
		Group group = findGroup(groupName);
		
		if (group == null || group.getGroupMembers() == null) {
			return null;
		} else {
			List groups = group.getGroupMembers();
			int size = groups.size();
			Object[] groupNames = new Object[size];
	
			for (int i = 0; i < size; i++) {
				groupNames[i] = ((Group) groups.get(i)).getName();
			}
	
			Arrays.sort(groupNames);
	
			return groupNames;
		}
	}
	
	/**
	 * Gets all Group objects that are a member of a Group.
	 * 
	 * @param group Group whose Group members are to be returned.
	 * @return Group object array containg all member Groups.
	 * @throws ApplicationException
	 */
	public static Object[] getGroupMemberObjects(Group group) throws ApplicationException {
		if (group == null || group.getGroupMembers() == null) {
			return null;
		} else {
			Collections.sort(group.getGroupMembers());
			Collections.sort(group.getUserMembers());
			
			List<Object> combinedList = new ArrayList<Object>();
			
			combinedList.addAll(group.getGroupMembers());
			combinedList.addAll(group.getUserMembers());
	
			return combinedList.toArray();
		}
	}
	
	/**
	 * Gets all Groups that are a member of a Group.
	 * 
	 * @param group Group whose Group members are to be returned.
	 * @return Group array containg all member Groups.
	 * @throws ApplicationException
	 */
	public static Group[] getGroupMemberGroups(Group group) throws ApplicationException {
		if (group == null || group.getGroupMembers() == null) {
			return null;
		} else {
			Collections.sort(group.getGroupMembers());
	
			return group.getGroupMembers().toArray(new Group[0]);
		}
	}
	
	/**
	 * Gets names of all of a Group's User members.
	 * 
	 * @param groupName Name of Group whose member names are to be returned.
	 * @return Names of all User members of a Group. 
	 * @throws ApplicationException
	 */
	public static Object[] getGroupMemberUserNames(String groupName) throws ApplicationException {
		Group group = findGroup(groupName);
		
		if (group == null || group.getUserMembers() == null) {
			return null;
		} else {
			List users = group.getUserMembers();
			int size = users.size();
			Object[] userNames = new Object[size];
	
			for (int i = 0; i < size; i++) {
				userNames[i] = ((User) users.get(i)).getName();
			}
	
			Arrays.sort(userNames);
	
			return userNames;
		}
	}
	
	/**
	 * Gets all Users that are a member of a Group.
	 * 
	 * @param group Group whose User members are to be returned.
	 * @return User array containg all member User.
	 * @throws ApplicationException
	 */
	public static User[] getGroupMemberUsers(Group group) throws ApplicationException {
		if (group == null || group.getUserMembers() == null) {
			return null;
		} else {
			Collections.sort(group.getUserMembers());
	
			return group.getUserMembers().toArray(new User[0]);
		}
	}

	/**
	 * Gets the list of all Groups.
	 * 
	 * @return List of Groups.
	 */
	public static List<Group> getGroups() {
		return groups;
	}
	
	public static List<User> getUsers() {
		return users;
	}

	/**
	 * Gets an array of AccessRules data for the AccessRules defined for
	 * the Path. The two dimensional array returned contains the User or 
	 * Group name and level of access for each AccessRule defined for the
	 * Path.
	 * 
	 * @param path Relative path within the Repository.
	 * @return Object array of AccessRule information.
	 * @throws ApplicationException
	 */
	public static Object[][] getPathAccessRules(Path path) throws ApplicationException {
		if (path == null || path.getAccessRules() == null) {
			return null;
		} else {
			List accessRules = path.getAccessRules(); 
			int size = accessRules.size();
			Object[][] accessRulesList = new Object[size][2];
	
			for (int i = 0; i < size; i++) {
				AccessRule rule = (AccessRule) accessRules.get(i);
	
				if (rule.getGroup() == null) {
					accessRulesList[i][0] = rule.getUser();
				} else {
					accessRulesList[i][0] = rule.getGroup();
				}
				
				accessRulesList[i][1] = rule.getLevelFullName();
			}
	
			return accessRulesList;
		}
	}

	public static List<Path> getPaths() {
		return paths;
	}

	/**
	 * Gets an array of AccessRules data in which the Repository is 
	 * referenced. The two dimensional array returned contains the 
	 * relative path, User or Group name and level of access for 
	 * each AccessRule in which the Repostiory is referenced.
	 * 
	 * @param repository Repository.
	 * @return Object array of AccessRule information.
	 * @throws ApplicationException
	 */
	public static Object[][] getRepositoryAccessRules(Repository repository) throws ApplicationException {
		if (repository == null || repository.getPaths() == null) {
			return null;
		} else {
			List<AccessRule> accessRules = new ArrayList<AccessRule>();
			
			for (Path path : repository.getPaths()) {	
				if (path.getAccessRules() != null) {
					for (AccessRule rule : path.getAccessRules()) {
						accessRules.add(rule);
					}
				}
			}
			 
			int size = accessRules.size();
			Object[][] accessRulesList = new Object[size][3];

			for (int i = 0; i < size; i++) {
				AccessRule rule = (AccessRule) accessRules.get(i);

				accessRulesList[i][0] = rule.getPath();
				
				if (rule.getGroup() == null) {
					accessRulesList[i][1] = rule.getUser();
				} else {
					accessRulesList[i][1] = rule.getGroup();
				}
				
				accessRulesList[i][2] = rule.getLevelFullName();
			}

			return accessRulesList;
		}
	}

	/**
	 * Gets list of all Repositories.
	 * 
	 * @return List of Repositories.
	 */
	public static List<Repository> getRepositories() {
		return repositories;
	}

	/**
	 * Gets list of all Repository names.
	 * 
	 * @return Object array of names.
	 */
	public static Object[] getRepositoryNames() {
		if (repositories == null) {
			return null;
		} else {
			int size = repositories.size();
			Object[] repositoryNames = new Object[size];

			for (int i = 0; i < size; i++) {
				repositoryNames[i] = ((Repository) repositories.get(i))
						.getName();
			}

			Arrays.sort(repositoryNames);

			return repositoryNames;
		}
	}
	
	/**
	 * Gets array of repository objects.
	 * 
	 * @return Array of Repository objects.
	 */
	public static Object[] getRepositoryObjects() {
		if (repositories == null) {
			return null;
		} else {
			Collections.sort(repositories);

			return (Object[])repositories.toArray();
		}
	}
	
	/**
	 * Gets all server AccessRule data as two-dimensional array.
	 * 
	 * Array contents:
	 * [0] - Repository object
	 * [1] - Path object
	 * [2] - User or Group object
	 * [3] - Full text name of access level
	 * 
	 * @return Two-dimensional array of AccessRule data.
	 * @throws ApplicationException
	 */
	public static Object[][] getServerAccessRules() throws ApplicationException {
		if (accessRules == null) {
			return null;
		} else {
			Collections.sort(accessRules);
			
			int size = accessRules.size();
			Object[][] accessRulesList = new Object[size][4];
			
			for (int i = 0; i < size; i++) {
				AccessRule rule = (AccessRule) accessRules.get(i);
				
				accessRulesList[i][0] = rule.getPath().getRepository();
				
				accessRulesList[i][1] = rule.getPath();
				
				if (rule.getGroup() == null) {
					accessRulesList[i][2] = rule.getUser();
				} else {
					accessRulesList[i][2] = rule.getGroup();
				}
				
				accessRulesList[i][3] = rule.getLevelFullName();
			}
			
			return accessRulesList;
		}
	}
	
	/**
	 * Gets an array of AccessRules data in which the User is referenced.
	 * The two dimensional array returned contains the Repository name,
	 * relative path and level of access for each AccessRule in which the
	 * User is referenced.
	 * 
	 * @param userName Name of the User.
	 * @return Object array of AccessRule information.
	 * @throws ApplicationException
	 */
	public static Object[][] getUserAccessRules(String userName) throws ApplicationException {
		User user = findUser(userName);
		
		if (user == null || user.getAccessRules() == null) {
			return null;
		} else {
			List accessRules = user.getAccessRules(); 
			int size = accessRules.size();
			Object[][] accessRulesList = new Object[size][3];
	
			for (int i = 0; i < size; i++) {
				AccessRule rule = (AccessRule) accessRules.get(i);
	
				accessRulesList[i][0] = rule.getPath().getRepository();
				accessRulesList[i][1] = rule.getPath();
				accessRulesList[i][2] = rule.getLevelFullName();
			}
	
			return accessRulesList;
		}
	}

	/**
	 * Gets all AccessRule data for rules assigned to a User.
	 * 
	 * @param user User whose rules are to be returned.
	 * @return Two-dimensional array of AccessRule data.
	 * @throws ApplicationException
	 */
	public static Object[][] getUserAccessRuleObjects(User user) throws ApplicationException {
		if (user == null || user.getAccessRules() == null) {
			return null;
		} else {
			List accessRules = user.getAccessRules(); 
			int size = accessRules.size();
			Object[][] accessRulesList = new Object[size][3];
	
			for (int i = 0; i < size; i++) {
				AccessRule rule = (AccessRule) accessRules.get(i);
	
				accessRulesList[i][0] = rule.getPath().getRepository();
				accessRulesList[i][1] = rule.getPath();
				accessRulesList[i][2] = rule.getLevelFullName();
			}
	
			return accessRulesList;
		}
	}
	
	/**
	 * Gets an array of names for all Groups in which the User is a member.
	 * 
	 * @param userName Name of the User.
	 * @return Object array of Group names.
	 * @throws ApplicationException
	 */
	public static Object[] getUserGroupNames(String userName) throws ApplicationException {
		User user = findUser(userName);
		
		if (user == null || user.getGroups() == null) {
			return null;
		} else {
			List groups = user.getGroups();
			int size = groups.size();
			Object[] groupNames = new Object[size];
	
			for (int i = 0; i < size; i++) {
				groupNames[i] = ((Group) groups.get(i)).getName();
			}
	
			Arrays.sort(groupNames);
	
			return groupNames;
		}
	}
	
	/**
	 * Gets array of Groups in which the User is a member.
	 * 
	 * @param user User whose Groups are to be returned
	 * @return Array of Groups in which the User is a member.
	 * @throws ApplicationException
	 */
	public static Group[] getUserGroupsArray(User user) throws ApplicationException {		
		if (user == null || user.getGroups() == null) {
			return null;
		} else {
			List<Group> groups = user.getGroups();
			
			Collections.sort(groups);
			
			return groups.toArray(new Group[0]);
		}
	}

	/**
	 * Gets array of Group objects in which the User is a member.
	 * 
	 * @param user User whose Group objects are to be returned.
	 * @return Array of Group objects in which the User is a member.
	 * @throws ApplicationException
	 */
	public static Object[] getUserGroupObjects(User user) throws ApplicationException {		
		if (user == null || user.getGroups() == null) {
			return null;
		} else {
			List<Group> groups = user.getGroups();
			
			Collections.sort(groups);
			
			return (Object[])groups.toArray();
		}
	}
	
	/**
	 * Gets all Users as object array.
	 * 
	 * @return Array of all User objects.
	 */
	public static Object[] getUserObjects() {
		if (users == null) {
			return null;
		} else {
			Collections.sort(users);
			
			return (Object[])users.toArray();
		}
	}
	
	/**
	 * Gets all Users, except * as object array. The user name * represents
	 * "all users". This method returns all Users except this special one.
	 * 
	 * @return Array of User objects.
	 * @throws ApplicationException
	 */
	public static User[] getUserObjectsExcludeAllUsers() throws ApplicationException {
		if (users == null) {
			return null;
		} else {
			List<User> filteredUsers = users;
			User allUsers = findUser(Constants.ALL_USERS);
			
			if (allUsers != null) {
				filteredUsers.remove(allUsers);
			}
			
			Collections.sort(filteredUsers);
			
			return filteredUsers.toArray(new User[0]);
		}
	}
	
	/**
	 * Gets names of all Users.
	 * 
	 * @return Array of User name objects.
	 */
	public static Object[] getUserNames() {
		if (users == null) {
			return null;
		} else {
			int size = users.size();
			Object[] userNames = new Object[size];

			for (int i = 0; i < size; i++) {
				userNames[i] = ((User) users.get(i)).getName();
			}

			Arrays.sort(userNames);

			return userNames;
		}
	}
	
	/**
	 * Determines if the Document contains any data.
	 * If any Users, Groups, Repositories, Paths and AccessRules are all
	 * empty then this returns true.
	 * 
	 * @return true if the Document doesn't contain data.
	 */
	public static boolean isEmpty() {
		return users.size() == 0 && 
			groups.size() == 0 &&
			repositories.size() == 0 &&
			accessRules.size() == 0 &&
			paths.size() == 0;
	}
	
	/**
	 * Determines if the document has any unsaved changes.
	 * 
	 * @return True if there are any unsaved changes.
	 */
	public static boolean hasUnsavedChanges() {
		return unsavedChanges;
	}
	
	/**
	 * Sets the unsaved changes flag to true. Indicates that there are changes
	 * not persisted to a file.
	 */
	public static void setUnsavedChanges() {
		unsavedChanges = true;
	}
	
	/**
	 * Resets the unsaved changes flag. Indicates that there are no changes
	 * that need to be saved.
	 */
	public static void resetUnsavedChangesFlag() {
		unsavedChanges = false;
	}

	/**
	 * Removes a User from all Groups in which he is a member. 
	 * 
	 * @param user User to be processed.
	 */
	private static void removeUserFromAssignedGroups(User user) {
		for (Group group : user.getGroups()) {
			group.getUserMembers().remove(user);
		}
		
		user.setGroups(new ArrayList<Group>());
		
		setUnsavedChanges();
	}
	
	/**
	 * Saves the file object representing the currently edited authz file.
	 * 
	 * @param file The file to set.
	 */
	public static void setFile(File file) {
		Document.file = file;
	}

	/**
	 * Updates a User's group membership. User is removed from all Groups he
	 * is currently a member of and is made a member of all specified Groups.
	 * 
	 * @param user User to be processed.
	 * @param newGroupObjects Vector of new Group membership.
	 * @throws ApplicationException
	 */
	public static void changeUserMembership(User user, Vector<Group> newGroupObjects) throws ApplicationException {
		// Remove from old groups
		 removeUserFromAssignedGroups(user);
		
		// Add to new groups
		List<Group> newGroups = new ArrayList<Group>(newGroupObjects.size());
		
		for (Group group : newGroupObjects) {
			group.addUserMember(user);
			newGroups.add(group);
		}
		
		user.setGroups(newGroups);
		
		setUnsavedChanges();
	}

	/**
	 * Removes all members of a Group.
	 * 
	 * @param group Group whose members are to be removed.
	 */
	private static void removeGroupMembers(Group group) {
		// Remove groups
		for (Group member : group.getGroupMembers()) {
			member.removeGroup(group);
		}
				
		group.getGroupMembers().clear();
		
		// Removed users
		for (User member : group.getUserMembers()) {
			member.removeGroup(group);
		}
				
		group.getUserMembers().clear();
		
		setUnsavedChanges();
	}	
	
	/**
	 * Changes the list of members of a Group. Current Group members are
	 * removed. All new Group and User members are added. 
	 * 
	 * @param group Group to be processed.
	 * @param groupMembers New group memebers.
	 * @param userMembers New User members.
	 * @throws ApplicationException
	 */
	public static void changeGroupMembers(Group group, Vector<Group> groupMembers, Vector<User> userMembers) throws ApplicationException {
		Group results = hasCircularReference(group, groupMembers);
		if (results != null) {
			Object[] args = new Object[2];
			
			args[0] = results;
			args[1] = group;
			
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.circularreference", args));
		}
		
		removeGroupMembers(group);
				
		for (Group member : groupMembers) {
			member.addGroup(group);
			group.addGroupMember(member);
		}
		
		for (User member : userMembers) {
			member.addGroup(group);
			group.addUserMember(member);
		}
		
		setUnsavedChanges();
	}
	
	/**
	 * Clones an existing User. Creates a complete duplicate of the specified
	 * User and gives it the specified name.
	 * 
	 * @param user User to be cloned.
	 * @param userName Name of the new clone.
	 * @return Clone of the User.
	 * @throws ApplicationException
	 */
	public static User cloneUser(User user, String userName) throws ApplicationException {
		User clone = addUser(userName);
		
		for (Group group : user.getGroups()) {
			group.addUserMember(clone);
			clone.addGroup(group);
		}
		
		for (AccessRule rule : user.getAccessRules()) {
			addAccessRuleForUser(rule.getPath(), clone, rule.getLevel());
		}
				
		setUnsavedChanges();
		
		return clone;
	}
	
	/**
	 * Clones an existing Group. Creates a complete duplicate of the specified
	 * Group and gives it the specified name.
	 * 
	 * @param group Group to be cloned.
	 * @param groupName Name of the new clone.
	 * @return Clone of the Group.
	 * @throws ApplicationException
	 */
	public static Group cloneGroup(Group group, String groupName) throws ApplicationException {
		Group clone = addGroup(groupName, group.getGroupMembers(), group.getUserMembers());
		
		for (Group groupObject : group.getGroups()) {
			groupObject.addGroupMember(clone);
			clone.addGroup(group);
		}
		
		for (AccessRule rule : group.getAccessRules()) {
			addAccessRuleForGroup(rule.getPath(), clone, rule.getLevel());
		}
		
		setUnsavedChanges();
		
		return clone;
	}
	
	public static Group hasCircularReference(Group group, Collection<Group> groupMembers) {
		for (Group member : groupMembers) {
			if (group == member) {
				return group;
			}
			
			Group results = hasCircularReference(group, member.getGroupMembers());
			
			if (results != null) {
				return member;
			}
		}
		
		return null;
	}
}