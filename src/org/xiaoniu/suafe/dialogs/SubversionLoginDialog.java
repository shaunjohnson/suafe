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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xiaoniu.suafe.beans.Subversion;
import javax.swing.JPasswordField;

/**
 * Dialog that allows a user to specify Subversion credentials.
 * 
 * @author Shaun Johnson
 */
public class SubversionLoginDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 8590748922235609033L;
	private javax.swing.JPanel jContentPane = null;
	private JPanel buttonPanel = null;
	private JButton loginButton = null;
	private JButton cancelButton = null;
	private JPanel formPanel = null;
	private JLabel instructionsLabel = null;
	private JTextField urlTextField = null;
	private JTextField userNameTextField = null;
	private JPasswordField passwordTextField = null;
	private JPanel urlPanel = null;
	private JPanel userNamePanel = null;
	private JPanel passwordPanel = null;
	private JLabel urlLabel = null;
	private JLabel userNameLabel = null;
	private JLabel passwordLabel = null;
	private Subversion subversion;
	/**
	 * This is the default constructor
	 */
	public SubversionLoginDialog(Subversion subversion) {
		super();
		
		this.subversion = subversion;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setModal(true);
		this.setSize(378, 174);
		this.setContentPane(getJContentPane());
		
		this.getRootPane().setDefaultButton(getLoginButton());
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
			instructionsLabel.setText("Enter Subversion URL, user name and password.");
			jContentPane.add(instructionsLabel, java.awt.BorderLayout.NORTH);
			jContentPane.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getFormPanel(), java.awt.BorderLayout.CENTER);
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
			buttonPanel = new JPanel();
			buttonPanel.add(getLoginButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getLoginButton() {
		if (loginButton == null) {
			loginButton = new JButton();
			loginButton.setText("Login");
			loginButton.setActionCommand("Login");
			loginButton.addActionListener(this);
		}
		return loginButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
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
	private JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new JPanel();
			formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
			formPanel.add(getUrlPanel(), null);
			formPanel.add(getUserNamePanel(), null);
			formPanel.add(getPasswordPanel(), null);
		}
		return formPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getUrlTextField() {
		if (urlTextField == null) {
			urlTextField = new JTextField();
			urlTextField.setColumns(25);
		}
		return urlTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getUserNameTextField() {
		if (userNameTextField == null) {
			userNameTextField = new JTextField();
			userNameTextField.setColumns(15);
		}
		return userNameTextField;
	}
	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JPasswordField getPasswordTextField() {
		if (passwordTextField == null) {
			passwordTextField = new JPasswordField();
			passwordTextField.setColumns(15);
		}
		return passwordTextField;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getUrlPanel() {
		if (urlPanel == null) {
			urlLabel = new JLabel();
			FlowLayout flowLayout2 = new FlowLayout();
			urlPanel = new JPanel();
			urlPanel.setLayout(flowLayout2);
			urlLabel.setText("URL:");
			urlLabel.setPreferredSize(new java.awt.Dimension(70,15));
			urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			urlPanel.add(urlLabel, null);
			urlPanel.add(getUrlTextField(), null);
		}
		return urlPanel;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getUserNamePanel() {
		if (userNamePanel == null) {
			userNameLabel = new JLabel();
			FlowLayout flowLayout3 = new FlowLayout();
			userNamePanel = new JPanel();
			userNamePanel.setLayout(flowLayout3);
			userNameLabel.setText("User Name:");
			userNameLabel.setPreferredSize(new java.awt.Dimension(70,15));
			userNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
			flowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			userNamePanel.add(userNameLabel, null);
			userNamePanel.add(getUserNameTextField(), null);
		}
		return userNamePanel;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPasswordPanel() {
		if (passwordPanel == null) {
			passwordLabel = new JLabel();
			FlowLayout flowLayout4 = new FlowLayout();
			passwordPanel = new JPanel();
			passwordPanel.setLayout(flowLayout4);
			passwordLabel.setText("Password:");
			passwordLabel.setPreferredSize(new java.awt.Dimension(70,15));
			passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
			flowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			passwordPanel.add(passwordLabel, null);
			passwordPanel.add(getPasswordTextField(), null);
		}
		return passwordPanel;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Login")) {
			subversion.setUrl(getUrlTextField().getText());
			
			String userName = getUserNameTextField().getText();
			String password = new String(getPasswordTextField().getPassword());
			
			subversion.setUserName(userName.trim());
			subversion.setPassword(password.trim());
			
			subversion.setLoginCancel(false);
			dispose();
		}
		else {
			subversion.setLoginCancel(true);
			dispose();
		}
	}
           }  //  @jve:decl-index=0:visual-constraint="10,10"
