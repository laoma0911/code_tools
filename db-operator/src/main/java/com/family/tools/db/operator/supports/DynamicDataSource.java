package com.family.tools.db.operator.supports;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 动态数据源
 * 重写determineTargetDataSource方法，
 * 	如果DataSourceContextHolder中指定了数据源，这使用该数据源
 * 	否则在配置中查询，如果查找不到提示错误
 * @date Jul 2, 2013 5:47:42 PM
 * @author wuzl
 * @comment
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	public static final String BASE_DATA_SOURCE="default";
	private static Log log = LogFactory.getLog(DynamicDataSource.class);
	private Map<Object, Object> _targetDataSources;
	@Override
	protected Object determineCurrentLookupKey() {
		String key=DataSourceContextHolder.getKeyContextHolder();
		if(key==null){
			key=BASE_DATA_SOURCE;
		}
		return key;
	}
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this._targetDataSources = targetDataSources;
		super.setTargetDataSources(targetDataSources);
	}
	/**
	 * 动态添加一个数据源，添加成功后可以用key直接获取
	 * (暂没有使用，可以配合DataSourceContextHolder和determineTargetDataSource来配置)
	 * @param key
	 * @param dataSource
	 * @date Jul 3, 2013 5:05:06 PM
	 * @author wuzl
	 * @throws Exception 
	 * @comment
	 */
	public void addTargetDataSource(String key, DataSource dataSource) throws Exception{
		this._targetDataSources.put(key, dataSource);
		this.setTargetDataSources(_targetDataSources);
	}
	
	public DataSource determineTargetDataSource() {
		DataSource dataSource=DataSourceContextHolder.getDataSourceContextHolder();
		if(dataSource!=null){//使用动态指定的数据源
			log.info("调用动态生成的数据源");
			return dataSource;
		}
		Object lookupKey = determineCurrentLookupKey();
		dataSource =(DataSource) _targetDataSources.get(lookupKey);
		if (dataSource == null) {//找不到数据源提示错误
			throw new IllegalStateException("获取不到指定的数据源连接 [" + lookupKey + "]");
		}
		log.error("调用指定的数据源【"+lookupKey+"】");
		return dataSource;
	}
}
