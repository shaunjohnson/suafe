package org.suafe.core.utilities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.suafe.core.enums.AuthzErrorResourceKey;

public class AuthzResourcesTest {
    @Test
    public void testGetString() {
        try {
            AuthzResources.getString(null);

            fail("Successfully got value of key null");
        }
        catch (final NullPointerException e) {
            assertNotNull("Expected NullPointerException", e.getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected Exception");
        }

        try {
            assertNotNull("Value should not be null", AuthzResources
                    .getString(AuthzErrorResourceKey.INVALID_USER_NAME));
        }
        catch (final Exception e) {
            fail("Unexpected Exception");
        }
    }
}
