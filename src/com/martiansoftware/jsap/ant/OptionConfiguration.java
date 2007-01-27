/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.ant;

import java.io.PrintStream;
import java.util.Vector;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.Option;
import com.martiansoftware.jsap.PropertyStringParser;
import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.UnflaggedOption;

/**
 * Stores/provides configuration data common to both flaggedoptions and
 * unflaggedoptions nested inside a jsap ant task.
 * For detailed information on using the jsap task, see the documentation for
 * JSAPAntTask.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see JSAPAntTask
 */

public abstract class OptionConfiguration extends ParameterConfiguration {

    /**
     * If true, this option is required.
     */
    private boolean required = JSAP.NOT_REQUIRED;

    /**
     * Stores any properties for this option's StringParser (if it's a
     * PropertyStringParser)
     */
    private Vector parserProperties = null;

    /**
     * If true, this option stores a list of values.
     */
    private boolean isList = JSAP.NOT_LIST;

    /**
     * If this option is a list, this is the list separator token.
     */
    private char listSeparator = JSAP.DEFAULT_LISTSEPARATOR;

    /**
     * If this is true, the user has specified a list separator token.
     */
    private boolean declaredListSeparator = false;

    /**
     * The name of the StringParser for this option.
     */
    private String stringParser = null;

    /**
     * Creates a new OptionConfiguration.
     */
    public OptionConfiguration() {
        super();
        parserProperties = new Vector();
    }

    /**
     * Sets the StringParser for this option.
     * @param stringParser the StringParser for this option.
     */
    public void setStringparser(String stringParser) {
        this.stringParser = stringParser;
    }

    /**
     * Returns the classname of the StringParser for this option.  If the
     * current StringParser name
     * does not contains any "dot"
     * characters, it is prefixed with "com.martiansoftware.jsap.stringparsers."
     * prior to returning.
     * @return the classname of the StringParser for this option.
     */
    public String getStringparser() {
        String result = null;
        if (stringParser != null) {
            if (stringParser.indexOf(".") == -1) {
                result =
                    "com.martiansoftware.jsap.stringparsers." + stringParser;
            } else {
                result = stringParser;
            }
        }
        return (result);
    }

    /**
     * Sets whether this option is required.
     * @param required if true, this option is required.
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Returns a boolean indicating whether this option is required.
     * @return a boolean indicating whether this option is required.
     */
    public boolean getRequired() {
        return (required);
    }

    /**
     * Sets whether this option is a list.
     * @param isList if true, this option is a list.
     */
    public void setIslist(boolean isList) {
        this.isList = isList;
    }

    /**
     * Returns a boolean indicating whether this option is a list.
     * @return a boolean indicating whether this option is a list.
     */
    public boolean getIslist() {
        return (isList);
    }

    /**
     * Sets the list separator for this option, if it is a list.
     * @param listSeparator the list separator character for this option, if it
     * is a list.
     */
    public void setListseparator(char listSeparator) {
        declaredListSeparator = true;
        this.listSeparator = listSeparator;
    }

    /**
     * Returns the list separator character for this option.
     * @return the list separator character for this option.
     */
    public char getListseparator() {
        return (listSeparator);
    }

    /**
     * Returns a boolean indicating whether the list separator character has
     * been specified by the user.
     * @return a boolean indicating whether the list separator character has
     * been specified by the user.
     */
    public boolean declaredListSeparator() {
        return (declaredListSeparator);
    }

    /**
     * Adds a property to the current list of properties for this option's
     * PropertyStringParser.
     * @param p the property to add.
     */
    public void addConfiguredProperty(ParserProperty p) {
        parserProperties.add(p);
    }

    /**
     * Returns an array of ParserProperties for this option's
     * PropertyStringParser, or null if no ParserProperties
     * are defined.
     * @return an array of ParserProperties for this option's
     * PropertyStringParser, or null if no ParserProperties
     * are defined.
     */
    public ParserProperty[] getParserProperties() {
        ParserProperty[] result = null;
        if (parserProperties.size() > 0) {
            result =
                (ParserProperty[]) parserProperties.toArray(
                    new ParserProperty[0]);
        }
        return (result);
    }

    /**
     * Returns a boolean indicating whether any ParserProperties are defined.
     * @return a boolean indicating whether any ParserProperties are defined.
     */
    public boolean hasProperties() {
        ParserProperty[] props = getParserProperties();
        return ((props != null) && (props.length > 0));
    }

    /**
     * Instantiates and configures A {@link com.martiansoftware.jsap.StringParser} according to this
     * configuration, and provides it to the
     * specified Option.
     * @param option the Option that should use the configured StringParser.
     */
    protected void setupStringParser(Option option) {
        if (this.getStringparser() != null) {
            try {
                StringParser sp =
                    (StringParser) Class
                        .forName(this.getStringparser())
                        .newInstance();
                if (option instanceof FlaggedOption) {
                    ((FlaggedOption) option).setStringParser(sp);
                } else {
                    ((UnflaggedOption) option).setStringParser(sp);
                }
            } catch (Exception e) {
                throw (
                    new IllegalArgumentException(
                        "Unable to instantiate \""
                            + this.getStringparser()
                            + "\""));
            }
        }

        if (this.hasProperties()) {
            try {
                PropertyStringParser psp =
                    (PropertyStringParser) option.getStringParser();
                ParserProperty[] props = this.getParserProperties();
                for (int i = 0; i < props.length; ++i) {
                    psp.setProperty(props[i].getName(), props[i].getValue());
                }
            } catch (Exception e) {
                throw (
                    new IllegalArgumentException(
                        "Option \""
                            + option.getID()
                            + "\": "
                            + option.getStringParser().getClass().getName()
                            + " is not an instance of "
                            + "com.martiansoftware.jsap.PropertyParser."));
            }
        }
    }

    /**
     * Creates java source code to configure an option as specified in this
     * object.  These methods are called
     * by both UnflaggedOptionConfiguration and FlaggedOptionConfiguration.
     * @param objName the name of the object in the generated source code
     * @param out a PrintStream to which the source code should be written
     */
    protected void createParentStatements(String objName, PrintStream out) {
        super.createParentStatements(objName, out);
        out.println(
            "        "
                + objName
                + ".setList("
                + (getIslist() ? "true" : "false")
                + ");");
        out.println(
            "        "
                + objName
                + ".setRequired("
                + (getRequired() ? "true" : "false")
                + ");");
        if (getStringparser() != null) {
            out.println(
                "        "
                    + objName
                    + ".setStringParser( new "
                    + getStringparser()
                    + "() );");
        }
        if (hasProperties()) {
            ParserProperty[] props = getParserProperties();
            out.println();
            out.println(
                "        PropertyStringParser psp = (PropertyStringParser) "
                    + objName
                    + ".getStringParser();");
            for (int i = 0; i < props.length; ++i) {
                out.println(
                    "        psp.setProperty(\""
                        + props[i].getName()
                        + "\", \""
                        + props[i].getValue()
                        + "\");");
            }
            out.println();
        }
        if (declaredListSeparator()) {
            out.println(
                "        "
                    + objName
                    + ".setListSeparator('"
                    + getListseparator()
                    + "');");
        }
    }

}
