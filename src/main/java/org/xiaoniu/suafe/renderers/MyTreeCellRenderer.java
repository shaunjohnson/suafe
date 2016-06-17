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

import org.xiaoniu.suafe.api.beans.Path;
import org.xiaoniu.suafe.api.beans.Repository;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * Default tree cell renderer.
 *
 * @author Shaun Johnson
 */
public final class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3819189157641390968L;

    /**
     * Copy of default tree cell renderer. Used for reference.
     */
    private final DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();

    /**
     * Renders tree cells.
     *
     * @param tree       Tree where the cell resides
     * @param value      Value of the cell
     * @param isSelected Indicates whether the cell is selected
     * @param expanded   Indicates whether the cell is expanded
     * @param leaf       Indicates whether this is a leaf
     * @param row        Row number
     * @param hasFocus   Indicates whether the cell has focus
     */
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean isSelected,
                                                  final boolean expanded, final boolean leaf, final int row,
                                                  final boolean hasFocus) {
        final Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
        setText(value.toString());

        if (userObject instanceof Path) {
            setIcon(ResourceUtil.pathIcon);
            setToolTipText(((Path) userObject).getPath());
        }
        else if (userObject instanceof Repository) {
            setIcon(ResourceUtil.repositoryIcon);
            setToolTipText(((Repository) userObject).getName());
        }
        else if (userObject instanceof String) {
            setIcon(ResourceUtil.serverIcon);
            setToolTipText((String) userObject);
        }
        else {
            setIcon(ResourceUtil.serverIcon);
        }

        if (isSelected && hasFocus) {
            setBackground(render.getBackgroundSelectionColor());
            setForeground(render.getTextSelectionColor());
            setBorder(new LineBorder(render.getBackgroundSelectionColor()));
        }
        else if (isSelected && !hasFocus) {
            setBackground(tree.getBackground());
            setForeground(render.getTextSelectionColor());
            setBorder(new LineBorder(render.getBackgroundSelectionColor()));
        }
        else if (!isSelected && hasFocus) {
            setBackground(tree.getBackground());
            setForeground(tree.getForeground());
            setBorder(new LineBorder(render.getBackgroundSelectionColor()));
        }
        else {
            setBackground(tree.getBackground());
            setForeground(tree.getForeground());
            setBorder(new LineBorder(tree.getBackground()));
        }

        setEnabled(tree.isEnabled());
        setFont(tree.getFont());
        setOpaque(true);

        return this;
    }
}
