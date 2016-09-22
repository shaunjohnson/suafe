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
package org.xiaoniu.suafe.api.beans;

import org.xiaoniu.suafe.api.SubversionConstants;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;

/**
 * Represents a single Subversion AccessRule.
 * An AccessRule consists of a Path (repository and relative path),
 * a User or Group, and the level of access. AccessRules are always
 * persisted.
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
        super();

        this.path = null;
        this.group = null;
        this.user = null;
        this.level = null;
    }

    /**
     * Constructor that accepts the Path and level of access.
     *
     * @param path  The Path to be set.
     * @param level The level of access to be set.
     */
    public AccessRule(final Path path, final String level) {
        super();

        this.path = path;
        this.group = null;
        this.user = null;
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
        super();

        this.path = path;
        this.group = group;
        this.user = null;
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
        super();

        this.path = path;
        this.group = null;
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

        if ( (group != null & that.group == null) | (group == null & that.group != null) ) {
        	return false;
        }
        if (group != null ? !group.equals(that.group) : that.group != null) {
            return false;
        }
        if ( (level != null & that.level == null) | (level == null & that.level != null) ) {
        	return false;
        }
        if (level != null ? !level.equals(that.level) : that.level != null) {
            return false;
        }
        if ( (path != null & that.path == null) | (path == null & that.path != null) ) {
        	return false;
        }
        if (path != null ? !path.equals(that.path) : that.path != null) {
            return false;
        }
        if ( (user != null & that.user == null) | (user == null & that.user != null) ) {
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
