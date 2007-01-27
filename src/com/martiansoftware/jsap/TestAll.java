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
 * Runs all of the JSAP tests, including those in sub-packages.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestAll extends TestCase {

    /**
     * Returns a collection of all the JSAP tests, including those in
     * subpackages.
     * @return a collection of all the JSAP tests, including those in
     * subpackages.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("All JUnit Tests");
        suite.addTest(TestSwitch.suite());
        suite.addTest(TestOption.suite());
        suite.addTest(TestFlaggedOption.suite());
        suite.addTest(TestJSAPConfiguration.suite());
        suite.addTest(TestParser.suite());
        suite.addTest(TestDefaults.suite());
        suite.addTest(TestCommandLineTokenizer.suite());
        suite.addTest(TestUsageString.suite());
        suite.addTest(com.martiansoftware.jsap.stringparsers.TestAll.suite());
        suite.addTest(com.martiansoftware.jsap.defaultsources.TestAll.suite());
        return (suite);
    }

}
