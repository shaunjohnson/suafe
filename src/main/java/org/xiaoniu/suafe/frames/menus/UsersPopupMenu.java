/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.xiaoniu.suafe.frames.menus;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.event.ActionListener;

public final class UsersPopupMenu extends JPopupMenu {
    private static final long serialVersionUID = 6018077204492947280L;

    private final ActionListener actionListener;

    private JMenuItem addUserPopupMenuItem;

    private JMenuItem changeMembershipPopupMenuItem;

    private JMenuItem cloneUserPopupMenuItem;

    private JMenuItem deleteUserPopupMenuItem;

    private JMenuItem renameUserPopupMenuItem;

    public UsersPopupMenu(@Nonnull final ActionListener actionListener) {
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
