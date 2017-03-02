package com.family.util.constaints;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用静态变量类
 * @author dell
 *
 */
public class Constants {
	public static class DateMsg{
		//时间格式化
		public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
		//简单时间只有年日月格式化
		public static final String SIMPLE_DATE_FORMAT="yyyy-MM-dd";
		//时间格式正则验证yyyy-MM-dd
		public static final String DATE_REGEX="\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
		//简单时间格式正则验证yyyy-MM-dd HH:mm:ss
		public static final String SIMPLE_DATE_REGEX="\\d{4}-\\d{1,2}-\\d{1,2}";
	}
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_ENCODED="UTF-8";
	//文件分隔符
	public static final String FILE_SEPERATOR ="/";
	//linux的home路径
	public static final String LINUX_HONE_PATH="/home/";
}
