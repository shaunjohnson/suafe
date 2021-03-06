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
package net.lmxm.suafe;

import net.lmxm.suafe.exceptions.AppException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Generic utility methods.
 *
 * @author Shaun Johnson
 */
public final class Utilities {
    /**
     * Open output file.
     *
     * @param filePath Path of output file
     * @return Output stream for file
     * @throws AppException Error occurred
     */
    @Nonnull
    public static PrintStream openOutputFile(@Nonnull final String filePath) throws AppException {
        final PrintStream output;

        try {
            output = new PrintStream(new File(filePath));
        }
        catch (FileNotFoundException fne) {
            throw new AppException("generator.filenotfound");
        }
        catch (Exception e) {
            throw new AppException("generator.error");
        }

        return output;
    }

    /**
     * Open output file.
     *
     * @param file File object representing the output file
     * @return Output stream for file
     * @throws AppException Error occurred
     */
    @Nonnull
    public static PrintStream openOutputFile(@Nonnull final File file) throws AppException {
        final PrintStream output;

        try {
            output = openOutputFile(file.getCanonicalPath());
        }
        catch (IOException e) {
            throw new AppException("generator.error");
        }

        return output;
    }

    private Utilities() {
        // Deliberately left blank
    }
}
