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
package org.suafe.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.suafe.core.enums.AuthzAccessLevel;
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

/**
 * The Interface AuthzDocument.
 */
public interface AuthzDocument extends Serializable {

	/**
	 * Add a new member to a group.
	 * 
	 * @param group Group to add a member to
	 * @param member Member to add to group
	 * @throws AuthzGroupMemberAlreadyExistsException If group already has member
	 * @throws AuthzAlreadyMemberOfGroupException If member is already in the group
	 */
	void addGroupMember(final AuthzGroup group, final AuthzGroupMember member)
			throws AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException;

	/**
	 * Add a new member to a collection of groups.
	 * 
	 * @param groups Collection of groups to add a member to
	 * @param member Member to add to group
	 * @throws AuthzGroupMemberAlreadyExistsException If group already has member
	 * @throws AuthzAlreadyMemberOfGroupException If member is already in the group
	 */
	void addGroupMember(final Collection<AuthzGroup> groups, final AuthzGroupMember member)
			throws AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException;

	/**
	 * Add new members to a group.
	 * 
	 * @param group Group to add a member to
	 * @param members Collection of members to add to group
	 * @throws AuthzGroupMemberAlreadyExistsException If group already has member
	 * @throws AuthzAlreadyMemberOfGroupException If member is already in the group
	 */
	void addGroupMembers(final AuthzGroup group, final Collection<AuthzGroupMember> members)
			throws AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException;

	/**
	 * Clones an existing access rule and applies the rule to the provided permissionable object.
	 * 
	 * @param authzAccessRule Access rule to clone
	 * @param permissionable Permissionable to apply new rule against
	 * @return Clone access rule
	 * @throws AuthzAccessRuleAlreadyExistsException the authz access rule already exists exception
	 * @throws AuthzAccessRuleAlreadyAppliedException If the access rule is already applied to the member
	 */
	AuthzAccessRule cloneAccessRule(final AuthzAccessRule authzAccessRule, final AuthzPermissionable permissionable)
			throws AuthzAccessRuleAlreadyExistsException, AuthzAccessRuleAlreadyAppliedException;

	/**
	 * Clones a collection of existing access rules and applies the rules to the provided permissionable object.
	 * 
	 * @param authzAccessRules Access rules to clone
	 * @param permissionable Permissionable to apply new rules against
	 * @return Clone access rules
	 * @throws AuthzAccessRuleAlreadyExistsException the authz access rule already exists exception
	 * @throws AuthzAccessRuleAlreadyAppliedException If the access rule is already applied to the member
	 */
	Collection<AuthzAccessRule> cloneAccessRules(final Collection<AuthzAccessRule> authzAccessRule,
			final AuthzPermissionable permissionable) throws AuthzAccessRuleAlreadyExistsException,
			AuthzAccessRuleAlreadyAppliedException;;

	/**
	 * Clones the provided group.
	 * 
	 * @param groupToClone Group to be cloned
	 * @param cloneGroupName Name for the group clone
	 * @return the group clone
	 * @throws AuthzGroupAlreadyExistsException If group with the provided group name already exists
	 * @throws AuthzInvalidGroupNameException If provided group name is invalid
	 * @throws AuthzGroupMemberAlreadyExistsException If group already has member
	 * @throws AuthzAlreadyMemberOfGroupException If member is already in the group
	 * @throws AuthzAccessRuleAlreadyExistsException the authz access rule already exists exception
	 * @throws AuthzAccessRuleAlreadyAppliedException If the access rule is already applied to the member
	 */
	AuthzGroup cloneGroup(final AuthzGroup groupToClone, final String cloneGroupName)
			throws AuthzGroupAlreadyExistsException, AuthzInvalidGroupNameException,
			AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException,
			AuthzAccessRuleAlreadyExistsException, AuthzAccessRuleAlreadyAppliedException;

	/**
	 * Clones the provided user.
	 * 
	 * @param userToClone User to be cloned
	 * @param cloneUserName Name for the user clone
	 * @param cloneAlias Alias for the user clone
	 * @return the user clone * @throws AuthzInvalidUserNameException If provided user name is invalid
	 * @throws AuthzUserAlreadyExistsException If user with the provided name already exists
	 * @throws AuthzUserAliasAlreadyExistsException If user with the provided alias already exists
	 * @throws AuthzInvalidUserAliasException the authz invalid user alias exception
	 * @throws AuthzGroupMemberAlreadyExistsException If group already has member
	 * @throws AuthzAlreadyMemberOfGroupException If member is already in the group
	 * @throws AuthzAccessRuleAlreadyExistsException the authz access rule already exists exception
	 * @throws AuthzAccessRuleAlreadyAppliedException If the access rule is already applied to the member
	 */
	AuthzUser cloneUser(final AuthzUser userToClone, final String cloneUserName, final String cloneAlias)
			throws AuthzInvalidUserNameException, AuthzUserAlreadyExistsException,
			AuthzUserAliasAlreadyExistsException, AuthzInvalidUserAliasException,
			AuthzGroupMemberAlreadyExistsException, AuthzAlreadyMemberOfGroupException,
			AuthzAccessRuleAlreadyExistsException, AuthzAccessRuleAlreadyAppliedException;

	/**
	 * Creates the access rule.
	 * 
	 * @param path the path
	 * @param group the group
	 * @param accessLevel the access level
	 * @return the authz access rule
	 * @throws AuthzAccessRuleAlreadyExistsException the authz access rule already exists exception
	 * @throws AuthzAccessRuleAlreadyAppliedException If the access rule is already applied to the member
	 */
	AuthzAccessRule createAccessRule(final AuthzPath path, final AuthzPermissionable permissionable,
			final AuthzAccessLevel accessLevel) throws AuthzAccessRuleAlreadyExistsException,
			AuthzAccessRuleAlreadyAppliedException;

	/**
	 * Creates a new group.
	 * 
	 * @param name Name of user
	 * @return Newly created AuthzGroup object
	 * @throws AuthzGroupAlreadyExistsException If group with the provided group name already exists
	 * @throws AuthzInvalidGroupNameException If provided group name is invalid
	 */
	AuthzGroup createGroup(final String name) throws AuthzGroupAlreadyExistsException, AuthzInvalidGroupNameException;

	/**
	 * Creates the path.
	 * 
	 * @param repository the repository
	 * @param pathString the path string
	 * @return the authz path
	 * @throws AuthzInvalidPathException the authz invalid path exception
	 * @throws AuthzPathAlreadyExistsException the authz path already exists exception
	 */
	AuthzPath createPath(final AuthzRepository repository, final String pathString) throws AuthzInvalidPathException,
			AuthzPathAlreadyExistsException;

	/**
	 * Creates a new repository.
	 * 
	 * @param name Name of repository
	 * @return Newly created AuthzRepository object
	 * @throws AuthzInvalidRepositoryNameException If provided repository name is invalid
	 * @throws AuthzRepositoryAlreadyExistsException If repository with the provided name already exists
	 */
	AuthzRepository createRepository(final String name) throws AuthzInvalidRepositoryNameException,
			AuthzRepositoryAlreadyExistsException;

	/**
	 * Creates a new user.
	 * 
	 * @param name Name of user (required)
	 * @param alias Alias of user (optional)
	 * @return Newly created AuthzUser object
	 * @throws AuthzInvalidUserNameException If provided user name is invalid
	 * @throws AuthzUserAlreadyExistsException If user with the provided name already exists
	 * @throws AuthzUserAliasAlreadyExistsException If user with the provided alias already exists
	 * @throws AuthzInvalidUserAliasException the authz invalid user alias exception
	 */
	AuthzUser createUser(final String name, final String alias) throws AuthzInvalidUserNameException,
			AuthzUserAlreadyExistsException, AuthzUserAliasAlreadyExistsException, AuthzInvalidUserAliasException;

	/**
	 * Determines if an access rule with the provided path and group exists.
	 * 
	 * @param path Path of access rule to find
	 * @param permissionable Permissionable object that is assigned to the access rule
	 * @return True if the access rule with the provided path and group exists, otherwise false
	 */
	boolean doesAccessRuleExist(final AuthzPath path, final AuthzPermissionable permissionable);

	/**
	 * Determines if a group with the provided name exists.
	 * 
	 * @param name Name of group to find
	 * @return True if the group with the provided name exists, otherwise false
	 * @throws AuthzInvalidGroupNameException If provided group name is invalid
	 */
	boolean doesGroupNameExist(final String name) throws AuthzInvalidGroupNameException;

	/**
	 * Determines if a path within the provided repository exists.
	 * 
	 * @param repository Repository where the path exists
	 * @param path Path string within the repository
	 * @return True if path with the provided arguments exists, otherwise false
	 * @throws AuthzInvalidPathException If provided path is invalid
	 */
	boolean doesPathExist(final AuthzRepository repository, final String path) throws AuthzInvalidPathException;

	/**
	 * Determines if a repository with the provided name exists.
	 * 
	 * @param name Name of repository to find
	 * @return True if repository with the provided name exists, otherwise false
	 * @throws AuthzInvalidRepositoryNameException If provided repository name is invalid
	 */
	boolean doesRepositoryNameExist(final String name) throws AuthzInvalidRepositoryNameException;

	/**
	 * Determines if a user with the provided alias exists.
	 * 
	 * @param alias Alias of user to find
	 * @return True if the user with the provided alias exists, otherwise false
	 * @throws AuthzInvalidUserAliasException If provided user alias is invalid
	 */
	boolean doesUserAliasExist(final String alias) throws AuthzInvalidUserAliasException;

	/**
	 * Determines if a user with the provided name exists.
	 * 
	 * @param name Name of user to find
	 * @return True if user with the provided name exists, otherwise false
	 * @throws AuthzInvalidUserNameException If provided user name is invalid
	 */
	boolean doesUserNameExist(final String name) throws AuthzInvalidUserNameException;

	/**
	 * Returns the access rule with the provided path and group.
	 * 
	 * @param path Path of access rule to find
	 * @param permissionable Permissionable object assigned to the access rule to find
	 * @return AuthzAccessRule if found, otherwise null
	 */
	AuthzAccessRule getAccessRule(final AuthzPath path, final AuthzPermissionable permissionable);

	/**
	 * Returns an immutable collection of AuthzAccessRuleIF objects.
	 * 
	 * @return Immutable collection of AuthzAccessRuleIF objects
	 */
	List<AuthzAccessRule> getAccessRules();

	/**
	 * Returns an immutable collection of AuthGroup objects.
	 * 
	 * @return Immutable collection of AuthGroup objects
	 */
	List<AuthzGroup> getGroups();

	/**
	 * Returns the group with the provided name.
	 * 
	 * @param name Name of group to find
	 * @return AuthzGroup if found, otherwise null
	 * @throws AuthzInvalidGroupNameException If provided group name is invalid
	 */
	AuthzGroup getGroupWithName(final String name) throws AuthzInvalidGroupNameException;

	/**
	 * Returns the path with the provided path and repository.
	 * 
	 * @param repository AuthzRepository, or null
	 * @param path Path string of path to find
	 * @return AuthzPath if found, otherwise null
	 * @throws AuthzInvalidPathException If provided path is invalid
	 */
	AuthzPath getPath(final AuthzRepository repository, final String path) throws AuthzInvalidPathException;

	/**
	 * Returns an immutable collection of AuthzPath objects.
	 * 
	 * @return Immutable collection of AuthzPath objects
	 */
	List<AuthzPath> getPaths();

	/**
	 * Returns an immutable collection of AuthzRepository objects.
	 * 
	 * @return Immutable collection of AuthzRepository objects
	 */
	List<AuthzRepository> getRepositories();

	/**
	 * Returns the repository with the provided name.
	 * 
	 * @param name Name of repository to find
	 * @return AuthzRepository if found, otherwise null
	 * @throws AuthzInvalidRepositoryNameException If provided repository name is invalid
	 */
	AuthzRepository getRepositoryWithName(final String name) throws AuthzInvalidRepositoryNameException;

	/**
	 * Returns an immutable collection of AuthzUser objects.
	 * 
	 * @return Immutable collection of AuthzUser objects
	 */
	List<AuthzUser> getUsers();

	/**
	 * Returns the user with the provided alias.
	 * 
	 * @param alias Alias of user to find
	 * @return AuthzUser if found, otherwise null
	 * @throws AuthzInvalidUserAliasException If provided user alias is invalid
	 */
	AuthzUser getUserWithAlias(final String alias) throws AuthzInvalidUserAliasException;

	/**
	 * Returns the user with the provided name.
	 * 
	 * @param name Name of user to find
	 * @return AuthzUser if found, otherwise null
	 * @throws AuthzInvalidUserNameException If provided user name is invalid
	 */
	AuthzUser getUserWithName(final String name) throws AuthzInvalidUserNameException;

	/**
	 * Returns "has unsaved changes" flag.
	 * 
	 * @return Current value of "has unsaved changes" flag
	 */
	boolean hasUnsavedChanges();

	/**
	 * Initializes the document.
	 */
	void initialize();

	/**
	 * Remove member from a group.
	 * 
	 * @param group Group to remove member from
	 * @param member Member to remove from group
	 * @throws AuthzNotMemberOfGroupException If member isn't member of group
	 * @throws AuthzNotGroupMemberException If group doesn't have member
	 */
	void removeGroupMember(final AuthzGroup group, final AuthzGroupMember member)
			throws AuthzNotMemberOfGroupException, AuthzNotGroupMemberException;

	/**
	 * Remove members from a group.
	 * 
	 * @param group Group to remove member from
	 * @param members Collection of member to remove from group
	 * @throws AuthzNotMemberOfGroupException If member isn't member of group
	 * @throws AuthzNotGroupMemberException If group doesn't have member
	 */
	void removeGroupMembers(final AuthzGroup group, final Collection<AuthzGroupMember> members)
			throws AuthzNotMemberOfGroupException, AuthzNotGroupMemberException;
}
