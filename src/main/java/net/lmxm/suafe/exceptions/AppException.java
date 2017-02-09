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

package net.lmxm.suafe.exceptions;

import net.lmxm.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;

/**
 * Exception thrown when a general error occurs.
 *
 * @author Shaun Johnson
 */
public class AppException extends Exception {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7928884482694610184L;

    /**
     * Constructor that accepts a message key.
     *
     * @param key Error message key.
     */
    public AppException(@Nonnull final String key) {
        super(ResourceUtil.getString(key));
    }

    /**
     * Constructor that accepts a message key and argument.
     *
     * @param key      Error message key.
     * @param argument Argument value
     */
    public AppException(@Nonnull final String key, @Nonnull final Object argument) {
        super(ResourceUtil.getFormattedString(key, argument));
    }

    /**
     * Constructor that accepts a message key and argument.
     *
     * @param key      Error message key.
     * @param argument1 Argument value 1
     * @param argument2 Argument value 2
     */
    public AppException(@Nonnull final String key, @Nonnull final Object argument1, @Nonnull final Object argument2) {
        super(ResourceUtil.getFormattedString(key, argument1, argument2));
    }

    /**
     * Constructor that accepts a message key and arguments.
     *
     * @param key       Error message key.
     * @param arguments Argument values
     */
    public AppException(@Nonnull final String key, @Nonnull final Object[] arguments) {
        super(ResourceUtil.getFormattedString(key, arguments));
    }
}
