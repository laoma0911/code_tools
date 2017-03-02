package com.family.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 获取数月前的那天
	 * @param endDate
	 * @param monthNum
	 * @return
	 * @date Aug 30, 2013 4:04:58 PM
	 * @author wuzl
	 * @comment
	 */
	public static Date anyMonthBefore(Date endDate,int monthNum){
		Calendar cal =Calendar.getInstance(); 
		cal.setTime(endDate);
		cal.add(Calendar.MONTH,-monthNum);
		return cal.getTime();
	}
	/**
     * 获取当前日期和时间的中文显示
     * 
     * @return
     */
    public static String nowCn() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH时:mm分:ss秒").format(new Date());
    }

    /**
     * 获取当前日期和时间的英文显示
     * 
     * @return
     */
    public static String nowEn() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取当前日期的中文显示
     * 
     * @return
     */
    public static String nowDateCn() {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
    }

    /**
     * 获取当前日期的英文显示
     * 
     * @return
     */
    public static String nowDateEn() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    
    /**
     * 获取当前时间的中文显示
     * 
     * @return
     */
    public static String nowTimeCn() {
        return new SimpleDateFormat("HH时:mm分:ss秒").format(new Date());
    }

    /**
     * 获取当前时间的英文显示
     * 
     * @return
     */
    public static String nowTimeEn() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * 获取当前时间的英文显示
     * 
     * @return
     */
    public static String getNowDate() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
    /**
     * 获取当前日期的指定格式
     * @param format
     * @return
     * @author wuzl
     */
    public static String nowDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }
    /**
     * 获取日期的指定格式
     * @param format
     * @param date
     * @return
     * @author wuzl
     */
    public static String formatDate(String format,Date date) {
        return new SimpleDateFormat(format).format(date);
    }
    /**
     * 获取当前月第一天
     * @param format
     * @return
     * @author wuzl
     */
    public static String getCurrentMonthFirstDay(String format){
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);
        return  new SimpleDateFormat(format).format(c.getTime());
    }
    /**
     * 获取当前月第一天的中文显示
     * @return
     * @author wuzl
     */
    public static String getCurrentMonthFirstDay(){
        return  getCurrentMonthFirstDay("yyyy年MM月dd日");
    }
    /**
     * 获取当前月最后一天
     * @param format
     * @return
     * @author wuzl
     */
    public static String getCurrentMonthLastDay(String format){
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return  new SimpleDateFormat(format).format(ca.getTime());
    }
    /**
     * 字符串转成日期
     * @param dateString
     * @param format
     * @retu
     * @throws ParseException
     * @author wuzl
     */
    public static Date getDateFromStringByFormat(String dateString,String format) throws Exception{
        SimpleDateFormat sdf =   new SimpleDateFormat(format);
        return sdf.parse( dateString);
    }
    /**
     * 返回天数
     * @param date
     * @return
     * @author wuzl
     */
    public  static  int  getDay(java.util.Date  date)  {  
        java.util.Calendar  c  =  java.util.Calendar.getInstance();  
        c.setTime(date);  
        return  c.get(java.util.Calendar.DAY_OF_MONTH);  
    }
    /**
     * 获取下月第一天
     * @param format
     * @return
     * @author wuzl
     */
    public static String getNextMonthFirstDay(String format){
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH,1);
        return  simpleDateFormat.format(c.getTime());
    }
   /**
    * 获取某月下月的第一天时间 输入格式为yyyyMM
    * @param date
    * @param format
    * @return
    */
    public static String getSomeMonthNextMonthFirstDay(String date,String format){
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();    
        c.set(Calendar.YEAR , Integer.parseInt(date.substring(0, 4)));
        c.set(Calendar.MONTH,Integer.parseInt(date.substring(4, 6)));
        c.set(Calendar.DAY_OF_MONTH,1);
        return  simpleDateFormat.format(c.getTime());
    }
    /**
     * 获取下一天
     * @param format
     * @return
     */
    public static String getNextDay(String format){
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.DAY_OF_MONTH, 1);
        return  simpleDateFormat.format(c.getTime());
    }
    
   /**
    * 获取某天的下一天 时间格式为YYYYMMdd
    * @param date
    * @param format
    * @return
    */
    public static String getSomeDayNextDay(String date,String format){
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();    
        c.set(Calendar.YEAR , Integer.parseInt(date.substring(0, 4)));
        c.set(Calendar.MONTH,Integer.parseInt(date.substring(4, 6))-1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6, 8))+1);
        return  simpleDateFormat.format(c.getTime());
    }
    /**
     * 获取上周一的日期
     * @return
     */
    public static Date getPreWeekMondayTime(){
    	Calendar c = Calendar.getInstance();    
        c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.WEEK_OF_YEAR, -1); 
		c.set(Calendar.DAY_OF_WEEK, 2);
		return c.getTime();
    }
    /**
     * 获取上周一的日期
     * @return
     */
    public static String getPreWeekMondayTimeStr(){
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String dateString=simpleDateFormat.format(getPreWeekMondayTime().getTime());
		return dateString;
    }
    /**
     * 判断是否是每月的第一天
     * @return
     */
	public static boolean isEveryMonthFirstDay() {
		return nowDateEn().endsWith("01");
	}
	public static void main(String[] args) {
//		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();    
//        c.set(Calendar.DAY_OF_MONTH,1);
//      		System.out.println(simpleDateFormat.format(c.getTime()));
//        System.out.println(c.getTime().getTime()/1000);
//        c.add(Calendar.MONTH, -1);
//        System.out.println(c.getTime().getTime()/1000);
//		System.out.println(simpleDateFormat.format(c.getTime()));
		try {
	        Calendar c = Calendar.getInstance();    
	        c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.DAY_OF_WEEK, 2);
			c.add(Calendar.WEEK_OF_YEAR, -1);
//			c.set(Calendar.DATE,1);
//			c.set(Calendar.MONTH, 0);
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HHmmss");
			long time=(c.getTime().getTime()-System.currentTimeMillis())/1000;
			System.out.println(time);
			System.out.println((time-6*60*60*24)/(60*60));
			String dateString=simpleDateFormat.format(c.getTime());
			System.out.println(dateString);
			Date date=simpleDateFormat.parse(dateString);
			System.out.println(date.getTime()/1000);
			System.out.println(formatDate("yyyyMMddHHmmss",date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
