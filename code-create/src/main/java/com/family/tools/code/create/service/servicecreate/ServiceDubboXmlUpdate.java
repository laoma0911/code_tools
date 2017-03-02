package com.family.tools.code.create.service.servicecreate;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.family.tools.code.create.supports.AbstractXmlFileUpdateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class ServiceDubboXmlUpdate extends AbstractXmlFileUpdateCodeOperator {
	@Override
	public Document doUpdateXmlCode(ParamBean paramBean, Document doc) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/* 2.获取验证包名 */
		String packagePath = paramBean.getPath(Constants.Service.SERVICE_PACKAGE_PATH);
		/* 3.生成service名称 */
		String serviceName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName)
				+ "Service";
		String refName=ConfigUtil.changeUnderLineToHump(tableName)+ "Service";
		Element rootEl = doc.getRootElement();
		Namespace nsContext = Namespace.getNamespace("dubbo", // prefix
			       "http://code.alibabatech.com/schema/dubbo"); // URI
		Element beanElement = new Element("service",nsContext);
		beanElement.setAttribute("interface", packagePath + "."
				+ serviceName);
		beanElement.setAttribute("ref", refName);
//		beanElement.setNamespace(rootEl.getNamespace());
		rootEl.addContent(beanElement);
		return doc;
	}

	@Override
	public void doSetFilePath(ParamBean paramBean) {
		String projectPath = paramBean.getPath(Constants.Service.SERVICE_IMPL_PROJECT_PATH);
		String springResourcesPath = paramBean
				.getPath(Constants.Service.SERVICE_SPRING_RESOURCES_PATH,"/");
		paramBean.setFilePath(projectPath + paramBean.getResourcesPath()
				+ springResourcesPath);
		paramBean.setFileName(paramBean
				.getPath(Constants.Dubbo.DUBBO_PROVIDER_FILE_NAME));
	}
}
