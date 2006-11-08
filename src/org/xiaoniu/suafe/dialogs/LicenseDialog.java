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

	private static final long serialVersionUID = 3553343708226187634L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel buttonPanel = null;
	
	private JButton okButton = null;
	
	private JEditorPane licenseTextArea = null;
	
	private JScrollPane licenseScrollPane = null;
	
	/**
	 * This is the default constructor
	 */
	public LicenseDialog() {
		super();
		initialize();
		loadLicense();
	}
	
	/**
	 * This method initializes this
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
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getLicenseScrollPane(), java.awt.BorderLayout.CENTER);
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
			buttonPanel.add(getOkButton(), null);
		}
		return buttonPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(ResourceUtil.getString("button.ok"));
			okButton.setActionCommand("OK");
			okButton.addActionListener(this);
			
			getRootPane().setDefaultButton(okButton);
		}
		return okButton;
	}
	
	private void loadLicense() {
		String url = Constants.FULL_RESOURCE_DIR + "/LICENSE";
		URL helpUrl = this.getClass().getResource(url);
		
		if (helpUrl != null) {
			try {
				getLicenseTextArea().setPage(helpUrl);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			dispose();
		}
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JEditorPane getLicenseTextArea() {
		if (licenseTextArea == null) {
			licenseTextArea = new JEditorPane();
			licenseTextArea.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12));
			licenseTextArea.setEditable(false);
		}
		return licenseTextArea;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getLicenseScrollPane() {
		if (licenseScrollPane == null) {
			licenseScrollPane = new JScrollPane();
			licenseScrollPane.setViewportView(getLicenseTextArea());
		}
		return licenseScrollPane;
	}
 }
