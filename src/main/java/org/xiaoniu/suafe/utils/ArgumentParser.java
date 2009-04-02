package org.xiaoniu.suafe.utils;

import org.xiaoniu.suafe.resources.ResourceUtil;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.Switch;

public class ArgumentParser extends JSAP {
	public void addSwitchOption(String name, Character shortFlag, String longFlag, String help) throws JSAPException {
		Switch opt = new Switch(name);
		
		opt.setShortFlag(shortFlag == null ? JSAP.NO_SHORTFLAG : shortFlag.charValue()); 
		opt.setLongFlag(longFlag);
		
		opt.setHelp(ResourceUtil.getString("application.args." + help + ".help"));
		 
		registerParameter(opt);
	}
	
	public void addStringOption(String name, Character shortFlag, String longFlag, String help) throws JSAPException {
		FlaggedOption opt = new FlaggedOption(name)
			.setStringParser(JSAP.STRING_PARSER)
			.setShortFlag(shortFlag == null ? JSAP.NO_SHORTFLAG : shortFlag.charValue()) 
			.setLongFlag(longFlag);
		
		opt.setHelp(ResourceUtil.getString("application.args." + help + ".help"));
		 
		registerParameter(opt);
	}
	
	public void addListOption(String name, Character shortFlag, String longFlag, String help) throws JSAPException {
		FlaggedOption opt = new FlaggedOption(name)
			.setStringParser(JSAP.STRING_PARSER)
			.setShortFlag(shortFlag == null ? JSAP.NO_SHORTFLAG : shortFlag.charValue()) 
			.setLongFlag(longFlag)
			.setList(true)
			.setListSeparator(',');
		
		opt.setHelp(ResourceUtil.getString("application.args." + help + ".help"));
		 
		registerParameter(opt);
	}
}
