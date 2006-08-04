/*
 * 
 */
package org.xiaoniu.suafe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.resources.ResourceUtil;


/**
 * Subversion user authentication file parser.
 * Reads and parse auth file and populates the Document object.
 * 
 * @author Shaun Johnson
 */
public class FileParser {
	
	private static final int STATE_START = 1;
	private static final int STATE_PROCESS_GROUPS = 2;	
	private static final int STATE_PROCESS_SERVER_RULES = 3;
	private static final int STATE_PROCESS_RULES = 4;
	
	private static int currentState = STATE_START;
	
	private static Path currentPath = null;
	
	/**
	 * Validates whether the supplied file is readable.
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void validateReadable(File file) throws ValidatorException {
		if (!file.canRead()) {
			throw new ValidatorException(ResourceUtil.getString("parser.unreadablefile"));
		}
	}
	
	/**
	 * @param selectedFile
	 * @throws Exception
	 */
	public static void parse(File file) throws ApplicationException {
		BufferedReader input = null;
		
		currentState = STATE_START;
		
		validateReadable(file);
		
		try {
			input = new BufferedReader(new FileReader(file));
			String line = input.readLine();
			
			Document.initialize();
			
			while (line != null) {
				line = line.trim();
				
				// Process non-blank lines
				if (!line.equals("")) {
					parseLine(line);
				}
				
				line = input.readLine();
			}
		}
		catch(FileNotFoundException fne) {
			throw new ApplicationException(ResourceUtil.getString("parser.filenotfound"));
		}
		catch(IOException ioe) {
			throw new ApplicationException(ResourceUtil.getString("parser.error"));
		}
		catch(Exception e) {
			throw new ApplicationException(ResourceUtil.getString("parser.error"));
		}
		finally {
			if (input != null) {
				try {
					input.close();
				}
				catch (IOException ioe) {
					// Do nothing
				}
			}
		}		
	}
	
	private static void parseLine(String line) throws ApplicationException {
		switch(line.charAt(0)) {
			case '#':
				// Ignore comments
				break;
				
			case '[':
				// Parse section start
				if (line.equals("[groups]")) {
					// Start processing groups
					if (currentState != STATE_START) {
						throw new ApplicationException(ResourceUtil.getString("parser.syntaxerror.multiplegroupsection"));
					}
					
					currentState = STATE_PROCESS_GROUPS;
				}
				else if (line.indexOf(':') == -1) {
					// Server level access
					currentState = STATE_PROCESS_SERVER_RULES;
					
					String path = line.substring(1, line.length() - 1).trim();
					
					currentPath = Document.addPath(null, path);
				}
				else if (line.indexOf(':') >= 0) {
					// Repository level access
					currentState = STATE_PROCESS_RULES;
					
					int index = line.indexOf(':');
					
					String repository = line.substring(1, index).trim();
					String path = line.substring(index + 1, line.length() - 1).trim();
					
					Repository repositoryObject = Document.addRepository(repository);
					currentPath = Document.addPath(repositoryObject, path);
				}
				else {
					throw new ApplicationException(ResourceUtil.getString("parser.syntaxerror.invalidpath"));
				}
				
				break;
				
			case '@':
				if (currentState == STATE_PROCESS_RULES || currentState == STATE_PROCESS_SERVER_RULES) {
					// Group Access
					int index = line.indexOf('=');
					
					String group = line.substring(1, index).trim();
					String level = line.substring(index + 1).trim();
					
					Document.addAccessRuleForGroup(currentPath, group, level);
				}
				else {
					throw new ApplicationException(ResourceUtil.getString("parser.syntaxerror.invalidgrouprule"));
				}
				
				break;
				
			default:
				if (currentState == STATE_PROCESS_GROUPS) {
					int index = line.indexOf('=');
					
					if (index == -1) {
						throw new ApplicationException(ResourceUtil.getString("parser.syntaxerror.invalidgroupdefinition"));
					}
					
					String name = line.substring(0, index).trim();
					String members = line.substring(index + 1).trim();
					StringTokenizer tokens = new StringTokenizer(members, " ,");
					int memberCount = tokens.countTokens();
					List groupMembers = new ArrayList();
					List userMembers = new ArrayList();
					
					for(int i = 0; i < memberCount; i++) {
						String member = tokens.nextToken();
						
						if (member.charAt(0) == '@') {
							groupMembers.add(member.substring(1, member.length()));
						}
						else {
							userMembers.add(member);
						}
					}
					
					Document.addGroup(name, groupMembers, userMembers);
				}
				else if (currentState == STATE_PROCESS_RULES || currentState == STATE_PROCESS_SERVER_RULES) {
					// User Access
					int index = line.indexOf('=');
					
					String user = line.substring(0, index).trim();
					String level = line.substring(index + 1).trim();
					
					Document.addAccessRuleForUser(currentPath, user, level);
				}
				
			break;
		}	
	}
}
