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

package net.lmxm.suafe.frames.toolbars;

import net.lmxm.suafe.ActionConstants;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.event.ActionListener;

public final class MainFrameToolBar extends JToolBar {
    private static final long serialVersionUID = 4040931707131792923L;

    private JButton addAccessRuleToolbarButton;

    private JButton addGroupToolbarButton;

    private JButton addUserToolbarButton;

    private JButton newFileToolbarButton;

    private JButton openFileToolbarButton;

    private JButton previewToolbarButton;

    private JButton saveFileAsToolbarButton;

    private JButton saveFileToolbarButton;

    private ActionListener actionListener;

    public MainFrameToolBar(@Nonnull final ActionListener actionListener) {
        this.actionListener = actionListener;

        setFloatable(false);

        add(getNewFileToolbarButton());
        add(getOpenFileToolbarButton());
        add(getSaveFileToolbarButton());
        add(getSaveFileAsToolbarButton());
        add(getAddUserToolbarButton());
        add(getAddGroupToolbarButton());
        add(getAddAccessRuleToolbarButton());
        add(getPreviewToolbarButton());
    }

    /**
     * This method initializes addAccessRuleToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getAddAccessRuleToolbarButton() {
        if (addAccessRuleToolbarButton == null) {
            addAccessRuleToolbarButton = new JButton();
            addAccessRuleToolbarButton.addActionListener(actionListener);
            addAccessRuleToolbarButton.setActionCommand(ActionConstants.ADD_ACCESS_RULE_ACTION);
            addAccessRuleToolbarButton.setIcon(ResourceUtil.addAccessRuleIcon);
            addAccessRuleToolbarButton.setText(ResourceUtil.getString("mainframe.button.addaccessrule"));
            addAccessRuleToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.addaccessrule.tooltip"));
        }

        return addAccessRuleToolbarButton;
    }

    /**
     * This method initializes addGroupToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getAddGroupToolbarButton() {
        if (addGroupToolbarButton == null) {
            addGroupToolbarButton = new JButton();
            addGroupToolbarButton.addActionListener(actionListener);
            addGroupToolbarButton.setActionCommand(ActionConstants.ADD_GROUP_ACTION);
            addGroupToolbarButton.setIcon(ResourceUtil.addGroupIcon);
            addGroupToolbarButton.setText(ResourceUtil.getString("mainframe.button.addgroup"));
            addGroupToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.addgroup.tooltip"));
        }

        return addGroupToolbarButton;
    }

    /**
     * This method initializes addUserToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getAddUserToolbarButton() {
        if (addUserToolbarButton == null) {
            addUserToolbarButton = new JButton();
            addUserToolbarButton.addActionListener(actionListener);
            addUserToolbarButton.setActionCommand(ActionConstants.ADD_USER_ACTION);
            addUserToolbarButton.setIcon(ResourceUtil.addUserIcon);
            addUserToolbarButton.setText(ResourceUtil.getString("mainframe.button.adduser"));
            addUserToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.adduser.tooltip"));
        }

        return addUserToolbarButton;
    }

    /**
     * This method initializes newFileToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getNewFileToolbarButton() {
        if (newFileToolbarButton == null) {
            newFileToolbarButton = new JButton();
            newFileToolbarButton.addActionListener(actionListener);
            newFileToolbarButton.setActionCommand(ActionConstants.NEW_FILE_ACTION);
            newFileToolbarButton.setIcon(ResourceUtil.newFileIcon);
            newFileToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.new.tooltip"));
        }

        return newFileToolbarButton;
    }

    /**
     * This method initializes openFileToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getOpenFileToolbarButton() {
        if (openFileToolbarButton == null) {
            openFileToolbarButton = new JButton();
            openFileToolbarButton.addActionListener(actionListener);
            openFileToolbarButton.setActionCommand(ActionConstants.OPEN_FILE_ACTION);
            openFileToolbarButton.setIcon(ResourceUtil.openFileIcon);
            openFileToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.open.tooltip"));
        }

        return openFileToolbarButton;
    }

    /**
     * This method initializes previewToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getPreviewToolbarButton() {
        if (previewToolbarButton == null) {
            previewToolbarButton = new JButton();
            previewToolbarButton.addActionListener(actionListener);
            previewToolbarButton.setActionCommand(ActionConstants.PREVIEW_ACTION);
            previewToolbarButton.setIcon(ResourceUtil.previewIcon);
            previewToolbarButton.setText(ResourceUtil.getString("mainframe.button.preview"));
            previewToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.preview.tooltip"));
        }

        return previewToolbarButton;
    }

    /**
     * This method initializes saveFileAsToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getSaveFileAsToolbarButton() {
        if (saveFileAsToolbarButton == null) {
            saveFileAsToolbarButton = new JButton();
            saveFileAsToolbarButton.addActionListener(actionListener);
            saveFileAsToolbarButton.setActionCommand(ActionConstants.SAVE_FILE_AS_ACTION);
            saveFileAsToolbarButton.setIcon(ResourceUtil.saveFileAsIcon);
            saveFileAsToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.saveas.tooltip"));
        }
        return saveFileAsToolbarButton;
    }

    /**
     * This method initializes saveFileToolbarButton.
     *
     * @return javax.swing.JButton
     */
    public JButton getSaveFileToolbarButton() {
        if (saveFileToolbarButton == null) {
            saveFileToolbarButton = new JButton();
            saveFileToolbarButton.addActionListener(actionListener);
            saveFileToolbarButton.setActionCommand(ActionConstants.SAVE_FILE_ACTION);
            saveFileToolbarButton.setIcon(ResourceUtil.saveFileIcon);
            saveFileToolbarButton.setToolTipText(ResourceUtil.getString("mainframe.button.save.tooltip"));
        }
        return saveFileToolbarButton;
    }
}
