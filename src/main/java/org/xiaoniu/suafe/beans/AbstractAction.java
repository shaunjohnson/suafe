package org.xiaoniu.suafe.beans;

public abstract class AbstractAction {

	protected String action = null;

	public AbstractAction(String action) {
		super();
		
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
