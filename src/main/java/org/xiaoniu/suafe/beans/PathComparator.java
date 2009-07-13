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
package org.xiaoniu.suafe.beans;

import java.util.Comparator;

/**
 * Comparator for Path objects.
 * 
 * @author Shaun Johnson
 */
public final class PathComparator implements Comparator<Path> {
	
	/**
	 * Compares two Path objects. If repostory names and paths match then the
	 * Path objects are considered a match.
	 */
	public int compare(Path path1, Path path2) {
		String string1 = ((path1.getRepository() == null) ? "" : path1.getRepository().toString()) + ":" +
			((path1.getPath() == null) ? "" : path1.getPath());
		
		String string2 = ((path2.getRepository() == null) ? "" : path2.getRepository().toString()) + ":" +
			((path2.getPath() == null) ? "" : path2.getPath());
		
		return string1.compareTo(string2);
	}

}
