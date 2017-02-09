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
package net.lmxm.suafe.gui.dialogs;

import net.lmxm.suafe.gui.ActionConstants;
import net.lmxm.suafe.ApplicationDefaultsConstants;
import net.lmxm.suafe.api.beans.*;
import net.lmxm.suafe.exceptions.AppException;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog that allows a user to add an access rule.
 *
 * @author Shaun Johnson
 */
public final class AddProjectAccessRulesDialog extends ParentDialog implements ActionListener {
    private static final long serialVersionUID = -1001510687982587543L;

    private JButton addButton;

    private AccessRuleForm branchesForm;

    private JPanel buttonPanel;

    private JPanel buttonSubPanel;

    private JButton cancelButton;

    private JPanel formPanel;

    private JPanel jContentPane;

    private Message message;

    private AccessRuleForm tagsForm;

    private AccessRuleForm trunkForm;

    private String path;

    private Repository repository;

    private Document document;

    /**
     * Default constructor.
     */
    public AddProjectAccessRulesDialog(@Nonnull final Document document, @Nullable final Object userObject,
                                       final Message message) {
        if (userObject != null && userObject instanceof Repository) {
            this.repository = (Repository) userObject;
            this.path = ApplicationDefaultsConstants.DEFAULT_SVN_PATH;
        }
        else if (userObject != null && userObject instanceof Path) {
            Path path = (Path) userObject;

            this.repository = path.getRepository();
            this.path = path.getPath();
        }
        else {
            this.repository = null;
            this.path = ApplicationDefaultsConstants.DEFAULT_SVN_PATH;
        }

        this.document = document;
        this.message = message;
        this.message.setState(Message.CANCEL);

        initialize();
    }

    /**
     * ActionPerformed event handler.
     *
     * @param event ActionEvent object.
     */
    public void actionPerformed(@Nonnull final ActionEvent event) {
        if (event.getActionCommand().equals(ActionConstants.ADD_ACTION)) {
            try {
                getBranchesForm().addAccessRule();
                getTagsForm().addAccessRule();
                AccessRule rule = getTrunkForm().addAccessRule();

                message.setUserObject(rule);
                message.setState(Message.SUCCESS);
                dispose();
            }
            catch (final AppException ex) {
                displayError(ex.getMessage());
            }
        }
        else if (event.getActionCommand().equals(ActionConstants.CANCEL_ACTION)) {
            message.setState(Message.CANCEL);
            dispose();
        }
    }

    /**
     * This method initializes addButton.
     *
     * @return javax.swing.JButton
     */
    private JButton getAddButton() {
        if (addButton == null) {
            addButton = createButton("button.add", ActionConstants.ADD_ACTION, this);
        }

        return addButton;
    }

    private AccessRuleForm getBranchesForm() {
        if (branchesForm == null) {
            branchesForm = new AccessRuleForm(document, "branches", repository, path);
        }

        return branchesForm;
    }

    /**
     * This method initializes buttonPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel(new GridLayout(1, 1));
            buttonPanel.add(getButtonSubPanel());
        }

        return buttonPanel;
    }

    /**
     * This method initializes buttonSubPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getButtonSubPanel() {
        if (buttonSubPanel == null) {
            buttonSubPanel = new JPanel();
            buttonSubPanel.add(getAddButton());
            buttonSubPanel.add(getCancelButton());
        }

        return buttonSubPanel;
    }

    /**
     * This method initializes cancelButton.
     *
     * @return javax.swing.JButton
     */
    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = createButton("button.cancel", ActionConstants.CANCEL_ACTION, this);
        }

        return cancelButton;
    }

    /**
     * This method initializes formPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel(new GridLayout(3, 1));
            formPanel.add(getBranchesForm());
            formPanel.add(getTagsForm());
            formPanel.add(getTrunkForm());
        }

        return formPanel;
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel(new BorderLayout());
            jContentPane.add(getInstructionsPanel("addprojectaccessrules.instructions"), BorderLayout.NORTH);
            jContentPane.add(getFormPanel(), BorderLayout.CENTER);
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }

        return jContentPane;
    }

    private AccessRuleForm getTagsForm() {
        if (tagsForm == null) {
            tagsForm = new AccessRuleForm(document, "tags", repository, path);
        }

        return tagsForm;
    }

    private AccessRuleForm getTrunkForm() {
        if (trunkForm == null) {
            trunkForm = new AccessRuleForm(document, "trunk", repository, path);
        }

        return trunkForm;
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(ResourceUtil.getString("addprojectaccessrules.title"));
        this.setContentPane(getJContentPane());

        getRootPane().setDefaultButton(addButton);

        this.pack();
        this.setModal(true);
    }
}
