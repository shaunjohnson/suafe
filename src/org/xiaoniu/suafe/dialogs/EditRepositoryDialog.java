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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;


/**
 * Dialog that allows a user to edit a Repository.
 * 
 * @author Shaun Johnson
 */
public class EditRepositoryDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = -6556917867353535060L;
	private Message message;
	private javax.swing.JPanel jContentPane = null;
	private Repository repository = null;
	private JPanel buttonPanel = null;
	private JButton saveButton = null;
	private JButton cancelButton = null;
	private JPanel buttonSubPanel = null;
	private JPanel formPanel = null;
	private JPanel formSubPanel = null;
	private JLabel repositoryNameLabel = null;
	private JTextField repositoryNameText = null;
	private JLabel instructionsLabel = null;
	
	/**
	 * This is the default constructor
	 */
	public EditRepositoryDialog(Repository repository, Message message) {
		super();
		
		this.repository = repository;
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
		this.setTitle(ResourceUtil.getString("editrepository.title"));
		this.setSize(470, 135);
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
			instructionsLabel.setText(ResourceUtil.getString("editrepository.instructions"));
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
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText(ResourceUtil.getString("button.save"));
			saveButton.setActionCommand("Save");
			saveButton.addActionListener(this);
			
			getRootPane().setDefaultButton(saveButton);
		}
		return saveButton;
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
			buttonSubPanel.add(getSaveButton(), null);
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
			repositoryNameLabel = new JLabel();
			formSubPanel = new JPanel();
			formSubPanel.setLayout(new FlowLayout());
			repositoryNameLabel.setText(ResourceUtil.getString("editrepository.repositoryname"));
			formSubPanel.add(repositoryNameLabel, null);
			formSubPanel.add(getRepositoryNameText(), null);
		}
		return formSubPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getRepositoryNameText() {
		if (repositoryNameText == null) {
			repositoryNameText = new JTextField();
			repositoryNameText.setColumns(30);
			repositoryNameText.setText(repository.getName());
		}
		return repositoryNameText;
	}
	
	private void displayError(String message) {
		JOptionPane.showMessageDialog(this, message, ResourceUtil.getString("application.error"), JOptionPane.ERROR_MESSAGE);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			try {
				String repositoryName = getRepositoryNameText().getText();
				
				Validator.validateNotEmptyString(ResourceUtil.getString("editrepository.repositoryname"), repositoryName);
				Validator.validateRepositoryName(repositoryName);
				
				Repository existingRepository = Document.findRepository(repositoryName);
				
				if (existingRepository == null || existingRepository == repository) {				
					repository.setName(repositoryName);
					message.setUserObject(repository);
					message.setState(Message.SUCCESS);
					dispose();
				}
				else {
					displayError(ResourceUtil.getFormattedString("editrepository.error.repositoryalreadyexists", repositoryName));
				}	
			}
			catch (ApplicationException ex) {
				displayError(ex.getMessage());
			}
		}
		else if (e.getActionCommand().equals("Cancel")) {
			dispose();
			message.setState(Message.CANCEL);
		}
	}
}
