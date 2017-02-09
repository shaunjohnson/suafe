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
package net.lmxm.suafe.api.exceptions;

import net.lmxm.suafe.exceptions.AppException;
import net.lmxm.suafe.resources.ResourceUtil;

/**
 * Exception thrown when a parser error occurs.
 *
 * @author Shaun Johnson
 */
public final class ParserException extends AppException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3638514503455900657L;

    /**
     * Constructor that an error message.
     *
     * @param message Error message
     */
    private ParserException(String key, Object argument1, Object argument2) {
        super(key, argument1, argument2);
    }

    /**
     * Exception factory method that generates ParserException objects. The line
     * number and error message may be displayed to the user so that he knows
     * where the error occurred.
     *
     * @param lineNumber Line number where error occurred.
     * @param key        Error message key
     * @return ParserException with localized error message.
     */
    public static ParserException generateException(int lineNumber, String key) {
        return new ParserException("parser.exception", lineNumber, ResourceUtil
                .getString(key));
    }

    /**
     * Exception factory method that generates ParserException objects. The line
     * number and error message may be displayed to the user so that he knows
     * where the error occurred.
     *
     * @param lineNumber Line number where error occurred.
     * @param key        Error message key
     * @param argument   Argument value
     * @return ParserException with localized error message.
     */
    public static ParserException generateException(int lineNumber, String key,
                                                    String argument) {
        return new ParserException("parser.exception", lineNumber, ResourceUtil
                .getFormattedString(key, argument));
    }

    /**
     * Exception factory method that generates ParserException objects. The line
     * number and error message may be displayed to the user so that he knows
     * where the error occurred.
     *
     * @param lineNumber Line number where error occurred.
     * @param key        Error message key
     * @param arguments  Argument values
     * @return ParserException with localized error message.
     */
    public static ParserException generateException(int lineNumber, String key,
                                                    Object[] arguments) {
        return new ParserException("parser.exception", lineNumber, ResourceUtil
                .getFormattedString(key, arguments));
    }

    /**
     * Exception factory method that generates ParserException objects. The line
     * number and error message may be displayed to the user so that he knows
     * where the error occurred.
     *
     * @param lineNumber Line number where error occurred.
     * @param key        Error message key
     * @return ParserException with localized error message.
     */
    public static ParserException generateException(int lineNumber, Exception e) {
        return new ParserException("parser.exception", lineNumber, e.getMessage());
    }
}
