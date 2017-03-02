package com.family.tools.db.operator.supports;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.tools.db.operator.bean.DbConfBean;

/**
 * DataSource上下文句柄，通过此类设置需要访问的对应数据源 
 * 
 * @date Jul 2, 2013 5:39:49 PM
 * @author wuzl
 * @comment
 */
public class DataSourceContextHolder {
	private static Log log = LogFactory.getLog(DataSourceContextHolder.class);
	// DataSource上下文，每个线程对应相应的数据源key
	private static final ThreadLocal<String> keyContextHolder = new ThreadLocal<String>();
	// DataSource上下文，每个线程对应相应的数据源
	private static final ThreadLocal< DataSource> dataSourceContextHolder = new ThreadLocal<DataSource>();
	/**
	 * 获取key
	 * @return
	 * @date Jul 2, 2013 5:45:24 PM
	 * @author wuzl
	 * @comment
	 */
	public static String getKeyContextHolder() {
		return keyContextHolder.get();
	}
	/**
	 * 设置key
	 * @param dataSourceType
	 * @date Jul 2, 2013 5:45:33 PM
	 * @author wuzl
	 * @comment
	 */
	public static void setKeyContextHolder(String  dataSourceType) {
		keyContextHolder.set(dataSourceType);
	}
	/**
	 * 获取数据源
	 * @return
	 * @date Jul 2, 2013 5:45:39 PM
	 * @author wuzl
	 * @comment
	 */
	public static DataSource getDataSourceContextHolder() {
		return dataSourceContextHolder.get();
	}
	/**
	 * 设置数据源
	 * @param bean
	 */
	public static void setDataSourceContextHolder(DbConfBean bean){
		try {
			setDataSourceContextHolder(DataSourceUtil.createDataSource(bean));
			log.info("设置当前数据源【"+bean.getKey()+"】成功");
		} catch (Exception e) {
			throw new RuntimeException("设置当前数据源【"+bean.getKey()+"】失败:"+e.getMessage());
		}
	}
	/**
	 * 设置数据源
	 * @param dataSource
	 * @date Jul 2, 2013 5:45:45 PM
	 * @author wuzl
	 * @comment
	 */
	public static void setDataSourceContextHolder(DataSource dataSource) {
		dataSourceContextHolder.set(dataSource);
	}
	public static void clearContextHolder(){
		keyContextHolder.remove(); 
		DataSource dataSource=dataSourceContextHolder.get();
		dataSourceContextHolder.remove();
	}
	
}
