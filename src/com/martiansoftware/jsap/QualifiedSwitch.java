/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import com.martiansoftware.jsap.FlaggedOption;

/**
 * A QualifiedSwitch is a parameter that has something in common with a Switch,
 * i.e., its presence or absence is significant, but different from a "pure"
 * Switch it can have an additional value (or values) prefixed by a ':' sign 
 * that qualifies the Switch - making it behave like a FlaggedOption if a value 
 * is specified.
 * <p>
 * QualifiedSwitch in fact extends FlaggedOption, providing all of its 
 * functionality including the ability to use any StringParser to parse its 
 * optional value(s).
 * Values are retrieved from the JSAPResult in the same manner they would
 * be retrieved for an equivalent FlaggedOption.
 * <p>
 * Additionally, the QualifiedSwitch's presence on the command line can be
 * determined via JSAPResult.getBoolean(id).  This presents a small challenge
 * in the unlikely event that you're also using a BooleanStringParser with
 * the QualifiedSwitch; if you are, JSAPResult.getBoolean(id) will <b>not</b>
 * return the optional values, but will still signify the QualifiedSwitch's
 * presence.  Boolean optional values can be retrieved via 
 * JSAPResult.getBooleanArray(id).  The first boolean in the array will be the
 * first optionally specified boolean value qualifying the switch.
 * <p>
 * The following are some examples of a QualifiedSwitch's use:
 * <ul>
 * <li><b>-s:value</b> is a QualifiedSwitch with only one qualifying value.</li>
 * <li><b>-s</b> has no qualifying value.</li>
 * <li><b>--qswitch:a,b,c</b> (configured with <code>setList(true)</code>
 * and <code>setListSeparator(',')</code>) has three qualifying values.</li>
 * </ul> 
 * <p>
 * Please note that QualifiedSwitch is currently <b>experimental</b>, although
 * it has no known problems.
 * <p>
 * QualifiedSwitch and its supporting code in other JSAP classes was generously 
 * contributed to JSAP by Klaus P. Berg of Siemens AG, Munich, Germany.
 * @since 1.03
 * @author  Klaus P. Berg, Siemens AG, Munich, Germany
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
 /* ML - switched superclass from Switch to FlaggedOption */
public final class QualifiedSwitch extends FlaggedOption {

    /**
     * The current short flag for this UnflaggedOption.  
         * Default is JSAP.NO_SHORTFLAG.
     */
//    private char shortFlag = JSAP.NO_SHORTFLAG;

    /**
     * The current long flag for this UnflaggedOption.  
         *Default is JSAP.NO_LONGFLAG.
     */
//    private String longFlag = JSAP.NO_LONGFLAG;

//    private String qualifyingValues; // the qualifying values that are allowed for the switch
//    boolean checkEnumeratedValues = false; // tells the parser if the qualifyingValues are enumerated values

	
	/**
	 * A shortcut constructor that creates a new QualifiedSwitch and configures
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
	public QualifiedSwitch(
			String id,
			StringParser stringParser,
			String defaultValue,
			boolean required,
			char shortFlag,
			String longFlag,
			String help) {
	
			super(id, stringParser, defaultValue, required, shortFlag, longFlag, help);
	}

	/**
	 * A shortcut constructor that creates a new QualifiedSwitch and configures
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
	public QualifiedSwitch(
			String id,
			StringParser stringParser,
			String defaultValue,
			boolean required,
			char shortFlag,
			String longFlag) {
	
			super(id, stringParser, defaultValue, required, shortFlag, longFlag);
	}


    /**
     * A shortcut constructor that creates a new QualifiedSwitch
     * 
     * @param id the unique ID for this QualifiedSwitch.         
     */
    public QualifiedSwitch(String id) {
    	super(id);
    }   

    /**
     * Returns syntax instructions for this QualifiedSwitch.
     * @return syntax instructions for this QualifiedSwitch based upon its current
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
    			result.append("-" + getShortFlag());
    		} else if (getShortFlag() == JSAP.NO_SHORTFLAG) {
    			result.append("--" + getLongFlag());
    		} else {
    			result.append(
    					"(-" + getShortFlag() + "|--" + getLongFlag() + ")");
    		}
    	}
    	result.append("[:");
    	String un = getUsageName();
    	char sep = this.getListSeparator();
    	if (this.isList()) {
    		result.append(
    				un + "1" + sep + un + "2" + sep + "..." + sep + un + "N ");
    	} else {
    		result.append("<" + un + ">");
    	}
    	if (!required()) {
    		result.append("]");
    	}
    	result.append("]");
    	return (result.toString());
    } 
    
    
    
    /**
     * Creates a new QualifiedSwitchValuesParser to which it delegates the parsing of 
     * the specified argument.
     * The result is as follows:
     * <ul>
     * <li>ArrayList[0] contains a single Boolean that tells the
     *  user whether this QualifiedSwitch is present or not.
     * <li>ArrayList[1] is a string that contains the qualifying value.
     * </ul>
     * 
     * @param arg the argument to parse.
     * @return an ArrayList containing the parse results.
     * @throws ParseException if the specified parameter cannot be parsed.
     */
//    protected final List parse(String arg) throws ParseException {
//    	List result = new java.util.ArrayList();
//        if (arg != null && arg.startsWith(":")) {
//            result.add(Boolean.TRUE); // flag is present
//            if (checkEnumeratedValues) {
//                result.add((new EnumeratedStringParser(qualifyingValues)).parse(arg.substring(1))); // ad the value allowed
//            }
//            else {
//                result.add((new StringStringParser()).parse(arg.substring(1))); // add arbitrary string value
//            }
//        }
//        else {
//            result.add((new BooleanStringParser()).parse(arg));
//        }
//        return result;
//    }

//    /**
//     * Returns usage instructions for this Switch.
//     * @return usage instructions for this Switch based upon its current 
//     * configuration.
//     */
//    public String getSyntax() {
//        StringBuffer buf = new StringBuffer();
//        boolean shortFlag = false;
//        buf.append("[");
//        if (getShortFlag() != JSAP.NO_SHORTFLAG) {
//            buf.append("-" + getShortFlag());
//            shortFlag = true;
//        }
//        if (getLongFlag() != JSAP.NO_LONGFLAG) {
//            if (shortFlag) {
//                buf.append("|");
//            }
//            buf.append("--" + getLongFlag());
//        }
//        buf.append("[:");
//        if (checkEnumeratedValues) {
//            buf.append(qualifyingValues.replace(';', '|'));
//        }
//        else {
//            buf.append(qualifyingValues);
//        }
//        buf.append("]]");
//        return(buf.toString());
//    }
}
