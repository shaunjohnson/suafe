/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import java.lang.reflect.Method;

import com.martiansoftware.jsap.ParseException;
import com.martiansoftware.jsap.StringParser;


/** A {@link com.martiansoftware.jsap.StringParser} that passes the
 * argument to a static method of signature <code>forName(String)</code> of a specified class.
 * 
 * <P>Note that, for instance, this parser can be used with {@link java.lang.Class} (resulting in a 
 * string parser identical to {@link com.martiansoftware.jsap.stringparsers.ClassStringParser}),
 * but also {@link java.nio.charset.Charset}, and more generally, any class using the <code>forName(String)</code>
 * convention.
 *
 * @author Sebastiano Vigna
 */

public class ForNameStringParser extends StringParser {

	/** The class array describing the parameters (a string) of <code>forName</code>. */
	private final static Class[] PARAMETERS = new Class[] { String.class };
	
	/** The class given to the constructor. */
	private final Class klass;
	/** The <code>forName(String)</code> static method of {@link #klass}. */
	private final Method forName;

	private ForNameStringParser( final Class klass ) throws SecurityException, NoSuchMethodException {
		this.klass = klass;
		forName = klass.getMethod( "forName", PARAMETERS );
	}
	
	/** Returns a class <code>forName()</code> string parser.
	 *
	 * <p>When required to parse an argument, the returned string parser will return the
	 * object obtain by means of a call to a static method of <code>klass</code> of signature
	 * <code>forName(String)</code>.
	 *  
	 * @param klass a class with a static method of signature <code>forName(String)</code>.
	 */
	
	public static ForNameStringParser getParser( final Class klass ) throws SecurityException, NoSuchMethodException {
		return new ForNameStringParser( klass );
	}
	
	public Object parse( String arg ) throws ParseException {
		try {
			return forName.invoke( klass, new Object[] { arg } );
		}
		catch ( Exception e ) {
			throw new ParseException ( e );
		}
	}
}
