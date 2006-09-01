/*
 * Created on Jul 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
 * @author spjohnso
 */
public class MyListCellRenderer extends JLabel implements ListCellRenderer {

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
