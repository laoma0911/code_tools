package com.family.util;

public class StringUtil {
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 * @date Nov 3, 2013 1:00:58 PM
	 * @author wuzl
	 * @comment
	 */
	public static boolean isBlank(String str) {
        if (null == str) {
            return true;
        }
        if ("".equals(str.trim())) {
            return true;
        }

        return false;
    }
}
