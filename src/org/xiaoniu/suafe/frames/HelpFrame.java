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

import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.awt.Toolkit;
import javax.swing.JSplitPane;

import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Main Suafe help window.
 * 
 * @author Shaun Johnson
 */
public class HelpFrame extends ParentFrame implements HyperlinkListener {

	private static final long serialVersionUID = 5057005120918134417L;
	private javax.swing.JPanel jContentPane = null;
	private JScrollPane contentScrollPane = null;
	private JEditorPane contentEditorPane = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane tableOfContentsScrollPane = null;
	private JEditorPane tableOfContentsEditorPane = null;
	/**
	 * This is the default constructor
	 */
	public HelpFrame() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/xiaoniu/suafe/resources/Server16.gif")));
		this.setTitle(ResourceUtil.getFormattedString("application.name",
		"Help"));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(800, 700);
		this.setContentPane(getJContentPane());
		this.center();
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
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
			jContentPane.add(getSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jScrollPane	
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
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getContentEditorPane() {
		if (contentEditorPane == null) {
			contentEditorPane = new JEditorPane();
			contentEditorPane.setEditable(false);
			contentEditorPane.setContentType("text/html");
			contentEditorPane.addHyperlinkListener(this);
			
			URL helpUrl = this.getClass().getResource("/org/xiaoniu/suafe/resources/help/en/welcome.html");
			
			if (helpUrl != null) {
				try {
					getContentEditorPane().setPage(helpUrl);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return contentEditorPane;
	}
	
	public void hyperlinkUpdate(HyperlinkEvent evt) {
		if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			//JEditorPane pane = (JEditorPane)evt.getSource();
			
			try {
				URL newUrl = evt.getURL();
				
				getContentEditorPane().setPage(newUrl);
				
				if (newUrl.getFile().endsWith(".html")) {
					getContentEditorPane().setContentType("text/html");
				}
				else if (newUrl.getFile().endsWith(".txt")) {
					getContentEditorPane().setContentType("text/plain");
				}
				else {
					getContentEditorPane().setContentType("text/html");
				}
			} 
			catch (IOException e) {
				
			}
		}
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getTableOfContentsScrollPane());
			jSplitPane.setRightComponent(getContentScrollPane());
			jSplitPane.setDividerLocation(200);
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getTableOfContentsScrollPane() {
		if (tableOfContentsScrollPane == null) {
			tableOfContentsScrollPane = new JScrollPane();
			tableOfContentsScrollPane.setViewportView(getTableOfContentsEditorPane());
		}
		return tableOfContentsScrollPane;
	}
	/**
	 * This method initializes jEditorPane1	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getTableOfContentsEditorPane() {
		if (tableOfContentsEditorPane == null) {
			tableOfContentsEditorPane = new JEditorPane();
			tableOfContentsEditorPane.setEditable(false);
			tableOfContentsEditorPane.setContentType("text/html");
			tableOfContentsEditorPane.addHyperlinkListener(this);
			
			URL helpUrl = this.getClass().getResource("/org/xiaoniu/suafe/resources/help/en/toc.html");
			
			if (helpUrl != null) {
				try {
					getTableOfContentsEditorPane().setPage(helpUrl);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return tableOfContentsEditorPane;
	}
     }
