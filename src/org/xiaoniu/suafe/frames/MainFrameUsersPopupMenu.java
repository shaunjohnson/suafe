package org.xiaoniu.suafe.frames;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class MainFrameUsersPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 6018077204492947280L;

	private ActionListener actionListener;
	
	private JMenuItem addUserPopupMenuItem = null;  
	
	private JMenuItem changeMembershipPopupMenuItem = null;
	
	private JMenuItem cloneUserPopupMenuItem = null;
	
	private JMenuItem deleteUserPopupMenuItem = null;
	
	private JMenuItem editUserPopupMenuItem = null;
	
	public MainFrameUsersPopupMenu(ActionListener actionListener) {
		super();
		
		this.actionListener = actionListener;
		
		add(getAddUserPopupMenuItem());
		add(getEditUserPopupMenuItem());
		add(getDeleteUserPopupMenuItem());
		add(getChangeMembershipPopupMenuItem());
		add(getCloneUserPopupMenuItem());
	}
	
	/**
	 * This method initializes addUserPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getAddUserPopupMenuItem() {
		if (addUserPopupMenuItem == null) {
			addUserPopupMenuItem = new JMenuItem();
			addUserPopupMenuItem.addActionListener(actionListener);
			addUserPopupMenuItem.setActionCommand(Constants.ADD_USER_ACTION);
			addUserPopupMenuItem.setIcon(ResourceUtil.addUserIcon);
			addUserPopupMenuItem.setText(ResourceUtil.getString("button.add"));
			addUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
		}
		
		return addUserPopupMenuItem;
	}

	/**
	 * This method initializes changeMembershipPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getChangeMembershipPopupMenuItem() {
		if (changeMembershipPopupMenuItem == null) {
			changeMembershipPopupMenuItem = new JMenuItem();
			changeMembershipPopupMenuItem.addActionListener(actionListener);
			changeMembershipPopupMenuItem.setActionCommand(Constants.CHANGE_MEMBERSHIP_ACTION);
			changeMembershipPopupMenuItem.setIcon(ResourceUtil.changeMembershipIcon);
			changeMembershipPopupMenuItem.setText(ResourceUtil.getString("mainframe.button.changemembership"));
			changeMembershipPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.changemembership.tooltip"));
			changeMembershipPopupMenuItem.setEnabled(false);
		}
		
		return changeMembershipPopupMenuItem;
	}

	/**
	 * This method initializes cloneUserPopupMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	public JMenuItem getCloneUserPopupMenuItem() {
		if (cloneUserPopupMenuItem == null) {
			cloneUserPopupMenuItem = new JMenuItem();
			cloneUserPopupMenuItem.addActionListener(actionListener);
			cloneUserPopupMenuItem.setActionCommand(Constants.CLONE_USER_ACTION);
			cloneUserPopupMenuItem.setIcon(ResourceUtil.cloneUserIcon);
			cloneUserPopupMenuItem.setText(ResourceUtil.getString("menu.clone"));
			cloneUserPopupMenuItem.setEnabled(false);
		}
		return cloneUserPopupMenuItem;
	}

	/**
	 * This method initializes deleteUserPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getDeleteUserPopupMenuItem() {
		if (deleteUserPopupMenuItem == null) {
			deleteUserPopupMenuItem = new JMenuItem();
			deleteUserPopupMenuItem.addActionListener(actionListener);
			deleteUserPopupMenuItem.setActionCommand(Constants.DELETE_USER_ACTION);
			deleteUserPopupMenuItem.setIcon(ResourceUtil.deleteUserIcon);
			deleteUserPopupMenuItem.setText(ResourceUtil.getString("button.delete"));
			deleteUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deleteuser.tooltip"));
			deleteUserPopupMenuItem.setEnabled(false);
		}
		
		return deleteUserPopupMenuItem;
	}
	
	/**
	 * This method initializes editUserPopupMenuItem.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getEditUserPopupMenuItem() {
		if (editUserPopupMenuItem == null) {
			editUserPopupMenuItem = new JMenuItem();
			editUserPopupMenuItem.addActionListener(actionListener);
			editUserPopupMenuItem.setActionCommand(Constants.EDIT_USER_ACTION);
			editUserPopupMenuItem.setIcon(ResourceUtil.editUserIcon);
			editUserPopupMenuItem.setText(ResourceUtil.getString("button.edit"));
			editUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.edituser.tooltip"));
			editUserPopupMenuItem.setEnabled(false);
		}
		
		return editUserPopupMenuItem;
	}
}
