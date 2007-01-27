/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
 
package com.martiansoftware.jsap;

import java.util.List;

import com.martiansoftware.jsap.stringparsers.BooleanStringParser;

/**
 * A Switch is a parameter whose presence alone is significant; another 
 * commonly used term for a Switch is "Flag".
 * Switches use a {@link com.martiansoftware.jsap.stringparsers.BooleanStringParser}
 * internally, so their results can be 
 * obtained from a JSAPResult using
 * the getBoolean() methods.
 * 
 * <p>An example of a command line using a Switch is "dosomething -v", where 
 * "-v" might mean "verbose."  
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.Flagged
 * @see com.martiansoftware.jsap.stringparsers.BooleanStringParser
 */
public class Switch extends Parameter implements Flagged {

	/**
	 * The current short flag for this UnflaggedOption.  Default is 
	 * JSAP.NO_SHORTFLAG.
	 */
	private char shortFlag = JSAP.NO_SHORTFLAG;
                          
	/**
	 * The current long flag for this UnflaggedOption.  Default is 
	 * JSAP.NO_LONGFLAG.
	 */
	private String longFlag = JSAP.NO_LONGFLAG;

	/**
	 * Creates a new Switch with the specified unique ID.
	 * @param id the unique ID for this Switch.
	 */
	public Switch(String id) {
		super(id);
		setDefault("FALSE");
	}

	/**
	 * A shortcut constructor that creates a new Switch and configures all of 
	 * its settings, including help.
	 * @param id the unique ID for this Switch.
	 * @param shortFlag the short flag for this Switch (may be set to 
	 * JSAP.NO_SHORTFLAG for none).
	 * @param longFlag the long flag for this Switch (may be set to 
	 * JSAP.NO_LONGFLAG for none).
	 * @param help the help text for this Switch (may be set to {@link JSAP#NO_HELP}for none).
	 * */
	public Switch(String id, char shortFlag, String longFlag, String help) {
		this(id);
		setShortFlag(shortFlag);
		setLongFlag(longFlag);
		setHelp(help);
	}

	/**
	 * A shortcut constructor that creates a new Switch and configures all of 
	 * its settings.
	 * @param id the unique ID for this Switch.
	 * @param shortFlag the short flag for this Switch (may be set to 
	 * JSAP.NO_SHORTFLAG for none).
	 * @param longFlag the long flag for this Switch (may be set to 
	 * JSAP.NO_LONGFLAG for none).
	 * */
	public Switch(String id, char shortFlag, String longFlag) {
		this(id, shortFlag, longFlag, JSAP.NO_HELP);
	}

		/**
	 * Sets the short flag for this Switch.  To use no short flag at all, set 
	 * the value to JSAP.NO_SHORTFLAG.
	 * @param shortFlag the short flag for this Switch.
	 * @return the modified Switch
	 */
	public Switch setShortFlag(char shortFlag) {
		enforceParameterLock();
		this.shortFlag = shortFlag;
		return (this);
	}

	/**
	 * Returns the short flag for this Switch.  If this Switch has no short 
	 * flag, the
	 * return value will be equal to JSAP.NO_SHORTFLAG.
	 * @return the short flag for this Switch.  If this Switch has no short 
	 * flag, the
	 * return value will be equal to JSAP.NO_SHORTFLAG.
	 */
	public char getShortFlag() {
		return (shortFlag);
	}

	/**
	 * Returns the short flag for this Switch.  If this Switch has no short 
	 * flag, the
	 * return value will be null.
	 * @return the short flag for this Switch.  If this Switch has no short 
	 * flag, the
	 * return value will be null.
	 */
	public Character getShortFlagCharacter() {
		return ((shortFlag == '\0') ? null : new Character(shortFlag));
	}

	/**
	 * Sets the long flag for this Switch.  To use no long flag at all, set 
	 * the value to JSAP.NO_LONGFLAG.
	 * @param longFlag the long flag for this Switch.
	 * @return the modified Switch
	 */
	public Switch setLongFlag(String longFlag) {
		enforceParameterLock();
		this.longFlag = longFlag;
		return (this);
	}

	/**
	 * Returns the long flag for this Switch.  If this Switch has no long flag, 
	 * the return
	 * value will be equal to JSAP.NO_LONGFLAG.
	 * @return the long flag for this FlaggedOption.  If this FlaggedOption has 
	 * no long flag, the return
	 * value will be equal to JSAP.NO_LONGFLAG.
	 */
	public String getLongFlag() {
		return (longFlag);
	}

	/**
	 * Creates a new BooleanStringParser to which it delegates the parsing of 
	 * the specified argument.
	 * The result is always a single Boolean.
	 * @param arg the argument to parse.
	 * @return an ArrayList containing a single Boolean.
	 * @throws ParseException if the specified parameter cannot be parsed.
	 */
	protected List parse(String arg) throws ParseException {
		List result = new java.util.ArrayList(1);
		result.add((BooleanStringParser.getParser()).parse(arg));
		return (result);
	}

	/**
	 * Returns usage instructions for this Switch.
	 * @return usage instructions for this Switch based upon its current 
	 * configuration.
	 */
	public String getSyntax() {
		StringBuffer buf = new StringBuffer();
		boolean shortFlag = false;
		buf.append("[");
		if (getShortFlag() != JSAP.NO_SHORTFLAG) {
			buf.append("-" + getShortFlag());
			shortFlag = true;
		}
		if (getLongFlag() != JSAP.NO_LONGFLAG) {
			if (shortFlag) {
				buf.append("|");
			}
			buf.append("--" + getLongFlag());
		}
		buf.append("]");
		return (buf.toString());
	}
	
	/**
	 * Sets a default value for this parameter.  The default is specified
	 * as a String, and is parsed as a single value specified on the
	 * command line.  In other words, default values for "list"
	 * parameters or parameters allowing multiple declarations should be
	 * set using setDefault(String[]), as JSAP
	 * would otherwise treat the entire list of values as a single value.
	 *
	 * @param defaultValue the default value for this parameter.
	 * @see #setDefault(String)
	 */
	public Switch setDefault(String defaultValue) {
		_setDefault(defaultValue);
		return (this);
	}    

	/**
	 * Sets one or more default values for this parameter.  This method
	 * should be used whenever a parameter has more than one default
	 * value.
	 * @param defaultValues the default values for this parameter.
	 * @see #setDefault(String)
	 */
	public Switch setDefault(String[] defaultValues) {
		_setDefault(defaultValues);
		return (this);
	}
}
