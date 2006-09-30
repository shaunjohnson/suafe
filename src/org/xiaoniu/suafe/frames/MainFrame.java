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

package org.xiaoniu.suafe.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
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

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.FileGenerator;
import org.xiaoniu.suafe.FileParser;
import org.xiaoniu.suafe.Printer;
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
import org.xiaoniu.suafe.dialogs.PreviewDialog;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.models.NonEditableTableModel;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.renderers.MyTableCellRenderer;
import org.xiaoniu.suafe.renderers.MyTreeCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Main Suafe application window.
 * 
 * @author Shaun Johnson
 */
public class MainFrame extends BaseFrame implements ActionListener, KeyListener,
		ListSelectionListener, MouseListener, TreeSelectionListener {
	
	private static final long serialVersionUID = -4378074679449146788L;
	private ImageIcon pathEditIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/PathEdit.gif"));
	private ImageIcon pathDeleteIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/PathDelete.gif"));
	private ImageIcon repositoryEditIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/RepositoryEdit.gif"));
	private ImageIcon repositoryDeleteIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/RepositoryDelete.gif"));
	private ImageIcon previewIcon = new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/Preview.gif"));
	
	private JPanel jContentPane = null;  

	private JTabbedPane mainTabbedPane = null;  

	private JSplitPane usersSplitPane = null;

	private JList userList = null;

	private JSplitPane groupsSplitPane = null;

	private JPanel userDetailsPanel = null;  

	private JMenuBar jJMenuBar = null;  

	private JMenu fileMenu = null;  

	private JMenuItem newMenuItem = null;

	private JMenuItem openMenuItem = null;  

	private JMenuItem saveMenuItem = null;

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

	private JMenuItem newUserMenuItem = null;

	private JMenuItem newGroupMenuItem = null;

	private JMenuItem newAccessRuleMenuItem = null;  

	private JScrollPane userListScrollPane = null;

	private JScrollPane jScrollPane2 = null;

	private JToolBar actionToolBar = null;  

	private JButton addUserToolbarButton = null;

	private JButton addGroupToolbarButton = null;  

	private JButton previewToolbarButton = null;

	private JButton addAccessRuleButton = null;  

	private JPanel usersPaneActionsPanel = null;

	private JButton editUserButton = null;  

	private JButton addUserButton = null;  

	private JButton deleteUserButton = null;

	private JButton changeMembershipButton = null;

	private JPanel groupsPaneActionsPanel = null;  

	private JButton addGroupButton = null;  

	private JButton editGroupButton = null;  

	private JButton deleteGroupButton = null;

	private JButton addRemoveMembersButton = null;

	private JPopupMenu usersPopupMenu = null; 

	private JMenuItem addUserMenuItem = null;  

	private JMenuItem editUserMenuItem = null;

	private JMenuItem deleteUserMenuItem = null;  

	private JMenuItem changeMembershipMenuItem = null;

	private JPopupMenu groupsPopupMenu = null;  

	private JMenuItem addGroupMenuItem = null;

	private JMenuItem editGroupMenuItem = null;

	private JMenuItem deleteGroupMenuItem = null;

	private JMenuItem addRemoveMembersMenuItem = null;  

	private JButton addAccessRuleButton1 = null;

	private JButton editAccessRuleButton = null;

	private JButton deleteAccessRuleButton = null;  

	private JSplitPane jPanel4 = null;  

	private JList userGroupList = null;  

	private JTable userAccessRulesTable = null;

	private JScrollPane userAccessRulesScrollPane = null;  

	private JScrollPane userGroupListScrollPane = null;

	private JPanel jPanel5 = null;  

	private JSplitPane jPanel6 = null;  

	private JTable groupAccessRulesTable = null;  

	private JScrollPane groupAccessRulesScrollPane = null;  

	private JSplitPane accessRulesSplitPane = null;

	private JScrollPane accessRulesScrollPane = null;

	private JTree accessRulesTree = null;

	private TreeModel accessRuleTreeModel = null;

	private JPanel jPanel8 = null;  

	private JPanel accessRulesPaneActionsPanel = null;

	private JTable accessRulesTable = null;

	private JPanel toolbarPanel = null;  

	private JMenuItem printMenuItem = null;

	private JScrollPane accessRulesScrollPane1 = null;
	private JPanel jPanel1 = null;  
	private JLabel jLabel5 = null;  
	private JPanel jPanel2 = null;  
	private JLabel jLabel3 = null;  
	private JPanel jPanel3 = null;  
	private JLabel jLabel20 = null;
	private JLabel jLabel22 = null;
	private JScrollPane jScrollPane = null;
	private JList groupMemberList = null;
	private JPanel jPanel10 = null;
	private JLabel jLabel = null;
	private JPanel jPanel7 = null;
	private JPanel jPanel9 = null;
	private JButton editTreeItemButton = null;
	private JButton deleteTreeItemButton = null;
	private JPanel jPanel = null;
	private JPanel jPanel11 = null;
	private JPanel jPanel13 = null;
	private JPanel jPanel14 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel4 = null;
	private JMenuItem cloneUserMenuItem = null;
	private JMenuItem cloneGroupMenuItem = null;
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/xiaoniu/suafe/resources/Server16.gif")));
		this.setJMenuBar(getJJMenuBar());
		getUsersPopupMenu();
		getGroupsPopupMenu();
		this.setSize(800, 700);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceUtil.getFormattedString("application.name",
				"Untitled"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.center();
		this.addKeyListener(this);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getMainTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getToolbarPanel(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();
			mainTabbedPane.addTab(ResourceUtil
					.getString("mainframe.tabs.users"), new ImageIcon(
					getClass().getResource("/org/xiaoniu/suafe/resources/PlainPeople.gif")),
					getUsersSplitPane(), null);
			mainTabbedPane.addTab(ResourceUtil
					.getString("mainframe.tabs.groups"), new ImageIcon(
					getClass().getResource("/org/xiaoniu/suafe/resources/MorePeople.gif")),
					getGroupsSplitPane(), null);
			mainTabbedPane.addTab(ResourceUtil
					.getString("mainframe.tabs.accessrules"), new ImageIcon(
					getClass().getResource("/org/xiaoniu/suafe/resources/Reversed.gif")),
					getAccessRulesSplitPane(), null);
			mainTabbedPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		}
		return mainTabbedPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getUsersSplitPane() {
		if (usersSplitPane == null) {
			usersSplitPane = new JSplitPane();
			usersSplitPane.setRightComponent(getUserDetailsPanel());
			usersSplitPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			usersSplitPane.setLeftComponent(getJPanel13());
		}
		return usersSplitPane;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getUserList() {
		if (userList == null) {
			userList = new JList();
			userList.addListSelectionListener(this);
			userList.addMouseListener(this);
			userList.setCellRenderer(new MyListCellRenderer());
			userList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return userList;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getGroupsSplitPane() {
		if (groupsSplitPane == null) {
			groupsSplitPane = new JSplitPane();
			groupsSplitPane.setRightComponent(getGroupDetailsPanel());
			groupsSplitPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			groupsSplitPane.setLeftComponent(getJPanel14());
		}
		return groupsSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getUserDetailsPanel() {
		if (userDetailsPanel == null) {
			userDetailsPanel = new JPanel();
			userDetailsPanel.setLayout(new BorderLayout());
			userDetailsPanel.add(getJPanel4(), java.awt.BorderLayout.CENTER);
		}
		return userDetailsPanel;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getActionMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText(ResourceUtil.getString("menu.file"));
			fileMenu.add(getNewMenuItem());
			fileMenu.add(getOpenMenuItem());
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getSaveAsMenuItem());
			fileMenu.add(new JSeparator());
			fileMenu.add(getPrintMenuItem());
			fileMenu.add(new JSeparator());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewMenuItem() {
		if (newMenuItem == null) {
			newMenuItem = new JMenuItem();
			newMenuItem.setText(ResourceUtil.getString("menu.file.new"));
			newMenuItem.setActionCommand("NewFile");
			newMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/New16.gif")));
			newMenuItem.addActionListener(this);
			newMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.new.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return newMenuItem;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText(ResourceUtil.getString("menu.file.open"));
			openMenuItem.setActionCommand("OpenFile");
			openMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Open16.gif")));
			openMenuItem.addActionListener(this);
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.open.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return openMenuItem;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText(ResourceUtil.getString("menu.file.save"));
			saveMenuItem.setActionCommand("SaveFile");
			saveMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Save16.gif")));
			saveMenuItem.addActionListener(this);
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.save.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return saveMenuItem;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.setText(ResourceUtil.getString("menu.file.saveas"));
			saveAsMenuItem.setActionCommand("SaveFileAs");
			saveAsMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/SaveAs16.gif")));
			saveAsMenuItem.addActionListener(this);
			saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.saveas.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + InputEvent.SHIFT_MASK, false));
		}
		return saveAsMenuItem;
	}

	/**
	 * This method initializes jMenu1
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText(ResourceUtil.getString("menu.help"));
			helpMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			helpMenu.add(getHelpMenuItem());
			helpMenu.add(getLicenseMenuItem());
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem4
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getHelpMenuItem() {
		if (helpMenuItem == null) {
			helpMenuItem = new JMenuItem();
			helpMenuItem.setText(ResourceUtil.getString("menu.help.help"));
			helpMenuItem.setActionCommand("Help");
			helpMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Help16.gif")));
			helpMenuItem.addActionListener(this);
			helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}
		return helpMenuItem;
	}

	/**
	 * This method initializes jMenuItem5
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText(ResourceUtil.getString("menu.help.about"));
			aboutMenuItem.setActionCommand("About");
			aboutMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/About16.gif")));
			aboutMenuItem.addActionListener(this);
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes jMenuItem6
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText(ResourceUtil.getString("menu.file.exit"));
			exitMenuItem.setActionCommand("Exit");
			exitMenuItem.addActionListener(this);
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.exit.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes fileToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getActionToolBar() {
		if (actionToolBar == null) {
			actionToolBar = new JToolBar();
			actionToolBar.setFloatable(true);
			actionToolBar.add(getAddUserToolbarButton());
			actionToolBar.add(getAddGroupToolbarButton());
			actionToolBar.add(getAddAccessRuleButton());
			actionToolBar.add(getPreviewToolbarButton());
		}
		return actionToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddUserToolbarButton() {
		if (addUserToolbarButton == null) {
			addUserToolbarButton = new JButton();
			addUserToolbarButton.setText(ResourceUtil
					.getString("mainframe.button.adduser"));
			addUserToolbarButton.setActionCommand("AddUser");
			addUserToolbarButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserAdd.gif")));
			addUserToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
			addUserToolbarButton.addActionListener(this);
		}
		return addUserToolbarButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddGroupToolbarButton() {
		if (addGroupToolbarButton == null) {
			addGroupToolbarButton = new JButton();
			addGroupToolbarButton.setText(ResourceUtil
					.getString("mainframe.button.addgroup"));
			addGroupToolbarButton.setActionCommand("AddGroup");
			addGroupToolbarButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupAdd.gif")));
			addGroupToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));
			addGroupToolbarButton.addActionListener(this);
		}
		return addGroupToolbarButton;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPreviewToolbarButton() {
		if (previewToolbarButton == null) {
			previewToolbarButton = new JButton();
			previewToolbarButton.setText(ResourceUtil
					.getString("mainframe.button.preview"));
			previewToolbarButton.setActionCommand("Preview");
			previewToolbarButton
					.setIcon(previewIcon);
			previewToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.preview.tooltip"));
			previewToolbarButton.addActionListener(this);
		}
		return previewToolbarButton;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddAccessRuleButton() {
		if (addAccessRuleButton == null) {
			addAccessRuleButton = new JButton();
			addAccessRuleButton.setText(ResourceUtil
					.getString("mainframe.button.addaccessrule"));
			addAccessRuleButton.setActionCommand("AddAccessRule");
			addAccessRuleButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/AccessRuleAdd.gif")));
			addAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.addaccessrule.tooltip"));
			addAccessRuleButton.addActionListener(this);
		}
		return addAccessRuleButton;
	}

	private void fileNew() {
		Document.initialize();
		refreshUserList(null);
		refreshUserDetails();
		refreshGroupList(null);
		refreshGroupDetails();
		refreshAccessRuleTree(null);
		getEditTreeItemButton().setEnabled(false);
		getDeleteTreeItemButton().setEnabled(false);

		getMainTabbedPane().setSelectedComponent(getUsersSplitPane());

		this.setTitle(ResourceUtil.getFormattedString("application.name",
				"Untitled"));
	}

	private void fileOpen() {
		final JFileChooser fcOpen = new JFileChooser();

		fcOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);

		File directory = (Document.getFile() == null) ? null : Document
				.getFile().getParentFile();

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

				this.setTitle(ResourceUtil.getFormattedString(
						"application.name", file.getName()));
			} catch (Exception e) {
				displayError(e.getMessage());
			}
		}
	}

	/**
	 * @param message
	 */
	private void displayError(String message) {
		JOptionPane.showMessageDialog(this, message, ResourceUtil
				.getString("application.error"), JOptionPane.ERROR_MESSAGE);
	}

	private void displayWarning(String message) {
		JOptionPane.showMessageDialog(this, message, ResourceUtil
				.getString("application.warning"), JOptionPane.WARNING_MESSAGE);
	}

	private void fileSave() {
		if (Document.getFile() == null) {
			final JFileChooser fcSave = new JFileChooser();

			fcSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fcSave.setDialogType(JFileChooser.SAVE_DIALOG);

			int returnVal = fcSave.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					File file = fcSave.getSelectedFile();

					FileGenerator.generate(file);

					this.setTitle(ResourceUtil.getFormattedString(
							"application.name", file.getName()));
				} catch (Exception e) {
					displayError(e.getMessage());
				}
			}
		} else {
			try {
				FileGenerator.generate(Document.getFile());
			} catch (Exception e) {
				displayError(e.getMessage());
			}
		}
	}

	private void fileSaveAs() {
		final JFileChooser fcSaveAs = new JFileChooser();

		fcSaveAs.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fcSaveAs.setDialogType(JFileChooser.SAVE_DIALOG);
		fcSaveAs.setDialogTitle(ResourceUtil.getString("saveas.title"));

		int returnVal = fcSaveAs.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fcSaveAs.getSelectedFile();

				FileGenerator.generate(file);

				Document.setFile(file);

				this.setTitle(ResourceUtil.getFormattedString(
						"application.name", file.getName()));
			} catch (Exception e) {
				displayError(e.getMessage());
			}
		}
	}
	
	private void toggleUserActions(boolean enabled, boolean changeMembershipEnabled) {
		getEditUserButton().setEnabled(enabled);
		getDeleteUserButton().setEnabled(enabled);
		getChangeMembershipButton().setEnabled(changeMembershipEnabled);
		
		getEditUserMenuItem().setEnabled(enabled);
		getDeleteUserMenuItem().setEnabled(enabled);
		getChangeMembershipMenuItem().setEnabled(changeMembershipEnabled);
		
		getCloneUserMenuItem().setEnabled(enabled);
	}
	
	private void toggleGroupActions(boolean enabled) {
		getEditGroupButton().setEnabled(enabled);
		getDeleteGroupButton().setEnabled(enabled);
		getAddRemoveMembersButton().setEnabled(enabled);
		
		getEditGroupMenuItem().setEnabled(enabled);
		getDeleteGroupMenuItem().setEnabled(enabled);
		getAddRemoveMembersMenuItem().setEnabled(enabled);
		
		getCloneGroupMenuItem().setEnabled(enabled);
	}

	private void toggleAccessRulesActions(boolean enabled) {
		getEditAccessRuleButton().setEnabled(enabled);
		getDeleteAccessRuleButton().setEnabled(enabled);
	}
	
	private void refreshUserList(User selectedUser) {
		getUserList().setListData(Document.getUserObjects());
		
		boolean enabled = getUserList().isSelectionEmpty() == false;
		toggleUserActions(enabled, (selectedUser == null) ? enabled : enabled && !selectedUser.isAllUsers());
		
		if (selectedUser != null) {
			getUserList().setSelectedValue(selectedUser, true);
		}
		
		refreshUserDetails();
	}

	private void refreshGroupList(Group selectedGroup) {
		getGroupList().setListData(Document.getGroupObjects());
		toggleGroupActions(getGroupList().isSelectionEmpty() == false);
		
		if (selectedGroup != null) {
			getGroupList().setSelectedValue(selectedGroup, true);
		}
		
		refreshGroupDetails();
	}

	private void addUser() {
		getMainTabbedPane().setSelectedComponent(getUsersSplitPane());

		Message message = new Message();
		JDialog dialog = new AddUserDialog(message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);
		
		if (message.getState() == Message.SUCCESS) {
			refreshUserList((User)message.getUserObject());
		}
	}
	
	private void addGroup() {
		getMainTabbedPane().setSelectedComponent(getGroupsSplitPane());

		Message message = new Message();
		JDialog dialog = new AddGroupDialog(message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		if (message.getState() == Message.SUCCESS) {
			refreshGroupList((Group)message.getUserObject());
		}
	}

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
	}

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
					// selectedGroup = (Group)message.getUserObject();
					
					refreshUserDetails();
					refreshGroupDetails();
					refreshAccessRuleTree(null);
				}
				else if (message.getUserObject() == null) {
					// selectedGroup = (Group)selectedItems[i];
				}
			}
			catch (ApplicationException ae) {
				displayError(ResourceUtil.getString("mainframe.error.erroreditingaccessrule"));
			}
			
		}
	}
	
	private void deleteAccessRule() {
		if (getAccessRulesTable().getSelectedRowCount() < 1) {
			displayWarning(ResourceUtil.getString("mainframe.warnming.noaccessrule"));
		} else {
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
						
						if (object instanceof Group) {
							Document.deleteAccessRule(repository.getName(), path.getPath(), (Group)object, null);
						}
						else if (object instanceof User) {
							Document.deleteAccessRule(repository.getName(), path.getPath(), null, (User)object);
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
	}
	
	private void helpAbout() {
		JDialog dialog = new AboutDialog();
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);
	}

	private void helpLicense() {
		JDialog dialog = new LicenseDialog();
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);
	}

	private void preview() {
		
		if (Document.isEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.documentisempty"));
		}
		else {
			JDialog dialog = new PreviewDialog();
			DialogUtil.center(this, dialog);
			dialog.setVisible(true);
		}
	}

	private void deleteUser() {
		if (getUserList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nouserselected"));
		} else {
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
	}

	private void editUser() {
		Object[] selectedItems = getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} else {
			User selectedUser = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				JDialog dialog = new EditUserDialog((User)selectedItems[i], message);
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
	}
	
	private void cloneUser() {
		Object[] selectedItems = getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} else {
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
	}
	
	private void cloneGroup() {
		Object[] selectedItems = getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} else {
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
	}
	
	private void changeMembership() {
		Object[] selectedItems = getUserList().getSelectedValues();

		if (selectedItems.length == 0) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nouserselected"));
		} else {
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
	}
	
	private void addRemoveMembers() {
		Object[] selectedItems = getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		} else {
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
	}

	private void deleteGroup() {
		if (getGroupList().isSelectionEmpty()) {
			displayWarning(ResourceUtil.getString("mainframe.warning.nogroupselected"));
		} else {
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
	}

	private void editGroup() {
		Object[] selectedItems = getGroupList().getSelectedValues();

		if (selectedItems.length == 0) {
			return;
		} else {
			Group selectedGroup = null;
			
			for (int i = 0; i < selectedItems.length; i++) {
				Message message = new Message();
				
				JDialog dialog = new EditGroupDialog((Group)selectedItems[i], message);
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
	}
	
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
	}
	
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
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(message.getUserObject());				
			}
		}		
	}

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
	}

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
				refreshUserDetails();
				refreshGroupDetails();
				refreshAccessRuleTree(message.getUserObject());
			}
		}
	}

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
	
	private void showHelp() {
		JFrame frame = new HelpFrame();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("NewFile")) {
			fileNew();
		} else if (e.getActionCommand().equals("OpenFile")) {
			fileOpen();
		} else if (e.getActionCommand().equals("SaveFile")) {
			fileSave();
		} else if (e.getActionCommand().equals("SaveFileAs")) {
			fileSaveAs();
		} else if (e.getActionCommand().equals("Print")) {
			filePrint();
		} else if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("Help")) {
			showHelp();
		} else if (e.getActionCommand().equals("License")) {
			helpLicense();
		} else if (e.getActionCommand().equals("About")) {
			helpAbout();
		} else if (e.getActionCommand().equals("Preview")) {
			preview();
		} else if (e.getActionCommand().equals("AddUser")) {
			addUser();
		} else if (e.getActionCommand().equals("EditUser")) {
			editUser();
		} else if (e.getActionCommand().equals("CloneUser")) {
			cloneUser();
		} else if (e.getActionCommand().equals("DeleteUser")) {
			deleteUser();
		} else if (e.getActionCommand().equals("ChangeMembership")) {
			changeMembership();
		} else if (e.getActionCommand().equals("AddGroup")) {
			addGroup();
		} else if (e.getActionCommand().equals("EditGroup")) {
			editGroup();
		} else if (e.getActionCommand().equals("CloneGroup")) {
			cloneGroup();
		} else if (e.getActionCommand().equals("DeleteGroup")) {
			deleteGroup();
		} else if (e.getActionCommand().equals("AddRemoveMembers")) {
			addRemoveMembers();
		} else if (e.getActionCommand().equals("EditPath")) {
			editPath();
		} else if (e.getActionCommand().equals("DeletePath")) {
			deletePath();
		} else if (e.getActionCommand().equals("EditRepository")) {
			editRepository();
		} else if (e.getActionCommand().equals("DeleteRepository")) {
			deleteRepository();
		} else if (e.getActionCommand().equals("AddAccessRule")) {
			addAccessRule();
		} else if (e.getActionCommand().equals("EditAccessRule")) {
			editAccessRule();
		} else if (e.getActionCommand().equals("DeleteAccessRule")) {
			deleteAccessRule();
		} else {
			displayError(ResourceUtil.getString("application.error"));
		}
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getGroupList() {
		if (groupList == null) {
			groupList = new JList();
			groupList.addListSelectionListener(this);
			groupList.addMouseListener(this);
			groupList.setCellRenderer(new MyListCellRenderer());
			groupList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return groupList;
	}

	/**
	 * This method initializes jMenuItem7
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getLicenseMenuItem() {
		if (licenseMenuItem == null) {
			licenseMenuItem = new JMenuItem();
			licenseMenuItem.setActionCommand("License");
			licenseMenuItem.setText(ResourceUtil
					.getString("menu.help.license"));
			licenseMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/History16.gif")));
			licenseMenuItem.addActionListener(this);
		}
		return licenseMenuItem;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getGroupDetailsPanel() {
		if (groupDetailsPanel == null) {
			groupDetailsPanel = new JPanel();
			groupDetailsPanel.setLayout(new BorderLayout());
			groupDetailsPanel.add(getJPanel6(), java.awt.BorderLayout.CENTER);
		}
		return groupDetailsPanel;
	}

	private Object[] getUserAccessRulesColumnNames() {
		if (userAccessRulesColumnNames == null) {
			userAccessRulesColumnNames = new String[3];
			userAccessRulesColumnNames[0] = ResourceUtil
					.getString("mainframe.accessrulestable.repository");
			userAccessRulesColumnNames[1] = ResourceUtil
					.getString("mainframe.accessrulestable.path");
			userAccessRulesColumnNames[2] = ResourceUtil
					.getString("mainframe.accessrulestable.level");
		}

		return userAccessRulesColumnNames;
	}
	
	private Object[] getGroupAccessRulesColumnNames() {
		if (groupAccessRulesColumnNames == null) {
			groupAccessRulesColumnNames = new String[3];
			groupAccessRulesColumnNames[0] = ResourceUtil
					.getString("mainframe.accessrulestable.repository");
			groupAccessRulesColumnNames[1] = ResourceUtil
					.getString("mainframe.accessrulestable.path");
			groupAccessRulesColumnNames[2] = ResourceUtil
					.getString("mainframe.accessrulestable.level");
		}

		return groupAccessRulesColumnNames;
	}
	
	private Object[] getRepositoryAccessRulesColumnNames() {
		if (repositoryAccessRulesColumnNames == null) {
			repositoryAccessRulesColumnNames = new String[3];
			repositoryAccessRulesColumnNames[0] = ResourceUtil
					.getString("mainframe.accessrulestable.path");
			repositoryAccessRulesColumnNames[1] = ResourceUtil
			.getString("mainframe.accessrulestable.usergroup");
			repositoryAccessRulesColumnNames[2] = ResourceUtil
					.getString("mainframe.accessrulestable.level");			
		}

		return repositoryAccessRulesColumnNames;
	}
	
	private Object[] getPathAccessRulesColumnNames() {
		if (pathAccessRulesColumnNames == null) {
			pathAccessRulesColumnNames = new String[2];
			pathAccessRulesColumnNames[0] = ResourceUtil
					.getString("mainframe.accessrulestable.usergroup");
			pathAccessRulesColumnNames[1] = ResourceUtil
					.getString("mainframe.accessrulestable.level");
		}

		return pathAccessRulesColumnNames;
	}
	
	private Object[] getServerAccessRulesColumnNames() {
		if (serverAccessRulesColumnNames == null) {
			serverAccessRulesColumnNames = new String[4];
			serverAccessRulesColumnNames[0] = ResourceUtil
					.getString("mainframe.accessrulestable.repository");
			serverAccessRulesColumnNames[1] = ResourceUtil
					.getString("mainframe.accessrulestable.path");
			serverAccessRulesColumnNames[2] = ResourceUtil
					.getString("mainframe.accessrulestable.usergroup");
			serverAccessRulesColumnNames[3] = ResourceUtil
					.getString("mainframe.accessrulestable.level");
		}

		return serverAccessRulesColumnNames;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getActionMenu() {
		if (actionMenu == null) {
			actionMenu = new JMenu();
			actionMenu.setText(ResourceUtil.getString("menu.action"));
			actionMenu.add(getNewUserMenuItem());
			actionMenu.add(getNewGroupMenuItem());
			actionMenu.add(getNewAccessRuleMenuItem());
		}
		return actionMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewUserMenuItem() {
		if (newUserMenuItem == null) {
			newUserMenuItem = new JMenuItem();
			newUserMenuItem.setText(ResourceUtil
					.getString("menu.action.adduser"));
			newUserMenuItem.setActionCommand("AddUser");
			newUserMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserAdd.gif")));
			newUserMenuItem.addActionListener(this);
			newUserMenuItem.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return newUserMenuItem;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewGroupMenuItem() {
		if (newGroupMenuItem == null) {
			newGroupMenuItem = new JMenuItem();
			newGroupMenuItem.setText(ResourceUtil
					.getString("menu.action.addgroup"));
			newGroupMenuItem.setActionCommand("AddGroup");
			newGroupMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupAdd.gif")));
			newGroupMenuItem.addActionListener(this);
			newGroupMenuItem.setAccelerator(KeyStroke.getKeyStroke('G', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return newGroupMenuItem;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewAccessRuleMenuItem() {
		if (newAccessRuleMenuItem == null) {
			newAccessRuleMenuItem = new JMenuItem();
			newAccessRuleMenuItem.setText(ResourceUtil
					.getString("menu.action.addaccessrule"));
			newAccessRuleMenuItem.setActionCommand("AddAccessRule");
			newAccessRuleMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/AccessRuleAdd.gif")));
			newAccessRuleMenuItem.addActionListener(this);
			newAccessRuleMenuItem.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return newAccessRuleMenuItem;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getUserListScrollPane() {
		if (userListScrollPane == null) {
			userListScrollPane = new JScrollPane();
			userListScrollPane.setViewportView(getUserList());
			userListScrollPane
					.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			userListScrollPane.setPreferredSize(new Dimension(250, 150));
		}
		return userListScrollPane;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getGroupList());
			jScrollPane2.setPreferredSize(new Dimension(250, 150));
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getUsersPaneActionsPanel() {
		if (usersPaneActionsPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			usersPaneActionsPanel = new JPanel();
			usersPaneActionsPanel.setLayout(flowLayout1);
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			usersPaneActionsPanel.add(getAddUserButton(), null);
			usersPaneActionsPanel.add(getEditUserButton(), null);
			usersPaneActionsPanel.add(getDeleteUserButton(), null);
		}
		return usersPaneActionsPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditUserButton() {
		if (editUserButton == null) {
			editUserButton = new JButton();
			editUserButton.setText(ResourceUtil.getString("button.edit"));
			editUserButton.setActionCommand("EditUser");
			editUserButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserEdit.gif")));
			editUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.edituser.tooltip"));
			editUserButton.setEnabled(false);
			editUserButton.addActionListener(this);
		}
		return editUserButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddUserButton() {
		if (addUserButton == null) {
			addUserButton = new JButton();
			addUserButton.setText(ResourceUtil.getString("button.add"));
			addUserButton.setActionCommand("AddUser");
			addUserButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserAdd.gif")));
			addUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
			addUserButton.addActionListener(this);
		}
		return addUserButton;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteUserButton() {
		if (deleteUserButton == null) {
			deleteUserButton = new JButton();
			deleteUserButton.setText(ResourceUtil.getString("button.delete"));
			deleteUserButton.setActionCommand("DeleteUser");
			deleteUserButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserDelete.gif")));
			deleteUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.deleteuser.tooltip"));
			deleteUserButton.setEnabled(false);
			deleteUserButton.addActionListener(this);
		}
		return deleteUserButton;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getChangeMembershipButton() {
		if (changeMembershipButton == null) {
			changeMembershipButton = new JButton();
			changeMembershipButton.setText(ResourceUtil.getString("mainframe.button.changemembership"));
			changeMembershipButton.setActionCommand("ChangeMembership");
			changeMembershipButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Preferences16.gif")));
			changeMembershipButton.setToolTipText(ResourceUtil.getString("mainframe.button.changemembership.tooltip"));
			changeMembershipButton.setEnabled(false);
			changeMembershipButton.addActionListener(this);
		}
		return changeMembershipButton;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getGroupsPaneActionsPanel() {
		if (groupsPaneActionsPanel == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			groupsPaneActionsPanel = new JPanel();
			groupsPaneActionsPanel.setLayout(flowLayout2);
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			groupsPaneActionsPanel.add(getAddGroupButton(), null);
			groupsPaneActionsPanel.add(getEditGroupButton(), null);
			groupsPaneActionsPanel.add(getDeleteGroupButton(), null);
		}
		return groupsPaneActionsPanel;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddGroupButton() {
		if (addGroupButton == null) {
			addGroupButton = new JButton();
			addGroupButton.setText(ResourceUtil.getString("button.add"));
			addGroupButton.setActionCommand("AddGroup");
			addGroupButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupAdd.gif")));
			addGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));
			addGroupButton.addActionListener(this);
		}
		return addGroupButton;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditGroupButton() {
		if (editGroupButton == null) {
			editGroupButton = new JButton();
			editGroupButton.setText(ResourceUtil.getString("button.edit"));
			editGroupButton.setActionCommand("EditGroup");
			editGroupButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupEdit.gif")));
			editGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.editgroup.tooltip"));
			editGroupButton.addActionListener(this);
			editGroupButton.setEnabled(false);
		}
		return editGroupButton;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteGroupButton() {
		if (deleteGroupButton == null) {
			deleteGroupButton = new JButton();
			deleteGroupButton.setText(ResourceUtil.getString("button.delete"));
			deleteGroupButton.setActionCommand("DeleteGroup");
			deleteGroupButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupDelete.gif")));
			deleteGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.deletegroup.tooltip"));
			deleteGroupButton.addActionListener(this);
			deleteGroupButton.setEnabled(false);
		}
		return deleteGroupButton;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddRemoveMembersButton() {
		if (addRemoveMembersButton == null) {
			addRemoveMembersButton = new JButton();
			addRemoveMembersButton.setText(ResourceUtil.getString("mainframe.button.addremovemembers"));
			addRemoveMembersButton.setActionCommand("AddRemoveMembers");
			addRemoveMembersButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Preferences16.gif")));
			addRemoveMembersButton.setToolTipText(ResourceUtil.getString("mainframe.button.addremovemembers.tooltip"));
			addRemoveMembersButton.addActionListener(this);
			addRemoveMembersButton.setEnabled(false);
		}
		return addRemoveMembersButton;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getUsersPopupMenu() {
		if (usersPopupMenu == null) {
			usersPopupMenu = new JPopupMenu();
			usersPopupMenu.add(getAddUserMenuItem());
			usersPopupMenu.add(getEditUserMenuItem());
			usersPopupMenu.add(getDeleteUserMenuItem());
			usersPopupMenu.add(getChangeMembershipMenuItem());

			usersPopupMenu.add(getCloneUserMenuItem());
			// Add listener to the text area so the popup menu can come up.
			MouseListener popupListener = new PopupListener(usersPopupMenu);
			getUserList().addMouseListener(popupListener);
		}
		return usersPopupMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddUserMenuItem() {
		if (addUserMenuItem == null) {
			addUserMenuItem = new JMenuItem();
			addUserMenuItem.setText(ResourceUtil.getString("button.add"));
			addUserMenuItem.setActionCommand("AddUser");
			addUserMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserAdd.gif")));
			addUserMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
			addUserMenuItem.addActionListener(this);
		}
		return addUserMenuItem;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getEditUserMenuItem() {
		if (editUserMenuItem == null) {
			editUserMenuItem = new JMenuItem();
			editUserMenuItem.setText(ResourceUtil.getString("button.edit"));
			editUserMenuItem.setActionCommand("EditUser");
			editUserMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserEdit.gif")));
			editUserMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.edituser.tooltip"));
			editUserMenuItem.addActionListener(this);
			editUserMenuItem.setEnabled(false);
		}
		return editUserMenuItem;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDeleteUserMenuItem() {
		if (deleteUserMenuItem == null) {
			deleteUserMenuItem = new JMenuItem();
			deleteUserMenuItem.setText(ResourceUtil.getString("button.delete"));
			deleteUserMenuItem.setActionCommand("DeleteUser");
			deleteUserMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/UserDelete.gif")));
			deleteUserMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deleteuser.tooltip"));
			deleteUserMenuItem.addActionListener(this);
			deleteUserMenuItem.setEnabled(false);
		}
		return deleteUserMenuItem;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getChangeMembershipMenuItem() {
		if (changeMembershipMenuItem == null) {
			changeMembershipMenuItem = new JMenuItem();
			changeMembershipMenuItem.setText(ResourceUtil.getString("mainframe.button.changemembership"));
			changeMembershipMenuItem.setActionCommand("ChangeMembership");
			changeMembershipMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Preferences16.gif")));
			changeMembershipMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.changemembership.tooltip"));
			changeMembershipMenuItem.addActionListener(this);
			changeMembershipMenuItem.setEnabled(false);
		}
		return changeMembershipMenuItem;
	}

	class PopupListener extends MouseAdapter {
		JPopupMenu popup;

		PopupListener(JPopupMenu popupMenu) {
			popup = popupMenu;
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			// && e.isShiftDown()
			if (e.isPopupTrigger()) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getGroupsPopupMenu() {
		if (groupsPopupMenu == null) {
			groupsPopupMenu = new JPopupMenu();
			groupsPopupMenu.add(getAddGroupMenuItem());
			groupsPopupMenu.add(getEditGroupMenuItem());
			groupsPopupMenu.add(getDeleteGroupMenuItem());
			groupsPopupMenu.add(getAddRemoveMembersMenuItem());

			groupsPopupMenu.add(getCloneGroupMenuItem());
			MouseListener popupListener = new PopupListener(groupsPopupMenu);
			getGroupList().addMouseListener(popupListener);
		}
		return groupsPopupMenu;
	}

	/**
	 * This method initializes jMenuItem4
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddGroupMenuItem() {
		if (addGroupMenuItem == null) {
			addGroupMenuItem = new JMenuItem();
			addGroupMenuItem.setText(ResourceUtil.getString("button.add"));
			addGroupMenuItem.setActionCommand("AddGroup");
			addGroupMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupAdd.gif")));
			addGroupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));
		}
		return addGroupMenuItem;
	}

	/**
	 * This method initializes jMenuItem5
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getEditGroupMenuItem() {
		if (editGroupMenuItem == null) {
			editGroupMenuItem = new JMenuItem();
			editGroupMenuItem.setText(ResourceUtil.getString("button.edit"));
			editGroupMenuItem.setActionCommand("EditGroup");
			editGroupMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupEdit.gif")));
			editGroupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.editgroup.tooltip"));
			editGroupMenuItem.addActionListener(this);
			editGroupMenuItem.setEnabled(false);
		}
		return editGroupMenuItem;
	}

	/**
	 * This method initializes jMenuItem6
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDeleteGroupMenuItem() {
		if (deleteGroupMenuItem == null) {
			deleteGroupMenuItem = new JMenuItem();
			deleteGroupMenuItem.setText(ResourceUtil.getString("button.delete"));
			deleteGroupMenuItem.setActionCommand("DeleteGroup");
			deleteGroupMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/GroupDelete.gif")));
			deleteGroupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deletegroup.tooltip"));
			deleteGroupMenuItem.addActionListener(this);
			deleteGroupMenuItem.setEnabled(false);
		}
		return deleteGroupMenuItem;
	}

	/**
	 * This method initializes jMenuItem7
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddRemoveMembersMenuItem() {
		if (addRemoveMembersMenuItem == null) {
			addRemoveMembersMenuItem = new JMenuItem();
			addRemoveMembersMenuItem.setText(ResourceUtil.getString("mainframe.button.addremovemembers"));
			addRemoveMembersMenuItem.setActionCommand("AddRemoveMembers");
			addRemoveMembersMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Preferences16.gif")));
			addRemoveMembersMenuItem
					.setToolTipText(ResourceUtil.getString("mainframe.button.addremovemembers.tooltip"));
			addRemoveMembersMenuItem.addActionListener(this);
			addRemoveMembersMenuItem.setEnabled(false);
		}
		return addRemoveMembersMenuItem;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddAccessRuleButton1() {
		if (addAccessRuleButton1 == null) {
			addAccessRuleButton1 = new JButton();
			addAccessRuleButton1.setText(ResourceUtil.getString("button.add"));
			addAccessRuleButton1.setActionCommand("AddAccessRule");
			addAccessRuleButton1
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/AccessRuleAdd.gif")));
			addAccessRuleButton1.setToolTipText(ResourceUtil.getString("mainframe.button.addaccessrule.tooltip"));
			addAccessRuleButton1.addActionListener(this);
		}
		return addAccessRuleButton1;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditAccessRuleButton() {
		if (editAccessRuleButton == null) {
			editAccessRuleButton = new JButton();
			editAccessRuleButton.setText(ResourceUtil.getString("button.edit"));
			editAccessRuleButton.setActionCommand("EditAccessRule");
			editAccessRuleButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/AccessRuleEdit.gif")));
			editAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.editaccessrule.tooltip"));
			editAccessRuleButton.addActionListener(this);
			editAccessRuleButton.setEnabled(false);
		}
		return editAccessRuleButton;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteAccessRuleButton() {
		if (deleteAccessRuleButton == null) {
			deleteAccessRuleButton = new JButton();
			deleteAccessRuleButton.setText(ResourceUtil.getString("button.delete"));
			deleteAccessRuleButton.setActionCommand("DeleteAccessRule");
			deleteAccessRuleButton
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/AccessRuleDelete.gif")));
			deleteAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.deleteaccessrule.tooltip"));
			deleteAccessRuleButton.addActionListener(this);
			deleteAccessRuleButton.setEnabled(false);
		}
		return deleteAccessRuleButton;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JSplitPane getJPanel4() {
		if (jPanel4 == null) {
			
			jPanel4 = new JSplitPane();
			jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 7, 0, 0));
			
			jPanel4.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jPanel4.setTopComponent(getJPanel10());
			jPanel4.setBottomComponent(getJPanel5());
			jPanel4.setOneTouchExpandable(true);
			jPanel4.setDividerLocation(200);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getUserGroupList() {
		if (userGroupList == null) {
			userGroupList = new JList();
			userGroupList.setFixedCellWidth(150);
			userGroupList.addMouseListener(this);
			userGroupList.setCellRenderer(new MyListCellRenderer());
			userGroupList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return userGroupList;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getUserAccessRulesTable() {
		if (userAccessRulesTable == null) {
			userAccessRulesTable = new JTable();
			userAccessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			userAccessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);	
			userAccessRulesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		}
		return userAccessRulesTable;
	}

	/**
	 * This method initializes jScrollPane
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
	 * This method initializes jScrollPane4
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
	}
	
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
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			return;
		}

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
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 0, 0, 0));
			jLabel20 = new JLabel();
			jLabel20.setText(ResourceUtil.getString("mainframe.tabs.accessrules"));
			jPanel5.add(jLabel20, java.awt.BorderLayout.NORTH);
			jPanel5.add(getUserAccessRulesScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */	
	private JSplitPane getJPanel6() {
		if (jPanel6 == null) {
			
			jPanel6 = new JSplitPane();
			jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 7, 0, 0));
			
			jPanel6.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jPanel6.setTopComponent(getJPanel3());
			jPanel6.setBottomComponent(getJPanel2());
			jPanel6.setOneTouchExpandable(true);
			jPanel6.setDividerLocation(200);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getGroupAccessRulesTable() {
		if (groupAccessRulesTable == null) {
			groupAccessRulesTable = new JTable();
			groupAccessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			groupAccessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);	
			groupAccessRulesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		}
		return groupAccessRulesTable;
	}

	/**
	 * This method initializes jScrollPane5
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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getAccessRulesSplitPane() {
		if (accessRulesSplitPane == null) {
			accessRulesSplitPane = new JSplitPane();
			accessRulesSplitPane.setRightComponent(getJPanel8());
			accessRulesSplitPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			accessRulesSplitPane.setLeftComponent(getJPanel7());
		}
		return accessRulesSplitPane;
	}

	/**
	 * This method initializes jScrollPane7
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getAccessRulesScrollPane() {
		if (accessRulesScrollPane == null) {
			accessRulesScrollPane = new JScrollPane();
			accessRulesScrollPane.setViewportView(getAccessRulesTree());
			accessRulesScrollPane.setPreferredSize(new Dimension(250, 150));
		}
		return accessRulesScrollPane;
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getAccessRulesTree() {
		if (accessRulesTree == null) {
			accessRulesTree = new JTree(new DefaultMutableTreeNode(ResourceUtil.getString("application.server")));
			accessRulesTree.setShowsRootHandles(true);
			accessRulesTree.addTreeSelectionListener(this);
			accessRulesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			accessRulesTree.setCellRenderer(new MyTreeCellRenderer());
			accessRulesTree.setExpandsSelectedPaths(true);
			
		}
		return accessRulesTree;
	}
	
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
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getAccessRulesPaneActionsPanel(), java.awt.BorderLayout.SOUTH);
			jPanel8.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAccessRulesPaneActionsPanel() {
		if (accessRulesPaneActionsPanel == null) {
			FlowLayout flowLayout11 = new FlowLayout();
			accessRulesPaneActionsPanel = new JPanel();
			accessRulesPaneActionsPanel.setLayout(flowLayout11);
			flowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			accessRulesPaneActionsPanel.add(getAddAccessRuleButton1(), null);
			accessRulesPaneActionsPanel.add(getEditAccessRuleButton(), null);
			accessRulesPaneActionsPanel.add(getDeleteAccessRuleButton(), null);
		}
		return accessRulesPaneActionsPanel;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getAccessRulesTable() {
		if (accessRulesTable == null) {
			accessRulesTable = new JTable();
			accessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			accessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);
			accessRulesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			accessRulesTable.getSelectionModel().addListSelectionListener(this);
			accessRulesTable.addMouseListener(this);
		}
		return accessRulesTable;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getToolbarPanel() {
		if (toolbarPanel == null) {
			GridLayout gridLayout3 = new GridLayout();
			toolbarPanel = new JPanel();
			toolbarPanel.setLayout(gridLayout3);
			gridLayout3.setRows(1);
			gridLayout3.setColumns(1);
			toolbarPanel.add(getActionToolBar(), null);
		}
		return toolbarPanel;
	}

	/**
	 * This method initializes jMenuItem11
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPrintMenuItem() {
		if (printMenuItem == null) {
			printMenuItem = new JMenuItem();
			printMenuItem.setText(ResourceUtil.getString("menu.file.print"));
			printMenuItem.setActionCommand("Print");
			printMenuItem
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Print16.gif")));
			printMenuItem.setToolTipText(ResourceUtil.getString("menu.file.print.tooltip"));
			printMenuItem.addActionListener(this);
			printMenuItem.setAccelerator(KeyStroke.getKeyStroke(ResourceUtil.getString("menu.file.print.shortcut").charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
		}
		return printMenuItem;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getAccessRulesScrollPane1() {
		if (accessRulesScrollPane1 == null) {
			accessRulesScrollPane1 = new JScrollPane();
			accessRulesScrollPane1.setViewportView(getAccessRulesTable());
		}
		return accessRulesScrollPane1;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel5 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jLabel5.setText(ResourceUtil.getString("mainframe.accessrules"));
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 7, 0, 0));
			jPanel1.add(jLabel5, java.awt.BorderLayout.NORTH);
			jPanel1.add(getAccessRulesScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel3 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 0, 0, 0));
			jLabel3.setText(ResourceUtil.getString("mainframe.accessrules"));
			jPanel2.add(jLabel3, java.awt.BorderLayout.NORTH);
			jPanel2.add(getGroupAccessRulesScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 7, 0));
			
			jLabel22 = new JLabel();
			jLabel22.setText(ResourceUtil.getString("mainframe.members"));
			
			jPanel3.add(jLabel22, java.awt.BorderLayout.NORTH);
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel3.add(getJPanel11(), java.awt.BorderLayout.SOUTH);
		}
		return jPanel3;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getGroupMemberList());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getGroupMemberList() {
		if (groupMemberList == null) {
			groupMemberList = new JList();
			groupMemberList.addMouseListener(this);
			groupMemberList.setCellRenderer(new MyListCellRenderer());
			groupMemberList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return groupMemberList;
	}
	/**
	 * This method initializes jPanel10	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jLabel = new JLabel();
			jPanel10 = new JPanel();
			jPanel10.setLayout(new BorderLayout());
			jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 7, 0));
			jLabel.setText(ResourceUtil.getString("mainframe.groups"));
			jPanel10.add(jLabel, java.awt.BorderLayout.NORTH);
			jPanel10.add(getUserGroupListScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel10.add(getJPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jPanel10;
	}

	private void displayGroup(Object group) {
		if (group == null) {
			return;
		}
		
		getMainTabbedPane().setSelectedComponent(getGroupsSplitPane());
		getGroupList().setSelectedValue(group, true);
		refreshGroupDetails();
	}

	private void displayUser(Object user) {
		if (user == null) {
			return;
		}
		
		getMainTabbedPane().setSelectedComponent(getUsersSplitPane());
		getUserList().setSelectedValue(user, true);
		refreshUserDetails();
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (e.getSource() == getUserList()) {
				editUser();
			}
			else if (e.getSource() == getGroupList()) {
				editGroup();
			}
			else if (e.getSource() == getUserGroupList()) {
				displayGroup(getUserGroupList().getSelectedValue());
			}
			else if (e.getSource() == getGroupMemberList()) {
				if (getGroupMemberList().getSelectedValue() instanceof Group) {
					displayGroup(getGroupMemberList().getSelectedValue());
				}
				else {
					displayUser(getGroupMemberList().getSelectedValue());
				}
			}
			else if (e.getSource() == getAccessRulesTable()) {
				editAccessRule();
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		// Not used
	}


	public void mouseReleased(MouseEvent e) {
		// Not used
	}


	public void mouseEntered(MouseEvent e) {
		// Not used
	}

	public void mouseExited(MouseEvent e) {
		// Not used
	}

	private void refreshRepositoryAccessRules(Repository repository) {
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getRepositoryAccessRules(repository), getRepositoryAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforrepository"));
		}
		
		getAccessRulesTable().setModel(model);
		getEditAccessRuleButton().setEnabled(false);
		getDeleteAccessRuleButton().setEnabled(false);
	}

	private void refreshPathAccessRules(Path path) {
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getPathAccessRules(path), getPathAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforpath"));
		}
		
		getAccessRulesTable().setModel(model);
	}
	
	private void refreshServerAccessRules() {
		DefaultTableModel model = new NonEditableTableModel();
		
		try {
			model.setDataVector(Document.getServerAccessRules(), getServerAccessRulesColumnNames());
		}
		catch (ApplicationException ae) {
			displayError(ResourceUtil.getString("mainframe.error.errorloadingaccessrulesforserver"));
		}
		
		getAccessRulesTable().setModel(model);
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getAccessRulesTree().getLastSelectedPathComponent();
		
		if (node == null) {
			return;
		}
		
		Object userObject = node.getUserObject();
				
		if (userObject instanceof Repository) {
			refreshRepositoryAccessRules((Repository)userObject);
			
			getEditTreeItemButton().setEnabled(true);
			getDeleteTreeItemButton().setEnabled(true);
			
			getEditTreeItemButton().setIcon(repositoryEditIcon);
			getDeleteTreeItemButton().setIcon(repositoryDeleteIcon);
			
			getEditTreeItemButton().setActionCommand("EditRepository");
			getDeleteTreeItemButton().setActionCommand("DeleteRepository");
			
			getEditTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.editrepository.tooltip"));
			getDeleteTreeItemButton().setToolTipText(ResourceUtil.getString("mainframe.button.deleterepository.tooltip"));
		}
		else if (userObject instanceof Path) {
			refreshPathAccessRules((Path)userObject);
			
			getEditTreeItemButton().setEnabled(true);
			getDeleteTreeItemButton().setEnabled(true);
			
			getEditTreeItemButton().setIcon(pathEditIcon);
			getDeleteTreeItemButton().setIcon(pathDeleteIcon);
			
			getEditTreeItemButton().setActionCommand("EditPath");
			getDeleteTreeItemButton().setActionCommand("DeletePath");
			
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
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jLabel4 = new JLabel();
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 7));
			jLabel4.setText(ResourceUtil.getString("mainframe.serverstructure"));
			jPanel7.add(getAccessRulesScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel7.add(getJPanel9(), java.awt.BorderLayout.SOUTH);
			jPanel7.add(jLabel4, java.awt.BorderLayout.NORTH);
		}
		return jPanel7;
	}
	/**
	 * This method initializes jPanel9	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.add(getEditTreeItemButton(), null);
			jPanel9.add(getDeleteTreeItemButton(), null);
		}
		return jPanel9;
	}
	/**
	 * This method initializes jButton	
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
	 * This method initializes jButton1	
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

	public void keyTyped(KeyEvent e) {
		// Unused
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getID() == KeyEvent.VK_F1) {
			showHelp();
		}
	}

	public void keyReleased(KeyEvent e) {
		// Unused
		
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout12 = new FlowLayout();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout12);
			flowLayout12.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout12.setHgap(0);
			jPanel.add(getChangeMembershipButton(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			FlowLayout flowLayout21 = new FlowLayout();
			jPanel11 = new JPanel();
			jPanel11.setLayout(flowLayout21);
			flowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout21.setHgap(0);
			jPanel11.add(getAddRemoveMembersButton(), null);
		}
		return jPanel11;
	}
	/**
	 * This method initializes jPanel13	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel13() {
		if (jPanel13 == null) {
			jLabel1 = new JLabel();
			jPanel13 = new JPanel();
			jPanel13.setLayout(new BorderLayout());
			jPanel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 7));
			jLabel1.setText(ResourceUtil.getString("mainframe.users"));
			jPanel13.add(getUserListScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel13.add(getUsersPaneActionsPanel(), java.awt.BorderLayout.SOUTH);
			jPanel13.add(jLabel1, java.awt.BorderLayout.NORTH);
		}
		return jPanel13;
	}
	/**
	 * This method initializes jPanel14	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel14() {
		if (jPanel14 == null) {
			jLabel2 = new JLabel();
			jPanel14 = new JPanel();
			jPanel14.setLayout(new BorderLayout());
			jPanel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 7));
			jLabel2.setText(ResourceUtil.getString("mainframe.groups"));
			jPanel14.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
			jPanel14.add(getGroupsPaneActionsPanel(), java.awt.BorderLayout.SOUTH);
			jPanel14.add(jLabel2, java.awt.BorderLayout.NORTH);
		}
		return jPanel14;
	}
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getCloneUserMenuItem() {
		if (cloneUserMenuItem == null) {
			cloneUserMenuItem = new JMenuItem();
			cloneUserMenuItem.setText(ResourceUtil.getString("menu.clone"));
			cloneUserMenuItem.setActionCommand("CloneUser");
			cloneUserMenuItem.setEnabled(false);
			cloneUserMenuItem.addActionListener(this);
		}
		return cloneUserMenuItem;
	}
	/**
	 * This method initializes jMenuItem1	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getCloneGroupMenuItem() {
		if (cloneGroupMenuItem == null) {
			cloneGroupMenuItem = new JMenuItem();
			cloneGroupMenuItem.setText(ResourceUtil.getString("menu.clone"));
			cloneGroupMenuItem.setActionCommand("CloneGroup");
			cloneGroupMenuItem.setEnabled(false);
			cloneGroupMenuItem.addActionListener(this);
		}
		return cloneGroupMenuItem;
	}
        }  
