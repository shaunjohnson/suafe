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
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Main Suafe help window.
 * 
 * @author Shaun Johnson
 */
public class HelpFrame extends ParentFrame implements HyperlinkListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 5057005120918134417L;
	
	private JPanel jContentPane = null;
	
	private JScrollPane contentScrollPane = null;
	
	private JEditorPane contentEditorPane = null;
	
	private JSplitPane contentSplitPane = null;
	
	private JScrollPane tableOfContentsScrollPane = null;
	
	private JEditorPane tableOfContentsEditorPane = null;
	
	/**
	 * Default constructor.
	 */
	public HelpFrame() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setIconImage(ResourceUtil.serverImage);
		this.setTitle(ResourceUtil.getString("help.title"));
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
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getContentSplitPane(), BorderLayout.CENTER);
		}
		
		return jContentPane;
	}
	
	/**
	 * This method initializes contentScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getContentScrollPane() {
		if (contentScrollPane == null) {
			contentScrollPane = new JScrollPane(getContentEditorPane());
		}
		
		return contentScrollPane;
	}
	
	/**
	 * This method initializes contentEditorPane.	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getContentEditorPane() {
		if (contentEditorPane == null) {
			contentEditorPane = new JEditorPane();
			contentEditorPane.setEditable(false);
			contentEditorPane.setContentType("text/html");
			contentEditorPane.addHyperlinkListener(this);
			
			String url = Constants.HELP_DIR
				+ "/"
				+ ResourceUtil.getString("application.language")
				+ "/welcome.html";
			URL helpUrl = this.getClass().getResource(url);
			
			if (helpUrl != null) {
				try {
					getContentEditorPane().setPage(helpUrl);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return contentEditorPane;
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
				// Do nothing.
			}
		}
	}
	
	/**
	 * This method initializes contentSplitPane.	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getContentSplitPane() {
		if (contentSplitPane == null) {
			contentSplitPane = new JSplitPane();
			contentSplitPane.setLeftComponent(getTableOfContentsScrollPane());
			contentSplitPane.setRightComponent(getContentScrollPane());
			contentSplitPane.setDividerLocation(200);
		}
		
		return contentSplitPane;
	}
	
	/**
	 * This method initializes tableOfContentsScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getTableOfContentsScrollPane() {
		if (tableOfContentsScrollPane == null) {
			tableOfContentsScrollPane = new JScrollPane(getTableOfContentsEditorPane());
		}
		
		return tableOfContentsScrollPane;
	}
	
	/**
	 * This method initializes tableOfContentsEditorPane.	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getTableOfContentsEditorPane() {
		if (tableOfContentsEditorPane == null) {
			tableOfContentsEditorPane = new JEditorPane();
			tableOfContentsEditorPane.setEditable(false);
			tableOfContentsEditorPane.setContentType("text/html");
			tableOfContentsEditorPane.addHyperlinkListener(this);
			
			String url = Constants.HELP_DIR 
				+ "/"
				+ ResourceUtil.getString("application.language")
				+ "/toc.html";
			URL helpUrl = this.getClass().getResource(url);
			
			if (helpUrl != null) {
				try {
					getTableOfContentsEditorPane().setPage(helpUrl);
				} 
				catch (IOException e) {
					// Do nothing
				}
			}
		}
		
		return tableOfContentsEditorPane;
	}
}
