package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;

/**
 * Unit test for AuthzGroup.
 */
public class AuthzGroupTest {
	@Test
	public void testAddMember() {
		// Test happy path
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzGroup authzGroup = authzDocument.createGroup("name");

			assertTrue("addMember() should reutrn true", authzGroup.addMember(authzDocument.createGroup("group")));
			assertTrue("addMember() should return true", authzGroup.addMember(authzDocument.createUser("user", null)));
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test check for duplicate group
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzGroup authzGroup = authzDocument.createGroup("name");
			final AuthzGroupMember member = authzDocument.createGroup("group");

			assertTrue("addMember() should reutrn true", authzGroup.addMember(member));
			assertTrue("addMember() should reutrn true", authzGroup.addMember(member));

			fail("Unexpected success adding member twice");
		}
		catch (final AuthzGroupMemberAlreadyExistsException e) {
			assertNotNull("AuthzGroupMemberAlreadyExistsException should be thrown", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test check for duplicate user
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzGroup authzGroup = authzDocument.createGroup("name");
			final AuthzGroupMember member = authzDocument.createUser("user", null);

			assertTrue("addMember() should reutrn true", authzGroup.addMember(member));
			assertTrue("addMember() should reutrn true", authzGroup.addMember(member));

			fail("Unexpected success adding member twice");
		}
		catch (final AuthzGroupMemberAlreadyExistsException e) {
			assertNotNull("AuthzGroupMemberAlreadyExistsException should be thrown", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzGroup authzGroup = authzDocument.createGroup("name");

			authzGroup.addMember(null);

			fail("Unexpected successfully added null member");
		}
		catch (final NullPointerException e) {
			assertNotNull("Null pointer exception expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}
	}

	@Test
	public void testAuthzGroupString() {
		final AuthzGroup authzGroup = new AuthzGroup("name");

		assertEquals("name should be valid", "name", authzGroup.getName());
	}

	@Test
	public void testCompareTo() {
		assertTrue("Null groups should match", new AuthzGroup(null).compareTo(new AuthzGroup(null)) == 0);
		assertTrue("Empty groups should match", new AuthzGroup("").compareTo(new AuthzGroup("")) == 0);
		assertTrue("Groups with same name should match", new AuthzGroup("name").compareTo(new AuthzGroup("name")) == 0);
		assertTrue("Groups with same name and alias should match", new AuthzGroup("name").compareTo(new AuthzGroup(
				"name")) == 0);

		assertTrue("Groups should not match", new AuthzGroup("name").compareTo(new AuthzGroup("same")) < 0);
		assertTrue("Groups should not match", new AuthzGroup("same").compareTo(new AuthzGroup("name")) > 0);
	}

	@Test
	public void testRemoveMember() {
		// Test happy path
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzGroup authzGroup = authzDocument.createGroup("name");
			final AuthzGroupMember member = authzDocument.createUser("user", null);

			assertTrue("members should be empty", authzGroup.getMembers().size() == 0);

			assertTrue("addMember() should reutrn true", authzGroup.addMember(member));

			assertTrue("members should be empty", authzGroup.getMembers().size() == 1);

			assertTrue("removeMember() should reutrn true", authzGroup.removeMember(member));

			assertTrue("members should be empty", authzGroup.getMembers().size() == 0);
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test invalid values
		try {
			final AuthzDocument authzDocument = new AuthzDocument();
			final AuthzGroup authzGroup = authzDocument.createGroup("name");

			authzGroup.removeMember(null);

			fail("Unexpected successfully removed null member");
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
			final AuthzGroup authzGroup = authzDocument.createGroup("name");

			authzGroup.removeMember(authzDocument.createUser("name", null));

			fail("Unexpected successfully removed object that was not a group member");
		}
		catch (final AuthzNotGroupMemberException e) {
			assertNotNull("AuthzNotGroupMemberException expected", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}
	}

	@Test
	public void testToString() {
		final AuthzGroup authzGroup = new AuthzGroup("myName");

		assertTrue("toString() should output name", authzGroup.getName().contains("myName"));
	}
}
