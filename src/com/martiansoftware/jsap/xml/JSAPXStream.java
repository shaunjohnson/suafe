/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Provides support for loading JSAP configurations at runtime
 * via an xml file.  You don't need to access this class directly;
 * instead, use JSAP's constructors that support xml.
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
class JSAPXStream extends XStream {

	public JSAPXStream() {
		super(new DomDriver());
		alias("jsap", JSAPConfig.class);
		alias("flaggedOption", FlaggedOptionConfig.class);
		alias("unflaggedOption", UnflaggedOptionConfig.class);
		alias("property", Property.class);
		alias("qualifiedSwitch", QualifiedSwitchConfig.class);
		alias("switch", SwitchConfig.class);
	}

}
