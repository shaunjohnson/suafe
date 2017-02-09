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

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Base class for some windows. Listens for the user to click the escape
 * key, which causes the window to close.
 *
 * @author Shaun Johnson
 */
public abstract class ParentFrame extends BaseFrame implements ContainerListener, KeyListener {
    /**
     * Serial ID
     */
    private static final long serialVersionUID = 3312338357300824280L;

    /**
     * Dialog that implements listeners to provide Escape key functionality.
     */
    public ParentFrame() {
        addListeners(this);
    }

    /**
     * Component Added event handler. Adds listeners to new component
     * and all of its children.
     *
     * @param containerEvent ContainerEvent object.
     */
    public void componentAdded(@Nonnull final ContainerEvent containerEvent) {
        addListeners(containerEvent.getChild());
    }

    /**
     * Component Removed event handler. Removes listeners from component
     * and all of its children.
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
            Container container = (Container) component;

            container.addContainerListener(this);

            for (Component child : container.getComponents()) {
                addListeners(child);
            }
        }
    }

    /**
     * Removes this as a listener from the component and all of its children.
     *
     * @param component Child component from which listeners are removed.
     */
    private void removeListeners(@Nonnull final Component component) {
        component.removeKeyListener(this);

        if (component instanceof Container) {

            Container container = (Container) component;

            container.removeContainerListener(this);

            for (Component child : container.getComponents()) {
                removeListeners(child);
            }
        }
    }

    /**
     * Key Pressed event handler. Dispose the current dialog when the
     * escape key is pressed.
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
}

