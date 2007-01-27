/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.martiansoftware.jsap.stringparsers.StringStringParser;

/**
 * Tests the Option class
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestOption extends TestCase {

    /**
     * Returns a suite of tests defined by this class.
     * @return a suite of tests defined by this class.
     */
    public static Test suite() {
        return (new TestSuite(TestOption.class));
    }

    /**
     * Tests the various setters and getters on an option.
     */
    public void testSettersAndGetters() {
        FlaggedOption option = new FlaggedOption("testOption");

        assertEquals("testOption", option.getID());
        option.setList(true);
        assertEquals(true, option.isList());
        option.setListSeparator('W');
        assertEquals('W', option.getListSeparator());
        option.setAllowMultipleDeclarations(true);
        assertEquals(true, option.allowMultipleDeclarations());
        option.setRequired(true);
        assertEquals(true, option.required());
        assertNull(option.getStringParser());
        StringStringParser sop = StringStringParser.getParser();
        option.setStringParser(sop);
        assertEquals(sop, option.getStringParser());
    }

    /**
     * Tests the ability to parse via the option's parse() method.
     */
    public void testParsing() {
        FlaggedOption option = new FlaggedOption("testOption");

        option.setListSeparator(' ');
        option.setList(true);
        List parseResult = null;

        try {
            parseResult = option.parse("this is a test");
        } catch (JSAPException e) {
            fail(e.getMessage());
        }
        assertEquals(4, parseResult.size());
        assertEquals("this", parseResult.get(0));
        assertEquals("test", parseResult.get(3));
    }
}
