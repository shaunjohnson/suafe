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

package org.xiaoniu.suafe.frames.menus;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.GuiConstants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Stack;

/**
 * Menu bar components used by MainFrame.
 *
 * @author Shaun Johnson
 */
public final class MainFrameMenuBar extends JMenuBar {
    private static final long serialVersionUID = -70557657703079354L;

    private final ActionListener actionListener;

    private JMenuItem aboutMenuItem;

    private JMenu actionMenu;

    private JMenuItem addAccessRuleMenuItem;

    private JMenuItem addGroupMenuItem;

    private JMenuItem addProjectAccessRulesMenuItem;

    private JMenuItem addUserMenuItem;

    private JMenuItem clearRecentFilesMenuItem;

    private JMenuItem exitMenuItem;

    private JMenu fileMenu;

    private JMenu helpMenu;

    private JMenuItem helpMenuItem;

    private JMenuItem licenseMenuItem;

    private JRadioButtonMenuItem monospacedRadioButtonMenuItem;

    private JMenuItem newFileMenuItem;

    private JMenuItem openFileMenuItem;

    private JMenuItem openLastFileMenuItem;

    private JMenuItem multiLineGroupDefinitionsMenuItem;

    private JMenuItem previewMenuItem;

    private JMenuItem printMenuItem;

    private JMenu recentFilesMenu;

    private JMenuItem reloadMenuItem;

    private JMenu reportsMenu;

    private JMenuItem resetSettingsMenuItem;

    private JRadioButtonMenuItem sansSerifRadioButtonMenuItem;

    private JMenuItem saveAsMenuItem;

    private JMenuItem saveFileMenuItem;

    private JRadioButtonMenuItem serifRadioButtonMenuItem;

    private JMenu settingsMenu;

    private JMenuItem statisticsMenuItem;

    private JMenuItem summaryReportMenuItem;

    private JMenuItem viewGroupsMenuItem;

    private JMenu viewMenu;

    private JMenuItem viewRulesMenuItem;

    private JMenuItem viewUsersMenuItem;

    public MainFrameMenuBar(@Nonnull final ActionListener actionListener) {
        this.actionListener = actionListener;

        add(getFileMenu());
        add(getActionMenu());
        add(getViewMenu());
        add(getReportsMenu());
        add(getSettingsMenu());
        add(getHelpMenu());
    }

    /**
     * This method initializes aboutMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getAboutMenuItem() {
        if (aboutMenuItem == null) {
            aboutMenuItem = new JMenuItem();
            aboutMenuItem.addActionListener(actionListener);
            aboutMenuItem.setActionCommand(ActionConstants.ABOUT_ACTION);
            aboutMenuItem.setIcon(ResourceUtil.aboutIcon);
            aboutMenuItem.setText(ResourceUtil.getString("menu.help.about"));
        }

        return aboutMenuItem;
    }

    /**
     * This method initializes actionMenu.
     *
     * @return javax.swing.JMenu
     */
    public JMenu getActionMenu() {
        if (actionMenu == null) {
            actionMenu = new JMenu();
            actionMenu.setText(ResourceUtil.getString("menu.action"));
            actionMenu.add(getAddUserMenuItem());
            actionMenu.add(getAddGroupMenuItem());
            actionMenu.add(getAddAccessRuleMenuItem());
            actionMenu.add(getAddProjectAccessRulesMenuItem());
        }

        return actionMenu;
    }

    /**
     * This method initializes addAccessRuleMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getAddAccessRuleMenuItem() {
        if (addAccessRuleMenuItem == null) {
            addAccessRuleMenuItem = new JMenuItem();
            addAccessRuleMenuItem.addActionListener(actionListener);
            addAccessRuleMenuItem.setActionCommand(ActionConstants.ADD_ACCESS_RULE_ACTION);
            addAccessRuleMenuItem.setIcon(ResourceUtil.addAccessRuleIcon);
            addAccessRuleMenuItem.setText(ResourceUtil.getString("menu.action.addaccessrule"));
            addAccessRuleMenuItem.setAccelerator(KeyStroke.getKeyStroke('R',
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return addAccessRuleMenuItem;
    }

    /**
     * This method initializes addGroupMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getAddGroupMenuItem() {
        if (addGroupMenuItem == null) {
            addGroupMenuItem = new JMenuItem();
            addGroupMenuItem.addActionListener(actionListener);
            addGroupMenuItem.setActionCommand(ActionConstants.ADD_GROUP_ACTION);
            addGroupMenuItem.setIcon(ResourceUtil.addGroupIcon);
            addGroupMenuItem.setText(ResourceUtil.getString("menu.action.addgroup"));
            addGroupMenuItem.setAccelerator(KeyStroke.getKeyStroke('G',
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return addGroupMenuItem;
    }

    /**
     * This method initializes addProjectAccessRulesMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getAddProjectAccessRulesMenuItem() {
        if (addProjectAccessRulesMenuItem == null) {
            addProjectAccessRulesMenuItem = new JMenuItem();
            addProjectAccessRulesMenuItem.addActionListener(actionListener);
            addProjectAccessRulesMenuItem.setActionCommand(ActionConstants.ADD_PROJECT_ACCESS_RULES_ACTION);
            addProjectAccessRulesMenuItem.setIcon(ResourceUtil.addProjectAccessRulesIcon);
            addProjectAccessRulesMenuItem.setText(ResourceUtil.getString("menu.action.addprojectaccessrules"));
            addProjectAccessRulesMenuItem.setAccelerator(KeyStroke.getKeyStroke('T',
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return addProjectAccessRulesMenuItem;
    }

    /**
     * This method initializes addUserMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getAddUserMenuItem() {
        if (addUserMenuItem == null) {
            addUserMenuItem = new JMenuItem();
            addUserMenuItem.addActionListener(actionListener);
            addUserMenuItem.setActionCommand(ActionConstants.ADD_USER_ACTION);
            addUserMenuItem.setIcon(ResourceUtil.addUserIcon);
            addUserMenuItem.setText(ResourceUtil.getString("menu.action.adduser"));
            addUserMenuItem.setAccelerator(KeyStroke.getKeyStroke('U',
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return addUserMenuItem;
    }

    /**
     * This method initializes clearRecentFilesMenuItem
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getClearRecentFilesMenuItem() {
        if (clearRecentFilesMenuItem == null) {
            clearRecentFilesMenuItem = new JMenuItem(ResourceUtil.getString("menu.file.clearrecentfiles"));
            clearRecentFilesMenuItem.addActionListener(actionListener);
            clearRecentFilesMenuItem.setActionCommand(ActionConstants.CLEAR_RECENT_FILES_ACTION);
        }
        return clearRecentFilesMenuItem;
    }

    /**
     * This method initializes exitMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getExitMenuItem() {
        if (exitMenuItem == null) {
            exitMenuItem = new JMenuItem();
            exitMenuItem.addActionListener(actionListener);
            exitMenuItem.setActionCommand(ActionConstants.EXIT_ACTION);
            exitMenuItem.setText(ResourceUtil.getString("menu.file.exit"));
            exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.exit.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return exitMenuItem;
    }

    /**
     * This method initializes fileMenu.
     *
     * @return javax.swing.JMenu
     */
    public JMenu getFileMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu();
            fileMenu.setText(ResourceUtil.getString("menu.file"));
            fileMenu.add(getNewFileMenuItem());
            fileMenu.add(getOpenFileMenuItem());
            fileMenu.add(getSaveFileMenuItem());
            fileMenu.add(getSaveAsMenuItem());
            fileMenu.add(new JSeparator());
            fileMenu.add(getReloadMenuItem());
            fileMenu.add(new JSeparator());
            fileMenu.add(getRecentFilesMenu());
            fileMenu.add(getClearRecentFilesMenuItem());
            fileMenu.add(new JSeparator());

            // Printing is currently disabled.
            //fileMenu.add(getPrintMenuItem());
            //fileMenu.add(new JSeparator());

            fileMenu.add(getExitMenuItem());
        }

        return fileMenu;
    }

    /**
     * This method initializes helpMenu.
     *
     * @return javax.swing.JMenu
     */
    public JMenu getHelpMenu() {
        if (helpMenu == null) {
            helpMenu = new JMenu();
            helpMenu.setText(ResourceUtil.getString("menu.help"));
            helpMenu.add(getHelpMenuItem());
            helpMenu.add(getLicenseMenuItem());
            helpMenu.add(getAboutMenuItem());
        }

        return helpMenu;
    }

    /**
     * This method initializes helpMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getHelpMenuItem() {
        if (helpMenuItem == null) {
            helpMenuItem = new JMenuItem();
            helpMenuItem.setIcon(ResourceUtil.helpIcon);
            helpMenuItem.setText(ResourceUtil.getString("menu.help.help"));
        }

        return helpMenuItem;
    }

    /**
     * This method initializes licenseMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getLicenseMenuItem() {
        if (licenseMenuItem == null) {
            licenseMenuItem = new JMenuItem();
            licenseMenuItem.setIcon(ResourceUtil.licenseIcon);
            licenseMenuItem.setText(ResourceUtil.getString("menu.help.license"));
        }

        return licenseMenuItem;
    }

    /**
     * This method initializes monospacedRadioButtonMenuItem
     *
     * @return javax.swing.JRadioButtonMenuItem
     */
    public JRadioButtonMenuItem getMonospacedRadioButtonMenuItem() {
        if (monospacedRadioButtonMenuItem == null) {
            monospacedRadioButtonMenuItem = new JRadioButtonMenuItem();
            monospacedRadioButtonMenuItem.setText(ResourceUtil.getString("menu.settings.monospaced"));
            monospacedRadioButtonMenuItem.setFont(new Font(GuiConstants.FONT_FAMILY_MONOSPACED, Font.BOLD, 12));
            monospacedRadioButtonMenuItem.addActionListener(actionListener);
            monospacedRadioButtonMenuItem.setActionCommand(ActionConstants.MONOSPACED_ACTION);

            if (UserPreferences.getUserFontStyle().equals(GuiConstants.FONT_FAMILY_MONOSPACED)) {
                monospacedRadioButtonMenuItem.setSelected(true);
            }
        }
        return monospacedRadioButtonMenuItem;
    }

    /**
     * This method initializes newFileMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getNewFileMenuItem() {
        if (newFileMenuItem == null) {
            newFileMenuItem = new JMenuItem();
            newFileMenuItem.addActionListener(actionListener);
            newFileMenuItem.setActionCommand(ActionConstants.NEW_FILE_ACTION);
            newFileMenuItem.setIcon(ResourceUtil.newFileIcon);
            newFileMenuItem.setText(ResourceUtil.getString("menu.file.new"));
            newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.new.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return newFileMenuItem;
    }

    /**
     * This method initializes openFileMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getOpenFileMenuItem() {
        if (openFileMenuItem == null) {
            openFileMenuItem = new JMenuItem();
            openFileMenuItem.addActionListener(actionListener);
            openFileMenuItem.setActionCommand(ActionConstants.OPEN_FILE_ACTION);
            openFileMenuItem.setIcon(ResourceUtil.openFileIcon);
            openFileMenuItem.setText(ResourceUtil.getString("menu.file.open"));
            openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.open.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return openFileMenuItem;
    }

    /**
     * This method initializes openLastFileMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getOpenLastFileMenuItem() {
        if (openLastFileMenuItem == null) {
            openLastFileMenuItem = new JCheckBoxMenuItem();
            openLastFileMenuItem.setText(ResourceUtil.getString("menu.settings.openlastfile"));
            openLastFileMenuItem.addActionListener(actionListener);
            openLastFileMenuItem.setActionCommand(ActionConstants.OPEN_LAST_EDITED_FILE_ACTION);
            openLastFileMenuItem.setSelected(UserPreferences.getOpenLastFile());
        }

        return openLastFileMenuItem;
    }

    /**
     * This method initializes multiLineGroupDefinitionMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getMultiLineGroupDefinitionsMenuItem() {
        if (multiLineGroupDefinitionsMenuItem == null) {
            multiLineGroupDefinitionsMenuItem = new JCheckBoxMenuItem();
            multiLineGroupDefinitionsMenuItem.setText(ResourceUtil.getString("menu.settings.multilinegroups"));
            multiLineGroupDefinitionsMenuItem.addActionListener(actionListener);
            multiLineGroupDefinitionsMenuItem.setActionCommand(ActionConstants.MULTIPLE_LINE_GROUP_DEFINITION_ACTION);
            multiLineGroupDefinitionsMenuItem.setSelected(UserPreferences.getMultipleLineGroupDefinitions());
        }

        return multiLineGroupDefinitionsMenuItem;
    }

    /**
     * This method initializes previewMenuItem
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getPreviewMenuItem() {
        if (previewMenuItem == null) {
            previewMenuItem = new JMenuItem();
            previewMenuItem.setText(ResourceUtil.getString("menu.reports.preview"));
            previewMenuItem.addActionListener(actionListener);
            previewMenuItem.setActionCommand(ActionConstants.PREVIEW_ACTION);
        }
        return previewMenuItem;
    }

    /**
     * This method initializes printMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    @SuppressWarnings("unused")
    private JMenuItem getPrintMenuItem() {
        if (printMenuItem == null) {
            printMenuItem = new JMenuItem();
            printMenuItem.addActionListener(actionListener);
            printMenuItem.setActionCommand(ActionConstants.PRINT_ACTION);
            printMenuItem.setIcon(ResourceUtil.printIcon);
            printMenuItem.setText(ResourceUtil.getString("menu.file.print"));
            printMenuItem.setToolTipText(ResourceUtil.getString("menu.file.print.tooltip"));
            printMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.print.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return printMenuItem;
    }

    /**
     * This method initializes recentFilesMenu.
     *
     * @return javax.swing.JMenu
     */
    public JMenu getRecentFilesMenu() {
        if (recentFilesMenu == null) {
            recentFilesMenu = new JMenu(ResourceUtil.getString("menu.file.recentfiles"));
        }

        return recentFilesMenu;
    }

    /**
     * This method initializes reloadMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getReloadMenuItem() {
        if (reloadMenuItem == null) {
            reloadMenuItem = new JMenuItem();
            reloadMenuItem.addActionListener(actionListener);
            reloadMenuItem.setActionCommand(ActionConstants.RELOAD_ACTION);
            reloadMenuItem.setIcon(ResourceUtil.reloadIcon);
            reloadMenuItem.setText(ResourceUtil.getString("menu.file.reload"));
            reloadMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.reload.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return reloadMenuItem;
    }

    /**
     * This method initializes reportsMenu
     *
     * @return javax.swing.JMenu
     */
    public JMenu getReportsMenu() {
        if (reportsMenu == null) {
            reportsMenu = new JMenu();
            reportsMenu.setText(ResourceUtil.getString("menu.reports"));
            reportsMenu.add(getPreviewMenuItem());
            reportsMenu.add(getStatisticsReportMenuItem());
            reportsMenu.add(getSummaryReportMenuItem());
        }
        return reportsMenu;
    }

    /**
     * This method initializes resetSettingsMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getResetSettingsMenuItem() {
        if (resetSettingsMenuItem == null) {
            resetSettingsMenuItem = new JMenuItem();
            resetSettingsMenuItem.addActionListener(actionListener);
            resetSettingsMenuItem.setActionCommand(ActionConstants.RESET_SETTINGS_ACTION);
            resetSettingsMenuItem.setText(ResourceUtil.getString("menu.settings.resetsettings"));
        }

        return resetSettingsMenuItem;
    }

    /**
     * This method initializes sansSerifRadioButtonMenuItem
     *
     * @return javax.swing.JRadioButtonMenuItem
     */
    public JRadioButtonMenuItem getSansSerifRadioButtonMenuItem() {
        if (sansSerifRadioButtonMenuItem == null) {
            sansSerifRadioButtonMenuItem = new JRadioButtonMenuItem();
            sansSerifRadioButtonMenuItem.setText(ResourceUtil.getString("menu.settings.sanserif"));
            sansSerifRadioButtonMenuItem.setFont(new Font(GuiConstants.FONT_FAMILY_SANS_SERIF, Font.BOLD, 12));
            sansSerifRadioButtonMenuItem.addActionListener(actionListener);
            sansSerifRadioButtonMenuItem.setActionCommand(ActionConstants.SANS_SERIF_ACTION);

            if (UserPreferences.getUserFontStyle().equals(GuiConstants.FONT_FAMILY_SANS_SERIF)) {
                sansSerifRadioButtonMenuItem.setSelected(true);
            }
        }
        return sansSerifRadioButtonMenuItem;
    }

    /**
     * This method initializes saveAsMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getSaveAsMenuItem() {
        if (saveAsMenuItem == null) {
            saveAsMenuItem = new JMenuItem();
            saveAsMenuItem.addActionListener(actionListener);
            saveAsMenuItem.setActionCommand(ActionConstants.SAVE_FILE_AS_ACTION);
            saveAsMenuItem.setIcon(ResourceUtil.saveFileAsIcon);
            saveAsMenuItem.setText(ResourceUtil.getString("menu.file.saveas"));
            saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.saveas.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + InputEvent.SHIFT_MASK, false));
        }

        return saveAsMenuItem;
    }

    /**
     * This method initializes saveFileMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getSaveFileMenuItem() {
        if (saveFileMenuItem == null) {
            saveFileMenuItem = new JMenuItem();
            saveFileMenuItem.addActionListener(actionListener);
            saveFileMenuItem.setActionCommand(ActionConstants.SAVE_FILE_ACTION);
            saveFileMenuItem.setIcon(ResourceUtil.saveFileIcon);
            saveFileMenuItem.setText(ResourceUtil.getString("menu.file.save"));
            saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.save.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return saveFileMenuItem;
    }

    /**
     * This method initializes serifRadioButtonMenuItem
     *
     * @return javax.swing.JRadioButtonMenuItem
     */
    public JRadioButtonMenuItem getSerifRadioButtonMenuItem() {
        if (serifRadioButtonMenuItem == null) {
            serifRadioButtonMenuItem = new JRadioButtonMenuItem();
            serifRadioButtonMenuItem.setText(ResourceUtil.getString("menu.settings.serif"));
            serifRadioButtonMenuItem.setFont(new Font(GuiConstants.FONT_FAMILY_SERIF, Font.BOLD, 12));
            serifRadioButtonMenuItem.addActionListener(actionListener);
            serifRadioButtonMenuItem.setActionCommand(ActionConstants.SERIF_ACTION);

            if (UserPreferences.getUserFontStyle().equals(GuiConstants.FONT_FAMILY_SERIF)) {
                serifRadioButtonMenuItem.setSelected(true);
            }
        }
        return serifRadioButtonMenuItem;
    }

    /**
     * This method initializes settingsMenu.
     *
     * @return javax.swing.JMenu
     */
    public JMenu getSettingsMenu() {
        if (settingsMenu == null) {
            settingsMenu = new JMenu();
            settingsMenu.setText(ResourceUtil.getString("menu.settings"));
            settingsMenu.add(getOpenLastFileMenuItem());
            settingsMenu.add(getMultiLineGroupDefinitionsMenuItem());
            settingsMenu.add(new JSeparator());
            settingsMenu.add(getMonospacedRadioButtonMenuItem());
            settingsMenu.add(getSansSerifRadioButtonMenuItem());
            settingsMenu.add(getSerifRadioButtonMenuItem());
            settingsMenu.add(new JSeparator());
            settingsMenu.add(getResetSettingsMenuItem());

            ButtonGroup group = new ButtonGroup();
            group.add(getMonospacedRadioButtonMenuItem());
            group.add(getSansSerifRadioButtonMenuItem());
            group.add(getSerifRadioButtonMenuItem());
        }

        return settingsMenu;
    }

    /**
     * This method initializes statisticsMenuItem
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getStatisticsReportMenuItem() {
        if (statisticsMenuItem == null) {
            statisticsMenuItem = new JMenuItem();
            statisticsMenuItem.setText(ResourceUtil.getString("menu.reports.statisticsreport"));
            statisticsMenuItem.addActionListener(actionListener);
            statisticsMenuItem.setActionCommand(ActionConstants.STATISTICS_REPORT_ACTION);
        }
        return statisticsMenuItem;
    }

    /**
     * This method initializes summaryReportMenuItem
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getSummaryReportMenuItem() {
        if (summaryReportMenuItem == null) {
            summaryReportMenuItem = new JMenuItem();
            summaryReportMenuItem.setText(ResourceUtil.getString("menu.reports.summaryreport"));
            summaryReportMenuItem.addActionListener(actionListener);
            summaryReportMenuItem.setActionCommand(ActionConstants.SUMMARY_REPORT_ACTION);
        }
        return summaryReportMenuItem;
    }

    /**
     * This method initializes viewGroupsMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getViewGroupsMenuItem() {
        if (viewGroupsMenuItem == null) {
            viewGroupsMenuItem = new JMenuItem();
            viewGroupsMenuItem.addActionListener(actionListener);
            viewGroupsMenuItem.setActionCommand(ActionConstants.VIEW_GROUPS_ACTION);
            viewGroupsMenuItem.setIcon(ResourceUtil.groupIcon);
            viewGroupsMenuItem.setText(ResourceUtil.getString("menu.view.viewgroups"));
            viewGroupsMenuItem.setAccelerator(KeyStroke.getKeyStroke('2', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return viewGroupsMenuItem;
    }

    /**
     * This method initializes viewMenu.
     *
     * @return javax.swing.JMenu
     */
    public JMenu getViewMenu() {
        if (viewMenu == null) {
            viewMenu = new JMenu();
            viewMenu.setText(ResourceUtil.getString("menu.view"));
            viewMenu.add(getViewUsersMenuItem());
            viewMenu.add(getViewGroupsMenuItem());
            viewMenu.add(getViewRulesMenuItem());
        }

        return viewMenu;
    }

    /**
     * This method initializes viewRulesMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getViewRulesMenuItem() {
        if (viewRulesMenuItem == null) {
            viewRulesMenuItem = new JMenuItem();
            viewRulesMenuItem.addActionListener(actionListener);
            viewRulesMenuItem.setActionCommand(ActionConstants.VIEW_RULES_ACTION);
            viewRulesMenuItem.setIcon(ResourceUtil.listAccessRuleIcon);
            viewRulesMenuItem.setText(ResourceUtil.getString("menu.view.viewrules"));
            viewRulesMenuItem.setAccelerator(KeyStroke.getKeyStroke('3', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return viewRulesMenuItem;
    }

    /**
     * This method initializes viewUsersMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getViewUsersMenuItem() {
        if (viewUsersMenuItem == null) {
            viewUsersMenuItem = new JMenuItem();
            viewUsersMenuItem.addActionListener(actionListener);
            viewUsersMenuItem.setActionCommand(ActionConstants.VIEW_USERS_ACTION);
            viewUsersMenuItem.setIcon(ResourceUtil.userIcon);
            viewUsersMenuItem.setText(ResourceUtil.getString("menu.view.viewusers"));
            viewUsersMenuItem.setAccelerator(KeyStroke.getKeyStroke('1', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        }

        return viewUsersMenuItem;
    }

    /**
     * Refreshes the recent files menu with the current list of recent files.
     * The text displayed is the file name. The tooltip is the absolute path
     * of the file.
     */
    public void refreshRecentFiles(@Nonnull final Stack<String> fileStack) {
        getRecentFilesMenu().removeAll();

        if (fileStack.isEmpty()) {
            return;
        }

        // Add files to menu in reverse order so that latest is at the top
        // of the list of files.
        for (int slot = fileStack.size() - 1; slot >= 0; slot--) {
            String path = fileStack.elementAt(slot);
            int index = path.lastIndexOf(Constants.FILE_SEPARATOR);

            JMenuItem menuItem = new JMenuItem(path.substring(index + 1));

            menuItem.addActionListener(actionListener);
            menuItem.setActionCommand(ActionConstants.OPEN_FILE_ACTION + "_" + slot);
            menuItem.setToolTipText(path);

            getRecentFilesMenu().add(menuItem);
        }
    }
}
