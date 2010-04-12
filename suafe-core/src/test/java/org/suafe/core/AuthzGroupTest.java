package org.suafe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;

/**
 * Unit test for AuthzGroup.
 */
public class AuthzGroupTest {
    @Test
    public void testAddMember() {
        // Test happy path
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzGroup group = document.createGroup("name");

            assertTrue("addMember() should reutrn true", group
                    .addMember(document.createGroup("group")));
            assertTrue("addMember() should return true", group
                    .addMember(document.createUser("user", null)));
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test check for duplicate group
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzGroup group = document.createGroup("name");
            final AuthzGroupMember member = document.createGroup("group");

            assertTrue("addMember() should reutrn true", group
                    .addMember(member));
            assertTrue("addMember() should reutrn true", group
                    .addMember(member));

            fail("Unexpected success adding member twice");
        }
        catch (final AuthzGroupMemberAlreadyExistsException e) {
            assertNotNull(
                    "AuthzGroupMemberAlreadyExistsException should be thrown",
                    e.getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test check for duplicate user
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzGroup group = document.createGroup("name");
            final AuthzGroupMember member = document.createUser("user", null);

            assertTrue("addMember() should reutrn true", group
                    .addMember(member));
            assertTrue("addMember() should reutrn true", group
                    .addMember(member));

            fail("Unexpected success adding member twice");
        }
        catch (final AuthzGroupMemberAlreadyExistsException e) {
            assertNotNull(
                    "AuthzGroupMemberAlreadyExistsException should be thrown",
                    e.getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test invalid values
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzGroup group = document.createGroup("name");

            group.addMember(null);

            fail("Unexpected successfully added null member");
        }
        catch (final NullPointerException e) {
            assertNotNull("Null pointer exception expected", e.getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void testAuthzGroupString() {
        final AuthzGroup group = new AuthzGroup("name");

        assertEquals("name should be valid", "name", group.getName());
    }

    @Test
    public void testCompareTo() {
        assertTrue("Null groups should match", new AuthzGroup(null)
                .compareTo(new AuthzGroup(null)) == 0);
        assertTrue("Empty groups should match", new AuthzGroup("")
                .compareTo(new AuthzGroup("")) == 0);
        assertTrue("Groups with same name should match", new AuthzGroup("name")
                .compareTo(new AuthzGroup("name")) == 0);
        assertTrue("Groups with same name and alias should match",
                new AuthzGroup("name").compareTo(new AuthzGroup("name")) == 0);

        assertTrue("Groups should not match", new AuthzGroup("name")
                .compareTo(new AuthzGroup("same")) < 0);
        assertTrue("Groups should not match", new AuthzGroup("same")
                .compareTo(new AuthzGroup("name")) > 0);
    }

    @Test
    public void testEquals() {
        assertTrue("Values should match", new AuthzGroup("name")
                .equals(new AuthzGroup("name")));
        assertTrue("Values should match", new AuthzGroup(null)
                .equals(new AuthzGroup(null)));
        assertFalse("Values should not match", new AuthzGroup("name")
                .equals(new AuthzGroup("name2")));

        // Test invalid values
        assertFalse("Values should not match", new AuthzGroup("name")
                .equals(null));
        assertFalse("Values should not match", new AuthzGroup("name")
                .equals(""));
        assertFalse("Values should not match", new AuthzGroup("name")
                .equals(new AuthzGroup("name").toString()));
    }

    @Test
    public void testHashCode() {
        assertTrue("HashCode values should match", new AuthzGroup("name")
                .hashCode() == new AuthzGroup("name").hashCode());
        assertFalse("HashCode values should not match", new AuthzGroup("name")
                .hashCode() == new AuthzGroup("name2").hashCode());
    }

    @Test
    public void testRemoveMember() {
        // Test happy path
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzGroup group = document.createGroup("name");
            final AuthzGroupMember member = document.createUser("user", null);

            assertTrue("members should be empty",
                    group.getMembers().size() == 0);

            assertTrue("addMember() should reutrn true", group
                    .addMember(member));

            assertTrue("members should be empty",
                    group.getMembers().size() == 1);

            assertTrue("removeMember() should reutrn true", group
                    .removeMember(member));

            assertTrue("members should be empty",
                    group.getMembers().size() == 0);
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }

        // Test invalid values
        try {
            final AuthzDocument document = new AuthzDocument();
            final AuthzGroup group = document.createGroup("name");

            group.removeMember(null);

            fail("Unexpected successfully removed null member");
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
            final AuthzGroup group = document.createGroup("name");

            group.removeMember(document.createUser("name", null));

            fail("Unexpected successfully removed object that was not a group member");
        }
        catch (final AuthzNotGroupMemberException e) {
            assertNotNull("AuthzNotGroupMemberException expected", e
                    .getMessage());
        }
        catch (final Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void testToString() {
        final AuthzGroup group = new AuthzGroup("myName");

        assertTrue("toString() should output name", group.toString().contains(
                "myName"));
    }
}
