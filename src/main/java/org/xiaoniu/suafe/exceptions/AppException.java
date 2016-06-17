package org.xiaoniu.suafe.exceptions;

import org.xiaoniu.suafe.resources.ResourceUtil;

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
