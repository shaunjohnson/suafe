/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import java.util.Properties;

/**
 * A {@link com.martiansoftware.jsap.StringParser} subclass that provides a means for setting/getting properties.
 * This
 * is intended to support StringParsers that might requires some configuration,
 * such
 * as DateStringParser (which needs a format String).
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.StringParser
 * @see com.martiansoftware.jsap.stringparsers.DateStringParser
 */
public abstract class PropertyStringParser extends StringParser {

    /**
     * This PropertyStringParser's properties.
     */
    private Properties properties = null;

    /**
     * Replaces all properties in this PropertyStringParser with the
     * specified Properties.
     * @param properties the new properties for this PropertyStringParser.
     */
    private void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Returns the internal Properties object.  If none is currently defined, a
     * new Properties object is created.
     * @return the internal Properties object.  If none is currently defined, a
     * new Properties object is created.
     */
    private Properties getProperties() {
        if (properties == null) {
            setProperties(new Properties());
        }
        return (properties);
    }

    /**
     * Sets the property with the specified key to the specified value.
     * @param key the key of the property to set.
     * @param value the value to associated with the specified key.
     * @see java.util.Properties#setProperty(String,String)
     */
    public void setProperty(String key, String value) {
        Properties properties = getProperties();
        properties.setProperty(key, value);
    }

    /**
     * Returns the property associated with the specified key, or null if no
     * such
     * property exists.
     * @param key the key of the desired property
     * @return the property associated with the specified key, or null if no
     * such
     * property exists.
     */
    public String getProperty(String key) {
        return (getProperties().getProperty(key));
    }
    
    /**
     * Returns the property associated with the specified key, or the specified
     * default value if no such property exists.
     * @param key the key of the desired property
     * @param defaultValue the default value to return if no such property exists
     * @return the requested property, or the specified default value if no such property exists.
     */
    public String getProperty(String key, String defaultValue) {
    	String result = getProperty(key);
    	return (result == null ? defaultValue : result);
    }

}
