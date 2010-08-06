package org.suafe.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Authz document file.
 * 
 * @since 2.0
 */
public final class AuthzDocument implements Serializable {
    /** Logger handle. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AuthzDocument.class);

    /** Serialization ID. */
    private static final long serialVersionUID = -1396450094914018451L;

    /** Collection of groups. */
    private Vector<AuthzGroup> groups;

    /** Unsaved changes indicator. */
    private boolean hasUnsavedChanges;

    /** Collection of paths. */
    private Vector<AuthzPath> paths;

    /** Collection of repositories. */
    private Vector<AuthzRepository> repositories;

    /** Collection of users. */
    private Vector<AuthzUser> users;

    /** Regular expression pattern for matching valid path values. */
    private final Pattern VALID_PATH_PATTERN = Pattern
            .compile("^(/)|(/.*[^/])$");

    /**
     * Default constructor.
     */
    public AuthzDocument() {
        super();

        initialize();
    }

    /**
     * Add a new member to a group.
     * 
     * @param group Group to add a member to
     * @param member Member to add to group
     * @throws AuthzGroupMemberAlreadyExistsException If group already has
     *         member
     * @throws AuthzAlreadyMemberOfGroupException If member is already in the
     *         group
     */
    public void addGroupMember(final AuthzGroup group,
            final AuthzGroupMember member)
            throws AuthzGroupMemberAlreadyExistsException,
            AuthzAlreadyMemberOfGroupException {
        LOGGER.debug("addGroupMember() entered. group={}, member={}", group,
                member);

        Preconditions.checkNotNull(group, "Group is null");
        Preconditions.checkNotNull(member, "Member is null");

        group.addMember(member);
        member.addGroup(group);

        LOGGER.debug("addGroupMember() exited.");
    }

    /**
     * Sets "has unsaved changes" flag to false.
     */
    protected void clearHasUnsavedChanges() {
        LOGGER.debug("clearHasUnsavedChanges() entered. hasUnsavedChanged={}",
                hasUnsavedChanges);

        hasUnsavedChanges = false;

        LOGGER.debug("clearHasUnsavedChanges() exited. hasUnsavedChanged={}",
                hasUnsavedChanges);
    }

    /**
     * Creates a new group.
     * 
     * @param name Name of user
     * @return Newly created AuthzGroup object
     * @throws AuthzInvalidGroupNameException If provided group name is invalid
     * @throws AuthzGroupAlreadyExistsException If group with the provided group
     *         name already exists
     */
    public AuthzGroup createGroup(final String name)
            throws AuthzGroupAlreadyExistsException,
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

        final AuthzGroup group = new AuthzGroup(nameTrimmed);

        groups.add(group);

        setHasUnsavedChanges();

        LOGGER.debug("createGroup() group created successfully, returning {}",
                group);

        return group;
    }

    public AuthzPath createPath(final AuthzRepository repository,
            final String pathString) throws AuthzInvalidPathException,
            AuthzPathAlreadyExistsException {
        LOGGER.debug("createPath() entered, repository={}, path={}",
                repository, pathString);

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

        final AuthzPath path = new AuthzPath(repository, pathStringTrimmed);

        paths.add(path);

        setHasUnsavedChanges();

        LOGGER.debug("createPath() path created successfully, "
                + "returning {}", path);

        return path;
    }

    /**
     * Creates a new repository.
     * 
     * @param name Name of repository
     * @return Newly created AuthzRepository object
     * @throws AuthzInvalidRepositoryNameException If provided repository name
     *         is invalid
     * @throws AuthzRepositoryAlreadyExistsException If repository with the
     *         provided name already exists
     */
    public AuthzRepository createRepository(final String name)
            throws AuthzInvalidRepositoryNameException,
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

        final AuthzRepository repository = new AuthzRepository(nameTrimmed);

        repositories.add(repository);

        setHasUnsavedChanges();

        LOGGER.debug("createRepository() repository created successfully, "
                + "returning {}", repository);

        return repository;
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
     *         already exists
     * @throws AuthzUserAliasAlreadyExistsException If user with the provided
     *         alias already exists
     * @throws AuthzInvalidUserAliasException
     */
    public AuthzUser createUser(final String name, final String alias)
            throws AuthzInvalidUserNameException,
            AuthzUserAlreadyExistsException,
            AuthzUserAliasAlreadyExistsException,
            AuthzInvalidUserAliasException {
        LOGGER.debug("createUser() entered. name=\"{}\", alias=\"{}\"", name,
                alias);

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

        final AuthzUser user = new AuthzUser(nameTrimmed, aliasTrimmed);

        users.add(user);

        setHasUnsavedChanges();

        LOGGER.debug("createUser() user created successfully, returning {}",
                user);

        return user;
    }

    /**
     * Determines if a group with the provided name exists.
     * 
     * @param name Name of group to find
     * @return True if the group with the provided name exists, otherwise false
     * @throws AuthzInvalidGroupNameException If provided group name is invalid
     */
    public boolean doesGroupNameExist(final String name)
            throws AuthzInvalidGroupNameException {
        LOGGER.debug("doesGroupNameExist() entered. name=\"{}\"", name);

        final boolean doesGroupNameExist = getGroupWithName(name) != null;

        LOGGER.debug("doesGroupNameExist() exiting, returning {}",
                doesGroupNameExist);

        return doesGroupNameExist;
    }

    /**
     * Determines if a path within the provided repository exists.
     * 
     * @param repository Repository where the path exists
     * @param path Path string within the repository
     * @return True if path with the provided arguments exists, otherwise false
     * @throws AuthzInvalidPathException If provided path is invalid
     */
    public boolean doesPathExist(final AuthzRepository repository,
            final String path) throws AuthzInvalidPathException {
        LOGGER.debug("doesPathExist() entered. repositor{}, name=\"{}\"",
                repository, path);

        final boolean doesPathExist = getPath(repository, path) != null;

        LOGGER.debug("doesPathExist() exiting, returning {}", doesPathExist);

        return doesPathExist;
    }

    /**
     * Determines if a repository with the provided name exists.
     * 
     * @param name Name of repository to find
     * @return True if repository with the provided name exists, otherwise false
     * @throws AuthzInvalidRepositoryNameException If provided repository name
     *         is invalid
     */
    public boolean doesRepositoryNameExist(final String name)
            throws AuthzInvalidRepositoryNameException {
        LOGGER.debug("doesRepositoryNameExist() entered. name=\"{}\"", name);

        final boolean doesNameExist = getRepositoryWithName(name) != null;

        LOGGER.debug("doesRepositoryNameExist() exiting, returning {}",
                doesNameExist);

        return doesNameExist;
    }

    /**
     * Determines if a user with the provided alias exists.
     * 
     * @param alias Alias of user to find
     * @return True if the user with the provided alias exists, otherwise false
     * @throws AuthzInvalidUserAliasException If provided user alias is invalid
     */
    public boolean doesUserAliasExist(final String alias)
            throws AuthzInvalidUserAliasException {
        LOGGER.debug("doesUserAliasExist() entered. alias=\"{}\"", alias);

        final boolean doesUserAliasExist = getUserWithAlias(alias) != null;

        LOGGER.debug("doesUserAliasExist() exiting, returning {}",
                doesUserAliasExist);

        return doesUserAliasExist;
    }

    /**
     * Determines if a user with the provided name exists.
     * 
     * @param name Name of user to find
     * @return True if user with the provided name exists, otherwise false
     * @throws AuthzInvalidUserNameException If provided user name is invalid
     */
    public boolean doesUserNameExist(final String name)
            throws AuthzInvalidUserNameException {
        LOGGER.debug("doesUserNameExist() entered. name=\"{}\"", name);

        final boolean doesUserNameExist = getUserWithName(name) != null;

        LOGGER.debug("doesUserNameExist() exiting, returning {}",
                doesUserNameExist);

        return doesUserNameExist;
    }

    /**
     * Returns an immutable collection of AuthGroup objects.
     * 
     * @return Immutable collection of AuthGroup objects
     */
    public Collection<AuthzGroup> getGroups() {
        LOGGER.debug(
                "getGroups() entered, returning groups with {} group objects",
                groups.size());

        return Collections.unmodifiableCollection(groups);
    }

    /**
     * Returns the group with the provided name.
     * 
     * @param name Name of group to find
     * @return AuthzGroup if found, otherwise null
     * @throws AuthzInvalidGroupNameException If provided group name is invalid
     */
    public AuthzGroup getGroupWithName(final String name)
            throws AuthzInvalidGroupNameException {
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

    /**
     * Returns the path with the provided path and repository.
     * 
     * @param repository AuthzRepository, or null
     * @param path Path string of path to find
     * @return AuthzPath if found, otherwise null
     * @throws AuthzInvalidPathException If provided path is invalid
     */
    public AuthzPath getPath(final AuthzRepository repository, final String path)
            throws AuthzInvalidPathException {
        LOGGER.debug("getPath() entered. repository=\"{}\" path=\"{}\"",
                repository, path);

        final String pathTrimmed = StringUtils.trimToNull(path);

        if (!isValidPath(pathTrimmed)) {
            LOGGER.error("getPath() invalid path");

            throw new AuthzInvalidPathException();
        }

        AuthzPath foundPath = null;

        if (repository == null) {
            for (final AuthzPath pathObject : paths) {
                if (pathTrimmed.equals(pathObject.getPath())
                        && pathObject.getRepository() == null) {
                    foundPath = pathObject;
                    break;
                }
            }
        }
        else {
            for (final AuthzPath pathObject : paths) {
                if (pathTrimmed.equals(pathObject.getPath())
                        && repository.equals(pathObject.getRepository())) {
                    foundPath = pathObject;
                    break;
                }
            }
        }

        LOGGER.debug("getPath() exiting, returning {}", foundPath);

        return foundPath;
    }

    /**
     * Returns an immutable collection of AuthzPath objects.
     * 
     * @return Immutable collection of AuthzPath objects
     */
    public Collection<AuthzPath> getPaths() {
        LOGGER.debug("getPaths() entered, returning paths with "
                + "{} path objects", paths.size());

        return Collections.unmodifiableCollection(paths);
    }

    /**
     * Returns an immutable collection of AuthzRepository objects.
     * 
     * @return Immutable collection of AuthzRepository objects
     */
    public Collection<AuthzRepository> getRepositories() {
        LOGGER.debug("getRepositories() entered, returning repositories with "
                + "{} repository objects", repositories.size());

        return Collections.unmodifiableCollection(repositories);
    }

    /**
     * Returns the repository with the provided name.
     * 
     * @param name Name of repository to find
     * @return AuthzRepository if found, otherwise null
     * @throws AuthzInvalidRepositoryNameException If provided repository name
     *         is invalid
     */
    public AuthzRepository getRepositoryWithName(final String name)
            throws AuthzInvalidRepositoryNameException {
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

        LOGGER.debug("getRepositoryWithName() exiting, returning {}",
                foundRepository);

        return foundRepository;
    }

    /**
     * Returns an immutable collection of AuthzUser objects.
     * 
     * @return Immutable collection of AuthzUser objects
     */
    public Collection<AuthzUser> getUsers() {
        LOGGER.debug(
                "getUsers() entered, returning users with {} user objects",
                users.size());

        return Collections.unmodifiableCollection(users);
    }

    /**
     * Returns the user with the provided alias.
     * 
     * @param alias Alias of user to find
     * @return AuthzUser if found, otherwise null
     * @throws AuthzInvalidUserAliasException If provided user alias is invalid
     */
    public AuthzUser getUserWithAlias(final String alias)
            throws AuthzInvalidUserAliasException {
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

    /**
     * Returns the user with the provided name.
     * 
     * @param name Name of user to find
     * @return AuthzUser if found, otherwise null
     * @throws AuthzInvalidUserNameException If provided user name is invalid
     */
    public AuthzUser getUserWithName(final String name)
            throws AuthzInvalidUserNameException {
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

    /**
     * Returns "has unsaved changes" flag.
     * 
     * @return Current value of "has unsaved changes" flag
     */
    public boolean hasUnsavedChanges() {
        LOGGER.debug("hasUnsavedChanges() entered. hasUnsavedChanges={}",
                hasUnsavedChanges);

        return hasUnsavedChanges;
    }

    /**
     * Initializes the document.
     */
    public void initialize() {
        LOGGER.debug("initialize() entered.");

        groups = new Vector<AuthzGroup>();
        paths = new Vector<AuthzPath>();
        repositories = new Vector<AuthzRepository>();
        users = new Vector<AuthzUser>();

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

        LOGGER.debug("isValidGroupName() exited, returning {}",
                isValidGroupName);

        return isValidGroupName;
    }

    /**
     * Checks path for validity. Paths must start with a slash (/), but must not
     * end with a slash (/), except for when path consists of a single slash
     * (/).
     * 
     * @param path Path to validate
     * @return True if path is valid, otherwise false
     */
    protected boolean isValidPath(final String path) {
        LOGGER.debug("isValidPath() entered. path=\"{}\"", path);

        final boolean isValidPath = VALID_PATH_PATTERN.matcher(
                StringUtils.trimToEmpty(path)).matches();

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

        LOGGER.debug("isValidRepositoryName() exited, returning {}",
                isValidRepositoryName);

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

        LOGGER.debug("isValidUserAlias() exited, returning {}",
                isValidUserAlias);

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

    /**
     * Remove member from a group.
     * 
     * @param group Group to remove member from
     * @param member Member to remove from group
     * @throws AuthzNotMemberOfGroupException If member isn't member of group
     * @throws AuthzNotGroupMemberException If group doesn't have member
     */
    public void removeGroupMember(final AuthzGroup group,
            final AuthzGroupMember member)
            throws AuthzNotMemberOfGroupException, AuthzNotGroupMemberException {
        LOGGER.debug("removeGroupMember() entered. group={}, member={}", group,
                member);

        if (group == null) {
            LOGGER.error("addGroupMember() group is null");

            throw new NullPointerException("Group is null");
        }

        if (member == null) {
            LOGGER.error("addGroupMember() member is null");

            throw new NullPointerException("Member is null");
        }

        group.removeMember(member);
        member.removeGroup(group);

        LOGGER.debug("removeGroupMember() exited.");
    }

    /**
     * Sets "has unsaved changes" flag to true.
     */
    protected void setHasUnsavedChanges() {
        LOGGER.debug("setHasUnsavedChanges() entered. hasUnsavedChanged={}",
                hasUnsavedChanges);

        hasUnsavedChanges = true;

        LOGGER.debug("setHasUnsavedChanges() exited. hasUnsavedChanged={}",
                hasUnsavedChanges);
    }

    /**
     * Creates a string representation of this document.
     * 
     * @return String representation of this document
     */
    @Override
    public String toString() {
        final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

        toStringBuilder.append("groups", groups.size());
        toStringBuilder.append("users", users.size());
        toStringBuilder.append("repositories", repositories.size());

        return toStringBuilder.toString();
    }
}
