package org.suafe.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Authz user object.
 * 
 * @since 2.0
 */
public class AuthzUser extends AuthzGroupMember implements Comparable<AuthzUser> {
	private static final long serialVersionUID = 7672296029756141807L;

	private final String alias;

	private final String name;

	/**
	 * Constructor
	 * 
	 * @param name User name
	 * @param alias User alias
	 */
	public AuthzUser(final String name, final String alias) {
		super();

		this.name = name;
		this.alias = alias;
	}

	@Override
	public int compareTo(final AuthzUser authzUser) {
		final String myName = StringUtils.trimToEmpty(name) + StringUtils.trimToEmpty(alias);
		final String otherName = StringUtils.trimToEmpty(authzUser.getName())
				+ StringUtils.trimToEmpty(authzUser.getAlias());

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
		final AuthzUser other = (AuthzUser) obj;
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

	public String getAlias() {
		return alias;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("name", name).append("alias", alias);

		return toStringBuilder.toString();
	}
}
