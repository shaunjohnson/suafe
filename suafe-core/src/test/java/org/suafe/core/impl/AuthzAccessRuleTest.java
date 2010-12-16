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
package org.suafe.core.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.suafe.core.constants.AuthzAccessLevel;
import org.suafe.core.impl.AuthzAccessRule;
import org.suafe.core.impl.AuthzGroup;
import org.suafe.core.impl.AuthzPath;
import org.suafe.core.impl.AuthzUser;

/**
 * The Class AuthzAccessRuleTest.
 */
public class AuthzAccessRuleTest {

    /** The blank path. */
    private AuthzPath blankPath;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        blankPath = new AuthzPath(null, "/");
    }

    /**
     * Test authz access rule for group.
     */
    @Test
    public void testAuthzAccessRuleForGroup() {
        // Test all three null values
        try {
            new AuthzAccessRule(null, (AuthzGroup) null, null);

            fail("Unexpectedly success");
        }
        catch (final NullPointerException e) {
            assertNotNull("NullPointerException should be thrown", e);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test with non-null path
        try {
            new AuthzAccessRule(blankPath, (AuthzGroup) null, null);

            fail("Unexpectedly success");
        }
        catch (final NullPointerException e) {
            assertNotNull("NullPointerException should be thrown", e);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test with non-null path and group
        try {
            new AuthzAccessRule(blankPath, new AuthzGroup("group"), null);

            fail("Unexpectedly success");
        }
        catch (final NullPointerException e) {
            assertNotNull("NullPointerException should be thrown", e);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test valid values
        try {
            new AuthzAccessRule(blankPath, new AuthzGroup("group"),
                    AuthzAccessLevel.DENY_ACCESS);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }
    }

    /**
     * Test authz access rule for user.
     */
    @Test
    public void testAuthzAccessRuleForUser() {
        // Test all three null values
        try {
            new AuthzAccessRule(null, (AuthzUser) null, null);

            fail("Unexpectedly success");
        }
        catch (final NullPointerException e) {
            assertNotNull("NullPointerException should be thrown", e);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test with non-null path
        try {
            new AuthzAccessRule(blankPath, (AuthzUser) null, null);

            fail("Unexpectedly success");
        }
        catch (final NullPointerException e) {
            assertNotNull("NullPointerException should be thrown", e);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test with non-null path and group
        try {
            new AuthzAccessRule(blankPath, new AuthzUser("user", null), null);

            fail("Unexpectedly success");
        }
        catch (final NullPointerException e) {
            assertNotNull("NullPointerException should be thrown", e);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test valid values
        try {
            new AuthzAccessRule(blankPath, new AuthzUser("user", null),
                    AuthzAccessLevel.DENY_ACCESS);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }
    }

    /**
     * Test compare to.
     */
    @Test
    public void testCompareTo() {
        // Not yet implemented
    }

    /**
     * Test get access level.
     */
    @Test
    public void testGetAccessLevel() {
        // Not yet implemented
    }

    /**
     * Test get group.
     */
    @Test
    public void testGetGroup() {
        // Not yet implemented
    }

    /**
     * Test get path.
     */
    @Test
    public void testGetPath() {
        // Not yet implemented
    }

    /**
     * Test get user.
     */
    @Test
    public void testGetUser() {
        // Not yet implemented
    }
}
