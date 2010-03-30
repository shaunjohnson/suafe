package org.suafe.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Authz group object.
 * 
 * @since 2.0
 */
public class AuthzGroup extends AuthzGroupMember implements Comparable<AuthzGroup> {
	private static final long serialVersionUID = 7033919638521713150L;

	private final String name;

	/**
	 * Constructor
	 * 
	 * @param name User name
	 * @param alias User alias
	 */
	public AuthzGroup(final String name) {
		super();

		this.name = name;
	}

	@Override
	public int compareTo(final AuthzGroup authzGroup) {
		final String myName = StringUtils.trimToEmpty(name);
		final String otherName = StringUtils.trimToEmpty(authzGroup.getName());

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
		final AuthzGroup other = (AuthzGroup) obj;
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
