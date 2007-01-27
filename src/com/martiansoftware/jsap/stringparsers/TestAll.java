/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.stringparsers;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Contains all of the tests in the stringparsers package.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestAll {

    /**
     * Returns a suite of all tests in the stringparsers package.
     * @return a suite of all tests in the stringparsers package.
     */
    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.martiansoftware.jsap.stringparsers");
        suite.addTest(new TestSuite(TestColorStringParser.class));
        suite.addTest(new TestSuite(TestLongStringParser.class));
        return suite;
    }
}
