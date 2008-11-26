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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;

/**
 * Dialog that allows the user to add a user.
 * 
 * @author Shaun Johnson
 */
public class BasicDialog extends ParentDialog implements ActionListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -7533467798513820728L;

	public static String TYPE_ADD_GROUP = "addgroup";

	public static String TYPE_ADD_REPOSITORY = "addrepository";

	public static String TYPE_ADD_USER = "adduser";

	public static String TYPE_CLONE_GROUP = "clonegroup";

	public static String TYPE_CLONE_USER = "cloneuser";

	public static String TYPE_EDIT_GROUP = "editgroup";

	public static String TYPE_EDIT_PATH = "editpath";

	public static String TYPE_EDIT_REPOSITORY = "editrepository";

	public static String TYPE_EDIT_USER = "edituser";

	private JPanel buttonPanel = null;

	private JPanel buttonSubPanel = null;

	private JButton cancelButton = null;

	private JPanel formPanel = null;

	private JPanel formSubPanel = null;

	private Group group = null;

	private JPanel instructionsPanel = null;

	private JPanel jContentPane = null;

	private Message message;

	private Path path = null;

	private Repository repository = null;

	private JButton saveButton = null;

	private JTextField text = null;

	private String type = null;

	private User user = null;
	
	private Document document = null;

	/**
	 * Group dialog constructor.
	 */
	public BasicDialog(Document document, String type, Group group, Message message) {
		super();

		this.document = document;
		this.type = type;
		this.group = group;
		this.message = message;
		this.message.setState(Message.CANCEL);

		initialize();
	}

	/**
	 * Default constructor.
	 */
	public BasicDialog(String type, Message message) {
		super();

		this.type = type;
		this.message = message;
		this.message.setState(Message.CANCEL);

		initialize();
	}

	/**
	 * Group dialog constructor.
	 */
	public BasicDialog(String type, Path path, Message message) {
		super();

		this.type = type;
		this.path = path;
		this.message = message;
		this.message.setState(Message.CANCEL);

		initialize();
	}

	/**
	 * Repository dialog constructor.
	 */
	public BasicDialog(String type, Repository repository, Message message) {
		super();

		this.type = type;
		this.repository = repository;
		this.message = message;
		this.message.setState(Message.CANCEL);

		initialize();
	}

	/**
	 * User dialog constructor.
	 */
	public BasicDialog(String type, User user, Message message) {
		super();

		this.type = type;
		this.user = user;
		this.message = message;
		this.message.setState(Message.CANCEL);

		initialize();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Constants.SAVE_ACTION)) {
			try {
				String text = getText().getText();

				if (type.equals(TYPE_ADD_USER)) {
					addUser(text);
				}
				else if (type.equals(TYPE_CLONE_USER)) {
					cloneUser(text);
				}
				else if (type.equals(TYPE_EDIT_USER)) {
					editUser(text);
				}
				else if (type.equals(TYPE_ADD_GROUP)) {
					addGroup(text);
				}
				else if (type.equals(TYPE_EDIT_GROUP)) {
					editGroup(text);
				}
				else if (type.equals(TYPE_CLONE_GROUP)) {
					cloneGroup(text);
				}
				else if (type.equals(TYPE_ADD_REPOSITORY)) {
					addRepository(text);
				}
				else if (type.equals(TYPE_EDIT_REPOSITORY)) {
					editRepository(text);
				}
				else if (type.equals(TYPE_EDIT_PATH)) {
					editPath(text);
				}
			}
			catch (ApplicationException ex) {
				displayError(ex.getMessage());
			}
		}
		else if (e.getActionCommand().equals(Constants.CANCEL_ACTION)) {
			message.setState(Message.CANCEL);
			dispose();
		}
	}

	private void addGroup(String groupName) throws ApplicationException {
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

	private void addRepository(String repositoryName) throws ApplicationException {
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

	private void addUser(String userName) throws ApplicationException {
		validateUserName(userName);

		if (document.findUser(userName) == null) {
			message.setUserObject(document.addUser(userName));
			message.setState(Message.SUCCESS);
			dispose();
		}
		else {
			displayError(ResourceUtil.getFormattedString(type + ".error.useralreadyexists", userName));
		}
	}

	private void cloneGroup(String groupName) throws ApplicationException {
		validateGroupName(groupName);

		Group existingGroup = document.findGroup(groupName);

		if (existingGroup == null || existingGroup == group) {
			message.setUserObject(document.cloneGroup(group, groupName));
			message.setState(Message.SUCCESS);
			dispose();
		}
		else {
			displayError(ResourceUtil.getFormattedString(type + ".error.groupalreadyexists", groupName));
		}
	}

	private void cloneUser(String userName) throws ApplicationException {
		validateUserName(userName);

		if (document.findUser(userName) == null) {
			message.setUserObject(document.cloneUser(user, userName));
			message.setState(Message.SUCCESS);
			dispose();
		}
		else {
			displayError(ResourceUtil.getFormattedString("cloneuser.error.useralreadyexists", userName));
		}
	}

	private void editGroup(String groupName) throws ApplicationException {
		validateGroupName(groupName);

		Group existingGroup = document.findGroup(groupName);

		if (existingGroup == null || existingGroup == group) {
			group.setName(groupName);
			message.setUserObject(group);
			message.setState(Message.SUCCESS);
			dispose();
		}
		else {
			displayError(ResourceUtil.getFormattedString(type + ".error.groupalreadyexists", groupName));
		}
	}

	private void editPath(String pathString) throws ApplicationException {
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

	private void editRepository(String repositoryName) throws ApplicationException {
		validateRepositoryName(repositoryName);

		Repository existingRepository = document.findRepository(repositoryName);

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

	private void editUser(String userName) throws ApplicationException {
		validateUserName(userName);

		User existingUser = document.findUser(userName);

		if (existingUser == null || existingUser == user) {
			user.setName(userName);
			message.setUserObject(user);
			message.setState(Message.SUCCESS);
			dispose();
		}
		else {
			displayError(ResourceUtil.getFormattedString(type + ".error.useralreadyexist", userName));
		}
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
			cancelButton = createButton("button.cancel", Constants.CANCEL_ACTION, this);
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
			formPanel = new JPanel(new FlowLayout());
			formPanel.add(getFormSubPanel());
		}

		return formPanel;
	}

	/**
	 * This method initializes formSubPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFormSubPanel() {
		if (formSubPanel == null) {
			formSubPanel = new JPanel(new FlowLayout());
			formSubPanel.add(new JLabel(ResourceUtil.getString(type + ".label")));
			formSubPanel.add(getText());
		}

		return formSubPanel;
	}

	/**
	 * This method initializes instructionsPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInstructionsPanel() {
		if (instructionsPanel == null) {
			instructionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			instructionsPanel.add(new JLabel(ResourceUtil.getString(type + ".instructions")));
		}

		return instructionsPanel;
	}

	/**
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getInstructionsPanel(), BorderLayout.NORTH);
			jContentPane.add(getFormPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}

		return jContentPane;
	}

	/**
	 * This method initializes addButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = createButton(type + ".savebutton", Constants.SAVE_ACTION, this);
		}

		return saveButton;
	}

	/**
	 * This method initializes nameText.
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getText() {
		if (text == null) {
			text = new JTextField();
			text.setFont(UserPreferences.getUserFont());
			text.setPreferredSize(new Dimension(340, 21));
		}

		return text;
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString(type + ".title"));
		this.setContentPane(getJContentPane());

		getRootPane().setDefaultButton(saveButton);

		this.pack();
		this.setModal(true);
	}

	private void validateGroupName(String groupName) throws ValidatorException {
		Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), groupName);
		Validator.validateGroupName(groupName);
	}

	private void validatePath(String pathString) throws ValidatorException {
		Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), pathString);
		Validator.validatePath(pathString);
	}

	private void validateRepositoryName(String repositoryName) throws ValidatorException {
		Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), repositoryName);
		Validator.validateRepositoryName(repositoryName);
	}

	private void validateUserName(String userName) throws ValidatorException {
		Validator.validateNotEmptyString(ResourceUtil.getString(type + ".label"), userName);
		Validator.validateUserName(userName);
	}
}
