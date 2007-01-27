/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing Longs.  The parse() method delegates the actual
 * parsing to <code>Long.decode(String)</code>.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.lang.Long
 */
public class LongStringParser extends StringParser {
	
	private static final LongStringParser INSTANCE = new LongStringParser();	

	/** Returns a {@link LongStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#LONG_PARSER}.
	 *  
	 * @return a {@link LongStringParser}.
	 */
    public static LongStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new LongStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#LONG_PARSER}.
     */
    public LongStringParser() {
        super();
    }

    /**
     * Parses the specified argument into a Long.  This method delegates
     * the parsing to <code>Long.decode(arg)</code>.  If
     * <code>Long.decode()</code> throws a
     * NumberFormatException, it is encapsulated into a ParseException and
     * re-thrown.
     *
     * @param arg the argument to parse
     * @return a Long object with the value contained in the specified argument.
     * @throws ParseException if <code>Long.decode(arg)</code> throws a
     * NumberFormatException.
     * @see java.lang.Long
     * @see com.martiansoftware.jsap.StringParser#parse(String)
     */
    public Object parse(String arg) throws ParseException {
        Long result = null;
        try {
            result = Long.decode(arg);
        } catch (NumberFormatException e) {
            throw (
                new ParseException(
                    "Unable to convert '" + arg + "' to a Long.",
                    e));
        }
        return (result);
    }
}
