/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.examples;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;

/**
 * @author mlamb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Example1 {

    public static void main(String[] args) throws Exception {
        JSAP jsap = new JSAP();

        FlaggedOption f1 = new FlaggedOption("myflagged");
        f1.setShortFlag('f').setLongFlag("flagged").setRequired(true).setHelp(
            "do flagged stuff");
        jsap.registerParameter(f1);

        UnflaggedOption f2 = new UnflaggedOption("myunflagged");
        f2.setGreedy(JSAP.GREEDY).setHelp("input files");
        jsap.registerParameter(f2);

        Switch sw1 = new Switch("myswitch");
        sw1.setLongFlag("verbose").setHelp(
            "display extra logging information.");
        jsap.registerParameter(sw1);

        System.out.println("Usage: Example1 " + jsap.getUsage());
        System.out.println(jsap.getHelp());
    }
}
