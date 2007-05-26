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

import java.awt.Font;
import java.util.Stack;
import java.util.prefs.Preferences;

/**
 * Retrieves and persists user settings stored using java Preferences.
 * 
 * @author Shaun Johnson
 */
public class UserPreferences {
	
	/**
	 * Prefix for recent file preferences. This prefix is appened with a 
	 * number from 0-max. 
	 */
	public static final String RECENT_FILE_PREFIX = "recent.file.";
	
	/**
	 * Preference name for the "open last edited file" setting.
	 */
	public static final String OPEN_LAST_FILE = "open.last.file";
	
	/**
	 * Preference name for the user selected font style.
	 */
	public static final String FONT_STYLE = "font.style";
	
	/**
	 * Maximum number of files remembered in the recent files list.
	 */
	public static final int MAXIMUM_RECENT_FILES = 10;
	
	/**
	 * Default font style.
	 */
	public static final String DEFAULT_FONT_STYLE = Constants.FONT_MONOSPACED;
	
	/**
	 * Handle to the Preferences node for the application.
	 */
	private static Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);;
		
	/**
	 * Retrieves a list of recently opened files as a Stack. Files are ordered
	 * by the last time each was opened.
	 * 
	 * @return Stack containing paths for recently opened files.
	 */
	public static Stack<String> getRecentFiles() {
		Stack<String> fileStack = new Stack<String>();
		
		for (int slot = 0; slot < MAXIMUM_RECENT_FILES; slot++) {
			String value = prefs.get(RECENT_FILE_PREFIX + slot, null);
			
			if (value != null) {
				fileStack.push(value);
			}
		}
		
		return fileStack;
	}
	
	/**
	 * Persists a list of recently opened files into Preferences.
	 * 
	 * @param fileStack List of paths for recently opened files.
	 */
	public static void setRecentFiles(Stack<String> fileStack) {
		int slot = 0;
		
		// Put each file in its own slot
		for(String value : fileStack) {
			if (slot >= MAXIMUM_RECENT_FILES) {
				break;
			}
			
			prefs.put(RECENT_FILE_PREFIX + slot, value);
			slot++;
		}
		
		// Remove unused slots
		for (; slot < MAXIMUM_RECENT_FILES; slot++) {
			prefs.remove(RECENT_FILE_PREFIX + slot);
		}
	}
	
	public static void clearRecentFiles() {
		// Remove all slots
		for (int slot = 0; slot < MAXIMUM_RECENT_FILES; slot++) {
			prefs.remove(RECENT_FILE_PREFIX + slot);
		}		
	}
	
	/**
	 * Retrieves "open last edited file" setting from Preferences.
	 * 
	 * @return true if setting is enabled, otherwise false
	 */
	public static boolean getOpenLastFile() {
		String selected = prefs.get(OPEN_LAST_FILE, Boolean.toString(false));
		
		return Boolean.parseBoolean(selected);
	}
	
	/**
	 * Persists "open last edited file" setting to Preferences.
	 * 
	 * @param selected true if setting is enabled.
	 */
	public static void setOpenLastFile(boolean selected) {
		prefs.put(OPEN_LAST_FILE, Boolean.toString(selected));
	}
	
	/**
	 * Retrieves user selected font style from Preferences
	 * 
	 * @return user font style
	 */
	public static String getUserFontStyle() {
		String fontStyle = prefs.get(FONT_STYLE, DEFAULT_FONT_STYLE);
		
		if (fontStyle.equals(Constants.FONT_MONOSPACED) ||
				fontStyle.equals(Constants.FONT_SERIF) ||
				fontStyle.equals(Constants.FONT_SANS_SERIF)) {
				return fontStyle;
			}
			else {
				return DEFAULT_FONT_STYLE;
			}
	}
	
	/**
	 * Persists user selected font style to Preferences.
	 * Only persists valid font style, otherwise saves the default.
	 * 
	 * @param fontStyle User selected font style
	 */
	public static void setUserFontStyle(String fontStyle) {
		if (fontStyle.equals(Constants.FONT_MONOSPACED) ||
			fontStyle.equals(Constants.FONT_SERIF) ||
			fontStyle.equals(Constants.FONT_SANS_SERIF)) {
			prefs.put(FONT_STYLE, fontStyle);
		}
		else {
			prefs.put(FONT_STYLE, DEFAULT_FONT_STYLE);
		}
	}
	
	/**
	 * Helper method that returns Font object for the user selected font style.
	 * 
	 * @return Font object of user selected font style
	 */
	public static Font getUserFont() {
		return new Font(getUserFontStyle(), Font.PLAIN, 12);
	}
}