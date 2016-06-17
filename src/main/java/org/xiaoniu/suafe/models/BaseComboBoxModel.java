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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for all application combo-boxes.
 *
 * @author Shaun Johnson
 */
public abstract class BaseComboBoxModel implements ComboBoxModel {
    /**
     * List values.
     */
    protected Object[] itemList;

    /**
     * Selected item index.
     */
    protected int selectedItem;

    /**
     * Array of listeners.
     * TODO Not thread-safe
     */
    protected final List<ListDataListener> listeners = new ArrayList<>();

    /**
     * Default constructor.
     */
    public BaseComboBoxModel() {
        this.itemList = null;
        this.selectedItem = -1;
    }

    /**
     * Sets the selected item index.
     *
     * @param anItem Item to be selected.
     */
    public void setSelectedItem(final Object anItem) {
        if (itemList == null || anItem == null) {
            selectedItem = -1;
        }
        else {
            selectedItem = Arrays.binarySearch(itemList, anItem);
        }
    }

    /**
     * Gets the selected item.
     */
    @Nullable
    public Object getSelectedItem() {
        return (itemList == null || selectedItem == -1) ? null : itemList[selectedItem];
    }

    /**
     * Gets the list size.
     */
    public int getSize() {
        return (itemList == null) ? 0 : itemList.length;
    }

    /**
     * Gets object at specified index.
     *
     * @param index Index of object to retrieve.
     */
    @Nullable
    public Object getElementAt(int index) {
        return (itemList == null) ? null : itemList[index];
    }

    /**
     * Adds listener.
     *
     * @param listener Listener to add.
     */
    public void addListDataListener(@Nonnull final ListDataListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener.
     *
     * @param listener Listener to remove.
     */
    public void removeListDataListener(@Nonnull final ListDataListener listener) {
        listeners.remove(listeners.indexOf(listener));
    }
}
