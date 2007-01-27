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
 * Tests the ability to automatically create usage information.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestUsageString extends TestCase {

    /**
     * Creates a new TestCase with the specified name.
     * @param arg0 the name for this TestCase.
     */
    public TestUsageString(String arg0) {
        super(arg0);
    }

    /**
     * Returns a suite of tests defined by this class.
     * @return a suite of tests defined by this class.
     */
    public static Test suite() {
        return (new TestSuite(TestUsageString.class));
    }

    /**
     * Tests usage info for a single option, both required and not required.
     */
    public void testUsage1() {
        JSAP config = new JSAP();

        FlaggedOption opt1 = new FlaggedOption("flaggedOption");
        opt1.setShortFlag('f');
        opt1.setLongFlag("flagged");
        opt1.setRequired(JSAP.REQUIRED);
        try {
            config.registerParameter(opt1);
        } catch (JSAPException e) {
            fail("Unable to register opt1");
        }
        assertEquals("(-f|--flagged)" + JSAP.SYNTAX_SPACECHAR + "<flaggedOption>", config.getUsage());

        config.unregisterParameter(opt1);
        opt1.setRequired(JSAP.NOT_REQUIRED);
        try {
            config.registerParameter(opt1);
        } catch (JSAPException e) {
            fail("Unable to register opt1");
        }
        assertEquals("[(-f|--flagged)" + JSAP.SYNTAX_SPACECHAR + "<flaggedOption>]", config.getUsage());
    }

    /**
     * Tests usage info for a Switch.
     */
    public void testUsage2() {
        JSAP config = new JSAP();
        Switch sw = new Switch("testSwitch");
        sw.setShortFlag('s');
        sw.setLongFlag("switch");
        try {
            config.registerParameter(sw);
        } catch (JSAPException e) {
            fail("Unable to register sw");
        }
        assertEquals("[-s|--switch]", config.getUsage());
    }
}
