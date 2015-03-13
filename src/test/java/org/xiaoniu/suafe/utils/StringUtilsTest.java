package org.xiaoniu.suafe.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

    @Test
    public void testIsBlank() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank("     "));
        assertFalse(StringUtils.isBlank("a"));
        assertFalse(StringUtils.isBlank("  a  "));
    }

    @Test
    public void testIsNotBlank() {
        assertFalse(StringUtils.isNotBlank(null));
        assertFalse(StringUtils.isNotBlank(""));
        assertFalse(StringUtils.isNotBlank("     "));
        assertTrue(StringUtils.isNotBlank("a"));
        assertTrue(StringUtils.isNotBlank("  a  "));
    }

}
