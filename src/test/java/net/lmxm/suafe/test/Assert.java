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

package net.lmxm.suafe.test;

import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.User;

import javax.annotation.Nonnull;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Custom assertions for unit testing purposes.
 */
public final class Assert {
    public static void assertGroupsExist(@Nonnull final List<Group> groups, @Nonnull final String... groupNames) {
        assertThat("Groups should not be null", groups, is(notNullValue()));

        final int groupCount = groupNames.length;
        assertThat("Group count should be " + groupCount + ", but is " + groups.size(), groups.size(), is(groupCount));

        for (int i = 0; i < groupCount; i++) {
            final String expectedGroupName = groupNames[i];
            final String actualGroupName = groups.get(i).getName();

            assertThat("Group at index " + i + " expected to be " + expectedGroupName + ", but was " + actualGroupName,
                    actualGroupName, is(expectedGroupName));
        }
    }

    public static void assertUsersExist(@Nonnull final List<User> users, @Nonnull final String... userNames) {
        assertThat("Users should not be null", users, is(notNullValue()));

        final int groupCount = userNames.length;
        assertThat("User count should be " + groupCount + ", but is " + users.size(), users.size(), is(groupCount));

        for (int i = 0; i < groupCount; i++) {
            final String expectedUserName = userNames[i];
            final String actualUserName = users.get(i).getName();

            assertThat("User at index " + i + " expected to be " + expectedUserName + ", but was " + actualUserName,
                    actualUserName, is(expectedUserName));
        }
    }

    private Assert() {
        // Deliberately left blank
    }
}
