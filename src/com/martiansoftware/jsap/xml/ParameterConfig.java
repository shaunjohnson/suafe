/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import com.martiansoftware.jsap.Parameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides support for loading JSAP configurations at runtime
 * via an xml file.  You don't need to access this class directly;
 * instead, use JSAP's constructors that support xml.
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
abstract class ParameterConfig {

	private String id = null;
	private String help = null;
	private String usageName = null;
	private java.util.List defaults = null;
	
	public ParameterConfig() {
		defaults = new ArrayList();
	}
	
	public void addDefault(String defaultValue) {
		getDefaults().add(defaultValue);
	}
	
	public List getDefaults() {
		if (defaults == null) {
			defaults = new ArrayList();
		}
		return (defaults);
	}
	
	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsageName() {
		return usageName;
	}

	public void setUsageName(String usageName) {
		this.usageName = usageName;
	}
	
	protected void configure(Parameter param) {
		param.setHelp(getHelp());
		for (Iterator i = getDefaults().iterator(); i.hasNext();) {
			String def = (String) i.next();
			param.addDefault(def);
		}
	}
	public abstract Parameter getConfiguredParameter();
}
