package org.xiaoniu.suafe.models;

import org.xiaoniu.suafe.beans.Document;

/**
 * @author Shaun Johnson
 */
public class RepositoryListModel extends BaseComboBoxModel {
	
	public RepositoryListModel() {
		super();
		
		itemList = Document.getRepositoryObjects();
	}
}
