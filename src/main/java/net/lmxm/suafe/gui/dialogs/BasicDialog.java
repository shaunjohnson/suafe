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
import net.lmxm.suafe.api.beans.*;
import net.lmxm.suafe.exceptions.AppException;
import net.lmxm.suafe.exceptions.ValidatorException;
import org.apache.commons.lang3.StringUtils;
import net.lmxm.suafe.UserPreferences;
import net.lmxm.suafe.resources.ResourceUtil;
import net.lmxm.suafe.validators.Validator;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog that allows the user to add a user.
 *
 * @author Shaun Johnson
 */
public final class BasicDialog extends ParentDialog implements ActionListener {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7533467798513820728L;

    public static String TYPE_ADD_GROUP = "addgroup";

    public static String TYPE_ADD_REPOSITORY = "addrepository";

    public static String TYPE_ADD_USER = "adduser";

    public static String TYPE_CLONE_GROUP = "clonegroup";

    public static String TYPE_CLONE_USER = "cloneuser";

    public static String TYPE_EDIT_PATH = "editpath";

    public static String TYPE_RENAME_GROUP = "renamegroup";

    public static String TYPE_RENAME_REPOSITORY = "renamerepository";

    public static String TYPE_RENAME_USER = "renameuser";

    private JTextField aliasText;

    private JPanel buttonPanel;

    private JPanel buttonSubPanel;

    private JButton cancelButton;

    private Document document;

    private JPanel formAliasPanel;

    private JPanel formNamePanel;

    private JPanel formPanel;

    private Group group;

    private JPanel iconPanel;

    private JPanel jContentPane;

    private final Message message;

    private JTextField nameText;

    private Path path;

    private Repository repository;

    private JButton saveButton;

    private String type;

    private User user;

    /**
     * Group dialog constructor.
     */
    public BasicDialog(@Nonnull final Document document, final String type, final Group group, final Message message) {
        this.document = document;
        this.type = type;
        this.group = group;
        this.message = message;
        this.message.setState(Message.CANCEL);

        initialize(group.getName());
    }

    /**
     * Default constructor.
     */
    public BasicDialog(@Nonnull final Document document, final String type, final Message message) {
        this.document = document;
        this.type = type;
        this.message = message;
        this.message.setState(Message.CANCEL);

        initialize(null);
    }

    /**
     * Group dialog constructor.
     */
    public BasicDialog(@Nonnull final Document document, final String type, final Path path, final Message message) {
        this.document = document;
        this.type = type;
        this.path = path;
        this.message = message;
        this.message.setState(Message.CANCEL);

        initialize(path.getPath());
    }

    /**
     * Repository dialog constructor.
     */
    public BasicDialog(@Nonnull final Document document, final String type, final Repository repository,
                       final Message message) {
        this.document = document;
        this.type = type;
        this.repository = repository;
        this.message = message;
        this.message.setState(Message.CANCEL);

        initialize(repository.getName());
    }

    /**
     * User dialog constructor.
     */
    public BasicDialog(@Nonnull final Document document, final String type, final User user, final Message message) {
        this.document = document;
        this.type = type;
        this.user = user;
        this.message = message;
        this.message.setState(Message.CANCEL);

        initialize(user.getName(), user.getAlias());
    }

    public void actionPerformed(@Nonnull final ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals(ActionConstants.SAVE_ACTION)) {
            try {
                final String name = getNameText().getText();
                final String alias = getAliasText().getText();

                if (type.equals(TYPE_ADD_USER)) {
                    addUser(name, alias);
                }
                else if (type.equals(TYPE_CLONE_USER)) {
                    cloneUser(name, alias);
                }
                else if (type.equals(TYPE_RENAME_USER)) {
                    renameUser(name, alias);
                }
                else if (type.equals(TYPE_ADD_GROUP)) {
                    addGroup(name);
                }
                else if (type.equals(TYPE_RENAME_GROUP)) {
                    renameGroup(name);
                }
                else if (type.equals(TYPE_CLONE_GROUP)) {
                    cloneGroup(name);
                }
                else if (type.equals(TYPE_ADD_REPOSITORY)) {
                    addRepository(name);
                }
                else if (type.equals(TYPE_RENAME_REPOSITORY)) {
                    editRepository(name);
                }
                else if (type.equals(TYPE_EDIT_PATH)) {
                    editPath(name);
                }
            }
            catch (final AppException e) {
                displayError(e.getMessage());
            }
        }
        else if (actionEvent.getActionCommand().equals(ActionConstants.CANCEL_ACTION)) {
            message.setState(Message.CANCEL);
            dispose();
        }
    }

    private void addGroup(final String groupName) throws AppException {
        validateGroupName(groupName);

        if (document.findGroup(groupName) == null) {
            message.setUserObject(document.addGroup(groupName));
            message.setState(Message.SUCCESS);
            dispose();
        }
        else {
            displayError(ResourceUtil.getFormattedString(type + ".error.groupalreadyexists", groupName));
        }
    }

    private void addRepository(final String repositoryName) throws AppException {
        validateRepositoryName(repositoryName);

        if (document.findRepository(repositoryName) == null) {
            Repository repository = document.addRepository(repositoryName);

            message.setUserObject(repository);
            message.setState(Message.SUCCESS);

            dispose();
        }
        else {
            displayError(ResourceUtil.getFormattedString(type + ".error.repositoryalreadyexists", repositoryName));
        }
    }

    private void addUser(final String userName, final String alias) throws AppException {
        validateUserName(userName);

        final User user = document.findUser(userName);

        if (user != null) {
            displayError(ResourceUtil.getFormattedString(type + ".error.useralreadyexists", userName));
        }
        else {
            if (StringUtils.isBlank(alias)) {
                message.setUserObject(document.addUser(userName));
                message.setState(Message.SUCCESS);
                dispose();
            }
            else {
                validateAlias(alias);

                final User userByAlias = document.findUserByAlias(alias);

                if (userByAlias != null) {
                    displayError(ResourceUtil.getFormattedString(type + ".error.useralreadyexists", userName));
                }
                else {
                    message.setUserObject(document.addUser(userName, alias));
                    message.setState(Message.SUCCESS);
                    dispose();
                }
            }
        }
    }

    private void cloneGroup(final String groupName) throws AppException {
        validateGroupName(groupName);

        final Group existingGroup = document.findGroup(groupName);

        if (existingGroup == null || existingGroup == group) {
            message.setUserObject(document.cloneGroup(group, groupName));
            message.setState(Message.SUCCESS);
            dispose();
        }
        else {
            displayError(ResourceUtil.getFormattedString(type + ".error.groupalreadyexists", groupName));
        }
    }

    private void cloneUser(final String userName, final String alias) throws AppException {
        validateUserName(userName);
        validateAlias(alias);

        final User user = document.findUser(userName);
        final User userByAlias = document.findUserByAlias(alias);

        if (user != null) {
            displayError(ResourceUtil.getFormattedString("cloneuser.error.useralreadyexists", userName));
        }
        else if (userByAlias != null) {
            displayError(ResourceUtil.getFormattedString("cloneuser.error.useralreadyexists", userName));
        }
        else {
            message.setUserObject(document.cloneUser(user, userName, alias));
            message.setState(Message.SUCCESS);
            dispose();
        }
    }

    private void editPath(final String pathString) throws AppException {
        validatePath(pathString);

        Path existingPath = document.findPath(path.getRepository(), pathString);

        if (existingPath == null || existingPath == path) {
            path.setPath(pathString);
            message.setUserObject(path);
            message.setState(Message.SUCCESS);
            dispose();
        }
        else {
            Object[] args = new Object[2];
            args[0] = pathString;
            args[1] = path.getRepository();
            displayError(ResourceUtil.getFormattedString(type + ".error.pathrepositoryalreadyexists", args));
        }
    }

    private void editRepository(final String repositoryName) throws AppException {
        validateRepositoryName(repositoryName);

        final Repository existingRepository = document.findRepository(repositoryName);

        if (existingRepository == null || existingRepository == repository) {
            repository.setName(repositoryName);
            message.setUserObject(repository);
            message.setState(Message.SUCCESS);
            dispose();
        }
        else {
            displayError(ResourceUtil
                    .getFormattedString("editrepository.error.repositoryalreadyexists", repositoryName));
        }
    }

    /**
     * This method initializes aliasText.
     *
     * @return javax.swing.JTextField
     */
    private JTextField getAliasText() {
        if (aliasText == null) {
            aliasText = new JTextField();
            aliasText.setFont(UserPreferences.getUserFont());
            aliasText.setPreferredSize(new Dimension(340, 21));
        }

        return aliasText;
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
     * This method initializes formAliasPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getFormAliasPanel() {
        if (formAliasPanel == null) {
            formAliasPanel = new JPanel(new FlowLayout());

            JLabel label = new JLabel(ResourceUtil.getString(type + ".label.alias"));

            label.setHorizontalAlignment(JLabel.RIGHT);
            label.setPreferredSize(new Dimension(100, 15));

            formAliasPanel.add(label);
            formAliasPanel.add(getAliasText());
        }

        return formAliasPanel;
    }

    /**
     * This method initializes formNamePanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getFormNamePanel() {
        if (formNamePanel == null) {
            formNamePanel = new JPanel(new FlowLayout());

            JLabel label = new JLabel(ResourceUtil.getString(type + ".label"));

            label.setHorizontalAlignment(JLabel.RIGHT);
            label.setPreferredSize(new Dimension(100, 15));

            formNamePanel.add(label);
            formNamePanel.add(getNameText());
        }

        return formNamePanel;
    }

    /**
     * This method initializes formPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
            formPanel.add(getFormNamePanel());

            if (type.equals(TYPE_ADD_USER) || type.equals(TYPE_CLONE_USER) || type.equals(TYPE_RENAME_USER)) {
                formPanel.add(getFormAliasPanel());
            }
        }

        return formPanel;
    }

    private JPanel getIconPanel() {
        if (iconPanel == null) {
            iconPanel = new JPanel();

            final JLabel iconLabel = new JLabel();

            if (type.equals(TYPE_ADD_GROUP)) {
                iconLabel.setIcon(ResourceUtil.fullSizeGroupIcon);
            }
            else if (type.equals(TYPE_ADD_REPOSITORY)) {
                // TODO
                // iconLabel.setIcon(ResourceUtil.fullSizeRepositoryIcon);
            }
            else if (type.equals(TYPE_ADD_USER)) {
                iconLabel.setIcon(ResourceUtil.fullSizeUserIcon);
            }
            else if (type.equals(TYPE_CLONE_GROUP)) {
                iconLabel.setIcon(ResourceUtil.fullSizeGroupIcon);
            }
            else if (type.equals(TYPE_CLONE_USER)) {
                iconLabel.setIcon(ResourceUtil.fullSizeUserIcon);
            }
            else if (type.equals(TYPE_RENAME_GROUP)) {
                iconLabel.setIcon(ResourceUtil.fullSizeGroupIcon);
            }
            else if (type.equals(TYPE_EDIT_PATH)) {
                // TODO
                // iconLabel.setIcon(ResourceUtil.fullSizePathIcon);
            }
            else if (type.equals(TYPE_RENAME_REPOSITORY)) {
                // TODO
                // iconLabel.setIcon(ResourceUtil.fullSizeRepositoryIcon);
            }
            else if (type.equals(TYPE_RENAME_USER)) {
                iconLabel.setIcon(ResourceUtil.fullSizeUserIcon);
            }
            else {
                // TODO
            }

            iconPanel.add(iconLabel);
        }

        return iconPanel;
    }

    /**
     * This method initializes jContentPane.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel(new BorderLayout());
            jContentPane.add(getInstructionsPanel(type + ".instructions"), BorderLayout.NORTH);
            jContentPane.add(getIconPanel(), BorderLayout.WEST);
            jContentPane.add(getFormPanel(), BorderLayout.CENTER);
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }

        return jContentPane;
    }

    /**
     * This method initializes nameText.
     *
     * @return javax.swing.JTextField
     */
    private JTextField getNameText() {
        if (nameText == null) {
            nameText = new JTextField();
            nameText.setFont(UserPreferences.getUserFont());
            nameText.setPreferredSize(new Dimension(340, 21));
        }

        return nameText;
    }

    /**
     * This method initializes addButton.
     *
     * @return javax.swing.JButton
     */
    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = createButton(type + ".savebutton", ActionConstants.SAVE_ACTION, this);
        }

        return saveButton;
    }

    /**
     * This method initializes this
     */
    private void initialize(String initialText) {
        initialize(initialText, null);
    }

    /**
     * This method initializes this
     */
    private void initialize(final String initialText, final String alias) {
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle(ResourceUtil.getString(type + ".title"));
        this.setContentPane(getJContentPane());

        getRootPane().setDefaultButton(saveButton);
        getNameText().setText(initialText);

        if (StringUtils.isNotBlank(alias)) {
            getAliasText().setText(alias);
        }

        this.pack();
        this.setModal(true);
    }

    private void renameGroup(final String groupName) throws AppException {
        validateGroupName(groupName);

        Group existingGroup = document.findGroup(groupName);

        if (existingGroup == null || existingGroup == group) {
            message.setUserObject(document.renameGroup(group, groupName));
            message.setState(Message.SUCCESS);
            dispose();
        }
        else {
            displayError(ResourceUtil.getFormattedString(type + ".error.groupalreadyexists", groupName));
        }
    }

    private void renameUser(final String userName, final String alias) throws AppException {
        validateUserName(userName);

        if (StringUtils.isNotBlank(alias)) {
            validateAlias(alias);
        }

        final User existingUser = document.findUser(userName);
        final User userByAlias = document.findUserByAlias(alias);

        if (existingUser != null && existingUser != user) {
            displayError(ResourceUtil.getFormattedString("cloneuser.error.useralreadyexists", userName));
        }
        else if (userByAlias != null && userByAlias != user) {
            displayError(ResourceUtil.getFormattedString("cloneuser.error.useralreadyexists", userName));
        }
        else {
            message.setUserObject(document.renameUser(user, userName, alias));
            message.setState(Message.SUCCESS);
            dispose();
        }
    }

    private void validateAlias(final String alias) throws ValidatorException {
        Validator.validateAlias(alias);
    }

    private void validateGroupName(final String groupName) throws ValidatorException {
        Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), groupName);
        Validator.validateGroupName(groupName);
    }

    private void validatePath(final String pathString) throws ValidatorException {
        Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), pathString);
        Validator.validatePath(pathString);
    }

    private void validateRepositoryName(final String repositoryName) throws ValidatorException {
        Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), repositoryName);
        Validator.validateRepositoryName(repositoryName);
    }

    private void validateUserName(final String userName) throws ValidatorException {
        Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), userName);
        Validator.validateUserName(userName);
    }
}
