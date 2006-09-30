/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://suafe.xiaoniu.org.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://suafe.xiaoniu.org/.
 * ====================================================================
 * @endcopyright
 */
package org.xiaoniu.suafe;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.frames.MainFrame;

/**
 * Application starting point.
 * 
 * @author Shaun Johnson
 *
 * Where it all starts.
 */
public class Application {

	public static void main(String[] args) {	
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (Exception e) {
			
		}
		
		Document.initialize();
		
		JFrame main = new MainFrame();
		
		main.setVisible(true);
	}
}
