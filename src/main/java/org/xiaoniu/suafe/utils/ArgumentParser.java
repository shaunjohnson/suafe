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

package org.xiaoniu.suafe.utils;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.Switch;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Extension of JSAP used in order to add helper methods to JSAP.
 * @author Shaun Johnson
 */
public final class ArgumentParser extends JSAP {
    private static final char LIST_SEPARATOR = ',';

    /**
     * Adds a switch option using the provided name, short flag, long flag and help resource
     * key.
     *
     * @param name      Switch option name
     * @param shortFlag Switch short flag
     * @param longFlag  Switch long flag
     * @param help      Resource file key for switch help
     * @throws JSAPException if error occurs
     */
    public void addSwitchOption(@Nonnull final String name, @Nullable final Character shortFlag,
                                @Nonnull final String longFlag, @Nonnull final String help) throws JSAPException {
        final Switch opt = new Switch(name);

        opt.setShortFlag(shortFlag == null ? JSAP.NO_SHORTFLAG : shortFlag);
        opt.setLongFlag(longFlag);

        opt.setHelp(ResourceUtil.getString("application.args." + help + ".help"));

        registerParameter(opt);
    }

    /**
     * Adds a flagged option using the provided name, short flag, long flag and help
     * resource key.
     *
     * @param name      Flagged option name
     * @param shortFlag Option short flag
     * @param longFlag  Option long flag
     * @param help      Resource file key for option help
     * @throws JSAPException if error occurs
     */
    public void addStringOption(@Nonnull final String name, @Nullable final Character shortFlag,
                                @Nonnull final String longFlag, @Nonnull final String help) throws JSAPException {
        final FlaggedOption opt = new FlaggedOption(name)
                .setStringParser(JSAP.STRING_PARSER)
                .setShortFlag(shortFlag == null ? JSAP.NO_SHORTFLAG : shortFlag)
                .setLongFlag(longFlag);

        opt.setHelp(ResourceUtil.getString("application.args." + help + ".help"));

        registerParameter(opt);
    }

    /**
     * Adds a list option using the provided name, short flag, long flag and help resource
     * key.
     *
     * @param name      List option name
     * @param shortFlag Option short flag
     * @param longFlag  Option long flag
     * @param help      Resource file key for option help
     * @throws JSAPException if error occurs
     */
    public void addListOption(@Nonnull final String name, @Nullable final Character shortFlag,
                              @Nonnull final String longFlag, @Nonnull final String help) throws JSAPException {
        final FlaggedOption opt = new FlaggedOption(name)
                .setStringParser(JSAP.STRING_PARSER)
                .setShortFlag(shortFlag == null ? JSAP.NO_SHORTFLAG : shortFlag)
                .setLongFlag(longFlag)
                .setList(true)
                .setListSeparator(LIST_SEPARATOR);

        opt.setHelp(ResourceUtil.getString("application.args." + help + ".help"));

        registerParameter(opt);
    }
}
