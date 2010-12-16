package org.suafe.core;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Authz user object.
 * 
 * @since 2.0
 */
public final class AuthzUser extends AuthzGroupMember {
    /** Serialization ID. */
    private static final long serialVersionUID = 7672296029756141807L;

    /** Alias of this user. */
    private final String alias;

    /**
     * Constructor.
     * 
     * @param name User name
     * @param alias User alias
     */
    protected AuthzUser(final String name, final String alias) {
        super(name);

        this.alias = alias;
    }

    /**
     * Compares this object with the provided AuthzUser object for equality.
     * 
     * @param object Object to compare
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
        final AuthzUser other = (AuthzUser) object;
        if (alias == null) {
            if (other.alias != null) {
                return false;
            }
        }
        else if (!alias.equals(other.alias)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the user alias.
     * 
     * @return User alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Calculates hashCode value of this user.
     * 
     * @return Hashcode of this object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * Creates a string representation of this user.
     * 
     * @return String representation of this user
     */
    @Override
    public String toString() {
        final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

        toStringBuilder.append("name", name).append("alias", alias);

        return toStringBuilder.toString();
    }
}
