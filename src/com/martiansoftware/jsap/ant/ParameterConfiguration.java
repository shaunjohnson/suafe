/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.ant;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import com.martiansoftware.jsap.Parameter;

/**
 * Stores/provides configuration data common to switches, flaggedoptions, and
 * unflaggedoptions
 * nested inside a jsap ant task.
 * For detailed information on using the jsap task, see the documentation for
 * JSAPAntTask.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see JSAPAntTask
 */
public abstract class ParameterConfiguration {

    /**
     * The unique ID for this parameter.
     */
    private String id = null;

    /**
     * The set of default values for this parameter.
     */
    private Vector defaults = null;

    /**
     * Creates a new ParameterConfiguration.
     */
    public ParameterConfiguration() {
        defaults = new Vector();
    }

    /**
     * Returns an Parameter (String, FlaggedOption, or UnflaggedOption)
     * configured according
     * to the settings contained within this object.
     * @return an Parameter (String, FlaggedOption, or UnflaggedOption)
     * configured according
     * to the settings contained within this object.
     */
    public abstract Parameter getParameter();

    /**
     * Sets the unique ID for this parameter.
     * @param id ths unique ID for this parameter.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the unique ID for this parameter.
     * @return the unique ID for this parameter.
     */
    public String getId() {
        return (id);
    }

    /**
     * Adds a default value to the current list of default values for this
     * parameter.
     * @param defaultValue the default value to add to the current list of
     * default values for this parameter.
     */
    public void setDefault(String defaultValue) {
        defaults.add(defaultValue);
    }

    /**
     * Adds a configured default value to the current list of default values
     * for this parameter.
     * @param defaultValue the configured default value to add to the current
     * list of default
     * values for this parameter.
     */
    public void addConfiguredDefault(DefaultValue defaultValue) {
        defaults.add(defaultValue.getValue());
    }

    /**
     * Returns an array of this parameter's default values, or a zero-length
     * array if none exist.
     * @return an array of this parameter's default values, or a zero-length
     * array if none exist.
     */
    public String[] getDefaults() {
        return (
            (defaults.size() == 0)
                ? null
                : ((String[]) defaults.toArray(new String[0])));
    }

    /**
     * Creates java source code statements to configure an Parameter as
     * specified in this object.
     * @param objName the name of the object in the java source code
     * @param out the PrintStream to which the source code should be written
     */
    protected void createParentStatements(String objName, PrintStream out) {

        String[] defaults = getDefaults();
        if (defaults != null) {
            for (int i = 0; i < defaults.length; ++i) {
                out.println(
                    "        "
                        + objName
                        + ".addDefault(\""
                        + defaults[i]
                        + "\");");
            }
        }
    }

    /**
     * Returns a boolean indicating whether this parameter has any properties
     * associated with its
     * StringParser.  This method always returns false; it may be overridden by
     * subclasses.
     * @return a boolean indicating whether this parameter has any properties
     * associated with its
     * StringParser.
     */
    public boolean hasProperties() {
        return (false);
    }

    /**
     * Creates source code for a java method that creates a parameter matching
     * this object's configuration.
     * @param methodName the name of the java method to generate.
     * @param out the PrintStream to which the generated source code should be
     * written.
     * @throws IOException if any are thrown by PrintStream.
     */
    public abstract void createMethod(String methodName, PrintStream out)
        throws IOException;

}
