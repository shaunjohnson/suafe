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
package org.xiaoniu.suafe;

/**
 * Constant values used throughout the application.
 *
 * @author Shaun Johnson
 */
public final class Constants {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static final String MIME_HTML = "text/html";

    public static final String MIME_TEXT = "text/plain";

    public static final String PATH_BASE_RESOURCE_DIR = "org/xiaoniu/suafe/resources";

    public static final String PATH_RESOURCE_BUNDLE = PATH_BASE_RESOURCE_DIR + "/Resources";

    public static final String PATH_RESOURCE_DIR_FULL = "/" + PATH_BASE_RESOURCE_DIR;

    public static final String PATH_RESOURCE_HELP_DIR = PATH_BASE_RESOURCE_DIR + "/help";

    public static final String PATH_RESOURCE_IMAGE_DIR = PATH_RESOURCE_DIR_FULL + "/images";

    public static final String TEXT_NEW_LINE = System.getProperty("line.separator");

    private Constants() {
        // Deliberately left blank
    }
}