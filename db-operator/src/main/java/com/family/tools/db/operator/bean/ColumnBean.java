package com.family.tools.db.operator.bean;

import java.io.Serializable;

public class ColumnBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String table_name;
	private String column_name;
	private String data_type;
	private int numeric_precision;
	private int numeric_scale;
	private String column_key;
	private boolean is_pri=false;//主键
	public String getColumn_key() {
		return column_key;
	}
	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}
	public boolean isIs_pri() {
		return is_pri;
	}
	public void setIs_pri(boolean is_pri) {
		this.is_pri = is_pri;
	}
	public int getNumeric_scale() {
		return numeric_scale;
	}
	public void setNumeric_scale(int numeric_scale) {
		this.numeric_scale = numeric_scale;
	}
	private String javaType;
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public int getNumeric_precision() {
		return numeric_precision;
	}
	public void setNumeric_precision(int numeric_precision) {
		this.numeric_precision = numeric_precision;
	}
}
