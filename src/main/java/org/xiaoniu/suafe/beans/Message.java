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
package org.xiaoniu.suafe.beans;

/**
 * Represents the outcome of a dialog action.
 * When a dialog returns focus back to the main application the dialog returns
 * a Message object that indicates the success or failure of the action.
 *
 * @author Shaun Johnson
 */
public final class Message {

    /**
     * Status indicating that the user cancelled the operation.
     */
    public static final int CANCEL = -1;

    /**
     * Status indicating that the user didn't perform an operation.
     */
    public static final int UNKNOWN = 0;

    /**
     * Status indicating that the user allowed the operation to succeed.
     */
    public static final int SUCCESS = 1;

    /**
     * Object passed between dialogs.
     */
    private Object userObject;

    /**
     * Current state of the message.
     */
    private int state;

    /**
     * Default constructor.
     */
    public Message() {
        super();

        this.userObject = null;
        this.state = UNKNOWN;
    }

    /**
     * Returns the current state.
     *
     * @return Returns the state.
     */
    public int getState() {
        return state;
    }

    /**
     * Changes the object state.
     *
     * @param state The state to set.
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Returns the user object.
     *
     * @return Returns the userObject.
     */
    public Object getUserObject() {
        return userObject;
    }

    /**
     * Sets the user object.
     *
     * @param userObject The userObject to set.
     */
    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }
}
