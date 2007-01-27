/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.Iterator;

import com.martiansoftware.jsap.stringparsers.BigDecimalStringParser;
import com.martiansoftware.jsap.stringparsers.BigIntegerStringParser;
import com.martiansoftware.jsap.stringparsers.BooleanStringParser;
import com.martiansoftware.jsap.stringparsers.ByteStringParser;
import com.martiansoftware.jsap.stringparsers.CharacterStringParser;
import com.martiansoftware.jsap.stringparsers.ClassStringParser;
import com.martiansoftware.jsap.stringparsers.ColorStringParser;
import com.martiansoftware.jsap.stringparsers.DoubleStringParser;
import com.martiansoftware.jsap.stringparsers.FloatStringParser;
import com.martiansoftware.jsap.stringparsers.InetAddressStringParser;
import com.martiansoftware.jsap.stringparsers.IntSizeStringParser;
import com.martiansoftware.jsap.stringparsers.IntegerStringParser;
import com.martiansoftware.jsap.stringparsers.LongSizeStringParser;
import com.martiansoftware.jsap.stringparsers.LongStringParser;
import com.martiansoftware.jsap.stringparsers.PackageStringParser;
import com.martiansoftware.jsap.stringparsers.ShortStringParser;
import com.martiansoftware.jsap.stringparsers.StringStringParser;
import com.martiansoftware.jsap.stringparsers.URLStringParser;
import com.martiansoftware.jsap.xml.JSAPConfig;
import com.martiansoftware.util.StringUtils;

/**
 * The core class of the JSAP (Java Simple Argument Parser) API.
 *
 * <p>A JSAP is responsible for converting an array of Strings, typically
 * received from a  command line in the main class' main() method, into a
 * collection of Objects that are retrievable by a unique ID assigned by the
 * developer.</p>
 *
 * <p>Before a JSAP parses a command line, it is configured with the Switches,
 * FlaggedOptions, and UnflaggedOptions it will accept.  As a result, the
 * developer can rest assured that if no Exceptions are thrown by the JSAP's
 * parse() method, the entire command line was parsed successfully.</p>
 *
 * <p>For example, to parse a command line with the syntax "[--verbose]
 * {-n|--number} Mynumber", the following code could be used:</p.
 *
 * <code><pre>
 * JSAP myJSAP = new JSAP();
 * myJSAP.registerParameter( new Switch( "verboseSwitch", JSAP.NO_SHORTFLAG,
 * "verbose" ) );
 * myJSAP.registerParameter( new FlaggedOption( "numberOption", new
 * IntegerStringParser(), JSAP.NO_DEFAULT,
 * JSAP.NOT_REQUIRED, 'n', "number" ) );
 * JSAPResult result = myJSAP.parse(args);
 * </pre></code>
 *
 * <p>The results of the parse could then be obtained with:</p>
 *
 * <code><pre>
 * int n = result.getInt("numberOption");
 * boolean isVerbose = result.getBoolean("verboseSwitch");
 * </pre></code>
 *
 * <h3>Generating a JSAP from ANT</h3>
 * <p>If you don't want to register all your parameters manually as shown
 * above, the JSAP API provides a custom ANT task that will generate a
 * custom JSAP subclass to suit your needs.  See
 * com.martiansoftware.jsap.ant.JSAPAntTask for details.</p>
 * <p>See the accompanying documentation for examples and further information.
 *
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @author Klaus Berg (bug fixes in help generation)
 * @author Wolfram Esser (contributed code for custom line separators in help)
 * @see com.martiansoftware.jsap.ant.JSAPAntTask
 */
public class JSAP {

    /**
     * Map of this JSAP's AbstractParameters keyed on their unique ID.
     */
    private Map paramsByID = null;

    /**
     * Map of this JSAP's AbstractParameters keyed on their short flag.
     */
    private Map paramsByShortFlag = null;

    /**
     * Map of this JSAP's AbstractParameters keyed on their long flag.
     */
    private Map paramsByLongFlag = null;

    /**
     * List of this JSAP's UnflaggedOptions, in order of declaration.
     */
    private List unflaggedOptions = null;

    /**
     * List of all of this JSAP's AbstractParameters, in order of
     * declaration.
     */
    private List paramsByDeclarationOrder = null;

    /**
     * List of all of this JSAP's DefaultSources, in order of declaration.
     */
    private List defaultSources = null;

    /**
     * If not null, overrides the automatic usage info.
     */
    private String usage = null;

    /**
     * If not null, overrides the automatic help info.
     */
    private String help = null;

    /**
     * Does not have a short flag.
     *
     * @see com.martiansoftware.jsap.FlaggedOption
     * @see com.martiansoftware.jsap.UnflaggedOption
     */
    public static final char NO_SHORTFLAG = '\0';

    /**
     * Does not have a long flag.
     *
     * @see com.martiansoftware.jsap.FlaggedOption
     * @see com.martiansoftware.jsap.UnflaggedOption
     */
    public static final String NO_LONGFLAG = null;

    /**
     * The default separator for list parameters (equivalent to
     * java.io.File.pathSeparatorChar)
     *
     * @see FlaggedOption#setListSeparator(char)
     */
    public static final char DEFAULT_LISTSEPARATOR =
        java.io.File.pathSeparatorChar;

    /**
     * The default separator between parameters in generated help (a newline
     * by default)
     */
    public static final String DEFAULT_PARAM_HELP_SEPARATOR = "\n";
    
    /**
     * The parameter is required.
     *
     * @see FlaggedOption#setRequired(boolean)
     */
    public static final boolean REQUIRED = true;

    /**
     * The parameter is not required.
     *
     * @see FlaggedOption#setRequired(boolean)
     */
    public static final boolean NOT_REQUIRED = false;

    /**
     * The parameter is a list.
     *
     * @see FlaggedOption#setList(boolean)
     */
    public static final boolean LIST = true;

    /**
     * The parameter is not a list.
     *
     * @see FlaggedOption#setList(boolean)
     */
    public static final boolean NOT_LIST = false;

    /**
     * The parameter allows multiple declarations.
     *
     * @see FlaggedOption#setAllowMultipleDeclarations(boolean)
     */
    public static final boolean MULTIPLEDECLARATIONS = true;

    /**
     * The parameter does not allow multiple declarations.
     *
     * @see FlaggedOption#setAllowMultipleDeclarations(boolean)
     */
    public static final boolean NO_MULTIPLEDECLARATIONS = false;

    /**
     * The parameter consumes the command line.
     *
     * @see com.martiansoftware.jsap.UnflaggedOption#setGreedy(boolean)
     */
    public static final boolean GREEDY = true;

    /**
     * The parameter does not consume the command line.
     *
     * @see com.martiansoftware.jsap.UnflaggedOption#setGreedy(boolean)
     */
    public static final boolean NOT_GREEDY = false;

    /** The parameter has no default value.
     */
    public static final String NO_DEFAULT = null;

    /**
     * The parameter has no help text.
     *
     * @see com.martiansoftware.jsap.Parameter#setHelp(String)
     */
    public static final String NO_HELP = null;

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.BigDecimalStringParser}.
     */
    
    public static final BigDecimalStringParser BIGDECIMAL_PARSER = BigDecimalStringParser.getParser();
    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.BigIntegerStringParser}.
     */
    
    public static final BigIntegerStringParser BIGINTEGER_PARSER = BigIntegerStringParser.getParser();
    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.BooleanStringParser}.
     */
    
    public static final BooleanStringParser BOOLEAN_PARSER = BooleanStringParser.getParser();
    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.ByteStringParser}.
     */
    
    public static final ByteStringParser BYTE_PARSER = ByteStringParser.getParser();
    
    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.CharacterStringParser}.
     */
    
    public static final CharacterStringParser CHARACTER_PARSER = CharacterStringParser.getParser();
    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.ClassStringParser}.
     */
    
    public static final ClassStringParser CLASS_PARSER = ClassStringParser.getParser();

    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.ColorStringParser}.
     */
    
    public static final ColorStringParser COLOR_PARSER = ColorStringParser.getParser();

    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.DoubleStringParser}.
     */
    
    public static final DoubleStringParser DOUBLE_PARSER = DoubleStringParser.getParser();

    
    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.FloatStringParser}.
     */
    
    public static final FloatStringParser FLOAT_PARSER = FloatStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.InetAddressStringParser}.
     */
    
    public static final InetAddressStringParser INETADDRESS_PARSER = InetAddressStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.IntegerStringParser}.
     */
    
    public static final IntegerStringParser INTEGER_PARSER = IntegerStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.IntSizeStringParser}.
     */
    
    public static final IntSizeStringParser INTSIZE_PARSER = IntSizeStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.LongSizeStringParser}.
     */
    
    public static final LongSizeStringParser LONGSIZE_PARSER = LongSizeStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.LongStringParser}.
     */
    
    public static final LongStringParser LONG_PARSER = LongStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.PackageStringParser}.
     */
    
    public static final PackageStringParser PACKAGE_PARSER = PackageStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.ShortStringParser}.
     */
    
    public static final ShortStringParser SHORT_PARSER = ShortStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.StringStringParser}.
     */
    
    public static final StringStringParser STRING_PARSER = StringStringParser.getParser();

    /** 
     * The only instance of a {@link com.martiansoftware.jsap.stringparsers.URLStringParser}.
     */
    
    public static final URLStringParser URL_PARSER = URLStringParser.getParser();

    /**
     * The default screen width used for formatting help.
     */
    public static final int DEFAULT_SCREENWIDTH = 80;

    /**
     * Temporary fix for bad console encodings screwing up non-breaking spaces.
     */
    static char SYNTAX_SPACECHAR = ' ';
    
    static {
    	if (Boolean.valueOf(System.getProperty("com.martiansoftware.jsap.usenbsp", "false")).booleanValue()) {
    		SYNTAX_SPACECHAR = '\u00a0';
    	}
    }
    
    /**
     * Creates a new JSAP with an empty configuration.  It must be configured
     * with registerParameter() before its parse() methods may be called.
     */
    public JSAP() {
    	init();
    }
    
    /**
     * Creates a new JSAP configured as specified in the referenced xml.
     * @param jsapXML reference to xml representation of the JSAP configuration
     * @throws IOException if an I/O error occurs
     * @throws JSAPException if the configuration is not valid
     */
    public JSAP(URL jsapXML) throws IOException, JSAPException {
    	init();
    	JSAPConfig.configure(this, jsapXML);
    }

    /**
     * Creates a new JSAP configured as specified in the referenced xml.
     * @param resourceName name of the resource (accessible via this JSAP's classloader)
     * containing the xml representation of the JSAP configuration
     * @throws IOException if an I/O error occurs
     * @throws JSAPException if the configuration is not valid
     */
    public JSAP(String resourceName) throws IOException, JSAPException {
    	this(JSAP.class.getClassLoader().getResource(resourceName));
    }
    
    private void init() {
        paramsByID = new java.util.HashMap();
        paramsByShortFlag = new java.util.HashMap();
        paramsByLongFlag = new java.util.HashMap();
        unflaggedOptions = new java.util.ArrayList();
        paramsByDeclarationOrder = new java.util.ArrayList();
        defaultSources = new java.util.ArrayList();    	
    }
    
    /**
     * Sets the usage string manually, overriding the automatically-
     * generated String.  To remove the override, call setUsage(null).
     * @param usage the manually-set usage string.
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    /**
     * Sets the help string manually, overriding the automatically-
     * generated String.  To remove the override, call setHelp(null).
     * @param help the manualy-set help string.
     */
    public void setHelp(String help) {
        this.help = help;
    }

    /**
     * A shortcut method for calling getHelp(80, "\n").
     * @see #getHelp(int,String)
     * @return the same as gethelp(80, "\n")
     */
    public String getHelp() {
        return (getHelp(DEFAULT_SCREENWIDTH, DEFAULT_PARAM_HELP_SEPARATOR));
    }

    /**
     * A shortcut method for calling getHelp(screenWidth, "\n").
     * @param screenWidth the screen width for which to format the help.
     * @see #getHelp(int,String)
     * @return the same as gethelp(screenWidth, "\n")
     */
    public String getHelp(int screenWidth) {
    	return (getHelp(screenWidth, DEFAULT_PARAM_HELP_SEPARATOR));
    }
    
    /**
     * If the help text has been manually set, this method simply
     * returns it, ignoring the screenWidth parameter.  Otherwise,
     * an automatically-formatted help message is returned, tailored
     * to the specified screen width.
     * @param screenWidth the screen width (in characters) for which
     * the help text will be formatted.  If zero, help will not be
     * line-wrapped.
     * @return complete help text for this JSAP.
     */
    public String getHelp(int screenWidth, String paramSeparator) {
        String result = help;
        if (result == null) {
            StringBuffer buf = new StringBuffer();

            // We'll wrap at screenWidth - 8
            int wrapWidth = screenWidth - 8;

            // now loop through all the params again and display their help info
            for (Iterator i = paramsByDeclarationOrder.iterator();
                i.hasNext();) {
                Parameter param = (Parameter) i.next();
                StringBuffer defaultText = new StringBuffer();
                String[] defaultValue = param.getDefault();
                if ( !(param instanceof Switch) && defaultValue != null ) {
                    defaultText.append(" (default: ");
                    for(int j = 0; j < defaultValue.length; j++ ) {
                        if (j > 0) defaultText.append( ", " );
                        defaultText.append(defaultValue[ j ]);
                    }
                    defaultText.append(")");
                }
                Iterator helpInfo =
                    StringUtils
                        .wrapToList(param.getHelp() + defaultText, wrapWidth)
                        .iterator();

                buf.append("  "); // the two leading spaces
                buf.append(param.getSyntax());
                buf.append("\n");

                while (helpInfo.hasNext()) {
                    buf.append("        ");
                    buf.append( helpInfo.next() );
                    buf.append("\n");
                }
                if (i.hasNext()) {
                    buf.append(paramSeparator);
                }
            }
            result = buf.toString();
        }
        return (result);
    }

    /**
     * Returns an automatically generated usage description based upon this
     * JSAP's  current configuration.
     *
     * @return an automatically generated usage description based upon this
     * JSAP's current configuration.
     */
    public String getUsage() {
        String result = usage;
        if (result == null) {
            StringBuffer buf = new StringBuffer();
            for (Iterator i = paramsByDeclarationOrder.iterator();
                i.hasNext();) {
                Parameter param = (Parameter) i.next();
                if (buf.length() > 0) {
                    buf.append(" ");
                }
                buf.append(param.getSyntax());
            }
            result = buf.toString();
        }
        return (result);
    }

    /**
     * Returns an automatically generated usage description based upon this
     * JSAP's  current configuration.  This returns exactly the same result
     * as getUsage().
     *
     * @return an automatically generated usage description based upon this
     * JSAP's current configuration.
     */
    public String toString() {
        return (getUsage());
    }

    /**
     * Returns an IDMap associating long and short flags with their associated
     * parameters' IDs, and allowing the listing of IDs.  This is probably only
     * useful for developers creating their own DefaultSource classes.
     * @return an IDMap based upon this JSAP's current configuration.
     */
    public IDMap getIDMap() {
        List ids = new java.util.ArrayList(paramsByDeclarationOrder.size());
        for (Iterator i = paramsByDeclarationOrder.iterator(); i.hasNext();) {
            Parameter param = (Parameter) i.next();
            ids.add(param.getID());
        }

        Map byShortFlag = new java.util.HashMap();
        for (Iterator i = paramsByShortFlag.keySet().iterator();
          i.hasNext();) {
            Character c = (Character) i.next();
            byShortFlag.put(
                c,
                ((Parameter) paramsByShortFlag.get(c)).getID());
        }

        Map byLongFlag = new java.util.HashMap();
        for (Iterator i = paramsByLongFlag.keySet().iterator(); i.hasNext();) {
            String s = (String) i.next();
            byLongFlag.put(
                s,
                ((Parameter) paramsByLongFlag.get(s)).getID());
        }

        return (new IDMap(ids, byShortFlag, byLongFlag));
    }

    /**
     * Returns the requested Switch, FlaggedOption, or UnflaggedOption with the
     * specified ID.  Depending upon what you intend to do with the result, it
     * may be necessary to re-cast the result as a Switch, FlaggedOption, or
     * UnflaggedOption as appropriate.
     *
     * @param id the ID of the requested Switch, FlaggedOption, or
     * UnflaggedOption.
     * @return the requested Switch, FlaggedOption, or UnflaggedOption, or null
     * if no Parameter with the specified ID is defined in this JSAP.
     */
    public Parameter getByID(String id) {
        return ((Parameter) paramsByID.get(id));
    }

    /**
     * Returns the requested Switch or FlaggedOption with the specified long
     * flag. Depending upon what you intend to do with the result, it may be
     * necessary to re-cast the result as a Switch or FlaggedOption as
     * appropriate.
     *
     * @param longFlag the long flag of the requested Switch or FlaggedOption.
     * @return the requested Switch or FlaggedOption, or null if no Flagged
     * object with the specified long flag is defined in this JSAP.
     */
    public Flagged getByLongFlag(String longFlag) {
        return ((Flagged) paramsByLongFlag.get(longFlag));
    }

    /**
     * Returns the requested Switch or FlaggedOption with the specified short
     * flag. Depending upon what you intend to do with the result, it may be
     * necessary to re-cast the result as a Switch or FlaggedOption as
     * appropriate.
     *
     * @param shortFlag the short flag of the requested Switch or FlaggedOption.
     * @return the requested Switch or FlaggedOption, or null if no Flagged
     * object with the specified short flag is defined in this JSAP.
     */
    public Flagged getByShortFlag(Character shortFlag) {
        return ((Flagged) paramsByShortFlag.get(shortFlag));
    }

    /**
     * Returns the requested Switch or FlaggedOption with the specified short
     * flag. Depending upon what you intend to do with the result, it may be
     * necessary to re-cast the result as a Switch or FlaggedOption as
     * appropriate.
     *
     * @param shortFlag the short flag of the requested Switch or FlaggedOption.
     * @return the requested Switch or FlaggedOption, or null if no Flagged
     * object with the specified short flag is defined in this JSAP.
     */
    public Flagged getByShortFlag(char shortFlag) {
        return (getByShortFlag(new Character(shortFlag)));
    }

    /**
     * Returns an Iterator over all UnflaggedOptions currently registered with
     * this JSAP.
     *
     * @return an Iterator over all UnflaggedOptions currently registered with
     * this JSAP.
     * @see java.util.Iterator
     */
    public Iterator getUnflaggedOptionsIterator() {
        return (unflaggedOptions.iterator());
    }

    /**
     * Registers a new DefaultSource with this JSAP, at the end of the current
     * DefaultSource chain, but before the defaults defined within the
     * AbstractParameters themselves.
     *
     * @param ds the DefaultSource to append to the DefaultSource chain.
     * @see com.martiansoftware.jsap.DefaultSource
     */
    public void registerDefaultSource(DefaultSource ds) {
        defaultSources.add(ds);
    }

    /**
     * Removes the specified DefaultSource from this JSAP's DefaultSource chain.
     * If this specified DefaultSource is not currently in this JSAP's
     * DefaultSource chain, this method does nothing.
     *
     * @param ds the DefaultSource to remove from the DefaultSource chain.
     */
    public void unregisterDefaultSource(DefaultSource ds) {
        defaultSources.remove(ds);
    }

    /**
     * Returns a Defaults object representing the default values defined within
     * this JSAP's AbstractParameters themselves.
     *
     * @return a Defaults object representing the default values defined within
     * this JSAP's AbstractParameters themselves.
     */
    private Defaults getSystemDefaults() {
        Defaults defaults = new Defaults();
        for (Iterator i = paramsByDeclarationOrder.iterator(); i.hasNext();) {
            Parameter param = (Parameter) i.next();
            defaults.setDefault(param.getID(), param.getDefault());
        }
        return (defaults);
    }

    /**
     * Merges the specified Defaults objects, only copying Default values from
     * the source to the destination if they are NOT currently defined in the
     * destination.
     *
     * @param dest the destination Defaults object into which the source should
     * be merged.
     * @param src the source Defaults object.
     */
    private void combineDefaults(Defaults dest, Defaults src) {
        if (src != null) {
            for (Iterator i = src.idIterator(); i.hasNext();) {
                String paramID = (String) i.next();
                dest.setDefaultIfNeeded(paramID, src.getDefault(paramID));
            }
        }
    }

    /**
     * Returns a Defaults object representing the merged Defaults of every
     * DefaultSource in the DefaultSource chain and the default values specified
     * in the AbstractParameters themselves.
     *
     * @param exceptionMap the ExceptionMap object within which any encountered
     * exceptions will be returned.
     * @return a Defaults object representing the Defaults of the entire JSAP.
     * @see com.martiansoftware.jsap.DefaultSource#getDefaults(IDMap, ExceptionMap)
     */
    protected Defaults getDefaults(ExceptionMap exceptionMap) {
        Defaults defaults = new Defaults();
        IDMap idMap = getIDMap();
        for (Iterator dsi = defaultSources.iterator(); dsi.hasNext();) {
            DefaultSource ds = (DefaultSource) dsi.next();
            combineDefaults(defaults, ds.getDefaults(idMap, exceptionMap));
        }
        combineDefaults(defaults, getSystemDefaults());
        return (defaults);
    }

    /**
     * Registers the specified Parameter (i.e., Switch, FlaggedOption,
     * or UnflaggedOption) with this JSAP.
     *
     * <p>Registering an Parameter <b>locks</b> the parameter.
     * Attempting to change its properties (ID, flags, etc.) while it is locked
     * will result in a JSAPException.  To unlock an Parameter, it must
     * be unregistered from the JSAP.
     *
     * @param param the Parameter to register.
     * @throws JSAPException if this Parameter cannot be added. Possible
     * reasons include:
     * <ul>
     *     <li>Another Parameter with the same ID has already been
     *      registered.</li>
     *  <li>You are attempting to register a Switch or FlaggedOption with
     *      neither a short nor long flag.</li>
     *  <li>You are attempting to register a Switch or FlaggedOption with a long
     *      or short flag that is already
     *  defined in this JSAP.</li>
     *  <li>You are attempting to register a second greedy UnflaggedOption</li>
     * </ul>
     */
    public void registerParameter(Parameter param)
        throws JSAPException {
        String paramID = param.getID();

        if (paramsByID.containsKey(paramID)) {
            throw (
                new JSAPException(
                    "A parameter with ID '"
                        + paramID
                        + "' has already been registered."));
        }

        if (param instanceof Flagged) {
            Flagged f = (Flagged) param;
            if ((f.getShortFlagCharacter() == null)
                && (f.getLongFlag() == null)) {
                throw (
                    new JSAPException(
                        "FlaggedOption '"
                            + paramID
                            + "' has no flags defined."));
            }
            if (paramsByShortFlag.containsKey(f.getShortFlagCharacter())) {
                throw (
                    new JSAPException(
                        "A parameter with short flag '"
                            + f.getShortFlag()
                            + "' has already been registered."));
            }
            if (paramsByLongFlag.containsKey(f.getLongFlag())) {
                throw (
                    new JSAPException(
                        "A parameter with long flag '"
                            + f.getLongFlag()
                            + "' has already been registered."));
            }
        } else {
            if ((unflaggedOptions.size() > 0)
                && (((UnflaggedOption) unflaggedOptions
                    .get(unflaggedOptions.size() - 1))
                    .isGreedy())) {
                throw (
                    new JSAPException(
                        "A greedy unflagged option has already been registered;"
                            + " option '"
                            + paramID
                            + "' will never be reached."));
            }
        }

        if (param instanceof Option) {
            ((Option) param).register();
        }

        // if we got this far, it's safe to insert it.
        param.setLocked(true);
        paramsByID.put(paramID, param);
        paramsByDeclarationOrder.add(param);
        if (param instanceof Flagged) {
            Flagged f = (Flagged) param;
            if (f.getShortFlagCharacter() != null) {
                paramsByShortFlag.put(f.getShortFlagCharacter(), param);
            }
            if (f.getLongFlag() != null) {
                paramsByLongFlag.put(f.getLongFlag(), param);
            }
        } else if (param instanceof Option) {
            unflaggedOptions.add(param);
        }
    }

    /**
     * Unregisters the specified Parameter (i.e., Switch, FlaggedOption,
     * or UnflaggedOption) from this JSAP.  Unregistering an Parameter
     * also unlocks it, allowing changes to its properties (ID, flags, etc.).
     *
     * @param param the Parameter to unregister from this JSAP.
     */
    public void unregisterParameter(Parameter param) {
        if (paramsByID.containsKey(param.getID())) {

            if (param instanceof Option) {
                ((Option) param).unregister();
            }

            paramsByID.remove(param.getID());
            paramsByDeclarationOrder.remove(param);
            if (param instanceof Flagged) {
                Flagged f = (Flagged) param;
                paramsByShortFlag.remove(f.getShortFlagCharacter());
                paramsByLongFlag.remove(f.getLongFlag());
            } else if (param instanceof UnflaggedOption) {
                unflaggedOptions.remove(param);
            }
            param.setLocked(false);
        }
    }

    /**
     * Parses the specified command line array.  If no Exception is thrown, the
     * entire command line has been parsed successfully, and its results have
     * been successfully instantiated.
     *
     * @param args An array of command line arguments to parse.  This array is
     * typically provided in the application's main class' main() method.
     * @return a JSAPResult containing the resulting Objects.
     */
    public JSAPResult parse(String[] args) {
        Parser p = new Parser(this, args);
        return (p.parse());
    }

    /**
     * Parses the specified command line.  The specified command line is first
     * parsed into an array, much like the operating system does for the JVM
     * prior to calling your application's main class' main() method.  If no
     * Exception is thrown, the entire command line has been parsed
     * successfully, and its results have been successfully instantiated.
     *
     * @param cmdLine An array of command line arguments to parse.  This array
     * is typically provided in the application's main class' main() method.
     * @return a JSAPResult containing the resulting Objects.
     */
    public JSAPResult parse(String cmdLine) {
        String[] args = CommandLineTokenizer.tokenize(cmdLine);
        return (parse(args));
    }

    /**
     * Unregisters all registered AbstractParameters, allowing them to perform
     * their cleanup.
     */
    public void finalize() {
        Parameter[] params =
            (Parameter[]) paramsByDeclarationOrder.toArray(
                new Parameter[0]);
        int paramCount = params.length;
        for (int i = 0; i < paramCount; ++i) {
            unregisterParameter(params[i]);
        }
    }

}
