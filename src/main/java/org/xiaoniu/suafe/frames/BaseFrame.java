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
package org.xiaoniu.suafe.frames;

import org.xiaoniu.suafe.resources.ResourceUtil;

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
