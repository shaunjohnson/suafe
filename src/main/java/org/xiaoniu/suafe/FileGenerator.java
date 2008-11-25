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
	
	/**
	 * Generates authz content from the current document in memory.
	 * 
	 * @return Authz file content
	 * @throws ApplicationException
	 */
	public static String generate() throws ApplicationException {
		StringBuffer output = null;
		
		try {
			output = new StringBuffer();
			
			output.append("# " + ResourceUtil.getString("application.fileheader") + Constants.newline);
			
			// Process group definitions
			output.append("[groups]" + Constants.newline);
			
			Collections.sort(Document.getGroups());
			
			for (Group group : Document.getGroups()) {	
				output.append(group.getName() + " = ");
				
				if (!group.getGroupMembers().isEmpty()) {
					Collections.sort(group.getGroupMembers());
					
					Iterator<Group> members = group.getGroupMembers().iterator();
					
					while(members.hasNext()) {
						Group memberGroup = (Group)members.next();
						
						output.append("@" + memberGroup.getName());
						
						// Add comma if more members exist
						if (members.hasNext()) {
							output.append(", ");
						}
					}
				}
				
				if (!group.getUserMembers().isEmpty()) {
					if (!group.getGroupMembers().isEmpty()) {
						output.append(", ");
					}
					
					Collections.sort(group.getUserMembers());
					Iterator<User> members = group.getUserMembers().iterator();
					
					while(members.hasNext()) {
						User memberUser = (User)members.next();
						
						output.append(memberUser.getName());
						
						// Add comma if more members exist
						if (members.hasNext()) {
							output.append(", ");
						}
					}
				}
				
				output.append(Constants.newline);
			}
			
			output.append(Constants.newline);
			
			// Process access rules
			Collections.sort(Document.getPaths(), new PathComparator());
			
			for (Path path : Document.getPaths()) {
				if (path.getAccessRules().size() == 0) {
					continue;
				}
				
				if (path.getRepository() == null) {
					// Server permissions
					output.append("[" + path.getPath() + "]" + Constants.newline);
				}
				else {
					// Path permissions
					output.append("[" + path.getRepository().getName() + ":" + path.getPath() + "]" + Constants.newline);
				}
				
				Collections.sort(path.getAccessRules());
				
				for (AccessRule rule : path.getAccessRules()) {
					if (rule.getGroup() != null) {
						output.append("@" + rule.getGroup().getName() + " = " + rule.getLevel() + "" + Constants.newline);
					}
					else if (rule.getUser() != null) {
						output.append(rule.getUser().getName() + " = " + rule.getLevel() + "" + Constants.newline);
					}
					else {
						throw new ApplicationException(ResourceUtil.getString("generator.error"));
					}
				}
				
				output.append(Constants.newline);
			}
			
			output.append(Constants.newline);
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
	public static void generate(File file) throws ApplicationException {
		PrintWriter output = null;
		
		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			// Process group definitions
			output.print(generate());
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
}
