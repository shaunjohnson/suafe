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
package net.lmxm.suafe.api.beans;

import net.lmxm.suafe.api.beans.AccessRule;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.User;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Group unit tests.
 *
 * @author Shaun Johnson
 */
public final class GroupTest {
    private final String groupName = "TestGroupName";

    @Test
    public void testAddRemoveAccessRule() {
        final Group group = new Group();

        assertTrue(group.getAccessRules() != null);
        assertTrue(group.getAccessRules().size() == 0);

        final AccessRule accessRule = new AccessRule();
        group.addAccessRule(accessRule);

        assertTrue(group.getAccessRules() != null);
        assertTrue(group.getAccessRules().size() == 1);
        assertTrue(group.getAccessRules().get(0) == accessRule);
        assertTrue(group.getAccessRules().get(0).equals(accessRule));

        group.removeAccessRule(accessRule);

        assertTrue(group.getAccessRules() != null);
        assertTrue(group.getAccessRules().size() == 0);
    }

    @Test
    public void testAddRemoveGroup() {
        final Group group = new Group();

        assertTrue(group.getGroups() != null);
        assertTrue(group.getGroups().size() == 0);

        final Group memberOfGroup = new Group();
        group.addGroup(memberOfGroup);

        assertTrue(group.getGroups() != null);
        assertTrue(group.getGroups().size() == 1);

        assertTrue(group.getGroups().get(0) == memberOfGroup);
        assertTrue(group.getGroups().get(0).equals(memberOfGroup));

        group.removeGroup(memberOfGroup);

        assertTrue(group.getGroups() != null);
        assertTrue(group.getGroups().size() == 0);
    }

    @Test
    public void testAddRemoveGroupMember() {
        final Group group = new Group();

        assertTrue(group.getGroupMembers() != null);
        assertTrue(group.getGroupMembers().size() == 0);

        final Group memberGroup = new Group();
        group.addGroupMember(memberGroup);

        assertTrue(group.getGroupMembers() != null);
        assertTrue(group.getGroupMembers().size() == 1);
        assertTrue(group.getGroupMembers().get(0) == memberGroup);
        assertTrue(group.getGroupMembers().get(0).equals(memberGroup));

        group.removeGroupMember(memberGroup);

        assertTrue(group.getGroupMembers() != null);
        assertTrue(group.getGroupMembers().size() == 0);
    }

    @Test
    public void testAddRemoveUserMember() {
        final Group group = new Group();

        assertTrue(group.getUserMembers() != null);
        assertTrue(group.getUserMembers().size() == 0);

        final User memberUser = new User();
        group.addUserMember(memberUser);

        assertTrue(group.getUserMembers() != null);
        assertTrue(group.getUserMembers().size() == 1);
        assertTrue(group.getUserMembers().get(0) == memberUser);
        assertTrue(group.getUserMembers().get(0).equals(memberUser));

        group.removeUserMember(memberUser);

        assertTrue(group.getUserMembers() != null);
        assertTrue(group.getUserMembers().size() == 0);
    }

    @Test
    public void testCompareTo() {
        Group groupA = new Group();
        Group groupB = new Group();

        assertTrue(groupA.compareTo(groupB) == 0);
        assertTrue(groupB.compareTo(groupA) == 0);

        groupA = new Group("group");
        groupB = new Group("group");

        assertTrue(groupA.compareTo(groupB) == 0);
        assertTrue(groupB.compareTo(groupA) == 0);

        groupA = new Group("groupA");
        groupB = new Group(null);

        assertTrue(groupA.compareTo(groupB) != 0);
        assertTrue(groupB.compareTo(groupA) != 0);

        groupA = new Group("groupA");
        groupB = new Group("groupB");

        assertTrue(groupB.compareTo(groupA) != 0);
        assertTrue(groupA.compareTo(groupB) != 0);
        assertTrue(groupA.compareTo(groupB) < 0);
        assertTrue(groupB.compareTo(groupA) > 0);
    }

    @Test
    public void testEquals() {
        Group groupA = new Group();
        Group groupB = new Group();

        assertTrue(groupA.equals(groupB));
        assertTrue(groupB.equals(groupA));

        groupA = new Group("group");
        groupB = new Group("group");

        assertTrue(groupA.equals(groupB));
        assertTrue(groupB.equals(groupA));

        groupA = new Group("group");
        groupB = new Group("   group   ");

        assertTrue(groupA.equals(groupB));
        assertTrue(groupB.equals(groupA));

        groupA = new Group("group");
        groupB = new Group("GROUP");

        assertFalse(groupA.equals(groupB));
        assertFalse(groupB.equals(groupA));

        groupA = new Group("groupA");
        groupB = new Group(null);

        assertFalse(groupA.equals(groupB));
        assertFalse(groupB.equals(groupA));

        groupA = new Group("groupA");
        groupB = new Group("groupB");

        assertFalse(groupB.equals(groupA));
        assertFalse(groupA.equals(groupB));
    }

    @Test
    public void testGetSetName() {
        final Group group = new Group();

        assertTrue(group.getName() == null);

        group.setName(groupName);

        assertTrue(group.getName() != null);
        assertTrue(group.getName().equals(groupName));

        group.setName(null);

        assertTrue(group.getName() == null);
    }

    /*
     * Class under test for void Group()
     */
    @Test
    public void testGroup() {
        final Group group = new Group();

        assertTrue(group.getName() == null);

        assertTrue(group.getAccessRules() != null);
        assertTrue(group.getAccessRules().size() == 0);

        assertTrue(group.getGroups() != null);
        assertTrue(group.getGroups().size() == 0);
    }

    /*
     * Class under test for void Group(String)
     */
    @Test
    public void testGroupString() {
        final Group group = new Group(groupName);

        assertTrue(group.getName() != null);
        assertTrue(group.getName().equals(groupName));

        assertTrue(group.getAccessRules() != null);
        assertTrue(group.getAccessRules().size() == 0);

        assertTrue(group.getGroups() != null);
        assertTrue(group.getGroups().size() == 0);
    }

    /*
     * Class under test for String toString()
     */
    @Test
    public void testToString() {
        final Group group = new Group();

        assertTrue(group.toString() != null);
        assertTrue(group.toString().equals(""));
        assertTrue(group.toString() == "");

        final Group groupWithName = new Group(groupName);

        assertTrue(groupWithName.toString() != null);
        assertTrue(groupWithName.toString().equals(groupName));
        assertTrue(groupWithName.toString() == groupName);
    }
}
