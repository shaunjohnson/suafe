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

package net.lmxm.suafe.frames.panes;

import net.lmxm.suafe.ActionConstants;
import net.lmxm.suafe.ApplicationDefaultsConstants;
import net.lmxm.suafe.GuiConstants;
import net.lmxm.suafe.UserPreferences;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.GroupMemberObject;
import net.lmxm.suafe.renderers.GroupListCellRenderer;
import net.lmxm.suafe.renderers.GroupMemberListCellRenderer;
import net.lmxm.suafe.renderers.MyTableCellRenderer;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public final class GroupsPane extends BaseSplitPane {
    private static final long serialVersionUID = -743618351601446392L;

    private JButton addGroupButton;

    private JButton addRemoveMembersButton;

    private JButton cloneGroupButton;

    private JButton deleteGroupButton;

    private JButton renameGroupButton;

    private JPanel groupAccessRulesPanel;

    private JScrollPane groupAccessRulesScrollPane;

    private JTable groupAccessRulesTable;

    private JPanel groupActionsPanel;

    private JPanel groupDetailsPanel;

    private JSplitPane groupDetailsSplitPanel;

    private JList<Group> groupList;

    private JPanel groupListPanel;

    private JScrollPane groupListScrollPane;

    private JList<GroupMemberObject> groupMemberList;

    private JPanel groupMemberListActionsPanel;

    private JScrollPane groupMemberListScrollPane;

    private JPanel groupMembersPanel;

    private final ActionListener actionListener;

    private final KeyListener keyListener;

    private final ListSelectionListener listSelectionListener;

    private final MouseListener mouseListener;

    public GroupsPane(@Nonnull final ActionListener actionListener,
                      @Nonnull final KeyListener keyListener,
                      @Nonnull final ListSelectionListener listSelectionListener,
                      @Nonnull final MouseListener mouseListener) {
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
    private JButton getAddGroupButton() {
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
    private JPanel getGroupAccessRulesPanel() {
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
    private JScrollPane getGroupAccessRulesScrollPane() {
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
    private JPanel getGroupActionsPanel() {
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
    private JPanel getGroupDetailsPanel() {
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
    public JList<Group> getGroupList() {
        if (groupList == null) {
            groupList = new JList<>();
            groupList.addKeyListener(keyListener);
            groupList.addListSelectionListener(listSelectionListener);
            groupList.addMouseListener(mouseListener);
            groupList.setCellRenderer(new GroupListCellRenderer());
            groupList.setFont(GuiConstants.FONT_PLAIN);
        }

        return groupList;
    }

    /**
     * This method initializes groupListPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getGroupListPanel() {
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
    private JScrollPane getGroupListScrollPane() {
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
    public JList<GroupMemberObject> getGroupMemberList() {
        if (groupMemberList == null) {
            groupMemberList = new JList<>();
            groupMemberList.addKeyListener(keyListener);
            groupMemberList.addMouseListener(mouseListener);
            groupMemberList.setCellRenderer(new GroupMemberListCellRenderer());
            groupMemberList.setFont(GuiConstants.FONT_PLAIN);
        }

        return groupMemberList;
    }

    /**
     * This method initializes groupMemberListActionsPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getGroupMemberListActionsPanel() {
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
    private JScrollPane getGroupMemberListScrollPane() {
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
    private JPanel getGroupMembersPanel() {
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
