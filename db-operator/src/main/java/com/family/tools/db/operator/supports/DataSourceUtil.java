package com.family.tools.db.operator.supports;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.family.tools.db.operator.bean.DbConfBean;
import com.family.tools.db.operator.bean.DbMsgBean;

/**
 * 数据源操作
 * 
 * @date Jul 4, 2013 11:32:13 AM
 * @author wuzl
 * @comment
 */
public class DataSourceUtil {
	private static Log log = LogFactory.getLog(DataSourceUtil.class);
	private static Map<String, BasicDataSource> DATASOURCES = new ConcurrentHashMap<String, BasicDataSource>();
	private static Pattern pattern=Pattern.compile(".*/(.*)\\?.*");
	/**
	 * 生成数据源
	 * 
	 * @param bean
	 * @return
	 * @date Jul 4, 2013 11:33:28 AM
	 * @author wuzl
	 * @throws Exception
	 * @comment
	 */
	public static DataSource createDataSource(DbConfBean bean) throws Exception {
		String key = bean.getUrl() + "#" + bean.getUserName();
		BasicDataSource dataSource = DATASOURCES.get(key);
		if (dataSource != null) {
			return dataSource;
		}
		synchronized (DataSourceUtil.class) {
			dataSource = DATASOURCES.get(key);
			if (dataSource != null) {
				return dataSource;
			}
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(bean.getDriverClassName());
			dataSource.setUrl(bean.getUrl());
			dataSource.setUsername(bean.getUserName());
			dataSource.setPassword(bean.getPassword());
			dataSource.setInitialSize(1);
			dataSource.setMaxWait(300);
			DATASOURCES.put(key, dataSource);
			log.info("新生成一个【" + key + "】datasource");
		}
		return dataSource;
	}

	/**
	 * 从数据源中获取配置
	 * 
	 * @param dataSource
	 * @return
	 */
	public static DbMsgBean getDbConf(DataSource dataSource) {
		DbMsgBean bean = new DbMsgBean();
		if(dataSource instanceof DynamicDataSource){
			dataSource=DataSourceContextHolder.getDataSourceContextHolder();
		}
		BasicDataSource basicDataSource=(BasicDataSource)dataSource;
		bean.setUserName(basicDataSource.getUsername());
		bean.setPassword(basicDataSource.getPassword());
		String url=basicDataSource.getUrl();
		bean.setUrl(url);
		Matcher matcher=pattern.matcher(url);
		if(matcher.find()){
			bean.setDbName(matcher.group(1));
		}
		return bean;
	}
	
	/**
	 * 关闭一个数据源
	 */
	public static void closeDataSource() {
		DataSource nowDataSource = DataSourceContextHolder
				.getDataSourceContextHolder();
		if (nowDataSource != null && nowDataSource instanceof BasicDataSource) {
			BasicDataSource basicDataSource = (BasicDataSource) nowDataSource;
			String key = basicDataSource.getUrl() + "#"
					+ basicDataSource.getUsername();
			BasicDataSource dataSource = DATASOURCES.remove(key);
			if (dataSource != null) {
				try {
					dataSource.close();
				} catch (SQLException e) {
					log.info("关闭一个【" + key + "】datasource失败：" + e.getMessage());
				}
				log.info("成功关闭一个【" + key + "】datasource");
			}
		}

	}
}
