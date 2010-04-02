package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for AuthzUser.
 */
public class AuthzUserTest {
	@Test
	public void testAuthzUserStringString() {
		final AuthzUser user = new AuthzUser("name", "alias");

		assertEquals("name should be valid", "name", user.getName());
		assertEquals("alias should be valid", "alias", user.getAlias());
	}

	@Test
	public void testCompareTo() {
		assertTrue("Null users should match", new AuthzUser(null, null).compareTo(new AuthzUser(null, null)) == 0);
		assertTrue("Empty users should match", new AuthzUser("", "").compareTo(new AuthzUser("", "")) == 0);
		assertTrue("Users with same name should match", new AuthzUser("name", null).compareTo(new AuthzUser("name",
				null)) == 0);
		assertTrue("Users with same name and alias should match", new AuthzUser("name", "alias")
				.compareTo(new AuthzUser("name", "alias")) == 0);

		assertTrue("Users should not match", new AuthzUser("name", null).compareTo(new AuthzUser("same", null)) < 0);
		assertTrue("Users should not match", new AuthzUser("same", null).compareTo(new AuthzUser("name", null)) > 0);
	}

	@Test
	public void testToString() {
		final AuthzUser user = new AuthzUser("myName", "myAlias");

		assertTrue("toString() should output name", user.getName().contains("myName"));
		assertTrue("toString() should output alias", user.getAlias().contains("myAlias"));
	}
}
