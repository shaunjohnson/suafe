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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Subversion user Group.
 * Groups are persisted regardless of whether they contain users or
 * are referenced by an AccessRule.
 * 
 * @author Shaun Johnson
 */
public class Group extends GroupMemberObject implements Comparable<Group> {
	
	/**
	 * Name of the group.
	 */
	protected String name;
	
	/**
	 * List of Groups in which this Group is a member.
	 */
	protected List<Group> groups;
	
	/**
	 * List of Users that are a member of the Group.
	 */
	protected List<User> userMembers;
	
	/**
	 * List of Groups that are a member of the Group.
	 */
	protected List<Group> groupMembers;
	
	/**
	 * List of AccessRules that reference the Group.
	 */
	protected List<AccessRule> accessRules;
	
	/**
	 * Default Constructor.
	 */
	public Group() {
		super();
		
		this.name = null;
		this.groups = new ArrayList<Group>();
		this.userMembers = new ArrayList<User>();
		this.groupMembers = new ArrayList<Group>();
		this.accessRules = new ArrayList<AccessRule>();
	}
	
	/**
	 * Constructor that accepts the group name.
	 * 
	 * @param name Name of the group.
	 */
	public Group(String name) {
		super();
		
		this.name = name;
		this.groups = new ArrayList<Group>();
		this.userMembers = new ArrayList<User>();
		this.groupMembers = new ArrayList<Group>();
		this.accessRules = new ArrayList<AccessRule>();
	}
	
	/**
	 * Constructor that accepts the group name and list of User members.
	 * 
	 * @param name Name of the group.
	 * @param groupMembers List of Groups that are a member of the Group.
	 * @param userMembers List of Users that are a member of the Group.
	 */
	public Group(String name, List<Group> groupMembers, List<User> userMembers) {
		super();
		
		this.name = name;
		this.groups = new ArrayList<Group>();
		this.userMembers = userMembers;
		this.groupMembers = groupMembers;
		this.accessRules = new ArrayList<AccessRule>();
	}
	
	/**
	 * Returns the Group object as a String.
	 */
	public String toString() {
		return (name == null) ? "" : name;
	}

	/**
	 * Gets the name of the Group.
	 * 
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the Group.
	 * 
	 * @param name The new name for the Group.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets a list of the groups to which the Group is assigned.
	 * 
	 * @return list of Groups to which the Group is assigned.
	 */
	public List<Group> getGroups() {
		return groups;
	}
	
	/**
	 * Adds a Group to the Group's list of assigned Groups.
	 * 
	 * @param group Group to which the Group is assigned.
	 */
	public void addGroup(Group group) {
		groups.add(group);
	}
	
	/**
	 * Removes a Group from the Group's list of assigned Groups.
	 * 
	 * @param group Group to be removed.
	 */
	public void removeGroup(Group group) {
		groups.remove(group);
	}
	
	/**
	 * Gets the list of Groups that are a member of the Group.
	 * 
	 * @return Returns the list of Group members.
	 */
	public List<Group> getGroupMembers() {
		return groupMembers;
	}
	
	/**
	 * Adds a new Group to the list of Group members.
	 * 
	 * @param group Group to be added to the list of Group members.
	 */
	public void addGroupMember(Group group) {
		this.groupMembers.add(group);
	}
	
	/**
	 * Removes a Group from the list of Group members.
	 * 
	 * @param group Group to be removed.
	 */	
	public void removeGroupMember(Group group) {
		this.groupMembers.remove(group);
	}
	
	/**
	 * Gets the list of Users that are a member of the Group.
	 * 
	 * @return Returns the list of User members..
	 */
	public List<User> getUserMembers() {
		return userMembers;
	}
	
	/**
	 * Adds a new User to the list of User members.
	 * 
	 * @param user User to be added to the list of User members.
	 */
	public void addUserMember(User user) {
		this.userMembers.add(user);
	}
	
	/**
	 * Removes a User from the list of User members.
	 * 
	 * @param user User to be removed.
	 */
	public void removeUserMember(User user) {
		this.userMembers.remove(user);
	}
	
	/**
	 * Gets the list of AccessRules that reference the Group.
	 * 
	 * @return Returns the list of AccessRules.
	 */
	public List<AccessRule> getAccessRules() {
		return accessRules;
	}

	/**
	 * Adds a new AccessRule to the list of AccessRules that reference the 
	 * Group.
	 * 
	 * @param accessRule The AccessRule to add.
	 */
	public void addAccessRule(AccessRule accessRule) {
		accessRules.add(accessRule);
	}
	
	/**
	 * Removes an AccessRule from the list of AccessRules that reference the
	 * Group.
	 * 
	 * @param accessRule The AccessRule to remove.
	 */
	public void removeAccessRule(AccessRule accessRule) {
		accessRules.remove(accessRule);
	}

	/**
	 *  Compares this object to another.
	 *  
	 *  @param otherGroup The other Group to use for comparison. 
	 */
	public int compareTo(Group otherGroup) {
		return this.toString().compareTo(otherGroup.toString());
	}
	
	/**
	 * Compares this object to another.
	 * 
	 * @param otherGroup The other Group to use for comparison.
	 * @return true if the same group, other false
	 */
	public boolean equals(Group otherGroup) {
		return compareTo(otherGroup) == 0;
	}
}
