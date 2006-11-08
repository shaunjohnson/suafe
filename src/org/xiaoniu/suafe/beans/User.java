/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://suafe.xiaoniu.org.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://suafe.xiaoniu.org/.
 * ====================================================================
 * @endcopyright
 */

package org.xiaoniu.suafe.beans;

import java.util.ArrayList;
import java.util.List;

import org.xiaoniu.suafe.Constants;

/**
 * Represents a single Subversion user object.
 * User objects are not persisted unless they are a member of a group or
 * specifically granted access to a path.
 * 
 * @author Shaun Johnson
 */
public class User extends GroupMemberObject implements Comparable<User> {
	
	/**
	 * The User's name. This field must contain a unique value.
	 */
	protected String name;
	
	/**
	 * List of Groups to which the User is assigned.
	 */
	protected List<Group> groups;
	
	/**
	 * List of AccessRules in which the User is referenced.
	 */
	protected List<AccessRule> accessRules;
	
	/**
	 * Default constructor.
	 */
	public User() {
		super();
		
		this.name = null;
		this.groups = new ArrayList<Group>();
		this.accessRules = new ArrayList<AccessRule>();
	}
	
	/**
	 * Constructor that accepts User name.
	 * 
	 * @param name Name of the user.
	 */
	public User(String name) {
		super();
		
		this.name = name;
		this.groups = new ArrayList<Group>();
		this.accessRules = new ArrayList<AccessRule>();
	}
	
	/**
	 * Returns the User object as a String.
	 */
	public String toString() {
		return (name == null) ? "" : name;
	}
	
	/**
	 * Returns true if this user is "*" or All Subversion users.
	 * 
	 * @return Returns true if users is "*" (All SVN Users)
	 */
	public boolean isAllUsers() {
		return (name == null) ? false : name.equals(Constants.ALL_USERS);
	}
	
	/**
	 * Gets the name of the User.
	 * 
	 * @return Returns name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the User.
	 * 
	 * @param name The User's new name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets a list of the groups to which the User is assigned.
	 * 
	 * @return list of Groups to which the User is assigned.
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * Sets the list of groups to which the User is assigned.
	 * 
	 * @param groups New list of groups to which the user is assigned.
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	/**
	 * Adds a Group to the User's list of assigned Groups.
	 * 
	 * @param group Group to which the User is assigned.
	 */
	public void addGroup(Group group) {
		groups.add(group);
	}

	/**
	 * Removes an AccessRule from the User's list of AccessRules in which
	 * he is referenced.
	 * @param accessRule AccessRule which to remove from the list.
	 */
	public void removeAccessRule(AccessRule accessRule) {
		accessRules.remove(accessRule);
	}
	
	/**
	 * Removes a Group from the User's list of assigned Groups.
	 * @param group Group which to remove from the list.
	 */
	public void removeGroup(Group group) {
		groups.remove(group);
	}
	
	/**
	 * Gets the list of AccessRules in which the User is referenced.
	 * 
	 * @return Returns the AccessRules in which the User is referenced.
	 */
	public List<AccessRule> getAccessRules() {
		return accessRules;
	}
	
	/**
	 * Sets the list of AccessRules in which the User is referenced.
	 * 
	 * @param accessRules New list of AccessRules in which the user is referenced.
	 */
	public void setAccessRules(List<AccessRule> accessRules) {
		this.accessRules = accessRules;
	}
	
	/**
	 * Adds an AccessRule to the User's list of referenced AccessRules.
	 * 
	 * @param accessRule AccessRule in which the User is referenced.
	 */
	public void addAccessRule(AccessRule accessRule) {
		accessRules.add(accessRule);
	}

	/**
	 * Compares this to another object.
	 * 
	 * @param otherUser The other User to which this is compared.
	 */
	public int compareTo(User otherUser) {
		return this.toString().compareTo(otherUser.toString());
	}
}

