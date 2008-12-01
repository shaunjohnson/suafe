package org.xiaoniu.suafe.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;

public class DocumentUndoTest {

	private static final String accessLevel = Constants.ACCESS_LEVEL_READONLY;

	private static final String groupName = "TestGroupName";

	private static final String groupName2 = "TestGroupName1";

	private static final String pathString = "/pathString";

	private static final String repositoryName = "TestRepositoryName";

	private static final String userName = "TestUserName";

	private static final String userName2 = "TestUserName2";

	@Test
	public void testClearUndoStack() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());
			document.addUser(userName);
			assertTrue(document.hasUndoActions());
			document.clearUndoStack();
			assertTrue(!document.hasUndoActions());
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testHasUndoActions() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());
			document.addUser(userName);
			assertTrue(document.hasUndoActions());
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testIsUndoEnabled() {
		Document document = new Document();

		assertTrue(document.isUndoEnabled());

		document.disableUndo();
		assertTrue(!document.isUndoEnabled());

		document.enableUndo();
		assertTrue(document.isUndoEnabled());

		document.disableUndo();
		assertTrue(!document.isUndoEnabled());

		document.initialize();
		assertTrue(document.isUndoEnabled());
	}

	@Test
	public void testUndoLastAddGroupAction() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());
			document.addGroup(groupName);
			assertTrue(document.hasUndoActions());
			assertTrue(document.findGroup(groupName) != null);
			document.undoLastAction();
			assertTrue(document.findGroup(groupName) == null);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUndoLastAddGroupByNameAction() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());

			Group group = document.addGroup(groupName2);
			User user = document.addUser(userName);

			List<String> groupMemberNames = new ArrayList<String>(1);
			List<String> userMemberNames = new ArrayList<String>(1);

			groupMemberNames.add(groupName2);
			userMemberNames.add(userName);

			document.clearUndoStack();
			assertTrue(!document.hasUndoActions());

			document.addGroupByName(groupName, groupMemberNames, userMemberNames);

			assertTrue(document.hasUndoActions());

			Group group2 = document.findGroup(groupName);

			assertTrue(group2 != null);
			assertTrue(group2.getGroupMembers().contains(group));
			assertTrue(group2.getUserMembers().contains(user));
			assertTrue(group.getGroups().contains(group2));
			assertTrue(user.getGroups().contains(group2));

			document.undoLastAction();

			Group group3 = document.findGroup(groupName);

			assertTrue(group3 == null);
			assertTrue(group.getGroups().size() == 0);
			assertTrue(user.getGroups().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUndoLastAddGroupByObjectAction() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());

			Group group = document.addGroup(groupName2);
			User user = document.addUser(userName);

			List<Group> groupMembers = new ArrayList<Group>(1);
			List<User> userMembers = new ArrayList<User>(1);

			groupMembers.add(group);
			userMembers.add(user);

			document.clearUndoStack();
			assertTrue(!document.hasUndoActions());

			document.addGroup(groupName, groupMembers, userMembers);

			assertTrue(document.hasUndoActions());

			Group group2 = document.findGroup(groupName);

			assertTrue(group2 != null);
			assertTrue(group2.getGroupMembers().contains(group));
			assertTrue(group2.getUserMembers().contains(user));
			assertTrue(group.getGroups().contains(group2));
			assertTrue(user.getGroups().contains(group2));

			document.undoLastAction();

			Group group3 = document.findGroup(groupName);

			assertTrue(group3 == null);
			assertTrue(group.getGroups().size() == 0);
			assertTrue(user.getGroups().size() == 0);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUndoLastAddRepositoryAction() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());
			document.addRepository(repositoryName);
			assertTrue(document.hasUndoActions());
			assertTrue(document.findRepository(repositoryName) != null);
			document.undoLastAction();
			assertTrue(document.findRepository(repositoryName) == null);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUndoLastAddUserAction() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());
			document.addUser(userName);
			assertTrue(document.hasUndoActions());
			assertTrue(document.findUser(userName) != null);
			document.undoLastAction();
			assertTrue(document.findUser(userName) == null);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUndoLastCloneGroupAction() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());

			Group group = document.addGroup(groupName);
			document.addAccessRuleForGroup(new Repository(), pathString, group, accessLevel);

			document.clearUndoStack();

			assertTrue(!document.hasUndoActions());
			assertTrue(document.findGroup(groupName) != null);
			assertTrue(document.getAccessRules().size() == 1);

			document.cloneGroup(group, groupName2);

			assertTrue(document.hasUndoActions());
			assertTrue(document.findGroup(groupName) != null);
			assertTrue(document.findGroup(groupName2) != null);
			assertTrue(document.getAccessRules().size() == 2);

			document.undoLastAction();

			assertTrue(document.findGroup(groupName) != null);
			assertTrue(document.findGroup(groupName2) == null);
			assertTrue(document.getAccessRules().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUndoLastCloneUserAction() {
		try {
			Document document = new Document();

			assertTrue(!document.hasUndoActions());

			User user = document.addUser(userName);
			document.addAccessRuleForUser(new Repository(), pathString, user, accessLevel);

			document.clearUndoStack();

			assertTrue(!document.hasUndoActions());
			assertTrue(document.findUser(userName) != null);
			assertTrue(document.getAccessRules().size() == 1);

			document.cloneUser(user, userName2);

			assertTrue(document.hasUndoActions());
			assertTrue(document.findUser(userName) != null);
			assertTrue(document.findUser(userName2) != null);
			assertTrue(document.getAccessRules().size() == 2);

			document.undoLastAction();

			assertTrue(document.findUser(userName) != null);
			assertTrue(document.findUser(userName2) == null);
			assertTrue(document.getAccessRules().size() == 1);
		}
		catch (ApplicationException e) {
			fail();
		}
	}
}
