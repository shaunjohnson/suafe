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

import org.junit.Test;
import net.lmxm.suafe.api.SubversionConstants;
import net.lmxm.suafe.api.beans.Document;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.Repository;
import net.lmxm.suafe.api.beans.User;
import net.lmxm.suafe.exceptions.AppException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DocumentUndoTest {

    private static final String accessLevel = SubversionConstants.SVN_ACCESS_LEVEL_READONLY;

    private static final String groupName = "TestGroupName";

    private static final String groupName2 = "TestGroupName1";

    private static final String pathString = "/pathString";

    private static final String repositoryName = "TestRepositoryName";

    private static final String userName = "TestUserName";

    private static final String userName2 = "TestUserName2";

    @Test
    public void testClearUndoStack() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());
            document.addUser(userName);
            assertTrue(document.hasUndoActions());
            document.clearUndoStack();
            assertTrue(!document.hasUndoActions());
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testHasUndoActions() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());
            document.addUser(userName);
            assertTrue(document.hasUndoActions());
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testIsUndoEnabled() {
        Document document = new Document();

        assertTrue(document.isUndoEnabled());

        document.disableUndo();
        assertTrue(!document.isUndoEnabled());

        document.enableUndo();
        assertTrue(document.isUndoEnabled());

        document.disableUndo();
        assertTrue(!document.isUndoEnabled());

        document.initialize();
        assertTrue(document.isUndoEnabled());
    }

    @Test
    public void testUndoLastAddGroupAction() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());
            document.addGroup(groupName);
            assertTrue(document.hasUndoActions());
            assertTrue(document.findGroup(groupName) != null);
            document.undoLastAction();
            assertTrue(document.findGroup(groupName) == null);
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testUndoLastAddGroupByNameAction() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());

            Group group = document.addGroup(groupName2);
            User user = document.addUser(userName);

            List<String> groupMemberNames = new ArrayList<String>(1);
            List<String> userMemberNames = new ArrayList<String>(1);

            groupMemberNames.add(groupName2);
            userMemberNames.add(userName);

            document.clearUndoStack();
            assertTrue(!document.hasUndoActions());

            document.addGroupByName(groupName, groupMemberNames, userMemberNames, null);

            assertTrue(document.hasUndoActions());

            Group group2 = document.findGroup(groupName);

            assertTrue(group2 != null);
            assertTrue(group2.getGroupMembers().contains(group));
            assertTrue(group2.getUserMembers().contains(user));
            assertTrue(group.getGroups().contains(group2));
            assertTrue(user.getGroups().contains(group2));

            document.undoLastAction();

            Group group3 = document.findGroup(groupName);

            assertTrue(group3 == null);
            assertTrue(group.getGroups().size() == 0);
            assertTrue(user.getGroups().size() == 0);
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testUndoLastAddGroupByObjectAction() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());

            Group group = document.addGroup(groupName2);
            User user = document.addUser(userName);

            List<Group> groupMembers = new ArrayList<Group>(1);
            List<User> userMembers = new ArrayList<User>(1);

            groupMembers.add(group);
            userMembers.add(user);

            document.clearUndoStack();
            assertTrue(!document.hasUndoActions());

            document.addGroup(groupName, groupMembers, userMembers);

            assertTrue(document.hasUndoActions());

            Group group2 = document.findGroup(groupName);

            assertTrue(group2 != null);
            assertTrue(group2.getGroupMembers().contains(group));
            assertTrue(group2.getUserMembers().contains(user));
            assertTrue(group.getGroups().contains(group2));
            assertTrue(user.getGroups().contains(group2));

            document.undoLastAction();

            Group group3 = document.findGroup(groupName);

            assertTrue(group3 == null);
            assertTrue(group.getGroups().size() == 0);
            assertTrue(user.getGroups().size() == 0);
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testUndoLastAddRepositoryAction() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());
            document.addRepository(repositoryName);
            assertTrue(document.hasUndoActions());
            assertTrue(document.findRepository(repositoryName) != null);
            document.undoLastAction();
            assertTrue(document.findRepository(repositoryName) == null);
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testUndoLastAddUserAction() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());
            document.addUser(userName);
            assertTrue(document.hasUndoActions());
            assertTrue(document.findUser(userName) != null);
            document.undoLastAction();
            assertTrue(document.findUser(userName) == null);
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testUndoLastCloneGroupAction() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());

            Group group = document.addGroup(groupName);
            document.addAccessRuleForGroup(new Repository(), pathString, group, accessLevel);

            document.clearUndoStack();

            assertTrue(!document.hasUndoActions());
            assertTrue(document.findGroup(groupName) != null);
            assertTrue(document.getAccessRules().size() == 1);

            document.cloneGroup(group, groupName2);

            assertTrue(document.hasUndoActions());
            assertTrue(document.findGroup(groupName) != null);
            assertTrue(document.findGroup(groupName2) != null);
            assertTrue(document.getAccessRules().size() == 2);

            document.undoLastAction();

            assertTrue(document.findGroup(groupName) != null);
            assertTrue(document.findGroup(groupName2) == null);
            assertTrue(document.getAccessRules().size() == 1);
        }
        catch (AppException e) {
            fail();
        }
    }

    @Test
    public void testUndoLastCloneUserAction() {
        try {
            Document document = new Document();

            assertTrue(!document.hasUndoActions());

            User user = document.addUser(userName);
            document.addAccessRuleForUser(new Repository(), pathString, user, accessLevel);

            document.clearUndoStack();

            assertTrue(!document.hasUndoActions());
            assertTrue(document.findUser(userName) != null);
            assertTrue(document.getAccessRules().size() == 1);

            document.cloneUser(user, userName2);

            assertTrue(document.hasUndoActions());
            assertTrue(document.findUser(userName) != null);
            assertTrue(document.findUser(userName2) != null);
            assertTrue(document.getAccessRules().size() == 2);

            document.undoLastAction();

            assertTrue(document.findUser(userName) != null);
            assertTrue(document.findUser(userName2) == null);
            assertTrue(document.getAccessRules().size() == 1);
        }
        catch (AppException e) {
            fail();
        }
    }
}
