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
package net.lmxm.suafe.gui.models;

import javax.swing.table.DefaultTableModel;

/**
 * Extension of the DefaultTableModel, which forces all table cells to be
 * non-editable.
 *
 * @author Shaun Johnson
 */
public final class NonEditableTableModel extends DefaultTableModel {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2240746070531106855L;

    /**
     * Indicates whether the cell is editable or not. This method always
     * returns false, ensuring that the cell is never editable.
     *
     * @param row    Row number
     * @param column Column number
     */
    public boolean isCellEditable(final int row, final int column) {
        return false;
    }
}
