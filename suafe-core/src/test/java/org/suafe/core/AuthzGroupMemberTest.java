package org.suafe.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;

public class AuthzGroupMemberTest {
    @Test
    public void testAddGroup() {
        // Test happy path
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzUser user = document.createUser("user", null);
            final AuthzGroup group = document.createGroup("group");

            assertTrue("Groups should be empty", user.getGroups().size() == 0);

            user.addGroup(group);

            assertTrue("Groups should be not be empty",
                    user.getGroups().size() == 1);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test invalid values
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzUser user = document.createUser("user", null);

            user.addGroup(null);

            fail("Unexpected successfully added null group");
        }
        catch (final NullPointerException e) {
            assertNotNull("Null pointer exception expected", e.getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test exception
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzUser user = document.createUser("user", null);
            final AuthzGroup group = document.createGroup("group");

            user.addGroup(group);
            user.addGroup(group);

            fail("Unexpected successfully added same group twice");
        }
        catch (final AuthzAlreadyMemberOfGroupException e) {
            assertNotNull("Null pointer exception expected", e.getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void testRemoveGroup() {
        // Test happy path
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzGroup group = document.createGroup("name");
            final AuthzUser user = document.createUser("user", null);

            assertTrue("groups should be empty", user.getGroups().size() == 0);

            assertTrue("addGroup() should reutrn true", user.addGroup(group));

            assertTrue("groups should be empty", user.getGroups().size() == 1);

            assertTrue("removeMember() should reutrn true", user
                    .removeGroup(group));

            assertTrue("groups should be empty", user.getGroups().size() == 0);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test invalid values
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzUser user = document.createUser("name", null);

            user.removeGroup(null);

            fail("Unexpected successfully removed null group");
        }
        catch (final NullPointerException e) {
            assertNotNull("Null pointer exception expected", e.getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test for not a member exception
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzUser user = document.createUser("name", null);

            user.removeGroup(document.createGroup("group"));

            fail("Unexpected successfully removed group that this user is not a member of");
        }
        catch (final AuthzNotMemberOfGroupException e) {
            assertNotNull("AuthzNotGroupMemberException expected", e
                    .getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }
    }
}
