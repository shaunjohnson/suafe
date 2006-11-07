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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Default table cell renderer.
 * 
 * @author Shaun Johnson
 */
public class MyTableCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 2879090147475742072L;
		
	public MyTableCellRenderer() {
		super();
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value != null) {
			setText(value.toString());
		}
		else {
			setText(null);
		}
		
		if (value instanceof User) {
			setIcon(ResourceUtil.userIcon);
		}
		else if (value instanceof Group) {
			setIcon(ResourceUtil.groupIcon);
		}
		else if (value instanceof Path) {
			setIcon(null);
		}
		else if (value instanceof Repository) {
			setIcon(null);
		}
		else if (value instanceof String) {
			String valueString = (String)value;
			
			if (valueString.equals(ResourceUtil.getString("accesslevel.readonly"))) {
				setIcon(ResourceUtil.readOnlyIcon);
			}
			else if (valueString.equals(ResourceUtil.getString("accesslevel.readwrite"))) {
				setIcon(ResourceUtil.readWriteIcon);
			}
			else if (valueString.equals(ResourceUtil.getString("accesslevel.denyaccess"))) {
				setIcon(ResourceUtil.denyAccessIcon);
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
