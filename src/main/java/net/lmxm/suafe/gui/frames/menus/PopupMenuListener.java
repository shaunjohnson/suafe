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

package net.lmxm.suafe.gui.frames.menus;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Popup menu listener class.
 *
 * @author Shaun Johnson
 */
public final class PopupMenuListener extends MouseAdapter {
    private final JPopupMenu popupMenu;

    /**
     * Default Constructor.
     *
     * @param popupMenu Menu displayed upon click
     */
    public PopupMenuListener(@Nonnull final JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    /**
     * Display popup menu if requirements met.
     *
     * @param event MountEvent that triggered this
     */
    private void maybeShowPopup(@Nonnull final MouseEvent event) {
        if (event.isPopupTrigger()) {
            popupMenu.show(event.getComponent(), event.getX(), event.getY());
        }
    }

    /**
     * Mouse button pressed event handler.
     */
    @Override
    public void mousePressed(@Nonnull final MouseEvent e) {
        maybeShowPopup(e);
    }

    /**
     * Mouse button released event handler.
     */
    @Override
    public void mouseReleased(@Nonnull final MouseEvent e) {
        maybeShowPopup(e);
    }
}
