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
import org.xiaoniu.suafe.ApplicationDefaultsConstants;
import org.xiaoniu.suafe.GuiConstants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.renderers.MyTableCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

public final class GroupsPane extends BaseSplitPane {

	private static final long serialVersionUID = -743618351601446392L;

	private ActionListener actionListener = null;

	private JButton addGroupButton = null;

	private JButton addRemoveMembersButton = null;

	private JButton cloneGroupButton = null;

	private JButton deleteGroupButton = null;

	private JButton renameGroupButton = null;

	private JPanel groupAccessRulesPanel = null;

	private JScrollPane groupAccessRulesScrollPane = null;

	private JTable groupAccessRulesTable = null;

	private JPanel groupActionsPanel = null;

	private JPanel groupDetailsPanel = null;

	private JSplitPane groupDetailsSplitPanel = null;

	private JList groupList = null;

	private JPanel groupListPanel = null;

	private JScrollPane groupListScrollPane = null;

	private JList groupMemberList = null;

	private JPanel groupMemberListActionsPanel = null;

	private JScrollPane groupMemberListScrollPane = null;

	private JPanel groupMembersPanel = null;

	private KeyListener keyListener = null;

	private ListSelectionListener listSelectionListener = null;

	private MouseListener mouseListener = null;

	public GroupsPane(ActionListener actionListener, KeyListener keyListener,
			ListSelectionListener listSelectionListener, MouseListener mouseListener) {
		super();

		this.actionListener = actionListener;
		this.keyListener = keyListener;
		this.listSelectionListener = listSelectionListener;
		this.mouseListener = mouseListener;

		setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		setLeftComponent(getGroupListPanel());
		setRightComponent(getGroupDetailsPanel());
		setDividerLocation(UserPreferences.getGroupsPaneDividerLocation());
	}

	/**
	 * This method initializes addGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getAddGroupButton() {
		if (addGroupButton == null) {
			addGroupButton = createButton("button.add", "mainframe.button.addgroup.tooltip", ResourceUtil.addGroupIcon,
					ActionConstants.ADD_GROUP_ACTION, actionListener);
		}

		return addGroupButton;
	}

	/**
	 * This method initializes addRemoveMembersButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getAddRemoveMembersButton() {
		if (addRemoveMembersButton == null) {
			addRemoveMembersButton = createButton("mainframe.button.addremovemembers",
					"mainframe.button.addremovemembers.tooltip", ResourceUtil.addRemoveMembersIcon,
					ActionConstants.ADD_REMOVE_MEMBERS_ACTION, actionListener);
			addRemoveMembersButton.setEnabled(false);
		}

		return addRemoveMembersButton;
	}

	/**
	 * This method initializes cloneGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getCloneGroupButton() {
		if (cloneGroupButton == null) {
			cloneGroupButton = createButton("button.clone", "mainframe.button.clonegroup.tooltip",
					ResourceUtil.cloneGroupIcon, ActionConstants.CLONE_GROUP_ACTION, actionListener);
			cloneGroupButton.setEnabled(false);
		}

		return cloneGroupButton;
	}

	/**
	 * This method initializes deleteGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getDeleteGroupButton() {
		if (deleteGroupButton == null) {
			deleteGroupButton = createButton("button.delete", "mainframe.button.deletegroup.tooltip",
					ResourceUtil.deleteGroupIcon, ActionConstants.DELETE_GROUP_ACTION, actionListener);
			deleteGroupButton.setEnabled(false);
		}

		return deleteGroupButton;
	}

	/**
	 * This method initializes renameGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getRenameGroupButton() {
		if (renameGroupButton == null) {
			renameGroupButton = createButton("button.rename", "mainframe.button.renamegroup.tooltip",
					ResourceUtil.renameGroupIcon, ActionConstants.RENAME_GROUP_ACTION, actionListener);
			renameGroupButton.setEnabled(false);
		}

		return renameGroupButton;
	}

	/**
	 * This method initializes groupAccessRulesPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getGroupAccessRulesPanel() {
		if (groupAccessRulesPanel == null) {
			groupAccessRulesPanel = new JPanel(new BorderLayout());
			groupAccessRulesPanel.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
			groupAccessRulesPanel.add(createLabel("mainframe.accessrules"), BorderLayout.NORTH);
			groupAccessRulesPanel.add(getGroupAccessRulesScrollPane(), BorderLayout.CENTER);
		}

		return groupAccessRulesPanel;
	}

	/**
	 * This method initializes groupAccessRulesScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getGroupAccessRulesScrollPane() {
		if (groupAccessRulesScrollPane == null) {
			groupAccessRulesScrollPane = new JScrollPane(getGroupAccessRulesTable());
		}

		return groupAccessRulesScrollPane;
	}

	/**
	 * This method initializes groupAccessRulesTable.
	 * 
	 * @return javax.swing.JTable
	 */
	public JTable getGroupAccessRulesTable() {
		if (groupAccessRulesTable == null) {
			groupAccessRulesTable = new JTable();
			groupAccessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			groupAccessRulesTable.setRowHeight(ApplicationDefaultsConstants.DEFAULT_ACCESS_RULE_TABLE_ROW_HEIGHT);
			groupAccessRulesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			groupAccessRulesTable.setAutoCreateRowSorter(true);
		}

		return groupAccessRulesTable;
	}

	/**
	 * This method initializes groupActionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getGroupActionsPanel() {
		if (groupActionsPanel == null) {
			groupActionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			groupActionsPanel.add(getAddGroupButton());
			groupActionsPanel.add(getCloneGroupButton());
			groupActionsPanel.add(getRenameGroupButton());
			groupActionsPanel.add(getDeleteGroupButton());
		}

		return groupActionsPanel;
	}

	/**
	 * This method initializes groupDetailsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getGroupDetailsPanel() {
		if (groupDetailsPanel == null) {
			groupDetailsPanel = new JPanel(new BorderLayout());
			groupDetailsPanel.add(getGroupDetailsSplitPanel(), BorderLayout.CENTER);
		}

		return groupDetailsPanel;
	}

	/**
	 * This method initializes groupDetailsSubPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JSplitPane getGroupDetailsSplitPanel() {
		if (groupDetailsSplitPanel == null) {
			groupDetailsSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			groupDetailsSplitPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));
			groupDetailsSplitPanel.setTopComponent(getGroupMembersPanel());
			groupDetailsSplitPanel.setBottomComponent(getGroupAccessRulesPanel());
			groupDetailsSplitPanel.setOneTouchExpandable(true);
			groupDetailsSplitPanel.setDividerLocation(UserPreferences.getGroupDetailsDividerLocation());
		}

		return groupDetailsSplitPanel;
	}

	/**
	 * This method initializes groupList.
	 * 
	 * @return javax.swing.JList
	 */
	public JList getGroupList() {
		if (groupList == null) {
			groupList = new JList();
			groupList.addKeyListener(keyListener);
			groupList.addListSelectionListener(listSelectionListener);
			groupList.addMouseListener(mouseListener);
			groupList.setCellRenderer(new MyListCellRenderer());
			groupList.setFont(GuiConstants.FONT_PLAIN);
		}

		return groupList;
	}

	/**
	 * This method initializes groupListPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getGroupListPanel() {
		if (groupListPanel == null) {
			groupListPanel = new JPanel(new BorderLayout());
			groupListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
			groupListPanel.add(getGroupListScrollPane(), BorderLayout.CENTER);
			groupListPanel.add(getGroupActionsPanel(), BorderLayout.SOUTH);
			groupListPanel.add(new JLabel(ResourceUtil.getString("mainframe.groups")), BorderLayout.NORTH);
		}

		return groupListPanel;
	}

	/**
	 * This method initializes groupListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getGroupListScrollPane() {
		if (groupListScrollPane == null) {
			groupListScrollPane = new JScrollPane(getGroupList());
			groupListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

		return groupListScrollPane;
	}

	/**
	 * This method initializes groupMemberList.
	 * 
	 * @return javax.swing.JList
	 */
	public JList getGroupMemberList() {
		if (groupMemberList == null) {
			groupMemberList = new JList();
			groupMemberList.addKeyListener(keyListener);
			groupMemberList.addMouseListener(mouseListener);
			groupMemberList.setCellRenderer(new MyListCellRenderer());
			groupMemberList.setFont(GuiConstants.FONT_PLAIN);
		}

		return groupMemberList;
	}

	/**
	 * This method initializes groupMemberListActionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getGroupMemberListActionsPanel() {
		if (groupMemberListActionsPanel == null) {
			groupMemberListActionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			groupMemberListActionsPanel.add(getAddRemoveMembersButton());
		}

		return groupMemberListActionsPanel;
	}

	/**
	 * This method initializes groupMemberListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getGroupMemberListScrollPane() {
		if (groupMemberListScrollPane == null) {
			groupMemberListScrollPane = new JScrollPane(getGroupMemberList());
		}

		return groupMemberListScrollPane;
	}

	/**
	 * This method initializes groupMembersPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getGroupMembersPanel() {
		if (groupMembersPanel == null) {
			groupMembersPanel = new JPanel(new BorderLayout());
			groupMembersPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
			groupMembersPanel.add(new JLabel(ResourceUtil.getString("mainframe.members")), BorderLayout.NORTH);
			groupMembersPanel.add(getGroupMemberListScrollPane(), BorderLayout.CENTER);
			groupMembersPanel.add(getGroupMemberListActionsPanel(), BorderLayout.SOUTH);
		}

		return groupMembersPanel;
	}

	public void loadUserPreferences() {
		getGroupDetailsSplitPanel().setDividerLocation(UserPreferences.getGroupDetailsDividerLocation());
		setDividerLocation(UserPreferences.getGroupsPaneDividerLocation());
	}
}
