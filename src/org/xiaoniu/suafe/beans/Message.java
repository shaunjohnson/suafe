/*
 * Created on Aug 2, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.beans;

/**
 * @author spjohnso
 */
public class Message {

	public static int CANCEL = -1;
	
	public static int UNKNOWN = 0;
	
	public static int SUCCESS = 1;
	
	private Object userObject;
	
	private int state;
	
	public Message() {
		super();
		
		this.userObject = null;
		this.state = UNKNOWN;
	}
	
	/**
	 * @return Returns the state.
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @return Returns the userObject.
	 */
	public Object getUserObject() {
		return userObject;
	}
	/**
	 * @param userObject The userObject to set.
	 */
	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}
}
