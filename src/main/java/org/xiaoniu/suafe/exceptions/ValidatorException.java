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
package org.xiaoniu.suafe.exceptions;


import javax.annotation.Nonnull;

/**
 * Exception that is thrown when a validation fails.
 *
 * @author Shaun Johnson
 */
public final class ValidatorException extends AppException {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2790951968306188250L;

    /**
     * Constructor that accepts a message key.
     *
     * @param key Error message.
     */
    public ValidatorException(@Nonnull final String key) {
        super(key);
    }

    /**
     * Constructor that accepts a message key and argument.
     *
     * @param key      Error message key.
     * @param argument Argument value
     */
    public ValidatorException(@Nonnull final String key, @Nonnull final Object argument) {
        super(key, argument);
    }

    /**
     * Constructor that accepts a message key and argument.
     *
     * @param key      Error message key.
     * @param argument1 Argument value 1
     * @param argument2 Argument value 2
     */
    public ValidatorException(@Nonnull final String key, @Nonnull final Object argument1,
                              @Nonnull final Object argument2) {
        super(key, argument1, argument2);
    }

    /**
     * Constructor that accepts a message key and arguments.
     *
     * @param key       Error message key.
     * @param arguments Argument values
     */
    public ValidatorException(@Nonnull final String key, @Nonnull final Object[] arguments) {
        super(key, arguments);
    }
}
