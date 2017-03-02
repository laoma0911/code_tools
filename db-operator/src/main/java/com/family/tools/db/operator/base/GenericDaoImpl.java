package com.family.tools.db.operator.base;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.family.tools.db.operator.base.interfaces.GenericDao;

public class GenericDaoImpl<T, PK extends Serializable> extends
		SqlSessionDaoSupport implements GenericDao<T, PK> {
	private String nameSpace;

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * 获取具体的表空间名
	 * 
	 * @return
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	public static final Log logger = LogFactory.getLog(GenericDaoImpl.class);

	@Override
	public int insert(T bean) {
		return getSqlSession().insert(getNameSpace() + ".insert", bean);
	}

	@Override
	public int deleteByBean(Object bean) {
		return getSqlSession().delete(getNameSpace() + ".delete", bean);
	}

	@Override
	public int delete(PK key) {
		return getSqlSession().delete(getNameSpace() + ".delete", key);
	}

	@Override
	public List<T> selectList(T bean) {
		return (List<T>) getSqlSession().selectList(
				getNameSpace() + ".selectList", bean);
	}

	@Override
	public T selectOne(PK key) {
		return (T) getSqlSession()
				.selectOne(getNameSpace() + ".selectOne", key);
	}

	@Override
	public int update(T bean) {
		return getSqlSession().update(getNameSpace() + ".update", bean);
	}

	@Override
	public int selectCount(T bean) {
		return (Integer) getSqlSession().selectOne(
				getNameSpace() + ".selectCount", bean);
	}
}