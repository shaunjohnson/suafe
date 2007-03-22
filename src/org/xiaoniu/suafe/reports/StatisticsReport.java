package org.xiaoniu.suafe.reports;

import java.text.DateFormat;
import java.util.Date;

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class StatisticsReport implements GenericReport {

	public String generate() throws ApplicationException {
		StringBuffer report = new StringBuffer();
		
		report.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
		report.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		report.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">");
		report.append("<head>");
		report.append("<title>Statistics Report</title>");
//		report.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />");
		report.append("</head><body>");
		report.append("<h1>Statistics Report</h1>");
		
		report.append("<h2>Users</h2>");
		report.append("<p>" + Document.getUsers().size() + " users</p>");
		
		report.append("<h2>Groups</h2>");
		report.append("<p>" + Document.getGroups().size() + " groups</p>");
		
		report.append("<h2>Repositories</h2>");
		report.append("<p>" + Document.getRepositories().size() + " repositories</p>");
		
		report.append("<h2>Access Rules</h2>");
		report.append("<p>" + Document.getAccessRules().size() + " access rules</p>");
		
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		report.append("<hr></hr>");
		report.append("<p>Generated " + df.format(new Date()) + " using <a href=\"" +
				ResourceUtil.getString("application.url") + "\">" +
				ResourceUtil.getString("application.nameversion") + "</a><br></br>");
		report.append("Valid XHTML 1.0 Strict</p>");
		
		report.append("</body></html>");
		
		return report.toString();
	}
}
