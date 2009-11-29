package org.xiaoniu.suafe.reports.helpers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.xiaoniu.suafe.SubversionConstants;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;
import org.xiaoniu.suafe.beans.User;

public class AccessRuleStatisticsHelperTest {

	private AccessRuleStatisticsHelper emptyHelper;

	private AccessRuleStatisticsHelper helper;

	private final List<AccessRule> rules = new ArrayList<AccessRule>();

	@Before
	public void setUp() {
		final Repository repository = new Repository("repo");
		final Path path = new Path(repository, "/");
		final Group group = new Group("group");
		final User user = new User("user");
		final User allUsers = new User("*");

		rules.add(new AccessRule(path, group, SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS));
		rules.add(new AccessRule(path, group, SubversionConstants.SVN_ACCESS_LEVEL_READONLY));
		rules.add(new AccessRule(path, group, SubversionConstants.SVN_ACCESS_LEVEL_READWRITE));

		rules.add(new AccessRule(path, user, SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS));
		rules.add(new AccessRule(path, user, SubversionConstants.SVN_ACCESS_LEVEL_READONLY));
		rules.add(new AccessRule(path, user, SubversionConstants.SVN_ACCESS_LEVEL_READWRITE));

		rules.add(new AccessRule(path, allUsers, SubversionConstants.SVN_ACCESS_LEVEL_DENY_ACCESS));
		rules.add(new AccessRule(path, allUsers, SubversionConstants.SVN_ACCESS_LEVEL_READONLY));
		rules.add(new AccessRule(path, allUsers, SubversionConstants.SVN_ACCESS_LEVEL_READWRITE));

		helper = new AccessRuleStatisticsHelper(rules);
		emptyHelper = new AccessRuleStatisticsHelper(new ArrayList<AccessRule>());
	}

	@Test
	public void testAccessRuleStatisticsHelpList() {
		try {
			new AccessRuleStatisticsHelper(null);

			fail();
		}
		catch (final NullPointerException e) {
			// Expected error
		}
		catch (final Throwable t) {
			fail();
		}

		try {
			new AccessRuleStatisticsHelper(new ArrayList<AccessRule>());
		}
		catch (final Throwable t) {
			fail();
		}
	}

	@Test
	public void testGetAvgDenyAccess() {
		assertTrue(emptyHelper.getAvgDenyAccess() == 0d);
		assertTrue(helper.getAvgDenyAccess() == 1d / 3d);
	}

	@Test
	public void testGetAvgForAllUsers() {
		assertTrue(emptyHelper.getAvgForAllUsers() == 0d);
		assertTrue(helper.getAvgForAllUsers() == 1d / 3d);
	}

	@Test
	public void testGetAvgForGroup() {
		assertTrue(emptyHelper.getAvgForGroup() == 0d);
		assertTrue(helper.getAvgForGroup() == 1d / 3d);
	}

	@Test
	public void testGetAvgForUser() {
		assertTrue(emptyHelper.getAvgForUser() == 0d);
		assertTrue(helper.getAvgForUser() == 1d / 3d);
	}

	@Test
	public void testGetAvgReadOnly() {
		assertTrue(emptyHelper.getAvgReadOnly() == 0d);
		assertTrue(helper.getAvgReadOnly() == 1d / 3d);
	}

	@Test
	public void testGetAvgReadWrite() {
		assertTrue(emptyHelper.getAvgReadWrite() == 0d);
		assertTrue(helper.getAvgReadWrite() == 1d / 3d);
	}

	@Test
	public void testGetCount() {
		assertTrue(emptyHelper.getCount() == 0);
		assertTrue(helper.getCount() == rules.size());
	}

	@Test
	public void testGetMaxDenyAccess() {

	}

	@Test
	public void testGetMaxForAllUsers() {

	}

	@Test
	public void testGetMaxForGroup() {

	}

	@Test
	public void testGetMaxForUser() {

	}

	@Test
	public void testGetMaxReadOnly() {

	}

	@Test
	public void testGetMaxReadWrite() {

	}

	@Test
	public void testGetMinDenyAccess() {

	}

	@Test
	public void testGetMinForAllUsers() {

	}

	@Test
	public void testGetMinForGroup() {

	}

	@Test
	public void testGetMinForUser() {

	}

	@Test
	public void testGetMinReadOnly() {

	}

	@Test
	public void testGetMinReadWrite() {

	}

}
