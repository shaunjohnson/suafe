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
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
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
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.help.DefaultHelpBroker;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.help.WindowPresentation;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
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

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.AutofitTableColumns;
import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.FileGenerator;
import org.xiaoniu.suafe.FileOpener;
import org.xiaoniu.suafe.FileParser;
import org.xiaoniu.suafe.FileTransferHandler;
import org.xiaoniu.suafe.GuiConstants;
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
import org.xiaoniu.suafe.dialogs.AddProjectAccessRulesDialog;
import org.xiaoniu.suafe.dialogs.AddRemoveMembersDialog;
import org.xiaoniu.suafe.dialogs.BasicDialog;
import org.xiaoniu.suafe.dialogs.ChangeMembershipDialog;
import org.xiaoniu.suafe.dialogs.DialogUtil;
import org.xiaoniu.suafe.dialogs.EditAccessRuleDialog;
import org.xiaoniu.suafe.exceptions.AppException;
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
public final class MainFrame extends BaseFrame implements ActionListener, FileOpener, KeyListener, ListSelectionListener,
		MouseListener, TreeSelectionListener, WindowListener {

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
		 * Display popup menu if requirements met.
		 * 
		 * @param event MountEvent that triggered this
		 */
		private void maybeShowPopup(MouseEvent event) {
			if (event.isPopupTrigger()) {
				popupMenu.show(event.getComponent(), event.getX(), event.getY());
			}
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
	}

	private static final long serialVersionUID = -4378074679449146788L;

	private AccessRulesPane accessRulesPane = null;

	private TreeModel accessRulesTreeModel = null;

	private MainFrameToolBar actionToolBar = null;

	private JPanel contentPane = null;

	private Document document = new Document();

	private Stack<String> fileStack = null;

	private FileTransferHandler fileTransferHandler = null;

	private Object[] groupAccessRulesColumnNames;

	private GroupsPane groupsPane = null;

	private GroupsPopupMenu groupsPopupMenu = null;

	private HelpBroker mainHB = null;

	private MainFrameMenuBar menuBar = null;

	private Object[] pathAccessRulesColumnNames;

	private Object[] repositoryAccessRulesColumnNames;

	private Object[] serverAccessRulesColumnNames;

	private JLabel statusLabel = null;

	private JPanel statusPanel = null;

	private JTabbedPane tabbedPane = null;

	private JPanel toolbarPanel = null;

	private Object[] userAccessRulesColumnNames;

	private UsersPane usersPane = null;

	private UsersPopupMenu usersPopupMenu = null;

	/**
	 * Default constructor
	 */
	public MainFrame() {
		super();

		initialize();
	}

	/**
	 * ActionPerformed event handler. Redirects to the appropriate action handler.
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals(ActionConstants.NEW_FILE_ACTION)) {
			fileNew();
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION)) {
			fileOpen();
		}
		else if (action.equals(ActionConstants.RELOAD_ACTION)) {
			reload();
		}
		else if (action.equals(ActionConstants.SAVE_FILE_ACTION)) {
			fileSave();
		}
		else if (action.equals(ActionConstants.SAVE_FILE_AS_ACTION)) {
			fileSaveAs();
		}
		else if (action.equals(ActionConstants.OPEN_LAST_EDITED_FILE_ACTION)) {
			openLastEditedFileSettingChange();
		}
		else if (action.equals(ActionConstants.MULTIPLE_LINE_GROUP_DEFINITION_ACTION)) {
			multilineGroupDefinitionsSettingChange();
		}
		else if (action.equals(ActionConstants.PRINT_ACTION)) {
			filePrint();
		}
		else if (action.equals(ActionConstants.EXIT_ACTION)) {
			exit();
		}
		else if (action.equals(ActionConstants.ABOUT_ACTION)) {
			helpAbout();
		}
		else if (action.equals(ActionConstants.PREVIEW_ACTION)) {
			preview();
		}
		else if (action.equals(ActionConstants.STATISTICS_REPORT_ACTION)) {
			statisticsReport();
		}
		else if (action.equals(ActionConstants.SUMMARY_REPORT_ACTION)) {
			summaryReport();
		}
		else if (action.equals(ActionConstants.ADD_USER_ACTION)) {
			addUser();
		}
		else if (action.equals(ActionConstants.RENAME_USER_ACTION)) {
			renameUser();
		}
		else if (action.equals(ActionConstants.CLONE_USER_ACTION)) {
			cloneUser();
		}
		else if (action.equals(ActionConstants.DELETE_USER_ACTION)) {
			deleteUser();
		}
		else if (action.equals(ActionConstants.CHANGE_MEMBERSHIP_ACTION)) {
			changeMembership();
		}
		else if (action.equals(ActionConstants.ADD_GROUP_ACTION)) {
			addGroup();
		}
		else if (action.equals(ActionConstants.RENAME_GROUP_ACTION)) {
			renameGroup();
		}
		else if (action.equals(ActionConstants.CLONE_GROUP_ACTION)) {
			cloneGroup();
		}
		else if (action.equals(ActionConstants.DELETE_GROUP_ACTION)) {
			deleteGroup();
		}
		else if (action.equals(ActionConstants.ADD_REMOVE_MEMBERS_ACTION)) {
			addRemoveMembers();
		}
		else if (action.equals(ActionConstants.EDIT_PATH_ACTION)) {
			editPath();
		}
		else if (action.equals(ActionConstants.DELETE_PATH_ACTION)) {
			deletePath();
		}
		else if (action.equals(ActionConstants.RENAME_REPOSITORY_ACTION)) {
			renameRepository();
		}
		else if (action.equals(ActionConstants.DELETE_REPOSITORY_ACTION)) {
			deleteRepository();
		}
		else if (action.equals(ActionConstants.ADD_ACCESS_RULE_ACTION)) {
			addAccessRule();
		}
		else if (action.equals(ActionConstants.ADD_PROJECT_ACCESS_RULES_ACTION)) {
			addProjectAccessRules();
		}
		else if (action.equals(ActionConstants.EDIT_ACCESS_RULE_ACTION)) {
			editAccessRule();
		}
		else if (action.equals(ActionConstants.DELETE_ACCESS_RULE_ACTION)) {
			deleteAccessRule();
		}
		else if (action.equals(ActionConstants.MONOSPACED_ACTION)) {
			changeFont(GuiConstants.FONT_FAMILY_MONOSPACED);
		}
		else if (action.equals(ActionConstants.SANS_SERIF_ACTION)) {
			changeFont(GuiConstants.FONT_FAMILY_SANS_SERIF);
		}
		else if (action.equals(ActionConstants.SERIF_ACTION)) {
			changeFont(GuiConstants.FONT_FAMILY_SERIF);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_0")) {
			fileOpen(0);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_1")) {
			fileOpen(1);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_2")) {
			fileOpen(2);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_3")) {
			fileOpen(3);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_4")) {
			fileOpen(4);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_5")) {
			fileOpen(5);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_6")) {
			fileOpen(6);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_7")) {
			fileOpen(7);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_8")) {
			fileOpen(8);
		}
		else if (action.equals(ActionConstants.OPEN_FILE_ACTION + "_9")) {
			fileOpen(9);
		}
		else if (action.equals(ActionConstants.CLEAR_RECENT_FILES_ACTION)) {
			clearRecentFiles();
		}
		else if (action.equals(ActionConstants.RESET_SETTINGS_ACTION)) {
			resetSettings();
		}
		else if (e.getActionCommand().equals(ActionConstants.VIEW_USERS_ACTION)) {
			getMainTabbedPane().setSelectedComponent(getUsersPane());
		}
		else if (e.getActionCommand().equals(ActionConstants.VIEW_GROUPS_ACTION)) {
			getMainTabbedPane().setSelectedComponent(getGroupsPane());
		}
		else if (e.getActionCommand().equals(ActionConstants.VIEW_RULES_ACTION)) {
			getMainTabbedPane().setSelectedComponent(getAccessRulesPane());
		}
		else {
			displayError(ResourceUtil.getString("application.erroroccurred"));
		}

		refreshTabNames();
	}

	/**
	 * Add Access Rule action handler. Displays AddAccessRule dialog.
	 */
	private void addAccessRule() {
		getMainTabbedPane().setSelectedComponent(getAccessRulesPane());

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
				.getLastSelectedPathComponent();
		Object userObject = (node == null) ? null : node.getUserObject();
		Message message = new Message();
		JDialog dialog = new AddAccessRuleDialog(document, userObject, message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		refreshUserList(null);

		if (message.getState() == Message.SUCCESS) {
			AccessRule rule = (AccessRule) message.getUserObject();
			refreshAccessRuleTree(rule.getPath());
		}
		else {
			refreshAccessRuleTree(userObject);
		}

		updateTitle();
	}

	/**
	 * Add Group action handler. Displays AddGroup dialog.
	 */
	private void addGroup() {
		getMainTabbedPane().setSelectedComponent(getGroupsPane());

		Message message = new Message();
		JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_ADD_GROUP, message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		if (message.getState() == Message.SUCCESS) {
			refreshGroupList((Group) message.getUserObject());
		}

		updateTitle();
	}

	/**
	 * Add Project Access Rules action handler. Displays AddProjectAccessRules dialog.
	 */
	private void addProjectAccessRules() {
		getMainTabbedPane().setSelectedComponent(getAccessRulesPane());

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
				.getLastSelectedPathComponent();
		Object userObject = (node == null) ? null : node.getUserObject();
		Message message = new Message();
		JDialog dialog = new AddProjectAccessRulesDialog(document, userObject, message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		refreshUserList(null);

		if (message.getState() == Message.SUCCESS) {
			AccessRule rule = (AccessRule) message.getUserObject();
			refreshAccessRuleTree(rule.getPath());
		}
		else {
			refreshAccessRuleTree(userObject);
		}

		updateTitle();
	}

	/**
	 * Add/Remove Members action handler. Displays AddRemoveMembers dialog.
	 */
	private void addRemoveMembers() {
		Object[] selectedItems = getGroupsPane().getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		}
		else {
			Group selectedGroup = null;

			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();

				JDialog dialog = new AddRemoveMembersDialog(document, (Group) selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);

				if (message.getState() == Message.SUCCESS) {
					selectedGroup = (Group) message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedGroup = (Group) selectedItems[i];
				}

				// Don't edit any other groups if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshUserDetails();
			refreshGroupList(selectedGroup);
			refreshAccessRuleTree(null);
		}

		updateTitle();
	}

	/**
	 * Adds the absolute path of a file to the recent files list and persists it to Preferences.
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
		menuBar.refreshRecentFiles(fileStack);
	}

	/**
	 * Adds a TransferHanlder to the container.
	 * 
	 * @param container Container to which a transfer handler is added
	 */
	private void addTransferHandler(Container container) {
		if (container instanceof JComponent) {
			((JComponent) container).setTransferHandler(fileTransferHandler);
		}

		if (container.getComponentCount() > 0) {
			for (Component child : container.getComponents()) {
				if (child instanceof Container) {
					addTransferHandler((Container) child);
				}
			}
		}
	}

	/**
	 * Add User action handler. Displays AddUser dialog.
	 */
	private void addUser() {
		getMainTabbedPane().setSelectedComponent(getUsersPane());

		Message message = new Message();
		JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_ADD_USER, message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		if (message.getState() == Message.SUCCESS) {
			refreshUserList((User) message.getUserObject());
		}

		updateTitle();
	}

	/**
	 * Change the font to specified font style.
	 * 
	 * @param newFontStyle New font style
	 */
	private void changeFont(String newFontStyle) {
		if (newFontStyle != null) {
			UserPreferences.setUserFontStyle(newFontStyle);
		}

		getAccessRulesPane().getAccessRulesTable().setFont(UserPreferences.getUserFont());
		getAccessRulesPane().getAccessRulesTree().setFont(UserPreferences.getUserFont());
		getGroupsPane().getGroupAccessRulesTable().setFont(UserPreferences.getUserFont());
		getGroupsPane().getGroupList().setFont(UserPreferences.getUserFont());
		getGroupsPane().getGroupMemberList().setFont(UserPreferences.getUserFont());
		getUsersPane().getUserAccessRulesTable().setFont(UserPreferences.getUserFont());
		getUsersPane().getUserGroupList().setFont(UserPreferences.getUserFont());
		getUsersPane().getUserList().setFont(UserPreferences.getUserFont());
	}

	/**
	 * Change Membership action handler. Displays ChangeMembership dialog.
	 */
	private void changeMembership() {
		Object[] selectedItems = getUsersPane().getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nouserselected"));
		}
		else {
			User selectedUser = null;

			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				JDialog dialog = new ChangeMembershipDialog(document, (User) selectedItems[i], message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);

				if (message.getState() == Message.SUCCESS) {
					selectedUser = (User) message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedUser = (User) selectedItems[i];
				}

				// Don't edit any other users if Cancel was clicked
				if (message.getState() == Message.CANCEL) {
					break;
				}
			}

			refreshUserList(selectedUser);
			refreshGroupDetails();
			refreshAccessRuleTree(null);
		}

		updateTitle();
	}

	/**
	 * Prompts user if there is any data that is not going to be saved.
	 */
	private boolean checkForUnsaveableData() {
		String validateMessage = document.validateDocument();

		if (validateMessage != null) {
			int response = showWarnConfirmDialog(validateMessage);

			return response == JOptionPane.YES_OPTION;
		}

		return true;
	}

	/**
	 * Prompts user to save current file if there are any unsaved changes.
	 */
	private void checkForUnsavedChanges() {
		if (document.hasUnsavedChanges()) {
			int response = showWarnConfirmDialog(ResourceUtil.getString("application.unsavedchanges"));

			if (response == JOptionPane.YES_OPTION) {
				fileSave();
			}
		}
	}

	/**
	 * Clears all files from the recent files list.
	 */
	private void clearRecentFiles() {
		UserPreferences.clearRecentFiles();

		menuBar.getRecentFilesMenu().removeAll();
	}

	/**
	 * Clone group action handler.
	 */
	private void cloneGroup() {
		Object[] selectedItems = getGroupsPane().getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		}
		else {
			Group selectedGroup = null;

			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();

				JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_CLONE_GROUP, (Group) selectedItems[i],
						message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);

				if (message.getState() == Message.SUCCESS) {
					selectedGroup = (Group) message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedGroup = (Group) selectedItems[i];
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
	 * Clone user action handler.
	 */
	private void cloneUser() {
		Object[] selectedItems = getUsersPane().getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		}
		else {
			User selectedUser = null;

			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_CLONE_USER, (User) selectedItems[i],
						message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);

				if (message.getState() == Message.SUCCESS) {
					selectedUser = (User) message.getUserObject();
				}
				else if (message.getUserObject() == null) {
					selectedUser = (User) selectedItems[i];
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
	 * Delete access rule handler.
	 */
	private void deleteAccessRule() {
		if (getAccessRulesPane().getAccessRulesTable().getSelectedRowCount() < 1) {
			displayWarning(ResourceUtil.getString("mainframe.warnming.noaccessrule"));
		}
		else {
			int choice = showConfirmDialog("mainframe.deleteaccessrule.prompt", "mainframe.deleteaccessrule.title");

			if (choice == JOptionPane.YES_OPTION) {
				try {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
							.getLastSelectedPathComponent();

					if (node == null) {
						return;
					}

					Object userObject = node.getUserObject();
					DefaultTableModel tableModel = (DefaultTableModel) getAccessRulesPane().getAccessRulesTable()
							.getModel();
					int selectedRow = getAccessRulesPane().getAccessRulesTable().getSelectedRow();

					if (userObject instanceof Repository) {
						Repository repository = (Repository) userObject;
						Path path = (Path) tableModel.getValueAt(selectedRow, 0);
						Object object = tableModel.getValueAt(selectedRow, 1);

						if (object instanceof Group) {
							document.deleteAccessRule(repository.getName(), path.getPath(), (Group) object, null);
						}
						else if (object instanceof User) {
							document.deleteAccessRule(repository.getName(), path.getPath(), null, (User) object);
						}
					}
					else if (userObject instanceof Path) {
						Path path = (Path) userObject;
						Object object = tableModel.getValueAt(selectedRow, 0);

						if (object instanceof Group) {
							document.deleteAccessRule(path.getRepository().getName(), path.getPath(), (Group) object,
									null);
						}
						else if (object instanceof User) {
							document.deleteAccessRule(path.getRepository().getName(), path.getPath(), null,
									(User) object);
						}
					}
					else {
						Repository repository = (Repository) tableModel.getValueAt(selectedRow, 0);
						Path path = (Path) tableModel.getValueAt(selectedRow, 1);
						Object object = tableModel.getValueAt(selectedRow, 2);
						String repositoryName = (repository == null) ? null : repository.getName();

						if (object instanceof Group) {
							document.deleteAccessRule(repositoryName, path.getPath(), (Group) object, null);
						}
						else if (object instanceof User) {
							document.deleteAccessRule(repositoryName, path.getPath(), null, (User) object);
						}
					}

					refreshUserDetails();
					refreshGroupDetails();
					refreshAccessRuleTree(null);
				}
				catch (AppException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errordeletingaccessrule"));
				}
			}
		}

		updateTitle();
	}

	/**
	 * Delete group action handler.
	 */
	private void deleteGroup() {
		if (getGroupsPane().getGroupList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		}
		else {
			Object[] values = getGroupsPane().getGroupList().getSelectedValues();
			int choice;

			if (values.length == 1) {
				choice = showConfirmDialog("mainframe.deletegroup.prompt", "mainframe.deletegroup.title");
			}
			else {
				choice = showConfirmDialog("mainframe.deletegroups.prompt", "mainframe.deletegroups.title");
			}

			if (choice == JOptionPane.YES_OPTION) {
				try {
					document.deleteGroups(values);
				}
				catch (AppException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errordeletinggroup"));
				}

				refreshGroupList(null);
				refreshAccessRuleTree(null);
			}
		}

		updateTitle();
	}

	/**
	 * Delete path action handler.
	 */
	private void deletePath() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
				.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object userObject = node.getUserObject();

		if (userObject instanceof Path) {
			int choice = showConfirmDialog("mainframe.deletepath.prompt", "mainframe.deletepath.title");

			if (choice == JOptionPane.YES_OPTION) {
				try {
					document.deletePath((Path) userObject);
				}
				catch (AppException ae) {
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
	 * Delete repository action handler.
	 */
	private void deleteRepository() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
				.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object userObject = node.getUserObject();

		if (userObject instanceof Repository) {
			int choice = showConfirmDialog("mainframe.deleterepository.prompt", "mainframe.deleterepository.title");

			if (choice == JOptionPane.YES_OPTION) {
				try {
					document.deleteRepository((Repository) userObject);
				}
				catch (AppException ae) {
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
	 * Delete user action handler.
	 */
	private void deleteUser() {
		if (getUsersPane().getUserList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nouserselected"));
		}
		else {
			Object[] values = getUsersPane().getUserList().getSelectedValues();
			int choice;

			if (values.length == 1) {
				choice = showConfirmDialog("mainframe.deleteuser.prompt", "mainframe.deleteuser.title");
			}
			else {
				choice = showConfirmDialog("mainframe.deleteusers.prompt", "mainframe.deleteusers.title");
			}

			if (choice == JOptionPane.YES_OPTION) {
				try {
					document.deleteUsers(values);
				}
				catch (AppException ae) {
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
	 * Displays specified group in the Groups pane.
	 * 
	 * @param group Group to be displayed
	 */
	private void displayGroup(Object group) {
		if (group == null) {
			return;
		}

		getMainTabbedPane().setSelectedComponent(getGroupsPane());
		getGroupsPane().getGroupList().setSelectedValue(group, true);
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

		getMainTabbedPane().setSelectedComponent(getUsersPane());
		getUsersPane().getUserList().setSelectedValue(user, true);
		refreshUserDetails();
	}

	private void disposeWindowsFrames() {
		for (Window window : Window.getWindows()) {
			window.dispose();
		}

		for (Frame frame : Frame.getFrames()) {
			frame.dispose();
		}
	}

	/**
	 * Edit access rule handler.
	 */
	private void editAccessRule() {
		if (getAccessRulesPane().getAccessRulesTable().getSelectedRowCount() < 1) {
			displayWarning(ResourceUtil.getString("mainframe.warning.noaccessruleselected"));
		}
		else {
			try {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
						.getLastSelectedPathComponent();

				if (node == null) {
					return;
				}

				JTable accessRulesTable = getAccessRulesPane().getAccessRulesTable();
				Object userObject = node.getUserObject();
				DefaultTableModel tableModel = (DefaultTableModel) accessRulesTable.getModel();
				int selectedRow = accessRulesTable.convertRowIndexToModel(accessRulesTable.getSelectedRow());
				AccessRule accessRule = null;

				if (userObject instanceof Repository) {
					Repository repository = (Repository) userObject;
					Path path = (Path) tableModel.getValueAt(selectedRow, 0);
					Object object = tableModel.getValueAt(selectedRow, 1);

					if (object instanceof Group) {
						accessRule = document.findGroupAccessRule(repository, path.getPath(), (Group) object);
					}
					else if (object instanceof User) {
						accessRule = document.findUserAccessRule(repository, path.getPath(), (User) object);
					}
				}
				else if (userObject instanceof Path) {
					Path path = (Path) userObject;
					Object object = tableModel.getValueAt(selectedRow, 0);

					if (object instanceof Group) {
						accessRule = document.findGroupAccessRule(path.getRepository(), path.getPath(), (Group) object);
					}
					else if (object instanceof User) {
						accessRule = document.findUserAccessRule(path.getRepository(), path.getPath(), (User) object);
					}
				}
				else {
					Repository repository = (Repository) tableModel.getValueAt(selectedRow, 0);
					Path path = (Path) tableModel.getValueAt(selectedRow, 1);
					Object object = tableModel.getValueAt(selectedRow, 2);

					if (object instanceof Group) {
						accessRule = document.findGroupAccessRule(repository, path.getPath(), (Group) object);
					}
					else if (object instanceof User) {
						accessRule = document.findUserAccessRule(repository, path.getPath(), (User) object);
					}
				}

				if (accessRule == null) {
					displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessruleforedit"));
				}
				else {
					Message message = new Message();
					Path path = accessRule.getPath();

					JDialog dialog = new EditAccessRuleDialog(document, accessRule, message);
					DialogUtil.center(this, dialog);
					dialog.setVisible(true);

					if (message.getState() == Message.SUCCESS) {
						document.setUnsavedChanges();
						refreshUserDetails();
						refreshGroupDetails();

						if (accessRule.getPath().equals(path)) {
							refreshAccessRuleTree(path);
						}
						else {
							refreshAccessRuleTree(null);
						}
					}
				}
			}
			catch (AppException ae) {
				displayError(ResourceUtil.getString("mainframe.error.erroreditingaccessrule"));
			}
		}

		updateTitle();
	}

	/**
	 * Edit path action handler.
	 */
	private void editPath() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
				.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object userObject = node.getUserObject();

		if (userObject instanceof Path) {
			Message message = new Message();

			JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_EDIT_PATH, (Path) userObject, message);
			DialogUtil.center(this, dialog);
			dialog.setVisible(true);

			if (message.getState() == Message.SUCCESS) {
				document.setUnsavedChanges();
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(message.getUserObject());
			}
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
	 * New file action handler.
	 */
	private void fileNew() {
		checkForUnsavedChanges();

		document.initialize();
		updateTitle();
		refreshUserList(null);
		refreshUserDetails();
		refreshGroupList(null);
		refreshGroupDetails();
		refreshAccessRuleTree(null);
		getAccessRulesPane().getEditTreeItemButton().setEnabled(false);
		getAccessRulesPane().getDeleteTreeItemButton().setEnabled(false);
		getMainTabbedPane().setSelectedComponent(getUsersPane());
	}

	/**
	 * File open action handler.
	 */
	private void fileOpen() {
		final JFileChooser fcOpen = new JFileChooser();

		checkForUnsavedChanges();

		fcOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);

		File directory = (document.getFile() == null) ? null : document.getFile().getParentFile();

		fcOpen.setSelectedFile(directory);

		int returnVal = fcOpen.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fcOpen.getSelectedFile();
				document = new FileParser().parse(file);
				getUsersPane().getUserList().setListData(document.getUserObjects());
				getGroupsPane().getGroupList().setListData(document.getGroupObjects());

				document.setFile(file);

				refreshAccessRuleTree(null);

				document.resetUnsavedChangesFlag();
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
	public void fileOpen(File file) {
		checkForUnsavedChanges();

		try {
			document = new FileParser().parse(file);
			getUsersPane().getUserList().setListData(document.getUserObjects());
			getGroupsPane().getGroupList().setListData(document.getGroupObjects());

			document.setFile(file);

			refreshAccessRuleTree(null);

			document.resetUnsavedChangesFlag();
			updateTitle();

			addToRecentFiles(file.getAbsolutePath());
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
	 * File print action handler.
	 */
	private void filePrint() {
		/*
		 * Get the representation of the current printer and the current print job.
		 */
		PrinterJob printerJob = PrinterJob.getPrinterJob();

		/*
		 * Build a book containing pairs of page painters (Printables) and PageFormats. This example has a single page
		 * containing text.
		 */
		Book book = new Book();
		book.append(new Printer(), new PageFormat());

		/*
		 * Set the object to be printed (the Book) into the PrinterJob. Doing this before bringing up the print dialog
		 * allows the print dialog to correctly display the page range to be printed and to dissallow any print settings
		 * not appropriate for the pages to be printed.
		 */
		printerJob.setPageable(book);

		/*
		 * Show the print dialog to the user. This is an optional step and need not be done if the application wants to
		 * perform 'quiet' printing. If the user cancels the print dialog then false is returned. If true is returned we
		 * go ahead and print.
		 */
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {
			try {
				printerJob.print();
			}
			catch (PrinterException exception) {
				System.err.println(ResourceUtil.getString("mainframe.error.errorprinting"));
			}
		}
	}

	/**
	 * File save action handler.
	 */
	private void fileSave() {
		if (!checkForUnsaveableData()) {
			return;
		}

		if (document.getFile() == null) {
			final JFileChooser fcSave = new JFileChooser();

			fcSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fcSave.setDialogType(JFileChooser.SAVE_DIALOG);

			int returnVal = fcSave.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					File file = fcSave.getSelectedFile();

					new FileGenerator(document).generate(file, UserPreferences.getMultipleLineGroupDefinitions());

					document.setFile(file);

					document.resetUnsavedChangesFlag();
					updateTitle();
					addToRecentFiles(document.getFile().getAbsolutePath());
				}
				catch (Exception e) {
					displayError(e.getMessage());
				}
			}
		}
		else {
			try {
				new FileGenerator(document).generate(document.getFile(), UserPreferences.getMultipleLineGroupDefinitions());

				document.resetUnsavedChangesFlag();
				updateTitle();
				addToRecentFiles(document.getFile().getAbsolutePath());
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

		if (!checkForUnsaveableData()) {
			return;
		}

		fcSaveAs.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fcSaveAs.setDialogType(JFileChooser.SAVE_DIALOG);
		fcSaveAs.setDialogTitle(ResourceUtil.getString("saveas.title"));

		int returnVal = fcSaveAs.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fcSaveAs.getSelectedFile();

				new FileGenerator(document).generate(file, UserPreferences.getMultipleLineGroupDefinitions());

				document.setFile(file);

				document.resetUnsavedChangesFlag();
				updateTitle();
				addToRecentFiles(document.getFile().getAbsolutePath());
			}
			catch (Exception e) {
				displayError(e.getMessage());
			}
		}
	}

	/**
	 * This method initializes accessRulesPane.
	 * 
	 * @return AccessRulesPane
	 */
	private AccessRulesPane getAccessRulesPane() {
		if (accessRulesPane == null) {
			accessRulesPane = new AccessRulesPane(this, this, this, this);
		}

		return accessRulesPane;
	}

	/**
	 * This method initializes actionToolBar.
	 * 
	 * @return MainFrameToolBar
	 */
	private MainFrameToolBar getActionToolBar() {
		if (actionToolBar == null) {
			actionToolBar = new MainFrameToolBar(this);
		}

		return actionToolBar;
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
					ResourceUtil.getString("mainframe.accessrulestable.level") };
		}

		return groupAccessRulesColumnNames;
	}

	/**
	 * This method initializes groupsPane.
	 * 
	 * @return GroupsPane
	 */
	private GroupsPane getGroupsPane() {
		if (groupsPane == null) {
			groupsPane = new GroupsPane(this, this, this, this);
		}

		return groupsPane;
	}

	/**
	 * This method initializes groupsPopupMenu.
	 * 
	 * @return GroupsPopupMenu
	 */
	private GroupsPopupMenu getGroupsPopupMenu() {
		if (groupsPopupMenu == null) {
			groupsPopupMenu = new GroupsPopupMenu(this);

			// Add listener to the list.
			MouseListener popupListener = new PopupListener(groupsPopupMenu);
			getGroupsPane().getGroupList().addMouseListener(popupListener);
		}

		return groupsPopupMenu;
	}

	public HelpSet getHelpSet(String helpsetfile) {
		HelpSet hs = null;
		ClassLoader cl = this.getClass().getClassLoader();
		try {
			URL hsURL = HelpSet.findHelpSet(cl, helpsetfile);
			hs = new HelpSet(null, hsURL);
		}
		catch (Exception ee) {
			System.out.println("HelpSet: " + ee.getMessage());
			System.out.println("HelpSet: " + helpsetfile + " not found");
		}
		return hs;
	}

	/**
	 * This method initializes contentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel(new BorderLayout());
			contentPane.add(getToolbarPanel(), BorderLayout.NORTH);
			contentPane.add(getMainTabbedPane(), BorderLayout.CENTER);
			contentPane.add(getStatusPanel(), BorderLayout.SOUTH);
		}

		return contentPane;
	}

	/**
	 * This method initializes menuBar.
	 * 
	 * @return MainFrameMenuBar
	 */
	private MainFrameMenuBar getMainFrameMenuBar() {
		if (menuBar == null) {
			menuBar = new MainFrameMenuBar(this);
			menuBar.refreshRecentFiles(fileStack);
		}

		return menuBar;
	}

	/**
	 * This method initializes mainTabbedPane.
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getMainTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			tabbedPane.addTab(ResourceUtil.getString("mainframe.tabs.users"), ResourceUtil.fullSizeUserIcon,
					getUsersPane());
			tabbedPane.addTab(ResourceUtil.getString("mainframe.tabs.groups"), ResourceUtil.fullSizeGroupIcon,
					getGroupsPane());
			tabbedPane.addTab(ResourceUtil.getString("mainframe.tabs.accessrules"),
					ResourceUtil.fullSizeAccessRuleIcon, getAccessRulesPane());

			tabbedPane.setFont(GuiConstants.FONT_PLAIN);
		}

		return tabbedPane;
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
	 * This method initializes repositoryAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getRepositoryAccessRulesColumnNames() {
		if (repositoryAccessRulesColumnNames == null) {
			repositoryAccessRulesColumnNames = new String[] {
					ResourceUtil.getString("mainframe.accessrulestable.path"),
					ResourceUtil.getString("mainframe.accessrulestable.usergroup"),
					ResourceUtil.getString("mainframe.accessrulestable.level") };
		}

		return repositoryAccessRulesColumnNames;
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
					ResourceUtil.getString("mainframe.accessrulestable.level") };
		}

		return serverAccessRulesColumnNames;
	}

	private JLabel getStatusLabel() {
		if (statusLabel == null) {
			statusLabel = new JLabel();
			statusLabel.setFont(GuiConstants.FONT_PLAIN);
		}

		return statusLabel;
	}

	/**
	 * This method initializes statusPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getStatusPanel() {
		if (statusPanel == null) {
			statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			statusPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			statusPanel.add(getStatusLabel(), null);
		}
		return statusPanel;
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
	 * This method initializes userAccessRulesColumnNames.
	 * 
	 * @return Object[]
	 */
	private Object[] getUserAccessRulesColumnNames() {
		if (userAccessRulesColumnNames == null) {
			userAccessRulesColumnNames = new String[] {
					ResourceUtil.getString("mainframe.accessrulestable.repository"),
					ResourceUtil.getString("mainframe.accessrulestable.path"),
					ResourceUtil.getString("mainframe.accessrulestable.level") };
		}

		return userAccessRulesColumnNames;
	}

	/**
	 * This method initializes usersPane.
	 * 
	 * @return UsersPane
	 */
	private UsersPane getUsersPane() {
		if (usersPane == null) {
			usersPane = new UsersPane(this, this, this, this);
		}

		return usersPane;
	}

	/**
	 * This method initializes usersPopupMenu.
	 * 
	 * @return UsersPopupMenu
	 */
	private UsersPopupMenu getUsersPopupMenu() {
		if (usersPopupMenu == null) {
			usersPopupMenu = new UsersPopupMenu(this);

			// Add listener to the user list
			MouseListener popupListener = new PopupListener(usersPopupMenu);
			getUsersPane().getUserList().addMouseListener(popupListener);
		}

		return usersPopupMenu;
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

		loadHelp();
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

	/**
	 * KeyPressed event handler.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_DELETE) {
			Component component = event.getComponent();

			if (component == getUsersPane().getUserList()) {
				deleteUser();
			}
			else if (component == getGroupsPane().getGroupList()) {
				deleteGroup();
			}
			else if (component == getGroupsPane().getGroupMemberList()) {
				removeMembers();
			}
			else if (component == getUsersPane().getUserGroupList()) {
				removeFromGroups();
			}
			// else if (component == getAccessRulesTree()) {
			//				
			// }

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
	 * KeyTyped event handler. Not used.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyTyped(KeyEvent event) {
		// Unused
	}

	private void loadHelp() {
		try {
			String urlString = Constants.PATH_RESOURCE_HELP_DIR + "/" + ResourceUtil.getString("application.language")
					+ "/suafe.hs";

			ClassLoader cl = MainFrame.class.getClassLoader();
			URL url = cl.getResource(urlString);
			HelpSet mainHS = new HelpSet(cl, url);

			mainHB = mainHS.createHelpBroker("overview");

			mainHB.enableHelpKey(getRootPane(), "main-screen", mainHS, "javax.help.MainWindow", null);
			mainHB.enableHelpOnButton(getMainFrameMenuBar().getHelpMenuItem(), "overview", mainHS);
			mainHB.enableHelpOnButton(getMainFrameMenuBar().getLicenseMenuItem(), "license", mainHS);

			setIconImageForHelp(mainHB, ResourceUtil.serverImage);
		}
		catch (HelpSetException e) {
			displayError(ResourceUtil.getString("application.error.helpnotloaded"));
		}
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

		getGroupsPane().loadUserPreferences();
		getUsersPane().loadUserPreferences();
		getAccessRulesPane().loadUserPreferences();

		menuBar.getOpenLastFileMenuItem().setSelected(UserPreferences.getOpenLastFile());

		this.setVisible(true);
	}

	/**
	 * MouseClick event handler.
	 * 
	 * @param event MouseEvent object
	 */
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (event.getSource() == getUsersPane().getUserList()) {
				renameUser();
			}
			else if (event.getSource() == getGroupsPane().getGroupList()) {
				renameGroup();
			}
			else if (event.getSource() == getUsersPane().getUserGroupList()) {
				displayGroup(getUsersPane().getUserGroupList().getSelectedValue());
			}
			else if (event.getSource() == getGroupsPane().getGroupMemberList()) {
				if (getGroupsPane().getGroupMemberList().getSelectedValue() instanceof Group) {
					displayGroup(getGroupsPane().getGroupMemberList().getSelectedValue());
				}
				else {
					displayUser(getGroupsPane().getGroupMemberList().getSelectedValue());
				}
			}
			else if (event.getSource() == getAccessRulesPane().getAccessRulesTable()) {
				editAccessRule();
			}
		}
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

	private void openLastEditedFileSettingChange() {
		boolean selected = menuBar.getOpenLastFileMenuItem().isSelected();

		UserPreferences.setOpenLastFile(selected);
	}
	
	private void multilineGroupDefinitionsSettingChange() {
		boolean selected = menuBar.getMultiLineGroupDefinitionsMenuItem().isSelected();

		UserPreferences.setMultipleLineGroupDefinitions(selected);
	}

	/**
	 * Preview action handler.
	 */
	private void preview() {
		if (document.isEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.documentisempty"));
		}
		else {
			new Thread() {
				public void run() {
					try {
						JFrame frame = new ViewerFrame(ResourceUtil.getString("preview.title"), new FileGenerator(
								document).generate(UserPreferences.getMultipleLineGroupDefinitions()), Constants.MIME_TEXT);
						frame.setVisible(true);
					}
					catch (AppException e) {
						displayError(e.getMessage());
					}
				}
			}.start();
		}
	}

	/**
	 * Refresh access rules tree. Selects specified object.
	 * 
	 * @param selectedObject Object to select.
	 */
	private void refreshAccessRuleTree(Object selectedObject) {
		TreePath treePath = null;
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(ResourceUtil.getString("application.server"));
		accessRulesTreeModel = new DefaultTreeModel(node);

		List<Repository> repositoryList = document.getRepositories();

		Collections.sort(repositoryList);

		for (Repository repository : repositoryList) {
			DefaultMutableTreeNode repositoryNode = new DefaultMutableTreeNode(repository);

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

		getAccessRulesPane().getAccessRulesTree().setModel(accessRulesTreeModel);
		getAccessRulesPane().getAccessRulesTree().setSelectionPath(treePath);
		getAccessRulesPane().getAccessRulesTree().scrollPathToVisible(treePath);

		if (selectedObject instanceof Repository) {
			refreshRepositoryAccessRules((Repository) selectedObject);
		}
		else if (selectedObject instanceof Path) {
			refreshPathAccessRules((Path) selectedObject);
		}
		else {
			getAccessRulesPane().getAccessRulesTree().setSelectionPath(
					new TreePath(getAccessRulesPane().getAccessRulesTree().getModel().getRoot()));
			refreshServerAccessRules();
		}

		toggleAccessRulesActions(false);
	}

	/**
	 * Refresh group details for selected group.
	 */
	private void refreshGroupDetails() {
		Group group = (Group) getGroupsPane().getGroupList().getSelectedValue();

		try {
			getGroupsPane().getGroupMemberList().setModel(new DefaultListModel());

			if (!getGroupsPane().getGroupList().isSelectionEmpty()) {
				Object[] listData = document.getGroupMemberObjects(group);

				if (listData != null) {
					getGroupsPane().getGroupMemberList().setListData(listData);
				}
			}
		}
		catch (AppException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadinggroupmembers"));
		}

		toggleGroupActions(getGroupsPane().getGroupList().isSelectionEmpty() == false);

		DefaultTableModel model = new NonEditableTableModel();

		try {
			model.setDataVector(document.getGroupAccessRules(group), getGroupAccessRulesColumnNames());
		}
		catch (AppException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforgroup"));
		}

		getGroupsPane().getGroupAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getGroupsPane().getGroupAccessRulesTable(), true);
	}

	/**
	 * Refreshes group list with current groups. List of selected groups is used to reselect the groups after refresh.
	 * 
	 * @param selectedGroup Group currently selected.
	 */
	private void refreshGroupList(Group selectedGroup) {
		getGroupsPane().getGroupList().setListData(document.getGroupObjects());
		toggleGroupActions(getGroupsPane().getGroupList().isSelectionEmpty() == false);

		if (selectedGroup != null) {
			getGroupsPane().getGroupList().setSelectedValue(selectedGroup, true);
		}

		refreshGroupDetails();
	}

	/**
	 * Refresh access rules table for the selected path.
	 * 
	 * @param path Selected path.
	 */
	private void refreshPathAccessRules(Path path) {
		DefaultTableModel model = new NonEditableTableModel();

		try {
			model.setDataVector(document.getPathAccessRules(path), getPathAccessRulesColumnNames());
		}
		catch (AppException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforpath"));
		}

		getAccessRulesPane().getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesPane().getAccessRulesTable(), true);
	}

	/**
	 * Refresh access rules table for the selected repository.
	 * 
	 * @param repository Selected repository.
	 */
	private void refreshRepositoryAccessRules(Repository repository) {
		DefaultTableModel model = new NonEditableTableModel();

		try {
			model.setDataVector(document.getRepositoryAccessRules(repository), getRepositoryAccessRulesColumnNames());
		}
		catch (AppException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforrepository"));
		}

		getAccessRulesPane().getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesPane().getAccessRulesTable(), true);
		getAccessRulesPane().getEditAccessRuleButton().setEnabled(false);
		getAccessRulesPane().getDeleteAccessRuleButton().setEnabled(false);
	}

	/**
	 * Refresh access rules table for the entire server.
	 */
	private void refreshServerAccessRules() {
		DefaultTableModel model = new NonEditableTableModel();

		try {
			model.setDataVector(document.getServerAccessRules(), getServerAccessRulesColumnNames());
		}
		catch (AppException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforserver"));
		}

		getAccessRulesPane().getAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getAccessRulesPane().getAccessRulesTable(), true);
	}

	/**
	 * Refreshes main tabbed panel text with current object counts.
	 */
	private void refreshTabNames() {
		String title = "<html><span style=\"font-size: 1.2em;\"><strong>Users</strong>&nbsp;&nbsp;&nbsp;("
				+ document.getUsers().size() + ")</span></html>";
		getMainTabbedPane().setTitleAt(0, title);

		title = "<html><span style=\"font-size: 1.2em;\"><strong>Groups</strong>&nbsp;&nbsp;&nbsp;("
				+ document.getGroups().size() + ")</span></html>";
		getMainTabbedPane().setTitleAt(1, title);

		title = "<html><span style=\"font-size: 1.2em;\"><strong>Access Rules</strong>&nbsp;&nbsp;&nbsp;("
				+ document.getAccessRules().size() + ")</span></html>";
		getMainTabbedPane().setTitleAt(2, title);
	}

	/**
	 * Refresh user details for selected user.
	 */
	private void refreshUserDetails() {
		User user = (User) getUsersPane().getUserList().getSelectedValue();

		try {
			getUsersPane().getUserGroupList().setModel(new DefaultListModel());

			if (!getUsersPane().getUserList().isSelectionEmpty()) {
				Object[] listData = (Object[]) document.getUserGroupObjects(user);

				if (listData != null) {
					getUsersPane().getUserGroupList().setListData(listData);
				}
			}
		}
		catch (AppException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingusers"));
		}

		boolean enabled = getUsersPane().getUserList().isSelectionEmpty() == false;
		toggleUserActions(enabled, (user == null) ? enabled : enabled && !user.isAllUsers());

		DefaultTableModel model = new NonEditableTableModel();

		try {
			model.setDataVector(document.getUserAccessRuleObjects(user), getUserAccessRulesColumnNames());
		}
		catch (AppException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforuser"));
		}

		getUsersPane().getUserAccessRulesTable().setModel(model);
		AutofitTableColumns.autoResizeTable(getUsersPane().getUserAccessRulesTable(), true);
	}

	/**
	 * Refreshes user list with current users. List of selected users is used to reselect the users after refresh.
	 * 
	 * @param selectedUser User currently selected.
	 */
	private void refreshUserList(User selectedUser) {
		getUsersPane().getUserList().setListData(document.getUserObjects());

		boolean enabled = getUsersPane().getUserList().isSelectionEmpty() == false;
		toggleUserActions(enabled, (selectedUser == null) ? enabled : enabled && !selectedUser.isAllUsers());

		if (selectedUser != null) {
			getUsersPane().getUserList().setSelectedValue(selectedUser, true);
		}

		refreshUserDetails();
	}

	/**
	 * File open action handler.
	 */
	private void reload() {
		if (document.hasUnsavedChanges()) {
			int response = showWarnConfirmDialog(ResourceUtil.getString("application.unsavedchangesbeforereload"));

			if (response == JOptionPane.NO_OPTION) {
				return;
			}
		}

		try {
			File file = document.getFile();
			document = new FileParser().parse(file);
			getUsersPane().getUserList().setListData(document.getUserObjects());
			getGroupsPane().getGroupList().setListData(document.getGroupObjects());

			document.setFile(file);

			refreshAccessRuleTree(null);

			document.resetUnsavedChangesFlag();
			updateTitle();

			refreshTabNames();
		}
		catch (Exception e) {
			displayError(e.getMessage());
		}
	}

	/**
	 * Remove user from selected groups in user group list.
	 */
	private void removeFromGroups() {
		if (getUsersPane().getUserGroupList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		}
		else {
			Object[] values = getUsersPane().getUserGroupList().getSelectedValues();
			User user = (User) getUsersPane().getUserList().getSelectedValue();
			int choice;

			if (values.length == 1) {
				choice = showConfirmDialog("mainframe.removefromgroup.prompt", "mainframe.removefromgroup.title");
			}
			else {
				choice = showConfirmDialog("mainframe.removefromgroups.prompt", "mainframe.removefromgroups.title");
			}

			if (choice == JOptionPane.YES_OPTION) {
				try {
					document.removeFromGroups(user, values);
				}
				catch (AppException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errorremovingmember"));
				}

				refreshUserList((User) getUsersPane().getUserList().getSelectedValue());
				refreshGroupList((Group) getGroupsPane().getGroupList().getSelectedValue());
				refreshAccessRuleTree(null);
			}
		}

		updateTitle();
	}

	/**
	 * Remove selected users/groups in the group member list.
	 */
	private void removeMembers() {
		if (getGroupsPane().getGroupMemberList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nomemberselected"));
		}
		else {
			Object[] values = getGroupsPane().getGroupMemberList().getSelectedValues();
			Group group = (Group) getGroupsPane().getGroupList().getSelectedValue();
			int choice;

			if (values.length == 1) {
				choice = showConfirmDialog("mainframe.removemember.prompt", "mainframe.removemember.title");
			}
			else {
				choice = showConfirmDialog("mainframe.removemembers.prompt", "mainframe.removemembers.title");
			}

			if (choice == JOptionPane.YES_OPTION) {
				try {
					document.removeGroupMembers(group, values);
				}
				catch (AppException ae) {
					displayError(ResourceUtil.getString("mainframe.error.errorremovingmember"));
				}

				refreshUserList((User) getUsersPane().getUserList().getSelectedValue());
				refreshGroupList((Group) getGroupsPane().getGroupList().getSelectedValue());
				refreshAccessRuleTree(null);
			}
		}

		updateTitle();
	}

	/**
	 * Rename group action handler.
	 */
	private void renameGroup() {
		Object[] selectedItems = getGroupsPane().getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		}
		else {
			Group selectedGroup = null;

			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();

				JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_RENAME_GROUP, (Group) selectedItems[i],
						message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);

				if (message.getState() == Message.SUCCESS) {
					selectedGroup = (Group) message.getUserObject();
					document.setUnsavedChanges();
				}
				else if (message.getUserObject() == null) {
					selectedGroup = (Group) selectedItems[i];
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
	 * Edit repository action handler.
	 */
	private void renameRepository() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
				.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object userObject = node.getUserObject();

		if (userObject instanceof Repository) {
			Message message = new Message();

			JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_RENAME_REPOSITORY, (Repository) userObject,
					message);
			DialogUtil.center(this, dialog);
			dialog.setVisible(true);

			if (message.getState() == Message.SUCCESS) {
				document.setUnsavedChanges();
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(message.getUserObject());
			}
		}

		updateTitle();
	}

	/**
	 * Edit user action handler.
	 */
	private void renameUser() {
		Object[] selectedItems = getUsersPane().getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		}
		else {
			User selectedUser = null;

			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_RENAME_USER, (User) selectedItems[i],
						message);
				DialogUtil.center(this, dialog);
				dialog.setVisible(true);

				if (message.getState() == Message.SUCCESS) {
					selectedUser = (User) message.getUserObject();
					document.setUnsavedChanges();
				}
				else if (message.getUserObject() == null) {
					selectedUser = (User) selectedItems[i];
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
	 * Clears all saved user settings.
	 */
	private void resetSettings() {
		UserPreferences.resetSettings();
		loadUserPreferences();
	}

	/**
	 * Save current user settings.
	 */
	private void saveUserPreferences() {
		UserPreferences.setWindowState(getExtendedState());

		this.setExtendedState(JFrame.NORMAL);
		UserPreferences.setWindowSize(getSize());

		UserPreferences.setWindowLocation(getLocation());
		UserPreferences
				.setGroupDetailsDividerLocation(getGroupsPane().getGroupDetailsSplitPanel().getDividerLocation());
		UserPreferences.setGroupsPaneDividerLocation(getGroupsPane().getDividerLocation());

		UserPreferences.setUserDetailsDividerLocation(getUsersPane().getUserDetailsSplitPanel().getDividerLocation());
		UserPreferences.setUsersPaneDividerLocation(getUsersPane().getDividerLocation());

		UserPreferences.setRulesPaneDividerLocation(getAccessRulesPane().getDividerLocation());
	}

	private void setIconImageForHelp(HelpBroker hb, Image image) {
		// Hack the help frame to set its icon
		WindowPresentation wp = ((DefaultHelpBroker) hb).getWindowPresentation();
		wp.getLocation();
		JFrame jfrmHelp = (JFrame) wp.getHelpWindow();
		jfrmHelp.setIconImage(image);
		jfrmHelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Statistics Report action handler.
	 */
	private void statisticsReport() {
		if (document.isEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.documentisempty"));
		}
		else {
			new Thread() {
				public void run() {
					try {
						GenericReport report = new StatisticsReport(document);
						JFrame frame = new ViewerFrame(ResourceUtil.getString("statisticsreport.title"), report
								.generate(), Constants.MIME_HTML);
						frame.setVisible(true);
					}
					catch (AppException e) {
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
		if (document.isEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.documentisempty"));
		}
		else {
			new Thread() {
				public void run() {
					try {
						GenericReport report = new SummaryReport(document);
						JFrame frame = new ViewerFrame(ResourceUtil.getString("summaryreport.title"),
								report.generate(), Constants.MIME_HTML);
						frame.setVisible(true);
					}
					catch (AppException ae) {
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
	 * Toggles access rules actions state.
	 * 
	 * @param enabled If true access rules actions enabled, otherwise disabled.
	 */
	private void toggleAccessRulesActions(boolean enabled) {
		getAccessRulesPane().getEditAccessRuleButton().setEnabled(enabled);
		getAccessRulesPane().getDeleteAccessRuleButton().setEnabled(enabled);
	}

	/**
	 * Toggles group actions state.
	 * 
	 * @param enabled If true group actions are enabled, otherwise disabled.
	 */
	private void toggleGroupActions(boolean enabled) {
		getGroupsPane().getCloneGroupButton().setEnabled(enabled);
		getGroupsPane().getRenameGroupButton().setEnabled(enabled);
		getGroupsPane().getDeleteGroupButton().setEnabled(enabled);
		getGroupsPane().getAddRemoveMembersButton().setEnabled(enabled);

		groupsPopupMenu.getRenameGroupPopupMenuItem().setEnabled(enabled);
		groupsPopupMenu.getDeleteGroupPopupMenuItem().setEnabled(enabled);
		groupsPopupMenu.getAddRemoveMembersPopupMenuItem().setEnabled(enabled);
		groupsPopupMenu.getCloneGroupPopupMenuItem().setEnabled(enabled);
	}

	/**
	 * Toggles user actions state.
	 * 
	 * @param enabled If true user actions are enabled, otherwise disabled.
	 * @param changeMembershipEnabled If true change membership button enabled, otherwise disabled.
	 */
	private void toggleUserActions(boolean enabled, boolean changeMembershipEnabled) {
		getUsersPane().getCloneUserButton().setEnabled(enabled);
		getUsersPane().getRenameUserButton().setEnabled(enabled);
		getUsersPane().getDeleteUserButton().setEnabled(enabled);
		getUsersPane().getChangeMembershipButton().setEnabled(changeMembershipEnabled);

		usersPopupMenu.getRenameUserPopupMenuItem().setEnabled(enabled);
		usersPopupMenu.getDeleteUserPopupMenuItem().setEnabled(enabled);
		usersPopupMenu.getChangeMembershipPopupMenuItem().setEnabled(changeMembershipEnabled);
		usersPopupMenu.getCloneUserPopupMenuItem().setEnabled(enabled);
	}

	/**
	 * Updates title with current file name and state. For unknown files the name Untitled is used. Files that have
	 * unsaved changes are marked with a leading asterisk (*).
	 */
	private void updateTitle() {
		final String untitled = ResourceUtil.getString("application.untitled");
		String filename = "";
		String fullPath = "";

		// Add an asterisk if the file has changed.
		if (document.hasUnsavedChanges()) {
			filename = "*";
		}

		// Add Untitled for new files or the file name for existing files
		if (document.getFile() == null) {
			filename += untitled;
			fullPath = untitled;
		}
		else {
			filename += document.getFile().getName();
			fullPath = document.getFile().getAbsolutePath();
		}

		this.setTitle(ResourceUtil.getFormattedString("application.name", filename));

		getStatusLabel().setText(fullPath);
	}

	/**
	 * ValueChange event listenter. Refresh user/group details and access rules when new items selected.
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == getUsersPane().getUserList()) {
			refreshUserDetails();
		}
		else if (e.getSource() == getGroupsPane().getGroupList()) {
			refreshGroupDetails();
		}
		else if (e.getSource() == getAccessRulesPane().getAccessRulesTable().getSelectionModel()
				&& getAccessRulesPane().getAccessRulesTable().getRowSelectionAllowed()) {
			getAccessRulesPane().getEditAccessRuleButton().setEnabled(true);
			getAccessRulesPane().getDeleteAccessRuleButton().setEnabled(true);
		}
	}

	/**
	 * ValueChanged event handler for access rules tree
	 * 
	 * @param event TreeSelectionEven object
	 */
	public void valueChanged(TreeSelectionEvent event) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getAccessRulesPane().getAccessRulesTree()
				.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object userObject = node.getUserObject();
		JButton editButton = getAccessRulesPane().getEditTreeItemButton();
		JButton deleteButton = getAccessRulesPane().getDeleteTreeItemButton();

		if (userObject instanceof Repository) {
			refreshRepositoryAccessRules((Repository) userObject);

			editButton.setEnabled(true);
			editButton.setText(ResourceUtil.getString("button.rename"));
			deleteButton.setEnabled(true);

			editButton.setIcon(ResourceUtil.repositoryEditIcon);
			deleteButton.setIcon(ResourceUtil.repositoryDeleteIcon);

			editButton.setActionCommand(ActionConstants.RENAME_REPOSITORY_ACTION);
			deleteButton.setActionCommand(ActionConstants.DELETE_REPOSITORY_ACTION);

			editButton.setToolTipText(ResourceUtil.getString("mainframe.button.renamerepository.tooltip"));
			deleteButton.setToolTipText(ResourceUtil.getString("mainframe.button.deleterepository.tooltip"));
		}
		else if (userObject instanceof Path) {
			refreshPathAccessRules((Path) userObject);

			editButton.setEnabled(true);
			editButton.setText(ResourceUtil.getString("button.edit"));
			deleteButton.setEnabled(true);

			editButton.setIcon(ResourceUtil.pathEditIcon);
			deleteButton.setIcon(ResourceUtil.pathDeleteIcon);

			editButton.setActionCommand(ActionConstants.EDIT_PATH_ACTION);
			deleteButton.setActionCommand(ActionConstants.DELETE_PATH_ACTION);

			editButton.setToolTipText(ResourceUtil.getString("mainframe.button.editpath.tooltip"));
			deleteButton.setToolTipText(ResourceUtil.getString("mainframe.button.deletepath.tooltip"));
		}
		else {
			refreshServerAccessRules();

			editButton.setEnabled(false);
			editButton.setText(ResourceUtil.getString("button.edit"));
			deleteButton.setEnabled(false);

			editButton.setIcon(null);
			deleteButton.setIcon(null);

			editButton.setActionCommand(null);
			deleteButton.setActionCommand(null);

			editButton.setToolTipText(null);
			deleteButton.setToolTipText(null);
		}
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
	 * WindowClosing event handler. Prompts user to save the current file if there are any unsaved changes.
	 */
	public void windowClosing(WindowEvent event) {
		checkForUnsavedChanges();
		saveUserPreferences();
		disposeWindowsFrames();
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
}
