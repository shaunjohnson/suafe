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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.Stack;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

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
	 * Preference name for the "".
	 */
	public static final String MULTILINE_GROUP_DEFINITIONS = "multiline.group.defintions";
	
	/**
	 * Preference name for the user selected font style.
	 */
	public static final String FONT_STYLE = "font.style";
	
	public static final String WINDOW_LOCATION = "window.location";
	
	/**
	 * Preference name for the last window state.
	 */
	public static final String WINDOW_STATE = "window.state";
	
	/**
	 * Preference name for the last window dimension.
	 */
	public static final String WINDOW_SIZE = "window.size";
	
	/**
	 * Preference names for divider locations;
	 */
	public static final String USERS_PANE_DIVIDER_LOCATION = "users.pane.divider.location";
	public static final String GROUPS_PANE_DIVIDER_LOCATION = "groups.pane.divider.location";
	public static final String RULES_PANE_DIVIDER_LOCATION = "rules.pane.divider.location";
	public static final String USER_DETAILS_DIVIDER_LOCATION = "user.details.divider.location";
	public static final String GROUP_DETAILS_DIVIDER_LOCATION = "group.details.divider.location";
	
	/**
	 * Maximum number of files remembered in the recent files list.
	 */
	public static final int MAXIMUM_RECENT_FILES = 10;
	
	/**
	 * Default font style.
	 */
	public static final String DEFAULT_FONT_STYLE = GuiConstants.FONT_FAMILY_MONOSPACED;
	
	private static Font userFont = null;
	
	/**
	 * Handle to the Preferences node for the application.
	 */
	private static Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);;
		
	public static void clearRecentFiles() {
		// Remove all slots
		for (int slot = 0; slot < MAXIMUM_RECENT_FILES; slot++) {
			prefs.remove(RECENT_FILE_PREFIX + slot);
		}		
	}
	
	public static int getGroupDetailsDividerLocation() {
		return Integer.parseInt(prefs.get(GROUP_DETAILS_DIVIDER_LOCATION, 
				ApplicationDefaultsContants.DEFAULT_DIVIDER_LOCATION));
	}
	
	public static int getGroupsPaneDividerLocation() {
		return Integer.parseInt(prefs.get(GROUPS_PANE_DIVIDER_LOCATION, 
				ApplicationDefaultsContants.DEFAULT_DIVIDER_LOCATION));
	}
	
	/**
	 * Retrieves "multiline group definitions" setting from Preferences.
	 * 
	 * @return true if setting is enabled, otherwise false
	 */
	public static boolean getMultipleLineGroupDefinitions() {
		String selected = prefs.get(MULTILINE_GROUP_DEFINITIONS, Boolean.toString(true));
		
		return Boolean.parseBoolean(selected);
	}
	
	/**
	 * Retrieves "open last edited file" setting from Preferences.
	 * 
	 * @return true if setting is enabled, otherwise false
	 */
	public static boolean getOpenLastFile() {
		String selected = prefs.get(OPEN_LAST_FILE, Boolean.toString(true));
		
		return Boolean.parseBoolean(selected);
	}
	
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
	
	public static int getRulesPaneDividerLocation() {
		return Integer.parseInt(prefs.get(RULES_PANE_DIVIDER_LOCATION, 
				ApplicationDefaultsContants.DEFAULT_DIVIDER_LOCATION));
	}
	
	public static int getUserDetailsDividerLocation() {
		return Integer.parseInt(prefs.get(USER_DETAILS_DIVIDER_LOCATION, 
				ApplicationDefaultsContants.DEFAULT_DIVIDER_LOCATION));
	}
	
	/**
	 * Helper method that returns Font object for the user selected font style.
	 * 
	 * @return Font object of user selected font style
	 */
	public static Font getUserFont() {
		if (userFont == null) { 
			userFont = new Font(getUserFontStyle(), Font.PLAIN, 12);
		}
		
		return userFont;
	}
	
	/**
	 * Retrieves user selected font style from Preferences
	 * 
	 * @return user font style
	 */
	public static String getUserFontStyle() {
		String fontStyle = prefs.get(FONT_STYLE, DEFAULT_FONT_STYLE);
		
		if (fontStyle.equals(GuiConstants.FONT_FAMILY_MONOSPACED) ||
				fontStyle.equals(GuiConstants.FONT_FAMILY_SERIF) ||
				fontStyle.equals(GuiConstants.FONT_FAMILY_SANS_SERIF)) {
				return fontStyle;
			}
			else {
				return DEFAULT_FONT_STYLE;
			}
	}
	
	public static int getUsersPaneDividerLocation() {
		return Integer.parseInt(prefs.get(USERS_PANE_DIVIDER_LOCATION, 
				ApplicationDefaultsContants.DEFAULT_DIVIDER_LOCATION));
	}
	
	public static Point getWindowLocation() {
		Point location = null;
		String locationValue = prefs.get(WINDOW_LOCATION, "");
		String[] locationValues = locationValue.split(",");
		
		if (locationValues.length == 2) {
			try {
				int x = Integer.parseInt(locationValues[0]);
				int y = Integer.parseInt(locationValues[1]);
				
				location = new Point(x, y);
			}
			catch(Exception e) {
				location = null;
			}
		}
		
		return location;
	}
	
	public static Dimension getWindowSize() {
		String size = prefs.get(WINDOW_SIZE, 
				ApplicationDefaultsContants.DEFAULT_WIDTH + "," + ApplicationDefaultsContants.DEFAULT_HEIGHT);
		
		String[] sizes = size.split(",");
		int width = ApplicationDefaultsContants.DEFAULT_WIDTH;
		int height = ApplicationDefaultsContants.DEFAULT_HEIGHT;
		
		if (sizes.length == 2) {
			try {
				width = Integer.parseInt(sizes[0]);
				height = Integer.parseInt(sizes[1]);
			}
			catch(Exception e) {
				width = ApplicationDefaultsContants.DEFAULT_WIDTH;
				height = ApplicationDefaultsContants.DEFAULT_HEIGHT;
			}
		}
		
		return new Dimension(width, height);
	}
	
	public static int getWindowState() {
		try {
			return Integer.parseInt(prefs.get(WINDOW_STATE, 
					Integer.toString(JFrame.MAXIMIZED_BOTH)));
		}
		catch(Exception e) {
			return JFrame.MAXIMIZED_BOTH;
		}
	}
	
	public static void resetSettings() {
		prefs.remove(FONT_STYLE);
		prefs.remove(GROUP_DETAILS_DIVIDER_LOCATION);
		prefs.remove(GROUPS_PANE_DIVIDER_LOCATION);
		prefs.remove(OPEN_LAST_FILE);
		prefs.remove(RULES_PANE_DIVIDER_LOCATION);
		prefs.remove(USER_DETAILS_DIVIDER_LOCATION);
		prefs.remove(USERS_PANE_DIVIDER_LOCATION);
		prefs.remove(WINDOW_LOCATION);
		prefs.remove(WINDOW_SIZE);
		prefs.remove(WINDOW_STATE);
	}

	public static void setGroupDetailsDividerLocation(int location) {
		prefs.put(GROUP_DETAILS_DIVIDER_LOCATION, Integer.toString(location));
	}

	public static void setGroupsPaneDividerLocation(int location) {
		prefs.put(GROUPS_PANE_DIVIDER_LOCATION, Integer.toString(location));
	}

	/**
	 * Persists "multiline group definitions" setting to Preferences.
	 * 
	 * @param selected true if setting is enabled.
	 */
	public static void setMultipleLineGroupDefinitions(boolean selected) {
		prefs.put(MULTILINE_GROUP_DEFINITIONS, Boolean.toString(selected));
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
	
	public static void setRulesPaneDividerLocation(int location) {
		prefs.put(RULES_PANE_DIVIDER_LOCATION, Integer.toString(location));
	}

	public static void setUserDetailsDividerLocation(int location) {
		prefs.put(USER_DETAILS_DIVIDER_LOCATION, Integer.toString(location));
	}

	/**
	 * Persists user selected font style to Preferences.
	 * Only persists valid font style, otherwise saves the default.
	 * 
	 * @param fontStyle User selected font style
	 */
	public static void setUserFontStyle(String fontStyle) {
		if (fontStyle.equals(GuiConstants.FONT_FAMILY_MONOSPACED) ||
			fontStyle.equals(GuiConstants.FONT_FAMILY_SERIF) ||
			fontStyle.equals(GuiConstants.FONT_FAMILY_SANS_SERIF)) {
			prefs.put(FONT_STYLE, fontStyle);
		}
		else {
			prefs.put(FONT_STYLE, DEFAULT_FONT_STYLE);
		}
	}

	public static void setUsersPaneDividerLocation(int location) {
		prefs.put(USERS_PANE_DIVIDER_LOCATION, Integer.toString(location));
	}

	public static void setWindowLocation(Point location) {
		prefs.put(WINDOW_LOCATION, (int)location.getX() + "," + (int)location.getY());
	}
	
	public static void setWindowSize(Dimension size) {
		prefs.put(WINDOW_SIZE, (int)size.getWidth() + "," + (int)size.getHeight());
	}
	
	public static void setWindowState(int windowState) {
		prefs.put(WINDOW_STATE, Integer.toString(windowState));
	}
}