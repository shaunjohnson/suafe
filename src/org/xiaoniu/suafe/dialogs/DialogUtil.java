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

package org.xiaoniu.suafe.dialogs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * Parent class to all dialogs. Provides common functions useful in all
 * dialogs.
 * 
 * @author Shaun Johnson
 */
public class DialogUtil {
	
	public static void center(Component component) {
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		
		// Calculate the frame location
		int x = (screenSize.width - component.getWidth()) / 2;
		int y = (screenSize.height - component.getHeight()) / 2;

		// Set the new frame location
		component.setLocation(x, y);
	}
	
	public static void center(Component parent, Component child) {
		Dimension parentSize = parent.getSize();
		Point parentLocation = parent.getLocation();
		int x = (parentSize.width - child.getWidth()) / 2;
		int y = (parentSize.height - child.getHeight()) / 2;
		
		child.setLocation(parentLocation.x + x, parentLocation.y + y);
	}
}
