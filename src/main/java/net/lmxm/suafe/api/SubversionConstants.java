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

package net.lmxm.suafe.api;

public final class SubversionConstants {
    private SubversionConstants() {
        throw new AssertionError();
    }

    public static final String SVN_ACCESS_LEVEL_DENY_ACCESS = "";

    public static final String SVN_ACCESS_LEVEL_NONE = "none";

    public static final String SVN_ACCESS_LEVEL_READONLY = "r";

    public static final String SVN_ACCESS_LEVEL_READWRITE = "rw";

    public static final String SVN_ALL_USERS_NAME = "*";

    public static final String SVN_GROUP_REFERENCE_PREFIX = "@";

    public static final String SVN_PATH_SEPARATOR = "/";
}
