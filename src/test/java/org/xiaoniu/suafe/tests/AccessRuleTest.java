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

import org.junit.Test;

/**
 * @author Shaun Johnson
 */
public class AccessRuleTest {

	/*
	 * Class under test for void AccessRule()
	 */
	@Test
	public void testAccessRule() {
		/*
		AccessRule rule = new AccessRule();

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
	@Test
	public void testAccessRulePathString() {
		/*
		Path path = new Path();
		AccessRule rule = new AccessRule(path, "r");
		
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
	@Test
	public void testAccessRulePathGroupString() {
		/*
		Path path = new Path();
		Group group = new Group();
		AccessRule rule = new AccessRule(path, group, "r");
		
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
	@Test
	public void testAccessRulePathUserString() {
		/*
		Path path = new Path();
		User user = new User();
		AccessRule rule = new AccessRule(path, user, "r");
		
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
	@Test
	public void testToString() {
	}

	@Test
	public void testGetGroup() {
	}

	@Test
	public void testSetGroup() {
	}

	@Test
	public void testGetUser() {
	}

	@Test
	public void testSetUser() {
	}

	@Test
	public void testGetPath() {
	}

	@Test
	public void testSetPath() {
	}

	@Test
	public void testGetLevel() {
	}

	@Test
	public void testGetLevelFullName() {
	}

	@Test
	public void testSetLevel() {
	}

	@Test
	public void testCompareTo() {
	}
}
