/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.defaultsources;

import junit.framework.TestCase;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.UnflaggedOption;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.stringparsers.StringStringParser;
import java.util.Properties;

/**
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestPropertyDefaultSource extends TestCase {

    public void testUnexpectedProperties() {
        JSAP jsap = new JSAP();
        try {
            jsap.registerParameter(
                new FlaggedOption(
                    "testflagged",
                    StringStringParser.getParser(),
                    JSAP.NO_DEFAULT,
                    JSAP.NOT_REQUIRED,
                    'f',
                    "flagged"));
            jsap.registerParameter(
                new UnflaggedOption(
                    "testunflagged",
                    StringStringParser.getParser(),
                    JSAP.NO_DEFAULT,
                    JSAP.NOT_REQUIRED,
                    JSAP.NOT_GREEDY));
            jsap.registerParameter(new Switch("testswitch", 's', "switch"));
        } catch (Throwable t) {
            fail(t.getMessage());
        }

        Properties p = new Properties();
        p.setProperty("s", "true");
        p.setProperty("flagged", "My Flagged Value");
        PropertyDefaultSource pds = new PropertyDefaultSource(p);
        jsap.registerDefaultSource(pds);

        JSAPResult result = jsap.parse("");
        assertTrue(result.success());

        assertEquals(result.getBoolean("testswitch"), true);
        assertEquals(result.getString("testflagged"), "My Flagged Value");

        p.setProperty("unexpected", "jsap won't know what to do with this");
        result = jsap.parse("");
        assertFalse(result.success());

        /*
        for (java.util.Iterator i1 = result.getBadParameterIDIterator(); i1.hasNext(); ) {
            String badID = (String) i1.next();
            for (java.util.Iterator i2 = result.getExceptionIterator(badID); i2.hasNext(); ) {
                Exception e = (Exception) i2.next();
                System.out.println(e.getMessage());
            }
        }*/

    }

}
