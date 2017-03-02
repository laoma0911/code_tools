package com.family.tools.db.operator.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.family.tools.db.operator.bean.ColumnBean;
import com.family.tools.db.operator.bean.DbMsgBean;
import com.family.tools.db.operator.supports.DataSourceUtil;

public class MySqlDbOperatorImpl extends DBOperateHelper{
	@Override
	public boolean testConnect()  {
		Map<String,String> dto=getSqlSession().selectOne("com.family.tools.db.operator.mysql.db.testConnection");
		return dto!=null&&!dto.keySet().isEmpty();
	}

	@Override
	public List<Map<String, String>> selectDBTableList(String dbName){
		List<Map<String, String>>  rows=getSqlSession().selectList("com.family.tools.db.operator.mysql.db.selectTableList",dbName);
		return rows;
	}

	@Override
	public List<ColumnBean> doSelectDBTableColumnList(String tableName)
			 {
		SqlSession sqlSession=getSqlSession();
		DbMsgBean dbMsg=DataSourceUtil.getDbConf(sqlSession.getConfiguration().getEnvironment().getDataSource());
		String dbName=dbMsg.getDbName();
		Map<String,Object> dto=new HashMap<String,Object>();
		dto.put("tableName", tableName);
		dto.put("dbName", dbName);
		List<ColumnBean>  rows=getSqlSession().selectList("com.family.tools.db.operator.mysql.db.selectTableColumnList",dto);
		for(ColumnBean columnBean:rows){
			if(columnBean.getColumn_key().equals("PRI")){
				columnBean.setIs_pri(true);
			}
		}
		return rows;
	}
	
}
