package org.suafe.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;
import org.suafe.core.AuthzAccessRule;
import org.suafe.core.AuthzDocument;
import org.suafe.core.AuthzGroup;
import org.suafe.core.AuthzPath;
import org.suafe.core.AuthzRepository;
import org.suafe.core.AuthzUser;
import org.suafe.core.constants.AuthzAccessLevel;
import org.suafe.core.constants.AuthzAccessLevelIF;
import org.suafe.core.exceptions.AuthzAccessRuleAlreadyExistsException;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzException;
import org.suafe.core.exceptions.AuthzGroupAlreadyExistsException;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzInvalidGroupNameException;
import org.suafe.core.exceptions.AuthzInvalidPathException;
import org.suafe.core.exceptions.AuthzInvalidRepositoryNameException;
import org.suafe.core.exceptions.AuthzInvalidUserAliasException;
import org.suafe.core.exceptions.AuthzInvalidUserNameException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;
import org.suafe.core.exceptions.AuthzPathAlreadyExistsException;
import org.suafe.core.exceptions.AuthzRepositoryAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAliasAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAlreadyExistsException;

/**
 * Unit test for AuthzDocument
 */
public class AuthzDocumentImplTest {
	@Test
	public void testAddGroupMember() {
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.addGroupMember(null, new AuthzUserImpl("name", null));

			fail("Successfully added member to null group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.addGroupMember(new AuthzGroupImpl("name"), null);

			fail("Successfully added null member to a group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.addGroupMember(new AuthzGroupImpl("name"), new AuthzUserImpl("name", null));
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzUserImpl user = new AuthzUserImpl("user", null);

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
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzUserImpl user = new AuthzUserImpl("user", null);

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
			final AuthzDocumentImpl document = new AuthzDocumentImpl();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create user
			document.createUser("user", null);

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create group
			final AuthzGroupImpl group = document.createGroup("group");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create repository
			final AuthzRepository repository = document.createRepository("repository");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create path
			final AuthzPathImpl path = document.createPath(repository, "/path");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			// Test create access rule
			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testCreateAccessRuleGroup() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.createAccessRule(null, document.createGroup("name"), AuthzAccessLevel.READ_WRITE);

			fail("Unexpected sucessfully created access rule");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected a non-null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.createAccessRule(new AuthzPathImpl(null, "/"), null, AuthzAccessLevel.READ_WRITE);

			fail("Unexpected sucessfully created access rule");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected a non-null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.createAccessRule(new AuthzPathImpl(null, "/"), new AuthzGroupImpl("name"), null);

			fail("Unexpected sucessfully created access rule");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected a non-null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzPathImpl path = new AuthzPathImpl(null, "/");
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzAccessLevelIF accessLevel = AuthzAccessLevel.READ_WRITE;

			final AuthzAccessRule accessRule = document.createAccessRule(path, group, accessLevel);

			assertNotNull("access rule expected to be not null", accessRule);
			assertEquals("path should match", path, accessRule.getPath());
			assertEquals("group should match", group, accessRule.getGroup());
			assertNull("user should be null", accessRule.getUser());
			assertEquals("access level should match", accessLevel, accessRule.getAccessLevel());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzPathImpl path = new AuthzPathImpl(null, "/");
			final AuthzPathImpl pathWithRepo = new AuthzPathImpl(new AuthzRepositoryImpl("name"), "/");
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzAccessLevelIF accessLevel = AuthzAccessLevel.READ_WRITE;

			document.createAccessRule(path, group, accessLevel);
			document.createAccessRule(pathWithRepo, group, accessLevel);
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		// Test for duplicate values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzPathImpl path = new AuthzPathImpl(null, "/");
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzAccessLevelIF accessLevel = AuthzAccessLevel.READ_WRITE;

			document.createAccessRule(path, group, accessLevel);
			document.createAccessRule(path, group, accessLevel);

			fail("Unexpected successfully created duplicate access rule");
		}
		catch (final AuthzAccessRuleAlreadyExistsException e) {
			assertNotNull("Expected not-null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzPathImpl path = new AuthzPathImpl(null, "/");
			final AuthzGroupImpl group = new AuthzGroupImpl("name");

			document.createAccessRule(path, group, AuthzAccessLevel.READ_ONLY);
			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			fail("Unexpected successfully created duplicate access rule");
		}
		catch (final AuthzAccessRuleAlreadyExistsException e) {
			assertNotNull("Expected not-null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}
	}

	@Test
	public void testCreateGroup() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
	public void testCreatePath() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.createPath(null, null);

			fail("Unexpected successfully created path");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzPathAlreadyExistsException e) {
			fail("Unexpected AuthzPathAlreadyExistsException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.createPath(null, "");

			fail("Unexpected successfully created path");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzPathAlreadyExistsException e) {
			fail("Unexpected AuthzPathAlreadyExistsException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.createPath(null, "  ");

			fail("Unexpected successfully created path");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzPathAlreadyExistsException e) {
			fail("Unexpected AuthzPathAlreadyExistsException");
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzPath path = document.createPath(null, "/path");

			assertNotNull("Path should not be null", path);
			assertEquals("Path string should be valid", "/path", path.getPath());
		}
		catch (final AuthzInvalidPathException e) {
			fail("Unexpected AuthzInvalidPathException");
		}
		catch (final AuthzPathAlreadyExistsException e) {
			fail("Unexpected AuthzPathAlreadyExistsException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzPath path1 = document.createPath(document.createRepository("repository1"), "/path");
			final AuthzPath path2 = document.createPath(document.createRepository("repository2"), "/path");

			assertFalse("Paths with same path string in different repos should not match", path1.equals(path2));
		}
		catch (final AuthzInvalidPathException e) {
			fail("Unexpected AuthzInvalidPathException");
		}
		catch (final AuthzPathAlreadyExistsException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test for exiting repository exception
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.createPath(null, "/path");
			document.createPath(null, "/path");

			fail("Unexpected success creating duplicate path");
		}
		catch (final AuthzInvalidPathException e) {
			fail("Unexpected AuthzInvalidPathException");
		}
		catch (final AuthzPathAlreadyExistsException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzRepository repository = document.createRepository("repository");

			document.createPath(repository, "/path");
			document.createPath(repository, "/path");

			fail("Unexpected success creating duplicate path");
		}
		catch (final AuthzInvalidPathException e) {
			fail("Unexpected AuthzInvalidPathException");
		}
		catch (final AuthzPathAlreadyExistsException e) {
			assertNotNull("Expected a non-null exception", e);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testCreateRepository() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();
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
			final AuthzDocument document = new AuthzDocumentImpl();
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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
	public void testDoesAccessRuleExistGroup() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesAccessRuleExist(null, null);
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected not null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesAccessRuleExist(new AuthzPathImpl(null, "/"), null);
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected not null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesAccessRuleExist(null, null);
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected not null exception", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzRepository repository = document.createRepository("name");
			final AuthzPath path = document.createPath(repository, "/");
			final AuthzGroup group = document.createGroup("name");
			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertFalse(document.doesAccessRuleExist(path, new AuthzGroupImpl("name2")));
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzRepository repository = document.createRepository("name");
			final AuthzPath path = document.createPath(repository, "/");
			final AuthzGroup group = document.createGroup("name");
			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertTrue(document.doesAccessRuleExist(path, group));
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}
	}

	@Test
	public void testDoesGroupNameExist() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesGroupNameExist(null);

			fail("Unexpected success calling doesGroupNameExist() with null user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesGroupNameExist("");

			fail("Unexpected success calling doesGroupNameExist() with empty user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesGroupNameExist("  ");

			fail("Unexpected success calling doesGroupNameExist() with blank user alias");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("Group should not exist", document.doesGroupNameExist("name"));

			document.createGroup("name");

			assertTrue("Group should exist", document.doesGroupNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("Group should not exist", document.doesGroupNameExist("  name  "));

			document.createGroup("name");

			assertTrue("Group should exist", document.doesGroupNameExist("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testDoesPathExist() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesPathExist(null, null);

			fail("Unexpected success calling doesPathExist() with null repository and null repository name");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesPathExist(null, "");

			fail("Unexpected success calling doesPathExist() with null repository and empty repository name");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesPathExist(null, "  ");

			fail("Unexpected success calling doesPathExist() with null repository and blank repository name");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("Path with path string /test should not exist", document.doesPathExist(null, "/test"));

			document.createPath(null, "/test");

			assertTrue("Path with path string /test should exist", document.doesPathExist(null, "/test"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzRepository repository = document.createRepository("repository");

			assertFalse("Path with non-null repository and path string /test should not exist",
					document.doesPathExist(repository, "/test"));

			assertFalse("Path with null repository and path string /test should not exist",
					document.doesPathExist(null, "/test"));

			document.createPath(repository, "/test");

			assertTrue("Path with non-null repository and path string /test should exist",
					document.doesPathExist(repository, "/test"));

			assertFalse("Path with null repository and path string /test should not exist",
					document.doesPathExist(null, "/test"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testDoesRepositoryNameExist() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesRepositoryNameExist(null);

			fail("Unexpected success calling doesRepositoryNameExist() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesRepositoryNameExist("");

			fail("Unexpected success calling doesRepositoryNameExist() with empty repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesRepositoryNameExist("  ");

			fail("Unexpected success calling doesRepositoryNameExist() with blank repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("Repository with name should not exist", document.doesRepositoryNameExist("name"));

			document.createRepository("name");

			assertTrue("Repository with name should exist", document.doesRepositoryNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesUserAliasExist(null);

			fail("Unexpected success calling doesUserAliasExist() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesUserAliasExist("");

			fail("Unexpected success calling doesUserAliasExist() with empty user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesUserAliasExist("  ");

			fail("Unexpected success calling doesUserAliasExist() with blank user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("User with alias should not exist", document.doesUserAliasExist("alias"));

			document.createUser("name", "alias");

			assertTrue("User with alias should exist", document.doesUserAliasExist("alias"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesUserNameExist(null);

			fail("Unexpected success calling doesUserNameExist() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesUserNameExist("");

			fail("Unexpected success calling doesUserNameExist() with empty user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.doesUserNameExist("  ");

			fail("Unexpected success calling doesUserNameExist() with blank user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("User with name should not exist", document.doesUserNameExist("name"));

			document.createUser("name", null);

			assertTrue("User with name should exist", document.doesUserNameExist("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("User with name should not exist", document.doesUserNameExist("  name  "));

			document.createUser("name", null);

			assertTrue("User with name should exist", document.doesUserNameExist("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetAccessRules() {
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNotNull("Access rules should not be null", document.getAccessRules());
			assertTrue("Access rules should be empty", document.getAccessRules().size() == 0);

			final AuthzPath path = document.createPath(document.createRepository("name"), "/");
			final AuthzGroup group = document.createGroup("name");
			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertNotNull("Access rules should not be null", document.getAccessRules());
			assertTrue("Access rules should have one access rule", document.getAccessRules().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of access rules collection
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			Collection<AuthzAccessRule> accessRules = document.getAccessRules();

			assertNotNull("Access rules should not be null", accessRules);
			assertNotNull("Access rules should empty", accessRules.size() == 0);

			final AuthzPath path = document.createPath(document.createRepository("name"), "/");
			final AuthzGroup group = document.createGroup("name");
			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertNotNull("Access rules should remain empty", accessRules.size() == 0);

			accessRules = document.getAccessRules();

			assertNotNull("Access rules should not be null", document.getGroups());
			assertNotNull("Access rules should contain one group", document.getGroups().size() == 1);

			try {
				accessRules.add(new AuthzAccessRuleImpl(path, group, AuthzAccessLevel.READ_WRITE));

				fail("Successfully modified access rules list; should not have worked since collection is immutable");
			}
			catch (final UnsupportedOperationException e) {
				assertNotNull("Expected a non-null exception", e);
			}

			try {
				for (final AuthzAccessRule accessRule : accessRules) {
					accessRules.remove(accessRule);
				}

				fail("Successfully modified access rules list; should not have worked since collection is immutable");
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
	public void testGetAccessRuleWithGroup() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getAccessRuleWithGroup(null, null);

			fail("Unexpected success calling getAccessRuleWithGroup() with null path and group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected a non-null exception", e.getMessage());
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getAccessRuleWithGroup(null, document.createGroup("name"));

			fail("Unexpected success calling getAccessRuleWithGroup() with null path");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected a non-null exception", e.getMessage());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			final AuthzPath path = document.createPath(document.createRepository("name"), "/");

			document.getAccessRuleWithGroup(path, null);

			fail("Unexpected success calling getAccessRuleWithGroup() with null group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected a non-null exception", e.getMessage());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzPath path = document.createPath(document.createRepository("name"), "/");
			final AuthzGroup group = document.createGroup("name");

			assertNull("Access rule should not exist", document.getAccessRuleWithGroup(path, group));

			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertNotNull("Access rule should exist", document.getAccessRuleWithGroup(path, group));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzPath path = document.createPath(document.createRepository("name"), "/");
			final AuthzGroup group = document.createGroup("name");

			assertNull("Access rule should not exist", document.getAccessRuleWithGroup(path, group));

			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertNotNull("Access rule should exist", document.getAccessRuleWithGroup(path, group));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetGroups() {
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNotNull("Groups should not be null", document.getGroups());
			assertTrue("Groups should be empty", document.getGroups().size() == 0);

			document.createGroup("name");

			assertNotNull("Groups should not be null", document.getGroups());
			assertTrue("Groups should contain one group", document.getGroups().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of groups collection
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			Collection<AuthzGroup> groups = document.getGroups();

			assertNotNull("Groups should not be null", groups);
			assertNotNull("Groups should empty", groups.size() == 0);

			document.createGroup("name");

			assertNotNull("Groups should remain empty", groups.size() == 0);

			groups = document.getGroups();

			assertNotNull("Groups should not be null", document.getGroups());
			assertNotNull("Groups should contain one group", document.getGroups().size() == 1);

			try {
				groups.add(new AuthzGroupImpl("name"));

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
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getGroupWithName(null);

			fail("Unexpected success calling getGroupWithName() with null group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getGroupWithName("");

			fail("Unexpected success calling getGroupWithName() with empty group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getGroupWithName("  ");

			fail("Unexpected success calling getGroupWithName() with blank group name");
		}
		catch (final AuthzInvalidGroupNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNull("Group with name should not exist", document.getGroupWithName("name"));

			document.createGroup("name");

			assertNotNull("Group with name should exist", document.getGroupWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNull("Group with name should not exist", document.getGroupWithName("  name  "));

			document.createGroup("name");

			assertNotNull("Group with name should exist", document.getGroupWithName("  name  "));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetPath() {
		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getPath(null, null);

			fail("Unexpected success calling getPath() with null path");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getPath(null, "");

			fail("Unexpected success calling getPath() with empty path");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getPath(null, "  ");

			fail("Unexpected success calling getPath() with blank path");
		}
		catch (final AuthzInvalidPathException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNull("Path with path /path should not exist", document.getPath(null, "/path"));

			document.createPath(null, "/path");

			assertNotNull("Path with path /path should exist", document.getPath(null, "/path"));

			assertNull("Path with path /path, but a non-null repository should not exist",
					document.getPath(document.createRepository("repository"), "/path"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testGetPaths() {
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNotNull("Paths should not be null", document.getPaths());
			assertNotNull("Paths should empty", document.getPaths().size() == 0);

			document.createPath(null, "/path");

			assertNotNull("Paths should not be null", document.getPaths());
			assertNotNull("Paths should contain one repository", document.getPaths().size() == 1);
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test immutibility of repositories collection
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			Collection<AuthzPath> paths = document.getPaths();

			assertNotNull("Paths should not be null", paths);
			assertNotNull("Paths should empty", paths.size() == 0);

			document.createPath(null, "/path");

			assertNotNull("Paths should remain empty", paths.size() == 0);

			paths = document.getPaths();

			assertNotNull("Paths should not be null", document.getPaths());
			assertNotNull("Paths should contain one repository", document.getPaths().size() == 1);

			try {
				paths.add(new AuthzPathImpl(null, "/test"));

				fail("Successfully modified paths list; should not have worked since collection is immutable");
			}
			catch (final UnsupportedOperationException e) {
				assertNotNull("Expected a non-null exception", e);
			}

			try {
				for (final AuthzPath path : paths) {
					paths.remove(path);
				}

				fail("Successfully modified paths list; should not have worked since collection is immutable");
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
	public void testGetRepositories() {
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

			Collection<AuthzRepository> repositories = document.getRepositories();

			assertNotNull("Repositories should not be null", repositories);
			assertNotNull("Repositories should empty", repositories.size() == 0);

			document.createRepository("repository");

			assertNotNull("Repositories should remain empty", repositories.size() == 0);

			repositories = document.getRepositories();

			assertNotNull("Repositories should not be null", document.getRepositories());
			assertNotNull("Repositories should contain one repository", document.getRepositories().size() == 1);

			try {
				repositories.add(new AuthzRepositoryImpl("repository"));

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
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getRepositoryWithName(null);

			fail("Unexpected success calling getRepositoryWithName() with null repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getRepositoryWithName("");

			fail("Unexpected success calling getRepositoryWithName() with empty repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getRepositoryWithName("  ");

			fail("Unexpected success calling getRepositoryWithName() with blank repository name");
		}
		catch (final AuthzInvalidRepositoryNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNull("Repository with name should not exist", document.getRepositoryWithName("name"));

			document.createRepository("name");

			assertNotNull("Repository with name should exist", document.getRepositoryWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

			Collection<AuthzUser> users = document.getUsers();

			assertNotNull("Users should not be null", users);
			assertNotNull("Users should empty", users.size() == 0);

			document.createUser("user", null);

			assertNotNull("Users should remain empty", users.size() == 0);

			users = document.getUsers();

			assertNotNull("Users should not be null", document.getUsers());
			assertNotNull("Users should contain one user", document.getUsers().size() == 1);

			try {
				users.add(new AuthzUserImpl("name", "alias"));

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
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getUserWithAlias(null);

			fail("Unexpected success calling getUserWithAlias() with null user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getUserWithAlias("");

			fail("Unexpected success calling getUserWithAlias() with empty user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getUserWithAlias("  ");

			fail("Unexpected success calling getUserWithAlias() with blank user alias");
		}
		catch (final AuthzInvalidUserAliasException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNull("User with alias should not exist", document.getUserWithAlias("alias"));

			document.createUser("name", "alias");

			assertNotNull("User with alias should exist", document.getUserWithAlias("alias"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getUserWithName(null);

			fail("Unexpected success calling getUserWithName() with null user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getUserWithName("");

			fail("Unexpected success calling getUserWithName() with empty user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.getUserWithName("  ");

			fail("Unexpected success calling getUserWithName() with blank user name");
		}
		catch (final AuthzInvalidUserNameException e) {
			assertNotNull("Expected a non-null exception", e);
		}

		// Test valid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNull("User with name should not exist", document.getUserWithName("name"));

			document.createUser("name", "alias");

			assertNotNull("User with name should exist", document.getUserWithName("name"));
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createUser("user", null);

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test has unsaved changes flag when creating a group
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createGroup("group");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test has unsaved changes flag when creating a repository
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createRepository("repository");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test has unsaved changes flag when creating a path
		try {
			final AuthzDocumentImpl document = new AuthzDocumentImpl();
			final AuthzRepository repository = document.createRepository("name");

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createPath(repository, "/");

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}

		// Test has unsaved changes flag when creating a group access rule
		try {
			final AuthzDocumentImpl document = new AuthzDocumentImpl();
			final AuthzPathImpl path = document.createPath(document.createRepository("name"), "/");
			final AuthzGroupImpl group = document.createGroup("name");

			document.clearHasUnsavedChanges();

			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testInitialize() {
		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			assertNotNull("Access rules should not be null", document.getAccessRules());
			assertTrue("Access rules should be empty", document.getAccessRules().size() == 0);
			assertNotNull("Groups should not be null", document.getGroups());
			assertTrue("Groups should empty", document.getGroups().size() == 0);
			assertNotNull("Paths should not be null", document.getPaths());
			assertTrue("Paths should empty", document.getPaths().size() == 0);
			assertNotNull("Repositories should not be null", document.getRepositories());
			assertTrue("Repositories should empty", document.getRepositories().size() == 0);
			assertNotNull("Users should not be null", document.getUsers());
			assertTrue("Users should empty", document.getUsers().size() == 0);
			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

			final AuthzGroup group = document.createGroup("group");
			final AuthzRepository repository = document.createRepository("repository");
			document.createUser("user", null);
			final AuthzPath path = document.createPath(repository, "/");
			document.createAccessRule(path, group, AuthzAccessLevel.READ_WRITE);

			assertNotNull("Access rules should not be null", document.getAccessRules());
			assertTrue("Access rules should contain one access rule", document.getAccessRules().size() == 1);
			assertNotNull("Groups should not be null", document.getGroups());
			assertTrue("Groups should contain one group", document.getGroups().size() == 1);
			assertNotNull("Paths should not be null", document.getPaths());
			assertTrue("Paths should contain one path", document.getPaths().size() == 1);
			assertNotNull("Repositories should not be null", document.getRepositories());
			assertTrue("Repositories should contain one repository", document.getRepositories().size() == 1);
			assertNotNull("Users should not be null", document.getUsers());
			assertTrue("Users should contain one user", document.getUsers().size() == 1);
			assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());

			document.initialize();

			assertNotNull("Access rules should not be null", document.getAccessRules());
			assertTrue("Access rules should be empty", document.getAccessRules().size() == 0);
			assertNotNull("Groups should not be null", document.getGroups());
			assertTrue("Groups should empty", document.getGroups().size() == 0);
			assertNotNull("Paths should not be null", document.getPaths());
			assertTrue("Paths should empty", document.getPaths().size() == 0);
			assertNotNull("Repositories should not be null", document.getRepositories());
			assertTrue("Repositories should empty", document.getRepositories().size() == 0);
			assertNotNull("Users should not be null", document.getUsers());
			assertTrue("Users should empty", document.getUsers().size() == 0);
			assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());
		}
		catch (final AuthzException e) {
			fail("Unexpected AuthzException");
		}
	}

	@Test
	public void testIsValidGroupName() {
		final AuthzDocumentImpl document = new AuthzDocumentImpl();

		// Test invalid values
		assertFalse("Null group name is invalid", document.isValidGroupName(null));
		assertFalse("Empty group name is invalid", document.isValidGroupName(""));
		assertFalse("Blank group name is invalid", document.isValidGroupName("  "));

		// Test valid values
		assertTrue("Non-blank group name is valid", document.isValidGroupName("name"));
		assertTrue("Non-blank group name is valid", document.isValidGroupName("  name  "));
	}

	@Test
	public void testIsValidPath() {
		final AuthzDocumentImpl document = new AuthzDocumentImpl();

		// Test invalid values
		assertFalse("Null path is invalid", document.isValidPath(null));
		assertFalse("Empty path is invalid", document.isValidPath(""));
		assertFalse("Blank path is invalid", document.isValidPath("  "));

		// Test valid values
		assertTrue("Single slash is valid", document.isValidPath("/"));
		assertTrue("Path is valid", document.isValidPath("/asdf"));
		assertTrue("Path is valid", document.isValidPath(" /asdf "));
		assertTrue("Path is valid", document.isValidPath("/as df"));

		// Test invalid path
		assertFalse("Path must not end with a slash", document.isValidPath("/asdf/"));
		assertFalse("Path must not end with a slash", document.isValidPath("  /asdf/  "));
	}

	@Test
	public void testIsValidRepositoryName() {
		final AuthzDocumentImpl document = new AuthzDocumentImpl();

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
		final AuthzDocumentImpl document = new AuthzDocumentImpl();

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
		final AuthzDocumentImpl document = new AuthzDocumentImpl();

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
			final AuthzDocument document = new AuthzDocumentImpl();

			document.removeGroupMember(null, new AuthzUserImpl(null, null));

			fail("Successfully removed user from a null group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e);
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();

			document.removeGroupMember(new AuthzGroupImpl("name"), null);

			fail("Successfully removed null from a group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e);
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzUserImpl user = new AuthzUserImpl("name", null);

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
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzUserImpl user = new AuthzUserImpl("name", null);

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
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzUserImpl user = new AuthzUserImpl("name", null);

			group.addMember(user);
			user.addGroup(group);

			document.removeGroupMember(group, user);
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = new AuthzGroupImpl("name");
			final AuthzUserImpl user = new AuthzUserImpl("name", null);

			document.addGroupMember(group, user);
			document.removeGroupMember(group, user);
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}
	}

	@Test
	public void testSetHasUnsavedChanges() {
		final AuthzDocumentImpl document = new AuthzDocumentImpl();

		assertFalse("Document should not have any unsaved changes", document.hasUnsavedChanges());

		document.setHasUnsavedChanges();

		assertTrue("Document should have unsaved changes", document.hasUnsavedChanges());
	}

	@Test
	public void testToString() {
		final AuthzDocument document = new AuthzDocumentImpl();

		assertNotNull("Should not be null for empty document", document.toString());

		assertTrue("Should contain access rule information", document.toString().contains("accessRules"));
		assertTrue("Should contain group information", document.toString().contains("groups"));
		assertTrue("Should contain path information", document.toString().contains("paths"));
		assertTrue("Should contain repository information", document.toString().contains("repositories"));
		assertTrue("Should contain user information", document.toString().contains("users"));
	}
}
