package com.martiansoftware.jsap.examples;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.QualifiedSwitch;
import com.martiansoftware.jsap.SimpleJSAP;
import com.martiansoftware.jsap.UnflaggedOption;

public class Manual_HelloWorld_Simple {

	/** 
	 * Repeats the "Hello World" text multiple times.
	 * Decides whether to say "Hi" or "Hello" depending upon verbose switch.
	 * Uses the new QualifiedSwitch to set the language of verbosity
	 * Uses SimpleJSAP
	 * @param args the command line.
	 * @throws Exception for reasons made clear later in the manual.
	 */
	// @@snip:Manual_HelloWorld_Simple@@
	public static void main(String[] args) throws Exception {
		SimpleJSAP jsap = new SimpleJSAP( 
			"MyProgram", 
			"Repeats \"Hello, world!\" multiple times",
			new Parameter[] {
				new FlaggedOption( "count", JSAP.INTEGER_PARSER, "1", JSAP.REQUIRED, 'n', JSAP.NO_LONGFLAG, 
					"The number of times to say hello." ),
				new QualifiedSwitch( "verbose", JSAP.STRING_PARSER, JSAP.NO_DEFAULT, JSAP.NOT_REQUIRED, 'v', "verbose", 
					"Requests verbose output." ).setList( true ).setListSeparator( ',' ),
				new UnflaggedOption( "name", JSAP.STRING_PARSER, "World", JSAP.REQUIRED, JSAP.GREEDY, 
					"One or more names of people you would like to greet." )
			}
		);
		
		JSAPResult config = jsap.parse(args);	
		if ( jsap.messagePrinted() ) System.exit( 1 );
				
		String[] names = config.getStringArray("name");
		String[] languages = config.getStringArray("verbose");
		for (int i = 0; i < languages.length; ++i) {
			System.out.println("language=" + languages[i]);
		}
		for (int i = 0; i < config.getInt("count"); ++i) {
			for (int j = 0; j < names.length; ++j) {
				System.out.println((config.getBoolean("verbose") ? "Hello" : "Hi")
								+ ", "
								+ names[j]
								+ "!");
			}
		}
	}
	// @@endSnip@@
	
}