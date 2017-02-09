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
public abstract class BaseComboBoxModel<T> implements ComboBoxModel<T> {
    /**
     * List values.
     */
    protected T[] itemList;

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
    @Override
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
    @Override
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
    public T getElementAt(int index) {
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
