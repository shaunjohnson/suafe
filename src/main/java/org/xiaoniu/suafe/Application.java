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
