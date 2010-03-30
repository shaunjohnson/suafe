package org.suafe.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.exceptions.AuthzGroupAlreadyExistsException;
import org.suafe.core.exceptions.AuthzInvalidGroupNameException;
import org.suafe.core.exceptions.AuthzInvalidUserAliasException;
import org.suafe.core.exceptions.AuthzInvalidUserNameException;
import org.suafe.core.exceptions.AuthzUserAliasAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAlreadyExistsException;

/**
 * Authz document file.
 */
public class AuthzDocument implements Serializable {
	private static final long serialVersionUID = -1396450094914018451L;

	private Vector<AuthzGroup> groups;

	private boolean hasUnsavedChanges;

	private final Logger logger = LoggerFactory.getLogger(AuthzDocument.class);

	private Vector<AuthzRepository> repositories;

	private Vector<AuthzUser> users;

	public AuthzDocument() {
		super();

		initialize();
	}

	/**
	 * Sets "has unsaved changes" flag to false.
	 */
	protected void clearHasUnsavedChanges() {
		logger.debug("clearHasUnsavedChanges() entered. hasUnsavedChanged={}", hasUnsavedChanges);

		hasUnsavedChanges = false;

		logger.debug("clearHasUnsavedChanges() exited. hasUnsavedChanged={}", hasUnsavedChanges);
	}

	/**
	 * Creates a new group.
	 * 
	 * @param name Name of user
	 * @return Newly created AuthzGroup object
	 * @throws AuthzInvalidGroupNameException If provided group name is invalid
	 * @throws AuthzGroupAlreadyExistsException If group with the provided group
	 *             name already exists
	 */
	public AuthzGroup createGroup(final String name) throws AuthzGroupAlreadyExistsException,
			AuthzInvalidGroupNameException {
		logger.debug("createGroup() entered. name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		// Validate group name
		if (!isValidGroupName(nameTrimmed)) {
			logger.error("createGroup() invalid group name");

			throw new AuthzInvalidGroupNameException();
		}

		if (doesGroupNameExist(nameTrimmed)) {
			logger.info("createGroup() group already exists");

			throw new AuthzGroupAlreadyExistsException();
		}

		final AuthzGroup group = new AuthzGroup(nameTrimmed);

		groups.add(group);

		setHasUnsavedChanges();

		logger.debug("createGroup() group created successfully, returning {}", group);

		return group;
	}

	/**
	 * Creates a new user.
	 * 
	 * @param name Name of user (required)
	 * @param alias Alias of user (optional)
	 * @return Newly created AuthzUser object
	 * @throws AuthzInvalidUserNameException If provided user name is invalid
	 * @throws AuthzInvalidUserAliasException If provided user alias is invalid
	 * @throws AuthzUserAlreadyExistsException If user with the provided name
	 *             already exists
	 * @throws AuthzUserAliasAlreadyExistsException If user with the provided
	 *             alias already exists
	 * @throws AuthzInvalidUserAliasException
	 */
	public AuthzUser createUser(final String name, final String alias) throws AuthzInvalidUserNameException,
			AuthzUserAlreadyExistsException, AuthzUserAliasAlreadyExistsException, AuthzInvalidUserAliasException {
		logger.debug("createUser() entered. name=\"{}\", alias=\"{}\"", name, alias);

		final String nameTrimmed = StringUtils.trimToNull(name);
		final String aliasTrimmed = StringUtils.trimToNull(alias);

		// Validate user name and alias
		if (!isValidUserName(nameTrimmed)) {
			logger.error("createUser() invalid user name");

			throw new AuthzInvalidUserNameException();
		}

		if (aliasTrimmed != null && !isValidUserAlias(aliasTrimmed)) {
			logger.error("createUser() invalid user alias");

			throw new AuthzInvalidUserAliasException();
		}

		// Check for existing users with same user name or alias
		if (doesUserNameExist(nameTrimmed)) {
			logger.info("createUser() user already exists");

			throw new AuthzUserAlreadyExistsException();
		}

		if (aliasTrimmed != null && doesUserAliasExist(aliasTrimmed)) {
			logger.info("createUser() user alias already exists");

			throw new AuthzUserAliasAlreadyExistsException();
		}

		final AuthzUser user = new AuthzUser(nameTrimmed, aliasTrimmed);

		users.add(user);

		setHasUnsavedChanges();

		logger.debug("createUser() user created successfully, returning {}", user);

		return user;
	}

	/**
	 * Determines if a group with the provided name exists.
	 * 
	 * @param name Name of group to find
	 * @return True if the group with the provided name exists, otherwise false
	 * @throws AuthzInvalidGroupNameException If provided group name is invalid
	 */
	public boolean doesGroupNameExist(final String name) throws AuthzInvalidGroupNameException {
		logger.debug("doesGroupNameExist() entered. name=\"{}\"", name);

		final boolean doesGroupNameExist = getGroupWithName(name) != null;

		logger.debug("doesGroupNameExist() exiting, returning {}", doesGroupNameExist);

		return doesGroupNameExist;
	}

	/**
	 * Determines if a user with the provided alias exists.
	 * 
	 * @param alias Alias of user to find
	 * @return True if the user with the provided alias exists, otherwise false
	 * @throws AuthzInvalidUserAliasException If provided user alias is invalid
	 */
	public boolean doesUserAliasExist(final String alias) throws AuthzInvalidUserAliasException {
		logger.debug("doesUserAliasExist() entered. alias=\"{}\"", alias);

		final boolean doesUserAliasExist = getUserWithAlias(alias) != null;

		logger.debug("doesUserAliasExist() exiting, returning {}", doesUserAliasExist);

		return doesUserAliasExist;
	}

	/**
	 * Determines if a user with the provided name exists.
	 * 
	 * @param name Name of user to find
	 * @return True if user with the provided name exists, otherwise false
	 * @throws AuthzInvalidUserNameException If provided user name is invalid
	 */
	public boolean doesUserNameExist(final String name) throws AuthzInvalidUserNameException {
		logger.debug("doesUserNameExist() entered. name=\"{}\"", name);

		final boolean doesUserNameExist = getUserWithName(name) != null;

		logger.debug("doesUserNameExist() exiting, returning {}", doesUserNameExist);

		return doesUserNameExist;
	}

	/**
	 * Returns an immutable collection of AuthGroup objects
	 * 
	 * @return Immutable collection of AuthGroup objects
	 */
	public Collection<AuthzGroup> getGroups() {
		logger.debug("getGroups() entered, returning groups with {} group objects", groups.size());

		return Collections.unmodifiableCollection(groups);
	}

	/**
	 * Returns the group with the provided name.
	 * 
	 * @param name Name of group to find
	 * @return AuthzGroup if found, otherwise null
	 * @throws AuthzInvalidGroupNameException If provided group name is invalid
	 */
	public AuthzGroup getGroupWithName(final String name) throws AuthzInvalidGroupNameException {
		logger.debug("getGroupWithName() entered. name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		if (!isValidGroupName(nameTrimmed)) {
			logger.error("getGroupWithName() invalid gropu name");

			throw new AuthzInvalidGroupNameException();
		}

		AuthzGroup foundGroup = null;

		for (final AuthzGroup group : groups) {
			if (group.getName().equals(nameTrimmed)) {
				foundGroup = group;
				break;
			}
		}

		logger.debug("getGroupWithName() exiting, returning {}", foundGroup);

		return foundGroup;
	}

	/**
	 * Returns an immutable collection of AuthzUser objects
	 * 
	 * @return Immutable collection of AuthzUser objects
	 */
	public Collection<AuthzUser> getUsers() {
		logger.debug("getUsers() entered, returning users with {} user objects", users.size());

		return Collections.unmodifiableCollection(users);
	}

	/**
	 * Returns the user with the provided alias.
	 * 
	 * @param alias Alias of user to find
	 * @return AuthzUser if found, otherwise null
	 * @throws AuthzInvalidUserAliasException If provided user alias is invalid
	 */
	public AuthzUser getUserWithAlias(final String alias) throws AuthzInvalidUserAliasException {
		logger.debug("getUserWithAlias() entered. alias=\"{}\"", alias);

		final String aliasTrimmed = StringUtils.trimToNull(alias);

		if (!isValidUserAlias(aliasTrimmed)) {
			logger.error("getUserWithAlias() invalid user alias");

			throw new AuthzInvalidUserAliasException();
		}

		AuthzUser foundUser = null;

		for (final AuthzUser user : users) {
			if (user.getAlias().equals(aliasTrimmed)) {
				foundUser = user;
				break;
			}
		}

		logger.debug("getUserWithAlias() exiting, returning {}", foundUser);

		return foundUser;
	}

	/**
	 * Returns the user with the provided name.
	 * 
	 * @param name Name of user to find
	 * @return AuthzUser if found, otherwise null
	 * @throws AuthzInvalidUserNameException If provided user name is invalid
	 */
	public AuthzUser getUserWithName(final String name) throws AuthzInvalidUserNameException {
		logger.debug("getUserWithName() entered. name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		if (!isValidUserName(nameTrimmed)) {
			logger.error("getUserWithName() invalid user name");

			throw new AuthzInvalidUserNameException();
		}

		AuthzUser foundUser = null;

		for (final AuthzUser user : users) {
			if (user.getName().equals(nameTrimmed)) {
				foundUser = user;
				break;
			}
		}

		logger.debug("getUserWithName() exiting, returning {}", foundUser);

		return foundUser;
	}

	/**
	 * Returns "has unsaved changes" flag.
	 * 
	 * @return Current value of "has unsaved changes" flag
	 */
	public boolean hasUnsavedChanges() {
		logger.debug("hasUnsavedChanges() entered. hasUnsavedChanges={}", hasUnsavedChanges);

		return hasUnsavedChanges;
	}

	/**
	 * Initializes the document
	 */
	public void initialize() {
		logger.debug("initialize() entered.");

		groups = new Vector<AuthzGroup>();
		users = new Vector<AuthzUser>();

		clearHasUnsavedChanges();

		logger.debug("initialize() exited.");
	}

	/**
	 * Checks group name for validity.
	 * 
	 * @param name Group name to check
	 * @return True if group name is valid, otherwise false
	 */
	protected boolean isValidGroupName(final String name) {
		logger.debug("isValidGroupName() entered. name=\"{}\"", name);

		final boolean isValidGroupName = StringUtils.isNotBlank(name);

		logger.debug("isValidGroupName() exited, returning {}", isValidGroupName);

		return isValidGroupName;
	}

	/**
	 * Checks user alias for validity.
	 * 
	 * @param alias User alias to check
	 * @return True if user alias is valid, otherwise false
	 */
	protected boolean isValidUserAlias(final String alias) {
		logger.debug("isValidUserAlias() entered. alias=\"{}\"", alias);

		final boolean isValidUserAlias = StringUtils.isNotBlank(alias);

		logger.debug("isValidUserAlias() exited, returning {}", isValidUserAlias);

		return isValidUserAlias;
	}

	/**
	 * Checks user name for validity.
	 * 
	 * @param name User name to check
	 * @return True if user name is valid, otherwise false
	 */
	protected boolean isValidUserName(final String name) {
		logger.debug("isValidUserName() entered. name=\"{}\"", name);

		final boolean isValidUserName = StringUtils.isNotBlank(name);

		logger.debug("isValidUserName() exited, returning {}", isValidUserName);

		return isValidUserName;
	}

	/**
	 * Sets "has unsaved changes" flag to true.
	 */
	protected void setHasUnsavedChanges() {
		logger.debug("setHasUnsavedChanges() entered. hasUnsavedChanged={}", hasUnsavedChanges);

		hasUnsavedChanges = true;

		logger.debug("setHasUnsavedChanges() exited. hasUnsavedChanged={}", hasUnsavedChanges);
	}
}
