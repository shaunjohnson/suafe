package org.xiaoniu.suafe.reports;

import java.text.DateFormat;
import java.util.Date;

import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;

public class StatisticsReport implements GenericReport {

	public String generate() throws ApplicationException {
		StringBuffer report = new StringBuffer();
		
		report.append(ResourceUtil.getString("reports.header"));
		report.append("<head>");
		report.append("<title>" + ResourceUtil.getString("statisticsreport.title") + "</title>");
		report.append(ResourceUtil.getString("reports.contenttype"));
		report.append("</head><body>");
		report.append("<h1>" + ResourceUtil.getString("statisticsreport.title") + "</h1>");
		
		report.append("<h2>" + ResourceUtil.getString("statisticsreport.users") + "</h2>");
		report.append("<p>" + Document.getUsers().size() + " users</p>");
		
		report.append("<h2>" + ResourceUtil.getString("statisticsreport.groups") + "</h2>");
		report.append("<p>" + Document.getGroups().size() + " groups</p>");
		
		report.append("<h2>" + ResourceUtil.getString("statisticsreport.repositories") + "</h2>");
		report.append("<p>" + Document.getRepositories().size() + " repositories</p>");
		
		report.append("<h2>" + ResourceUtil.getString("statisticsreport.rules") + "</h2>");
		report.append("<p>" + Document.getAccessRules().size() + " access rules</p>");
		
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		Object[] args = new Object[3];
		args[0] = df.format(new Date());
		args[1] = ResourceUtil.getString("application.url");
		args[2] = ResourceUtil.getString("application.nameversion");
		
		report.append(ResourceUtil.getFormattedString("reports.footer", args));
		
		report.append("</body></html>");
		
		return report.toString();
	}
}
