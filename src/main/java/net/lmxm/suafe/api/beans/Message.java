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
package net.lmxm.suafe.api.beans;

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
