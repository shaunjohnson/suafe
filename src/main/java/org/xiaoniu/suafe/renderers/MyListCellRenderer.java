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

import org.xiaoniu.suafe.api.beans.AccessRule;
import org.xiaoniu.suafe.api.beans.Group;
import org.xiaoniu.suafe.api.beans.Repository;
import org.xiaoniu.suafe.api.beans.User;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Default list cell renderer.
 *
 * @author Shaun Johnson
 */
public final class MyListCellRenderer extends JLabel implements ListCellRenderer {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2612512361404880700L;

    /**
     * Custom cell painter.
     *
     * @param list         The list being painted
     * @param value        Value to display
     * @param index        Cell index
     * @param isSelected   Indicates whether cell is selected or not
     * @param cellHasFocus True if the cell has focus
     */
    public Component getListCellRendererComponent(final JList list, final Object value, // value to display
                                                  final int index, final boolean isSelected, final boolean cellHasFocus) {
        setText(value.toString());

        if (value instanceof User) {
            setIcon(ResourceUtil.userIcon);
        }
        else if (value instanceof Group) {
            setIcon(ResourceUtil.groupIcon);
        }
        else if (value instanceof Repository) {
            setIcon(ResourceUtil.repositoryIcon);
        }
        else if (value instanceof AccessRule) {
            setIcon(ResourceUtil.accessRuleIcon);
        }
        else {
            setIcon(null);
        }

        if (isSelected && cellHasFocus) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setBorder(new LineBorder(list.getSelectionBackground()));
        }
        else if (isSelected && !cellHasFocus) {
            setBackground(list.getBackground());
            setForeground(list.getSelectionForeground());
            setBorder(new LineBorder(list.getSelectionBackground()));
        }
        else if (!isSelected && cellHasFocus) {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setBorder(new LineBorder(list.getSelectionBackground()));
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setBorder(new LineBorder(list.getBackground()));
        }

        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);

        return this;
    }
}
