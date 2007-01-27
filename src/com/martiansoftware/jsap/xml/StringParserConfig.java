/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */
package com.martiansoftware.jsap.xml;

import java.util.Iterator;
import java.util.List;

import com.martiansoftware.jsap.PropertyStringParser;
import com.martiansoftware.jsap.StringParser;

/**
 * Provides support for loading JSAP configurations at runtime
 * via an xml file.  You don't need to access this class directly;
 * instead, use JSAP's constructors that support xml.
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
class StringParserConfig {
	
	String classname = null;
	List properties = null;

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public List getProperties() {
		return properties;
	}

	public void setProperties(List properties) {
		this.properties = properties;
	}

	public StringParser getConfiguredStringParser() {
		try {
			StringParser result = null;
			if (classname.indexOf('.') >= 0) {
				result = (StringParser) Class.forName(classname).newInstance();
			} else {
				result = (StringParser) Class.forName("com.martiansoftware.jsap.stringparsers." + classname).newInstance();
			}
			if ((properties != null) && (properties.size() > 0)) {
				PropertyStringParser p = (PropertyStringParser) result;
				for (Iterator i = properties.iterator(); i.hasNext(); ) {
					Property property = (Property) i.next();
					p.setProperty(property.getName(), property.getValue());
				}
			}
			return (result);
		} catch (Throwable t) {
			throw (new RuntimeException("Unable to create StringParser " + classname + ": " + t.getMessage(), t));
		}
	}

}
