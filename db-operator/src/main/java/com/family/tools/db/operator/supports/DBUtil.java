package com.family.tools.db.operator.supports;

import java.util.List;
import java.util.Map;

import com.family.tools.db.operator.dao.interfaces.IPublichSql;

/**
 * 提供数据库一些sql操作
 * 
 * @date Jul 3, 2013 4:16:27 PM
 * @author wuzl
 * @comment
 */
public class DBUtil {
	private static IPublichSql publichSql;

	public static void setPublichSql(IPublichSql publichSql) {
		DBUtil.publichSql = publichSql;
	}
	/**
	 * 查询列表
	 * @param selectSql
	 * @return
	 * @throws Exception
	 * @date Jul 3, 2013 8:27:46 PM
	 * @author wuzl
	 * @comment
	 */
	public static List<Map<String,String>> selectList(String selectSql) throws Exception{
		return publichSql.selectList( selectSql);
	}
	/**
	 * 删除数据
	 * @param delSql
	 * @return
	 * @throws Exception
	 * @date Jul 3, 2013 8:27:56 PM
	 * @author wuzl
	 * @comment
	 */
	public static int del(String delSql) throws Exception {
		return publichSql.del( delSql);
	}

	/**
	 * 插入数据
	 * @param insertSql
	 * @return
	 * @throws Exception
	 * @date Jul 3, 2013 8:28:03 PM
	 * @author wuzl
	 * @comment
	 */
	public static int insert(String insertSql) throws Exception {
		return publichSql.insert(insertSql);
	}


	/**
	 * 查询单条数据
	 * @param selectSql
	 * @return
	 * @throws Exception
	 * @date Jul 3, 2013 8:28:10 PM
	 * @author wuzl
	 * @comment
	 */
	public static Map selectOne(String selectSql) throws Exception {
		return publichSql.selectOne(selectSql);
	}

	/**
	 * 修改数据
	 * @param upSql
	 * @return
	 * @throws Exception
	 * @date Jul 3, 2013 8:28:17 PM
	 * @author wuzl
	 * @comment
	 */
	public static int update(String upSql) throws Exception {
		return publichSql.update(upSql);

	}
}
