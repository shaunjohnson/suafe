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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.xiaoniu.suafe.FileGenerator;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that displays a preview of the authz file being edited.
 * 
 * @author Shaun Johnson
 */
public class PreviewDialog extends ParentDialog implements ActionListener {

	private static final long serialVersionUID = 7030606022506692974L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JTextArea previewTextArea = null;
	private JScrollPane previewScrollPane = null;
	/**
	 * This is the default constructor
	 */
	public PreviewDialog() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setBounds(0, 0, 800, 600);
		this.setModal(true);
		this.setTitle(ResourceUtil.getString("preview.title"));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
			jContentPane.add(getPreviewScrollPane(), java.awt.BorderLayout.CENTER);
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
	private JTextArea getPreviewTextArea() {
		if (previewTextArea == null) {
			previewTextArea = new JTextArea();
			
			try {
				previewTextArea.setText(FileGenerator.generate());
			}
			catch (ApplicationException ae) {
				previewTextArea.setText(ae.getMessage());
			}
			
			previewTextArea.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12));
			previewTextArea.setEditable(false);
			previewTextArea.select(0, 0);
		}
		return previewTextArea;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getPreviewScrollPane() {
		if (previewScrollPane == null) {
			previewScrollPane = new JScrollPane();
			previewScrollPane.setViewportView(getPreviewTextArea());
		}
		return previewScrollPane;
	}
 }
