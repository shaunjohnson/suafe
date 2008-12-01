package org.xiaoniu.suafe.reports.helpers;

import java.util.List;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.User;

public class AccessRuleStatisticsHelper {

	private List<AccessRule> accessRules = null;

	private double avgDenyAccess = -1;

	private double avgForAllUsers = -1;

	private double avgForGroup = -1;

	private double avgForUser = -1;

	private double avgReadOnly = -1;

	private double avgReadWrite = -1;

	private int count = -1;

	private int maxDenyAccess = -1;

	private int maxForAllUsers = -1;

	private int maxForGroup = -1;

	private int maxForUser = -1;

	private int maxReadOnly = -1;

	private int maxReadWrite = -1;

	private int minDenyAccess = -1;

	private int minForAllUsers = -1;

	private int minForGroup = -1;

	private int minForUser = -1;

	private int minReadOnly = -1;

	private int minReadWrite = -1;

	public AccessRuleStatisticsHelper(List<AccessRule> accessRules) {
		super();

		this.accessRules = accessRules;
	}

	public double getAvgDenyAccess() {
		if (avgDenyAccess == -1) {
			avgDenyAccess = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_DENY_ACCESS)) ? 1 : 0;

				avgDenyAccess += size;
			}

			avgDenyAccess /= getCount();
		}

		return avgDenyAccess;
	}

	public double getAvgForAllUsers() {
		if (avgForAllUsers == -1) {
			avgForAllUsers = 0;

			for (AccessRule accessRule : accessRules) {
				User user = accessRule.getUser();

				int size = (user == null) ? 0 : (user.equals("*") ? 1 : 0);

				avgForAllUsers += size;
			}

			avgForAllUsers /= getCount();
		}

		return avgForAllUsers;
	}

	public double getAvgForGroup() {
		if (avgForGroup == -1) {
			avgForGroup = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getGroup() == null) ? 0 : 1;

				avgForGroup += size;
			}

			avgForGroup /= getCount();
		}

		return avgForGroup;
	}

	public double getAvgForUser() {
		if (avgForUser == -1) {
			avgForUser = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getUser() == null) ? 0 : 1;

				avgForUser += size;
			}

			avgForUser /= getCount();
		}

		return avgForUser;
	}
	
	

	public double getAvgReadOnly() {
		if (avgReadOnly == -1) {
			avgReadOnly = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READONLY)) ? 1 : 0;

				avgReadOnly += size;
			}

			avgReadOnly /= getCount();
		}

		return avgReadOnly;
	}
	
	public double getAvgReadWrite() {
		if (avgReadWrite == -1) {
			avgReadWrite = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READWRITE)) ? 1 : 0;

				avgReadWrite += size;
			}

			avgReadWrite /= getCount();
		}

		return avgReadWrite;
	}
	
	public int getCount() {
		if (count == -1) {
			count = accessRules.size();
		}

		return count;
	}
	
	public int getMaxDenyAccess() {
		if (maxDenyAccess == -1) {
			maxDenyAccess = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_DENY_ACCESS)) ? 1 : 0;

				maxDenyAccess = (size > maxDenyAccess) ? size : maxDenyAccess;
			}

			maxDenyAccess /= getCount();
		}

		return maxDenyAccess;
	}
	
	public int getMaxForAllUsers() {
		if (maxForAllUsers == -1) {
			maxForAllUsers = 0;

			for (AccessRule accessRule : accessRules) {
				User user = accessRule.getUser();

				int size = (user == null) ? 0 : (user.equals("*") ? 1 : 0);

				maxForAllUsers = (size > maxForAllUsers) ? size : maxForAllUsers;
			}

			maxForAllUsers /= getCount();
		}

		return maxForAllUsers;
	}
	
	public int getMaxForGroup() {
		if (maxForGroup == -1) {
			maxForGroup = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getGroup() == null) ? 0 : 1;

				maxForGroup = (size > maxForGroup) ? size : maxForGroup;
			}

			maxForGroup /= getCount();
		}

		return maxForGroup;
	}
	
	public int getMaxForUser() {
		if (maxForUser == -1) {
			maxForUser = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getUser() == null) ? 0 : 1;

				maxForUser = (size > maxForUser) ? size : maxForUser;
			}

			maxForUser /= getCount();
		}

		return maxForUser;
	}

	public int getMaxReadOnly() {
		if (maxReadOnly == -1) {
			maxReadOnly = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READONLY)) ? 1 : 0;

				maxReadOnly = (size > maxReadOnly) ? size : maxReadOnly;
			}

			maxReadOnly /= getCount();
		}

		return maxReadOnly;
	}

	public int getMaxReadWrite() {
		if (maxReadWrite == -1) {
			maxReadWrite = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READWRITE)) ? 1 : 0;

				maxReadWrite = (size > maxReadWrite) ? size : maxReadWrite;
			}

			maxReadWrite /= getCount();
		}

		return maxReadWrite;
	}


	
	
	public int getMinDenyAccess() {
		if (minDenyAccess == -1) {
			minDenyAccess = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_DENY_ACCESS)) ? 1 : 0;

				minDenyAccess = (size < minDenyAccess) ? size : minDenyAccess;
			}

			minDenyAccess /= getCount();
		}

		return minDenyAccess;
	}

	public int getMinForAllUsers() {
		if (minForAllUsers == -1) {
			minForAllUsers = 0;

			for (AccessRule accessRule : accessRules) {
				User user = accessRule.getUser();

				int size = (user == null) ? 0 : (user.equals("*") ? 1 : 0);

				minForAllUsers = (size < minForAllUsers) ? size : minForAllUsers;
			}

			minForAllUsers /= getCount();
		}

		return minForAllUsers;
	}

	public int getMinForGroup() {
		if (minForGroup == -1) {
			minForGroup = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getGroup() == null) ? 0 : 1;

				minForGroup = (size < minForGroup) ? size : minForGroup;
			}

			minForGroup /= getCount();
		}

		return minForGroup;
	}

	public int getMinForUser() {
		if (minForUser == -1) {
			minForUser = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getUser() == null) ? 0 : 1;

				minForUser = (size < minForUser) ? size : minForUser;
			}

			minForUser /= getCount();
		}

		return minForUser;
	}

	public int getMinReadOnly() {
		if (minReadOnly == -1) {
			minReadOnly = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READONLY)) ? 1 : 0;

				minReadOnly = (size < minReadOnly) ? size : minReadOnly;
			}

			minReadOnly /= getCount();
		}

		return minReadOnly;
	}

	public int getMinReadWrite() {
		if (minReadWrite == -1) {
			minReadWrite = 0;

			for (AccessRule accessRule : accessRules) {
				int size = (accessRule.getLevel().equals(Constants.ACCESS_LEVEL_READWRITE)) ? 1 : 0;

				minReadWrite = (size < minReadWrite) ? size : minReadWrite;
			}

			minReadWrite /= getCount();
		}

		return minReadWrite;
	}
}
