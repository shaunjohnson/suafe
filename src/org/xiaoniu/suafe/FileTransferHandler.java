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

    public boolean importData(JComponent component, Transferable transferable) {        
        //A real application would load the file in another
        //thread in order to not block the UI.  This step
        //was omitted here to simplify the code.
        try {
            if (hasFileFlavor(transferable.getTransferDataFlavors())) {
                List files = (List)transferable.getTransferData(DataFlavor.javaFileListFlavor);
                
                for (int i = 0; i < files.size(); i++) {
                    File file = (File)files.get(i);
                    
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
