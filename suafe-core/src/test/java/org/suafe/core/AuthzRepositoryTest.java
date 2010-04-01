package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for AuthzRepository.
 */
public class AuthzRepositoryTest {
	@Test
	public void testAuthzRepositoryString() {
		final AuthzRepository authzRepository = new AuthzRepository("name");

		assertEquals("name should be valid", "name", authzRepository.getName());
	}

	@Test
	public void testCompareTo() {
		assertTrue("Null users should match", new AuthzRepository(null).compareTo(new AuthzRepository(null)) == 0);
		assertTrue("Empty users should match", new AuthzRepository("").compareTo(new AuthzRepository("")) == 0);
		assertTrue("Users with same name should match", new AuthzRepository("name").compareTo(new AuthzRepository(
				"name")) == 0);
		assertTrue("Users with same name should match", new AuthzRepository("name").compareTo(new AuthzRepository(
				"name")) == 0);

		assertTrue("Users should not match", new AuthzRepository("name").compareTo(new AuthzRepository("same")) < 0);
		assertTrue("Users should not match", new AuthzRepository("same").compareTo(new AuthzRepository("name")) > 0);
	}

	@Test
	public void testToString() {
		final AuthzRepository authzRepository = new AuthzRepository("myName");

		assertTrue("toString() should output name", authzRepository.getName().contains("myName"));
	}
}
