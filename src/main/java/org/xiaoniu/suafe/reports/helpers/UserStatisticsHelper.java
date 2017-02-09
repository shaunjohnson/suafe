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

package org.xiaoniu.suafe.reports.helpers;

import org.xiaoniu.suafe.api.beans.User;

import javax.annotation.Nonnull;
import java.util.List;

public final class UserStatisticsHelper {
    private final List<User> users;

    private double avgAccessRules = -1;

    private double avgGroups = -1;

    private int count = -1;

    private int maxAccessRules = -1;

    private int maxGroups = -1;

    private int minAccessRules = -1;

    private int minGroups = -1;

    public UserStatisticsHelper(@Nonnull final List<User> users) {
        this.users = users;
    }

    public double getAvgAccessRules() {
        if (avgAccessRules == -1) {
            avgAccessRules = 0;

            for (User user : users) {
                int size = user.getAccessRules().size();

                avgAccessRules += size;
            }

            avgAccessRules /= getCount();
        }

        return avgAccessRules;
    }

    public double getAvgGroups() {
        if (avgGroups == -1) {
            avgGroups = 0;

            for (User user : users) {
                int size = user.getGroups().size();

                avgGroups += size;
            }

            avgGroups /= getCount();
        }

        return avgGroups;
    }

    public int getCount() {
        if (count == -1) {
            count = users.size();
        }

        return count;
    }

    public int getMaxAccessRules() {
        if (maxAccessRules == -1) {
            maxAccessRules = 0;

            for (User user : users) {
                int size = user.getAccessRules().size();

                maxAccessRules = (size > maxAccessRules) ? size : maxAccessRules;
            }
        }

        return maxAccessRules;
    }

    public int getMaxGroups() {
        if (maxGroups == -1) {
            maxGroups = 0;

            for (User user : users) {
                int size = user.getGroups().size();

                maxGroups = (size > maxGroups) ? size : maxGroups;
            }
        }

        return maxGroups;
    }

    public int getMinAccessRules() {
        if (minAccessRules == -1) {
            minAccessRules = Integer.MAX_VALUE;

            for (User user : users) {
                int size = user.getAccessRules().size();

                minAccessRules = (size < minAccessRules) ? size : minAccessRules;
            }
        }

        return minAccessRules;
    }

    public int getMinGroups() {
        if (minGroups == -1) {
            minGroups = Integer.MAX_VALUE;

            for (User user : users) {
                int size = user.getGroups().size();

                minGroups = (size < minGroups) ? size : minGroups;
            }
        }

        return minGroups;
    }
}
