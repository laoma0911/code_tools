package com.family.util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.family.util.constaints.Constants;

public class BaseUtil {
	/**
	 * 格式化数据为两位小数5舍6入
	 * 
	 * @param d
	 * @return
	 * @author wuzl
	 */
	public static String roundNum(Double d) {
		if (d == null) {
			return "0.00";
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		String str = nf.format(d);
		return str;
	}
	/**
     * 格式化数据为两位小数4舍5入
     * 
     * @param d
     * @return
     * @author wuzl
     */
    public static String roundNumHalfUp(Double d) {
        if (d == null) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        String str = nf.format(d);
        return str;
    }
    /**
     * 格式化数据为两位小数
     * 
     * @param d
     * @return
     * @author wuzl
     */
    public static String roundNum(String d) {
        if (d == null) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        String str = nf.format(Double.parseDouble(d));
        return str;
    }
    /**
     * 格式化数据为两位小数
     * 
     * @param d
     * @return
     * @author wuzl
     */
    public static String roundNumHalfUp(String d) {
        if (d == null) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        String str = nf.format(Double.parseDouble(d));
        return str;
    }
	/**
	 * 获取指定位数的随机数
	 * 
	 * @param length
	 * @return
	 * @date Apr 23, 2012 9:39:39 AM
	 * @author wuzl
	 * @comment
	 */
	public static long getRanDom(int length) {
		length = length - 1;
		return (long)Math.pow(10, length)+(long)(Math.random()*(Math.pow(10, length+1)-Math.pow(10, length)));
	}

	public static String getNowData() {
		SimpleDateFormat sdf = new SimpleDateFormat(
				Constants.DateMsg.DATE_FORMAT);
		return sdf.format(new Date());
	}
	/**
     * 转换数字为中文
     * @param num
     * @return
     * @author wuzl
     */
    public static String changeNumToChineseNum(int num){
        if(num>Integer.MAX_VALUE){
            throw new RuntimeException("数字过大，无法转换");
        }
        if(num<0){
            throw new RuntimeException("数字只可以是正整数");
        }
        
        String result= changeNumToChineseNum(num+"");
        //去掉多余的零
        result=result.replaceAll("零零+", "零");
        //去掉结尾的零
        result=result.replaceAll("零+$", "");
        return result;
    }
    /**
     * 具体的转换操作
     * @param num
     * @return
     * @author wuzl
     */
    private static String changeNumToChineseNum(String num){
        int length=num.length();
        /*1.判断是否最后一位*/
        if(length==1){//单个数字
            return singleNumChange(num);
        }
        /*2.获取中文位数*/
        String chineseFigures=getChineseFigures(length);
        /*3.获取当前最高的一位*/
        String highFiguresNum=num.substring(0, 1);
        //如果是0
        if(highFiguresNum.equals("0")){
            chineseFigures="";
        }
        /*4.递归查询*/
        return singleNumChange(highFiguresNum)+chineseFigures+changeNumToChineseNum(num.substring(1));
    }
    /**
     * 转化单位数字
     * @param num
     * @return
     * @author wuzl
     */
    public static String singleNumChange(String num){
        String chineseNum="0";
        switch (Integer.parseInt(num)) {
        case 0:
            chineseNum="零";return chineseNum;
        case 1:
            chineseNum="一";return chineseNum;
        case 2:
            chineseNum="二";return chineseNum;
        case 3:
            chineseNum="三";return chineseNum;
        case 4:
            chineseNum="四";return chineseNum;
        case 5:
            chineseNum="五";return chineseNum;
        case 6:
            chineseNum="六";return chineseNum;
        case 7:
            chineseNum="七";return chineseNum;
        case 8:
            chineseNum="八";return chineseNum;
        case 9:
            chineseNum="九";return chineseNum;
        }
        throw new RuntimeException("数字格式不正确");
    }
    /**
     * 转化单位数字
     * @param num
     * @return
     * @author wuzl
     */
    public static String singleNumChange(int num){
        if(num>=10||num<0){
            throw new RuntimeException("只能转换一位正数");
        }
        String chineseNum="0";
        switch (num) {
        case 0:
            chineseNum="零";break;
        case 1:
            chineseNum="一";break;
        case 2:
            chineseNum="二";break;
        case 3:
            chineseNum="三";break;
        case 4:
            chineseNum="四";break;
        case 5:
            chineseNum="五";break;
        case 6:
            chineseNum="六";break;
        case 7:
            chineseNum="七";break;
        case 8:
            chineseNum="八";break;
        case 9:
            chineseNum="九";break;
        }
        return chineseNum;
    }
    /**
     * 获取中文的位数
     * @param length
     * @return
     * @author wuzl
     */
    private static String getChineseFigures(int length){
        if(length<2||length>11){
            throw new RuntimeException("目前不支持这么大的位数");
        }
        switch (length) {
            case 2:
                return "十";
            case 3:
                return "百";
            case 4:
                return "千";
            case 5:
                return "万";
            case 6:
                return "十万";
            case 7:
                return "百万";
            case 8:
                return "千万";
            case 9:
                return "亿";
            case 10:
                return "十亿";
            case 11:
                return "百亿";
        }
        throw new RuntimeException("传入位数不正确");
    }
	public static void main(String[] args) {
//		long random = BaseUtil.getRanDom(4);
//		while (true) {
//			System.out.println(random);
//			random = BaseUtil.getRanDom(4);
//			if (random < 1000) {
//				System.out.println(">");
//			}
//		}
		System.out.println(BaseUtil.roundNum(12.005));
		System.out.println(BaseUtil.roundNum(12.006));
		System.out.println(BaseUtil.roundNumHalfUp(12.004));
		System.out.println(BaseUtil.roundNumHalfUp(12.005));
		System.out.println(BaseUtil.roundNumHalfUp(12.006));
	}
	

	
}
