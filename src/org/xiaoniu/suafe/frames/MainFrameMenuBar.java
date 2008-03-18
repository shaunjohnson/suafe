package org.xiaoniu.suafe.frames;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Menu bar components used by MainFrame.
 * 
 * @author Shaun Johnson
 */
public class MainFrameMenuBar extends JMenuBar {

	private static final long serialVersionUID = -70557657703079354L;

	private JMenuItem aboutMenuItem = null;
	
	private ActionListener actionListener = null;  
	
	private JMenu actionMenu = null;  	
	
	private JMenuItem addAccessRuleMenuItem = null;
	
	private JMenuItem addGroupMenuItem = null;
	
	private JMenuItem addProjectAccessRulesMenuItem = null;
	
	private JMenuItem addUserMenuItem = null;
	
	private JMenuItem clearRecentFilesMenuItem = null;

	private JMenuItem exitMenuItem = null;
	
	private JMenu fileMenu = null;  
	
	private JMenu helpMenu = null;
	
	private JMenuItem helpMenuItem = null;
	
	private JMenuItem licenseMenuItem = null;
	
	private JRadioButtonMenuItem monospacedRadioButtonMenuItem = null;
	
	private JMenuItem newFileMenuItem = null;
	
	private JMenuItem openFileMenuItem = null;
	
	private JMenuItem openLastFileMenuItem = null; 
	
	private JMenuItem previewMenuItem = null;
	
	private JMenu recentFilesMenu = null;
	
	private JMenuItem reloadMenuItem = null;
	
	private JMenu reportsMenu = null;
	
	private JMenuItem resetSettingsMenuItem = null;
	
	private JRadioButtonMenuItem sansSerifRadioButtonMenuItem = null;
	
	private JMenuItem saveAsMenuItem = null;
	
	private JMenuItem saveFileMenuItem = null;
	
	private JRadioButtonMenuItem serifRadioButtonMenuItem = null;
	
	private JMenu settingsMenu = null;
	
	private JMenuItem statisticsMenuItem = null;
	
	private JMenuItem summaryReportMenuItem = null;
	
	private JMenuItem viewGroupsMenuItem = null;
	
	private JMenu viewMenu = null;
	
	private JMenuItem viewRulesMenuItem = null;
	
	private JMenuItem viewUsersMenuItem = null;

	public MainFrameMenuBar(ActionListener actionListener) {
		super();
		
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
			aboutMenuItem.setActionCommand(Constants.ABOUT_ACTION);
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
			addAccessRuleMenuItem.setActionCommand(Constants.ADD_ACCESS_RULE_ACTION);
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
			addGroupMenuItem.setActionCommand(Constants.ADD_GROUP_ACTION);
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
			addProjectAccessRulesMenuItem.setActionCommand(Constants.ADD_PROJECT_ACCESS_RULES_ACTION);
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
			addUserMenuItem.setActionCommand(Constants.ADD_USER_ACTION);
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
			clearRecentFilesMenuItem.setActionCommand(Constants.CLEAR_RECENT_FILES_ACTION);
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
			exitMenuItem.setActionCommand(Constants.EXIT_ACTION);
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
			helpMenuItem.addActionListener(actionListener);
			helpMenuItem.setActionCommand(Constants.HELP_ACTION);
			helpMenuItem.setIcon(ResourceUtil.helpIcon);
			helpMenuItem.setText(ResourceUtil.getString("menu.help.help"));
			helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
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
			licenseMenuItem.addActionListener(actionListener);
			licenseMenuItem.setActionCommand(Constants.LICENSE_ACTION);
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
			monospacedRadioButtonMenuItem.setFont(new Font(Constants.FONT_MONOSPACED, Font.BOLD, 12));
			monospacedRadioButtonMenuItem.addActionListener(actionListener);
			monospacedRadioButtonMenuItem.setActionCommand(Constants.MONOSPACED_ACTION);
			
			if (UserPreferences.getUserFontStyle().equals(Constants.FONT_MONOSPACED)) {
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
			newFileMenuItem.setActionCommand(Constants.NEW_FILE_ACTION);
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
			openFileMenuItem.setActionCommand(Constants.OPEN_FILE_ACTION);
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
			openLastFileMenuItem.setActionCommand(Constants.OPEN_LAST_EDITED_FILE_ACTION);
			openLastFileMenuItem.setSelected(UserPreferences.getOpenLastFile());
		}
		
		return openLastFileMenuItem;
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
			previewMenuItem.setActionCommand(Constants.PREVIEW_ACTION);
		}
		return previewMenuItem;
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
			reloadMenuItem.setActionCommand(Constants.RELOAD_ACTION);
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
			resetSettingsMenuItem.setActionCommand(Constants.RESET_SETTINGS_ACTION);
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
			sansSerifRadioButtonMenuItem.setFont(new Font(Constants.FONT_SANS_SERIF, Font.BOLD, 12));
			sansSerifRadioButtonMenuItem.addActionListener(actionListener);
			sansSerifRadioButtonMenuItem.setActionCommand(Constants.SANS_SERIF_ACTION);
			
			if (UserPreferences.getUserFontStyle().equals(Constants.FONT_SANS_SERIF)) {
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
			saveAsMenuItem.setActionCommand(Constants.SAVE_FILE_AS_ACTION);
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
			saveFileMenuItem.setActionCommand(Constants.SAVE_FILE_ACTION);
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
			serifRadioButtonMenuItem.setFont(new Font(Constants.FONT_SERIF, Font.BOLD, 12));
			serifRadioButtonMenuItem.addActionListener(actionListener);
			serifRadioButtonMenuItem.setActionCommand(Constants.SERIF_ACTION);
			
			if (UserPreferences.getUserFontStyle().equals(Constants.FONT_SERIF)) {
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
			statisticsMenuItem.setActionCommand(Constants.STATISTICS_REPORT_ACTION);
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
			summaryReportMenuItem.setActionCommand(Constants.SUMMARY_REPORT_ACTION);
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
			viewGroupsMenuItem.setActionCommand(Constants.VIEW_GROUPS_ACTION);
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
			viewRulesMenuItem.setActionCommand(Constants.VIEW_RULES_ACTION);
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
			viewUsersMenuItem.setActionCommand(Constants.VIEW_USERS_ACTION);
			viewUsersMenuItem.setIcon(ResourceUtil.userIcon);
			viewUsersMenuItem.setText(ResourceUtil.getString("menu.view.viewusers"));
			viewUsersMenuItem.setAccelerator(KeyStroke.getKeyStroke('1', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return viewUsersMenuItem;
	}
}
