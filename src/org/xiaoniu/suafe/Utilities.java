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
