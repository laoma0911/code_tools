package com.family.tools.code.create.service.servicecreate;

import com.family.tools.code.create.supports.AbstractFileCreateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class ServiceImplCodeCreate extends AbstractFileCreateCodeOperator {

	@Override
	public String doCreateNewCode(ParamBean paramBean) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/* 2.获取service工程的绝对路径 */
		String projectPath = paramBean.getPath(Constants.Service.SERVICE_IMPL_PROJECT_PATH);
		/* 3.获取验证包名 */
		String daoPackagePath = paramBean.getPath(Constants.Dao.DAO_PACKAGE_PATH);
		String packagePath = paramBean.getPath(Constants.Service.SERVICE_PACKAGE_PATH);
		String serviceImplPackagePath = paramBean
				.getPath(Constants.Service.SERVICE_IMPL_PACKAGE_PATH );
		String beanPackagePath = paramBean
				.getPath(Constants.Dao.BEAN_PACKAGE_PATH);
		/* 4.生成service名称 */
		String daoName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName)
				+ "Dao";
		String serviceName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName)
				+ "Service";
		String serviceImplName = ConfigUtil
				.changeUnderLineToHumpAndFontUp(tableName) + "ServiceImpl";
		String beanName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName);
		/* 5.设置文件名 */
		paramBean.setFileName(serviceImplName + ".java");
		/* 6.设置保存路径 */
		String packageFilePath = ConfigUtil
				.changePackageToRelativePath(serviceImplPackagePath);
		paramBean.setFilePath(projectPath + paramBean.getJAVAPath()
				+ packageFilePath);
		/* 7.代码主体 */
		StringBuilder code = new StringBuilder();
		code.append("package " + serviceImplPackagePath + ";" + LINE_SEPARATOR
				+ LINE_SEPARATOR);
//		code.append("import java.util.List;"
//				+ LINE_SEPARATOR);
		code.append("import com.yoloho.service.GenericServiceImpl;"
				+ LINE_SEPARATOR);
		code.append("import " + packagePath + "." + serviceName + ";"
				+ LINE_SEPARATOR);
		code.append("import " + beanPackagePath + "." + beanName + ";"
				+ LINE_SEPARATOR + LINE_SEPARATOR);
		code.append("import " + daoPackagePath + "." + daoName + ";"
				+ LINE_SEPARATOR + LINE_SEPARATOR);
		code.append("public class " + serviceImplName + " extends GenericServiceImpl<"
				+ beanName + ", "+paramBean.getPriColumn().getJavaType()+">  implements " + serviceName + "{"
				+ LINE_SEPARATOR);
		code.append("\tprivate "+daoName  +" dao;"+LINE_SEPARATOR);
		code.append("\tpublic " + serviceImplName + "("+daoName+" dao) {" + LINE_SEPARATOR);
		code.append("\t\tsuper(dao);" + LINE_SEPARATOR);
		code.append("\t\tthis.dao = dao;" + LINE_SEPARATOR);
		code.append("\t}" + LINE_SEPARATOR);
		code.append("}" + LINE_SEPARATOR);
		return code.toString();
	}

}