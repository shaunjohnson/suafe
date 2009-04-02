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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;

import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.PathComparator;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;

/**
 * Provides methods to output the authz file information from memory.
 * 
 * @author Shaun Johnson
 */
public class FileGenerator {
	
	private Document document = null;
	
	private final int DEFAULT_MAX_LINE_LENGTH = 80;
	
	public FileGenerator(Document document) {
		super();
		
		this.document = document;
	}
	
	/**
	 * Generates authz content from the current document in memory.
	 * 
	 * @return Authz file content
	 * @throws ApplicationException
	 */
	public String generate(boolean allowMultipleLine) throws ApplicationException {
		return generate(allowMultipleLine ? DEFAULT_MAX_LINE_LENGTH : -1);
	}
	
	/**
	 * Generates authz content from the current document in memory.
	 * 
	 * @return Authz file content
	 * @param maxLineLength Maximum line length
	 * @throws ApplicationException
	 */
	public String generate(int maxLineLength) throws ApplicationException {
		StringBuffer output = null;
		
		try {
			output = new StringBuffer();
			
			output.append("# " + ResourceUtil.getString("application.fileheader") + Constants.TEXT_NEW_LINE);
			
			// Process group definitions
			output.append("[groups]" + Constants.TEXT_NEW_LINE);
			
			Collections.sort(document.getGroups());
			
			for (Group group : document.getGroups()) {	
				output.append(group.getName() + " = ");
				
				String prefix = createPrefix(group.getName().length() + 3);
				boolean isFirstGroupMember = true;
				
				if (!group.getGroupMembers().isEmpty()) {
					Collections.sort(group.getGroupMembers());
					
					Iterator<Group> members = group.getGroupMembers().iterator();
					
					StringBuffer groupLine = new StringBuffer();
					
					while(members.hasNext()) {
						Group memberGroup = (Group)members.next();
						
						if (maxLineLength > 0 && !isFirstGroupMember && groupLine.length() + memberGroup.getName().length() > maxLineLength) {
							output.append(groupLine);
							output.append(Constants.TEXT_NEW_LINE);
							output.append(prefix);
							
							groupLine = new StringBuffer();
						}
						
						groupLine.append("@" + memberGroup.getName());
						
						// Add comma if more members exist
						if (members.hasNext()) {
							groupLine.append(", ");
						}
						
						isFirstGroupMember = false;
					}
					
					output.append(groupLine);
				}
				
				if (!group.getUserMembers().isEmpty()) {
					if (!group.getGroupMembers().isEmpty()) {
						output.append(", ");
					}
					
					Collections.sort(group.getUserMembers());
					Iterator<User> members = group.getUserMembers().iterator();
					
					StringBuffer userLine = new StringBuffer();
					
					while(members.hasNext()) {
						User memberUser = (User)members.next();
						
						if (maxLineLength > 0 && !isFirstGroupMember && userLine.length() + memberUser.getName().length() > maxLineLength) {
							output.append(userLine);
							output.append(Constants.TEXT_NEW_LINE);
							output.append(prefix);
							
							userLine = new StringBuffer();
						}
						
						userLine.append(memberUser.getName());
						
						// Add comma if more members exist
						if (members.hasNext()) {
							userLine.append(", ");
						}
						
						isFirstGroupMember = false;
					}
					
					output.append(userLine);
				}
				
				output.append(Constants.TEXT_NEW_LINE);
			}
			
			if (document.getPaths().size() > 0) {
				output.append(Constants.TEXT_NEW_LINE);
			}
			
			// Process access rules
			Collections.sort(document.getPaths(), new PathComparator());
			
			for (Path path : document.getPaths()) {
				if (path.getAccessRules().size() == 0) {
					continue;
				}
				
				if (path.getRepository() == null) {
					// Server permissions
					output.append("[" + path.getPath() + "]" + Constants.TEXT_NEW_LINE);
				}
				else {
					// Path permissions
					output.append("[" + path.getRepository().getName() + ":" + path.getPath() + "]" + Constants.TEXT_NEW_LINE);
				}
				
				Collections.sort(path.getAccessRules());
				
				for (AccessRule rule : path.getAccessRules()) {
					if (rule.getGroup() != null) {
						output.append("@" + rule.getGroup().getName() + " = " + rule.getLevel() + "" + Constants.TEXT_NEW_LINE);
					}
					else if (rule.getUser() != null) {
						output.append(rule.getUser().getName() + " = " + rule.getLevel() + "" + Constants.TEXT_NEW_LINE);
					}
					else {
						throw new ApplicationException(ResourceUtil.getString("generator.error"));
					}
				}
				
				output.append(Constants.TEXT_NEW_LINE);
			}
		}
		catch(Exception e) {
			throw new ApplicationException(ResourceUtil.getString("generator.error"));
		}

		return output.toString();
	}
	
	/**
	 * Generates and saves the current authz content in memory.
	 * 
	 * @param file File where authz content is to be written.
	 * @throws ApplicationException
	 */
	public void generate(File file, boolean allowMultipleLine) throws ApplicationException {
		generate(file, allowMultipleLine ? DEFAULT_MAX_LINE_LENGTH : -1);
	}
	
	/**
	 * Generates and saves the current authz content in memory.
	 * 
	 * @param file File where authz content is to be written.
	 * @param maxLineLength Maximum line length
	 * @throws ApplicationException
	 */
	public void generate(File file, int maxLineLength) throws ApplicationException {
		PrintWriter output = null;
		
		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			// Process group definitions
			output.print(generate(maxLineLength));
		}
		catch(FileNotFoundException fne) {
			throw new ApplicationException(ResourceUtil.getString("generator.filenotfound"));
		}
		catch(IOException ioe) {
			throw new ApplicationException(ResourceUtil.getString("generator.error"));
		}
		catch(Exception e) {
			throw new ApplicationException(ResourceUtil.getString("generator.error"));
		}
		finally {
			if (output != null) {
				output.close();
			}
		}
	}
	
	private String createPrefix(int length) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			builder.append(" ");
		}
		
		return builder.toString();
	}
}
