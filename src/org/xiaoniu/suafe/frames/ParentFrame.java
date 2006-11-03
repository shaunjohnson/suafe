package org.xiaoniu.suafe.frames;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ParentFrame extends BaseFrame implements ContainerListener, KeyListener {

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
	 * Component Added event handler. Adds listenters to new component
	 * and all of its children.
	 */
	public void componentAdded(ContainerEvent containerEvent)
    {
		addListeners(containerEvent.getChild());
    }
     
	/**
	 * Component Removed event handler. Removes listeners from component
	 * and all of its children.
	 */
	public void componentRemoved(ContainerEvent e)
	{
		removeListeners(e.getChild());
	}

	/**
	 * Adds this as a listener to the component and all of its children.
	 * 
	 * @param component Child component to which listeners are added.
	 */
	private void addListeners(Component component)
	{
		component.addKeyListener(this);

		if(component instanceof Container) {
			Container container = (Container)component;
			
			container.addContainerListener(this);
						
			for(Component child : container.getComponents()) {
				addListeners(child);
			}
		}
	}
	
	/**
	 * Removes this as a listener from the component and all of its children.
	 * @param component Child component from which listeners are removed.
	 */
	private void removeListeners(Component component)
	{
		component.removeKeyListener(this);

		if(component instanceof Container){

			Container container = (Container)component;

			container.removeContainerListener(this);
			
			for(Component child : container.getComponents()) {
				removeListeners(child);
			}
		}
	}

	/**
	 * Key Pressed event handler. Dispose the current dialog when the
	 * escape key is pressed.
	 */
	public void keyPressed(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		
		if(keyCode == KeyEvent.VK_ESCAPE) {
			this.dispose();
        }
	}

	public void keyReleased(KeyEvent keyEvent) {
		// Do nothing		
	}

	public void keyTyped(KeyEvent keyEvent) {
		// Do nothing		
	}
}

