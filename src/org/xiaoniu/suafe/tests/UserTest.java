/*
 * Created on Jul 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.tests;

import junit.framework.TestCase;

import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.User;

/**
 * @author Shaun Johnson
 */
public class UserTest extends TestCase {
	
	private String userName;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		userName = "TestUserName";
	}

	/*
	 * Class under test for void User()
	 */
	public void testUser() {
		User user = new User();
		
		assertNull("Name should be null", user.getName());
		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty", user.getAccessRules().size() == 0);
		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);
	}

	/*
	 * Class under test for void User(String)
	 */
	public void testUserString() {
		User user = new User(userName);
		
		assertNotNull("Name should not be null", user.getName());
		assertEquals("Name should match", userName, user.getName());
		
		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty", user.getAccessRules().size() == 0);
		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);
	}

	/*
	 * Class under test for String toString()
	 */
	public void testToString() {
		User user = new User();
		
		assertNotNull("toString() should be not be null", user.toString());
		assertEquals("toString() should be empty string", "", user.toString());
		
		user = new User(userName);
		
		assertNotNull("toString() should be not be null", user.toString());
		assertEquals("toString() should match userName", userName, user.toString());
	}

	public void testGetName() {
		User user = new User();
		
		assertNull("getName() should be null", user.getName());
		
		user = new User(userName);
		
		assertNotNull("getName() should not be null", user.getName());
		assertEquals("getName() should match", userName, user.getName());
	}

	public void testSetName() {
		User user = new User();
		
		assertNull("getName() should be null", user.getName());
		
		user.setName(userName);
		
		assertNotNull("getName() should not be null", user.getName());
		assertEquals("getName() should match", userName, user.getName());
	}

	public void testGetGroups() {
		User user = new User();
		
		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);
	}

	public void testAddGroup() {
		User user = new User();
		
		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);
		
		Group group = new Group();
		user.addGroup(group);
		
		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should have one element", user.getGroups().size() == 1);
	}

	public void testGetAccessRules() {
		User user = new User();

		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty", user.getAccessRules().size() == 0);
	}

	public void testAddAccessRule() {
		User user = new User();

		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty", user.getAccessRules().size() == 0);
		
		AccessRule rule = new AccessRule();
		user.addAccessRule(rule);
		
		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should have one element", user.getAccessRules().size() == 1);
	}

	public void testCompareTo() {
		User userA = new User();
		User userB = new User();
		
		assertTrue("User A should match user B", userA.compareTo(userB) == 0);
		assertTrue("User B should match user A", userB.compareTo(userA) == 0);
		
		userA = new User("user");
		userB = new User("user");
		
		assertTrue("User A should match user B", userA.compareTo(userB) == 0);
		assertTrue("User B should match user A", userB.compareTo(userA) == 0);
		
		userA = new User("userA");
		userB = new User(null);
		
		assertTrue("User A should not match user B", userA.compareTo(userB) != 0);
		assertTrue("User B should not match user A", userB.compareTo(userA) != 0);
		
		userA = new User("userA");
		userB = new User("userB");
		
		assertTrue("User A should not match user B", userA.compareTo(userB) != 0);
		assertTrue("User B should not match user A", userB.compareTo(userA) != 0);
		assertTrue("User A should be lower than user B", userA.compareTo(userB) < 0);
		assertTrue("User B should be higher than user A", userB.compareTo(userA) > 0);
	}

}
