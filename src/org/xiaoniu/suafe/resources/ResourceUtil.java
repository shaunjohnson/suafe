/*
 * Created on Jul 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.resources;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Shaun Johnson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResourceUtil {

	protected static ResourceBundle bundle;
	
	protected static ResourceBundle getBundle() {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("org/xiaoniu/suafe/resources/Resources");
		}
		
		return bundle;
	}
	
	public static String getString(String name) {		
		return getBundle().getString(name);
	}
	
	public static String getFormattedString(String name, Object[] args) {
		return MessageFormat.format(getBundle().getString(name), args);
	}
	
	public static String getFormattedString(String name, String arg) {
		Object[] args = new Object[1];
		
		args[0] = arg;
		
		return MessageFormat.format(getBundle().getString(name), args);
	}
}
