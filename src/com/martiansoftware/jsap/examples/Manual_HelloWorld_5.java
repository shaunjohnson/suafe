package com.martiansoftware.jsap.examples;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;

public class Manual_HelloWorld_5 {

	/** 
	 * Repeats the "Hello World" text multiple times.
	 * Decides whether to say "Hi" or "Hello" depending upon verbose switch.
	 * @param args the command line.
	 * @throws Exception for reasons made clear later in the manual.
	 */
	// @@snip:Manual_HelloWorld_5@@
	public static void main(String[] args) throws Exception {
		JSAP jsap = new JSAP();
		
		FlaggedOption opt1 = new FlaggedOption("count")
								.setStringParser(JSAP.INTEGER_PARSER)
								.setDefault("1") 
								.setRequired(true) 
								.setShortFlag('n') 
								.setLongFlag(JSAP.NO_LONGFLAG);
		
		jsap.registerParameter(opt1);
		
		Switch sw1 = new Switch("verbose")
						.setShortFlag('v')
						.setLongFlag("verbose");
		
		jsap.registerParameter(sw1);
		
		UnflaggedOption opt2 = new UnflaggedOption("name")
								.setStringParser(JSAP.STRING_PARSER)
								.setDefault("World")
								.setRequired(true)
								.setGreedy(true);
								
		jsap.registerParameter(opt2);
		
		JSAPResult config = jsap.parse(args);	

		// check whether the command line was valid, and if it wasn't,
		// display usage information and exit.
		if (!config.success()) {
			System.err.println();
			System.err.println("Usage: java "
								+ Manual_HelloWorld_5.class.getName());
			System.err.println("                "
								+ jsap.getUsage());
			System.err.println();
			System.exit(1);
		}
		
		String[] names = config.getStringArray("name");
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