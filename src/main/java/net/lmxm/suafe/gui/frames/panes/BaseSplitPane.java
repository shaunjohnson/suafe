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

package net.lmxm.suafe.gui.frames.panes;

import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class BaseSplitPane extends JSplitPane {
    private static final long serialVersionUID = 6274498016581207351L;

    /**
     * Creates a button using the specified bundle key and action code.
     *
     * @param key      Key used to lookup button text in resource bundle
     * @param action   Action code to associate with the button
     * @param listener Action listener
     * @return Newly created button
     */
    @Nonnull
    protected JButton createButton(@Nonnull final String key, @Nullable final String action,
                                   final ActionListener listener) {
        final JButton button = new JButton();

        button.addActionListener(listener);

        if (action != null) {
            button.setActionCommand(action);
        }

        button.setText(ResourceUtil.getString(key));

        return button;
    }

    /**
     * Creates a button using the values.
     *
     * @param key        Key used to lookup button text in resource bundle
     * @param tooltipKey Key used to lookup tooltip text in resource bundle
     * @param icon       ImageIcon
     * @param action     Action code to associate with the button
     * @param listener   Action listener
     * @return Newly created button
     */
    @Nonnull
    protected JButton createButton(@Nonnull final String key, final String tooltipKey, final ImageIcon icon,
                                   final String action, final ActionListener listener) {
        final JButton button = new JButton();

        button.addActionListener(listener);
        button.setActionCommand(action);
        button.setText(ResourceUtil.getString(key));
        button.setIcon(icon);
        button.setToolTipText(ResourceUtil.getString(tooltipKey));

        return button;
    }

    /**
     * Create a label using the specified bundle key.
     *
     * @param key Key used to lookup label test in resource bundle
     * @return Newly created label
     */
    @Nonnull
    protected JLabel createLabel(@Nonnull final String key) {
        return new JLabel(ResourceUtil.getString(key));
    }
}
