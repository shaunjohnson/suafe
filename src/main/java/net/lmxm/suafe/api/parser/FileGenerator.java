/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.lmxm.suafe.api.parser;

import net.lmxm.suafe.Constants;
import net.lmxm.suafe.api.beans.*;
import net.lmxm.suafe.exceptions.AppException;
import org.apache.commons.lang3.StringUtils;
import net.lmxm.suafe.resources.ResourceUtil;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;

/**
 * Provides methods to output the authz file information from memory.
 *
 * @author Shaun Johnson
 */
public final class FileGenerator {

    private final int DEFAULT_MAX_LINE_LENGTH = 80;

    private Document document = null;

    public FileGenerator(Document document) {
        super();

        this.document = document;
    }

    private String createPrefix(int length) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }

        return builder.toString();
    }

    /**
     * Generates authz content from the current document in memory.
     *
     * @return Authz file content
     * @throws AppException
     */
    public String generate(boolean allowMultipleLine) throws AppException {
        return generate(allowMultipleLine ? DEFAULT_MAX_LINE_LENGTH : -1);
    }

    /**
     * Generates and saves the current authz content in memory.
     *
     * @param file File where authz content is to be written.
     * @throws AppException
     */
    public void generate(File file, boolean allowMultipleLine) throws AppException {
        generate(file, allowMultipleLine ? DEFAULT_MAX_LINE_LENGTH : -1);
    }

    /**
     * Generates and saves the current authz content in memory.
     *
     * @param file          File where authz content is to be written.
     * @param maxLineLength Maximum line length
     * @throws AppException
     */
    public void generate(File file, int maxLineLength) throws AppException {
        PrintWriter output = null;

        try {
            final Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), document.getEncoding());
            output = new PrintWriter(new BufferedWriter(outputStreamWriter));

            // Process group definitions
            output.print(generate(maxLineLength));
        }
        catch (FileNotFoundException fne) {
            throw new AppException("generator.filenotfound");
        }
        catch (IOException ioe) {
            throw new AppException("generator.error");
        }
        catch (Exception e) {
            throw new AppException("generator.error");
        }
        finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /**
     * Generates authz content from the current document in memory.
     *
     * @param maxLineLength Maximum line length
     * @return Authz file content
     * @throws AppException
     */
    public String generate(int maxLineLength) throws AppException {
        final StringBuilder output = new StringBuilder();

        try {
            output.append("# ");
            output.append(ResourceUtil.getString("application.fileheader"));
            output.append(Constants.TEXT_NEW_LINE);
            output.append(Constants.TEXT_NEW_LINE);

            // Process alias definitions
            final StringBuilder aliases = new StringBuilder();

            Collections.sort(document.getUsers());

            for (User user : document.getUsers()) {
                if (StringUtils.isBlank(user.getAlias())) {
                    continue;
                }

                aliases.append(user.getAlias());
                aliases.append(" = ");
                aliases.append(user.getName());
                aliases.append(Constants.TEXT_NEW_LINE);
            }

            if (aliases.length() > 0) {
                output.append("[aliases]");
                output.append(Constants.TEXT_NEW_LINE);
                output.append(aliases);

                if (document.getGroups().size() > 0) {
                    output.append(Constants.TEXT_NEW_LINE);
                }
            }

            // Process group definitions
            output.append("[groups]");
            output.append(Constants.TEXT_NEW_LINE);

            Collections.sort(document.getGroups());

            for (Group group : document.getGroups()) {
                output.append(group.getName());
                output.append(" = ");

                String prefix = createPrefix(group.getName().length() + 3);
                boolean isFirstGroupMember = true;

                if (!group.getGroupMembers().isEmpty()) {
                    Collections.sort(group.getGroupMembers());

                    Iterator<Group> members = group.getGroupMembers().iterator();

                    StringBuffer groupLine = new StringBuffer();

                    while (members.hasNext()) {
                        Group memberGroup = members.next();

                        if (maxLineLength > 0 && !isFirstGroupMember
                                && (groupLine.length() + memberGroup.getName().length() > maxLineLength)) {
                            output.append(groupLine);
                            output.append(Constants.TEXT_NEW_LINE);
                            output.append(prefix);

                            groupLine = new StringBuffer();
                        }

                        groupLine.append("@");
                        groupLine.append(memberGroup.getName());

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

                    while (members.hasNext()) {
                        User memberUser = members.next();
                        String nameAlias = (StringUtils.isBlank(memberUser.getAlias()) ?
                                memberUser.getName() : "&" + memberUser.getAlias());

                        if (maxLineLength > 0 && !isFirstGroupMember
                                && (userLine.length() + nameAlias.length() > maxLineLength)) {
                            output.append(userLine);
                            output.append(Constants.TEXT_NEW_LINE);
                            output.append(prefix);

                            userLine = new StringBuffer();
                        }

                        userLine.append(nameAlias);

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
                    output.append("[");
                    output.append(path.getPath());
                    output.append("]");
                    output.append(Constants.TEXT_NEW_LINE);
                }
                else {
                    // Path permissions
                    output.append("[");
                    output.append(path.getRepository().getName());
                    output.append(":");
                    output.append(path.getPath());
                    output.append("]");
                    output.append(Constants.TEXT_NEW_LINE);
                }

                Collections.sort(path.getAccessRules());

                for (AccessRule rule : path.getAccessRules()) {
                    if (rule.getGroup() != null) {
                        output.append("@");
                        output.append(rule.getGroup().getName());
                        output.append(" = ");
                        output.append(rule.getLevel());
                        output.append(Constants.TEXT_NEW_LINE);
                    }
                    else if (rule.getUser() != null) {
                        User user = rule.getUser();

                        if (user.getAlias() == null) {
                            output.append(user.getName());
                        }
                        else {
                            output.append("&");
                            output.append(user.getAlias());
                        }

                        output.append(" = ");
                        output.append(rule.getLevel());
                        output.append(Constants.TEXT_NEW_LINE);
                    }
                    else {
                        throw new AppException("generator.error");
                    }
                }

                output.append(Constants.TEXT_NEW_LINE);
            }
        }
        catch (Exception e) {
            throw new AppException("generator.error");
        }

        return output.toString();
    }
}
