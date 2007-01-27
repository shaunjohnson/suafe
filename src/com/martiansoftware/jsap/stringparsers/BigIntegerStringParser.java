/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;
import java.math.BigInteger;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing BigIntegers.  The parse() method delegates the
 * actual
 * parsing to BigInteger's constructor.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.math.BigInteger
 */
public class BigIntegerStringParser extends StringParser {

	private static final BigIntegerStringParser INSTANCE = new BigIntegerStringParser();	

	/** Returns a {@link BigIntegerStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#BIGINTEGER_PARSER}.
	 *  
	 * @return a {@link BigIntegerStringParser}.
	 */
    public static BigIntegerStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new BigIntegerStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#BIGINTEGER_PARSER}.
     */
    public BigIntegerStringParser() {
        super();
    }

    /**
    * Parses the specified argument into a BigInteger.  This method simply
    * delegates
    * the parsing to <code>new BigInteger(String)</code>.  If BigInteger
    * throws a
    * NumberFormatException, it is encapsulated into a ParseException and
    * re-thrown.
    *
    * @param arg the argument to parse
    * @return a BigInteger object with the value contained in the specified
    * argument.
    * @throws ParseException if <code>new BigInteger(arg)</code> throws a
    * NumberFormatException.
    * @see BigInteger
    * @see com.martiansoftware.jsap.StringParser#parse(String)
    */
    public Object parse(String arg) throws ParseException {
        BigInteger result = null;
        try {
            result = new BigInteger(arg);
        } catch (NumberFormatException e) {
            throw (
                new ParseException(
                    "Unable to convert '" + arg + "' to a BigInteger.",
                    e));
        }
        return (result);
    }
}
