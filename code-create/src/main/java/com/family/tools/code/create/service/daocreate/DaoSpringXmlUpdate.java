package com.family.tools.code.create.service.daocreate;

import org.jdom2.Document;
import org.jdom2.Element;

import com.family.tools.code.create.supports.AbstractXmlFileUpdateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class DaoSpringXmlUpdate extends AbstractXmlFileUpdateCodeOperator {
	@Override
	public Document doUpdateXmlCode(ParamBean paramBean, Document doc) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/* 2.获取验证包名 */
		String daoImplPackagePath = paramBean
				.getPath(Constants.Dao.DAO_IMPL_PACKAGE_PATH);
		/* 3.生成DAO名称 */
		String daoName = ConfigUtil.changeUnderLineToHump(tableName) + "Dao";
		String daoImplName = ConfigUtil
				.changeUnderLineToHumpAndFontUp(tableName) + "DaoImpl";
		Element rootEl = doc.getRootElement();
		Element beanElement = new Element("bean");
		beanElement.setAttribute("id", daoName);
		beanElement.setAttribute("class", daoImplPackagePath + "."
				+ daoImplName);
		beanElement.setAttribute("parent", "genericDao");
		beanElement.setNamespace(rootEl.getNamespace());
		rootEl.addContent(beanElement);
		return doc;
	}

	@Override
	public void doSetFilePath(ParamBean paramBean) {
		String projectPath = paramBean.getPath(Constants.Dao.DAO_PROJECT_PATH);
		String daoSpringResourcesPath = paramBean
				.getPath(Constants.Dao.DAO_SPRING_RESOURCES_PATH,"/");
		paramBean.setFilePath(projectPath + paramBean.getResourcesPath()
				+ daoSpringResourcesPath);
		paramBean.setFileName(paramBean
				.getPath(Constants.Dao.DAO_SPRING_FILE_NAME));
	}
}
