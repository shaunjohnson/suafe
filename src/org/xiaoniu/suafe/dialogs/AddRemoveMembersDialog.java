/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
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
public class AddRemoveMembersDialog extends ParentDialog implements 
	ActionListener, KeyListener, MouseListener {

	/**
	 * Serial ID.
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
	
	private JPanel jContentPane = null;
	
	private JPanel buttonPanel = null;
	
	private JButton cancelButton = null;
	
	private JButton saveButton = null;
	
	private JList nonMemberList = null;
	
	private JList memberList = null;
	
	private JButton unassignGroupButton = null;
	
	private JButton assignGroupButton = null;
	
	private JScrollPane nonMemberListScrollPane = null;
	
	private JScrollPane memberListScrollPane = null;
	
	private JPanel nonMemberPanel = null;
	
	private JPanel memberPanel = null;
	
	private JPanel actionSubPanel = null;
	
	private JPanel actionPanel = null;
	
	private JPanel formPanel = null;
	
	private JPanel formSubPanel = null;
	
	private JPanel instructionsPanel = null;
	
	/**
	 * Default constructor.
	 */
	public AddRemoveMembersDialog(Group group, Message message) {
		super();
		
		this.message = message;
		this.message.setState(Message.CANCEL);
		this.group = group;
		
		initialize();		
	}
	
	/**
	 * This method initializes this
	 */
	private void initialize() {
		try {
			// Initialize groups
			Group[] groupMembersObjects = Document.getGroupMemberGroups(group);
			
			if (groupMembersObjects != null) {
				List<Group> groupMembersList = Arrays.asList(groupMembersObjects);
				groupMembers = new Vector<Group>(groupMembersList);
				Collections.sort(groupMembers);
			}
			else {
				groupMembers = new Vector<Group>();
			}
			
			Group[] groupObjects = Document.getGroupsArray();
			
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
			User[] userMembersObjects = Document.getGroupMemberUsers(group);
			
			if (userMembersObjects != null) {
				List<User> userMembersList = Arrays.asList(userMembersObjects);
				userMembers = new Vector<User>(userMembersList);
				Collections.sort(userMembers);
			}
			else {
				userMembers = new Vector<User>();
			}
			
			User[] userObjects = Document.getUserObjectsExcludeAllUsers();
			
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
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString("addremovemembers.title"));
		this.setContentPane(getJContentPane());
		this.setIconImage(ResourceUtil.serverImage);
		this.setMinimumSize(new Dimension(500, 300));
		
		getRootPane().setDefaultButton(saveButton);
		
		this.pack();
		this.setModal(true);
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getFormPanel(), BorderLayout.CENTER);
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
			buttonPanel.add(getSaveButton());
			buttonPanel.add(getCancelButton());
		}
		
		return buttonPanel;
	}
	
	/**
	 * This method initializes cancelButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.addActionListener(this);
			cancelButton.setActionCommand(Constants.CANCEL_ACTION);
			cancelButton.setText(ResourceUtil.getString("button.cancel"));
		}
		
		return cancelButton;
	}
	
	/**
	 * This method initializes saveButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.addActionListener(this);
			saveButton.setActionCommand(Constants.SAVE_ACTION);
			saveButton.setText(ResourceUtil.getString("button.save"));
		}
		
		return saveButton;
	}
	
	/**
	 * This method initializes formPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new JPanel(new BorderLayout());
						
			formPanel.add(getInstructionsPanel(), BorderLayout.NORTH);
			formPanel.add(getFormSubPanel(), BorderLayout.CENTER);
		}
		
		return formPanel;
	}
	
	/**
	 * This method initializes formPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getFormSubPanel() {
		if (formSubPanel == null) {
			formSubPanel = new JPanel();
			formSubPanel.setLayout(new BoxLayout(formSubPanel, BoxLayout.X_AXIS));
			
			formSubPanel.add(getNonMemberPanel());
			formSubPanel.add(getActionPanel());			
			formSubPanel.add(getMemberPanel());
		}
		
		return formSubPanel;
	}
	
	/**
	 * This method initializes nonMemberList.	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getNonMemberList() {
		if (nonMemberList == null) {
			nonMemberList = new JList(nonMembers);
			nonMemberList.addKeyListener(this);
			nonMemberList.addMouseListener(this);
			nonMemberList.setCellRenderer(new MyListCellRenderer());
			nonMemberList.setFont(UserPreferences.getUserFont());
		}
		
		return nonMemberList;
	}
	
	/**
	 * This method initializes memberList.	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getMemberList() {
		if (memberList == null) {
			memberList = new JList(members);
			memberList.addKeyListener(this);
			memberList.addMouseListener(this);
			memberList.setCellRenderer(new MyListCellRenderer());
			memberList.setFont(UserPreferences.getUserFont());
		}
		
		return memberList;
	}
	
	/**
	 * This method initializes unassignGroupButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getUnassignGroupButton() {
		if (unassignGroupButton == null) {
			unassignGroupButton = new JButton();
			unassignGroupButton.addActionListener(this);
			unassignGroupButton.setActionCommand(Constants.UNASSIGN_ACTION);
			unassignGroupButton.setIcon(ResourceUtil.unassignIcon);			
		}
		
		return unassignGroupButton;
	}
	
	/**
	 * This method initializes assignGroupButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAssignGroupButton() {
		if (assignGroupButton == null) {
			assignGroupButton = new JButton();
			assignGroupButton.addActionListener(this);
			assignGroupButton.setActionCommand(Constants.ASSIGN_ACTION);
			assignGroupButton.setIcon(ResourceUtil.assignIcon);
		}
		
		return assignGroupButton;
	}
	
	/**
	 * This method initializes nonMemberListScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getNonMemberListScrollPane() {
		if (nonMemberListScrollPane == null) {
			nonMemberListScrollPane = new JScrollPane(getNonMemberList());
		}
		
		return nonMemberListScrollPane;
	}
	
	/**
	 * This method initializes memberListScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getMemberListScrollPane() {
		if (memberListScrollPane == null) {
			memberListScrollPane = new JScrollPane(getMemberList());
		}
		
		return memberListScrollPane;
	}
	
	/**
	 * This method initializes nonMemberPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getNonMemberPanel() {
		if (nonMemberPanel == null) {
			nonMemberPanel = new JPanel(new BorderLayout());			
			nonMemberPanel.setPreferredSize(new Dimension(350,500));
			nonMemberPanel.add(new JLabel(ResourceUtil.getString("addremovemembers.nonmembers")), BorderLayout.NORTH);
			nonMemberPanel.add(getNonMemberListScrollPane(), BorderLayout.CENTER);
		}
		
		return nonMemberPanel;
	}
	
	/**
	 * This method initializes memberPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getMemberPanel() {
		if (memberPanel == null) {
			memberPanel = new JPanel(new BorderLayout());
			memberPanel.setPreferredSize(new Dimension(350,500));
			memberPanel.add(new JLabel(ResourceUtil.getString("addremovemembers.members")), BorderLayout.NORTH);
			memberPanel.add(getMemberListScrollPane(), BorderLayout.CENTER);
		}
		
		return memberPanel;
	}
	
	/**
	 * This method initializes actionSubPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getActionSubPanel() {
		if (actionSubPanel == null) {
			actionSubPanel = new JPanel( new GridLayout(2, 1));
			actionSubPanel.add(getAssignGroupButton());
			actionSubPanel.add(getUnassignGroupButton());
		}
		
		return actionSubPanel;
	}
	
	/**
	 * This method initializes actionPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getActionPanel() {
		if (actionPanel == null) {
			actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
			actionPanel.add(getActionSubPanel());
		}
		
		return actionPanel;
	}
	
	/**
	 * Assigns selected members.
	 */
	private void assignMembers() {
		if (!getNonMemberList().isSelectionEmpty()) {
			List<Object> values = Arrays.asList(getNonMemberList().getSelectedValues());
					
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
	
	/**
	 * Unassigns selected members.
	 */
	private void unassignMembers() {
		if (!getMemberList().isSelectionEmpty()) {
			List<Object> values = Arrays.asList(getMemberList().getSelectedValues());
			
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
	
	/**
	 * Refreshes lists displayed in dialog with current data.
	 */
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
	
	/**
	 * ActionPerformed event handler
	 * 
	 * @param event ActionEvent object.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(Constants.ASSIGN_ACTION)) {
			assignMembers();
		}
		else if (event.getActionCommand().equals(Constants.UNASSIGN_ACTION)) {
			unassignMembers();
		}	
		else if (event.getActionCommand().equals(Constants.SAVE_ACTION)) {
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
		else if (event.getActionCommand().equals(Constants.CANCEL_ACTION)) {
			message.setState(Message.CANCEL);
			dispose();
		}		
	}
	
	/**
	 * MouseClicked event handler.
	 * 
	 * @param event MouseEvent object.
	 */
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (event.getSource() == getNonMemberList()) {
				assignMembers();
			}
			else if (event.getSource() == getMemberList()) {
				unassignMembers();
			}
		}
	}

	/**
	 * MousePressed event handler. Not used.
	 * 
	 * @param event MouseEvent object.
	 */
	public void mousePressed(MouseEvent event) {
		// Not used
	}

	/**
	 * MouseReleased event handler. Not used.
	 * 
	 * @param event MouseEvent object.
	 */
	public void mouseReleased(MouseEvent event) {
		// Not used
	}

	/**
	 * MouseEntered event handler. Not used.
	 * 
	 * @param event MouseEvent object.
	 */
	public void mouseEntered(MouseEvent event) {
		// Not used
	}

	/**
	 * MouseExited event handler. Not used.
	 * 
	 * @param event MouseEvent object.
	 */
	public void mouseExited(MouseEvent event) {
		// Not used
	}
	
	/**
	 * This method initializes instructionsPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getInstructionsPanel() {
		if (instructionsPanel == null) {
			instructionsPanel = new JPanel();
			instructionsPanel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
			instructionsPanel.add(new JLabel(ResourceUtil.getFormattedString("addremovemembers.instructions",  group.getName())));
		}
		
		return instructionsPanel;
	}
	
	/**
	 * KeyTyped event handler. Not used.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyTyped(KeyEvent event) {
		// Unused
	}

	/**
	 * KeyPressed event handler.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		
		if (code == KeyEvent.VK_SPACE) {
			if (event.getComponent() == getMemberList()) {
				unassignMembers();
			}
			else if (event.getComponent() == getNonMemberList()) {
				assignMembers();
			}
		}
	}

	/**
	 * KeyReleased event handler. Not used.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyReleased(KeyEvent event) {
		// Unused
	}
 }