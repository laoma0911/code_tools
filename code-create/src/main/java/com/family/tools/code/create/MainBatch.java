package com.family.tools.code.create;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.tools.code.create.supports.AbstractCodeOperator;
import com.family.tools.code.create.supports.ParamBean;
import com.family.tools.db.operator.bean.DbConfBean;
import com.family.tools.db.operator.supports.DataSourceContextHolder;

public class MainBatch {
	public static void main(String[] args) {
		DbConfBean bean=new DbConfBean();
		bean.setDriverClassName("com.mysql.jdbc.Driver");
		bean.setPassword("JuiIo90PoiUiejOiu38Hu");
		bean.setUrl("jdbc:mysql://192.168.123.3:3306/dayima?useUnicode=true&amp;characterEncoding=utf-8");
		bean.setUserName("kangseed");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		System.out.println(">>>>>load success");
		DataSourceContextHolder.setDataSourceContextHolder(bean);//指定数据源
		Map<String,String> PATH_MAP=new HashMap<String,String>();
		PATH_MAP.put("BEAN_PROJECT_PATH", "/home/wuzl/dayimaworkspace/uic/uic-api");
		PATH_MAP.put("BEAN_PACKAGE_PATH", "com.dayima.uic.api.model");
		PATH_MAP.put("DAO_PACKAGE_PATH", "com.dayima.uic.provider.dao");
		PATH_MAP.put("DAO_IMPL_PACKAGE_PATH", "com.dayima.uic.provider.dao.impl");
		PATH_MAP.put("DAO_PROJECT_PATH", "/home/wuzl/dayimaworkspace/uic/uic-provider");
		PATH_MAP.put("DAO_SPRING_RESOURCES_PATH", "/");
		PATH_MAP.put("DAO_SPRING_FILE_NAME", "app-dao.xml");
		PATH_MAP.put("MYBATIS_PACKAGE_PATH", "mybatis");
		PATH_MAP.put("MYBATIS_CONF_FILE_NAME", "mybatis-config.xml");
		
		PATH_MAP.put("SERVICE_INTERFACES_PROJECT_PATH", "/home/wuzl/dayimaworkspace/uic/uic-api");
		PATH_MAP.put("SERVICE_IMPL_PROJECT_PATH", "/home/wuzl/dayimaworkspace/dataapi/data-provider");
		PATH_MAP.put("SERVICE_PACKAGE_PATH", "com.dayima.data.api.interfaces");
		PATH_MAP.put("SERVICE_IMPL_PACKAGE_PATH", "com.dayima.data.provider.service.impl");
		PATH_MAP.put("SERVICE_SPRING_FILE_NAME", "app-service.xml");
		PATH_MAP.put("SERVICE_SPRING_RESOURCES_PATH", "/");
		
		ParamBean paramBean=new ParamBean();
		paramBean.setPathMap(PATH_MAP);
//		paramBean.setTableName("user");//暂时屏蔽
		AbstractCodeOperator mybatisDaoImplCodeCreate=context.getBean("mybatisDaoImplCodeCreate",AbstractCodeOperator.class);
//		mybatisDaoImplCodeCreate.operatorCodeFile(paramBean);
		//解析xml
		SAXBuilder builder = new SAXBuilder(); 
		String file="/home/wuzl/tableName.xml";
        Document doc = null;
        try {
        	doc=builder.build(file);
        	Element rootEl = doc.getRootElement();
        	List<Element> elements=rootEl.getChildren();
        	for(Element element:elements){
        		String tableName=element.getAttributeValue("tableName");
        		paramBean=new ParamBean();
        		paramBean.setPathMap(PATH_MAP);
        		paramBean.setTableName(tableName);
        		mybatisDaoImplCodeCreate.operatorCodeFile(paramBean);
        	}
		} catch(Exception e) {
			throw new RuntimeException("读取xml【"+file+"】失败:"+e.getMessage());
		}
	}
}
