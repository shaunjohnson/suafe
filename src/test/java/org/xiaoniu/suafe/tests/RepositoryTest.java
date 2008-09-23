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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.xiaoniu.suafe.beans.Repository;

/**
 * @author Shaun Johnson
 */
public class RepositoryTest {

	private String repositoryName;
	
	@Before
	public void before() throws Exception {		
		repositoryName = "TestRepoName";
	}

	/*
	 * Class under test for void Repository()
	 */
	@Test
	public void testRepository() {
		Repository repository = new Repository();
		
		assertNull("Name should be null", repository.getName());
		assertNotNull("Paths should not be null", repository.getPaths());
		assertTrue("Paths should be empty", repository.getPaths().size() == 0);
	}

	/*
	 * Class under test for void Repository(String)
	 */
	@Test
	public void testRepositoryString() {
		Repository repository = new Repository(repositoryName);
		
		assertNotNull("Name should not be null", repository.getName());
		assertEquals("Name should match", repositoryName, repository.getName());
		
		assertNotNull("Paths should not be null", repository.getPaths());
		assertTrue("Paths should be empty", repository.getPaths().size() == 0);	
	}

	/*
	 * Class under test for String toString()
	 */
	@Test
	public void testToString() {
		Repository repository = new Repository();
		
		assertNotNull("toString() should be not be null", repository.toString());
		assertEquals("toString() should be empty string", "", repository.toString());
		
		repository = new Repository(repositoryName);
		
		assertNotNull("toString() should be not be null", repository.toString());
		assertEquals("toString() should match repositoryName", repositoryName, repository.toString());
	}

	@Test
	public void testGetName() {
		Repository repository = new Repository();
		
		assertNull("getName() should be null", repository.getName());
		
		repository = new Repository(repositoryName);
		
		assertNotNull("getName() should not be null", repository.getName());
		assertEquals("getName() should match", repositoryName, repository.getName());
	}

	@Test
	public void testSetName() {
	}

	@Test
	public void testGetPaths() {
	}

	@Test
	public void testAddPath() {
	}

	@Test
	public void testRemovePath() {
	}

	@Test
	public void testCompareTo() {
	}
}
