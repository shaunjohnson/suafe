/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import java.util.Iterator;
import java.util.List;

import com.martiansoftware.util.StringUtils;

/** A simple interface to {@link com.martiansoftware.jsap.JSAP} that handles directly help,
 * explanation and an array of parameters.
 * 
 * <P>More precisely, instances of this class behave exactly like those of
 * {@link com.martiansoftware.jsap.JSAP}, but additionally require a command name, an explanation
 * (a wordy description) and an array of parameters (which will be registered automatically). 
 * A switch activated by <samp>--help</samp> is always
 * registered under the ID <samp>help</samp>. 
 * 
 * <p>A message will be automatically printed upon invocation
 * of the <code>parse()</code> methods if an error occurs, or if the help switch is detected. In this
 * case, {@link #messagePrinted()} will return true, and the caller may check this condition
 * to stop its actions.
 * 
 * <P>The screen width used to format the help text may be set using {@link #setScreenWidth(int)}.
 * The formatter will preserve newlines.
 * <i>Note: as of 2.0a non-breaking spaces are temporarily disabled until some console encoding
 * issues have been worked out. - ML</i>
 * 
 * @author Sebastiano Vigna
 */
public class SimpleJSAP extends JSAP {

	/** The screen witdh used for formatting. */
	private int screenWidth = DEFAULT_SCREENWIDTH;
	
	/** A wordy explanation. */
	private String explanation;

	/** True if the last parsing caused the help text to be printed. */
	private boolean messagePrinted;
	
	/** The name of the command that will appear in the help message. */
	final private String name;
	
	/** Creates a new simple JSAP with default screen width. 
	 * 
	 * @param name the name of the command for which help will be printed.
	 * @param explanation a wordy explanation of the command, or <code>null</code> for no explanation.
	 * @param parameter an array of parameters, which will be registered for you, or <code>null</code>.
	 */

	public SimpleJSAP( final String name, final String explanation, final Parameter[] parameter ) throws JSAPException {
		super();

		this.name = name;
		this.explanation = explanation;
		
		final Switch help = new Switch( "help", JSAP.NO_SHORTFLAG, "help" );
		help.setHelp( "Prints this help message." );
		this.registerParameter( help );

		if ( parameter != null ) 
			for( int i = 0; i < parameter.length; i++ ) this.registerParameter( parameter[ i ] );
	}

	/** Creates a new simple JSAP with default screen width. 
	 * 
	 * @param name the name of the command for which help will be printed.
	 * @param explanation a wordy explanation of the command, or <code>null</code> for no explanation.
	 */

	public SimpleJSAP( final String name, final String explanation ) throws JSAPException {
		this( name, explanation, null );
	}
		
	/** Creates a new simple JSAP with a help switch, no explanation and default screen width. 
	 * 
	 * @param name the name of the command for which help will be printed.
	 */

	public SimpleJSAP( final String name ) throws JSAPException {
		this( name, null );
	}
	
	public JSAPResult parse( String arg ) {
		JSAPResult jsapResult = super.parse( arg );
		messagePrinted = printMessageIfUnsuccessfulOrHelpRequired( jsapResult );
		return jsapResult;
	}

	public JSAPResult parse( String[] arg ) {
		JSAPResult jsapResult = super.parse( arg );
		messagePrinted = printMessageIfUnsuccessfulOrHelpRequired( jsapResult );
		return jsapResult;
	}

	/** Checks the given JSAP result for errors or help requests and acts accordingly.
	 * 
	 * @param jsapResult the result of a JSAP parsing.
	 * @return true if some message has been printed (i.e., if there was an error or a help request).
	 */
	
	private boolean printMessageIfUnsuccessfulOrHelpRequired( final JSAPResult jsapResult ) {
		if ( ! ( jsapResult.success() ) || jsapResult.getBoolean( "help" ) ) {

			//  To avoid spurious missing argument errors we never print errors if help is required.
			if ( ! jsapResult.getBoolean( "help" ) ) {
				for ( Iterator err = jsapResult.getErrorMessageIterator(); err.hasNext(); )
					System.err.println( "Error: " + err.next() );
				
				return true;
			}
			System.err.println();
			System.err.println( "Usage:" );

			List l = StringUtils.wrapToList( name + " " + getUsage(), screenWidth );
			for( Iterator i = l.iterator(); i.hasNext(); ) System.err.println( "  " + i.next().toString() );

			if ( explanation != null ) {
				System.err.println();
				l = StringUtils.wrapToList( explanation, screenWidth );
				for( Iterator i = l.iterator(); i.hasNext(); ) System.err.println( i.next() );
			}
			
			System.err.println();
			System.err.println();
			System.err.println( getHelp( screenWidth ) );
			return true;
		}
		
		return false;
	}

	/** Returns the current screen width.
	 * 
	 * <P>This value will be passed to {@link com.martiansoftware.jsap.JSAP#getHelp(int)}, and used
	 * to format the explanation.
	 * 
	 * @return the current screen width.
	 */
	public int getScreenWidth() {
		return screenWidth;
	}
	
	/** Sets the screen width.
	 * @param screenWidth the new screen width.
	 * @return this simple JSAP.
	 */
	
	public SimpleJSAP setScreenWidth( final int screenWidth ) {
		this.screenWidth = screenWidth;
		return this;
	}
	
	/** Returns true if the last parsing caused the a message to be printed.
	 * 
	 * @return true if the last parsing caused a message to be printed.
	 */
	
	public boolean messagePrinted() {
		return messagePrinted;
	}
}