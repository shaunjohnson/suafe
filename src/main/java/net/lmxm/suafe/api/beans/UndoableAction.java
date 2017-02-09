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

package net.lmxm.suafe.api.beans;

import java.util.HashMap;
import java.util.Map;

public final class UndoableAction extends AbstractAction {
    private Map<String, Object> values = new HashMap<>();

    public UndoableAction(final String action) {
        super(action);
    }

    public String getActionName() {
        // TODO Lookup from resource bundle;
        return action;
    }

    public void addValue(final String name, final Object value) {
        values.put(name, value);
    }

    public Object getValue(final String name) {
        return values.get(name);
    }
}
