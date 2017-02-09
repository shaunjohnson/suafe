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
import net.lmxm.suafe.GuiConstants;
import net.lmxm.suafe.resources.ResourceUtil;

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
