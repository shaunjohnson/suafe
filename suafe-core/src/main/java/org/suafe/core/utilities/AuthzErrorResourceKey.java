package org.suafe.core.utilities;

/**
 * Typesafe enumeration resource key constants for error messages.
 * 
 * @since 2.0
 */
public final class AuthzErrorResourceKey implements AuthzResourceKeyIF {
	public static final AuthzResourceKeyIF ALREADY_MEMBER_OF_GROUP = new AuthzErrorResourceKey(
			"errorAlreadyMemberOfGroup");

	public static final AuthzErrorResourceKey GROUP_ALREADY_EXISTS = new AuthzErrorResourceKey(
			"errorGroupAlreadyExists");

	public static final AuthzResourceKeyIF GROUP_MEMBER_ALREADY_EXISTS = new AuthzErrorResourceKey(
			"errorGroupMemberAlreadyExists");

	public static final AuthzErrorResourceKey INVALID_GROUP_NAME = new AuthzErrorResourceKey("errorInvalidGroupName");

	public static final AuthzResourceKeyIF INVALID_REPOSITORY_NAME = new AuthzErrorResourceKey(
			"errorInvalidRepositoryName");

	public static AuthzErrorResourceKey INVALID_USER_ALIAS = new AuthzErrorResourceKey("errorInvalidUserAlias");

	public static final AuthzErrorResourceKey INVALID_USER_NAME = new AuthzErrorResourceKey("errorInvalidUserName");

	public static final AuthzResourceKeyIF NOT_GROUP_MEMBER = new AuthzErrorResourceKey("errorNotGroupMember");

	public static final AuthzResourceKeyIF NOT_MEMBER_OF_GROUP = new AuthzErrorResourceKey("errorNotMemberOfGroup");

	public static final AuthzResourceKeyIF REPOSITORY_ALREADY_EXISTS = new AuthzErrorResourceKey(
			"errorRepositoryAlreadyExists");

	public static final AuthzErrorResourceKey USER_ALIAS_ALREADY_EXISTS = new AuthzErrorResourceKey(
			"errorUserAliasAlreadyExists");

	public static final AuthzErrorResourceKey USER_ALREADY_EXISTS = new AuthzErrorResourceKey("errorUserAlreadyExists");

	private final String key;

	/**
	 * Private constructor used to create static typesafe enumeration values.
	 * 
	 * @param key
	 */
	private AuthzErrorResourceKey(final String key) {
		super();

		this.key = key;
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
		final AuthzErrorResourceKey other = (AuthzErrorResourceKey) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		}
		else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return key;
	}
}
