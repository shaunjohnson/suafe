/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

/**
 * The base class for all of JSAP's exceptions.  A JSAPException can
 * encapsulate another Exception, which can be obtained via the getRootCause()
 * method.  This is useful in cases where subclasses might need to
 * throw an exception that JSAP is not expecting, such as an IOException while
 * loading a DefaultSource.  The subclass can in these cases throw a new
 * JSAPException encapsulating the IOException.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.Flagged
 * @see com.martiansoftware.jsap.Option
 */
public class JSAPException extends Exception {

    /**
     * Creates a new JSAPException.
     */
    public JSAPException() {
        super();
    }

    /**
     * Creates a new JSAPException with the specified message.
     * @param msg the message for this JSAPException.
     */
    public JSAPException(String msg) {
        super(msg);
    }

    /**
     * Creates a new JSAPException encapsulating the specified Throwable.
     * @param cause the Throwable to encapsulate.
     */
    public JSAPException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new JSAPException with the specified message encapsulating
     * the specified Throwable.
     * @param msg the message for this JSAPException.
     * @param cause the Throwable to encapsulate.
     */
    public JSAPException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
