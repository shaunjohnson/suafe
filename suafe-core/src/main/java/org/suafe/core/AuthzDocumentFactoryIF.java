package org.suafe.core;

import java.io.Serializable;


public interface AuthzDocumentFactoryIF extends Serializable {

	/**
	 * Creates the.
	 * 
	 * @return the authz document if
	 */
	public abstract AuthzDocumentIF create();

}
