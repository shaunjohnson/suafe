/*
 * Created on Jul 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe.tests;

import junit.framework.TestCase;

import org.xiaoniu.suafe.exceptions.ValidatorException;
import org.xiaoniu.suafe.validators.Validator;

/**
 * @author Shaun Johnson
 */
public class ValidatorTest extends TestCase {

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
		
//		try {
//			Validator.validateGroupName("My,Name,Is,Comma");
//			fail("Validate group name with comma should fail with ValidatorException");
//		}
//		catch (ValidatorException ve) {}
	}

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

	public void testValidateRelativePath() {
	}

	public void testValidateRepositoryName() {
	}

	public void testValidateUserName() {
	}

	/*
	 * Class under test for void validateNotEmptyString(String)
	 */
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

}
