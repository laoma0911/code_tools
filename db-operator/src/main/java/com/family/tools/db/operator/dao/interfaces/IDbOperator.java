package com.family.tools.db.operator.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.family.tools.db.operator.bean.ColumnBean;

/**
 * 数据库操作结果
 * @author wuzl
 *
 */
public interface IDbOperator {
	/**
	 * 测试数据库连接是否正常
	 * @return
	 * @date Jul 4, 2013 9:28:35 AM
	 * @author wuzl
	 * @comment
	 */
	public  boolean testConnect();
	/**
	 * 查询数据库中用户建的表
	 * @param dbName
	 * @return table_name
	 */
	public  List<Map<String,String>> selectDBTableList(String dbName);
	/**
	 * 查询表的字段
	 * @param tableName
	 * @return
	 */
	public  List<ColumnBean> selectDBTableColumnList(String tableName);
}
