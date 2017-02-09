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
package net.lmxm.suafe.reports;

import net.lmxm.suafe.api.SubversionConstants;
import net.lmxm.suafe.api.beans.*;
import net.lmxm.suafe.Project;
import net.lmxm.suafe.exceptions.AppException;
import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Summary report class.
 *
 * @author Shaun Johnson
 */
public final class SummaryReport extends GenericReport {
    /**
     * Generates HTML anchor tag.
     *
     * @param anchor Anchor name
     * @param text   Anchor text
     * @return HTML anchor tag text
     */
    @Nonnull
    private static String createAnchor(final String anchor, final String text) {
        return "<a name=\"" + anchor + "\">" + text + "</a>";
    }

    /**
     * Generates HTML anchor tag for Group.
     *
     * @param groupName Group name
     * @return HTML anchor tag text
     */
    @Nonnull
    private static String createGroupAnchor(final String groupName) {
        return createAnchor("group_" + groupName, SubversionConstants.SVN_GROUP_REFERENCE_PREFIX + groupName);
    }

    /**
     * Creates HTML link tag for Group.
     *
     * @param groupName Group name
     * @return HTML link tag text
     */
    @Nonnull
    private static String createGroupLink(final String groupName) {
        return createLink("group_" + groupName, SubversionConstants.SVN_GROUP_REFERENCE_PREFIX + groupName);
    }

    /**
     * Creates HTML link tag.
     *
     * @param href Link href
     * @param text Link text
     * @return HTML link tag text
     */
    @Nonnull
    private static String createLink(final String href, final String text) {
        return "<a href=\"#" + href + "\">" + text + "</a>";
    }

    /**
     * Generates HTML anchor tag for Repository.
     *
     * @param repositoryName Repository name
     * @return HTML anchor tag text
     */
    @Nonnull
    private static String createReposAnchor(final String repositoryName) {
        return createAnchor("repos_" + repositoryName, repositoryName);
    }

    /**
     * Creates HTML link tag for Repository.
     *
     * @param repositoryName Repository name
     * @return HTML link tag text
     */
    @Nonnull
    private static String createReposLink(final String repositoryName) {
        return createLink("repos_" + repositoryName, repositoryName);
    }

    /**
     * Generates HTML anchor tag for User.
     *
     * @param userName User name
     * @return HTML anchor tag text
     */
    @Nonnull
    private static String createUserAnchor(final String userName) {
        if (userName.equals(SubversionConstants.SVN_ALL_USERS_NAME)) {
            return createAnchor("all_users", userName);
        }
        else {
            return createAnchor("user_" + userName, userName);
        }
    }

    /**
     * Creates HTML link tag for User.
     *
     * @param userName User name
     * @return HTML link tag text
     */
    @Nonnull
    private static String createUserLink(final String userName) {
        if (userName.equals(SubversionConstants.SVN_ALL_USERS_NAME)) {
            return createLink("all_users", userName);
        }
        else {
            return createLink("user_" + userName, userName);
        }

    }

    public SummaryReport(@Nonnull final Document document) {
        super(document);
    }

    /**
     * Generates HTML summary report.
     *
     * @throws AppException if error occurs
     */
    @Override
    @Nonnull
    public String generate() throws AppException {
        final StringBuffer report = new StringBuffer();

        report.append(ResourceUtil.getString("reports.header"));
        report.append("<head>");
        report.append("<title>").append(ResourceUtil.getString("summaryreport.title")).append("</title>");
        report.append(ResourceUtil.getString("reports.contenttype"));
        report.append("</head><body>");
        report.append("<h1>").append(ResourceUtil.getString("summaryreport.title")).append("</h1>");

        report.append("<p>[").append(createLink("repositories", "Repositories")).append("] ").append("[")
                .append(createLink("groups", ResourceUtil.getString("summaryreport.groups"))).append("] ").append("[")
                .append(createLink("users", ResourceUtil.getString("summaryreport.users"))).append("] ").append("[")
                .append(createLink("projects", ResourceUtil.getString("summaryreport.projects"))).append("] </p>");

        final List<Repository> repositories = document.getRepositories();
        Collections.sort(repositories);

        report.append("<h2>").append(createAnchor("repositories", ResourceUtil.getString("summaryreport.repositories"))
               ).append("</h2>");
        report.append("<blockquote>");

        if (repositories.size() == 0) {
            report.append("<p>").append(ResourceUtil.getString("summaryreport.norepos")).append("</p>");
        }
        else {
            report.append("<ul>");

            for (final Repository repository : repositories) {
                report.append("<li>").append(createReposLink(repository.getName())).append("</li>");
            }

            report.append("</ul>");
        }

        report.append("</blockquote>");

        report.append("<h2>").append(ResourceUtil.getString("summaryreport.serverrules")).append("</h2>");

        final List<Path> serverPaths = document.getPaths();
        Collections.sort(serverPaths, new PathComparator());

        if (serverPaths.size() > 0) {
            report.append("<blockquote>");

            for (final Path path : serverPaths) {
                if (path.getRepository() != null) {
                    continue;
                }

                final List<AccessRule> rules = path.getAccessRules();
                Collections.sort(rules);

                report.append("<p><strong>").append(path.getPath()).append("</strong></p><ul>");

                for (final AccessRule rule : rules) {
                    if (rule.getGroup() != null) {
                        report.append("<li>").append(createGroupLink(rule.getGroup().getName())).append(" = ")
                               .append(rule.getLevelFullName()).append("</li>");
                    }
                    else if (rule.getUser() != null) {
                        report.append("<li>").append(createUserLink(rule.getUser().getName())).append(" = ")
                               .append(rule.getLevelFullName()).append("</li>");
                    }
                    else {
                        throw new AppException("summaryreport.invalidrule");
                    }
                }

                report.append("</ul>");
            }

            report.append("</blockquote>");
        }

        if (repositories.size() > 0) {
            for (final Repository repository : repositories) {
                report.append("<h2>").append(createReposAnchor(repository.getName())).append("</h2>");

                final List<Path> paths = repository.getPaths();
                Collections.sort(paths, new PathComparator());

                report.append("<blockquote>");

                for (final Path path : paths) {
                    final List<AccessRule> rules = path.getAccessRules();
                    Collections.sort(rules);

                    report.append("<p><strong>").append(path.getPath()).append("</strong></p><ul>");

                    for (final AccessRule rule : rules) {
                        if (rule.getGroup() != null) {
                            report.append("<li>").append(createGroupLink(rule.getGroup().getName())).append(" = ")
                                   .append(rule.getLevelFullName()).append("</li>");
                        }
                        else if (rule.getUser() != null) {
                            report.append("<li>").append(createUserLink(rule.getUser().getName())).append(" = ")
                                   .append(rule.getLevelFullName()).append("</li>");
                        }
                        else {
                            throw new AppException("summaryreport.invalidrule");
                        }
                    }

                    report.append("</ul>");
                }

                report.append("</blockquote>");
            }
        }

        final List<Group> groups = document.getGroups();
        Collections.sort(groups);

        report.append("<h2>").append(createAnchor("groups", ResourceUtil.getString("summaryreport.groups"))).append("</h2>");
        report.append("<blockquote>");

        for (final Group group : groups) {
            report.append("<p><strong>").append(createGroupAnchor(group.getName())).append("</strong></p>");

            final List<Group> groupMembers = group.getGroupMembers();
            final List<User> userMembers = group.getUserMembers();
            final List<AccessRule> rules = group.getAccessRules();

            Collections.sort(groupMembers);
            Collections.sort(userMembers);
            Collections.sort(rules);

            report.append("<blockquote><p>Members</p>");

            if (groupMembers.size() == 0 && userMembers.size() == 0) {
                report.append("<blockquote><p>").append(ResourceUtil.getString("summaryreport.nomembers"))
                       .append("</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final Group groupMember : groupMembers) {
                    report.append("<li>").append(createGroupLink(groupMember.getName())).append("</li>");
                }

                for (final User userMember : userMembers) {
                    report.append("<li>").append(createUserLink(userMember.getName())).append("</li>");
                }

                report.append("</ul>");
            }

            report.append("<p>").append(ResourceUtil.getString("summaryreport.rules")).append("</p>");

            if (rules.size() == 0) {
                report.append("<blockquote><p>")
                        .append(ResourceUtil.getString("summaryreport.norules"))
                        .append("</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final AccessRule rule : rules) {
                    final String repositoryName = (rule.getPath().getRepository() == null) ? "" : rule.getPath()
                            .getRepository().getName() + ":";
                    final String path = rule.getPath().getPath();

                    report.append("<li>[").append(repositoryName).append(path).append("] = ")
                            .append(rule.getLevelFullName()).append("</li>");
                }

                report.append("</ul>");
            }

            report.append("</blockquote>");
        }

        report.append("</blockquote>");

        final List<User> users = document.getUsers();
        Collections.sort(users);

        report.append("<h2>").append(createAnchor("users", ResourceUtil.getString("summaryreport.users"))).append("</h2>");
        report.append("<blockquote>");

        for (final User user : users) {
            report.append("<p><strong>").append(createUserAnchor(user.getName())).append("</strong></p>");

            final List<Group> userGroups = user.getGroups();
            final List<AccessRule> rules = user.getAccessRules();

            Collections.sort(userGroups);
            Collections.sort(rules);

            report.append("<blockquote><p>").append(ResourceUtil.getString("summaryreport.groups")).append("</p>");

            if (userGroups.size() == 0) {
                report.append("<blockquote><p>").append(ResourceUtil.getString("summaryreport.nogroups"))
                       .append("</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final Group userGroup : userGroups) {
                    report.append("<li>").append(createGroupLink(userGroup.getName())).append("</li>");
                }

                report.append("</ul>");
            }

            report.append("<p>").append(ResourceUtil.getString("summaryreport.rules")).append("</p>");

            if (rules.size() == 0) {
                report.append("<blockquote><p>").append(ResourceUtil.getString("summaryreport.norules"))
                               .append("</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final AccessRule rule : rules) {
                    final String repositoryName = (rule.getPath().getRepository() == null) ? "" :
                            rule.getPath().getRepository().getName() + ":";
                    final String path = rule.getPath().getPath();

                    report.append("<li>[").append(repositoryName).append(path).append("] = ")
                            .append(rule.getLevelFullName()).append("</li>");
                }

                report.append("</ul>");
            }

            report.append("</blockquote>");
        }

        report.append("</blockquote>");

        report.append("<h2>").append(createAnchor("projects", ResourceUtil.getString("summaryreport.projects"))).append("</h2>");

        if (repositories.size() > 0) {
            for (final Repository repository : repositories) {
                final List<Path> paths = repository.getPaths();
                Collections.sort(paths, new PathComparator());

                final HashMap<String, Project> projects = new HashMap<String, Project>();

                for (final Path path : paths) {
                    final List<AccessRule> rules = path.getAccessRules();
                    Collections.sort(rules);

                    final String pathString = path.getPath();

                    if (pathString.endsWith(ResourceUtil.getString("summaryreport.path.trunk"))) {
                        final String projectName = pathString.substring(0, pathString.lastIndexOf(ResourceUtil
                                .getString("summaryreport.path.trunk")));

                        final Project projectBean = projects.get(projectName);

                        if (projectBean == null) {
                            final Project newProjectBean = new Project();

                            newProjectBean.trunk = path;

                            projects.put(projectName, newProjectBean);
                        }
                        else {
                            projectBean.trunk = path;
                        }
                    }
                    else if (pathString.endsWith(ResourceUtil.getString("summaryreport.path.branches"))) {
                        final String projectName = pathString.substring(0, pathString.lastIndexOf(ResourceUtil
                                .getString("summaryreport.path.branches")));

                        final Project projectBean = projects.get(projectName);

                        if (projectBean == null) {
                            final Project newProjectBean = new Project();

                            newProjectBean.branches = path;

                            projects.put(projectName, newProjectBean);
                        }
                        else {
                            projectBean.branches = path;
                        }
                    }
                    else if (pathString.endsWith(ResourceUtil.getString("summaryreport.path.tags"))) {
                        final String projectName = pathString.substring(0, pathString.lastIndexOf(ResourceUtil
                                .getString("summaryreport.path.tags")));

                        final Project projectBean = projects.get(projectName);

                        if (projectBean == null) {
                            final Project newProjectBean = new Project();

                            newProjectBean.tags = path;

                            projects.put(projectName, newProjectBean);
                        }
                        else {
                            projectBean.tags = path;
                        }
                    }
                }

                report.append("<h3>").append(createReposAnchor(repository.getName())).append("</h3>");

                report.append("<blockquote>");

                if (projects.size() == 0) {
                    report.append("<p>").append(ResourceUtil.getString("summaryreport.noprojects")).append("</p>");
                }

                for (final String key : projects.keySet()) {
                    final Project projectBean = projects.get(key);
                    List<AccessRule> rules = null;

                    report.append("<p><strong>").append(key).append("</strong></p><blockquote>");

                    if (projectBean.branches != null) {
                        report.append("<p>").append(ResourceUtil.getString("summaryreport.branches")).append("</p>");
                        rules = projectBean.branches.getAccessRules();
                        Collections.sort(rules);

                        if (rules.size() == 0) {
                            report.append("<blockquote><p>").append(ResourceUtil.getString("summaryreport.norules"))
                                    .append("</p></blockquote>>");
                        }
                        else {
                            report.append("<ul>");

                            for (final AccessRule rule : rules) {
                                if (rule.getGroup() != null) {
                                    report.append("<li>").append(createGroupLink(rule.getGroup().getName()))
                                            .append(" = ").append(rule.getLevelFullName()).append("</li>");
                                }
                                else if (rule.getUser() != null) {
                                    report.append("<li>").append(createUserLink(rule.getUser().getName()))
                                            .append(" = ").append(rule.getLevelFullName()).append("</li>");
                                }
                                else {
                                    throw new AppException("summaryreport.invalidrule");
                                }
                            }

                            report.append("</ul>");
                        }
                    }

                    if (projectBean.tags != null) {
                        report.append("<p>").append(ResourceUtil.getString("summaryreport.tags")).append("</p>");
                        rules = projectBean.tags.getAccessRules();
                        Collections.sort(rules);

                        if (rules.size() == 0) {
                            report.append("<blockquote><p>").append(ResourceUtil.getString("summaryreport.norules"))
                                   .append("</p></blockquote>>");
                        }
                        else {
                            report.append("<ul>");

                            for (final AccessRule rule : rules) {
                                if (rule.getGroup() != null) {
                                    report.append("<li>").append(createGroupLink(rule.getGroup().getName()))
                                            .append(" = ").append(rule.getLevelFullName()).append("</li>");
                                }
                                else if (rule.getUser() != null) {
                                    report.append("<li>").append(createUserLink(rule.getUser().getName()))
                                            .append(" = ").append(rule.getLevelFullName()).append("</li>");
                                }
                                else {
                                    throw new AppException("summaryreport.invalidrule");
                                }
                            }

                            report.append("</ul>");
                        }
                    }

                    if (projectBean.trunk != null) {
                        report.append("<p>").append(ResourceUtil.getString("summaryreport.trunk")).append("</p>");
                        rules = projectBean.trunk.getAccessRules();
                        Collections.sort(rules);

                        if (rules.size() == 0) {
                            report.append("<blockquote><p>").append(ResourceUtil.getString("summaryreport.norules")
                                   ).append("</p></blockquote>>");
                        }
                        else {
                            report.append("<ul>");

                            for (final AccessRule rule : rules) {
                                if (rule.getGroup() != null) {
                                    report.append("<li>").append(createGroupLink(rule.getGroup().getName()))
                                            .append(" = ").append(rule.getLevelFullName()).append("</li>");
                                }
                                else if (rule.getUser() != null) {
                                    report.append("<li>").append(createUserLink(rule.getUser().getName()))
                                            .append(" = ").append(rule.getLevelFullName()).append("</li>");
                                }
                                else {
                                    throw new AppException("summaryreport.invalidrule");
                                }
                            }

                            report.append("</ul>");
                        }
                    }

                    report.append("</blockquote>");
                }

                report.append("</blockquote>");
            }
        }

        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        final Object[] args = new Object[3];
        args[0] = dateFormat.format(new Date());
        args[1] = ResourceUtil.getString("application.url");
        args[2] = ResourceUtil.getString("application.nameversion");

        report.append(ResourceUtil.getFormattedString("reports.footer", args));

        report.append("</body></html>");

        return report.toString();
    }
}
