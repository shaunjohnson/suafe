package org.suafe.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.suafe.core.AuthzUser;
import org.suafe.core.impl.AuthzUserImpl;

/**
 * Unit test for AuthzUser.
 */
public class AuthzUserImplTest {
    @Test
    public void testAuthzUserStringString() {
        final AuthzUserImpl user = new AuthzUserImpl("name", "alias");

        assertEquals("name should be valid", "name", user.getName());
        assertEquals("alias should be valid", "alias", user.getAlias());
    }

    @Test
    public void testCompareTo() {
        assertTrue("Null users should match", new AuthzUserImpl("", null)
                .compareTo(new AuthzUserImpl("", null)) == 0);
        assertTrue("Empty users should match", new AuthzUserImpl("", "")
                .compareTo(new AuthzUserImpl("", "")) == 0);
        assertTrue("Users with same name should match", new AuthzUserImpl("name",
                null).compareTo(new AuthzUserImpl("name", null)) == 0);
        assertTrue("Users with same name and alias should match",
                new AuthzUserImpl("name", "alias").compareTo(new AuthzUserImpl("name",
                        "alias")) == 0);

        assertTrue("Users should not match", new AuthzUserImpl("name", null)
                .compareTo(new AuthzUserImpl("same", null)) < 0);
        assertTrue("Users should not match", new AuthzUserImpl("same", null)
                .compareTo(new AuthzUserImpl("name", null)) > 0);
    }

    @Test
    public void testEquals() {
        assertTrue("Values should match", new AuthzUserImpl("name", null)
                .equals(new AuthzUserImpl("name", null)));
        assertFalse("Values should not match", new AuthzUserImpl("name", "alias")
                .equals(new AuthzUserImpl("name", null)));
        assertFalse("Values should not match", new AuthzUserImpl("name", null)
                .equals(new AuthzUserImpl("name", "alias")));
        assertTrue("Values should match", new AuthzUserImpl("name", "alias")
                .equals(new AuthzUserImpl("name", "alias")));

        assertTrue("Values should match", new AuthzUserImpl("", null)
                .equals(new AuthzUserImpl("", null)));
        assertFalse("Values should not match", new AuthzUserImpl("", "alias")
                .equals(new AuthzUserImpl("", null)));
        assertFalse("Values should not match", new AuthzUserImpl("", null)
                .equals(new AuthzUserImpl("", "alias")));
        assertTrue("Values should match", new AuthzUserImpl("", "alias")
                .equals(new AuthzUserImpl("", "alias")));
        assertFalse("Values should not match", new AuthzUserImpl("", "alias")
                .equals(new AuthzUserImpl("name", "alias")));

        assertFalse("Values should not match", new AuthzUserImpl("name", null)
                .equals(new AuthzUserImpl("name2", null)));

        // Test invalid values
        assertFalse("Values should not match", new AuthzUserImpl("name", null)
                .equals(null));
        assertFalse("Values should not match", new AuthzUserImpl("name", null)
                .equals(""));
        assertFalse("Values should not match", new AuthzUserImpl("name", null)
                .equals(new AuthzUserImpl("name", null).toString()));
    }

    @Test
    public void testHashCode() {
        assertTrue("HashCode values should match", new AuthzUserImpl("name", null)
                .hashCode() == new AuthzUserImpl("name", null).hashCode());
        assertFalse("HashCode values should not match", new AuthzUserImpl("name",
                null).hashCode() == new AuthzUserImpl("name2", null).hashCode());
    }

    @Test
    public void testToString() {
        final AuthzUser user = new AuthzUserImpl("myName", "myAlias");

        assertTrue("toString() should output name", user.toString().contains(
                "myName"));
        assertTrue("toString() should output alias", user.toString().contains(
                "myAlias"));
    }
}
