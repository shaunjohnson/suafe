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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.exceptions.ParserException;
import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.resources.ResourceUtil;


/**
 * Subversion user authentication file parser.
 * Reads and parse auth file and populates the Document object.
 * 
 * @author Shaun Johnson
 */
public class FileParser {
	
	/**
	 * Parser starting state. Indicates that nothing has been processed.
	 * The parser never returns to this state.
	 */
	private static final int STATE_START = 1;
	
	/**
	 * Parser is processing [groups]. The parser never returns to this state. 
	 */
	private static final int STATE_PROCESS_GROUPS = 2;	
	
	/**
	 * Parser is processing access rules for the server. The parser returns to
	 * this state each time a server path is encountered.
	 */
	private static final int STATE_PROCESS_SERVER_RULES = 3;
	
	/**
	 * Parser is processing access rules for a repository. The parser returns
	 * to this state each time a repository path is encountered. 
	 */
	private static final int STATE_PROCESS_RULES = 4;
	
	/**
	 * Current parser state.
	 */
	private static int currentState = STATE_START;
	
	/**
	 * Path currently being processed.
	 */
	private static Path currentPath = null;
	
	/**
	 * Validates whether the supplied file is readable.
	 * 
	 * @param file File to be validated.
	 * @throws ValidatorException
	 */
	public static void validateReadable(File file) throws ValidatorException {
		if (!file.canRead()) {
			throw new ValidatorException(ResourceUtil.getString("parser.unreadablefile"));
		}
	}
	
	public static void parse(InputStream inputStream) throws ParserException, ValidatorException {
		BufferedReader input = null;
		
		try { 
			input = new BufferedReader(new InputStreamReader(inputStream));
			
			parse(input);
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
	
	/**
	 * Reads and parses information from the specified authz file.
	 * 
	 * @param file File to be processed.
	 * @throws ParserException
	 * @throws ValidatorException
	 */
	public static void parse(File file) throws ParserException, ValidatorException {
		BufferedReader input = null;
		int lineNumber = 0;
		
		currentState = STATE_START;
		
		validateReadable(file);
		
		try {
			input = new BufferedReader(new FileReader(file));
			parse(input);
		}
		catch(FileNotFoundException fne) {
			throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.filenotfound"));
		}
		catch(ParserException pe) {
			throw pe;
		}
		catch(Exception e) {
			throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.error"));
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
	
	public static void parse(BufferedReader input) throws ParserException, ValidatorException {
		int lineNumber = 0;
		
		currentState = STATE_START;
		
		try {
			String line = input.readLine();
			lineNumber++;
			
			Document.initialize();
			
			while (line != null) {
				line = line.trim();
				
				// Process non-blank lines
				if (!line.equals("")) {
					parseLine(lineNumber, line);
				}
				
				line = input.readLine();
				lineNumber++;
			}
		}
		catch(IOException ioe) {
			throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.error"));
		}
		catch(ParserException pe) {
			throw pe;
		}
		catch(ApplicationException ae) {
			throw ParserException.generateException(lineNumber, ae.getMessage());
		}
		catch(Exception e) {
			throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.error"));
		}	
	}
	
	/**
	 * Parses a single line in the authz file.
	 * 
	 * @param lineNumber Number of the line being processed.
	 * @param line Content of the line.
	 * @throws ParserException
	 * @throws ApplicationException
	 */
	private static void parseLine(int lineNumber, String line) throws ParserException, ApplicationException {
		switch(line.charAt(0)) {
			case '#':
				// Ignore comments
				break;
				
			case '[':
				// Parse section start
				if (line.equals("[groups]")) {
					// Start processing groups
					if (currentState != STATE_START) {
						throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.syntaxerror.multiplegroupsection"));
					}
					
					currentState = STATE_PROCESS_GROUPS;
				}
				else if (line.indexOf(':') == -1) {
					// Server level access
					currentState = STATE_PROCESS_SERVER_RULES;
					
					String path = line.substring(1, line.length() - 1).trim();
					
					if (Document.findServerPath(path) != null) {
						throw ParserException.generateException(lineNumber, ResourceUtil.getFormattedString("parser.syntaxerror.duplicatepath", path));
					}
					
					try {
						currentPath = Document.addPath(null, path);
					}
					catch (ApplicationException ae) {
						throw ParserException.generateException(lineNumber, ae.getMessage());
					}
				}
				else if (line.indexOf(':') >= 0) {
					// Repository level access
					currentState = STATE_PROCESS_RULES;
					
					int index = line.indexOf(':');
					
					String repository = line.substring(1, index).trim();
					String path = line.substring(index + 1, line.length() - 1).trim();
					Repository repositoryObject = null;
					
					try {
						repositoryObject = Document.addRepository(repository);
					}
					catch (ApplicationException ae) {
						throw ParserException.generateException(lineNumber, ae.getMessage());
					}
					
					if (Document.findPath(repositoryObject, path) != null) {
						Object[] args = new Object[2];
						args[0] = path;
						args[1] = repository;
						throw ParserException.generateException(lineNumber, ResourceUtil.getFormattedString("parser.syntaxerror.duplicatepathrepository", args));
					}
					
					try {
						currentPath = Document.addPath(repositoryObject, path);
					}
					catch (ApplicationException ae) {
						throw ParserException.generateException(lineNumber, ae.getMessage());
					}
				}
				else {
					throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.syntaxerror.invalidpath"));
				}
				
				break;
				
			case '@':
				if (currentState == STATE_PROCESS_RULES || currentState == STATE_PROCESS_SERVER_RULES) {
					// Group Access
					int index = line.indexOf('=');
					
					String group = line.substring(1, index).trim();
					String level = line.substring(index + 1).trim();
					
					if (Document.findGroup(group) == null) {
						throw ParserException.generateException(lineNumber, ResourceUtil.getFormattedString("parser.syntaxerror.undefinedgroup", group));
					}
					
					try {
						Document.addAccessRuleForGroup(currentPath, group, level);
					}
					catch (ApplicationException ae) {
						throw ParserException.generateException(lineNumber, ae.getMessage());
					}
				}
				else {
					throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.syntaxerror.invalidgrouprule"));
				}
				
				break;
				
			default:
				if (currentState == STATE_PROCESS_GROUPS) {
					int index = line.indexOf('=');
					
					if (index == -1) {
						throw ParserException.generateException(lineNumber, ResourceUtil.getString("parser.syntaxerror.invalidgroupdefinition"));
					}
					
					String name = line.substring(0, index).trim();
					String members = line.substring(index + 1).trim();
					StringTokenizer tokens = new StringTokenizer(members, " ,");
					int memberCount = tokens.countTokens();
					List<String> groupMembers = new ArrayList<String>();
					List<String> userMembers = new ArrayList<String>();
					
					for(int i = 0; i < memberCount; i++) {
						String member = tokens.nextToken();
						
						if (member.charAt(0) == '@') {
							groupMembers.add(member.substring(1, member.length()));
						}
						else {
							userMembers.add(member);
						}
					}
					
					if (Document.findGroup(name) != null) {
						throw ParserException.generateException(lineNumber, ResourceUtil.getFormattedString("parser.syntaxerror.duplicategroup", name));
					}
					
					try {
						Document.addGroupByName(name, groupMembers, userMembers);
					}
					catch (ApplicationException ae) {
						throw ParserException.generateException(lineNumber, ae.getMessage());
					}
				}
				else if (currentState == STATE_PROCESS_RULES || currentState == STATE_PROCESS_SERVER_RULES) {
					// User Access
					int index = line.indexOf('=');
					
					String user = line.substring(0, index).trim();
					String level = line.substring(index + 1).trim();
					
					try {
						Document.addAccessRuleForUser(currentPath, user, level);
					}
					catch (ApplicationException ae) {
						throw ParserException.generateException(lineNumber, ae.getMessage());
					}
				}
				
			break;
		}	
	}
}
