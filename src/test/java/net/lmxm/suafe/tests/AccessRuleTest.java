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

import net.lmxm.suafe.api.SubversionConstants;
import net.lmxm.suafe.api.beans.AccessRule;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.Path;
import net.lmxm.suafe.api.beans.User;
import net.lmxm.suafe.exceptions.AppException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Shaun Johnson
 */
public class AccessRuleTest {

    private final String groupName = "TestGroupName";

    private final String userName = "TestUserName";

    /*
     * Class under test for void AccessRule()
     */
    @Test
    public void testAccessRule() {
        AccessRule rule = new AccessRule();

        assertTrue(rule.getPath() == null);
        assertTrue(rule.getGroup() == null);
        assertTrue(rule.getUser() == null);
        assertTrue(rule.getLevel() == null);
        assertTrue(rule.getLevelFullName() != null);
    }

    /*
     * Class under test for void AccessRule(Path, Group, String)
     */
    @Test
    public void testAccessRulePathGroupString() {
        Path path = new Path();
        Group group = new Group();
        AccessRule rule = new AccessRule(path, group, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(rule.getPath().equals(path));
        assertTrue(rule.getGroup().equals(group));
        assertTrue(rule.getUser() == null);
        assertTrue(rule.getLevel() != null);
        assertTrue(rule.getLevel().equals(SubversionConstants.SVN_ACCESS_LEVEL_READONLY));
        assertTrue(rule.getLevelFullName() != null);
    }

    /*
     * Class under test for void AccessRule(Path, String)
     */
    @Test
    public void testAccessRulePathString() {
        Path path = new Path();
        AccessRule rule = new AccessRule(path, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(rule.getPath().equals(path));
        assertTrue(rule.getGroup() == null);
        assertTrue(rule.getUser() == null);
        assertTrue(rule.getLevel() != null);
        assertTrue(rule.getLevel().equals(SubversionConstants.SVN_ACCESS_LEVEL_READONLY));
        assertTrue(rule.getLevelFullName() != null);
    }

    /*
     * Class under test for void AccessRule(Path, User, String)
     */
    @Test
    public void testAccessRulePathUserString() {
        Path path = new Path();
        User user = new User();
        AccessRule rule = new AccessRule(path, user, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(rule.getPath().equals(path));
        assertTrue(rule.getGroup() == null);
        assertTrue(rule.getUser().equals(user));
        assertTrue(rule.getLevel() != null);
        assertTrue(rule.getLevel().equals(SubversionConstants.SVN_ACCESS_LEVEL_READONLY));
        assertTrue(rule.getLevelFullName() != null);
    }

    @Test
    public void testCompareTo() {
        AccessRule ac1a = new AccessRule();
        AccessRule ac1b = new AccessRule();

        assertTrue(ac1a.compareTo(ac1b) == 0);

        Path path2 = new Path();

        AccessRule ac2a = new AccessRule(path2, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        AccessRule ac2b = new AccessRule(path2, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(ac2a.compareTo(ac2b) == 0);

        Path path3 = new Path();
        Group group = new Group(groupName);

        AccessRule ac3a = new AccessRule(path3, group, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        AccessRule ac3b = new AccessRule(path3, group, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(ac3a.compareTo(ac3b) == 0);

        Path path4 = new Path();
        User user = new User(userName);

        AccessRule ac4a = new AccessRule(path4, user, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        AccessRule ac4b = new AccessRule(path4, user, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(ac4a.compareTo(ac4b) == 0);
    }

    @Test
    public void testEquals() {
        AccessRule ac1a = new AccessRule();
        AccessRule ac1b = new AccessRule();

        assertTrue(ac1a.equals(ac1b));

        Path path2 = new Path();

        AccessRule ac2a = new AccessRule(path2, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        AccessRule ac2b = new AccessRule(path2, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(ac2a.equals(ac2b));

        Path path3 = new Path();
        Group group = new Group(groupName);

        AccessRule ac3a = new AccessRule(path3, group, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        AccessRule ac3b = new AccessRule(path3, group, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(ac3a.equals(ac3b));

        Path path4 = new Path();
        User user = new User(userName);

        AccessRule ac4a = new AccessRule(path4, user, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        AccessRule ac4b = new AccessRule(path4, user, SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(ac4a.equals(ac4b));
    }

    @Test
    public void testGetLevelFullName() {
        AccessRule accessRule = new AccessRule();

        assertTrue(accessRule.getLevelFullName() != null);
        assertTrue(accessRule.getLevelFullName().length() > 0);

        try {
            accessRule.setLevel(SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS);
        }
        catch (AppException e) {
            fail("Unexpected exception");
        }

        assertTrue(accessRule.getLevelFullName() != null);
        assertTrue(accessRule.getLevelFullName().length() > 0);

        try {
            accessRule.setLevel(SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        }
        catch (AppException e) {
            fail("Unexpected exception");
        }

        assertTrue(accessRule.getLevelFullName() != null);
        assertTrue(accessRule.getLevelFullName().length() > 0);

        try {
            accessRule.setLevel(SubversionConstants.SVN_ACCESS_LEVEL_READWRITE);
        }
        catch (AppException e) {
            fail("Unexpected exception");
        }

        assertTrue(accessRule.getLevelFullName() != null);
        assertTrue(accessRule.getLevelFullName().length() > 0);
    }

    @Test
    public void testGetSetGroup() {
        AccessRule accessRule = new AccessRule();

        assertTrue(accessRule.getGroup() == null);

        Group group = new Group();

        accessRule.setGroup(group);

        assertTrue(accessRule.getGroup() != null);
        assertTrue(accessRule.getGroup() == group);
        assertTrue(accessRule.getGroup().equals(group));

        accessRule.setGroup(null);

        assertTrue(accessRule.getGroup() == null);
    }

    @Test
    public void testGetSetLevel() {
        AccessRule accessRule = new AccessRule();

        assertTrue(accessRule.getLevel() == null);

        try {
            accessRule.setLevel(SubversionConstants.SVN_ACCESS_LEVEL_READONLY);
        }
        catch (AppException e) {
            fail("Unexpected exception");
        }

        assertTrue(accessRule.getLevel() != null);
        assertTrue(accessRule.getLevel().equals(SubversionConstants.SVN_ACCESS_LEVEL_READONLY));

        accessRule = new AccessRule(new Path(), SubversionConstants.SVN_ACCESS_LEVEL_READONLY);

        assertTrue(accessRule.getLevel() != null);
        assertTrue(accessRule.getLevel().equals(SubversionConstants.SVN_ACCESS_LEVEL_READONLY));

        try {
            accessRule.setLevel(null);
            fail("Unexpected failure to throw exception");
        }
        catch (AppException e) {
            // Expected exception
        }

        try {
            accessRule.setLevel("shouldn't work");
            fail("Unexpected failure to throw exception");
        }
        catch (AppException e) {
            // Expected exception
        }
    }

    @Test
    public void testGetSetPath() {
        AccessRule accessRule = new AccessRule();

        assertTrue(accessRule.getPath() == null);

        Path path = new Path();

        accessRule.setPath(path);

        assertTrue(accessRule.getPath() != null);
        assertTrue(accessRule.getPath() == path);
        assertTrue(accessRule.getPath().equals(path));

        accessRule.setPath(null);

        assertTrue(accessRule.getPath() == null);
    }

    @Test
    public void testGetSetUser() {
        AccessRule accessRule = new AccessRule();

        assertTrue(accessRule.getGroup() == null);

        User user = new User();

        accessRule.setUser(user);

        assertTrue(accessRule.getUser() != null);
        assertTrue(accessRule.getUser() == user);
        assertTrue(accessRule.getUser().equals(user));

        accessRule.setUser(null);

        assertTrue(accessRule.getUser() == null);
    }

    /*
     * Class under test for String toString()
     */
    @Test
    public void testToString() {
        AccessRule accessRule = new AccessRule();

        assertTrue(accessRule.toString() != null);
    }
}
