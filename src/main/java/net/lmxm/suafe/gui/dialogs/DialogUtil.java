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
package net.lmxm.suafe.gui.dialogs;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * Parent class to all dialogs. Provides common functions useful in all
 * dialogs.
 *
 * @author Shaun Johnson
 */
public final class DialogUtil {
    /**
     * Center the specified component on the screen.
     *
     * @param component Component to be centered.
     */
    public static void center(@Nonnull final Component component) {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();

        final int x = (screenSize.width - component.getWidth()) / 2;
        final int y = (screenSize.height - component.getHeight()) / 2;

        component.setLocation(x, y);
    }

    /**
     * Center the specivied component on the parent.
     *
     * @param parent Parent component
     * @param child  Child component
     */
    public static void center(@Nonnull final Component parent, @Nonnull final Component child) {
        final Dimension parentSize = parent.getSize();
        final Point parentLocation = parent.getLocation();

        final int x = (parentSize.width - child.getWidth()) / 2;
        final int y = (parentSize.height - child.getHeight()) / 2;

        child.setLocation(parentLocation.x + x, parentLocation.y + y);
    }
}
