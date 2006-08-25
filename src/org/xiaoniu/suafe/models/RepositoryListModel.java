/*
 * Created on Jul 9, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.models;

import org.xiaoniu.suafe.beans.Document;

/**
 * @author Shaun Johnson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositoryListModel extends BaseComboBoxModel {
	
	public RepositoryListModel() {
		super();
		
		itemList = Document.getRepositoryObjects();
	}
}
