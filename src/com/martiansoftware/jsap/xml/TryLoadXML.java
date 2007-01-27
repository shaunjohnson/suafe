/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import com.martiansoftware.jsap.JSAP;

/**
 * Loads a silly example JSAP from xml and displays its help message
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TryLoadXML {

	public static void main(String[] args) throws Exception {
		JSAP jsap = new JSAP("com/martiansoftware/jsap/xml/silly-example.xml");
		System.out.println(jsap.getHelp());
	}
}
