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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.GroupMemberObject;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Dialog that allows a user to add/remove members of a group.
 * 
 * @author Shaun Johnson
 */
public final class AddRemoveMembersDialog extends ParentDialog implements ActionListener, KeyListener, MouseListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 2808087711449246371L;

	private JPanel actionPanel = null;

	private JPanel actionSubPanel = null;

	private JButton assignGroupButton = null;

	private JPanel buttonPanel = null;

	private JButton cancelButton = null;

	private Document document = null;

	private JPanel formPanel = null;

	private Group group = null;

	private Vector<Group> groupMembers = null;

	private Vector<Group> groupNonMembers = null;

	private JPanel jContentPane = null;

	private JList memberList = null;

	private JScrollPane memberListScrollPane = null;

	private JPanel memberPanel = null;

	private Vector<GroupMemberObject> members = null;

	private Message message = null;

	private JList nonMemberList = null;

	private JScrollPane nonMemberListScrollPane = null;

	private JPanel nonMemberPanel = null;

	private Vector<GroupMemberObject> nonMembers = null;

	private JButton saveButton = null;

	private JButton unassignGroupButton = null;

	private Vector<User> userMembers = null;

	private Vector<User> userNonMembers = null;

	/**
	 * Default constructor.
	 */
	public AddRemoveMembersDialog(Document document, Group group, Message message) {
		super();

		this.document = document;
		this.message = message;
		this.message.setState(Message.CANCEL);
		this.group = group;

		initialize();
	}

	/**
	 * ActionPerformed event handler
	 * 
	 * @param event ActionEvent object.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(ActionConstants.ASSIGN_ACTION)) {
			assignMembers();
		}
		else if (event.getActionCommand().equals(ActionConstants.UNASSIGN_ACTION)) {
			unassignMembers();
		}
		else if (event.getActionCommand().equals(ActionConstants.SAVE_ACTION)) {
			try {
				document.changeGroupMembers(group, groupMembers, userMembers);
				message.setUserObject(group);
				message.setState(Message.SUCCESS);
				dispose();
			}
			catch (Exception ex) {
				displayError(ex.getMessage());
			}
		}
		else if (event.getActionCommand().equals(ActionConstants.CANCEL_ACTION)) {
			message.setState(Message.CANCEL);
			dispose();
		}
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
	 * This method initializes actionSubPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getActionSubPanel() {
		if (actionSubPanel == null) {
			actionSubPanel = new JPanel(new GridLayout(2, 1));
			actionSubPanel.add(getAssignGroupButton());
			actionSubPanel.add(getUnassignGroupButton());
		}

		return actionSubPanel;
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
			assignGroupButton.setActionCommand(ActionConstants.ASSIGN_ACTION);
			assignGroupButton.setIcon(ResourceUtil.assignIcon);
		}

		return assignGroupButton;
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
			cancelButton = createButton("button.cancel", ActionConstants.CANCEL_ACTION, this);
		}

		return cancelButton;
	}

	/**
	 * This method initializes formPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new JPanel();
			formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.X_AXIS));

			formPanel.add(getNonMemberPanel());
			formPanel.add(getActionPanel());
			formPanel.add(getMemberPanel());
		}

		return formPanel;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane
					.add(getInstructionsPanel("addremovemembers.instructions", group.getName()), BorderLayout.NORTH);
			jContentPane.add(getFormPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}

		return jContentPane;
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
	 * This method initializes memberPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMemberPanel() {
		if (memberPanel == null) {
			memberPanel = new JPanel(new BorderLayout());
			memberPanel.setPreferredSize(new Dimension(350, 500));
			memberPanel.add(new JLabel(ResourceUtil.getString("addremovemembers.members")), BorderLayout.NORTH);
			memberPanel.add(getMemberListScrollPane(), BorderLayout.CENTER);
		}

		return memberPanel;
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
	 * This method initializes nonMemberPanel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getNonMemberPanel() {
		if (nonMemberPanel == null) {
			nonMemberPanel = new JPanel(new BorderLayout());
			nonMemberPanel.setPreferredSize(new Dimension(350, 500));
			nonMemberPanel.add(new JLabel(ResourceUtil.getString("addremovemembers.nonmembers")), BorderLayout.NORTH);
			nonMemberPanel.add(getNonMemberListScrollPane(), BorderLayout.CENTER);
		}

		return nonMemberPanel;
	}

	/**
	 * This method initializes saveButton.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = createButton("button.save", ActionConstants.SAVE_ACTION, this);
		}

		return saveButton;
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
			unassignGroupButton.setActionCommand(ActionConstants.UNASSIGN_ACTION);
			unassignGroupButton.setIcon(ResourceUtil.unassignIcon);
		}

		return unassignGroupButton;
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		try {
			// Initialize groups
			Group[] groupMembersObjects = document.getGroupMemberGroups(group);

			if (groupMembersObjects != null) {
				List<Group> groupMembersList = Arrays.asList(groupMembersObjects);
				groupMembers = new Vector<Group>(groupMembersList);
				Collections.sort(groupMembers);
			}
			else {
				groupMembers = new Vector<Group>();
			}

			Group[] groupObjects = document.getGroupsArray();

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
			User[] userMembersObjects = document.getGroupMemberUsers(group);

			if (userMembersObjects != null) {
				List<User> userMembersList = Arrays.asList(userMembersObjects);
				userMembers = new Vector<User>(userMembersList);
				Collections.sort(userMembers);
			}
			else {
				userMembers = new Vector<User>();
			}

			User[] userObjects = document.getUserObjectsExcludeAllUsers();

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
		catch (AppException e) {
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
		else {
			super.keyPressed(event);
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

	/**
	 * KeyTyped event handler. Not used.
	 * 
	 * @param event KeyEvent object.
	 */
	public void keyTyped(KeyEvent event) {
		// Unused
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
}