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

import net.lmxm.suafe.api.SubversionConstants;
import net.lmxm.suafe.exceptions.AppException;
import net.lmxm.suafe.resources.ResourceUtil;
import net.lmxm.suafe.validators.Validator;

/**
 * Represents a single Subversion AccessRule.
 * An AccessRule consists of a Path (repository and relative path), a User or Group, and the level of access.
 * AccessRules are always persisted.
 *
 * @author Shaun Johsnon
 */
public final class AccessRule implements Comparable<AccessRule> {
    /**
     * Path referenced by the AccessRule.
     */
    protected Path path;

    /**
     * Group that has the specified level of access in the Path.
     * Group is null if User is set.
     */
    protected Group group;

    /**
     * User that has the specified level of access in the Path.
     * User is null if Group is set.
     */
    protected User user;

    /**
     * Level of access that the User or Group has within the Path.
     */
    protected String level;

    /**
     * Default Constructor.
     */
    public AccessRule() {

    }

    /**
     * Constructor that accepts the Path and level of access.
     *
     * @param path  The Path to be set.
     * @param level The level of access to be set.
     */
    public AccessRule(final Path path, final String level) {
        this.path = path;
        this.level = level;
    }

    /**
     * Constructor that accepts the Path, Group and level of access.
     *
     * @param path  The Path to be set.
     * @param group The Group to be given access.
     * @param level The level of access to be set.
     */
    public AccessRule(final Path path, final Group group, final String level) {
        this.path = path;
        this.group = group;
        this.level = level;
    }

    /**
     * Constructor that accepts the Path, User and level of access.
     *
     * @param path  The Path to be set.
     * @param user  The user to be given access.
     * @param level The level of access to be set.
     */
    public AccessRule(final Path path, final User user, final String level) {
        this.path = path;
        this.user = user;
        this.level = level;
    }

    /**
     * Returns the AccessRule object as a String.
     */
    public String toString() {
        return
                ((path == null || path.getRepository() == null) ? "" : path.getRepository().toString()) + ":" +
                        ((path == null) ? "" : path.toString()) + ":" +
                        ((group == null) ? "" : group.toString()) + ":" +
                        ((user == null) ? "" : user.toString());
    }

    /**
     * Gets the Group referenced by the AccessRule.
     *
     * @return Returns the Group.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets the Group referenced by the AccessRule.
     *
     * @param group The Group to set.
     */
    public void setGroup(final Group group) {
        this.group = group;
    }

    /**
     * Gets the User referenced by the AccessRule.
     *
     * @return Returns the User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the User referenced by the AccessRule.
     *
     * @param user The User to set.
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Gets the Path referenced by the AccessRule.
     *
     * @return Returns the Path.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Sets the Path referenced by the AccessRule.
     *
     * @param path The Path to set.
     */
    public void setPath(final Path path) {
        this.path = path;
    }

    /**
     * Gets the level of access.
     *
     * @return Returns the level of access.
     */
    public String getLevel() {
        return level;
    }

    /**
     * Gets the full English form of the level of access.
     *
     * @return Returns the level of access in English.
     */
    public String getLevelFullName() {
        String name = null;

        if (level == null) {
            name = ResourceUtil.getString("application.unknown");
        }
        else if (level.equals(SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS)) {
            name = ResourceUtil.getString("accesslevel.denyaccess");
        }
        else if (level.equalsIgnoreCase(SubversionConstants.SVN_ACCESS_LEVEL_READONLY)) {
            name = ResourceUtil.getString("accesslevel.readonly");
        }
        else if (level.equalsIgnoreCase(SubversionConstants.SVN_ACCESS_LEVEL_READWRITE)) {
            name = ResourceUtil.getString("accesslevel.readwrite");
        }

        return name;
    }

    /**
     * Sets the level of access.
     *
     * @param level The level of access to set.
     * @throws AppException
     */
    public void setLevel(final String level) throws AppException {
        Validator.validateLevelOfAccess(level);

        this.level = level;
    }

    /**
     * Compares this object to another.
     *
     * @param otherAccessRule The other AccessRule to use for comparison.
     */
    public int compareTo(final AccessRule otherAccessRule) {
        return this.toString().compareTo(otherAccessRule.toString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AccessRule that = (AccessRule) o;

        if (group != null ? !group.equals(that.group) : that.group != null) {
            return false;
        }
        if (level != null ? !level.equals(that.level) : that.level != null) {
            return false;
        }
        if (path != null ? !path.equals(that.path) : that.path != null) {
            return false;
        }
        if (user != null ? !user.equals(that.user) : that.user != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }
}
