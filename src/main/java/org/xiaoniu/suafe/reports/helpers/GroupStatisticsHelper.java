package org.xiaoniu.suafe.reports.helpers;

import java.util.List;

import org.xiaoniu.suafe.beans.Group;

public class GroupStatisticsHelper {

	private double avgAccessRules = -1;

	private double avgGroupMembers = -1;

	private double avgGroups = -1;

	private double avgMembers = -1;

	private double avgUserMembers = -1;

	private int count = -1;

	private List<Group> groups = null;

	private int maxAccessRules = -1;

	private int maxGroupMembers = -1;

	private int maxGroups = -1;

	private int maxMembers = -1;

	private int maxUserMembers = -1;

	private int minAccessRules = -1;

	private int minGroupMembers = -1;

	private int minGroups = -1;

	private int minMembers = -1;

	private int minUserMembers = -1;

	public GroupStatisticsHelper(List<Group> groups) {
		super();

		this.groups = groups;
	}

	public double getAvgAccessRules() {
		if (avgAccessRules == -1) {
			avgAccessRules = 0;

			for (Group group : groups) {
				int size = group.getAccessRules().size();

				avgAccessRules += size;
			}

			avgAccessRules /= getCount();
		}

		return avgAccessRules;
	}

	public double getAvgGroupMembers() {
		if (avgGroupMembers == -1) {
			avgGroupMembers = 0;

			for (Group group : groups) {
				int size = group.getGroupMembers().size();

				avgGroupMembers += size;
			}

			avgGroupMembers /= getCount();
		}

		return avgGroupMembers;
	}

	public double getAvgGroups() {
		if (avgGroups == -1) {
			avgGroups = 0;

			for (Group group : groups) {
				int size = group.getGroups().size();

				avgGroups += size;
			}

			avgGroups /= getCount();
		}

		return avgGroups;
	}

	public double getAvgMembers() {
		if (avgMembers == -1) {
			avgMembers = 0;

			for (Group group : groups) {
				int size = group.getGroupMembers().size() + group.getUserMembers().size();

				avgMembers += size;
			}

			avgMembers /= getCount();
		}

		return avgMembers;
	}

	public double getAvgUserMembers() {
		if (avgUserMembers == -1) {
			avgUserMembers = 0;

			for (Group group : groups) {
				int size = group.getUserMembers().size();

				avgUserMembers += size;
			}

			avgUserMembers /= getCount();
		}

		return avgUserMembers;
	}

	public int getCount() {
		if (count == -1) {
			count = groups.size();
		}

		return count;
	}

	public int getMaxAccessRules() {
		if (maxAccessRules == -1) {
			maxAccessRules = 0;

			for (Group group : groups) {
				int size = group.getAccessRules().size();

				maxAccessRules = (size > maxAccessRules) ? size : maxAccessRules;
			}
		}

		return maxAccessRules;
	}

	public int getMaxGroupMembers() {
		if (maxGroupMembers == -1) {
			maxGroupMembers = 0;

			for (Group group : groups) {
				int size = group.getGroupMembers().size();

				maxGroupMembers = (size > maxGroupMembers) ? size : maxGroupMembers;
			}
		}

		return maxGroupMembers;
	}

	public int getMaxGroups() {
		if (maxGroups == -1) {
			maxGroups = 0;

			for (Group group : groups) {
				int size = group.getGroups().size();

				maxGroups = (size > maxGroups) ? size : maxGroups;
			}
		}

		return maxGroups;
	}

	public int getMaxMembers() {
		if (maxMembers == -1) {
			maxMembers = 0;

			for (Group group : groups) {
				int size = group.getGroupMembers().size() + group.getUserMembers().size();

				maxMembers = (size > maxMembers) ? size : maxMembers;
			}
		}

		return maxMembers;
	}

	public int getMaxUserMembers() {
		if (maxUserMembers == -1) {
			maxUserMembers = 0;

			for (Group group : groups) {
				int size = group.getUserMembers().size();

				maxUserMembers = (size > maxUserMembers) ? size : maxUserMembers;
			}
		}

		return maxUserMembers;
	}

	public int getMinAccessRules() {
		if (minAccessRules == -1) {
			minAccessRules = Integer.MAX_VALUE;

			for (Group group : groups) {
				int size = group.getAccessRules().size();

				minAccessRules = (size < minAccessRules) ? size : minAccessRules;
			}
		}

		return minAccessRules;
	}

	public int getMinGroupMembers() {
		if (minGroupMembers == -1) {
			minGroupMembers = Integer.MAX_VALUE;

			for (Group group : groups) {
				int size = group.getGroupMembers().size();

				minGroupMembers = (size < minGroupMembers) ? size : minGroupMembers;
			}
		}

		return minGroupMembers;
	}

	public int getMinGroups() {
		if (minGroups == -1) {
			minGroups = Integer.MAX_VALUE;

			for (Group group : groups) {
				int size = group.getGroups().size();

				minGroups = (size < minGroups) ? size : minGroups;
			}
		}

		return minGroups;
	}

	public int getMinMembers() {
		if (minMembers == -1) {
			minMembers = Integer.MAX_VALUE;

			for (Group group : groups) {
				int size = group.getGroupMembers().size() + group.getUserMembers().size();

				minMembers = (size < minMembers) ? size : minMembers;
			}
		}

		return minMembers;
	}

	public int getMinUserMembers() {
		if (minUserMembers == -1) {
			minUserMembers = Integer.MAX_VALUE;

			for (Group group : groups) {
				int size = group.getUserMembers().size();

				minUserMembers = (size < minUserMembers) ? size : minUserMembers;
			}
		}

		return minUserMembers;
	}
}
