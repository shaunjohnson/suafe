/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import com.martiansoftware.jsap.JSAP;

/**
 * Provides support for loading JSAP configurations at runtime
 * via an xml file.  You don't need to access this class directly;
 * instead, use JSAP's constructors that support xml.
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
abstract class FlaggedConfig extends ParameterConfig {
	
	private char shortFlag = JSAP.NO_SHORTFLAG;
	private String longFlag = JSAP.NO_LONGFLAG;
	
	public String getLongFlag() {
		return longFlag;
	}
	
	public void setLongFlag(String longFlag) {
		this.longFlag = longFlag;
	}

	public char getShortFlag() {
		return shortFlag;
	}

	public void setShortFlag(char shortFlag) {
		this.shortFlag = shortFlag;
	}

	protected void configure(com.martiansoftware.jsap.FlaggedOption option) {
		super.configure(option);
	}
}
