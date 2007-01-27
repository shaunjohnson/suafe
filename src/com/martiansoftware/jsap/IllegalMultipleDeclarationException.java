/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

/**
 * An exception indicating that a parameter has illegally been declared multiple
 * times.
 *
 * @see
 *  com.martiansoftware.jsap.FlaggedOption#setAllowMultipleDeclarations(boolean)
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.Flagged
 * @see com.martiansoftware.jsap.Option
 */
public class IllegalMultipleDeclarationException extends JSAPException {

    /**
     * The unique ID of the parameter that was illegally declared more than
     * once.
     */
    private String id = null;

    /**
     * Creates a new IllegalMultipleDeclarationException referencing the
     * specified parameter.
     * @param paramID the unique ID of the parameter that was illegally declared
     * more than once.
     */
    public IllegalMultipleDeclarationException(String paramID) {
        super("Parameter '" + paramID + "' cannot be declared more than once.");
        this.id = paramID;
    }

    /**
     * Returns the unique ID of the parameter that was illegally declared more
     * than once.
     * @return the unique ID of the parameter that was illegally declared more
     * than once.
     */
    public String getID() {
        return (this.id);
    }

}
