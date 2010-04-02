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
		final AuthzRepository repository = new AuthzRepository("name");

		assertEquals("name should be valid", "name", repository.getName());
	}

	@Test
	public void testCompareTo() {
		assertTrue("Null repositories should match",
				new AuthzRepository(null).compareTo(new AuthzRepository(null)) == 0);
		assertTrue("Empty repositories should match", new AuthzRepository("").compareTo(new AuthzRepository("")) == 0);
		assertTrue("Repositories with same name should match", new AuthzRepository("name")
				.compareTo(new AuthzRepository("name")) == 0);
		assertTrue("Repositories with same name should match", new AuthzRepository("name")
				.compareTo(new AuthzRepository("name")) == 0);

		assertTrue("Repositories should not match",
				new AuthzRepository("name").compareTo(new AuthzRepository("same")) < 0);
		assertTrue("Repositories should not match",
				new AuthzRepository("same").compareTo(new AuthzRepository("name")) > 0);
	}

	@Test
	public void testToString() {
		final AuthzRepository repository = new AuthzRepository("myName");

		assertTrue("toString() should output name", repository.getName().contains("myName"));
	}
}
