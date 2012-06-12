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
public abstract class BaseComboBoxModel implements ComboBoxModel {
	
	/**
	 * List values.
	 */
	protected Object[] itemList;
	
	/**
	 * Selected item index.
	 */
	protected int selectedItem;
	
	/**
	 * Array of listeners.
	 */
	protected List<ListDataListener> listeners;
	
	/**
	 * Default constructor.
	 */
	public BaseComboBoxModel() {
		super();
		
		this.itemList = null;
		this.selectedItem = -1;
		this.listeners = new ArrayList<ListDataListener>(1);
	}

	/**
	 * Sets the selected item index.
	 * 
	 * @param anItem Item to be selected.
	 */
	public void setSelectedItem(Object anItem) {
		if (itemList == null || anItem == null) {
			selectedItem = -1;
		}
		else {
			selectedItem = Arrays.binarySearch(itemList, anItem);
		}
	}

	/**
	 * Gets the selected item.
	 */
	public Object getSelectedItem() {
		return (itemList == null || selectedItem == -1) ? null : itemList[selectedItem];
	}

	/**
	 * Gets the list size.
	 */
	public int getSize() {
		return (itemList == null) ? 0 : itemList.length;
	}

	/**
	 * Gets object at specified index.
	 * 
	 * @param index Index of object to retrieve.
	 */
	public Object getElementAt(int index) {
		return (itemList == null) ? null : itemList[index];
	}

	/**
	 * Adds listener.
	 * 
	 * @param listener Listener to add.
	 */
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);		
	}

	/**
	 * Removes a listener.
	 * 
	 * @param listener Listener to remove.
	 */
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listeners.indexOf(listener));
	}	
}
