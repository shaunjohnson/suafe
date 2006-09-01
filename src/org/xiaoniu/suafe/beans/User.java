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
public class User implements Comparable {
	
	/**
	 * The User's name. This field must contain a unique value.
	 */
	protected String name;
	
	/**
	 * List of Groups to which the User is assigned.
	 */
	protected List groups;
	
	/**
	 * List of AccessRules in which the User is referenced.
	 */
	protected List accessRules;
	
	/**
	 * Default constructor.
	 */
	public User() {
		super();
		
		this.name = null;
		this.groups = new ArrayList();
		this.accessRules = new ArrayList();
	}
	
	/**
	 * Constructor that accepts User name.
	 * 
	 * @param name Name of the user.
	 */
	public User(String name) {
		super();
		
		this.name = name;
		this.groups = new ArrayList();
		this.accessRules = new ArrayList();
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
	public List getGroups() {
		return groups;
	}

	/**
	 * Sets the list of groups to which the User is assigned.
	 * 
	 * @param groups New list of groups to which the user is assigned.
	 */
	public void setGroups(List groups) {
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
	public List getAccessRules() {
		return accessRules;
	}
	
	/**
	 * Sets the list of AccessRules in which the User is referenced.
	 * 
	 * @param groups New list of AccessRules in which the user is referenced.
	 */
	public void setAccessRules(List accessRules) {
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
	 * Compares this to the specified object.
	 * Used when sorting lists of User objects.
	 * 
	 * @param other Other User object to which this is compared.
	 * @throws ClassCastException Other is not an instance of User.
	 */
	public int compareTo(Object other) throws ClassCastException {
		if (!(other instanceof User)) {
			throw new ClassCastException("Invalid object type. Cannot cast to User.");
		}	
		
		User otherUser = (User)other;
		
		return this.toString().compareTo(otherUser.toString());
	}
}

