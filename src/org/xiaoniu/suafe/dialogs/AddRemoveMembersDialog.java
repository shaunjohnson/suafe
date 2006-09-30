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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.GroupMemberObject;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that allows a user to add/remove members of a group.
 * 
 * @author Shaun Johnson
 */
public class AddRemoveMembersDialog extends JDialog implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2808087711449246371L;
	private Message message = null;
	private Group group = null;
	private Vector<Group> groupMembers = null;
	private Vector<Group> groupNonMembers =  null;
	private Vector<User> userMembers = null;
	private Vector<User> userNonMembers = null;
	private Vector<GroupMemberObject> members = null;
	private Vector<GroupMemberObject> nonMembers = null;
	private javax.swing.JPanel jContentPane = null;
	private JPanel buttonPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JPanel jPanel1 = null;
	private JList nonMemberList = null;
	private JList memberList = null;
	private JButton unassignGroupButton = null;
	private JButton assignGroupButton = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JPanel jPanel4 = null;
	private JLabel jLabel1 = null;
	private JPanel jPanel5 = null;
	private JLabel jLabel2 = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel = null;
	private JPanel jPanel3 = null;
	private JLabel jLabel = null;
	/**
	 * This is the default constructor
	 */
	public AddRemoveMembersDialog(Group group, Message message) {
		super();
		
		this.message = message;
		this.group = group;
		
		initialize();		
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		try {
			// Initialize groups
			Group[] groupMembersObjects = (Group[])Document.getGroupMemberGroupObjects(group);
			
			if (groupMembersObjects != null) {
				List<Group> groupMembersList = Arrays.asList(groupMembersObjects);
				groupMembers = new Vector<Group>(groupMembersList);
				Collections.sort(groupMembers);
			}
			else {
				groupMembers = new Vector<Group>();
			}
			
			Group[] groupObjects = (Group[])Document.getGroupObjects();
			
			if (groupObjects != null) {
				List<Group> groupNonMembersList = Arrays.asList(groupObjects);
				groupNonMembers = new Vector<Group>(groupNonMembersList);

				// Remove group being edited
				groupNonMembers.remove(group);
				
				Collections.sort(groupNonMembers);
				groupNonMembers.removeAll(groupMembers);
				
				// Remove the group being edited.
				groupNonMembers.remove(group.getName());
			}
			else {
				groupNonMembers = new Vector<Group>();
			}			
			
			// Initialize users
			User[] userMembersObjects = (User[])Document.getGroupMemberUserObjects(group);
			
			if (userMembersObjects != null) {
				List<User> userMembersList = Arrays.asList(userMembersObjects);
				userMembers = new Vector<User>(userMembersList);
				Collections.sort(userMembers);
			}
			else {
				userMembers = new Vector<User>();
			}
			
			User[] userObjects = (User[])Document.getUserObjectsExcludeAllUsers();
			
			if (userObjects != null) {
				List<User> userNonMembersList = Arrays.asList(userObjects);
				userNonMembers = new Vector<User>(userNonMembersList);
				Collections.sort(userNonMembers);			
				userNonMembers.removeAll(userMembers);
			}
			else {
				userNonMembers = new Vector<User>();
			}
			
			members = new Vector<GroupMemberObject>(groupMembers.size() + userMembers.size());
			nonMembers = new Vector<GroupMemberObject>(groupNonMembers.size() + userNonMembers.size());
			
			members.addAll(groupMembers);
			members.addAll(userMembers);
			nonMembers.addAll(groupNonMembers);
			nonMembers.addAll(userNonMembers);
		}
		catch (ApplicationException e) {
			displayError(ResourceUtil.getFormattedString("addremovemembers.error.errorloadinggroups", e.getMessage()));
		}
		
		this.setBounds(0, 0, 600, 500);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setTitle(ResourceUtil.getString("addremovemembers.title"));
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
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
			
			jPanel1.add(getJPanel4(), java.awt.BorderLayout.WEST);
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJPanel5(), java.awt.BorderLayout.EAST);
			jPanel1.add(getJPanel3(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getNonMemberList() {
		if (nonMemberList == null) {
			nonMemberList = new JList();
			nonMemberList.setListData(nonMembers);
			nonMemberList.addMouseListener(this);
			nonMemberList.setCellRenderer(new MyListCellRenderer());
			nonMemberList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return nonMemberList;
	}
	/**
	 * This method initializes jList1	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getMemberList() {
		if (memberList == null) {
			memberList = new JList();
			memberList.setListData(members);
			memberList.addMouseListener(this);
			memberList.setCellRenderer(new MyListCellRenderer());
			memberList.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}
		return memberList;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getUnassignGroupButton() {
		if (unassignGroupButton == null) {
			unassignGroupButton = new JButton();
			unassignGroupButton.setIcon(new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/navigation/Back24.gif")));
			unassignGroupButton.setActionCommand("UnassignMember");
			unassignGroupButton.setPreferredSize(new Dimension(75, 50));
			unassignGroupButton.addActionListener(this);
		}
		return unassignGroupButton;
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAssignGroupButton() {
		if (assignGroupButton == null) {
			assignGroupButton = new JButton();
			assignGroupButton.setIcon(new ImageIcon(getClass().getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/navigation/Forward24.gif")));
			assignGroupButton.setActionCommand("AssignMember");
			assignGroupButton.setPreferredSize(new Dimension(75, 50));
			assignGroupButton.addActionListener(this);
		}
		return assignGroupButton;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getNonMemberList());
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
			jScrollPane1.setViewportView(getMemberList());
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
			jLabel1.setText(ResourceUtil.getString("addremovemembers.nonmembers"));
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
			jLabel2.setText(ResourceUtil.getString("addremovemembers.members"));
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
			gridLayout3.setVgap(0);
			jPanel6.add(getAssignGroupButton(), null);
			jPanel6.add(getUnassignGroupButton(), null);
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
			FlowLayout flowLayout3 = new FlowLayout();
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout3);
			flowLayout3.setVgap(15);
			jPanel2.add(getJPanel6(), null);
		}
		return jPanel2;
	}
	
	private void displayError(String message) {
		JOptionPane.showMessageDialog(this, message, ResourceUtil.getString("application.error"), JOptionPane.ERROR_MESSAGE);
	}
	
	private void assignMember() {
		if (!getNonMemberList().isSelectionEmpty()) {
			List values = Arrays.asList(getNonMemberList().getSelectedValues());
					
			for (Object object : values) {
				if (object instanceof Group) {
					groupMembers.add((Group) object);
					groupNonMembers.remove(object);
				}
				if (object instanceof User) {
					userMembers.add((User) object);
					userNonMembers.remove(object);
				}
			}
			
			refreshLists();
		}
	}
	
	private void unassignMember() {
		if (!getMemberList().isSelectionEmpty()) {
			List values = Arrays.asList(getMemberList().getSelectedValues());
			
			for (Object object : values) {	
				if (object instanceof Group) {
					groupMembers.remove(object);
					groupNonMembers.add((Group) object);
				}
				if (object instanceof User) {
					userMembers.remove(object);
					userNonMembers.add((User) object);
				}
			}
			
			refreshLists();
		}
	}
	
	private void refreshLists() {
		Collections.sort(groupMembers);
		Collections.sort(userMembers);
		Collections.sort(groupNonMembers);
		Collections.sort(userNonMembers);
		
		members = new Vector<GroupMemberObject>(groupMembers.size() + userMembers.size());
		nonMembers = new Vector<GroupMemberObject>(groupNonMembers.size() + userNonMembers.size());
		
		members.addAll(groupMembers);
		members.addAll(userMembers);
		
		nonMembers.addAll(groupNonMembers);
		nonMembers.addAll(userNonMembers);
	
		getMemberList().setListData(members);
		getNonMemberList().setListData(nonMembers);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("AssignMember")) {
			assignMember();
		}
		else if (e.getActionCommand().equals("UnassignMember")) {
			unassignMember();
		}	
		else if (e.getActionCommand().equals("Save")) {
			try {
				Document.changeGroupMembers(group, groupMembers, userMembers);
				message.setUserObject(group);
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
			if (e.getSource() == getNonMemberList()) {
				assignMember();
			}
			else if (e.getSource() == getMemberList()) {
				unassignMember();
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
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
			jPanel.add(getJPanel1(), null);
			
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel = new JLabel();
			jPanel3 = new JPanel();
			jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,0,5,0));
			jLabel.setText(ResourceUtil.getFormattedString("addremovemembers.instructions",  group.getName()));
			jPanel3.add(jLabel, null);
		}
		return jPanel3;
	}
 }  //  @jve:decl-index=0:visual-constraint="10,10"
