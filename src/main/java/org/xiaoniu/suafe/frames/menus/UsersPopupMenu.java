package org.xiaoniu.suafe.frames.menus;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.swing.*;
import java.awt.event.ActionListener;

public final class UsersPopupMenu extends JPopupMenu {

    private static final long serialVersionUID = 6018077204492947280L;

    private ActionListener actionListener;

    private JMenuItem addUserPopupMenuItem = null;

    private JMenuItem changeMembershipPopupMenuItem = null;

    private JMenuItem cloneUserPopupMenuItem = null;

    private JMenuItem deleteUserPopupMenuItem = null;

    private JMenuItem renameUserPopupMenuItem = null;

    public UsersPopupMenu(ActionListener actionListener) {
        super();

        this.actionListener = actionListener;

        add(getAddUserPopupMenuItem());
        add(getRenameUserPopupMenuItem());
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
            addUserPopupMenuItem.setActionCommand(ActionConstants.ADD_USER_ACTION);
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
            changeMembershipPopupMenuItem.setActionCommand(ActionConstants.CHANGE_MEMBERSHIP_ACTION);
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
            cloneUserPopupMenuItem.setActionCommand(ActionConstants.CLONE_USER_ACTION);
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
            deleteUserPopupMenuItem.setActionCommand(ActionConstants.DELETE_USER_ACTION);
            deleteUserPopupMenuItem.setIcon(ResourceUtil.deleteUserIcon);
            deleteUserPopupMenuItem.setText(ResourceUtil.getString("button.delete"));
            deleteUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deleteuser.tooltip"));
            deleteUserPopupMenuItem.setEnabled(false);
        }

        return deleteUserPopupMenuItem;
    }

    /**
     * This method initializes renameUserPopupMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getRenameUserPopupMenuItem() {
        if (renameUserPopupMenuItem == null) {
            renameUserPopupMenuItem = new JMenuItem();
            renameUserPopupMenuItem.addActionListener(actionListener);
            renameUserPopupMenuItem.setActionCommand(ActionConstants.RENAME_USER_ACTION);
            renameUserPopupMenuItem.setIcon(ResourceUtil.renameUserIcon);
            renameUserPopupMenuItem.setText(ResourceUtil.getString("button.rename"));
            renameUserPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.renameuser.tooltip"));
            renameUserPopupMenuItem.setEnabled(false);
        }

        return renameUserPopupMenuItem;
    }
}
