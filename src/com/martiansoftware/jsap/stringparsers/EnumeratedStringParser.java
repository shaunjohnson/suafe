/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import java.util.Arrays;
import java.util.StringTokenizer;
import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.ParseException;

/**
 * A {@link com.martiansoftware.jsap.StringParser} that enforces a limited set of String options for its
 * values.
 * These values are provided in the constructor together with one or two parameters
 * that control the processing of these values.
 * 
 * <p>EnumeratedStringParser was generously 
 * contributed to JSAP by Klaus-Peter Berg of Siemens AG, Munich, Germany.
 * @since 1.03
 * @author  Klaus-Peter Berg, Siemens AG, Munich, Germany
 * @version 2.0
 */
public class EnumeratedStringParser extends StringParser {

	/**
	 * char used to separate enumerated values when they are supplied 
	 * to the constructor
	 */
	public static final char CONSTRUCTOR_VALUE_SEPARATOR = ';';
	

	private String[] validOptionValuesArray = null;
	private boolean isCaseSensitive;
	private boolean checkOptionChars;

	/**
	 * Returns an EnumeratedParameterParser.
	 * 
	 * @param validOptionValues a string that contains valid values for an option 
	 *        in the format "value_1;value_2;..;value_n"; spaces between values are allowed 
	 *        to make things more readable, e.g., "value_1; value_2";
	 *        option values have to be constructed using Java identifier characters
	 *        if the checkOptionChars parameter tells the parser to do this.
	 * @param caseSensitive tells the parser whether the option value is case sensitive
	 * @param checkOptionChars tells the parser whether to check for Java identifier conformant characters.
	 * @throws IllegalArgumentException if the option value string has wrong format
	 *         or is empty
	 */
	public static EnumeratedStringParser getParser( String validOptionValues, boolean caseSensitive, boolean checkOptionChars ) throws IllegalArgumentException {
		return new EnumeratedStringParser( validOptionValues, caseSensitive, checkOptionChars );
	}

	/**
	 * Constructs a new instance of EnumeratedParameterParser.
	 * 
	 * @param validOptionValues a string that contains valid values for an option 
	 *        in the format "value_1;value_2;..;value_n"; spaces between values are allowed 
	 *        to make things more readable, e.g., "value_1; value_2";
	 *        option values have to be constructed using Java identifier characters
	 *        if the checkOptionChars parameter tells the parser to do this.
	 * @param caseSensitive tells the parser whether the option value is case sensitive
	 * @param checkOptionChars tells the parser whether to check for Java identifier conformant characters.
	 * @throws IllegalArgumentException if the option value string has wrong format
	 *         or is empty
	 * @deprecated use {@link #getParser(String, boolean, boolean)}.
	 */
	public EnumeratedStringParser(String validOptionValues, boolean caseSensitive, boolean checkOptionChars) throws IllegalArgumentException {
		if (validOptionValues == null) {
			throw new IllegalArgumentException("EnumeratedStringParser validOptions parameter is null");
		}
		if (validOptionValues.length() == 0) {
			throw new IllegalArgumentException("EnumeratedStringParser validOptions parameter is empty");
		}

		this.isCaseSensitive = caseSensitive;
		this.checkOptionChars = checkOptionChars;
		if (validOptionValues.indexOf(CONSTRUCTOR_VALUE_SEPARATOR) == -1) {
			validOptionValuesArray = new String[1];	// we assume to have only one valid option value
			if (isValidOptionName(validOptionValues)) {
				validOptionValuesArray[0] = validOptionValues;
			}
			else {
				throw new IllegalArgumentException("Wrong character in EnumeratedStringParser option value: "+validOptionValues
					+ "\nsee EnumeratedStringParser javadoc for more information");
			}
		}
		else {
			StringTokenizer stok = new StringTokenizer(validOptionValues, ";");
			validOptionValuesArray = new String[stok.countTokens()];
			int i = 0;
			while (stok.hasMoreTokens()) {
				String value = stok.nextToken().trim();
				if (!isCaseSensitive) {
					value = value.toLowerCase();
				}
				if (isValidOptionName(value)) {
					validOptionValuesArray[i++] = value;
				}
				else {
					throw new IllegalArgumentException("Wrong character in EnumeratedStringParser option value: "+value
						+ "\nsee EnumeratedStringParser javadoc for more information");
				}               
			}
		}

	}

	/**
	 * Returns an EnumeratedParameterParser with parameter "checkOptionChars" set to true.
	 * 
	 * @param validOptionValues a string that contains valid values for an option 
	 *        in the format "value_1;value_2;..;value_n"; spaces between values are allowed 
	 *        to make things more readable, e.g., "value_1; value_2";
	 *        option values have to be constructed using Java identifier characters.
	 * @param caseSensitive tells the parser wether the option value is case sensitive
	 * @throws IllegalArgumentException if the option value string has wrong format
	 *         or is empty
	 */
	public static EnumeratedStringParser getParser(String validOptionValues, boolean caseSensitive) throws IllegalArgumentException {
		return new EnumeratedStringParser( validOptionValues, caseSensitive, true );
	}

	/**
	 * Constructs a new instance of EnumeratedStringParser.
	 * @deprecated use {@link #getParser(String, boolean)}.
	 * 
	 */
	public EnumeratedStringParser(String validOptionValues, boolean caseSensitive) throws IllegalArgumentException {
		this(validOptionValues, caseSensitive, true);
	}


	/**
	 * Returns an EnumeratedParameterParser with parameter
	 * "caseSensitive" set to false and "checkOptionChars" set to true.
	 * All command line arguments for this parser and the values provided
	 * by the user in the returned parser are converted to lower case.
	 * 
	 * @param validOptionValues a string that contains valid values for an option 
	 *        in the format "value_1;value_2;..;value_n"; spaces between values are allowed 
	 *        to make things more readable, e.g., "value_1; value_2";
	 *        option values have to be constructed using Java identifier characters.
	 * @throws IllegalArgumentException if the option value string has wrong format
	 *         or is empty
	 */
	public static EnumeratedStringParser getParser(String validOptionValues) throws IllegalArgumentException {
		return new EnumeratedStringParser( validOptionValues, false, true );
	}


	/**
	 * Constructs a new instance of EnumeratedStringParser.
	 * @deprecated use {@link #getParser(String)}.
	 */
	public EnumeratedStringParser(String validOptionValues) throws IllegalArgumentException {
		this(validOptionValues, false, true);
	}

	/**
	 * Parses the specified argument, making sure it matches one of the valid
	 * options supplied to its constructor.  
	 * If the specified argument is not a valid option, 
	 * a ParseException is thrown.
	 * 
	 * @param arg the argument to parse
	 * @return the String resulting from the parsed argument.
	 * @throws ParseException if the specified argument cannot be parsed.
	 */
	public Object parse(String arg) throws ParseException {
		if (arg == null) {
			return null;
		}
		if (!isCaseSensitive) {
			arg = arg.toLowerCase();
		}
		if (!isValidOptionName(arg)) {
			throw new ParseException("Wrong character in command line option value for enumerated option: '" + arg + "'"
				+"\nallowed are alphanumeric characters + '$' and '_' sign only",
				new IllegalArgumentException());
		}
		// we cannot use Arrays.binarySearch() because strings cannot be 
		// sorted according to the required natural order!
		if (Arrays.asList(validOptionValuesArray).contains(arg)) {
			return arg;
		}
		else {
			throw new ParseException("Option has wrong value '" + arg + "'"
				+ "; valid values are: "+Arrays.asList(validOptionValuesArray), new IllegalArgumentException());
		}
	}

	/**
	 * Check for valid enumerated option values ("names").
	 * Allowed are Java identifier chars, i.e., alphanumeric chars + '$' + _' signs.
	 * If you need a different validation scheme you can override this method
	 * when subclassig EnumeratedStringParser.
	 * 
	 * @param name   the option value to check
	 * 
	 * @return true, if the value contains only valid chars, false otherwise
	 */
	protected boolean isValidOptionName(String name) {
		if (!checkOptionChars) {
			return true;
		}
		for (int i=0; i<name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isJavaIdentifierPart(c)) {
				continue;
			}
			else {
				return false;
			}
		}
		return true;
	}
}
