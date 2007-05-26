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
	 * @param <T> Any type
	 * @param array Array of <T> objects
	 * @param typeSample Sample type, matching <T>
	 * @return Array of <T> objects
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] convertToArray(Object[] array, T[] typeSample) {
        if (typeSample.length < array.length)
            typeSample = (T[])java.lang.reflect.Array.newInstance(typeSample.getClass().getComponentType(), array.length);
	
        System.arraycopy(array, 0, typeSample, 0, array.length);
        
        if (typeSample.length > array.length)
            typeSample[array.length] = null;
        
        return typeSample;
	}
}
