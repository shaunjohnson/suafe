/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.util;

import java.util.List;

/**
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public final class StringUtils {

    public static String noNull(String s) {
        return (s == null ? "" : s);
    }

    public static String padRight(String s, int padCount) {
        if (padCount < 0) {
            throw (new IllegalArgumentException("padCount must be >= 0"));
        }
        StringBuffer buf = new StringBuffer(noNull(s));
        for (int i = 0; i < padCount; ++i) {
            buf.append(' ');
        }
        return (buf.toString());
    }

    public static String padRightToWidth(String s, int desiredWidth) {
        String result = noNull(s);
        if (result.length() < desiredWidth) {
            result = padRight(result, desiredWidth - result.length());
        }
        return (result);
    }

    public static List wrapToList(String s, int width) {
        List result = new java.util.LinkedList();
        if ((s != null) && (s.length() > 0)) {
            StringBuffer buf = new StringBuffer();
            int lastSpaceBufIndex = -1;
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c == '\n') {
                    result.add(buf.toString());
                    buf.setLength(0);
                    lastSpaceBufIndex = -1;
                } else {
                    if (c == ' ') {
                        if (buf.length() >= width - 1) {
                            result.add(buf.toString());
                            buf.setLength(0);
                            lastSpaceBufIndex = -1;
                        }
                        if (buf.length() > 0) {
                            lastSpaceBufIndex = buf.length();
                            buf.append(c);
                        }
                    } else {
                    	if (buf.length() >= width) {
                            if (lastSpaceBufIndex != -1) {
                                result.add(buf.substring(0, lastSpaceBufIndex));
                                buf.delete(0, lastSpaceBufIndex + 1);
                                lastSpaceBufIndex = -1;
                            }
                        }
                        buf.append(c);
                    }
                }
            }
            if (buf.length() > 0) {
                result.add(buf.toString());
            }
        }
        return (result);
    }

    public static void main(String[] args) {
        String s =
            "This is\n a test that I would like to word wrap at 15 characters.";

        System.out.println("123456789012345");
        List l = wrapToList(s, 15);
        for (java.util.Iterator i = l.iterator(); i.hasNext();) {
            String s1 = (String) i.next();
            System.out.println(s1 + "|");
        }
    }
}