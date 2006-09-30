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

package org.xiaoniu.suafe.dialogs;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 * Represents a node in the Subversion path tree.
 * 
 * @author Shaun Johnson
 */
public class SvnTreeNode implements MutableTreeNode {
	
	private MutableTreeNode parent;
	
	private Vector<MutableTreeNode> children;
	
	private Object userObject;
	
	private SVNRepository repository;
	
	public SvnTreeNode(Object userObject, SVNRepository repository) {
		super();
		
		this.parent = null;
		this.children = null;
		this.userObject = userObject;
		this.repository = repository;
	}

	public void insert(MutableTreeNode child, int index) {
		getChildren().add(index, child);		
	}

	public void remove(int index) {
		getChildren().remove(index);		
	}

	public void remove(MutableTreeNode node) {
		getChildren().remove(node);		
	}

	public void setUserObject(Object object) {
		userObject = object;
	}
	
	public Object getUserObject() {
		return userObject;
	}

	public void removeFromParent() {
		if (parent != null) {
			parent.remove(this);
			parent = null;
		}
	}

	public void setParent(MutableTreeNode newParent) {
		removeFromParent();
		parent = newParent;
	}

	public TreeNode getChildAt(int childIndex) {
		return (TreeNode)getChildren().get(childIndex);
	}

	public int getChildCount() {
		return getChildren().size();
	}

	public TreeNode getParent() {
		return parent;
	}

	public int getIndex(TreeNode node) {
		return getChildren().indexOf(node);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return getChildren().size() == 0;
	}

	public Enumeration children() {
		return getChildren().elements();
	}
	
	public void add(MutableTreeNode newNode) {
		getChildren().add(newNode);
		newNode.setParent(this);
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public String toString() {
		return (userObject == null) ? null : userObject.toString();
	}
	
	public String getPath() {
		StringBuffer path = new StringBuffer();
		
		if (isRoot()) {
			path.append("/");
		}
		else {
			path.append("/" + userObject + "/");
		}
		
		SvnTreeNode node = (SvnTreeNode)parent;
		
		while (node != null && node.isRoot() == false) {
			path.insert(0, "/" + (String)node.getUserObject());
			
			node = (SvnTreeNode)node.getParent();			
		}
		
		return path.toString();
	}
	
	@SuppressWarnings("unchecked")
	private Vector<MutableTreeNode> getChildren() {	
		if (children == null) {
			children = new Vector<MutableTreeNode>();
			
			if (repository != null) {
				try {
					List<SVNDirEntry> entries = new Vector(repository.getDir(getPath(), -1, null, (Collection) null));
					
					Collections.sort(entries);
					
					for(SVNDirEntry entry : entries) {
						
						if (entry.getKind() == SVNNodeKind.DIR) {
							SvnTreeNode newNode = new SvnTreeNode(entry.getName(), repository);
							children.add(newNode);
							newNode.setParent(this);
						}
					}
				}
				catch (SVNException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
		return children;
	}
}
