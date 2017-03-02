package com.family.tools.code.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.tools.code.create.supports.AbstractCodeOperator;
import com.family.tools.code.create.supports.ParamBean;
import com.family.tools.db.operator.bean.DbConfBean;
import com.family.tools.db.operator.supports.DataSourceContextHolder;

public class Main {
	public static void main(String[] args) {
		DbConfBean bean=new DbConfBean();
		bean.setDriverClassName("com.mysql.jdbc.Driver");
		bean.setUrl("jdbc:mysql://dbserver:3306/dayima?useUnicode=true&amp;characterEncoding=utf-8");
		bean.setUserName("kangseed");
		bean.setPassword("JuiIo90PoiUiejOiu38Hu");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		System.out.println(">>>>>load success");
		DataSourceContextHolder.setDataSourceContextHolder(bean);//指定数据源
		Map<String,String> PATH_MAP=new HashMap<String,String>();
		PATH_MAP.put("BEAN_PROJECT_PATH", "/home/wuzl/dayimaworkspace/forumapi/forum-service-api");
		PATH_MAP.put("BEAN_PACKAGE_PATH", "com.yoloho.forum.service.api.model");
		PATH_MAP.put("DAO_PACKAGE_PATH", "com.yoloho.forum.provider.dao");
		PATH_MAP.put("DAO_IMPL_PACKAGE_PATH", "com.yoloho.forum.provider.dao.impl");
		PATH_MAP.put("DAO_PROJECT_PATH", "/home/wuzl/dayimaworkspace/forumapi/forum-provider");
		PATH_MAP.put("DAO_SPRING_RESOURCES_PATH", "/");
		PATH_MAP.put("DAO_SPRING_FILE_NAME", "app-dao.xml");
		PATH_MAP.put("MYBATIS_PACKAGE_PATH", "mybatis");
		PATH_MAP.put("MYBATIS_CONF_FILE_NAME", "mybatis-config.xml");
		
		PATH_MAP.put("SERVICE_INTERFACES_PROJECT_PATH", "/home/wuzl/dayimaworkspace/forumapi/forum-service-api");
		PATH_MAP.put("SERVICE_IMPL_PROJECT_PATH", "/home/wuzl/dayimaworkspace/forumapi/forum-provider");
		PATH_MAP.put("SERVICE_PACKAGE_PATH", "com.yoloho.forum.service.api.interfaces");
		PATH_MAP.put("SERVICE_IMPL_PACKAGE_PATH", "com.yoloho.forum.provider.service.impl");
		PATH_MAP.put("SERVICE_SPRING_FILE_NAME", "app-service.xml");
		PATH_MAP.put("SERVICE_SPRING_RESOURCES_PATH", "/");
		PATH_MAP.put("DUBBO_PROVIDER_FILE_NAME", "app-dubbo-provider.xml");
		ParamBean paramBean=new ParamBean();
		paramBean.setPathMap(PATH_MAP);
		paramBean.setTableName("topic");//暂时屏蔽
//		AbstractCodeOperator beanCodeCreate=context.getBean("beanCodeCreate",AbstractCodeOperator.class);
//		beanCodeCreate.setTableName("admin_log");
//		beanCodeCreate.putPath(PATH_MAP);
//		beanCodeCreate.operatorCodeFile();
//		mybatisDaoImplCodeCreate.setTableName("admin_log");
//		mybatisDaoImplCodeCreate.putPath(PATH_MAP);
//		mybatisDaoImplCodeCreate.operatorCodeFile();
//		AbstractCodeOperator maybatisConfXmlUpdate=context.getBean("maybatisConfXmlUpdate",AbstractCodeOperator.class);
//		maybatisConfXmlUpdate.operatorCodeFile(paramBean);
//		AbstractCodeOperator mybatisDaoImplCodeCreate=context.getBean("mybatisDaoImplCodeCreate",AbstractCodeOperator.class);
//		mybatisDaoImplCodeCreate.operatorCodeFile(paramBean);
//		AbstractCodeOperator serviceImplCodeCreate=context.getBean("serviceImplCodeCreate",AbstractCodeOperator.class);
//		serviceImplCodeCreate.operatorCodeFile(paramBean);
		AbstractCodeOperator dubboSpringXmlUpdate=context.getBean("dubboSpringXmlUpdate",AbstractCodeOperator.class);
		dubboSpringXmlUpdate.operatorCodeFile(paramBean);
	}
}
