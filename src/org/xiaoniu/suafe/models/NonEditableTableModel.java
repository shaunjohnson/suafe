package org.xiaoniu.suafe.models;

import javax.swing.table.DefaultTableModel;

/**
 * @author Shaun Johnson
 */
public class NonEditableTableModel extends DefaultTableModel {

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
