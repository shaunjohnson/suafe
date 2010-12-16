package org.suafe.core;

import java.io.Serializable;

public interface AuthzRepositoryIF extends Comparable<AuthzRepositoryIF>, Serializable {

	/**
	 * Gets the name.
	 * 
	 * @return The name.
	 */
	String getName();
}
