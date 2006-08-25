/*
 * Created on Aug 4, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.frames;

import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.awt.Toolkit;
import javax.swing.JSplitPane;

import org.xiaoniu.suafe.resources.ResourceUtil;
/**
 * @author spjohnso
 */
public class HelpFrame extends BaseFrame implements HyperlinkListener {

	private javax.swing.JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane jEditorPane = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPane1 = null;
	private JEditorPane jEditorPane1 = null;
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setEditable(false);
			jEditorPane.setContentType("text/html");
			jEditorPane.addHyperlinkListener(this);
			
			URL helpUrl = this.getClass().getResource("/org/xiaoniu/suafe/resources/help/en/welcome.html");
			
			if (helpUrl != null) {
				try {
					getJEditorPane().setPage(helpUrl);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return jEditorPane;
	}
	
	public void hyperlinkUpdate(HyperlinkEvent evt) {
		if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			//JEditorPane pane = (JEditorPane)evt.getSource();
			
			try {
				URL newUrl = evt.getURL();
				
				getJEditorPane().setPage(newUrl);
				
				if (newUrl.getFile().endsWith(".html")) {
					getJEditorPane().setContentType("text/html");
				}
				else if (newUrl.getFile().endsWith(".txt")) {
					getJEditorPane().setContentType("text/plain");
				}
				else {
					getJEditorPane().setContentType("text/html");
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
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setRightComponent(getJScrollPane());
			jSplitPane.setLeftComponent(getJScrollPane1());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJEditorPane1());
			jScrollPane1.setPreferredSize(new java.awt.Dimension(200,24));
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jEditorPane1	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getJEditorPane1() {
		if (jEditorPane1 == null) {
			jEditorPane1 = new JEditorPane();
			jEditorPane1.setEditable(false);
			jEditorPane1.setContentType("text/html");
			jEditorPane1.addHyperlinkListener(this);
			
			URL helpUrl = this.getClass().getResource("/org/xiaoniu/suafe/resources/help/en/toc.html");
			
			if (helpUrl != null) {
				try {
					getJEditorPane1().setPage(helpUrl);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return jEditorPane1;
	}
     }
