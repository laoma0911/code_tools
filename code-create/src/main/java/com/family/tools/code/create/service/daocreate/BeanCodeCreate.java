package com.family.tools.code.create.service.daocreate;

import java.util.List;

import com.family.tools.code.create.supports.AbstractFileCreateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.tools.db.operator.bean.ColumnBean;
import com.family.tools.db.operator.dao.interfaces.IDbOperator;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class BeanCodeCreate extends AbstractFileCreateCodeOperator {
	private IDbOperator mysqlDbOperator;
	public void setMysqlDbOperator(IDbOperator mysqlDbOperator) {
		this.mysqlDbOperator = mysqlDbOperator;
	}
	@Override
	public String doCreateNewCode(ParamBean paramBean) {
		/*1.验证table属性*/
		String tableName=paramBean.getTableName();
		if(StringUtil.isBlank(tableName)){
			throw new RuntimeException("tableName不可以为空");
		}
		/*2.获取列信息*/
		List<ColumnBean> columns=paramBean.getColumns();
		if(columns==null||columns.isEmpty()){
			columns=mysqlDbOperator.selectDBTableColumnList(tableName);
			if(columns.size()==0){
				throw new RuntimeException("没有查到表【"+tableName+"】的列");
			}
			/*2.1设置列信息*/
			paramBean.setColumns(columns);
		}
		/*3.获取bean工程的绝对路径*/
		String projectPath=paramBean.getPath(Constants.Dao.BEAN_PROJECT_PATH);
		/*4.获取验证包名*/
		String packagePath=paramBean.getPath(Constants.Dao.BEAN_PACKAGE_PATH);
		/*5.生成bean名称*/
		String beanName=ConfigUtil.changeUnderLineToHumpAndFontUp(tableName);
		/*6.设置文件名*/
		paramBean.setFileName(beanName+".java");
		/*7.设置保存路径*/
		String packageFilePath=ConfigUtil.changePackageToRelativePath(packagePath);
		paramBean.setFilePath(projectPath+paramBean.getJAVAPath()+packageFilePath);
		/*8.代码主体*/
		StringBuilder code=new StringBuilder();
		StringBuilder setGetMethod=new StringBuilder();//保存getset方法体
		code.append("package "+packagePath+";"+LINE_SEPARATOR+LINE_SEPARATOR);
		code.append("import java.io.Serializable;"+LINE_SEPARATOR);
		code.append("public class "+beanName+" implements Serializable {"+LINE_SEPARATOR);
		code.append("\tprivate static final long serialVersionUID = -6188544384149284540L;"+LINE_SEPARATOR);
		for(ColumnBean columnBean:columns){
			String javaType=columnBean.getJavaType();
			String columnName=columnBean.getColumn_name();
			String columnNameHump=ConfigUtil.changeUnderLineToHump(columnName);
			String columnNameHumpFont=ConfigUtil.changeUnderLineToHumpAndFontUp(columnName);
			code.append("\tprivate "+javaType+" "+columnNameHump+";"+LINE_SEPARATOR);
			setGetMethod.append("\tpublic "+javaType+" "+"get"+columnNameHumpFont+"() {"+LINE_SEPARATOR);
			setGetMethod.append("\t\treturn "+columnNameHump+";"+LINE_SEPARATOR);
			setGetMethod.append("\t}"+LINE_SEPARATOR+LINE_SEPARATOR);
			setGetMethod.append("\tpublic void set"+columnNameHumpFont+"("+javaType+" "+columnNameHump+") {"+LINE_SEPARATOR);
			setGetMethod.append("\t\tthis."+columnNameHump+" = "+columnNameHump+";"+LINE_SEPARATOR);
			setGetMethod.append("\t}"+LINE_SEPARATOR+LINE_SEPARATOR);
		}
		code.append(LINE_SEPARATOR);
		code.append(setGetMethod);
		code.append("}"+LINE_SEPARATOR);
		return code.toString();
	}
	
}
