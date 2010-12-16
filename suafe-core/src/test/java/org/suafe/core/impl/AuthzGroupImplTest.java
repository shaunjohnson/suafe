package org.suafe.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.suafe.core.AuthzDocument;
import org.suafe.core.AuthzGroup;
import org.suafe.core.AuthzGroupMember;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;

/**
 * Unit test for AuthzGroup.
 */
public class AuthzGroupImplTest {
	@Test
	public void testAddMember() {
		// Test happy path
		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = (AuthzGroupImpl) document.createGroup("name");

			assertTrue("addMember() should reutrn true", group.addMember(document.createGroup("group")));
			assertTrue("addMember() should return true", group.addMember(document.createUser("user", null)));
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test check for duplicate group
		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = (AuthzGroupImpl) document.createGroup("name");
			final AuthzGroupMemberImpl member = (AuthzGroupMemberImpl) document.createGroup("group");

			assertTrue("addMember() should reutrn true", group.addMember(member));
			assertTrue("addMember() should reutrn true", group.addMember(member));

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
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = (AuthzGroupImpl) document.createGroup("name");
			final AuthzGroupMember member = document.createUser("user", null);

			assertTrue("addMember() should reutrn true", group.addMember(member));
			assertTrue("addMember() should reutrn true", group.addMember(member));

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
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = (AuthzGroupImpl) document.createGroup("name");

			group.addMember(null);

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
		final AuthzGroupImpl group = new AuthzGroupImpl("name");

		assertEquals("name should be valid", "name", group.getName());
	}

	@Test
	public void testCompareTo() {
		assertTrue("Empty groups should match", new AuthzGroupImpl("").compareTo(new AuthzGroupImpl("")) == 0);
		assertTrue("Groups with same name should match", new AuthzGroupImpl("name").compareTo(new AuthzGroupImpl("name")) == 0);
		assertTrue("Groups with same name and alias should match",
				new AuthzGroupImpl("name").compareTo(new AuthzGroupImpl("name")) == 0);

		assertTrue("Groups should not match", new AuthzGroupImpl("name").compareTo(new AuthzGroupImpl("same")) < 0);
		assertTrue("Groups should not match", new AuthzGroupImpl("same").compareTo(new AuthzGroupImpl("name")) > 0);
	}

	@Test
	public void testEquals() {
		assertTrue("Values should match", new AuthzGroupImpl("name").equals(new AuthzGroupImpl("name")));
		assertTrue("Values should match", new AuthzGroupImpl("").equals(new AuthzGroupImpl("")));
		assertFalse("Values should not match", new AuthzGroupImpl("name").equals(new AuthzGroupImpl("name2")));

		// Test invalid values
		assertFalse("Values should not match", new AuthzGroupImpl("name").equals(null));
		assertFalse("Values should not match", new AuthzGroupImpl("name").equals(""));
		assertFalse("Values should not match", new AuthzGroupImpl("name").equals(new AuthzGroupImpl("name").toString()));
	}

	@Test
	public void testHashCode() {
		assertTrue("HashCode values should match",
				new AuthzGroupImpl("name").hashCode() == new AuthzGroupImpl("name").hashCode());
		assertFalse("HashCode values should not match",
				new AuthzGroupImpl("name").hashCode() == new AuthzGroupImpl("name2").hashCode());
	}

	@Test
	public void testRemoveMember() {
		// Test happy path
		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = (AuthzGroupImpl) document.createGroup("name");
			final AuthzGroupMember member = document.createUser("user", null);

			assertTrue("members should be empty", group.getMembers().size() == 0);

			assertTrue("addMember() should reutrn true", group.addMember(member));

			assertTrue("members should be empty", group.getMembers().size() == 1);

			assertTrue("removeMember() should reutrn true", group.removeMember(member));

			assertTrue("members should be empty", group.getMembers().size() == 0);
		}
		catch (final Exception e) {
			fail("Unexpected exception");
		}

		// Test invalid values
		try {
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = (AuthzGroupImpl) document.createGroup("name");

			group.removeMember(null);

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
			final AuthzDocument document = new AuthzDocumentImpl();
			final AuthzGroupImpl group = (AuthzGroupImpl) document.createGroup("name");

			group.removeMember(document.createUser("name", null));

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
		final AuthzGroup group = new AuthzGroupImpl("myName");

		assertTrue("toString() should output name", group.toString().contains("myName"));
	}
}
