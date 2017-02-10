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

package net.lmxm.suafe.test;

import net.lmxm.suafe.exceptions.AppException;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import javax.annotation.Nonnull;

/**
 * JUnit matcher used for asserting AppException key values.
 */
public final class AppExceptionMatcher extends BaseMatcher {
    private final String expectedKey;

    private AppExceptionMatcher(@Nonnull final String expectedKey) {
        this.expectedKey = expectedKey;
    }

    @Override
    public boolean matches(final Object o) {
        return o instanceof AppException && expectedKey.equals(((AppException) o).getKey());

    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("Key value does not match ").appendText(expectedKey);
    }

    @Nonnull
    public static AppExceptionMatcher hasKey(@Nonnull final String expectedKey) {
        return new AppExceptionMatcher(expectedKey);
    }
}
