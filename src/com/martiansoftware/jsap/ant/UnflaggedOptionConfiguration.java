/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.ant;

import java.io.PrintStream;

import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.UnflaggedOption;

/**
 * Stores/provides configuration data for unflaggedoptions nested inside a jsap
 * ant task.
 * For detailed information on using the jsap task, see the documentation for
 * JSAPAntTask.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see JSAPAntTask
 * @see com.martiansoftware.jsap.UnflaggedOption
 */

public class UnflaggedOptionConfiguration extends OptionConfiguration {

    /**
     * If true, this unflaggedoption should be greedy.
     * (i.e., should consume the remaining unflaggedoptions from the command
     * line.
     */
    private boolean greedy = JSAP.NOT_GREEDY;

    /**
     * Creates a new UnflaggedOptionConfiguration.
     */
    public UnflaggedOptionConfiguration() {
        super();
    }

    /**
     * Sets whether this UnflaggedOption should be greedy.
     * (i.e., should consume the remaining unflaggedoptions from the command
     * line.
     * @param greedy if true, this UnflaggedOption should be greedy.
     */
    public void setGreedy(boolean greedy) {
        this.greedy = greedy;
    }

    /**
     * Returns a boolean indicating whether this UnflaggedOption is greedy.
     * @return a boolean indicating whether this UnflaggedOption is greedy.
     */
    public boolean getGreedy() {
        return (greedy);
    }

    /**
     * Returns an UnflaggedOption configured according to this configuration.
     * @return an UnflaggedOption configured according to this configuration.
     */
    public Parameter getParameter() {
        UnflaggedOption result = new UnflaggedOption(this.getId());

        result.setRequired(this.getRequired());
        result.setList(this.getIslist());
        if (this.declaredListSeparator()) {
            result.setListSeparator(this.getListseparator());
        }
        result.setGreedy(this.getGreedy());
        result.setDefault(this.getDefaults());
        setupStringParser(result);
        return (result);
    }

    /**
     * Creates java source code for a method that instantiates an
     * UnflaggedOption and
     * configures it according to this configuration.
     * @param methodName the name of the method to generate
     * @param out the PrintStream to which the java source code will be written.
     */
    public void createMethod(String methodName, PrintStream out)
        {

        out.println("    private UnflaggedOption " + methodName + "() {");
        out.println(
            "        UnflaggedOption result = new UnflaggedOption(\""
                + this.getId()
                + "\");");

        out.println(
            "        result.setGreedy("
                + (getGreedy() ? "true" : "false")
                + ");");

        super.createParentStatements("result", out);
        out.println("        return (result);");
        out.println("    }");
    }

}
