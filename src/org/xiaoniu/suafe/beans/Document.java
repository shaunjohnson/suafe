package org.xiaoniu.suafe.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.exceptions.ValidatorException;
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
	protected static List accessRules = new ArrayList();

	/**
	 * Authentication file being edited.
	 */
	protected static File file = null;

	/**
	 * List of all Groups.
	 */
	protected static List groups = new ArrayList();

	/**
	 * List of all Paths.
	 */
	protected static List paths = new ArrayList();

	/**
	 * List of all Repositories.
	 */
	protected static List repositories = new ArrayList();

	/**
	 * List of all Users.
	 */
	protected static List users = new ArrayList();

	/**
	 * Resets all data stored within the document.
	 */
	public static void initialize() {
		file = null;

		users = new ArrayList();
		groups = new ArrayList();
		repositories = new ArrayList();
		accessRules = new ArrayList();
		paths = new ArrayList();
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

		return addAccessRuleForGroup(path, group, level);
	}
	
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
	
		return accessRule;
	}
	
	public static AccessRule addAccessRuleForGroup(Repository repository, String pathString, Group group, String level) throws ApplicationException {
		Path path = addPath(repository, pathString);
		
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
				
		return addAccessRuleForUser(path, user, level);
	}
	
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
	
		return accessRule;
	}

	public static AccessRule addAccessRuleForUser(Repository repository, String pathString, User user, String level) throws ApplicationException {
		Path path = addPath(repository, pathString);
		
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
			List groupMemberList = new ArrayList();
			List userMemberList = new ArrayList();
			
			group = new Group(groupName, groupMemberList, userMemberList);
	
			// Add Group members
			if (groupMembers != null) {
				Iterator iterator = groupMembers.iterator();
	
				while (iterator.hasNext()) {
					Object object = iterator.next();
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
				Iterator iterator = userMembers.iterator();
				
				while (iterator.hasNext()) {
					Object object = iterator.next();
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
		Validator.validateRelativePath(relativePath);
		
		Path path = findPath(repository, relativePath);
		
		if (path == null) {
			path = new Path(repository, relativePath);
			
			paths.add(path);
			
			if (repository != null) {
				repository.addPath(path);
			}
		}
		
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
	
		return repository;
	}

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
	
		return accessRule;
	}
	
	public static AccessRule addServerAccessRuleForUser(String userName, String level) throws ApplicationException {
		Validator.validateUserName(userName);
		Validator.validateLevelOfAccess(level);
		
		User user = addUser(userName);
							
		Path path = addPath(null, "/");
		
		AccessRule accessRule = new AccessRule(path, user, level);
		
		path.addAccessRule(accessRule);
		
		accessRules.add(accessRule);
	
		return accessRule;
	}

	public static User addUser(String userName) throws ApplicationException {
		Validator.validateUserName(userName);
		
		User user = findUser(userName);
	
		if (user == null) {
			user = new User(userName);
	
			users.add(user);
		}
	
		return user;
	}
	
	public static void deleteAccessRule(String repositoryName, String pathString, Group group, User user) throws ApplicationException {
		Repository repository = findRepository(repositoryName);
		AccessRule accessRule = null;
		
		if (group != null) {
			accessRule = findGroupAccessRule(repository, pathString, group);
			group.removeAccessRule(accessRule);
		}
		else if (user != null) {
			accessRule = findUserAccessRule(repository, pathString, user);
			user.removeAccessRule(accessRule);
		}
		
		accessRule.getPath().removeAccessRule(accessRule);
		accessRules.remove(accessRule);
	}

	public static void deleteGroup(Group group) throws ApplicationException {
		deleteGroupAccessRules(group);
		removeGroupMembers(group);
		groups.remove(group);
	}

	private static void deleteGroupAccessRules(Group group) {
		List deleteList = new ArrayList();
		Iterator iterator = accessRules.iterator();
		AccessRule rule = null;
		
		while(iterator.hasNext()) {
			rule = (AccessRule)iterator.next();
			
			if (rule.getGroup() != null && rule.getGroup().equals(group)) {
				deleteList.add(rule);
			}
		}
		
		accessRules.removeAll(deleteList);
	}

	public static void deleteGroups(Object[] groups) throws ApplicationException {		
		for (int i = 0; i < groups.length; i++) {
			deleteGroup((Group)groups[i]);
		}
	}

	public static void deletePath(Path path) throws ApplicationException {
		deletePathAccessRules(path);	
		
		if (path.getRepository() != null) {
			path.getRepository().removePath(path);
		}
		
		paths.remove(path);
	}

	private static void deletePathAccessRules(Path path) {
		List deleteList = new ArrayList();
		Iterator iterator = accessRules.iterator();
		AccessRule rule = null;
		
		while(iterator.hasNext()) {
			rule = (AccessRule)iterator.next();
			
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
	}

	
	public static void deleteRepository(Repository repository) throws ApplicationException {
		deleteRepositoryAccessRules(repository);
		deleteRepositoryPaths(repository);
		repositories.remove(repository);
	}

	private static void deleteRepositoryAccessRules(Repository repository) {
		List deleteList = new ArrayList();
		Iterator iterator = accessRules.iterator();
		AccessRule rule = null;
		
		while(iterator.hasNext()) {
			rule = (AccessRule)iterator.next();
			
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
	}

	private static void deleteRepositoryPaths(Repository repository) throws ApplicationException {
		List deleteList = new ArrayList();
		Iterator iterator = repository.getPaths().iterator();
		Path path = null;
		
		while(iterator.hasNext()) {
			path = (Path)iterator.next();
			
			deleteList.add(path);
			deletePathAccessRules(path);
		}
		
		paths.removeAll(deleteList);
	}
	
	public static void deleteRepositories(Object[] repositories) throws ApplicationException {
		for (int i = 0; i < repositories.length; i++) {
			deleteRepository((Repository)repositories[i]);
		}
	}
	
	public static void deleteUser(User user) throws ApplicationException {
		deleteUserAccessRules(user);		
		removeUserFromAssignedGroups(user);
		users.remove(user);
	}

	private static void deleteUserAccessRules(User user) {
		List deleteList = new ArrayList();
		Iterator iterator = accessRules.iterator();
		AccessRule rule = null;
		
		while(iterator.hasNext()) {
			rule = (AccessRule)iterator.next();
			
			if (rule.getUser() != null && rule.getUser().equals(user)) {
				// Remove access rule from path
				rule.getPath().removeAccessRule(rule);
				
				// Add rule to list for deletion1
				deleteList.add(rule);
			}
		}
		
		accessRules.removeAll(deleteList);
	}
	
	public static void deleteUsers(Object[] users) throws ApplicationException {
		for (int i = 0; i < users.length; i++) {
			deleteUser((User)users[i]);
		}
	}
	
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

	public static AccessRule findGroupAccessRule(Repository repository, String pathString, Group group) throws ApplicationException {
		if (accessRules == null) {
			return null;
		}
		
		Path path = findPath(repository, pathString);
		
		if (path == null || group == null || group.getAccessRules() == null) {
			return null;
		}
		
		Iterator iterator = group.getAccessRules().iterator();
		AccessRule foundRule = null;
		
		while (iterator.hasNext()) {
			AccessRule rule = (AccessRule)iterator.next();
			
			if (rule.getPath() == path) {
				foundRule = rule;
				break;
			}
		}
		
		return foundRule;
	}
	
	public static AccessRule findUserAccessRule(Repository repository, String pathString, User user) throws ApplicationException {
		if (accessRules == null) {
			return null;
		}
		
		Path path = findPath(repository, pathString);
		
		if (path == null || user == null || user.getAccessRules() == null) {
			return null;
		}
		
		Iterator iterator = user.getAccessRules().iterator();
		AccessRule foundRule = null;
		
		while (iterator.hasNext()) {
			AccessRule rule = (AccessRule)iterator.next();
			
			if (rule.getPath() == path) {
				foundRule = rule;
				break;
			}
		}
		
		return foundRule;
	}
	
	public static Path findPath(String repositoryName, String pathString) throws ApplicationException {		
		Repository repository = findRepository(repositoryName);
	
		return findPath(repository, pathString);
	}

	public static Path findPath(Repository repository, String relativePath) throws ApplicationException {
		Validator.validateRelativePath(relativePath);
		
		Path path = null;
		boolean found = false;
	
		if (paths != null) {
			int size = paths.size();
	
			for (int i = 0; i < size; i++) {
				path = (Path)paths.get(i);
	
				if (path.getRepository() == repository && path.getPath().equals(relativePath)) {
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

	public static Path findServerPath(String relativePath) throws ApplicationException {
		Path path = null;
		boolean found = false;
	
		if (paths != null) {
			int size = paths.size();
	
			for (int i = 0; i < size; i++) {
				path = (Path)paths.get(i);
	
				if (path.getRepository() == null && path.getPath().equals(relativePath)) {
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

	public static Object[][] getAccessRulesData() {
		if (accessRules == null) {
			return null;
		} else {
			int size = accessRules.size();
			Object[][] accessRulesList = new Object[size][2];
	
			for (int i = 0; i < size; i++) {
				AccessRule rule = (AccessRule) accessRules.get(i);
	
				accessRulesList[i][0] = rule.getLevelFullName();
	
				if (rule.getGroup() == null) {
					accessRulesList[i][1] = rule.getUser().getName();
				} else {
					accessRulesList[i][1] = rule.getGroup().getName();
				}
			}
	
			return accessRulesList;
		}
	}
	
	/**
	 * @return Returns the file.
	 */
	public static File getFile() {
		return file;
	}

	/**
	 * Gets an array of AccessRules data in which the Group is referenced.
	 * The two dimensional array returned contains the Repository name,
	 * relative path and level of access for each AccessRule in which the
	 * Group is referenced.
	 * 
	 * @param groupName Name of the Group.
	 * @return Object array of AccessRule information.
	 * @throws ApplicationException
	 */
	public static Object[][] getGroupAccessRules(Group group) throws ApplicationException {
		Object[][] retval = null;
		
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
	
	public static Object[] getGroupObjects() {
		if (groups == null) {
			return null;
		} else {	
			Collections.sort(groups);
	
			return groups.toArray();
		}
	}

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
	
	public static Object[] getGroupMemberObjects(Group group) throws ApplicationException {
		if (group == null || group.getGroupMembers() == null) {
			return null;
		} else {
			Collections.sort(group.getGroupMembers());
			Collections.sort(group.getUserMembers());
			
			List combinedList = new ArrayList();
			
			combinedList.addAll(group.getGroupMembers());
			combinedList.addAll(group.getUserMembers());
	
			return combinedList.toArray();
		}
	}
	
	public static Object[] getGroupMemberGroupObjects(Group group) throws ApplicationException {
		if (group == null || group.getGroupMembers() == null) {
			return null;
		} else {
			Collections.sort(group.getGroupMembers());
	
			return group.getGroupMembers().toArray();
		}
	}
	
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
	
	public static Object[] getGroupMemberUserObjects(Group group) throws ApplicationException {
		if (group == null || group.getUserMembers() == null) {
			return null;
		} else {
			Collections.sort(group.getUserMembers());
	
			return group.getUserMembers().toArray();
		}
	}

	/**
	 * Gets the list of all Groups.
	 * 
	 * @return List of Groups.
	 */
	public static List getGroups() {
		return groups;
	}

	/**
	 * Gets an array of AccessRules data for the AccessRules defined for
	 * the Path. The two dimensional array returned contains the User or 
	 * Group name and level of access for each AccessRule defined for the
	 * Path.
	 * 
	 * @param repositoryName Name of the Repository.
	 * @param pathString Relative path within the Repository.
	 * @return Object array of AccessRule information.
	 * @throws ApplicationException
	 */
	public static Object[][] getPathAccessRules(Path path) throws ApplicationException {
		Object[][] retval = null;
				
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

	public static List getPaths() {
		return paths;
	}

	/**
	 * Gets an array of AccessRules data in which the Repository is 
	 * referenced. The two dimensional array returned contains the 
	 * relative path, User or Group name and level of access for 
	 * each AccessRule in which the Repostiory is referenced.
	 * 
	 * @param repositoryName Name of the Repository.
	 * @return Object array of AccessRule information.
	 * @throws ApplicationException
	 */
	public static Object[][] getRepositoryAccessRules(Repository repository) throws ApplicationException {
		Object[][] retval = null;
		
		if (repository == null || repository.getPaths() == null) {
			return null;
		} else {
			Iterator paths = repository.getPaths().iterator();
			List accessRules = new ArrayList();
			
			while (paths.hasNext()) {
				Path path = (Path)paths.next();
				
				if (path.getAccessRules() != null) {
					Iterator rules = path.getAccessRules().iterator();
				
					while (rules.hasNext()) {
						accessRules.add(rules.next());
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

	public static List getRepositories() {
		return repositories;
	}

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
	
	public static Object[] getRepositoryObjects() {
		if (repositories == null) {
			return null;
		} else {
			Collections.sort(repositories);

			return repositories.toArray();
		}
	}
	
	public static Object[][] getServerAccessRules() throws ApplicationException {
		Object[][] retval = null;
		
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
		Object[][] retval = null;
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

	public static Object[][] getUserAccessRuleObjects(User user) throws ApplicationException {
		Object[][] retval = null;
		
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

	public static Object[] getUserGroupObjects(User user) throws ApplicationException {		
		if (user == null || user.getGroups() == null) {
			return null;
		} else {
			List groups = user.getGroups();
			
			Collections.sort(groups);
			
			return groups.toArray();
		}
	}
	
	public static Object[] getUserObjects() {
		if (users == null) {
			return null;
		} else {
			Collections.sort(users);
			
			return users.toArray();
		}
	}
	
	public static Object[] getUserObjectsExcludeAllUsers() throws ApplicationException {
		if (users == null) {
			return null;
		} else {
			List filteredUsers = users;
			User allUsers = findUser(Constants.ALL_USERS);
			
			if (allUsers != null) {
				filteredUsers.remove(allUsers);
			}
			
			Collections.sort(filteredUsers);
			
			return filteredUsers.toArray();
		}
	}
	
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

	private static void removeUserFromAssignedGroups(User user) {
		Iterator iterator = user.getGroups().iterator();
		Group group = null;
		
		while(iterator.hasNext()) {
			group = (Group)iterator.next();
			
			group.getUserMembers().remove(user);
		}
		
		user.setGroups(new ArrayList());
	}
	
	/**
	 * @param file The file to set.
	 */
	public static void setFile(File file) {
		Document.file = file;
	}

	public static void changeUserMembership(User user, Vector newGroupObjects) throws ApplicationException {
		// Remove from old groups
		 removeUserFromAssignedGroups(user);
		
		// Add to new groups
		List newGroups = new ArrayList(newGroupObjects.size());
		Iterator iterator = newGroupObjects.iterator();
		
		while (iterator.hasNext()) {
			Group group = (Group)iterator.next();
			
			group.addUserMember(user);
			newGroups.add(group);
		}
		
		user.setGroups(newGroups);
	}

	private static void removeGroupMembers(Group group) {
		// Remove groups
		Iterator groups = group.getGroupMembers().iterator();
		
		while (groups.hasNext()) {
			Group member = (Group)groups.next();
			
			member.removeGroup(group);
		}
		
		group.getGroupMembers().clear();
		
		// Removed users
		Iterator users = group.getUserMembers().iterator();
		
		while (users.hasNext()) {
			User member = (User)users.next();
			
			member.removeGroup(group);
		}
		
		group.getUserMembers().clear();
	}	
	
	/**
	 * @param group
	 * @param groupMembers
	 * @param userMembers
	 * @throws ApplicationException
	 */
	public static void changeGroupMembers(Group group, Vector groupMembers, Vector userMembers) throws ApplicationException {
		removeGroupMembers(group);
				
		Iterator iterator = groupMembers.iterator();
		
		while (iterator.hasNext()) {
			Group member = (Group)iterator.next();
			
			if (member == null) {
				throw new ApplicationException("Error finding group");
			}
			
			member.addGroup(group);
			group.addGroupMember(member);
		}
		
		iterator = userMembers.iterator();
		
		while (iterator.hasNext()) {
			User member = (User)iterator.next();
			
			if (member == null) {
				throw new ApplicationException("Error finding user");
			}
			
			member.addGroup(group);
			group.addUserMember(member);
		}
		
		String name = group.getName();
	}
	
	public static User cloneUser(User user, String userName) throws ApplicationException {
		User clone = addUser(userName);
		Iterator groups = user.getGroups().iterator();
		Iterator accessRules = user.getAccessRules().iterator();
		
		while (groups.hasNext()) {
			Group group = (Group)groups.next();
			
			group.addUserMember(clone);
			clone.addGroup(group);
		}
		
		while (accessRules.hasNext()) {
			AccessRule accessRule = (AccessRule)accessRules.next();
						
			addAccessRuleForUser(accessRule.getPath(), clone, accessRule.getLevel());
		}
		
		return clone;
	}
	
	public static Group cloneGroup(Group group, String groupName) throws ApplicationException {
		Group clone = addGroup(groupName, group.getGroupMembers(), group.getUserMembers());
		Iterator groups = group.getGroups().iterator();
		Iterator accessRules = group.getAccessRules().iterator();
		
		while (groups.hasNext()) {
			Group groupObject = (Group)groups.next();
			
			groupObject.addGroupMember(clone);
			clone.addGroup(group);
		}
		
		while (accessRules.hasNext()) {
			AccessRule accessRule = (AccessRule)accessRules.next();
						
			addAccessRuleForGroup(accessRule.getPath(), clone, accessRule.getLevel());
		}
		
		return clone;
	}
}