/*
 * Created on Jul 8, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.xiaoniu.suafe.FileGenerator;
import org.xiaoniu.suafe.exceptions.ApplicationException;

/**
 * @author Shaun Johnson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PreviewDialog extends JDialog implements ActionListener {

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
		this.setModal(true);
		this.setTitle("Preview");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
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
			okButton.setText("OK");
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
