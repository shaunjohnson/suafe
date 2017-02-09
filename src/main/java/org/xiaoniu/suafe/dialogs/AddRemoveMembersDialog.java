/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.xiaoniu.suafe.dialogs;

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.api.beans.*;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.renderers.MyListCellRenderer;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

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

    private JPanel actionPanel;

    private JPanel actionSubPanel;

    private JButton assignGroupButton;

    private JPanel buttonPanel;

    private JButton cancelButton;

    private Document document;

    private JPanel formPanel;

    private Group group;

    private Vector<Group> groupMembers;

    private Vector<Group> groupNonMembers;

    private JPanel jContentPane;

    private JList memberList;

    private JScrollPane memberListScrollPane;

    private JPanel memberPanel;

    private Vector<GroupMemberObject> members;

    private Message message;

    private JList nonMemberList;

    private JScrollPane nonMemberListScrollPane;

    private JPanel nonMemberPanel;

    private Vector<GroupMemberObject> nonMembers;

    private JButton saveButton;

    private JButton unassignGroupButton;

    private Vector<User> userMembers;

    private Vector<User> userNonMembers;

    /**
     * Default constructor.
     */
    public AddRemoveMembersDialog(@Nonnull final Document document, @Nonnull final Group group,
                                  final Message message) {
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
    public void actionPerformed(@Nonnull final ActionEvent event) {
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
            final List<Object> values = Arrays.asList(getNonMemberList().getSelectedValues());

            for (final Object object : values) {
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
            final Group[] groupMembersObjects = document.getGroupMemberGroups(group);

            if (groupMembersObjects != null) {
                List<Group> groupMembersList = Arrays.asList(groupMembersObjects);
                groupMembers = new Vector<Group>(groupMembersList);
                Collections.sort(groupMembers);
            }
            else {
                groupMembers = new Vector<Group>();
            }

            final Group[] groupObjects = document.getGroupsArray();

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
            final User[] userMembersObjects = document.getGroupMemberUsers(group);

            if (userMembersObjects != null) {
                List<User> userMembersList = Arrays.asList(userMembersObjects);
                userMembers = new Vector<User>(userMembersList);
                Collections.sort(userMembers);
            }
            else {
                userMembers = new Vector<User>();
            }

            final User[] userObjects = document.getUserObjectsExcludeAllUsers();

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
        catch (final AppException e) {
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
     * @param keyEvent KeyEvent object.
     */
    public void keyPressed(@Nonnull final KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {
            if (keyEvent.getComponent() == getMemberList()) {
                unassignMembers();
            }
            else if (keyEvent.getComponent() == getNonMemberList()) {
                assignMembers();
            }
        }
        else {
            super.keyPressed(keyEvent);
        }
    }

    /**
     * KeyReleased event handler. Not used.
     *
     * @param keyEvent KeyEvent object.
     */
    public void keyReleased(@Nonnull final KeyEvent keyEvent) {
        // Unused
    }

    /**
     * KeyTyped event handler. Not used.
     *
     * @param keyEvent KeyEvent object.
     */
    public void keyTyped(@Nonnull final KeyEvent keyEvent) {
        // Unused
    }

    /**
     * MouseClicked event handler.
     *
     * @param mouseEvent MouseEvent object.
     */
    public void mouseClicked(@Nonnull final MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            if (mouseEvent.getSource() == getNonMemberList()) {
                assignMembers();
            }
            else if (mouseEvent.getSource() == getMemberList()) {
                unassignMembers();
            }
        }
    }

    /**
     * MouseEntered event handler. Not used.
     *
     * @param mouseEvent MouseEvent object.
     */
    public void mouseEntered(@Nonnull final MouseEvent mouseEvent) {
        // Not used
    }

    /**
     * MouseExited event handler. Not used.
     *
     * @param mouseEvent MouseEvent object.
     */
    public void mouseExited(@Nonnull final MouseEvent mouseEvent) {
        // Not used
    }

    /**
     * MousePressed event handler. Not used.
     *
     * @param mouseEvent MouseEvent object.
     */
    public void mousePressed(@Nonnull final MouseEvent mouseEvent) {
        // Not used
    }

    /**
     * MouseReleased event handler. Not used.
     *
     * @param mouseEvent MouseEvent object.
     */
    public void mouseReleased(@Nonnull final MouseEvent mouseEvent) {
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

        members = new Vector<>(groupMembers.size() + userMembers.size());
        nonMembers = new Vector<>(groupNonMembers.size() + userNonMembers.size());

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
            final List<Object> values = Arrays.asList(getMemberList().getSelectedValues());

            for (final Object object : values) {
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