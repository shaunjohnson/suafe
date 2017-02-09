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
package org.xiaoniu.suafe.reports;

import org.xiaoniu.suafe.api.beans.Document;
import org.xiaoniu.suafe.exceptions.AppException;

import javax.annotation.Nonnull;

/**
 * Abstract Report class.
 *
 * @author Shaun Johnson
 */
public abstract class GenericReport {
    protected final Document document;

    /**
     * Constructor that accepts Document object to be used as source for the report.
     *
     * @param document Document object.
     */
    public GenericReport(@Nonnull final Document document) {
        this.document = document;
    }

    /**
     * Generate report and return report HTML contents as String.
     *
     * @return Report HTML source.
     * @throws AppException if error occurs
     */
    @Nonnull
    public abstract String generate() throws AppException;
}
