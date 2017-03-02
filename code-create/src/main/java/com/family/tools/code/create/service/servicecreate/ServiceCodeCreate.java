package com.family.tools.code.create.service.servicecreate;

import java.util.List;

import com.family.tools.code.create.supports.AbstractFileCreateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.tools.db.operator.bean.ColumnBean;
import com.family.tools.db.operator.dao.interfaces.IDbOperator;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class ServiceCodeCreate extends AbstractFileCreateCodeOperator {
	private IDbOperator mysqlDbOperator;
	public void setMysqlDbOperator(IDbOperator mysqlDbOperator) {
		this.mysqlDbOperator = mysqlDbOperator;
	}
	@Override
	public String doCreateNewCode(ParamBean paramBean) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/*获取列信息*/
		List<ColumnBean> columns=paramBean.getColumns();
		if(columns==null||columns.isEmpty()){
			columns=mysqlDbOperator.selectDBTableColumnList(tableName);
			if(columns.size()==0){
				throw new RuntimeException("没有查到表【"+tableName+"】的列");
			}
			/*2.1设置列信息*/
			paramBean.setColumns(columns);
		}
		/* 2.获取service接口工程的绝对路径 */
		String projectPath = paramBean.getPath(Constants.Service.SERVICE_INTERFACES_PROJECT_PATH );
		/* 3.获取验证包名 */
		String packagePath = paramBean.getPath(Constants.Service.SERVICE_PACKAGE_PATH);
		String beanPackagePath = paramBean
				.getPath(Constants.Dao.BEAN_PACKAGE_PATH);
		/* 4.生成service名称 */
		String serviceName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName)
				+ "Service";
		String beanName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName);
		/* 5.设置文件名 */
		paramBean.setFileName(serviceName + ".java");
		/* 6.设置保存路径 */
		String packageFilePath = ConfigUtil
				.changePackageToRelativePath(packagePath);
		paramBean.setFilePath(projectPath + paramBean.getJAVAPath()
				+ packageFilePath);
		/* 7.代码主体 */
		StringBuilder code = new StringBuilder();
		code.append("package " + packagePath + ";" + LINE_SEPARATOR
				+ LINE_SEPARATOR);
//		code.append("import java.util.List;"
//				+ LINE_SEPARATOR);
		code.append("import com.yoloho.service.api.GenericService;"
				+ LINE_SEPARATOR);
		code.append("import " + beanPackagePath + "." + beanName + ";"
				+ LINE_SEPARATOR + LINE_SEPARATOR);
		code.append("public interface " + serviceName + " extends GenericService<"
				+ beanName + ", "+paramBean.getPriColumn().getJavaType()+"> {" + LINE_SEPARATOR + LINE_SEPARATOR);
		code.append("}");
		return code.toString();
	}

}
