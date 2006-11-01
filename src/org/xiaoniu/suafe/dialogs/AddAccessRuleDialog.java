/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://suafe.xiaoniu.org.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://suafe.xiaoniu.org/.
 * ====================================================================
 * @endcopyright
 */

package org.xiaoniu.suafe.dialogs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.xiaoniu.suafe.Constants;
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
public class AddAccessRuleDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -1001510687982587543L;
	private javax.swing.JPanel jContentPane = null;
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
	private JLabel instructionsLabel = null;
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
	 * This is the default constructor
	 */
	public AddAccessRuleDialog(Object userObject, Message message) {
		super();
		
		if (userObject != null && userObject instanceof Repository) {
			this.repository = (Repository)userObject;
			this.path = Constants.DEFAULT_PATH;
		}
		else if (userObject != null && userObject instanceof Path) {
			Path path = (Path)userObject;
			
			this.repository = path.getRepository();
			this.path = path.getPath();
		}
		else {
			this.repository = null;
			this.path = Constants.DEFAULT_PATH;
		}
		
		this.message = message;
		
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString("addaccessrule.title"));
		this.setSize(500, 256);
		this.setContentPane(getJContentPane());
		
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			instructionsLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			instructionsLabel.setText(ResourceUtil.getString("addaccessrule.instructions"));
			jContentPane.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getFormPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(instructionsLabel, java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			buttonPanel = new JPanel();
			buttonPanel.setLayout(gridLayout1);
			gridLayout1.setRows(1);
			buttonPanel.add(getButtonSubPanel(), null);
		}
		return buttonPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText(ResourceUtil.getString("button.add"));
			addButton.setActionCommand("Add");
			addButton.addActionListener(this);
			
			getRootPane().setDefaultButton(addButton);
		}
		return addButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(ResourceUtil.getString("button.cancel"));
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getButtonSubPanel() {
		if (buttonSubPanel == null) {
			buttonSubPanel = new JPanel();
			buttonSubPanel.add(getAddButton(), null);
			buttonSubPanel.add(getCancelButton(), null);
		}
		return buttonSubPanel;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new JPanel();
			formPanel.setLayout(new FlowLayout());
			formPanel.add(getFormSubPanel(), null);
		}
		return formPanel;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getFormSubPanel() {
		if (formSubPanel == null) {
			repositoryLabel = new JLabel();
			formSubPanel = new JPanel();
			formSubPanel.setLayout(new BoxLayout(formSubPanel, BoxLayout.Y_AXIS));
			repositoryLabel.setText(ResourceUtil.getString("addaccessrule.repository"));
			repositoryLabel.setPreferredSize(new java.awt.Dimension(100,15));
			formSubPanel.add(getRepositoryPanel(), null);
			formSubPanel.add(getPathPanel(), null);
			formSubPanel.add(getLevelOfAccessPanel(), null);
			formSubPanel.add(getApplyToPanel(), null);
			formSubPanel.add(getGroupPanel(), null);
			formSubPanel.add(getUserPanel(), null);
		}
		return formSubPanel;
	}
	private void displayError(String message) {
		JOptionPane.showMessageDialog(this, message, ResourceUtil.getString("application.error"), JOptionPane.ERROR_MESSAGE);
	}
	
	private void refreshRepositoryList(Repository repository) {
		getRepositoryComboBox().setModel(new RepositoryListModel());
		
		if (repository != null) {
			getRepositoryComboBox().setSelectedItem(repository);
		}
	}
	
	private void addRepository() {
		Message message = new Message();
		
		JDialog dialog = new AddRepositoryDialog(message);
		DialogUtil.center(this, dialog);
		dialog.setVisible(true);

		if (message.getState() == Message.SUCCESS) {
			refreshRepositoryList((Repository)message.getUserObject());			
		}
	}
	
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
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Group")) {
			refereshApplyToPanels();
		}
		else if (e.getActionCommand().equals("User")) {
			refereshApplyToPanels();
		}
		else if (e.getActionCommand().equals("AllUsers")) {
			refereshApplyToPanels();
		}
		else if (e.getActionCommand().equals("Add")) {
			try {
				Repository repository = (Repository)getRepositoryComboBox().getSelectedItem();								
				String pathString = (String)getPathTextField().getText();
				String levelOfAccess = null;
				Group group = null;
				User user = null;
				
				Validator.validateNotEmptyString("Path", pathString);
				
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
					group = (Group)getGroupComboBox().getSelectedItem();
					
					Validator.validateNotNull("Group", group);
				}
				else if (getUserRadioButton().isSelected()) {
					user = (User)getUserComboBox().getSelectedItem();
					
					Validator.validateNotNull("User", user);
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
		else if (e.getActionCommand().equals("AddRepository")) {
			addRepository();
		}
		else if (e.getActionCommand().equals("Cancel")) {
			message.setState(Message.CANCEL);
			dispose();
		}
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getRepositoryComboBox() {
		if (repositoryComboBox == null) {
			repositoryComboBox = new JComboBox();
			repositoryComboBox.setModel(new RepositoryListModel());
			
			if (repository != null) {
				repositoryComboBox.setSelectedItem(repository);
			}
			
			repositoryComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			repositoryComboBox.setBackground(java.awt.Color.white);
		}
		return repositoryComboBox;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getRepositoryPanel() {
		if (repositoryPanel == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			repositoryPanel = new JPanel();
			repositoryPanel.setLayout(flowLayout2);
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			repositoryPanel.add(repositoryLabel, null);
			repositoryPanel.add(getRepositoryComboBox(), null);
			repositoryPanel.add(getAddRepositoryButton(), null);
		}
		return repositoryPanel;
	}
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getGroupPanel() {
		if (groupPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			groupLabel = new JLabel();
			groupPanel = new JPanel();
			groupPanel.setLayout(flowLayout1);
			groupLabel.setText(ResourceUtil.getString("addaccessrule.group"));
			groupLabel.setPreferredSize(new java.awt.Dimension(100,15));
			groupLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			groupPanel.add(groupLabel, null);
			groupPanel.add(getGroupComboBox(), null);
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
			FlowLayout flowLayout3 = new FlowLayout();
			userLabel = new JLabel();
			userPanel = new JPanel();
			userPanel.setLayout(flowLayout3);
			userLabel.setText(ResourceUtil.getString("addaccessrule.user"));
			userLabel.setPreferredSize(new java.awt.Dimension(100,15));
			userLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
			flowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			userPanel.add(userLabel, null);
			userPanel.add(getUserComboBox(), null);
			userPanel.setVisible(false);
		}
		return userPanel;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getGroupComboBox() {
		if (groupComboBox == null) {
			groupComboBox = new JComboBox();
			groupComboBox.setModel(new GroupListModel());
			groupComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			groupComboBox.setBackground(java.awt.Color.white);
		}
		return groupComboBox;
	}
	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getUserComboBox() {
		if (userComboBox == null) {
			userComboBox = new JComboBox();
			userComboBox.setModel(new UserListModel());
			userComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			userComboBox.setBackground(java.awt.Color.white);
		}
		return userComboBox;
	}
	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getApplyToPanel() {
		if (applyToPanel == null) {
			applyToLabel = new JLabel();
			FlowLayout flowLayout4 = new FlowLayout();
			applyToPanel = new JPanel();
			applyToPanel.setLayout(flowLayout4);
			flowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			applyToLabel.setText(ResourceUtil.getString("addaccessrule.applyto"));
			applyToLabel.setPreferredSize(new java.awt.Dimension(100,15));
			applyToPanel.add(applyToLabel, null);
			applyToPanel.add(getGroupRadioButton(), null);
			applyToPanel.add(getUserRadioButton(), null);
			applyToPanel.add(getAllUsersRadioButton(), null);
						
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
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getGroupRadioButton() {
		if (groupRadioButton == null) {
			groupRadioButton = new JRadioButton();
			groupRadioButton.setText(ResourceUtil.getString("addaccessrule.applyto.group"));
			groupRadioButton.setSelected(true);
			groupRadioButton.setActionCommand("Group");
			groupRadioButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			groupRadioButton.addActionListener(this);
		}
		return groupRadioButton;
	}
	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getUserRadioButton() {
		if (userRadioButton == null) {
			userRadioButton = new JRadioButton();
			userRadioButton.setText(ResourceUtil.getString("addaccessrule.applyto.user"));
			userRadioButton.setActionCommand("User");
			userRadioButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			userRadioButton.addActionListener(this);
		}
		return userRadioButton;
	}
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getLevelOfAccessPanel() {
		if (levelOfAccessPanel == null) {
			FlowLayout flowLayout7 = new FlowLayout();
			levelOfAccessLabel = new JLabel();
			levelOfAccessPanel = new JPanel();
			levelOfAccessPanel.setLayout(flowLayout7);
			levelOfAccessLabel.setText(ResourceUtil.getString("addaccessrule.level"));
			levelOfAccessLabel.setPreferredSize(new java.awt.Dimension(100,15));
			flowLayout7.setAlignment(java.awt.FlowLayout.LEFT);
			levelOfAccessPanel.add(levelOfAccessLabel, null);
			levelOfAccessPanel.add(getReadWriteRadioButton(), null);
			levelOfAccessPanel.add(getReadOnlyRadioButton(), null);
			levelOfAccessPanel.add(getDenyAccessRadioButton(), null);
			levelOfAccessPanel.add(getLevelOfAccessSubPanel(), null);
			
			accessLevelButtonGroup = new ButtonGroup();
			accessLevelButtonGroup.add(getReadWriteRadioButton());
			accessLevelButtonGroup.add(getReadOnlyRadioButton());			
			accessLevelButtonGroup.add(getDenyAccessRadioButton());
		}
		return levelOfAccessPanel;
	}
	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getReadOnlyRadioButton() {
		if (readOnlyRadioButton == null) {
			readOnlyRadioButton = new JRadioButton();
			readOnlyRadioButton.setText(ResourceUtil.getString("accesslevel.readonly"));
			readOnlyRadioButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return readOnlyRadioButton;
	}
	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getReadWriteRadioButton() {
		if (readWriteRadioButton == null) {
			readWriteRadioButton = new JRadioButton();
			readWriteRadioButton.setText(ResourceUtil.getString("accesslevel.readwrite"));
			readWriteRadioButton.setSelected(true);
			readWriteRadioButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return readWriteRadioButton;
	}
	/**
	 * This method initializes jRadioButton2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getDenyAccessRadioButton() {
		if (denyAccessRadioButton == null) {
			denyAccessRadioButton = new JRadioButton();
			denyAccessRadioButton.setText(ResourceUtil.getString("accesslevel.denyaccess"));
			denyAccessRadioButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return denyAccessRadioButton;
	}
	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getLevelOfAccessSubPanel() {
		if (levelOfAccessSubPanel == null) {
			GridLayout gridLayout6 = new GridLayout();
			levelOfAccessSubPanel = new JPanel();
			levelOfAccessSubPanel.setLayout(gridLayout6);
			gridLayout6.setRows(3);
			gridLayout6.setColumns(1);
		}
		return levelOfAccessSubPanel;
	}
	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getAllUsersRadioButton() {
		if (allUsersRadioButton == null) {
			allUsersRadioButton = new JRadioButton();
			allUsersRadioButton.setText(ResourceUtil.getString("addaccessrule.applyto.allusers"));
			allUsersRadioButton.setActionCommand("AllUsers");
			allUsersRadioButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			allUsersRadioButton.addActionListener(this);
		}
		return allUsersRadioButton;
	}
	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPathPanel() {
		if (pathPanel == null) {
			pathLabel = new JLabel();
			FlowLayout flowLayout8 = new FlowLayout();
			pathPanel = new JPanel();
			pathPanel.setLayout(flowLayout8);
			flowLayout8.setAlignment(java.awt.FlowLayout.LEFT);
			pathLabel.setText(ResourceUtil.getString("addaccessrule.path"));
			pathLabel.setPreferredSize(new java.awt.Dimension(100,15));
			pathPanel.add(pathLabel, null);
			pathPanel.add(getPathTextField(), null);
		}
		return pathPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getPathTextField() {
		if (pathTextField == null) {
			pathTextField = new JTextField();
			
			pathTextField.setColumns(30);
			
			pathTextField.setText(path);
		}
		return pathTextField;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAddRepositoryButton() {
		if (addRepositoryButton == null) {
			addRepositoryButton = new JButton();
			addRepositoryButton.setActionCommand("AddRepository");
			addRepositoryButton.setToolTipText(ResourceUtil.getString("addaccessrule.addrepository.tooltip"));
			addRepositoryButton.setText(ResourceUtil.getString("addaccessrule.addrepository"));
			addRepositoryButton.setPreferredSize(new java.awt.Dimension(56,25));
			addRepositoryButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			addRepositoryButton.addActionListener(this);
		}
		return addRepositoryButton;
	}
}
