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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.FileGenerator;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that displays a preview of the authz file being edited.
 * 
 * @author Shaun Johnson
 */
public class PreviewDialog extends ParentDialog implements ActionListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 7030606022506692974L;

	private JPanel jContentPane = null;

	private JPanel buttonPanel = null;
	
	private JButton okButton = null;
	
	private JTextArea previewTextArea = null;
	
	private JScrollPane previewScrollPane = null;
	
	/**
	 * Default constructor.
	 */
	public PreviewDialog() {
		super();
		
		initialize();
	}
	
	/**
	 * This method initializes this.
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
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getPreviewScrollPane(), BorderLayout.CENTER);
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
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton());
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
	 * This method initializes previewTextArea.	
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
			
			previewTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
			previewTextArea.setEditable(false);
			previewTextArea.select(0, 0);
		}
		
		return previewTextArea;
	}
	
	/**
	 * This method initializes previewScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getPreviewScrollPane() {
		if (previewScrollPane == null) {
			previewScrollPane = new JScrollPane(getPreviewTextArea());
		}
		
		return previewScrollPane;
	}
 }
