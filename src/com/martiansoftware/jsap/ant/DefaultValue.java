/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.ant;

/**
 * Provides a means of specifying default values for the jsap task containing
 * &lt;default&gt; elements.
 * For detailed information on using the jsap task, see the documentation for
 * JSAPAntTask.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see JSAPAntTask
 */
public class DefaultValue {

    /**
     * The default value.
     */
    private String value = null;

    /**
     * Sets the default value.
     * @param text the default value.
     */
    public void addText(String text) {
        this.value = text;
    }

    /**
     * Returns the default value.
     * @return the default value.
     */
    public String getValue() {
        return (value);
    }
}
