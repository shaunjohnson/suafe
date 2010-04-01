package org.suafe.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;

public class AuthzGroupMemberTest {
	@Test
	public void testAddGroup() {
		// Test happy path
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzUser authzUser = authzDocument.createUser("user", null);
			final AuthzGroup authzGroup = authzDocument.createGroup("group");

			assertTrue("Groups should be empty", authzUser.getGroups().size() == 0);

			authzUser.addGroup(authzGroup);

			assertTrue("Groups should be not be empty", authzUser.getGroups().size() == 1);
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzUser authzUser = authzDocument.createUser("user", null);

			authzUser.addGroup(null);

			fail("Unexpected successfully added null group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Null pointer exception expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test exception
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzUser authzUser = authzDocument.createUser("user", null);
			final AuthzGroup authzGroup = authzDocument.createGroup("group");

			authzUser.addGroup(authzGroup);
			authzUser.addGroup(authzGroup);

			fail("Unexpected successfully added same group twice");
		}
		catch (final AuthzAlreadyMemberOfGroupException e) {
			assertNotNull("Null pointer exception expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}
	}

	@Test
	public void testRemoveGroup() {
		// Test happy path
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzGroup authzGroup = authzDocument.createGroup("name");
			final AuthzUser authzUser = authzDocument.createUser("user", null);

			assertTrue("groups should be empty", authzUser.getGroups().size() == 0);

			assertTrue("addGroup() should reutrn true", authzUser.addGroup(authzGroup));

			assertTrue("groups should be empty", authzUser.getGroups().size() == 1);

			assertTrue("removeMember() should reutrn true", authzUser.removeGroup(authzGroup));

			assertTrue("groups should be empty", authzUser.getGroups().size() == 0);
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzUser authzUser = authzDocument.createUser("name", null);

			authzUser.removeGroup(null);

			fail("Unexpected successfully removed null group");
		}
		catch (final NullPointerException e) {
			assertNotNull("Null pointer exception expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test for not a member exception
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzUser authzUser = authzDocument.createUser("name", null);

			authzUser.removeGroup(authzDocument.createGroup("group"));

			fail("Unexpected successfully removed group that this user is not a member of");
		}
		catch (final AuthzNotMemberOfGroupException e) {
			assertNotNull("AuthzNotGroupMemberException expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}
	}
}
