/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap;

/**
 * An exception indicating that a unknown flag has been specified.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.Flagged
 */
public class UnknownFlagException extends JSAPException {

    /**
     * The unknown flag that was encountered.
     */
    private String flag = null;

    /**
     * Creates a new UnknownFlagException referencing the specified parameter.
     * @param flag the unknown flag that was encountered.
     */
    public UnknownFlagException(String flag) {
        super("Unknown flag '" + flag + "'.");
        this.flag = flag;
    }

    /**
     * Creates a new UnknownFlagException referencing the specified parameter.
     * @param flag the unknown flag that was encountered.
     */
    public UnknownFlagException(Character flag) {
        super("Unknown flag '" + flag + "'.");
        this.flag = flag.toString();
    }

    /**
     * Returns the unknown flag that was encountered.
     * @return the unknown flag that was encountered.
     */
    public String getFlag() {
        return (this.flag);
    }

}
