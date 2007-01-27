/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.ant;

/**
 * Stores/provides a single name/value pair for a PropertyStringParser
 * For detailed information on using the jsap task, see the documentation for
 * JSAPAntTask.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see JSAPAntTask
 * @see com.martiansoftware.jsap.PropertyStringParser
 */
public class ParserProperty {

    /**
     * The name of this property.
     */
    private String name = null;

    /**
     * The value of this property.
     */
    private String value = null;

    /**
     * Sets the name of this property.
     * @param name the name of this property.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this property.
     * @return the name of this property.
     */
    public String getName() {
        return (name);
    }

    /**
     * Sets the value of this property.
     * @param value the value of this property.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the value of this property.
     * @return the value of this property.
     */
    public String getValue() {
        return (value);
    }

}
