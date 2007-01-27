/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

/*
 * 09/26/2002	ML	:	Modified parseShortForm() and parseLongForm() to allow
 *                      options that begin with a minus sign.  Previous
 *                      behavior assumed that the minus sign indicated another
 *                      flag and therefore no value for the current option.
 */
package com.martiansoftware.jsap;

import java.util.Iterator;

/**
 * The class that performs the actual parsing on a set of arguments.  This is 
 * created and managed by a JSAP;
 * developers should never need to access this class directly.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 * @see com.martiansoftware.jsap.JSAP
 */
class Parser {

    /**
     * A reference to the JSAP for which this Parser is working.
     */
    private JSAP config = null;

    /**
     * An Iterator through the declared unflagged options, in order of their 
     * declaration.
     */
    private Iterator unflaggedOptions = null;

    /**
     * A reference to the currently expected UnflaggedOption.
     */
    private UnflaggedOption curUnflaggedOption = null;

    /**
     * The array of arguments to parse.
     */
    private String[] args = null;

    /**
     * Boolean indicating whether this Parser has already been run (a parser 
     * can only run once).
     */
    private boolean parsed = false;

    /**
     * The result of the parse.
     */
    private JSAPResult result = null;

    /**
     * Creates a new Parser for the specified JSAP and argument list.
     * @param config the JSAP for which this Parser is working.
     * @param args the arguments to parse.
     */
    public Parser(JSAP config, String[] args) {
        this.config = config;
        this.args = args;
        this.parsed = false;
        this.result = new JSAPResult();
        this.unflaggedOptions = config.getUnflaggedOptionsIterator();
        advanceUnflaggedOption();
        
    }

    /**
     * Advances the current UnflaggedOption pointer to the next UnflaggedOption 
     * (by declaration order).
     * If there are no more expected UnflaggedOptions, the current 
     * UnflaggedOption pointer is set to null.
     */
    private void advanceUnflaggedOption() {
        if (unflaggedOptions.hasNext()) {
            curUnflaggedOption = (UnflaggedOption) unflaggedOptions.next();
        }
        else {
            curUnflaggedOption = null;
        }
    }

    /**
     * Instructs the specified parameter to parse the specified value, and 
     * stores the results in the working
     * JSAPResult.  Any ParseExceptions or IllegalMultipleDeclarationExceptions 
     * that are encountered
     * are associated with the specified parameter.
     * @param param the parameter to parse the value
     * @param valueToParse the value to parse
     */
    private void processParameter(Parameter param, 
        String valueToParse) {

        // if this argument has already been parsed at least once...
        if (result.getObject(param.getID()) != null) {
            if (((param instanceof FlaggedOption)
                && (!((FlaggedOption) param).allowMultipleDeclarations()))
                || (param instanceof Switch)
				|| (param instanceof QualifiedSwitch)
                || ((param instanceof UnflaggedOption)
                && (!((UnflaggedOption) param).isGreedy()))) {
                result.addException(param.getID(), 
                    new IllegalMultipleDeclarationException(param.getID()));
            }
        }

        if (param instanceof QualifiedSwitch) {
        	result.registerQualifiedSwitch(param.getID(), true);
        }
        
        try {
            result.add(param.getID(), param.parse(valueToParse));
        }
        catch (ParseException pe) {
            result.addException(param.getID(), pe);
        }
    }

    /**
     * Parses the next argument in the array.
     *
     * @param args the array containing the arguments
     * @param index the index of the next argument to parse
     * @return the index of the next argument to parse
     */
    private int parseArg(String[] args, int index) {
        if (args[index].startsWith("--") && !args[index].equals("--")) {
            return(parseLongForm(args, index));
        }
        else if (args[index].startsWith("-") && !args[index].equals("-") && !args[index].equals("--")) {
            return(parseShortForm(args, index));
        }
        else {
            return(parseUnflaggedOption(args, index));
        }
    }

    /**
     * Processes the default values for this parser's JSAP, and stores their 
     * values in the 
     * working JSAPResult
     */
    private void processDefaults() {
        Defaults defaults = config.getDefaults(result);
        for (Iterator i = defaults.idIterator(); i.hasNext();) {
            String paramID = (String) i.next();
            if (result.getObject(paramID) == null) {
                String[] paramValues = defaults.getDefault(paramID);
                if (paramValues != null) {
                    int z = paramValues.length;
                    for (int index = 0; index < z; ++index) {
                        processParameter(
                            config.getByID(paramID),
                            paramValues[index]);
                    }
                }
            }
        }
    }

    /**
     * Checks the current JSAPResult against the JSAP's configuration, and 
     * stores a
     * RequiredParameterMissingException in the JSAPResult for any missing 
     * parameters.
     */
    private void enforceRequirements() {
        IDMap idMap = config.getIDMap();
        for (Iterator i = idMap.idIterator(); i.hasNext();) {
            String id = (String) i.next();
            Object o = config.getByID(id);
            if (o instanceof Option) {
                Option option = (Option) o;
                if (option.required() && (result.getObject(id) == null)) {
                	
                	if (result.getException(id) == null) {
	                    result.addException(option.getID(), 
	                        new RequiredParameterMissingException(id));
                	}
                }
            }
        }
    }

    /**
     * Runs the parser.
     * @return a JSAPResult representing the parsed data.
     */
    public JSAPResult parse() {
        if (parsed) {
            result.addException(null, 
                new JSAPException("This Parser has already run."));
        }
        else {
        	result.setValuesFromUser(true);
        	preregisterQualifiedSwitches();
            int z = args.length;
            int i = 0;
            while (i < z) {
                i = parseArg(args, i);
            }
            result.setValuesFromUser(false);
            processDefaults();
            enforceRequirements();
            this.parsed = true;
        }
        return(result);
    }

    /**
     * Loops through all parameters, informing the JSAPResult
     * of any QualifiedSwitches so it can assume they're not present.
     */
    private void preregisterQualifiedSwitches() {
    	for (Iterator iter = config.getIDMap().idIterator(); iter.hasNext();) {
    		String thisID = (String) iter.next();
    		Parameter param = config.getByID(thisID);
    		if (param instanceof QualifiedSwitch) {
    			result.registerQualifiedSwitch(thisID, false);
    		}
    	}
    		
    }
    /**
     * Parses the long form of an Option or Switch (i.e., preceded by a double 
     * hyphen).
     *
     * @param  args    the argument array being parsed
     * @param  index   the index of the current argument to parse
     * @return the index of the next argument to parse
     */
    private int parseLongForm(String[] args, int index) {
        int equalsIndex = args[index].indexOf('=');
        int colonIndex = args[index].indexOf(':'); // KPB
        String paramFlag = null;
        String paramEquals = null;
        // KPB<<<<<<
        String paramColon = null; 
        // if no equals sign and no colon for QualifiedSwitches, this whole argument is the long flag
        if (equalsIndex == -1 && colonIndex == -1) {
            paramFlag = args[index].substring(2);
        }
        if (equalsIndex != -1) {
            // there's a value assignment in this argument, too; possible colons are taken as part of the value
            paramFlag = args[index].substring(2, equalsIndex);
            paramEquals = args[index].substring(equalsIndex + 1);
        }
        if (equalsIndex == -1 && colonIndex != -1) {
            // we have a QualifiedSwitch
            paramFlag = args[index].substring(2, colonIndex);
            paramColon = args[index].substring(colonIndex); // parameter starts with ':'
        }
        // <<<<<<KPB
        ++index;

        // we'll need to reference the appropriate parameter both as a Flagged 
        // and as 
        // an Parameter down below.
        Flagged option = config.getByLongFlag(paramFlag);
        Parameter param = (Parameter) option;

        // unknown long flag
        if (option == null) {
            result.addException(null, new UnknownFlagException(paramFlag));
        }
        else {
            // if it's a switch, first check to make sure no value is specified.
            if (option instanceof Switch) {
                if (equalsIndex != -1) {
                    result.addException(param.getID(), 
                        new SyntaxException("Switch \"" 
                        + paramFlag 
                        + "\" does not take any parameters."));         
                }
                else {
                    processParameter(param, null);
                }
            } else if (param instanceof QualifiedSwitch) {
            	String paramValue = null;
            	if (colonIndex == -1) {
            		processParameter(param, null);
            	} else {
            		processParameter(param, args[index-1].substring(colonIndex + 1));
            	}
            	
            } else {
            	// it's an option
            	if (equalsIndex == -1) {
                    // no "=" supplied on command line, so next item must 
                    // contain the value

//					ML: Changed this and the corresponding line in
//					parseShortForm.  Longer version (commented below) would
//                  not allow values that begin with a minus sign.
//					if ((index >= args.length) || args[index].startsWith("-")) {
                    if (index >= args.length) {
                        result.addException(param.getID(), 
                            new SyntaxException("No value specified for option \"" 
                            + paramFlag + "\""));
                    }
                    else {
                        paramEquals = args[index];
                        ++index;
                        processParameter(param, paramEquals);
                    }
                }
                else {
                    processParameter(param, paramEquals);
                }
            }
        }
        return(index);
    }

    /**
     * Parses the short form of an Option or Switch (i.e., preceded by a single 
     * hyphen).
     *
     * @param  args    the argument array being parsed
     * @param  index   the index of the current argument to parse
     * @return the index of the next argument to parse
     */
    private int parseShortForm(String[] args, int index) {
        Character paramFlag = null;
        int equalsIndex = args[index].indexOf('=');
        int parseTo = args[index].length();
        if (equalsIndex != -1) {
            parseTo = equalsIndex;
        }

        // loop through all the single characters up to the equals sign (if
        // there is one) or the end
        // of the argument
        for (int charPos = 1; charPos < parseTo; ++charPos) {

            // paramFlag is the single short character used as a flag.  
            // multiple flags might exist here,
            // which is why we're looping through them.
            paramFlag = new Character(args[index].charAt(charPos));

            // we'll need to reference the appropriate parameter both as a 
            // Flagged and as 
            // an Parameter down below.
            Flagged option = config.getByShortFlag(paramFlag);
            Parameter param = (Parameter) option;

            // unknown flag
            if (option == null) {
                result.addException(null, new UnknownFlagException(paramFlag));
            }
            else {
                // flag is a switch.  if it is, first check to make sure a value
                // isn't specified,
                // then process it.
                if (option instanceof Switch) {
                    if (charPos == (equalsIndex - 1)) {
                        result.addException(param.getID(), 
                            new SyntaxException("Switch \"" 
                            + paramFlag 
                            + "\" does not take any parameters."));
                    } else {
//                        // KPB-start
//                        // Qualified switches have a format like "-o:value"
//                        if (parseTo >= "-o:".length() && args[index].charAt(charPos+1) == ':') {
//                            // we have a QualifiedSwitch
//                            processParameter(param, args[index].substring(charPos+1)); 
//                            break; // args[index] completely parsed, so get out of loop
//                        }
//                        else {
                            processParameter(param, null);
//                        }
//                        //>>>>>KPB
                    }
                    // flag is not a switch, so we first check to make sure there IS
                    // a value specified.
                } else if(option instanceof QualifiedSwitch) {
                	// flag is a QualifiedSwitch.
                	if ((args[index].length() > (charPos + 1))
							&& args[index].charAt(charPos+1) == ':') {
                		// the QualifiedSwitch is, in fact, qualified
                		processParameter(param, args[index].substring(charPos+2));// ':' must be part of the argument
                		break; // args[index] completely parsed, so get out of loop
                	}
                	else {
                		processParameter(param, null);
                	}
                	
                } else {
                	// it's an option
                    String paramEquals = null;

                    /* We now allow stuff like -b12.
                     * 
                     * if (charPos != (parseTo - 1)) {
                        result.addException(param.getID(), 
                            new SyntaxException("No value specified for option \"" 
                            + paramFlag + "\"."));
                    }
                    else {*/
                    
                    // if there's an equals sign, the value is whatever is 
                    // on the right
                    if (equalsIndex != -1) {
                    	paramEquals = 
                    		args[index].substring(equalsIndex + 1);
                    	processParameter(param, paramEquals);
                    }
                    else {
                    	if (charPos < parseTo - 1) {
                    		// the value is just after the short option (no space)
                    		paramEquals = args[index].substring(charPos + 1, parseTo);
                    		processParameter(param, paramEquals);
                    		break;
                    	}
                    	else {
                    		// otherwise, it's the next argument.
                    		++index;
                    		
                    		//					ML: Changed this and the corresponding line in
                    		//					parseLongForm.  Longer version (commented below) would
                    		//                  not allow values that begin with a minus sign.
                    		//							if ((index >= args.length) 
                    		//								|| args[index].startsWith("-")) {
                    		if (index >= args.length) {
                    			result.addException(param.getID(), 
                    					new SyntaxException(
                    							"No value specified for option \"" 
                    							+ paramFlag + "\"."));
                    		}
                    		else {
                    			paramEquals = args[index];
                    			processParameter(param, paramEquals);
                    		}
                    	}
                    }
                }
            }
        }

        ++index;
        return(index);
    }

    /**
     * Parses the unflagged form of an Option (i.e., preceded by a no hyphens).
     *
     * @param  args    the argument array being parsed
     * @param  index   the index of the current argument to parse
     * @return the index of the next argument to parse
     */
    private int parseUnflaggedOption(String[] args, int index) {
        if (curUnflaggedOption != null) {
            processParameter(curUnflaggedOption, args[index]);
            if (!curUnflaggedOption.isGreedy()) {
                advanceUnflaggedOption();
            }
        }
        else {
            result.addException(null, new JSAPException("Unexpected argument: " 
                + args[index]));
        }
        return(index + 1);
    }

}
