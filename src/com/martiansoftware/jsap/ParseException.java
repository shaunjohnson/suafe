/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

/**
 * A JSAPException subclass notifying the application of a parse error.
 * Additional information
 * in the form of an exception may be encapsulated in this object, as in other
 * JSAPExceptions.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.JSAPException
 */
public class ParseException extends JSAPException {

    /**
     * Creates a new ParseException.
     */
    public ParseException() {
        super();
    }

    /**
     * Creates a new ParseException with the specified message.
     * @param msg the message for this ParseException.
     */
    public ParseException(String msg) {
        super(msg);
    }

    /**
     * Creates a new ParseException encapsulating the specified Throwable.
     * @param cause the Throwable to encapsulate.
     */
    public ParseException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new ParseException with the specified message encapsulating the
     * specified Throwable.
     * @param msg the message for this ParseException.
     * @param cause the Throwable to encapsulate.
     */
    public ParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
