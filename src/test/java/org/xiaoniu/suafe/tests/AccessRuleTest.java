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

import junit.framework.TestCase;

import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.User;

/**
 * @author Shaun Johnson
 */
public class AccessRuleTest extends TestCase {

	/*
	 * Class under test for void AccessRule()
	 */
	public void testAccessRule() {
		AccessRule rule = new AccessRule();
		/*
		assertNull("Path should be null", rule.getPath());
		assertNull("Group should be null", rule.getGroup());
		assertNull("User should be null", rule.getUser());
		assertNull("Level should be null", rule.getLevel());
		assertNotNull("Full level name should not be null", rule.getLevelFullName());
		*/
	}

	/*
	 * Class under test for void AccessRule(Path, String)
	 */
	public void testAccessRulePathString() {
		Path path = new Path();
		AccessRule rule = new AccessRule(path, "r");
		/*
		assertEquals("Path should match", path, rule.getPath());
		assertNull("Group should be null", rule.getGroup());
		assertNull("User should be null", rule.getUser());
		assertNotNull("Level should not be null", rule.getLevel());
		assertEquals("Level should match", "r", rule.getLevel());
		assertNotNull("Full level name should not be null", rule.getLevelFullName());		
		*/
	}

	/*
	 * Class under test for void AccessRule(Path, Group, String)
	 */
	public void testAccessRulePathGroupString() {
		Path path = new Path();
		Group group = new Group();
		AccessRule rule = new AccessRule(path, group, "r");
		
		/*
		assertEquals("Path should match", path, rule.getPath());
		assertEquals("Group should match", group, rule.getGroup());
		assertNull("User should be null", rule.getUser());
		assertNotNull("Level should not be null", rule.getLevel());
		assertEquals("Level should match", "r", rule.getLevel());
		assertNotNull("Full level name should not be null", rule.getLevelFullName());		
		*/
	}

	/*
	 * Class under test for void AccessRule(Path, User, String)
	 */
	public void testAccessRulePathUserString() {
		Path path = new Path();
		User user = new User();
		AccessRule rule = new AccessRule(path, user, "r");
		
		/*
		assertEquals("Path should match", path, rule.getPath());
		assertNull("Group should be null", rule.getGroup());
		assertEquals("User should match", user, rule.getUser());
		assertNotNull("Level should not be null", rule.getLevel());
		assertEquals("Level should match", "r", rule.getLevel());
		assertNotNull("Full level name should not be null", rule.getLevelFullName());	
		*/
	}

	/*
	 * Class under test for String toString()
	 */
	public void testToString() {
	}

	public void testGetGroup() {
	}

	public void testSetGroup() {
	}

	public void testGetUser() {
	}

	public void testSetUser() {
	}

	public void testGetPath() {
	}

	public void testSetPath() {
	}

	public void testGetLevel() {
	}

	public void testGetLevelFullName() {
	}

	public void testSetLevel() {
	}

	public void testCompareTo() {
	}

}
