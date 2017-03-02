package com.family.tools.code.create;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.tools.code.create.action.support.Constants.SysParamConstants;
import com.family.tools.code.create.dao.bean.DataSourceBean;
import com.family.tools.code.create.dao.interfaces.IDataSourceDao;

public class DaoMain {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		IDataSourceDao dataSourceDao=context.getBean(IDataSourceDao.class);
		System.out.println(dataSourceDao.selectCount(null));
//		DataSourceBean bean=new DataSourceBean();
//		bean.setDriverClassName("driver");
//		bean.setPassword("11111");
//		bean.setId(1l);
//		bean.setDataSourceName("testdatasource");
//		bean.setUrl("mysql://ddasss");
//		bean.setUserName("root");
//		dataSourceDao.insert(bean);
		
//		DataSourceBean bean=new DataSourceBean();
//		bean.setDriverClassName("driver2");
//		bean.setPassword("22222");
//		bean.setId(2l);
//		bean.setDataSourceName("testdatasource2");
//		bean.setUrl("mysql://ddasss2");
//		bean.setUserName("root2");
//		dataSourceDao.update(bean);
//		System.out.println(dataSourceDao.selectCount(null));
//		dataSourceDao.delete(2l);
		List<DataSourceBean>  beans=dataSourceDao.selectList(null);
		for(DataSourceBean bean:beans){
			System.out.println(bean.getId());
			System.out.println(bean.getDataSourceName());
		}
	}
}
