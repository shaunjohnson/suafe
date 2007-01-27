/*
 * Created on Sep 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.martiansoftware.jsap.examples;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;

/**
 * A reimplementation of Manual_HelloWorld_8 that loads the JSAP
 * configuration from an XML file.
 * 
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class Manual_HelloWorld_9 {

	// @@snip:Manual_HelloWorld_9@@
	public static void main(String[] args) throws Exception {
		JSAP jsap = new JSAP(Manual_HelloWorld_9.class.getResource("Manual_HelloWorld_9.jsap"));
		
		JSAPResult config = jsap.parse(args);	

		if (!config.success()) {
			
			System.err.println();

			// print out specific error messages describing the problems
			// with the command line, THEN print usage, THEN print full
			// help.  This is called "beating the user with a clue stick."
			for (java.util.Iterator errs = config.getErrorMessageIterator();
					errs.hasNext();) {
				System.err.println("Error: " + errs.next());
			}
			
			System.err.println();
			System.err.println("Usage: java "
								+ Manual_HelloWorld_9.class.getName());
			System.err.println("                "
								+ jsap.getUsage());
			System.err.println();
			System.err.println(jsap.getHelp());
			System.exit(1);
		}
		
		String[] names = config.getStringArray("name");
		String[] languages = config.getStringArray("verbose");
		if (languages.length == 0) languages = new String[] {"en"};
		
		for (int lang = 0; lang < languages.length; ++lang) {
			for (int i = 0; i < config.getInt("count"); ++i) {
				for (int j = 0; j < names.length; ++j) {
					System.out.println((config.getBoolean("verbose") ? getVerboseHello(languages[lang]) : "Hi")
									+ ", "
									+ names[j]
									+ "!");
				}
			}
		}
	}
	
	private static String getVerboseHello(String language) {
		if ((language == null) || "en".equalsIgnoreCase(language)) {
			return("Hello");
		} else if ("de".equalsIgnoreCase(language)) {
			return("Guten Tag");
		} else {
			return("(Barely audible grunt)");
		}
	}	
	// @@endSnip@@
	
}
