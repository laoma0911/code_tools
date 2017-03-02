package com.family.util;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * bean与map转化的util
 * 
 * @author wuzl
 * 
 */
public class BeansUtil {

	/**
	 * bean转化成map
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> convertBeanToMap(Object bean) {
		/* 1.如果bean是null直接返回null */
		if (bean == null) {
			return null;
		}
		/* 2.定义返回结果map */
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			/* 3.同过内省获取bean信息 */
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			/* 4.获取bean属性的描述器 */
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			/* 5.遍历bean的属性 */
			for (PropertyDescriptor property : propertyDescriptors) {
				/* 5.1 获取属性名称 */
				String key = property.getName();
				/* 5.2 过滤class方法 */
				if (!key.equals("class")) {
					/* 5.2.1 获取get方法 */
					Method getter = property.getReadMethod();
					/* 5.2.2 获取值 */
					Object value = getter.invoke(bean);
					returnMap.put(key, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("bean[" + bean.getClass().getName()
					+ "]转化为map时出错");
		}
		/* 6.返回结果 */
		return returnMap;
	}
	
	public static void copyBean(Object fromObj,Object toObj) {
		/* 1.如果bean是null直接返回null */
		if (fromObj == null) {
			return;
		}
		try {
			/* 2.同过内省获取bean信息 */
			BeanInfo beanInfo = Introspector.getBeanInfo(fromObj.getClass());
			/* 3.获取bean属性的描述器 */
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			/* 4.遍历bean的属性 */
			for (PropertyDescriptor property : propertyDescriptors) {
				/* 4.1 获取属性名称 */
				String key = property.getName();
				/* 4.2 过滤class方法 */
				if (!key.equals("class")) {
					/* 4.2.1 获取get方法 */
					Method getter = property.getReadMethod();
					/* 4.2.2 获取值 */
					Object value = getter.invoke(fromObj);
					Method write=property.getWriteMethod();
					Method toObjSetMethod=toObj.getClass().getMethod(write.getName(), write.getParameterTypes());
					if(toObjSetMethod!=null){
						toObjSetMethod.invoke(toObj, value);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("bean[" + fromObj.getClass().getName()
					+ "]复制时出错");
		}
	}
	/**
	 * 把bean中的属性加入到dto中 只放入基本类型
	 * 
	 * @param dto
	 * @param bean
	 * @param cover是否覆盖
	 */
	public static void convertBeanToMapIgnoreFormat(Map<String, Object> dto,
			Object bean, boolean cover) {
		if (dto == null) {
			throw new RuntimeException("map不可以为null");
		}
		/* 1.如果bean是null直接返回 */
		if (bean == null) {
			return;
		}
		try {
			/* 2.同过内省获取bean信息 */
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			/* 3.获取bean属性的描述器 */
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			/* 4.遍历bean的属性 */
			for (PropertyDescriptor property : propertyDescriptors) {
				/* 4.1 获取属性名称 */
				String key = property.getName();
				/* 4.2 过滤class方法 */
				if (!key.equals("class")) {
					/* 4.2.1 判断是否基本类型 */
					if (!isBasicType(property.getPropertyType())) {
						continue;
					}
					/* 4.2.2 获取get方法 */
					Method getter = property.getReadMethod();
					/* 4.2.3 获取值 */
					Object value = getter.invoke(bean);
					if (!cover) {
						if (dto.get(key) != null) {
							continue;
						}
					}
					dto.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("bean[" + bean.getClass().getName()
					+ "]转化为map时出错");
		}
	}

	/**
	 * 根据指定class返回一个从map初始的对象 忽略下划线大小写驼峰等
	 * 
	 * @param srcDto
	 * @param objClass
	 * @return
	 */
	public static <T> T convertMapToBeanIgnoreFormat(
			Map<String, Object> srcDto, Class<T> objClass) {
		T obj;
		Map<String, Object> dto = copyMapKeyDelUnderlineHumpAndToLower(srcDto);
		try {
			/* 1.获取对象 */
			obj = objClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(objClass + "没有无参构造函数");
		}
		/* 2.获取内省获取bean信息 */
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(objClass);
			/* 3.获取bean属性的描述器 */
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			/* 4.遍历bean的属性 */
			for (PropertyDescriptor property : propertyDescriptors) {
				/* 4.1 获取属性名称 */
				String key = property.getName();
				/* 4.2 过滤class方法 */
				if (!key.equals("class")) {
					/* 4.3 反射 */
					Method setter = property.getWriteMethod();
					Class cl = property.getPropertyType();
					Object value = dto.get(key);
					if (value != null) {
						try {
							setter.invoke(obj,
									getObejctFromTypeAndValue(value, cl));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						value = dto.get(CodeFormat
								.delUnderlineHumpAndToLower(key));
						if (value != null) {
							try {
								setter.invoke(obj,
										getObejctFromTypeAndValue(value, cl));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
			throw new RuntimeException("map转化为bean[" + objClass + "]时出错");
		}

		return obj;
	}

	/**
	 * 复制个map忽略格式如大小写驼峰下划线等
	 * 
	 * @param dto
	 * @return
	 */
	public static Map<String, Object> copyMapKeyDelUnderlineHumpAndToLower(
			Map<String, Object> dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : dto.entrySet()) {
			resultMap.put(
					CodeFormat.delUnderlineHumpAndToLower(entry.getKey()),
					entry.getValue());
		}
		return resultMap;
	}

	/**
	 * 判断是否基本类型
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isBasicType(Class<?> c) {
		if (c == int.class || Integer.class.equals(c))
			return true;
		else if (c == boolean.class || Boolean.class.equals(c))
			return true;
		else if (c == long.class || Long.class.equals(c))
			return true;
		else if (c == float.class || Float.class.equals(c))
			return true;
		else if (c == double.class || Double.class.equals(c))
			return true;
		else if (c == char.class)
			return true;
		else if (c == byte.class || Byte.class.equals(c))
			return true;
		else if (c == short.class || Short.class.equals(c))
			return true;
		else if (c == String.class)
			return true;
		return false;
	}

	public static Object getObejctFromTypeAndValue(Object obj, Class cl) {
		String s;
		if (cl.isInstance(obj)) {
			return obj;
		}
		try {
			s = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (String.class.equals(cl)) {
			return s;
		} else if (boolean.class.equals(cl) || Boolean.class.equals(cl)) {
			return Boolean.parseBoolean(s);
		} else if (byte.class.equals(cl) || Byte.class.equals(cl)) {
			return Byte.parseByte(s);
		} else if (short.class.equals(cl) || Short.class.equals(cl)) {
			return Short.parseShort(s);
		} else if (int.class.equals(cl) || Integer.class.equals(cl)) {
			return Integer.parseInt(s);
		} else if (long.class.equals(cl) || Long.class.equals(cl)) {
			return Long.parseLong(s);
		} else if (float.class.equals(cl) || Float.class.equals(cl)) {
			return Float.parseFloat(s);
		} else if (double.class.equals(cl) || Double.class.equals(cl)) {
			return Double.parseDouble(s);
		} else {
			return null;
		}
	}
}
