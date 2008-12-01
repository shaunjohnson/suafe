package org.xiaoniu.suafe.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.models.GroupListModel;
import org.xiaoniu.suafe.models.RepositoryListModel;
import org.xiaoniu.suafe.models.UserListModel;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;

public class AccessRuleForm extends JPanel implements ActionListener {

	private static final String ALL_USERS_ACTION = "ALL_USERS_ACTION";

	private static final String GROUP_ACTION = "USER_ACTION";

	private static final long serialVersionUID = 2833102288856456199L;

	private static final String USER_ACTION = "USER_ACTION";

	private static final String TYPE_ADD_RULE = "addaccessrule";

	private static final String TYPE_ADD_PROJECT_RULES = "addprojectaccessrules";

	private static final String TYPE_EDIT_RULE = "editaccessrule";

	private AccessRule accessRule = null;

	private String type = null;

	private ButtonGroup accessLevelButtonGroup = null;

	private JButton addRepositoryButton = null;

	private JRadioButton allUsersRadioButton = null;

	private JLabel applyToLabel = null;

	private JPanel applyToPanel = null;

	private JRadioButton denyAccessRadioButton = null;

	private JComboBox groupComboBox = null;

	private JLabel groupLabel = null;

	private JPanel groupPanel = null;

	private JRadioButton groupRadioButton = null;

	private ButtonGroup groupUserButtonGroup = null;

	private JPanel headerPanel;

	private JLabel levelOfAccessLabel = null;

	private JPanel levelOfAccessPanel = null;

	private JPanel levelOfAccessSubPanel = null;

	private String name;

	private String path = null;

	private JLabel pathLabel = null;

	private JPanel pathPanel = null;

	private JTextField pathTextField = null;

	private JRadioButton readOnlyRadioButton = null;

	private JRadioButton readWriteRadioButton = null;

	private Repository repository = null;

	private JComboBox repositoryComboBox = null;

	private JLabel repositoryLabel = null;

	private JPanel repositoryPanel = null;

	private JComboBox userComboBox = null;

	private JLabel userLabel = null;

	private JPanel userPanel = null;

	private JRadioButton userRadioButton = null;
	
	private Document document = null;

	public AccessRuleForm(Document document, Repository repository, String path) {
		super();

		this.document = document;
		this.type = TYPE_ADD_RULE;
		this.repository = repository;
		this.path = path;

		initialize();
	}

	public AccessRuleForm(Document document, String name, Repository repository, String path) {
		super();

		this.document = document;
		this.type = TYPE_ADD_PROJECT_RULES;
		this.name = name;
		this.repository = repository;
		this.path = path;

		initialize();
	}

	public AccessRuleForm(Document document, AccessRule accessRule) {
		super();

		this.document = document;
		this.type = TYPE_EDIT_RULE;
		this.accessRule = accessRule;
		this.repository = accessRule.getPath().getRepository();
		this.path = accessRule.getPath().getPath();

		initialize();
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
		else if (event.getActionCommand().equals(Constants.ADD_REPOSITORY_ACTION)) {
			addRepository();
		}
	}

	public AccessRule addAccessRule() throws ApplicationException {
		Repository repository = (Repository) getRepositoryComboBox().getSelectedItem();
		String pathString = (String) getPathTextField().getText();
		String levelOfAccess = null;
		Group group = null;
		User user = null;
		AccessRule rule = null;

		Validator.validateNotEmptyString(ResourceUtil.getString(type + ".path"), pathString);

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

			Validator.validateNotNull(ResourceUtil.getString(type + ".group"), group);
		}
		else if (getUserRadioButton().isSelected()) {
			user = (User) getUserComboBox().getSelectedItem();

			Validator.validateNotNull(ResourceUtil.getString(type + ".user"), user);
		}
		else if (getAllUsersRadioButton().isSelected()) {
			user = document.addUser("*");
		}

		if (group != null) {
			if (document.findGroupAccessRule(repository, pathString, group) != null) {
				throw new ApplicationException(ResourceUtil.getString(type + ".error.grouprulealreadyexists"));
			}

			rule = document.addAccessRuleForGroup(repository, pathString, group, levelOfAccess);
		}
		else if (user != null) {
			if (document.findUserAccessRule(repository, pathString, user) != null) {
				throw new ApplicationException(ResourceUtil.getString(type + ".error.userrulealreadyexists"));
			}

			rule = document.addAccessRuleForUser(repository, pathString, user, levelOfAccess);
		}
		else {
			throw new ApplicationException(ResourceUtil.getString(type + ".error.erroraddingrule"));
		}

		return rule;
	}

	public AccessRule editAccessRule() throws ApplicationException {
		Repository repository = (Repository) getRepositoryComboBox().getSelectedItem();
		String pathString = (String) getPathTextField().getText();
		String levelOfAccess = null;
		Group group = null;
		User user = null;

		Validator.validateNotEmptyString(ResourceUtil.getString("editaccessrule.path"), pathString);

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

			Validator.validateNotNull(ResourceUtil.getString("editaccessrule.group"), group);
		}
		else if (getUserRadioButton().isSelected()) {
			user = (User) getUserComboBox().getSelectedItem();

			Validator.validateNotNull(ResourceUtil.getString("editaccessrule.user"), user);
		}
		else if (getAllUsersRadioButton().isSelected()) {
			user = document.addUser("*");
		}

		if (group != null) {
			AccessRule foundRule = document.findGroupAccessRule(repository, pathString, group);

			if (foundRule == null || accessRule == foundRule) {
				accessRule.getPath().removeAccessRule(accessRule);
				accessRule.setPath(document.addPath(repository, pathString));
				accessRule.getPath().addAccessRule(accessRule);
				accessRule.setGroup(group);
				group.addAccessRule(accessRule);

				if (accessRule.getUser() != null) {
					accessRule.getUser().removeAccessRule(accessRule);
				}

				accessRule.setUser(null);
				accessRule.setLevel(levelOfAccess);
			}
			else {
				throw new ApplicationException(ResourceUtil.getString("editaccessrule.error.grouprulealreadyexists"));
			}
		}
		else if (user != null) {
			AccessRule foundRule = document.findUserAccessRule(repository, pathString, user);

			if (foundRule == null || accessRule == foundRule) {
				accessRule.getPath().removeAccessRule(accessRule);
				accessRule.setPath(document.addPath(repository, pathString));
				accessRule.getPath().addAccessRule(accessRule);

				if (accessRule.getGroup() != null) {
					accessRule.getGroup().removeAccessRule(accessRule);
				}

				accessRule.setGroup(null);
				accessRule.setUser(user);
				user.addAccessRule(accessRule);
				accessRule.setLevel(levelOfAccess);
			}
			else {
				throw new ApplicationException(ResourceUtil.getString("editaccessrule.error.userrulealreadyexists"));
			}
		}
		else {
			throw new ApplicationException(ResourceUtil.getString("editaccessrule.error.errorsavingrule"));
		}

		return accessRule;
	}

	/**
	 * Adds a new Repository.
	 */
	private void addRepository() {
		Message message = new Message();

		JDialog dialog = new BasicDialog(document, BasicDialog.TYPE_ADD_REPOSITORY, message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		if (message.getState() == Message.SUCCESS) {
			refreshRepositoryList((Repository) message.getUserObject());
		}
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
			addRepositoryButton.setText(ResourceUtil.getString(type + ".addrepository"));
			addRepositoryButton.setToolTipText(ResourceUtil.getString(type + ".addrepository.tooltip"));
			addRepositoryButton.setFont(new Font("Dialog", Font.BOLD, 12));
		}

		return addRepositoryButton;
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
			allUsersRadioButton.setFont(Constants.FONT_PLAIN);
			allUsersRadioButton.setText(ResourceUtil.getString(type + ".applyto.allusers"));

			if (accessRule != null) {
				User user = accessRule.getUser();

				if (user != null && user.getName().equals("*")) {
					allUsersRadioButton.setSelected(true);
				}
			}
		}

		return allUsersRadioButton;
	}

	/**
	 * This method initializes applyToPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getApplyToPanel() {
		if (applyToPanel == null) {
			applyToLabel = new JLabel();
			applyToLabel.setText(ResourceUtil.getString(type + ".applyto"));
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

			if (document.getGroupObjects().length == 0) {
				getGroupRadioButton().setEnabled(false);
				getUserRadioButton().setSelected(true);
			}

			if (document.getUserObjects().length == 0) {
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
	 * This method initializes denyAccessRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getDenyAccessRadioButton() {
		if (denyAccessRadioButton == null) {
			denyAccessRadioButton = new JRadioButton();
			denyAccessRadioButton.setText(ResourceUtil.getString("accesslevel.denyaccess"));
			denyAccessRadioButton.setFont(Constants.FONT_PLAIN);

			if (accessRule != null && accessRule.getLevel().equals(Constants.ACCESS_LEVEL_DENY_ACCESS)) {
				denyAccessRadioButton.setSelected(true);
			}
		}

		return denyAccessRadioButton;
	}

	/**
	 * This method initializes groupComboBox.
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getGroupComboBox() {
		if (groupComboBox == null) {
			groupComboBox = createComboBox(new GroupListModel(document));

			if (accessRule != null && accessRule.getGroup() != null) {
				groupComboBox.setSelectedItem(accessRule.getGroup());
			}
		}

		return groupComboBox;
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
			groupLabel.setText(ResourceUtil.getString(type + ".group"));

			groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			groupPanel.add(groupLabel);
			groupPanel.add(getGroupComboBox());
		}

		return groupPanel;
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
			groupRadioButton.setText(ResourceUtil.getString(type + ".applyto.group"));
			groupRadioButton.setFont(Constants.FONT_PLAIN);

			if (accessRule != null) {
				if (accessRule.getGroup() != null) {
					groupRadioButton.setSelected(true);
				}
			}
			else {
				groupRadioButton.setSelected(true);
			}
		}

		return groupRadioButton;
	}

	private JPanel getHeaderPanel() {
		if (headerPanel == null) {
			headerPanel = new JPanel();

			if (name != null) {
				headerPanel.add(new JLabel(name, JLabel.CENTER));
			}
		}

		return headerPanel;
	}

	/**
	 * This method initializes levelOfAccessPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLevelOfAccessPanel() {
		if (levelOfAccessPanel == null) {
			levelOfAccessLabel = new JLabel();
			levelOfAccessLabel.setText(ResourceUtil.getString(type + ".level"));
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
	 * This method initializes pathPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPathPanel() {
		if (pathPanel == null) {
			pathLabel = new JLabel();
			pathLabel.setText(ResourceUtil.getString(type + ".path"));
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
	 * This method initializes readOnlyRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getReadOnlyRadioButton() {
		if (readOnlyRadioButton == null) {
			readOnlyRadioButton = new JRadioButton();
			readOnlyRadioButton.setText(ResourceUtil.getString("accesslevel.readonly"));
			readOnlyRadioButton.setFont(Constants.FONT_PLAIN);

			if (accessRule != null && accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READONLY)) {
				readOnlyRadioButton.setSelected(true);
			}
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
			readWriteRadioButton.setFont(Constants.FONT_PLAIN);

			if (accessRule != null && accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READWRITE)) {
				readWriteRadioButton.setSelected(true);
			}
			else {
				readWriteRadioButton.setSelected(true);
			}
		}
		return readWriteRadioButton;
	}

	/**
	 * This method initializes repositoryComboBox.
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getRepositoryComboBox() {
		if (repositoryComboBox == null) {
			repositoryComboBox = createComboBox(new RepositoryListModel(document));

			if (repository != null) {
				repositoryComboBox.setSelectedItem(repository);
			}
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
			repositoryLabel.setText(ResourceUtil.getString(type + ".repository"));

			repositoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			repositoryPanel.add(repositoryLabel);
			repositoryPanel.add(getRepositoryComboBox());
			repositoryPanel.add(getAddRepositoryButton());
		}

		return repositoryPanel;
	}

	/** 
	 * Create a JComboBox with a white background and current font.
	 * @param model
	 * @return
	 */
	private JComboBox createComboBox(ComboBoxModel model) {
		JComboBox comboBox = new JComboBox(model);
		comboBox.setBackground(Color.white);
		comboBox.setFont(UserPreferences.getUserFont());

		return comboBox;
	}

	/**
	 * This method initializes userComboBox.
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getUserComboBox() {
		if (userComboBox == null) {
			userComboBox = createComboBox(new UserListModel(document));

			if (accessRule != null && accessRule.getUser() != null) {
				userComboBox.setSelectedItem(accessRule.getUser());
			}
		}

		return userComboBox;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getUserPanel() {
		if (userPanel == null) {
			userLabel = new JLabel();
			userLabel.setText(ResourceUtil.getString(type + ".user"));
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
	 * This method initializes userRadioButton.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getUserRadioButton() {
		if (userRadioButton == null) {
			userRadioButton = new JRadioButton();
			userRadioButton.addActionListener(this);
			userRadioButton.setActionCommand(USER_ACTION);
			userRadioButton.setFont(Constants.FONT_PLAIN);
			userRadioButton.setText(ResourceUtil.getString(type + ".applyto.user"));

			if (accessRule != null && accessRule.getUser() != null && !accessRule.getUser().getName().equals("*")) {
				userRadioButton.setSelected(true);
			}
		}

		return userRadioButton;
	}

	public void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getHeaderPanel());
		this.add(getRepositoryPanel());
		this.add(getPathPanel());
		this.add(getLevelOfAccessPanel());
		this.add(getApplyToPanel());
		this.add(getGroupPanel());
		this.add(getUserPanel());
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
	 * Refreshses list of Repositories. Selects the specified repository.
	 * 
	 * @param repository Repository to select.
	 */
	private void refreshRepositoryList(Repository repository) {
		getRepositoryComboBox().setModel(new RepositoryListModel(document));

		if (repository != null) {
			getRepositoryComboBox().setSelectedItem(repository);
		}
	}
}
