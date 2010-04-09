package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzException;
import org.suafe.core.exceptions.AuthzGroupAlreadyExistsException;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzInvalidGroupNameException;
import org.suafe.core.exceptions.AuthzInvalidRepositoryNameException;
import org.suafe.core.exceptions.AuthzInvalidUserAliasException;
import org.suafe.core.exceptions.AuthzInvalidUserNameException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;
import org.suafe.core.exceptions.AuthzRepositoryAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAliasAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAlreadyExistsException;

/**
 * Unit test for AuthzDocument
 */
public class AuthzDocumentTest {
	@Test
	public void testAddGroupMember() {
		try {
			final AuthzDocument document = new AuthzDocument();

			document.addGroupMember(null, new AuthzUser(null, null));

			fail("Successfully added member to null group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.addGroupMember(new AuthzGroup(null), null);

			fail("Successfully added null member to a group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.addGroupMember(new AuthzGroup(null), new AuthzUser(null, null));
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();
			final AuthzGroup group = new AuthzGroup("name");
			final AuthzUser user = new AuthzUser("user", null);

			group.addMember(user);

			document.addGroupMember(group, user);

			fail("Successfully added user to the same group more than once");
		}
		catch (final AuthzGroupMemberAlreadyExistsException e) {
			assertNotNull("Expected AuthzGroupMemberAlreadyExistsException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();
			final AuthzGroup group = new AuthzGroup("name");
			final AuthzUser user = new AuthzUser("user", null);

			user.addGroup(group);

			document.addGroupMember(group, user);

			fail("Successfully added user to the same group more than once");
		}
		catch (final AuthzAlreadyMemberOfGroupException e) {
			assertNotNull("Expected AuthzAlreadyMemberOfGroupException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}
	}

	@Test
	public void testClearHasUnsavedChanges() {
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create user
			document.createUser("user", null);

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create group
			document.createGroup("group");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create repository
			document.createRepository("repository");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testCreateGroup() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.createGroup(null);

			fail("Unexpected successfully created group");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzGroupAlreadyExistsException e) {
			fail("Unexpected AuthzGroupAlreadyExistsException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.createGroup("");

			fail("Unexpected successfully created group");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzGroupAlreadyExistsException e) {
			fail("Unexpected AuthzGroupAlreadyExistsException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.createGroup("  ");

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
			final AuthzDocument document = new AuthzDocument();

			final AuthzGroup group = document.createGroup("name");

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
			final AuthzDocument document = new AuthzDocument();

			document.createGroup("name");
			document.createGroup("name");

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
	public void testCreateRepository() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.createRepository(null);

			fail("Unexpected successfully created repository");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzRepositoryAlreadyExistsException e) {
			fail("Unexpected AuthzRepositoryAlreadyExistsException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.createRepository("");

			fail("Unexpected successfully created repository");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzRepositoryAlreadyExistsException e) {
			fail("Unexpected AuthzRepositoryAlreadyExistsException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.createRepository("  ");

			fail("Unexpected successfully created repository");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzRepositoryAlreadyExistsException e) {
			fail("Unexpected AuthzRepositoryAlreadyExistsException");
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			final AuthzRepository repository = document.createRepository("name");

			assertNotNull("Repository should not be null", repository);
			assertEquals("Repository name should be valid", "name", repository.getName());
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			fail("Unexpected AuthzInvalidRepositoryNameException");
		}
		catch (final AuthzRepositoryAlreadyExistsException e) {
			fail("Unexpected AuthzRepositoryAlreadyExistsException");
		}

		// Test for exiting repository exception
		try {
			final AuthzDocument document = new AuthzDocument();

			document.createRepository("name");
			document.createRepository("name");

			fail("Unexpected success creating duplicate repository");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			fail("Unexpected AuthzInvalidRepositoryNameException");
		}
		catch (final AuthzRepositoryAlreadyExistsException e) {
			assertNotNull("Expected a non-null exception", e);
		}
	}

	@Test
	public void testCreateUser() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.createUser(null, null);

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
			final AuthzDocument document = new AuthzDocument();

			document.createUser("", "");

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
			final AuthzDocument document = new AuthzDocument();

			document.createUser("  ", "  ");

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
			final AuthzDocument document = new AuthzDocument();
			final AuthzUser user = document.createUser("name", null);

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
			final AuthzDocument document = new AuthzDocument();
			final AuthzUser user = document.createUser("name", "alias");

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
			final AuthzDocument document = new AuthzDocument();

			document.createUser("name", null);
			document.createUser("name", null);

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
			final AuthzDocument document = new AuthzDocument();

			document.createUser("name1", "alias");
			document.createUser("name2", "alias");

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
			final AuthzDocument document = new AuthzDocument();

			document.doesGroupNameExist(null);

			fail("Unexpected success calling doesGroupNameExist() with null user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesGroupNameExist("");

			fail("Unexpected success calling doesGroupNameExist() with null user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesGroupNameExist("  ");

			fail("Unexpected success calling doesGroupNameExist() with null user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Group should not exist", document.doesGroupNameExist("name"));

			document.createGroup("name");

			assertTrue("Group should exist", document.doesGroupNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Group should not exist", document.doesGroupNameExist("  name  "));

			document.createGroup("name");

			assertTrue("Group should exist", document.doesGroupNameExist("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testDoesRepositoryNameExist() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesRepositoryNameExist(null);

			fail("Unexpected success calling doesRepositoryNameExist() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesRepositoryNameExist("");

			fail("Unexpected success calling doesRepositoryNameExist() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesRepositoryNameExist("  ");

			fail("Unexpected success calling doesRepositoryNameExist() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Repository with name should not exist", document.doesRepositoryNameExist("name"));

			document.createRepository("name");

			assertTrue("Repository with name should exist", document.doesRepositoryNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Repository with name should not exist", document.doesRepositoryNameExist("  name  "));

			document.createRepository("name");

			assertTrue("Repository with name should exist", document.doesRepositoryNameExist("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testDoesUserAliasExist() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesUserAliasExist(null);

			fail("Unexpected success calling doesUserAliasExist() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesUserAliasExist("");

			fail("Unexpected success calling doesUserAliasExist() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesUserAliasExist("  ");

			fail("Unexpected success calling doesUserAliasExist() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("User with alias should not exist", document.doesUserAliasExist("alias"));

			document.createUser("name", "alias");

			assertTrue("User with alias should exist", document.doesUserAliasExist("alias"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("User with name should not exist", document.doesUserAliasExist("  alias  "));

			document.createUser("name", "alias");

			assertTrue("User with name should exist", document.doesUserAliasExist("  alias  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testDoesUserNameExist() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesUserNameExist(null);

			fail("Unexpected success calling doesUserNameExist() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesUserNameExist("");

			fail("Unexpected success calling doesUserNameExist() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.doesUserNameExist("  ");

			fail("Unexpected success calling doesUserNameExist() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("User with name should not exist", document.doesUserNameExist("name"));

			document.createUser("name", null);

			assertTrue("User with name should exist", document.doesUserNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("User with name should not exist", document.doesUserNameExist("  name  "));

			document.createUser("name", null);

			assertTrue("User with name should exist", document.doesUserNameExist("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetGroups() {
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNotNull("Groups should not be null", document.getGroups());
			assertNotNull("Groups should empty", document.getGroups().size() == 0);

			document.createGroup("name");

			assertNotNull("Groups should not be null", document.getGroups());
			assertNotNull("Groups should contain one user", document.getGroups().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of groups collection
		try {
			final AuthzDocument document = new AuthzDocument();

			Collection<AuthzGroup> groups = document.getGroups();

			assertNotNull("Groups should not be null", groups);
			assertNotNull("Groups should empty", groups.size() == 0);

			document.createGroup("name");

			assertNotNull("Groups should remain empty", groups.size() == 0);

			groups = document.getGroups();

			assertNotNull("Groups should not be null", document.getGroups());
			assertNotNull("Groups should contain one user", document.getGroups().size() == 1);

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
			final AuthzDocument document = new AuthzDocument();

			document.getGroupWithName(null);

			fail("Unexpected success calling getGroupWithName() with null group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getGroupWithName("");

			fail("Unexpected success calling getGroupWithName() with null group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getGroupWithName("  ");

			fail("Unexpected success calling getGroupWithName() with null group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("Group with name should not exist", document.getGroupWithName("name"));

			document.createGroup("name");

			assertNotNull("Group with name should exist", document.getGroupWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("Group with name should not exist", document.getGroupWithName("  name  "));

			document.createGroup("name");

			assertNotNull("Group with name should exist", document.getGroupWithName("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetRepositories() {
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNotNull("Repositories should not be null", document.getRepositories());
			assertNotNull("Repositories should empty", document.getRepositories().size() == 0);

			document.createRepository("repository");

			assertNotNull("Repositories should not be null", document.getRepositories());
			assertNotNull("Repositories should contain one repository", document.getRepositories().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of repositories collection
		try {
			final AuthzDocument document = new AuthzDocument();

			Collection<AuthzRepository> repositories = document.getRepositories();

			assertNotNull("Repositories should not be null", repositories);
			assertNotNull("Repositories should empty", repositories.size() == 0);

			document.createRepository("repository");

			assertNotNull("Repositories should remain empty", repositories.size() == 0);

			repositories = document.getRepositories();

			assertNotNull("Repositories should not be null", document.getRepositories());
			assertNotNull("Repositories should contain one repository", document.getRepositories().size() == 1);

			try {
				repositories.add(new AuthzRepository("repository"));

				fail("Successfully modified repositories list; should not have worked since collection is immutable");
			}
			catch (final UnsupportedOperationException e) {
				assertNotNull("Expected a non-null exception", e);
			}

			try {
				for (final AuthzRepository repository : repositories) {
					repositories.remove(repository);
				}

				fail("Successfully modified repositories list; should not have worked since collection is immutable");
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
	public void testGetRepositoryWithName() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.getRepositoryWithName(null);

			fail("Unexpected success calling getRepositoryWithName() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getRepositoryWithName("");

			fail("Unexpected success calling getRepositoryWithName() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getRepositoryWithName("  ");

			fail("Unexpected success calling getRepositoryWithName() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("Repository with name should not exist", document.getRepositoryWithName("name"));

			document.createRepository("name");

			assertNotNull("Repository with name should exist", document.getRepositoryWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("Repository with name should not exist", document.getRepositoryWithName("  name  "));

			document.createRepository("name");

			assertNotNull("Repository with name should exist", document.getRepositoryWithName("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetUsers() {
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNotNull("Users should not be null", document.getUsers());
			assertNotNull("Users should empty", document.getUsers().size() == 0);

			document.createUser("user", null);

			assertNotNull("Users should not be null", document.getUsers());
			assertNotNull("Users should contain one user", document.getUsers().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of users collection
		try {
			final AuthzDocument document = new AuthzDocument();

			Collection<AuthzUser> users = document.getUsers();

			assertNotNull("Users should not be null", users);
			assertNotNull("Users should empty", users.size() == 0);

			document.createUser("user", null);

			assertNotNull("Users should remain empty", users.size() == 0);

			users = document.getUsers();

			assertNotNull("Users should not be null", document.getUsers());
			assertNotNull("Users should contain one user", document.getUsers().size() == 1);

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
			final AuthzDocument document = new AuthzDocument();

			document.getUserWithAlias(null);

			fail("Unexpected success calling getUserWithAlias() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getUserWithAlias("");

			fail("Unexpected success calling getUserWithAlias() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getUserWithAlias("  ");

			fail("Unexpected success calling getUserWithAlias() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("User with alias should not exist", document.getUserWithAlias("alias"));

			document.createUser("name", "alias");

			assertNotNull("User with alias should exist", document.getUserWithAlias("alias"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("User with alias should not exist", document.getUserWithAlias("  alias  "));

			document.createUser("name", "alias");

			assertNotNull("User with alias should exist", document.getUserWithAlias("  alias  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetUserWithName() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocument();

			document.getUserWithName(null);

			fail("Unexpected success calling getUserWithName() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getUserWithName("");

			fail("Unexpected success calling getUserWithName() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.getUserWithName("  ");

			fail("Unexpected success calling getUserWithName() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("User with name should not exist", document.getUserWithName("name"));

			document.createUser("name", "alias");

			assertNotNull("User with name should exist", document.getUserWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			assertNull("User with name should not exist", document.getUserWithName("  name  "));

			document.createUser("name", "alias");

			assertNotNull("User with name should exist", document.getUserWithName("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testHasUnsavedChanges() {
		// Test has unsaved changes flag when creating a user
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createUser("user", null);

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test has unsaved changes flag when creating a group
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createGroup("group");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test has unsaved changes flag when creating a repository
		try {
			final AuthzDocument document = new AuthzDocument();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createRepository("repository");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testInitialize() {
		try {
			final AuthzDocument document = new AuthzDocument();

			assertNotNull("Groups should not be null", document.getGroups());
			assertNotNull("Groups should empty", document.getGroups().size() == 0);
			assertNotNull("Repositories should not be null", document.getRepositories());
			assertNotNull("Repositories should empty", document.getRepositories().size() == 0);
			assertNotNull("Users should not be null", document.getUsers());
			assertNotNull("Users should empty", document.getUsers().size() == 0);
			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createGroup("group");
			document.createRepository("repository");
			document.createUser("user", null);

			assertNotNull("Groups should not be null", document.getGroups());
			assertNotNull("Groups should contain one group", document.getGroups().size() == 1);
			assertNotNull("Repositories should not be null", document.getRepositories());
			assertNotNull("Repositories should empty", document.getRepositories().size() == 1);
			assertNotNull("Users should not be null", document.getUsers());
			assertNotNull("Users should contain one user", document.getUsers().size() == 1);
			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.initialize();

			assertNotNull("Groups should not be null", document.getGroups());
			assertNotNull("Groups should empty", document.getGroups().size() == 0);
			assertNotNull("Repositories should not be null", document.getRepositories());
			assertNotNull("Repositories should empty", document.getRepositories().size() == 0);
			assertNotNull("Users should not be null", document.getUsers());
			assertNotNull("Users should empty", document.getUsers().size() == 0);
			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testIsValidGroupName() {
		final AuthzDocument document = new AuthzDocument();

		// Test invalid values
		assertFalse("Null group name is invalid", document.isValidGroupName(null));
		assertFalse("Empty group name is invalid", document.isValidGroupName(""));
		assertFalse("Blank group name is invalid", document.isValidGroupName("  "));

		// Test valid values
		assertTrue("Non-blank group name is valid", document.isValidGroupName("name"));
		assertTrue("Non-blank group name is valid", document.isValidGroupName("  name  "));
	}

	@Test
	public void testIsValidRepositoryName() {
		final AuthzDocument document = new AuthzDocument();

		// Test invalid values
		assertFalse("Null repository name is invalid", document.isValidRepositoryName(null));
		assertFalse("Empty repository name is invalid", document.isValidRepositoryName(""));
		assertFalse("Blank repository name is invalid", document.isValidRepositoryName("  "));

		// Test valid values
		assertTrue("Non-blank repository name is valid", document.isValidRepositoryName("name"));
		assertTrue("Non-blank repository name is valid", document.isValidRepositoryName("  name  "));
	}

	@Test
	public void testIsValidUserAlias() {
		final AuthzDocument document = new AuthzDocument();

		// Test invalid values
		assertFalse("Null user alias is invalid", document.isValidUserAlias(null));
		assertFalse("Empty user alias is invalid", document.isValidUserAlias(""));
		assertFalse("Blank user alias is invalid", document.isValidUserAlias("  "));

		// Test valid values
		assertTrue("Non-blank user alias is valid", document.isValidUserAlias("name"));
		assertTrue("Non-blank user alias is valid", document.isValidUserAlias("  name  "));
	}

	@Test
	public void testIsValidUserName() {
		final AuthzDocument document = new AuthzDocument();

		// Test invalid values
		assertFalse("Null user name is invalid", document.isValidUserName(null));
		assertFalse("Empty user name is invalid", document.isValidUserName(""));
		assertFalse("Blank user name is invalid", document.isValidUserName("  "));

		// Test valid values
		assertTrue("Non-blank user name is valid", document.isValidUserName("name"));
		assertTrue("Non-blank user name is valid", document.isValidUserName("  name  "));
	}

	@Test
	public void testRemoveGroupMember() {
		try {
			final AuthzDocument document = new AuthzDocument();

			document.removeGroupMember(null, new AuthzUser(null, null));

			fail("Successfully removed user from a null group");
		}
		catch (final NullPointerException e) {
			assertNotNull("NullPointerException expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();

			document.removeGroupMember(new AuthzGroup(null), null);

			fail("Successfully removed null from a group");
		}
		catch (final NullPointerException e) {
			assertNotNull("NullPointerException expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();
			final AuthzGroup group = new AuthzGroup(null);
			final AuthzUser user = new AuthzUser(null, null);

			user.addGroup(group);

			document.removeGroupMember(group, user);
		}
		catch (final AuthzNotGroupMemberException e) {
			assertNotNull("Expected AuthzNotGroupMemberException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();
			final AuthzGroup group = new AuthzGroup(null);
			final AuthzUser user = new AuthzUser(null, null);

			group.addMember(user);

			document.removeGroupMember(group, user);
		}
		catch (final AuthzNotMemberOfGroupException e) {
			assertNotNull("Expected AuthzNotMemberOfGroupException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();
			final AuthzGroup group = new AuthzGroup(null);
			final AuthzUser user = new AuthzUser(null, null);

			group.addMember(user);
			user.addGroup(group);

			document.removeGroupMember(group, user);
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocument();
			final AuthzGroup group = new AuthzGroup(null);
			final AuthzUser user = new AuthzUser(null, null);

			document.addGroupMember(group, user);
			document.removeGroupMember(group, user);
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}
	}

	@Test
	public void testSetHasUnsavedChanges() {
		final AuthzDocument document = new AuthzDocument();

		assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

		document.setHasUnsavedChanges();

		assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
	}

	@Test
	public void testToString() {
		final AuthzDocument document = new AuthzDocument();

		assertNotNull("Should not be null for empty document", document.toString());

		assertTrue("Should contain group information", document.toString().contains("groups"));
		assertTrue("Should contain repository information", document.toString().contains("repositories"));
		assertTrue("Should contain user information", document.toString().contains("users"));
	}
}
