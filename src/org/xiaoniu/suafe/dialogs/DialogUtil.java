package org.xiaoniu.suafe.dialogs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
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
