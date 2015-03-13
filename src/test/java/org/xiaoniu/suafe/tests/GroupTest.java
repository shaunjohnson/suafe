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
package org.xiaoniu.suafe.tests;

import org.junit.Test;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Shaun Johnson
 */
public class GroupTest {

    private final String groupName = "TestGroupName";

    @Test
    public void testAddRemoveAccessRule() {
        Group group = new Group();

        assertTrue(group.getAccessRules() != null);
        assertTrue(group.getAccessRules().size() == 0);

        AccessRule accessRule = new AccessRule();
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
        Group group = new Group();

        assertTrue(group.getGroups() != null);
        assertTrue(group.getGroups().size() == 0);

        Group memberOfGroup = new Group();
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
        Group group = new Group();

        assertTrue(group.getGroupMembers() != null);
        assertTrue(group.getGroupMembers().size() == 0);

        Group memberGroup = new Group();
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
        Group group = new Group();

        assertTrue(group.getUserMembers() != null);
        assertTrue(group.getUserMembers().size() == 0);

        User memberUser = new User();
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
        Group group = new Group();

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
        Group group = new Group();

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
        Group group = new Group(groupName);

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
        Group group = new Group();

        assertTrue(group.toString() != null);
        assertTrue(group.toString().equals(""));
        assertTrue(group.toString() == "");

        Group groupWithName = new Group(groupName);

        assertTrue(groupWithName.toString() != null);
        assertTrue(groupWithName.toString().equals(groupName));
        assertTrue(groupWithName.toString() == groupName);
    }
}
