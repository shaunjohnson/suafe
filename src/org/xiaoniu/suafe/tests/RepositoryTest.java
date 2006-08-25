package org.xiaoniu.suafe.tests;

import junit.framework.TestCase;

import org.xiaoniu.suafe.beans.Repository;

/**
 * @author Shaun Johnson
 */
public class RepositoryTest extends TestCase {

	private String repositoryName;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		repositoryName = "TestGroupName";
	}

	/*
	 * Class under test for void Repository()
	 */
	public void testRepository() {
		Repository repository = new Repository();
		
		assertNull("Name should be null", repository.getName());
		assertNotNull("Paths should not be null", repository.getPaths());
		assertTrue("Paths should be empty", repository.getPaths().size() == 0);
	}

	/*
	 * Class under test for void Repository(String)
	 */
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
	public void testToString() {
		Repository repository = new Repository();
		
		assertNotNull("toString() should be not be null", repository.toString());
		assertEquals("toString() should be empty string", "", repository.toString());
		
		repository = new Repository(repositoryName);
		
		assertNotNull("toString() should be not be null", repository.toString());
		assertEquals("toString() should match repositoryName", repositoryName, repository.toString());
	}

	public void testGetName() {
		Repository repository = new Repository();
		
		assertNull("getName() should be null", repository.getName());
		
		repository = new Repository(repositoryName);
		
		assertNotNull("getName() should not be null", repository.getName());
		assertEquals("getName() should match", repositoryName, repository.getName());
	}

	public void testSetName() {
	}

	public void testGetPaths() {
	}

	public void testAddPath() {
	}

	public void testRemovePath() {
	}

	public void testCompareTo() {
	}

}
