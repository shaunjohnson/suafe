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
package org.xiaoniu.suafe.api.beans;

import java.util.Comparator;

/**
 * Comparator for Path objects.
 *
 * @author Shaun Johnson
 */
public final class PathComparator implements Comparator<Path> {

    /**
     * Compares two Path objects. If repostory names and paths match then the
     * Path objects are considered a match.
     */
    public int compare(Path path1, Path path2) {
        String string1 = ((path1.getRepository() == null) ? "" : path1.getRepository().toString()) + ":" +
                ((path1.getPath() == null) ? "" : path1.getPath());

        String string2 = ((path2.getRepository() == null) ? "" : path2.getRepository().toString()) + ":" +
                ((path2.getPath() == null) ? "" : path2.getPath());

        return string1.compareTo(string2);
    }

}
