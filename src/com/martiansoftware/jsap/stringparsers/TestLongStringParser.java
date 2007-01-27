/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import com.martiansoftware.jsap.JSAPException;

import junit.framework.TestCase;
/**
 * A series of tests for the LongStringParser
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.stringparsers.ColorStringParser
 */
public class TestLongStringParser extends TestCase {

    /**
     * Constructor for TestLongStringParser.
     * @param arg0 name of this test
     */
    public TestLongStringParser(String arg0) {
        super(arg0);
    }

    /**
     * Tests the ability to parse tuples of integers representing RGB values.
     */
    public void testBasicParse() {

        LongStringParser lsp = LongStringParser.getParser();

        assertEquals(456, Long.decode("456").longValue());
        try {
            Long result = (Long) lsp.parse("123");
            assertEquals(123, result.longValue());
        } catch (JSAPException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}
