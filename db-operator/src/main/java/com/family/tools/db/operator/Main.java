package com.family.tools.db.operator;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.tools.db.operator.bean.ColumnBean;
import com.family.tools.db.operator.bean.DbConfBean;
import com.family.tools.db.operator.dao.interfaces.IDbOperator;
import com.family.tools.db.operator.supports.DBUtil;
import com.family.tools.db.operator.supports.DataSourceContextHolder;

public class Main {
	public static void main(String[] args) {
		DbConfBean bean=new DbConfBean();
		bean.setDriverClassName("com.mysql.jdbc.Driver");
		bean.setPassword("JuiIo90PoiUiejOiu38Hu");
		bean.setUrl("jdbc:mysql://dbserver:3306/dayima?useUnicode=true&amp;characterEncoding=utf-8");
		bean.setUserName("kangseed");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-dao.xml" });
		DataSourceContextHolder.setDataSourceContextHolder(bean);
		try {
			DBUtil.update("delete from points_user_level_path  where level=0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IDbOperator mysqlDbOperator= context.getBean("mysqlDbOperator",IDbOperator.class);
		System.out.println(mysqlDbOperator.testConnect());
		System.out.println(mysqlDbOperator.selectDBTableList("dayima"));
		List<ColumnBean> columns=mysqlDbOperator.selectDBTableColumnList("admin_log");
		for(ColumnBean columnBean:columns){
			System.out.println(">");
			System.out.println(columnBean.getColumn_name());
			System.out.println(columnBean.getNumeric_precision());
			System.out.println(columnBean.getData_type());
			System.out.println(columnBean.getTable_name());
			System.out.println(columnBean.getJavaType());
		}
		
	}
}
