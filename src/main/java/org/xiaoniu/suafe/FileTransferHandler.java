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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * 
 * @author Shaun Johnson
 */
public class FileTransferHandler extends TransferHandler {
	private static final long serialVersionUID = -6421801541654568376L;
	
	private FileOpener fileOpener = null;

    public FileTransferHandler(FileOpener fileOpener) {
    	super();
    	
    	this.fileOpener = fileOpener;
    }

	@SuppressWarnings("unchecked")
	public boolean importData(JComponent component, Transferable transferable) {        
        //A real application would load the file in another
        //thread in order to not block the UI.  This step
        //was omitted here to simplify the code.
        try {
            if (hasFileFlavor(transferable.getTransferDataFlavors())) {
                List<File> files = (List<File>)transferable.getTransferData(DataFlavor.javaFileListFlavor);
                
                for (File file : files) {
                    // Process file
                    fileOpener.fileOpen(file);
                }
                
                return true;
                
            }
        } 
        catch (UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
        } 
        catch (IOException ieo) {
            System.out.println("importData: I/O exception");
        }
        
        return false;
    }

    public boolean canImport(JComponent component, DataFlavor[] flavors) {
        if (hasFileFlavor(flavors)) { 
        	return true; 
        }

        return false;
    }
    
    public int getSourceActions(JComponent component) {
        return COPY_OR_MOVE;
    }

    private boolean hasFileFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                return true;
            }
        }
        
        return false;
    }
}
