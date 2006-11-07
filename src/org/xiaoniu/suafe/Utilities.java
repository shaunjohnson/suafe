/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://suafe.xiaoniu.org.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://suafe.xiaoniu.org/.
 * ====================================================================
 * @endcopyright
 */
package org.xiaoniu.suafe;

/**
 * Generic utility methods.
 * 
 * @author Shaun Johnson
 */
public class Utilities {
	/**
	 * Converts an array of Objects into an array of <T>.
	 * 
	 * @param <T>
	 * @param array
	 * @param a
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] convertToArray(Object[] array, T[] a) {
        if (a.length < array.length)
            a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), array.length);
	
        System.arraycopy(array, 0, a, 0, array.length);
        
        if (a.length > array.length)
            a[array.length] = null;
        
        return a;
	}
}
