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

package org.xiaoniu.suafe.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Main Suafe help window.
 * 
 * @author Shaun Johnson
 */
public class ViewerFrame extends ParentFrame implements ActionListener, HyperlinkListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 5057005120918134417L;
	
	private String title = null;
	
	private String content = null;
	
	private String contentType = null;
	
	private JPanel jContentPane = null;

	private JPanel buttonPanel = null;

	private JButton closeButton = null;

	private JScrollPane contentScrollPane = null;

	private JEditorPane contentEditorPane = null;
	
	/**
	 * Default constructor.
	 */
	public ViewerFrame(String title, String content, String contentType) {
		super();
		
		this.title = title;
		this.content = content;
		this.contentType = contentType;
		
		initialize();
	}
	
	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setIconImage(ResourceUtil.serverImage);
		this.setTitle(title);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(800, 700);
		this.setContentPane(getJContentPane());
		this.center();
		
		getRootPane().setDefaultButton(getCloseButton());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
			jContentPane.add(getContentScrollPane(), BorderLayout.CENTER);
		}
		
		return jContentPane;
	}
	
	/**
	 * HyperlinkEvent listener.
	 * 
	 * @param event HyperlinkEvent object.
	 */
	public void hyperlinkUpdate(HyperlinkEvent event) {
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {			
			try {
				URL newUrl = event.getURL();
				
				getContentEditorPane().setPage(newUrl);
				
				if (newUrl.getFile().endsWith(".html")) {
					getContentEditorPane().setContentType(Constants.MIME_HTML);
				}
				else if (newUrl.getFile().endsWith(".txt")) {
					getContentEditorPane().setContentType(Constants.MIME_TEXT);
				}
				else {
					getContentEditorPane().setContentType(Constants.MIME_HTML);
				}
			} 
			catch (IOException e) {
				// Do nothing.
			}
		}
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.add(getCloseButton(), null);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText(ResourceUtil.getString("button.close"));
			closeButton.addActionListener(this);
			closeButton.setActionCommand(Constants.CLOSE_ACTION);
		}
		return closeButton;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(Constants.CLOSE_ACTION)) {
			dispose();
		}		
	}

	/**
	 * This method initializes contentScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getContentScrollPane() {
		if (contentScrollPane == null) {
			contentScrollPane = new JScrollPane();
			contentScrollPane.setViewportView(getContentEditorPane());
		}
		return contentScrollPane;
	}

	/**
	 * This method initializes contentEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getContentEditorPane() {
		if (contentEditorPane == null) {
			contentEditorPane = new JEditorPane();
			contentEditorPane.setContentType(contentType);
			contentEditorPane.setText(content);
			contentEditorPane.setEditable(false);
			contentEditorPane.select(0, 0);
			contentEditorPane.addHyperlinkListener(this);
			
			if (contentType == Constants.MIME_TEXT) {
				contentEditorPane.setFont(new Font("Courier New", Font.PLAIN, 12));
			}
		}
		return contentEditorPane;
	}
}