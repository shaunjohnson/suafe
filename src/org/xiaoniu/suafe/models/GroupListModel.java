package org.xiaoniu.suafe.models;

import org.xiaoniu.suafe.beans.Document;

/**
 * @author Shaun Johnson
 */
public class GroupListModel extends BaseComboBoxModel {
	
	public GroupListModel() {
		super();
		
		itemList = Document.getGroupObjects();
	}
}
