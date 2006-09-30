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

import javax.swing.table.DefaultTableModel;

/**
 * Extension of the DefaultTableModel, which forces all table cells to be 
 * non-editable.
 * 
 * @author Shaun Johnson
 */
public class NonEditableTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -2240746070531106855L;

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
