package org.xiaoniu.suafe.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * @author Shaun Johnson
 */
public class BaseComboBoxModel implements ComboBoxModel {
	
	protected Object[] itemList;
	
	protected int selectedItem;
	
	protected List listeners;
	
	public BaseComboBoxModel() {
		super();
		
		itemList = null;
		selectedItem = -1;
		listeners = new ArrayList(1);
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
