/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.UnflaggedOption;
import com.martiansoftware.jsap.JSAP;

/**
 * Provides support for loading JSAP configurations at runtime
 * via an xml file.  You don't need to access this class directly;
 * instead, use JSAP's constructors that support xml.
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
class UnflaggedOptionConfig extends ParameterConfig {
	
	private boolean greedy = JSAP.NOT_GREEDY;
	private boolean list = JSAP.NOT_LIST;
	private char listSeparator = JSAP.DEFAULT_LISTSEPARATOR;
	private StringParserConfig stringParser = null;
	private boolean required = JSAP.NOT_REQUIRED;
	private String usageName = null;
	
	public UnflaggedOptionConfig() {
		super();
	}

	public boolean isGreedy() {
		return greedy;
	}

	public void setGreedy(boolean greedy) {
		this.greedy = greedy;
	}

	public boolean isList() {
		return list;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	public char getListSeparator() {
		return listSeparator;
	}

	public void setListSeparator(char listSeparator) {
		this.listSeparator = listSeparator;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public StringParserConfig getStringParser() {
		return stringParser;
	}

	public void setStringParser(StringParserConfig stringParser) {
		this.stringParser = stringParser;
	}
	
	public String getUsageName() {
		return usageName;
	}

	public void setUsageName(String usageName) {
		this.usageName = usageName;
	}
	
	public Parameter getConfiguredParameter() {
		UnflaggedOption result = new UnflaggedOption(getId());
		super.configure(result);
		result.setGreedy(isGreedy());
		result.setListSeparator(getListSeparator());
		result.setList(isList());
		result.setRequired(isRequired());
		result.setUsageName(getUsageName());

		if (stringParser != null) {
			result.setStringParser(stringParser.getConfiguredStringParser());
		}
		
		return (result);
	}
}
