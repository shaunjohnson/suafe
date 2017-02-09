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

/**
 * The follow code comes from:
 * http://forum.java.sun.com/thread.jspa?forumID=54&messageID=2532529&threadID=527301
 */
package net.lmxm.suafe.gui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Helper class for resizing table columns to fit current data.
 *
 * @author Shaun Johnson
 */
public final class AutofitTableColumns {

    private static final int DEFAULT_COLUMN_PADDING = 5;

    /**
     * @param JTable  aTable, the JTable to autoresize the columns on
     * @param boolean includeColumnHeaderWidth, use the Column Header width as a
     *                minimum width
     * @returns The table width, just in case the caller wants it...
     */
    public static int autoResizeTable(JTable aTable,
                                      boolean includeColumnHeaderWidth) {
        return (autoResizeTable(aTable, includeColumnHeaderWidth,
                DEFAULT_COLUMN_PADDING));
    }

    /**
     * @param JTable  aTable, the JTable to autoresize the columns on
     * @param boolean includeColumnHeaderWidth, use the Column Header width as a
     *                minimum width
     * @param int     columnPadding, how many extra pixels do you want on the end of
     *                each column
     * @returns The table width, just in case the caller wants it...
     */
    public static int autoResizeTable(JTable aTable,
                                      boolean includeColumnHeaderWidth, int columnPadding) {
        int columnCount = aTable.getColumnCount();
        int tableWidth = 0;

        Dimension cellSpacing = aTable.getIntercellSpacing();

        if (columnCount > 0) // must have columns !
        {
            // STEP ONE : Work out the column widths

            int columnWidth[] = new int[columnCount];

            for (int i = 0; i < columnCount; i++) {
                columnWidth[i] = getMaxColumnWidth(aTable, i, true,
                        columnPadding);

                tableWidth += columnWidth[i];
            }

            // account for cell spacing too
            tableWidth += ((columnCount - 1) * cellSpacing.width);

            // STEP TWO : Dynamically resize each column

            // try changing the size of the column names area
            JTableHeader tableHeader = aTable.getTableHeader();

            Dimension headerDim = tableHeader.getPreferredSize();

            // headerDim.height = tableHeader.getHeight();
            headerDim.width = tableWidth;
            tableHeader.setPreferredSize(headerDim);

            Dimension interCellSpacing = aTable.getIntercellSpacing();
            Dimension dim = new Dimension();
            int rowHeight = aTable.getRowHeight();

            if (rowHeight == 0) {
                rowHeight = 16; // default rowheight
            }

            // System.out.println ("Row Height : " + rowHeight );

            dim.height = headerDim.height
                    + ((rowHeight + interCellSpacing.height) * aTable
                    .getRowCount());
            dim.width = tableWidth;

            // System.out.println
            // ("AutofitTableColumns.autoResizeTable() - Setting Table size to ( "
            // + dim.width + ", " + dim.height + " )" );
            // aTable.setPreferredSize ( dim );

            TableColumnModel tableColumnModel = aTable.getColumnModel();
            TableColumn tableColumn;

            for (int i = 0; i < columnCount; i++) {
                tableColumn = tableColumnModel.getColumn(i);

                tableColumn.setPreferredWidth(columnWidth[i]);
            }

            aTable.invalidate();
            aTable.doLayout();
            aTable.repaint();
        }

        return (tableWidth);
    }

    /**
     * @param JTable  aTable, the JTable to autoresize the columns on
     * @param int     columnNo, the column number, starting at zero, to calculate
     *                the maximum width on
     * @param boolean includeColumnHeaderWidth, use the Column Header width as a
     *                minimum width
     * @param int     columnPadding, how many extra pixels do you want on the end of
     *                each column
     * @returns The table width, just in case the caller wants it...
     */

    private static int getMaxColumnWidth(JTable aTable, int columnNo,
                                         boolean includeColumnHeaderWidth, int columnPadding) {
        TableColumn column = aTable.getColumnModel().getColumn(columnNo);
        Component comp = null;
        int maxWidth = 0;

        if (includeColumnHeaderWidth) {
            TableCellRenderer headerRenderer = column.getHeaderRenderer();
            if (headerRenderer != null) {
                comp = headerRenderer.getTableCellRendererComponent(aTable,
                        column.getHeaderValue(), false, false, 0, columnNo);

                if (comp instanceof JTextComponent) {
                    JTextComponent jtextComp = (JTextComponent) comp;

                    String text = jtextComp.getText();
                    Font font = jtextComp.getFont();
                    FontMetrics fontMetrics = jtextComp.getFontMetrics(font);

                    maxWidth = SwingUtilities.computeStringWidth(fontMetrics,
                            text);
                }
                else {
                    maxWidth = comp.getPreferredSize().width;
                }
            }
            else {
                try {
                    String headerText = (String) column.getHeaderValue();
                    JLabel defaultLabel = new JLabel(headerText);

                    Font font = defaultLabel.getFont();
                    FontMetrics fontMetrics = defaultLabel.getFontMetrics(font);

                    maxWidth = SwingUtilities.computeStringWidth(fontMetrics,
                            headerText);
                }
                catch (ClassCastException ce) {
                    // Can't work out the header column width..
                    maxWidth = 0;
                }
            }
        }

        TableCellRenderer tableCellRenderer;
        // Component comp;
        int cellWidth = 0;

        for (int i = 0; i < aTable.getRowCount(); i++) {
            tableCellRenderer = aTable.getCellRenderer(i, columnNo);

            comp = tableCellRenderer.getTableCellRendererComponent(aTable,
                    aTable.getValueAt(i, columnNo), false, false, i, columnNo);

            if (comp instanceof JTextComponent) {
                JTextComponent jtextComp = (JTextComponent) comp;

                String text = jtextComp.getText();
                Font font = jtextComp.getFont();
                FontMetrics fontMetrics = jtextComp.getFontMetrics(font);

                int textWidth = SwingUtilities.computeStringWidth(fontMetrics,
                        text);

                maxWidth = Math.max(maxWidth, textWidth);
            }
            else {
                cellWidth = comp.getPreferredSize().width;

                // maxWidth = Math.max ( headerWidth, cellWidth );
                maxWidth = Math.max(maxWidth, cellWidth);
            }
        }

        return (maxWidth + columnPadding);
    }
}