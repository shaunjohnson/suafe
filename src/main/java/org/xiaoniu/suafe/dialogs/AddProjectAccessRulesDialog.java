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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.models.GroupListModel;
import org.xiaoniu.suafe.models.RepositoryListModel;
import org.xiaoniu.suafe.models.UserListModel;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;

/**
 * Dialog that allows a user to add an access rule.
 * 
 * @author Shaun Johnson
 */
public class AddProjectAccessRulesDialog extends ParentDialog implements ActionListener {

	private static final String ALL_USERS_ACTION = "ALL_USERS_ACTION";

	private static final String USER_ACTION = "USER_ACTION";

	private static final String GROUP_ACTION = "USER_ACTION";

	private static final long serialVersionUID = -1001510687982587543L;

	private JPanel jContentPane = null;

	private Message message = null;

	private Repository repository = null;

	private String path = null;

	private JPanel buttonPanel = null;

	private JButton addButton = null;

	private JButton cancelButton = null;

	private JPanel buttonSubPanel = null;

	private JPanel formPanel = null;

	private JPanel formSubPanel = null;

	private JLabel repositoryLabel = null;

	private JComboBox repositoryComboBox = null;

	private JPanel repositoryPanel = null;

	private JPanel groupPanel = null;

	private JPanel userPanel = null;

	private JLabel groupLabel = null;

	private JComboBox groupComboBox = null;

	private JLabel userLabel = null;

	private JComboBox userComboBox = null;

	private JPanel applyToPanel = null;

	private JRadioButton groupRadioButton = null;

	private ButtonGroup groupUserButtonGroup = null;

	private ButtonGroup accessLevelButtonGroup = null;

	private JRadioButton userRadioButton = null;

	private JPanel levelOfAccessPanel = null;

	private JRadioButton readOnlyRadioButton = null;

	private JRadioButton readWriteRadioButton = null;

	private JRadioButton denyAccessRadioButton = null;

	private JLabel levelOfAccessLabel = null;

	private JPanel levelOfAccessSubPanel = null;

	private JLabel applyToLabel = null;

	private JRadioButton allUsersRadioButton = null;

	private JPanel pathPanel = null;

	private JLabel pathLabel = null;

	private JTextField pathTextField = null;

	private JButton addRepositoryButton = null;

	/**
	 * Default constructor.
	 */
	public AddProjectAccessRulesDialog(Object userObject, Message message) {
		super();

		if (userObject != null && userObject instanceof Repository) {
			this.repository = (Repository) userObject;
			this.path = Constants.DEFAULT_PATH;
		}
		else if (userObject != null && userObject instanceof Path) {
			Path path = (Path) userObject;

			this.repository = path.getRepository();
			this.path = path.getPath();
		}
		else {
			this.repository = null;
			this.path = Constants.DEFAULT_PATH;
		}

		this.message = message;
		this.message.setState(Message.CANCEL);

		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString("addaccessrule.title"));
		this.setContentPane(getJContentPane());

		getRootPane().setDefaultButton(addButton);

		this.pack();
		this.setModal(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(new JLabel(ResourceUtil.getString("addaccessrule.instructions")), BorderLayout.NORTH);
			jContentPane.add(getFormPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}

		return jContentPane;
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
	 * This method initializes addButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = createButton("button.add", Constants.ADD_ACTION, this);
		}

		return addButton;
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
			formSubPanel = new JPanel();
			formSubPanel.setLayout(new BoxLayout(formSubPanel, BoxLayout.Y_AXIS));
			formSubPanel.add(getRepositoryPanel());
			formSubPanel.add(getPathPanel());
			formSubPanel.add(getLevelOfAccessPanel());
			formSubPanel.add(getApplyToPanel());
			formSubPanel.add(getGroupPanel());
			formSubPanel.add(getUserPanel());
		}

		return formSubPanel;
	}

	/**
	 * Refreshses list of Repositories. Selects the specified repository.
	 * 
	 * @param repository Repository to select.
	 */
	private void refreshRepositoryList(Repository repository) {
		getRepositoryComboBox().setModel(new RepositoryListModel());

		if (repository != null) {
			getRepositoryComboBox().setSelectedItem(repository);
		}
	}

	/**
	 * Adds a new Repository.
	 */
	private void addRepository() {
		Message message = new Message();

		JDialog dialog = new BasicDialog(BasicDialog.TYPE_ADD_REPOSITORY, message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		if (message.getState() == Message.SUCCESS) {
			refreshRepositoryList((Repository) message.getUserObject());
		}
	}

	/**
	 * Displays selection lists depending on user selection.
	 */
	private void refereshApplyToPanels() {
		if (getGroupRadioButton().isSelected()) {
			getGroupPanel().setVisible(true);
			getUserPanel().setVisible(false);
		}
		else if (getUserRadioButton().isSelected()) {
			getGroupPanel().setVisible(false);
			getUserPanel().setVisible(true);
		}
		else {
			getGroupPanel().setVisible(false);
			getUserPanel().setVisible(false);
		}
	}

	/**
	 * ActionPerformed event handler.
	 * 
	 * @param event ActionEvent object.
	 */
	public void actionPerformed(ActionEvent event) {

		if (event.getActionCommand().equals(GROUP_ACTION)) {
			refereshApplyToPanels();
		}
		else if (event.getActionCommand().equals(USER_ACTION)) {
			refereshApplyToPanels();
		}
		else if (event.getActionCommand().equals(ALL_USERS_ACTION)) {
			refereshApplyToPanels();
		}
		else if (event.getActionCommand().equals(Constants.ADD_ACTION)) {
			try {
				Repository repository = (Repository) getRepositoryComboBox().getSelectedItem();
				String pathString = (String) getPathTextField().getText();
				String levelOfAccess = null;
				Group group = null;
				User user = null;

				Validator.validateNotEmptyString(ResourceUtil.getString("addaccessrule.path"), pathString);

				if (getReadWriteRadioButton().isSelected()) {
					levelOfAccess = Constants.ACCESS_LEVEL_READWRITE;
				}
				else if (getReadOnlyRadioButton().isSelected()) {
					levelOfAccess = Constants.ACCESS_LEVEL_READONLY;
				}
				else {
					levelOfAccess = Constants.ACCESS_LEVEL_DENY_ACCESS;
				}

				if (getGroupRadioButton().isSelected()) {
					group = (Group) getGroupComboBox().getSelectedItem();

					Validator.validateNotNull(ResourceUtil.getString("addaccessrule.group"), group);
				}
				else if (getUserRadioButton().isSelected()) {
					user = (User) getUserComboBox().getSelectedItem();

					Validator.validateNotNull(ResourceUtil.getString("addaccessrule.user"), user);
				}
				else if (getAllUsersRadioButton().isSelected()) {
					user = Document.addUser("*");
				}

				if (group != null) {
					if (Document.findGroupAccessRule(repository, pathString, group) == null) {
						AccessRule rule = Document.addAccessRuleForGroup(repository, pathString, group, levelOfAccess);
						message.setUserObject(rule);
						message.setState(Message.SUCCESS);
						dispose();
					}
					else {

						displayError(ResourceUtil.getString("addaccessrule.error.grouprulealreadyexists"));
					}
				}
				else if (user != null) {
					if (Document.findUserAccessRule(repository, pathString, user) == null) {
						AccessRule rule = Document.addAccessRuleForUser(repository, pathString, user, levelOfAccess);
						message.setUserObject(rule);
						message.setState(Message.SUCCESS);
						dispose();
					}
					else {
						displayError(ResourceUtil.getString("addaccessrule.error.userrulealreadyexists"));
					}
				}
				else {
					displayError(ResourceUtil.getString("addaccessrule.error.erroraddingrule"));
				}
			}
			catch (ApplicationException ex) {
				displayError(ex.getMessage());
			}
		}
		else if (event.getActionCommand().equals(Constants.ADD_REPOSITORY_ACTION)) {
			addRepository();
		}
		else if (event.getActionCommand().equals(Constants.CANCEL_ACTION)) {
			message.setState(Message.CANCEL);
			dispose();
		}
	}

	/**
	 * This method initializes repositoryComboBox.
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getRepositoryComboBox() {
		if (repositoryComboBox == null) {
			repositoryComboBox = new JComboBox(new RepositoryListModel());

			if (repository != null) {
				repositoryComboBox.setSelectedItem(repository);
			}

			repositoryComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
			repositoryComboBox.setBackground(Color.white);
			repositoryComboBox.setFont(UserPreferences.getUserFont());
		}

		return repositoryComboBox;
	}

	/**
	 * This method initializes repositoryLabel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getRepositoryPanel() {
		if (repositoryPanel == null) {
			repositoryLabel = new JLabel();
			repositoryLabel.setPreferredSize(new Dimension(100, 15));
			repositoryLabel.setText(ResourceUtil.getString("addaccessrule.repository"));

			repositoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			repositoryPanel.add(repositoryLabel);
			repositoryPanel.add(getRepositoryComboBox());
			repositoryPanel.add(getAddRepositoryButton());
		}

		return repositoryPanel;
	}

	/**
	 * This method initializes groupPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getGroupPanel() {
		if (groupPanel == null) {
			groupLabel = new JLabel();
			groupLabel.setPreferredSize(new Dimension(100, 15));
			groupLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			groupLabel.setText(ResourceUtil.getString("addaccessrule.group"));

			groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			groupPanel.add(groupLabel);
			groupPanel.add(getGroupComboBox());
		}

		return groupPanel;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getUserPanel() {
		if (userPanel == null) {
			userLabel = new JLabel();
			userLabel.setText(ResourceUtil.getString("addaccessrule.user"));
			userLabel.setPreferredSize(new Dimension(100, 15));
			userLabel.setHorizontalAlignment(SwingConstants.TRAILING);

			userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			userPanel.add(userLabel);
			userPanel.add(getUserComboBox());
			userPanel.setVisible(false);
		}
		return userPanel;
	}

	/**
	 * This method initializes groupComboBox.
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getGroupComboBox() {
		if (groupComboBox == null) {
			groupComboBox = new JComboBox(new GroupListModel());
			groupComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
			groupComboBox.setBackground(Color.white);
			groupComboBox.setFont(UserPreferences.getUserFont());
		}

		return groupComboBox;
	}

	/**
	 * This method initializes userComboBox.
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getUserComboBox() {
		if (userComboBox == null) {
			userComboBox = new JComboBox(new UserListModel());
			userComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
			userComboBox.setBackground(Color.white);
			userComboBox.setFont(UserPreferences.getUserFont());
		}

		return userComboBox;
	}

	/**
	 * This method initializes applyToPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getApplyToPanel() {
		if (applyToPanel == null) {
			applyToLabel = new JLabel();
			applyToLabel.setText(ResourceUtil.getString("addaccessrule.applyto"));
			applyToLabel.setPreferredSize(new Dimension(100, 15));

			applyToPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			applyToPanel.add(applyToLabel);
			applyToPanel.add(getGroupRadioButton());
			applyToPanel.add(getUserRadioButton());
			applyToPanel.add(getAllUsersRadioButton());

			groupUserButtonGroup = new ButtonGroup();
			groupUserButtonGroup.add(getGroupRadioButton());
			groupUserButtonGroup.add(getUserRadioButton());
			groupUserButtonGroup.add(getAllUsersRadioButton());

			if (Document.getGroupObjects().length == 0) {
				getGroupRadioButton().setEnabled(false);
				getUserRadioButton().setSelected(true);
			}

			if (Document.getUserObjects().length == 0) {
				getUserRadioButton().setEnabled(false);

				if (getGroupRadioButton().isEnabled()) {
					getGroupRadioButton().setSelected(true);
				}
				else {
					getAllUsersRadioButton().setSelected(true);
				}
			}

			refereshApplyToPanels();
		}

		return applyToPanel;
	}

	/**
	 * This method initializes groupRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getGroupRadioButton() {
		if (groupRadioButton == null) {
			groupRadioButton = new JRadioButton();
			groupRadioButton.addActionListener(this);
			groupRadioButton.setActionCommand(GROUP_ACTION);
			groupRadioButton.setText(ResourceUtil.getString("addaccessrule.applyto.group"));
			groupRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
			groupRadioButton.setSelected(true);
		}

		return groupRadioButton;
	}

	/**
	 * This method initializes userRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getUserRadioButton() {
		if (userRadioButton == null) {
			userRadioButton = new JRadioButton();
			userRadioButton.addActionListener(this);
			userRadioButton.setActionCommand(USER_ACTION);
			userRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
			userRadioButton.setText(ResourceUtil.getString("addaccessrule.applyto.user"));
		}

		return userRadioButton;
	}

	/**
	 * This method initializes levelOfAccessPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLevelOfAccessPanel() {
		if (levelOfAccessPanel == null) {
			levelOfAccessLabel = new JLabel();
			levelOfAccessLabel.setText(ResourceUtil.getString("addaccessrule.level"));
			levelOfAccessLabel.setPreferredSize(new Dimension(100, 15));

			levelOfAccessPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			levelOfAccessPanel.add(levelOfAccessLabel);
			levelOfAccessPanel.add(getReadWriteRadioButton());
			levelOfAccessPanel.add(getReadOnlyRadioButton());
			levelOfAccessPanel.add(getDenyAccessRadioButton());
			levelOfAccessPanel.add(getLevelOfAccessSubPanel());

			accessLevelButtonGroup = new ButtonGroup();
			accessLevelButtonGroup.add(getReadWriteRadioButton());
			accessLevelButtonGroup.add(getReadOnlyRadioButton());
			accessLevelButtonGroup.add(getDenyAccessRadioButton());
		}

		return levelOfAccessPanel;
	}

	/**
	 * This method initializes readOnlyRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getReadOnlyRadioButton() {
		if (readOnlyRadioButton == null) {
			readOnlyRadioButton = new JRadioButton();
			readOnlyRadioButton.setText(ResourceUtil.getString("accesslevel.readonly"));
			readOnlyRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		}

		return readOnlyRadioButton;
	}

	/**
	 * This method initializes readWriteRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getReadWriteRadioButton() {
		if (readWriteRadioButton == null) {
			readWriteRadioButton = new JRadioButton();
			readWriteRadioButton.setText(ResourceUtil.getString("accesslevel.readwrite"));
			readWriteRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
			readWriteRadioButton.setSelected(true);
		}
		return readWriteRadioButton;
	}

	/**
	 * This method initializes denyAccessRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getDenyAccessRadioButton() {
		if (denyAccessRadioButton == null) {
			denyAccessRadioButton = new JRadioButton();
			denyAccessRadioButton.setText(ResourceUtil.getString("accesslevel.denyaccess"));
			denyAccessRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		}

		return denyAccessRadioButton;
	}

	/**
	 * This method initializes levelOfAccessSubPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLevelOfAccessSubPanel() {
		if (levelOfAccessSubPanel == null) {
			levelOfAccessSubPanel = new JPanel(new GridLayout(3, 1));
		}

		return levelOfAccessSubPanel;
	}

	/**
	 * This method initializes allUsersRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getAllUsersRadioButton() {
		if (allUsersRadioButton == null) {
			allUsersRadioButton = new JRadioButton();
			allUsersRadioButton.addActionListener(this);
			allUsersRadioButton.setActionCommand(ALL_USERS_ACTION);
			allUsersRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
			allUsersRadioButton.setText(ResourceUtil.getString("addaccessrule.applyto.allusers"));
		}

		return allUsersRadioButton;
	}

	/**
	 * This method initializes pathPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPathPanel() {
		if (pathPanel == null) {
			pathLabel = new JLabel();
			pathLabel.setText(ResourceUtil.getString("addaccessrule.path"));
			pathLabel.setPreferredSize(new Dimension(100, 15));

			pathPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pathPanel.add(pathLabel);
			pathPanel.add(getPathTextField());
		}
		return pathPanel;
	}

	/**
	 * This method initializes pathTextField.
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getPathTextField() {
		if (pathTextField == null) {
			pathTextField = new JTextField();
			pathTextField.setText(path);
			pathTextField.setPreferredSize(new Dimension(350, 21));
			pathTextField.setFont(UserPreferences.getUserFont());
		}

		return pathTextField;
	}

	/**
	 * This method initializes addRepositoryButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddRepositoryButton() {
		if (addRepositoryButton == null) {
			addRepositoryButton = new JButton();
			addRepositoryButton.addActionListener(this);
			addRepositoryButton.setActionCommand(Constants.ADD_REPOSITORY_ACTION);
			addRepositoryButton.setPreferredSize(new Dimension(56, 25));
			addRepositoryButton.setText(ResourceUtil.getString("addaccessrule.addrepository"));
			addRepositoryButton.setToolTipText(ResourceUtil.getString("addaccessrule.addrepository.tooltip"));
			addRepositoryButton.setFont(new Font("Dialog", Font.BOLD, 12));
		}

		return addRepositoryButton;
	}
}
