/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.lmxm.suafe.renderers;

import net.lmxm.suafe.api.beans.Path;
import net.lmxm.suafe.api.beans.Repository;
import net.lmxm.suafe.resources.ResourceUtil;

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
