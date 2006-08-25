package org.xiaoniu.suafe.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Shaun Johnson
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.xiaoniu.suafe.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(AccessRuleTest.class);
		suite.addTestSuite(RepositoryTest.class);
		suite.addTestSuite(UserTest.class);
		suite.addTestSuite(ValidatorTest.class);
		//$JUnit-END$
		return suite;
	}
}
