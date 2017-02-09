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
package net.lmxm.suafe.frames;

import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * Base class for all windows. Provides functions useful in all windows.
 *
 * @author Shaun Johnson
 */
public abstract class BaseFrame extends JFrame {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -6296033233946137131L;

    /**
     * Centers the frame on the user's screen.
     */
    public void center() {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();

        final int x = (screenSize.width - this.getWidth()) / 2;
        final int y = (screenSize.height - this.getHeight()) / 2;

        this.setLocation(x, y);
    }

    /**
     * Generic error message dialog.
     *
     * @param message Error message to display.
     */
    protected void displayError(@Nonnull final String message) {
        JOptionPane.showMessageDialog(this,
                message,
                ResourceUtil.getString("application.error"),
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Generic warning message dialog.
     *
     * @param message Warning message to display.
     */
    protected void displayWarning(@Nonnull final String message) {
        JOptionPane.showMessageDialog(this,
                message,
                ResourceUtil.getString("application.warning"),
                JOptionPane.WARNING_MESSAGE);
    }

    protected int showConfirmDialog(@Nonnull final String messageId, @Nonnull final String titleId) {
        return JOptionPane.showConfirmDialog(this, ResourceUtil.getString(messageId), ResourceUtil.getString(titleId),
                JOptionPane.YES_NO_OPTION);
    }

    protected int showWarnConfirmDialog(@Nonnull final String message) {
        return JOptionPane.showConfirmDialog(this, message, ResourceUtil.getString("application.warning"),
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }
}
