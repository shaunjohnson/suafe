/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import junit.framework.TestCase;
import java.awt.Color;
import com.martiansoftware.jsap.JSAPException;

/**
 * A series of tests for the ColorStringParser
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.stringparsers.ColorStringParser
 */
public class TestColorStringParser extends TestCase {

    /**
     * Constructor for TestColorStringParser.
     * @param arg0 name of this test
     */
    public TestColorStringParser(String arg0) {
        super(arg0);
    }

    /**
     * Tests the ability to parse tuples of integers representing RGB values.
     */
    public void testIntegerRGB() {

        Color c = new Color(12, 34, 56);
        ColorStringParser parser = ColorStringParser.getParser();

        Color result = null;
        try {
            result = (Color) parser.parse("12,34,56");
        } catch (JSAPException e) {
            fail("12,34,56");
        }
        assertEquals(c, result);

        try {
            result = (Color) parser.parse("12,34,56,");
            fail("12,34,56,");
        } catch (JSAPException e) {
            // this is normal behavior
        }

        try {
            result = (Color) parser.parse("12,3b,56");
            fail("12,3b,56");
        } catch (JSAPException e) {
            // this is normal behavior
        }

    }

    /**
     * Tests the ability to parse tuples of floats representing RGB values.
     */
    public void testFloatRGB() {
        Color c = new Color(0.12f, 0.34f, 0.56f);
        ColorStringParser parser = ColorStringParser.getParser();

        Color result = null;
        try {
            result = (Color) parser.parse("0.12,.34,00.56");
        } catch (JSAPException e) {
            fail("0.12,.34,00.56");
        }
        assertEquals(c, result);

        try {
            result = (Color) parser.parse("0.12,.34");
            fail("0.12,.34");
        } catch (JSAPException e) {
            // this is normal behavior
        }
    }

    /**
     * Tests the ability to parse hexadecimal strings as RGB values.
     */
    public void testHexRGB() {
        Color c = new Color(255, 255, 255);
        ColorStringParser parser = ColorStringParser.getParser();

        Color result = null;
        try {
            result = (Color) parser.parse("#fFFfFF");
        } catch (JSAPException e) {
            fail("#fFFfFF");
        }
        assertEquals(c, result);
    }

    /**
     * Tests the ability to parse color names as in java.awt.Color fields.
     */
    public void testByName() {
        Color c = new Color(255, 255, 255);
        ColorStringParser parser = ColorStringParser.getParser();

        Color result = null;
        try {
            result = (Color) parser.parse("white");
        } catch (JSAPException e) {
            fail("white");
        }
        assertEquals(c, result);

        try {
            result = (Color) parser.parse("offwhite");
            fail("offwhite");
        } catch (JSAPException e) {
            // this is normal behavior
        }

    }

}
