package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;
import org.suafe.core.exceptions.AuthzException;
import org.suafe.core.exceptions.AuthzGroupAlreadyExistsException;
import org.suafe.core.exceptions.AuthzInvalidGroupNameException;
import org.suafe.core.exceptions.AuthzInvalidUserAliasException;
import org.suafe.core.exceptions.AuthzInvalidUserNameException;
import org.suafe.core.exceptions.AuthzUserAliasAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAlreadyExistsException;

/**
 * Unit test for AuthzDocument
 */
public class AuthzDocumentTest {
	@Test
	public void testClearHasUnsavedChanges() {
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());

			// Test create user
			authzDocument.createUser("user", null);

			assertTrue("Document should have unsaved changes", authzDocument.hasUnsavedChanges());

			authzDocument.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());

			// Test create group
			authzDocument.createGroup("group");

			assertTrue("Document should have unsaved changes", authzDocument.hasUnsavedChanges());

			authzDocument.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testCreateGroup() {
		// Test invalid values
		try {
			final AuthzDocument authDocument = new AuthzDocument();

			authDocument.createGroup(null);

			fail("Unexpected successfully created group");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzGroupAlreadyExistsException e) {
			fail("Unexpected AuthzGroupAlreadyExistsException");
		}

		try {
			final AuthzDocument authDocument = new AuthzDocument();

			authDocument.createGroup("");

			fail("Unexpected successfully created group");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzGroupAlreadyExistsException e) {
			fail("Unexpected AuthzGroupAlreadyExistsException");
		}

		try {
			final AuthzDocument authDocument = new AuthzDocument();

			authDocument.createGroup("  ");

			fail("Unexpected successfully created group");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzGroupAlreadyExistsException e) {
			fail("Unexpected AuthzGroupAlreadyExistsException");
		}

		// Test valid values
		try {
			final AuthzDocument authDocument = new AuthzDocument();

			final AuthzGroup group = authDocument.createGroup("name");

			assertNotNull("Group should not be null", group);
			assertEquals("Group name should be valid", "name", group.getName());
		}
		catch (final AuthzInvalidGroupNameException e) {
			fail("Unexpected AuthzInvalidGroupNameException");
		}
		catch (final AuthzGroupAlreadyExistsException e) {
			fail("Unexpected AuthzGroupAlreadyExistsException");
		}

		// Test for exiting group exception
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.createGroup("name");
			authzDocument.createGroup("name");

			fail("Unexpected success creating duplicate group");
		}
		catch (final AuthzInvalidGroupNameException e) {
			fail("Unexpected AuthzInvalidGroupNameException");
		}
		catch (final AuthzGroupAlreadyExistsException e) {
			assertNotNull("Expected a non-null exception", e);
		}
	}

	@Test
	public void testCreateUser() {
		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.createUser(null, null);

			fail("Unexpected successfully created user");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzInvalidUserAliasException e) {
			fail("Unexpected AuthzInvalidUserAliasException");
		}
		catch (final AuthzUserAlreadyExistsException e) {
			fail("Unexpected AuthzUserAlreadyExistsException");
		}
		catch (final AuthzUserAliasAlreadyExistsException e) {
			fail("Unexpected AuthzUserAliasAlreadyExistsException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.createUser("", "");

			fail("Unexpected successfully created user");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzInvalidUserAliasException e) {
			fail("Unexpected AuthzInvalidUserAliasException");
		}
		catch (final AuthzUserAlreadyExistsException e) {
			fail("Unexpected AuthzUserAlreadyExistsException");
		}
		catch (final AuthzUserAliasAlreadyExistsException e) {
			fail("Unexpected AuthzUserAliasAlreadyExistsException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.createUser("  ", "  ");

			fail("Unexpected successfully created user");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzInvalidUserAliasException e) {
			fail("Unexpected AuthzInvalidUserAliasException");
		}
		catch (final AuthzUserAlreadyExistsException e) {
			fail("Unexpected AuthzUserAlreadyExistsException");
		}
		catch (final AuthzUserAliasAlreadyExistsException e) {
			fail("Unexpected AuthzUserAliasAlreadyExistsException");
		}

		// Test valid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzUser user = authzDocument.createUser("name", null);

			assertNotNull("User should not be null", user);
			assertEquals("User name should be valid", "name", user.getName());
			assertNull("User alias should be null", user.getAlias());
		}
		catch (final AuthzInvalidUserNameException e) {
			fail("Unexpected AuthzInvalidUserException");
		}
		catch (final AuthzInvalidUserAliasException e) {
			fail("Unexpected AuthzInvalidUserAliasException");
		}
		catch (final AuthzUserAlreadyExistsException e) {
			fail("Unexpected AuthzUserAlreadyExistsException");
		}
		catch (final AuthzUserAliasAlreadyExistsException e) {
			fail("Unexpected AuthzUserAliasAlreadyExistsException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzUser user = authzDocument.createUser("name", "alias");

			assertNotNull("User should not be null", user);
			assertEquals("User name should be valid", "name", user.getName());
			assertEquals("User alias should be valid", "alias", user.getAlias());
		}
		catch (final AuthzInvalidUserNameException e) {
			fail("Unexpected AuthzInvalidUserException");
		}
		catch (final AuthzInvalidUserAliasException e) {
			fail("Unexpected AuthzInvalidUserAliasException");
		}
		catch (final AuthzUserAlreadyExistsException e) {
			fail("Unexpected AuthzUserAlreadyExistsException");
		}
		catch (final AuthzUserAliasAlreadyExistsException e) {
			fail("Unexpected AuthzUserAliasAlreadyExistsException");
		}

		// Test for exiting user and alias exceptions
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.createUser("name", null);
			authzDocument.createUser("name", null);

			fail("Unexpected success creating duplicate user");
		}
		catch (final AuthzInvalidUserNameException e) {
			fail("Unexpected AuthzInvalidUserException");
		}
		catch (final AuthzInvalidUserAliasException e) {
			fail("Unexpected AuthzInvalidUserAliasException");
		}
		catch (final AuthzUserAlreadyExistsException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzUserAliasAlreadyExistsException e) {
			fail("Unexpected AuthzUserAliasAlreadyExistsException");
		}

		// Test for exiting user and alias exceptions
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.createUser("name1", "alias");
			authzDocument.createUser("name2", "alias");

			fail("Unexpected success creating duplicate user alias");
		}
		catch (final AuthzInvalidUserNameException e) {
			fail("Unexpected AuthzInvalidUserException");
		}
		catch (final AuthzInvalidUserAliasException e) {
			fail("Unexpected AuthzInvalidUserAliasException");
		}
		catch (final AuthzUserAlreadyExistsException e) {
			fail("Unexpected AuthzUserAlreadyExistsException");
		}
		catch (final AuthzUserAliasAlreadyExistsException e) {
			assertNotNull("Expected a non-null exception", e);
		}
	}

	@Test
	public void testDoesGroupNameExist() {
		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesGroupNameExist(null);

			fail("Unexpected success calling doesGroupNameExist() with null user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesGroupNameExist("");

			fail("Unexpected success calling doesGroupNameExist() with null user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesGroupNameExist("  ");

			fail("Unexpected success calling doesGroupNameExist() with null user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("Group should not exist", authzDocument.doesGroupNameExist("name"));

			authzDocument.createGroup("name");

			assertTrue("Group should exist", authzDocument.doesGroupNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("Group should not exist", authzDocument.doesGroupNameExist("  name  "));

			authzDocument.createGroup("name");

			assertTrue("Group should exist", authzDocument.doesGroupNameExist("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testDoesUserAliasExist() {
		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesUserAliasExist(null);

			fail("Unexpected success calling doesUserAliasExist() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesUserAliasExist("");

			fail("Unexpected success calling doesUserAliasExist() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesUserAliasExist("  ");

			fail("Unexpected success calling doesUserAliasExist() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("User with alias should not exist", authzDocument.doesUserAliasExist("alias"));

			authzDocument.createUser("name", "alias");

			assertTrue("User with alias should exist", authzDocument.doesUserAliasExist("alias"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("User with name should not exist", authzDocument.doesUserAliasExist("  alias  "));

			authzDocument.createUser("name", "alias");

			assertTrue("User with name should exist", authzDocument.doesUserAliasExist("  alias  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testDoesUserNameExist() {
		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesUserNameExist(null);

			fail("Unexpected success calling doesUserNameExist() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesUserNameExist("");

			fail("Unexpected success calling doesUserNameExist() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.doesUserNameExist("  ");

			fail("Unexpected success calling doesUserNameExist() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("User with name should not exist", authzDocument.doesUserNameExist("name"));

			authzDocument.createUser("name", null);

			assertTrue("User with name should exist", authzDocument.doesUserNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("User with name should not exist", authzDocument.doesUserNameExist("  name  "));

			authzDocument.createUser("name", null);

			assertTrue("User with name should exist", authzDocument.doesUserNameExist("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetGroups() {
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNotNull("Groups should not be null", authzDocument.getGroups());
			assertNotNull("Groups should empty", authzDocument.getGroups().size() == 0);

			authzDocument.createGroup("name");

			assertNotNull("Groups should not be null", authzDocument.getGroups());
			assertNotNull("Groups should contain one user", authzDocument.getGroups().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of groups collection
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			Collection<AuthzGroup> groups = authzDocument.getGroups();

			assertNotNull("Groups should not be null", groups);
			assertNotNull("Groups should empty", groups.size() == 0);

			authzDocument.createGroup("name");

			assertNotNull("Groups should remain empty", groups.size() == 0);

			groups = authzDocument.getGroups();

			assertNotNull("Groups should not be null", authzDocument.getGroups());
			assertNotNull("Groups should contain one user", authzDocument.getGroups().size() == 1);

			try {
				groups.add(new AuthzGroup("name"));

				fail("Successfully modified groups list; should not have worked since collection is immutable");
			}
			catch (final UnsupportedOperationException e) {
				assertNotNull("Expected a non-null exception", e);
			}

			try {
				for (final AuthzGroup group : groups) {
					groups.remove(group);
				}

				fail("Successfully modified groups list; should not have worked since collection is immutable");
			}
			catch (final UnsupportedOperationException e) {
				assertNotNull("Expected a non-null exception", e);
			}
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetGroupWithName() {
		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getGroupWithName(null);

			fail("Unexpected success calling getGroupWithName() with null group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getGroupWithName("");

			fail("Unexpected success calling getGroupWithName() with null group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getGroupWithName("  ");

			fail("Unexpected success calling getGroupWithName() with null group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNull("Group with name should not exist", authzDocument.getGroupWithName("name"));

			authzDocument.createGroup("name");

			assertNotNull("Group with name should exist", authzDocument.getGroupWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNull("Group with name should not exist", authzDocument.getGroupWithName("  name  "));

			authzDocument.createGroup("name");

			assertNotNull("Group with name should exist", authzDocument.getGroupWithName("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetUsers() {
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNotNull("Users should not be null", authzDocument.getUsers());
			assertNotNull("Users should empty", authzDocument.getUsers().size() == 0);

			authzDocument.createUser("user", null);

			assertNotNull("Users should not be null", authzDocument.getUsers());
			assertNotNull("Users should contain one user", authzDocument.getUsers().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of users collection
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			Collection<AuthzUser> users = authzDocument.getUsers();

			assertNotNull("Users should not be null", users);
			assertNotNull("Users should empty", users.size() == 0);

			authzDocument.createUser("user", null);

			assertNotNull("Users should remain empty", users.size() == 0);

			users = authzDocument.getUsers();

			assertNotNull("Users should not be null", authzDocument.getUsers());
			assertNotNull("Users should contain one user", authzDocument.getUsers().size() == 1);

			try {
				users.add(new AuthzUser("name", "alias"));

				fail("Successfully modified users list; should not have worked since collection is immutable");
			}
			catch (final UnsupportedOperationException e) {
				assertNotNull("Expected a non-null exception", e);
			}

			try {
				for (final AuthzUser user : users) {
					users.remove(user);
				}

				fail("Successfully modified users list; should not have worked since collection is immutable");
			}
			catch (final UnsupportedOperationException e) {
				assertNotNull("Expected a non-null exception", e);
			}
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetUserWithAlias() {
		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getUserWithAlias(null);

			fail("Unexpected success calling getUserWithAlias() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getUserWithAlias("");

			fail("Unexpected success calling getUserWithAlias() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getUserWithAlias("  ");

			fail("Unexpected success calling getUserWithAlias() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNull("User with alias should not exist", authzDocument.getUserWithAlias("alias"));

			authzDocument.createUser("name", "alias");

			assertNotNull("User with alias should exist", authzDocument.getUserWithAlias("alias"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNull("User with alias should not exist", authzDocument.getUserWithAlias("  alias  "));

			authzDocument.createUser("name", "alias");

			assertNotNull("User with alias should exist", authzDocument.getUserWithAlias("  alias  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetUserWithName() {
		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getUserWithName(null);

			fail("Unexpected success calling getUserWithName() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getUserWithName("");

			fail("Unexpected success calling getUserWithName() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			authzDocument.getUserWithName("  ");

			fail("Unexpected success calling getUserWithName() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNull("User with name should not exist", authzDocument.getUserWithName("name"));

			authzDocument.createUser("name", "alias");

			assertNotNull("User with name should exist", authzDocument.getUserWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNull("User with name should not exist", authzDocument.getUserWithName("  name  "));

			authzDocument.createUser("name", "alias");

			assertNotNull("User with name should exist", authzDocument.getUserWithName("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testHasUnsavedChanges() {
		// Test has unsaved changes flag when creating a user
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());

			authzDocument.createUser("user", null);

			assertTrue("Document should have unsaved changes", authzDocument.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test has unsaved changes flag when creating a group
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());

			authzDocument.createGroup("group");

			assertTrue("Document should have unsaved changes", authzDocument.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testInitialize() {
		try {
			final AuthzDocument authzDocument = new AuthzDocument();

			assertNotNull("Groups should not be null", authzDocument.getGroups());
			assertNotNull("Groups should empty", authzDocument.getGroups().size() == 0);
			assertNotNull("Users should not be null", authzDocument.getUsers());
			assertNotNull("Users should empty", authzDocument.getUsers().size() == 0);
			assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());

			authzDocument.createGroup("group");
			authzDocument.createUser("user", null);

			assertNotNull("Groups should not be null", authzDocument.getGroups());
			assertNotNull("Groups should contain one group", authzDocument.getGroups().size() == 1);
			assertNotNull("Users should not be null", authzDocument.getUsers());
			assertNotNull("Users should contain one user", authzDocument.getUsers().size() == 1);
			assertTrue("Document should have unsaved changes", authzDocument.hasUnsavedChanges());

			authzDocument.initialize();

			assertNotNull("Groups should not be null", authzDocument.getGroups());
			assertNotNull("Groups should empty", authzDocument.getGroups().size() == 0);
			assertNotNull("Users should not be null", authzDocument.getUsers());
			assertNotNull("Users should empty", authzDocument.getUsers().size() == 0);
			assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testIsValidGroupName() {
		final AuthzDocument authzDocument = new AuthzDocument();

		// Test invalid values
		assertFalse("Null group name is invalid", authzDocument.isValidGroupName(null));
		assertFalse("Empty group name is invalid", authzDocument.isValidGroupName(""));
		assertFalse("Blank group name is invalid", authzDocument.isValidGroupName("  "));

		// Test valid values
		assertTrue("Non-blank group name is valid", authzDocument.isValidGroupName("name"));
		assertTrue("Non-blank group name is valid", authzDocument.isValidGroupName("  name  "));
	}

	@Test
	public void testIsValidUserAlias() {
		final AuthzDocument authzDocument = new AuthzDocument();

		// Test invalid values
		assertFalse("Null user alias is invalid", authzDocument.isValidUserAlias(null));
		assertFalse("Empty user alias is invalid", authzDocument.isValidUserAlias(""));
		assertFalse("Blank user alias is invalid", authzDocument.isValidUserAlias("  "));

		// Test valid values
		assertTrue("Non-blank user alias is valid", authzDocument.isValidUserAlias("name"));
		assertTrue("Non-blank user alias is valid", authzDocument.isValidUserAlias("  name  "));
	}

	@Test
	public void testIsValidUserName() {
		final AuthzDocument authzDocument = new AuthzDocument();

		// Test invalid values
		assertFalse("Null user name is invalid", authzDocument.isValidUserName(null));
		assertFalse("Empty user name is invalid", authzDocument.isValidUserName(""));
		assertFalse("Blank user name is invalid", authzDocument.isValidUserName("  "));

		// Test valid values
		assertTrue("Non-blank user name is valid", authzDocument.isValidUserName("name"));
		assertTrue("Non-blank user name is valid", authzDocument.isValidUserName("  name  "));
	}

	@Test
	public void testSetHasUnsavedChanges() {
		final AuthzDocument authzDocument = new AuthzDocument();

		assertFalse("Document should not have any unsaved changes", authzDocument.hasUnsavedChanges());

		authzDocument.setHasUnsavedChanges();

		assertTrue("Document should have unsaved changes", authzDocument.hasUnsavedChanges());
	}
}
