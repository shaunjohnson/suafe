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
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
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
import org.xiaoniu.suafe.dialogs.AddProjectAccessRulesDialog;
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
import org.xiaoniu.suafe.frames.menus.GroupsPopupMenu;
import org.xiaoniu.suafe.frames.menus.MainFrameMenuBar;
import org.xiaoniu.suafe.frames.menus.UsersPopupMenu;
import org.xiaoniu.suafe.frames.panes.AccessRulesPane;
import org.xiaoniu.suafe.frames.panes.GroupsPane;
import org.xiaoniu.suafe.frames.panes.UsersPane;
import org.xiaoniu.suafe.frames.toolbars.MainFrameToolBar;
import org.xiaoniu.suafe.models.NonEditableTableModel;
import org.xiaoniu.suafe.reports.GenericReport;
import org.xiaoniu.suafe.reports.StatisticsReport;
import org.xiaoniu.suafe.reports.SummaryReport;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Main Suafe application window.
 * 
 * @author Shaun Johnson
 */
public class MainFrame extends BaseFrame implements ActionListener, FileOpener,
	KeyListener, ListSelectionListener, MouseListener, TreeSelectionListener, 
	WindowListener {

	private static final long serialVersionUID = -4378074679449146788L;

	private FileTransferHandler fileTransferHandler = null;
	
	private JLabel statusLabel = null;
	
	private JPanel contentPane = null;    
	
	private JPanel statusPanel = null;
		
	private JTabbedPane mainTabbedPane = null;  
	
	private JPanel toolbarPanel = null;
	
	private AccessRulesPane accessRulesSplitPane = null;
	
	private MainFrameToolBar actionToolBar = null;
	
	private GroupsPopupMenu groupsPopupMenu = null;
	
	private GroupsPane groupsSplitPane = null; 
	
	private MainFrameMenuBar menuBar = null;
	
	private UsersPopupMenu usersPopupMenu = null; 
	
	private UsersPane usersSplitPane = null;
	
	private Object[] groupAccessRulesColumnNames;
	
	private Object[] pathAccessRulesColumnNames;	
	
	private Object[] repositoryAccessRulesColumnNames;
	
	private Object[] serverAccessRulesColumnNames;
	
	private Object[] userAccessRulesColumnNames;

	private Stack<String> fileStack = null;
	
	private TreeModel accessRuleTreeModel = null;
	
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
		this.setJMenuBar(getMainFrameMenuBar());		
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
		
		getGroupsSplitPane().getGroupDetailsSplitPanel().setDividerLocation(UserPreferences.getGroupDetailsDividerLocation());
		getGroupsSplitPane().setDividerLocation(UserPreferences.getGroupsPaneDividerLocation());
		getUsersSplitPane().getUserDetailsSplitPanel().setDividerLocation(UserPreferences.getUserDetailsDividerLocation());
		getUsersSplitPane().setDividerLocation(UserPreferences.getUsersPaneDividerLocation());
		getAccessRulesSplitPane().setDividerLocation(UserPreferences.getRulesPaneDividerLocation());
		
		menuBar.getOpenLastFileMenuItem().setSelected(UserPreferences.getOpenLastFile());
		
		this.setVisible(true);
	}
	
	private void saveUserPreferences() {
		UserPreferences.setWindowState(getExtendedState());
		
		this.setExtendedState(JFrame.NORMAL);
		UserPreferences.setWindowSize(getSize());
		
		UserPreferences.setWindowLocation(getLocation());
		UserPreferences.setGroupDetailsDividerLocation(
				getGroupsSplitPane().getGroupDetailsSplitPanel().getDividerLocation());
		UserPreferences.setGroupsPaneDividerLocation(
				getGroupsSplitPane().getDividerLocation());
		
		UserPreferences.setUserDetailsDividerLocation(
				getUsersSplitPane().getUserDetailsSplitPanel().getDividerLocation());
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
		
		getAccessRulesSplitPane().getAccessRulesTable().setFont(UserPreferences.getUserFont());
		getAccessRulesSplitPane().getAccessRulesTree().setFont(UserPreferences.getUserFont());
		getGroupsSplitPane().getGroupAccessRulesTable().setFont(UserPreferences.getUserFont());
		getGroupsSplitPane().getGroupList().setFont(UserPreferences.getUserFont());
		getGroupsSplitPane().getGroupMemberList().setFont(UserPreferences.getUserFont());
		getUsersSplitPane().getUserAccessRulesTable().setFont(UserPreferences.getUserFont());
		getUsersSplitPane().getUserGroupList().setFont(UserPreferences.getUserFont());
		getUsersSplitPane().getUserList().setFont(UserPreferences.getUserFont());
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
			
			mainTabbedPane.setFont(Constants.FONT_PLAIN);
		}
		
		return mainTabbedPane;
	}

	/**
	 * This method initializes usersSplitPane.
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private UsersPane getUsersSplitPane() {
		if (usersSplitPane == null) {
			usersSplitPane = new UsersPane(this, this, this, this);
		}
		
		return usersSplitPane;
	}

	/**
	 * This method initializes groupsSplitPane.
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private GroupsPane getGroupsSplitPane() {
		if (groupsSplitPane == null) {
			groupsSplitPane = new GroupsPane(this, this, this, this);
		}
		
		return groupsSplitPane;
	}

	/**
	 * This method initializes menuBar.
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private MainFrameMenuBar getMainFrameMenuBar() {
		if (menuBar == null) {
			menuBar = new MainFrameMenuBar(this);
			refreshRecentFiles();
		}
		
		return menuBar;
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
	 * Refreshes the recent files menu with the current list of recent files.
	 * The text displayed is the file name. The tooltip is the absolute path
	 * of the file.
	 */
	private void refreshRecentFiles() {
		menuBar.getRecentFilesMenu().removeAll();
		
		if (fileStack.isEmpty()) {
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
			
			menuBar.getRecentFilesMenu().add(menuItem);
		}
	}

	private void clearRecentFiles() {
		UserPreferences.clearRecentFiles();
		
		menuBar.getRecentFilesMenu().removeAll();
	}
	
	private void resetSettings() {
		UserPreferences.resetSettings();
		loadUserPreferences();
	}

	/**
	 * This method initializes actionToolBar.
	 * 
	 * @return javax.swing.JToolBar
	 */
	private MainFrameToolBar getActionToolBar() {
		if (actionToolBar == null) {
			actionToolBar = new MainFrameToolBar(this);
		}
		
		return actionToolBar;
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
		getAccessRulesSplitPane().getEditTreeItemButton().setEnabled(false);
		getAccessRulesSplitPane().getDeleteTreeItemButton().setEnabled(false);
		getMainTabbedPane().setSelectedComponent(getUsersSplitPane());
	}

	/**
	 * File open action handler.
	 */
	private void fileOpen() {
		final JFileChooser fcOpen = new JFileChooser();
		
		checkForUnsavedChanges();

		fcOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);

		File directory = (Document.getFile() == null) ? 
				null : Document.getFile().getParentFile();

		fcOpen.setSelectedFile(directory);

		int returnVal = fcOpen.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fcOpen.getSelectedFile();
				FileParser.parse(file);
				getUsersSplitPane().getUserList().setListData(Document.getUserObjects());
				getGroupsSplitPane().getGroupList().setListData(Document.getGroupObjects());

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
	private void reload() {
		if (Document.hasUnsavedChanges()) {
			int response = JOptionPane.showConfirmDialog(this,
					ResourceUtil.getString("application.unsavedchangesbeforereload"),
					ResourceUtil.getString("application.warning"), 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			
			if (response == JOptionPane.NO_OPTION) {
				return;
			}
		}

		try {
			File file = Document.getFile();
			FileParser.parse(file);
			getUsersSplitPane().getUserList().setListData(Document.getUserObjects());
			getGroupsSplitPane().getGroupList().setListData(Document.getGroupObjects());

			Document.setFile(file);
			
			refreshAccessRuleTree(null);

			Document.resetUnsavedChangesFlag();
			updateTitle();
			
			refreshTabNames();
		} 
		catch (Exception e) {
			displayError(e.getMessage());
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
			getUsersSplitPane().getUserList().setListData(Document.getUserObjects());
			getGroupsSplitPane().getGroupList().setListData(Document.getGroupObjects());

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
		getUsersSplitPane().getCloneUserButton().setEnabled(enabled);
		getUsersSplitPane().getEditUserButton().setEnabled(enabled);
		getUsersSplitPane().getDeleteUserButton().setEnabled(enabled);
		getUsersSplitPane().getChangeMembershipButton().setEnabled(changeMembershipEnabled);
		
		usersPopupMenu.getEditUserPopupMenuItem().setEnabled(enabled);
		usersPopupMenu.getDeleteUserPopupMenuItem().setEnabled(enabled);
		usersPopupMenu.getChangeMembershipPopupMenuItem().setEnabled(changeMembershipEnabled);
		usersPopupMenu.getCloneUserPopupMenuItem().setEnabled(enabled);
	}
	
	/**
	 * Toggles group actions state.
	 * 
	 * @param enabled If true group actions are enabled, otherwise disabled.
	 */
	private void toggleGroupActions(boolean enabled) {
		getGroupsSplitPane().getCloneGroupButton().setEnabled(enabled);
		getGroupsSplitPane().getEditGroupButton().setEnabled(enabled);
		getGroupsSplitPane().getDeleteGroupButton().setEnabled(enabled);
		getGroupsSplitPane().getAddRemoveMembersButton().setEnabled(enabled);
		
		groupsPopupMenu.getEditGroupPopupMenuItem().setEnabled(enabled);
		groupsPopupMenu.getDeleteGroupPopupMenuItem().setEnabled(enabled);
		groupsPopupMenu.getAddRemoveMembersPopupMenuItem().setEnabled(enabled);
		groupsPopupMenu.getCloneGroupPopupMenuItem().setEnabled(enabled);
	}

	/**
	 * Toggles access rules actions state.
	 * 
	 * @param enabled If true access rules actions enabled, otherwise disabled.
	 */
	private void toggleAccessRulesActions(boolean enabled) {
		getAccessRulesSplitPane().getEditAccessRuleButton().setEnabled(enabled);
		getAccessRulesSplitPane().getDeleteAccessRuleButton().setEnabled(enabled);
	}
	
	/**
	 * Refreshes user list with current users. List of selected users is
	 * used to reselect the users after refresh.
	 * 
	 * @param selectedUser User currently selected.
	 */
	private void refreshUserList(User selectedUser) {
		getUsersSplitPane().getUserList().setListData(Document.getUserObjects());
		
		boolean enabled = getUsersSplitPane().getUserList().isSelectionEmpty() == false;
		toggleUserActions(enabled, (selectedUser == null) ? enabled : enabled && !selectedUser.isAllUsers());
		
		if (selectedUser != null) {
			getUsersSplitPane().getUserList().setSelectedValue(selectedUser, true);
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
		getGroupsSplitPane().getGroupList().setListData(Document.getGroupObjects());
		toggleGroupActions(getGroupsSplitPane().getGroupList().isSelectionEmpty() == false);
		
		if (selectedGroup != null) {
			getGroupsSplitPane().getGroupList().setSelectedValue(selectedGroup, true);
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

		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
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
	 * Add Project Access Rules action handler. Displays AddProjectAccessRules
	 * dialog.
	 */
	private void addProjectAccessRules() {
		getMainTabbedPane().setSelectedComponent(getAccessRulesSplitPane());

		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
		Object userObject = (node == null) ? null : node.getUserObject();
		Message message = new Message();
		JDialog dialog = new AddProjectAccessRulesDialog(userObject, message);
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
		if (getAccessRulesSplitPane().getAccessRulesTable().getSelectedRowCount() < 1) {
			displayWarning(ResourceUtil.getString("mainframe.warning.noaccessruleselected"));
		}
		else {
			try {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
				
				if (node == null) {
					return;
				}
				
				Object userObject = node.getUserObject();
				DefaultTableModel tableModel = (DefaultTableModel)getAccessRulesSplitPane().getAccessRulesTable().getModel();
				int selectedRow = getAccessRulesSplitPane().getAccessRulesTable().getSelectedRow();
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
		if (getAccessRulesSplitPane().getAccessRulesTable().getSelectedRowCount() < 1) {
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
					DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
					
					if (node == null) {
						return;
					}
					
					Object userObject = node.getUserObject();
					DefaultTableModel tableModel = (DefaultTableModel)getAccessRulesSplitPane().getAccessRulesTable().getModel();
					int selectedRow = getAccessRulesSplitPane().getAccessRulesTable().getSelectedRow();
							
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
		if (getUsersSplitPane().getUserList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nouserselected"));
		} 
		else {
			Object[] values = getUsersSplitPane().getUserList().getSelectedValues(); 
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
		Object[] selectedItems = getUsersSplitPane().getUserList().getSelectedValues();

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
		Object[] selectedItems = getUsersSplitPane().getUserList().getSelectedValues();

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
		Object[] selectedItems = getGroupsSplitPane().getGroupList().getSelectedValues();

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
		Object[] selectedItems = getUsersSplitPane().getUserList().getSelectedValues();

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
		Object[] selectedItems = getGroupsSplitPane().getGroupList().getSelectedValues();

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
		if (getGroupsSplitPane().getGroupList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		} 
		else {
			Object[] values = getGroupsSplitPane().getGroupList().getSelectedValues();
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
		Object[] selectedItems = getGroupsSplitPane().getGroupList().getSelectedValues();

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
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
		
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
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
		
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
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
		
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
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
		
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
		boolean selected = menuBar.getOpenLastFileMenuItem().isSelected();
		
		UserPreferences.setOpenLastFile(selected);
	}
	
	/**
	 * ActionPerformed event handler. Redirects to the appropriate action handler.
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action.equals(Constants.NEW_FILE_ACTION)) {
			fileNew();
		} else if (action.equals(Constants.OPEN_FILE_ACTION)) {
			fileOpen();
		} else if (action.equals(Constants.RELOAD_ACTION)) {
			reload();
		} else if (action.equals(Constants.SAVE_FILE_ACTION)) {
			fileSave();
		} else if (action.equals(Constants.SAVE_FILE_AS_ACTION)) {
			fileSaveAs();
		} else if (action.equals(Constants.OPEN_LAST_EDITED_FILE_ACTION)) {
			openLastEditedFileSettingChange();
		} else if (action.equals(Constants.PRINT_ACTION)) {
			filePrint();
		} else if (action.equals(Constants.EXIT_ACTION)) {
			exit();
		} else if (action.equals(Constants.HELP_ACTION)) {
			showHelp();
		} else if (action.equals(Constants.LICENSE_ACTION)) {
			helpLicense();
		} else if (action.equals(Constants.ABOUT_ACTION)) {
			helpAbout();
		} else if (action.equals(Constants.PREVIEW_ACTION)) {
			preview();
		} else if (action.equals(Constants.STATISTICS_REPORT_ACTION)) {
			statisticsReport();
		} else if (action.equals(Constants.SUMMARY_REPORT_ACTION)) {
			summaryReport();
		} else if (action.equals(Constants.ADD_USER_ACTION)) {
			addUser();
		} else if (action.equals(Constants.EDIT_USER_ACTION)) {
			editUser();
		} else if (action.equals(Constants.CLONE_USER_ACTION)) {
			cloneUser();
		} else if (action.equals(Constants.DELETE_USER_ACTION)) {
			deleteUser();
		} else if (action.equals(Constants.CHANGE_MEMBERSHIP_ACTION)) {
			changeMembership();
		} else if (action.equals(Constants.ADD_GROUP_ACTION)) {
			addGroup();
		} else if (action.equals(Constants.EDIT_GROUP_ACTION)) {
			editGroup();
		} else if (action.equals(Constants.CLONE_GROUP_ACTION)) {
			cloneGroup();
		} else if (action.equals(Constants.DELETE_GROUP_ACTION)) {
			deleteGroup();
		} else if (action.equals(Constants.ADD_REMOVE_MEMBERS_ACTION)) {
			addRemoveMembers();
		} else if (action.equals(Constants.EDIT_PATH_ACTION)) {
			editPath();
		} else if (action.equals(Constants.DELETE_PATH_ACTION)) {
			deletePath();
		} else if (action.equals(Constants.EDIT_REPOSITORY_ACTION)) {
			editRepository();
		} else if (action.equals(Constants.DELETE_REPOSITORY_ACTION)) {
			deleteRepository();
		} else if (action.equals(Constants.ADD_ACCESS_RULE_ACTION)) {
			addAccessRule();
		} else if (action.equals(Constants.ADD_PROJECT_ACCESS_RULES_ACTION)) {
			addProjectAccessRules();
		} else if (action.equals(Constants.EDIT_ACCESS_RULE_ACTION)) {
			editAccessRule();
		} else if (action.equals(Constants.DELETE_ACCESS_RULE_ACTION)) {
			deleteAccessRule();
		} else if (action.equals(Constants.MONOSPACED_ACTION)) {
			changeFont(Constants.FONT_MONOSPACED);
		} else if (action.equals(Constants.SANS_SERIF_ACTION)) {
			changeFont(Constants.FONT_SANS_SERIF);
		} else if (action.equals(Constants.SERIF_ACTION)) {
			changeFont(Constants.FONT_SERIF);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_0")) {
			fileOpen(0);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_1")) {
			fileOpen(1);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_2")) {
			fileOpen(2);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_3")) {
			fileOpen(3);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_4")) {
			fileOpen(4);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_5")) {
			fileOpen(5);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_6")) {
			fileOpen(6);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_7")) {
			fileOpen(7);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_8")) {
			fileOpen(8);
		} else if (action.equals(Constants.OPEN_FILE_ACTION + "_9")) {
			fileOpen(9);
		} else if (action.equals(Constants.CLEAR_RECENT_FILES_ACTION)) {
			clearRecentFiles();
		} else if (action.equals(Constants.RESET_SETTINGS_ACTION)) {
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
	 * This method initializes userAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getUserAccessRulesColumnNames() {
		if (userAccessRulesColumnNames == null) {
			userAccessRulesColumnNames = new String[] {
				ResourceUtil.getString("mainframe.accessrulestable.repository"),
				ResourceUtil.getString("mainframe.accessrulestable.path"),
				ResourceUtil.getString("mainframe.accessrulestable.level")
			};
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
			groupAccessRulesColumnNames = new String[] {
				ResourceUtil.getString("mainframe.accessrulestable.repository"),
				ResourceUtil.getString("mainframe.accessrulestable.path"),
				ResourceUtil.getString("mainframe.accessrulestable.level")
			};
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
			repositoryAccessRulesColumnNames = new String[] {
				ResourceUtil.getString("mainframe.accessrulestable.path"),
				ResourceUtil.getString("mainframe.accessrulestable.usergroup"),
				ResourceUtil.getString("mainframe.accessrulestable.level")
			};
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
			serverAccessRulesColumnNames = new String[] {
				ResourceUtil.getString("mainframe.accessrulestable.repository"),
				ResourceUtil.getString("mainframe.accessrulestable.path"),
				ResourceUtil.getString("mainframe.accessrulestable.usergroup"),
				ResourceUtil.getString("mainframe.accessrulestable.level")
			};
		}

		return serverAccessRulesColumnNames;
	}

	/**
	 * This method initializes usersPopupMenu.
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private UsersPopupMenu getUsersPopupMenu() {
		if (usersPopupMenu == null) {
			usersPopupMenu = new UsersPopupMenu(this);
			
			// Add listener to the user list
			MouseListener popupListener = new PopupListener(usersPopupMenu);
			getUsersSplitPane().getUserList().addMouseListener(popupListener);
		}
		
		return usersPopupMenu;
	}

	/**
	 * This method initializes groupsPopupMenu.
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private GroupsPopupMenu getGroupsPopupMenu() {
		if (groupsPopupMenu == null) {
			groupsPopupMenu = new GroupsPopupMenu(this);
			
			// Add listener to the list.
			MouseListener popupListener = new PopupListener(groupsPopupMenu);
			getGroupsSplitPane().getGroupList().addMouseListener(popupListener);
		}
		
		return groupsPopupMenu;
	}
	
	/**
	 * Refresh user details for selected user.
	 */
	private void refreshUserDetails() {
		User user = (User) getUsersSplitPane().getUserList().getSelectedValue();

		try {
			getUsersSplitPane().getUserGroupList().setModel(new DefaultListModel());
			
			if (!getUsersSplitPane().getUserList().isSelectionEmpty()) {
				Object[] listData = (Object[])Document.getUserGroupObjects(user);
				
				if (listData != null) {
					getUsersSplitPane().getUserGroupList().setListData(listData);						
				}
			}
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingusers"));
		}
		
		boolean enabled = getUsersSplitPane().getUserList().isSelectionEmpty() == false;
		toggleUserActions(enabled, (user == null) ? enabled : enabled && !user.isAllUsers());
		
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getUserAccessRuleObjects(user), getUserAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforuser"));
		}
		
		getUsersSplitPane().getUserAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getUsersSplitPane().getUserAccessRulesTable(), true);
	}
	
	/**
	 * Refresh group details for selected group.
	 */
	private void refreshGroupDetails() {		
		Group group = (Group)getGroupsSplitPane().getGroupList().getSelectedValue();
		
		try {
			getGroupsSplitPane().getGroupMemberList().setModel(new DefaultListModel());
			
			if (!getGroupsSplitPane().getGroupList().isSelectionEmpty()) {
				Object[] listData = Document.getGroupMemberObjects(group);
				
				if (listData != null) {
					getGroupsSplitPane().getGroupMemberList().setListData(listData);						
				}
			}
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadinggroupmembers"));
		}
		
		toggleGroupActions(getGroupsSplitPane().getGroupList().isSelectionEmpty() == false);
		
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getGroupAccessRules(group), getGroupAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforgroup"));
		}
		
		getGroupsSplitPane().getGroupAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getGroupsSplitPane().getGroupAccessRulesTable(), true);
	}

	/**
	 * ValueChange event listenter. Refresh user/group details and
	 * access rules when new items selected.
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == getUsersSplitPane().getUserList()) {
			refreshUserDetails();
		} 
		else if (e.getSource() == getGroupsSplitPane().getGroupList()) {
			refreshGroupDetails();
		}
		else if (e.getSource() == getAccessRulesSplitPane().getAccessRulesTable().getSelectionModel() 
				&& getAccessRulesSplitPane().getAccessRulesTable().getRowSelectionAllowed()) {			
			getAccessRulesSplitPane().getEditAccessRuleButton().setEnabled(true);
			getAccessRulesSplitPane().getDeleteAccessRuleButton().setEnabled(true);
		} 
	}

	/**
	 * This method initializes accessRulesSplitPane.
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private AccessRulesPane getAccessRulesSplitPane() {
		if (accessRulesSplitPane == null) {
			accessRulesSplitPane = new AccessRulesPane(this, this, this, this);
		}
		
		return accessRulesSplitPane;
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

		getAccessRulesSplitPane().getAccessRulesTree().setModel(accessRuleTreeModel);
		getAccessRulesSplitPane().getAccessRulesTree().setSelectionPath(treePath);
		getAccessRulesSplitPane().getAccessRulesTree().scrollPathToVisible(treePath);
		
		if (selectedObject instanceof Repository) {
			refreshRepositoryAccessRules((Repository)selectedObject);
		}
		else if (selectedObject instanceof Path) {
			refreshPathAccessRules((Path)selectedObject);
		}
		else {
			getAccessRulesSplitPane().getAccessRulesTree().setSelectionPath(new TreePath(getAccessRulesSplitPane().getAccessRulesTree().getModel().getRoot()));
			refreshServerAccessRules();
		}
		
		toggleAccessRulesActions(false);
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
	 * Displays specified group in the Groups pane.
	 * 
	 * @param group Group to be displayed
	 */
	private void displayGroup(Object group) {
		if (group == null) {
			return;
		}
		
		getMainTabbedPane().setSelectedComponent(getGroupsSplitPane());
		getGroupsSplitPane().getGroupList().setSelectedValue(group, true);
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
		getUsersSplitPane().getUserList().setSelectedValue(user, true);
		refreshUserDetails();
	}
	
	/**
	 * MouseClick event handler.
	 * 
	 * @param event MouseEvent object
	 */
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (event.getSource() == getUsersSplitPane().getUserList()) {
				editUser();
			}
			else if (event.getSource() == getGroupsSplitPane().getGroupList()) {
				editGroup();
			}
			else if (event.getSource() == getUsersSplitPane().getUserGroupList()) {
				displayGroup(getUsersSplitPane().getUserGroupList().getSelectedValue());
			}
			else if (event.getSource() == getGroupsSplitPane().getGroupMemberList()) {
				if (getGroupsSplitPane().getGroupMemberList().getSelectedValue() instanceof Group) {
					displayGroup(getGroupsSplitPane().getGroupMemberList().getSelectedValue());
				}
				else {
					displayUser(getGroupsSplitPane().getGroupMemberList().getSelectedValue());
				}
			}
			else if (event.getSource() == getAccessRulesSplitPane().getAccessRulesTable()) {
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
		
		getAccessRulesSplitPane().getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesSplitPane().getAccessRulesTable(), true);
		getAccessRulesSplitPane().getEditAccessRuleButton().setEnabled(false);
		getAccessRulesSplitPane().getDeleteAccessRuleButton().setEnabled(false);
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
		
		getAccessRulesSplitPane().getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesSplitPane().getAccessRulesTable(), true);
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
		
		getAccessRulesSplitPane().getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesSplitPane().getAccessRulesTable(), true);
	}
	
	/**
	 * ValueChanged event handler for access rules tree
	 * 
	 * @param event TreeSelectionEven object
	 */
	public void valueChanged(TreeSelectionEvent event) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesSplitPane().getAccessRulesTree().getLastSelectedPathComponent();
		
		if (node == null) {
			return;
		}
		
		Object userObject = node.getUserObject();
				
		if (userObject instanceof Repository) {
			refreshRepositoryAccessRules((Repository)userObject);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setEnabled(true);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setEnabled(true);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setIcon(ResourceUtil.repositoryEditIcon);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setIcon(ResourceUtil.repositoryDeleteIcon);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setActionCommand(Constants.EDIT_REPOSITORY_ACTION);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setActionCommand(Constants.DELETE_REPOSITORY_ACTION);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.editrepository.tooltip"));
			getAccessRulesSplitPane().getDeleteTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.deleterepository.tooltip"));
		}
		else if (userObject instanceof Path) {
			refreshPathAccessRules((Path)userObject);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setEnabled(true);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setEnabled(true);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setIcon(ResourceUtil.pathEditIcon);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setIcon(ResourceUtil.pathDeleteIcon);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setActionCommand(Constants.EDIT_PATH_ACTION);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setActionCommand(Constants.DELETE_PATH_ACTION);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.editpath.tooltip"));
			getAccessRulesSplitPane().getDeleteTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.deletepath.tooltip"));
		}
		else {
			refreshServerAccessRules();
			
			getAccessRulesSplitPane().getEditTreeItemButton().setEnabled(false);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setEnabled(false);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setIcon(null);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setIcon(null);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setActionCommand(null);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setActionCommand(null);
			
			getAccessRulesSplitPane().getEditTreeItemButton().setToolTipText(null);
			getAccessRulesSplitPane().getDeleteTreeItemButton().setToolTipText(null);
		}
	}

	/**
	 * KeyTyped event handler. Not used.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyTyped(KeyEvent event) {
		// Unused
	}
	
	private void removeMembers() {
		if (getGroupsSplitPane().getGroupMemberList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nomemberselected"));
		} 
		else {
			Object[] values = getGroupsSplitPane().getGroupMemberList().getSelectedValues(); 
			Group group = (Group)getGroupsSplitPane().getGroupList().getSelectedValue();
			int choice;
			
			if (values.length == 1) {
				choice = JOptionPane
				.showConfirmDialog(
						this,
						ResourceUtil.getString("mainframe.removemember.prompt"),
						ResourceUtil.getString("mainframe.removemember.title"),
						JOptionPane.YES_NO_OPTION);
			}
			else {
				choice = JOptionPane
				.showConfirmDialog(
						this,
						ResourceUtil.getString("mainframe.removemembers.prompt"),
						ResourceUtil.getString("mainframe.removemembers.title"),
						JOptionPane.YES_NO_OPTION);
			}
			
			if (choice == JOptionPane.YES_OPTION) {
				try {
					Document.removeGroupMembers(group, values);
				}
				catch (ApplicationException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errorremovingmember"));
				}
				
				refreshUserList((User)getUsersSplitPane().getUserList().getSelectedValue());
				refreshGroupList((Group)getGroupsSplitPane().getGroupList().getSelectedValue());
				refreshAccessRuleTree(null);
			}
		}
		
		updateTitle();
	}
	
	private void removeFromGroups() {
		if (getUsersSplitPane().getUserGroupList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		} 
		else {
			Object[] values = getUsersSplitPane().getUserGroupList().getSelectedValues(); 
			User user = (User)getUsersSplitPane().getUserList().getSelectedValue();
			int choice;
			
			if (values.length == 1) {
				choice = JOptionPane
				.showConfirmDialog(
						this,
						ResourceUtil.getString("mainframe.removefromgroup.prompt"),
						ResourceUtil.getString("mainframe.removefromgroup.title"),
						JOptionPane.YES_NO_OPTION);
			}
			else {
				choice = JOptionPane
				.showConfirmDialog(
						this,
						ResourceUtil.getString("mainframe.removefromgroups.prompt"),
						ResourceUtil.getString("mainframe.removefromgroups.title"),
						JOptionPane.YES_NO_OPTION);
			}
			
			if (choice == JOptionPane.YES_OPTION) {
				try {
					Document.removeFromGroups(user, values);
				}
				catch (ApplicationException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errorremovingmember"));
				}
				
				refreshUserList((User)getUsersSplitPane().getUserList().getSelectedValue());
				refreshGroupList((Group)getGroupsSplitPane().getGroupList().getSelectedValue());
				refreshAccessRuleTree(null);
			}
		}
		
		updateTitle();	
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
		else if (event.getKeyCode() == KeyEvent.VK_DELETE) {
			Component component = event.getComponent();
			
			if (component == getUsersSplitPane().getUserList()) {
				deleteUser();
			}
			else if (component == getGroupsSplitPane().getGroupList()) {
				deleteGroup();
			}
			else if (component == getGroupsSplitPane().getGroupMemberList()) {
				removeMembers();
			}
			else if (component == getUsersSplitPane().getUserGroupList()) {
				removeFromGroups();
			}
//			else if (component == getAccessRulesTree()) {
//				
//			}
			
			refreshTabNames();
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
			statusLabel.setFont(Constants.FONT_PLAIN);
		}
		
		return statusLabel;
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
}  
