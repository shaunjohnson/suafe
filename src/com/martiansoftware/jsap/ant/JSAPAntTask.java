/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap.ant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;

/**
 * <p>An ANT task that generates a custom subclass of JSAP to simplify its use
 * in
 * a program.  Rather than create all of the Switches, FlaggedOptions, and
 * UnflaggedOptions and registering each with the JSAP, the developer does not
 * need to do anything but instantiate the custom JSAP produced by this task.
 * </p>
 *
 * <p>To use this task, you must first declare it in your ANT build file with a
 * &lt;taskdef&gt; tag, as follows:</p>
 *
 * <p><code>
 * &lt;taskdef name="jsap" classname="com.martiansoftware.jsap.ant.JSAPAntTask"
 * classpath="${lib}/[jsap jarfile]"/&gt;
 * </code></p>
 *
 * <p>Note that this <code>taskdef</code> must be placed in your build file
 * BEFORE your jsap task.  The
 * <code>classpath</code> attribute in the above example assumes that
 * [jsap jarfile] is the name of the JSAP jarfile you're using, and
 * is in the directory referenced by the ANT "lib" property.</p>
 *
 * <p>Once declared, the jsap task can be used as many times as you wish.  The
 * jsap
 * task supports the following attributes:</p>
 * <ul>
 * <li><b>srcdir</b> (required): the source directory below which the generated
 * JSAP source code should be placed.</li>
 * <li><b>classname</b> (required): the fully-qualified classname of the JSAP to
 * generate.</li>
 * <li><b>public</b> (optional): "true" or "false" (default false).  If true,
 * the
 * generated class will be declared public.</li>
 * </ul>
 *
 * <p>The jsap task supports the following nested elements:</p>
 *
 * <ul>
 * <li><b>switch</b> - declares a Switch (com.martiansoftware.jsap.Switch).</li>
 * <li><b>flaggedoption</b> - declares a FlaggedOption
 * (com.martiansoftware.jsap.FlaggedOption).</li>
 * <li><b>unflaggedoption</b> - declares an UnflaggedOption
 * (com.martiansoftware.jsap.UnflaggedOption).</li>
 * </ul>
 *
 * <p>These nested elements support the following attributes:</p>
 *
 * <center>
 * <table width="95%" border="1" cellpadding="0" cellspacing="0">
 * <tr>
 *    <td align="center"><b>Attribute</b></td>
 *    <td align="center"><b>Description</b></td>
 *    <td align="center"><b>switch</b></td>
 *    <td align="center"><b>flaggedoption</b></td>
 *    <td align="center"><b>unflaggedoption</b></td>
 *    <td align="center"><b>qualifiedswitch</b></td>
 * </tr>
 * <tr>
 *    <td align="center"><b>id</b></td>
 *    <td align="left">Unique id for this parameter.  This must be unique among
 * all parameters defined in this ANT task.</td>
 *    <td align="center">Required</td>
 *    <td align="center">Required</td>
 *    <td align="center">Required</td>
 *    <td align="center">Required</td>
 * </tr>
 * <tr>
 *    <td align="center"><b>shortflag</b></td>
 *    <td align="left">Short flag for this parameter.  Only the first character
 * of this attribute is read by the jsap task.  This must be unique among all
 * short
 * flags defined in this ANT task.</td>
 *    <td align="left">Either <b>shortflag</b> or <b>longflag</b> is required.
 * Both may be specified.</td>
 *    <td align="left">Either <b>shortflag</b> or <b>longflag</b> is required.
 * Both may be specified.</td>
 *    <td align="center">N/A</td>
 *    <td align="left">Either <b>shortflag</b> or <b>longflag</b> is required.
 * Both may be specified.</td>
 * </tr>
 * <tr>
 *    <td align="center"><b>longflag</b></td>
 *    <td align="left">Long flag for this parameter.  This must be unique among
 * all long flags defined in this ANT task.</td>
 *    <td align="left">Either <b>shortflag</b> or <b>longflag</b> is required.
 * Both may be specified.</td>
 *    <td align="left">Either <b>shortflag</b> or <b>longflag</b> is required.
 * Both may be specified.</td>
 *    <td align="center">N/A</td>
 *    <td align="left">Either <b>shortflag</b> or <b>longflag</b> is required.
 * Both may be specified.</td>
 * </tr>
 * <tr>
 *    <td align="center"><b>required</b></td>
 *    <td align="left">"true" or "false" (default false).  Indicates whether the
 * specified parameter is required.</td>
 *    <td align="center">N/A</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 * </tr>
 * <tr>
 *    <td align="center"><b>islist</b></td>
 *    <td align="left">"true" or "false" (default false).  Indicates whether the
 * specified parameter can be supplied as a list of values separated by a
 * delimiter
 * character.</td>
 *    <td align="center">N/A</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 * </tr>
 * <tr>
 *    <td align="center"><b>listseparator</b></td>
 *    <td align="left">Specifies the delimiter character to use for list
 * parameters.
 * Default is JSAP.DEFAULT_LISTSEPARATOR</td>
 *    <td align="center">N/A</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 * </tr>
 * <tr>
 *    <td align="center"><b>stringparser</b></td>
 *    <td align="left">Specifies the subclass of
 * com.martiansoftware.jsap.StringParser
 * to be used in parsing this parameter's values.  If the specified class name
 * contains
 * no "dot" characters, it is assumed to be in the package
 * com.martiansoftware.jsap.stringparsers.
 * Default value is
 * com.martiansoftware.jsap.stringparsers.StringStringParser.</td>
 *    <td align="center">N/A</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 *    <td align="center">Optional</td>
 * </tr>
 * <tr>
 *    <td align="center"><b>greedy</b></td>
 *    <td align="left">"true" or "false" (default false).  Specifies whether the
 * unflaggedoption should be "greedy"; that is, should consume all remaining
 * unflagged arguments from the command line.</td>
 *    <td align="center">N/A</td>
 *    <td align="center">N/A</td>
 *    <td align="center">Optional</td>
 *    <td align="center">N/A</td>
 * </tr>
 * </table>
 * </center>
 *
 * <p>All of these nested elements support multiple nested
 * <code>&lt;default&gt;</code>
 * elements.  The text content of these tags is used as a default value for the
 * parameter containing the tag.<p>
 *
 * <p>Finally, the &lt;flaggedoption&gt; and &lt;unflaggedoption&gt; support
 * multiple
 * nested &lt;property&gt; elements, with similar syntax to ANT's
 * &lt;property&gt;
 * elements.  These properties are set within the parameter's StringParser,
 * assuming
 * it is a subclass of com.martiansoftware.jsap.PropertyStringParser.</p>
 *
 * <h2>Example</h2>
 *
 * <pre>
* & lt;taskdef name = "jsap" classname = "com.martiansoftware.jsap.ant.JSAPAntTask"
 *      classpath="${build}"/&gt;
 *
 *     &lt;target name="createExampleAntJSAP"&gt;
 *
 *         &lt;jsap srcdir="${src}"
 *          classname="com.martiansoftware.jsap.examples.ExampleAntJSAP"&gt;
 *
 *             &lt;!-- create a switch flagged by "v" or "verbose" --&gt;
 *             &lt;switch id="verbose" shortflag="v" longflag="verbose"/&gt;
 *
 *             &lt;!-- create a required flaggedoption looking for an integer,
 *              flagged by "n" (e.g. "-n 5") --&gt;
 *             &lt;flaggedoption id="num" required="true" shortflag="n"
 *              stringparser="IntegerStringParser"&gt;
 *                 &lt;default&gt;5&lt;/default&gt;
 *             &lt;/flaggedoption&gt;
 *
 *             &lt;!-- create an unflaggedoption that reads all of the unflagged
 *              arguments from the
 *              command line --&gt;
 *             &lt;unflaggedoption id="files" greedy="true" /&gt;
 *
 *             &lt;!-- create a flaggedoption looking for a Date in "MM/DD/YY"
 *              format, flagged by "d"
 *                  or "date", defaulting to Christmas, 2002. --&gt;
 *             &lt;flaggedoption id="date" shortflag="d" longflag="date"
 *              stringparser="DateStringParser"&gt;
 *                 &lt;property name="format" value="MM/DD/YYYY"/&gt;
 *                 &lt;default>12/25/2002&lt;/default&gt;
 *             &lt;/flaggedoption&gt;
 *                 
 *         &lt;/jsap&gt;
 *     
 *     &lt;/target&gt;
 *</pre>
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.JSAP
 * @see com.martiansoftware.jsap.Parameter
 * @see com.martiansoftware.jsap.Switch
 * @see com.martiansoftware.jsap.FlaggedOption
 * @see com.martiansoftware.jsap.UnflaggedOption
 * @see com.martiansoftware.jsap.StringParser
 * @see com.martiansoftware.jsap.PropertyStringParser
 */
public class JSAPAntTask extends Task {

    /**
     * if true, the generated JSAP class should be declared public.
     */
    private boolean isPublic = false;

    /**
     * the top-level directory holding the source code (probably has a "com"
     * subdirectory, etc.)
     */
    private File srcDir = null;

    /**
     * the FULL class name of the JSAP subclass to create (i.e.,
     * "fullpackage.Class").
     */
    private String className = null;

    /**
     * A Vector containing all of the nested parameter configurations, in
     * declaration order.
     */
    private Vector parameterConfigs = null;

    /**
     * A "Test JSAP" that is instantiated to validate the configuration before
     * the class file is
     * generated.
     */
    private JSAP jsap = null;

    /**
     * If true, this JSAP uses FlaggedOptions (used for import statements).
     */
    private boolean containsFlaggedOptions = false;

    /**
     * If true, this JSAP uses UnflaggedOptions (used for import statements).
     */
    private boolean containsUnflaggedOptions = false;

    /**
     * If true, this JSAP uses Switches (used for import statements).
     */
    private boolean containsSwitches = false;

    /**
     * If true, this JSAP's StringParser has properties.
     */
    private boolean hasProperties = false;

    /**
     * Creates a new JSAPAntTask.  One JSAPAntTask is created for each
     * &lt;jsap&gt; section in an
     * ant built file.
     */
    public JSAPAntTask() {
        parameterConfigs = new Vector();
    }

    /**
     * Sets whether the generated JSAP should be declared public.  Default is
     * false.
     * @param isPublic if true, the generated JSAP will be declared public.
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Sets the top-level source directory under which the generated JSAP class
     * file should be written.
     * @param srcDir the top-level source directory under which the generated
     * JSAP class file should be written.
     */
    public void setSrcdir(File srcDir) {
        this.srcDir = srcDir;
    }

    /**
     * Sets the full classname for the generated JSAP.
     * @param className the full classname for the generated JSAP.
     */
    public void setClassname(String className) {
        this.className = className.trim();
    }

    /**
     * Adds a nested FlaggedOptionConfiguration to the generated JSAP.
     * @param flaggedOptionConfig the nested FlaggedOptionConfiguration to add
     * to the generated JSAP.
     */
    public void addConfiguredFlaggedoption(FlaggedOptionConfiguration flaggedOptionConfig) {
        containsFlaggedOptions = true;
        parameterConfigs.add(flaggedOptionConfig);
    }

    /**
     * Adds a nested UnflaggedOptionConfiguration to the generated JSAP.
     * @param unflaggedOptionConfig the nested UnflaggedOptionConfiguration to
     * add to the generated JSAP.
     */
    public void addConfiguredUnflaggedoption(UnflaggedOptionConfiguration unflaggedOptionConfig) {
        containsUnflaggedOptions = true;
        parameterConfigs.add(unflaggedOptionConfig);
    }

    /**
     * Adds a nested SwitchConfiguration to the generated JSAP.
     * @param switchConfig the nested SwitchConfiguration to add to the
     * generated JSAP.
     */
    public void addConfiguredSwitch(SwitchConfiguration switchConfig) {
        containsSwitches = true;
        parameterConfigs.add(switchConfig);
    }

    /**
     * Builds a JSAP that conforms to the configuration in the build file.  If
     * all of the specified
     * parameters can successfully be registered with the JSAP, the java file
     * will be written.
     * @throws JSAPException if there are any problems with the specified
     * configuration.
     */
    private void buildJSAP() throws JSAPException {
        JSAP jsap = new JSAP();
        for (Enumeration e = parameterConfigs.elements();
            e.hasMoreElements();
            ) {

            ParameterConfiguration pc =
                (ParameterConfiguration) e.nextElement();

            if (pc.hasProperties()) {
                hasProperties = true;
            }
            jsap.registerParameter(pc.getParameter());
        }
    }

    /**
     * Generates a JSAP java file that implements the specified configuration.
     * @throws IOException if an I/O error occurs.
     */
    private void writeJSAP() throws IOException {
        int lastDotPos = className.lastIndexOf(".");
        String packageName = "";
        String shortClassName = className;
        if (lastDotPos > -1) {
            packageName = className.substring(0, lastDotPos);
            shortClassName = className.substring(lastDotPos + 1);
        }

        System.out.println("package name: [" + packageName + "]");
        System.out.println("shortClassName: [" + shortClassName + "]");

        File classFileDir =
            new File(
                srcDir.getCanonicalPath()
                    + File.separatorChar
                    + packageName.replace('.', File.separatorChar));

        File classFile = new File(classFileDir, shortClassName + ".java");

        System.out.println(
            "Creating directory \"" + classFileDir.toString() + "\"");
        classFileDir.mkdirs();
        System.out.println("Creating JSAP class file \"" + classFile + "\"");
        classFile.createNewFile();
        System.out.println("Created");
        PrintStream out =
            new PrintStream(
                new BufferedOutputStream(new FileOutputStream(classFile)));

        createJavaFile(shortClassName, packageName, out);
        out.close();
    }

    /**
     * Simple utility to capitalize the first letter of the specified String.
     * @param s the String to represent in proper case.
     * @return the specified String with the first letter capitalized.
     */
    private String properCase(String s) {
        String result = null;
        if (s != null) {
            if (s.length() < 2) {
                result = s.toUpperCase();
            } else {
                result = s.toUpperCase().charAt(0) + s.substring(1);
            }
        }
        return (result);
    }

    /**
     * Writes java source code for a JSAP subclass that implements the
     * configuration specified in the
     * ant build file.
     * @param shortClassName the name of the JSAP subclass, sans package name.
     * @param packageName the name of the package to contain the generated JSAP
     * subclass.
     * @param out the PrintStream to which the java source should be written.
     * @throws IOException if an I/O error occurs.
     */
    private void createJavaFile(
        String shortClassName,
        String packageName,
        PrintStream out)
        throws IOException {
        if (packageName.length() > 0) {
            out.println("package " + packageName + ";");
            out.println();
        }
        out.println(" /*");
        out.println("  * THIS FILE IS AUTOMATICALLY GENERATED - DO NOT EDIT");
        out.println("  */");
        out.println();
        out.println("import com.martiansoftware.jsap.JSAP;");
        out.println("import com.martiansoftware.jsap.JSAPException;");
        if (containsSwitches) {
            out.println("import com.martiansoftware.jsap.Switch;");
        }
        if (containsFlaggedOptions) {
            out.println("import com.martiansoftware.jsap.FlaggedOption;");
        }
        if (containsUnflaggedOptions) {
            out.println("import com.martiansoftware.jsap.UnflaggedOption;");
        }
        if (hasProperties) {
            out.println(
                "import com.martiansoftware.jsap.PropertyStringParser;");
        }
        out.println();

        if (isPublic) {
            out.print("public ");
        }
        out.println("class " + shortClassName + " extends JSAP {");
        out.println();

        out.println("    public " + shortClassName + "() {");
        out.println("        super();");
        out.println("        try {");
        out.println("            init();");
        out.println("        } catch (JSAPException e) {");
        out.println(
            "            throw(new IllegalStateException(e.getMessage()));");
        out.println("        }");
        out.println("    }");
        out.println();
        out.println("    private void init() throws JSAPException {");
        for (Enumeration e1 = parameterConfigs.elements();
            e1.hasMoreElements();
            ) {

            ParameterConfiguration pc =
                (ParameterConfiguration) e1.nextElement();

            out.println(
                "        this.registerParameter( create"
                    + properCase(pc.getId())
                    + "() );");
        }
        out.println("    }");
        out.println();
        for (Enumeration e1 = parameterConfigs.elements();
            e1.hasMoreElements();
            ) {

            ParameterConfiguration pc =
                (ParameterConfiguration) e1.nextElement();

            pc.createMethod("create" + properCase(pc.getId()), out);
            out.println();
        }
        out.println("}");
    }

    /**
     * Validates the JSAP configuration and, if successful, writes the java
     * source code for a JSAP subclass
     * that implements the configuration specified in the ant build file.
     * @throws BuildException if unsuccessful for any reason.
     */
    public void execute() throws BuildException {
        if (srcDir == null) {
            throw (new BuildException("srcdir is required."));
        }
        if ((className == null) || (className.length() == 0)) {
            throw (new BuildException("classname is required."));
        }
        if (!srcDir.isDirectory()) {
            throw (new BuildException("srcdir must be a directory."));
        }
        System.out.println("srcDir=[" + srcDir + "]");
        System.out.println("className=[" + className + "]");
        System.out.println("public=" + isPublic);

        try {
            buildJSAP();
            writeJSAP();
        } catch (Exception e) {
            throw (new BuildException(e.getMessage()));
        }

    }

}
