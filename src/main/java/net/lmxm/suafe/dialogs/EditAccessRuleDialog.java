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
package net.lmxm.suafe.dialogs;

import net.lmxm.suafe.ActionConstants;
import net.lmxm.suafe.api.beans.AccessRule;
import net.lmxm.suafe.api.beans.Document;
import net.lmxm.suafe.api.beans.Message;
import net.lmxm.suafe.exceptions.AppException;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog that allows a user to add an access rule.
 *
 * @author Shaun Johnson
 */
public final class EditAccessRuleDialog extends ParentDialog implements ActionListener {

    private static final long serialVersionUID = -1001510687982587543L;

    private JButton saveButton = null;

    private JPanel buttonPanel = null;

    private JPanel buttonSubPanel = null;

    private JButton cancelButton = null;

    private JPanel formPanel = null;

    private JPanel jContentPane = null;

    private Message message = null;

    private AccessRuleForm accessRuleForm = null;

    private AccessRule accessRule = null;

    private Document document = null;

    /**
     * Default constructor.
     */
    public EditAccessRuleDialog(Document document, AccessRule accessRule, Message message) {
        super();

        this.document = document;
        this.accessRule = accessRule;
        this.message = message;
        this.message.setState(Message.CANCEL);

        initialize();
    }

    /**
     * ActionPerformed event handler.
     *
     * @param event ActionEvent object.
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals(ActionConstants.SAVE_ACTION)) {
            try {
                AccessRule rule = getAccessRuleForm().editAccessRule();

                message.setUserObject(rule);
                message.setState(Message.SUCCESS);
                dispose();
            }
            catch (AppException ex) {
                displayError(ex.getMessage());
            }
        }
        else if (event.getActionCommand().equals(ActionConstants.CANCEL_ACTION)) {
            message.setState(Message.CANCEL);
            dispose();
        }
    }

    /**
     * This method initializes saveButton.
     *
     * @return javax.swing.JButton
     */
    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = createButton("button.save", ActionConstants.SAVE_ACTION, this);
        }

        return saveButton;
    }

    private AccessRuleForm getAccessRuleForm() {
        if (accessRuleForm == null) {
            accessRuleForm = new AccessRuleForm(document, accessRule);
        }

        return accessRuleForm;
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
            buttonSubPanel.add(getSaveButton());
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
            formPanel = new JPanel(new GridLayout(1, 1));
            formPanel.add(getAccessRuleForm());
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
            jContentPane.add(getInstructionsPanel("editaccessrule.instructions"), BorderLayout.NORTH);
            jContentPane.add(getFormPanel(), BorderLayout.CENTER);
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }

        return jContentPane;
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle(ResourceUtil.getString("editaccessrule.title"));
        this.setContentPane(getJContentPane());

        getRootPane().setDefaultButton(saveButton);

        this.pack();
        this.setModal(true);
    }

}
