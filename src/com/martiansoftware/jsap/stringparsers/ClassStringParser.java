/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing Class objects.  The parse(arg) method calls
 * Class.forName(arg) and returns
 * the result.  If any exceptions are thrown by Class.forName(), they are
 * encapsulated in a ParseException
 * and re-thrown.
 * 
 * <p><b>Note:</b> The Class.forName() call attempts to load the class from the
 * same ClassLoader that loaded
 * this StringParser.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.lang.Class
 */
public class ClassStringParser extends StringParser {

	private static final ClassStringParser INSTANCE = new ClassStringParser();	

	/** Returns a {@link ClassStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#CLASS_PARSER}.
	 *  
	 * @return a {@link ClassStringParser}.
	 */

    public static ClassStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new ClassStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#CLASS_PARSER}.
     */
    public ClassStringParser() {
        super();
    }

    /**
     * Parses the specified argument into a Class object.  This method calls
     * Class.forName(), passing
     * the specified argument as the name of the class to load, and returns
     * the resulting Class object.
     * If an exception is thrown by Class.forName(), it is encapsulated in a
     * ParseException and re-thrown.
     *
     * @param arg the argument to parse
     * @return a Class object representing the class named by the specified
     * argument.
     * @throws ParseException if <code>Class.forName(arg)</code> throws an
     * exception.
     * @see java.lang.Class
     * @see com.martiansoftware.jsap.StringParser#parse(String)
     */
    public Object parse(String arg) throws ParseException {
        Class result = null;
        try {
            result = Class.forName(arg);
        } catch (Exception e) {
            throw (
                new ParseException("Unable to locate class '" + arg + "'.", e));
        }
        return (result);
    }
}
