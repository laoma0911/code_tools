package com.family.tools.db.operator.dao;

import java.util.List;
import java.util.Map;

import com.family.tools.db.operator.base.GenericDaoImpl;
import com.family.tools.db.operator.bean.ColumnBean;
import com.family.tools.db.operator.dao.interfaces.IDbOperator;

public abstract class DBOperateHelper extends GenericDaoImpl<Object, ColumnBean> implements IDbOperator{


	@Override
	public List<ColumnBean> selectDBTableColumnList(String tableName) {
		List<ColumnBean> columns=doSelectDBTableColumnList(tableName);
		for(ColumnBean columnBean:columns){
			columnBean.setJavaType(getJavaType(columnBean.getData_type(), columnBean.getNumeric_precision(), columnBean.getNumeric_scale()));
		}
		return columns;
	}
	public abstract List<ColumnBean> doSelectDBTableColumnList(String tableName);
	/**
	 * 是否long类型
	 * @param dbType
	 * @param dataPrecision
	 * @param numericScale
	 * @return
	 */
	private boolean isLong(String dbType,int dataPrecision,int numericScale){
		dbType=dbType.toLowerCase();
		if(dbType.indexOf("bigint")>-1){
			return true;
		}else if(dbType.indexOf("int")>-1||dbType.indexOf("number")>-1){
			if(dataPrecision>9){
				return true;
			}
		}else if(dbType.indexOf("numeric")>-1){
			try {
				if(numericScale==0){
					return true;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 是否int类型
	 * @param dbType
	 * @param numericScale
	 * @return
	 * @date Aug 29, 2013 11:28:24 AM
	 * @author wuzl
	 * @comment
	 */
	private boolean isInteger(String dbType,int numericScale){
		dbType=dbType.toLowerCase();
		if(dbType.indexOf("int")>-1||dbType.indexOf("bigint")>-1||dbType.indexOf("number")>-1){
			return true;
		}else if(dbType.indexOf("numeric")>-1){
			try {
				if(numericScale==0){
					return true;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 是否double
	 * @param dbType
	 * @param numericScale
	 * @return
	 * @date Jul 19, 2013 3:14:11 PM
	 * @author wuzl
	 * @comment
	 */
	private boolean isDouble(String dbType,int numericScale){
		dbType=dbType.toLowerCase();
		if(dbType.indexOf("numeric")>-1||dbType.indexOf("number")>-1){
			try {
				if(numericScale>0){
					return true;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	private boolean isDate(String dbType){
		dbType=dbType.toLowerCase();
		if(dbType.indexOf("date")>-1||dbType.indexOf("time")>-1){
			return true;
		}
		return false;
	}
	public String getJavaType(String sqlType,int precision,int numericScale){
		if(this.isDate(sqlType)){
			return "Date";
		}else if(isLong(sqlType,precision,numericScale)){
			return "Long";
		}else if(isInteger(sqlType,precision)){
			return "Integer";
		}else if(isDouble(sqlType,precision)){
			return "Double";
		}
		return "String";
	}
}
