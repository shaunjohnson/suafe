package org.xiaoniu.suafe.beans;

import java.util.HashMap;
import java.util.Map;

public class UndoableAction extends AbstractAction {
	
	private Map<String,Object> values = null;
	
	public UndoableAction(String action) {
		super(action);
		
		values = new HashMap<String,Object>();
	}
	
	public String getActionName() {
		// TODO Lookup from resource bundle;
		return action;
	}
	
	public void addValue(String name, Object value) {
		values.put(name, value);
	}
	
	public Object getValue(String name) {
		return values.get(name);
	}
}
