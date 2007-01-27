/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

/**
 * An exception indicating that a syntax error was encountered in the argument
 * list.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class SyntaxException extends JSAPException {

    /**
     * Creates a new SyntaxException with the specified message.
     * @param msg a description of the syntax problem.
     */
    public SyntaxException(String msg) {
        super(msg);
    }

}
