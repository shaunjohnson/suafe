package org.suafe.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.suafe.core.AuthzPath;
import org.suafe.core.AuthzRepository;
import org.suafe.core.impl.AuthzPathImpl;
import org.suafe.core.impl.AuthzRepositoryImpl;

/**
 * Unit test for AuthzPath.
 */
public class AuthzPathImplTest {
    @Test
    public void testAuthzPathRepositoryString() {
        final AuthzPath path = new AuthzPathImpl(null, "/path");

        assertNull("repository should be null", path.getRepository());
        assertEquals("path should be valid", "/path", path.getPath());

        final AuthzRepository repository = new AuthzRepositoryImpl("repository");
        final AuthzPath path2 = new AuthzPathImpl(repository, "/path2");

        assertEquals("repository should be valid", repository, path2
                .getRepository());
        assertEquals("path should be valid", "/path2", path2.getPath());
    }

    @Test
    public void testCompareTo() {
        assertTrue("Empty paths should match", new AuthzPathImpl(null, "")
                .compareTo(new AuthzPathImpl(null, "")) == 0);
        assertTrue("Paths with same path should match", new AuthzPathImpl(null,
                "/path").compareTo(new AuthzPathImpl(null, "/path")) == 0);

        final AuthzRepository repository = new AuthzRepositoryImpl("repository");

        assertTrue("Paths with same repository and path should match",
                new AuthzPathImpl(repository, "/path").compareTo(new AuthzPathImpl(
                        repository, "/path")) == 0);

        assertTrue("Paths should not match", new AuthzPathImpl(null, "/path")
                .compareTo(new AuthzPathImpl(null, "/qath")) < 0);
        assertTrue("Paths should not match", new AuthzPathImpl(null, "/qath")
                .compareTo(new AuthzPathImpl(null, "/path")) > 0);
    }

    @Test
    public void testEquals() {
        // Test with null repository
        assertTrue("Values should match", new AuthzPathImpl(null, "name")
                .equals(new AuthzPathImpl(null, "name")));
        assertTrue("Values should match", new AuthzPathImpl(null, "")
                .equals(new AuthzPathImpl(null, "")));
        assertFalse("Values should not match", new AuthzPathImpl(null, "name")
                .equals(new AuthzPathImpl(null, "name2")));

        final AuthzPath path = new AuthzPathImpl(null, "name");

        assertTrue("Value should match self", path.equals(path));

        // Test with repository
        assertTrue("Values should match", new AuthzPathImpl(new AuthzRepositoryImpl(
                "repo"), "name").equals(new AuthzPathImpl(new AuthzRepositoryImpl(
                "repo"), "name")));
        assertTrue("Values should match", new AuthzPathImpl(new AuthzRepositoryImpl(
                "repo"), "").equals(new AuthzPathImpl(new AuthzRepositoryImpl("repo"),
                "")));
        assertFalse("Values should not match", new AuthzPathImpl(
                new AuthzRepositoryImpl("repo"), "name").equals(new AuthzPathImpl(
                new AuthzRepositoryImpl("repo"), "name2")));
        assertFalse("Values should not match", new AuthzPathImpl(
                new AuthzRepositoryImpl("repo"), "").equals(new AuthzPathImpl(
                new AuthzRepositoryImpl("repo"), "name2")));

        assertFalse("Values should not match", new AuthzPathImpl(
                new AuthzRepositoryImpl("repo1"), "name").equals(new AuthzPathImpl(
                new AuthzRepositoryImpl("repo2"), "name")));
        assertFalse("Values should not match", new AuthzPathImpl(null, "name")
                .equals(new AuthzPathImpl(new AuthzRepositoryImpl("repo2"), "name")));

        // Test invalid values
        assertFalse("Values should not match", new AuthzPathImpl(null, "name")
                .equals(null));
        assertFalse("Values should not match", new AuthzPathImpl(null, "name")
                .equals(""));
        assertFalse("Values should not match", new AuthzPathImpl(null, "name")
                .equals(new AuthzPathImpl(null, "name").toString()));
    }

    @Test
    public void testHashCode() {
        assertTrue("HashCode values should match", new AuthzPathImpl(null, "name")
                .hashCode() == new AuthzPathImpl(null, "name").hashCode());
        assertFalse("HashCode values should not match", new AuthzPathImpl(null,
                "name").hashCode() == new AuthzPathImpl(null, "name2").hashCode());
    }

    @Test
    public void testToString() {
        final AuthzRepository repository = new AuthzRepositoryImpl("myRepository");
        final AuthzPath path = new AuthzPathImpl(repository, "myName");

        assertTrue("toString() should output repository", path.toString()
                .contains("myRepository"));
        assertTrue("toString() should output name", path.toString().contains(
                "myName"));
    }
}
