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
public class ChangeMembershipDialog extends ParentDialog implements 
	ActionListener, KeyListener, MouseListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 4595558087993098499L;
	
	private Message message = null;
	
	private User user = null;
	
	private Vector<Group> memberOf = null;
	
	private Vector<Group> notMemberOf =  null;
	
	private JPanel jContentPane = null;
	
	private JPanel buttonPanel = null;
	
	private JButton cancelButton = null;
	
	private JButton saveButton = null;
	
	private JPanel formPanel = null;
	
	private JPanel formSubPanel = null;
	
	private JList notMemberOfList = null;
	
	private JList memberOfList = null;
	
	private JButton unassignButton = null;
	
	private JButton assignButton = null;
	
	private JScrollPane nonMemberListScrollPane = null;
	
	private JScrollPane memberListScrollPane = null;
	
	private JPanel nonMemberPanel = null;
	
	private JPanel memberPanel = null;
	
	private JPanel actionSubPanel = null;
	
	private JPanel actionPanel = null;
	
	private JPanel instructionsPanel = null;
	
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
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString("changemembership.title"));
		this.setContentPane(getJContentPane());
		this.setIconImage(ResourceUtil.serverImage);
		this.setMinimumSize(new Dimension(500, 300));
		
		getRootPane().setDefaultButton(saveButton);
		
		this.pack();
		this.setModal(true);
	}
	
	/**
	 * This method initializes jContentPane.
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
			buttonPanel.add(getJButton1());
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
	private JButton getJButton1() {
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
	 * This method initializes notMemberOfList.	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getNotMemberOfList() {
		if (notMemberOfList == null) {
			notMemberOfList = new JList();
			notMemberOfList.setListData(notMemberOf);
			notMemberOfList.addMouseListener(this);
			notMemberOfList.setCellRenderer(new MyListCellRenderer());
			notMemberOfList.setFont(UserPreferences.getUserFont());
		}
		
		return notMemberOfList;
	}
	
	/**
	 * This method initializes memberOfList.	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getMemberOfList() {
		if (memberOfList == null) {
			memberOfList = new JList();
			memberOfList.setListData(memberOf);
			memberOfList.addMouseListener(this);
			memberOfList.setCellRenderer(new MyListCellRenderer());
			memberOfList.setFont(UserPreferences.getUserFont());
		}
		
		return memberOfList;
	}
	
	/**
	 * This method initializes unassignButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getUnassignButton() {
		if (unassignButton == null) {
			unassignButton = new JButton();
			unassignButton.setIcon(ResourceUtil.unassignIcon);
			unassignButton.addActionListener(this);
			unassignButton.setActionCommand(Constants.UNASSIGN_ACTION);
		}
		
		return unassignButton;
	}
	
	/**
	 * This method initializes assignButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAssignButton() {
		if (assignButton == null) {
			assignButton = new JButton();
			assignButton.setIcon(ResourceUtil.assignIcon);
			assignButton.addActionListener(this);
			assignButton.setActionCommand(Constants.ASSIGN_ACTION);
		}
		
		return assignButton;
	}
	
	/**
	 * This method initializes nonMemberListScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getNonMemberListScrollPane() {
		if (nonMemberListScrollPane == null) {
			nonMemberListScrollPane = new JScrollPane(getNotMemberOfList());
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
			memberListScrollPane = new JScrollPane(getMemberOfList());
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
			nonMemberPanel = new JPanel();
			nonMemberPanel.setLayout(new BorderLayout());
			nonMemberPanel.setPreferredSize(new Dimension(350,500));
			nonMemberPanel.add(new JLabel(ResourceUtil.getString("changemembership.notmemberof")), BorderLayout.NORTH);
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
			memberPanel.add(new JLabel(ResourceUtil.getString("changemembership.memberof")), BorderLayout.NORTH);
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
			actionSubPanel = new JPanel(new GridLayout(2, 1));
			actionSubPanel.add(getAssignButton());
			actionSubPanel.add(getUnassignButton());
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
	 * Assigns user to selected groups.
	 */
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
	
	/**
	 * Unassigns user from selected groups.
	 */
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
	
	/**
	 * ActionPerformed event handler.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Constants.ASSIGN_ACTION)) {
			assign();
		}
		else if (e.getActionCommand().equals(Constants.UNASSIGN_ACTION)) {
			unassign();
		}		
		else if (e.getActionCommand().equals(Constants.SAVE_ACTION)) {
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
		else if (e.getActionCommand().equals(Constants.CANCEL_ACTION)) {
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
			if (event.getSource() == getNotMemberOfList()) {
				assign();
			}
			else if (event.getSource() == getMemberOfList()) {
				unassign();
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
			instructionsPanel.add(new JLabel(ResourceUtil.getFormattedString("changemembership.instructions", user.getName())));
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
			if (event.getComponent() == getMemberOfList()) {
				unassign();
			}
			else if (event.getComponent() == getNotMemberOfList()) {
				assign();
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
}