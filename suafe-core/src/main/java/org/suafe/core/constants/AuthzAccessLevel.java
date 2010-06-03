package org.suafe.core.constants;

/**
 * Typesafe enumeration access level constants.
 * 
 * @since 2.0
 */
public final class AuthzAccessLevel implements AuthzAccessLevelIF {
    public static final AuthzAccessLevelIF DENY_ACCESS = new AuthzAccessLevel(
            "");

    public static final AuthzAccessLevelIF READ_ONLY = new AuthzAccessLevel("r");

    public static final AuthzAccessLevelIF READ_WRITE = new AuthzAccessLevel(
            "rw");

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
