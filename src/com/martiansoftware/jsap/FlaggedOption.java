/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

/**
 * An option that implements the Flagged interface.  A flagged option is
 * preceded by a short flag or a long flag; i.e. "-n 5" or "--number 5".
 * FlaggedOptions also provide an additional features over unflagged
 * options, namely the ability to be declared more than once in a command line
 * (e.g., "-n 5 -n 10").  This is not possible with unflagged options, as they
 * are never declared.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.Flagged
 * @see com.martiansoftware.jsap.Option
 */
public class FlaggedOption extends Option implements Flagged {

    /**
     * The current short flag for this FlaggedOption.  Default is
     * JSAP.NO_SHORTFLAG.
     */
    private char shortFlag = JSAP.NO_SHORTFLAG;

    /**
     * The current long flag for this FlaggedOption.  Default is
     * JSAP.NO_LONGLAG.
     */
    private String longFlag = JSAP.NO_LONGFLAG;

    /**
     * If true, this FlaggedOption may be declared more than once on a
     * command line.
     * Default is JSAP.NO_MULTIPLEDECLARATIONS.
     */
    private boolean allowMultipleDeclarations = JSAP.NO_MULTIPLEDECLARATIONS;

    /**
     * Creates a new FlaggedOption with the specified unique ID.
     * @param id the unique ID for this FlaggedOption.
     */
    public FlaggedOption(String id) {
        super(id);
    }

    /**
     * A shortcut constructor that creates a new FlaggedOption and configures
     * its most commonly used settings, including help.
     * @param id the unique ID for this FlaggedOption.
     * @param stringParser the StringParser this FlaggedOption should use.
     * @param defaultValue the default value for this FlaggedOption (may be
     * null).
     * @param required if true, this FlaggedOption is required.
     * @param shortFlag the short flag for this option (may be set to
     * JSAP.NO_SHORTFLAG for none).
     * @param longFlag the long flag for this option (may be set to
     * JSAP.NO_LONGFLAG for none).
     * @param help the help text for this option (may be set to {@link JSAP#NO_HELP} for none).
     */
    public FlaggedOption(
        String id,
        StringParser stringParser,
        String defaultValue,
        boolean required,
        char shortFlag,
        String longFlag,
		String help) {
        this(id);
        setStringParser(stringParser);
        setDefault(defaultValue);
        setShortFlag(shortFlag);
        setLongFlag(longFlag);
        setRequired(required);
        setHelp(help);
    }

    /**
     * A shortcut constructor that creates a new FlaggedOption and configures
     * its most commonly used settings.
     * @param id the unique ID for this FlaggedOption.
     * @param stringParser the StringParser this FlaggedOption should use.
     * @param defaultValue the default value for this FlaggedOption (may be
     * null).
     * @param required if true, this FlaggedOption is required.
     * @param shortFlag the short flag for this option (may be set to
     * JSAP.NO_SHORTFLAG for none).
     * @param longFlag the long flag for this option (may be set to
     * JSAP.NO_LONGFLAG for none).
     */
    public FlaggedOption(
        String id,
        StringParser stringParser,
        String defaultValue,
        boolean required,
        char shortFlag,
        String longFlag) {
        this(id, stringParser, defaultValue, required, shortFlag, longFlag, JSAP.NO_HELP);
    }


    /**
     * Sets the short flag for this FlaggedOption.  To use no short flag at all,
     * set the value to JSAP.NO_SHORTFLAG.
     * @param shortFlag the short flag for this FlaggedOption.
     * @return the modified FlaggedOption
     */
    public FlaggedOption setShortFlag(char shortFlag) {
        enforceParameterLock();
        this.shortFlag = shortFlag;
        return (this);
    }

    /**
     * Returns the short flag for this FlaggedOption.  If this FlaggedOption
     * has no short flag, the
     * return value will be equal to JSAP.NO_SHORTFLAG.
     * @return the short flag for this FlaggedOption.  If this FlaggedOption
     * has no short flag, the
     * return value will be equal to JSAP.NO_SHORTFLAG.
     */
    public char getShortFlag() {
        return (shortFlag);
    }

    /**
     * Returns the short flag for this FlaggedOption.  If this FlaggedOption
     * has no short flag, the
     * return value will be null.
     * @return the short flag for this FlaggedOption.  If this FlaggedOption
     * has no short flag, the
     * return value will be null.
     */
    public Character getShortFlagCharacter() {
        return (
            (shortFlag == JSAP.NO_SHORTFLAG) ? null : new Character(shortFlag));
    }

    /**
     * Sets the long flag for this FlaggedOption.  To use no long flag at all,
     * set the value to JSAP.NO_LONGFLAG.
     * @param longFlag the long flag for this FlaggedOption.
     * @return the modified FlaggedOption
     */
    public FlaggedOption setLongFlag(String longFlag) {
        enforceParameterLock();
        this.longFlag = longFlag;
        return (this);
    }

    /**
     * Sets the name that will be displayed when getSyntax() is called
     * @param usageName the name to use, or null if the id should be used (default)
     * @return the modified FlaggedOption
     */
    public FlaggedOption setUsageName(String usageName) {
    	_setUsageName(usageName);
    	return (this);
    }
    
    /**
     * Returns the long flag for this FlaggedOption.  If this FlaggedOption has
     * no long flag, the return
     * value will be equal to JSAP.NO_LONGFLAG.
     * @return the long flag for this FlaggedOption.  If this FlaggedOption has
     * no long flag, the return
     * value will be equal to JSAP.NO_LONGFLAG.
     */
    public String getLongFlag() {
        return (longFlag);
    }

    /**
     * <p>Sets this FlaggedOption to allow or disallow multiple declarations.
     * If multiple declarations are allowed,
     * the flag may be specified multiple times on the command line (e.g.,
     * "-n 5 -n 10").  All of the results
     * are aggregated in the resulting JSAPResult.</p>
     *
     * <p>Default behavior is to disallow multiple declarations.</p>
     * @param allowMultipleDeclarations if true, multiple declarations are
     * allowed.
     * @return the modified FlaggedOption
     */
    public FlaggedOption setAllowMultipleDeclarations(
        boolean allowMultipleDeclarations) {
        enforceParameterLock();
        this.allowMultipleDeclarations = allowMultipleDeclarations;
        return (this);
    }

    /**
     * Returns a boolean indicating whether multiple declarations are allowed
     * for this FlaggedOption.
     * @return a boolean indicating whether multiple declarations are allowed
     * for this FlaggedOption.
     * @see #setAllowMultipleDeclarations(boolean)
     */
    public boolean allowMultipleDeclarations() {
        return (allowMultipleDeclarations);
    }

    /**
     * Returns syntax instructions for this FlaggedOption.
     * @return syntax instructions for this FlaggedOption based upon its current
     * configuration.
     */
    public String getSyntax() {
        StringBuffer result = new StringBuffer();
        if (!required()) {
            result.append("[");
        }

        if ((getLongFlag() != JSAP.NO_LONGFLAG)
            || (getShortFlag() != JSAP.NO_SHORTFLAG)) {
            if (getLongFlag() == JSAP.NO_LONGFLAG) {
                result.append("-" + getShortFlag() + JSAP.SYNTAX_SPACECHAR);
            } else if (getShortFlag() == JSAP.NO_SHORTFLAG) {
                result.append("--" + getLongFlag() + JSAP.SYNTAX_SPACECHAR);
            } else {
                result.append(
                    "(-" + getShortFlag() + "|--" + getLongFlag() + ")" + JSAP.SYNTAX_SPACECHAR);
            }
        }
        String un = getUsageName();
        char sep = this.getListSeparator();
        if (this.isList()) {
            result.append(
                un + "1" + sep + un + "2" + sep + "..." + sep + un + "N ");
        } else {
            result.append("<" + getUsageName() + ">");
        }
        if (!required()) {
            result.append("]");
        }
        return (result.toString());
    }

    /**
     * Sets whether this FlaggedOption is a list.  Default behavior is
     * JSAP.NOT_LIST.
     * @param isList if true, this Option is a list.
     * @return the modified FlaggedOption
     */
    public FlaggedOption setList(boolean isList) {
        super.internalSetList(isList);
        return (this);
    }

    /**
     * Sets the list separator character for this FlaggedOption.  The default
     * list separator is JSAP.DEFAULT_LISTSEPARATOR.
     * @param listSeparator the list separator for this Option.
     * @return the modified FlaggedOption
     */
    public FlaggedOption setListSeparator(char listSeparator) {
        super.internalSetListSeparator(listSeparator);
        return (this);
    }

    /**
     * Sets whether this FlaggedOption is required.  Default is
     * JSAP.NOT_REQUIRED.
     * @param required if true, this Option will be required.
     * @return the modified FlaggedOption
     */
    public FlaggedOption setRequired(boolean required) {
        super.internalSetRequired(required);
        return (this);
    }

    /**
     * Sets one or more default values for this parameter.  This method
     * should be used whenever a parameter has more than one default
     * value.
     * @param defaultValues the default values for this parameter.
     * @see #setDefault(String)
     */
    public FlaggedOption setDefault(String[] defaultValues) {
    	_setDefault(defaultValues);
    	return (this);
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
    public FlaggedOption setDefault(String defaultValue) {
    	_setDefault(defaultValue);
    	return (this);
    }    
    
    /**
     * Sets the StringParser to which this FlaggedOption's parse() method
     * should delegate.
     * @param stringParser the StringParser to which this Option's parse()
     * method should delegate.
     * @see com.martiansoftware.jsap.StringParser
     * @return the modified FlaggedOption
     */
    public FlaggedOption setStringParser(StringParser stringParser) {
        super.internalSetStringParser(stringParser);
        return (this);
    }
}
