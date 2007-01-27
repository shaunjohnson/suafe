/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing Integers.  The parse() method delegates the actual
 * parsing to Integer.decode(String).
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.lang.Integer
 */
public class IntegerStringParser extends StringParser {
	
	private static final IntegerStringParser INSTANCE = new IntegerStringParser();	

	/** Returns a {@link IntegerStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#INTEGER_PARSER}.
	 *  
	 * @return a {@link IntegerStringParser}.
	 */
    public static IntegerStringParser getParser() {
		return new IntegerStringParser();
	}

	/**
     * Creates a new IntegerStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#INTEGER_PARSER}.
     */
    public IntegerStringParser() {
        super();
    }

    /**
     * Parses the specified argument into an Integer.  This method delegates
     * the parsing to <code>Integer.decode(arg)</code>.  If
     * <code>Integer.decode()</code> throws a
     * NumberFormatException, it is encapsulated into a ParseException and
     * re-thrown.
     *
     * @param arg the argument to parse
     * @return an Integer object with the value contained in the specified
     * argument.
     * @throws ParseException if <code>Integer.decode(arg)</code> throws a
     * NumberFormatException.
     * @see java.lang.Integer
     * @see com.martiansoftware.jsap.StringParser#parse(String)
     */
    public Object parse(String arg) throws ParseException {
        Integer result = null;
        try {
            result = Integer.decode(arg);
        } catch (NumberFormatException nfe) {
            throw (
                new ParseException(
                    "Unable to convert '" + arg + "' to an Integer.",
                    nfe));
        }
        return (result);
    }
}
