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
 * This junit TestCase runs a barrage of tests against the CommandLineTokenizer.
 * This TestCase is unusual for this project in that it contains a main()
 * method;
 * this method is used to generate test methods to paste into this class.
 * @see CommandLineTokenizer
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TestCommandLineTokenizer extends TestCase {

    /**
     * Creates and returns a new Test based upon this class.
     * @return a new Test based upon this class.
     */
    public static Test suite() {
        return (new TestSuite(TestCommandLineTokenizer.class));
    }

    /**
     * Tests for correct parsing of [this is a test].
     */
    public void test1() {
        String cmdLine = "this is a test";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);

        assertEquals(4, tokens.length);

        assertEquals("this", tokens[0]);
        assertEquals("is", tokens[1]);
        assertEquals("a", tokens[2]);
        assertEquals("test", tokens[3]);
    }

    /**
     * Tests for corect parsing of [this is a "test"]
     */
    public void test2() {
        String cmdLine = "";
        String[] tokens = CommandLineTokenizer.tokenize("this is a \"test\"");

        assertEquals(4, tokens.length);

        assertEquals("this", tokens[0]);
        assertEquals("is", tokens[1]);
        assertEquals("a", tokens[2]);
        assertEquals("test", tokens[3]);
    }

    /**
     * Tests for correct parsing of ["this is a test"]
     */
    public void test3() {
        String cmdLine = "\"this is a test\"";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);

        assertEquals(1, tokens.length);

        assertEquals("this is a test", tokens[0]);
    }

    /**
     * Tests for correct parsing of [this is a "test]
     */
    public void test4() {
        String cmdLine = "this is a \"test";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);

        assertEquals(4, tokens.length);

        assertEquals("this", tokens[0]);
        assertEquals("is", tokens[1]);
        assertEquals("a", tokens[2]);
        assertEquals("test", tokens[3]);
    }

    /**
     * Tests for correct parsing of [thi\s is a \"test]
     */
    public void test5() {
        String cmdLine = "thi\\s is a \\\"test";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);

        assertEquals(4, tokens.length);

        assertEquals("thi\\s", tokens[0]);
        assertEquals("is", tokens[1]);
        assertEquals("a", tokens[2]);
        assertEquals("\"test", tokens[3]);
    }

    /**
     * Tests for correct parsing of [thi\s is a \"test\\]
     */
    public void test6() {
        String cmdLine = "thi\\s is a \\\"test\\";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);

        assertEquals(4, tokens.length);

        assertEquals("thi\\s", tokens[0]);
        assertEquals("is", tokens[1]);
        assertEquals("a", tokens[2]);
        assertEquals("\"test\\", tokens[3]);
    }

    /**
     * Tests for correct parsing of [thi\s is a \"test\\\"]
     */
    public void test7() {
        String cmdLine = "thi\\s is a \\\"test\\\\\"";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);

        assertEquals(4, tokens.length);

        assertEquals("thi\\s", tokens[0]);
        assertEquals("is", tokens[1]);
        assertEquals("a", tokens[2]);
        assertEquals("\"test\\", tokens[3]);
    }

    /**
     * Tests for correct parsing of a null command line.
     */
    public void test8() {
        String[] tokens = CommandLineTokenizer.tokenize(null);
        assertEquals(0, tokens.length);
    }

    /**
     * Tests for correct parsing of a whitespace-only command line.
     */
    public void test9() {
        String cmdLine = "   \t\t  \t ";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);
        assertEquals(0, tokens.length);
    }

    /**
     * Tests for correct parsing of ["this is a test]
     */
    public void test10() {
        String cmdLine = "\"this is a test";
        String[] tokens = CommandLineTokenizer.tokenize(cmdLine);

        assertEquals(1, tokens.length);

        assertEquals("this is a test", tokens[0]);
    }

    /**
     * A helper method to write additional test cases based upon the specified
     * argument array.
     * @param args the argument array that CommandLineTokenizer.tokenize()
     * should produce.
     */
    public static void main(String[] args) {

        System.out.println("public void testXXX(){");
        System.out.println("\tString cmdLine = \"\";");
        System.out.println(
            "\tString[] tokens = CommandLineTokenizer.tokenize(cmdLine);");
        System.out.println("\n");
        System.out.println(
            "\tassertEquals(" + args.length + ", tokens.length);\n");
        for (int i = 0; i < args.length; ++i) {
            System.out.println(
                "\tassertEquals(\"" + args[i] + "\", tokens[" + i + "]);");
        }
        System.out.println("}");
    }

}
