package org.xiaoniu.suafe;

import javax.swing.JFrame;
import javax.swing.UIManager;

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
		
		JFrame main = new MainFrame();
		
		main.setVisible(true);
	}
}
