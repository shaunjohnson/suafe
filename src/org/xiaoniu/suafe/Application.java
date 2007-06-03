/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.xiaoniu.suafe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.frames.MainFrame;
import org.xiaoniu.suafe.reports.GenericReport;
import org.xiaoniu.suafe.reports.StatisticsReport;
import org.xiaoniu.suafe.reports.SummaryReport;
import org.xiaoniu.suafe.resources.ResourceUtil;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;

/**
 * Application starting point. If no arguments are specified then the 
 * application GUI is initiated. Otherwise, the command line application
 * equivalent is initiated.
 * 
 * @author Shaun Johnson
 */
public class Application {

	/**
	 * Application starting point.
	 * 
	 * @param args Application arguments
	 */
	public static void main(String[] args) {
		Document.initialize();
		
		if (args.length == 0) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			}
			catch (Exception e) {
				// Do nothing
			}
			
			JFrame main = new MainFrame();
			
			main.setVisible(true);			
		}
		else {
			processArguments(args);
		}
	}
	
	/**
	 * Processes command line arguments and initiates command execution.
	 * 
	 * @param args User specified arguments.
	 */
	private static void processArguments(String[] args) {
		JSAP jsap = new JSAP();
		
		try {
			FlaggedOption opt;
			Switch swtch;
			
			opt = new FlaggedOption(Constants.ARGS_INPUT_FILE)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(Constants.ARGS_INPUT_FILE_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_INPUT_FILE_LONGFLAG);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.inputfile.help")));

			opt = new FlaggedOption(Constants.ARGS_OUTPUT_FILE)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(Constants.ARGS_OUTPUT_FILE_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_OUTPUT_FILE_LONGFLAG);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.outputfile.help"))); 

			swtch = new Switch(Constants.ARGS_HELP)
				.setShortFlag(Constants.ARGS_HELP_SHORTFLAG)
				.setLongFlag(Constants.ARGS_HELP);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.help.help")));
			
			swtch = new Switch(Constants.ARGS_VERBOSE_HELP)
				.setShortFlag(JSAP.NO_SHORTFLAG)
				.setLongFlag(Constants.ARGS_VERBOSE_HELP);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.verbose.help")));
			
			swtch = new Switch(Constants.ARGS_VERSION)
				.setShortFlag(Constants.ARGS_VERSION_SHORTFLAG)
				.setLongFlag(Constants.ARGS_VERSION);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.version.help")));
			
			opt = new FlaggedOption(Constants.ARGS_NAME)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_NAME);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.name.help")));
			
			opt = new FlaggedOption(Constants.ARGS_GROUPS)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GROUPS)
				.setList(true)
				.setListSeparator(',');
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.groups.help")));
			
			opt = new FlaggedOption(Constants.ARGS_USERS)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_USERS)
				.setList(true)
				.setListSeparator(',');
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.users.help")));
			
			opt = new FlaggedOption(Constants.ARGS_REPOS)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_REPOS);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.repos.help")));
			
			opt = new FlaggedOption(Constants.ARGS_PATH)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_PATH);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.path.help")));
			
			opt = new FlaggedOption(Constants.ARGS_USER)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_USER);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.user.help")));
			
			opt = new FlaggedOption(Constants.ARGS_GROUP)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GROUP);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.group.help")));
			
			opt = new FlaggedOption(Constants.ARGS_ACCESS)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_ACCESS);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.access.help")));
			
			opt = new FlaggedOption(Constants.ARGS_NEW_NAME)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_NEW_NAME);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.newname.help")));
			
			opt = new FlaggedOption(Constants.ARGS_NEW_REPOS)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_NEW_REPOS);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.newrepos.help")));
			
			opt = new FlaggedOption(Constants.ARGS_NEW_PATH)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_NEW_PATH);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.newpath.help")));
			
			opt = new FlaggedOption(Constants.ARGS_NEW_USER)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_NEW_USER);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.newuser.help")));
			
			opt = new FlaggedOption(Constants.ARGS_NEW_GROUP)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_NEW_GROUP);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.newgroup.help")));
			
			opt = new FlaggedOption(Constants.ARGS_NEW_ACCESS)
				.setStringParser(JSAP.STRING_PARSER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_NEW_ACCESS);
			
			jsap.registerParameter(opt.setHelp(ResourceUtil.getString("application.args.newaccess.help")));
			
			swtch = new Switch(Constants.ARGS_CLONE_USER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_CLONE_USER);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.cloneuser.help")));
			
			swtch = new Switch(Constants.ARGS_EDIT_USER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_EDIT_USER);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.edituser.help")));
			
			swtch = new Switch(Constants.ARGS_DELETE_USER)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_DELETE_USER);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.deleteuser.help")));
			
			swtch = new Switch(Constants.ARGS_ADD_GROUPS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_ADD_GROUPS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.addgroups.help")));
			
			swtch = new Switch(Constants.ARGS_COUNT_USERS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_COUNT_USERS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.countusers.help")));
			
			swtch = new Switch(Constants.ARGS_GET_USERS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_USERS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getusers.help")));
			
			swtch = new Switch(Constants.ARGS_REMOVE_GROUPS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_REMOVE_GROUPS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.removegroups.help")));
			
			swtch = new Switch(Constants.ARGS_GET_USER_GROUPS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_USER_GROUPS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getusergroups.help")));
			
			swtch = new Switch(Constants.ARGS_GET_USER_RULES)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_USER_RULES);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getuserrules.help")));
			
			swtch = new Switch(Constants.ARGS_ADD_GROUP)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_ADD_GROUP);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.addgroup.help")));
			
			swtch = new Switch(Constants.ARGS_CLONE_GROUP)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_CLONE_GROUP);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.clonegroup.help")));

			swtch = new Switch(Constants.ARGS_EDIT_GROUP)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_EDIT_GROUP);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.editgroup.help")));
			
			swtch = new Switch(Constants.ARGS_DELETE_GROUP)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_DELETE_GROUP);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.deletegroup.help")));
			
			swtch = new Switch(Constants.ARGS_ADD_MEMBERS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_ADD_MEMBERS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.addmembers.help")));
			
			swtch = new Switch(Constants.ARGS_REMOVE_MEMBERS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_REMOVE_MEMBERS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.removemembers.help")));
			
			swtch = new Switch(Constants.ARGS_COUNT_GROUPS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_COUNT_GROUPS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.countgroups.help")));
			
			swtch = new Switch(Constants.ARGS_GET_GROUPS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_GROUPS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getgroups.help")));
	
			swtch = new Switch(Constants.ARGS_GET_GROUP_MEMBERS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_GROUP_MEMBERS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getgroupmembers.help")));
			
			swtch = new Switch(Constants.ARGS_GET_GROUP_GROUP_MEMBERS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_GROUP_GROUP_MEMBERS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getgroupgroupmembers.help")));
			
			swtch = new Switch(Constants.ARGS_GET_GROUP_USER_MEMBERS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_GROUP_USER_MEMBERS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getgroupusermembers.help")));
			
			swtch = new Switch(Constants.ARGS_GET_GROUP_RULES)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_GROUP_RULES);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getgrouprules.help")));
			
			swtch = new Switch(Constants.ARGS_EDIT_REPOS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_EDIT_REPOS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.editrepos.help")));
			
			swtch = new Switch(Constants.ARGS_DELETE_REPOS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_DELETE_REPOS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.deleterepos.help")));
			
			swtch = new Switch(Constants.ARGS_COUNT_REPOS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_COUNT_REPOS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.countrepos.help")));
			
			swtch = new Switch(Constants.ARGS_GET_REPOS)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_REPOS);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getrepos.help")));
			
			swtch = new Switch(Constants.ARGS_GET_REPOS_RULES)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_REPOS_RULES);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getreposrules.help")));
			
			swtch = new Switch(Constants.ARGS_ADD_RULE)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_ADD_RULE);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.addrule.help")));
			
			swtch = new Switch(Constants.ARGS_EDIT_RULE)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_EDIT_RULE);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.editrule.help")));
			
			swtch = new Switch(Constants.ARGS_DELETE_RULE)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_DELETE_RULE);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.deleterule.help")));
			
			swtch = new Switch(Constants.ARGS_COUNT_RULES)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_COUNT_RULES);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.countrules.help")));
			
			swtch = new Switch(Constants.ARGS_GET_RULES)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_GET_RULES);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.getrules.help")));

			swtch = new Switch(Constants.ARGS_STATISTICS_REPORT)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_STATISTICS_REPORT);
		
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.statisticsreport.help")));

			swtch = new Switch(Constants.ARGS_SUMMARY_REPORT)
				.setShortFlag(JSAP.NO_SHORTFLAG) 
				.setLongFlag(Constants.ARGS_SUMMARY_REPORT);
			
			jsap.registerParameter(swtch.setHelp(ResourceUtil.getString("application.args.summaryreport.help")));
			
			// Process arguments
			JSAPResult config = jsap.parse(args);
			
	        // Display usage if parsing was unsuccessful
	        if (!config.success()) {
	        	System.err.println();
	        	System.err.println(ResourceUtil.getString("application.args.invalidsyntax"));
	        	System.err.println();
	        	
//	        	Exception[] exceptions = config.getExceptionArray(null);
//	        	
//	        	for (Exception exception : exceptions) {
//	        		System.err.println(exception.getMessage());
//	        	}
	        	
	        	System.exit(1);
	        }
	        
	        // Arguments parsed properly, continue onto command execution
	        executeCommands(jsap, config);
		} 
		catch (JSAPException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Outputs application name and version information.
	 * 
	 * @param stream Output stream
	 */
	private static void displayVersion(PrintStream stream) {
		stream.println(ResourceUtil.getString("application.nameversion"));
	}
	
	/**
	 * Outputs basic command line usage.
	 * 
	 * @param stream Output stream
	 * @param jsap JSAP results
	 */
	private static void displayUsage(PrintStream stream, JSAP jsap) {
        stream.println();
        stream.println(ResourceUtil.getString("application.nameversion"));
        stream.println();
        stream.println(ResourceUtil.getString("application.args.usage"));
        stream.println("  " + jsap.getUsage());
        stream.println();
        stream.println(ResourceUtil.getString("application.args.detailedusage"));
        stream.println(jsap.getHelp());
        stream.println();
	}

	/**
	 * Outputs verbose/detailed command line usage.
	 * 
	 * @param out Output stream
	 * @param jsap JSAP results.
	 */
	private static void displayVerboseHelp(PrintStream out, JSAP jsap) {
		final String SUAFE_EXECUTABLE = ResourceUtil.getString("application.args.executablepath");
		
		displayUsage(System.out, jsap);
		
		out.println(ResourceUtil.getString("application.args.verbosehelp"));
		out.println(ResourceUtil.getString("application.args.verbosehelp.summary"));
		
		Object[] args = new Object[11];
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_INPUT_FILE_LONGFLAG;
		args[2] = Constants.ARGS_OUTPUT_FILE_LONGFLAG;
		args[3] = Constants.ARGS_CLONE_USER;
		args[4] = Constants.ARGS_NAME;
		args[5] = Constants.ARGS_NEW_NAME;
		args[6] = Constants.ARGS_COUNT_USERS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.inputoutput", args));
		
		out.println(ResourceUtil.getString("application.args.verbose.adduser"));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_CLONE_USER;
		args[2] = Constants.ARGS_NAME;
		args[3] = Constants.ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.cloneuser", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_EDIT_USER;
		args[2] = Constants.ARGS_NAME;
		args[3] = Constants.ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.edituser", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_DELETE_USER;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deleteuser", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_ADD_GROUPS;
		args[2] = Constants.ARGS_NAME;
		args[3] = Constants.ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addgroups", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_REMOVE_GROUPS;
		args[2] = Constants.ARGS_NAME;
		args[3] = Constants.ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.removegroups", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_COUNT_USERS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countusers", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_USERS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getusers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_USER_GROUPS;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getusergroups", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_USER_RULES;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getuserrules", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_USER_RULES;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addgroup", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_CLONE_GROUP;
		args[2] = Constants.ARGS_NAME;
		args[3] = Constants.ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.clonegroup", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_EDIT_GROUP;
		args[2] = Constants.ARGS_NAME;
		args[3] = Constants.ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.editgroup", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_DELETE_GROUP;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deletegroup", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_ADD_MEMBERS;
		args[2] = Constants.ARGS_USERS;
		args[3] = Constants.ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addmembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_REMOVE_MEMBERS;
		args[2] = Constants.ARGS_USERS;
		args[3] = Constants.ARGS_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.removemembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_COUNT_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countgroups", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_GROUPS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroups", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_GROUP_MEMBERS;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroupmembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_GROUP_GROUP_MEMBERS;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroupgroupmembers", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_GROUP_USER_MEMBERS;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgroupusermembers", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_GROUP_RULES;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getgrouprules", args));		

		out.println(ResourceUtil.getString("application.args.verbose.addrepos"));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_EDIT_REPOS;
		args[2] = Constants.ARGS_NAME;
		args[3] = Constants.ARGS_NEW_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.editrepos", args));	

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_DELETE_REPOS;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deleterepos", args));	
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_COUNT_REPOS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countrepos", args));	

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_REPOS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getrepos", args));	

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_REPOS_RULES;
		args[2] = Constants.ARGS_NAME;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getreposrules", args));	

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_ADD_RULE;
		args[2] = Constants.ARGS_REPOS;
		args[3] = Constants.ARGS_PATH;
		args[4] = Constants.ARGS_USER;
		args[5] = Constants.ARGS_GROUP;
		args[6] = Constants.ARGS_ACCESS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.addrule", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_EDIT_RULE;
		args[2] = Constants.ARGS_REPOS;
		args[3] = Constants.ARGS_PATH;
		args[4] = Constants.ARGS_USER;
		args[5] = Constants.ARGS_GROUP;
		args[6] = Constants.ARGS_NEW_REPOS;
		args[7] = Constants.ARGS_NEW_PATH;
		args[8] = Constants.ARGS_NEW_USER;
		args[9] = Constants.ARGS_NEW_GROUP;
		args[10] = Constants.ARGS_NEW_ACCESS;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.editrule", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_DELETE_RULE;
		args[2] = Constants.ARGS_REPOS;
		args[3] = Constants.ARGS_PATH;
		args[4] = Constants.ARGS_USER;
		args[5] = Constants.ARGS_GROUP;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.deleterule", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_COUNT_RULES;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.countrules", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_GET_RULES;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.getrules", args));

		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_STATISTICS_REPORT;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.statisticsreport", args));
		
		args[0] = SUAFE_EXECUTABLE;
		args[1] = Constants.ARGS_SUMMARY_REPORT;
		out.println(ResourceUtil.getFormattedString("application.args.verbose.summaryreport", args));
	}
	
	/**
	 * Executes commands specified as application arguments parsed by JSAP.
	 * Output is directed to System.out by default or to an output file if one
	 * is specified. 
	 * 
	 * Application help, verbose help and version commands are processed 
	 * immediately. All remaining arguments are ignored.
	 * 
	 * If neither of these commands are specified then the arguments are
	 * checked for an input file directive. If no input file was specified
	 * then input is pulled from System.in. Finally, all remaining commands
	 * are executed against the specified input stream.
	 * 
	 * @param jsap JSAP results
	 * @param config JSAP configuration
	 */
	private static void executeCommands(JSAP jsap, JSAPResult config) {
		PrintStream out = null;
		
		try {
			// Initialize the output stream
			if (config.getString(Constants.ARGS_OUTPUT_FILE) != null) {
				out = openOutputFile(config.getString(Constants.ARGS_OUTPUT_FILE));
			}
			else {
				out = System.out;
			}
			
			if (config.getBoolean(Constants.ARGS_HELP)) {
				displayUsage(out, jsap);
				System.exit(0);
			}
			else if (config.getBoolean(Constants.ARGS_VERBOSE_HELP)) {
				displayVerboseHelp(out, jsap);
				System.exit(0);
			}
			else if (config.getBoolean(Constants.ARGS_VERSION)) {
				displayVersion(out);
				System.exit(0);
			}
			
			// Parse input from file or stdin
			if (config.getString(Constants.ARGS_INPUT_FILE) == null) {
				FileParser.parse(System.in);
			}
			else {
				FileParser.parse(new File(config.getString(Constants.ARGS_INPUT_FILE)));
			}
			
			// Process the specified command
			if (config.getBoolean(Constants.ARGS_STATISTICS_REPORT)) {
				GenericReport report = new StatisticsReport();
				out.print(report.generate());
			}
			else if (config.getBoolean(Constants.ARGS_SUMMARY_REPORT)) {
				GenericReport report = new SummaryReport();
				out.print(report.generate());
			}
			else if (config.getBoolean(Constants.ARGS_CLONE_USER)) {
				cloneUser(config.getString(Constants.ARGS_NAME), 
						config.getString(Constants.ARGS_NEW_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_EDIT_USER)) {
				editUser(config.getString(Constants.ARGS_NAME), 
						config.getString(Constants.ARGS_NEW_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_DELETE_USER)) {
				deleteUser(config.getString(Constants.ARGS_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_ADD_GROUPS)) {
				addGroups(config.getString(Constants.ARGS_NAME), 
						config.getStringArray(Constants.ARGS_GROUPS));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_REMOVE_GROUPS)) {
				removeGroups(config.getString(Constants.ARGS_NAME), 
						config.getStringArray(Constants.ARGS_GROUPS));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_COUNT_USERS)) {
				countUsers(out);
			}
			else if (config.getBoolean(Constants.ARGS_GET_USERS)) {
				getUsers(out);
			}
			else if (config.getBoolean(Constants.ARGS_GET_USER_GROUPS)) {
				getUserGroups(out, config.getString(Constants.ARGS_NAME));
			}
			else if (config.getBoolean(Constants.ARGS_GET_USER_RULES)) {
				getUserRules(out, config.getString(Constants.ARGS_NAME));
			}
			else if (config.getBoolean(Constants.ARGS_ADD_GROUP)) {
				addGroup(config.getString(Constants.ARGS_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_CLONE_GROUP)) {
				cloneGroup(config.getString(Constants.ARGS_NAME), 
						config.getString(Constants.ARGS_NEW_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_EDIT_GROUP)) {
				editGroup(config.getString(Constants.ARGS_NAME), 
						config.getString(Constants.ARGS_NEW_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_DELETE_GROUP)) {
				deleteGroup(config.getString(Constants.ARGS_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_ADD_MEMBERS)) {
				addMembers(config.getString(Constants.ARGS_NAME), 
						config.getStringArray(Constants.ARGS_USERS), 
						config.getStringArray(Constants.ARGS_GROUPS));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_REMOVE_MEMBERS)) {
				removeMembers(config.getString(Constants.ARGS_NAME), 
						config.getStringArray(Constants.ARGS_USERS), 
						config.getStringArray(Constants.ARGS_GROUPS));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_COUNT_GROUPS)) {
				countGroups(out);
			}
			else if (config.getBoolean(Constants.ARGS_GET_GROUPS)) {
				getGroups(out);
			}
			else if (config.getBoolean(Constants.ARGS_GET_GROUP_MEMBERS)) {
				getGroupMembers(out, config.getString(Constants.ARGS_NAME));
			}
			else if (config.getBoolean(Constants.ARGS_GET_GROUP_GROUP_MEMBERS)) {
				getGroupGroupMembers(out, config.getString(Constants.ARGS_NAME));
			}
			else if (config.getBoolean(Constants.ARGS_GET_GROUP_USER_MEMBERS)) {
				getGroupUserMembers(out, config.getString(Constants.ARGS_NAME));
			}
			else if (config.getBoolean(Constants.ARGS_GET_GROUP_RULES)) {
				getGroupRules(out, config.getString(Constants.ARGS_NAME));
			}
			else if (config.getBoolean(Constants.ARGS_EDIT_REPOS)) {
				editRepository(config.getString(Constants.ARGS_NAME), 
						config.getString(Constants.ARGS_NEW_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_DELETE_REPOS)) {
				deleteRepository(config.getString(Constants.ARGS_NAME));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_COUNT_REPOS)) {
				countRepositories(out);
			}
			else if (config.getBoolean(Constants.ARGS_GET_REPOS)) {
				getRepositories(out);
			}
			else if (config.getBoolean(Constants.ARGS_GET_REPOS_RULES)) {
				getRepositoryRules(out, config.getString(Constants.ARGS_NAME));
			}
			else if (config.getBoolean(Constants.ARGS_ADD_RULE)) {
				addRule(config.getString(Constants.ARGS_REPOS), 
						config.getString(Constants.ARGS_PATH), 
						config.getString(Constants.ARGS_USER), 
						config.getString(Constants.ARGS_GROUP), 
						config.getString(Constants.ARGS_ACCESS));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_EDIT_RULE)) {
				editRule(config.getString(Constants.ARGS_REPOS), 
						config.getString(Constants.ARGS_PATH), 
						config.getString(Constants.ARGS_USER), 
						config.getString(Constants.ARGS_GROUP), 
						config.getString(Constants.ARGS_NEW_REPOS), 
						config.getString(Constants.ARGS_NEW_PATH), 
						config.getString(Constants.ARGS_NEW_USER), 
						config.getString(Constants.ARGS_NEW_GROUP), 
						config.getString(Constants.ARGS_NEW_ACCESS));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_DELETE_RULE)) {
				deleteRule(config.getString(Constants.ARGS_REPOS), 
						config.getString(Constants.ARGS_PATH), 
						config.getString(Constants.ARGS_USER), 
						config.getString(Constants.ARGS_GROUP));
				out.print(FileGenerator.generate());
			}
			else if (config.getBoolean(Constants.ARGS_COUNT_RULES)) {
				countRules(out);
			}
			else if (config.getBoolean(Constants.ARGS_GET_RULES)) {
				getRules(out);
			}
			
			// Close the output stream
			if (config.getString(Constants.ARGS_OUTPUT_FILE) != null) {
				out.close();
				out = null;
			}
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}			
	}

	/**
	 * Add a new access rule. Either user or group name, but not both must be 
	 * specified. All other arguments are required.
	 * 
	 * @param repositoryName Repository name
	 * @param path Relative path string
	 * @param userName User name
	 * @param groupName Group name
	 * @param access Access level
	 * @throws ApplicationException Error occurred
	 */
	private static void addRule(String repositoryName, String path, String userName, String groupName, String access) throws ApplicationException {
		if (path == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.pathrequired"));
		}
		
		if (userName == null && groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
		
		if (userName != null && groupName != null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
		
		if (access == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.accessrequired"));
		}
		
		// Convert "none" to "" if necessary
		access = access.equals(Constants.ACCESS_LEVEL_NONE) ? Constants.ACCESS_LEVEL_DENY_ACCESS : access;
		
		if (userName != null) {
			if (repositoryName == null) {
				Document.addAccessRuleForUser(null, path, Document.addUser(userName), access);
			}
			else {
				Document.addAccessRuleForUser(Document.addRepository(repositoryName), path, Document.addUser(userName), access);
			}
		}
		else if (groupName != null) {
			if (repositoryName == null) {
				Document.addAccessRuleForGroup(null, path, Document.addGroup(groupName), access);
			}
			else {
				Document.addAccessRuleForGroup(Document.addRepository(repositoryName), path, Document.addGroup(groupName, null, null), access);
			}
		}
		else {
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
	}
	
	/**
	 * Deletes an existing access rule. Either user or group name, but not 
	 * both is required. All other arguments are required. Arguments are used
	 * to identify an existing access rule.
	 * 
	 * @param repositoryName Repository name
	 * @param path Relative path string
	 * @param userName User name
	 * @param groupName Group name
	 * @throws ApplicationException Error occurred
	 */
	private static void deleteRule(String repositoryName, String path, String userName, String groupName) throws ApplicationException {
		if (path == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.pathrequired"));
		}
		
		if (userName == null && groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
		
		if (userName != null && groupName != null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
		
		if (userName != null) {
			User user = Document.findUser(userName);
			
			if (user == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
			}
			
			Repository repository = null;
			
			if (repositoryName != null) {
				repository = Document.findRepository(repositoryName);
				
				if (repository == null) {
					throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
				}
			}
			
			Document.deleteAccessRule(repositoryName, path, null, user);
		}
		else if (groupName != null) {
			Group group = Document.findGroup(groupName);
			
			if (group == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
			}
			
			Repository repository = null;
			
			if (repositoryName != null) {
				repository = Document.findRepository(repositoryName);
				
				if (repository == null) {
					throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
				}
			}
			
			Document.deleteAccessRule(repositoryName, path, group, null);
		}
		else {
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
	}
	
	/**
	 * Edits an existing access rule. Either user or group name, but not both 
	 * must be specified. Arguments prefixed with "new" indicate new values
	 * for the access rule. Multiple changes may be specified at once.
	 * 
	 * @param repositoryName Repository name
	 * @param path Relative path string
	 * @param userName User name
	 * @param groupName Group name
	 * @param newRepositoryName New repository name
	 * @param newPathString New relative path string
	 * @param newUserName New user name
	 * @param newGroupName New group name
	 * @param newAccess New access level
	 * @throws ApplicationException Error occurred
	 */
	private static void editRule(String repositoryName, String path, String userName, String groupName, String newRepositoryName, String newPathString, String newUserName, String newGroupName, String newAccess) throws ApplicationException {
		if (path == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.pathrequired"));
		}
		
		if (userName == null && groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
		
		if (userName != null && groupName != null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
		
		if (newUserName != null && newGroupName != null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.newuserorgrouprequired"));
		}
		
		AccessRule rule = null;
		
		if (userName != null) {
			User user = Document.findUser(userName);
			
			if (user == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
			}
			
			Repository repository = null;
			
			if (repositoryName != null) {
				repository = Document.findRepository(repositoryName);
				
				if (repository == null) {
					throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
				}
			}
			
			rule = Document.findUserAccessRule(repository, path, user);
			
			if (rule == null) {
				throw new ApplicationException(ResourceUtil.getString("application.error.unabletofindrule"));
			}
		}
		else if (groupName != null) {
			Group group = Document.findGroup(groupName);
			
			if (group == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
			}
			
			Repository repository = null;
			
			if (repositoryName != null) {
				repository = Document.findRepository(repositoryName);
				
				if (repository == null) {
					throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
				}
			}
			
			rule = Document.findGroupAccessRule(repository, path, group);
			
			if (rule == null) {
				throw new ApplicationException(ResourceUtil.getString("application.error.unabletofindrule"));
			}
		}
		else {
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouprequired"));
		}
		
		if (newRepositoryName != null) {
			Repository newRepository = Document.findRepository(newRepositoryName);
			
			if (newRepository == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
			}
			
			rule.getPath().setRepository(newRepository);
		}
		
		if (newPathString != null) {
			Path newPath = Document.addPath(rule.getPath().getRepository(), newPathString);
			
			rule.setPath(newPath);
		}
		
		if (newUserName != null) {
			User newUser = Document.addUser(newUserName);
			
			if (newUser == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
			}
			
			rule.setUser(newUser);
			rule.setGroup(null);
		}
		
		if (newGroupName != null) {
			Group newGroup = Document.findGroup(newGroupName);
			
			if (newGroup == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
			}
			
			rule.setUser(null);
			rule.setGroup(newGroup);
		}
		
		if (newAccess != null) {
			String newAccessLevel = (newAccess.equals(Constants.ACCESS_LEVEL_NONE)) ? Constants.ACCESS_LEVEL_DENY_ACCESS : newAccess; 
				
			rule.setLevel(newAccessLevel);
		}
	}

	/**
	 * Add new user and/or group members to an existing group. New user and 
	 * group members may be added at the same time. At least one user or group
	 * name is required.
	 * 
	 * @param groupName Group name to be updated
	 * @param userNames User names of new members
	 * @param groupNames Group names of new members
	 * @throws ApplicationException Error occurred
	 */
	private static void addMembers(String groupName, String[] userNames, String[] groupNames) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		if (	(userNames == null || userNames.length < 1) && 
				(groupNames == null || groupNames.length < 1)	) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouplistrequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		for (String memberUserName : userNames) {
			User memberUser = Document.findUser(memberUserName);
			
			if (memberUser == null) {
				// Add user since it doesn't exist.
				memberUser = Document.addUser(memberUserName);
			}
			
			group.addUserMember(memberUser);
		}
		
		for (String memberGroupName : groupNames) {
			Group memberGroup = Document.findGroup(memberGroupName);
			
			if (memberGroup == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", memberGroupName));
			}
			
			group.addGroupMember(memberGroup);
		}
	}
	
	/**
	 * Remove members from an existing group. User and group members may be 
	 * removed at the same time. At least one user or group name must be
	 * specified.
	 * 
	 * @param groupName Name of group to be updated
	 * @param userNames User names of users to be removed
	 * @param groupNames Group names of groups to be removed
	 * @throws ApplicationException Error occurred.
	 */
	private static void removeMembers(String groupName, String[] userNames, String[] groupNames) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		if (	(userNames == null || userNames.length < 1) && 
				(groupNames == null || groupNames.length < 1)	) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userorgrouplistrequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		for (String memberUserName : userNames) {
			User memberUser = Document.findUser(memberUserName);
			
			if (memberUser == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", memberUserName));
			}
			
			group.removeUserMember(memberUser);
		}
		
		for (String memberGroupName : groupNames) {
			Group memberGroup = Document.findGroup(memberGroupName);
			
			if (memberGroup == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", memberGroupName));
			}
			
			group.removeGroupMember(memberGroup);
		}
	}
	
	/**
	 * Outputs the number of groups.
	 * 
	 * @param out Output stream
	 */
	private static void countGroups(PrintStream out) {
		out.println(Document.getGroupObjects().length);
	}

	/**
	 * Deletes an existing group.
	 * 
	 * @param groupName Name of group to delete
	 * @throws ApplicationException Error occurred
	 */
	private static void deleteGroup(String groupName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		Document.deleteGroup(group);
	}

	/**
	 * Edits existing group name.
	 * 
	 * @param groupName Name of group to edit
	 * @param newGroupName New name for group
	 * @throws ApplicationException Error occurred
	 */
	private static void editGroup(String groupName, String newGroupName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		if (newGroupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.newgrouprequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		group.setName(newGroupName);
	}

	/**
	 * Clone existing group.
	 * 
	 * @param groupName Name of group to clone
	 * @param cloneName Clone name
	 * @throws ApplicationException Error occurred
	 */
	private static void cloneGroup(String groupName, String cloneName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		if (cloneName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.clonerequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		Document.cloneGroup(group, cloneName);		
	}

	/**
	 * Adds a new group.
	 * 
	 * @param groupName Name of new group
	 * @throws ApplicationException Error occurred
	 */
	private static void addGroup(String groupName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		Document.addGroup(groupName);
	}
	
	/**
	 * Get access rules for a group.
	 * 
	 * @param out Output stream
	 * @param groupName Name of group
	 * @throws ApplicationException Error occurred
	 */
	private static void getGroupRules(PrintStream out, String groupName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		Object[][] accessRules = Document.getGroupAccessRules(group);
		
		for(Object[] accessRule : accessRules) {
			Repository repository = (Repository)accessRule[0];
			Path path = (Path)accessRule[1];
			String accessLevel = (String)accessRule[2];
			
			out.println(repository.getName() + " " + path.getPath() + " " + Constants.GROUP_PREFIX + groupName + " " + accessLevel);
		}
	}
	
	/**
	 * Get access rules for a repository.
	 * 
	 * @param out Ouptput stream
	 * @param repositoryName Name of repository
	 * @throws ApplicationException Error occurred
	 */
	private static void getRepositoryRules(PrintStream out, String repositoryName) throws ApplicationException {
		if (repositoryName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.repositoryrequired"));
		}
		
		Repository repository = Document.findRepository(repositoryName);
		
		if (repository == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
		}
		
		Object[][] accessRules = Document.getRepositoryAccessRules(repository);
		
		for(Object[] accessRule : accessRules) {
			Path path = (Path)accessRule[0];
			String accessLevel = (String)accessRule[2];
			String name = null;
			
			if (accessRule[1] instanceof Group) {
				name = Constants.GROUP_PREFIX + ((Group)accessRule[1]).getName();
			}
			else if (accessRule[1] instanceof User) {
				name = ((User)accessRule[1]).getName();
			}
			else {
				throw new ApplicationException(ResourceUtil.getString("application.erroroccurred"));
			}
			
			out.println(repositoryName + " " + path.getPath() + " " + name + " " + accessLevel);
		}
	}
	
	/**
	 * Gets access rules for a user.
	 * 
	 * @param out Output stream
	 * @param userName Name of user
	 * @throws ApplicationException Error occurred
	 */
	private static void getUserRules(PrintStream out, String userName) throws ApplicationException {
		if (userName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userrequired"));
		}
		
		if (Document.findUser(userName) == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
		}
		
		Object[][] accessRules = Document.getUserAccessRules(userName);
		
		for(Object[] accessRule : accessRules) {
			Repository repository = (Repository)accessRule[0];
			Path path = (Path)accessRule[1];
			String accessLevel = (String)accessRule[2];
			
			out.println(repository.getName() + " " + path.getPath() + " " + userName + " " + accessLevel);
		}
	}
	
	/**
	 * Get all access rules.
	 * 
	 * @param out Ouptput stream
	 * @throws ApplicationException Error occurred
	 */
	private static void getRules(PrintStream out) throws ApplicationException {
		List<AccessRule> rules = Document.getAccessRules();
		
		for(AccessRule rule : rules) {
			Path path = rule.getPath();
			String accessLevel = rule.getLevelFullName();
			String name = null;
			String repositoryName = "";
			
			if (path.getRepository() != null) {
				repositoryName = path.getRepository().getName();
			}
			
			if (rule.getGroup() != null) {
				name = Constants.GROUP_PREFIX + rule.getGroup().getName();
			}
			else if (rule.getUser() != null) {
				name = rule.getUser().getName();
			}
			else {
				throw new ApplicationException(ResourceUtil.getString("application.erroroccurred"));
			}
			
			out.println(repositoryName + " " + path.getPath() + " " + name + " " + accessLevel);
		}
	}

	/**
	 * Get list of groups in which the user is a member.
	 * 
	 * @param out Output stream
	 * @param userName Name of user
	 * @throws ApplicationException Error occurred
	 */
	private static void getUserGroups(PrintStream out, String userName) throws ApplicationException {
		if (userName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userrequired"));
		}
		
		if (Document.findUser(userName) == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
		}
		
		Object[] groupNames = Document.getUserGroupNames(userName);
		
		for(Object groupName : groupNames) {
			out.println((String)groupName);
		}
	}
	
	/**
	 * Get list of groups that are a member of a group.
	 * 
	 * @param out Output stream
	 * @param groupName Name of group
	 * @throws ApplicationException Error occurred
	 */
	private static void getGroupGroupMembers(PrintStream out, String groupName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		List<Group> groupMembers = group.getGroupMembers();
	
		for(Group groupMember : groupMembers) {
			out.println(groupMember.getName());
		}
	}
	
	/**
	 * Get list of users that are a member of a group.
	 * 
	 * @param out Output stream
	 * @param groupName Name of group
	 * @throws ApplicationException Exception occurred
	 */
	private static void getGroupUserMembers(PrintStream out, String groupName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		List<User> userMembers = group.getUserMembers();
			
		for(User userMember : userMembers) {
			out.println(userMember.getName());
		}
	}
	
	/**
	 * Get list of members of a group. User and group members are returned.
	 * 
	 * @param out Output stream
	 * @param groupName Name of group
	 * @throws ApplicationException Error occurred
	 */
	private static void getGroupMembers(PrintStream out, String groupName) throws ApplicationException {
		if (groupName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouprequired"));
		}
		
		Group group = Document.findGroup(groupName);
		
		if (group == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
		}
		
		List<Group> groupMembers = group.getGroupMembers();
		
		if (groupMembers != null && groupMembers.size() > 0) {
			out.println(ResourceUtil.getString("application.groups"));
			
			for(Group groupMember : groupMembers) {
				out.println(groupMember.getName());
			}
		}
		
		List<User> userMembers = group.getUserMembers();
		
		if (groupMembers != null && userMembers.size() > 0) {
			out.println(ResourceUtil.getString("application.users"));
			
			for(User userMember : userMembers) {
				out.println(userMember.getName());
			}
		}
	}

	/**
	 * Gets list of all groups.
	 * 
	 * @param out Output stream
	 */
	private static void getGroups(PrintStream out) {
		Object[] groupNames = Document.getGroupNames();
		
		for(Object groupName : groupNames) {
			out.println((String)groupName);
		}
	}

	/**
	 * Gets list of all repositories.
	 * 
	 * @param out Output stream
	 */
	private static void getRepositories(PrintStream out) {
		Object[] repositoryNames = Document.getRepositoryNames();
		
		for(Object repositoryName : repositoryNames) {
			out.println((String)repositoryName);
		}
	}
	
	/**
	 * Gets list of all users.
	 * 
	 * @param out Output stream
	 */
	private static void getUsers(PrintStream out) {
		Object[] userNames = Document.getUserNames();
		
		for(Object userName : userNames) {
			out.println((String)userName);
		}
	}

	/**
	 * Counts number of repositories.
	 * 
	 * @param out Output stream
	 */
	private static void countRepositories(PrintStream out) {
		out.println(Document.getRepositories().size());
	}
	
	/**
	 * Counts number of users.
	 * 
	 * @param out Output stream
	 */
	private static void countUsers(PrintStream out) {
		out.println(Document.getUserObjects().length);
	}
	
	/**
	 * Counts number of rules.
	 * 
	 * @param out Output stream
	 */
	private static void countRules(PrintStream out) {
		out.println(Document.getAccessRules().size());
	}

	/**
	 * Removes user from groups.
	 * 
	 * @param userName Name of user
	 * @param groupNames List of groups
	 * @throws ApplicationException Error occurred
	 */
	private static void removeGroups(String userName, String[] groupNames) throws ApplicationException {
		if (userName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userrequired"));
		}
		
		if (groupNames == null || groupNames.length < 1) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouplistrequired"));
		}
		
		User user = Document.findUser(userName);
		
		if (user == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
		}
		
		for (String groupName : groupNames) {
			Group group = Document.findGroup(groupName);
			
			if (group == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
			}
			
			user.removeGroup(group);
			group.removeUserMember(user);
		}		
	}

	/**
	 * Add user to groups.
	 * 
	 * @param userName Name of user
	 * @param groupNames List of groups
	 * @throws ApplicationException Error occurred
	 */
	private static void addGroups(String userName, String[] groupNames) throws ApplicationException {
		if (userName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userrequired"));
		}
		
		if (groupNames == null || groupNames.length < 1) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.grouplistrequired"));
		}
		
		User user = Document.findUser(userName);
		
		if (user == null) {
			user = Document.addUser(userName);
		}
		
		for (String groupName : groupNames) {
			Group group = Document.findGroup(groupName);
			
			if (group == null) {
				throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindgroup", groupName));
			}
			
			user.addGroup(group);
			group.addUserMember(user);
		}
	}

	/**
	 * Deletes an existing repository.
	 * 
	 * @param repositoryName Name of repository
	 * @throws ApplicationException Error occurred
	 */
	private static void deleteRepository(String repositoryName) throws ApplicationException {
		if (repositoryName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.repositoryrequired"));
		}
		
		Repository repository = Document.findRepository(repositoryName);
		
		if (repository == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
		}
		
		Document.deleteRepository(repository);
	}
	
	/**
	 * Deletes an existing user
	 * 
	 * @param userName Name of user
	 * @throws ApplicationException Error occurred
	 */
	private static void deleteUser(String userName) throws ApplicationException {
		if (userName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userrequired"));
		}
		
		User user = Document.findUser(userName);
		
		if (user == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
		}
		
		Document.deleteUser(user);
	}
	
	/**
	 * Edits an existing repository.
	 * 
	 * @param repositoryName Name of repository
	 * @param newRepositoryName Repository new name
	 * @throws ApplicationException Error occurred
	 */
	private static void editRepository(String repositoryName, String newRepositoryName) throws ApplicationException {
		if (repositoryName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.repositoryrequired"));
		}
		
		if (newRepositoryName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.newrepositoryrequired"));
		}
		
		Repository repository = Document.findRepository(repositoryName);
		
		if (repository == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofindrepository", repositoryName));
		}
		
		repository.setName(newRepositoryName);
	}

	/**
	 * Edits an existing user.
	 * 
	 * @param userName Name of user
	 * @param newUserName User new name
	 * @throws ApplicationException
	 */
	private static void editUser(String userName, String newUserName) throws ApplicationException {
		if (userName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userrequired"));
		}
		
		if (newUserName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.newuserrequired"));
		}
		
		User user = Document.findUser(userName);
		
		if (user == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
		}
		
		user.setName(newUserName);
	}

	/**
	 * Clone existing user.
	 * 
	 * @param userName Name of user to be cloned
	 * @param cloneName Name for the clone
	 * @throws ApplicationException Error occurred
	 */
	private static void cloneUser(String userName, String cloneName) throws ApplicationException {
		if (userName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.userrequired"));
		}
		
		if (cloneName == null) { 
			throw new ApplicationException(ResourceUtil.getString("application.error.clonerequired"));
		}
		
		User user = Document.findUser(userName);
		
		if (user == null) {
			throw new ApplicationException(ResourceUtil.getFormattedString("application.error.unabletofinduser", userName));
		}
		
		Document.cloneUser(user, cloneName);		
	}

	/**
	 * Open output file.
	 * 
	 * @param filePath Path of output file
	 * @return Output stream for file
	 * @throws ApplicationException Error occurred
	 */
	private static PrintStream openOutputFile(String filePath) throws ApplicationException {
		PrintStream output = null;
		
		try {
			output = new PrintStream(new File(filePath));
		}
		catch(FileNotFoundException fne) {
			throw new ApplicationException(ResourceUtil.getString("generator.filenotfound"));
		}
		catch(Exception e) {
			throw new ApplicationException(ResourceUtil.getString("generator.error"));
		}
		
		return output;
	}
}
