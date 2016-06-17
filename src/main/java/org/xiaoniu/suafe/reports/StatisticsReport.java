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
package org.xiaoniu.suafe.reports;

import org.xiaoniu.suafe.api.beans.Document;
import org.xiaoniu.suafe.exceptions.AppException;
import org.xiaoniu.suafe.reports.helpers.StatisticsReportHelper;
import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.util.Date;

/**
 * Statistics report generator.
 *
 * @author Shaun Johnson
 */
public final class StatisticsReport extends GenericReport {
    private final StatisticsReportHelper helper;

    /**
     * Constructor.
     *
     * @param document Document used as report source.
     */
    public StatisticsReport(@Nonnull final Document document) {
        super(document);

        helper = new StatisticsReportHelper(document);
    }

    /**
     * Generate report.
     *
     * @return Report HTML contents
     */
    @Override
    public String generate() throws AppException {
        final StringBuilder report = new StringBuilder();

        report.append(ResourceUtil.getString("reports.header"));
        report.append("<head>");
        report.append("<title>").append(ResourceUtil.getString("statisticsreport.title")).append("</title>");
        report.append(ResourceUtil.getString("reports.contenttype"));
        report.append("</head><body>");
        report.append("<h1>").append(ResourceUtil.getString("statisticsreport.title")).append("</h1>");

        report.append("<h2>").append(ResourceUtil.getString("statisticsreport.users")).append("</h2>");
        report.append("<p>").append(helper.getUserCount()).append(" users</p>");

        report.append("<h2>").append(ResourceUtil.getString("statisticsreport.groups")).append("</h2>");
        report.append("<p>").append(helper.getGroupCount()).append(" groups</p>");

        report.append("<h2>").append(ResourceUtil.getString("statisticsreport.repositories")).append("</h2>");
        report.append("<p>").append(helper.getRepoStatistics().getCount()).append(" repositories</p>");

        report.append("<h2>").append(ResourceUtil.getString("statisticsreport.rules")).append("</h2>");
        report.append("<p>").append(helper.getAccessRuleCount()).append(" access rules</p>");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        final Object[] args = new Object[3];
        args[0] = df.format(new Date());
        args[1] = ResourceUtil.getString("application.url");
        args[2] = ResourceUtil.getString("application.nameversion");

        report.append(ResourceUtil.getFormattedString("reports.footer", args));

        report.append("</body></html>");

        return report.toString();
    }
}
