package org.xiaoniu.suafe.beans;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.xiaoniu.suafe.dialogs.DialogUtil;
import org.xiaoniu.suafe.dialogs.SubversionLoginDialog;

/**
 * @author spjohnso
 */
public class Subversion {
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int CANCEL = 2;
	public static final int RETRY = 3;
	
	private String url;
	
	private String userName;
	
	private String password;
	
	private SVNRepository repository;
	
	private Component parentComponent;
	
	private boolean loginCancel;
	
	public Subversion(Component parentComponent) {
		super(); 
		
		this.url = null;
		this.userName = null;
		this.password = null;
		this.parentComponent = parentComponent;
		this.loginCancel = false;
		
		DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
	}
    
    public void connect() {
    	int tries = 2;
    	while(_connect() == RETRY && tries-- > 0);
    }
    
    public void setLoginCancel(boolean loginCancel) {
    	this.loginCancel = loginCancel;
    }
    
	private int _connect() {
		JDialog dialog = new SubversionLoginDialog(this);
		DialogUtil.center(dialog);
		dialog.setVisible(true);
		
		if (loginCancel == true) {
			repository = null;
			return CANCEL;
		}
		
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        	
			if (userName != null && userName.length() > 0 && password != null && password.length() > 0) {
				//File keyFile = new File("C:\\keyFile");
				
				//ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(keyFile, userName, password);
				ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password);
				repository.setAuthenticationManager(authManager);
			}
        } 
		catch (SVNException svne) {
            //JOptionPane.showMessageDialog(parentComponent, "Error connecting to the Subversion. URL may be malformed.", "Error", JOptionPane.ERROR_MESSAGE);
			int reponse = JOptionPane.showConfirmDialog(parentComponent, 
					"Error connecting to the Subversion. URL may be malformed. Retry?", 
					"Error", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE);
            
            return (reponse == JOptionPane.YES_OPTION) ? RETRY : FAIL;
        }

        try {
            SVNNodeKind nodeKind = repository.checkPath("", -1);
            
            if (nodeKind == SVNNodeKind.NONE) {
            	//JOptionPane.showMessageDialog(parentComponent, "There is no entry at '" + url + "'.", "Error", JOptionPane.ERROR_MESSAGE);
            	repository = null;
            	
    			int reponse = JOptionPane.showConfirmDialog(parentComponent, 
    					"There is no entry at '" + url + "'. Retry?", 
    					"Error", 
    					JOptionPane.YES_NO_OPTION,
    					JOptionPane.ERROR_MESSAGE);
                
                return (reponse == JOptionPane.YES_OPTION) ? RETRY : FAIL;
            	
            } 
            else if (nodeKind == SVNNodeKind.FILE) {
                //JOptionPane.showMessageDialog(parentComponent, "The entry at '" + url + "' is a file while a directory was expected.", "Error", JOptionPane.ERROR_MESSAGE);
                repository = null;

                int reponse = JOptionPane.showConfirmDialog(parentComponent, 
                		"The entry at '" + url + "' is a file while a directory was expected. Retry?", 
    					"Error", 
    					JOptionPane.YES_NO_OPTION,
    					JOptionPane.ERROR_MESSAGE);
                
                return (reponse == JOptionPane.YES_OPTION) ? RETRY : FAIL;
            } 
        } 
        catch (SVNException svne) {
        	if (svne.getErrorMessage().getErrorCode() == SVNErrorCode.RA_NOT_AUTHORIZED) {
        		//JOptionPane.showMessageDialog(parentComponent, "Authentication Failed.", "Error", JOptionPane.ERROR_MESSAGE);
        		repository = null;

                int reponse = JOptionPane.showConfirmDialog(parentComponent, 
                		"Authentication Failed. Retry?", 
    					"Error", 
    					JOptionPane.YES_NO_OPTION,
    					JOptionPane.ERROR_MESSAGE);
                
                return (reponse == JOptionPane.YES_OPTION) ? RETRY : FAIL;
        	}
        	else {
        		//JOptionPane.showMessageDialog(parentComponent, "Unknown error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        		repository = null;

                int reponse = JOptionPane.showConfirmDialog(parentComponent, 
                		"Unknown error occurred. Retry?",
    					"Error", 
    					JOptionPane.YES_NO_OPTION,
    					JOptionPane.ERROR_MESSAGE);
                
                return (reponse == JOptionPane.YES_OPTION) ? RETRY : FAIL;
        	}
        }
        
        return SUCCESS;
	}
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the repository.
	 */
	public SVNRepository getRepository() {
		if (repository == null) {
			connect();
		}
		
		return repository;
	}
}
