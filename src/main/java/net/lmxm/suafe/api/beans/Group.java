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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trimToNull;

/**
 * Represents a single Subversion user Group.
 * Groups are persisted regardless of whether they contain users or
 * are referenced by an AccessRule.
 *
 * @author Shaun Johnson
 */
public final class Group extends GroupMemberObject implements Comparable<Group> {
    /**
     * Name of the group.
     */
    protected String name;

    /**
     * List of Groups in which this Group is a member.
     */
    protected List<Group> groups = new ArrayList<>();

    /**
     * List of Users that are a member of the Group.
     */
    private List<User> userMembers = new ArrayList<>();

    /**
     * List of Groups that are a member of the Group.
     */
    private List<Group> groupMembers = new ArrayList<>();

    /**
     * List of AccessRules that reference the Group.
     */
    private List<AccessRule> accessRules = new ArrayList<>();

    /**
     * Default Constructor.
     */
    public Group() {

    }

    /**
     * Constructor that accepts the group name.
     *
     * @param name Name of the group.
     */
    public Group(final String name) {
        setName(name);
    }

    /**
     * Constructor that accepts the group name and list of User members.
     *
     * @param name         Name of the group.
     * @param groupMembers List of Groups that are a member of the Group.
     * @param userMembers  List of Users that are a member of the Group.
     */
    public Group(final String name, final List<Group> groupMembers, final List<User> userMembers) {
        setName(name);
        this.userMembers = userMembers;
        this.groupMembers = groupMembers;
    }

    /**
     * Gets the name of the Group.
     *
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Group.
     *
     * @param name The new name for the Group.
     */
    public void setName(final String name) {
        this.name = trimToNull(name);
    }

    /**
     * Gets a list of the groups to which the Group is assigned.
     *
     * @return list of Groups to which the Group is assigned.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Adds a Group to the Group's list of assigned Groups.
     *
     * @param group Group to which the Group is assigned.
     */
    public void addGroup(final Group group) {
        groups.add(group);
    }

    /**
     * Removes a Group from the Group's list of assigned Groups.
     *
     * @param group Group to be removed.
     */
    public void removeGroup(final Group group) {
        groups.remove(group);
    }

    /**
     * Gets the list of Groups that are a member of the Group.
     *
     * @return Returns the list of Group members.
     */
    public List<Group> getGroupMembers() {
        return groupMembers;
    }

    /**
     * Adds a new Group to the list of Group members.
     *
     * @param group Group to be added to the list of Group members.
     */
    public void addGroupMember(final Group group) {
        this.groupMembers.add(group);
    }

    /**
     * Removes a Group from the list of Group members.
     *
     * @param group Group to be removed.
     */
    public void removeGroupMember(final Group group) {
        this.groupMembers.remove(group);
    }

    /**
     * Gets the list of Users that are a member of the Group.
     *
     * @return Returns the list of User members..
     */
    public List<User> getUserMembers() {
        return userMembers;
    }

    /**
     * Adds a new User to the list of User members.
     *
     * @param user User to be added to the list of User members.
     */
    public void addUserMember(final User user) {
        this.userMembers.add(user);
    }

    /**
     * Removes a User from the list of User members.
     *
     * @param user User to be removed.
     */
    public void removeUserMember(final User user) {
        this.userMembers.remove(user);
    }

    /**
     * Gets the list of AccessRules that reference the Group.
     *
     * @return Returns the list of AccessRules.
     */
    public List<AccessRule> getAccessRules() {
        return accessRules;
    }

    /**
     * Adds a new AccessRule to the list of AccessRules that reference the
     * Group.
     *
     * @param accessRule The AccessRule to add.
     */
    public void addAccessRule(final AccessRule accessRule) {
        accessRules.add(accessRule);
    }

    /**
     * Removes an AccessRule from the list of AccessRules that reference the
     * Group.
     *
     * @param accessRule The AccessRule to remove.
     */
    public void removeAccessRule(final AccessRule accessRule) {
        accessRules.remove(accessRule);
    }

    /**
     * Compares this object to another.
     *
     * @param otherGroup The other Group to use for comparison.
     */
    @Override
    public int compareTo(final Group otherGroup) {
        return this.toString().compareTo(otherGroup.toString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns the Group object as a String.
     */
    @Override
    public String toString() {
        return (name == null) ? "" : name;
    }
}
