/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing java.net.InetAddress objects.  The parse() method
 * delegates the actual
 * parsing to <code>InetAddress.getByName(String)</code>.  If
 * <code>InetAddress.getByName()</code>
 * throws an UnknownHostException, it is encapsulated in a ParseException and
 * re-thrown.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.net.InetAddress
 */
public class InetAddressStringParser extends StringParser {

	private static final InetAddressStringParser INSTANCE = new InetAddressStringParser();	

	/** Returns a {@link InetAddressStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#INETADDRESS_PARSER}.
	 *  
	 * @return a {@link InetAddressStringParser}.
	 */

    public static InetAddressStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new InetAddressStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#INETADDRESS_PARSER}.
     */
    public InetAddressStringParser() {
        super();
    }

    /**
     * Parses the specified argument into an InetAddress.  This method
     * delegates the actual
     * parsing to <code>InetAddress.getByName(arg)</code>.  If
     * <code>InetAddress.getByName(arg)</code>
     * throws an UnknownHostException, it is encapsulated in a ParseException
     * and re-thrown.
     *
     * @param arg the argument to parse
     * @return an InetAddress object representing the specified address.
     * @throws ParseException if <code>InetAddress.getByName(arg)</code> throws
     * an UnknownHostException.
     * @see java.net InetAddress
     * @see com.martiansoftware.jsap.StringParser#parse(String)
     */
    public Object parse(String arg) throws ParseException {
        InetAddress result = null;
        try {
            result = InetAddress.getByName(arg);
        } catch (UnknownHostException e) {
            throw (new ParseException("Unknown host: " + arg, e));
        }
        return (result);
    }
}
