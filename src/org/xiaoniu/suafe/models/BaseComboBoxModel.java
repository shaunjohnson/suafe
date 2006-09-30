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

package org.xiaoniu.suafe.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * Base class for all application combo-boxes.
 * 
 * @author Shaun Johnson
 */
public class BaseComboBoxModel implements ComboBoxModel {
	
	protected Object[] itemList;
	
	protected int selectedItem;
	
	protected List<ListDataListener> listeners;
	
	public BaseComboBoxModel() {
		super();
		
		itemList = null;
		selectedItem = -1;
		listeners = new ArrayList<ListDataListener>(1);
	}

	public void setSelectedItem(Object anItem) {
		if (itemList == null || anItem == null) {
			selectedItem = -1;
		}
		else {
			selectedItem = Arrays.binarySearch(itemList, anItem);
		}
	}

	public Object getSelectedItem() {
		return (itemList == null || selectedItem == -1) ? null : itemList[selectedItem];
	}

	public int getSize() {
		return (itemList == null) ? 0 : itemList.length;
	}

	public Object getElementAt(int index) {
		return (itemList == null) ? null : itemList[index];
	}

	public void addListDataListener(ListDataListener l) {
		listeners.add(l);		
	}

	public void removeListDataListener(ListDataListener l) {
		listeners.remove(listeners.indexOf(l));
	}	
}
