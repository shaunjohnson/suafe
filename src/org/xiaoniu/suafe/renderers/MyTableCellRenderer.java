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

package org.xiaoniu.suafe.renderers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;

/**
 * Default table cell renderer.
 * 
 * @author Shaun Johnson
 */
public class MyTableCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 2879090147475742072L;
	private static ImageIcon userIcon = null;
	private static ImageIcon groupIcon = null;
	private static ImageIcon readOnlyIcon = null;
	private static ImageIcon readWriteIcon = null;
	private static ImageIcon denyAccessIcon = null;
		
	public MyTableCellRenderer() {
		super();
		
		userIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/ListUser.gif"));
		groupIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/ListGroup.gif"));
		readOnlyIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/ReadOnly16.gif"));
		readWriteIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/ReadWrite16.gif"));
		denyAccessIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/DenyAccess16.gif"));
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value != null) {
			setText(value.toString());
		}
		else {
			setText(null);
		}
		
		if (value instanceof User) {
			setIcon(userIcon);
		}
		else if (value instanceof Group) {
			setIcon(groupIcon);
		}
		else if (value instanceof Path) {
			setIcon(null);
		}
		else if (value instanceof Repository) {
			setIcon(null);
		}
		else if (value instanceof String) {
			String valueString = (String)value;
			
			if (valueString.equals(Constants.ACCESS_LEVEL_READONLY_FULL)) {
				setIcon(readOnlyIcon);
			}
			else if (valueString.equals(Constants.ACCESS_LEVEL_READWRITE_FULL)) {
				setIcon(readWriteIcon);
			}
			else if (valueString.equals(Constants.ACCESS_LEVEL_DENY_ACCESS_FULL)) {
				setIcon(denyAccessIcon);
			}
			else {
				setIcon(null);
			}
		}
		else {
			setIcon(null);
		}
		
		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		}
		else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}
		
		setEnabled(table.isEnabled());
		setFont(table.getFont());
		setOpaque(true);
		return this;
	}

}
