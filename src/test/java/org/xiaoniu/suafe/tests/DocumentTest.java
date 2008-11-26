package org.xiaoniu.suafe.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Test;
import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;

public class DocumentTest {
	
	private static final Document document = new Document();

	private static final String groupName = "TestGroupName";

	private static final String groupName2 = groupName + "2";

	private static final String level = Constants.ACCESS_LEVEL_READONLY;

	private static final String relativePath = "/relativepath";

	private static final String repositoryName = "TestRepositoryName";

	private static final String userName = "TestUserName";

	private static final String userName2 = userName + "2";

	@Test
	public void testAddAccessRuleForGroupPathGroupString() {
		try {
			document.initialize();

			Group groupOriginal = document.addGroup(groupName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = document.addAccessRuleForGroup(pathOriginal, groupOriginal, level);

			// Validate group
			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			Group group = document.getGroups().get(0);
			assertTrue(group != null);
			assertTrue(group.equals(groupOriginal));
			assertTrue(group.getName().equals(groupName));

			// Validate group rules
			assertTrue(group.getAccessRules() != null);
			assertTrue(group.getAccessRules().size() == 1);
			AccessRule groupAccessRule = group.getAccessRules().get(0);
			assertTrue(groupAccessRule.getGroup().equals(group));
			assertTrue(groupAccessRule.getPath() != null);
			assertTrue(groupAccessRule.equals(accessRuleOriginal));

			// Validate rules
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			AccessRule accessRule = document.getAccessRules().get(0);
			assertTrue(accessRule.equals(groupAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			Path path = document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(groupAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			Repository repository = document.getRepositories().get(0);
			assertTrue(repository.getName().equals(repositoryName));
			assertTrue(repository.getPaths() != null);
			assertTrue(repository.getPaths().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddAccessRuleForGroupPathStringString() {
		try {
			document.initialize();

			Group groupOriginal = document.addGroup(groupName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = document.addAccessRuleForGroup(pathOriginal, groupName, level);

			// Validate group
			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			Group group = document.getGroups().get(0);
			assertTrue(group != null);
			assertTrue(group.equals(groupOriginal));
			assertTrue(group.getName().equals(groupName));

			// Validate group rules
			assertTrue(group.getAccessRules() != null);
			assertTrue(group.getAccessRules().size() == 1);
			AccessRule groupAccessRule = group.getAccessRules().get(0);
			assertTrue(groupAccessRule.getGroup().equals(group));
			assertTrue(groupAccessRule.getPath() != null);
			assertTrue(groupAccessRule.equals(accessRuleOriginal));

			// Validate rules
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			AccessRule accessRule = document.getAccessRules().get(0);
			assertTrue(accessRule.equals(groupAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			Path path = document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(groupAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			Repository repository = document.getRepositories().get(0);
			assertTrue(repository.getName().equals(repositoryName));
			assertTrue(repository.getPaths() != null);
			assertTrue(repository.getPaths().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddAccessRuleForGroupRepositoryStringGroupString() {
		try {
			document.initialize();

			Group groupOriginal = document.addGroup(groupName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = document.addAccessRuleForGroup(repositoryOriginal, relativePath,
					groupOriginal, level);

			// Validate group
			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			Group group = document.getGroups().get(0);
			assertTrue(group != null);
			assertTrue(group.equals(groupOriginal));
			assertTrue(group.getName().equals(groupName));

			// Validate group rules
			assertTrue(group.getAccessRules() != null);
			assertTrue(group.getAccessRules().size() == 1);
			AccessRule groupAccessRule = group.getAccessRules().get(0);
			assertTrue(groupAccessRule.getGroup().equals(group));
			assertTrue(groupAccessRule.getPath() != null);
			assertTrue(groupAccessRule.equals(accessRuleOriginal));

			// Validate rules
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			AccessRule accessRule = document.getAccessRules().get(0);
			assertTrue(accessRule.equals(groupAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			Path path = document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(groupAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			Repository repository = document.getRepositories().get(0);
			assertTrue(repository.getName().equals(repositoryName));
			assertTrue(repository.getPaths() != null);
			assertTrue(repository.getPaths().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddAccessRuleForUserPathStringString() {
		try {
			document.initialize();

			User userOriginal = document.addUser(userName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = document.addAccessRuleForUser(pathOriginal, userName, level);

			// Validate user
			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 1);
			User user = document.getUsers().get(0);
			assertTrue(user != null);
			assertTrue(user.equals(userOriginal));
			assertTrue(user.getName().equals(userName));

			// Validate user rules
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			AccessRule userAccessRule = user.getAccessRules().get(0);
			assertTrue(userAccessRule.getUser().equals(user));
			assertTrue(userAccessRule.getPath() != null);
			assertTrue(userAccessRule.equals(accessRuleOriginal));

			// Validate rules
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			AccessRule accessRule = document.getAccessRules().get(0);
			assertTrue(accessRule.equals(userAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			Path path = document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(userAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			Repository repository = document.getRepositories().get(0);
			assertTrue(repository.getName().equals(repositoryName));
			assertTrue(repository.getPaths() != null);
			assertTrue(repository.getPaths().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddAccessRuleForUserPathUserString() {
		try {
			document.initialize();

			User userOriginal = document.addUser(userName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = document.addAccessRuleForUser(pathOriginal, userOriginal, level);

			// Validate user
			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 1);
			User user = document.getUsers().get(0);
			assertTrue(user != null);
			assertTrue(user.equals(userOriginal));
			assertTrue(user.getName().equals(userName));

			// Validate user rules
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			AccessRule userAccessRule = user.getAccessRules().get(0);
			assertTrue(userAccessRule.getUser().equals(user));
			assertTrue(userAccessRule.getPath() != null);
			assertTrue(userAccessRule.equals(accessRuleOriginal));

			// Validate rules
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			AccessRule accessRule = document.getAccessRules().get(0);
			assertTrue(accessRule.equals(userAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			Path path = document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(userAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			Repository repository = document.getRepositories().get(0);
			assertTrue(repository.getName().equals(repositoryName));
			assertTrue(repository.getPaths() != null);
			assertTrue(repository.getPaths().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddAccessRuleForUserRepositoryStringUserString() {
		try {
			document.initialize();

			User userOriginal = document.addUser(userName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = document.addAccessRuleForUser(repositoryOriginal, relativePath,
					userOriginal, level);

			// Validate user
			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 1);
			User user = document.getUsers().get(0);
			assertTrue(user != null);
			assertTrue(user.equals(userOriginal));
			assertTrue(user.getName().equals(userName));

			// Validate user rules
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			AccessRule userAccessRule = user.getAccessRules().get(0);
			assertTrue(userAccessRule.getUser().equals(user));
			assertTrue(userAccessRule.getPath() != null);
			assertTrue(userAccessRule.equals(accessRuleOriginal));

			// Validate rules
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			AccessRule accessRule = document.getAccessRules().get(0);
			assertTrue(accessRule.equals(userAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			Path path = document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(userAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			Repository repository = document.getRepositories().get(0);
			assertTrue(repository.getName().equals(repositoryName));
			assertTrue(repository.getPaths() != null);
			assertTrue(repository.getPaths().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddGroupByName() {
		try {
			document.initialize();
			document.addGroupByName(null, null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addGroupByName("", null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addGroupByName(groupName, null, null);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			assertTrue(document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addGroupByName(groupName, new ArrayList<String>(), new ArrayList<String>());

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			assertTrue(document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			List<String> groupNames = new ArrayList<String>();
			List<String> userNames = new ArrayList<String>();

			groupNames.add(groupName2);
			userNames.add(userName);

			document.addGroupByName(groupName, groupNames, userNames);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 2);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddGroupString() {
		try {
			document.initialize();
			document.addGroup(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addGroup("");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addGroup(groupName);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			assertTrue(document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddGroupStringListOfGroupListOfUser() {
		try {
			document.initialize();
			document.addGroup(null, null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addGroup("", null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addGroup(groupName, null, null);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			assertTrue(document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addGroup(groupName, new ArrayList<Group>(), new ArrayList<User>());

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
			assertTrue(document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			List<Group> groups = new ArrayList<Group>();
			List<User> users = new ArrayList<User>();

			groups.add(document.addGroup(groupName2));
			users.add(document.addUser(userName));

			document.addGroup(groupName, groups, users);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 2);
			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddMembersByName() {
		try {
			document.initialize();

			Group group = document.addGroup(groupName);
			List<String> groupMemberNames = new ArrayList<String>();
			List<String> userMemberNames = new ArrayList<String>();

			groupMemberNames.add(groupName2);
			userMemberNames.add(userName);

			document.addMembersByName(group, groupMemberNames, userMemberNames);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 2);

			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 1);

			assertTrue(document.findGroup(groupName) != null);
			assertTrue(document.findGroup(groupName2) != null);
			assertTrue(document.findUser(userName) != null);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			Group group = document.addGroup(groupName);

			document.addMembersByName(group, null, null);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);

			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddPath() {
		try {
			document.initialize();
			document.addPath(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addPath(new Repository(), null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addPath(null, relativePath);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			Repository repository = document.addRepository(repositoryName);
			document.addPath(repository, relativePath);

			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			assertTrue(document.getPaths().get(0).getPath().equals(relativePath));

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			assertTrue(document.getRepositories().get(0).getName().equals(repositoryName));

			assertTrue(document.getPaths().get(0).getRepository() != null);
			assertTrue(document.getPaths().get(0).getRepository().getName() != null);
			assertTrue(document.getPaths().get(0).getRepository().getName().equals(repositoryName));

			assertTrue(document.getRepositories().get(0).getPaths() != null);
			assertTrue(document.getRepositories().get(0).getPaths().size() == 1);
			assertTrue(document.getRepositories().get(0).getPaths().get(0).getPath() != null);
			assertTrue(document.getRepositories().get(0).getPaths().get(0).getPath().equals(relativePath));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddRepository() {
		try {
			document.initialize();
			document.addRepository(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addRepository("");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			document.addRepository(repositoryName);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			assertTrue(document.getRepositories().get(0).getName().equals(repositoryName));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddServerAccessRuleForGroup() {
		try {
			document.initialize();
			document.addServerAccessRuleForGroup(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addServerAccessRuleForGroup(groupName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addGroup(groupName);
			document.addServerAccessRuleForGroup(groupName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addServerAccessRuleForGroup(null, level);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			document.addGroup(groupName);
			document.addServerAccessRuleForGroup(groupName, level);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);

			AccessRule accessRule = document.getAccessRules().get(0);

			assertTrue(accessRule.getGroup().getName().equals(groupName));
			assertTrue(accessRule.getLevel().equals(level));

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);

			Group group = document.getGroups().get(0);

			assertTrue(group.getAccessRules() != null);
			assertTrue(group.getAccessRules().size() == 1);
			assertTrue(group.getAccessRules().get(0).getLevel().equals(level));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddServerAccessRuleForUser() {
		try {
			document.initialize();
			document.addServerAccessRuleForUser(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addServerAccessRuleForUser(userName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addUser(userName);
			document.addServerAccessRuleForUser(userName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addServerAccessRuleForUser(null, level);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			document.addUser(userName);
			document.addServerAccessRuleForUser(userName, level);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);

			AccessRule accessRule = document.getAccessRules().get(0);

			assertTrue(accessRule.getUser().getName().equals(userName));
			assertTrue(accessRule.getLevel().equals(level));

			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 1);

			User user = document.getUsers().get(0);

			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			assertTrue(user.getAccessRules().get(0).getLevel().equals(level));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddUser() {
		try {
			document.initialize();
			document.addUser(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addUser("");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.addUser(userName);

			// Add same user again
			document.addUser(userName);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testChangeGroupMembers() {
		try {
			document.initialize();

			document.changeGroupMembers(null, null, null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			document.changeGroupMembers(document.addGroup(groupName), null, null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			document.changeGroupMembers(document.addGroup(groupName), new Vector<Group>(), null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			Group group = document.addGroup(groupName);

			assertTrue(group.getGroupMembers() != null);
			assertTrue(group.getGroupMembers().size() == 0);

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 0);

			document.changeGroupMembers(group, new Vector<Group>(), new Vector<User>());

			assertTrue(group.getGroupMembers() != null);
			assertTrue(group.getGroupMembers().size() == 0);

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			Group group = document.addGroup(groupName);

			assertTrue(group.getGroupMembers() != null);
			assertTrue(group.getGroupMembers().size() == 0);

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 0);

			Vector<Group> groupMembers = new Vector<Group>();
			Vector<User> userMembers = new Vector<User>();

			Group groupMember = document.addGroup(groupName2);
			User userMember = document.addUser(userName);

			assertTrue(groupMember.getGroups() != null);
			assertTrue(groupMember.getGroups().size() == 0);

			assertTrue(userMember.getGroups() != null);
			assertTrue(userMember.getGroups().size() == 0);

			groupMembers.add(groupMember);
			userMembers.add(userMember);

			document.changeGroupMembers(group, groupMembers, userMembers);

			assertTrue(group.getGroupMembers() != null);
			assertTrue(group.getGroupMembers().size() == 1);
			assertTrue(group.getGroupMembers().get(0).equals(groupMember));

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 1);
			assertTrue(group.getUserMembers().get(0).equals(userMember));

			assertTrue(groupMember.getGroups() != null);
			assertTrue(groupMember.getGroups().size() == 1);
			assertTrue(groupMember.getGroups().get(0).equals(group));

			assertTrue(userMember.getGroups() != null);
			assertTrue(userMember.getGroups().size() == 1);
			assertTrue(userMember.getGroups().get(0).equals(group));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testChangeUserMembership() {
		try {
			document.initialize();

			document.changeUserMembership(null, null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			document.changeUserMembership(document.addUser(userName), null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			document.changeUserMembership(document.addUser(userName), new Vector<Group>());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			User user = document.addUser(userName);

			assertTrue(user.getGroups() != null);
			assertTrue(user.getGroups().size() == 0);

			document.changeUserMembership(user, new Vector<Group>());

			assertTrue(user.getGroups() != null);
			assertTrue(user.getGroups().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			User user = document.addUser(userName);

			assertTrue(user.getGroups() != null);
			assertTrue(user.getGroups().size() == 0);

			Vector<Group> groupMembers = new Vector<Group>();

			Group group = document.addGroup(groupName);

			assertTrue(group.getGroups() != null);
			assertTrue(group.getGroups().size() == 0);

			groupMembers.add(group);

			document.changeUserMembership(user, groupMembers);

			assertTrue(user.getGroups() != null);
			assertTrue(user.getGroups().size() == 1);
			assertTrue(user.getGroups().get(0).equals(group));

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 1);
			assertTrue(group.getUserMembers().get(0).equals(user));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testCloneGroup() {
		try {
			document.initialize();
			document.cloneGroup(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			document.cloneGroup(group, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			document.cloneGroup(group, "");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			document.cloneGroup(group, groupName);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			document.cloneGroup(group, groupName2);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			document.cloneGroup(group, groupName2);

			// Clone same group again
			document.cloneGroup(group, groupName2);
			fail();
		}
		catch (ApplicationException e) {
		}
	}

	@Test
	public void testCloneUser() {
		try {
			document.initialize();
			document.cloneUser(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			User user = document.addUser(userName);
			document.cloneUser(user, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			User user = document.addUser(userName);
			document.cloneUser(user, "");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			User user = document.addUser(userName);
			document.cloneUser(user, userName);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			User user = document.addUser(userName);
			document.cloneUser(user, userName2);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			User user = document.addUser(userName);
			document.cloneUser(user, userName2);

			// Clone same user again
			document.cloneUser(user, userName2);
			fail();
		}
		catch (ApplicationException e) {
		}
	}

	@Test
	public void testDeleteAccessRule() {
		// TODO
	}

	@Test
	public void testDeleteGroup() {
		try {
			document.initialize();
			document.deleteGroup(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.deleteGroup(new Group());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 0);

			Group group = document.addGroup(groupName);

			document.deleteGroup(group);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 0);

			// Delete same group again
			document.deleteGroup(group);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			Group group = document.addGroup(groupName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			document.addAccessRuleForGroup(pathOriginal, groupName, level);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			assertTrue(document.getAccessRules().get(0).getGroup().equals(group));

			document.deleteGroup(group);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteGroups() {
		try {
			document.initialize();
			document.deleteGroups(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			Object[] groups = { new Group() };

			document.deleteGroups(groups);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 0);

			Group group = document.addGroup(groupName);

			Object[] groups = { group };

			document.deleteGroups(groups);

			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 0);

			// Delete same group again
			document.deleteGroups(groups);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			Group group = document.addGroup(groupName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			document.addAccessRuleForGroup(pathOriginal, groupName, level);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			assertTrue(document.getAccessRules().get(0).getGroup().equals(group));

			Object[] groups = { group };

			document.deleteGroups(groups);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeletePath() {
		try {
			document.initialize();
			document.deletePath(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.deletePath(new Path());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			Repository repository = document.addRepository(repositoryName);

			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 0);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			assertTrue(document.getRepositories().get(0).equals(repository));

			assertTrue(document.getRepositories().get(0).getPaths() != null);
			assertTrue(document.getRepositories().get(0).getPaths().size() == 0);

			Path path = document.addPath(repository, relativePath);

			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			assertTrue(document.getPaths().get(0).equals(path));

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			assertTrue(document.getRepositories().get(0).equals(repository));

			assertTrue(document.getPaths().get(0).getRepository() != null);
			assertTrue(document.getPaths().get(0).getRepository().equals(repository));

			assertTrue(document.getRepositories().get(0).getPaths() != null);
			assertTrue(document.getRepositories().get(0).getPaths().size() == 1);
			assertTrue(document.getRepositories().get(0).getPaths().get(0).equals(path));

			document.deletePath(path);

			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 0);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			assertTrue(document.getRepositories().get(0).equals(repository));

			assertTrue(document.getRepositories().get(0).getPaths() != null);
			assertTrue(document.getRepositories().get(0).getPaths().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteRepositories() {
		try {
			document.initialize();
			document.deleteRepositories(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			
			Object[] repositories = { new Repository() };
			
			document.deleteRepositories(repositories);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			Repository repository = document.addRepository(repositoryName);

			Object[] repositories = { repository };
			
			document.deleteRepositories(repositories);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			// Delete same user again
			document.deleteRepository(repository);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			User user = document.addUser(userName);
			Repository repository = document.addRepository(repositoryName);
			Path path = document.addPath(repository, relativePath);
			AccessRule accessRule = document.addAccessRuleForUser(repository, relativePath, user, level);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			assertTrue(document.getRepositories().get(0).equals(repository));

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			assertTrue(document.getAccessRules().get(0).equals(accessRule));
			
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			assertTrue(document.getPaths().get(0).equals(path));
			
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			assertTrue(user.getAccessRules().get(0).equals(accessRule));

			Object[] repositories = { repository };
			
			document.deleteRepositories(repositories);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);
			
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 0);
			
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 0);
			
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteRepository() {
		try {
			document.initialize();
			document.deleteRepository(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.deleteRepository(new Repository());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			Repository repository = document.addRepository(repositoryName);

			document.deleteRepository(repository);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			// Delete same user again
			document.deleteRepository(repository);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);

			User user = document.addUser(userName);
			Repository repository = document.addRepository(repositoryName);
			Path path = document.addPath(repository, relativePath);
			AccessRule accessRule = document.addAccessRuleForUser(repository, relativePath, user, level);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 1);
			assertTrue(document.getRepositories().get(0).equals(repository));

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			assertTrue(document.getAccessRules().get(0).equals(accessRule));
			
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 1);
			assertTrue(document.getPaths().get(0).equals(path));
			
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			assertTrue(user.getAccessRules().get(0).equals(accessRule));

			document.deleteRepository(repository);

			assertTrue(document.getRepositories() != null);
			assertTrue(document.getRepositories().size() == 0);
			
			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 0);
			
			assertTrue(document.getPaths() != null);
			assertTrue(document.getPaths().size() == 0);
			
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteUser() {
		try {
			document.initialize();
			document.deleteUser(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();
			document.deleteUser(new User());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 0);

			User user = document.addUser(userName);

			document.deleteUser(user);

			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 0);

			// Delete same user again
			document.deleteUser(user);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			User user = document.addUser(userName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			document.addAccessRuleForUser(pathOriginal, userName, level);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			assertTrue(document.getAccessRules().get(0).getUser().equals(user));

			document.deleteUser(user);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteUsers() {
		try {
			document.initialize();
			document.deleteUsers(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			document.initialize();

			Object[] users = { new User() };

			document.deleteUsers(users);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 0);

			User user = document.addUser(userName);

			Object[] users = { user };

			document.deleteUsers(users);

			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 0);

			// Delete same user again
			document.deleteUsers(users);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();

			User user = document.addUser(userName);
			Repository repositoryOriginal = document.addRepository(repositoryName);
			Path pathOriginal = document.addPath(repositoryOriginal, relativePath);
			document.addAccessRuleForUser(pathOriginal, userName, level);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 1);
			assertTrue(document.getAccessRules().get(0).getUser().equals(user));

			Object[] users = { user };

			document.deleteUsers(users);

			assertTrue(document.getAccessRules() != null);
			assertTrue(document.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testFindGroup() {

	}

	@Test
	public void testFindGroupAccessRule() {

	}

	@Test
	public void testFindPathRepositoryString() {

	}

	@Test
	public void testFindPathStringString() {

	}

	@Test
	public void testFindRepository() {

	}

	@Test
	public void testFindServerPath() {

	}

	@Test
	public void testFindUser() {

	}

	@Test
	public void testFindUserAccessRule() {

	}

	@Test
	public void testGetAccessRules() {

	}

	@Test
	public void testGetFile() {

	}

	@Test
	public void testGetGroupAccessRules() {

	}

	@Test
	public void testGetGroupMemberGroupNames() {

	}

	@Test
	public void testGetGroupMemberGroups() {

	}

	@Test
	public void testGetGroupMemberObjects() {

	}

	@Test
	public void testGetGroupMemberUserNames() {

	}

	@Test
	public void testGetGroupMemberUsers() {

	}

	@Test
	public void testGetGroupNames() {

	}

	@Test
	public void testGetGroupObjects() {

	}

	@Test
	public void testGetGroups() {
		try {
			document.initialize();
			
			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 0);
			
			document.addGroup(groupName);
			
			assertTrue(document.getGroups() != null);
			assertTrue(document.getGroups().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testGetGroupsArray() {
		try {
			document.initialize();
			
			assertTrue(document.getGroupsArray() != null);
			assertTrue(document.getGroupsArray().length == 0);
			
			document.addGroup(groupName);
			
			assertTrue(document.getGroupsArray() != null);
			assertTrue(document.getGroupsArray().length == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testGetPathAccessRules() {

	}

	@Test
	public void testGetPaths() {

	}

	@Test
	public void testGetRepositories() {

	}

	@Test
	public void testGetRepositoryAccessRules() {

	}

	@Test
	public void testGetRepositoryNames() {

	}

	@Test
	public void testGetRepositoryObjects() {

	}

	@Test
	public void testGetServerAccessRules() {

	}

	@Test
	public void testGetUserAccessRuleObjects() {

	}

	@Test
	public void testGetUserAccessRules() {

	}

	@Test
	public void testGetUserGroupNames() {

	}

	@Test
	public void testGetUserGroupObjects() {

	}

	@Test
	public void testGetUserGroupsArray() {

	}

	@Test
	public void testGetUserNames() {

	}

	@Test
	public void testGetUserObjects() {
		try {
			document.initialize();
			
			assertTrue(document.getUserObjects() != null);
			assertTrue(document.getUserObjects().length == 0);
			
			document.addUser(userName);
			
			assertTrue(document.getUserObjects() != null);
			assertTrue(document.getUserObjects().length == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testGetUserObjectsExcludeAllUsers() {

	}

	@Test
	public void testGetUsers() {
		try {
			document.initialize();
			
			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 0);
			
			document.addUser(userName);
			
			assertTrue(document.getUsers() != null);
			assertTrue(document.getUsers().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testCheckForCircularReference() {

	}

	@Test
	public void testHasUnsavedChanges() {
		// Add user
		try {
			document.initialize();
			document.addUser(userName);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit user
		// TODO

		// Clone user
		try {
			document.initialize();
			User user = document.addUser(userName);
			document.resetUnsavedChangesFlag();

			document.cloneUser(user, userName2);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Delete user
		try {
			document.initialize();
			User user = document.addUser(userName);
			document.resetUnsavedChangesFlag();

			document.deleteUser(user);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Change group membership
		// TODO

		// Add group
		try {
			document.initialize();
			document.addGroup(groupName);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addGroup(groupName, null, null);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addGroupByName(groupName, null, null);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit group
		// TODO

		// Clone group
		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			document.resetUnsavedChangesFlag();

			document.cloneGroup(group, groupName2);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Delete group
		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			document.resetUnsavedChangesFlag();

			document.deleteGroup(group);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Change group membership
		// TODO

		// Add repository
		try {
			document.initialize();
			document.addRepository(repositoryName);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit repository
		// TODO

		// Delete repository
		try {
			document.initialize();
			Repository repository = document.addRepository(repositoryName);
			document.resetUnsavedChangesFlag();

			document.deleteRepository(repository);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Add path
		try {
			document.initialize();
			document.addPath(new Repository(), relativePath);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit path
		// TODO

		// Delete path
		try {
			document.initialize();
			Path path = document.addPath(new Repository(), relativePath);
			document.resetUnsavedChangesFlag();

			document.deletePath(path);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Add access rule
		try {
			document.initialize();
			document.addAccessRuleForGroup(new Path(new Repository(repositoryName), relativePath),
					new Group(groupName), level);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addGroup(groupName);
			document.addAccessRuleForGroup(new Path(new Repository(repositoryName), relativePath), groupName, level);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addAccessRuleForGroup(new Repository(repositoryName), relativePath, new Group(groupName), level);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addAccessRuleForUser(new Path(new Repository(repositoryName), relativePath), new User(userName),
					level);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addUser(userName);
			document.addAccessRuleForUser(new Path(new Repository(repositoryName), relativePath), userName, level);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			document.initialize();
			document.addAccessRuleForUser(new Repository(repositoryName), relativePath, new User(userName), level);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit access rule
		// TODO

		// Delete access rule
		try {
			document.initialize();
			Group group = document.addGroup(groupName);
			Repository repository = document.addRepository(repositoryName);
			Path path = document.addPath(repository, relativePath);
			document.addAccessRuleForGroup(path, group, level);
			document.resetUnsavedChangesFlag();

			document.deleteAccessRule(repositoryName, relativePath, group, null);
			assertTrue(document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testInitialize() {
		document.initialize();

		assertTrue(document.getUsers().size() == 0);

		try {
			document.addGroup(groupName);
			document.addPath(new Repository(repositoryName), relativePath);
			document.addRepository(repositoryName);
			document.addUser(userName);
		}
		catch (ApplicationException e) {
			fail();
		}

		assertTrue(document.getUsers().size() == 1);

		document.initialize();

		assertTrue(document.getUsers().size() == 0);
	}

	@Test
	public void testIsEmpty() {

	}

	@Test
	public void testRemoveFromGroups() {

	}

	@Test
	public void testRemoveGroupMembers() {

	}

	@Test
	public void testResetUnsavedChangesFlag() {

	}

	@Test
	public void testSetFile() {

	}

	@Test
	public void testSetUnsavedChanges() {

	}

	@Test
	public void testValidateDocument() {

	}
}
