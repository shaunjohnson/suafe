/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
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
