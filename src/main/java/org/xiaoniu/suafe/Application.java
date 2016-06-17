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
package org.xiaoniu.suafe;

import org.xiaoniu.suafe.frames.MainFrame;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Application starting point. If no arguments are specified then the application GUI is initiated. Otherwise, the
 * command line application equivalent is initiated.
 *
 * @author Shaun Johnson
 */
public final class Application {
    /**
     * Application starting point.
     *
     * @param args Application arguments
     */
    public static void main(@Nonnull final String[] args) {
        if (args.length == 0) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            }
            catch (Exception e) {
                // Do nothing
            }

            new MainFrame().setVisible(true);
        }
        else {
            new CommandLineApplication().run(args);
        }
    }
}
