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

import net.lmxm.suafe.api.beans.Document;
import net.lmxm.suafe.api.beans.Group;

import javax.annotation.Nonnull;

/**
 * Group list for a combo-box. A combo-box listing out all Group objects
 * from the current document.
 *
 * @author Shaun Johnson
 */
public final class GroupListModel extends BaseComboBoxModel<Group> {
    /**
     * Default constructor.
     */
    public GroupListModel(@Nonnull final Document document) {
        itemList = document.getGroupObjects();
    }
}
