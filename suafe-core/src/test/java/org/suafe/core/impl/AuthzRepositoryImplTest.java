package org.suafe.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.suafe.core.AuthzRepository;
import org.suafe.core.impl.AuthzRepositoryImpl;

/**
 * Unit test for AuthzRepository.
 */
public class AuthzRepositoryImplTest {
    @Test
    public void testAuthzRepositoryString() {
        final AuthzRepository repository = new AuthzRepositoryImpl("name");

        assertEquals("name should be valid", "name", repository.getName());
    }

    @Test
    public void testCompareTo() {
        assertTrue("Empty repositories should match", new AuthzRepositoryImpl("")
                .compareTo(new AuthzRepositoryImpl("")) == 0);
        assertTrue("Repositories with same name should match",
                new AuthzRepositoryImpl("name").compareTo(new AuthzRepositoryImpl(
                        "name")) == 0);
        assertTrue("Repositories with same name should match",
                new AuthzRepositoryImpl("name").compareTo(new AuthzRepositoryImpl(
                        "name")) == 0);

        assertTrue("Repositories should not match", new AuthzRepositoryImpl("name")
                .compareTo(new AuthzRepositoryImpl("same")) < 0);
        assertTrue("Repositories should not match", new AuthzRepositoryImpl("same")
                .compareTo(new AuthzRepositoryImpl("name")) > 0);
    }

    @Test
    public void testEquals() {
        assertTrue("Values should match", new AuthzRepositoryImpl("name")
                .equals(new AuthzRepositoryImpl("name")));
        assertTrue("Values should match", new AuthzRepositoryImpl("")
                .equals(new AuthzRepositoryImpl("")));
        assertFalse("Values should not match", new AuthzRepositoryImpl("name")
                .equals(new AuthzRepositoryImpl("name2")));
        assertFalse("Values should not match", new AuthzRepositoryImpl("")
                .equals(new AuthzRepositoryImpl("name2")));

        // Test invalid values;
        assertFalse("Values should not match", new AuthzRepositoryImpl("name")
                .equals(""));
        assertFalse("Values should not match", new AuthzRepositoryImpl("name")
                .equals(new AuthzRepositoryImpl("name").toString()));
    }

    @Test
    public void testHashCode() {
        assertTrue("HashCode values should match", new AuthzRepositoryImpl("name")
                .hashCode() == new AuthzRepositoryImpl("name").hashCode());
        assertFalse("HashCode values should not match", new AuthzRepositoryImpl(
                "name").hashCode() == new AuthzRepositoryImpl("name2").hashCode());
    }

    @Test
    public void testToString() {
        final AuthzRepository repository = new AuthzRepositoryImpl("myName");

        assertTrue("toString() should output name", repository.toString()
                .contains("myName"));
    }
}
