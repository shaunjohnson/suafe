/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.lmxm.suafe.tests;

import net.lmxm.suafe.api.beans.AccessRule;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.User;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User unit tests.
 *
 * @author Shaun Johnson
 */
public final class UserTest {
    private final String userName = "TestUserName";

    @Test
    public void testAddRemoveAccessRule() {
        final User user = new User();

        assertTrue(user.getAccessRules() != null);
        assertTrue(user.getAccessRules().size() == 0);

        AccessRule accessRule = new AccessRule();
        user.addAccessRule(accessRule);

        assertTrue(user.getAccessRules() != null);
        assertTrue(user.getAccessRules().size() == 1);
        assertTrue(user.getAccessRules().get(0) == accessRule);
        assertTrue(user.getAccessRules().get(0).equals(accessRule));

        user.removeAccessRule(accessRule);

        assertTrue(user.getAccessRules() != null);
        assertTrue(user.getAccessRules().size() == 0);
    }

    @Test
    public void testAddRemoveGroup() {
        final User user = new User();

        assertTrue(user.getGroups() != null);
        assertTrue(user.getGroups().size() == 0);

        final Group memberOfGroup = new Group();
        user.addGroup(memberOfGroup);

        assertTrue(user.getGroups() != null);
        assertTrue(user.getGroups().size() == 1);

        assertTrue(user.getGroups().get(0) == memberOfGroup);
        assertTrue(user.getGroups().get(0).equals(memberOfGroup));

        user.removeGroup(memberOfGroup);

        assertTrue(user.getGroups() != null);
        assertTrue(user.getGroups().size() == 0);
    }

    @Test
    public void testCompareTo() {
        User userA = new User();
        User userB = new User();

        assertTrue(userA.compareTo(userB) == 0);
        assertTrue(userB.compareTo(userA) == 0);

        userA = new User("user");
        userB = new User("user");

        assertTrue(userA.compareTo(userB) == 0);
        assertTrue(userB.compareTo(userA) == 0);

        userA = new User("userA");
        userB = new User(null);

        assertTrue(userA.compareTo(userB) != 0);
        assertTrue(userB.compareTo(userA) != 0);

        userA = new User("userA");
        userB = new User("userB");

        assertTrue(userA.compareTo(userB) != 0);
        assertTrue(userB.compareTo(userA) != 0);
        assertTrue(userA.compareTo(userB) < 0);
        assertTrue(userB.compareTo(userA) > 0);
    }

    @Test
    public void testEquals() {
        User userA = new User();
        User userB = new User();

        assertTrue(userA.equals(userB));
        assertTrue(userB.equals(userA));

        userA = new User("user");
        userB = new User("user");

        assertTrue(userA.equals(userB));
        assertTrue(userB.equals(userA));

        userA = new User("user");
        userB = new User("  user  ");

        assertTrue(userA.equals(userB));
        assertTrue(userB.equals(userA));

        userA = new User("user");
        userB = new User("USER");

        assertFalse(userA.equals(userB));
        assertFalse(userB.equals(userA));

        userA = new User("userA");
        userB = new User(null);

        assertFalse(userA.equals(userB));
        assertFalse(userB.equals(userA));

        userA = new User("userA");
        userB = new User("userB");

        assertFalse(userA.equals(userB));
        assertFalse(userB.equals(userA));
    }

    @Test
    public void testIsAllUsers() {
        final User userA = new User("*");
        final User userB = new User(" * ");
        final User userC = new User("A");

        assertTrue(userA.isAllUsers());
        assertTrue(userB.isAllUsers());
        assertFalse(userC.isAllUsers());
    }

    @Test
    public void testGetSetName() {
        final User user = new User();

        assertTrue(user.getName() == null);

        user.setName(userName);

        assertTrue(user.getName() != null);
        assertTrue(user.getName().equals(userName));

        user.setName(null);

        assertTrue(user.getName() == null);
    }

    /*
     * Class under test for String toString()
     */
    @Test
    public void testToString() {
        final User user = new User();

        assertTrue(user.toString() != null);
        assertTrue(user.toString().equals(""));

        final User userWithName = new User(userName);

        assertTrue(userWithName.toString() != null);
        assertTrue(userWithName.toString().equals(userName));
    }

    /*
     * Class under test for void User()
     */
    @Test
    public void testUser() {
        final User user = new User();

        assertTrue(user.getName() == null);

        assertTrue(user.getAccessRules() != null);
        assertTrue(user.getAccessRules().size() == 0);

        assertTrue(user.getGroups() != null);
        assertTrue(user.getGroups().size() == 0);
    }

    /*
     * Class under test for void User(String)
     */
    @Test
    public void testUserString() {
        final User user = new User(userName);

        assertTrue(user.getName() != null);
        assertTrue(user.getName().equals(userName));

        assertTrue(user.getAccessRules() != null);
        assertTrue(user.getAccessRules().size() == 0);

        assertTrue(user.getGroups() != null);
        assertTrue(user.getGroups().size() == 0);
    }
}
