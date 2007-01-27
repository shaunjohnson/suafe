/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import java.util.ArrayList;

/**
 * Sends a JSAPConfig to System.out as xml (for test purposes)
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class TryDumpXML {

	public static void main(String[] args) throws Exception {
		JSAPXStream jsx = new JSAPXStream();
		JSAPConfig jc = new JSAPConfig();
		
		FlaggedOptionConfig foc = new FlaggedOptionConfig();
		foc.setId("flagged");
		foc.setShortFlag('f');
		foc.setHelp("This flag does something, but I'm not sure what.");
		jc.addParameter(foc);
		
		UnflaggedOptionConfig uoc = new UnflaggedOptionConfig();
		uoc.setId("unflagged");
		uoc.setGreedy(true);
		StringParserConfig spc = new StringParserConfig();
		spc.setClassname("DateStringParser");
		ArrayList props = new java.util.ArrayList();
		Property p = new Property();
		p.setName("DateFormat");
		p.setValue("MM/dd/yyyy");
		props.add(p);
		p = new Property();
		p.setName("Another Property");
		p.setValue("123");
		props.add(p);
		spc.setProperties(props);
		uoc.setStringParser(spc);
		
		jc.addParameter(uoc);
		System.out.println(jsx.toXML(jc));
		
//		JSAP jsap = jc.getJSAP();
//		JSAPResult result = jsap.parse("-f abc");
//		System.out.println(result.getString("flagged"));
	}
}
