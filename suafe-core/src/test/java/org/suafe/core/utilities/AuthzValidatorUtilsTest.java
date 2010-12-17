package org.suafe.core.utilities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AuthzValidatorUtilsTest {
	@Test
	public void testIsValidGroupName() {
		// Test invalid values
		assertFalse("Null group name is invalid", AuthzValidatorUtils.isValidGroupName(null));
		assertFalse("Empty group name is invalid", AuthzValidatorUtils.isValidGroupName(""));
		assertFalse("Blank group name is invalid", AuthzValidatorUtils.isValidGroupName("  "));

		// Test valid values
		assertTrue("Non-blank group name is valid", AuthzValidatorUtils.isValidGroupName("name"));
		assertTrue("Non-blank group name is valid", AuthzValidatorUtils.isValidGroupName("  name  "));
	}

	@Test
	public void testIsValidPath() {
		// Test invalid values
		assertFalse("Null path is invalid", AuthzValidatorUtils.isValidPath(null));
		assertFalse("Empty path is invalid", AuthzValidatorUtils.isValidPath(""));
		assertFalse("Blank path is invalid", AuthzValidatorUtils.isValidPath("  "));

		// Test valid values
		assertTrue("Single slash is valid", AuthzValidatorUtils.isValidPath("/"));
		assertTrue("Path is valid", AuthzValidatorUtils.isValidPath("/asdf"));
		assertTrue("Path is valid", AuthzValidatorUtils.isValidPath(" /asdf "));
		assertTrue("Path is valid", AuthzValidatorUtils.isValidPath("/as df"));

		// Test invalid path
		assertFalse("Path must not end with a slash", AuthzValidatorUtils.isValidPath("/asdf/"));
		assertFalse("Path must not end with a slash", AuthzValidatorUtils.isValidPath("  /asdf/  "));
	}

	@Test
	public void testIsValidRepositoryName() {
		// Test invalid values
		assertFalse("Null repository name is invalid", AuthzValidatorUtils.isValidRepositoryName(null));
		assertFalse("Empty repository name is invalid", AuthzValidatorUtils.isValidRepositoryName(""));
		assertFalse("Blank repository name is invalid", AuthzValidatorUtils.isValidRepositoryName("  "));

		// Test valid values
		assertTrue("Non-blank repository name is valid", AuthzValidatorUtils.isValidRepositoryName("name"));
		assertTrue("Non-blank repository name is valid", AuthzValidatorUtils.isValidRepositoryName("  name  "));
	}

	@Test
	public void testIsValidUserAlias() {
		// Test invalid values
		assertFalse("Null user alias is invalid", AuthzValidatorUtils.isValidUserAlias(null));
		assertFalse("Empty user alias is invalid", AuthzValidatorUtils.isValidUserAlias(""));
		assertFalse("Blank user alias is invalid", AuthzValidatorUtils.isValidUserAlias("  "));

		// Test valid values
		assertTrue("Non-blank user alias is valid", AuthzValidatorUtils.isValidUserAlias("name"));
		assertTrue("Non-blank user alias is valid", AuthzValidatorUtils.isValidUserAlias("  name  "));
	}

	@Test
	public void testIsValidUserName() {
		// Test invalid values
		assertFalse("Null user name is invalid", AuthzValidatorUtils.isValidUserName(null));
		assertFalse("Empty user name is invalid", AuthzValidatorUtils.isValidUserName(""));
		assertFalse("Blank user name is invalid", AuthzValidatorUtils.isValidUserName("  "));

		// Test valid values
		assertTrue("Non-blank user name is valid", AuthzValidatorUtils.isValidUserName("name"));
		assertTrue("Non-blank user name is valid", AuthzValidatorUtils.isValidUserName("  name  "));
	}
}
