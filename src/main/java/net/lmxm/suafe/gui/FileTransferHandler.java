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
package net.lmxm.suafe.gui;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Shaun Johnson
 */
public final class FileTransferHandler extends TransferHandler {
    private static final long serialVersionUID = -6421801541654568376L;

    private FileOpener fileOpener = null;

    public FileTransferHandler(@Nonnull final FileOpener fileOpener) {
        this.fileOpener = fileOpener;
    }

    @SuppressWarnings("unchecked")
    public boolean importData(@Nonnull final JComponent component, @Nonnull final Transferable transferable) {
        //A real application would load the file in another
        //thread in order to not block the UI.  This step
        //was omitted here to simplify the code.
        try {
            if (hasFileFlavor(transferable.getTransferDataFlavors())) {
                final List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                for (final File file : files) {
                    // Process file
                    fileOpener.fileOpen(file);
                }

                return true;
            }
        }
        catch (final UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
        }
        catch (final IOException ieo) {
            System.out.println("importData: I/O exception");
        }

        return false;
    }

    public boolean canImport(@Nonnull final JComponent component, @Nonnull final DataFlavor[] flavors) {
        if (hasFileFlavor(flavors)) {
            return true;
        }

        return false;
    }

    public int getSourceActions(@Nonnull final JComponent component) {
        return COPY_OR_MOVE;
    }

    private boolean hasFileFlavor(@Nonnull final DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                return true;
            }
        }

        return false;
    }
}
