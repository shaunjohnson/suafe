/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.PropertyStringParser;
import com.martiansoftware.jsap.ParseException;
import java.io.File;

/**
 * A StringParser for parsing {@link File} objects.  The parse() method
 * delegates the actual
 * parsing to <code>new File(String)</code>.  If <code>new File(String)</code>
 * throws a NullPointerException, it is encapsulated in a ParseException and
 * re-thrown.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @author  Edward Glen (edward@glencomm.com) (modified URLStringParser)
 * @author  Eric Sword (made setters return "this", fixed bug triggered when
 *          file does not exist)
 * @since 1.4
 * @see com.martiansoftware.jsap.StringParser
 * @see java.net.URL
 */
public class FileStringParser extends PropertyStringParser {

	public static final String MUSTBEFILE = "mustBeFile";
	public static final String MUSTBEDIRECTORY = "mustBeDirectory";
	public static final String MUSTEXIST = "mustExist";

	private boolean mustExist = false;
	private boolean mustBeDirectory = false;
	private boolean mustBeFile = false;

	/** Creates a new FileStringParser.
	 * @deprecated use {@link #getParser()}.
	 */
	public FileStringParser() {
		super();
	}

	/** Returns a new {@link FileStringParser}.
	 * @return a new {@link FileStringParser}.
	 */
	public static FileStringParser getParser() {
		return new FileStringParser();
	}

	public void setUp() throws ParseException {
		BooleanStringParser bool = JSAP.BOOLEAN_PARSER;
		setMustExist(((Boolean) bool.parse(getProperty(MUSTEXIST, (new Boolean(mustExist)).toString()))).booleanValue());
		setMustBeDirectory(((Boolean) bool.parse(getProperty(MUSTBEDIRECTORY, (new Boolean(mustBeDirectory)).toString()))).booleanValue());
		setMustBeFile(((Boolean) bool.parse(getProperty(MUSTBEFILE, (new Boolean(mustBeFile)).toString()))).booleanValue());
	}

	public FileStringParser setMustBeDirectory(boolean mustBeDirectory) {
		this.mustBeDirectory = mustBeDirectory;
        return this;
	}

	public FileStringParser setMustBeFile(boolean mustBeFile) {
		this.mustBeFile = mustBeFile;
        return this;
	}

	public FileStringParser setMustExist(boolean mustExist) {
		this.mustExist = mustExist;
        return this;
	}

	public boolean mustBeDirectory() {
		return (mustBeDirectory);
	}

	public boolean mustBeFile() {
		return (mustBeFile);
	}

	public boolean mustExist() {
		return (mustExist);
	}

	public void tearDown() {
	}

	/**
	 * Parses the specified argument into a File.  This method delegates the
	 * actual
	 * parsing to <code>new File(arg)</code>.  If <code>new File(arg)</code>
	 * throws a NullPointerException, it is encapsulated in a ParseException
	 * and re-thrown.
	 *
	 * @param arg the argument to parse
	 * @return a File as specified by arg.
	 * @throws ParseException if <code>new File(arg)</code> throws a
	 * NullPointerException.
	 * @see java.io File
	 * @see com.martiansoftware.jsap.StringParser#parse(String)
	 */
	public Object parse(String arg) throws ParseException {
		File result = null;
		try {
			result = new File(arg);

            if (mustExist() && !result.exists()) {
                throw (new ParseException(result + " does not exist."));
            }
			if (mustBeDirectory() && result.exists() && !result.isDirectory()) {
				throw (new ParseException(result + " is not a directory."));
			}
			if (mustBeFile() && result.exists() && result.isDirectory()) {
				throw (new ParseException(result + " is not a file."));
			}
		} catch (NullPointerException e) {
			throw (
				new ParseException(
					"No File given to parse",
					e));
		}
		return (result);
	}
}
