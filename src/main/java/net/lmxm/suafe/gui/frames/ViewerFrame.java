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
package net.lmxm.suafe.gui.frames;

import net.lmxm.suafe.gui.ActionConstants;
import net.lmxm.suafe.Constants;
import net.lmxm.suafe.gui.GuiConstants;
import net.lmxm.suafe.Utilities;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

/**
 * Main Suafe help window.
 *
 * @author Shaun Johnson
 */
public final class ViewerFrame extends ParentFrame implements ActionListener, HyperlinkListener {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 5057005120918134417L;

    private String title;

    private String content;

    private String contentType;

    private JPanel jContentPane;

    private JPanel buttonPanel;

    private JButton closeButton;

    private JScrollPane contentScrollPane;

    private JEditorPane contentEditorPane;

    private JButton saveButton;

    /**
     * Default constructor.
     */
    public ViewerFrame(@Nonnull final String title, @Nonnull final String content, @Nonnull final String contentType) {
        this.title = title;
        this.content = content;
        this.contentType = contentType;

        initialize();
    }

    /**
     * This method initializes this.
     */
    private void initialize() {
        this.setIconImage(ResourceUtil.serverImage);
        this.setTitle(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 700);
        this.setContentPane(getJContentPane());
        this.center();

        getRootPane().setDefaultButton(getCloseButton());
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel(new BorderLayout());
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
            jContentPane.add(getContentScrollPane(), BorderLayout.CENTER);
        }

        return jContentPane;
    }

    /**
     * HyperlinkEvent listener.
     *
     * @param event HyperlinkEvent object.
     */
    public void hyperlinkUpdate(HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                URL newUrl = event.getURL();

                getContentEditorPane().setPage(newUrl);

                if (newUrl.getFile().endsWith(".html")) {
                    getContentEditorPane().setContentType(Constants.MIME_HTML);
                }
                else if (newUrl.getFile().endsWith(".txt")) {
                    getContentEditorPane().setContentType(Constants.MIME_TEXT);
                }
                else {
                    getContentEditorPane().setContentType(Constants.MIME_HTML);
                }
            }
            catch (IOException e) {
                // Do nothing.
            }
        }
    }

    /**
     * This method initializes buttonPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(getSaveButton(), null);
            buttonPanel.add(getCloseButton(), null);
        }
        return buttonPanel;
    }

    /**
     * This method initializes closeButton
     *
     * @return javax.swing.JButton
     */
    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton();
            closeButton.setText(ResourceUtil.getString("button.close"));
            closeButton.addActionListener(this);
            closeButton.setActionCommand(ActionConstants.CLOSE_ACTION);
        }
        return closeButton;
    }

    /**
     * This method initializes saveButton
     *
     * @return javax.swing.JButton
     */
    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton();
            saveButton.setText(ResourceUtil.getString("button.save"));
            saveButton.addActionListener(this);
            saveButton.setActionCommand(ActionConstants.SAVE_ACTION);
        }
        return saveButton;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals(ActionConstants.CLOSE_ACTION)) {
            dispose();
        }
        else if (event.getActionCommand().equals(ActionConstants.SAVE_ACTION)) {
            fileSave();
        }
    }

    /**
     * File save as action handler.
     */
    private void fileSave() {
        final JFileChooser fcSaveAs = new JFileChooser();

        fcSaveAs.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fcSaveAs.setDialogType(JFileChooser.SAVE_DIALOG);
        fcSaveAs.setDialogTitle(ResourceUtil.getString("saveas.title"));

        int returnVal = fcSaveAs.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fcSaveAs.getSelectedFile();

                PrintStream out = Utilities.openOutputFile(file);

                out.print(content);
            }
            catch (Exception e) {
                displayError(e.getMessage());
            }
        }
    }

    /**
     * This method initializes contentScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getContentScrollPane() {
        if (contentScrollPane == null) {
            contentScrollPane = new JScrollPane();
            contentScrollPane.setViewportView(getContentEditorPane());
        }
        return contentScrollPane;
    }

    /**
     * This method initializes contentEditorPane
     *
     * @return javax.swing.JEditorPane
     */
    private JEditorPane getContentEditorPane() {
        if (contentEditorPane == null) {
            contentEditorPane = new JEditorPane();
            contentEditorPane.setContentType(contentType);
            contentEditorPane.setText(content);
            contentEditorPane.setEditable(false);
            contentEditorPane.select(0, 0);
            contentEditorPane.addHyperlinkListener(this);

            if (contentType == Constants.MIME_TEXT) {
                contentEditorPane.setFont(GuiConstants.FONT_MONOSPACE);
            }
        }
        return contentEditorPane;
    }
}