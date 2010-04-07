package org.suafe.core;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authz path object.
 * 
 * @since 2.0
 */
public class AuthzPath implements Serializable, Comparable<AuthzPath> {
	private static final long serialVersionUID = 9125579229041836584L;

	private final Logger logger = LoggerFactory.getLogger(AuthzPath.class);

	private final String path;

	private final AuthzRepository repository;

	/**
	 * Constructor
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
	 * Compares this object with the provided AuthzPath object
	 * 
	 * @param authzUser AuthzPath to compare
	 */
	@Override
	public int compareTo(final AuthzPath authzPath) {
		final String myName = StringUtils.trimToEmpty(path);
		final String otherName = StringUtils.trimToEmpty(authzPath.getPath());

		return myName.compareTo(otherName);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AuthzPath other = (AuthzPath) obj;
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
	 * Gets the path
	 * 
	 * @return Path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the repository
	 * 
	 * @return Repository
	 */
	public AuthzRepository getRepository() {
		return repository;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((repository == null) ? 0 : repository.hashCode());
		return result;
	}
}
