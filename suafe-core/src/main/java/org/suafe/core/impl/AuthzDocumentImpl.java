/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.suafe.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.AuthzAccessRule;
import org.suafe.core.AuthzDocument;
import org.suafe.core.AuthzGroup;
import org.suafe.core.AuthzGroupMember;
import org.suafe.core.AuthzPath;
import org.suafe.core.AuthzPermissionable;
import org.suafe.core.AuthzRepository;
import org.suafe.core.AuthzUser;
import org.suafe.core.constants.AuthzAccessLevel;
import org.suafe.core.exceptions.AuthzAccessRuleAlreadyAppliedException;
import org.suafe.core.exceptions.AuthzAccessRuleAlreadyExistsException;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzGroupAlreadyExistsException;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzInvalidGroupNameException;
import org.suafe.core.exceptions.AuthzInvalidPathException;
import org.suafe.core.exceptions.AuthzInvalidRepositoryNameException;
import org.suafe.core.exceptions.AuthzInvalidUserAliasException;
import org.suafe.core.exceptions.AuthzInvalidUserNameException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;
import org.suafe.core.exceptions.AuthzPathAlreadyExistsException;
import org.suafe.core.exceptions.AuthzRepositoryAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAliasAlreadyExistsException;
import org.suafe.core.exceptions.AuthzUserAlreadyExistsException;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Authz document implementation.
 * 
 * @since 2.0
 */
public final class AuthzDocumentImpl implements AuthzDocument {
	/** Logger handle. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzDocumentImpl.class);

	/** Serialization ID. */
	private static final long serialVersionUID = -1396450094914018451L;

	/** The access rules. */
	private List<AuthzAccessRule> accessRules;

	/** Collection of groups. */
	private List<AuthzGroup> groups;

	/** Unsaved changes indicator. */
	private boolean hasUnsavedChanges;

	/** Collection of paths. */
	private List<AuthzPath> paths;

	/** Collection of repositories. */
	private List<AuthzRepository> repositories;

	/** Collection of users. */
	private List<AuthzUser> users;

	/** Regular expression pattern for matching valid path values. */
	private final Pattern VALID_PATH_PATTERN = Pattern.compile("^(/)|(/.*[^/])$");

	/**
	 * Default constructor.
	 */
	protected AuthzDocumentImpl() {
		super();

		initialize();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#addGroupMember(org.suafe.core.impl.AuthzGroup,
	 * org.suafe.core.impl.AuthzGroupMember)
	 */
	@Override
	public void addGroupMember(final AuthzGroup group, final AuthzGroupMember member)
			throws AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException {
		LOGGER.debug("addGroupMember() entered. group={}, member={}", group, member);

		Preconditions.checkNotNull(group, "Group is null");
		Preconditions.checkNotNull(member, "Member is null");

		if (group instanceof AuthzGroupImpl) {
			((AuthzGroupImpl) group).addMember(member);
		}

		if (member instanceof AuthzGroupMemberImpl) {
			((AuthzGroupMemberImpl) member).addGroup(group);
		}

		LOGGER.debug("addGroupMember() exited.");
	}

	/**
	 * Sets "has unsaved changes" flag to false.
	 */
	protected void clearHasUnsavedChanges() {
		LOGGER.debug("clearHasUnsavedChanges() entered. hasUnsavedChanged={}", hasUnsavedChanges);

		hasUnsavedChanges = false;

		LOGGER.debug("clearHasUnsavedChanges() exited. hasUnsavedChanged={}", hasUnsavedChanges);
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzDocumentIF#cloneGroup(org.suafe.core.AuthzGroupIF, java.lang.String)
	 */
	@Override
	public AuthzGroup cloneGroup(final AuthzGroup groupToClone, final String cloneGroupName)
			throws AuthzGroupAlreadyExistsException, AuthzInvalidGroupNameException,
			AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException {
		LOGGER.debug("cloneGroup() entered. groupToClone=\"{}\", cloneGroupName=\"{}\"", groupToClone, cloneGroupName);

		final AuthzGroup cloneGroup = createGroup(cloneGroupName);

		for (final AuthzGroup group : groupToClone.getGroups()) {
			addGroupMember(group, cloneGroup);
		}

		for (final AuthzGroupMember authzGroupMember : groupToClone.getMembers()) {
			addGroupMember(cloneGroup, authzGroupMember);
		}

		// TODO Clone access rules

		LOGGER.debug("cloneGroup() group clone created successfully, returning {}", cloneGroup);

		return cloneGroup;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzDocumentIF#cloneUser(org.suafe.core.AuthzUserIF, java.lang.String, java.lang.String)
	 */
	@Override
	public AuthzUser cloneUser(final AuthzUser userToClone, final String cloneUserName, final String cloneAlias)
			throws AuthzInvalidUserNameException, AuthzUserAlreadyExistsException,
			AuthzUserAliasAlreadyExistsException, AuthzInvalidUserAliasException,
			AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException {
		LOGGER.debug("cloneUser() entered. userToClone=\"{}\", cloneUserName=\"{}\"", userToClone, cloneUserName);

		final AuthzUser cloneUser = createUser(cloneUserName, cloneAlias);

		for (final AuthzGroup group : userToClone.getGroups()) {
			addGroupMember(group, cloneUser);
		}

		// TODO Clone access rules

		LOGGER.debug("cloneUser() user clone created successfully, returning {}", cloneUser);

		return cloneUser;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzDocument#createAccessRule(org.suafe.core.AuthzPath, org.suafe.core.AuthzPermissionable,
	 * org.suafe.core.constants.AuthzAccessLevel)
	 */
	@Override
	public AuthzAccessRule createAccessRule(final AuthzPath path, final AuthzPermissionable permissionable,
			final AuthzAccessLevel accessLevel) throws AuthzAccessRuleAlreadyExistsException,
			AuthzAccessRuleAlreadyAppliedException {
		LOGGER.debug("createAccessRule() entered. path=\"{}\", permissionable=\"{}\"", path, permissionable);

		Preconditions.checkNotNull(path, "Path is null");
		Preconditions.checkNotNull(permissionable, "Permissionable is null");
		Preconditions.checkNotNull(accessLevel, "AccessLevel is null");

		if (doesAccessRuleExist(path, permissionable)) {
			LOGGER.info("createAccessRule() access rule already exists");

			throw new AuthzAccessRuleAlreadyExistsException();
		}

		final AuthzAccessRule accessRule = new AuthzAccessRuleImpl(path, permissionable, accessLevel);

		if (permissionable instanceof AuthzGroupMemberImpl) {
			((AuthzGroupMemberImpl) permissionable).addAccessRule(accessRule);
		}

		accessRules.add(accessRule);

		Collections.sort(accessRules);

		setHasUnsavedChanges();

		LOGGER.debug("createAccessRule() access rule created successfully, returning {}", accessRule);

		return accessRule;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#createGroup(java.lang.String)
	 */
	@Override
	public AuthzGroupImpl createGroup(final String name) throws AuthzGroupAlreadyExistsException,
			AuthzInvalidGroupNameException {
		LOGGER.debug("createGroup() entered. name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		// Validate group name
		if (!isValidGroupName(nameTrimmed)) {
			LOGGER.error("createGroup() invalid group name");

			throw new AuthzInvalidGroupNameException();
		}

		if (doesGroupNameExist(nameTrimmed)) {
			LOGGER.info("createGroup() group already exists");

			throw new AuthzGroupAlreadyExistsException();
		}

		final AuthzGroupImpl group = new AuthzGroupImpl(nameTrimmed);

		groups.add(group);

		Collections.sort(groups);

		setHasUnsavedChanges();

		LOGGER.debug("createGroup() group created successfully, returning {}", group);

		return group;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#createPath(org.suafe.core.impl.AuthzRepository, java.lang.String)
	 */
	@Override
	public AuthzPathImpl createPath(final AuthzRepository repository, final String pathString)
			throws AuthzInvalidPathException, AuthzPathAlreadyExistsException {
		LOGGER.debug("createPath() entered, repository={}, path={}", repository, pathString);

		final String pathStringTrimmed = StringUtils.trimToNull(pathString);

		// Validate path
		if (!isValidPath(pathStringTrimmed)) {
			LOGGER.error("createPath() invalid path");

			throw new AuthzInvalidPathException();
		}

		if (doesPathExist(repository, pathStringTrimmed)) {
			LOGGER.info("createPath() path already exists");

			throw new AuthzPathAlreadyExistsException();
		}

		final AuthzPathImpl path = new AuthzPathImpl(repository, pathStringTrimmed);

		paths.add(path);

		Collections.sort(paths);

		setHasUnsavedChanges();

		LOGGER.debug("createPath() path created successfully, " + "returning {}", path);

		return path;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#createRepository(java.lang.String)
	 */
	@Override
	public AuthzRepository createRepository(final String name) throws AuthzInvalidRepositoryNameException,
			AuthzRepositoryAlreadyExistsException {
		LOGGER.debug("createRepository() entered, name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		// Validate repository name
		if (!isValidRepositoryName(nameTrimmed)) {
			LOGGER.error("createRepository() invalid repository name");

			throw new AuthzInvalidRepositoryNameException();
		}

		// Check for existing repositories with same name
		if (doesRepositoryNameExist(nameTrimmed)) {
			LOGGER.info("createRepository() repository already exists");

			throw new AuthzRepositoryAlreadyExistsException();
		}

		final AuthzRepositoryImpl repository = new AuthzRepositoryImpl(nameTrimmed);

		repositories.add(repository);

		Collections.sort(repositories);

		setHasUnsavedChanges();

		LOGGER.debug("createRepository() repository created successfully, " + "returning {}", repository);

		return repository;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#createUser(java.lang.String, java.lang.String)
	 */
	@Override
	public AuthzUserImpl createUser(final String name, final String alias) throws AuthzInvalidUserNameException,
			AuthzUserAlreadyExistsException, AuthzUserAliasAlreadyExistsException, AuthzInvalidUserAliasException {
		LOGGER.debug("createUser() entered. name=\"{}\", alias=\"{}\"", name, alias);

		final String nameTrimmed = StringUtils.trimToNull(name);
		final String aliasTrimmed = StringUtils.trimToNull(alias);

		// Validate user name and alias
		if (!isValidUserName(nameTrimmed)) {
			LOGGER.error("createUser() invalid user name");

			throw new AuthzInvalidUserNameException();
		}

		if (aliasTrimmed != null && !isValidUserAlias(aliasTrimmed)) {
			LOGGER.error("createUser() invalid user alias");

			throw new AuthzInvalidUserAliasException();
		}

		// Check for existing users with same user name or alias
		if (doesUserNameExist(nameTrimmed)) {
			LOGGER.info("createUser() user already exists");

			throw new AuthzUserAlreadyExistsException();
		}

		if (aliasTrimmed != null && doesUserAliasExist(aliasTrimmed)) {
			LOGGER.info("createUser() user alias already exists");

			throw new AuthzUserAliasAlreadyExistsException();
		}

		final AuthzUserImpl user = new AuthzUserImpl(nameTrimmed, aliasTrimmed);

		users.add(user);

		Collections.sort(users);

		setHasUnsavedChanges();

		LOGGER.debug("createUser() user created successfully, returning {}", user);

		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzDocument#doesAccessRuleExist(org.suafe.core.AuthzPath,
	 * org.suafe.core.AuthzPermissionable)
	 */
	@Override
	public boolean doesAccessRuleExist(final AuthzPath path, final AuthzPermissionable permissionable) {
		LOGGER.debug("doesAccessRuleExist() entered. path=\"{}\", group=\"{}\"", path, permissionable);

		final boolean doesAccessRuleExist = getAccessRule(path, permissionable) != null;

		LOGGER.debug("doesAccessRuleExist() exiting, returning {}", doesAccessRuleExist);

		return doesAccessRuleExist;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#doesGroupNameExist(java.lang.String)
	 */
	@Override
	public boolean doesGroupNameExist(final String name) throws AuthzInvalidGroupNameException {
		LOGGER.debug("doesGroupNameExist() entered. name=\"{}\"", name);

		final boolean doesGroupNameExist = getGroupWithName(name) != null;

		LOGGER.debug("doesGroupNameExist() exiting, returning {}", doesGroupNameExist);

		return doesGroupNameExist;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#doesPathExist(org.suafe.core.impl.AuthzRepository, java.lang.String)
	 */
	@Override
	public boolean doesPathExist(final AuthzRepository repository, final String path) throws AuthzInvalidPathException {
		LOGGER.debug("doesPathExist() entered. repositor{}, name=\"{}\"", repository, path);

		final boolean doesPathExist = getPath(repository, path) != null;

		LOGGER.debug("doesPathExist() exiting, returning {}", doesPathExist);

		return doesPathExist;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#doesRepositoryNameExist(java.lang.String)
	 */
	@Override
	public boolean doesRepositoryNameExist(final String name) throws AuthzInvalidRepositoryNameException {
		LOGGER.debug("doesRepositoryNameExist() entered. name=\"{}\"", name);

		final boolean doesNameExist = getRepositoryWithName(name) != null;

		LOGGER.debug("doesRepositoryNameExist() exiting, returning {}", doesNameExist);

		return doesNameExist;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#doesUserAliasExist(java.lang.String)
	 */
	@Override
	public boolean doesUserAliasExist(final String alias) throws AuthzInvalidUserAliasException {
		LOGGER.debug("doesUserAliasExist() entered. alias=\"{}\"", alias);

		final boolean doesUserAliasExist = getUserWithAlias(alias) != null;

		LOGGER.debug("doesUserAliasExist() exiting, returning {}", doesUserAliasExist);

		return doesUserAliasExist;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#doesUserNameExist(java.lang.String)
	 */
	@Override
	public boolean doesUserNameExist(final String name) throws AuthzInvalidUserNameException {
		LOGGER.debug("doesUserNameExist() entered. name=\"{}\"", name);

		final boolean doesUserNameExist = getUserWithName(name) != null;

		LOGGER.debug("doesUserNameExist() exiting, returning {}", doesUserNameExist);

		return doesUserNameExist;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.AuthzDocument#getAccessRule(org.suafe.core.AuthzPath, org.suafe.core.AuthzPermissionable)
	 */
	@Override
	public AuthzAccessRule getAccessRule(final AuthzPath path, final AuthzPermissionable permissionable) {
		LOGGER.debug("getAccessRuleWithGroup() entered. path=\"{}\", group=\"{}\"", path, permissionable);

		Preconditions.checkNotNull(path, "Path is null");
		Preconditions.checkNotNull(permissionable, "Permissionable is null");

		AuthzAccessRule foundAccessRule = null;

		for (final AuthzAccessRule accessRule : accessRules) {
			if (accessRule.getPath().equals(path) && accessRule.getPermissionable() != null
					&& accessRule.getPermissionable().equals(permissionable)) {
				foundAccessRule = accessRule;
				break;
			}
		}

		LOGGER.debug("getAccessRuleWithGroup() exiting, returning {}", foundAccessRule);

		return foundAccessRule;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getAccessRules()
	 */
	@Override
	public List<AuthzAccessRule> getAccessRules() {
		LOGGER.debug("getAccessRules() entered, returning accessRules with {} access rule objects", accessRules.size());

		return new ImmutableList.Builder<AuthzAccessRule>().addAll(accessRules).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getGroups()
	 */
	@Override
	public Collection<AuthzGroup> getGroups() {
		LOGGER.debug("getGroups() entered, returning groups with {} group objects", groups.size());

		return new ImmutableList.Builder<AuthzGroup>().addAll(groups).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getGroupWithName(java.lang.String)
	 */
	@Override
	public AuthzGroup getGroupWithName(final String name) throws AuthzInvalidGroupNameException {
		LOGGER.debug("getGroupWithName() entered. name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		if (!isValidGroupName(nameTrimmed)) {
			LOGGER.error("getGroupWithName() invalid group name");

			throw new AuthzInvalidGroupNameException();
		}

		AuthzGroup foundGroup = null;

		for (final AuthzGroup group : groups) {
			if (group.getName().equals(nameTrimmed)) {
				foundGroup = group;
				break;
			}
		}

		LOGGER.debug("getGroupWithName() exiting, returning {}", foundGroup);

		return foundGroup;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getPath(org.suafe.core.impl.AuthzRepository, java.lang.String)
	 */
	@Override
	public AuthzPath getPath(final AuthzRepository repository, final String path) throws AuthzInvalidPathException {
		LOGGER.debug("getPath() entered. repository=\"{}\" path=\"{}\"", repository, path);

		final String pathTrimmed = StringUtils.trimToNull(path);

		if (!isValidPath(pathTrimmed)) {
			LOGGER.error("getPath() invalid path");

			throw new AuthzInvalidPathException();
		}

		AuthzPath foundPath = null;

		if (repository == null) {
			for (final AuthzPath pathObject : paths) {
				if (pathTrimmed.equals(pathObject.getPath()) && pathObject.getRepository() == null) {
					foundPath = pathObject;
					break;
				}
			}
		}
		else {
			for (final AuthzPath pathObject : paths) {
				if (pathTrimmed.equals(pathObject.getPath()) && repository.equals(pathObject.getRepository())) {
					foundPath = pathObject;
					break;
				}
			}
		}

		LOGGER.debug("getPath() exiting, returning {}", foundPath);

		return foundPath;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getPaths()
	 */
	@Override
	public Collection<AuthzPath> getPaths() {
		LOGGER.debug("getPaths() entered, returning paths with " + "{} path objects", paths.size());

		return new ImmutableList.Builder<AuthzPath>().addAll(paths).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getRepositories()
	 */
	@Override
	public Collection<AuthzRepository> getRepositories() {
		LOGGER.debug("getRepositories() entered, returning repositories with " + "{} repository objects",
				repositories.size());

		return new ImmutableList.Builder<AuthzRepository>().addAll(repositories).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getRepositoryWithName(java.lang.String)
	 */
	@Override
	public AuthzRepository getRepositoryWithName(final String name) throws AuthzInvalidRepositoryNameException {
		LOGGER.debug("getRepositoryWithName() entered. name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		if (!isValidRepositoryName(nameTrimmed)) {
			LOGGER.error("getRepositoryWithName() invalid repository name");

			throw new AuthzInvalidRepositoryNameException();
		}

		AuthzRepository foundRepository = null;

		for (final AuthzRepository repository : repositories) {
			if (repository.getName().equals(nameTrimmed)) {
				foundRepository = repository;
				break;
			}
		}

		LOGGER.debug("getRepositoryWithName() exiting, returning {}", foundRepository);

		return foundRepository;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getUsers()
	 */
	@Override
	public Collection<AuthzUser> getUsers() {
		LOGGER.debug("getUsers() entered, returning users with {} user objects", users.size());

		return new ImmutableList.Builder<AuthzUser>().addAll(users).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getUserWithAlias(java.lang.String)
	 */
	@Override
	public AuthzUser getUserWithAlias(final String alias) throws AuthzInvalidUserAliasException {
		LOGGER.debug("getUserWithAlias() entered. alias=\"{}\"", alias);

		final String aliasTrimmed = StringUtils.trimToNull(alias);

		if (!isValidUserAlias(aliasTrimmed)) {
			LOGGER.error("getUserWithAlias() invalid user alias");

			throw new AuthzInvalidUserAliasException();
		}

		AuthzUser foundUser = null;

		for (final AuthzUser user : users) {
			if (user.getAlias().equals(aliasTrimmed)) {
				foundUser = user;
				break;
			}
		}

		LOGGER.debug("getUserWithAlias() exiting, returning {}", foundUser);

		return foundUser;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#getUserWithName(java.lang.String)
	 */
	@Override
	public AuthzUser getUserWithName(final String name) throws AuthzInvalidUserNameException {
		LOGGER.debug("getUserWithName() entered. name=\"{}\"", name);

		final String nameTrimmed = StringUtils.trimToNull(name);

		if (!isValidUserName(nameTrimmed)) {
			LOGGER.error("getUserWithName() invalid user name");

			throw new AuthzInvalidUserNameException();
		}

		AuthzUser foundUser = null;

		for (final AuthzUser user : users) {
			if (user.getName().equals(nameTrimmed)) {
				foundUser = user;
				break;
			}
		}

		LOGGER.debug("getUserWithName() exiting, returning {}", foundUser);

		return foundUser;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#hasUnsavedChanges()
	 */
	@Override
	public boolean hasUnsavedChanges() {
		LOGGER.debug("hasUnsavedChanges() entered. hasUnsavedChanges={}", hasUnsavedChanges);

		return hasUnsavedChanges;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#initialize()
	 */
	@Override
	public void initialize() {
		LOGGER.debug("initialize() entered.");

		accessRules = new ArrayList<AuthzAccessRule>();
		groups = new ArrayList<AuthzGroup>();
		paths = new ArrayList<AuthzPath>();
		repositories = new ArrayList<AuthzRepository>();
		users = new ArrayList<AuthzUser>();

		clearHasUnsavedChanges();

		LOGGER.debug("initialize() exited.");
	}

	/**
	 * Checks group name for validity.
	 * 
	 * @param name Group name to check
	 * @return True if group name is valid, otherwise false
	 */
	protected boolean isValidGroupName(final String name) {
		LOGGER.debug("isValidGroupName() entered. name=\"{}\"", name);

		final boolean isValidGroupName = StringUtils.isNotBlank(name);

		LOGGER.debug("isValidGroupName() exited, returning {}", isValidGroupName);

		return isValidGroupName;
	}

	/**
	 * Checks path for validity. Paths must start with a slash (/), but must not end with a slash (/), except for when
	 * path consists of a single slash (/).
	 * 
	 * @param path Path to validate
	 * @return True if path is valid, otherwise false
	 */
	protected boolean isValidPath(final String path) {
		LOGGER.debug("isValidPath() entered. path=\"{}\"", path);

		final boolean isValidPath = VALID_PATH_PATTERN.matcher(StringUtils.trimToEmpty(path)).matches();

		LOGGER.debug("isValidPath() exited. returning {}", isValidPath);

		return isValidPath;
	}

	/**
	 * Checks repository name for validity.
	 * 
	 * @param name Repository name to check
	 * @return True if repository name is valid, otherwise false
	 */
	protected boolean isValidRepositoryName(final String name) {
		LOGGER.debug("isValidRepositoryName() entered. name=\"{}\"", name);

		final boolean isValidRepositoryName = StringUtils.isNotBlank(name);

		LOGGER.debug("isValidRepositoryName() exited, returning {}", isValidRepositoryName);

		return isValidRepositoryName;
	}

	/**
	 * Checks user alias for validity.
	 * 
	 * @param alias User alias to check
	 * @return True if user alias is valid, otherwise false
	 */
	protected boolean isValidUserAlias(final String alias) {
		LOGGER.debug("isValidUserAlias() entered. alias=\"{}\"", alias);

		final boolean isValidUserAlias = StringUtils.isNotBlank(alias);

		LOGGER.debug("isValidUserAlias() exited, returning {}", isValidUserAlias);

		return isValidUserAlias;
	}

	/**
	 * Checks user name for validity.
	 * 
	 * @param name User name to check
	 * @return True if user name is valid, otherwise false
	 */
	protected boolean isValidUserName(final String name) {
		LOGGER.debug("isValidUserName() entered. name=\"{}\"", name);

		final boolean isValidUserName = StringUtils.isNotBlank(name);

		LOGGER.debug("isValidUserName() exited, returning {}", isValidUserName);

		return isValidUserName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzDocumentIF#removeGroupMember(org.suafe.core.impl.AuthzGroup,
	 * org.suafe.core.impl.AuthzGroupMember)
	 */
	@Override
	public void removeGroupMember(final AuthzGroup group, final AuthzGroupMember member)
			throws AuthzNotMemberOfGroupException, AuthzNotGroupMemberException {
		LOGGER.debug("removeGroupMember() entered. group={}, member={}", group, member);

		Preconditions.checkNotNull(group, "Group is null");
		Preconditions.checkNotNull(member, "Member is null");

		if (group instanceof AuthzGroupImpl) {
			((AuthzGroupImpl) group).removeMember(member);
		}

		if (group instanceof AuthzGroupMemberImpl) {
			((AuthzGroupMemberImpl) member).removeGroup(group);
		}

		LOGGER.debug("removeGroupMember() exited.");
	}

	/**
	 * Sets "has unsaved changes" flag to true.
	 */
	protected void setHasUnsavedChanges() {
		LOGGER.debug("setHasUnsavedChanges() entered. hasUnsavedChanged={}", hasUnsavedChanges);

		hasUnsavedChanges = true;

		LOGGER.debug("setHasUnsavedChanges() exited. hasUnsavedChanged={}", hasUnsavedChanges);
	}

	/**
	 * Creates a string representation of this document.
	 * 
	 * @return String representation of this document
	 */
	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("accessRules", accessRules.size());
		toStringBuilder.append("groups", groups.size());
		toStringBuilder.append("paths", paths.size());
		toStringBuilder.append("repositories", repositories.size());
		toStringBuilder.append("users", users.size());

		return toStringBuilder.toString();
	}
}