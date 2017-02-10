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

package net.lmxm.suafe;

import net.lmxm.suafe.api.beans.Document;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.User;
import net.lmxm.suafe.api.parser.FileParser;
import net.lmxm.suafe.exceptions.AppException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static net.lmxm.suafe.CommandLineApplication.*;
import static net.lmxm.suafe.api.SubversionConstants.SVN_ACCESS_LEVEL_READWRITE;
import static net.lmxm.suafe.test.AppExceptionMatcher.hasKey;
import static net.lmxm.suafe.test.Assert.assertGroupsExist;
import static net.lmxm.suafe.test.Assert.assertUsersExist;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * CommandLineApplication unit tests.
 */
public final class CommandLineApplicationTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void setAddGroup() throws Exception {
        final Document document = new Document();

        assertThat(document.getUsers().size(), is(0));
        assertThat(document.getGroups().size(), is(0));
        assertThat(document.getAccessRules().size(), is(0));

        final Document output = new FileParser().parse(addGroup(document, "groupName"));

        assertThat(document.getUsers().size(), is(0));

        assertThat(output.getGroups().size(), is(1));
        assertThat(output.getGroups().get(0).getName(), is("groupName"));
        assertThat(output.getGroups().get(0).getGroups().size(), is(0));
        assertThat(output.getGroups().get(0).getGroupMembers().size(), is(0));
        assertThat(output.getGroups().get(0).getUserMembers().size(), is(0));
        assertThat(output.getGroups().get(0).getAccessRules().size(), is(0));

        assertThat(document.getAccessRules().size(), is(0));
    }

    @Test
    public void setAddGroups() throws Exception {
        final Document document = new Document();
        final String userName = "userName";
        final String[] groupNames = new String[]{"groupName1", "groupName2"};

        for (final String groupName : groupNames) {
            document.addGroup(groupName);
        }

        assertThat(document.getUsers().size(), is(0));
        assertGroupsExist(document.getGroups(), groupNames);
        assertThat(document.getAccessRules().size(), is(0));

        final Document output = new FileParser().parse(addGroups(document, userName, groupNames));

        assertThat(document.getUsers().size(), is(1));

        final User user = output.getUsers().get(0);
        assertThat(user.getName(), is(userName));
        assertThat(user.getAlias(), is(nullValue()));
        assertGroupsExist(user.getGroups(), groupNames);
        assertThat(user.getAccessRules().size(), is(0));

        assertThat(document.getGroups().size(), is(2));

        final Group group1 = document.getGroups().get(0);
        assertThat(group1.getName(), is(groupNames[0]));
        assertUsersExist(group1.getUserMembers(), userName);

        final Group group2 = document.getGroups().get(1);
        assertThat(group2.getName(), is(groupNames[1]));
        assertUsersExist(group2.getUserMembers(), userName);

        assertThat(document.getAccessRules().size(), is(0));
    }

    @Test
    public void setAddGroups_userName_null() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.userrequired"));
        addGroups(new Document(), null, new String[]{"groupName1", "groupName2"});
    }

    @Test
    public void setAddGroups_groupNames_null() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.grouplistrequired"));
        addGroups(new Document(), "userName", null);
    }

    @Test
    public void setAddGroups_groupNames_empty() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.grouplistrequired"));
        addGroups(new Document(), "userName", new String[]{});
    }

    @Test
    public void setAddGroups_groupNames_doesNotExist() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.unabletofindgroup"));
        addGroups(new Document(), "userName", new String[]{"groupName"});
    }

    @Test
    public void setAddMembers_groupName_null() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.grouprequired"));
        addMembers(new Document(), null, new String[]{"userName"}, new String[]{"groupName1"});
    }

    @Test
    public void setAddMembers_groupName_doesNotExist() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.unabletofindgroup"));
        addMembers(new Document(), "groupName", new String[]{"userName"}, new String[]{"groupName1"});
    }

    @Test
    public void setAddMembers() throws Exception {
        final Document document = new Document();
        final String groupName = "groupName";
        final String memberGroupName = "memberGroupName";
        final String[] userNames = new String[]{"userName1", "userName2"};
        final String[] groupNames = new String[]{memberGroupName};

        document.addGroup(groupName);
        document.addGroup(memberGroupName);

        assertThat(document.getUsers().size(), is(0));
        assertGroupsExist(document.getGroups(), groupName, memberGroupName);

        final Group groupBefore = document.findGroup(groupName);
        assertThat(groupBefore.getGroups().size(), is(0));
        assertThat(groupBefore.getUserMembers().size(), is(0));
        assertThat(groupBefore.getGroupMembers().size(), is(0));

        final Group memberGroupBefore = document.findGroup(memberGroupName);
        assertThat(memberGroupBefore.getGroups().size(), is(0));
        assertThat(memberGroupBefore.getUserMembers().size(), is(0));
        assertThat(memberGroupBefore.getGroupMembers().size(), is(0));

        final Document output = new FileParser().parse(addMembers(document, groupName, userNames, groupNames));

        assertThat(output.getUsers().size(), is(2));
        assertUsersExist(output.getUsers(), userNames);
        assertGroupsExist(output.getGroups(), groupName, memberGroupName);

        final Group groupAfter = output.findGroup(groupName);
        assertThat(groupAfter.getGroups().size(), is(0));
        assertUsersExist(groupAfter.getUserMembers(), userNames);
        assertGroupsExist(groupAfter.getGroupMembers(), groupNames);

        final Group memberGroupAfter = output.findGroup(memberGroupName);
        assertGroupsExist(memberGroupAfter.getGroups(), groupName);
        assertThat(memberGroupAfter.getUserMembers().size(), is(0));
        assertThat(memberGroupAfter.getGroupMembers().size(), is(0));
    }

    @Test
    public void setAddMembers_members_missing() throws Exception {
        final Document document = new Document();
        final String groupName = "groupName";

        document.addGroup(groupName);

        assertGroupsExist(document.getGroups(), groupName);

        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.userorgrouplistrequired"));
        addMembers(document, groupName, new String[]{}, new String[]{});
    }

    @Test
    public void setAddMembers_circularReference() throws Exception {
        final Document document = new Document();
        final String groupName = "groupName";
        final String[] userNames = new String[]{};
        final String[] groupNames = new String[]{groupName};

        document.addGroup(groupName);

        assertThat(document.getUsers().size(), is(0));
        assertGroupsExist(document.getGroups(), groupName);

        final Group groupBefore = document.findGroup(groupName);
        assertThat(groupBefore.getGroups().size(), is(0));
        assertThat(groupBefore.getUserMembers().size(), is(0));
        assertThat(groupBefore.getGroupMembers().size(), is(0));

        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.circularreference"));
        addMembers(document, groupName, userNames, groupNames);
    }

    @Test
    public void setAddMembers_userNames_null() throws Exception {
        final Document document = new Document();
        final String groupName = "groupName";

        document.addGroup(groupName);

        assertGroupsExist(document.getGroups(), groupName);

        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.userorgrouplistrequired"));
        addMembers(document, groupName, null, new String[]{});
    }

    @Test
    public void setAddMembers_groupNames_null() throws Exception {
        final Document document = new Document();
        final String groupName = "groupName";

        document.addGroup(groupName);

        assertGroupsExist(document.getGroups(), groupName);

        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.userorgrouplistrequired"));
        addMembers(document, groupName, new String[]{}, null);
    }

    @Test
    public void setAddMembers_groupNames_doesNotExist() throws Exception {
        final Document document = new Document();
        final String groupName = "groupName";

        document.addGroup(groupName);

        assertGroupsExist(document.getGroups(), groupName);

        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.unabletofindgroup"));
        addMembers(document, groupName, new String[]{}, new String[]{"groupName1"});
    }



    @Test
    public void setAddRule_path_null() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.pathrequired"));
        addRule(new Document(), "repositoryName", null, "userName", null, SVN_ACCESS_LEVEL_READWRITE);
    }

    @Test
    public void setAddRule_userAndGroup_null() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.userorgrouprequired"));
        addRule(new Document(), "repositoryName", "/", null, null, SVN_ACCESS_LEVEL_READWRITE);
    }

    @Test
    public void setAddRule_userAndGroup_bothSpecified() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.userorgrouprequired"));
        addRule(new Document(), "repositoryName", "/", "userName", "groupName", SVN_ACCESS_LEVEL_READWRITE);
    }

    @Test
    public void setAddRule_access_null() throws Exception {
        thrown.expect(AppException.class);
        thrown.expect(hasKey("application.error.accessrequired"));
        addRule(new Document(), "repositoryName", "/", "userName", null, null);
    }
}