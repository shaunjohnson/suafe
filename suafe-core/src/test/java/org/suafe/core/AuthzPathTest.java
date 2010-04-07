package org.suafe.core;

import static org.junit.Assert.assertEquals;
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
	public void testToString() {
		final AuthzRepository repository = new AuthzRepository("myRepository");
		final AuthzPath path = new AuthzPath(repository, "myName");

		assertTrue("toString() should output repository", path.toString().contains("myRepository"));
		assertTrue("toString() should output name", path.toString().contains("myName"));
	}
}
