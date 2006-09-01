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
 * @author Shaun Johnson
 */
public class FileGenerator {
	public static String generate() throws ApplicationException {
		StringBuffer output = null;
		
		try {
			output = new StringBuffer();
			
			output.append(Constants.HEADER_CURRENT + Constants.newline);
			
			// Process group definitions
			output.append("[groups]" + Constants.newline);
			
			Collections.sort(Document.getGroups());
			Iterator groups = Document.getGroups().iterator();
			
			while(groups.hasNext()) {
				Group group = (Group)groups.next();
				
				output.append(group.getName() + " = ");
				
				if (group.getGroupMembers() != null) {
					Collections.sort(group.getGroupMembers());
					Iterator members = group.getGroupMembers().iterator();
					
					while(members.hasNext()) {
						Group memberGroup = (Group)members.next();
						
						output.append("@" + memberGroup.getName());
						
						// Add comma if more members exist
						if (members.hasNext()) {
							output.append(", ");
						}
					}
				}
				
				if (group.getUserMembers() != null) {
					if (group.getGroupMembers() != null && group.getGroupMembers().size() > 0) {
						output.append(", ");
					}
					
					Collections.sort(group.getUserMembers());
					Iterator members = group.getUserMembers().iterator();
					
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
			Iterator paths = Document.getPaths().iterator();
			
			while(paths.hasNext()) {
				Path path = (Path)paths.next();
				
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
				Iterator accessRules = path.getAccessRules().iterator();
				
				while(accessRules.hasNext()) {
					AccessRule rule = (AccessRule)accessRules.next();
					
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
