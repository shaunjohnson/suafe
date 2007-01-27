/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing Shorts.  The parse() method delegates the actual
 * parsing to <code>Short.decode(String)</code>.  If <code>Short.decode()</code>
 * throws a
 * NumberFormatException, it is encapsulated in a ParseException and re-thrown.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.math.BigDecimal
 */
public class ShortStringParser extends StringParser {

	private static final ShortStringParser INSTANCE = new ShortStringParser();	

	/** Returns a {@link ShortStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#SHORT_PARSER}.
	 *  
	 * @return a {@link ShortStringParser}.
	 */

    public static ShortStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new ShortStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#SHORT_PARSER}.
     */
    public ShortStringParser() {
        super();
    }

    /**
     * Parses the specified argument into a Short.  This method delegates the
     * parsing to <code>Short.decode(String)</code>.  If
     * <code>Short.decode()</code> throws a
     * NumberFormatException, it is encapsulated in a ParseException and
     * re-thrown.
     *
     * @param arg the argument to parse
     * @return a Short object with the value contained in the specified
     * argument.
     * @throws ParseException if <code>Short.decode(arg)</code> throws a
     * NumberFormatException.
     * @see java.lang.Short
     * @see com.martiansoftware.jsap.StringParser#parse(String)
     */
    public Object parse(String arg) throws ParseException {
        Short result = null;
        try {
            result = Short.decode(arg);
        } catch (NumberFormatException e) {
            throw (
                new ParseException(
                    "Unable to convert '" + arg + "' to a Short.",
                    e));
        }
        return (result);
    }
}
