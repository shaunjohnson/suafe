package org.xiaoniu.suafe.reports;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.Project;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.PathComparator;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class SummaryReport implements GenericReport {

	/**
	 * Generates HTML anchor tag.
	 * 
	 * @param anchor Anchor name
	 * @param text Anchor text
	 * @return HTML anchor tag text
	 */
	private static String createAnchor(String anchor, String text) {
		return new String("<a name=\""+ anchor + "\">" + text + "</a>");
	}
	
	/**
	 * Creates HTML link tag.
	 * 
	 * @param href Link href
	 * @param text Link text
	 * @return HTML link tag text
	 */
	private static String createLink(String href, String text) {
		return new String("<a href=\"#"+ href + "\">" + text + "</a>");
	}
	
	/**
	 * Generates HTML anchor tag for Group.
	 * 
	 * @param anchor Anchor name
	 * @param text Anchor text
	 * @return HTML anchor tag text
	 */
	private static String createGroupAnchor(String groupName) {
		return createAnchor("group_" + groupName, Constants.GROUP_PREFIX + groupName);
	}
	
	/**
	 * Creates HTML link tag for Group.
	 * 
	 * @param href Link href
	 * @param text Link text
	 * @return HTML link tag text
	 */
	private static String createGroupLink(String groupName) {
		return createLink("group_" + groupName, Constants.GROUP_PREFIX + groupName);
	}
	
	/**
	 * Generates HTML anchor tag for Repository.
	 * 
	 * @param anchor Anchor name
	 * @param text Anchor text
	 * @return HTML anchor tag text
	 */
	private static String createReposAnchor(String repositoryName) {
		return createAnchor("repos_" + repositoryName, repositoryName);
	}
	
	/**
	 * Creates HTML link tag for Repository.
	 * 
	 * @param href Link href
	 * @param text Link text
	 * @return HTML link tag text
	 */
	private static String createReposLink(String repositoryName) {
		return createLink("repos_" + repositoryName, repositoryName);
	}
	
	/**
	 * Generates HTML anchor tag for User.
	 * 
	 * @param anchor Anchor name
	 * @param text Anchor text
	 * @return HTML anchor tag text
	 */
	private static String createUserAnchor(String userName) {
		if (userName.equals(Constants.ALL_USERS)) {
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
	private static String createUserLink(String userName) {
		if (userName.equals(Constants.ALL_USERS)) {
			return createLink("all_users", userName);
		}
		else {
			return createLink("user_" + userName, userName);
		}
		
	}
	
	/**
	 * Generates HTML summary report.
	 * 
	 * @param out
	 * @throws ApplicationException
	 */
	public String generate() throws ApplicationException {
		StringBuffer report = new StringBuffer();
				
		report.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
		report.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		report.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">");
		report.append("<head>");
		report.append("<title>Summary Report</title>");
//		report.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />");
		report.append("</head><body>");
		report.append("<h1>Summary Report</h1>");
		
		report.append("<p>[" + createLink("repositories", "Repositories") + "] " +
				"[" + createLink("groups", "Groups") + "] " +
				"[" + createLink("users", "Users") + "] " + 
				"[" + createLink("projects", "Projects") + "] </p>");
		
		List<Repository> repositories = Document.getRepositories();
		Collections.sort(repositories);
		
		report.append("<h2>" + createAnchor("repositories", "Repositories") + "</h2>");
		report.append("<blockquote>");
		
		if (repositories.size() == 0) {
			report.append("<p>No repositories.</p>");
		}
		else {
			report.append("<ul>");
			
			for (Repository repository : repositories) {
				report.append("<li>" + createReposLink(repository.getName()) + "</li>");
			}
			
			report.append("</ul>");
		}
		
		report.append("</blockquote>");
		
		report.append("<h2>Server Level Access Rules</h2>");
		
		List<Path> serverPaths = Document.getPaths();
		Collections.sort(serverPaths, new PathComparator());
		
		if (serverPaths.size() > 0) {
			report.append("<blockquote>");
			
			for (Path path : serverPaths) {
				if (path.getRepository() != null) {
					continue;
				}
				
				List<AccessRule> rules = path.getAccessRules();
				Collections.sort(rules);
				
				report.append("<p><strong>" + path.getPath() + "</strong></p><ul>");
				
				for (AccessRule rule : rules) {
					if (rule.getGroup() != null) {
						report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = " + rule.getLevelFullName() + "</li>");
					}
					else if (rule.getUser() != null) {
						report.append("<li>" + createUserLink(rule.getUser().getName()) + " = " + rule.getLevelFullName() + "</li>");
					}
					else {
						throw new ApplicationException("Invalid access rule");
					}
				}
				
				report.append("</ul>");
			}
			
			report.append("</blockquote>");
		}
		
		if (repositories.size() > 0) {
			for (Repository repository : repositories) {
				report.append("<h2>" + createReposAnchor(repository.getName()) + "</h2>");
				
				List<Path> paths = repository.getPaths();
				Collections.sort(paths, new PathComparator());
				
				report.append("<blockquote>");
				
				for (Path path : paths) {				
					List<AccessRule> rules = path.getAccessRules();
					Collections.sort(rules);
					
					report.append("<p><strong>" + path.getPath() + "</strong></p><ul>");
					
					for (AccessRule rule : rules) {						
						if (rule.getGroup() != null) {
							report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = " + rule.getLevelFullName() + "</li>");
						}
						else if (rule.getUser() != null) {
							report.append("<li>" + createUserLink(rule.getUser().getName()) + " = " + rule.getLevelFullName() + "</li>");
						}
						else {
							throw new ApplicationException("Invalid access rule");
						}
					}
					
					report.append("</ul>");
				}
				
				report.append("</blockquote>");
			}
		}
		
		List<Group> groups = Document.getGroups();
		Collections.sort(groups);
		
		report.append("<h2>" + createAnchor("groups", "Groups") + "</h2>");
		report.append("<blockquote>");
		
		for (Group group : groups) {
			report.append("<p><strong>" + createGroupAnchor(group.getName()) + "</strong></p>");
			
			List<Group> groupMembers = group.getGroupMembers();
			List<User> userMembers = group.getUserMembers();
			List<AccessRule> rules = group.getAccessRules();
			
			Collections.sort(groupMembers);
			Collections.sort(userMembers);
			Collections.sort(rules);
			
			report.append("<blockquote><p>Members</p>");
			
			if (groupMembers.size() == 0 && userMembers.size() == 0) {
				report.append("<blockquote><p>No members</p></blockquote>");
			}
			else {
				report.append("<ul>");
				
				for (Group groupMember : groupMembers) {
					report.append("<li>" + createGroupLink(groupMember.getName()) + "</li>");
				}
				
				for (User userMember : userMembers) {
					report.append("<li>" + createUserLink(userMember.getName()) + "</li>");
				}
				
				report.append("</ul>");
			}
						
			report.append("<p>Access Rules</p>");
			
			if (rules.size() == 0) {
				report.append("<blockquote><p>No access rules</p></blockquote>");
			}
			else {
				report.append("<ul>");
				
				for (AccessRule rule : rules) {
					String repositoryName = (rule.getPath().getRepository() ==  null) ? "" : rule.getPath().getRepository().getName() + ":";
					String path = rule.getPath().getPath();
					
					
					report.append("<li>[" + repositoryName + path + "] = " + rule.getLevelFullName() + "</li>");
				}
				
				report.append("</ul>");
			}
			
			report.append("</blockquote>");
		}
		
		report.append("</blockquote>");
		
		List<User> users = Document.getUsers();
		Collections.sort(users);
		
		report.append("<h2>" + createAnchor("users", "Users") + "</h2>");
		report.append("<blockquote>");
		
		for (User user : users) {
			report.append("<p><strong>" + createUserAnchor(user.getName()) + "</strong></p>");
			
			List<Group> userGroups = user.getGroups();
			List<AccessRule> rules = user.getAccessRules();
			
			Collections.sort(userGroups);
			Collections.sort(rules);
			
			report.append("<blockquote><p>Groups</p>");
			
			if (userGroups.size() == 0) {
				report.append("<blockquote><p>No groups</p></blockquote>");
			}
			else {
				report.append("<ul>");
				
				for (Group userGroup : userGroups) {
					report.append("<li>" + createGroupLink(userGroup.getName()) + "</li>");
				}
				
				report.append("</ul>");
			}
			
			report.append("<p>Access Rules</p>");
			
			if (rules.size() == 0) {
				report.append("<blockquote><p>No access rules</p></blockquote>");
			}
			else {
				report.append("<ul>");
				
				for (AccessRule rule : rules) {
					String repositoryName = (rule.getPath().getRepository() ==  null) ? "" : rule.getPath().getRepository().getName() + ":";
					String path = rule.getPath().getPath();
					
					
					report.append("<li>[" + repositoryName + path + "] = " + rule.getLevelFullName() + "</li>");
				}
				
				report.append("</ul>");
			}
			
			report.append("</blockquote>");
		}
		
		report.append("</blockquote>");
		
		report.append("<h2>" + createAnchor("projects", "Projects") + "</h2>");
		
		if (repositories.size() > 0) {
			for (Repository repository : repositories) {
				List<Path> paths = repository.getPaths();
				Collections.sort(paths, new PathComparator());
				
				HashMap<String, Project> projects = new HashMap<String, Project>();
				
				for (Path path : paths) {				
					List<AccessRule> rules = path.getAccessRules();
					Collections.sort(rules);

					String pathString = path.getPath();
					
					if (pathString.endsWith("/trunk")) {
						String projectName = pathString.substring(0, pathString.lastIndexOf("/trunk"));
						
						Project projectBean = projects.get(projectName);
						
						if (projectBean == null) {
							Project newProjectBean = new Project();
							
							newProjectBean.trunk = path;
							
							projects.put(projectName, newProjectBean);
						}
						else {
							projectBean.trunk = path;
						}
					}	
					else if (pathString.endsWith("/branches")) {
						String projectName = pathString.substring(0, pathString.lastIndexOf("/branches"));
						
						Project projectBean = projects.get(projectName);
						
						if (projectBean == null) {
							Project newProjectBean = new Project();
							
							newProjectBean.branches = path;
							
							projects.put(projectName, newProjectBean);
						}
						else {
							projectBean.branches = path;
						}
					}
					else if (pathString.endsWith("/tags")) {
						String projectName = pathString.substring(0, pathString.lastIndexOf("/tags"));
						
						Project projectBean = projects.get(projectName);
						
						if (projectBean == null) {
							Project newProjectBean = new Project();
							
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
					report.append("<p>No projects</p>");
				}
				
				for (String key : projects.keySet()) {
					Project projectBean = projects.get(key);
					
					report.append("<p><strong>" + key + "</strong></p><blockquote>");
					
					report.append("<p>Branches</p>");
					
					List<AccessRule> rules = projectBean.branches.getAccessRules();
					Collections.sort(rules);
					
					if (rules.size() == 0) {
						report.append("<blockquote><p>No access rules</p></blockquote>>");
					}
					else {
						report.append("<ul>");
						
						for (AccessRule rule : rules) {
							if (rule.getGroup() != null) {
								report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = " + rule.getLevelFullName() + "</li>");
							}
							else if (rule.getUser() != null) {
								report.append("<li>" + createUserLink(rule.getUser().getName()) + " = " + rule.getLevelFullName() + "</li>");
							}
							else {
								throw new ApplicationException("Invalid access rule");
							}
						}
						
						report.append("</ul>");
					}
					
					report.append("<p>Tags</p>");
					
					rules = projectBean.tags.getAccessRules();
					Collections.sort(rules);
					
					if (rules.size() == 0) {
						report.append("<blockquote><p>No access rules</p></blockquote>>");
					}
					else {
						report.append("<ul>");
						
						for (AccessRule rule : rules) {
							if (rule.getGroup() != null) {
								report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = " + rule.getLevelFullName() + "</li>");
							}
							else if (rule.getUser() != null) {
								report.append("<li>" + createUserLink(rule.getUser().getName()) + " = " + rule.getLevelFullName() + "</li>");
							}
							else {
								throw new ApplicationException("Invalid access rule");
							}
						}
						
						report.append("</ul>");
					}
					
					report.append("<p>Trunk</p>");
					
					rules = projectBean.trunk.getAccessRules();
					Collections.sort(rules);
					
					if (rules.size() == 0) {
						report.append("<blockquote><p>No access rules</p></blockquote>>");
					}
					else {
						report.append("<ul>");
						
						for (AccessRule rule : rules) {
							if (rule.getGroup() != null) {
								report.append("<li>" + createGroupLink(rule.getGroup().getName()) + " = " + rule.getLevelFullName() + "</li>");
							}
							else if (rule.getUser() != null) {
								report.append("<li>" + createUserLink(rule.getUser().getName()) + " = " + rule.getLevelFullName() + "</li>");
							}
							else {
								throw new ApplicationException("Invalid access rule");
							}
						}
						
						report.append("</ul>");
					}
					
					report.append("</blockquote>");
				}
				
				report.append("</blockquote>");
			}
		}
		
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		report.append("<hr></hr>");
		report.append("<p>Generated " + df.format(new Date()) + " using <a href=\"" +
				ResourceUtil.getString("application.url") + "\">" +
				ResourceUtil.getString("application.nameversion") + "</a><br></br>");
		report.append("Valid XHTML 1.0 Strict</p>");
		
		report.append("</body></html>");
		
		return report.toString();
	}
}
