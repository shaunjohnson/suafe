package org.xiaoniu.suafe.reports;

import org.xiaoniu.suafe.exceptions.ApplicationException;

public interface GenericReport {
	public String generate() throws ApplicationException;
}
