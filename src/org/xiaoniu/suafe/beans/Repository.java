package org.xiaoniu.suafe.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a singled Subversion repository.
 * Repositories are not persisted unless they are used in conjunction
 * with a path to define AccessRules.
 * 
 * @author Shaun Johnson
 */
public class Repository implements Comparable {
	
	/**
	 * Name of the Repository. This field must contain a unique value.
	 */
	protected String name;
	
	/**
	 * List of paths in which the Repository is referenced.
	 */
	protected List paths;
	
	/**
	 * Default Constuctor.
	 */
	public Repository() {
		super();
		
		this.name = null;
		this.paths = new ArrayList();
	}
	
	/**
	 * Constructor that accepts Repository name.
	 * 
	 * @param name The name of the Repository.
	 */
	public Repository(String name) {
		super();
		
		this.name = name;
		this.paths = new ArrayList();
	}
	
	/**
	 * Returns the Repository object as a String.
	 */
	public String toString() {
		return (name == null) ? "" : name;
	}
	/**
	 * Gets the name of the Repository.
	 * 
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the Repository.
	 * 
	 * @param name The Repository's new name.
	 */
	public void setName(String name) {
		this.name = name;
	}
		
	/**
	 * Gets the Paths in which the Repository is referenced.
	 * 
	 * @return Returns the paths.
	 */
	public List getPaths() {
		return paths;
	}
	
	/**
	 * Adds a Path to the list of Paths in which the Repository is 
	 * referenced.
	 * 
	 * @param path Path to add to the list.
	 */
	public void addPath(Path path) {
		paths.add(path);
	}
	
	/**
	 * Removes a Path from the list of Paths in which the Repository is
	 * referenced.
	 * 
	 * @param path Path to be removed.
	 */
	public void removePath(Path path) {
		paths.remove(path);
	}
	
	/**
	 * Compares this to the specified object.
	 * Used when sorting lists of Repository objects.
	 * 
	 * @param other Other Repository object to which this is compared.
	 * @throws ClassCastException Other is not an instance of Repository.
	 */
	public int compareTo(Object other) throws ClassCastException {
		if (!(other instanceof Repository)) {
			throw new ClassCastException("Invalid object type. Cannot cast to Repository.");
		}	 
	
		Repository otherRepository = (Repository)other;
		
		return this.toString().compareTo(otherRepository.toString());
	}
}
