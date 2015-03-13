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
package org.xiaoniu.suafe.frames;

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
        super();

        addListeners(this);
    }

    /**
     * Component Added event handler. Adds listeners to new component
     * and all of its children.
     *
     * @param containerEvent ContainerEvent object.
     */
    public void componentAdded(ContainerEvent containerEvent) {
        addListeners(containerEvent.getChild());
    }

    /**
     * Component Removed event handler. Removes listeners from component
     * and all of its children.
     *
     * @param containerEvent ContainerEvent object.
     */
    public void componentRemoved(ContainerEvent containerEvent) {
        removeListeners(containerEvent.getChild());
    }

    /**
     * Adds this as a listener to the component and all of its children.
     *
     * @param component Child component to which listeners are added.
     */
    private void addListeners(Component component) {
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
    private void removeListeners(Component component) {
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
    public void keyPressed(KeyEvent keyEvent) {
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
    public void keyReleased(KeyEvent keyEvent) {
        // Do nothing
    }

    /**
     * KeyTyped event handler. Not used.
     *
     * @param keyEvent KeyEvent object.
     */
    public void keyTyped(KeyEvent keyEvent) {
        // Do nothing
    }
}

