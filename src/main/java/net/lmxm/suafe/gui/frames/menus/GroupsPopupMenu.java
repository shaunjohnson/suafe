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

package net.lmxm.suafe.gui.frames.menus;

import net.lmxm.suafe.gui.ActionConstants;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.event.ActionListener;

public final class GroupsPopupMenu extends JPopupMenu {
    private static final long serialVersionUID = -8798702081535764095L;

    private final ActionListener actionListener;

    private JMenuItem addGroupPopupMenuItem;

    private JMenuItem addRemoveMembersPopupMenuItem;

    private JMenuItem cloneGroupPopupMenuItem;

    private JMenuItem deleteGroupPopupMenuItem;

    private JMenuItem renameGroupPopupMenuItem;

    public GroupsPopupMenu(@Nonnull final ActionListener actionListener) {
        this.actionListener = actionListener;

        add(getAddGroupPopupMenuItem());
        add(getRenameGroupPopupMenuItem());
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
            addGroupPopupMenuItem.setActionCommand(ActionConstants.ADD_GROUP_ACTION);
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
            addRemoveMembersPopupMenuItem.setActionCommand(ActionConstants.ADD_REMOVE_MEMBERS_ACTION);
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
            cloneGroupPopupMenuItem.setActionCommand(ActionConstants.CLONE_GROUP_ACTION);
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
            deleteGroupPopupMenuItem.setActionCommand(ActionConstants.DELETE_GROUP_ACTION);
            deleteGroupPopupMenuItem.setIcon(ResourceUtil.deleteGroupIcon);
            deleteGroupPopupMenuItem.setText(ResourceUtil.getString("button.delete"));
            deleteGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.deletegroup.tooltip"));
            deleteGroupPopupMenuItem.setEnabled(false);
        }

        return deleteGroupPopupMenuItem;
    }

    /**
     * This method initializes renameGroupPopupMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    public JMenuItem getRenameGroupPopupMenuItem() {
        if (renameGroupPopupMenuItem == null) {
            renameGroupPopupMenuItem = new JMenuItem();
            renameGroupPopupMenuItem.addActionListener(actionListener);
            renameGroupPopupMenuItem.setActionCommand(ActionConstants.RENAME_GROUP_ACTION);
            renameGroupPopupMenuItem.setIcon(ResourceUtil.renameGroupIcon);
            renameGroupPopupMenuItem.setText(ResourceUtil.getString("button.rename"));
            renameGroupPopupMenuItem.setToolTipText(ResourceUtil.getString("mainframe.button.renamegroup.tooltip"));
            renameGroupPopupMenuItem.setEnabled(false);
        }

        return renameGroupPopupMenuItem;
    }
}
