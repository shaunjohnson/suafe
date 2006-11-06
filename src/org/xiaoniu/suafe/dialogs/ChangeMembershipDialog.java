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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.xiaoniu.suafe.Utilities;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that allows a user to change a user's group membership.
 * 
 * @author Shaun Johnson
 */
public class ChangeMembershipDialog extends ParentDialog implements ActionListener, MouseListener {

	private static final long serialVersionUID = 4595558087993098499L;
	private Message message = null;
	private User user = null;
	private Vector<Group> memberOf = null;
	private Vector<Group> notMemberOf =  null;
	private JPanel jContentPane = null;
	private JPanel buttonPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JPanel jPanel1 = null;
	private JList notMemberOfList = null;
	private JList memberOfList = null;
	private JButton unassignButton = null;
	private JButton assignButton = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JPanel jPanel4 = null;
	private JLabel jLabel1 = null;
	private JPanel jPanel5 = null;
	private JLabel jLabel2 = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	
	/**
	 * This is the default constructor
	 */
	public ChangeMembershipDialog(User user, Message message) {
		super();
		
		this.message = message;
		this.message.setState(Message.CANCEL);
		this.user = user;
		
		initialize();		
	}
	
	/**
	 * This method initializes this
	 */
	private void initialize() {
		try {
			Group[] groups = Document.getUserGroupsArray(user);
			
			if (groups != null) {
				List<Group> memberOfList = Arrays.asList(groups);
				memberOf = new Vector<Group>(memberOfList);
				Collections.sort(memberOf);
			}
			else {
				memberOf = new Vector<Group>();
			}
			
			Group[] allGroups = Document.getGroupsArray();
			
			if (allGroups != null) {
				List<Group> notMemberOfList = Arrays.asList(allGroups);
				notMemberOf = new Vector<Group>(notMemberOfList);
				Collections.sort(notMemberOf);
				notMemberOf.removeAll(memberOf);
			}
			else {
				notMemberOf = new Vector<Group>();
			}
		}
		catch (ApplicationException e) {
			displayError(ResourceUtil.getFormattedString("changemembership.error.errorloadinggroups", e.getMessage()));
		}
		
		this.setBounds(0, 0, 600, 500);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setTitle(ResourceUtil.getString("changemembership.title"));
		this.setContentPane(getJContentPane());
		this.setResizable(false);
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
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
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
			buttonPanel.add(getJButton1(), null);
			buttonPanel.add(getJButton(), null);
		}
		return buttonPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText(ResourceUtil.getString("button.cancel"));
			jButton.setActionCommand("Cancel");
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
			jButton1.setText(ResourceUtil.getString("button.save"));
			jButton1.setActionCommand("Save");
			jButton1.addActionListener(this);
			
			getRootPane().setDefaultButton(jButton1);
		}
		return jButton1;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setPreferredSize(new java.awt.Dimension(500,200));
			jPanel1.add(getJPanel4(), java.awt.BorderLayout.WEST);
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJPanel5(), java.awt.BorderLayout.EAST);
			jPanel1.add(getJPanel(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getNotMemberOfList() {
		if (notMemberOfList == null) {
			notMemberOfList = new JList();
			notMemberOfList.setListData(notMemberOf);
			notMemberOfList.addMouseListener(this);
			notMemberOfList.setCellRenderer(new MyListCellRenderer());
			notMemberOfList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return notMemberOfList;
	}
	/**
	 * This method initializes jList1	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getMemberOfList() {
		if (memberOfList == null) {
			memberOfList = new JList();
			memberOfList.setListData(memberOf);
			memberOfList.addMouseListener(this);
			memberOfList.setCellRenderer(new MyListCellRenderer());
			memberOfList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return memberOfList;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getUnassignButton() {
		if (unassignButton == null) {
			unassignButton = new JButton();
			unassignButton.setIcon(ResourceUtil.unassignIcon);
			unassignButton.setActionCommand("Unassign");
			unassignButton.addActionListener(this);
			unassignButton.setPreferredSize(new Dimension(75, 50));
		}
		return unassignButton;
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAssignButton() {
		if (assignButton == null) {
			assignButton = new JButton();
			assignButton.setIcon(ResourceUtil.assignIcon);
			assignButton.setActionCommand("Assign");
			assignButton.addActionListener(this);
			assignButton.setPreferredSize(new Dimension(75, 50));
		}
		return assignButton;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getNotMemberOfList());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getMemberOfList());
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel1 = new JLabel();
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jLabel1.setText(ResourceUtil.getString("changemembership.notmemberof"));
			jPanel4.setPreferredSize(new java.awt.Dimension(250,250));
			jPanel4.add(jLabel1, java.awt.BorderLayout.NORTH);
			jPanel4.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLabel2 = new JLabel();
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jLabel2.setText(ResourceUtil.getString("changemembership.memberof"));
			jPanel5.setPreferredSize(new java.awt.Dimension(250,250));
			jPanel5.add(jLabel2, java.awt.BorderLayout.NORTH);
			jPanel5.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}
	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			GridLayout gridLayout3 = new GridLayout();
			jPanel6 = new JPanel();
			jPanel6.setLayout(gridLayout3);
			gridLayout3.setRows(2);
			gridLayout3.setColumns(1);
			jPanel6.add(getAssignButton(), null);
			jPanel6.add(getUnassignButton(), null);
		}
		return jPanel6;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			FlowLayout flowLayout4 = new FlowLayout();
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout4);
			flowLayout4.setVgap(15);
			jPanel2.add(getJPanel6(), null);
		}
		return jPanel2;
	}
	
	private void displayError(String message) {
		JOptionPane.showMessageDialog(this, message, ResourceUtil.getString("application.error"), JOptionPane.ERROR_MESSAGE);
	}

	private void assign() {
		if (!getNotMemberOfList().isSelectionEmpty()) {
			Group[] groupObjects = Utilities.convertToArray(getNotMemberOfList().getSelectedValues(), new Group[0]);
			List<Group> values = Arrays.asList(groupObjects); 
			
			memberOf.addAll(values);
			notMemberOf.removeAll(values);
			
			Collections.sort(memberOf);
			Collections.sort(notMemberOf);
		
			getMemberOfList().setListData(memberOf);
			getNotMemberOfList().setListData(notMemberOf);
		}
	}
	
	private void unassign() {
		if (!getMemberOfList().isSelectionEmpty()) {		
			Group[] groupObjects = Utilities.convertToArray(getMemberOfList().getSelectedValues(), new Group[0]);
			List<Group> values = Arrays.asList(groupObjects);
			
			memberOf.removeAll(values);
			notMemberOf.addAll(values);
			
			Collections.sort(memberOf);
			Collections.sort(notMemberOf);
		
			getMemberOfList().setListData(memberOf);
			getNotMemberOfList().setListData(notMemberOf);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Assign")) {
			assign();
		}
		else if (e.getActionCommand().equals("Unassign")) {
			unassign();
		}		
		else if (e.getActionCommand().equals("Save")) {
			try {
				Document.changeUserMembership(user, memberOf);
				message.setUserObject(user);
				message.setState(Message.SUCCESS);
				dispose();
			}
			catch (Exception ex) {
				displayError(ex.getMessage());
			}
		}
		else if (e.getActionCommand().equals("Cancel")) {
			message.setState(Message.CANCEL);
			dispose();
		}		
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (e.getSource() == getNotMemberOfList()) {
				assign();
			}
			else if (e.getSource() == getMemberOfList()) {
				unassign();
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		// Not used
	}


	public void mouseReleased(MouseEvent e) {
		// Not used
	}


	public void mouseEntered(MouseEvent e) {
		// Not used
	}

	public void mouseExited(MouseEvent e) {
		// Not used
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,0,5,0));
			jLabel.setText(ResourceUtil.getFormattedString("changemembership.instructions", user.getName()));
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}
 }  //  @jve:decl-index=0:visual-constraint="10,10"
