package com.family.tools.code.create.service.daocreate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.tools.code.create.supports.AbstractXmlFileCreateCodeOperator;
import com.family.tools.code.create.supports.Constants;
import com.family.tools.code.create.supports.ParamBean;
import com.family.tools.db.operator.bean.ColumnBean;
import com.family.tools.db.operator.bean.DbConfBean;
import com.family.tools.db.operator.dao.interfaces.IDbOperator;
import com.family.tools.db.operator.supports.DataSourceContextHolder;
import com.family.util.ConfigUtil;
import com.family.util.StringUtil;

public class MybatisXmlCodeCreate extends AbstractXmlFileCreateCodeOperator {
	private IDbOperator mysqlDbOperator;

	public void setMysqlDbOperator(IDbOperator mysqlDbOperator) {
		this.mysqlDbOperator = mysqlDbOperator;
	}

	/**
	 * 获取mybatis的命名空间
	 * 
	 * @param beanPackage
	 * @param beanName
	 * @return
	 */
	public static String getMybatisNameSpace(String beanPackage, String beanName) {
		String[] array = beanPackage.split("[.]");
		if (array.length >= 3) {
			beanPackage = array[0] + "." + array[1] + "." + array[2] + ".";
		}
		return beanPackage + beanName.toLowerCase();
	}

	@Override
	public Document doCreateNewXmlCode(ParamBean paramBean) {
		/* 1.验证table属性 */
		String tableName = paramBean.getTableName();
		if (StringUtil.isBlank(tableName)) {
			throw new RuntimeException("tableName不可以为空");
		}
		/* 2.获取列信息 */
		List<ColumnBean> columns = paramBean.getColumns();
		if (columns == null || columns.isEmpty()) {
			columns = mysqlDbOperator.selectDBTableColumnList(tableName);
			if (columns.size() == 0) {
				throw new RuntimeException("没有查到表【" + tableName + "】的列");
			}
			/* 2.1设置列信息 */
			paramBean.setColumns(columns);
		}
		/* 3.获取包名 */
		String mybatisPackage = paramBean.getPath(
				Constants.Dao.MYBATIS_PACKAGE_PATH, "/");
		String beanPackagePath = paramBean
				.getPath(Constants.Dao.BEAN_PACKAGE_PATH);
		/* 4.生成bean名称 */
		String beanName = ConfigUtil.changeUnderLineToHumpAndFontUp(tableName);
		/* 5.设置文件名 */
		paramBean.setFileName(tableName + ".xml");
		/* 6.获取mybatis的工程路径 */
		String projectPath = paramBean.getPath(
				Constants.Dao.MYBATIS_PROJECT_PATH, false);
		if (StringUtil.isBlank(projectPath)) {// 如果没有配置mybatis 使用dao的
			projectPath = paramBean.getPath(Constants.Dao.DAO_PROJECT_PATH);
		}
		/* 7.设置保存路径 */
		String packageFilePath = ConfigUtil
				.changePackageToRelativePath(mybatisPackage);
		paramBean.setFilePath(projectPath + paramBean.getResourcesPath()
				+ packageFilePath);
		/* 8.生成xml */
		ColumnBean priColumn=paramBean.getPriColumn();
		StringBuilder priWhere=new StringBuilder();//主键的where条件
		priWhere.append(priColumn.getColumn_name());
		priWhere.append("=#{");
		priWhere.append(ConfigUtil.changeUnderLineToHump(priColumn.getColumn_name()));
		priWhere.append("}");
		StringBuilder insertSql=new StringBuilder("insert into ");
		insertSql.append(tableName).append("(");
		StringBuilder insertValueSql=new StringBuilder();
		StringBuilder selectSql=new StringBuilder("select ");
		StringBuilder updateSql=new StringBuilder();
		for(int i=0;i<columns.size();i++){//解析列
			ColumnBean columnBean=columns.get(i);
			String columnName=columnBean.getColumn_name();
			String beanColumnName=ConfigUtil.changeUnderLineToHump(columnName);
			insertSql.append(columnName);
			insertValueSql.append("#{").append(beanColumnName).append("}");
			selectSql.append(columnName).append(" as ").append(beanColumnName);
			if(!columnBean.isIs_pri()){
				updateSql.append(columnName).append("=#{").append(beanColumnName).append("}");
			}
			if(i!=columns.size()-1){
				insertValueSql.append(",");
				insertSql.append(",");
				selectSql.append(",");
				if(!columnBean.isIs_pri()){
					updateSql.append(",");
				}
			}
		}
		insertSql.append(") values (").append(insertValueSql).append(")");
		selectSql.append(" from ").append(tableName);
		Element mapperElement = new Element("mapper");// 主节点
		mapperElement.setAttribute("namespace",
				getMybatisNameSpace(beanPackagePath, beanName));
		Document doc = new Document(mapperElement);
		DocType docType = new DocType("mapper");// docType
		docType.setPublicID("-//ibatis.apache.org//DTD Mapper 3.0//EN");
		docType.setSystemID("http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd");
		doc.setDocType(docType);
		
		Element selectSqlElement =new Element("sql");//sql 
		selectSqlElement.setAttribute("id", "SelectSQL");
		selectSqlElement.addContent(selectSql.toString());
		mapperElement.addContent(selectSqlElement);
		Element sqlInclude=new Element("include");
		sqlInclude.setAttribute("refid","SelectSQL");
		
		Element insertElement =new Element("insert");//insert 
		insertElement.setAttribute("id", "insert");
		insertElement.setAttribute("parameterType", beanName);
		insertElement.setAttribute("useGeneratedKeys", "true");
		insertElement.setText(insertSql.toString());
		mapperElement.addContent(insertElement);
		
		Element deleteElement =new Element("delete");//insert 
		deleteElement.setAttribute("id", "delete");
		deleteElement.setAttribute("parameterType", priColumn.getJavaType());
		deleteElement.setText("delete from "+tableName+" where "+priWhere);
		mapperElement.addContent(deleteElement);
		
		Element countByFilterElement =new Element("select");//countByFilter 
		countByFilterElement.setAttribute("id", "countByFilter");
		countByFilterElement.setAttribute("parameterType", "string");
		countByFilterElement.setAttribute("resultType", "int");
		countByFilterElement.setText("select count(1) from "+tableName);
		Element whereElement =new Element("where");//where
		whereElement.setText("${WhereSQL}");
		countByFilterElement.addContent(whereElement);
		mapperElement.addContent(countByFilterElement);
		
		Element findByFilterElement =new Element("select");//findByFilter 
		findByFilterElement.setAttribute("id", "findByFilter");
		findByFilterElement.setAttribute("parameterType", "java.util.Map");
		findByFilterElement.setAttribute("resultType", beanName);
		findByFilterElement.addContent(sqlInclude);
		whereElement =new Element("where");//where
		whereElement.setText("${WhereSQL} ${SortSQL}");
		findByFilterElement.addContent(whereElement);
		mapperElement.addContent(findByFilterElement);
		
		Element selectOneElement =new Element("select");//selectOne 
		selectOneElement.setAttribute("id", "getByPK");
		selectOneElement.setAttribute("parameterType", "long");
		selectOneElement.setAttribute("resultType", beanName);
		sqlInclude=new Element("include");
		sqlInclude.setAttribute("refid","SelectSQL");
		selectOneElement.addContent(sqlInclude);
		whereElement =new Element("where");//where
		whereElement.setText(priWhere.toString());
		selectOneElement.addContent(whereElement);
		mapperElement.addContent(selectOneElement);
		
		Element updateElement =new Element("update");//update 
		updateElement.setAttribute("id", "updateByBean");
		updateElement.setAttribute("parameterType", beanName);
		updateElement.setText("update "+tableName+" set "+updateSql+" where "+priWhere);
		mapperElement.addContent(updateElement);
		return doc;
	}

	public static void main(String[] args) {
		DbConfBean bean=new DbConfBean();
		bean.setDriverClassName("com.mysql.jdbc.Driver");
		bean.setPassword("JuiIo90PoiUiejOiu38Hu");
		bean.setUrl("jdbc:mysql://192.168.123.3:3306/dayima?useUnicode=true&amp;characterEncoding=utf-8");
		bean.setUserName("kangseed");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		System.out.println(">>>>>load success");
		DataSourceContextHolder.setDataSourceContextHolder(bean);//指定数据源
		IDbOperator mysqlDbOperator=context.getBean("mysqlDbOperator", IDbOperator.class);
		String beanPackage = "com.family.tools.code.create.service.daocreate";
		String beanName = "TestBean";
		String tableName="admin_log";
		List<ColumnBean> columns =mysqlDbOperator.selectDBTableColumnList(tableName);
		ParamBean paramBean=new ParamBean();
		paramBean.setColumns(columns);
		
		ColumnBean priColumn=paramBean.getPriColumn();
		StringBuilder priWhere=new StringBuilder();//主键的where条件
		priWhere.append(priColumn.getColumn_name());
		priWhere.append("=#{");
		priWhere.append(ConfigUtil.changeUnderLineToHump(priColumn.getColumn_name()));
		priWhere.append("}");
		StringBuilder insertSql=new StringBuilder("insert into ");
		insertSql.append(tableName).append("(");
		StringBuilder insertValueSql=new StringBuilder();
		StringBuilder selectSql=new StringBuilder("select ");
		StringBuilder updateSql=new StringBuilder();
		for(int i=0;i<columns.size();i++){//解析列
			ColumnBean columnBean=columns.get(i);
			String columnName=columnBean.getColumn_name();
			String beanColumnName=ConfigUtil.changeUnderLineToHump(columnName);
			insertSql.append(columnName);
			insertValueSql.append("#{").append(beanColumnName).append("}");
			selectSql.append(columnName).append(" as ").append(beanColumnName);
			if(!columnBean.isIs_pri()){
				updateSql.append(columnName).append("=#{").append(beanColumnName).append("}");
			}
			if(i!=columns.size()-1){
				insertValueSql.append(",");
				insertSql.append(",");
				selectSql.append(",");
				if(!columnBean.isIs_pri()){
					updateSql.append(",");
				}
			}
		}
		insertSql.append(") values (").append(insertValueSql).append(")");
		selectSql.append(" from ").append(tableName);
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		Element mapperElement = new Element("mapper");// 主节点
		mapperElement.setAttribute("namespace",
				getMybatisNameSpace(beanPackage, beanName));
		Document doc = new Document(mapperElement);
		DocType docType = new DocType("mapper");// docType
		docType.setPublicID("-//ibatis.apache.org//DTD Mapper 3.0//EN");
		docType.setSystemID("http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd");
		doc.setDocType(docType);
		Element insertElement =new Element("insert");//insert 
		insertElement.setAttribute("id", "insert");
		insertElement.setAttribute("parameterType", beanName);
		insertElement.setAttribute("useGeneratedKeys", "true");
		insertElement.setText(insertSql.toString());
		mapperElement.addContent(insertElement);
		
		Element deleteElement =new Element("delete");//insert 
		deleteElement.setAttribute("id", "delete");
		deleteElement.setAttribute("parameterType", priColumn.getJavaType());
		deleteElement.setText("delete from "+tableName+" where "+priWhere);
		mapperElement.addContent(deleteElement);
		
		Element countByFilterElement =new Element("select");//countByFilter 
		countByFilterElement.setAttribute("id", "countByFilter");
		countByFilterElement.setAttribute("parameterType", "string");
		countByFilterElement.setAttribute("resultType", "int");
		countByFilterElement.setText("select count(1) from "+tableName);
		Element whereElement =new Element("where");//where
		whereElement.setText("${WhereSQL}");
		countByFilterElement.addContent(whereElement);
		mapperElement.addContent(countByFilterElement);
		
		Element findByFilterElement =new Element("select");//findByFilter 
		findByFilterElement.setAttribute("id", "findByFilter");
		findByFilterElement.setAttribute("parameterType", "java.util.Map");
		findByFilterElement.setAttribute("resultType", beanName);
		findByFilterElement.setText(selectSql.toString()+" <where> ${DynamicSQL} ${SortSQL} </where>");
		whereElement =new Element("where");//where
		whereElement.setText("${DynamicSQL} ${SortSQL}");
		findByFilterElement.addContent(whereElement);
		mapperElement.addContent(findByFilterElement);
		
		Element selectOneElement =new Element("select");//selectOne 
		selectOneElement.setAttribute("id", "selectOne");
		selectOneElement.setAttribute("parameterType", "long");
		selectOneElement.setAttribute("resultType", beanName);
		selectOneElement.setText(selectSql.toString()+" where "+priWhere);
		mapperElement.addContent(selectOneElement);
		
		Element updateElement =new Element("update");//update 
		updateElement.setAttribute("id", "update");
		updateElement.setAttribute("parameterType", "long");
		updateElement.setAttribute("resultType", beanName);
		updateElement.setText("update "+tableName+" set "+updateSql+" where "+priWhere);
		mapperElement.addContent(updateElement);
		System.out.println(out.outputElementContentString(mapperElement));
//		try {
//			out.output(doc, new FileOutputStream("/home/wuzl/test.xml"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
