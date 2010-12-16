package org.suafe.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.suafe.core.AuthzRepositoryIF;
import org.suafe.core.impl.AuthzRepository;

/**
 * Unit test for AuthzRepository.
 */
public class AuthzRepositoryTest {
    @Test
    public void testAuthzRepositoryString() {
        final AuthzRepositoryIF repository = new AuthzRepository("name");

        assertEquals("name should be valid", "name", repository.getName());
    }

    @Test
    public void testCompareTo() {
        assertTrue("Empty repositories should match", new AuthzRepository("")
                .compareTo(new AuthzRepository("")) == 0);
        assertTrue("Repositories with same name should match",
                new AuthzRepository("name").compareTo(new AuthzRepository(
                        "name")) == 0);
        assertTrue("Repositories with same name should match",
                new AuthzRepository("name").compareTo(new AuthzRepository(
                        "name")) == 0);

        assertTrue("Repositories should not match", new AuthzRepository("name")
                .compareTo(new AuthzRepository("same")) < 0);
        assertTrue("Repositories should not match", new AuthzRepository("same")
                .compareTo(new AuthzRepository("name")) > 0);
    }

    @Test
    public void testEquals() {
        assertTrue("Values should match", new AuthzRepository("name")
                .equals(new AuthzRepository("name")));
        assertTrue("Values should match", new AuthzRepository("")
                .equals(new AuthzRepository("")));
        assertFalse("Values should not match", new AuthzRepository("name")
                .equals(new AuthzRepository("name2")));
        assertFalse("Values should not match", new AuthzRepository("")
                .equals(new AuthzRepository("name2")));

        // Test invalid values;
        assertFalse("Values should not match", new AuthzRepository("name")
                .equals(""));
        assertFalse("Values should not match", new AuthzRepository("name")
                .equals(new AuthzRepository("name").toString()));
    }

    @Test
    public void testHashCode() {
        assertTrue("HashCode values should match", new AuthzRepository("name")
                .hashCode() == new AuthzRepository("name").hashCode());
        assertFalse("HashCode values should not match", new AuthzRepository(
                "name").hashCode() == new AuthzRepository("name2").hashCode());
    }

    @Test
    public void testToString() {
        final AuthzRepositoryIF repository = new AuthzRepository("myName");

        assertTrue("toString() should output name", repository.toString()
                .contains("myName"));
    }
}
