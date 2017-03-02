package com.family.tools.code.create;

import java.util.ArrayList;
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

public class MainBatchStrings {
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
		PATH_MAP.put("SERVICE_IMPL_PROJECT_PATH", "/home/wuzl/dayimaworkspace/uic/uic-provider");
		PATH_MAP.put("SERVICE_PACKAGE_PATH", "com.dayima.uic.api.interfaces");
		PATH_MAP.put("SERVICE_IMPL_PACKAGE_PATH", "com.dayima.uic.provider.service.impl");
		PATH_MAP.put("SERVICE_SPRING_FILE_NAME", "app-service.xml");
		PATH_MAP.put("SERVICE_SPRING_RESOURCES_PATH", "/");
		
		ParamBean paramBean=new ParamBean();
		AbstractCodeOperator mybatisDaoImplCodeCreate=context.getBean("mybatisDaoImplCodeCreate",AbstractCodeOperator.class);
		AbstractCodeOperator serviceImplCodeCreate=context.getBean("serviceImplCodeCreate",AbstractCodeOperator.class);
		List<String> tables=new ArrayList<String>();
		tables.add("points_level_rule");
		tables.add("points_user_points");
		tables.add("points_reward_rule");
		tables.add("points_user_quota");
		tables.add("points_user_level_path");
    	for(String tableName:tables){
    		paramBean=new ParamBean();
    		paramBean.setPathMap(PATH_MAP);
    		paramBean.setTableName(tableName);
    		mybatisDaoImplCodeCreate.operatorCodeFile(paramBean);
    		serviceImplCodeCreate.operatorCodeFile(paramBean);
    	}
	}
}
