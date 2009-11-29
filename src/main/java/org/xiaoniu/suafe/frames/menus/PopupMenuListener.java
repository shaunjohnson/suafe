package org.xiaoniu.suafe.frames.menus;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

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
	public PopupMenuListener(final JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	/**
	 * Display popup menu if requirements met.
	 * 
	 * @param event MountEvent that triggered this
	 */
	private void maybeShowPopup(final MouseEvent event) {
		if (event.isPopupTrigger()) {
			popupMenu.show(event.getComponent(), event.getX(), event.getY());
		}
	}

	/**
	 * Mouse button pressed event handler.
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		maybeShowPopup(e);
	}

	/**
	 * Mouse button released event handler.
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {
		maybeShowPopup(e);
	}
}
