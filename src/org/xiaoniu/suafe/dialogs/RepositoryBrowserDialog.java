/*
 * Created on Jul 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import org.tmatesoft.svn.core.io.SVNRepository;
import org.xiaoniu.suafe.beans.Subversion;
/**
 * @author spjohnso
 */
public class RepositoryBrowserDialog extends JDialog implements TreeSelectionListener, ActionListener {

	private String path;
	private javax.swing.JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTree jTree = null;
	private Subversion subversion = null;
	
	private JPanel jPanel = null;
	private JTextField pathTextField = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel = null;
	/**
	 * This is the default constructor
	 */
	public RepositoryBrowserDialog(String path) {
		super();
		initialize();
		
		this.path = path;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {				
		subversion = new Subversion(this);
        
		this.setTitle("Subversion Browser");
		this.setSize(600, 400);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		this.getRootPane().setDefaultButton(getJButton());
	}
    
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jLabel.setText("Make your selection and click OK.");
			jContentPane.add(jLabel, java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
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
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTree	
	 * 	
	 * @return javax.swing.JTree	
	 */    
	private JTree getJTree() {
		if (jTree == null) {
			SVNRepository repository = subversion.getRepository();
			
			if (repository == null) {
				dispose();
			}
			
			jTree = new JTree(new SvnTreeNode("/", repository));
			jTree.setShowsRootHandles(true);
			jTree.addTreeSelectionListener(this);
			jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		}
		return jTree;
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		SvnTreeNode node = (SvnTreeNode)getJTree().getLastSelectedPathComponent();
		String path = (node == null) ? "" : node.getPath();
		
		getPathTextField().setText(path);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getPathTextField(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getPathTextField() {
		if (pathTextField == null) {
			pathTextField = new JTextField();
			pathTextField.setEditable(false);
		}
		return pathTextField;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("OK");
			jButton.setActionCommand("OK");
			jButton.addActionListener(this);
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Cancel");
			jButton1.setActionCommand("Cancel");
			jButton1.addActionListener(this);
		}
		return jButton1;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			path = "Something new!!";
			dispose();			
		}
		else if (e.getActionCommand().equals("Cancel")) {
			dispose();			
		}		
	}
       }
