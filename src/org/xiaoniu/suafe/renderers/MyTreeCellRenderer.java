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
package org.xiaoniu.suafe.renderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Default tree cell renderer.
 * 
 * @author Shaun Johnson
 */
public class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -3819189157641390968L;
	
	/**
	 * Copy of default tree cell renderer. Used for reference.
	 */
	private static DefaultTreeCellRenderer render = null;
	
	/**
	 * Default constructor.
	 */
	public MyTreeCellRenderer() {
		super();
		
		render = new DefaultTreeCellRenderer();
	}
	
	/**
	 * Renders tree cells.
	 * 
	 * @param tree Tree where the cell resides
	 * @param value Value of the cell
	 * @param isSelected Indicates whether the cell is selected
	 * @param expanded Indicates whether the cell is expanded
	 * @param leaf Indicates whether this is a leaf
	 * @param row Row number
	 * @param hasFocus Indicates whether the cell has focus
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String s = value.toString();
		Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
		setText(s);
		
		if (userObject instanceof Path) {
			setIcon(ResourceUtil.pathIcon);			
			setToolTipText( ((Path)userObject).getPath() );
		}
		else if (userObject instanceof Repository) {
			setIcon(ResourceUtil.repositoryIcon);
			setToolTipText( ((Repository)userObject).getName() );
		}
		else if (userObject instanceof String) {
			setIcon(ResourceUtil.serverIcon);
			setToolTipText( (String)userObject );
		}
		else {
			setIcon(ResourceUtil.serverIcon);
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
