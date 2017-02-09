/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.xiaoniu.suafe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.util.prefs.Preferences;

/**
 * Retrieves and persists user settings stored using java Preferences.
 *
 * @author Shaun Johnson
 */
public final class UserPreferences {
    /**
     * Prefix for recent file preferences. This prefix is appened with a
     * number from 0-max.
     */
    private static final String RECENT_FILE_PREFIX = "recent.file.";

    /**
     * Preference name for the "open last edited file" setting.
     */
    private static final String OPEN_LAST_FILE = "open.last.file";

    /**
     * Preference name for the "".
     */
    private static final String MULTILINE_GROUP_DEFINITIONS = "multiline.group.defintions";

    /**
     * Preference name for the user selected font style.
     */
    private static final String FONT_STYLE = "font.style";

    private static final String WINDOW_LOCATION = "window.location";

    /**
     * Preference name for the last window state.
     */
    private static final String WINDOW_STATE = "window.state";

    /**
     * Preference name for the last window dimension.
     */
    private static final String WINDOW_SIZE = "window.size";

    /**
     * Preference names for divider locations;
     */
    private static final String USERS_PANE_DIVIDER_LOCATION = "users.pane.divider.location";
    private static final String GROUPS_PANE_DIVIDER_LOCATION = "groups.pane.divider.location";
    private static final String RULES_PANE_DIVIDER_LOCATION = "rules.pane.divider.location";
    private static final String USER_DETAILS_DIVIDER_LOCATION = "user.details.divider.location";
    private static final String GROUP_DETAILS_DIVIDER_LOCATION = "group.details.divider.location";

    /**
     * Maximum number of files remembered in the recent files list.
     */
    public static final int MAXIMUM_RECENT_FILES = 10;

    /**
     * Default font style.
     */
    private static final String DEFAULT_FONT_STYLE = GuiConstants.FONT_FAMILY_MONOSPACED;

    private static Font userFont = null;

    /**
     * Handle to the Preferences node for the application.
     */
    private static Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);

    public static void clearRecentFiles() {
        // Remove all slots
        for (int slot = 0; slot < MAXIMUM_RECENT_FILES; slot++) {
            prefs.remove(RECENT_FILE_PREFIX + slot);
        }
    }

    public static int getGroupDetailsDividerLocation() {
        return Integer.parseInt(prefs.get(GROUP_DETAILS_DIVIDER_LOCATION,
                ApplicationDefaultsConstants.DEFAULT_DIVIDER_LOCATION));
    }

    public static int getGroupsPaneDividerLocation() {
        return Integer.parseInt(prefs.get(GROUPS_PANE_DIVIDER_LOCATION,
                ApplicationDefaultsConstants.DEFAULT_DIVIDER_LOCATION));
    }

    /**
     * Retrieves "multiline group definitions" setting from Preferences.
     *
     * @return true if setting is enabled, otherwise false
     */
    public static boolean getMultipleLineGroupDefinitions() {
        final String selected = prefs.get(MULTILINE_GROUP_DEFINITIONS, Boolean.toString(true));

        return Boolean.parseBoolean(selected);
    }

    /**
     * Retrieves "open last edited file" setting from Preferences.
     *
     * @return true if setting is enabled, otherwise false
     */
    public static boolean getOpenLastFile() {
        final String selected = prefs.get(OPEN_LAST_FILE, Boolean.toString(true));

        return Boolean.parseBoolean(selected);
    }

    /**
     * Retrieves a list of recently opened files as a Stack. Files are ordered
     * by the last time each was opened.
     *
     * @return Stack containing paths for recently opened files.
     */
    @Nonnull
    public static Stack<String> getRecentFiles() {
        final Stack<String> fileStack = new Stack<>();

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
                ApplicationDefaultsConstants.DEFAULT_DIVIDER_LOCATION));
    }

    public static int getUserDetailsDividerLocation() {
        return Integer.parseInt(prefs.get(USER_DETAILS_DIVIDER_LOCATION,
                ApplicationDefaultsConstants.DEFAULT_DIVIDER_LOCATION));
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
    @Nonnull
    public static String getUserFontStyle() {
        final String fontStyle = prefs.get(FONT_STYLE, DEFAULT_FONT_STYLE);

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
                ApplicationDefaultsConstants.DEFAULT_DIVIDER_LOCATION));
    }

    @Nullable
    public static Point getWindowLocation() {
        Point location = null;
        final String locationValue = prefs.get(WINDOW_LOCATION, "");
        final String[] locationValues = locationValue.split(",");

        if (locationValues.length == 2) {
            try {
                final int x = Integer.parseInt(locationValues[0]);
                final int y = Integer.parseInt(locationValues[1]);

                location = new Point(x, y);
            }
            catch (final Exception e) {
                location = null;
            }
        }

        return location;
    }

    @Nonnull
    public static Dimension getWindowSize() {
        final String size = prefs.get(WINDOW_SIZE,
                ApplicationDefaultsConstants.DEFAULT_WIDTH + "," + ApplicationDefaultsConstants.DEFAULT_HEIGHT);

        final String[] sizes = size.split(",");
        int width = ApplicationDefaultsConstants.DEFAULT_WIDTH;
        int height = ApplicationDefaultsConstants.DEFAULT_HEIGHT;

        if (sizes.length == 2) {
            try {
                width = Integer.parseInt(sizes[0]);
                height = Integer.parseInt(sizes[1]);
            }
            catch (final Exception e) {
                width = ApplicationDefaultsConstants.DEFAULT_WIDTH;
                height = ApplicationDefaultsConstants.DEFAULT_HEIGHT;
            }
        }

        return new Dimension(width, height);
    }

    public static int getWindowState() {
        try {
            return Integer.parseInt(prefs.get(WINDOW_STATE,
                    Integer.toString(JFrame.MAXIMIZED_BOTH)));
        }
        catch (final Exception e) {
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

    public static void setGroupDetailsDividerLocation(final int location) {
        prefs.put(GROUP_DETAILS_DIVIDER_LOCATION, Integer.toString(location));
    }

    public static void setGroupsPaneDividerLocation(final int location) {
        prefs.put(GROUPS_PANE_DIVIDER_LOCATION, Integer.toString(location));
    }

    /**
     * Persists "multiline group definitions" setting to Preferences.
     *
     * @param selected true if setting is enabled.
     */
    public static void setMultipleLineGroupDefinitions(final boolean selected) {
        prefs.put(MULTILINE_GROUP_DEFINITIONS, Boolean.toString(selected));
    }

    /**
     * Persists "open last edited file" setting to Preferences.
     *
     * @param selected true if setting is enabled.
     */
    public static void setOpenLastFile(final boolean selected) {
        prefs.put(OPEN_LAST_FILE, Boolean.toString(selected));
    }

    /**
     * Persists a list of recently opened files into Preferences.
     *
     * @param fileStack List of paths for recently opened files.
     */
    public static void setRecentFiles(@Nonnull final Stack<String> fileStack) {
        int slot = 0;

        // Put each file in its own slot
        for (final String value : fileStack) {
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

    public static void setRulesPaneDividerLocation(final int location) {
        prefs.put(RULES_PANE_DIVIDER_LOCATION, Integer.toString(location));
    }

    public static void setUserDetailsDividerLocation(final int location) {
        prefs.put(USER_DETAILS_DIVIDER_LOCATION, Integer.toString(location));
    }

    /**
     * Persists user selected font style to Preferences.
     * Only persists valid font style, otherwise saves the default.
     *
     * @param fontStyle User selected font style
     */
    public static void setUserFontStyle(@Nonnull final String fontStyle) {
        if (fontStyle.equals(GuiConstants.FONT_FAMILY_MONOSPACED) ||
                fontStyle.equals(GuiConstants.FONT_FAMILY_SERIF) ||
                fontStyle.equals(GuiConstants.FONT_FAMILY_SANS_SERIF)) {
            prefs.put(FONT_STYLE, fontStyle);
        }
        else {
            prefs.put(FONT_STYLE, DEFAULT_FONT_STYLE);
        }
    }

    public static void setUsersPaneDividerLocation(final int location) {
        prefs.put(USERS_PANE_DIVIDER_LOCATION, Integer.toString(location));
    }

    public static void setWindowLocation(@Nonnull final Point location) {
        prefs.put(WINDOW_LOCATION, (int) location.getX() + "," + (int) location.getY());
    }

    public static void setWindowSize(@Nonnull final Dimension size) {
        prefs.put(WINDOW_SIZE, (int) size.getWidth() + "," + (int) size.getHeight());
    }

    public static void setWindowState(final int windowState) {
        prefs.put(WINDOW_STATE, Integer.toString(windowState));
    }

    private UserPreferences() {
        // Deliberately left blank
    }
}