/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.xiaoniu.suafe.dialogs;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.ApplicationDefaultsConstants;
import org.xiaoniu.suafe.api.beans.*;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.resources.ResourceUtil;

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
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle(ResourceUtil.getString("addprojectaccessrules.title"));
        this.setContentPane(getJContentPane());

        getRootPane().setDefaultButton(addButton);

        this.pack();
        this.setModal(true);
    }
}
