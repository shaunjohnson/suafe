package org.xiaoniu.suafe.frames.menus;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Popup menu listener class.
 *
 * @author Shaun Johnson
 */
public final class PopupMenuListener extends MouseAdapter {
    private final JPopupMenu popupMenu;

    /**
     * Default Constructor.
     *
     * @param popupMenu Menu displayed upon click
     */
    public PopupMenuListener(@Nonnull final JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    /**
     * Display popup menu if requirements met.
     *
     * @param event MountEvent that triggered this
     */
    private void maybeShowPopup(@Nonnull final MouseEvent event) {
        if (event.isPopupTrigger()) {
            popupMenu.show(event.getComponent(), event.getX(), event.getY());
        }
    }

    /**
     * Mouse button pressed event handler.
     */
    @Override
    public void mousePressed(@Nonnull final MouseEvent e) {
        maybeShowPopup(e);
    }

    /**
     * Mouse button released event handler.
     */
    @Override
    public void mouseReleased(@Nonnull final MouseEvent e) {
        maybeShowPopup(e);
    }
}
