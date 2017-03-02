package com.family.tools.code.create.service.daocreate;

import org.jdom2.Document;
import org.jdom2.Element;

import com.family.tools.code.create.supports.AbstractXmlFileUpdateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class MaybatisConfXmlUpdate extends AbstractXmlFileUpdateCodeOperator {
	@Override
	public Document doUpdateXmlCode(ParamBean paramBean, Document doc) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/* 2.获取验证包名 */
		String beanPackagePath = paramBean
				.getPath(Constants.Dao.BEAN_PACKAGE_PATH);
		String mybatisPackage = paramBean.getPath(
				Constants.Dao.MYBATIS_PACKAGE_PATH, "/");
		/* 3.生成bean名称 */
		String beanName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName);
		Element rootEl = doc.getRootElement();//根目录
		Element typeAliasesElement=rootEl.getChild("typeAliases");//typeAliases
		Element typeAliasElement=new Element("typeAlias");
		typeAliasElement.setAttribute("alias",beanName);
		typeAliasElement.setAttribute("type",beanPackagePath+"."+beanName);
		typeAliasesElement.addContent(typeAliasElement);
		Element mappersElement=rootEl.getChild("mappers");//mappers
		Element mapperElement=new Element("mapper");
		mapperElement.setAttribute("resource",mybatisPackage+"/"+tableName+".xml");
		mappersElement.addContent(mapperElement);
		return doc;
	}

	@Override
	public void doSetFilePath(ParamBean paramBean) {
		String projectPath = paramBean.getPath(
				Constants.Dao.MYBATIS_PROJECT_PATH, false);
		if (StringUtil.isBlank(projectPath)) {// 如果没有配置mybatis 使用dao的
			projectPath = paramBean.getPath(Constants.Dao.DAO_PROJECT_PATH);
		}
		String mybatisConfPackage = paramBean.getPath(
				Constants.Dao.MYBATIS_CONF_PACKAGE_PATH, "/");
		paramBean.setFilePath(projectPath + paramBean.getResourcesPath()
				+ mybatisConfPackage);
		paramBean.setFileName(paramBean
				.getPath(Constants.Dao.MYBATIS_CONF_FILE_NAME));
	}
}
