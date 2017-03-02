package com.family.util;

public class RandomUtil {
    /**
     * 获取指定位数的随机数
     * @param length
     * @return
     * @author wuzl
     */
    public static long getRanDom(int length){
        length=length-1;
        return (long)Math.pow(10, length)+(long)(Math.random()*(Math.pow(10, length+1)-Math.pow(10, length)));
    }
}
