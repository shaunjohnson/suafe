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
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;

/**
 * Default list cell renderer.
 * 
 * @author Shaun Johnson
 */
public class MyListCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 2612512361404880700L;
	private static ImageIcon userIcon = null;
	private static ImageIcon groupIcon = null;
	private static ImageIcon repositoryIcon = null;
	private static ImageIcon accessRuleIcon = null;
	
	public MyListCellRenderer() {
		super();
		
		userIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/ListUser.gif"));
		groupIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/ListGroup.gif"));
		repositoryIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/Repository16.gif"));
		accessRuleIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/Reversed.gif"));
	}
	
	public Component getListCellRendererComponent(
			JList list,
			Object value,            // value to display
			int index,               // cell index
			boolean isSelected,      // is the cell selected
			boolean cellHasFocus)    // the list and the cell have the focus
	{
		String s = value.toString();
		setText(s);
		
		if (value instanceof User) {
			setIcon(userIcon);
		}
		else if (value instanceof Group) {
			setIcon(groupIcon);
		}
		else if (value instanceof Repository) {
			setIcon(repositoryIcon);
		}
		else if (value instanceof AccessRule) {
			setIcon(accessRuleIcon);
		}
		else {
			setIcon(null);
		}
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
}
