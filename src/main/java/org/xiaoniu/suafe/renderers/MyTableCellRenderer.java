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

import org.xiaoniu.suafe.api.beans.Group;
import org.xiaoniu.suafe.api.beans.Path;
import org.xiaoniu.suafe.api.beans.Repository;
import org.xiaoniu.suafe.api.beans.User;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Default table cell renderer.
 *
 * @author Shaun Johnson
 */
public final class MyTableCellRenderer extends JLabel implements TableCellRenderer {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2879090147475742072L;

    /**
     * Custom cell painter.
     *
     * @param table      The table being painted
     * @param value      Value to display
     * @param isSelected Indicates whether item is selected or not
     * @param hasFocus   True if the item has focus
     * @param row        Row number
     * @param column     Column number
     */
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
                                                   final boolean hasFocus, final int row, final int column) {
        setText(value == null ? null : value.toString());

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
            final String valueString = (String) value;

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

        if (isSelected && hasFocus) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
            setBorder(new LineBorder(table.getSelectionBackground()));
        }
        else if (isSelected && !hasFocus) {
            setBackground(table.getBackground());
            setForeground(table.getSelectionForeground());
            setBorder(new LineBorder(table.getSelectionBackground()));
        }
        else if (!isSelected && hasFocus) {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
            setBorder(new LineBorder(table.getSelectionForeground()));
        }
        else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
            setBorder(new LineBorder(table.getBackground()));
        }

        setEnabled(table.isEnabled());
        setFont(table.getFont());
        setOpaque(true);

        return this;
    }
}
