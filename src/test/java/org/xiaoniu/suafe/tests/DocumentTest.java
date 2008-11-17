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
			Document.initialize();

			Group groupOriginal = Document.addGroup(groupName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = Document.addAccessRuleForGroup(pathOriginal, groupOriginal, level);

			// Validate group
			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			Group group = Document.getGroups().get(0);
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
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			AccessRule accessRule = Document.getAccessRules().get(0);
			assertTrue(accessRule.equals(groupAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			Path path = Document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(groupAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			Repository repository = Document.getRepositories().get(0);
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
			Document.initialize();

			Group groupOriginal = Document.addGroup(groupName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = Document.addAccessRuleForGroup(pathOriginal, groupName, level);

			// Validate group
			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			Group group = Document.getGroups().get(0);
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
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			AccessRule accessRule = Document.getAccessRules().get(0);
			assertTrue(accessRule.equals(groupAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			Path path = Document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(groupAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			Repository repository = Document.getRepositories().get(0);
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
			Document.initialize();

			Group groupOriginal = Document.addGroup(groupName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = Document.addAccessRuleForGroup(repositoryOriginal, relativePath,
					groupOriginal, level);

			// Validate group
			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			Group group = Document.getGroups().get(0);
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
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			AccessRule accessRule = Document.getAccessRules().get(0);
			assertTrue(accessRule.equals(groupAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			Path path = Document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(groupAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			Repository repository = Document.getRepositories().get(0);
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
			Document.initialize();

			User userOriginal = Document.addUser(userName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = Document.addAccessRuleForUser(pathOriginal, userName, level);

			// Validate user
			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 1);
			User user = Document.getUsers().get(0);
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
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			AccessRule accessRule = Document.getAccessRules().get(0);
			assertTrue(accessRule.equals(userAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			Path path = Document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(userAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			Repository repository = Document.getRepositories().get(0);
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
			Document.initialize();

			User userOriginal = Document.addUser(userName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = Document.addAccessRuleForUser(pathOriginal, userOriginal, level);

			// Validate user
			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 1);
			User user = Document.getUsers().get(0);
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
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			AccessRule accessRule = Document.getAccessRules().get(0);
			assertTrue(accessRule.equals(userAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			Path path = Document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(userAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			Repository repository = Document.getRepositories().get(0);
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
			Document.initialize();

			User userOriginal = Document.addUser(userName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			AccessRule accessRuleOriginal = Document.addAccessRuleForUser(repositoryOriginal, relativePath,
					userOriginal, level);

			// Validate user
			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 1);
			User user = Document.getUsers().get(0);
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
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			AccessRule accessRule = Document.getAccessRules().get(0);
			assertTrue(accessRule.equals(userAccessRule));
			assertTrue(accessRule.equals(accessRuleOriginal));

			// Validate path
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			Path path = Document.getPaths().get(0);
			assertTrue(path.equals(pathOriginal));
			assertTrue(path.getPath().equals(relativePath));
			assertTrue(accessRule.getPath().equals(path));
			assertTrue(userAccessRule.getPath().equals(path));

			// Validate repository
			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			Repository repository = Document.getRepositories().get(0);
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
			Document.initialize();
			Document.addGroupByName(null, null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addGroupByName("", null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addGroupByName(groupName, null, null);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			assertTrue(Document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addGroupByName(groupName, new ArrayList<String>(), new ArrayList<String>());

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			assertTrue(Document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			List<String> groupNames = new ArrayList<String>();
			List<String> userNames = new ArrayList<String>();

			groupNames.add(groupName2);
			userNames.add(userName);

			Document.addGroupByName(groupName, groupNames, userNames);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 2);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddGroupString() {
		try {
			Document.initialize();
			Document.addGroup(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addGroup("");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addGroup(groupName);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			assertTrue(Document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddGroupStringListOfGroupListOfUser() {
		try {
			Document.initialize();
			Document.addGroup(null, null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addGroup("", null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addGroup(groupName, null, null);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			assertTrue(Document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addGroup(groupName, new ArrayList<Group>(), new ArrayList<User>());

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
			assertTrue(Document.getGroups().get(0).getName().equals(groupName));
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			List<Group> groups = new ArrayList<Group>();
			List<User> users = new ArrayList<User>();

			groups.add(Document.addGroup(groupName2));
			users.add(Document.addUser(userName));

			Document.addGroup(groupName, groups, users);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 2);
			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddMembersByName() {
		try {
			Document.initialize();

			Group group = Document.addGroup(groupName);
			List<String> groupMemberNames = new ArrayList<String>();
			List<String> userMemberNames = new ArrayList<String>();

			groupMemberNames.add(groupName2);
			userMemberNames.add(userName);

			Document.addMembersByName(group, groupMemberNames, userMemberNames);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 2);

			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 1);

			assertTrue(Document.findGroup(groupName) != null);
			assertTrue(Document.findGroup(groupName2) != null);
			assertTrue(Document.findUser(userName) != null);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			Group group = Document.addGroup(groupName);

			Document.addMembersByName(group, null, null);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);

			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddPath() {
		try {
			Document.initialize();
			Document.addPath(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addPath(new Repository(), null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addPath(null, relativePath);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Repository repository = Document.addRepository(repositoryName);
			Document.addPath(repository, relativePath);

			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			assertTrue(Document.getPaths().get(0).getPath().equals(relativePath));

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			assertTrue(Document.getRepositories().get(0).getName().equals(repositoryName));

			assertTrue(Document.getPaths().get(0).getRepository() != null);
			assertTrue(Document.getPaths().get(0).getRepository().getName() != null);
			assertTrue(Document.getPaths().get(0).getRepository().getName().equals(repositoryName));

			assertTrue(Document.getRepositories().get(0).getPaths() != null);
			assertTrue(Document.getRepositories().get(0).getPaths().size() == 1);
			assertTrue(Document.getRepositories().get(0).getPaths().get(0).getPath() != null);
			assertTrue(Document.getRepositories().get(0).getPaths().get(0).getPath().equals(relativePath));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddRepository() {
		try {
			Document.initialize();
			Document.addRepository(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addRepository("");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Document.addRepository(repositoryName);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			assertTrue(Document.getRepositories().get(0).getName().equals(repositoryName));
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddServerAccessRuleForGroup() {
		try {
			Document.initialize();
			Document.addServerAccessRuleForGroup(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addServerAccessRuleForGroup(groupName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addGroup(groupName);
			Document.addServerAccessRuleForGroup(groupName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addServerAccessRuleForGroup(null, level);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Document.addGroup(groupName);
			Document.addServerAccessRuleForGroup(groupName, level);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);

			AccessRule accessRule = Document.getAccessRules().get(0);

			assertTrue(accessRule.getGroup().getName().equals(groupName));
			assertTrue(accessRule.getLevel().equals(level));

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);

			Group group = Document.getGroups().get(0);

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
			Document.initialize();
			Document.addServerAccessRuleForUser(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addServerAccessRuleForUser(userName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addUser(userName);
			Document.addServerAccessRuleForUser(userName, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addServerAccessRuleForUser(null, level);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Document.addUser(userName);
			Document.addServerAccessRuleForUser(userName, level);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);

			AccessRule accessRule = Document.getAccessRules().get(0);

			assertTrue(accessRule.getUser().getName().equals(userName));
			assertTrue(accessRule.getLevel().equals(level));

			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 1);

			User user = Document.getUsers().get(0);

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
			Document.initialize();
			Document.addUser(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addUser("");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.addUser(userName);

			// Add same user again
			Document.addUser(userName);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testChangeGroupMembers() {
		try {
			Document.initialize();

			Document.changeGroupMembers(null, null, null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Document.changeGroupMembers(Document.addGroup(groupName), null, null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Document.changeGroupMembers(Document.addGroup(groupName), new Vector<Group>(), null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);

			assertTrue(group.getGroupMembers() != null);
			assertTrue(group.getGroupMembers().size() == 0);

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 0);

			Document.changeGroupMembers(group, new Vector<Group>(), new Vector<User>());

			assertTrue(group.getGroupMembers() != null);
			assertTrue(group.getGroupMembers().size() == 0);

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);

			assertTrue(group.getGroupMembers() != null);
			assertTrue(group.getGroupMembers().size() == 0);

			assertTrue(group.getUserMembers() != null);
			assertTrue(group.getUserMembers().size() == 0);

			Vector<Group> groupMembers = new Vector<Group>();
			Vector<User> userMembers = new Vector<User>();

			Group groupMember = Document.addGroup(groupName2);
			User userMember = Document.addUser(userName);

			assertTrue(groupMember.getGroups() != null);
			assertTrue(groupMember.getGroups().size() == 0);

			assertTrue(userMember.getGroups() != null);
			assertTrue(userMember.getGroups().size() == 0);

			groupMembers.add(groupMember);
			userMembers.add(userMember);

			Document.changeGroupMembers(group, groupMembers, userMembers);

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
			Document.initialize();

			Document.changeUserMembership(null, null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Document.changeUserMembership(Document.addUser(userName), null);

			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Document.changeUserMembership(Document.addUser(userName), new Vector<Group>());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			User user = Document.addUser(userName);

			assertTrue(user.getGroups() != null);
			assertTrue(user.getGroups().size() == 0);

			Document.changeUserMembership(user, new Vector<Group>());

			assertTrue(user.getGroups() != null);
			assertTrue(user.getGroups().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			User user = Document.addUser(userName);

			assertTrue(user.getGroups() != null);
			assertTrue(user.getGroups().size() == 0);

			Vector<Group> groupMembers = new Vector<Group>();

			Group group = Document.addGroup(groupName);

			assertTrue(group.getGroups() != null);
			assertTrue(group.getGroups().size() == 0);

			groupMembers.add(group);

			Document.changeUserMembership(user, groupMembers);

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
			Document.initialize();
			Document.cloneGroup(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Document.cloneGroup(group, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Document.cloneGroup(group, "");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Document.cloneGroup(group, groupName);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Document.cloneGroup(group, groupName2);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Document.cloneGroup(group, groupName2);

			// Clone same group again
			Document.cloneGroup(group, groupName2);
			fail();
		}
		catch (ApplicationException e) {
		}
	}

	@Test
	public void testCloneUser() {
		try {
			Document.initialize();
			Document.cloneUser(null, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			User user = Document.addUser(userName);
			Document.cloneUser(user, null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			User user = Document.addUser(userName);
			Document.cloneUser(user, "");
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			User user = Document.addUser(userName);
			Document.cloneUser(user, userName);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			User user = Document.addUser(userName);
			Document.cloneUser(user, userName2);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			User user = Document.addUser(userName);
			Document.cloneUser(user, userName2);

			// Clone same user again
			Document.cloneUser(user, userName2);
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
			Document.initialize();
			Document.deleteGroup(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.deleteGroup(new Group());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 0);

			Group group = Document.addGroup(groupName);

			Document.deleteGroup(group);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 0);

			// Delete same group again
			Document.deleteGroup(group);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			Group group = Document.addGroup(groupName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			Document.addAccessRuleForGroup(pathOriginal, groupName, level);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			assertTrue(Document.getAccessRules().get(0).getGroup().equals(group));

			Document.deleteGroup(group);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteGroups() {
		try {
			Document.initialize();
			Document.deleteGroups(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Object[] groups = { new Group() };

			Document.deleteGroups(groups);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 0);

			Group group = Document.addGroup(groupName);

			Object[] groups = { group };

			Document.deleteGroups(groups);

			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 0);

			// Delete same group again
			Document.deleteGroups(groups);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			Group group = Document.addGroup(groupName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			Document.addAccessRuleForGroup(pathOriginal, groupName, level);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			assertTrue(Document.getAccessRules().get(0).getGroup().equals(group));

			Object[] groups = { group };

			Document.deleteGroups(groups);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeletePath() {
		try {
			Document.initialize();
			Document.deletePath(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.deletePath(new Path());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Repository repository = Document.addRepository(repositoryName);

			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 0);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			assertTrue(Document.getRepositories().get(0).equals(repository));

			assertTrue(Document.getRepositories().get(0).getPaths() != null);
			assertTrue(Document.getRepositories().get(0).getPaths().size() == 0);

			Path path = Document.addPath(repository, relativePath);

			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			assertTrue(Document.getPaths().get(0).equals(path));

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			assertTrue(Document.getRepositories().get(0).equals(repository));

			assertTrue(Document.getPaths().get(0).getRepository() != null);
			assertTrue(Document.getPaths().get(0).getRepository().equals(repository));

			assertTrue(Document.getRepositories().get(0).getPaths() != null);
			assertTrue(Document.getRepositories().get(0).getPaths().size() == 1);
			assertTrue(Document.getRepositories().get(0).getPaths().get(0).equals(path));

			Document.deletePath(path);

			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 0);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			assertTrue(Document.getRepositories().get(0).equals(repository));

			assertTrue(Document.getRepositories().get(0).getPaths() != null);
			assertTrue(Document.getRepositories().get(0).getPaths().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteRepositories() {
		try {
			Document.initialize();
			Document.deleteRepositories(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			
			Object[] repositories = { new Repository() };
			
			Document.deleteRepositories(repositories);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			Repository repository = Document.addRepository(repositoryName);

			Object[] repositories = { repository };
			
			Document.deleteRepositories(repositories);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			// Delete same user again
			Document.deleteRepository(repository);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			User user = Document.addUser(userName);
			Repository repository = Document.addRepository(repositoryName);
			Path path = Document.addPath(repository, relativePath);
			AccessRule accessRule = Document.addAccessRuleForUser(repository, relativePath, user, level);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			assertTrue(Document.getRepositories().get(0).equals(repository));

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			assertTrue(Document.getAccessRules().get(0).equals(accessRule));
			
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			assertTrue(Document.getPaths().get(0).equals(path));
			
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			assertTrue(user.getAccessRules().get(0).equals(accessRule));

			Object[] repositories = { repository };
			
			Document.deleteRepositories(repositories);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);
			
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 0);
			
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 0);
			
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
			Document.initialize();
			Document.deleteRepository(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.deleteRepository(new Repository());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			Repository repository = Document.addRepository(repositoryName);

			Document.deleteRepository(repository);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			// Delete same user again
			Document.deleteRepository(repository);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);

			User user = Document.addUser(userName);
			Repository repository = Document.addRepository(repositoryName);
			Path path = Document.addPath(repository, relativePath);
			AccessRule accessRule = Document.addAccessRuleForUser(repository, relativePath, user, level);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 1);
			assertTrue(Document.getRepositories().get(0).equals(repository));

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			assertTrue(Document.getAccessRules().get(0).equals(accessRule));
			
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 1);
			assertTrue(Document.getPaths().get(0).equals(path));
			
			assertTrue(user.getAccessRules() != null);
			assertTrue(user.getAccessRules().size() == 1);
			assertTrue(user.getAccessRules().get(0).equals(accessRule));

			Document.deleteRepository(repository);

			assertTrue(Document.getRepositories() != null);
			assertTrue(Document.getRepositories().size() == 0);
			
			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 0);
			
			assertTrue(Document.getPaths() != null);
			assertTrue(Document.getPaths().size() == 0);
			
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
			Document.initialize();
			Document.deleteUser(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();
			Document.deleteUser(new User());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 0);

			User user = Document.addUser(userName);

			Document.deleteUser(user);

			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 0);

			// Delete same user again
			Document.deleteUser(user);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			User user = Document.addUser(userName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			Document.addAccessRuleForUser(pathOriginal, userName, level);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			assertTrue(Document.getAccessRules().get(0).getUser().equals(user));

			Document.deleteUser(user);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteUsers() {
		try {
			Document.initialize();
			Document.deleteUsers(null);
			fail();
		}
		catch (ApplicationException e) {
		}

		try {
			Document.initialize();

			Object[] users = { new User() };

			Document.deleteUsers(users);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 0);

			User user = Document.addUser(userName);

			Object[] users = { user };

			Document.deleteUsers(users);

			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 0);

			// Delete same user again
			Document.deleteUsers(users);
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();

			User user = Document.addUser(userName);
			Repository repositoryOriginal = Document.addRepository(repositoryName);
			Path pathOriginal = Document.addPath(repositoryOriginal, relativePath);
			Document.addAccessRuleForUser(pathOriginal, userName, level);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 1);
			assertTrue(Document.getAccessRules().get(0).getUser().equals(user));

			Object[] users = { user };

			Document.deleteUsers(users);

			assertTrue(Document.getAccessRules() != null);
			assertTrue(Document.getAccessRules().size() == 0);
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
			Document.initialize();
			
			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 0);
			
			Document.addGroup(groupName);
			
			assertTrue(Document.getGroups() != null);
			assertTrue(Document.getGroups().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testGetGroupsArray() {
		try {
			Document.initialize();
			
			assertTrue(Document.getGroupsArray() != null);
			assertTrue(Document.getGroupsArray().length == 0);
			
			Document.addGroup(groupName);
			
			assertTrue(Document.getGroupsArray() != null);
			assertTrue(Document.getGroupsArray().length == 1);
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
			Document.initialize();
			
			assertTrue(Document.getUserObjects() != null);
			assertTrue(Document.getUserObjects().length == 0);
			
			Document.addUser(userName);
			
			assertTrue(Document.getUserObjects() != null);
			assertTrue(Document.getUserObjects().length == 1);
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
			Document.initialize();
			
			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 0);
			
			Document.addUser(userName);
			
			assertTrue(Document.getUsers() != null);
			assertTrue(Document.getUsers().size() == 1);
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
			Document.initialize();
			Document.addUser(userName);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit user
		// TODO

		// Clone user
		try {
			Document.initialize();
			User user = Document.addUser(userName);
			Document.resetUnsavedChangesFlag();

			Document.cloneUser(user, userName2);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Delete user
		try {
			Document.initialize();
			User user = Document.addUser(userName);
			Document.resetUnsavedChangesFlag();

			Document.deleteUser(user);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Change group membership
		// TODO

		// Add group
		try {
			Document.initialize();
			Document.addGroup(groupName);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addGroup(groupName, null, null);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addGroupByName(groupName, null, null);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit group
		// TODO

		// Clone group
		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Document.resetUnsavedChangesFlag();

			Document.cloneGroup(group, groupName2);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Delete group
		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Document.resetUnsavedChangesFlag();

			Document.deleteGroup(group);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Change group membership
		// TODO

		// Add repository
		try {
			Document.initialize();
			Document.addRepository(repositoryName);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit repository
		// TODO

		// Delete repository
		try {
			Document.initialize();
			Repository repository = Document.addRepository(repositoryName);
			Document.resetUnsavedChangesFlag();

			Document.deleteRepository(repository);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Add path
		try {
			Document.initialize();
			Document.addPath(new Repository(), relativePath);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit path
		// TODO

		// Delete path
		try {
			Document.initialize();
			Path path = Document.addPath(new Repository(), relativePath);
			Document.resetUnsavedChangesFlag();

			Document.deletePath(path);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Add access rule
		try {
			Document.initialize();
			Document.addAccessRuleForGroup(new Path(new Repository(repositoryName), relativePath),
					new Group(groupName), level);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addGroup(groupName);
			Document.addAccessRuleForGroup(new Path(new Repository(repositoryName), relativePath), groupName, level);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addAccessRuleForGroup(new Repository(repositoryName), relativePath, new Group(groupName), level);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addAccessRuleForUser(new Path(new Repository(repositoryName), relativePath), new User(userName),
					level);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addUser(userName);
			Document.addAccessRuleForUser(new Path(new Repository(repositoryName), relativePath), userName, level);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		try {
			Document.initialize();
			Document.addAccessRuleForUser(new Repository(repositoryName), relativePath, new User(userName), level);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}

		// Edit access rule
		// TODO

		// Delete access rule
		try {
			Document.initialize();
			Group group = Document.addGroup(groupName);
			Repository repository = Document.addRepository(repositoryName);
			Path path = Document.addPath(repository, relativePath);
			Document.addAccessRuleForGroup(path, group, level);
			Document.resetUnsavedChangesFlag();

			Document.deleteAccessRule(repositoryName, relativePath, group, null);
			assertTrue(Document.hasUnsavedChanges());
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testInitialize() {
		Document.initialize();

		assertTrue(Document.getUsers().size() == 0);

		try {
			Document.addGroup(groupName);
			Document.addPath(new Repository(repositoryName), relativePath);
			Document.addRepository(repositoryName);
			Document.addUser(userName);
		}
		catch (ApplicationException e) {
			fail();
		}

		assertTrue(Document.getUsers().size() == 1);

		Document.initialize();

		assertTrue(Document.getUsers().size() == 0);
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
