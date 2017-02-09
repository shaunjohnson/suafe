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
package net.lmxm.suafe.dialogs;

import net.lmxm.suafe.ActionConstants;
import net.lmxm.suafe.UserPreferences;
import net.lmxm.suafe.api.beans.Document;
import net.lmxm.suafe.api.beans.Group;
import net.lmxm.suafe.api.beans.Message;
import net.lmxm.suafe.api.beans.User;
import net.lmxm.suafe.exceptions.AppException;
import net.lmxm.suafe.renderers.GroupListCellRenderer;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Dialog that allows a user to change a user's group membership.
 *
 * @author Shaun Johnson
 */
public final class ChangeMembershipDialog extends ParentDialog implements ActionListener, KeyListener, MouseListener {
    private static final long serialVersionUID = 4595558087993098499L;

    private JPanel actionPanel;

    private JPanel actionSubPanel;

    private JButton assignButton;

    private JPanel buttonPanel;

    private JButton cancelButton;

    private Document document;

    private JPanel formPanel;

    private JPanel jContentPane;

    private JScrollPane memberListScrollPane;

    private Vector<Group> memberOf;

    private JList<Group> memberOfList;

    private JPanel memberPanel;

    private Message message;

    private JScrollPane nonMemberListScrollPane;

    private JPanel nonMemberPanel;

    private Vector<Group> notMemberOf;

    private JList<Group> notMemberOfList;

    private JButton saveButton;

    private JButton unassignButton;

    private User user;

    /**
     * This is the default constructor
     */
    public ChangeMembershipDialog(@Nonnull final Document document, @Nonnull final User user,
                                  @Nonnull final Message message) {
        this.document = document;
        this.message = message;
        this.message.setState(Message.CANCEL);
        this.user = user;

        initialize();
    }

    /**
     * ActionPerformed event handler.
     */
    public void actionPerformed(@Nonnull final ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals(ActionConstants.ASSIGN_ACTION)) {
            assign();
        }
        else if (actionEvent.getActionCommand().equals(ActionConstants.UNASSIGN_ACTION)) {
            unassign();
        }
        else if (actionEvent.getActionCommand().equals(ActionConstants.SAVE_ACTION)) {
            try {
                document.changeUserMembership(user, memberOf);
                message.setUserObject(user);
                message.setState(Message.SUCCESS);
                dispose();
            }
            catch (Exception ex) {
                displayError(ex.getMessage());
            }
        }
        else if (actionEvent.getActionCommand().equals(ActionConstants.CANCEL_ACTION)) {
            message.setState(Message.CANCEL);
            dispose();
        }
    }

    /**
     * Assigns user to selected groups.
     */
    private void assign() {
        if (!getNotMemberOfList().isSelectionEmpty()) {
            final List<Group> values = getNotMemberOfList().getSelectedValuesList();

            memberOf.addAll(values);
            notMemberOf.removeAll(values);

            Collections.sort(memberOf);
            Collections.sort(notMemberOf);

            getMemberOfList().setListData(memberOf);
            getNotMemberOfList().setListData(notMemberOf);
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
            actionSubPanel.add(getAssignButton());
            actionSubPanel.add(getUnassignButton());
        }

        return actionSubPanel;
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
            assignButton.setActionCommand(ActionConstants.ASSIGN_ACTION);
        }

        return assignButton;
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
     * This method initializes jContentPane.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel(new BorderLayout());
            jContentPane.add(getInstructionsPanel("changemembership.instructions", user.getName()), BorderLayout.NORTH);
            jContentPane.add(getFormPanel(), BorderLayout.CENTER);
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }

        return jContentPane;
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
     * This method initializes memberOfList.
     *
     * @return javax.swing.JList
     */
    private JList<Group> getMemberOfList() {
        if (memberOfList == null) {
            memberOfList = new JList<>();
            memberOfList.setListData(memberOf);
            memberOfList.addMouseListener(this);
            memberOfList.setCellRenderer(new GroupListCellRenderer());
            memberOfList.setFont(UserPreferences.getUserFont());
        }

        return memberOfList;
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
            memberPanel.add(new JLabel(ResourceUtil.getString("changemembership.memberof")), BorderLayout.NORTH);
            memberPanel.add(getMemberListScrollPane(), BorderLayout.CENTER);
        }

        return memberPanel;
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
     * This method initializes nonMemberPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getNonMemberPanel() {
        if (nonMemberPanel == null) {
            nonMemberPanel = new JPanel();
            nonMemberPanel.setLayout(new BorderLayout());
            nonMemberPanel.setPreferredSize(new Dimension(350, 500));
            nonMemberPanel.add(new JLabel(ResourceUtil.getString("changemembership.notmemberof")), BorderLayout.NORTH);
            nonMemberPanel.add(getNonMemberListScrollPane(), BorderLayout.CENTER);
        }

        return nonMemberPanel;
    }

    /**
     * This method initializes notMemberOfList.
     *
     * @return javax.swing.JList
     */
    private JList<Group> getNotMemberOfList() {
        if (notMemberOfList == null) {
            notMemberOfList = new JList<>();
            notMemberOfList.setListData(notMemberOf);
            notMemberOfList.addMouseListener(this);
            notMemberOfList.setCellRenderer(new GroupListCellRenderer());
            notMemberOfList.setFont(UserPreferences.getUserFont());
        }

        return notMemberOfList;
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
     * This method initializes unassignButton.
     *
     * @return javax.swing.JButton
     */
    private JButton getUnassignButton() {
        if (unassignButton == null) {
            unassignButton = new JButton();
            unassignButton.setIcon(ResourceUtil.unassignIcon);
            unassignButton.addActionListener(this);
            unassignButton.setActionCommand(ActionConstants.UNASSIGN_ACTION);
        }

        return unassignButton;
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        try {
            final Group[] groups = document.getUserGroupsArray(user);

            if (groups != null) {
                final List<Group> memberOfList = Arrays.asList(groups);
                memberOf = new Vector<>(memberOfList);
                Collections.sort(memberOf);
            }
            else {
                memberOf = new Vector<>();
            }

            final Group[] allGroups = document.getGroupsArray();

            if (allGroups != null) {
                final List<Group> notMemberOfList = Arrays.asList(allGroups);
                notMemberOf = new Vector<>(notMemberOfList);
                Collections.sort(notMemberOf);
                notMemberOf.removeAll(memberOf);
            }
            else {
                notMemberOf = new Vector<>();
            }
        }
        catch (final AppException e) {
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
     * KeyPressed event handler.
     *
     * @param keyEvent KeyEvent object.
     */
    public void keyPressed(@Nonnull final KeyEvent keyEvent) {
        final int code = keyEvent.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {
            if (keyEvent.getComponent() == getMemberOfList()) {
                unassign();
            }
            else if (keyEvent.getComponent() == getNotMemberOfList()) {
                assign();
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
            if (mouseEvent.getSource() == getNotMemberOfList()) {
                assign();
            }
            else if (mouseEvent.getSource() == getMemberOfList()) {
                unassign();
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
     * Unassigns user from selected groups.
     */
    private void unassign() {
        if (!getMemberOfList().isSelectionEmpty()) {
            final List<Group> values = getMemberOfList().getSelectedValuesList();

            memberOf.removeAll(values);
            notMemberOf.addAll(values);

            Collections.sort(memberOf);
            Collections.sort(notMemberOf);

            getMemberOfList().setListData(memberOf);
            getNotMemberOfList().setListData(notMemberOf);
        }
    }
}