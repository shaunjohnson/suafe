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

import net.lmxm.suafe.api.beans.AccessRule;
import net.lmxm.suafe.api.beans.Repository;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.User;
import net.lmxm.suafe.resources.ResourceUtil;

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
