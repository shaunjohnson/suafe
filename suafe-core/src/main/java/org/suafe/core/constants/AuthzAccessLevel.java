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
package org.suafe.core.constants;

/**
 * Typesafe enumeration access level constants.
 * 
 * @since 2.0
 */
public final class AuthzAccessLevel implements AuthzAccessLevelIF {

    /** The Constant DENY_ACCESS. */
    public static final AuthzAccessLevelIF DENY_ACCESS = new AuthzAccessLevel(
            "");

    /** The Constant READ_ONLY. */
    public static final AuthzAccessLevelIF READ_ONLY = new AuthzAccessLevel("r");

    /** The Constant READ_WRITE. */
    public static final AuthzAccessLevelIF READ_WRITE = new AuthzAccessLevel(
            "rw");

    /** The access level. */
    private final String accessLevel;

    /**
     * Private constructor used to create static typesafe access level values.
     * 
     * @param accessLevel Access level value
     */
    private AuthzAccessLevel(final String accessLevel) {
        super();

        this.accessLevel = accessLevel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final AuthzAccessLevelIF otherAccessLevel) {
        return accessLevel.compareTo(otherAccessLevel.toString());
    }

    /**
     * Compares this object with the provided AuthzAccessLevel object for
     * equality.
     * 
     * @param object Object to compare
     * @return True if this object matches the provided object, otherwise false
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final AuthzAccessLevel other = (AuthzAccessLevel) object;
        if (accessLevel == null) {
            if (other.accessLevel != null) {
                return false;
            }
        }
        else if (!accessLevel.equals(other.accessLevel)) {
            return false;
        }
        return true;
    }

    /**
     * Calculates hashCode value of this access level.
     * 
     * @return Hashcode of this object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accessLevel == null) ? 0 : accessLevel.hashCode());
        return result;
    }

    /**
     * Creates a string representation of this access level.
     * 
     * @return String representation of this access level
     */
    @Override
    public String toString() {
        return accessLevel;
    }
}
