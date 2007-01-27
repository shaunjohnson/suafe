package com.martiansoftware.jsap.examples;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;

public class Manual_HelloWorld_2 {

	/** 
	 * Repeats the "Hello World" text multiple times.
	 * @param args the command line.
	 * @throws Exception for reasons made clear later in the manual.
	 */
	// @@snip:Manual_HelloWorld_2@@
	public static void main(String[] args) throws Exception {
		JSAP jsap = new JSAP();
		
		// create a flagged option we'll access using the id "count".
		// it's going to be an integer, with a default value of 1.
		// it's required (which has no effect since there's a default value)
		// its short flag is "n", so a command line containing "-n 5"
		//    will print our message five times.
		// it has no long flag.
		FlaggedOption opt1 = new FlaggedOption("count")
								.setStringParser(JSAP.INTEGER_PARSER)
								.setDefault("1") 
								.setRequired(true) 
								.setShortFlag('n') 
								.setLongFlag(JSAP.NO_LONGFLAG);
		
		jsap.registerParameter(opt1);

		JSAPResult config = jsap.parse(args);	

		for (int i = 0; i < config.getInt("count"); ++i) {
			System.out.println("Hello, World!");
		}
	}
	// @@endSnip@@
}
