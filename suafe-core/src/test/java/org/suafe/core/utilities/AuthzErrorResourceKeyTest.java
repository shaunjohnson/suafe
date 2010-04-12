package org.suafe.core.utilities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;

public class AuthzErrorResourceKeyTest {
    @Test
    public void testAuthzErrorResourceKey() {
        try {
            final Class<?> c = AuthzErrorResourceKey.class;

            for (final Field f : c.getFields()) {
                if (f.getType().equals(AuthzErrorResourceKey.class)) {
                    final AuthzErrorResourceKey key = (AuthzErrorResourceKey) f
                            .get(null);

                    assertNotNull("Unable to load resource with key \""
                            + key.toString() + "\"", AuthzResources
                            .getString(key));
                }
            }
        }
        catch (final IllegalArgumentException e) {
            fail("IllegalArgumentException caught");
        }
        catch (final IllegalAccessException e) {
            fail("IllegalAccessException caught");
        }
    }

    @Test
    public void testEquals() {
        assertTrue("Values should match",
                AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP
                        .equals(AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP));
        assertFalse("Values should not match",
                AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP
                        .equals(AuthzErrorResourceKey.GROUP_ALREADY_EXISTS));

        // Test invalid values
        assertFalse("Values should not match",
                AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.equals(null));
        assertFalse("Values should not match",
                AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.equals(""));
        assertFalse("Values should not match",
                AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP
                        .equals(AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP
                                .toString()));
    }

    @Test
    public void testHashCode() {
        assertTrue(
                "HashCode values should match",
                AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.hashCode() == AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP
                        .hashCode());
        assertFalse(
                "HashCode values should not match",
                AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.hashCode() == AuthzErrorResourceKey.GROUP_ALREADY_EXISTS
                        .hashCode());
    }
}
