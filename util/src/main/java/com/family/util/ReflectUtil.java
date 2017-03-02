package com.family.util;

import java.lang.reflect.Method;
/**
 * 反射的工具类
 * 
 * @date Jun 19, 2013 1:35:05 AM
 * @author wuzl
 * @comment
 */
public class ReflectUtil {
	/**
	 * 反射获取对象值
	 * @param bean
	 * @param att
	 * @return
	 * @date Jun 19, 2013 1:25:18 AM
	 * @author wuzl
	 * @throws Exception 
	 * @comment
	 */
    public static Object getter(Object bean, String att) throws Exception {
    	String getMethodName=ConfigUtil.changeToGetter(att);
    	try {
            Method method = bean.getClass().getMethod(getMethodName);
            return method.invoke(bean);
        } catch (Exception e) {
        	throw new Exception("通过反射获取"+att+"的值有误请检查类"+bean.getClass().getName()+"是否有【"+getMethodName+"】方法");
        }
    }
 
    /**
     * 反射设置属性值
     * @param bean
     * @param att
     * @param value
     * @param type参数的类型
     * @date Jun 19, 2013 1:25:01 AM
     * @author wuzl
     * @throws Exception 
     * @comment
     */
    public static void setter(Object bean, String att, Object value,
            Class<?> type) throws Exception {
    	String setMethodName=ConfigUtil.changeToSetter(att);
        try {
            Method method = bean.getClass().getMethod(setMethodName, type);
            method.invoke(bean, value);
        } catch (Exception e) {
        	throw new Exception("通过反射设置"+att+"的值有误，请检查类"+bean.getClass().getName()+"是否有【"+setMethodName+"】方法");
        }
    }
    /**
     * 反射调用方法
     * @param obj
     * @param methodName
     * @throws Exception
     * @date Jul 4, 2013 1:58:36 PM
     * @author wuzl
     * @comment
     */
    public static void callMethod(Object obj, String methodName) throws Exception{
    	try {
            Method method = obj.getClass().getMethod(methodName);
            method.invoke(obj);
        } catch (Exception e) {
        	throw new Exception("通过反射调用"+methodName+"的方法失败请检查类"+obj.getClass().getName()+"是否有【"+methodName+"】方法");
        }
    }
    
}
