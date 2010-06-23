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
package org.suafe.core;

import org.suafe.core.constants.AuthzAccessLevelIF;

import com.google.common.collect.ComparisonChain;

/**
 * Authz access rule object.
 * 
 * @since 2.0
 */
public class AuthzAccessRule implements Comparable<AuthzAccessRule> {

    /** The access level. */
    private final AuthzAccessLevelIF accessLevel;

    /** The group. */
    private final AuthzGroup group;

    /** The path. */
    private final AuthzPath path;

    /** The user. */
    private final AuthzUser user;

    /**
     * Instantiates a new authz access rule.
     * 
     * @param path the path
     * @param group the group
     * @param accessLevel the access level
     */
    public AuthzAccessRule(final AuthzPath path, final AuthzGroup group,
            final AuthzAccessLevelIF accessLevel) {
        super();

        if (path == null || group == null || accessLevel == null) {
            throw new NullPointerException();
        }

        this.path = path;
        this.group = group;
        this.user = null;
        this.accessLevel = accessLevel;
    }

    /**
     * Instantiates a new authz access rule.
     * 
     * @param path the path
     * @param user the user
     * @param accessLevel the access level
     */
    public AuthzAccessRule(final AuthzPath path, final AuthzUser user,
            final AuthzAccessLevelIF accessLevel) {
        super();

        if (path == null || user == null || accessLevel == null) {
            throw new NullPointerException();
        }

        this.path = path;
        this.group = null;
        this.user = user;
        this.accessLevel = accessLevel;
    }

    /**
     * Compares this object with the provided AuthzAccessRule object.
     * 
     * @param authzAccessRule AuthzAccessRule to compare
     * @return Returns 0 if rules are equal, less than 0 if this rule is less
     *         than the other or greater than 0 if this rule is greater
     */
    @Override
    public int compareTo(final AuthzAccessRule authzAccessRule) {
        final ComparisonChain comparisonChain = ComparisonChain.start();

        comparisonChain.compare(this.path, authzAccessRule.path);
        comparisonChain.compare(this.group, authzAccessRule.group);
        comparisonChain.compare(this.user, authzAccessRule.user);
        comparisonChain.compare(this.accessLevel, authzAccessRule.accessLevel);

        return comparisonChain.result();

        /*
         * final AuthzPath otherPath = authzAccessRule.getPath(); final int
         * comparePath = path.compareTo(otherPath); if (comparePath != 0) {
         * return comparePath; }
         * 
         * final AuthzGroup otherGroup = authzAccessRule.getGroup(); if
         * (otherGroup != null) { final int compareGroup =
         * group.compareTo(otherGroup); if (compareGroup != 0) { return
         * compareGroup; } }
         * 
         * final AuthzUser otherUser = authzAccessRule.getUser(); if (otherUser
         * != null) { final int compareUser = user.compareTo(otherUser); if
         * (compareUser != 0) { return compareUser; } }
         * 
         * final AuthzAccessLevelIF otherAccessLevel = authzAccessRule
         * .getAccessLevel();
         * 
         * return accessLevel.compareTo(otherAccessLevel);
         */
    }

    /**
     * Gets the access level.
     * 
     * @return the access level
     */
    public AuthzAccessLevelIF getAccessLevel() {
        return accessLevel;
    }

    /**
     * Gets the group.
     * 
     * @return the group
     */
    public AuthzGroup getGroup() {
        return group;
    }

    /**
     * Gets the path.
     * 
     * @return the path
     */
    public AuthzPath getPath() {
        return path;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public AuthzUser getUser() {
        return user;
    }
}
