package org.xiaoniu.suafe.beans;

import java.util.Comparator;

/**
 * @author spjohnso
 */
public class PathComparator implements Comparator {

	public int compare(Object object1, Object object2) {
		if (!(object1 instanceof Path) || !(object2 instanceof Path)) {
			throw new ClassCastException("Invalid object type. Cannot cast to Path.");
		}	
		
		Path path1 = (Path)object1;
		Path path2 = (Path)object2; 
		
		String string1 = ((path1.getRepository() == null) ? "" : path1.getRepository().toString()) + ":" +
			((path1.getPath() == null) ? "" : path1.getPath());
		
		String string2 = ((path2.getRepository() == null) ? "" : path2.getRepository().toString()) + ":" +
			((path2.getPath() == null) ? "" : path2.getPath());
		
		return string1.compareTo(string2);
	}

}
