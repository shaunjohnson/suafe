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
package net.lmxm.suafe.gui.renderers;

import net.lmxm.suafe.api.beans.Path;
import net.lmxm.suafe.api.beans.Repository;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.User;
import net.lmxm.suafe.resources.ResourceUtil;

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
