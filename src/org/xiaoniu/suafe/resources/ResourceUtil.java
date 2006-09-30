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

package org.xiaoniu.suafe.resources;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Utility class used to access application resources.
 * 
 * @author Shaun Johnson
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
