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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single Path within a Repository.
 * A Path consists of a Repository and a relative path within the repository.
 *
 * @author Shaun Johnson
 */
public final class Path implements Comparable<Path> {
    /**
     * Repository in which the Path exists.
     */
    protected Repository repository;

    /**
     * Relative path within the Repository.
     */
    protected String path;

    /**
     * List of AccessRules in which the Path is referenced.
     */
    private List<AccessRule> accessRules = new ArrayList<>();

    /**
     * Default Constructor.
     */
    public Path() {

    }

    /**
     * Constructor that accepts a Repository and path.
     *
     * @param repository The Repository in which the Path exists.
     * @param path       The relative path within the Repository.
     */
    public Path(Repository repository, String path) {
        this.repository = repository;
        this.path = path;
    }

    /**
     * Gets the relative path within the Repository.
     *
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the relative path within the Repository.
     *
     * @param path The new value for path.
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Gets the Repository referenced by the Path.
     *
     * @return Returns the repository.
     */
    public Repository getRepository() {
        return repository;
    }

    /**
     * Sets the Repository referenced by the Path.
     *
     * @param repository The repository to set.
     */
    public void setRepository(final Repository repository) {
        this.repository = repository;
    }

    /**
     * Gets the list of AccessRules in which the Path is referenced.
     *
     * @return List of AccessRules in which the Path is referenced.
     */
    public List<AccessRule> getAccessRules() {
        return accessRules;
    }

    /**
     * Adds an AccessRule to the list of AccessRules in which the Path is
     * referenced.
     *
     * @param accessRule AccessRule to be added.
     */
    public void addAccessRule(final AccessRule accessRule) {
        accessRules.add(accessRule);
    }

    /**
     * Removes an AccessRule from the list of AccessRules in which the Path
     * is referenced.
     *
     * @param accessRule AccessRule to be removed.
     */
    public void removeAccessRule(final AccessRule accessRule) {
        accessRules.remove(accessRule);
    }

    /**
     * Compares this to the specified object.
     * Used when sorting lists of Path objects.
     *
     * @param otherPath Other Path object to which this is compared.
     * @throws ClassCastException Other is not an instance of Path.
     */
    @Override
    public int compareTo(final Path otherPath) throws ClassCastException {
        return this.toString().compareTo(otherPath.toString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Path path1 = (Path) o;
        return Objects.equals(repository, path1.repository) &&
                Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repository, path);
    }

    /**
     * Returns the Path object as a String.
     */
    @Override
    public String toString() {
        return (path == null) ? "" : path;
    }
}
