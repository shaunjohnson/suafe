/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.defaultsources;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestAll {

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.martiansoftware.jsap.defaultsources");
        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(TestPropertyDefaultSource.class));
        //$JUnit-END$
        return suite;
    }
}
