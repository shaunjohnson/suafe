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
 * Represents a single Subversion repository.
 * Repositories are not persisted unless they are used in conjunction
 * with a path to define AccessRules.
 * 
 * @author Shaun Johnson
 */
public final class Repository implements Comparable<Repository> {
	
	/**
	 * Name of the Repository. This field must contain a unique value.
	 */
	protected String name;
	
	/**
	 * List of paths in which the Repository is referenced.
	 */
	protected List<Path> paths;
	
	/**
	 * Default Constuctor.
	 */
	public Repository() {
		super();
		
		setName(null);
		this.paths = new ArrayList<Path>();
	}
	
	/**
	 * Constructor that accepts Repository name.
	 * 
	 * @param name The name of the Repository.
	 */
	public Repository(String name) {
		super();
		
		setName(name);
		this.paths = new ArrayList<Path>();
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
		this.name = (name == null) ? null : name.trim().intern();
	}
		
	/**
	 * Gets the Paths in which the Repository is referenced.
	 * 
	 * @return Returns the paths.
	 */
	public List<Path> getPaths() {
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
	 * Compares this to another object.
	 * 
	 * @param otherRepository The other Repository to which this is compared.
	 */
	public int compareTo(Repository otherRepository) {
		return this.toString().compareTo(otherRepository.toString());
	}
	
	/**
	 * Compares this to another object.
	 * 
	 * @param otherRepository The other Repository to which this is compared.
	 * @return true if it is the same object, otherwise false
	 */
	public boolean equals(Repository otherRepository) {
		return name.equals(otherRepository.getName());
	}
}
