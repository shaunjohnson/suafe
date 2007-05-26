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
