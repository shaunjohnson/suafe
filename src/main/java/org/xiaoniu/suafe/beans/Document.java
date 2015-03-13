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
package org.xiaoniu.suafe.beans;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.SubversionConstants;
import org.xiaoniu.suafe.UndoConstants;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.utils.StringUtils;
import org.xiaoniu.suafe.validators.Validator;

import java.io.File;
import java.util.*;

/**
 * Reprenents a single Subversion user authentication file.
 *
 * @author Shaun Johnson
 */
public final class Document {

    private static final Log logger = LogFactory.getLog(Document.class);

    /**
     * File encoding.
     */
    private String encoding;

    /**
     * List of all AccessRules.
     */
    protected List<AccessRule> accessRules = null;

    /**
     * Authentication file being edited.
     */
    protected File file = null;

    /**
     * List of all Groups.
     */
    protected List<Group> groups = null;

    /**
     * Indicator whether undo recording is enabled or not.
     */
    protected boolean isUndoEnabled = true;

    /**
     * List of all Paths.
     */
    protected List<Path> paths = null;

    /**
     * List of all Repositories.
     */
    protected List<Repository> repositories = null;

    /**
     * List of undo-able actions
     */
    protected Stack<UndoableAction> undoActions = null;

    /**
     * Indicates whether the document has unsaved changes.
     */
    protected boolean unsavedChanges = false;

    /**
     * List of all Users.
     */
    protected List<User> users = null;

    /**
     * Default constructor.
     */
    public Document() {
        super();

        initialize();
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    /**
     * Adds a new AccessRule specifying group authorization.
     *
     * @param path  The Path in which the Group will have access.
     * @param group The Group that will have access.
     * @param level The level of access.
     * @return The newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addAccessRuleForGroup(Path path, Group group, String level) throws AppException {
        if (path == null) {
            throw new ValidatorException("application.error.pathmissing");
        }

        Validator.validateLevelOfAccess(level);

        if (group == null) {
            throw new ValidatorException("application.error.groupmissing");
        }

        AccessRule accessRule = new AccessRule(path, group, level);

        group.addAccessRule(accessRule);

        if (path != null) {
            path.addAccessRule(accessRule);
        }

        accessRules.add(accessRule);

        setUnsavedChanges();

        return accessRule;
    }

    /**
     * Adds a new AccessRule specifying Group authorization.
     *
     * @param path      The Path in which the Group will have access.
     * @param groupName The name of the Group that will have access.
     * @param level     The level of access.
     * @return The newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addAccessRuleForGroup(Path path, String groupName, String level) throws AppException {
        if (path == null) {
            throw new ValidatorException("application.error.pathmissing");
        }

        Validator.validateGroupName(groupName);
        Validator.validateLevelOfAccess(level);

        Group group = findGroup(groupName);

        setUnsavedChanges();

        return addAccessRuleForGroup(path, group, level);
    }

    /**
     * Adds a new AccessRule specifying group authorization.
     *
     * @param repository Respository to which the rule is applied.
     * @param pathString The Path in which the Group will have access.
     * @param group      The Group that will have access.
     * @param level      The level of access.
     * @return The newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addAccessRuleForGroup(Repository repository, String pathString, Group group, String level)
            throws AppException {
        Path path = addPath(repository, pathString);

        setUnsavedChanges();

        return addAccessRuleForGroup(path, group, level);
    }

    /**
     * Adds a new AccessRule specifying User authorization.
     *
     * @param path     The Path in which the User will have access.
     * @param userName The name of the User that will have access.
     * @param level    The level of access.
     * @return The newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addAccessRuleForUser(Path path, String userName, String level) throws AppException {
        if (path == null) {
            throw new ValidatorException("application.error.pathmissing");
        }

        Validator.validateUserName(userName);
        Validator.validateLevelOfAccess(level);

        User user = addUser(userName);

        setUnsavedChanges();

        return addAccessRuleForUser(path, user, level);
    }

    /**
     * Adds a new AccessRule specifying User authorization.
     *
     * @param path  The Path in which the User will have access.
     * @param user  The User that will have access.
     * @param level The level of access.
     * @return The newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addAccessRuleForUser(Path path, User user, String level) throws AppException {
        if (path == null) {
            throw new ValidatorException("application.error.pathmissing");
        }

        Validator.validateLevelOfAccess(level);

        AccessRule accessRule = new AccessRule(path, user, level);

        user.addAccessRule(accessRule);

        if (path != null) {
            path.addAccessRule(accessRule);
        }

        accessRules.add(accessRule);

        setUnsavedChanges();

        return accessRule;
    }

    /**
     * Adds a new AccessRule specifying User authorization.
     *
     * @param repository Respository to which the rule is applied
     * @param pathString The Path in which the User will have access.
     * @param user       The User that will have access.
     * @param level      The level of access.
     * @return The newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addAccessRuleForUser(Repository repository, String pathString, User user, String level)
            throws AppException {
        Path path = addPath(repository, pathString);

        setUnsavedChanges();

        return addAccessRuleForUser(path, user, level);
    }

    /**
     * Adds a new Group if one by the same name doesn't already exist. If an existing group exists it will be returned
     * intact.
     *
     * @param groupName Name of the Group.
     * @return Newly created or found Group.
     * @throws AppException
     */
    public Group addGroup(String groupName) throws AppException {
        Validator.validateGroupName(groupName);

        Group group = findGroup(groupName);

        if (group == null) {
            List<Group> groupMemberList = new ArrayList<Group>();
            List<User> userMemberList = new ArrayList<User>();

            group = new Group(groupName, groupMemberList, userMemberList);

            groups.add(group);
        }

        setUnsavedChanges();

        // Record action
        UndoableAction action = new UndoableAction(ActionConstants.ADD_GROUP_ACTION);
        action.addValue(UndoConstants.VALUE_NEW_GROUP_NAME, groupName);
        addUndoAction(action);

        return group;
    }

    public Group addGroup(String groupName, List<Group> groupMembers, List<User> userMembers) throws AppException {
        Validator.validateGroupName(groupName);

        Group group = findGroup(groupName);

        if (group == null) {
            List<Group> groupMemberList = new ArrayList<Group>();
            List<User> userMemberList = new ArrayList<User>();

            group = new Group(groupName, groupMemberList, userMemberList);

            // Add Group members
            if (groupMembers != null) {
                for (Group member : groupMembers) {
                    member.addGroup(group);
                    groupMemberList.add(member);
                }
            }

            // Add User members
            if (userMembers != null) {
                for (User member : userMembers) {
                    member.addGroup(group);
                    userMemberList.add(member);
                }
            }

            groups.add(group);

            // Record action
            UndoableAction action = new UndoableAction(ActionConstants.ADD_GROUP_ACTION);
            action.addValue(UndoConstants.VALUE_NEW_GROUP_NAME, groupName);
            addUndoAction(action);
        }

        setUnsavedChanges();

        return group;
    }

    public Group addGroupByName(String groupName, List<String> groupMemberNames, List<String> userMemberNames, List<String> aliasMemberNames)
            throws AppException {
        Validator.validateGroupName(groupName);

        Group group = findGroup(groupName);

        if (group == null) {
            group = new Group(groupName);
            addMembersByName(group, groupMemberNames, userMemberNames, aliasMemberNames);
            groups.add(group);
        }

        setUnsavedChanges();

        // Record action
        UndoableAction action = new UndoableAction(ActionConstants.ADD_GROUP_ACTION);
        action.addValue(UndoConstants.VALUE_NEW_GROUP_NAME, groupName);
        addUndoAction(action);

        return group;
    }

    public void addMembersByName(Group group, List<String> groupMemberNames, List<String> userMemberNames, List<String> aliasMemberNames)
            throws AppException {
        List<Group> groupMemberList = group.getGroupMembers();
        List<User> userMemberList = group.getUserMembers();

        // Add Group members
        if (groupMemberNames != null) {
            for (String groupMemberName : groupMemberNames) {
                Group member = addGroup(groupMemberName);
                member.addGroup(group);
                groupMemberList.add(member);
            }
        }

        // Add User members
        if (userMemberNames != null) {
            for (String userMemberName : userMemberNames) {
                User member = addUser(userMemberName);
                member.addGroup(group);
                userMemberList.add(member);
            }
        }

        // Add Alias members
        if (aliasMemberNames != null) {
            for (String aliasMemberName : aliasMemberNames) {
                User member = this.findUserByAlias(aliasMemberName);

                if (member == null) {
                    throw new AppException("Alias is not defined");
                }

                member.addGroup(group);
                userMemberList.add(member);
            }
        }
    }

    /**
     * Adds a new Path if one by in the same repository and path doesn't exist. Returns the found path if a matching one
     * is found.
     *
     * @param repository   Repository where the path exists.
     * @param relativePath Relative path within the Repository.
     * @return The newly create or found Path.
     * @throws AppException
     */
    public Path addPath(Repository repository, String relativePath) throws AppException {
        Validator.validatePath(relativePath);

        Path path = findPath(repository, relativePath);

        if (path == null) {
            path = new Path(repository, relativePath);

            paths.add(path);

            if (repository != null) {
                repository.addPath(path);
            }
        }

        setUnsavedChanges();

        return path;
    }

    /**
     * Adds a new Repository if one by the same name doesn't exist. Returns the found Repository if a matching one is
     * found.
     *
     * @param repositoryName Name of the Repository.
     * @return The newly created or found Repository.
     * @throws AppException
     */
    public Repository addRepository(String repositoryName) throws AppException {
        Validator.validateRepositoryName(repositoryName);

        Repository repository = findRepository(repositoryName);

        if (repository == null) {
            repository = new Repository(repositoryName);

            repositories.add(repository);
        }

        setUnsavedChanges();

        // Record action
        UndoableAction action = new UndoableAction(ActionConstants.ADD_REPOSITORY_ACTION);
        action.addValue(UndoConstants.VALUE_NEW_REPOSITORY_NAME, repositoryName);
        addUndoAction(action);

        return repository;
    }

    /**
     * Adds a new server level AccessRule specifying Group authorization.
     *
     * @param groupName Name of the Group that will have access.
     * @param level     The level of access that is allowed.
     * @return Newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addServerAccessRuleForGroup(String groupName, String level) throws AppException {
        Validator.validateGroupName(groupName);
        Validator.validateLevelOfAccess(level);

        Group group = findGroup(groupName);

        if (group == null) {
            throw new ValidatorException("application.error.groupmissing");
        }

        Path path = addPath(null, "/");

        AccessRule accessRule = new AccessRule(path, group, level);

        path.addAccessRule(accessRule);

        accessRules.add(accessRule);
        group.addAccessRule(accessRule);

        setUnsavedChanges();

        return accessRule;
    }

    /**
     * Adds a new server level AccessRule specifying User authorization.
     *
     * @param userName Name of the User that will have access.
     * @param level    The level of access that is allowed.
     * @return Newly created AccessRule.
     * @throws AppException
     */
    public AccessRule addServerAccessRuleForUser(String userName, String level) throws AppException {
        Validator.validateUserName(userName);
        Validator.validateLevelOfAccess(level);

        User user = addUser(userName);

        Path path = addPath(null, "/");

        AccessRule accessRule = new AccessRule(path, user, level);

        path.addAccessRule(accessRule);

        accessRules.add(accessRule);
        user.addAccessRule(accessRule);

        setUnsavedChanges();

        return accessRule;
    }

    /**
     * Adds an undo-able action to the stack if undo is enabled.
     *
     * @param action Action to add to the stack if undo is enabled
     */
    private void addUndoAction(UndoableAction action) {
        if (isUndoEnabled()) {
            undoActions.add(action);
        }
    }

    /**
     * Adds a new User to the document.
     *
     * @param userName Name of the new user.
     * @return Newly created User.
     * @throws AppException
     */
    public User addUser(String userName) throws AppException {
        return addUser(userName, null);
    }

    /**
     * Adds a new User to the document.
     *
     * @param userName Name of the new user.
     * @param alias    Alias of the new user.
     * @return Newly created User.
     * @throws AppException
     */
    public User addUser(String userName, String alias) throws AppException {
        Validator.validateUserName(userName);

        if (StringUtils.isNotBlank(alias)) {
            Validator.validateAlias(alias);
        }

        User user = findUser(userName);

        if (user == null) {
            user = new User(userName, alias);

            users.add(user);

            setUnsavedChanges();

            // Record action
            UndoableAction action = new UndoableAction(ActionConstants.ADD_USER_ACTION);
            action.addValue(UndoConstants.VALUE_NEW_USER_NAME, userName);
            action.addValue(UndoConstants.VALUE_NEW_ALIAS, alias);
            addUndoAction(action);
        }
        else {
            String userAlias = user.getAlias();

            if (!ObjectUtils.equals(userAlias, alias)) {
                throw new AppException("User already exists, but with a different alias");
            }
        }

        return user;
    }

    /**
     * Changes the list of members of a Group. Current Group members are removed. All new Group and User members are
     * added.
     *
     * @param group        Group to be processed.
     * @param groupMembers New group memebers.
     * @param userMembers  New User members.
     * @throws AppException
     */
    public void changeGroupMembers(Group group, Vector<Group> groupMembers, Vector<User> userMembers)
            throws AppException {
        if (group == null) {
            throw new ValidatorException("application.error.groupmissing");
        }

        if (groupMembers == null) {
            throw new ValidatorException("application.error.membergroupsmissing");
        }

        if (userMembers == null) {
            throw new ValidatorException("application.error.memberusersmissing");
        }

        checkForCircularReference(group, groupMembers);

        removeGroupMembers(group);

        for (Group member : groupMembers) {
            member.addGroup(group);
            group.addGroupMember(member);
        }

        for (User member : userMembers) {
            member.addGroup(group);
            group.addUserMember(member);
        }

        setUnsavedChanges();
    }

    /**
     * Updates a User's group membership. User is removed from all Groups he is currently a member of and is made a
     * member of all specified Groups.
     *
     * @param user            User to be processed.
     * @param newGroupObjects Vector of new Group membership.
     * @throws AppException
     */
    public void changeUserMembership(User user, Vector<Group> newGroupObjects) throws AppException {
        if (user == null) {
            throw new ValidatorException("application.error.usermissing");
        }

        if (newGroupObjects == null) {
            throw new ValidatorException("application.error.groupsmissing");
        }

        // Remove from old groups
        removeUserFromAssignedGroups(user);

        // Add to new groups
        List<Group> newGroups = new ArrayList<Group>(newGroupObjects.size());

        for (Group group : newGroupObjects) {
            group.addUserMember(user);
            newGroups.add(group);
        }

        user.setGroups(newGroups);

        setUnsavedChanges();
    }

    public void checkForCircularReference(Group group, Collection<Group> groupMembers) throws AppException {
        if (group == null) {
            throw new ValidatorException("application.error.groupmissing");
        }

        if (groupMembers == null) {
            throw new ValidatorException("application.error.groupsmissing");
        }

        Group results = hasCircularReference(group, groupMembers);

        if (results != null) {
            Object[] args = new Object[2];

            args[0] = results;
            args[1] = group;

            throw new AppException("application.error.circularreference", args);
        }
    }

    /**
     * Removes all actions from the undo stack.
     */
    public void clearUndoStack() {
        undoActions.clear();
    }

    /**
     * Clones an existing Group. Creates a complete duplicate of the specified Group and gives it the specified name.
     *
     * @param group     Group to be cloned.
     * @param groupName Name of the new clone.
     * @return Clone of the Group.
     * @throws AppException
     */
    public Group cloneGroup(Group group, String groupName) throws AppException {
        if (findGroup(groupName) != null) {
            throw new AppException("clonegroup.error.groupalreadyexists");
        }

        List<Group> groupMembers = (group == null) ? null : group.getGroupMembers();
        List<User> userMembers = (group == null) ? null : group.getUserMembers();

        Group clone = addGroup(groupName, groupMembers, userMembers);

        for (Group groupObject : group.getGroups()) {
            groupObject.addGroupMember(clone);
            clone.addGroup(group);
        }

        for (AccessRule rule : group.getAccessRules()) {
            addAccessRuleForGroup(rule.getPath(), clone, rule.getLevel());
        }

        setUnsavedChanges();

        // Record action
        UndoableAction action = new UndoableAction(ActionConstants.CLONE_GROUP_ACTION);
        action.addValue(UndoConstants.VALUE_NEW_GROUP_NAME, groupName);
        addUndoAction(action);

        return clone;
    }

    /**
     * Clones an existing User. Creates a complete duplicate of the specified User and gives it the specified name.
     *
     * @param user     User to be cloned.
     * @param userName Name of the new clone.
     * @return Clone of the User.
     * @throws AppException
     */
    public User cloneUser(User user, String userName) throws AppException {
        return cloneUser(user, userName, null);
    }

    /**
     * Clones an existing User. Creates a complete duplicate of the specified User and gives it the specified name.
     *
     * @param user     User to be cloned.
     * @param userName Name of the new clone.
     * @param alias    Alias of the new clone.
     * @return Clone of the User.
     * @throws AppException
     */
    public User cloneUser(User user, String userName, String alias) throws AppException {
        if (findUser(userName) != null) {
            throw new AppException("cloneuser.error.useralreadyexists");
        }

        if (alias != null && findUserByAlias(alias) != null) {
            throw new AppException("cloneuser.error.aliasalreadyexists");
        }

        User clone = addUser(userName);

        for (Group group : user.getGroups()) {
            group.addUserMember(clone);
            clone.addGroup(group);
        }

        for (AccessRule rule : user.getAccessRules()) {
            addAccessRuleForUser(rule.getPath(), clone, rule.getLevel());
        }

        setUnsavedChanges();

        // Record action
        UndoableAction action = new UndoableAction(ActionConstants.CLONE_USER_ACTION);
        action.addValue(UndoConstants.VALUE_NEW_USER_NAME, userName);
        addUndoAction(action);

        return clone;
    }

    /**
     * Deletes an existing AccessRule. Either group or user must be specified, but in no instances should both be
     * provided.
     *
     * @param repositoryName Name of Repository to which the rule applies.
     * @param pathString     Path in the repository to which the rule applies.
     * @param group          Group to which the rule applies.
     * @param user           User to which the rule applies.
     * @throws AppException
     */
    public void deleteAccessRule(String repositoryName, String pathString, Group group, User user) throws AppException {
        Repository repository = null;
        AccessRule accessRule = null;

        if (repositoryName != null) {
            repository = findRepository(repositoryName);
        }

        if (group != null) {
            accessRule = findGroupAccessRule(repository, pathString, group);

            if (accessRule == null) {
                throw new AppException("application.error.unabletofindrule");
            }

            group.removeAccessRule(accessRule);
        }
        else if (user != null) {
            accessRule = findUserAccessRule(repository, pathString, user);

            if (accessRule == null) {
                throw new AppException("application.error.unabletofindrule");
            }

            user.removeAccessRule(accessRule);
        }

        accessRule.getPath().removeAccessRule(accessRule);
        accessRules.remove(accessRule);

        setUnsavedChanges();
    }

    /**
     * Deletes an existing Group. Deletes all Group AccessRules.
     *
     * @param group Group to be removed.
     * @throws AppException
     */
    public void deleteGroup(Group group) throws AppException {
        logger.debug("in deleteGroup()");

        deleteGroupAccessRules(group);
        removeGroupMembers(group);
        groups.remove(group);

        setUnsavedChanges();

        logger.debug("out deleteGroup()");
    }

    /**
     * Deletes an existing Group by name.
     *
     * @param groupName Name of group to be deleted.
     * @throws AppException
     */
    public void deleteGroup(String groupName) throws AppException {
        deleteGroup(findGroup(groupName));
    }

    /**
     * Deletes all AccessRules that apply to a specific Group.
     *
     * @param group Group whose AccessRules are to be deleted.
     */
    private void deleteGroupAccessRules(Group group) {
        logger.debug("in deleteGroupAccessRules()");

        List<AccessRule> deleteList = new ArrayList<AccessRule>();

        for (AccessRule rule : accessRules) {
            if (rule.getGroup() != null && rule.getGroup().equals(group)) {
                logger.debug("remove group " + rule.getGroup().getName() + " from " + rule);

                deleteList.add(rule);
                group.removeAccessRule(rule);
                rule.getPath().removeAccessRule(rule);
            }
        }

        accessRules.removeAll(deleteList);

        setUnsavedChanges();

        logger.debug("out deleteGroupAccessRules()");
    }

    /**
     * Delete all groups in array.
     *
     * @param groups Array of Groups to be deleted.
     * @throws AppException
     */
    public void deleteGroups(Object[] groups) throws AppException {
        if (groups == null) {
            throw new ValidatorException("application.error.groupsmissing");
        }

        for (Object group : groups) {
            deleteGroup((Group) group);
        }

        setUnsavedChanges();
    }

    /**
     * Deletes an existing path.
     *
     * @param path Path to be deleted.
     * @throws AppException
     */
    public void deletePath(Path path) throws AppException {
        if (path == null) {
            throw new ValidatorException("application.error.pathmissing");
        }

        deletePathAccessRules(path);

        if (path.getRepository() != null) {
            path.getRepository().removePath(path);
        }

        paths.remove(path);

        setUnsavedChanges();
    }

    /**
     * Deletes all AccessRules for a path.
     *
     * @param path Path whose AccessRules are to be deleted.
     */
    private void deletePathAccessRules(Path path) {
        List<AccessRule> deleteList = new ArrayList<AccessRule>();

        for (AccessRule rule : accessRules) {
            if (rule.getPath() != null && rule.getPath().equals(path)) {
                deleteList.add(rule);

                if (rule.getGroup() != null) {
                    rule.getGroup().removeAccessRule(rule);
                }

                if (rule.getUser() != null) {
                    rule.getUser().removeAccessRule(rule);
                }
            }
        }

        accessRules.removeAll(deleteList);

        setUnsavedChanges();
    }

    /**
     * Deletes array of Repositories.
     *
     * @param repositories Repositories to be deleted.
     * @throws AppException
     */
    public void deleteRepositories(Object[] repositories) throws AppException {
        if (repositories == null) {
            throw new ValidatorException("application.error.repositoriesmissing");
        }

        for (Object repositorie : repositories) {
            deleteRepository((Repository) repositorie);
        }

        setUnsavedChanges();
    }

    /**
     * Delete an existing repository.
     *
     * @param repository Repository to be deleted.
     * @throws AppException
     */
    public void deleteRepository(Repository repository) throws AppException {
        deleteRepositoryAccessRules(repository);
        deleteRepositoryPaths(repository);
        repositories.remove(repository);

        setUnsavedChanges();
    }

    /**
     * Delete an existing repository by name.
     *
     * @param repositoryName Name of repository to be deleted.
     * @throws AppException
     */
    public void deleteRepository(String repositoryName) throws AppException {
        deleteRepository(findRepository(repositoryName));
    }

    /**
     * Deletes all AccessRules for a Repository.
     *
     * @param repository Repository whose AccessRules are to be deleted.
     */
    private void deleteRepositoryAccessRules(Repository repository) {
        List<AccessRule> deleteList = new ArrayList<AccessRule>();

        for (AccessRule rule : accessRules) {
            if (rule.getPath().getRepository() != null && rule.getPath().getRepository().equals(repository)) {
                deleteList.add(rule);

                if (rule.getGroup() != null) {
                    rule.getGroup().removeAccessRule(rule);
                }

                if (rule.getUser() != null) {
                    rule.getUser().removeAccessRule(rule);
                }
            }
        }

        accessRules.removeAll(deleteList);

        setUnsavedChanges();
    }

    /**
     * Deletes all Paths for a Repository.
     *
     * @param repository Repository whose Paths are to be deleted.
     * @throws AppException
     */
    private void deleteRepositoryPaths(Repository repository) throws AppException {
        if (repository == null) {
            throw new ValidatorException("application.error.repositorymissing");
        }

        List<Path> deleteList = new ArrayList<Path>();

        for (Path path : repository.getPaths()) {
            deleteList.add(path);
            deletePathAccessRules(path);
        }

        paths.removeAll(deleteList);

        setUnsavedChanges();
    }

    /**
     * Deletes an existing User by name.
     *
     * @param userName Name of user to be deleted.
     * @throws AppException
     */
    public void deleteUser(String userName) throws AppException {
        deleteUser(findUser(userName));
    }

    /**
     * Deletes an existing User.
     *
     * @param user User to be deleted.
     * @throws AppException
     */
    public void deleteUser(User user) throws AppException {
        deleteUserAccessRules(user);
        removeUserFromAssignedGroups(user);
        users.remove(user);

        setUnsavedChanges();
    }

    /**
     * Deletes all AccessRules for a user.
     *
     * @param user User whose AccessRules are to be deleted.
     */
    private void deleteUserAccessRules(User user) {
        List<AccessRule> deleteList = new ArrayList<AccessRule>();

        for (AccessRule rule : accessRules) {
            if (rule.getUser() != null && rule.getUser().equals(user)) {
                // Remove access rule from path
                rule.getPath().removeAccessRule(rule);

                // Add rule to list for deletion1
                deleteList.add(rule);
            }
        }

        accessRules.removeAll(deleteList);

        setUnsavedChanges();
    }

    /**
     * Deletes an existing User by alias.
     *
     * @param alias Alias of user to be deleted.
     * @throws AppException
     */
    public void deleteUserByAlias(String alias) throws AppException {
        deleteUser(findUserByAlias(alias));
    }

    /**
     * Deletes an array of Users.
     *
     * @param users Users to be deleted.
     * @throws AppException
     */
    public void deleteUsers(Object[] users) throws AppException {
        if (users == null) {
            throw new ValidatorException("application.error.usersmissing");
        }

        for (Object user : users) {
            deleteUser((User) user);
        }

        setUnsavedChanges();
    }

    /**
     * Disable undo recording.
     */
    public void disableUndo() {
        isUndoEnabled = false;
    }

    /**
     * Enable undo recording.
     */
    public void enableUndo() {
        isUndoEnabled = true;
    }

    /**
     * Locates an existing Group by name.
     *
     * @param groupName Name of Group to find.
     * @return Found group.
     * @throws AppException
     */
    public Group findGroup(String groupName) throws AppException {
        Validator.validateGroupName(groupName);

        Group group = null;
        boolean found = false;

        if (groups != null) {
            int size = groups.size();

            for (int i = 0; i < size; i++) {
                group = groups.get(i);

                if (group.getName().equals(groupName)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                group = null;
            }
        }

        return group;
    }

    /**
     * Locates an AccessRules by Repository, path and Group.
     *
     * @param repository Respository referenced by AccessRule.
     * @param pathString Path referenced by AccessRule.
     * @param group      Group referenced by AccessRule.
     * @return Found AccessRule.
     * @throws AppException
     */
    public AccessRule findGroupAccessRule(Repository repository, String pathString, Group group) throws AppException {
        if (accessRules == null) {
            return null;
        }

        Path path = findPath(repository, pathString);

        if (path == null || group == null || group.getAccessRules() == null) {
            return null;
        }

        AccessRule foundRule = null;

        for (AccessRule rule : group.getAccessRules()) {
            if (rule.getPath() == path) {
                foundRule = rule;
                break;
            }
        }

        return foundRule;
    }

    /**
     * Locates a Path by Repository and path.
     *
     * @param repository Repository referenced by Path.
     * @param pathString Path referenced by Path.
     * @return Found Path.
     * @throws AppException
     */
    public Path findPath(Repository repository, String pathString) throws AppException {
        Validator.validatePath(pathString);

        Path path = null;
        boolean found = false;

        if (paths != null) {
            int size = paths.size();

            for (int i = 0; i < size; i++) {
                path = paths.get(i);

                if (path.getRepository() == repository && path.getPath().equals(pathString)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                path = null;
            }
        }

        return path;
    }

    /**
     * Locates a Path by repository and path.
     *
     * @param repositoryName Name of repository referenced by Path.
     * @param pathString     Path referenced by Path.
     * @return Found Path.
     * @throws AppException
     */
    public Path findPath(String repositoryName, String pathString) throws AppException {
        Repository repository = findRepository(repositoryName);

        return findPath(repository, pathString);
    }

    /**
     * Locates a repository by name.
     *
     * @param repositoryName Name of Repository to locate.
     * @return Found Repository.
     * @throws AppException
     */
    public Repository findRepository(String repositoryName) throws AppException {
        Validator.validateRepositoryName(repositoryName);

        Repository repository = null;
        boolean found = false;

        if (repositories != null) {
            int size = repositories.size();

            for (int i = 0; i < size; i++) {
                repository = repositories.get(i);

                if (repository.getName().equals(repositoryName)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                repository = null;
            }
        }

        return repository;
    }

    /**
     * Locates a server level Path.
     *
     * @param pathString Path referenced by the Path.
     * @return Found Path.
     * @throws AppException
     */
    public Path findServerPath(String pathString) throws AppException {
        Path path = null;
        boolean found = false;

        if (paths != null) {
            int size = paths.size();

            for (int i = 0; i < size; i++) {
                path = paths.get(i);

                if (path.getRepository() == null && path.getPath().equals(pathString)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                path = null;
            }
        }

        return path;
    }

    /**
     * Finds a User by name.
     *
     * @param userName Name of User to locate.
     * @return Found User.
     * @throws AppException
     */
    public User findUser(String userName) throws AppException {
        Validator.validateUserName(userName);

        User user = null;
        boolean found = false;

        if (users != null) {
            int size = users.size();

            for (int i = 0; i < size; i++) {
                user = users.get(i);

                if (user.getName().equals(userName)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                user = null;
            }
        }

        return user;
    }

    /**
     * Locates an AccessRule by Repository, path and User
     *
     * @param repository Repository referenced by AccessRule.
     * @param pathString Path referenced by AccessRule.
     * @param user       User referenced by AccessRule.
     * @return Found AccessRule.
     * @throws AppException
     */
    public AccessRule findUserAccessRule(Repository repository, String pathString, User user) throws AppException {
        if (accessRules == null) {
            return null;
        }

        Path path = findPath(repository, pathString);

        if (path == null || user == null || user.getAccessRules() == null) {
            return null;
        }

        AccessRule foundRule = null;

        for (AccessRule rule : user.getAccessRules()) {
            if (rule.getPath() == path) {
                foundRule = rule;
                break;
            }
        }

        return foundRule;
    }

    /**
     * Finds a User by alias.
     *
     * @param alias Alias of User to locate.
     * @return Found User.
     * @throws AppException
     */
    public User findUserByAlias(String alias) throws AppException {
        if (StringUtils.isBlank(alias)) {
            return null;
        }

        Validator.validateAlias(alias);

        User user = null;
        boolean found = false;

        if (users != null) {
            int size = users.size();

            for (int i = 0; i < size; i++) {
                user = users.get(i);

                if (user.getAlias() != null && user.getAlias().equals(alias)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                user = null;
            }
        }

        return user;
    }

    /**
     * Retrieves a list of all AccessRule objects.
     *
     * @return List of AccessRule objects
     */
    public List<AccessRule> getAccessRules() {
        return accessRules;
    }

    /**
     * @return Returns the file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets an array of AccessRules data in which the Group is referenced. The two dimensional array returned contains
     * the Repository name, relative path and level of access for each AccessRule in which the Group is referenced.
     *
     * @param group Group.
     * @return Object array of AccessRule information.
     * @throws AppException
     */
    public Object[][] getGroupAccessRules(Group group) throws AppException {
        if (group == null || group.getAccessRules() == null) {
            return null;
        }
        else {
            List<AccessRule> accessRules = group.getAccessRules();
            int size = accessRules.size();
            Object[][] accessRulesList = new Object[size][3];

            for (int i = 0; i < size; i++) {
                AccessRule rule = accessRules.get(i);

                accessRulesList[i][0] = rule.getPath().getRepository();
                accessRulesList[i][1] = rule.getPath();
                accessRulesList[i][2] = rule.getLevelFullName();
            }

            return accessRulesList;
        }
    }

    /**
     * Gets names of all Groups that are a member of a Group.
     *
     * @param groupName Name of Group whose member names are to be returned.
     * @return Names of all Groups members of Group
     * @throws AppException
     */
    public Object[] getGroupMemberGroupNames(String groupName) throws AppException {
        Group group = findGroup(groupName);

        if (group == null || group.getGroupMembers() == null) {
            return null;
        }
        else {
            List<Group> groups = group.getGroupMembers();
            int size = groups.size();
            Object[] groupNames = new Object[size];

            for (int i = 0; i < size; i++) {
                groupNames[i] = groups.get(i).getName();
            }

            Arrays.sort(groupNames);

            return groupNames;
        }
    }

    /**
     * Gets all Groups that are a member of a Group.
     *
     * @param group Group whose Group members are to be returned.
     * @return Group array containg all member Groups.
     * @throws AppException
     */
    public Group[] getGroupMemberGroups(Group group) throws AppException {
        if (group == null || group.getGroupMembers() == null) {
            return null;
        }
        else {
            Collections.sort(group.getGroupMembers());

            return group.getGroupMembers().toArray(new Group[0]);
        }
    }

    /**
     * Gets all Group objects that are a member of a Group.
     *
     * @param group Group whose Group members are to be returned.
     * @return Group object array containg all member Groups.
     * @throws AppException
     */
    public Object[] getGroupMemberObjects(Group group) throws AppException {
        if (group == null || group.getGroupMembers() == null) {
            return null;
        }
        else {
            Collections.sort(group.getGroupMembers());
            Collections.sort(group.getUserMembers());

            List<Object> combinedList = new ArrayList<Object>();

            combinedList.addAll(group.getGroupMembers());
            combinedList.addAll(group.getUserMembers());

            return combinedList.toArray();
        }
    }

    /**
     * Gets names of all of a Group's User members.
     *
     * @param groupName Name of Group whose member names are to be returned.
     * @return Names of all User members of a Group.
     * @throws AppException
     */
    public Object[] getGroupMemberUserNames(String groupName) throws AppException {
        Group group = findGroup(groupName);

        if (group == null || group.getUserMembers() == null) {
            return null;
        }
        else {
            List<User> users = group.getUserMembers();
            int size = users.size();
            Object[] userNames = new Object[size];

            for (int i = 0; i < size; i++) {
                userNames[i] = users.get(i).getName();
            }

            Arrays.sort(userNames);

            return userNames;
        }
    }

    /**
     * Gets all Users that are a member of a Group.
     *
     * @param group Group whose User members are to be returned.
     * @return User array containg all member User.
     * @throws AppException
     */
    public User[] getGroupMemberUsers(Group group) throws AppException {
        if (group == null || group.getUserMembers() == null) {
            return null;
        }
        else {
            Collections.sort(group.getUserMembers());

            return group.getUserMembers().toArray(new User[0]);
        }
    }

    /**
     * Gets an array of all Group names.
     *
     * @return Object array of Group names.
     */
    public Object[] getGroupNames() {
        if (groups == null) {
            return null;
        }
        else {
            int size = groups.size();
            Object[] groupNames = new Object[size];

            for (int i = 0; i < size; i++) {
                groupNames[i] = (groups.get(i)).getName();
            }

            Arrays.sort(groupNames);

            return groupNames;
        }
    }

    /**
     * Gets an array of all Group objects.
     *
     * @return Group object array.
     */
    public Object[] getGroupObjects() {
        if (groups == null) {
            return null;
        }
        else {
            Collections.sort(groups);

            return groups.toArray();
        }
    }

    /**
     * Gets the list of all Groups.
     *
     * @return List of Groups.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Gets an array of all Groups.
     *
     * @return Group array.
     */
    public Group[] getGroupsArray() {
        if (groups == null) {
            return null;
        }
        else {
            Collections.sort(groups);

            return groups.toArray(new Group[0]);
        }
    }

    /**
     * Gets an array of AccessRules data for the AccessRules defined for the Path. The two dimensional array returned
     * contains the User or Group name and level of access for each AccessRule defined for the Path.
     *
     * @param path Relative path within the Repository.
     * @return Object array of AccessRule information.
     * @throws AppException
     */
    public Object[][] getPathAccessRules(Path path) throws AppException {
        if (path == null || path.getAccessRules() == null) {
            return null;
        }
        else {
            List<AccessRule> accessRules = path.getAccessRules();
            int size = accessRules.size();
            Object[][] accessRulesList = new Object[size][2];

            for (int i = 0; i < size; i++) {
                AccessRule rule = accessRules.get(i);

                if (rule.getGroup() == null) {
                    accessRulesList[i][0] = rule.getUser();
                }
                else {
                    accessRulesList[i][0] = rule.getGroup();
                }

                accessRulesList[i][1] = rule.getLevelFullName();
            }

            return accessRulesList;
        }
    }

    public List<Path> getPaths() {
        return paths;
    }

    /**
     * Gets list of all Repositories.
     *
     * @return List of Repositories.
     */
    public List<Repository> getRepositories() {
        return repositories;
    }

    /**
     * Gets an array of AccessRules data in which the Repository is referenced. The two dimensional array returned
     * contains the relative path, User or Group name and level of access for each AccessRule in which the Repostiory is
     * referenced.
     *
     * @param repository Repository.
     * @return Object array of AccessRule information.
     * @throws AppException
     */
    public Object[][] getRepositoryAccessRules(Repository repository) throws AppException {
        if (repository == null || repository.getPaths() == null) {
            return null;
        }
        else {
            List<AccessRule> accessRules = new ArrayList<AccessRule>();

            for (Path path : repository.getPaths()) {
                if (path.getAccessRules() != null) {
                    for (AccessRule rule : path.getAccessRules()) {
                        accessRules.add(rule);
                    }
                }
            }

            int size = accessRules.size();
            Object[][] accessRulesList = new Object[size][3];

            for (int i = 0; i < size; i++) {
                AccessRule rule = accessRules.get(i);

                accessRulesList[i][0] = rule.getPath();

                if (rule.getGroup() == null) {
                    accessRulesList[i][1] = rule.getUser();
                }
                else {
                    accessRulesList[i][1] = rule.getGroup();
                }

                accessRulesList[i][2] = rule.getLevelFullName();
            }

            return accessRulesList;
        }
    }

    /**
     * Gets list of all Repository names.
     *
     * @return Object array of names.
     */
    public Object[] getRepositoryNames() {
        if (repositories == null) {
            return null;
        }
        else {
            int size = repositories.size();
            Object[] repositoryNames = new Object[size];

            for (int i = 0; i < size; i++) {
                repositoryNames[i] = (repositories.get(i)).getName();
            }

            Arrays.sort(repositoryNames);

            return repositoryNames;
        }
    }

    /**
     * Gets array of repository objects.
     *
     * @return Array of Repository objects.
     */
    public Object[] getRepositoryObjects() {
        if (repositories == null) {
            return null;
        }
        else {
            Collections.sort(repositories);

            return repositories.toArray();
        }
    }

    /**
     * Gets all server AccessRule data as two-dimensional array.
     * <p/>
     * Array contents: [0] - Repository object [1] - Path object [2] - User or Group object [3] - Full text name of
     * access level
     *
     * @return Two-dimensional array of AccessRule data.
     * @throws AppException
     */
    public Object[][] getServerAccessRules() throws AppException {
        if (accessRules == null) {
            return null;
        }
        else {
            Collections.sort(accessRules);

            int size = accessRules.size();
            Object[][] accessRulesList = new Object[size][4];

            for (int i = 0; i < size; i++) {
                AccessRule rule = accessRules.get(i);

                accessRulesList[i][0] = rule.getPath().getRepository();

                accessRulesList[i][1] = rule.getPath();

                if (rule.getGroup() == null) {
                    accessRulesList[i][2] = rule.getUser();
                }
                else {
                    accessRulesList[i][2] = rule.getGroup();
                }

                accessRulesList[i][3] = rule.getLevelFullName();
            }

            return accessRulesList;
        }
    }

    /**
     * Gets all AccessRule data for rules assigned to a User.
     *
     * @param user User whose rules are to be returned.
     * @return Two-dimensional array of AccessRule data.
     * @throws AppException
     */
    public Object[][] getUserAccessRuleObjects(User user) throws AppException {
        if (user == null || user.getAccessRules() == null) {
            return null;
        }
        else {
            List<AccessRule> accessRules = user.getAccessRules();
            int size = accessRules.size();
            Object[][] accessRulesList = new Object[size][3];

            for (int i = 0; i < size; i++) {
                AccessRule rule = accessRules.get(i);

                accessRulesList[i][0] = rule.getPath().getRepository();
                accessRulesList[i][1] = rule.getPath();
                accessRulesList[i][2] = rule.getLevelFullName();
            }

            return accessRulesList;
        }
    }

    /**
     * Gets an array of AccessRules data in which the User is referenced. The two dimensional array returned contains
     * the Repository name, relative path and level of access for each AccessRule in which the User is referenced.
     *
     * @param userName Name of the User.
     * @return Object array of AccessRule information.
     * @throws AppException
     */
    public Object[][] getUserAccessRules(String userName) throws AppException {
        return getUserAccessRules(findUser(userName));
    }

    /**
     * Gets an array of AccessRules data in which the User is referenced. The two dimensional array returned contains
     * the Repository name, relative path and level of access for each AccessRule in which the User is referenced.
     *
     * @param user The User.
     * @return Object array of AccessRule information.
     * @throws AppException
     */
    private Object[][] getUserAccessRules(User user) throws AppException {
        if (user == null || user.getAccessRules() == null) {
            return null;
        }
        else {
            List<AccessRule> accessRules = user.getAccessRules();
            int size = accessRules.size();
            Object[][] accessRulesList = new Object[size][3];

            for (int i = 0; i < size; i++) {
                AccessRule rule = accessRules.get(i);

                accessRulesList[i][0] = rule.getPath().getRepository();
                accessRulesList[i][1] = rule.getPath();
                accessRulesList[i][2] = rule.getLevelFullName();
            }

            return accessRulesList;
        }
    }

    /**
     * Gets an array of AccessRules data in which the User is referenced. The two dimensional array returned contains
     * the Repository name, relative path and level of access for each AccessRule in which the User is referenced.
     *
     * @param alias Alias of the User.
     * @return Object array of AccessRule information.
     * @throws AppException
     */
    public Object[][] getUserAccessRulesByAlias(String alias) throws AppException {
        return getUserAccessRules(findUserByAlias(alias));
    }

    /**
     * Gets an array of names for all Groups in which the User is a member.
     *
     * @param userName Name of the User.
     * @return Object array of Group names.
     * @throws AppException
     */
    public Object[] getUserGroupNames(String userName) throws AppException {
        return getUserGroupNames(findUser(userName));
    }

    /**
     * Gets an array of names for all Groups in which the User is a member.
     *
     * @param user The User.
     * @return Object array of Group names.
     * @throws AppException
     */
    public Object[] getUserGroupNames(User user) throws AppException {
        if (user == null || user.getGroups() == null) {
            return null;
        }
        else {
            List<Group> groups = user.getGroups();
            int size = groups.size();
            Object[] groupNames = new Object[size];

            for (int i = 0; i < size; i++) {
                groupNames[i] = groups.get(i).getName();
            }

            Arrays.sort(groupNames);

            return groupNames;
        }
    }

    /**
     * Gets an array of names for all Groups in which the User is a member.
     *
     * @param alias Alias of the User.
     * @return Object array of Group names.
     * @throws AppException
     */
    public Object[] getUserGroupNamesByAlias(String alias) throws AppException {
        return getUserGroupNames(findUserByAlias(alias));
    }

    /**
     * Gets array of Group objects in which the User is a member.
     *
     * @param user User whose Group objects are to be returned.
     * @return Array of Group objects in which the User is a member.
     * @throws AppException
     */
    public Object[] getUserGroupObjects(User user) throws AppException {
        if (user == null || user.getGroups() == null) {
            return null;
        }
        else {
            List<Group> groups = user.getGroups();

            Collections.sort(groups);

            return groups.toArray();
        }
    }

    /**
     * Gets array of Groups in which the User is a member.
     *
     * @param user User whose Groups are to be returned
     * @return Array of Groups in which the User is a member.
     * @throws AppException
     */
    public Group[] getUserGroupsArray(User user) throws AppException {
        if (user == null || user.getGroups() == null) {
            return null;
        }
        else {
            List<Group> groups = user.getGroups();

            Collections.sort(groups);

            return groups.toArray(new Group[0]);
        }
    }

    /**
     * Gets names of all Users.
     *
     * @return Array of User name objects.
     */
    public Object[] getUserNames() {
        if (users == null) {
            return null;
        }
        else {
            int size = users.size();
            Object[] userNames = new Object[size];

            for (int i = 0; i < size; i++) {
                userNames[i] = (users.get(i)).getName();
            }

            Arrays.sort(userNames);

            return userNames;
        }
    }

    /**
     * Gets all Users as object array.
     *
     * @return Array of all User objects.
     */
    public Object[] getUserObjects() {
        if (users == null) {
            return null;
        }
        else {
            Collections.sort(users);

            return users.toArray();
        }
    }

    /**
     * Gets all Users, except * as object array. The user name * represents "all users". This method returns all Users
     * except this special one.
     *
     * @return Array of User objects.
     * @throws AppException
     */
    public User[] getUserObjectsExcludeAllUsers() throws AppException {
        if (users == null) {
            return null;
        }
        else {
            List<User> filteredUsers = users;
            User allUsers = findUser(SubversionConstants.SVN_ALL_USERS_NAME);

            if (allUsers != null) {
                filteredUsers.remove(allUsers);
            }

            Collections.sort(filteredUsers);

            return filteredUsers.toArray(new User[0]);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    private Group hasCircularReference(Group group, Collection<Group> groupMembers) {
        for (Group member : groupMembers) {
            if (group == member) {
                return group;
            }

            Group results = hasCircularReference(group, member.getGroupMembers());

            if (results != null) {
                return member;
            }
        }

        return null;
    }

    /**
     * Determines if there are any undo actions in the stack.
     *
     * @return true if undo actions exist, otherwise false
     */
    public boolean hasUndoActions() {
        return undoActions.size() > 0;
    }

    /**
     * Determines if the document has any unsaved changes.
     *
     * @return True if there are any unsaved changes.
     */
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    /**
     * Resets all data stored within the document.
     */
    public void initialize() {
        file = null;
        unsavedChanges = false;

        users = new ArrayList<User>();
        groups = new ArrayList<Group>();
        repositories = new ArrayList<Repository>();
        accessRules = new ArrayList<AccessRule>();
        paths = new ArrayList<Path>();
        undoActions = new Stack<UndoableAction>();
        isUndoEnabled = true;
    }

    /**
     * Determines if the Document contains any data. If any Users, Groups, Repositories, Paths and AccessRules are all
     * empty then this returns true.
     *
     * @return true if the Document doesn't contain data.
     */
    public boolean isEmpty() {
        return users.size() == 0 && groups.size() == 0 && repositories.size() == 0 && accessRules.size() == 0
                && paths.size() == 0;
    }

    /**
     * Determines if undo recording is enabled.
     *
     * @return true if undo recording is enabled, otherwise false
     */
    public boolean isUndoEnabled() {
        return isUndoEnabled;
    }

    public void removeFromGroups(User user, Object[] groups) throws AppException {
        for (Object groupObject : groups) {
            if (groupObject instanceof Group) {
                Group group = (Group) groupObject;

                group.removeUserMember(user);
                user.removeGroup(group);
            }
            else {
                throw new AppException("application.erroroccurred");
            }
        }
    }

    /**
     * Removes all members of a Group.
     *
     * @param group Group whose members are to be removed.
     * @throws ValidatorException
     */
    private void removeGroupMembers(Group group) throws ValidatorException {
        if (group == null) {
            throw new ValidatorException("application.error.groupmissing");
        }

        // Remove groups
        for (Group member : group.getGroupMembers()) {
            member.removeGroup(group);
        }

        group.getGroupMembers().clear();

        // Removed users
        for (User member : group.getUserMembers()) {
            member.removeGroup(group);
        }

        group.getUserMembers().clear();

        setUnsavedChanges();
    }

    public void removeGroupMembers(Group group, Object[] members) throws AppException {
        for (Object member : members) {
            if (member instanceof Group) {
                Group groupMember = (Group) member;

                group.removeGroupMember(groupMember);
                groupMember.removeGroup(group);
            }
            else if (member instanceof User) {
                User userMember = (User) member;

                group.removeUserMember(userMember);
                userMember.removeGroup(group);
            }
            else {
                throw new AppException("application.erroroccurred");
            }
        }
    }

    /**
     * Removes a User from all Groups in which he is a member.
     *
     * @param user User to be processed.
     * @throws ValidatorException
     */
    private void removeUserFromAssignedGroups(User user) throws ValidatorException {
        if (user == null) {
            throw new ValidatorException("application.error.usermissing");
        }

        for (Group group : user.getGroups()) {
            group.getUserMembers().remove(user);
        }

        user.setGroups(new ArrayList<Group>());

        setUnsavedChanges();
    }

    /**
     * Renames the provided group to the new groupName.
     *
     * @param user         Group to be renamed
     * @param newGroupName New group name
     * @return Renamed group
     * @throws AppException
     */
    public Group renameGroup(Group group, String newGroupName) throws AppException {
        Validator.validateUserName(newGroupName);

        if (group == null) {
            throw new ValidatorException("application.error.groupmissing");
        }

        group.setName(newGroupName);

        return group;
    }

    /**
     * Renames the provided user to the new userName.
     *
     * @param user        User to be renamed
     * @param newUserName New user name
     * @return Renamed user
     * @throws AppException
     */
    public User renameUser(User user, String newUserName, String alias) throws AppException {
        Validator.validateUserName(newUserName);

        if (user == null) {
            throw new ValidatorException("application.error.usermissing");
        }

        user.setAlias(alias);
        user.setName(newUserName);

        return user;
    }

    /**
     * Resets the unsaved changes flag. Indicates that there are no changes that need to be saved.
     */
    public void resetUnsavedChangesFlag() {
        unsavedChanges = false;
    }

    /**
     * Saves the file object representing the currently edited authz file.
     *
     * @param file The file to set.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Sets the unsaved changes flag to true. Indicates that there are changes not persisted to a file.
     */
    public void setUnsavedChanges() {
        unsavedChanges = true;
    }

    /**
     * Undoes addGroup action.
     *
     * @param action Action undo information
     * @throws AppException
     */
    private void undoAddGroup(UndoableAction action) throws AppException {
        String groupName = (String) action.getValue(UndoConstants.VALUE_NEW_GROUP_NAME);

        deleteGroup(groupName);
    }

    /**
     * Undoes addRepository action.
     *
     * @param action Action undo information
     * @throws AppException
     */
    private void undoAddRepository(UndoableAction action) throws AppException {
        String repositoryName = (String) action.getValue(UndoConstants.VALUE_NEW_REPOSITORY_NAME);

        deleteRepository(repositoryName);
    }

    /**
     * Undoes addUser action.
     *
     * @param action Action undo information
     * @throws AppException
     */
    private void undoAddUser(UndoableAction action) throws AppException {
        String userName = (String) action.getValue(UndoConstants.VALUE_NEW_USER_NAME);

        deleteUser(userName);
    }

    /**
     * Pop item off of undo stack, if not empty, and undo the action
     *
     * @throws AppException
     */
    public void undoLastAction() throws AppException {
        if (hasUndoActions()) {
            UndoableAction action = undoActions.pop();
            String actionCode = action.getAction();

            if (actionCode.equals(ActionConstants.ADD_GROUP_ACTION)
                    || actionCode.equals(ActionConstants.CLONE_GROUP_ACTION)) {
                undoAddGroup(action);
            }
            else if (actionCode.equals(ActionConstants.ADD_REPOSITORY_ACTION)) {
                undoAddRepository(action);
            }
            else if (actionCode.equals(ActionConstants.ADD_USER_ACTION)
                    || actionCode.equals(ActionConstants.CLONE_USER_ACTION)) {
                undoAddUser(action);
            }
            else {
                // TODO Unknown/Unsupported action
            }
        }
    }

    public String validateDocument() {
        StringBuffer buffer = new StringBuffer();

        ArrayList<String> unsavedObjects = new ArrayList<String>();

        for (User user : users) {
            if (user.getGroups().isEmpty() && user.getAccessRules().isEmpty()) {
                unsavedObjects.add(user.getName());
            }
        }

        if (!unsavedObjects.isEmpty()) {
            buffer.append("<p>" + ResourceUtil.getString("document.unreferencedusers") + "</p>");
            buffer.append("<ul>");

            for (String name : unsavedObjects) {
                buffer.append("<li>" + name + "</li>");
            }

            buffer.append("</ul>");

            unsavedObjects.clear();
        }

        for (Repository repository : repositories) {
            if (repository.getPaths().isEmpty()) {
                unsavedObjects.add(repository.getName());
            }
        }

        if (!unsavedObjects.isEmpty()) {
            buffer.append("<p>" + ResourceUtil.getString("document.unreferencedrepos") + "</p>");
            buffer.append("<ul>");

            for (String name : unsavedObjects) {
                buffer.append("<li>" + name + "</li>");
            }

            buffer.append("</ul>");

            unsavedObjects.clear();
        }

        for (Path path : paths) {
            if (path.getAccessRules().isEmpty()) {
                String repositoryName = (path.getRepository() == null) ? "" : path.getRepository().getName();

                unsavedObjects.add(repositoryName + path.getPath());
            }
        }

        if (!unsavedObjects.isEmpty()) {
            buffer.append("<p>" + ResourceUtil.getString("document.unreferencedpaths") + "</p>");
            buffer.append("<ul>");

            for (String name : unsavedObjects) {
                buffer.append("<li>" + name + "</li>");
            }

            buffer.append("</ul>");

            unsavedObjects.clear();
        }

        if (buffer.length() > 0) {
            buffer.append("<p>" + ResourceUtil.getString("document.unreferencedobjectsprompt") + "</p>");
        }

        return (buffer.length() == 0) ? null : "<html>" + buffer.toString() + "</html>";
    }
}