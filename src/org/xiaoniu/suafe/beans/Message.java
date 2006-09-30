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

/**
 * Represents the outcome of a dialog action.
 * When a dialog returns focus back to the main application the dialog returns
 * a Message object that indicates the success or failure of the action.  
 * 
 * @author Shaun Johnson
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
