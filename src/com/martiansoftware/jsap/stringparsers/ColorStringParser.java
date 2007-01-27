/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.ParseException;
import com.martiansoftware.jsap.StringParser;

import java.awt.Color;
import java.util.StringTokenizer;

/**
 * A {@link com.martiansoftware.jsap.StringParser} for parsing java.awt.Color objects.  Color information can be
 * specified in a variety
 * of formats:
 * 
 * <ul>
 * <li>RGB, as integers in the range 0-255, separated by commas
 * (e.g., "123,45,6")</li>
 * <li>RGB, as floats in the range 0.0-1.0, separated by commas
 * (e.g., "0.123,0.45,0.6")</li>
 * <li>RGB, as hexadecimal strings following the '#' character
 * (e.g., "#1234ef")</li>
 * <li>By name, as matching the names of the color fields of java.awt.Color
 * (case-insensitive).  (e.g., "black")</li>
 * <li>RGBAlpha, as integers in the range 0-255, separated by commas
 * (e.g., "123,45,6,128")</li>
 * <li>RGBAlpha, as floats in the range 0.0-1.0, separated by commas
 * (e.g., "0.123,0.45,0.6,.5")</li>
 * <li>RGBAlpha, as hexadecimal strings following the '#' character
 * (e.g., "#1234efab")</li>
 * </ul>
 *
 * If the specified argument does not match any of these formats, a
 * ParseException is thrown.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see java.awt.Color
 */
public class ColorStringParser extends StringParser {

	private static final ColorStringParser INSTANCE = new ColorStringParser();	

	/** Returns a {@link ColorStringParser}.
	 * 
	 * <p>Convenient access to the only instance returned by
	 * this method is available through
	 * {@link com.martiansoftware.jsap.JSAP#COLOR_PARSER}.
	 *  
	 * @return a {@link ColorStringParser}.
	 */

	public static ColorStringParser getParser() {
		return INSTANCE;
	}

	/**
     * Creates a new ColorStringParser.
     * @deprecated Use {@link #getParser()} or, even better, {@link com.martiansoftware.jsap.JSAP#COLOR_PARSER}.
     */
    public ColorStringParser() {
        super();
    }

    /**
     * Simple utility for creating ParseExceptions, as there are so many
     * opportunities to throw them.
     * @param s the ParseException's message.
     * @return a new ParseException with the specified message.
     */
    private ParseException colorException(String s) {
        return (
            new ParseException("Unable to convert '" + s + "' to a Color."));
    }

    /**
     * Parses a hexadecimal String into a Color.
     * @param hexString the hexadecimal String to parse.
     * @return a Color object based upon the specified color string.
     * @throws ParseException if the specified String cannot be interpreted as
     * a Color.
     */
    private Color parseHexColor(String hexString) throws ParseException {
        Color result = null;
        int hexLength = hexString.length();
        if ((hexLength != 7) && (hexLength != 9)) {
            throw (colorException(hexString));
        }
        try {
            int red = Integer.parseInt(hexString.substring(1, 3), 16);
            int green = Integer.parseInt(hexString.substring(3, 5), 16);
            int blue = Integer.parseInt(hexString.substring(5, 7), 16);
            if (hexLength == 7) {
                result = new Color(red, green, blue);
            } else {
                int alpha = Integer.parseInt(hexString.substring(7, 9), 16);
                result = new Color(red, green, blue, alpha);
            }
        } catch (NumberFormatException e) {
            throw (colorException(hexString));
        }
        return (result);
    }

    /**
     * Parses a 3- or 4-tuple of comma-separated floats into a Color.
     * @param floatString the String to parse.
     * @return a Color object based upon the specified String.
     * @throws ParseException if the specified String cannot be interpreted as
     * a Color.
     */
    private Color parseFloatTuple(String floatString) throws ParseException {
        String[] tuple = tupleToArray(floatString);
        Color result = null;
        try {
            float red = Float.parseFloat(tuple[0]);
            float green = Float.parseFloat(tuple[1]);
            float blue = Float.parseFloat(tuple[2]);
            if (tuple.length == 3) {
                result = new Color(red, green, blue);
            } else {
                float alpha = Float.parseFloat(tuple[3]);
                result = new Color(red, green, blue, alpha);
            }
        } catch (NumberFormatException e) {
            throw (colorException(floatString));
        }
        return (result);
    }

    /**
     * Parses a 3- or 4-tuple of comma-separated integers into a Color.
     * @param intString the String to parse.
     * @return a Color object based upon the specified color String.
     * @throws ParseException if the specified String cannot be interpreted as
     * a Color.
     */
    private Color parseIntTuple(String intString) throws ParseException {
        String[] tuple = tupleToArray(intString);
        Color result = null;
        try {
            int red = Integer.parseInt(tuple[0]);
            int green = Integer.parseInt(tuple[1]);
            int blue = Integer.parseInt(tuple[2]);
            if (tuple.length == 3) {
                result = new Color(red, green, blue);
            } else {
                int alpha = Integer.parseInt(tuple[3]);
                result = new Color(red, green, blue, alpha);
            }
        } catch (NumberFormatException e) {
            throw (colorException(intString));
        }
        return (result);
    }

    /**
     * Parses a String into a Color by name (names lifted from java.awt.Color).
     * @param colorName the name of the desired Color.
     * @return a Color object based upon the specified color name.
     * @throws ParseException if the specified String is not a valid color name.
     */
    private Color parseColorName(String colorName) throws ParseException {
        Color result = null;
        if (colorName.equalsIgnoreCase("black")) {
            result = Color.black;
        } else if (colorName.equalsIgnoreCase("blue")) {
            result = Color.blue;
        } else if (colorName.equalsIgnoreCase("cyan")) {
            result = Color.cyan;
        } else if (colorName.equalsIgnoreCase("gray")) {
            result = Color.gray;
        } else if (colorName.equalsIgnoreCase("green")) {
            result = Color.green;
        } else if (colorName.equalsIgnoreCase("lightGray")) {
            result = Color.lightGray;
        } else if (colorName.equalsIgnoreCase("magenta")) {
            result = Color.magenta;
        } else if (colorName.equalsIgnoreCase("orange")) {
            result = Color.orange;
        } else if (colorName.equalsIgnoreCase("pink")) {
            result = Color.pink;
        } else if (colorName.equalsIgnoreCase("red")) {
            result = Color.red;
        } else if (colorName.equalsIgnoreCase("white")) {
            result = Color.white;
        } else if (colorName.equalsIgnoreCase("yellow")) {
            result = Color.yellow;
        } else {
            throw (colorException(colorName));
        }
        return (result);
    }

    /**
     * Parses a 3- or 4-tuple, comma separated, to an array.
     * @param s the String to be parsed to an array.
     * @return the parsed String as a 3- or 4-element array of Strings.
     * @throws ParseException if the specified String contains fewer than 3 or
     * more than 4 elements.
     */
    private String[] tupleToArray(String s) throws ParseException {
        String[] result = null;
        StringTokenizer tokenizer = new StringTokenizer(s, ",", true);
        int tokenCount = tokenizer.countTokens();
        if (tokenCount == 5) {
            result = new String[3];
        } else if (tokenCount == 7) {
            result = new String[4];
        } else {
            throw (colorException(s));
        }
        result[0] = tokenizer.nextToken();
        tokenizer.nextToken();
        result[1] = tokenizer.nextToken();
        tokenizer.nextToken();
        result[2] = tokenizer.nextToken();
        if (tokenCount == 7) {
            tokenizer.nextToken();
            result[3] = tokenizer.nextToken();
        }
        return (result);
    }

    /**
     * Parses java.awt.Color objects from Strings.  Color information can be
     * specified in a variety
     * of formats:
     * 
     * <ul>
     * <li>RGB, as integers in the range 0-255, separated by commas
     * (e.g., "123,45,6")</li>
     * <li>RGB, as floats in the range 0.0-1.0, separated by commas
     * (e.g., "0.123,0.45,0.6")</li>
     * <li>RGB, as hexadecimal strings following the '#' character
     * (e.g., "#1234ef")</li>
     * <li>By name, as matching the names of the color fields of java.awt.Color
     * (case-insensitive).
     * (e.g., "black")</li>
     * <li>RGBAlpha, as integers in the range 0-255, separated by commas
     * (e.g., "123,45,6,128")</li>
     * <li>RGBAlpha, as floats in the range 0.0-1.0, separated by commas
     * (e.g., "0.123,0.45,0.6,.5")</li>
     * <li>RGBAlpha, as hexadecimal strings following the '#' character
     * (e.g., "#1234efab")</li>
     * </ul>
     *
     * If the specified argument does not match any of these formats, a
     * ParseException is thrown.
     * @param arg the String to convert to a Color object.
     * @return the Color specified by arg.
     * @throws ParseException if arg cannot be interpreted as a Color as
     * described above.
     */
    public Object parse(String arg) throws ParseException {
        Color result = null;
        if (arg.charAt(0) == '#') {
            result = parseHexColor(arg);
        } else if (arg.indexOf(".") >= 0) {
            result = parseFloatTuple(arg);
        } else if (arg.indexOf(",") >= 0) {
            result = parseIntTuple(arg);
        } else {
            result = parseColorName(arg);
        }
        return (result);
    }

}
