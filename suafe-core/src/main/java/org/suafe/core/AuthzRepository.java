package org.suafe.core;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Authz repository object
 * 
 * @since 2.0
 */
public class AuthzRepository implements Serializable, Comparable<AuthzRepository> {
	private static final long serialVersionUID = 1252145167842473309L;

	private final String name;

	/**
	 * Constructor
	 * 
	 * @param name User name
	 */
	public AuthzRepository(final String name) {
		super();

		this.name = name;
	}

	@Override
	public int compareTo(final AuthzRepository authzRepository) {
		final String myName = StringUtils.trimToEmpty(name);
		final String otherName = StringUtils.trimToEmpty(authzRepository.getName());

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
		final AuthzRepository other = (AuthzRepository) obj;
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

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("name", name);

		return toStringBuilder.toString();
	}
}
