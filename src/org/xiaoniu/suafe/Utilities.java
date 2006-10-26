package org.xiaoniu.suafe;

public class Utilities {
	public static <T> T[] convertToArray(Object[] array, T[] a) {
        if (a.length < array.length)
            a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), array.length);
	
        System.arraycopy(array, 0, a, 0, array.length);
        
        if (a.length > array.length)
            a[array.length] = null;
        
        return a;
	}
}
