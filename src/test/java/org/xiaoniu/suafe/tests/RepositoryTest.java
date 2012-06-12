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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.xiaoniu.suafe.beans.Path;
import org.xiaoniu.suafe.beans.Repository;

/**
 * @author Shaun Johnson
 */
public class RepositoryTest {

	private String repositoryName = "TestRepoName";

	@Test
	public void testAddRemovePath() {
		Repository repository = new Repository();

		assertTrue(repository.getPaths() != null);
		assertTrue(repository.getPaths().size() == 0);

		Path path = new Path();

		repository.addPath(path);

		assertTrue(repository.getPaths() != null);
		assertTrue(repository.getPaths().size() == 1);
		assertTrue(repository.getPaths().get(0) == path);
		assertTrue(repository.getPaths().get(0).equals(path));

		Path path2 = new Path(repository, "path");

		repository.addPath(path2);

		assertTrue(repository.getPaths() != null);
		assertTrue(repository.getPaths().size() == 2);
		assertTrue(repository.getPaths().get(1) == path2);
		assertTrue(repository.getPaths().get(1).equals(path2));
		assertTrue(repository.getPaths().get(1).getRepository().equals(repository));

		repository.removePath(path);
		repository.removePath(path2);

		assertTrue(repository.getPaths() != null);
		assertTrue(repository.getPaths().size() == 0);
	}

	@Test
	public void testCompareTo() {
		Repository repositoryA = new Repository();
		Repository repositoryB = new Repository();

		assertTrue(repositoryA.compareTo(repositoryB) == 0);
		assertTrue(repositoryB.compareTo(repositoryA) == 0);

		repositoryA = new Repository("repository");
		repositoryB = new Repository("repository");

		assertTrue(repositoryA.compareTo(repositoryB) == 0);
		assertTrue(repositoryB.compareTo(repositoryA) == 0);

		repositoryA = new Repository("repositoryA");
		repositoryB = new Repository(null);

		assertTrue(repositoryA.compareTo(repositoryB) != 0);
		assertTrue(repositoryB.compareTo(repositoryA) != 0);

		repositoryA = new Repository("repositoryA");
		repositoryB = new Repository("repositoryB");

		assertTrue(repositoryB.compareTo(repositoryA) != 0);
		assertTrue(repositoryA.compareTo(repositoryB) != 0);
		assertTrue(repositoryA.compareTo(repositoryB) < 0);
		assertTrue(repositoryB.compareTo(repositoryA) > 0);
	}

	@Test
	public void testEquals() {
		Repository repositoryA = new Repository();
		Repository repositoryB = new Repository();

		assertTrue(repositoryA.equals(repositoryB));
		assertTrue(repositoryB.equals(repositoryA));

		repositoryA = new Repository("repository");
		repositoryB = new Repository("repository");

		assertTrue(repositoryA.equals(repositoryB));
		assertTrue(repositoryB.equals(repositoryA));
		
		repositoryA = new Repository("repository");
		repositoryB = new Repository("   repository   ");

		assertTrue(repositoryA.equals(repositoryB));
		assertTrue(repositoryB.equals(repositoryA));
		
		repositoryA = new Repository("repository");
		repositoryB = new Repository("REPOSITORY");

		assertFalse(repositoryA.equals(repositoryB));
		assertFalse(repositoryB.equals(repositoryA));

		repositoryA = new Repository("repositoryA");
		repositoryB = new Repository(null);

		assertFalse(repositoryA.equals(repositoryB));
		assertFalse(repositoryB.equals(repositoryA));

		repositoryA = new Repository("repositoryA");
		repositoryB = new Repository("repositoryB");

		assertFalse(repositoryB.equals(repositoryA));
		assertFalse(repositoryA.equals(repositoryB));
	}

	@Test
	public void testGetName() {
		Repository repository = new Repository();

		assertTrue(repository.getName() == null);

		repository = new Repository(repositoryName);

		assertTrue(repository.getName() != null);
		assertTrue(repository.getName().equals(repositoryName));
	}

	/*
	 * Class under test for void Repository()
	 */
	@Test
	public void testRepository() {
		Repository repository = new Repository();

		assertTrue(repository.getName() == null);
		assertTrue(repository.getPaths() != null);
		assertTrue(repository.getPaths().size() == 0);
	}

	/*
	 * Class under test for void Repository(String)
	 */
	@Test
	public void testRepositoryString() {
		Repository repository = new Repository(repositoryName);

		assertTrue(repository.getName() != null);
		assertTrue(repository.getName().equals(repositoryName));

		assertTrue(repository.getPaths() != null);
		assertTrue(repository.getPaths().size() == 0);
	}

	@Test
	public void testSetName() {
		Repository repository = new Repository();

		assertTrue(repository.getName() == null);

		repository.setName(repositoryName);

		assertTrue(repository.getName() != null);
		assertTrue(repository.getName().equals(repositoryName));
		assertTrue(repository.getName() == repositoryName);
	}

	/*
	 * Class under test for String toString()
	 */
	@Test
	public void testToString() {
		Repository repository = new Repository();

		assertTrue(repository.toString() != null);
		assertTrue(repository.toString().equals(""));

		repository = new Repository(repositoryName);

		assertTrue(repository.toString() != null);
		assertTrue(repository.toString().equals(repositoryName));
	}
}
