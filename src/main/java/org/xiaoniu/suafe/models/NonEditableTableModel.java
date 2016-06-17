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
package org.xiaoniu.suafe.models;

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
