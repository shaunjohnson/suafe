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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that displays the application license to the user.
 * 
 * @author Shaun Johnson
 */
public class LicenseDialog extends ParentDialog implements ActionListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 3553343708226187634L;

	private JPanel jContentPane = null;

	private JPanel buttonPanel = null;
	
	private JButton okButton = null;
	
	private JEditorPane licenseEditorPane = null;
	
	private JScrollPane licenseScrollPane = null;
	
	/**
	 * Default constructor.
	 */
	public LicenseDialog() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setResizable(false);
		this.setModal(true);
		this.setTitle(ResourceUtil.getString("license.title"));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(600, 600);
		this.setContentPane(getJContentPane());
		
		this.getRootPane().setDefaultButton(getOkButton());
	}
	
	/**
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel(new BorderLayout());
			jContentPane.add(getLicenseScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		
		return jContentPane;
	}
	
	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
		}
		
		return buttonPanel;
	}
	
	/**
	 * This method initializes okButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.addActionListener(this);
			okButton.setActionCommand(Constants.OK_ACTION);
			okButton.setText(ResourceUtil.getString("button.ok"));
		}
		
		return okButton;
	}
	
	/**
	 * ActionPerformed event handler.
	 * 
	 * @param event ActionEvent object.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(Constants.OK_ACTION)) {
			dispose();
		}
	}
	
	/**
	 * This method initializes licenseEditorPane.	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JEditorPane getLicenseEditorPane() {
		if (licenseEditorPane == null) {
			licenseEditorPane = new JEditorPane();
			licenseEditorPane.setFont(new Font("Courier New", Font.PLAIN, 12));
			licenseEditorPane.setEditable(false);
			
			String url = Constants.FULL_RESOURCE_DIR + "/LICENSE";
			URL helpUrl = this.getClass().getResource(url);
			
			if (helpUrl != null) {
				try {
					licenseEditorPane.setPage(helpUrl);
				} 
				catch (IOException e) {
					// Do nothing
				}
			}
		}
		
		return licenseEditorPane;
	}
	
	/**
	 * This method initializes licenseScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getLicenseScrollPane() {
		if (licenseScrollPane == null) {
			licenseScrollPane = new JScrollPane(getLicenseEditorPane());
		}
		
		return licenseScrollPane;
	}
 }
