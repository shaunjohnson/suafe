package org.xiaoniu.suafe.models;

import org.xiaoniu.suafe.beans.Document;

/**
 * @author Shaun Johnson
 */
public class UserListModel extends BaseComboBoxModel {
	
	public UserListModel() {
		super();
		
		itemList = Document.getUserObjects();
	}
}