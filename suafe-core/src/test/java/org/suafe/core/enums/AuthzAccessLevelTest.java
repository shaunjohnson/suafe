/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.suafe.core.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.suafe.core.enums.AuthzAccessLevel;

/**
 * The Class AuthzAccessLevelTest.
 */
public class AuthzAccessLevelTest {
    /**
     * Test compare to.
     */
    @Test
    public void testCompareTo() {
        assertTrue(AuthzAccessLevel.DENY_ACCESS
                .compareTo(AuthzAccessLevel.DENY_ACCESS) == 0);
        assertTrue(AuthzAccessLevel.READ_ONLY
                .compareTo(AuthzAccessLevel.READ_ONLY) == 0);
        assertTrue(AuthzAccessLevel.READ_WRITE
                .compareTo(AuthzAccessLevel.READ_WRITE) == 0);

        assertTrue(AuthzAccessLevel.DENY_ACCESS
                .compareTo(AuthzAccessLevel.READ_ONLY) < 0);
        assertTrue(AuthzAccessLevel.READ_ONLY
                .compareTo(AuthzAccessLevel.DENY_ACCESS) > 0);
        assertTrue(AuthzAccessLevel.READ_WRITE
                .compareTo(AuthzAccessLevel.READ_ONLY) > 0);
    }

    /**
     * Test equals object.
     */
    @Test
    public void testEqualsObject() {
        assertEquals(AuthzAccessLevel.DENY_ACCESS, AuthzAccessLevel.DENY_ACCESS);
        assertEquals(AuthzAccessLevel.READ_ONLY, AuthzAccessLevel.READ_ONLY);
        assertEquals(AuthzAccessLevel.READ_WRITE, AuthzAccessLevel.READ_WRITE);

        assertFalse(AuthzAccessLevel.DENY_ACCESS
                .equals(AuthzAccessLevel.READ_WRITE));
    }

    /**
     * Test hash code.
     */
    @Test
    public void testHashCode() {
        assertEquals(AuthzAccessLevel.DENY_ACCESS.hashCode(),
                AuthzAccessLevel.DENY_ACCESS.hashCode());
        assertEquals(AuthzAccessLevel.READ_ONLY.hashCode(),
                AuthzAccessLevel.READ_ONLY.hashCode());
        assertEquals(AuthzAccessLevel.READ_WRITE.hashCode(),
                AuthzAccessLevel.READ_WRITE.hashCode());

        assertFalse(AuthzAccessLevel.DENY_ACCESS.hashCode() == AuthzAccessLevel.READ_WRITE
                .hashCode());
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString() {
        // Not yet implemented
    }
}
