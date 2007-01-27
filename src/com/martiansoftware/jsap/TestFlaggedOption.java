/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestFlaggedOption extends TestCase {

    /**
     * Creates a TestCase with the specified name.
     * @param name the name for this TestCase.
     */
    public TestFlaggedOption(String name) {
        super(name);
    }

    /**
     * Returns a suite of all tests defined in this class.
     * @return a suite of all tests defined in this class.
     */
    public static Test suite() {
        return (new TestSuite(TestFlaggedOption.class));
    }

    /**
     * Tests the ability to set/get short flags.
     */
    public void testShortFlag() {
        FlaggedOption option = new FlaggedOption("testOption");
        option.setShortFlag('x');
        assertEquals('x', option.getShortFlag());
        assertEquals(new Character('x'), option.getShortFlagCharacter());
    }

    /**
     * Tests the ability to set/get long flags.
     */
    public void testLongFlag() {
        FlaggedOption option = new FlaggedOption("testOption");
        option.setLongFlag("test");
        assertEquals("test", option.getLongFlag());
    }

}
