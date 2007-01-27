/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

/**
 * An exception indicating that a required parameter was missing from the
 * supplied arguments and defaults.
 *
 * @see FlaggedOption#setAllowMultipleDeclarations(boolean)
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.Option#required()
 */
public class RequiredParameterMissingException extends JSAPException {

    /**
     * The unique ID of the parameter that was missing.
     */
    private String id = null;

    /**
     * Creates a new RequiredParameterMissingException referencing the
     * specified parameter.
     * @param paramID the unique ID of the parameter that was missing.
     */
    public RequiredParameterMissingException(String paramID) {
        super("Parameter '" + paramID + "' is required.");
        this.id = paramID;
    }

    /**
     * Returns the unique ID of the parameter that was missing.
     * @return the unique ID of the parameter that was missing.
     */
    public String getID() {
        return (this.id);
    }

}
