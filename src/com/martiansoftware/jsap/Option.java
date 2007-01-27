/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import java.util.List;

/**
 * The base class from which FlaggedOption and UnflaggedOption are derived.
 * An Option is a Parameter
 * that requires some information (unlike a Switch whose mere presence is
 * significant).<br>
 * <br> 
 * Options may be declared as lists, or multiple values separated by a
 * delimiting character.  An example of
 * a list option might be a classpath, which is a collection of paths separated
 * by a ":" on *nix systems and
 * a ";" on DOS/Windows systems.  JSAP automatically separates list options
 * into multiple tokens before calling
 * their StringParsers' parse() method.
 * 
 * <p>The default list separator is JSAP.DEFAULT_LISTSEPARATOR, which is defined
 * as the platform's path separator
 * character (":" on *nix and ";" on DOS/Windows as described above).
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.Flagged
 * @see com.martiansoftware.jsap.Option
 */
public abstract class Option extends Parameter {

    /**
     * Boolean indicating whether this option is a list.  Default is
     * JSAP.NOT_LIST.
     */
    private boolean isList = JSAP.NOT_LIST;

    /**
     * Boolean indicating whether this option is required.  Default is
     * JSAP.NOT_REQUIRED.
     */
    private boolean required = JSAP.NOT_REQUIRED;

    /**
     * The current list separator character.  Default is
     * JSAP.DEFAULT_LISTSEPARATOR.
     */
    private char listSeparator = JSAP.DEFAULT_LISTSEPARATOR;

    /**
     * The current StringParser.  Default is null, although a
     * StringStringParser will be created if necessary
     * when this Option's parse() method is called.
     */
    private StringParser stringParser = null;

    /**
     * Creates a new Option with the specified unique ID.
     * @param id the unique ID for this Option.
     */
    public Option(String id) {
        super(id);
    }

    /**
     * Sets whether this Option is a list.  Default behavior is JSAP.NOT_LIST.
     * @param isList if true, this Option is a list.
     */
    protected final void internalSetList(boolean isList) {
        enforceParameterLock();
        this.isList = isList;
    }

    /**
     * Returns a boolean indicating whether this Option is a list.
     * @return a boolean indicating whether this Option is a list.
     */
    public final boolean isList() {
        return (isList);
    }

    /**
     * Sets the list separator character for this Option.  The default list
     * separator is JSAP.DEFAULT_LISTSEPARATOR.
     * @param listSeparator the list separator for this Option.
     */
    protected final void internalSetListSeparator(char listSeparator) {
        enforceParameterLock();
        this.listSeparator = listSeparator;
    }

    /**
     * Returns the current list separator character for this Option.
     * @return the current list separator character for this Option.
     */
    public final char getListSeparator() {
        return (listSeparator);
    }

    /**
     * Sets whether this Option is required.  Default is JSAP.NOT_REQUIRED.
     * @param required if true, this Option will be required.
     */
    protected final void internalSetRequired(boolean required) {
        enforceParameterLock();
        this.required = required;
    }

    /**
     * Returns a boolean indicating whether this Option is required.
     * @return a boolean indicating whether this Option is required.
     */
    public final boolean required() {
        return (required);
    }

    /**
     * Sets the StringParser to which this Option's parse() method should
     * delegate.
     * @param stringParser the StringParser to which this Option's parse()
     * method should delegate.
     * @see com.martiansoftware.jsap.StringParser
     */
    protected final void internalSetStringParser(StringParser stringParser) {
        enforceParameterLock();
        this.stringParser = stringParser;
    }

    /**
     * Returns the StringParser to which this Option will delegate calls to its
     * parse() method, or null if
         * no StringParser is currently defined.
     * @return the StringParser to which this Option will delegate calls to its
     * parse() method, or null if
         * no StringParser is currently defined.
     */
    public final StringParser getStringParser() {
        return (stringParser);
    }

    /**
     * Delegates the parsing of a single argument to the current StringParser,
     * and stores the result in the
     * specified List.  List options are broken into multiple arguments,
     * resulting in multiple calls
     * to this method.
     * @param result the List to which the result should be appended.
     * @param argToParse the argument to parse.
     * @throws ParseException if the specified argument cannot be parsed.
     */
    private void storeParseResult(List result, String argToParse)
        throws ParseException {
    	if (argToParse == null) return;
        Object parseResult = getStringParser().parse(argToParse);
        if (parseResult != null) {
            result.add(parseResult);
        }
    }

    /**
     * Parses the specified argument, returning the results in an ArrayList.
     * List options are tokenized
     * before the parse call is delegated to the StringParser.
     * @param arg the argument to parse.
     * @return a List of objects resulting from the parse.
     * @throws ParseException if the specified argument (or one of its tokens,
     * in the case of a list) cannot
     * be parsed.
     */
    protected final List parse(String arg) throws ParseException {
        List result = new java.util.ArrayList();
        if (getStringParser() == null) {
            boolean wasLocked = this.locked();
            setLocked(false);
            internalSetStringParser( JSAP.STRING_PARSER );
            setLocked(wasLocked);
        }

        if ((arg == null)
            || !isList()) { // handle null arguments and non-list arguments
            storeParseResult(result, arg);
        } else {
            StringBuffer subarg = new StringBuffer();
            int arglen = arg.length();
            for (int index = 0; index < arglen; ++index) {
                char c = arg.charAt(index);
                if (c == getListSeparator()) {
                    // parse the subargument stored so far...
                    storeParseResult(result, subarg.toString());
                    subarg.setLength(0);
                } else {
                    subarg.append(c);
                    if (index == arglen - 1) {
                        // needed to parse the last subargument
                        storeParseResult(result, subarg.toString());
                    }
                }
            }
        }
        return (result);
    }

    /**
     * Informs this Option's StringParser that this Option is being registered
     * with a JSAP.  If the StringParser requires any setup not taken care of
     * in its constructor, it should override StringParser.setUp().
     * @throws JSAPException if the underlying StringParser throws it.
     * @see com.martiansoftware.jsap.StringParser#setUp()
     */
    protected void register() throws JSAPException {
        StringParser stringParser = getStringParser();
        try {
            if (stringParser != null) {
                stringParser.setUp();
            }
        } catch (Exception e) {
            throw (new JSAPException(e.getMessage(), e));
        }
    }

    /**
     * Informs this Option's StringParser that this Option is being
     * unregistered
     * from a JSAP.  If the StringParser requires any cleanup, it should
     * ovverride
     * StringParser.tearDown().
     * @see com.martiansoftware.jsap.StringParser#tearDown()
     */
    protected void unregister() {
        StringParser stringParser = getStringParser();
        if (stringParser != null) {
            stringParser.tearDown();
        }
    }

}
