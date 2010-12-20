package org.suafe.core.utilities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.suafe.core.enums.AuthzErrorResourceKey;
import org.suafe.core.enums.AuthzMessageResourceKey;

public class AuthzResourcesTest {
	@Test
	public void testGetStringAuthzErrorResourceKey() {
		try {
			AuthzResources.getString((AuthzErrorResourceKey) null);

			fail("Successfully got value of key null");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			assertNotNull("Value should not be null", AuthzResources.getString(AuthzErrorResourceKey.INVALID_USER_NAME));
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}
	}

	@Test
	public void testGetStringAuthzMessageResourceKey() {
		try {
			AuthzResources.getString((AuthzMessageResourceKey) null);

			fail("Successfully got value of key null");
		}
		catch (final NullPointerException e) {
			assertNotNull("Expected NullPointerException", e.getMessage());
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}

		try {
			assertNotNull("Value should not be null",
					AuthzResources.getString(AuthzMessageResourceKey.APPLICATION_FILE_HEADER));
		}
		catch (final Exception e) {
			fail("Unexpected Exception");
		}
	}
}
