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
package org.xiaoniu.suafe.reports;

import org.xiaoniu.suafe.Project;
import org.xiaoniu.suafe.SubversionConstants;
import org.xiaoniu.suafe.beans.*;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.resources.ResourceUtil;

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
    private static String createAnchor(final String anchor, final String text) {
        return new String("<a name=\"" + anchor + "\">" + text + "</a>");
    }

    /**
     * Generates HTML anchor tag for Group.
     *
     * @param anchor Anchor name
     * @param text   Anchor text
     * @return HTML anchor tag text
     */
    private static String createGroupAnchor(final String groupName) {
        return createAnchor("group_" + groupName, SubversionConstants.SVN_GROUP_REFERENCE_PREFIX + groupName);
    }

    /**
     * Creates HTML link tag for Group.
     *
     * @param href Link href
     * @param text Link text
     * @return HTML link tag text
     */
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
    private static String createLink(final String href, final String text) {
        return new String("<a href=\"#" + href + "\">" + text + "</a>");
    }

    /**
     * Generates HTML anchor tag for Repository.
     *
     * @param anchor Anchor name
     * @param text   Anchor text
     * @return HTML anchor tag text
     */
    private static String createReposAnchor(final String repositoryName) {
        return createAnchor("repos_" + repositoryName, repositoryName);
    }

    /**
     * Creates HTML link tag for Repository.
     *
     * @param href Link href
     * @param text Link text
     * @return HTML link tag text
     */
    private static String createReposLink(final String repositoryName) {
        return createLink("repos_" + repositoryName, repositoryName);
    }

    /**
     * Generates HTML anchor tag for User.
     *
     * @param anchor Anchor name
     * @param text   Anchor text
     * @return HTML anchor tag text
     */
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
     * @param href Link href
     * @param text Link text
     * @return HTML link tag text
     */
    private static String createUserLink(final String userName) {
        if (userName.equals(SubversionConstants.SVN_ALL_USERS_NAME)) {
            return createLink("all_users", userName);
        }
        else {
            return createLink("user_" + userName, userName);
        }

    }

    public SummaryReport(final Document document) {
        super(document);
    }

    /**
     * Generates HTML summary report.
     *
     * @param out
     * @throws AppException
     */
    @Override
    public String generate() throws AppException {
        final StringBuffer report = new StringBuffer();

        report.append(ResourceUtil.getString("reports.header"));
        report.append("<head>");
        report.append("<title>" + ResourceUtil.getString("summaryreport.title") + "</title>");
        report.append(ResourceUtil.getString("reports.contenttype"));
        report.append("</head><body>");
        report.append("<h1>" + ResourceUtil.getString("summaryreport.title") + "</h1>");

        report.append("<p>[" + createLink("repositories", "Repositories") + "] " + "["
                + createLink("groups", ResourceUtil.getString("summaryreport.groups")) + "] " + "["
                + createLink("users", ResourceUtil.getString("summaryreport.users")) + "] " + "["
                + createLink("projects", ResourceUtil.getString("summaryreport.projects")) + "] </p>");

        final List<Repository> repositories = document.getRepositories();
        Collections.sort(repositories);

        report.append("<h2>" + createAnchor("repositories", ResourceUtil.getString("summaryreport.repositories"))
                + "</h2>");
        report.append("<blockquote>");

        if (repositories.size() == 0) {
            report.append("<p>" + ResourceUtil.getString("summaryreport.norepos") + "</p>");
        }
        else {
            report.append("<ul>");

            for (final Repository repository : repositories) {
                report.append("<li>" + createReposLink(repository.getName()) + "</li>");
            }

            report.append("</ul>");
        }

        report.append("</blockquote>");

        report.append("<h2>" + ResourceUtil.getString("summaryreport.serverrules") + "</h2>");

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

                report.append("<p><strong>" + path.getPath() + "</strong></p><ul>");

                for (final AccessRule rule : rules) {
                    if (rule.getGroup() != null) {
                        report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = "
                                + rule.getLevelFullName() + "</li>");
                    }
                    else if (rule.getUser() != null) {
                        report.append("<li>" + createUserLink(rule.getUser().getName()) + " = "
                                + rule.getLevelFullName() + "</li>");
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
                report.append("<h2>" + createReposAnchor(repository.getName()) + "</h2>");

                final List<Path> paths = repository.getPaths();
                Collections.sort(paths, new PathComparator());

                report.append("<blockquote>");

                for (final Path path : paths) {
                    final List<AccessRule> rules = path.getAccessRules();
                    Collections.sort(rules);

                    report.append("<p><strong>" + path.getPath() + "</strong></p><ul>");

                    for (final AccessRule rule : rules) {
                        if (rule.getGroup() != null) {
                            report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = "
                                    + rule.getLevelFullName() + "</li>");
                        }
                        else if (rule.getUser() != null) {
                            report.append("<li>" + createUserLink(rule.getUser().getName()) + " = "
                                    + rule.getLevelFullName() + "</li>");
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

        report.append("<h2>" + createAnchor("groups", ResourceUtil.getString("summaryreport.groups")) + "</h2>");
        report.append("<blockquote>");

        for (final Group group : groups) {
            report.append("<p><strong>" + createGroupAnchor(group.getName()) + "</strong></p>");

            final List<Group> groupMembers = group.getGroupMembers();
            final List<User> userMembers = group.getUserMembers();
            final List<AccessRule> rules = group.getAccessRules();

            Collections.sort(groupMembers);
            Collections.sort(userMembers);
            Collections.sort(rules);

            report.append("<blockquote><p>Members</p>");

            if (groupMembers.size() == 0 && userMembers.size() == 0) {
                report.append("<blockquote><p>" + ResourceUtil.getString("summaryreport.nomembers")
                        + "</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final Group groupMember : groupMembers) {
                    report.append("<li>" + createGroupLink(groupMember.getName()) + "</li>");
                }

                for (final User userMember : userMembers) {
                    report.append("<li>" + createUserLink(userMember.getName()) + "</li>");
                }

                report.append("</ul>");
            }

            report.append("<p>" + ResourceUtil.getString("summaryreport.rules") + "</p>");

            if (rules.size() == 0) {
                report
                        .append("<blockquote><p>" + ResourceUtil.getString("summaryreport.norules")
                                + "</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final AccessRule rule : rules) {
                    final String repositoryName = (rule.getPath().getRepository() == null) ? "" : rule.getPath()
                            .getRepository().getName()
                            + ":";
                    final String path = rule.getPath().getPath();

                    report.append("<li>[" + repositoryName + path + "] = " + rule.getLevelFullName() + "</li>");
                }

                report.append("</ul>");
            }

            report.append("</blockquote>");
        }

        report.append("</blockquote>");

        final List<User> users = document.getUsers();
        Collections.sort(users);

        report.append("<h2>" + createAnchor("users", ResourceUtil.getString("summaryreport.users")) + "</h2>");
        report.append("<blockquote>");

        for (final User user : users) {
            report.append("<p><strong>" + createUserAnchor(user.getName()) + "</strong></p>");

            final List<Group> userGroups = user.getGroups();
            final List<AccessRule> rules = user.getAccessRules();

            Collections.sort(userGroups);
            Collections.sort(rules);

            report.append("<blockquote><p>" + ResourceUtil.getString("summaryreport.groups") + "</p>");

            if (userGroups.size() == 0) {
                report.append("<blockquote><p>" + ResourceUtil.getString("summaryreport.nogroups")
                        + "</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final Group userGroup : userGroups) {
                    report.append("<li>" + createGroupLink(userGroup.getName()) + "</li>");
                }

                report.append("</ul>");
            }

            report.append("<p>" + ResourceUtil.getString("summaryreport.rules") + "</p>");

            if (rules.size() == 0) {
                report
                        .append("<blockquote><p>" + ResourceUtil.getString("summaryreport.norules")
                                + "</p></blockquote>");
            }
            else {
                report.append("<ul>");

                for (final AccessRule rule : rules) {
                    final String repositoryName = (rule.getPath().getRepository() == null) ? "" : rule.getPath()
                            .getRepository().getName()
                            + ":";
                    final String path = rule.getPath().getPath();

                    report.append("<li>[" + repositoryName + path + "] = " + rule.getLevelFullName() + "</li>");
                }

                report.append("</ul>");
            }

            report.append("</blockquote>");
        }

        report.append("</blockquote>");

        report.append("<h2>" + createAnchor("projects", ResourceUtil.getString("summaryreport.projects")) + "</h2>");

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

                report.append("<h3>" + createReposAnchor(repository.getName()) + "</h3>");

                report.append("<blockquote>");

                if (projects.size() == 0) {
                    report.append("<p>" + ResourceUtil.getString("summaryreport.noprojects") + "</p>");
                }

                for (final String key : projects.keySet()) {
                    final Project projectBean = projects.get(key);
                    List<AccessRule> rules = null;

                    report.append("<p><strong>" + key + "</strong></p><blockquote>");

                    if (projectBean.branches != null) {
                        report.append("<p>" + ResourceUtil.getString("summaryreport.branches") + "</p>");
                        rules = projectBean.branches.getAccessRules();
                        Collections.sort(rules);

                        if (rules.size() == 0) {
                            report.append("<blockquote><p>" + ResourceUtil.getString("summaryreport.norules")
                                    + "</p></blockquote>>");
                        }
                        else {
                            report.append("<ul>");

                            for (final AccessRule rule : rules) {
                                if (rule.getGroup() != null) {
                                    report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = "
                                            + rule.getLevelFullName() + "</li>");
                                }
                                else if (rule.getUser() != null) {
                                    report.append("<li>" + createUserLink(rule.getUser().getName()) + " = "
                                            + rule.getLevelFullName() + "</li>");
                                }
                                else {
                                    throw new AppException("summaryreport.invalidrule");
                                }
                            }

                            report.append("</ul>");
                        }
                    }

                    if (projectBean.tags != null) {
                        report.append("<p>" + ResourceUtil.getString("summaryreport.tags") + "</p>");
                        rules = projectBean.tags.getAccessRules();
                        Collections.sort(rules);

                        if (rules.size() == 0) {
                            report.append("<blockquote><p>" + ResourceUtil.getString("summaryreport.norules")
                                    + "</p></blockquote>>");
                        }
                        else {
                            report.append("<ul>");

                            for (final AccessRule rule : rules) {
                                if (rule.getGroup() != null) {
                                    report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = "
                                            + rule.getLevelFullName() + "</li>");
                                }
                                else if (rule.getUser() != null) {
                                    report.append("<li>" + createUserLink(rule.getUser().getName()) + " = "
                                            + rule.getLevelFullName() + "</li>");
                                }
                                else {
                                    throw new AppException("summaryreport.invalidrule");
                                }
                            }

                            report.append("</ul>");
                        }
                    }

                    if (projectBean.trunk != null) {
                        report.append("<p>" + ResourceUtil.getString("summaryreport.trunk") + "</p>");
                        rules = projectBean.trunk.getAccessRules();
                        Collections.sort(rules);

                        if (rules.size() == 0) {
                            report.append("<blockquote><p>" + ResourceUtil.getString("summaryreport.norules")
                                    + "</p></blockquote>>");
                        }
                        else {
                            report.append("<ul>");

                            for (final AccessRule rule : rules) {
                                if (rule.getGroup() != null) {
                                    report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = "
                                            + rule.getLevelFullName() + "</li>");
                                }
                                else if (rule.getUser() != null) {
                                    report.append("<li>" + createUserLink(rule.getUser().getName()) + " = "
                                            + rule.getLevelFullName() + "</li>");
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

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        final Object[] args = new Object[3];
        args[0] = df.format(new Date());
        args[1] = ResourceUtil.getString("application.url");
        args[2] = ResourceUtil.getString("application.nameversion");

        report.append(ResourceUtil.getFormattedString("reports.footer", args));

        report.append("</body></html>");

        return report.toString();
    }
}
