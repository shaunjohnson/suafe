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
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trimToNull;

/**
 * Represents a single Subversion user object. User objects are not persisted unless they are a member of a group or
 * specifically granted access to a path.
 *
 * @author Shaun Johnson
 */
public final class User extends GroupMemberObject implements Comparable<User> {
    /**
     * List of AccessRules in which the User is referenced.
     */
    private List<AccessRule> accessRules = new ArrayList<>();

    /**
     * The User's alias. This field must be unique.
     */
    private String alias;

    /**
     * List of Groups to which the User is assigned.
     */
    protected List<Group> groups = new ArrayList<>();

    /**
     * The User's name. This field must contain a unique value.
     */
    protected String name;

    /**
     * Default constructor.
     */
    public User() {

    }

    /**
     * Constructor that accepts User name.
     *
     * @param name Name of the user.
     */
    public User(final String name) {
        setName(name);
    }

    /**
     * Constructor that accepts User name and alias.
     *
     * @param name Name of the user.
     */
    public User(final String name, final String alias) {
        setName(name);
        setAlias(alias);
    }

    /**
     * Adds an AccessRule to the User's list of referenced AccessRules.
     *
     * @param accessRule AccessRule in which the User is referenced.
     */
    public void addAccessRule(final AccessRule accessRule) {
        accessRules.add(accessRule);
    }

    /**
     * Adds a Group to the User's list of assigned Groups.
     *
     * @param group Group to which the User is assigned.
     */
    public void addGroup(final Group group) {
        groups.add(group);
    }

    /**
     * Gets the list of AccessRules in which the User is referenced.
     *
     * @return Returns the AccessRules in which the User is referenced.
     */
    public List<AccessRule> getAccessRules() {
        return accessRules;
    }

    /**
     * @return the alias
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * Gets a list of the groups to which the User is assigned.
     *
     * @return list of Groups to which the User is assigned.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Gets the name of the User.
     *
     * @return Returns name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if this user is "*" or All Subversion users.
     *
     * @return Returns true if users is "*" (All SVN Users)
     */
    public boolean isAllUsers() {
        return SubversionConstants.SVN_ALL_USERS_NAME.equals(name);
    }

    /**
     * Removes an AccessRule from the User's list of AccessRules in which he is referenced.
     *
     * @param accessRule AccessRule which to remove from the list.
     */
    public void removeAccessRule(final AccessRule accessRule) {
        accessRules.remove(accessRule);
    }

    /**
     * Removes a Group from the User's list of assigned Groups.
     *
     * @param group Group which to remove from the list.
     */
    public void removeGroup(final Group group) {
        groups.remove(group);
    }

    /**
     * Sets the list of AccessRules in which the User is referenced.
     *
     * @param accessRules New list of AccessRules in which the user is referenced.
     */
    public void setAccessRules(final List<AccessRule> accessRules) {
        this.accessRules = accessRules;
    }

    /**
     * @param alias the alias to set
     */
    public final void setAlias(final String alias) {
        this.alias = trimToNull(alias);
    }

    /**
     * Sets the list of groups to which the User is assigned.
     *
     * @param groups New list of groups to which the user is assigned.
     */
    public void setGroups(final List<Group> groups) {
        this.groups = groups;
    }

    /**
     * Sets the name of the User.
     *
     * @param name The User's new name.
     */
    public void setName(final String name) {
        this.name = trimToNull(name);
    }

    /**
     * Compares this to another object.
     *
     * @param otherUser The other User to which this is compared.
     */
    @Override
    public int compareTo(final User otherUser) {
        return this.toString().compareTo(otherUser.toString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns the User object as a String.
     */
    @Override
    public String toString() {
        if (StringUtils.isBlank(alias) && StringUtils.isBlank(name)) {
            return "";
        }
        else if (StringUtils.isBlank(alias) && StringUtils.isNotBlank(name)) {
            return name;
        }
        else if (StringUtils.isNotBlank(alias) && StringUtils.isBlank(name)) {
            return alias;
        }
        else {
            return alias + "[" + name + "]";
        }
    }
}
