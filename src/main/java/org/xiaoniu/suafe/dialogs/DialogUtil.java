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
package org.xiaoniu.suafe.dialogs;

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
