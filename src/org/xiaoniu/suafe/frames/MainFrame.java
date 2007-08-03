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
package org.xiaoniu.suafe.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.xiaoniu.suafe.AutofitTableColumns;
import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.FileGenerator;
import org.xiaoniu.suafe.FileOpener;
import org.xiaoniu.suafe.FileParser;
import org.xiaoniu.suafe.FileTransferHandler;
import org.xiaoniu.suafe.Printer;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.PathComparator;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.dialogs.AboutDialog;
import org.xiaoniu.suafe.dialogs.AddAccessRuleDialog;
import org.xiaoniu.suafe.dialogs.AddGroupDialog;
import org.xiaoniu.suafe.dialogs.AddRemoveMembersDialog;
import org.xiaoniu.suafe.dialogs.AddUserDialog;
import org.xiaoniu.suafe.dialogs.ChangeMembershipDialog;
import org.xiaoniu.suafe.dialogs.CloneGroupDialog;
import org.xiaoniu.suafe.dialogs.CloneUserDialog;
import org.xiaoniu.suafe.dialogs.DialogUtil;
import org.xiaoniu.suafe.dialogs.EditAccessRuleDialog;
import org.xiaoniu.suafe.dialogs.EditGroupDialog;
import org.xiaoniu.suafe.dialogs.EditPathDialog;
import org.xiaoniu.suafe.dialogs.EditRepositoryDialog;
import org.xiaoniu.suafe.dialogs.EditUserDialog;
import org.xiaoniu.suafe.dialogs.LicenseDialog;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.models.NonEditableTableModel;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.renderers.MyTableCellRenderer;
import org.xiaoniu.suafe.renderers.MyTreeCellRenderer;
import org.xiaoniu.suafe.reports.GenericReport;
import org.xiaoniu.suafe.reports.StatisticsReport;
import org.xiaoniu.suafe.reports.SummaryReport;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Main Suafe application window.
 * 
 * @author Shaun Johnson
 */
public class MainFrame extends BaseFrame implements ActionListener, KeyListener,
		ListSelectionListener, MouseListener, TreeSelectionListener, 
		WindowListener, FileOpener {

	private static final long serialVersionUID = -4378074679449146788L;
	
	private JPanel contentPane = null;  

	private JTabbedPane mainTabbedPane = null;  

	private JSplitPane usersSplitPane = null;

	private JList userList = null;

	private JSplitPane groupsSplitPane = null;

	private JPanel userDetailsPanel = null;  

	private JMenuBar menuBar = null;  

	private JMenu fileMenu = null;  
	
	private JMenu recentFilesMenu = null;

	private JMenuItem newFileMenuItem = null;

	private JMenuItem openFileMenuItem = null;  

	private JMenuItem saveFileMenuItem = null;

	private JMenuItem saveAsMenuItem = null;

	private JMenu helpMenu = null;

	private JMenuItem helpMenuItem = null;

	private JMenuItem aboutMenuItem = null;

	private JMenuItem exitMenuItem = null;

	private JList groupList = null;  

	private JMenuItem licenseMenuItem = null;

	private JPanel groupDetailsPanel = null;
	
	private Object[] userAccessRulesColumnNames;

	private Object[] groupAccessRulesColumnNames;
	
	private Object[] repositoryAccessRulesColumnNames;
	
	private Object[] pathAccessRulesColumnNames;
	
	private Object[] serverAccessRulesColumnNames;
	
	private JMenu actionMenu = null;  

	private JMenuItem addUserMenuItem = null;

	private JMenuItem addGroupMenuItem = null;

	private JMenuItem addAccessRuleMenuItem = null;  

	private JScrollPane userListScrollPane = null;

	private JScrollPane groupListScrollPane = null;

	private JToolBar actionToolBar = null;  

	private JButton addUserToolbarButton = null;

	private JButton newFileToolbarButton = null;
	
	private JButton openFileToolbarButton = null;
	
	private JButton saveFileToolbarButton = null;
	
	private JButton saveFileAsToolbarButton = null;
	
	private JButton addGroupToolbarButton = null;  

	private JButton previewToolbarButton = null;

	private JButton addAccessRuleToolbarButton = null;  

	private JPanel userActionsPanel = null;

	private JButton editUserButton = null;  

	private JButton addUserButton = null;  
	
	private JButton cloneUserButton = null;

	private JButton deleteUserButton = null;

	private JButton changeMembershipButton = null;

	private JPanel groupActionsPanel = null;  

	private JButton addGroupButton = null;  

	private JButton cloneGroupButton = null;
	
	private JButton editGroupButton = null;  

	private JButton deleteGroupButton = null;

	private JButton addRemoveMembersButton = null;

	private JPopupMenu usersPopupMenu = null; 

	private JMenuItem addUserPopupMenuItem = null;  

	private JMenuItem editUserPopupMenuItem = null;

	private JMenuItem deleteUserPopupMenuItem = null;  

	private JMenuItem changeMembershipPopupMenuItem = null;

	private JPopupMenu groupsPopupMenu = null;  

	private JMenuItem addGroupPopupMenuItem = null;

	private JMenuItem editGroupPopupMenuItem = null;

	private JMenuItem deleteGroupPopupMenuItem = null;

	private JMenuItem addRemoveMembersPopupMenuItem = null;  

	private JButton addAccessRuleButton = null;

	private JButton editAccessRuleButton = null;

	private JButton deleteAccessRuleButton = null;  

	private JSplitPane userDetailsSplitPanel = null;  

	private JList userGroupList = null;  

	private JTable userAccessRulesTable = null;

	private JScrollPane userAccessRulesScrollPane = null;  

	private JScrollPane userGroupListScrollPane = null;

	private JPanel userAccessRulesFormatPanel = null;  

	private JSplitPane groupDetailsSplitPanel = null;  

	private JTable groupAccessRulesTable = null;  

	private JScrollPane groupAccessRulesScrollPane = null;  

	private JSplitPane accessRulesSplitPane = null;

	private JScrollPane accessRulesTreeScrollPane = null;

	private JTree accessRulesTree = null;

	private TreeModel accessRuleTreeModel = null;

	private JPanel accessRulesPanel = null;  

	private JPanel accessRuleActionsPanel = null;

	private JTable accessRulesTable = null;

	private JPanel toolbarPanel = null;  

	private JMenuItem printMenuItem = null;

	private JScrollPane accessRulesScrollPane = null;
	
	private JPanel accessRulesFormatPanel = null;  
	
	private JPanel groupAccessRulesPanel = null;  
	
	private JPanel groupMembersPanel = null;  
	
	private JScrollPane groupMemberListScrollPane = null;
	
	private JList groupMemberList = null;
	
	private JPanel userGroupListPanel = null;
	
	private JPanel accessRulesTreePanel = null;
	
	private JPanel accessRulesTreeActionsPanel = null;
	
	private JButton editTreeItemButton = null;
	
	private JButton deleteTreeItemButton = null;
	
	private JPanel userGroupsActionPanel = null;
	
	private JPanel groupMemberListActionsPanel = null;
	
	private JPanel userListPanel = null;
	
	private JPanel groupListPanel = null;
	
	private JMenuItem cloneUserPopupMenuItem = null;
	
	private JMenuItem cloneGroupPopupMenuItem = null;
	
	private JMenu settingsMenu = null;
	
	private JCheckBoxMenuItem openLastFileMenuItem = null;
	
	private Stack<String> fileStack = null;

	private JMenu reportsMenu = null;

	private JMenuItem previewMenuItem = null;

	private JMenuItem summaryReportMenuItem = null;
	
	private FileTransferHandler fileTransferHandler = null;
	
	private JPanel statusPanel = null;

	private JLabel statusLabel = null;
	
	private JRadioButtonMenuItem monospacedRadioButtonMenuItem = null;

	private JRadioButtonMenuItem sansSerifRadioButtonMenuItem = null;

	private JRadioButtonMenuItem serifRadioButtonMenuItem = null;

	private JMenuItem clearRecentFilesMenuItem = null;

	private JMenuItem statisticsMenuItem = null;
	
	private JMenuItem resetSettingsMenuItem = null;
	
	private JMenu viewMenu = null;
	
	private JMenuItem viewUsersMenuItem = null;
	
	private JMenuItem viewGroupsMenuItem = null;
	
	private JMenuItem viewRulesMenuItem = null;
	
	/**
	 * Default constructor
	 */
	public MainFrame() {
		super();
		
		initialize();
	}

	/**
	 * This method initializes this frame.
	 */
	private void initialize() {
		fileStack = UserPreferences.getRecentFiles();
		
		this.fileTransferHandler = new FileTransferHandler(this);
		this.addKeyListener(this);
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(ResourceUtil.serverImage);
		this.setJMenuBar(getJJMenuBar());		
		this.setContentPane(getJContentPane());
		addTransferHandler(this);
		
		loadUserPreferences();
		
		getGroupsPopupMenu();
		getUsersPopupMenu();
		updateTitle();
		changeFont(null);
		
		// Load last opened file if setting is enabled
		if (UserPreferences.getOpenLastFile() && fileStack.size() > 0) {
			fileOpen(fileStack.size() - 1);
		}
		
		refreshTabNames();
	}
	
	private void loadUserPreferences() {
		this.setVisible(false);
		
		changeFont(UserPreferences.getUserFontStyle());
		
		this.setSize(UserPreferences.getWindowSize());
		
		Point location = UserPreferences.getWindowLocation();
		
		if (location == null) {
			this.center();
		}
		else {
			this.setLocation(location);
		}
		
		this.setExtendedState(UserPreferences.getWindowState());
		
		getGroupDetailsSplitPanel().setDividerLocation(UserPreferences.getGroupDetailsDividerLocation());
		getGroupsSplitPane().setDividerLocation(UserPreferences.getGroupsPaneDividerLocation());
		getUserDetailsSplitPanel().setDividerLocation(UserPreferences.getUserDetailsDividerLocation());
		getUsersSplitPane().setDividerLocation(UserPreferences.getUsersPaneDividerLocation());
		getAccessRulesSplitPane().setDividerLocation(UserPreferences.getRulesPaneDividerLocation());
		
		getOpenLastFileMenuItem().setSelected(UserPreferences.getOpenLastFile());
		
		this.setVisible(true);
	}
	
	private void saveUserPreferences() {
		UserPreferences.setWindowState(getExtendedState());
		
		this.setVisible(false);
		
		this.setExtendedState(JFrame.NORMAL);
		UserPreferences.setWindowSize(getSize());
		
		UserPreferences.setWindowLocation(getLocation());
		UserPreferences.setGroupDetailsDividerLocation(
				getGroupDetailsSplitPanel().getDividerLocation());
		UserPreferences.setGroupsPaneDividerLocation(
				getGroupsSplitPane().getDividerLocation());
		
		UserPreferences.setUserDetailsDividerLocation(
				getUserDetailsSplitPanel().getDividerLocation());
		UserPreferences.setUsersPaneDividerLocation(
				getUsersSplitPane().getDividerLocation());
	
		UserPreferences.setRulesPaneDividerLocation(
				getAccessRulesSplitPane().getDividerLocation());
	}

	private void addTransferHandler(Container container) {
		if (container instanceof JComponent) {
			((JComponent)container).setTransferHandler(fileTransferHandler);
		}
			
		if (container.getComponentCount() > 0) {
			for (Component child : container.getComponents()) {
				if (child instanceof Container) {
					addTransferHandler((Container)child);
				}
			}
		}
	}
	
	private void changeFont(String newFontStyle) {
		if (newFontStyle != null) {
			UserPreferences.setUserFontStyle(newFontStyle);
		}
		
		getAccessRulesTable().setFont(UserPreferences.getUserFont());
		getAccessRulesTree().setFont(UserPreferences.getUserFont());
		getGroupAccessRulesTable().setFont(UserPreferences.getUserFont());
		getGroupList().setFont(UserPreferences.getUserFont());
		getGroupMemberList().setFont(UserPreferences.getUserFont());
		getUserAccessRulesTable().setFont(UserPreferences.getUserFont());
		getUserGroupList().setFont(UserPreferences.getUserFont());
		getUserList().setFont(UserPreferences.getUserFont());
	}
	
	/**
	 * This method initializes contentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setLayout(new BorderLayout());
			contentPane.add(getToolbarPanel(), BorderLayout.NORTH);
			contentPane.add(getMainTabbedPane(), BorderLayout.CENTER);
			contentPane.add(getStatusPanel(), BorderLayout.SOUTH);
		}
		
		return contentPane;
	}

	/**
	 * This method initializes mainTabbedPane.
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();
			mainTabbedPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			mainTabbedPane.addTab(ResourceUtil
					.getString("mainframe.tabs.users"), 
					ResourceUtil.fullSizeUserIcon,
					getUsersSplitPane());
			mainTabbedPane.addTab(ResourceUtil
					.getString("mainframe.tabs.groups"), 
					ResourceUtil.fullSizeGroupIcon,
					getGroupsSplitPane());
			mainTabbedPane.addTab(ResourceUtil
					.getString("mainframe.tabs.accessrules"), 
					ResourceUtil.fullSizeAccessRuleIcon,
					getAccessRulesSplitPane());
			
			mainTabbedPane.setFont(new Font(null, Font.PLAIN, 12));
		}
		
		return mainTabbedPane;
	}

	/**
	 * This method initializes usersSplitPane.
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getUsersSplitPane() {
		if (usersSplitPane == null) {
			usersSplitPane = new JSplitPane();
			usersSplitPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			usersSplitPane.setLeftComponent(getUserListPanel());
			usersSplitPane.setRightComponent(getUserDetailsPanel());
			usersSplitPane.setDividerLocation(UserPreferences.getUsersPaneDividerLocation());
		}
		
		return usersSplitPane;
	}

	/**
	 * This method initializes userList.
	 * 
	 * @return javax.swing.JList
	 */
	private JList getUserList() {
		if (userList == null) {
			userList = new JList();
			userList.addListSelectionListener(this);
			userList.addMouseListener(this);
			userList.setCellRenderer(new MyListCellRenderer());
			userList.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		
		return userList;
	}

	/**
	 * This method initializes groupsSplitPane.
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getGroupsSplitPane() {
		if (groupsSplitPane == null) {
			groupsSplitPane = new JSplitPane();
			groupsSplitPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			groupsSplitPane.setLeftComponent(getGroupListPanel());
			groupsSplitPane.setRightComponent(getGroupDetailsPanel());
			groupsSplitPane.setDividerLocation(UserPreferences.getGroupsPaneDividerLocation());
		}
		
		return groupsSplitPane;
	}

	/**
	 * This method initializes userDetailsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getUserDetailsPanel() {
		if (userDetailsPanel == null) {
			userDetailsPanel = new JPanel();
			userDetailsPanel.setLayout(new BorderLayout());
			userDetailsPanel.add(getUserDetailsSplitPanel(), BorderLayout.CENTER);
		}
		
		return userDetailsPanel;
	}

	/**
	 * This method initializes menuBar.
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getFileMenu());
			menuBar.add(getActionMenu());
			menuBar.add(getViewMenu());
			menuBar.add(getReportsMenu());
			menuBar.add(getSettingsMenu());
			menuBar.add(getHelpMenu());
		}
		
		return menuBar;
	}
	
	/** 
	 * This method initializes openLastFileMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getOpenLastFileMenuItem() {
		if (openLastFileMenuItem == null) {
			openLastFileMenuItem = new JCheckBoxMenuItem();
			openLastFileMenuItem.setText(ResourceUtil.getString("menu.settings.openlastfile"));
			openLastFileMenuItem.addActionListener(this);
			openLastFileMenuItem.setActionCommand(Constants.OPEN_LAST_EDITED_FILE_ACTION);
			openLastFileMenuItem.setSelected(UserPreferences.getOpenLastFile());
		}
		
		return openLastFileMenuItem;
	}
	
	/**
	 * Adds the absolute path of a file to the recent files list and persists
	 * it to Preferences.
	 * 
	 * @param absolutePath Absolute path of newly opened file.
	 */
	private void addToRecentFiles(String absolutePath) {
		fileStack = UserPreferences.getRecentFiles();
		
		// If file already appears in list remove it first so that it can
		// be added to the top of the stack.
		if (fileStack.search(absolutePath) != -1) {
			fileStack.remove(absolutePath);
		}
		
		fileStack.push(absolutePath);
		
		// Maintain maximum number of recent files
		if (fileStack.size() > UserPreferences.MAXIMUM_RECENT_FILES) {
			fileStack.remove(0);
		}
		
		UserPreferences.setRecentFiles(fileStack);
		refreshRecentFiles();		
	}

	/**
	 * This method initializes fileMenu.
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText(ResourceUtil.getString("menu.file"));
			fileMenu.add(getNewFileMenuItem());
			fileMenu.add(getOpenFileMenuItem());
			fileMenu.add(getSaveFileMenuItem());
			fileMenu.add(getSaveAsMenuItem());
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
	 * This method initializes recentFilesMenu.
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getRecentFilesMenu() {
		if (recentFilesMenu == null) {
			recentFilesMenu = new JMenu(ResourceUtil.getString("menu.file.recentfiles"));
			refreshRecentFiles();
		}
		
		return recentFilesMenu;
	}
	
	/**
	 * Refreshes the recent files menu with the current list of recent files.
	 * The text displayed is the file name. The tooltip is the absolute path
	 * of the file.
	 */
	private void refreshRecentFiles() {
		recentFilesMenu.removeAll();
		
		if (fileStack.size() == 0) {
			return;
		}
		
		// Add files to menu in reverse order so that latest is at the top
		// of the list of files.
		for(int slot = fileStack.size() - 1; slot >= 0; slot--) {
			String path = fileStack.elementAt(slot);
			int index = path.lastIndexOf(Constants.FILE_SEPARATOR);
			
			JMenuItem menuItem = new JMenuItem(path.substring(index + 1));
			
			menuItem.addActionListener(this);
			menuItem.setActionCommand(Constants.OPEN_FILE_ACTION + "_" + slot);
			menuItem.setToolTipText(path);
			
			recentFilesMenu.add(menuItem);
		}
	}

	private void clearRecentFiles() {
		UserPreferences.clearRecentFiles();
		
		recentFilesMenu.removeAll();
	}
	
	private void resetSettings() {
		UserPreferences.resetSettings();
		loadUserPreferences();
	}
	
	/**
	 * This method initializes newFileMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewFileMenuItem() {
		if (newFileMenuItem == null) {
			newFileMenuItem = new JMenuItem();
			newFileMenuItem.addActionListener(this);
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
	private JMenuItem getOpenFileMenuItem() {
		if (openFileMenuItem == null) {
			openFileMenuItem = new JMenuItem();
			openFileMenuItem.addActionListener(this);
			openFileMenuItem.setActionCommand(Constants.OPEN_FILE_ACTION);
			openFileMenuItem.setIcon(ResourceUtil.openFileIcon);
			openFileMenuItem.setText(ResourceUtil.getString("menu.file.open"));						
			openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.open.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return openFileMenuItem;
	}

	/**
	 * This method initializes saveFileMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveFileMenuItem() {
		if (saveFileMenuItem == null) {
			saveFileMenuItem = new JMenuItem();
			saveFileMenuItem.addActionListener(this);
			saveFileMenuItem.setActionCommand(Constants.SAVE_FILE_ACTION);
			saveFileMenuItem.setIcon(ResourceUtil.saveFileIcon);
			saveFileMenuItem.setText(ResourceUtil.getString("menu.file.save"));			
			saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.save.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return saveFileMenuItem;
	}

	/**
	 * This method initializes saveAsMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.addActionListener(this);
			saveAsMenuItem.setActionCommand(Constants.SAVE_FILE_AS_ACTION);
			saveAsMenuItem.setIcon(ResourceUtil.saveFileAsIcon);
			saveAsMenuItem.setText(ResourceUtil.getString("menu.file.saveas"));		
			saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.saveas.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + InputEvent.SHIFT_MASK, false));
		}
		
		return saveAsMenuItem;
	}

	/**
	 * This method initializes helpMenu.
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
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
	private JMenuItem getHelpMenuItem() {
		if (helpMenuItem == null) {
			helpMenuItem = new JMenuItem();
			helpMenuItem.addActionListener(this);
			helpMenuItem.setActionCommand(Constants.HELP_ACTION);
			helpMenuItem.setIcon(ResourceUtil.helpIcon);
			helpMenuItem.setText(ResourceUtil.getString("menu.help.help"));
			helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}
		
		return helpMenuItem;
	}

	/**
	 * This method initializes aboutMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.addActionListener(this);
			aboutMenuItem.setActionCommand(Constants.ABOUT_ACTION);
			aboutMenuItem.setIcon(ResourceUtil.aboutIcon);
			aboutMenuItem.setText(ResourceUtil.getString("menu.help.about"));
		}
		
		return aboutMenuItem;
	}

	/**
	 * This method initializes exitMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.addActionListener(this);
			exitMenuItem.setActionCommand(Constants.EXIT_ACTION);
			exitMenuItem.setText(ResourceUtil.getString("menu.file.exit"));
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.exit.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return exitMenuItem;
	}

	/**
	 * This method initializes actionToolBar.
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getActionToolBar() {
		if (actionToolBar == null) {
			actionToolBar = new JToolBar();
			actionToolBar.setFloatable(false);
			actionToolBar.add(getNewFileToolbarButton());
			actionToolBar.add(getOpenFileToolbarButton());
			actionToolBar.add(getSaveFileToolbarButton());
			actionToolBar.add(getSaveFileAsToolbarButton());			
			actionToolBar.add(getAddUserToolbarButton());
			actionToolBar.add(getAddGroupToolbarButton());
			actionToolBar.add(getAddAccessRuleToolbarButton());
			actionToolBar.add(getPreviewToolbarButton());
		}
		
		return actionToolBar;
	}

	/**
	 * This method initializes newFileToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNewFileToolbarButton() {
		if (newFileToolbarButton == null) {
			newFileToolbarButton = new JButton();
			newFileToolbarButton.addActionListener(this);
			newFileToolbarButton.setActionCommand(Constants.NEW_FILE_ACTION);
			newFileToolbarButton.setIcon(ResourceUtil.newFileIcon);
			newFileToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.new.tooltip"));			
		}
		
		return newFileToolbarButton;
	}
	
	/**
	 * This method initializes openFileToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOpenFileToolbarButton() {
		if (openFileToolbarButton == null) {
			openFileToolbarButton = new JButton();
			openFileToolbarButton.addActionListener(this);
			openFileToolbarButton.setActionCommand(Constants.OPEN_FILE_ACTION);
			openFileToolbarButton.setIcon(ResourceUtil.openFileIcon);
			openFileToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.open.tooltip"));
		}
		
		return openFileToolbarButton;
	}
	
	/**
	 * This method initializes saveFileToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSaveFileToolbarButton() {
		if (saveFileToolbarButton == null) {
			saveFileToolbarButton = new JButton();
			saveFileToolbarButton.addActionListener(this);
			saveFileToolbarButton.setActionCommand(Constants.SAVE_FILE_ACTION);
			saveFileToolbarButton.setIcon(ResourceUtil.saveFileIcon);
			saveFileToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.save.tooltip"));
		}
		return saveFileToolbarButton;
	}
	
	/**
	 * This method initializes saveFileAsToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSaveFileAsToolbarButton() {
		if (saveFileAsToolbarButton == null) {
			saveFileAsToolbarButton = new JButton();
			saveFileAsToolbarButton.addActionListener(this);
			saveFileAsToolbarButton.setActionCommand(Constants.SAVE_FILE_AS_ACTION);
			saveFileAsToolbarButton.setIcon(ResourceUtil.saveFileAsIcon);
			saveFileAsToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.saveas.tooltip"));
		}
		return saveFileAsToolbarButton;
	}
	
	/**
	 * This method initializes addUserToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddUserToolbarButton() {
		if (addUserToolbarButton == null) {
			addUserToolbarButton = new JButton();
			addUserToolbarButton.addActionListener(this);
			addUserToolbarButton.setActionCommand(Constants.ADD_USER_ACTION);
			addUserToolbarButton.setIcon(ResourceUtil.addUserIcon);
			addUserToolbarButton.setText(ResourceUtil.getString("mainframe.button.adduser"));
			addUserToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));			
		}
		
		return addUserToolbarButton;
	}

	/**
	 * This method initializes addGroupToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddGroupToolbarButton() {
		if (addGroupToolbarButton == null) {
			addGroupToolbarButton = new JButton();
			addGroupToolbarButton.addActionListener(this);
			addGroupToolbarButton.setActionCommand(Constants.ADD_GROUP_ACTION);
			addGroupToolbarButton.setIcon(ResourceUtil.addGroupIcon);
			addGroupToolbarButton.setText(ResourceUtil.getString("mainframe.button.addgroup"));
			addGroupToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));
		}
		
		return addGroupToolbarButton;
	}

	/**
	 * This method initializes previewToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPreviewToolbarButton() {
		if (previewToolbarButton == null) {
			previewToolbarButton = new JButton();
			previewToolbarButton.addActionListener(this);
			previewToolbarButton.setActionCommand(Constants.PREVIEW_ACTION);
			previewToolbarButton.setIcon(ResourceUtil.previewIcon);
			previewToolbarButton.setText(ResourceUtil.getString("mainframe.button.preview"));
			previewToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.preview.tooltip"));
		}
		
		return previewToolbarButton;
	}

	/**
	 * This method initializes addAccessRuleToolbarButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddAccessRuleToolbarButton() {
		if (addAccessRuleToolbarButton == null) {
			addAccessRuleToolbarButton = new JButton();
			addAccessRuleToolbarButton.addActionListener(this);
			addAccessRuleToolbarButton.setActionCommand(Constants.ADD_ACCESS_RULE_ACTION);
			addAccessRuleToolbarButton.setIcon(ResourceUtil.addAccessRuleIcon);
			addAccessRuleToolbarButton.setText(ResourceUtil.getString("mainframe.button.addaccessrule"));
			addAccessRuleToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.addaccessrule.tooltip"));			
		}
		
		return addAccessRuleToolbarButton;
	}

	/**
	 * New file action handler.
	 */
	private void fileNew() {
		checkForUnsavedChanges();
		
		Document.initialize();
		updateTitle();
		refreshUserList(null);
		refreshUserDetails();
		refreshGroupList(null);
		refreshGroupDetails();
		refreshAccessRuleTree(null);
		getEditTreeItemButton().setEnabled(false);
		getDeleteTreeItemButton().setEnabled(false);
		getMainTabbedPane().setSelectedComponent(getUsersSplitPane());
	}

	/**
	 * File open action handler.
	 */
	private void fileOpen() {
		final JFileChooser fcOpen = new JFileChooser();
		
		checkForUnsavedChanges();

		fcOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);

		File directory = (Document.getFile() == null) ? null : Document.getFile().getParentFile();

		fcOpen.setSelectedFile(directory);

		int returnVal = fcOpen.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fcOpen.getSelectedFile();
				FileParser.parse(file);
				getUserList().setListData(Document.getUserObjects());
				getGroupList().setListData(Document.getGroupObjects());

				Document.setFile(file);

				refreshAccessRuleTree(null);

				Document.resetUnsavedChangesFlag();
				updateTitle();
				
				addToRecentFiles(file.getAbsolutePath());
				refreshTabNames();
			} 
			catch (Exception e) {
				displayError(e.getMessage());
			}
		}
	}
	
	/**
	 * File open action handler.
	 */
	private void fileOpen(int index) {		
		fileOpen(new File(fileStack.elementAt(index)));
	}
	
	/**
	 * File open action handler.
	 */
	public void fileOpen(File file) {
		checkForUnsavedChanges();
		
		try {
			FileParser.parse(file);
			getUserList().setListData(Document.getUserObjects());
			getGroupList().setListData(Document.getGroupObjects());

			Document.setFile(file);

			refreshAccessRuleTree(null);

			Document.resetUnsavedChangesFlag();
			updateTitle();
			
			addToRecentFiles(file.getAbsolutePath());
			refreshTabNames();
		} 
		catch (Exception e) {
			displayError(e.getMessage());
		}
	}

	/**
	 * Generic error message dialog.
	 * 
	 * @param message Error message to display.
	 */
	private void displayError(String message) {
		JOptionPane.showMessageDialog(this, 
				message, 
				ResourceUtil.getString("application.error"), 
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Generic warning message dialog.
	 * 
	 * @param message Warning message to display.
	 */
	private void displayWarning(String message) {
		JOptionPane.showMessageDialog(this, 
				message, 
				ResourceUtil.getString("application.warning"), 
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * File save action handler.
	 */
	private void fileSave() {		
		if(!checkForUnsaveableData()) {
			return;
		}
		
		if (Document.getFile() == null) {
			final JFileChooser fcSave = new JFileChooser();

			fcSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fcSave.setDialogType(JFileChooser.SAVE_DIALOG);

			int returnVal = fcSave.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					File file = fcSave.getSelectedFile();
					
					FileGenerator.generate(file);
					
					Document.setFile(file);

					Document.resetUnsavedChangesFlag();
					updateTitle();
					addToRecentFiles(Document.getFile().getAbsolutePath());
				} 
				catch (Exception e) {
					displayError(e.getMessage());
				}
			}
		} 
		else {
			try {
				FileGenerator.generate(Document.getFile());
				
				Document.resetUnsavedChangesFlag();
				updateTitle();
				addToRecentFiles(Document.getFile().getAbsolutePath());
			} 
			catch (Exception e) {
				displayError(e.getMessage());
			}
		}
	}

	/** 
	 * File save as action handler.
	 */
	private void fileSaveAs() {
		final JFileChooser fcSaveAs = new JFileChooser();
		
		if(!checkForUnsaveableData()) {
			return;
		}

		fcSaveAs.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fcSaveAs.setDialogType(JFileChooser.SAVE_DIALOG);
		fcSaveAs.setDialogTitle(ResourceUtil.getString("saveas.title"));

		int returnVal = fcSaveAs.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fcSaveAs.getSelectedFile();

				FileGenerator.generate(file);

				Document.setFile(file);

				Document.resetUnsavedChangesFlag();
				updateTitle();
				addToRecentFiles(Document.getFile().getAbsolutePath());
			} 
			catch (Exception e) {
				displayError(e.getMessage());
			}
		}
	}
	
	/**
	 * Toggles user actions state.
	 * 
	 * @param enabled If true user actions are enabled, otherwise disabled.
	 * @param changeMembershipEnabled If true change membership button enabled, otherwise disabled.
	 */
	private void toggleUserActions(boolean enabled, boolean changeMembershipEnabled) {
		getCloneUserButton().setEnabled(enabled);
		getEditUserButton().setEnabled(enabled);
		getDeleteUserButton().setEnabled(enabled);
		getChangeMembershipButton().setEnabled(changeMembershipEnabled);
		
		getEditUserPopupMenuItem().setEnabled(enabled);
		getDeleteUserPopupMenuItem().setEnabled(enabled);
		getChangeMembershipPopupMenuItem().setEnabled(changeMembershipEnabled);
		getCloneUserPopupMenuItem().setEnabled(enabled);
	}
	
	/**
	 * Toggles group actions state.
	 * 
	 * @param enabled If true group actions are enabled, otherwise disabled.
	 */
	private void toggleGroupActions(boolean enabled) {
		getCloneGroupButton().setEnabled(enabled);
		getEditGroupButton().setEnabled(enabled);
		getDeleteGroupButton().setEnabled(enabled);
		getAddRemoveMembersButton().setEnabled(enabled);
		
		getEditGroupPopupMenuItem().setEnabled(enabled);
		getDeleteGroupPopupMenuItem().setEnabled(enabled);
		getAddRemoveMembersPopupMenuItem().setEnabled(enabled);
		getCloneGroupPopupMenuItem().setEnabled(enabled);
	}

	/**
	 * Toggles access rules actions state.
	 * 
	 * @param enabled If true access rules actions enabled, otherwise disabled.
	 */
	private void toggleAccessRulesActions(boolean enabled) {
		getEditAccessRuleButton().setEnabled(enabled);
		getDeleteAccessRuleButton().setEnabled(enabled);
	}
	
	/**
	 * Refreshes user list with current users. List of selected users is
	 * used to reselect the users after refresh.
	 * 
	 * @param selectedUser User currently selected.
	 */
	private void refreshUserList(User selectedUser) {
		getUserList().setListData(Document.getUserObjects());
		
		boolean enabled = getUserList().isSelectionEmpty() == false;
		toggleUserActions(enabled, (selectedUser == null) ? enabled : enabled && !selectedUser.isAllUsers());
		
		if (selectedUser != null) {
			getUserList().setSelectedValue(selectedUser, true);
		}
		
		refreshUserDetails();
	}

	/**
	 * Refreshes group list with current groups. List of selected groups is
	 * used to reselect the groups after refresh.
	 * 
	 * @param selectedGroup Group currently selected.
	 */
	private void refreshGroupList(Group selectedGroup) {
		getGroupList().setListData(Document.getGroupObjects());
		toggleGroupActions(getGroupList().isSelectionEmpty() == false);
		
		if (selectedGroup != null) {
			getGroupList().setSelectedValue(selectedGroup, true);
		}
		
		refreshGroupDetails();
	}

	/**
	 * Add User action handler. Displays AddUser dialog.
	 */
	private void addUser() {
		getMainTabbedPane().setSelectedComponent(getUsersSplitPane());

		Message message = new Message();
		JDialog dialog = new AddUserDialog(message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);
		
		if (message.getState() == Message.SUCCESS) {
			refreshUserList((User)message.getUserObject());
		}
		
		updateTitle();
	}
	
	/**
	 * Add Group action handler. Displays AddGroup dialog.
	 */
	private void addGroup() {
		getMainTabbedPane().setSelectedComponent(getGroupsSplitPane());

		Message message = new Message();
		JDialog dialog = new AddGroupDialog(message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		if (message.getState() == Message.SUCCESS) {
			refreshGroupList((Group)message.getUserObject());
		}
		
		updateTitle();
	}

	/**
	 * Add Access Rule action handler. Displays AddAccessRule dialog.
	 */
	private void addAccessRule() {
		getMainTabbedPane().setSelectedComponent(getAccessRulesSplitPane());

		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
		Object userObject = (node == null) ? null : node.getUserObject();
		Message message = new Message();
		JDialog dialog = new AddAccessRuleDialog(userObject, message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		refreshUserList(null);
		
		if (message.getState() == Message. SUCCESS) {
			AccessRule rule = (AccessRule)message.getUserObject();
			refreshAccessRuleTree(rule.getPath());
		}
		else {
			refreshAccessRuleTree(userObject);
		}
		
		updateTitle();
	}

	/**
	 * Edit access rule handler.
	 */
	private void editAccessRule() {
		if (getAccessRulesTable().getSelectedRowCount() < 1) {
			displayWarning(ResourceUtil.getString("mainframe.warning.noaccessruleselected"));
		}
		else {
			try {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
				
				if (node == null) {
					return;
				}
				
				Object userObject = node.getUserObject();
				DefaultTableModel tableModel = (DefaultTableModel)getAccessRulesTable().getModel();
				int selectedRow = getAccessRulesTable().getSelectedRow();
				AccessRule accessRule = null;
						
				if (userObject instanceof Repository) {
					Repository repository = (Repository)userObject;
					Path path = (Path)tableModel.getValueAt(selectedRow, 0);
					Object object = tableModel.getValueAt(selectedRow, 1);
					
					if (object instanceof Group) {
						accessRule = Document.findGroupAccessRule(repository, path.getPath(), (Group)object);
					}
					else if (object instanceof User) {
						accessRule = Document.findUserAccessRule(repository, path.getPath(), (User)object);
					}
				}
				else if (userObject instanceof Path) {
					Path path = (Path)userObject;
					Object object = tableModel.getValueAt(selectedRow, 0);
					
					if (object instanceof Group) {
						accessRule = Document.findGroupAccessRule(path.getRepository(), path.getPath(), (Group)object);
					}
					else if (object instanceof User) {
						accessRule = Document.findUserAccessRule(path.getRepository(), path.getPath(), (User)object);
					}
				}
				else {
					Repository repository = (Repository)tableModel.getValueAt(selectedRow, 0);
					Path path = (Path)tableModel.getValueAt(selectedRow, 1);
					Object object = tableModel.getValueAt(selectedRow, 2);
					
					if (object instanceof Group) {
						accessRule = Document.findGroupAccessRule(repository, path.getPath(), (Group)object);
					}
					else if (object instanceof User) {
						accessRule = Document.findUserAccessRule(repository, path.getPath(), (User)object);
					}
				}
				
				Message message = new Message();
				
				JDialog dialog = new EditAccessRuleDialog(accessRule, message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);
				
				if (message.getState() == Message.SUCCESS) {					
					Document.setUnsavedChanges();
					refreshUserDetails();
					refreshGroupDetails();
					refreshAccessRuleTree(null);
				}
			}
			catch (ApplicationException ae) {
				displayError(ResourceUtil.getString("mainframe.error.erroreditingaccessrule"));
			}			
		}
		
		updateTitle();
	}
	
	/**
	 * Delete access rule handler.
	 */
	private void deleteAccessRule() {
		if (getAccessRulesTable().getSelectedRowCount() < 1) {
			displayWarning(ResourceUtil.getString("mainframe.warnming.noaccessrule"));
		} 
		else {
			int choice = JOptionPane
					.showConfirmDialog(
							this,
							ResourceUtil.getString("mainframe.deleteaccessrule.prompt"),
							ResourceUtil.getString("mainframe.deleteaccessrule.title"), 
							JOptionPane.YES_NO_OPTION);

			if (choice == JOptionPane.YES_OPTION) {
				try {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
					
					if (node == null) {
						return;
					}
					
					Object userObject = node.getUserObject();
					DefaultTableModel tableModel = (DefaultTableModel)getAccessRulesTable().getModel();
					int selectedRow = getAccessRulesTable().getSelectedRow();
							
					if (userObject instanceof Repository) {
						Repository repository = (Repository)userObject;
						Path path = (Path)tableModel.getValueAt(selectedRow, 0);
						Object object = tableModel.getValueAt(selectedRow, 1);
						
						if (object instanceof Group) {
							Document.deleteAccessRule(repository.getName(), path.getPath(), (Group)object, null);
						}
						else if (object instanceof User) {
							Document.deleteAccessRule(repository.getName(), path.getPath(), null, (User)object);
						}
					}
					else if (userObject instanceof Path) {
						Path path = (Path)userObject;
						Object object = tableModel.getValueAt(selectedRow, 0);
						
						if (object instanceof Group) {
							Document.deleteAccessRule(path.getRepository().getName(), path.getPath(), (Group)object, null);
						}
						else if (object instanceof User) {
							Document.deleteAccessRule(path.getRepository().getName(), path.getPath(), null, (User)object);
						}
					}
					else {
						Repository repository = (Repository)tableModel.getValueAt(selectedRow, 0);
						Path path = (Path)tableModel.getValueAt(selectedRow, 1);
						Object object = tableModel.getValueAt(selectedRow, 2);
						String repositoryName = (repository == null) ? null : repository.getName();
						
						if (object instanceof Group) {
							Document.deleteAccessRule(repositoryName, path.getPath(), (Group)object, null);
						}
						else if (object instanceof User) {
							Document.deleteAccessRule(repositoryName, path.getPath(), null, (User)object);
						}
					}
					
					refreshUserDetails();
					refreshGroupDetails();
					refreshAccessRuleTree(null);
				}
				catch (ApplicationException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errordeletingaccessrule"));
				}
			}
		}
		
		updateTitle();
	}
	
	/**
	 * About action handler.
	 */
	private void helpAbout() {
		JDialog dialog = new AboutDialog();
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);
	}

	/**
	 * License action handler.
	 */
	private void helpLicense() {
		JDialog dialog = new LicenseDialog();
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);
	}

	/**
	 * Preview action handler.
	 */
	private void preview() {
		if (Document.isEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.documentisempty"));
		}
		else {
			new Thread() {
			    public void run() {
			    	try {
						JFrame frame = new ViewerFrame(ResourceUtil.getString("preview.title"),
								FileGenerator.generate(),
								Constants.MIME_TEXT);
						frame.setVisible(true);
					}
					catch (ApplicationException e) {
						displayError(e.getMessage());
					}
				}
			  }.start();
		}
	}
	
	/**
	 * Statistics Report action handler.
	 */
	private void statisticsReport() {
		if (Document.isEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.documentisempty"));
		}
		else {
			new Thread() {
			    public void run() {
			    	try {
						GenericReport report = new StatisticsReport();
						JFrame frame = new ViewerFrame(ResourceUtil.getString("statisticsreport.title"),
								report.generate(),
								Constants.MIME_HTML);
						frame.setVisible(true);
					}
					catch (ApplicationException e) {
						displayError(e.getMessage());
					}
				}
			  }.start();
		}
	}
	
	/**
	 * Summary Report action handler.
	 */
	private void summaryReport() {
		if (Document.isEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.documentisempty"));
		}
		else {
			new Thread() {
			    public void run() {
					try {
						GenericReport report = new SummaryReport();
						JFrame frame = new ViewerFrame(ResourceUtil.getString("summaryreport.title"),
								report.generate(),
								Constants.MIME_HTML);
						frame.setVisible(true);
					}
					catch (ApplicationException ae) {
						displayError(ae.getMessage());
					}
					catch (Exception e) {
						displayError(e.getMessage());
					}
				}
			  }.start();
		}
	}

	/**
	 * Delete user action handler.
	 */
	private void deleteUser() {
		if (getUserList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nouserselected"));
		} 
		else {
			Object[] values = getUserList().getSelectedValues(); 
			int choice;
			
			if (values.length == 1) {
				choice = JOptionPane
				.showConfirmDialog(
						this,
						ResourceUtil.getString("mainframe.deleteuser.prompt"),
						ResourceUtil.getString("mainframe.deleteuser.title"),
						JOptionPane.YES_NO_OPTION);
			}
			else {
				choice = JOptionPane
				.showConfirmDialog(
						this,
						ResourceUtil.getString("mainframe.deleteusers.prompt"),
						ResourceUtil.getString("mainframe.deleteusers.title"),
						JOptionPane.YES_NO_OPTION);
			}
			
			if (choice == JOptionPane.YES_OPTION) {
				try {
					Document.deleteUsers(values);
				}
				catch (ApplicationException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errordeletinguser"));
				}
				
				refreshUserList(null);
				refreshGroupDetails();
				refreshAccessRuleTree(null);
			}
		}
		
		updateTitle();
	}

	/**
	 * Edit user action handler.
	 */
	private void editUser() {
		Object[] selectedItems = getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} 
		else {
			User selectedUser = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				JDialog dialog = new EditUserDialog((User)selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);
				
				if (message.getState() == Message.SUCCESS) {
					selectedUser = (User)message.getUserObject();
					Document.setUnsavedChanges();
				}
				else if (message.getUserObject() == null) {
					selectedUser = (User)selectedItems[i];
				}
				
				// Don't edit any other users if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshUserList(selectedUser);
			refreshAccessRuleTree(null);
		}
		
		updateTitle();
	}
	
	/**
	 * Exit action handler.
	 */
	private void exit() {
		checkForUnsavedChanges();
		System.exit(0);
	}
	
	/**
	 * Clone user action handler.
	 */
	private void cloneUser() {
		Object[] selectedItems = getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} 
		else {
			User selectedUser = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				JDialog dialog = new CloneUserDialog((User)selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);
				
				if (message.getState() == Message.SUCCESS) {
					selectedUser = (User)message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedUser = (User)selectedItems[i];
				}
				
				// Don't edit any other users if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshUserList(selectedUser);
			refreshAccessRuleTree(null);
		}
		
		updateTitle();
	}
	
	/**
	 * Clone group action handler.
	 */
	private void cloneGroup() {
		Object[] selectedItems = getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} 
		else {
			Group selectedGroup = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				
				JDialog dialog = new CloneGroupDialog((Group)selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);
				
				if (message.getState() == Message.SUCCESS) {
					selectedGroup = (Group)message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedGroup = (Group)selectedItems[i];
				}
				
				// Don't edit any other groups if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshGroupList(selectedGroup);
			refreshAccessRuleTree(null);
		}
		
		updateTitle();
	}
	
	/**
	 * Change Membership action handler. Displays ChangeMembership dialog.
	 */
	private void changeMembership() {
		Object[] selectedItems = getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nouserselected"));
		} 
		else {
			User selectedUser = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				JDialog dialog = new ChangeMembershipDialog((User)selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);
				
				if (message.getState() == Message.SUCCESS) {
					selectedUser = (User)message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedUser = (User)selectedItems[i];
				}
				
				// Don't edit any other users if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshUserList(selectedUser);
			refreshAccessRuleTree(null);
		}
		
		updateTitle();
	}
	
	/**
	 * Add/Remove Members action handler. Displays AddRemoveMembers dialog.
	 */
	private void addRemoveMembers() {
		Object[] selectedItems = getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		} 
		else {
			Group selectedGroup = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				
				JDialog dialog = new AddRemoveMembersDialog((Group)selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);
				
				if (message.getState() == Message.SUCCESS) {
					selectedGroup = (Group)message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedGroup = (Group)selectedItems[i];
				}
				
				// Don't edit any other groups if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshGroupList(selectedGroup);
			refreshAccessRuleTree(null);
		}
		
		updateTitle();
	}

	/**
	 * Delete group action handler.
	 */
	private void deleteGroup() {
		if (getGroupList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		} 
		else {
			Object[] values = getGroupList().getSelectedValues();
			int choice;
			
			if (values.length == 1) {
				choice = JOptionPane
				.showConfirmDialog(
						this, 
						ResourceUtil.getString("mainframe.deletegroup.prompt"),
						ResourceUtil.getString("mainframe.deletegroup.title"),
						JOptionPane.YES_NO_OPTION);
			}
			else {
				choice = JOptionPane
				.showConfirmDialog(
						this, 
						ResourceUtil.getString("mainframe.deletegroup.prompt"),
						ResourceUtil.getString("mainframe.deletegroup.title"),
						JOptionPane.YES_NO_OPTION);
			}
			
			if (choice == JOptionPane.YES_OPTION) {
				try {
					Document.deleteGroups(values);
				}
				catch (ApplicationException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errordeletinggroup"));
				}
				
				refreshGroupList(null);
				refreshAccessRuleTree(null);
			}
		}
		
		updateTitle();
	}

	/**
	 * Edit group action handler.
	 */
	private void editGroup() {
		Object[] selectedItems = getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} 
		else {
			Group selectedGroup = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				
				JDialog dialog = new EditGroupDialog((Group)selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);
				
				if (message.getState() == Message.SUCCESS) {
					selectedGroup = (Group)message.getUserObject();
					Document.setUnsavedChanges();
				}
				else if (message.getUserObject() == null) {
					selectedGroup = (Group)selectedItems[i];
				}
				
				// Don't edit any other groups if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshGroupList(selectedGroup);
			refreshAccessRuleTree(null);
		}
		
		updateTitle();
	}
	
	/**
	 * Delete path action handler.
	 */
	private void deletePath() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
		
		if (node == null) {
			return;
		}
		
		Object userObject = node.getUserObject();
				
		if (userObject instanceof Path) {
			int choice = JOptionPane
			.showConfirmDialog(
					this,
					ResourceUtil.getString("mainframe.deletepath.prompt"),
					ResourceUtil.getString("mainframe.deletepath.title"),
					JOptionPane.YES_NO_OPTION);

			if (choice == JOptionPane.YES_OPTION) {
				try {
					Document.deletePath((Path)userObject);
				}
				catch (ApplicationException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errordeletingpath"));
				}
				
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(null);
			}
		}		
		
		updateTitle();
	}
	
	/**
	 * Edit path action handler.
	 */
	private void editPath() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
		
		if (node == null) {
			return;
		}
		
		Object userObject = node.getUserObject();
				
		if (userObject instanceof Path) {
			Message message = new Message();
			
			JDialog dialog = new EditPathDialog((Path)userObject, message);
			DialogUtil.center(this, dialog);
			dialog.setVisible(true);
			
			if (message.getState() == Message.SUCCESS) {
				Document.setUnsavedChanges();
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(message.getUserObject());				
			}
		}		
		
		updateTitle();
	}

	/**
	 * Delete repository action handler.
	 */
	private void deleteRepository() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
		
		if (node == null) {
			return;
		}
		
		Object userObject = node.getUserObject();
				
		if (userObject instanceof Repository) {
			int choice = JOptionPane
			.showConfirmDialog(
					this,
					ResourceUtil.getString("mainframe.deleterepository.prompt"),
					ResourceUtil.getString("mainframe.deleterepository.title"),
					JOptionPane.YES_NO_OPTION);

			if (choice == JOptionPane.YES_OPTION) {
				try {
					Document.deleteRepository((Repository)userObject);
				}
				catch (ApplicationException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errordeletingrepository"));
				}
				
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(null);
			}
		}		
		
		updateTitle();
	}

	/**
	 * Edit repository action handler.
	 */
	private void editRepository() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
		
		if (node == null) {
			return;
		}
		
		Object userObject = node.getUserObject();
				
		if (userObject instanceof Repository) {
			Message message = new Message();
			
			JDialog dialog = new EditRepositoryDialog((Repository)userObject, message);
			DialogUtil.center(this, dialog);
			dialog.setVisible(true);
			
			if (message.getState() == Message.SUCCESS) {
				Document.setUnsavedChanges();
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(message.getUserObject());
			}
		}
		
		updateTitle();
	}

	/**
	 * File print action handler.
	 */
	private void filePrint() {
		/*
		 * Get the representation of the current printer and the current print
		 * job.
		 */
		PrinterJob printerJob = PrinterJob.getPrinterJob();

		/*
		 * Build a book containing pairs of page painters (Printables) and
		 * PageFormats. This example has a single page containing text.
		 */
		Book book = new Book();
		book.append(new Printer(), new PageFormat());

		/*
		 * Set the object to be printed (the Book) into the PrinterJob. Doing
		 * this before bringing up the print dialog allows the print dialog to
		 * correctly display the page range to be printed and to dissallow any
		 * print settings not appropriate for the pages to be printed.
		 */
		printerJob.setPageable(book);

		/*
		 * Show the print dialog to the user. This is an optional step and need
		 * not be done if the application wants to perform 'quiet' printing. If
		 * the user cancels the print dialog then false is returned. If true is
		 * returned we go ahead and print.
		 */
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {

			try {

				printerJob.print();

			} catch (PrinterException exception) {

				System.err.println(ResourceUtil.getString("mainframe.error.errorprinting"));

			}
		}

	}
	
	/**
	 * Help action handler.
	 */
	private void showHelp() {
		JFrame frame = new HelpFrame();
		frame.setVisible(true);
	}

	private void openLastEditedFileSettingChange() {
		boolean selected = openLastFileMenuItem.isSelected();
		
		UserPreferences.setOpenLastFile(selected);
	}
	
	/**
	 * ActionPerformed event handler. Redirects to the appropriate action handler.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Constants.NEW_FILE_ACTION)) {
			fileNew();
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION)) {
			fileOpen();
		} else if (e.getActionCommand().equals(Constants.SAVE_FILE_ACTION)) {
			fileSave();
		} else if (e.getActionCommand().equals(Constants.SAVE_FILE_AS_ACTION)) {
			fileSaveAs();
		} else if (e.getActionCommand().equals(Constants.OPEN_LAST_EDITED_FILE_ACTION)) {
			openLastEditedFileSettingChange();
		} else if (e.getActionCommand().equals(Constants.PRINT_ACTION)) {
			filePrint();
		} else if (e.getActionCommand().equals(Constants.EXIT_ACTION)) {
			exit();
		} else if (e.getActionCommand().equals(Constants.HELP_ACTION)) {
			showHelp();
		} else if (e.getActionCommand().equals(Constants.LICENSE_ACTION)) {
			helpLicense();
		} else if (e.getActionCommand().equals(Constants.ABOUT_ACTION)) {
			helpAbout();
		} else if (e.getActionCommand().equals(Constants.PREVIEW_ACTION)) {
			preview();
		} else if (e.getActionCommand().equals(Constants.STATISTICS_REPORT_ACTION)) {
			statisticsReport();
		} else if (e.getActionCommand().equals(Constants.SUMMARY_REPORT_ACTION)) {
			summaryReport();
		} else if (e.getActionCommand().equals(Constants.ADD_USER_ACTION)) {
			addUser();
		} else if (e.getActionCommand().equals(Constants.EDIT_USER_ACTION)) {
			editUser();
		} else if (e.getActionCommand().equals(Constants.CLONE_USER_ACTION)) {
			cloneUser();
		} else if (e.getActionCommand().equals(Constants.DELETE_USER_ACTION)) {
			deleteUser();
		} else if (e.getActionCommand().equals(Constants.CHANGE_MEMBERSHIP_ACTION)) {
			changeMembership();
		} else if (e.getActionCommand().equals(Constants.ADD_GROUP_ACTION)) {
			addGroup();
		} else if (e.getActionCommand().equals(Constants.EDIT_GROUP_ACTION)) {
			editGroup();
		} else if (e.getActionCommand().equals(Constants.CLONE_GROUP_ACTION)) {
			cloneGroup();
		} else if (e.getActionCommand().equals(Constants.DELETE_GROUP_ACTION)) {
			deleteGroup();
		} else if (e.getActionCommand().equals(Constants.ADD_REMOVE_MEMBERS_ACTION)) {
			addRemoveMembers();
		} else if (e.getActionCommand().equals(Constants.EDIT_PATH_ACTION)) {
			editPath();
		} else if (e.getActionCommand().equals(Constants.DELETE_PATH_ACTION)) {
			deletePath();
		} else if (e.getActionCommand().equals(Constants.EDIT_REPOSITORY_ACTION)) {
			editRepository();
		} else if (e.getActionCommand().equals(Constants.DELETE_REPOSITORY_ACTION)) {
			deleteRepository();
		} else if (e.getActionCommand().equals(Constants.ADD_ACCESS_RULE_ACTION)) {
			addAccessRule();
		} else if (e.getActionCommand().equals(Constants.EDIT_ACCESS_RULE_ACTION)) {
			editAccessRule();
		} else if (e.getActionCommand().equals(Constants.DELETE_ACCESS_RULE_ACTION)) {
			deleteAccessRule();
		} else if (e.getActionCommand().equals(Constants.MONOSPACED_ACTION)) {
			changeFont(Constants.FONT_MONOSPACED);
		} else if (e.getActionCommand().equals(Constants.SANS_SERIF_ACTION)) {
			changeFont(Constants.FONT_SANS_SERIF);
		} else if (e.getActionCommand().equals(Constants.SERIF_ACTION)) {
			changeFont(Constants.FONT_SERIF);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_0")) {
			fileOpen(0);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_1")) {
			fileOpen(1);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_2")) {
			fileOpen(2);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_3")) {
			fileOpen(3);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_4")) {
			fileOpen(4);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_5")) {
			fileOpen(5);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_6")) {
			fileOpen(6);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_7")) {
			fileOpen(7);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_8")) {
			fileOpen(8);
		} else if (e.getActionCommand().equals(Constants.OPEN_FILE_ACTION + "_9")) {
			fileOpen(9);
		} else if (e.getActionCommand().equals(Constants.CLEAR_RECENT_FILES_ACTION)) {
			clearRecentFiles();
		} else if (e.getActionCommand().equals(Constants.RESET_SETTINGS_ACTION)) {
			resetSettings();
		} else if (e.getActionCommand().equals(Constants.VIEW_USERS_ACTION)) {
			getMainTabbedPane().setSelectedComponent(getUsersSplitPane());
		} else if (e.getActionCommand().equals(Constants.VIEW_GROUPS_ACTION)) {
			getMainTabbedPane().setSelectedComponent(getGroupsSplitPane());
		} else if (e.getActionCommand().equals(Constants.VIEW_RULES_ACTION)) {
			getMainTabbedPane().setSelectedComponent(getAccessRulesSplitPane());
		} else {
			displayError(ResourceUtil.getString("application.erroroccurred"));
		}
		
		refreshTabNames();
	}

	/**
	 * This method initializes groupList.
	 * 
	 * @return javax.swing.JList
	 */
	private JList getGroupList() {
		if (groupList == null) {
			groupList = new JList();
			groupList.addListSelectionListener(this);
			groupList.addMouseListener(this);
			groupList.setCellRenderer(new MyListCellRenderer());
			groupList.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		
		return groupList;
	}

	/**
	 * This method initializes licenseMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getLicenseMenuItem() {
		if (licenseMenuItem == null) {
			licenseMenuItem = new JMenuItem();
			licenseMenuItem.addActionListener(this);
			licenseMenuItem.setActionCommand(Constants.LICENSE_ACTION);
			licenseMenuItem.setIcon(ResourceUtil.licenseIcon);
			licenseMenuItem.setText(ResourceUtil.getString("menu.help.license"));
		}
		
		return licenseMenuItem;
	}

	/**
	 * This method initializes groupDetailsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getGroupDetailsPanel() {
		if (groupDetailsPanel == null) {
			groupDetailsPanel = new JPanel();
			groupDetailsPanel.setLayout(new BorderLayout());
			groupDetailsPanel.add(getGroupDetailsSplitPanel(), BorderLayout.CENTER);
		}
		
		return groupDetailsPanel;
	}

	/**
	 * This method initializes userAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getUserAccessRulesColumnNames() {
		if (userAccessRulesColumnNames == null) {
			userAccessRulesColumnNames = new String[3];
			userAccessRulesColumnNames[0] = ResourceUtil.getString("mainframe.accessrulestable.repository");
			userAccessRulesColumnNames[1] = ResourceUtil.getString("mainframe.accessrulestable.path");
			userAccessRulesColumnNames[2] = ResourceUtil.getString("mainframe.accessrulestable.level");
		}

		return userAccessRulesColumnNames;
	}
	
	/**
	 * This method initializes groupAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getGroupAccessRulesColumnNames() {
		if (groupAccessRulesColumnNames == null) {
			groupAccessRulesColumnNames = new String[3];
			groupAccessRulesColumnNames[0] = ResourceUtil.getString("mainframe.accessrulestable.repository");
			groupAccessRulesColumnNames[1] = ResourceUtil.getString("mainframe.accessrulestable.path");
			groupAccessRulesColumnNames[2] = ResourceUtil.getString("mainframe.accessrulestable.level");
		}

		return groupAccessRulesColumnNames;
	}
	
	/**
	 * This method initializes repositoryAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getRepositoryAccessRulesColumnNames() {
		if (repositoryAccessRulesColumnNames == null) {
			repositoryAccessRulesColumnNames = new String[3];
			repositoryAccessRulesColumnNames[0] = ResourceUtil.getString("mainframe.accessrulestable.path");
			repositoryAccessRulesColumnNames[1] = ResourceUtil.getString("mainframe.accessrulestable.usergroup");
			repositoryAccessRulesColumnNames[2] = ResourceUtil.getString("mainframe.accessrulestable.level");			
		}

		return repositoryAccessRulesColumnNames;
	}
	
	/**
	 * This method initializes pathAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getPathAccessRulesColumnNames() {
		if (pathAccessRulesColumnNames == null) {
			pathAccessRulesColumnNames = new String[2];
			pathAccessRulesColumnNames[0] = ResourceUtil.getString("mainframe.accessrulestable.usergroup");
			pathAccessRulesColumnNames[1] = ResourceUtil.getString("mainframe.accessrulestable.level");
		}

		return pathAccessRulesColumnNames;
	}
	
	/**
	 * This method initializes serverAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getServerAccessRulesColumnNames() {
		if (serverAccessRulesColumnNames == null) {
			serverAccessRulesColumnNames = new String[4];
			serverAccessRulesColumnNames[0] = ResourceUtil.getString("mainframe.accessrulestable.repository");
			serverAccessRulesColumnNames[1] = ResourceUtil.getString("mainframe.accessrulestable.path");
			serverAccessRulesColumnNames[2] = ResourceUtil.getString("mainframe.accessrulestable.usergroup");
			serverAccessRulesColumnNames[3] = ResourceUtil.getString("mainframe.accessrulestable.level");
		}

		return serverAccessRulesColumnNames;
	}

	/**
	 * This method initializes actionMenu.
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getActionMenu() {
		if (actionMenu == null) {
			actionMenu = new JMenu();
			actionMenu.setText(ResourceUtil.getString("menu.action"));
			actionMenu.add(getAddUserMenuItem());
			actionMenu.add(getAddGroupMenuItem());
			actionMenu.add(getAddAccessRuleMenuItem());
		}
		
		return actionMenu;
	}
	
	/**
	 * This method initializes viewMenu.
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getViewMenu() {
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
	 * This method initializes viewUsersMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getViewUsersMenuItem() {
		if (viewUsersMenuItem == null) {
			viewUsersMenuItem = new JMenuItem();
			viewUsersMenuItem.addActionListener(this);
			viewUsersMenuItem.setActionCommand(Constants.VIEW_USERS_ACTION);
			viewUsersMenuItem.setIcon(ResourceUtil.userIcon);
			viewUsersMenuItem.setText(ResourceUtil.getString("menu.view.viewusers"));
			viewUsersMenuItem.setAccelerator(KeyStroke.getKeyStroke('1', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return viewUsersMenuItem;
	}

	/**
	 * This method initializes viewGroupsMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getViewGroupsMenuItem() {
		if (viewGroupsMenuItem == null) {
			viewGroupsMenuItem = new JMenuItem();
			viewGroupsMenuItem.addActionListener(this);
			viewGroupsMenuItem.setActionCommand(Constants.VIEW_GROUPS_ACTION);
			viewGroupsMenuItem.setIcon(ResourceUtil.groupIcon);
			viewGroupsMenuItem.setText(ResourceUtil.getString("menu.view.viewgroups"));
			viewGroupsMenuItem.setAccelerator(KeyStroke.getKeyStroke('2', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return viewGroupsMenuItem;
	}

	/**
	 * This method initializes viewRulesMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getViewRulesMenuItem() {
		if (viewRulesMenuItem == null) {
			viewRulesMenuItem = new JMenuItem();
			viewRulesMenuItem.addActionListener(this);
			viewRulesMenuItem.setActionCommand(Constants.VIEW_RULES_ACTION);
			viewRulesMenuItem.setIcon(ResourceUtil.listAccessRuleIcon);
			viewRulesMenuItem.setText(ResourceUtil.getString("menu.view.viewrules"));
			viewRulesMenuItem.setAccelerator(KeyStroke.getKeyStroke('3', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return viewRulesMenuItem;
	}

	/**
	 * This method initializes addUserMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddUserMenuItem() {
		if (addUserMenuItem == null) {
			addUserMenuItem = new JMenuItem();
			addUserMenuItem.addActionListener(this);
			addUserMenuItem.setActionCommand(Constants.ADD_USER_ACTION);
			addUserMenuItem.setIcon(ResourceUtil.addUserIcon);
			addUserMenuItem.setText(ResourceUtil.getString("menu.action.adduser"));
			addUserMenuItem.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return addUserMenuItem;
	}

	/**
	 * This method initializes addGroupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddGroupMenuItem() {
		if (addGroupMenuItem == null) {
			addGroupMenuItem = new JMenuItem();
			addGroupMenuItem.addActionListener(this);
			addGroupMenuItem.setActionCommand(Constants.ADD_GROUP_ACTION);
			addGroupMenuItem.setIcon(ResourceUtil.addGroupIcon);
			addGroupMenuItem.setText(ResourceUtil.getString("menu.action.addgroup"));
			addGroupMenuItem.setAccelerator(KeyStroke.getKeyStroke('G', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return addGroupMenuItem;
	}

	/**
	 * This method initializes addAccessRuleMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddAccessRuleMenuItem() {
		if (addAccessRuleMenuItem == null) {
			addAccessRuleMenuItem = new JMenuItem();
			addAccessRuleMenuItem.addActionListener(this);
			addAccessRuleMenuItem.setActionCommand(Constants.ADD_ACCESS_RULE_ACTION);
			addAccessRuleMenuItem.setIcon(ResourceUtil.addAccessRuleIcon);
			addAccessRuleMenuItem.setText(ResourceUtil.getString("menu.action.addaccessrule"));
			addAccessRuleMenuItem.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return addAccessRuleMenuItem;
	}

	/**
	 * This method initializes userListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getUserListScrollPane() {
		if (userListScrollPane == null) {
			userListScrollPane = new JScrollPane();
			userListScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			userListScrollPane.setViewportView(getUserList());
		}
		
		return userListScrollPane;
	}

	/**
	 * This method initializes groupListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getGroupListScrollPane() {
		if (groupListScrollPane == null) {
			groupListScrollPane = new JScrollPane();
			groupListScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			groupListScrollPane.setViewportView(getGroupList());			
		}
		
		return groupListScrollPane;
	}

	/**
	 * This method initializes userActionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getUserActionsPanel() {
		if (userActionsPanel == null) {
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			userActionsPanel = new JPanel(layout);
			userActionsPanel.add(getAddUserButton());
			userActionsPanel.add(getCloneUserButton());
			userActionsPanel.add(getEditUserButton());
			userActionsPanel.add(getDeleteUserButton());
		}
		
		return userActionsPanel;
	}

	/**
	 * This method initializes editUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditUserButton() {
		if (editUserButton == null) {
			editUserButton = new JButton();
			editUserButton.addActionListener(this);
			editUserButton.setActionCommand(Constants.EDIT_USER_ACTION);
			editUserButton.setIcon(ResourceUtil.editUserIcon);
			editUserButton.setText(ResourceUtil.getString("button.edit"));
			editUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.edituser.tooltip"));
			editUserButton.setEnabled(false);
		}
		
		return editUserButton;
	}

	/**
	 * This method initializes addUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddUserButton() {
		if (addUserButton == null) {
			addUserButton = new JButton();
			addUserButton.addActionListener(this);
			addUserButton.setActionCommand(Constants.ADD_USER_ACTION);
			addUserButton.setIcon(ResourceUtil.addUserIcon);
			addUserButton.setText(ResourceUtil.getString("button.add"));
			addUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
		}
		
		return addUserButton;
	}
	
	/**
	 * This method initializes cloneUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCloneUserButton() {
		if (cloneUserButton == null) {
			cloneUserButton = new JButton();
			cloneUserButton.addActionListener(this);
			cloneUserButton.setActionCommand(Constants.CLONE_USER_ACTION);
			cloneUserButton.setIcon(ResourceUtil.cloneUserIcon);
			cloneUserButton.setText(ResourceUtil.getString("button.clone"));
			cloneUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.cloneuser.tooltip"));
			cloneUserButton.setEnabled(false);
		}
		
		return cloneUserButton;
	}

	/**
	 * This method initializes deleteUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteUserButton() {
		if (deleteUserButton == null) {
			deleteUserButton = new JButton();
			deleteUserButton.addActionListener(this);
			deleteUserButton.setActionCommand(Constants.DELETE_USER_ACTION);
			deleteUserButton.setIcon(ResourceUtil.deleteUserIcon);
			deleteUserButton.setText(ResourceUtil.getString("button.delete"));
			deleteUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.deleteuser.tooltip"));
			deleteUserButton.setEnabled(false);			
		}
		
		return deleteUserButton;
	}

	/**
	 * This method initializes changeMembershipButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getChangeMembershipButton() {
		if (changeMembershipButton == null) {
			changeMembershipButton = new JButton();
			changeMembershipButton.addActionListener(this);
			changeMembershipButton.setActionCommand(Constants.CHANGE_MEMBERSHIP_ACTION);
			changeMembershipButton.setIcon(ResourceUtil.changeMembershipIcon);
			changeMembershipButton.setText(ResourceUtil.getString("mainframe.button.changemembership"));
			changeMembershipButton.setToolTipText(ResourceUtil.getString("mainframe.button.changemembership.tooltip"));
			changeMembershipButton.setEnabled(false);			
		}
		
		return changeMembershipButton;
	}

	/**
	 * This method initializes groupActionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getGroupActionsPanel() {
		if (groupActionsPanel == null) {
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			groupActionsPanel = new JPanel(layout);			
			groupActionsPanel.add(getAddGroupButton());
			groupActionsPanel.add(getCloneGroupButton());
			groupActionsPanel.add(getEditGroupButton());
			groupActionsPanel.add(getDeleteGroupButton());
		}
		
		return groupActionsPanel;
	}

	/**
	 * This method initializes addGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddGroupButton() {
		if (addGroupButton == null) {
			addGroupButton = new JButton();
			addGroupButton.addActionListener(this);
			addGroupButton.setActionCommand(Constants.ADD_GROUP_ACTION);
			addGroupButton.setIcon(ResourceUtil.addGroupIcon);
			addGroupButton.setText(ResourceUtil.getString("button.add"));
			addGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));			
		}
		
		return addGroupButton;
	}
	
	/**
	 * This method initializes cloneGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCloneGroupButton() {
		if (cloneGroupButton == null) {
			cloneGroupButton = new JButton();
			cloneGroupButton.addActionListener(this);
			cloneGroupButton.setActionCommand(Constants.CLONE_GROUP_ACTION);
			cloneGroupButton.setIcon(ResourceUtil.cloneGroupIcon);
			cloneGroupButton.setText(ResourceUtil.getString("button.clone"));
			cloneGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.clonegroup.tooltip"));
			cloneGroupButton.setEnabled(false);
		}
		
		return cloneGroupButton;
	}

	/**
	 * This method initializes editGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditGroupButton() {
		if (editGroupButton == null) {
			editGroupButton = new JButton();
			editGroupButton.addActionListener(this);
			editGroupButton.setActionCommand(Constants.EDIT_GROUP_ACTION);
			editGroupButton.setIcon(ResourceUtil.editGroupIcon);
			editGroupButton.setText(ResourceUtil.getString("button.edit"));
			editGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.editgroup.tooltip"));
			editGroupButton.setEnabled(false);
		}
		
		return editGroupButton;
	}

	/**
	 * This method initializes deleteGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteGroupButton() {
		if (deleteGroupButton == null) {
			deleteGroupButton = new JButton();
			deleteGroupButton.addActionListener(this);
			deleteGroupButton.setActionCommand(Constants.DELETE_GROUP_ACTION);
			deleteGroupButton.setIcon(ResourceUtil.deleteGroupIcon);
			deleteGroupButton.setText(ResourceUtil.getString("button.delete"));
			deleteGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.deletegroup.tooltip"));
			deleteGroupButton.setEnabled(false);
		}
		
		return deleteGroupButton;
	}

	/**
	 * This method initializes addRemoveMembersButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddRemoveMembersButton() {
		if (addRemoveMembersButton == null) {
			addRemoveMembersButton = new JButton();
			addRemoveMembersButton.addActionListener(this);
			addRemoveMembersButton.setActionCommand(Constants.ADD_REMOVE_MEMBERS_ACTION);
			addRemoveMembersButton.setIcon(ResourceUtil.addRemoveMembersIcon);
			addRemoveMembersButton.setText(ResourceUtil.getString("mainframe.button.addremovemembers"));
			addRemoveMembersButton.setToolTipText(ResourceUtil.getString("mainframe.button.addremovemembers.tooltip"));
			addRemoveMembersButton.setEnabled(false);
		}
		
		return addRemoveMembersButton;
	}

	/**
	 * This method initializes usersPopupMenu.
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getUsersPopupMenu() {
		if (usersPopupMenu == null) {
			usersPopupMenu = new JPopupMenu();
			usersPopupMenu.add(getAddUserPopupMenuItem());
			usersPopupMenu.add(getEditUserPopupMenuItem());
			usersPopupMenu.add(getDeleteUserPopupMenuItem());
			usersPopupMenu.add(getChangeMembershipPopupMenuItem());
			usersPopupMenu.add(getCloneUserPopupMenuItem());
			
			// Add listener to the user list
			MouseListener popupListener = new PopupListener(usersPopupMenu);
			getUserList().addMouseListener(popupListener);
		}
		
		return usersPopupMenu;
	}

	/**
	 * This method initializes addUserPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddUserPopupMenuItem() {
		if (addUserPopupMenuItem == null) {
			addUserPopupMenuItem = new JMenuItem();
			addUserPopupMenuItem.addActionListener(this);
			addUserPopupMenuItem.setActionCommand(Constants.ADD_USER_ACTION);
			addUserPopupMenuItem.setIcon(ResourceUtil.addUserIcon);
			addUserPopupMenuItem.setText(ResourceUtil.getString("button.add"));
			addUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
		}
		
		return addUserPopupMenuItem;
	}

	/**
	 * This method initializes editUserPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getEditUserPopupMenuItem() {
		if (editUserPopupMenuItem == null) {
			editUserPopupMenuItem = new JMenuItem();
			editUserPopupMenuItem.addActionListener(this);
			editUserPopupMenuItem.setActionCommand(Constants.EDIT_USER_ACTION);
			editUserPopupMenuItem.setIcon(ResourceUtil.editUserIcon);
			editUserPopupMenuItem.setText(ResourceUtil.getString("button.edit"));
			editUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.edituser.tooltip"));
			editUserPopupMenuItem.setEnabled(false);
		}
		
		return editUserPopupMenuItem;
	}

	/**
	 * This method initializes deleteUserPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDeleteUserPopupMenuItem() {
		if (deleteUserPopupMenuItem == null) {
			deleteUserPopupMenuItem = new JMenuItem();
			deleteUserPopupMenuItem.addActionListener(this);
			deleteUserPopupMenuItem.setActionCommand(Constants.DELETE_USER_ACTION);
			deleteUserPopupMenuItem.setIcon(ResourceUtil.deleteUserIcon);
			deleteUserPopupMenuItem.setText(ResourceUtil.getString("button.delete"));
			deleteUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deleteuser.tooltip"));
			deleteUserPopupMenuItem.setEnabled(false);
		}
		
		return deleteUserPopupMenuItem;
	}

	/**
	 * This method initializes changeMembershipPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getChangeMembershipPopupMenuItem() {
		if (changeMembershipPopupMenuItem == null) {
			changeMembershipPopupMenuItem = new JMenuItem();
			changeMembershipPopupMenuItem.addActionListener(this);
			changeMembershipPopupMenuItem.setActionCommand(Constants.CHANGE_MEMBERSHIP_ACTION);
			changeMembershipPopupMenuItem.setIcon(ResourceUtil.changeMembershipIcon);
			changeMembershipPopupMenuItem.setText(ResourceUtil.getString("mainframe.button.changemembership"));
			changeMembershipPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.changemembership.tooltip"));
			changeMembershipPopupMenuItem.setEnabled(false);
		}
		
		return changeMembershipPopupMenuItem;
	}

	/**
	 * This method initializes groupsPopupMenu.
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getGroupsPopupMenu() {
		if (groupsPopupMenu == null) {
			groupsPopupMenu = new JPopupMenu();
			groupsPopupMenu.add(getAddGroupPopupMenuItem());
			groupsPopupMenu.add(getEditGroupPopupMenuItem());
			groupsPopupMenu.add(getDeleteGroupPopupMenuItem());
			groupsPopupMenu.add(getAddRemoveMembersPopupMenuItem());
			groupsPopupMenu.add(getCloneGroupPopupMenuItem());
			
			// Add listener to the list.
			MouseListener popupListener = new PopupListener(groupsPopupMenu);
			getGroupList().addMouseListener(popupListener);
		}
		
		return groupsPopupMenu;
	}

	/**
	 * This method initializes addGroupPopupMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddGroupPopupMenuItem() {
		if (addGroupPopupMenuItem == null) {
			addGroupPopupMenuItem = new JMenuItem();
			addGroupPopupMenuItem.addActionListener(this);
			addGroupPopupMenuItem.setActionCommand(Constants.ADD_GROUP_ACTION);
			addGroupPopupMenuItem.setIcon(ResourceUtil.addGroupIcon);
			addGroupPopupMenuItem.setText(ResourceUtil.getString("button.add"));
			addGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));
		}
		
		return addGroupPopupMenuItem;
	}

	/**
	 * This method initializes editGroupPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getEditGroupPopupMenuItem() {
		if (editGroupPopupMenuItem == null) {
			editGroupPopupMenuItem = new JMenuItem();
			editGroupPopupMenuItem.addActionListener(this);
			editGroupPopupMenuItem.setActionCommand(Constants.EDIT_GROUP_ACTION);
			editGroupPopupMenuItem.setIcon(ResourceUtil.editGroupIcon);
			editGroupPopupMenuItem.setText(ResourceUtil.getString("button.edit"));
			editGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.editgroup.tooltip"));
			editGroupPopupMenuItem.setEnabled(false);
		}
		
		return editGroupPopupMenuItem;
	}

	/**
	 * This method initializes deleteGroupPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDeleteGroupPopupMenuItem() {
		if (deleteGroupPopupMenuItem == null) {
			deleteGroupPopupMenuItem = new JMenuItem();
			deleteGroupPopupMenuItem.addActionListener(this);
			deleteGroupPopupMenuItem.setActionCommand(Constants.DELETE_GROUP_ACTION);
			deleteGroupPopupMenuItem.setIcon(ResourceUtil.deleteGroupIcon);
			deleteGroupPopupMenuItem.setText(ResourceUtil.getString("button.delete"));
			deleteGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deletegroup.tooltip"));			
			deleteGroupPopupMenuItem.setEnabled(false);
		}
		
		return deleteGroupPopupMenuItem;
	}

	/**
	 * This method initializes addRemoveMembersPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddRemoveMembersPopupMenuItem() {
		if (addRemoveMembersPopupMenuItem == null) {
			addRemoveMembersPopupMenuItem = new JMenuItem();
			addRemoveMembersPopupMenuItem.addActionListener(this);
			addRemoveMembersPopupMenuItem.setActionCommand(Constants.ADD_REMOVE_MEMBERS_ACTION);
			addRemoveMembersPopupMenuItem.setIcon(ResourceUtil.addRemoveMembersIcon);
			addRemoveMembersPopupMenuItem.setText(ResourceUtil.getString("mainframe.button.addremovemembers"));
			addRemoveMembersPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.addremovemembers.tooltip"));
			addRemoveMembersPopupMenuItem.setEnabled(false);
		}
		
		return addRemoveMembersPopupMenuItem;
	}

	/**
	 * This method initializes addAccessRuleButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddAccessRuleButton() {
		if (addAccessRuleButton == null) {
			addAccessRuleButton = new JButton();
			addAccessRuleButton.addActionListener(this);
			addAccessRuleButton.setActionCommand(Constants.ADD_ACCESS_RULE_ACTION);
			addAccessRuleButton.setIcon(ResourceUtil.addAccessRuleIcon);
			addAccessRuleButton.setText(ResourceUtil.getString("button.add"));
			addAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.addaccessrule.tooltip"));			
		}
		
		return addAccessRuleButton;
	}

	/**
	 * This method initializes editAccessRuleButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditAccessRuleButton() {
		if (editAccessRuleButton == null) {
			editAccessRuleButton = new JButton();
			editAccessRuleButton.addActionListener(this);
			editAccessRuleButton.setActionCommand(Constants.EDIT_ACCESS_RULE_ACTION);
			editAccessRuleButton.setIcon(ResourceUtil.editAccessRuleIcon);
			editAccessRuleButton.setText(ResourceUtil.getString("button.edit"));
			editAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.editaccessrule.tooltip"));
			editAccessRuleButton.setEnabled(false);
		}
		
		return editAccessRuleButton;
	}

	/**
	 * This method initializes deleteAccessRuleButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteAccessRuleButton() {
		if (deleteAccessRuleButton == null) {
			deleteAccessRuleButton = new JButton();
			deleteAccessRuleButton.addActionListener(this);
			deleteAccessRuleButton.setActionCommand(Constants.DELETE_ACCESS_RULE_ACTION);
			deleteAccessRuleButton.setIcon(ResourceUtil.deleteAccessRuleIcon);
			deleteAccessRuleButton.setText(ResourceUtil.getString("button.delete"));
			deleteAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.deleteaccessrule.tooltip"));
			deleteAccessRuleButton.setEnabled(false);
		}
		return deleteAccessRuleButton;
	}

	/**
	 * This method initializes userDetailsSubPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JSplitPane getUserDetailsSplitPanel() {
		if (userDetailsSplitPanel == null) {
			userDetailsSplitPanel = new JSplitPane();
			userDetailsSplitPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));			
			userDetailsSplitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			userDetailsSplitPanel.setTopComponent(getUserGroupListPanel());
			userDetailsSplitPanel.setBottomComponent(getUserAccessRulesFormatPanel());
			userDetailsSplitPanel.setOneTouchExpandable(true);
			userDetailsSplitPanel.setDividerLocation(UserPreferences.getUserDetailsDividerLocation());
		}
		
		return userDetailsSplitPanel;
	}

	/**
	 * This method initializes userGroupList.
	 * 
	 * @return javax.swing.JList
	 */
	private JList getUserGroupList() {
		if (userGroupList == null) {
			userGroupList = new JList();
			userGroupList.addMouseListener(this);
			userGroupList.setCellRenderer(new MyListCellRenderer());
			userGroupList.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		
		return userGroupList;
	}

	/**
	 * This method initializes userAccessRulesTable.
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getUserAccessRulesTable() {
		if (userAccessRulesTable == null) {
			userAccessRulesTable = new JTable();
			userAccessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			userAccessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);	
			userAccessRulesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			userAccessRulesTable.setAutoCreateRowSorter(true);
		}
		
		return userAccessRulesTable;
	}

	/**
	 * This method initializes userAccessRulesScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getUserAccessRulesScrollPane() {
		if (userAccessRulesScrollPane == null) {
			userAccessRulesScrollPane = new JScrollPane();
			userAccessRulesScrollPane.setViewportView(getUserAccessRulesTable());
		}
		
		return userAccessRulesScrollPane;
	}

	/**
	 * This method initializes userGroupListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getUserGroupListScrollPane() {
		if (userGroupListScrollPane == null) {
			userGroupListScrollPane = new JScrollPane();
			userGroupListScrollPane.setViewportView(getUserGroupList());
		}
		
		return userGroupListScrollPane;
	}
	
	/**
	 * Refresh user details for selected user.
	 */
	private void refreshUserDetails() {
		User user = (User) getUserList().getSelectedValue();

		try {
			getUserGroupList().setModel(new DefaultListModel());
			
			if (!getUserList().isSelectionEmpty()) {
				Object[] listData = (Object[])Document.getUserGroupObjects(user);
				
				if (listData != null) {
					getUserGroupList().setListData(listData);						
				}
			}
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingusers"));
		}
		
		boolean enabled = getUserList().isSelectionEmpty() == false;
		toggleUserActions(enabled, (user == null) ? enabled : enabled && !user.isAllUsers());
		
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getUserAccessRuleObjects(user), getUserAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforuser"));
		}
		
		getUserAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getUserAccessRulesTable(), true);
	}
	
	/**
	 * Refresh group details for selected group.
	 */
	private void refreshGroupDetails() {		
		Group group = (Group)getGroupList().getSelectedValue();
		
		try {
			getGroupMemberList().setModel(new DefaultListModel());
			
			if (!getGroupList().isSelectionEmpty()) {
				Object[] listData = Document.getGroupMemberObjects(group);
				
				if (listData != null) {
					getGroupMemberList().setListData(listData);						
				}
			}
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadinggroupmembers"));
		}
		
		toggleGroupActions(getGroupList().isSelectionEmpty() == false);
		
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getGroupAccessRules(group), getGroupAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforgroup"));
		}
		
		getGroupAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getGroupAccessRulesTable(), true);
	}

	/**
	 * ValueChange event listenter. Refresh user/group details and
	 * access rules when new items selected.
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == getUserList()) {
			refreshUserDetails();
		} 
		else if (e.getSource() == getGroupList()) {
			refreshGroupDetails();
		}
		else if (e.getSource() == getAccessRulesTable().getSelectionModel() 
				&& getAccessRulesTable().getRowSelectionAllowed()) {			
			getEditAccessRuleButton().setEnabled(true);
			getDeleteAccessRuleButton().setEnabled(true);
		} 
	}

	/**
	 * This method initializes userAccessRulesFormatPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getUserAccessRulesFormatPanel() {
		if (userAccessRulesFormatPanel == null) {
			userAccessRulesFormatPanel = new JPanel();
			userAccessRulesFormatPanel.setLayout(new BorderLayout());
			userAccessRulesFormatPanel.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
			userAccessRulesFormatPanel.add(new JLabel(ResourceUtil.getString("mainframe.tabs.accessrules")), BorderLayout.NORTH);
			userAccessRulesFormatPanel.add(getUserAccessRulesScrollPane(), BorderLayout.CENTER);
		}
		
		return userAccessRulesFormatPanel;
	}

	/**
	 * This method initializes groupDetailsSubPanel.
	 * 
	 * @return javax.swing.JPanel
	 */	
	private JSplitPane getGroupDetailsSplitPanel() {
		if (groupDetailsSplitPanel == null) {
			groupDetailsSplitPanel = new JSplitPane();
			groupDetailsSplitPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));
			groupDetailsSplitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			groupDetailsSplitPanel.setTopComponent(getGroupMembersPanel());
			groupDetailsSplitPanel.setBottomComponent(getGroupAccessRulesPanel());
			groupDetailsSplitPanel.setOneTouchExpandable(true);
			groupDetailsSplitPanel.setDividerLocation(UserPreferences.getGroupDetailsDividerLocation());
		}
		
		return groupDetailsSplitPanel;
	}

	/**
	 * This method initializes groupAccessRulesTable.
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getGroupAccessRulesTable() {
		if (groupAccessRulesTable == null) {
			groupAccessRulesTable = new JTable();
			groupAccessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			groupAccessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);	
			groupAccessRulesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			groupAccessRulesTable.setAutoCreateRowSorter(true);
		}
		
		return groupAccessRulesTable;
	}

	/**
	 * This method initializes groupAccessRulesScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getGroupAccessRulesScrollPane() {
		if (groupAccessRulesScrollPane == null) {
			groupAccessRulesScrollPane = new JScrollPane();
			groupAccessRulesScrollPane.setViewportView(getGroupAccessRulesTable());
		}
		
		return groupAccessRulesScrollPane;
	}

	/**
	 * This method initializes accessRulesSplitPane.
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getAccessRulesSplitPane() {
		if (accessRulesSplitPane == null) {
			accessRulesSplitPane = new JSplitPane();
			accessRulesSplitPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			accessRulesSplitPane.setLeftComponent(getAccessRulesTreePanel());
			accessRulesSplitPane.setRightComponent(getAccessRulesPanel());
			accessRulesSplitPane.setDividerLocation(UserPreferences.getRulesPaneDividerLocation());
		}
		
		return accessRulesSplitPane;
	}

	/**
	 * This method initializes accessRulesTreeScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getAccessRulesTreeScrollPane() {
		if (accessRulesTreeScrollPane == null) {
			accessRulesTreeScrollPane = new JScrollPane();
			accessRulesTreeScrollPane.setViewportView(getAccessRulesTree());
		}
		
		return accessRulesTreeScrollPane;
	}

	/**
	 * This method initializes accessRulesTree.
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getAccessRulesTree() {
		if (accessRulesTree == null) {
			accessRulesTree = new JTree(new DefaultMutableTreeNode(ResourceUtil.getString("application.server")));
			accessRulesTree.addTreeSelectionListener(this);
			accessRulesTree.setShowsRootHandles(true);
			accessRulesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			accessRulesTree.setCellRenderer(new MyTreeCellRenderer());
			accessRulesTree.setExpandsSelectedPaths(true);
		}
		
		return accessRulesTree;
	}
	
	/**
	 * Refresh access rules tree. Selects specified object.
	 * 
	 * @param selectedObject Object to select.
	 */
	private void refreshAccessRuleTree(Object selectedObject) {
		TreePath treePath = null;
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(ResourceUtil.getString("application.server"));
		accessRuleTreeModel = new DefaultTreeModel(node);

		List<Repository> repositoryList = Document.getRepositories();
		
		Collections.sort(repositoryList);

		for (Repository repository : repositoryList) {
			DefaultMutableTreeNode repositoryNode = new DefaultMutableTreeNode(
					repository);

			node.add(repositoryNode);
			
			if (selectedObject == repository) {
				treePath = new TreePath(repositoryNode.getPath());
			}

			List<Path> pathList = repository.getPaths();
			
			Collections.sort(pathList, new PathComparator());
				
			for (Path path : pathList) {
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(path);
				repositoryNode.add(newNode);
				
				if (selectedObject == path) {
					treePath = new TreePath(newNode.getPath());
				}
			}
		}

		getAccessRulesTree().setModel(accessRuleTreeModel);
		getAccessRulesTree().setSelectionPath(treePath);
		getAccessRulesTree().scrollPathToVisible(treePath);
		
		if (selectedObject instanceof Repository) {
			refreshRepositoryAccessRules((Repository)selectedObject);
		}
		else if (selectedObject instanceof Path) {
			refreshPathAccessRules((Path)selectedObject);
		}
		else {
			getAccessRulesTree().setSelectionPath(new TreePath(getAccessRulesTree().getModel().getRoot()));
			refreshServerAccessRules();
		}
		
		toggleAccessRulesActions(false);
	}

	/**
	 * This method initializes accessRulesPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAccessRulesPanel() {
		if (accessRulesPanel == null) {
			accessRulesPanel = new JPanel();
			accessRulesPanel.setLayout(new BorderLayout());
			accessRulesPanel.add(getAccessRulesFormatPanel(), BorderLayout.CENTER);
			accessRulesPanel.add(getAccessRuleActionsPanel(), BorderLayout.SOUTH);			
		}
		
		return accessRulesPanel;
	}

	/**
	 * This method initializes accessRuleActionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAccessRuleActionsPanel() {
		if (accessRuleActionsPanel == null) {
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			accessRuleActionsPanel = new JPanel(layout);
			accessRuleActionsPanel.add(getAddAccessRuleButton());
			accessRuleActionsPanel.add(getEditAccessRuleButton());
			accessRuleActionsPanel.add(getDeleteAccessRuleButton());
		}
		
		return accessRuleActionsPanel;
	}

	/**
	 * This method initializes accessRulesTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getAccessRulesTable() {
		if (accessRulesTable == null) {
			accessRulesTable = new JTable();
			accessRulesTable.addMouseListener(this);
			accessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			accessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);
			accessRulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			accessRulesTable.getSelectionModel().addListSelectionListener(this);
			accessRulesTable.setAutoCreateRowSorter(true);
		}
		
		return accessRulesTable;
	}

	/**
	 * This method initializes toolbarPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getToolbarPanel() {
		if (toolbarPanel == null) {
			toolbarPanel = new JPanel(new GridLayout(1, 1));			
			toolbarPanel.add(getActionToolBar());
		}
		
		return toolbarPanel;
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
			printMenuItem.addActionListener(this);
			printMenuItem.setActionCommand(Constants.PRINT_ACTION);
			printMenuItem.setIcon(ResourceUtil.printIcon);
			printMenuItem.setText(ResourceUtil.getString("menu.file.print"));
			printMenuItem.setToolTipText(ResourceUtil.getString("menu.file.print.tooltip"));			
			printMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.print.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		
		return printMenuItem;
	}
	
	/**
	 * This method initializes accessRulesScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getAccessRulesScrollPane() {
		if (accessRulesScrollPane == null) {
			accessRulesScrollPane = new JScrollPane();
			accessRulesScrollPane.setViewportView(getAccessRulesTable());
		}
		
		return accessRulesScrollPane;
	}
	
	/**
	 * This method initializes accessRulesFormatPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getAccessRulesFormatPanel() {
		if (accessRulesFormatPanel == null) {
			accessRulesFormatPanel = new JPanel();
			accessRulesFormatPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));
			accessRulesFormatPanel.setLayout(new BorderLayout());			
			accessRulesFormatPanel.add(new JLabel(ResourceUtil.getString("mainframe.accessrules")), BorderLayout.NORTH);
			accessRulesFormatPanel.add(getAccessRulesScrollPane(), BorderLayout.CENTER);
		}
		
		return accessRulesFormatPanel;
	}
	
	/**
	 * This method initializes groupAccessRulesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getGroupAccessRulesPanel() {
		if (groupAccessRulesPanel == null) {
			groupAccessRulesPanel = new JPanel(new BorderLayout());
			groupAccessRulesPanel.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
			groupAccessRulesPanel.add(new JLabel(ResourceUtil.getString("mainframe.accessrules")), BorderLayout.NORTH);
			groupAccessRulesPanel.add(getGroupAccessRulesScrollPane(), BorderLayout.CENTER);
		}
		
		return groupAccessRulesPanel;
	}
	
	/**
	 * This method initializes groupMembersPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getGroupMembersPanel() {
		if (groupMembersPanel == null) {
			groupMembersPanel = new JPanel(new BorderLayout());
			groupMembersPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));			
			groupMembersPanel.add(new JLabel(ResourceUtil.getString("mainframe.members")), BorderLayout.NORTH);
			groupMembersPanel.add(getGroupMemberListScrollPane(), BorderLayout.CENTER);
			groupMembersPanel.add(getGroupMemberListActionsPanel(), BorderLayout.SOUTH);
		}
		
		return groupMembersPanel;
	}
	
	/**
	 * This method initializes groupMemberListScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getGroupMemberListScrollPane() {
		if (groupMemberListScrollPane == null) {
			groupMemberListScrollPane = new JScrollPane();
			groupMemberListScrollPane.setViewportView(getGroupMemberList());
		}
		
		return groupMemberListScrollPane;
	}
	
	/**
	 * This method initializes groupMemberList.	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getGroupMemberList() {
		if (groupMemberList == null) {
			groupMemberList = new JList();
			groupMemberList.addMouseListener(this);
			groupMemberList.setCellRenderer(new MyListCellRenderer());
			groupMemberList.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		
		return groupMemberList;
	}
	
	/**
	 * This method initializes userGroupListPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getUserGroupListPanel() {
		if (userGroupListPanel == null) {
			userGroupListPanel = new JPanel();
			userGroupListPanel.setLayout(new BorderLayout());
			userGroupListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
			userGroupListPanel.add(new JLabel(ResourceUtil.getString("mainframe.groups")), BorderLayout.NORTH);
			userGroupListPanel.add(getUserGroupListScrollPane(), BorderLayout.CENTER);
			userGroupListPanel.add(getUserGroupsActionPanel(), BorderLayout.SOUTH);
		}
		
		return userGroupListPanel;
	}

	/**
	 * Displays specified group in the Groups pane.
	 * 
	 * @param group Group to be displayed
	 */
	private void displayGroup(Object group) {
		if (group == null) {
			return;
		}
		
		getMainTabbedPane().setSelectedComponent(getGroupsSplitPane());
		getGroupList().setSelectedValue(group, true);
		refreshGroupDetails();
	}

	/**
	 * Displays specified user in the Users pane.
	 * 
	 * @param user User to be displayed
	 */
	private void displayUser(Object user) {
		if (user == null) {
			return;
		}
		
		getMainTabbedPane().setSelectedComponent(getUsersSplitPane());
		getUserList().setSelectedValue(user, true);
		refreshUserDetails();
	}
	
	/**
	 * MouseClick event handler.
	 * 
	 * @param event MouseEvent object
	 */
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (event.getSource() == getUserList()) {
				editUser();
			}
			else if (event.getSource() == getGroupList()) {
				editGroup();
			}
			else if (event.getSource() == getUserGroupList()) {
				displayGroup(getUserGroupList().getSelectedValue());
			}
			else if (event.getSource() == getGroupMemberList()) {
				if (getGroupMemberList().getSelectedValue() instanceof Group) {
					displayGroup(getGroupMemberList().getSelectedValue());
				}
				else {
					displayUser(getGroupMemberList().getSelectedValue());
				}
			}
			else if (event.getSource() == getAccessRulesTable()) {
				editAccessRule();
			}
		}
	}

	/**
	 * MousePressed event handler. Not used.
	 * 
	 * @param event MouseEvent object
	 */
	public void mousePressed(MouseEvent event) {
		// Not used
	}

	/**
	 * MouseReleased event handler. Not used.
	 * 
	 * @param event MouseEvent object
	 */
	public void mouseReleased(MouseEvent event) {
		// Not used
	}

	/**
	 * MouseEntered event handler. Not used.
	 * 
	 * @param event MouseEvent object
	 */
	public void mouseEntered(MouseEvent event) {
		// Not used
	}

	/**
	 * MouseExited event handler. Not used.
	 * 
	 * @param event MouseEvent object
	 */
	public void mouseExited(MouseEvent event) {
		// Not used
	}

	/**
	 * Refresh access rules table for the selected repository.
	 * 
	 * @param repository Selected repository.
	 */
	private void refreshRepositoryAccessRules(Repository repository) {
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getRepositoryAccessRules(repository), getRepositoryAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforrepository"));
		}
		
		getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesTable(), true);
		getEditAccessRuleButton().setEnabled(false);
		getDeleteAccessRuleButton().setEnabled(false);
	}

	/**
	 * Refresh access rules table for the selected path.
	 * 
	 * @param path Selected path.
	 */
	private void refreshPathAccessRules(Path path) {
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getPathAccessRules(path), getPathAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforpath"));
		}
		
		getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesTable(), true);
	}
	
	/**
	 * Refresh access rules table for the entire server.
	 */
	private void refreshServerAccessRules() {
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getServerAccessRules(), getServerAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforserver"));
		}
		
		getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesTable(), true);
	}
	
	/**
	 * ValueChanged event handler for access rules tree
	 * 
	 * @param event TreeSelectionEven object
	 */
	public void valueChanged(TreeSelectionEvent event) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
		
		if (node == null) {
			return;
		}
		
		Object userObject = node.getUserObject();
				
		if (userObject instanceof Repository) {
			refreshRepositoryAccessRules((Repository)userObject);
			
			getEditTreeItemButton().setEnabled(true);
			getDeleteTreeItemButton().setEnabled(true);
			
			getEditTreeItemButton().setIcon(ResourceUtil.repositoryEditIcon);
			getDeleteTreeItemButton().setIcon(ResourceUtil.repositoryDeleteIcon);
			
			getEditTreeItemButton().setActionCommand(Constants.EDIT_REPOSITORY_ACTION);
			getDeleteTreeItemButton().setActionCommand(Constants.DELETE_REPOSITORY_ACTION);
			
			getEditTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.editrepository.tooltip"));
			getDeleteTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.deleterepository.tooltip"));
		}
		else if (userObject instanceof Path) {
			refreshPathAccessRules((Path)userObject);
			
			getEditTreeItemButton().setEnabled(true);
			getDeleteTreeItemButton().setEnabled(true);
			
			getEditTreeItemButton().setIcon(ResourceUtil.pathEditIcon);
			getDeleteTreeItemButton().setIcon(ResourceUtil.pathDeleteIcon);
			
			getEditTreeItemButton().setActionCommand(Constants.EDIT_PATH_ACTION);
			getDeleteTreeItemButton().setActionCommand(Constants.DELETE_PATH_ACTION);
			
			getEditTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.editpath.tooltip"));
			getDeleteTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.deletepath.tooltip"));
		}
		else {
			refreshServerAccessRules();
			
			getEditTreeItemButton().setEnabled(false);
			getDeleteTreeItemButton().setEnabled(false);
			
			getEditTreeItemButton().setIcon(null);
			getDeleteTreeItemButton().setIcon(null);
			
			getEditTreeItemButton().setActionCommand(null);
			getDeleteTreeItemButton().setActionCommand(null);
			
			getEditTreeItemButton().setToolTipText(null);
			getDeleteTreeItemButton().setToolTipText(null);
		}
	}
	
	/**
	 * This method initializes accessRulesTreePanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getAccessRulesTreePanel() {
		if (accessRulesTreePanel == null) {
			accessRulesTreePanel = new JPanel(new BorderLayout());
			accessRulesTreePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
			accessRulesTreePanel.add(new JLabel(ResourceUtil.getString("mainframe.serverstructure")), BorderLayout.NORTH);
			accessRulesTreePanel.add(getAccessRulesTreeScrollPane(), BorderLayout.CENTER);
			accessRulesTreePanel.add(getAccessRulesTreeActionsPanel(), BorderLayout.SOUTH);
		}
		
		return accessRulesTreePanel;
	}
	
	/**
	 * This method initializes accessRulesTreeActionsPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getAccessRulesTreeActionsPanel() {
		if (accessRulesTreeActionsPanel == null) {
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			accessRulesTreeActionsPanel = new JPanel(layout);
			accessRulesTreeActionsPanel.add(getEditTreeItemButton());
			accessRulesTreeActionsPanel.add(getDeleteTreeItemButton());
		}
		
		return accessRulesTreeActionsPanel;
	}
	
	/**
	 * This method initializes editTreeItemButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getEditTreeItemButton() {
		if (editTreeItemButton == null) {
			editTreeItemButton = new JButton();
			editTreeItemButton.addActionListener(this);
			editTreeItemButton.setText(ResourceUtil.getString("button.edit"));
			editTreeItemButton.setEnabled(false);
		}
		
		return editTreeItemButton;
	}
	
	/**
	 * This method initializes deleteTreeItemButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getDeleteTreeItemButton() {
		if (deleteTreeItemButton == null) {
			deleteTreeItemButton = new JButton();
			deleteTreeItemButton.addActionListener(this);
			deleteTreeItemButton.setText(ResourceUtil.getString("button.delete"));
			deleteTreeItemButton.setEnabled(false);
		}
		
		return deleteTreeItemButton;
	}

	/**
	 * KeyTyped event handler. Not used.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyTyped(KeyEvent event) {
		// Unused
	}

	/**
	 * KeyPressed event handler.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyPressed(KeyEvent event) {
		if (event.getID() == KeyEvent.VK_F1) {
			showHelp();
		}
	}

	/**
	 * KeyReleased event handler. Not used.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyReleased(KeyEvent event) {
		// Unused
	}

	/**
	 * This method initializes userGroupsActionPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getUserGroupsActionPanel() {
		if (userGroupsActionPanel == null) {
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			userGroupsActionPanel = new JPanel(layout);			
			userGroupsActionPanel.add(getChangeMembershipButton());
		}
		
		return userGroupsActionPanel;
	}
	
	/**
	 * This method initializes groupMemberListActionsPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getGroupMemberListActionsPanel() {
		if (groupMemberListActionsPanel == null) {
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			groupMemberListActionsPanel = new JPanel(layout);
			groupMemberListActionsPanel.add(getAddRemoveMembersButton());
		}
		
		return groupMemberListActionsPanel;
	}
	
	/**
	 * This method initializes userListPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getUserListPanel() {
		if (userListPanel == null) {
			userListPanel = new JPanel(new BorderLayout());
			userListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
			userListPanel.add(getUserListScrollPane(), BorderLayout.CENTER);
			userListPanel.add(getUserActionsPanel(), BorderLayout.SOUTH);
			userListPanel.add(new JLabel(ResourceUtil.getString("mainframe.users")), BorderLayout.NORTH);
		}
		
		return userListPanel;
	}
	
	/**
	 * This method initializes groupListPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getGroupListPanel() {
		if (groupListPanel == null) {
			groupListPanel = new JPanel();
			groupListPanel.setLayout(new BorderLayout());
			groupListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
			groupListPanel.add(getGroupListScrollPane(), BorderLayout.CENTER);
			groupListPanel.add(getGroupActionsPanel(), BorderLayout.SOUTH);
			groupListPanel.add(new JLabel(ResourceUtil.getString("mainframe.groups")), BorderLayout.NORTH);
		}
		
		return groupListPanel;
	}
	
	/**
	 * This method initializes cloneUserPopupMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getCloneUserPopupMenuItem() {
		if (cloneUserPopupMenuItem == null) {
			cloneUserPopupMenuItem = new JMenuItem();
			cloneUserPopupMenuItem.addActionListener(this);
			cloneUserPopupMenuItem.setActionCommand(Constants.CLONE_USER_ACTION);
			cloneUserPopupMenuItem.setIcon(ResourceUtil.cloneUserIcon);
			cloneUserPopupMenuItem.setText(ResourceUtil.getString("menu.clone"));
			cloneUserPopupMenuItem.setEnabled(false);
		}
		return cloneUserPopupMenuItem;
	}
	/**
	 * This method initializes cloneGroupPopupMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getCloneGroupPopupMenuItem() {
		if (cloneGroupPopupMenuItem == null) {
			cloneGroupPopupMenuItem = new JMenuItem();
			cloneGroupPopupMenuItem.addActionListener(this);
			cloneGroupPopupMenuItem.setActionCommand(Constants.CLONE_GROUP_ACTION);
			cloneGroupPopupMenuItem.setIcon(ResourceUtil.cloneGroupIcon);
			cloneGroupPopupMenuItem.setText(ResourceUtil.getString("menu.clone"));
			cloneGroupPopupMenuItem.setEnabled(false);
		}
		
		return cloneGroupPopupMenuItem;
	}
	
	/**
	 * Updates title with current file name and state. For unknown files the
	 * name Untitled is used. Files that have unsaved changes are marked with
	 * a leading asterisk (*).
	 */
	private void updateTitle() {
		final String untitled = ResourceUtil.getString("application.untitled");
		String filename = "";
		String fullPath = "";
		
		// Add an asterisk if the file has changed.
		if (Document.hasUnsavedChanges()) {
			filename = "*";
		}
		
		// Add Untitled for new files or the file name for existing files
		if (Document.getFile() == null) {
			filename += untitled;
			fullPath = untitled;
		}
		else {
			filename += Document.getFile().getName();
			fullPath = Document.getFile().getAbsolutePath();
		}
		
		this.setTitle(ResourceUtil.getFormattedString("application.name", filename));
		
		getStatusLabel().setText(fullPath);
	}
	
	/**
	 * Prompts user to save current file if there are any unsaved changes.
	 */
	private void checkForUnsavedChanges() {
		if (Document.hasUnsavedChanges()) {
			int response = JOptionPane.showConfirmDialog(this,
					ResourceUtil.getString("application.unsavedchanges"),
					ResourceUtil.getString("application.warning"), 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			
			if (response == JOptionPane.YES_OPTION) {
				fileSave();
			}
		}
	}
	
	/**
	 * Prompts user if there is any data that is not going to be saved.
	 */
	private boolean checkForUnsaveableData() {
		String validateMessage = Document.validateDocument();
		
		if (validateMessage != null) {
			int response = JOptionPane.showConfirmDialog(this,
					validateMessage,
					ResourceUtil.getString("application.warning"), 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			
			return response == JOptionPane.YES_OPTION;
		}
		
		return true;
	}

	/**
	 * WindowActivated event handler. Does nothing.
	 */
	public void windowActivated(WindowEvent event) {
		// Do nothing
	}

	/**
	 * WindowClosed event handler. Does nothing.
	 */
	public void windowClosed(WindowEvent event) {
		// Do nothing.
	}

	/**
	 * WindowClosing event handler. Prompts user to save the current file if
	 * there are any unsaved changes.
	 */
	public void windowClosing(WindowEvent event) {
		checkForUnsavedChanges();
		saveUserPreferences();
	}

	/**
	 * WindowDeactivated event handler. Does nothing.
	 */
	public void windowDeactivated(WindowEvent event) {
		// Do nothing	
	}

	/**
	 * WindowDeiconified event handler. Does nothing.
	 */
	public void windowDeiconified(WindowEvent event) {
		// Do nothing	
	}

	/**
	 * WindowIconified event handler. Does nothing.
	 */
	public void windowIconified(WindowEvent event) {
		// Do nothing
	}

	/**
	 * WindowOpened event handler. Does nothing.
	 */
	public void windowOpened(WindowEvent event) {
		// Do nothing
	}
	
	/**
	 * Popup menu listener class.
	 * 
	 * @author Shaun Johnson
	 */
	class PopupListener extends MouseAdapter {
		private JPopupMenu popupMenu;

		/**
		 * Default Constructor.
		 * 
		 * @param popupMenu Menu displayed upon click
		 */
		public PopupListener(JPopupMenu popupMenu) {
			this.popupMenu = popupMenu;
		}

		/**
		 * Mouse button pressed event handler.
		 */
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		/**
		 * Mouse button released event handler.
		 */
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		/**
		 * Display popup menu if requirements met.
		 * 
		 * @param event MountEvent that triggered this
		 */
		private void maybeShowPopup(MouseEvent event) {
			if (event.isPopupTrigger()) {
				popupMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
	}

	/**
	 * This method initializes reportsMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getReportsMenu() {
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
	 * This method initializes previewMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPreviewMenuItem() {
		if (previewMenuItem == null) {
			previewMenuItem = new JMenuItem();
			previewMenuItem.setText(ResourceUtil.getString("menu.reports.preview"));
			previewMenuItem.addActionListener(this);
			previewMenuItem.setActionCommand(Constants.PREVIEW_ACTION);
		}
		return previewMenuItem;
	}

	/**
	 * This method initializes summaryReportMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSummaryReportMenuItem() {
		if (summaryReportMenuItem == null) {
			summaryReportMenuItem = new JMenuItem();
			summaryReportMenuItem.setText(ResourceUtil.getString("menu.reports.summaryreport"));
			summaryReportMenuItem.addActionListener(this);
			summaryReportMenuItem.setActionCommand(Constants.SUMMARY_REPORT_ACTION);
		}
		return summaryReportMenuItem;
	}
	
	/**
	 * This method initializes statusPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStatusPanel() {
		if (statusPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			
			statusPanel = new JPanel();
			statusPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			statusPanel.setLayout(flowLayout);
			statusPanel.add(getStatusLabel(), null);
		}
		return statusPanel;
	}
	
	private JLabel getStatusLabel() {
		if (statusLabel == null) {
			statusLabel = new JLabel();
			statusLabel.setFont(new Font(null, Font.PLAIN, 12));
		}
		
		return statusLabel;
	}

	/** 
	 * This method initializes settingsMenu.
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getSettingsMenu() {
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
	 * This method initializes resetSettingsMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getResetSettingsMenuItem() {
		if (resetSettingsMenuItem == null) {
			resetSettingsMenuItem = new JMenuItem();
			resetSettingsMenuItem.addActionListener(this);
			resetSettingsMenuItem.setActionCommand(Constants.RESET_SETTINGS_ACTION);
			resetSettingsMenuItem.setText(ResourceUtil.getString("menu.settings.resetsettings"));			
		}
		
		return resetSettingsMenuItem;
	}
			
	/**
	 * This method initializes monospacedRadioButtonMenuItem	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getMonospacedRadioButtonMenuItem() {
		if (monospacedRadioButtonMenuItem == null) {
			monospacedRadioButtonMenuItem = new JRadioButtonMenuItem();
			monospacedRadioButtonMenuItem.setText(ResourceUtil.getString("menu.settings.monospaced"));
			monospacedRadioButtonMenuItem.setFont(new Font(Constants.FONT_MONOSPACED, Font.BOLD, 12));
			monospacedRadioButtonMenuItem.addActionListener(this);
			monospacedRadioButtonMenuItem.setActionCommand(Constants.MONOSPACED_ACTION);
			
			if (UserPreferences.getUserFontStyle().equals(Constants.FONT_MONOSPACED)) {
				monospacedRadioButtonMenuItem.setSelected(true);
			}
		}
		return monospacedRadioButtonMenuItem;
	}

	/**
	 * This method initializes sansSerifRadioButtonMenuItem	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getSansSerifRadioButtonMenuItem() {
		if (sansSerifRadioButtonMenuItem == null) {
			sansSerifRadioButtonMenuItem = new JRadioButtonMenuItem();
			sansSerifRadioButtonMenuItem.setText(ResourceUtil.getString("menu.settings.sanserif"));
			sansSerifRadioButtonMenuItem.setFont(new Font(Constants.FONT_SANS_SERIF, Font.BOLD, 12));
			sansSerifRadioButtonMenuItem.addActionListener(this);
			sansSerifRadioButtonMenuItem.setActionCommand(Constants.SANS_SERIF_ACTION);
			
			if (UserPreferences.getUserFontStyle().equals(Constants.FONT_SANS_SERIF)) {
				sansSerifRadioButtonMenuItem.setSelected(true);
			}
		}
		return sansSerifRadioButtonMenuItem;
	}

	/**
	 * This method initializes serifRadioButtonMenuItem	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getSerifRadioButtonMenuItem() {
		if (serifRadioButtonMenuItem == null) {
			serifRadioButtonMenuItem = new JRadioButtonMenuItem();
			serifRadioButtonMenuItem.setText(ResourceUtil.getString("menu.settings.serif"));
			serifRadioButtonMenuItem.setFont(new Font(Constants.FONT_SERIF, Font.BOLD, 12));
			serifRadioButtonMenuItem.addActionListener(this);
			serifRadioButtonMenuItem.setActionCommand(Constants.SERIF_ACTION);
			
			if (UserPreferences.getUserFontStyle().equals(Constants.FONT_SERIF)) {
				serifRadioButtonMenuItem.setSelected(true);
			}
		}
		return serifRadioButtonMenuItem;
	}
	
	private void refreshTabNames() {		
		String title = "<html><strong>Users</strong>&nbsp;&nbsp;&nbsp;(" + 
			Document.getUsers().size() + 
			")</html>";
		getMainTabbedPane().setTitleAt(0, title);
		
		title = "<html><strong>Groups</strong>&nbsp;&nbsp;&nbsp;(" + 
			Document.getGroups().size() + 
			")</html>";
		getMainTabbedPane().setTitleAt(1, title);
	
		title = "<html><strong>Access Rules</strong>&nbsp;&nbsp;&nbsp;(" + 
			Document.getAccessRules().size() + 
			")</html>";
		getMainTabbedPane().setTitleAt(2, title);
	}

	/**
	 * This method initializes clearRecentFilesMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getClearRecentFilesMenuItem() {
		if (clearRecentFilesMenuItem == null) {
			clearRecentFilesMenuItem = new JMenuItem(ResourceUtil.getString("menu.file.clearrecentfiles"));
			clearRecentFilesMenuItem.addActionListener(this);
			clearRecentFilesMenuItem.setActionCommand(Constants.CLEAR_RECENT_FILES_ACTION);
		}
		return clearRecentFilesMenuItem;
	}

	/**
	 * This method initializes statisticsMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getStatisticsReportMenuItem() {
		if (statisticsMenuItem == null) {
			statisticsMenuItem = new JMenuItem();
			statisticsMenuItem.setText(ResourceUtil.getString("menu.reports.statisticsreport"));
			statisticsMenuItem.addActionListener(this);
			statisticsMenuItem.setActionCommand(Constants.STATISTICS_REPORT_ACTION);
		}
		return statisticsMenuItem;
	}
}  
