package org.xiaoniu.suafe.beans;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;

/**
 * Represents a single Subversion AccessRule.
 * An AccessRule consists of a Path (repository and relative path),
 * a User or Group, and the level of access. AccessRules are always
 * persisted.
 * 
 * @author Shaun Johsnon
 */
public class AccessRule implements Comparable {
		
	/**
	 * Path referenced by the AccessRule.
	 */
	protected Path path;
	
	/**
	 * Group that has the specified level of access in the Path.
	 * Group is null if User is set.
	 */
	protected Group group;
	
	/**
	 * User that has the specified level of access in the Path.
	 * User is null if Group is set.
	 */
	protected User user;
	
	/**
	 * Level of access that the User or Group has within the Path.
	 */
	protected String level;
	
	/**
	 * Default Constructor.
	 */
	public AccessRule() {
		super();
		
		this.path = null;
		this.group = null;
		this.user = null;
		this.level = null;
	}

	/**
	 * Constructor that accepts the Path and level of access.
	 * 
	 * @param path The Path to be set.
	 * @param level The level of access to be set.
	 */
	public AccessRule(Path path, String level) {
		super();
		
		this.path = path;
		this.group = null;
		this.user = null;
		this.level = level;
	}
	
	/**
	 * Constructor that accepts the Path, Group and level of access.
	 * 
	 * @param path The Path to be set.
	 * @param group The Group to be given access.
	 * @param level The level of access to be set.
	 */
	public AccessRule(Path path, Group group, String level) {
		super();
		
		this.path = path;
		this.group = group;
		this.user = null;
		this.level = level;
	}	

	/**
	 * Constructor that accepts the Path, User and level of access.
	 * 
	 * @param path The Path to be set.
	 * @param user The user to be given access.
	 * @param level The level of access to be set.
	 */
	public AccessRule(Path path, User user, String level) {
		super();
		
		this.path = path;
		this.group = null;
		this.user = user;
		this.level = level;
	}	
	
	/**
	 * Returns the AccessRule object as a String.
	 */
	public String toString() {
		return
			((path == null || path.getRepository() == null) ? "" : path.getRepository().toString()) + ":" +
			((path == null) ? "" : path.toString()) + ":" +
			((group == null) ? "" : group.toString()) + ":" +
			((user == null) ? "" : user.toString());
	}
	
	/**
	 * Gets the Group referenced by the AccessRule.
	 * 
	 * @return Returns the Group.	 
	 */
	public Group getGroup() {
		return group;
	}
	
	/**
	 * Sets the Group referenced by the AccessRule.
	 * 
	 * @param group The Group to set.
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	
	/**
	 * Gets the User referenced by the AccessRule.
	 * 
	 * @return Returns the User.
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the User referenced by the AccessRule.
	 * 
	 * @param user The User to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Gets the Path referenced by the AccessRule.
	 * 
	 * @return Returns the Path.
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 * Sets the Path referenced by the AccessRule.
	 * 
	 * @param path The Path to set.
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/**
	 * Gets the level of access.
	 * 
	 * @return Returns the level of access.
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * Gets the full English form of the level of access.
	 * 
	 * @return Returns the level of access in English.
	 */
	public String getLevelFullName() {
		String name = null;
		
		if (level == null) {
			name = ResourceUtil.getString("application.unknown");
		}
		else if (level.equals(Constants.ACCESS_LEVEL_DENY_ACCESS)) {
			name = Constants.ACCESS_LEVEL_DENY_ACCESS_FULL;
		}
		else if (level.equalsIgnoreCase(Constants.ACCESS_LEVEL_READONLY)) {
			name = Constants.ACCESS_LEVEL_READONLY_FULL;
		}
		else if (level.equalsIgnoreCase(Constants.ACCESS_LEVEL_READWRITE)) {
			name = Constants.ACCESS_LEVEL_READWRITE_FULL;
		}
		
		return name;
	}
	
	/**
	 * Sets the level of access.
	 * 
	 * @param level The level of access to set.
	 * @throws ApplicationException
	 */
	public void setLevel(String level) throws ApplicationException {
		Validator.validateLevelOfAccess(level);
		
		this.level = level;
	}
	
	/**
	 * Compares this to the specified object.
	 * Used when sorting lists of AccessRule objects.
	 * 
	 * @param other Other AccessRule object to which this is compared.
	 * @throws ClassCastException Other is not an instance of AccessRule.
	 */
	public int compareTo(Object other) throws ClassCastException {
		if (!(other instanceof AccessRule)) {
			throw new ClassCastException("Invalid object type. Cannot cast to AccessRule.");
		}		
		
		AccessRule otherAccessRule = (AccessRule)other;
		
		return this.toString().compareTo(otherAccessRule.toString());
	}
}
