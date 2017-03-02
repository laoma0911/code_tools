package com.family.tools.db.operator.dao.interfaces;

import java.util.List;
import java.util.Map;


public interface IPublichSql {
	/**
	 * 新增
	 * @param insertSql
	 * @throws Exception
	 * @date Jul 3, 2013 8:18:25 PM
	 * @author wuzl
	 * @comment
	 */
	public int insert(String insertSql)throws Exception;
	/**
	 * 删除
	 * @param delSql
	 * @throws Exception
	 * @date Jul 3, 2013 8:18:19 PM
	 * @author wuzl
	 * @comment
	 */
	public int del(String delSql)throws Exception;
	/**
	 * 修改
	 * @param upSql
	 * @throws Exception
	 * @date Jul 3, 2013 8:18:12 PM
	 * @author wuzl
	 * @comment
	 */
	public int update(String upSql)throws Exception;
	/**
	 * 获取单条数据
	 * @param selectSql
	 * @return
	 * @throws Exception
	 * @date Jul 3, 2013 8:18:03 PM
	 * @author wuzl
	 * @comment
	 */
	public Map<String,String> selectOne(String selectSql)throws Exception;
	/**
	 * 分页查询数据
	 * @param selectSql
	 * @return
	 * @throws Exception
	 * @date Jul 3, 2013 8:17:58 PM
	 * @author wuzl
	 * @comment
	 */
	public List<Map<String,String>> selectList(String selectSql)throws Exception;
}
