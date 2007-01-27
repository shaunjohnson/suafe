/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

/** An exception thrown when an argument that requires a conversion
 * (e.g., an integer) has no associated value, but it is retrieved
 * by means of a type-specified method (e.g., {@link com.martiansoftware.jsap.JSAPResult#getInt(String)}).
 * 
 * @author Sebastiano Vigna
 */
public class UnspecifiedParameterException extends RuntimeException {

    /**
     * The unique ID of the parameter whose retrieval was attempted.
     */
    private String id = null;

    /** Creates a new {@link UnspecifiedParameterException} referencing the
     * specified parameter.
     * @param paramID the unique ID of the parameter whose retrieval was attempted.
     */
    public UnspecifiedParameterException(String paramID) {
        super("Parameter '" + paramID + "' has no associated value.");
        this.id = paramID;
    }

    /**
     * Returns the unique ID of the parameter whose retrieval was attempted.
     * @return the unique ID of the parameter whose retrieval was attempted.
     */
    public String getID() {
        return (this.id);
    }
}
