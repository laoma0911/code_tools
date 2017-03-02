package com.family.tools.db.operator.base.interfaces;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
	/**
	 * 插入
	 * @param bean
	 * @return
	 */
	public int insert(T bean);
	/**
	 * 条件删除
	 * @param bean
	 * @return
	 */
	public int deleteByBean(Object bean);
	/**
	 * 主键删除
	 * @param key
	 * @return
	 */
	public int delete(PK key);
	/**
	 * 查询
	 * @param bean
	 * @return
	 */
	public List<T> selectList(T bean);
	/**
	 * 单个查询
	 * @param key
	 * @return
	 */
	public T selectOne(PK key);
	/**
	 * 修改
	 * @param bean
	 * @return
	 */
	public int update(T bean);
	/**
	 * 查询总数
	 * @param bean
	 * @return
	 */
	public int selectCount(T bean);
}
