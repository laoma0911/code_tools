package com.family.tools.code.create.service.daocreate;

import com.family.tools.code.create.supports.AbstractFileCreateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class MybatisDaoCodeCreate extends AbstractFileCreateCodeOperator {

	@Override
	public String doCreateNewCode(ParamBean paramBean) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/* 2.获取DAO工程的绝对路径 */
		String projectPath = paramBean.getPath(Constants.Dao.DAO_PROJECT_PATH);
		/* 3.获取验证包名 */
		String packagePath = paramBean.getPath(Constants.Dao.DAO_PACKAGE_PATH);
		String beanPackagePath = paramBean
				.getPath(Constants.Dao.BEAN_PACKAGE_PATH);
		/* 4.生成DAO名称 */
		String daoName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName)
				+ "Dao";
		String beanName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName);
		/* 5.设置文件名 */
		paramBean.setFileName(daoName + ".java");
		/* 6.设置保存路径 */
		String packageFilePath = ConfigUtil
				.changePackageToRelativePath(packagePath);
		paramBean.setFilePath(projectPath + paramBean.getJAVAPath()
				+ packageFilePath);
		/* 7.代码主体 */
		StringBuilder code = new StringBuilder();
		code.append("package " + packagePath + ";" + LINE_SEPARATOR
				+ LINE_SEPARATOR);
		code.append("import com.yoloho.dao.api.GenericDao;"
				+ LINE_SEPARATOR);
		code.append("import " + beanPackagePath + "." + beanName + ";"
				+ LINE_SEPARATOR + LINE_SEPARATOR);
		code.append("public interface " + daoName + " extends GenericDao<"
				+ beanName + ", "+paramBean.getPriColumn().getJavaType()+"> {" + LINE_SEPARATOR + LINE_SEPARATOR);
		code.append("}");
		return code.toString();
	}

}
