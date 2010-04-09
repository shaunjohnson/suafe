package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for AuthzPath.
 */
public class AuthzPathTest {
	@Test
	public void testAuthzPathRepositoryString() {
		final AuthzPath path = new AuthzPath(null, "/path");

		assertNull("repository should be null", path.getRepository());
		assertEquals("path should be valid", "/path", path.getPath());

		final AuthzRepository repository = new AuthzRepository("repository");
		final AuthzPath path2 = new AuthzPath(repository, "/path2");

		assertEquals("repository should be valid", repository, path2.getRepository());
		assertEquals("path should be valid", "/path2", path2.getPath());
	}

	@Test
	public void testCompareTo() {
		assertTrue("Null paths should match", new AuthzPath(null, null).compareTo(new AuthzPath(null, null)) == 0);
		assertTrue("Empty paths should match", new AuthzPath(null, "").compareTo(new AuthzPath(null, "")) == 0);
		assertTrue("Paths with same path should match", new AuthzPath(null, "/path").compareTo(new AuthzPath(null,
				"/path")) == 0);

		final AuthzRepository repository = new AuthzRepository("repository");

		assertTrue("Paths with same repository and path should match", new AuthzPath(repository, "/path")
				.compareTo(new AuthzPath(repository, "/path")) == 0);

		assertTrue("Paths should not match", new AuthzPath(null, "/path").compareTo(new AuthzPath(null, "/qath")) < 0);
		assertTrue("Paths should not match", new AuthzPath(null, "/qath").compareTo(new AuthzPath(null, "/path")) > 0);
	}

	@Test
	public void testEquals() {
		// Test with null repository
		assertTrue("Values should match", new AuthzPath(null, "name").equals(new AuthzPath(null, "name")));
		assertTrue("Values should match", new AuthzPath(null, null).equals(new AuthzPath(null, null)));
		assertFalse("Values should not match", new AuthzPath(null, "name").equals(new AuthzPath(null, "name2")));

		final AuthzPath path = new AuthzPath(null, "name");

		assertTrue("Value should match self", path.equals(path));

		// Test with repository
		assertTrue("Values should match", new AuthzPath(new AuthzRepository("repo"), "name").equals(new AuthzPath(
				new AuthzRepository("repo"), "name")));
		assertTrue("Values should match", new AuthzPath(new AuthzRepository("repo"), null).equals(new AuthzPath(
				new AuthzRepository("repo"), null)));
		assertFalse("Values should not match", new AuthzPath(new AuthzRepository("repo"), "name").equals(new AuthzPath(
				new AuthzRepository("repo"), "name2")));
		assertFalse("Values should not match", new AuthzPath(new AuthzRepository("repo"), null).equals(new AuthzPath(
				new AuthzRepository("repo"), "name2")));

		assertFalse("Values should not match", new AuthzPath(new AuthzRepository("repo1"), "name")
				.equals(new AuthzPath(new AuthzRepository("repo2"), "name")));
		assertFalse("Values should not match", new AuthzPath(null, "name").equals(new AuthzPath(new AuthzRepository(
				"repo2"), "name")));

		// Test invalid values
		assertFalse("Values should not match", new AuthzPath(null, "name").equals(null));
		assertFalse("Values should not match", new AuthzPath(null, "name").equals(""));
		assertFalse("Values should not match", new AuthzPath(null, "name").equals(new AuthzPath(null, "name")
				.toString()));
	}

	@Test
	public void testHashCode() {
		assertTrue("HashCode values should match",
				new AuthzPath(null, "name").hashCode() == new AuthzPath(null, "name").hashCode());
		assertFalse("HashCode values should not match", new AuthzPath(null, "name").hashCode() == new AuthzPath(null,
				"name2").hashCode());
	}

	@Test
	public void testToString() {
		final AuthzRepository repository = new AuthzRepository("myRepository");
		final AuthzPath path = new AuthzPath(repository, "myName");

		assertTrue("toString() should output repository", path.toString().contains("myRepository"));
		assertTrue("toString() should output name", path.toString().contains("myName"));
	}
}
