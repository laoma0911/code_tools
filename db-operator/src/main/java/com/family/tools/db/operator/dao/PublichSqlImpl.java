package com.family.tools.db.operator.dao;

import java.util.List;
import java.util.Map;

import com.family.tools.db.operator.base.GenericDaoImpl;
import com.family.tools.db.operator.dao.interfaces.IPublichSql;

public class PublichSqlImpl extends GenericDaoImpl implements IPublichSql{
	public PublichSqlImpl() {
		setNameSpace("com.family.tools.db.operator.sql");
	}
	@Override
	public int insert(String insertSql) throws Exception {
		return super.insert(insertSql);
	}

	@Override
	public int del(String delSql) throws Exception {
		return super.delete(delSql);
	}

	@Override
	public int update(String upSql) throws Exception {
		return super.update(upSql);
	}

	@Override
	public Map<String, String> selectOne(String selectSql) throws Exception {
		return (Map<String, String>) super.selectOne(selectSql);
	}

	@Override
	public List<Map<String, String>> selectList(String selectSql)
			throws Exception {
		return  super.selectList(selectSql);
	}
	
}
