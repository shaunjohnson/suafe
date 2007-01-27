/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.martiansoftware.jsap.defaultsources.PropertyDefaultSource;

/**
 * Tests JSAP's handling of default values.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestDefaults extends TestCase {

    /**
     * JSAP object created in setUp()
     */
    private JSAP jsap = null;

    /**
     * Returns the tests defined in this class.
     * @return the tests defined in this class.
     */
    public static Test suite() {
        return (new TestSuite(TestDefaults.class));
    }

    /**
     * Ensures that the setUp() method was successful.
     */
    public void testConfig() {
        Switch b = (Switch) jsap.getByID("b");
        assertNotNull(b);
        Switch b2 = (Switch) jsap.getByShortFlag('b');
        assertNotNull(b2);
        assertEquals(b, b2);
    }

    /**
     * Tests the parameter-level defaults.
     */
    public void testSimpleDefault() {
        assertNotNull(jsap);

        String[] args = { "-a", "2a 2b 2c" };
        JSAPResult result = null;

        result = jsap.parse(args);
        assertEquals(true, result.success());

        assertEquals(true, result.getBoolean("a"));
        assertEquals("field1-default", result.getString("field1"));
        assertEquals(false, result.getBoolean("b"));

        // attempt to throw a ClassCastException
        try {
            String s = result.getString("a");
            fail("Switch returned a String [" + s + "]");
        } catch (Exception e) {
            // this is normal
        }
    }

    /**
     * Another test of parameter-level defaults.
     */
    public void testDefaults2() {
        assertNotNull(jsap);
        String[] args = { "-b", "--field1", "HELLO" };
        JSAPResult result = null;
        result = jsap.parse(args);
        assertEquals(true, result.success());
        assertEquals(false, result.getBoolean("a"));
        assertEquals(true, result.getBoolean("b"));
        assertEquals("HELLO", result.getString("field1"));
        assertEquals(0, result.getObjectArray("field2").length);
    }

    /**
     * Tests JSAP's enforcing of required parameters.
     */
    public void testRequired() {
        FlaggedOption requiredOption = new FlaggedOption("required");
        requiredOption.setRequired(true);
        requiredOption.setLongFlag("required");
        try {
            jsap.registerParameter(requiredOption);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        String[] args = { "-b", "--field1", "HELLO" };

        JSAPResult result = null;

        result = jsap.parse(args);
        assertEquals(
            "Required field not provided, but no exception thrown.",
            false,
            result.success());

        String[] args2 =
            { "-b", "--field1", "HELLO", "--required", "requiredinfo" };

        result = jsap.parse(args2);
        assertEquals(true, result.success());
        assertNotNull(result);
        assertEquals(false, result.getBoolean("a"));
        assertEquals(true, result.getBoolean("b"));
        assertEquals("HELLO", result.getString("field1"));
        assertEquals(0, result.getObjectArray("field2").length);
        assertEquals("requiredinfo", result.getString("required"));

        jsap.unregisterParameter(requiredOption);
        requiredOption.setDefault("required_default");
        try {
            jsap.registerParameter(requiredOption);
        } catch (JSAPException e) {
            fail(e.getMessage());
        }
        result = null;
        result = jsap.parse(args);
        assertEquals(true, result.success());
        assertEquals("required_default", result.getString("required"));

    }

    /**
     * Tests a single level of property defaults.
     */
    public void testOneLevelDefaults() {
        try {
            File propertyTest = File.createTempFile("jsap-", ".properties");
            OutputStream out =
                new BufferedOutputStream(new FileOutputStream(propertyTest));
            Properties properties = new Properties();
            properties.setProperty("field1", "FromPropertyFile");
            properties.setProperty("a", "true");
            properties.store(
                out,
                "JUnit test for " + this.getClass().getName());
            out.close();
            PropertyDefaultSource pds =
                new PropertyDefaultSource(propertyTest.getAbsolutePath(), true);

            jsap.registerDefaultSource(pds);

            JSAPResult result = null;
            String[] args = { "-b", "--field1", "HELLO" };
            result = jsap.parse(args);
            assertEquals(true, result.success());
            assertNotNull(result);
            assertEquals("HELLO", result.getString("field1"));

            String[] args2 = { "-b" };
            result = jsap.parse(args2);
            assertEquals(true, result.success());

            assertNotNull(result);
            assertEquals("FromPropertyFile", result.getString("field1"));
            assertEquals(true, result.getBoolean("a"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Configures the JSAP object for the tests.
     * @throws JSAPException if the JSAP object cannot be instantiated.
     */
    public void setUp() throws JSAPException {
        // set up a command line parser for the syntax
        // [-a] [-b] [--field1 field1] [field2 field3 field4]
        jsap = new JSAP();

        Switch a = new Switch("a");
        a.setShortFlag('a');
        jsap.registerParameter(a);

        Switch b = new Switch("b");
        b.setShortFlag('b');
        jsap.registerParameter(b);

        FlaggedOption field1 = new FlaggedOption("field1");
        field1.setLongFlag("field1");
        field1.setDefault("field1-default");
        jsap.registerParameter(field1);

        UnflaggedOption field2 = new UnflaggedOption("field2");
        field2.setGreedy(true);
        jsap.registerParameter(field2);
    }

    /**
     * Cleans up the JSAP object.
     */
    public void tearDown() {
        jsap = null;
    }

}
