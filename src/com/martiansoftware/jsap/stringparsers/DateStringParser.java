/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.ParseException;
import com.martiansoftware.jsap.PropertyStringParser;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing java.util.Date objects.  By default, arguments
 * are parsed using the
 * java.text.SimpleDateFormat for the default locale.  The format can be
 * overridden using this StringParser's
 * setProperties() method, supplying a java.util.Properties object with a
 * property key named "format".
 * The value associated with the "format" property is used to create a new
 * java.text.SimpleDateFormat
 * to parse the argument.
 * 
 * <p>A ParseException is thrown if a SimpleDateFormat cannot be constructed with
 * the specified format, or if the SimpleDateFormat throws a
 * java.text.ParseException during parsing.
 * 
 * <p>The SimpleDateFormat object is instantiated when an option referencing this
 * DateStringParser is
 * registered with a JSAP object.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.util.Date
 * @see java.text.SimpleDateFormat
 */
public class DateStringParser extends PropertyStringParser {

    /**
     * The SimpleDateFormat used to do the parsing.
     */
    private SimpleDateFormat format = null;

    /** Returns a {@link DateStringParser}.
	 * 
	 * @return a {@link DateStringParser}.
	 */
    
	public static DateStringParser getParser() {
		return new DateStringParser();
	}

    /**
     * Creates a new DateStringParser.
     * @deprecated Use {@link #getParser()}.
     */
    public DateStringParser() {
        super();
    }

    /**
     * Instantiates the SimpleDateFormat to use for parsing.
     * @throws ParseException if a SimpleDateFormat cannot be instantiated with
     * the contents of the
     * "format" property.
     */
    public void setUp() throws ParseException {
        String formatString = this.getProperty("format");
        if (formatString == null) {
            format = new SimpleDateFormat();
        } else {
        	try {
        		format = new SimpleDateFormat(formatString);
        	}
        	catch( RuntimeException e ) {
        		throw new ParseException( e );
        	}
        }
    }

    /**
     * Destroys the SimpleDateFormat used for parsing.
     */
    public void tearDown() {
        format = null;
    }

    /**
     * Parses the specified argument using either the java.text.SimpleDateFormat
     * for the current locale
     * (by default) or a java.text.SimpleDateFormat as defined by this
     * PropertyStringParser's "format"
     * property.
     *
     * If the specified argument cannot be parsed by the current format, a
     * ParseException is thrown.
     *
     * @param arg the argument to convert to a Date.
     * @return a Date as described above.
     * @throws ParseException if the specified argument cannot be parsed by the
     * current format..
     */
    public Object parse(String arg) throws ParseException {
        Date result = null;
        try {
            result = format.parse(arg);
        } catch (java.text.ParseException e) {
            throw (
                new ParseException(
                    "Unable to convert '" + arg + "' to a Date.",
                    e));
        }
        return (result);
    }

}
