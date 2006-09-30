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
//import java.io.BufferedReader;
//import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.resources.License;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that displays the application license to the user.
 * 
 * @author Shaun Johnson
 */
public class LicenseDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 3553343708226187634L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JTextArea licenseTextArea = null;
	private JScrollPane licenseScrollPane = null;
	
	/**
	 * This is the default constructor
	 */
	public LicenseDialog() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setModal(true);
		this.setTitle(ResourceUtil.getString("license.title"));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
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
	
//	private String readLicense() {
//		StringBuffer license = new StringBuffer();
//		
//		try {
//			BufferedReader input = new BufferedReader(new FileReader("org/svn/resources/gpl.txt"));
//			String line = input.readLine();
//			
//			Document.initialize();
//			
//			while (line != null) {
//				license.append(line).append("\n");
//				line = input.readLine();
//			}
//			
//			input.close();
//		}
//		catch (Exception e) {
//			license.append(e.getMessage());
//		}
//		
//		return license.toString();
//	}
	
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
	private JTextArea getLicenseTextArea() {
		if (licenseTextArea == null) {
			licenseTextArea = new JTextArea();
			licenseTextArea.setText(License.licenseText);
			licenseTextArea.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12));
			licenseTextArea.setEditable(false);
			licenseTextArea.select(0, 0);
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
