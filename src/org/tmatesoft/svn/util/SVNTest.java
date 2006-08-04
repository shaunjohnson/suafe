/*
 * ====================================================================
 * Copyright (c) 2004-2006 TMate Software Ltd.  All rights reserved.
 *
 * This software is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://tmate.org/svn/license.html.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 * ====================================================================
 */
package org.tmatesoft.svn.util;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.SVNFileUtil;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;


/**
 * @version 1.0
 * @author  TMate Software Ltd.
 */
public class SVNTest {

    public static void main(String[] args) throws SVNException {
        
        SVNRepositoryFactoryImpl.setup();

        SVNURL url = SVNURL.parseURIEncoded("svn+ssh://svn.tmate.org/home/httpd/svn/repos/jsvn/");
        SVNRepository repos = SVNRepositoryFactory.create(url);

        String userName = "alex";
        File keyFile = new File("c:/id2.dsa");
        ISVNAuthenticationManager authManager = new BasicAuthenticationManager(userName, keyFile, null, 22);
        repos.setAuthenticationManager(authManager);
        
        System.out.println(repos.info("/tags", -1));
        System.exit(0);
        
        
        url = SVNURL.parseURIEncoded("svn://localhost/basic");
        SVNURL url2 = SVNURL.parseURIEncoded("svn://localhost:3690/basic");
        File dstPath = new File("c:/test/svn/wc/basic");
        SVNFileUtil.deleteAll(dstPath, true);
        
        SVNClientManager manager = SVNClientManager.newInstance();

        manager.getUpdateClient().doCheckout(url2, dstPath, SVNRevision.UNDEFINED, SVNRevision.HEAD, true);
        System.out.println("checked out");
        manager.getUpdateClient().doRelocate(dstPath, url2, url, true);
        System.out.println("relocated");
        manager.getUpdateClient().doRelocate(dstPath, url2, url, true);
        System.out.println("relocated again");
        /*
        
        System.out.println(SVNURL.create("svn", null, "localhost", -1, "path", true));
        System.out.println(SVNURL.create("svn", null, "localhost", 3690, "path", true));
        System.out.println(SVNURL.create("svn", null, "localhost", 3691, "path", true));
        System.out.println(SVNURL.parseURIEncoded("svn://localhost/path"));
        System.out.println(SVNURL.parseURIEncoded("svn://localhost:3690/path"));
        System.out.println(SVNURL.parseURIEncoded("svn://localhost:3691/path"));
        
        System.out.println(SVNURL.create("svn", null, "localhost", 3690, "path", true).hasPort());
        
        System.exit(0);
        */
    }

}
