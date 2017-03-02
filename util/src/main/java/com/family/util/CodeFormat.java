package com.family.util;


import java.util.regex.Pattern;

public class CodeFormat {
	private static final Pattern HUMP_PATTERN = Pattern.compile("[a-z]+?([A-Z])");
	/**
	 * 去下划线和驼峰并且小写
	 * @param srcCode
	 * @return
	 */
	public static String delUnderlineHumpAndToLower(String srcCode){
		return srcCode.replace("_", "").toLowerCase();
	}
}
