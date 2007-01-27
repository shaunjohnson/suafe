/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing java.net.URL objects.  The parse() method
 * delegates the actual
 * parsing to <code>new URL(String)</code>.  If <code>new URL()</code>
 * throws a MalformedURLException, it is encapsulated in a ParseException and
 * re-thrown.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.net.URL
 */
public class URLStringParser extends StringParser {

	private static final URLStringParser INSTANCE = new URLStringParser();	

	/** Returns a {@link URLStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#URL_PARSER}.
	 *  
	 * @return a {@link URLStringParser}.
	 */

    public static URLStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new URLStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#URL_PARSER}.
     */
    public URLStringParser() {
        super();
    }

    /**
     * Parses the specified argument into a URL.  This method delegates the
     * actual
     * parsing to <code>new URL(arg)</code>.  If <code>new URL(arg)</code>
     * throws a MalformedURLException, it is encapsulated in a ParseException
     * and re-thrown.
     *
     * @param arg the argument to parse
     * @return a URL as specified by arg.
     * @throws ParseException if <code>new URL(arg)</code> throws a
     * MalformedURLException.
     * @see java.net URL
     * @see com.martiansoftware.jsap.StringParser#parse(String)
     */
    public Object parse(String arg) throws ParseException {
        URL result = null;
        try {
            result = new URL(arg);
        } catch (MalformedURLException e) {
            throw (
                new ParseException(
                    "Unable to convert '" + arg + "' to a URL.",
                    e));
        }
        return (result);
    }
}
