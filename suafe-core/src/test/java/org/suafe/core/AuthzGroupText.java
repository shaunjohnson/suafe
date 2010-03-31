package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for AuthzGroup.
 */
public class AuthzGroupText {
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
	public void testToString() {
		final AuthzGroup authzGroup = new AuthzGroup("myName");

		assertTrue("toString() should output name", authzGroup.getName().contains("myName"));
	}
}
