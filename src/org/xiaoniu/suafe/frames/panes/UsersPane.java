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

public class UsersPane extends JSplitPane {

	private static final long serialVersionUID = 7496096861009879724L;

	private ActionListener actionListener = null;
	
	private JButton addUserButton = null;
	
	private JButton changeMembershipButton = null;
	
	private JButton cloneUserButton = null;
	
	private JButton deleteUserButton = null;
	
	private JButton editUserButton = null;
	
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
			addUserButton = new JButton();
			addUserButton.addActionListener(actionListener);
			addUserButton.setActionCommand(Constants.ADD_USER_ACTION);
			addUserButton.setIcon(ResourceUtil.addUserIcon);
			addUserButton.setText(ResourceUtil.getString("button.add"));
			addUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
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
			changeMembershipButton = new JButton();
			changeMembershipButton.addActionListener(actionListener);
			changeMembershipButton.setActionCommand(Constants.CHANGE_MEMBERSHIP_ACTION);
			changeMembershipButton.setIcon(ResourceUtil.changeMembershipIcon);
			changeMembershipButton.setText(ResourceUtil.getString("mainframe.button.changemembership"));
			changeMembershipButton.setToolTipText(ResourceUtil.getString("mainframe.button.changemembership.tooltip"));
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
			cloneUserButton = new JButton();
			cloneUserButton.addActionListener(actionListener);
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
	public JButton getDeleteUserButton() {
		if (deleteUserButton == null) {
			deleteUserButton = new JButton();
			deleteUserButton.addActionListener(actionListener);
			deleteUserButton.setActionCommand(Constants.DELETE_USER_ACTION);
			deleteUserButton.setIcon(ResourceUtil.deleteUserIcon);
			deleteUserButton.setText(ResourceUtil.getString("button.delete"));
			deleteUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.deleteuser.tooltip"));
			deleteUserButton.setEnabled(false);			
		}
		
		return deleteUserButton;
	}
	
	/**
	 * This method initializes editUserButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getEditUserButton() {
		if (editUserButton == null) {
			editUserButton = new JButton();
			editUserButton.addActionListener(actionListener);
			editUserButton.setActionCommand(Constants.EDIT_USER_ACTION);
			editUserButton.setIcon(ResourceUtil.editUserIcon);
			editUserButton.setText(ResourceUtil.getString("button.edit"));
			editUserButton.setToolTipText(ResourceUtil.getString("mainframe.button.edituser.tooltip"));
			editUserButton.setEnabled(false);
		}
		
		return editUserButton;
	}

	/**
	 * This method initializes userAccessRulesFormatPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserAccessRulesFormatPanel() {
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
	 * This method initializes userAccessRulesScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getUserAccessRulesScrollPane() {
		if (userAccessRulesScrollPane == null) {
			userAccessRulesScrollPane = new JScrollPane();
			userAccessRulesScrollPane.setViewportView(getUserAccessRulesTable());
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
			userAccessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);	
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
	 * This method initializes userDetailsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getUserDetailsPanel() {
		if (userDetailsPanel == null) {
			userDetailsPanel = new JPanel();
			userDetailsPanel.setLayout(new BorderLayout());
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
	public JList getUserGroupList() {
		if (userGroupList == null) {
			userGroupList = new JList();
			userGroupList.addKeyListener(keyListener);
			userGroupList.addMouseListener(mouseListener);
			userGroupList.setCellRenderer(new MyListCellRenderer());
			userGroupList.setFont(Constants.FONT_PLAIN);
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
	 * This method initializes userGroupListScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getUserGroupListScrollPane() {
		if (userGroupListScrollPane == null) {
			userGroupListScrollPane = new JScrollPane();
			userGroupListScrollPane.setViewportView(getUserGroupList());
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
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			userGroupsActionPanel = new JPanel(layout);			
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
			userList.setFont(Constants.FONT_PLAIN);
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
			userListScrollPane = new JScrollPane();
			userListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			userListScrollPane.setViewportView(getUserList());
		}
		
		return userListScrollPane;
	}
}
