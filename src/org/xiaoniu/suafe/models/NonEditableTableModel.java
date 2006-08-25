/*
 * Created on Jul 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.models;

import javax.swing.table.DefaultTableModel;

/**
 * @author spjohnso
 */
public class NonEditableTableModel extends DefaultTableModel {

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
