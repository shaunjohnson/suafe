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
