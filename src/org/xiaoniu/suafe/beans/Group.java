package org.xiaoniu.suafe.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Subversion user group.
 * Groups are persisted regardless of whether they contain users or
 * are referenced by an AccessRule.
 * 
 * @author Shaun Johnson
 */
public class Group implements Comparable {
	
	/**
	 * Name of the group.
	 */
	protected String name;
	
	/**
	 * List of Groups in which this Group is a member.
	 */
	protected List groups;
	
	/**
	 * List of Users that are a member of the Group.
	 */
	protected List userMembers;
	
	/**
	 * List of Groups that are a member of the Group.
	 */
	protected List groupMembers;
	
	/**
	 * List of AccessRules that reference the Group.
	 */
	protected List accessRules;
	
	/**
	 * Default Constructor.
	 */
	public Group() {
		super();
		
		this.name = null;
		this.groups = new ArrayList();
		this.userMembers = new ArrayList();
		this.groupMembers = new ArrayList();
		this.accessRules = new ArrayList();
	}
	
	/**
	 * Constructor that accepts the group name and list of User members.
	 * 
	 * @param name Name of the group.
	 * @param groupMembers List of Groups that are a member of the Group.
	 * @param userMembers List of Users that are a member of the Group.
	 */
	public Group(String name, List groupMembers, List userMembers) {
		super();
		
		this.name = name;
		this.groups = new ArrayList();
		this.userMembers = userMembers;
		this.groupMembers = groupMembers;
		this.accessRules = new ArrayList();
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
	public List getGroups() {
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
	public List getGroupMembers() {
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
	public List getUserMembers() {
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
	public List getAccessRules() {
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
	 * Compares this to the specified object.
	 * Used when sorting lists of Group objects.
	 * 
	 * @param other Other Group object to which this is compared.
	 * @throws ClassCastException Other is not an instance of Group.
	 */
	public int compareTo(Object other) throws ClassCastException {
		if (!(other instanceof Group)) {
			throw new ClassCastException("Invalid object type. Cannot cast to Group.");
		}	
		
		Group otherGroup = (Group)other;
		
		return this.toString().compareTo(otherGroup.toString());
	}
}
