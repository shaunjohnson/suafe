package com.martiansoftware.jsap.examples;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;


public class Manual_HelloWorld_3 {

	/** 
	 * Repeats the "Hello World" text multiple times.
	 * Decides whether to say "Hi" or "Hello" depending upon verbose switch.
	 * @param args the command line.
	 * @throws Exception for reasons made clear later in the manual.
	 */
	// @@snip:Manual_HelloWorld_3@@
	public static void main(String[] args) throws Exception {
		JSAP jsap = new JSAP();
		
		FlaggedOption opt1 = new FlaggedOption("count")
								.setStringParser(JSAP.INTEGER_PARSER)
								.setDefault("1") 
								.setRequired(true) 
								.setShortFlag('n') 
								.setLongFlag(JSAP.NO_LONGFLAG);
		
		jsap.registerParameter(opt1);
		
		// create a switch we'll access using the id "verbose".
		// it has the short flag "-v" and the long flag "--verbose"
		// this will govern whether we say "Hi" or "Hello".
		Switch sw1 = new Switch("verbose")
						.setShortFlag('v')
						.setLongFlag("verbose");
		
		jsap.registerParameter(sw1);
		
		JSAPResult config = jsap.parse(args);	

		for (int i = 0; i < config.getInt("count"); ++i) {
			System.out.println((config.getBoolean("verbose") ? "Hello" : "Hi")
								+ ", World!");
		}
		
	}
	// @@endSnip@@
}