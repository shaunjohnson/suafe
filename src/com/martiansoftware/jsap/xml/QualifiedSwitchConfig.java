/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.QualifiedSwitch;

/**
 * Provides support for loading JSAP configurations at runtime
 * via an xml file.  You don't need to access this class directly;
 * instead, use JSAP's constructors that support xml.
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
class QualifiedSwitchConfig extends FlaggedOptionConfig {

	protected void configure(QualifiedSwitch qs) {
		super.configure(qs);
	}

	public Parameter getConfiguredParameter() {
		QualifiedSwitch result = new QualifiedSwitch(getId());
		configure(result);
		return (result);
	}
}
