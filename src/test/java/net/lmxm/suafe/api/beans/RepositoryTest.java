/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.lmxm.suafe.api.beans;

import net.lmxm.suafe.api.beans.Path;
import net.lmxm.suafe.api.beans.Repository;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Repository unit tests.
 *
 * @author Shaun Johnson
 */
public final class RepositoryTest {
    private String repositoryName = "TestRepoName";

    @Test
    public void testAddRemovePath() {
        final Repository repository = new Repository();

        assertTrue(repository.getPaths() != null);
        assertTrue(repository.getPaths().size() == 0);

        final Path path = new Path();

        repository.addPath(path);

        assertTrue(repository.getPaths() != null);
        assertTrue(repository.getPaths().size() == 1);
        assertTrue(repository.getPaths().get(0) == path);
        assertTrue(repository.getPaths().get(0).equals(path));

        final Path path2 = new Path(repository, "path");

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
        final Repository repository = new Repository();

        assertTrue(repository.getName() == null);
        assertTrue(repository.getPaths() != null);
        assertTrue(repository.getPaths().size() == 0);
    }

    /*
     * Class under test for void Repository(String)
     */
    @Test
    public void testRepositoryString() {
        final Repository repository = new Repository(repositoryName);

        assertTrue(repository.getName() != null);
        assertTrue(repository.getName().equals(repositoryName));

        assertTrue(repository.getPaths() != null);
        assertTrue(repository.getPaths().size() == 0);
    }

    @Test
    public void testSetName() {
        final Repository repository = new Repository();

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
