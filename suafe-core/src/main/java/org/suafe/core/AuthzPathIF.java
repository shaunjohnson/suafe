package org.suafe.core;

import java.io.Serializable;

public interface AuthzPathIF extends Comparable<AuthzPathIF>, Serializable {

	/**
	 * Gets the path.
	 * 
	 * @return Path
	 */
	String getPath();

	/**
	 * Gets the repository.
	 * 
	 * @return Repository
	 */
	AuthzRepositoryIF getRepository();
}
