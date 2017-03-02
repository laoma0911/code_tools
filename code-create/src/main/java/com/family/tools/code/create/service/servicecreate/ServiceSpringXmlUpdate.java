package com.family.tools.code.create.service.servicecreate;

import org.jdom2.Document;
import org.jdom2.Element;

import com.family.tools.code.create.supports.AbstractXmlFileUpdateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class ServiceSpringXmlUpdate extends AbstractXmlFileUpdateCodeOperator {
	@Override
	public Document doUpdateXmlCode(ParamBean paramBean, Document doc) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/* 2.获取验证包名 */
		String serviceImplPackagePath = paramBean
				.getPath(Constants.Service.SERVICE_IMPL_PACKAGE_PATH );
		/* 3.生成service名称 */
		String daoName = ConfigUtil.changeUnderLineToHump(tableName) + "Dao";
		String serviceName = ConfigUtil.changeUnderLineToHump(tableName)
				+ "Service";
		String serviceImplName = ConfigUtil
				.changeUnderLineToHumpAndFontUp(tableName) + "ServiceImpl";
		Element rootEl = doc.getRootElement();
		Element beanElement = new Element("bean");
		beanElement.setAttribute("id", serviceName);
		beanElement.setAttribute("class", serviceImplPackagePath + "."
				+ serviceImplName);
		beanElement.setNamespace(rootEl.getNamespace());
		Element constructorElement=new Element("constructor-arg");
		constructorElement.setNamespace(rootEl.getNamespace());
		constructorElement.setAttribute("index","0");
		constructorElement.setAttribute("ref",daoName);
		beanElement.addContent(constructorElement);
		rootEl.addContent(beanElement);
		return doc;
	}

	@Override
	public void doSetFilePath(ParamBean paramBean) {
		String projectPath = paramBean.getPath(Constants.Service.SERVICE_IMPL_PROJECT_PATH);
		String daoSpringResourcesPath = paramBean
				.getPath(Constants.Service.SERVICE_SPRING_RESOURCES_PATH,"/");
		paramBean.setFilePath(projectPath + paramBean.getResourcesPath()
				+ daoSpringResourcesPath);
		paramBean.setFileName(paramBean
				.getPath(Constants.Service.SERVICE_SPRING_FILE_NAME));
	}
}
