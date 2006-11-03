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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that displays information about this application.
 * 
 * @author Shaun Johnson
 */
public class AboutDialog extends ParentDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2009320543683373156L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JPanel contentPanel = null;
	private JLabel titleLabel = null;
	private JLabel descriptionLabel = null;
	/**
	 * This is the default constructor
	 */
	public AboutDialog() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString("about.title"));
		this.setSize(300,200);
		this.setContentPane(getJContentPane());
		this.setModal(true);
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
			jContentPane.add(getContentPanel(), java.awt.BorderLayout.CENTER);
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
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getContentPanel() {
		if (contentPanel == null) {
			descriptionLabel = new JLabel();
			titleLabel = new JLabel();
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			titleLabel.setText(ResourceUtil.getString("application.nameversion"));
			titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			titleLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			descriptionLabel.setText(ResourceUtil.getString("about.content"));
			descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			contentPanel.add(titleLabel, java.awt.BorderLayout.NORTH);
			contentPanel.add(descriptionLabel, java.awt.BorderLayout.CENTER);
		}
		return contentPanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			dispose();
		}
	}
 }
