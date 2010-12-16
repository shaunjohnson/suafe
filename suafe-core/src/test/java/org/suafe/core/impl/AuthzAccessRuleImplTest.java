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
import org.suafe.core.impl.AuthzAccessRuleImpl;
import org.suafe.core.impl.AuthzGroupImpl;
import org.suafe.core.impl.AuthzPathImpl;
import org.suafe.core.impl.AuthzUserImpl;

/**
 * The Class AuthzAccessRuleTest.
 */
public class AuthzAccessRuleImplTest {

    /** The blank path. */
    private AuthzPathImpl blankPath;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        blankPath = new AuthzPathImpl(null, "/");
    }

    /**
     * Test authz access rule for group.
     */
    @Test
    public void testAuthzAccessRuleForGroup() {
        // Test all three null values
        try {
            new AuthzAccessRuleImpl(null, (AuthzGroupImpl) null, null);

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
            new AuthzAccessRuleImpl(blankPath, (AuthzGroupImpl) null, null);

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
            new AuthzAccessRuleImpl(blankPath, new AuthzGroupImpl("group"), null);

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
            new AuthzAccessRuleImpl(blankPath, new AuthzGroupImpl("group"),
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
            new AuthzAccessRuleImpl(null, (AuthzUserImpl) null, null);

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
            new AuthzAccessRuleImpl(blankPath, (AuthzUserImpl) null, null);

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
            new AuthzAccessRuleImpl(blankPath, new AuthzUserImpl("user", null), null);

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
            new AuthzAccessRuleImpl(blankPath, new AuthzUserImpl("user", null),
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
