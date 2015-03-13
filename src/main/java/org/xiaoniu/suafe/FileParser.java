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

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.exceptions.ParserException;
import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.parser.FileEncodingUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Subversion user authentication file parser. Reads and parse auth file and populates the Document object.
 *
 * @author Shaun Johnson
 */
public final class FileParser {

    private enum State {
        STATE_PROCESS_ALIASES, STATE_PROCESS_GROUPS, STATE_PROCESS_RULES, STATE_PROCESS_SERVER_RULES, STATE_START
    }

    ;

    /**
     * Group currently being processed.
     */
    private Group currentGroup = null;

    /**
     * Path currently being processed.
     */
    private Path currentPath = null;

    /**
     * Current parser state.
     */
    private State currentState = null;

    public Document parse(final BufferedReader input) throws ParserException, ValidatorException {
        final Document document = new Document();
        int lineNumber = 0;

        currentState = State.STATE_START;

        try {
            String line = input.readLine();
            lineNumber++;

            document.initialize();

            while (line != null) {
                parseLine(document, lineNumber, line);

                line = input.readLine();
                lineNumber++;
            }
        }
        catch (final IOException ioe) {
            throw ParserException.generateException(lineNumber, "parser.error");
        }
        catch (final ParserException pe) {
            throw pe;
        }
        catch (final AppException ae) {
            throw ParserException.generateException(lineNumber, ae);
        }
        catch (final Exception e) {
            throw ParserException.generateException(lineNumber, "parser.error");
        }

        return document;
    }

    /**
     * Reads and parses information from the specified authz file.
     *
     * @param file File to be processed.
     * @throws ParserException
     * @throws ValidatorException
     */
    public Document parse(final File file) throws ParserException, ValidatorException {
        Document document = null;
        BufferedReader input = null;
        final int lineNumber = 0;

        currentState = State.STATE_START;

        validateReadable(file);

        try {
            final String encoding = FileEncodingUtils.detect(file, "ISO-8859-1");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            document = parse(input);
            document.setEncoding(encoding);
        }
        catch (final FileNotFoundException fne) {
            throw ParserException.generateException(lineNumber, "parser.filenotfound");
        }
        catch (final ParserException pe) {
            throw pe;
        }
        catch (final Exception e) {
            throw ParserException.generateException(lineNumber, "parser.error");
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (final IOException ioe) {
                    // Do nothing
                }
            }
        }

        return document;
    }

    public Document parse(final InputStream inputStream) throws ParserException, ValidatorException {
        Document document = null;
        BufferedReader input = null;

        currentState = State.STATE_START;

        try {
            input = new BufferedReader(new InputStreamReader(inputStream));

            document = parse(input);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (final IOException ioe) {
                    // Do nothing
                }
            }
        }

        return document;
    }

    private void parseAlias(final Document document, final int lineNumber, final String line) throws AppException {
        final int index = line.indexOf('=');

        if (index == 0) {
            // Invalid syntax
            throw ParserException.generateException(lineNumber, "parser.syntaxerror.invalidaliasdefinition");
        }

        // New Group
        final String aliasName = line.substring(0, index).trim();
        final String userName = line.substring(index + 1).trim();

        document.addUser(userName, aliasName);
    }

    private void parseGroup(final Document document, final int lineNumber, final String line) throws AppException {
        final int index = line.indexOf('=');

        if (index == 0) {
            // Invalid syntax
            throw ParserException.generateException(lineNumber, "parser.syntaxerror.invalidgroupdefinition");
        }

        if (currentGroup != null) {
            // Invalid syntax
            throw ParserException.generateException(lineNumber, "parser.syntaxerror.invalidgroupdefinition");
        }

        // New Group
        final String name = line.substring(0, index).trim();
        final String members = line.substring(index + 1).trim();
        final StringTokenizer tokens = new StringTokenizer(members, " ,");
        final int memberCount = tokens.countTokens();
        final List<String> aliasMembers = new ArrayList<String>();
        final List<String> groupMembers = new ArrayList<String>();
        final List<String> userMembers = new ArrayList<String>();

        for (int i = 0; i < memberCount; i++) {
            final String member = tokens.nextToken();

            if (member.charAt(0) == '@') {
                final String memberGroupName = member.substring(1, member.length());

                if (!groupMembers.contains(memberGroupName)) {
                    groupMembers.add(memberGroupName);
                }
            }
            else if (member.charAt(0) == '&') {
                final String memberAliasName = member.substring(1, member.length());

                if (!aliasMembers.contains(memberAliasName)) {
                    aliasMembers.add(memberAliasName);
                }
            }
            else {
                if (!userMembers.contains(member)) {
                    userMembers.add(member);
                }
            }
        }

        Group existingGroup = document.findGroup(name);

        if (existingGroup == null) {
            try {
                existingGroup = document.addGroupByName(name, groupMembers, userMembers, aliasMembers);
            }
            catch (final AppException ae) {
                throw ParserException.generateException(lineNumber, ae);
            }
        }
        else {
            // Group already exists.

            if (existingGroup.getGroupMembers().isEmpty() && existingGroup.getUserMembers().isEmpty()) {
                // Existing group does not have any members
                document.addMembersByName(existingGroup, groupMembers, userMembers, aliasMembers);

            }
            else {
                // Existing group already has members. This is likely a duplicate group definition
                throw ParserException.generateException(lineNumber, "parser.syntaxerror.duplicategroup", name);
            }
        }

        // Keep group for next line if there are more lines to process
        final String slimLine = line.trim();

        if (slimLine.charAt(slimLine.length() - 1) == ',') {
            currentGroup = existingGroup;
        }
    }

    private void parseGroupAccessRule(final Document document, final int lineNumber, final String line)
            throws AppException {
        // Group Access
        final int index = line.indexOf('=');

        final String group = line.substring(1, index).trim();
        final String level = line.substring(index + 1).trim();

        if (document.findGroup(group) == null) {
            throw ParserException.generateException(lineNumber, "parser.syntaxerror.undefinedgroup", group);
        }

        try {
            document.addAccessRuleForGroup(currentPath, group, level);
        }
        catch (final AppException ae) {
            throw ParserException.generateException(lineNumber, ae);
        }
    }

    private void parseGroupWrappedLine(final Document document, final int lineNumber, final String line)
            throws AppException {
        final StringTokenizer tokens = new StringTokenizer(line.trim(), " ,");
        final int memberCount = tokens.countTokens();
        final List<String> aliasMembers = new ArrayList<String>();
        final List<String> groupMembers = new ArrayList<String>();
        final List<String> userMembers = new ArrayList<String>();

        for (int i = 0; i < memberCount; i++) {
            final String member = tokens.nextToken();

            if (member.charAt(0) == '@') {
                final String memberGroupName = member.substring(1, member.length());

                if (!groupMembers.contains(memberGroupName)) {
                    groupMembers.add(memberGroupName);
                }
            }
            else if (member.charAt(0) == '&') {
                final String memberAliasName = member.substring(1, member.length());

                if (!aliasMembers.contains(memberAliasName)) {
                    aliasMembers.add(memberAliasName);
                }
            }
            else {
                if (!userMembers.contains(member)) {
                    userMembers.add(member);
                }
            }
        }

        document.addMembersByName(currentGroup, groupMembers, userMembers, aliasMembers);

        // Keep group for next line if there are more lines to process
        final String slimLine = line.trim();

        if (slimLine.charAt(slimLine.length() - 1) != ',') {
            currentGroup = null;
        }
    }

    /**
     * Parses a single line in the authz file.
     *
     * @param document   Document to be updated with values parsed.
     * @param lineNumber Number of the line being processed.
     * @param line       Content of the line.
     * @throws ParserException
     * @throws AppException
     */
    private void parseLine(final Document document, final int lineNumber, String line) throws ParserException,
            AppException {
        // Process non-blank lines
        if (isBlank(line)) {
            if (currentState == State.STATE_PROCESS_GROUPS && currentGroup != null) {
                // Invalid syntax
                throw ParserException.generateException(lineNumber, "parser.syntaxerror.invalidgroupdefinition");
            }

            return;
        }

        // Replace all "tab" characters with a space
        line = line.replaceAll("\\t", " ");

        switch (line.charAt(0)) {
            case '#':
                // Ignore comments
                break;

            case '[':
                // Parse section start
                if (line.equals("[aliases]")) {
                    if (currentState != State.STATE_START) {
                        throw ParserException.generateException(lineNumber, "parser.syntaxerror.multiplealiassection");
                    }

                    currentState = State.STATE_PROCESS_ALIASES;
                }
                else if (line.equals("[groups]")) {
                    if (currentState != State.STATE_START && currentState != State.STATE_PROCESS_ALIASES) {
                        throw ParserException.generateException(lineNumber, "parser.syntaxerror.multiplegroupsection");
                    }

                    currentState = State.STATE_PROCESS_GROUPS;
                }
                else {
                    final int index = line.indexOf(':');

                    if (index == -1) {
                        // Server level access (e.g. [/path])
                        currentState = State.STATE_PROCESS_SERVER_RULES;

                        parseServerPath(document, lineNumber, line);
                    }
                    else if (index >= 0) {
                        // Repository level access (e.g. [repository:/path])
                        currentState = State.STATE_PROCESS_RULES;

                        parseRepositoryPath(document, lineNumber, line, index);
                    }
                    else {
                        throw ParserException.generateException(lineNumber, "parser.syntaxerror.invalidpath");
                    }
                }
                break;

            case '@':
                if (currentState == State.STATE_PROCESS_RULES || currentState == State.STATE_PROCESS_SERVER_RULES) {
                    // Group Access
                    parseGroupAccessRule(document, lineNumber, line);
                }
                else {
                    throw ParserException.generateException(lineNumber, "parser.syntaxerror.invalidgrouprule");
                }

                break;

            case ' ':
                // Group, continued
                parseGroupWrappedLine(document, lineNumber, line);

                break;

            default:
                if (currentState == State.STATE_PROCESS_ALIASES) {
                    parseAlias(document, lineNumber, line);
                }
                else if (currentState == State.STATE_PROCESS_GROUPS) {
                    parseGroup(document, lineNumber, line);
                }
                else if (currentState == State.STATE_PROCESS_RULES || currentState == State.STATE_PROCESS_SERVER_RULES) {
                    parseUserAccessRule(document, lineNumber, line);
                }

                break;
        }
    }

    private void parseRepositoryPath(final Document document, final int lineNumber, final String line, final int index)
            throws AppException {
        final String repository = line.substring(1, index).trim();
        final String path = line.substring(index + 1, line.length() - 1).trim();
        Repository repositoryObject = null;

        try {
            repositoryObject = document.addRepository(repository);
        }
        catch (final AppException ae) {
            throw ParserException.generateException(lineNumber, ae);
        }

        if (document.findPath(repositoryObject, path) != null) {
            final Object[] args = new Object[2];
            args[0] = path;
            args[1] = repository;
            throw ParserException.generateException(lineNumber, "parser.syntaxerror.duplicatepathrepository", args);
        }

        try {
            currentPath = document.addPath(repositoryObject, path);
        }
        catch (final AppException ae) {
            throw ParserException.generateException(lineNumber, ae);
        }
    }

    private void parseServerPath(final Document document, final int lineNumber, final String line) throws AppException {
        final String path = line.substring(1, line.length() - 1).trim();

        if (document.findServerPath(path) != null) {
            throw ParserException.generateException(lineNumber, "parser.syntaxerror.duplicatepath", path);
        }

        try {
            currentPath = document.addPath(null, path);
        }
        catch (final AppException ae) {
            throw ParserException.generateException(lineNumber, ae);
        }
    }

    private void parseUserAccessRule(final Document document, final int lineNumber, final String line)
            throws ParserException {
        final int index = line.indexOf('=');

        final String user = line.substring(0, index).trim();
        final String level = line.substring(index + 1).trim();

        try {
            document.addAccessRuleForUser(currentPath, user, level);
        }
        catch (final AppException ae) {
            throw ParserException.generateException(lineNumber, ae);
        }
    }

    /**
     * Validates whether the supplied file exists and is readable.
     *
     * @param file File to be validated.
     * @throws ValidatorException
     */
    public void validateReadable(final File file) throws ValidatorException {
        if (!file.exists()) {
            throw new ValidatorException("parser.filenotfound");
        }

        if (!file.canRead()) {
            throw new ValidatorException("parser.unreadablefile");
        }
    }
}
