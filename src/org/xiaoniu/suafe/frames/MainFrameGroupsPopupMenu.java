package org.xiaoniu.suafe.frames;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class MainFrameGroupsPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = -8798702081535764095L;

	private ActionListener actionListener = null;
	
	private JMenuItem addGroupPopupMenuItem = null;
	
	private JMenuItem addRemoveMembersPopupMenuItem = null;	
	
	private JMenuItem cloneGroupPopupMenuItem = null;
	
	private JMenuItem deleteGroupPopupMenuItem = null;
	
	private JMenuItem editGroupPopupMenuItem = null;
	
	public MainFrameGroupsPopupMenu(ActionListener actionListener) {
		super();
		
		this.actionListener = actionListener;
		
		add(getAddGroupPopupMenuItem());
		add(getEditGroupPopupMenuItem());
		add(getDeleteGroupPopupMenuItem());
		add(getAddRemoveMembersPopupMenuItem());
		add(getCloneGroupPopupMenuItem());
	}

	/**
	 * This method initializes addGroupPopupMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getAddGroupPopupMenuItem() {
		if (addGroupPopupMenuItem == null) {
			addGroupPopupMenuItem = new JMenuItem();
			addGroupPopupMenuItem.addActionListener(actionListener);
			addGroupPopupMenuItem.setActionCommand(Constants.ADD_GROUP_ACTION);
			addGroupPopupMenuItem.setIcon(ResourceUtil.addGroupIcon);
			addGroupPopupMenuItem.setText(ResourceUtil.getString("button.add"));
			addGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));
		}
		
		return addGroupPopupMenuItem;
	}

	/**
	 * This method initializes addRemoveMembersPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getAddRemoveMembersPopupMenuItem() {
		if (addRemoveMembersPopupMenuItem == null) {
			addRemoveMembersPopupMenuItem = new JMenuItem();
			addRemoveMembersPopupMenuItem.addActionListener(actionListener);
			addRemoveMembersPopupMenuItem.setActionCommand(Constants.ADD_REMOVE_MEMBERS_ACTION);
			addRemoveMembersPopupMenuItem.setIcon(ResourceUtil.addRemoveMembersIcon);
			addRemoveMembersPopupMenuItem.setText(ResourceUtil.getString("mainframe.button.addremovemembers"));
			addRemoveMembersPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.addremovemembers.tooltip"));
			addRemoveMembersPopupMenuItem.setEnabled(false);
		}
		
		return addRemoveMembersPopupMenuItem;
	}

	/**
	 * This method initializes cloneGroupPopupMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	public JMenuItem getCloneGroupPopupMenuItem() {
		if (cloneGroupPopupMenuItem == null) {
			cloneGroupPopupMenuItem = new JMenuItem();
			cloneGroupPopupMenuItem.addActionListener(actionListener);
			cloneGroupPopupMenuItem.setActionCommand(Constants.CLONE_GROUP_ACTION);
			cloneGroupPopupMenuItem.setIcon(ResourceUtil.cloneGroupIcon);
			cloneGroupPopupMenuItem.setText(ResourceUtil.getString("menu.clone"));
			cloneGroupPopupMenuItem.setEnabled(false);
		}
		
		return cloneGroupPopupMenuItem;
	}

	/**
	 * This method initializes deleteGroupPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getDeleteGroupPopupMenuItem() {
		if (deleteGroupPopupMenuItem == null) {
			deleteGroupPopupMenuItem = new JMenuItem();
			deleteGroupPopupMenuItem.addActionListener(actionListener);
			deleteGroupPopupMenuItem.setActionCommand(Constants.DELETE_GROUP_ACTION);
			deleteGroupPopupMenuItem.setIcon(ResourceUtil.deleteGroupIcon);
			deleteGroupPopupMenuItem.setText(ResourceUtil.getString("button.delete"));
			deleteGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deletegroup.tooltip"));			
			deleteGroupPopupMenuItem.setEnabled(false);
		}
		
		return deleteGroupPopupMenuItem;
	}
	
	/**
	 * This method initializes editGroupPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getEditGroupPopupMenuItem() {
		if (editGroupPopupMenuItem == null) {
			editGroupPopupMenuItem = new JMenuItem();
			editGroupPopupMenuItem.addActionListener(actionListener);
			editGroupPopupMenuItem.setActionCommand(Constants.EDIT_GROUP_ACTION);
			editGroupPopupMenuItem.setIcon(ResourceUtil.editGroupIcon);
			editGroupPopupMenuItem.setText(ResourceUtil.getString("button.edit"));
			editGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.editgroup.tooltip"));
			editGroupPopupMenuItem.setEnabled(false);
		}
		
		return editGroupPopupMenuItem;
	}
}
