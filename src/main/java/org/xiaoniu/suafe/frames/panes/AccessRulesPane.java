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

package org.xiaoniu.suafe.frames.panes;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.ApplicationDefaultsConstants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.frames.menus.AccessRulesPopupMenu;
import org.xiaoniu.suafe.frames.menus.PopupMenuListener;
import org.xiaoniu.suafe.renderers.MyTableCellRenderer;
import org.xiaoniu.suafe.renderers.MyTreeCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public final class AccessRulesPane extends BaseSplitPane {
    private static final long serialVersionUID = 4055338694233688725L;

    private JPanel accessRuleActionsPanel;

    private JPanel accessRulesFormatPanel;

    private JPanel accessRulesPanel;

    private JScrollPane accessRulesScrollPane;

    private JTable accessRulesTable;

    private JTree accessRulesTree;

    private JPanel accessRulesTreeActionsPanel;

    private JPanel accessRulesTreePanel;

    private AccessRulesPopupMenu accessRulestreePopupMenu;

    private JScrollPane accessRulesTreeScrollPane;

    private JButton addAccessRuleButton;

    private JButton addProjectAccessRulesButton;

    private JButton deleteAccessRuleButton;

    private JButton deleteTreeItemButton;

    private JButton editAccessRuleButton;

    private JButton editTreeItemButton;

    private final ActionListener actionListener;

    private final ListSelectionListener listSelectionListener;

    private final MouseListener mouseListener;

    private final TreeSelectionListener treeSelectionListener;

    public AccessRulesPane(@Nonnull final ActionListener actionListener,
                           @Nonnull final ListSelectionListener listSelectionListener,
                           @Nonnull final MouseListener mouseListener,
                           @Nonnull final TreeSelectionListener treeSelectionListener) {
        this.actionListener = actionListener;
        this.mouseListener = mouseListener;
        this.listSelectionListener = listSelectionListener;
        this.treeSelectionListener = treeSelectionListener;

        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        setLeftComponent(getAccessRulesTreePanel());
        setRightComponent(getAccessRulesPanel());
        setDividerLocation(UserPreferences.getRulesPaneDividerLocation());

        // getAccessRulesTreePopupMenu();
    }

    /**
     * This method initializes accessRuleActionsPanel.
     *
     * @return javax.swing.JPanel
     */
    public JPanel getAccessRuleActionsPanel() {
        if (accessRuleActionsPanel == null) {
            accessRuleActionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
            accessRulesFormatPanel = new JPanel(new BorderLayout());
            accessRulesFormatPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));
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
            accessRulesPanel = new JPanel(new BorderLayout());
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
            accessRulesScrollPane = new JScrollPane(getAccessRulesTable());
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
            accessRulesTable.setRowHeight(ApplicationDefaultsConstants.DEFAULT_ACCESS_RULE_TABLE_ROW_HEIGHT);
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
            accessRulesTreeActionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
            accessRulesTreePanel.add(createLabel("mainframe.serverstructure"), BorderLayout.NORTH);
            accessRulesTreePanel.add(getAccessRulesTreeScrollPane(), BorderLayout.CENTER);
            accessRulesTreePanel.add(getAccessRulesTreeActionsPanel(), BorderLayout.SOUTH);
        }

        return accessRulesTreePanel;
    }

    /**
     * This method initializes groupsPopupMenu.
     *
     * @return GroupsPopupMenu
     */
    private AccessRulesPopupMenu getAccessRulesTreePopupMenu() {
        if (accessRulestreePopupMenu == null) {
            accessRulestreePopupMenu = new AccessRulesPopupMenu(actionListener);

            // Add listener to the list.
            final MouseListener popupListener = new PopupMenuListener(accessRulestreePopupMenu);
            getAccessRulesTree().addMouseListener(popupListener);
        }

        return accessRulestreePopupMenu;
    }

    /**
     * This method initializes accessRulesTreeScrollPane.
     *
     * @return javax.swing.JScrollPane
     */
    public JScrollPane getAccessRulesTreeScrollPane() {
        if (accessRulesTreeScrollPane == null) {
            accessRulesTreeScrollPane = new JScrollPane(getAccessRulesTree());
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
            addAccessRuleButton = createButton("button.add", "mainframe.button.addaccessrule.tooltip",
                    ResourceUtil.addAccessRuleIcon, ActionConstants.ADD_ACCESS_RULE_ACTION, actionListener);
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
            addProjectAccessRulesButton = createButton("button.addProjectAccessRules",
                    "mainframe.button.addprojectaccessrules.tooltip", ResourceUtil.addProjectAccessRulesIcon,
                    ActionConstants.ADD_PROJECT_ACCESS_RULES_ACTION, actionListener);
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
            deleteAccessRuleButton = createButton("button.delete", "mainframe.button.deleteaccessrule.tooltip",
                    ResourceUtil.deleteAccessRuleIcon, ActionConstants.DELETE_ACCESS_RULE_ACTION, actionListener);
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
            deleteTreeItemButton = createButton("button.delete", null, actionListener);
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
            editAccessRuleButton = createButton("button.edit", "mainframe.button.editaccessrule.tooltip",
                    ResourceUtil.editAccessRuleIcon, ActionConstants.EDIT_ACCESS_RULE_ACTION, actionListener);
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
            editTreeItemButton = createButton("button.edit", null, actionListener);
            editTreeItemButton.setEnabled(false);
        }

        return editTreeItemButton;
    }

    public void loadUserPreferences() {
        setDividerLocation(UserPreferences.getRulesPaneDividerLocation());
    }
}
