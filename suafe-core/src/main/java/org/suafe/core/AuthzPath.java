package org.suafe.core;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authz path object.
 * 
 * @since 2.0
 */
public final class AuthzPath implements Serializable, Comparable<AuthzPath> {
    /** Logger handle. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AuthzPath.class);

    /** Serialization ID. */
    private static final long serialVersionUID = 9125579229041836584L;

    /** Path string. */
    private final String path;

    /** Repository object. */
    private final AuthzRepository repository;

    /**
     * Constructor.
     * 
     * @param repository Repository
     * @param path Path
     */
    public AuthzPath(final AuthzRepository repository, final String path) {
        super();

        this.repository = repository;
        this.path = path;
    }

    /**
     * Compares this object with the provided AuthzPath object.
     * 
     * @param authzPath AuthzPath to compare
     * @return Returns 0 if paths are equal, less than 0 if this path is less
     *         than the other or greater than 0 if this path is greater
     */
    @Override
    public int compareTo(final AuthzPath authzPath) {
        final String myName = StringUtils.trimToEmpty(path);
        final String otherName = StringUtils.trimToEmpty(authzPath.getPath());

        return myName.compareTo(otherName);
    }

    /**
     * Compares this object with the provided AuthzPath object for equality.
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
        final AuthzPath other = (AuthzPath) object;
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        }
        else if (!path.equals(other.path)) {
            return false;
        }
        if (repository == null) {
            if (other.repository != null) {
                return false;
            }
        }
        else if (!repository.equals(other.repository)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the path.
     * 
     * @return Path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the repository.
     * 
     * @return Repository
     */
    public AuthzRepository getRepository() {
        return repository;
    }

    /**
     * Calculates hashCode value of this path.
     * 
     * @return Hashcode of this object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result
                + ((repository == null) ? 0 : repository.hashCode());
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

        toStringBuilder.append("repository", repository).append("path", path);

        return toStringBuilder.toString();
    }
}
