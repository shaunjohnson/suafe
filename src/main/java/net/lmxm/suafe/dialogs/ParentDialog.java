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

import net.lmxm.suafe.GuiConstants;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class ParentDialog extends JDialog implements ContainerListener, KeyListener {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 3312338357300824280L;

    /**
     * Dialog that implements listeners to provide Escape key functionality.
     */
    public ParentDialog() {
        addListeners(this);
    }

    /**
     * Component Added event handler. Adds listenters to new component and all of its children.
     *
     * @param containerEvent ContainerEvent object.
     */
    public void componentAdded(@Nonnull final ContainerEvent containerEvent) {
        addListeners(containerEvent.getChild());
    }

    /**
     * Component Removed event handler. Removes listeners from component and all of its children.
     *
     * @param containerEvent ContainerEvent object.
     */
    public void componentRemoved(@Nonnull final ContainerEvent containerEvent) {
        removeListeners(containerEvent.getChild());
    }

    /**
     * Adds this as a listener to the component and all of its children.
     *
     * @param component Child component to which listeners are added.
     */
    private void addListeners(@Nonnull final Component component) {
        component.addKeyListener(this);

        if (component instanceof Container) {
            final  Container container = (Container) component;

            container.addContainerListener(this);

            for (final Component child : container.getComponents()) {
                addListeners(child);
            }
        }
    }

    /**
     * Creates a button using the specified bundle key and action code.
     *
     * @param key    Key used to lookup button text in resource bundle
     * @param action Action code to associate with the button
     * @return Newly created button
     */
    protected JButton createButton(@Nonnull final String key, @Nonnull final String action,
                                   @Nonnull final ActionListener actionListener) {
        JButton button = new JButton();

        button.addActionListener(actionListener);
        button.setActionCommand(action);
        button.setText(ResourceUtil.getString(key));

        return button;
    }

    /**
     * Removes this as a listener from the component and all of its children.
     *
     * @param component Child component from which listeners are removed.
     */
    private void removeListeners(@Nonnull final Component component) {
        component.removeKeyListener(this);

        if (component instanceof Container) {
            final Container container = (Container) component;

            container.removeContainerListener(this);

            for (final Component child : container.getComponents()) {
                removeListeners(child);
            }
        }
    }

    /**
     * Key Pressed event handler. Dispose the current dialog when the escape key is pressed.
     *
     * @param keyEvent KeyEvent object.
     */
    public void keyPressed(@Nonnull final KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }

    /**
     * KeyReleased event handler. Not used.
     *
     * @param keyEvent KeyEvent object.
     */
    public void keyReleased(@Nonnull final KeyEvent keyEvent) {
        // Do nothing
    }

    /**
     * KeyTyped event handler. Not used.
     *
     * @param keyEvent KeyEvent object.
     */
    public void keyTyped(@Nonnull final KeyEvent keyEvent) {
        // Do nothing
    }

    /**
     * Generic error message dialog.
     *
     * @param message Error message to be displayed.
     */
    protected void displayError(@Nonnull final String message) {
        JOptionPane.showMessageDialog(this, message, ResourceUtil.getString("application.error"),
                JOptionPane.ERROR_MESSAGE);
    }

    private JPanel instructionsPanel = null;

    protected JPanel getInstructionsPanel(@Nonnull final String textId) {
        return getInstructionsPanelImpl(ResourceUtil.getString(textId));
    }

    protected JPanel getInstructionsPanel(@Nonnull final String textId, @Nonnull final String arg) {
        return getInstructionsPanelImpl(ResourceUtil.getFormattedString(textId, arg));
    }

    private JPanel getInstructionsPanelImpl(@Nonnull final String text) {
        if (instructionsPanel == null) {
            instructionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            final JLabel instructionsLabel = new JLabel(text);
            instructionsLabel.setFont(GuiConstants.FONT_BOLD_LARGE);

            instructionsPanel.add(instructionsLabel);
        }

        return instructionsPanel;
    }
}
