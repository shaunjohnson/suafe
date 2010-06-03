package org.suafe.core;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Authz repository object.
 * 
 * @since 2.0
 */
public final class AuthzRepository implements Serializable,
        Comparable<AuthzRepository> {
    /** Serialization ID. */
    private static final long serialVersionUID = 1252145167842473309L;

    /** Name of this repository. */
    private final String name;

    /**
     * Constructor.
     * 
     * @param name User name
     */
    public AuthzRepository(final String name) {
        super();

        this.name = name;
    }

    /**
     * Compares this object with the provided AuthzRepository object.
     * 
     * @param authzRepository AuthzRepository to compare
     * @return Returns 0 if repositories are equal, less than 0 if this
     *         repository is less than the other or greater than 0 if this
     *         repository is greater
     */
    @Override
    public int compareTo(final AuthzRepository authzRepository) {
        if (this == authzRepository) {
            return 0;
        }

        final String myName = StringUtils.trimToEmpty(name);
        final String otherName = StringUtils.trimToEmpty(authzRepository
                .getName());

        return myName.compareTo(otherName);
    }

    /**
     * Compares this object with the provided AuthzRepository object for
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
        final AuthzRepository other = (AuthzRepository) object;
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
     * Gets the name.
     * 
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Calculates hashCode value of this repository.
     * 
     * @return Hashcode of this object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * Creates a string representation of this repository.
     * 
     * @return String representation of this repository
     */
    @Override
    public String toString() {
        final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

        toStringBuilder.append("name", name);

        return toStringBuilder.toString();
    }
}
