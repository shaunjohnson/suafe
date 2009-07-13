package org.xiaoniu.suafe.reports.helpers;

import java.io.File;

import org.xiaoniu.suafe.FileParser;
import org.xiaoniu.suafe.beans.Document;

public final class StatisticsReportHelper {

	public static void main(String[] args) throws Exception {
		Document doc = new FileParser().parse(new File("C:/My Documents/authz.svn"));

		StatisticsReportHelper helper = new StatisticsReportHelper(doc);
		AccessRuleStatisticsHelper accessRuleStatistics = helper.getAccessRuleStatistics();
		GroupStatisticsHelper groupStatistics = helper.getGroupStatistics();
		RepositoryStatisticsHelper repoStatistics = helper.getRepoStatistics();
		UserStatisticsHelper userStatistics = helper.getUserStatistics();

		System.out.println("Access Rules");
		System.out.println("Access rule count: " + accessRuleStatistics.getCount());
		
		System.out.println("Avg deny access: " + accessRuleStatistics.getAvgDenyAccess());
		System.out.println("Min deny access: " + accessRuleStatistics.getMinDenyAccess());
		System.out.println("Max deny access: " + accessRuleStatistics.getMaxDenyAccess());
		
		System.out.println("Avg read only: " + accessRuleStatistics.getAvgReadOnly());
		System.out.println("Min read only: " + accessRuleStatistics.getMinReadOnly());
		System.out.println("Max read only: " + accessRuleStatistics.getMaxReadOnly());
		
		System.out.println("Avg read write: " + accessRuleStatistics.getAvgReadWrite());
		System.out.println("Min read write: " + accessRuleStatistics.getMinReadWrite());
		System.out.println("Max read write: " + accessRuleStatistics.getMaxReadWrite());
		
		System.out.println("Avg for group: " + accessRuleStatistics.getAvgForGroup());
		System.out.println("Min for group: " + accessRuleStatistics.getMinForGroup());
		System.out.println("Max for group: " + accessRuleStatistics.getMaxForGroup());
		
		System.out.println("Avg for user: " + accessRuleStatistics.getAvgForUser());
		System.out.println("Min for user: " + accessRuleStatistics.getMinForUser());
		System.out.println("Max for user: " + accessRuleStatistics.getMaxForUser());
		
		System.out.println("Avg for all users: " + accessRuleStatistics.getAvgForAllUsers());
		System.out.println("Min for all users: " + accessRuleStatistics.getMinForAllUsers());
		System.out.println("Max for all users: " + accessRuleStatistics.getMaxForAllUsers());
		
		
		
		System.out.println("\n\nGroups");
		System.out.println("Group count: " + groupStatistics.getCount());
		
		System.out.println("Avg access rules: " + groupStatistics.getAvgAccessRules());
		System.out.println("Min access rules: " + groupStatistics.getMinAccessRules());
		System.out.println("Max access rules: " + groupStatistics.getMaxAccessRules());

		System.out.println("Avg groups: " + groupStatistics.getAvgGroups());
		System.out.println("Min groups: " + groupStatistics.getMinGroups());
		System.out.println("Max groups: " + groupStatistics.getMaxGroups());
		
		System.out.println("Avg members: " + groupStatistics.getAvgMembers());
		System.out.println("Min members: " + groupStatistics.getMinMembers());
		System.out.println("Max members: " + groupStatistics.getMaxMembers());
		
		System.out.println("Avg group members: " + groupStatistics.getAvgGroupMembers());
		System.out.println("Min group members: " + groupStatistics.getMinGroupMembers());
		System.out.println("Max group members: " + groupStatistics.getMaxGroupMembers());
		
		System.out.println("Avg user members: " + groupStatistics.getAvgUserMembers());
		System.out.println("Min user members: " + groupStatistics.getMinUserMembers());
		System.out.println("Max user members: " + groupStatistics.getMaxUserMembers());
		
		
		System.out.println("\n\nRepositories");
		System.out.println("Repository count: " + repoStatistics.getCount());
		
		System.out.println("Avg paths: " + repoStatistics.getAvgPaths());
		System.out.println("Min paths: " + repoStatistics.getMinPaths());
		System.out.println("Max paths: " + repoStatistics.getMaxPaths());
		
		
		System.out.println("\n\nUsers");
		System.out.println("User count: " + userStatistics.getCount());
		
		System.out.println("Avg access rules: " + userStatistics.getAvgAccessRules());
		System.out.println("Min access rules: " + userStatistics.getMinAccessRules());
		System.out.println("Max access rules: " + userStatistics.getMaxAccessRules());

		System.out.println("Avg groups: " + userStatistics.getAvgGroups());
		System.out.println("Min groups: " + userStatistics.getMinGroups());
		System.out.println("Max groups: " + userStatistics.getMaxGroups());
	}

	private AccessRuleStatisticsHelper accessRuleStatistics = null;

	private Document document = null;

	private GroupStatisticsHelper groupStatistics = null;
	
	private RepositoryStatisticsHelper repoStatistics = null;
	
	private UserStatisticsHelper userStatistics = null;

	public StatisticsReportHelper(Document document) {
		super();

		this.document = document;
		this.accessRuleStatistics = new AccessRuleStatisticsHelper(document.getAccessRules());
		this.groupStatistics = new GroupStatisticsHelper(document.getGroups());
		this.repoStatistics = new RepositoryStatisticsHelper(document.getRepositories());
		this.userStatistics = new UserStatisticsHelper(document.getUsers());
	}
	
	public int getAccessRuleCount() {
		return document.getAccessRules().size();
	}

	public AccessRuleStatisticsHelper getAccessRuleStatistics() {
		return accessRuleStatistics;
	}

	public int getGroupCount() {
		return document.getGroups().size();
	}

	public GroupStatisticsHelper getGroupStatistics() {
		return groupStatistics;
	}

	public int getPathCount() {
		return document.getPaths().size();
	}

	public RepositoryStatisticsHelper getRepoStatistics() {
		return repoStatistics;
	}
	
	public int getUserCount() {
		return document.getUsers().size();
	}

	public UserStatisticsHelper getUserStatistics() {
		return userStatistics;
	}
}
