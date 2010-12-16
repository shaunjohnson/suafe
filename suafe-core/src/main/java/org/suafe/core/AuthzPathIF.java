package org.suafe.core;


public interface AuthzPathIF extends Comparable<AuthzPathIF> {

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
