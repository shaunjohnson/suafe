package org.xiaoniu.suafe.frames.panes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.renderers.MyTableCellRenderer;
import org.xiaoniu.suafe.renderers.MyTreeCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class MainFrameAccessRulesPane extends JSplitPane {

	private static final long serialVersionUID = 4055338694233688725L;

	private JPanel accessRuleActionsPanel = null;
	
	private JPanel accessRulesFormatPanel = null;
	
	private JPanel accessRulesPanel = null;

	private JScrollPane accessRulesScrollPane = null;
	  
	private JTable accessRulesTable = null;
	
	private JTree accessRulesTree = null; 
	
	private JPanel accessRulesTreeActionsPanel = null;
	
	private JPanel accessRulesTreePanel = null;
	
	private JScrollPane accessRulesTreeScrollPane = null;
	
	private ActionListener actionListener = null;
	
	private JButton addAccessRuleButton = null;
	
	private JButton addProjectAccessRulesButton = null;
	
	private JButton deleteAccessRuleButton = null;  
	
	private JButton deleteTreeItemButton = null;
	
	private JButton editAccessRuleButton = null;
	
	private JButton editTreeItemButton = null;
	
	private ListSelectionListener listSelectionListener = null;
	
	private MouseListener mouseListener = null;
	
	private TreeSelectionListener treeSelectionListener = null;
	
	public MainFrameAccessRulesPane(ActionListener actionListener, ListSelectionListener listSelectionListener, 
			MouseListener mouseListener, TreeSelectionListener treeSelectionListener) {
		super();
		
		this.actionListener = actionListener;
		this.mouseListener =  mouseListener;		
		this.listSelectionListener = listSelectionListener;
		this.treeSelectionListener = treeSelectionListener;
		
		setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		setLeftComponent(getAccessRulesTreePanel());
		setRightComponent(getAccessRulesPanel());
		setDividerLocation(UserPreferences.getRulesPaneDividerLocation());
	}
	
	/**
	 * This method initializes accessRuleActionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getAccessRuleActionsPanel() {
		if (accessRuleActionsPanel == null) {
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			
			accessRuleActionsPanel = new JPanel(layout);
			accessRuleActionsPanel.add(getAddAccessRuleButton());
			accessRuleActionsPanel.add(getAddProjectAccessRulesButton());
			accessRuleActionsPanel.add(getEditAccessRuleButton());
			accessRuleActionsPanel.add(getDeleteAccessRuleButton());
		}
		
		return accessRuleActionsPanel;
	}

	/**
	 * This method initializes accessRulesFormatPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	public JPanel getAccessRulesFormatPanel() {
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
	 * This method initializes accessRulesPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getAccessRulesPanel() {
		if (accessRulesPanel == null) {
			accessRulesPanel = new JPanel();
			accessRulesPanel.setLayout(new BorderLayout());
			accessRulesPanel.add(getAccessRulesFormatPanel(), BorderLayout.CENTER);
			accessRulesPanel.add(getAccessRuleActionsPanel(), BorderLayout.SOUTH);			
		}
		
		return accessRulesPanel;
	}

	/**
	 * This method initializes accessRulesScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	public JScrollPane getAccessRulesScrollPane() {
		if (accessRulesScrollPane == null) {
			accessRulesScrollPane = new JScrollPane();
			accessRulesScrollPane.setViewportView(getAccessRulesTable());
		}
		
		return accessRulesScrollPane;
	}

	/**
	 * This method initializes accessRulesTable
	 * 
	 * @return javax.swing.JTable
	 */
	public JTable getAccessRulesTable() {
		if (accessRulesTable == null) {
			accessRulesTable = new JTable();
			accessRulesTable.addMouseListener(mouseListener);
			accessRulesTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
			accessRulesTable.setRowHeight(Constants.ACCESS_RULE_TABLE_ROW_HEIGHT);
			accessRulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			accessRulesTable.getSelectionModel().addListSelectionListener(listSelectionListener);
			accessRulesTable.setAutoCreateRowSorter(true);
		}
		
		return accessRulesTable;
	}
	
	/**
	 * This method initializes accessRulesTree.
	 * 
	 * @return javax.swing.JTree
	 */
	public JTree getAccessRulesTree() {
		if (accessRulesTree == null) {
			accessRulesTree = new JTree(new DefaultMutableTreeNode(ResourceUtil.getString("application.server")));
			accessRulesTree.addTreeSelectionListener(treeSelectionListener);
			accessRulesTree.setShowsRootHandles(true);
			accessRulesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			accessRulesTree.setCellRenderer(new MyTreeCellRenderer());
			accessRulesTree.setExpandsSelectedPaths(true);
		}
		
		return accessRulesTree;
	}
	
	/**
	 * This method initializes accessRulesTreeActionsPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	public JPanel getAccessRulesTreeActionsPanel() {
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
	 * This method initializes accessRulesTreePanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	public JPanel getAccessRulesTreePanel() {
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
	 * This method initializes accessRulesTreeScrollPane.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getAccessRulesTreeScrollPane() {
		if (accessRulesTreeScrollPane == null) {
			accessRulesTreeScrollPane = new JScrollPane();
			accessRulesTreeScrollPane.setViewportView(getAccessRulesTree());
		}
		
		return accessRulesTreeScrollPane;
	}
	
	/**
	 * This method initializes addAccessRuleButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getAddAccessRuleButton() {
		if (addAccessRuleButton == null) {
			addAccessRuleButton = new JButton();
			addAccessRuleButton.addActionListener(actionListener);
			addAccessRuleButton.setActionCommand(Constants.ADD_ACCESS_RULE_ACTION);
			addAccessRuleButton.setIcon(ResourceUtil.addAccessRuleIcon);
			addAccessRuleButton.setText(ResourceUtil.getString("button.add"));
			addAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.addaccessrule.tooltip"));			
		}
		
		return addAccessRuleButton;
	}
	
	/**
	 * This method initializes addAccessRuleButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getAddProjectAccessRulesButton() {
		if (addProjectAccessRulesButton == null) {
			addProjectAccessRulesButton = new JButton();
			addProjectAccessRulesButton.addActionListener(actionListener);
			addProjectAccessRulesButton.setActionCommand(Constants.ADD_PROJECT_ACCESS_RULES_ACTION);
			addProjectAccessRulesButton.setIcon(ResourceUtil.addProjectAccessRulesIcon);
			addProjectAccessRulesButton.setText(ResourceUtil.getString("button.addProjectAccessRules"));
			addProjectAccessRulesButton.setToolTipText(ResourceUtil.getString("mainframe.button.addprojectaccessrules.tooltip"));			
		}
		
		return addProjectAccessRulesButton;
	}
	
	/**
	 * This method initializes deleteAccessRuleButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getDeleteAccessRuleButton() {
		if (deleteAccessRuleButton == null) {
			deleteAccessRuleButton = new JButton();
			deleteAccessRuleButton.addActionListener(actionListener);
			deleteAccessRuleButton.setActionCommand(Constants.DELETE_ACCESS_RULE_ACTION);
			deleteAccessRuleButton.setIcon(ResourceUtil.deleteAccessRuleIcon);
			deleteAccessRuleButton.setText(ResourceUtil.getString("button.delete"));
			deleteAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.deleteaccessrule.tooltip"));
			deleteAccessRuleButton.setEnabled(false);
		}
		return deleteAccessRuleButton;
	}

	/**
	 * This method initializes deleteTreeItemButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getDeleteTreeItemButton() {
		if (deleteTreeItemButton == null) {
			deleteTreeItemButton = new JButton();
			deleteTreeItemButton.addActionListener(actionListener);
			deleteTreeItemButton.setText(ResourceUtil.getString("button.delete"));
			deleteTreeItemButton.setEnabled(false);
		}
		
		return deleteTreeItemButton;
	}
	
	/**
	 * This method initializes editAccessRuleButton.
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getEditAccessRuleButton() {
		if (editAccessRuleButton == null) {
			editAccessRuleButton = new JButton();
			editAccessRuleButton.addActionListener(actionListener);
			editAccessRuleButton.setActionCommand(Constants.EDIT_ACCESS_RULE_ACTION);
			editAccessRuleButton.setIcon(ResourceUtil.editAccessRuleIcon);
			editAccessRuleButton.setText(ResourceUtil.getString("button.edit"));
			editAccessRuleButton.setToolTipText(ResourceUtil.getString("mainframe.button.editaccessrule.tooltip"));
			editAccessRuleButton.setEnabled(false);
		}
		
		return editAccessRuleButton;
	}

	/**
	 * This method initializes editTreeItemButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getEditTreeItemButton() {
		if (editTreeItemButton == null) {
			editTreeItemButton = new JButton();
			editTreeItemButton.addActionListener(actionListener);
			editTreeItemButton.setText(ResourceUtil.getString("button.edit"));
			editTreeItemButton.setEnabled(false);
		}
		
		return editTreeItemButton;
	}
}
