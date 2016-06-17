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

import org.xiaoniu.suafe.ActionConstants;
import org.xiaoniu.suafe.GuiConstants;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog that displays information about this application.
 *
 * @author Shaun Johnson
 */
public final class AboutDialog extends ParentDialog implements ActionListener {
    /**
     * Unique ID.
     */
    private static final long serialVersionUID = 2009320543683373156L;

    /**
     * Main content panel.
     */
    private JPanel jContentPane;

    /**
     * Action button panel.
     */
    private JPanel buttonPanel;

    /**
     * Confirmation button.
     */
    private JButton okButton;

    /**
     * Content panel.
     */
    private JPanel contentPanel;

    /**
     * Title panel.
     */
    private JLabel titleLabel;

    /**
     * Description label.
     */
    private JLabel descriptionLabel;


    /**
     * Default constructor.
     */
    public AboutDialog() {
        initialize();
    }

    /**
     * This method initializes this.
     */
    private void initialize() {
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle(ResourceUtil.getString("about.title"));
        this.setContentPane(getJContentPane());
        this.getRootPane().setDefaultButton(getOkButton());

        this.setSize(300, 200);
        this.setModal(true);
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel(new BorderLayout());
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
            jContentPane.add(getContentPanel(), BorderLayout.CENTER);
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
            buttonPanel.add(getOkButton());
        }

        return buttonPanel;
    }

    /**
     * This method initializes okButton
     *
     * @return javax.swing.JButton
     */
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = createButton("button.ok", ActionConstants.OK_ACTION, this);
        }

        return okButton;
    }

    /**
     * This method initializes contentPanel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel(new BorderLayout());

            titleLabel = new JLabel(ResourceUtil.getString("application.nameversion"));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setFont(GuiConstants.FONT_BOLD_XLARGE);

            descriptionLabel = new JLabel(ResourceUtil.getString("about.content"));
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

            contentPanel.add(titleLabel, BorderLayout.NORTH);
            contentPanel.add(descriptionLabel, BorderLayout.CENTER);
        }

        return contentPanel;
    }

    /**
     * ActionPerformed event handler.
     *
     * @param actionEvent ActionEvent object.
     */
    public void actionPerformed(@Nonnull final ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals(ActionConstants.OK_ACTION)) {
            dispose();
        }
    }
}
