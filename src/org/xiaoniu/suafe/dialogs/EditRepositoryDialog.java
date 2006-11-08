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

import java.awt.BorderLayout;
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
public class EditRepositoryDialog extends ParentDialog implements ActionListener {
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -6556917867353535060L;
	
	private Message message;
	
	private JPanel jContentPane = null;
	
	private Repository repository = null;
	
	private JPanel buttonPanel = null;
	
	private JButton saveButton = null;
	
	private JButton cancelButton = null;
	
	private JPanel buttonSubPanel = null;
	
	private JPanel formPanel = null;
	
	private JPanel formSubPanel = null;
	
	private JTextField repositoryNameText = null;
	
	/**
	 * Default constructor.
	 */
	public EditRepositoryDialog(Repository repository, Message message) {
		super();
		
		this.repository = repository;
		this.message = message;
		this.message.setState(Message.CANCEL);
		
		initialize();
	}
	
	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString("editrepository.title"));
		this.setSize(470, 135);
		this.setContentPane(getJContentPane());
		
		getRootPane().setDefaultButton(saveButton);
	}
	
	/**
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(new JLabel(ResourceUtil.getString("editrepository.instructions")), BorderLayout.NORTH);
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
			buttonPanel.add(getButtonSubPanel(), null);
		}
		
		return buttonPanel;
	}
	
	/**
	 * This method initializes saveButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.addActionListener(this);
			saveButton.setActionCommand(Constants.SAVE_ACTION);
			saveButton.setText(ResourceUtil.getString("button.save"));
		}
		
		return saveButton;
	}
	
	/**
	 * This method initializes cancelButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.addActionListener(this);
			cancelButton.setActionCommand(Constants.CANCEL_ACTION);
			cancelButton.setText(ResourceUtil.getString("button.cancel"));
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
			buttonSubPanel.add(getSaveButton(), null);
			buttonSubPanel.add(getCancelButton(), null);
		}
		
		return buttonSubPanel;
	}
	
	/**
	 * This method initializes formPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new JPanel(new FlowLayout());
			formPanel.add(getFormSubPanel(), null);
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
			formSubPanel.add(new JLabel(ResourceUtil.getString("editrepository.repositoryname")));
			formSubPanel.add(getRepositoryNameText());
		}
		
		return formSubPanel;
	}
	
	/**
	 * This method initializes repositoryNameText.	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getRepositoryNameText() {
		if (repositoryNameText == null) {
			repositoryNameText = new JTextField(30);
			repositoryNameText.setText(repository.getName());
		}
		
		return repositoryNameText;
	}
	
	/**
	 * ActionPerformed event handler.
	 * 
	 * @param event ActionEvent object.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(Constants.SAVE_ACTION)) {
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
		else if (event.getActionCommand().equals(Constants.CANCEL_ACTION)) {
			dispose();
			message.setState(Message.CANCEL);
		}
	}
}
