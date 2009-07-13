package org.xiaoniu.suafe.utils;

public final class StringUtils {
	public static boolean isBlank(String value) {
		if (value == null) {
			return true;
		}
		else if (value.trim().length() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isNotBlank(String value) {
		if (value == null) {
			return false;
		}
		else if (value.trim().length() == 0) {
			return false;
		}
		else {
			return true;
		}
	}
}
