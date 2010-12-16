package org.suafe.core.enums;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.suafe.core.enums.AuthzErrorResourceKey;
import org.suafe.core.utilities.AuthzResources;

public class AuthzErrorResourceKeyTest {
	@Test
	public void testAuthzErrorResourceKey() {
		for (final AuthzErrorResourceKey value : AuthzErrorResourceKey.values()) {
			assertNotNull("Unable to load resource with key \"" + value.toString() + "\"",
					AuthzResources.getString(value));
		}
	}

	@Test
	public void testEquals() {
		assertTrue("Values should match",
				AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.equals(AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP));
		assertFalse("Values should not match",
				AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.equals(AuthzErrorResourceKey.GROUP_ALREADY_EXISTS));

		// Test invalid values
		assertFalse("Values should not match", AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.equals(null));
		assertFalse("Values should not match", AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.equals(""));
		assertFalse("Values should not match",
				AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.equals(AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP
						.toString()));
	}

	@Test
	public void testHashCode() {
		assertTrue(
				"HashCode values should match",
				AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.hashCode() == AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP
						.hashCode());
		assertFalse("HashCode values should not match",
				AuthzErrorResourceKey.ALREADY_MEMBER_OF_GROUP.hashCode() == AuthzErrorResourceKey.GROUP_ALREADY_EXISTS
						.hashCode());
	}
}
