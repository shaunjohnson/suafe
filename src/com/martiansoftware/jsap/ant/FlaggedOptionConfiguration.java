/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.ant;

import java.io.PrintStream;

import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;

/**
 * Stores/provides configuration data for flaggedoptions nested inside a jsap
 * ant task.
 * For detailed information on using the jsap task, see the documentation for
 * JSAPAntTask.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see JSAPAntTask
 * @see com.martiansoftware.jsap.FlaggedOption
 */

public class FlaggedOptionConfiguration extends OptionConfiguration {

    /**
     * The short flag for this option.
     */
    private char shortFlag = JSAP.NO_SHORTFLAG;

    /**
     * The long flag for this option.
     */
    private String longFlag = JSAP.NO_LONGFLAG;

    /**
     * If true, this option may be declared multiple times on a single command
     * line.
     */
    private boolean allowMultipleDeclarations = JSAP.NO_MULTIPLEDECLARATIONS;

    /**
     * If true, the allowMultipleDeclarations field has been set by the user.
     */
    private boolean declaredAllowMultipleDeclarations = false;

    /**
     * Creates a new FlaggedOptionConfiguration.
     */
    public FlaggedOptionConfiguration() {
        super();
    }

    /**
     * Sets the short flag for this option.
     * @param shortFlag the short flag for this option.
     */
    public void setShortflag(char shortFlag) {
        this.shortFlag = shortFlag;
    }

    /**
     * Returns the short flag for this option.
     * @return the short flag for this option.
     */
    public char getShortflag() {
        return (shortFlag);
    }

    /**
     * Sets the long flag for this option.
     * @param longFlag the long flag for this option.
     */
    public void setLongflag(String longFlag) {
        this.longFlag = longFlag;
    }

    /**
     * Returns the long flag for this option.
     * @return the long flag for this option.
     */
    public String getLongflag() {
        return (longFlag);
    }

    /**
     * Specifies whether this option can be declared multiple times on the same
     * command line.
     * @param allowMultipleDeclarations if true, this option can be declared
     * multiple times on the same
     * command line.
     */
    public void setAllowmultipledeclarations(
    	boolean allowMultipleDeclarations) {

        this.allowMultipleDeclarations = allowMultipleDeclarations;
        declaredAllowMultipleDeclarations = true;
    }

    /**
     * Returns a FlaggedOption preconfigured according to this configuration.
     * @return a FlaggedOption preconfigured according to this configuration.
     */
    public Parameter getParameter() {
        FlaggedOption result = new FlaggedOption(this.getId());

        result.setShortFlag(this.getShortflag());
        result.setLongFlag(this.getLongflag());
        result.setRequired(this.getRequired());
        result.setList(this.getIslist());
        if (this.declaredListSeparator()) {
            result.setListSeparator(this.getListseparator());
        }
        if (this.declaredAllowMultipleDeclarations) {
            result.setAllowMultipleDeclarations(this.allowMultipleDeclarations);
        }
        setupStringParser(result);
        result.setDefault(this.getDefaults());
        return (result);
    }

    /**
     * Creates source code for a java method that will instantiate a
     * FlaggedOption matching this
     * configuration.
     * @param methodName the name of the method to create
     * @param out the PrintStream to which the java source for the method
     * should be written.
     */
    public void createMethod(String methodName, PrintStream out) {

        out.println("    private FlaggedOption " + methodName + "() {");
        out.println(
            "        FlaggedOption result = new FlaggedOption(\""
                + this.getId()
                + "\");");
        if (getShortflag() != JSAP.NO_SHORTFLAG) {
            out.println(
                "        result.setShortFlag('" + getShortflag() + "');");
        }
        if (getLongflag() != JSAP.NO_LONGFLAG) {
            out.println(
                "        result.setLongFlag(\"" + getLongflag() + "\");");
        }
        if (this.declaredAllowMultipleDeclarations) {
            out.println(
                "        result.setAllowMultipleDeclarations("
                    + (allowMultipleDeclarations ? "true" : "false")
                    + ");");
        }
        super.createParentStatements("result", out);
        out.println("        return (result);");
        out.println("    }");
    }

}
