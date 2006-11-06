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

package org.xiaoniu.suafe.resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

/**
 * Utility class used to access application resources.
 * 
 * @author Shaun Johnson
 */
public class ResourceUtil {
	
	protected static ResourceBundle bundle;
	
	protected static ResourceBundle getBundle() {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("org/xiaoniu/suafe/resources/Resources");
		}
		
		return bundle;
	}
	
	public static String getString(String name) {		
		return getBundle().getString(name);
	}
	
	public static String getFormattedString(String name, Object[] args) {
		return MessageFormat.format(getBundle().getString(name), args);
	}
	
	public static String getFormattedString(String name, String arg) {
		Object[] args = new Object[1];
		
		args[0] = arg;
		
		return MessageFormat.format(getBundle().getString(name), args);
	}
	
	public static final Image serverImage = Toolkit.getDefaultToolkit().getImage(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Server16.gif"));
	
	public static final ImageIcon aboutIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/About16.gif"));

	public static final ImageIcon accessRuleIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Reversed.gif"));
	
	public static final ImageIcon fullSizeAccessRuleIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Reversed.gif"));
	
	public static final ImageIcon addAccessRuleIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/AccessRuleAdd.gif"));
	
	public static final ImageIcon addGroupIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/GroupAdd.gif"));
	
	public static final ImageIcon addRemoveMembersIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Preferences16.gif"));
	
	public static final ImageIcon addUserIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/UserAdd.gif"));
	
	public static final ImageIcon changeMembershipIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Preferences16.gif"));
	
	public static final ImageIcon cloneGroupIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Clone.gif"));
	
	public static final ImageIcon cloneUserIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Clone.gif"));
	
	public static final ImageIcon deleteAccessRuleIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/AccessRuleDelete.gif"));
	
	public static final ImageIcon deleteGroupIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/GroupDelete.gif"));
	
	public static final ImageIcon deleteUserIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/UserDelete.gif"));
	
	public static final ImageIcon denyAccessIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/DenyAccess16.gif"));
	
	public static final ImageIcon editAccessRuleIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/AccessRuleEdit.gif"));
	
	public static final ImageIcon editGroupIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/GroupEdit.gif"));
	
	public static final ImageIcon editUserIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/UserEdit.gif"));
	
	public static final ImageIcon fullSizeGroupIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/MorePeople.gif"));
	
	public static final ImageIcon fullSizeUserIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/PlainPeople.gif"));
	
	public static final ImageIcon groupIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/ListGroup.gif"));
	
	public static final ImageIcon helpIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Help16.gif"));
	
	public static final ImageIcon licenseIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/History16.gif"));
	
	public static final ImageIcon newFileIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/New16.gif"));
	
	public static final ImageIcon openFileIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Open16.gif"));
	
	public static final ImageIcon pathIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Path16.gif"));
	
	public static final ImageIcon pathEditIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/PathEdit.gif"));
	
	public static final ImageIcon pathDeleteIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/PathDelete.gif"));
	
	public static final ImageIcon previewIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Preview.gif"));
	
	public static final ImageIcon printIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Print16.gif"));
	
	public static final ImageIcon readOnlyIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/ReadOnly16.gif"));
	
	public static final ImageIcon readWriteIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/ReadWrite16.gif"));
	
	public static final ImageIcon repositoryIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Repository16.gif"));
	
	public static final ImageIcon repositoryEditIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/RepositoryEdit.gif"));
	
	public static final ImageIcon repositoryDeleteIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/RepositoryDelete.gif"));
	
	public static final ImageIcon saveFileIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/Save16.gif"));
	
	public static final ImageIcon saveFileAsIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/toolbarButtonGraphics/general/SaveAs16.gif"));

	public static final ImageIcon serverIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/Server16.gif"));
	
	public static final ImageIcon userIcon = new ImageIcon(ResourceUtil.class.getResource("/org/xiaoniu/suafe/resources/ListUser.gif"));
}