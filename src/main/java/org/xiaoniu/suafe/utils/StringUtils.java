package org.xiaoniu.suafe.utils;

/**
 * String utility methods.
 *
 * @author Shaun Johnson
 */
public final class StringUtils {
    /**
     * Determines if the provided String is null or blank. Strings that contains
     * only white space are considered blank.
     *
     * @param value Value to be tested
     * @return True if value is blank or empty, otherwise false
     */
    public static boolean isBlank(String value) {
        return value == null ? true : value.trim().length() == 0;
    }

    /**
     * Determines if the provided String is not null or blank. Strings that
     * contain only white space are considered blank.
     *
     * @param value Value to be tested.
     * @return True if value is not blank, otherwise false.
     */
    public static boolean isNotBlank(String value) {
        return value == null ? false : value.trim().length() > 0;
    }
}
