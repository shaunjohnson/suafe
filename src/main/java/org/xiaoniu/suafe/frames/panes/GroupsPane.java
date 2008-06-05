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

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.renderers.MyTableCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class GroupsPane extends JSplitPane {

	private static final long serialVersionUID = -743618351601446392L;

	private ActionListener actionListener = null;
	
	private JButton addGroupButton = null;
	
	private JButton addRemoveMembersButton = null;
	
	private JButton cloneGroupButton = null;
	
	private JButton deleteGroupButton = null;
	
	private JButton editGroupButton = null;
	
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
			addGroupButton = new JButton();
			addGroupButton.addActionListener(actionListener);
			addGroupButton.setActionCommand(Constants.ADD_GROUP_ACTION);
			addGroupButton.setIcon(ResourceUtil.addGroupIcon);
			addGroupButton.setText(ResourceUtil.getString("button.add"));
			addGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));			
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
			addRemoveMembersButton = new JButton();
			addRemoveMembersButton.addActionListener(actionListener);
			addRemoveMembersButton.setActionCommand(Constants.ADD_REMOVE_MEMBERS_ACTION);
			addRemoveMembersButton.setIcon(ResourceUtil.addRemoveMembersIcon);
			addRemoveMembersButton.setText(ResourceUtil.getString("mainframe.button.addremovemembers"));
			addRemoveMembersButton.setToolTipText(ResourceUtil.getString("mainframe.button.addremovemembers.tooltip"));
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
			cloneGroupButton = new JButton();
			cloneGroupButton.addActionListener(actionListener);
			cloneGroupButton.setActionCommand(Constants.CLONE_GROUP_ACTION);
			cloneGroupButton.setIcon(ResourceUtil.cloneGroupIcon);
			cloneGroupButton.setText(ResourceUtil.getString("button.clone"));
			cloneGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.clonegroup.tooltip"));
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
			deleteGroupButton = new JButton();
			deleteGroupButton.addActionListener(actionListener);
			deleteGroupButton.setActionCommand(Constants.DELETE_GROUP_ACTION);
			deleteGroupButton.setIcon(ResourceUtil.deleteGroupIcon);
			deleteGroupButton.setText(ResourceUtil.getString("button.delete"));
			deleteGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.deletegroup.tooltip"));
			deleteGroupButton.setEnabled(false);
		}
		
		return deleteGroupButton;
	}

	/**
	 * This method initializes editGroupButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getEditGroupButton() {
		if (editGroupButton == null) {
			editGroupButton = new JButton();
			editGroupButton.addActionListener(actionListener);
			editGroupButton.setActionCommand(Constants.EDIT_GROUP_ACTION);
			editGroupButton.setIcon(ResourceUtil.editGroupIcon);
			editGroupButton.setText(ResourceUtil.getString("button.edit"));
			editGroupButton.setToolTipText(ResourceUtil.getString("mainframe.button.editgroup.tooltip"));
			editGroupButton.setEnabled(false);
		}
		
		return editGroupButton;
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
			groupAccessRulesPanel.add(new JLabel(ResourceUtil.getString("mainframe.accessrules")), BorderLayout.NORTH);
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
			groupAccessRulesScrollPane = new JScrollPane();
			groupAccessRulesScrollPane.setViewportView(getGroupAccessRulesTable());
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
			groupAccessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);	
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
	 * This method initializes groupDetailsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getGroupDetailsPanel() {
		if (groupDetailsPanel == null) {
			groupDetailsPanel = new JPanel();
			groupDetailsPanel.setLayout(new BorderLayout());
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
			groupList.setFont(Constants.FONT_PLAIN);
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
	 * This method initializes groupListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getGroupListScrollPane() {
		if (groupListScrollPane == null) {
			groupListScrollPane = new JScrollPane();
			groupListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			groupListScrollPane.setViewportView(getGroupList());			
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
			groupMemberList.setFont(Constants.FONT_PLAIN);
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
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			groupMemberListActionsPanel = new JPanel(layout);
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
			groupMemberListScrollPane = new JScrollPane();
			groupMemberListScrollPane.setViewportView(getGroupMemberList());
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
