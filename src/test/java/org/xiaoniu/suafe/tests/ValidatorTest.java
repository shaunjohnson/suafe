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

import static org.junit.Assert.fail;

import org.junit.Test;
import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.validators.Validator;

/**
 * @author Shaun Johnson
 */
public class ValidatorTest {

	@Test
	public void testValidateGroupName() {
		try {
			Validator.validateGroupName(null);
			fail("Validate null group name should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateGroupName("");
			fail("Validate empty group name should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validateGroupName("This is a test");
		}
		catch (ValidatorException ve) {
			fail("Validate group name with spaces should not fail with ValidatorException");	
		}
	}

	@Test
	public void testValidateLevelOfAccess() {
		try {
			Validator.validateLevelOfAccess(null);
			fail("Validate null level of access should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateLevelOfAccess("r");
		}
		catch (ValidatorException ve) {
			fail("Validate r level of access should not fail with ValidatorException");
		}
		
		try {
			Validator.validateLevelOfAccess("rw");
		}
		catch (ValidatorException ve) {
			fail("Validate rw level of access should not fail with ValidatorException");
		}
		
		try {
			Validator.validateLevelOfAccess("");
		}
		catch (ValidatorException ve) {
			fail("Validate Deny Access level of access not should fail with ValidatorException");	
		}
		
		try {
			Validator.validateLevelOfAccess("R");
			fail("Validate R level of access should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validateLevelOfAccess("RW");
			fail("Validate RW level of access should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateLevelOfAccess("This is wrong");
			fail("Validate unknown level of access should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
	}

	/*
	 * Class under test for void validateNotEmptyString(String)
	 */
	@Test
	public void testValidateNotEmptyStringString() {
		try {
			Validator.validateNotEmptyString(null);
			fail("Validate null string should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateNotEmptyString("    ");
			fail("Validate string of spaces should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validateNotEmptyString("\t\t\t\t");
			fail("Validate string of tabs should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateNotEmptyString("    This is surrounded by spaces    ");
		}
		catch (ValidatorException ve) {
			fail("Validate string of with spaces should not fail with ValidatorException");	
		}
	}

	/*
	 * Class under test for void validateNotEmptyString(String, String)
	 */
	@Test
	public void testValidateNotEmptyStringStringString() {
		try {
			Validator.validateNotEmptyString("field", null);
			fail("Validate null string should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateNotEmptyString("field", "    ");
			fail("Validate string of spaces should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validateNotEmptyString("field", "\t\t\t\t");
			fail("Validate string of tabs should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateNotEmptyString("field", "    This is surrounded by spaces    ");
		}
		catch (ValidatorException ve) {
			fail("Validate string of with spaces should not fail with ValidatorException");	
		}
	}

	@Test
	public void testValidatePath() {
		try {
			Validator.validatePath(null);
			fail("Validate null path should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validatePath("");
			fail("Validate empty path should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validatePath("test");
			fail("Validate path must start with / should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validatePath("/this=test");
			fail("Validate path with equals characters should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validatePath("/test/");
			fail("Validate path ending with / should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validatePath("/test");
		}
		catch (ValidatorException ve) {
			fail("Valid path should not fail with ValidatorException");
		}
	}
	
	@Test
	public void testValidateRepositoryName() {
		try {
			Validator.validateRepositoryName(null);
			fail("Validate null repository name should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateRepositoryName("");
			fail("Validate empty repository name should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validateRepositoryName("This is a test");
		}
		catch (ValidatorException ve) {
			fail("Validate repository name with spaces should not fail with ValidatorException");	
		}
	}

	@Test
	public void testValidateUserName() {
		try {
			Validator.validateUserName(null);
			fail("Validate null user name should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}

		try {
			Validator.validateUserName("");
			fail("Validate empty user name should fail with ValidatorException");
		}
		catch (ValidatorException ve) {}
		
		try {
			Validator.validateUserName("This is a test");
		}
		catch (ValidatorException ve) {
			fail("Validate user name with spaces should not fail with ValidatorException");	
		}
	}
}
