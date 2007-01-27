/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing Doubles.  The parse() method delegates the actual
 * parsing to new Double(String).  If a NumberFormatException is thrown by new
 * Double(String), it
 * is encapsulated in a ParseException and re-thrown.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.lang.Double
 */
public class DoubleStringParser extends StringParser {

	private static final DoubleStringParser INSTANCE = new DoubleStringParser();	

	/** Returns a {@link DoubleStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#DOUBLE_PARSER}.
	 *  
	 * @return a {@link DoubleStringParser}.
	 */

	public static DoubleStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new DoubleStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#DOUBLE_PARSER}.
     */
    public DoubleStringParser() {
        super();
    }

    /**
     * Parses the specified argument into a Double.  This method simply
     * delegates the actual
     * parsing to new Double(String).  If a NumberFormatException is thrown by
     * new Double(String), it
     * is encapsulated in a ParseException and re-thrown.
     *
     * @param arg the argument to parse
     * @return a Double object with the value contained in the specified
     * argument.
     * @throws ParseException if <code>new Double(arg)</code> throws a
     * NumberFormatException.
     * @see java.lang.Double
     * @see com.martiansoftware.jsap.StringParser#parse(String)
     */
    public Object parse(String arg) throws ParseException {
        Double result = null;
        try {
            result = new Double(arg);
        } catch (NumberFormatException e) {
            throw (
                new ParseException(
                    "Unable to convert '" + arg + "' to a Double.",
                    e));
        }
        return (result);
    }
}
