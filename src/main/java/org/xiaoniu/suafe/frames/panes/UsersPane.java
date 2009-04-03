package org.xiaoniu.suafe.frames.panes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.ApplicationDefaultsContants;
import org.xiaoniu.suafe.GuiConstants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.renderers.MyTableCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class UsersPane extends BaseSplitPane {

	private static final long serialVersionUID = 7496096861009879724L;

	private ActionListener actionListener = null;

	private JButton addUserButton = null;

	private JButton changeMembershipButton = null;

	private JButton cloneUserButton = null;

	private JButton deleteUserButton = null;

	private JButton renameUserButton = null;

	private KeyListener keyListener = null;

	private ListSelectionListener listSelectionListener = null;

	private MouseListener mouseListener = null;

	private JPanel userAccessRulesFormatPanel = null;

	private JScrollPane userAccessRulesScrollPane = null;

	private JTable userAccessRulesTable = null;

	private JPanel userActionsPanel = null;

	private JPanel userDetailsPanel = null;

	private JSplitPane userDetailsSplitPanel = null;

	private JList userGroupList = null;

	private JPanel userGroupListPanel = null;

	private JScrollPane userGroupListScrollPane = null;

	private JPanel userGroupsActionPanel = null;

	private JList userList = null;

	private JPanel userListPanel = null;

	private JScrollPane userListScrollPane = null;

	public UsersPane(ActionListener actionListener, KeyListener keyListener,
			ListSelectionListener listSelectionListener, MouseListener mouseListener) {
		super();

		this.actionListener = actionListener;
		this.keyListener = keyListener;
		this.listSelectionListener = listSelectionListener;
		this.mouseListener = mouseListener;

		setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		setLeftComponent(getUserListPanel());
		setRightComponent(getUserDetailsPanel());
		setDividerLocation(UserPreferences.getUsersPaneDividerLocation());
	}

	/**
	 * This method initializes addUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getAddUserButton() {
		if (addUserButton == null) {
			addUserButton = createButton("button.add", "mainframe.button.adduser.tooltip", ResourceUtil.addUserIcon,
					ActionConstants.ADD_USER_ACTION, actionListener);
		}

		return addUserButton;
	}

	/**
	 * This method initializes changeMembershipButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getChangeMembershipButton() {
		if (changeMembershipButton == null) {
			changeMembershipButton = createButton("mainframe.button.changemembership",
					"mainframe.button.changemembership.tooltip", ResourceUtil.changeMembershipIcon,
					ActionConstants.CHANGE_MEMBERSHIP_ACTION, actionListener);
			changeMembershipButton.setEnabled(false);
		}

		return changeMembershipButton;
	}

	/**
	 * This method initializes cloneUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getCloneUserButton() {
		if (cloneUserButton == null) {
			cloneUserButton = createButton("button.clone", "mainframe.button.cloneuser.tooltip",
					ResourceUtil.cloneUserIcon, ActionConstants.CLONE_USER_ACTION, actionListener);
			cloneUserButton.setEnabled(false);
		}

		return cloneUserButton;
	}

	/**
	 * This method initializes deleteUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getDeleteUserButton() {
		if (deleteUserButton == null) {
			deleteUserButton = createButton("button.delete", "mainframe.button.deleteuser.tooltip",
					ResourceUtil.deleteUserIcon, ActionConstants.DELETE_USER_ACTION, actionListener);
			deleteUserButton.setEnabled(false);
		}

		return deleteUserButton;
	}

	/**
	 * This method initializes renameUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getRenameUserButton() {
		if (renameUserButton == null) {
			renameUserButton = createButton("button.rename", "mainframe.button.renameuser.tooltip",
					ResourceUtil.renameUserIcon, ActionConstants.RENAME_USER_ACTION, actionListener);
			renameUserButton.setEnabled(false);
		}

		return renameUserButton;
	}

	/**
	 * This method initializes userAccessRulesFormatPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserAccessRulesFormatPanel() {
		if (userAccessRulesFormatPanel == null) {
			userAccessRulesFormatPanel = new JPanel(new BorderLayout());
			userAccessRulesFormatPanel.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
			userAccessRulesFormatPanel.add(createLabel("mainframe.tabs.accessrules"), BorderLayout.NORTH);
			userAccessRulesFormatPanel.add(getUserAccessRulesScrollPane(), BorderLayout.CENTER);
		}

		return userAccessRulesFormatPanel;
	}

	/**
	 * This method initializes userAccessRulesScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getUserAccessRulesScrollPane() {
		if (userAccessRulesScrollPane == null) {
			userAccessRulesScrollPane = new JScrollPane(getUserAccessRulesTable());
		}

		return userAccessRulesScrollPane;
	}

	/**
	 * This method initializes userAccessRulesTable.
	 * 
	 * @return javax.swing.JTable
	 */
	public JTable getUserAccessRulesTable() {
		if (userAccessRulesTable == null) {
			userAccessRulesTable = new JTable();
			userAccessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			userAccessRulesTable.setRowHeight(ApplicationDefaultsContants.DEFAULT_ACCESS_RULE_TABLE_ROW_HEIGHT);
			userAccessRulesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			userAccessRulesTable.setAutoCreateRowSorter(true);
		}

		return userAccessRulesTable;
	}

	/**
	 * This method initializes userActionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserActionsPanel() {
		if (userActionsPanel == null) {
			userActionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			userActionsPanel.add(getAddUserButton());
			userActionsPanel.add(getCloneUserButton());
			userActionsPanel.add(getRenameUserButton());
			userActionsPanel.add(getDeleteUserButton());
		}

		return userActionsPanel;
	}

	/**
	 * This method initializes userDetailsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserDetailsPanel() {
		if (userDetailsPanel == null) {
			userDetailsPanel = new JPanel(new BorderLayout());
			userDetailsPanel.add(getUserDetailsSplitPanel(), BorderLayout.CENTER);
		}

		return userDetailsPanel;
	}

	/**
	 * This method initializes userDetailsSubPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JSplitPane getUserDetailsSplitPanel() {
		if (userDetailsSplitPanel == null) {
			userDetailsSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			userDetailsSplitPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));
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
	public JList getUserGroupList() {
		if (userGroupList == null) {
			userGroupList = new JList();
			userGroupList.addKeyListener(keyListener);
			userGroupList.addMouseListener(mouseListener);
			userGroupList.setCellRenderer(new MyListCellRenderer());
			userGroupList.setFont(GuiConstants.FONT_PLAIN);
		}

		return userGroupList;
	}

	/**
	 * This method initializes userGroupListPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserGroupListPanel() {
		if (userGroupListPanel == null) {
			userGroupListPanel = new JPanel(new BorderLayout());
			userGroupListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
			userGroupListPanel.add(new JLabel(ResourceUtil.getString("mainframe.groups")), BorderLayout.NORTH);
			userGroupListPanel.add(getUserGroupListScrollPane(), BorderLayout.CENTER);
			userGroupListPanel.add(getUserGroupsActionPanel(), BorderLayout.SOUTH);
		}

		return userGroupListPanel;
	}

	/**
	 * This method initializes userGroupListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getUserGroupListScrollPane() {
		if (userGroupListScrollPane == null) {
			userGroupListScrollPane = new JScrollPane(getUserGroupList());
		}

		return userGroupListScrollPane;
	}

	/**
	 * This method initializes userGroupsActionPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserGroupsActionPanel() {
		if (userGroupsActionPanel == null) {
			userGroupsActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			userGroupsActionPanel.add(getChangeMembershipButton());
		}

		return userGroupsActionPanel;
	}

	/**
	 * This method initializes userList.
	 * 
	 * @return javax.swing.JList
	 */
	public JList getUserList() {
		if (userList == null) {
			userList = new JList();
			userList.addKeyListener(keyListener);
			userList.addListSelectionListener(listSelectionListener);
			userList.addMouseListener(mouseListener);
			userList.setCellRenderer(new MyListCellRenderer());
			userList.setFont(GuiConstants.FONT_PLAIN);
		}

		return userList;
	}

	/**
	 * This method initializes userListPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserListPanel() {
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
	 * This method initializes userListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getUserListScrollPane() {
		if (userListScrollPane == null) {
			userListScrollPane = new JScrollPane(getUserList());
			userListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

		return userListScrollPane;
	}

	public void loadUserPreferences() {
		getUserDetailsSplitPanel().setDividerLocation(UserPreferences.getUserDetailsDividerLocation());
		setDividerLocation(UserPreferences.getUsersPaneDividerLocation());
	}
}
