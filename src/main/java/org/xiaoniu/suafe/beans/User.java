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
package org.xiaoniu.suafe.beans;

import org.xiaoniu.suafe.SubversionConstants;
import org.xiaoniu.suafe.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    protected List<AccessRule> accessRules;

    /**
     * The User's alias. This field must be unique.
     */
    protected String alias;

    /**
     * List of Groups to which the User is assigned.
     */
    protected List<Group> groups;

    /**
     * The User's name. This field must contain a unique value.
     */
    protected String name;

    /**
     * Default constructor.
     */
    public User() {
        super();

        this.alias = null;
        this.name = null;
        this.groups = new ArrayList<Group>();
        this.accessRules = new ArrayList<AccessRule>();
    }

    /**
     * Constructor that accepts User name.
     *
     * @param name Name of the user.
     */
    public User(String name) {
        super();

        setName(name);
        setGroups(new ArrayList<Group>());
        setAccessRules(new ArrayList<AccessRule>());
    }

    /**
     * Constructor that accepts User name and alias.
     *
     * @param name Name of the user.
     */
    public User(String name, String alias) {
        super();

        setAlias(alias);
        setName(name);
        setGroups(new ArrayList<Group>());
        setAccessRules(new ArrayList<AccessRule>());
    }

    /**
     * Adds an AccessRule to the User's list of referenced AccessRules.
     *
     * @param accessRule AccessRule in which the User is referenced.
     */
    public void addAccessRule(AccessRule accessRule) {
        accessRules.add(accessRule);
    }

    /**
     * Adds a Group to the User's list of assigned Groups.
     *
     * @param group Group to which the User is assigned.
     */
    public void addGroup(Group group) {
        groups.add(group);
//		group.addUserMember(this);
    }

    /**
     * Compares this to another object.
     *
     * @param otherUser The other User to which this is compared.
     */
    public int compareTo(User otherUser) {
        return this.toString().compareTo(otherUser.toString());
    }

    /**
     * Compares this to another object.
     *
     * @param otherUser The other User to which this is compared.
     */
    public boolean equals(User otherUser) {
        return name == otherUser.getName();
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
        return name == SubversionConstants.SVN_ALL_USERS_NAME;
    }

    /**
     * Removes an AccessRule from the User's list of AccessRules in which he is referenced.
     *
     * @param accessRule AccessRule which to remove from the list.
     */
    public void removeAccessRule(AccessRule accessRule) {
        accessRules.remove(accessRule);
    }

    /**
     * Removes a Group from the User's list of assigned Groups.
     *
     * @param group Group which to remove from the list.
     */
    public void removeGroup(Group group) {
        groups.remove(group);
    }

    /**
     * Sets the list of AccessRules in which the User is referenced.
     *
     * @param accessRules New list of AccessRules in which the user is referenced.
     */
    public void setAccessRules(List<AccessRule> accessRules) {
        this.accessRules = accessRules;
    }

    /**
     * @param alias the alias to set
     */
    public final void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Sets the list of groups to which the User is assigned.
     *
     * @param groups New list of groups to which the user is assigned.
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    /**
     * Sets the name of the User.
     *
     * @param name The User's new name.
     */
    public void setName(String name) {
        this.name = (name == null) ? null : name.trim().intern();
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
