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

import java.awt.Font;

/**
 * Constant values used throughout the application.
 * 
 * @author Shaun Johnson
 */
public class Constants {
	
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	
	public static final Font FONT_BOLD_LARGE = new Font("Dialog", Font.BOLD, 14);
	
	public static final String FONT_FAMILY_MONOSPACED = "Monospaced";
	
	public static final String FONT_FAMILY_SANS_SERIF = "SansSerif";
	
	public static final String FONT_FAMILY_SERIF = "Serif";
	
	public static final Font FONT_MONOSPACE = new Font("Courier New", Font.PLAIN, 12);
	
	public static final Font FONT_PLAIN = new Font("Dialog", Font.PLAIN, 12);
	
	public static final String MIME_HTML = "text/html";

	public static final String MIME_TEXT = "text/plain";
	
	public static final String PATH_BASE_RESOURCE_DIR = "org/xiaoniu/suafe/resources";
	
	public static final String PATH_RESOURCE_BUNDLE = PATH_BASE_RESOURCE_DIR + "/Resources";
	
	public static final String PATH_RESOURCE_DIR_FULL = "/" + PATH_BASE_RESOURCE_DIR;
	
	public static final String PATH_RESOURCE_HELP_DIR = PATH_BASE_RESOURCE_DIR + "/help";

	public static final String PATH_RESOURCE_IMAGE_DIR = PATH_RESOURCE_DIR_FULL + "/images";

	public static final String TEXT_NEW_LINE = System.getProperty("line.separator");
}