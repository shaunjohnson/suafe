/*
 * Created on Jul 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.renderers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;

/**
 * @author spjohnso
 */
public class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {

	private static ImageIcon pathIcon = null;
	
	private static ImageIcon repositoryIcon = null;
	
	private static ImageIcon serverIcon = null;
	
	private static DefaultTreeCellRenderer render = null;
	
	public MyTreeCellRenderer() {
		super();
		
		pathIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/Path16.gif"));
		repositoryIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/Repository16.gif"));
		serverIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/Server16.gif"));
		
		render = new DefaultTreeCellRenderer();
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String s = value.toString();
		Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
		setText(s);
		
		if (userObject instanceof Path) {
			setIcon(pathIcon);			
			setToolTipText( ((Path)userObject).getPath() );
		}
		else if (userObject instanceof Repository) {
			setIcon(repositoryIcon);
			setToolTipText( ((Repository)userObject).getName() );
		}
		else if (userObject instanceof String) {
			setIcon(serverIcon);
			setToolTipText( (String)userObject );
		}
		else {
			setIcon(serverIcon);
		}
		
		if (isSelected) {
			setBackground(render.getBackgroundSelectionColor());
			setForeground(render.getTextSelectionColor());			
		}
		else {
			setBackground(tree.getBackground());
			setForeground(tree.getForeground());
		}
		
		setEnabled(tree.isEnabled());
		setFont(tree.getFont());
		setOpaque(true);
		return this;
	}

}
