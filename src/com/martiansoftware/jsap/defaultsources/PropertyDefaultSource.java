/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.defaultsources;

import com.martiansoftware.jsap.DefaultSource;
import com.martiansoftware.jsap.Defaults;
import com.martiansoftware.jsap.IDMap;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.ExceptionMap;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Enumeration;
import java.io.IOException;

/**
 *
 * <p>A DefaultSource with values defined in a java.util.Properties object.
 * In order to determine which parameter
 * a value is associated with, each property key is first compared to each
 * parameter's unique ID.  Failing a
 * match, each parameter's long flag is checked, and finally the short flags
 * are checked.  A PropertyDefaultSource
 * may contain a mix of IDs, long flags, and short flags.</p>
 *
 * <p>A PropertyDefaultSource is also incredibly useful as a configuration file
 * loader.  Multiple PropertyDefaultSources
 * can be chained together in a JSAP in order to prioritize their entries
 * (e.g., load "~/myproject.conf" first, then
 * "/etc/myproject.conf").</p>
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.DefaultSource
 * @see java.util.Properties
 */
public class PropertyDefaultSource implements DefaultSource {

    /**
     * If true, this PropertyDefaultSource is being loaded from a file.
     * @see PropertyDefaultSource#PropertyDefaultSource(String,boolean)
     */
    private boolean loadFromFile = false;

    /**
     * If true, this PropertyDefaultSource is being loaded from a Properties
     * object.
     * @see PropertyDefaultSource#PropertyDefaultSource(Properties)
     * @see java.util.Properties
     */
    private boolean loadFromProperties = false;

    /**
     * If true, this PropertyDefaultSource is being loaded from an InputStream.
     * @see PropertyDefaultSource#PropertyDefaultSource(InputStream,boolean)
     * @see java.io.InputStream
     */
    private boolean loadFromInputStream = false;

    /**
     * The InputStream from which the PropertyDefaultSource will be loaded, if
     * any.
     */
    private InputStream in = null;

    /**
     * The name of the file from which the PropertyDefaultSource will be
     * loaded, if any.
     */
    private String propertyFileName = null;

    /**
     * If true, any encountered IOExceptions will be thrown.  Default is true.
     */
    private boolean throwIOExceptions = true;

    /**
     * The Properties object containing the default values.
     */
    private Properties properties = null;

    /**
     * Indicates whether we have already loaded the Properties.
     */
    private boolean loaded = false;

    /**
     * Creates a new PropertyDefaultSource by loading the specified file.  The
     * file is loaded when the JSAP
     * requests the defaults from this object.
     * @param propertyFileName the name of the properties file containing the
     * default values.
     * @param throwIOExceptions if true, any encountered IOExceptions will be
     * re-thrown.  Set this to false
     * if you want to ignore any exceptions (e.g., specified file does not
     * exist).
     */
    public PropertyDefaultSource(
        String propertyFileName,
        boolean throwIOExceptions) {
        this.loadFromFile = true;
        this.propertyFileName = propertyFileName;
        this.throwIOExceptions = throwIOExceptions;
    }

    /**
     * Creates a new PropertyDefaultSource based upon the specified Properties
     * object.
     * @param properties the Properties object containing the default values.
     */
    public PropertyDefaultSource(Properties properties) {
        this.loadFromProperties = true;
        this.properties = properties;
        loaded = true;
    }

    /**
     * Creates a new PropertyDefaultSource based upon the specified InputStream.
     * @param in the InputStream containing the Properties.
     * @param throwIOExceptions if true, any encountered IOExceptions will be
     * re-thrown.
     */
    public PropertyDefaultSource(InputStream in, boolean throwIOExceptions) {
        this.loadFromInputStream = true;
        this.in = in;
        this.throwIOExceptions = throwIOExceptions;
    }

    /**
     * Returns the properties for this PropertyDefaultSource.  If necessary,
     * the properties are loaded first
     * from a file or InputStream as specified by the constructor.
     * @return the properties for this PropertyDefaultSource.
     * @throws IOException if an I/O exception occurs, AND this
     * PropertyDefaultSource is configured to throw
     * IOExceptions.
     */
    private Properties getProperties() throws IOException {
        if (!loaded) {
            try {
                if (loadFromFile) {
                    in =
                        new BufferedInputStream(
                            new FileInputStream(propertyFileName));
                }
                Properties properties = new Properties();
                properties.load(in);
                in.close();
                in = null;
                this.properties = properties;
                loaded = true;
            } catch (IOException e) {
                if (throwIOExceptions) {
                    throw (e);
                }
            }
        }
        Properties result = this.properties;
        if (result == null) {
            this.properties = new Properties();
            result = this.properties;
            loaded = true;
        }
        return (result);
    }

    /**
     * Returns a Defaults object based upon this PropertyDefaultSource's
     * properties and the specified IDMap.
     * In order to determine which parameter
     * a value is associated with, each property key is first compared to each
     * parameter's unique ID.  Failing a
     * match, each parameter's long flag is checked, and finally the short
     * flags are checked.  A
     * PropertyDefaultSource may contain a mix of IDs, long flags, and short
     * flags.
     * @param idMap the IDMap containing the current JSAP configuration.
     * @param exceptionMap the ExceptionMap object within which any encountered
     * exceptions will be thrown.
     * @return a Defaults object based upon this PropertyDefaultSource's
     * properties and the specified IDMap.
     */
    public Defaults getDefaults(IDMap idMap, ExceptionMap exceptionMap) {
        Defaults defaults = new Defaults();
        try {
            Properties properties = getProperties();
            for (Enumeration enumeration = properties.propertyNames();
                enumeration.hasMoreElements();
                ) {

                String thisName = (String) enumeration.nextElement();
                if (idMap.idExists(thisName)) {
                    defaults.addDefault(
                        thisName,
                        properties.getProperty(thisName));
                } else {
                    String paramID = idMap.getIDByLongFlag(thisName);
                    if (paramID != null) {
                        defaults.addDefault(
                            paramID,
                            properties.getProperty(thisName));
                    } else if (thisName.length() == 1) {
                        paramID = idMap.getIDByShortFlag(thisName.charAt(0));
                        if (paramID != null) {
                            defaults.addDefault(
                                paramID,
                                properties.getProperty(thisName));
                        } else {
                            exceptionMap.addException(
                                null,
                                new JSAPException(
                                    "Unknown parameter: " + thisName));
                        }
                    } else {
                        exceptionMap.addException(
                            null,
                            new JSAPException(
                                "Unknown parameter: " + thisName));
                    }
                }
            }
        } catch (IOException ioe) {
            exceptionMap.addException(
                null,
                new JSAPException("Unable to load properties.", ioe));
        }
        return (defaults);
    }

}
