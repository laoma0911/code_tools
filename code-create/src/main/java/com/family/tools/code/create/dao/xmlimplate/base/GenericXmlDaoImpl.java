package com.family.tools.code.create.dao.xmlimplate.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.jaxen.JaxenXPathFactory;

import com.family.tools.code.create.dao.bean.DataSourceBean;
import com.family.tools.db.operator.base.interfaces.GenericDao;
import com.family.util.ConfigUtil;
import com.family.util.FileUtil;
import com.family.util.StringUtil;
import com.thoughtworks.xstream.XStream;

public abstract class GenericXmlDaoImpl<T> implements GenericDao<T, Long>{
	protected static XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());//用来输入输出xml
	protected XStream xStream=new XStream();
	private XPathFactory factory=JaxenXPathFactory.instance();
	private SAXBuilder builder = new SAXBuilder(); 
	private String fileName;
	private String beanName;
	private String idField;
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public String getBeanName() {
		if(StringUtil.isBlank(beanName)){
			throw new RuntimeException("beanname不可以为空");
		}
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public GenericXmlDaoImpl(String fileName,String beanName,Class beanClass,String idField) {
		this.setFileName(fileName);
		this.setBeanName(beanName);
		this.setIdField(idField);
		xStream.alias(beanName, beanClass); 
		xStream.alias(ConfigUtil.changeUnderLineToHumpAndFontUp(beanName)+"s", List.class); 
		xStream.useAttributeFor(idField, Long.class);
	}
	@Override
	public int insert(T bean) {
		Document doc= getDoc();
		Element rootElement=doc.getRootElement();
		Element lastELement=this.getLastElement(rootElement);
		long id=1l;
		if(lastELement!=null){
			Long maxid=Long.parseLong(lastELement.getAttributeValue(idField));
			id=maxid+1;
		}
		doSetId(id, bean);
		String xml=xStream.toXML(bean);
		Element element =changeToElement(xml);
		rootElement.addContent(element.detach());
		writeFile(doc);
		return 1;
	}

	@Override
	public int deleteByBean(Object bean) {
		throw new RuntimeException("暂时不支持deleteByBean");
	}

	@Override
	public int delete(Long key) {
		Document doc= getDoc();
		Element rootElement=doc.getRootElement();
		Element element =selectOneById(key,rootElement);
		int index=rootElement.indexOf(element);
		rootElement.getContent().remove(index);
		writeFile(doc);
		return 1;
	}

	@Override
	public List<T> selectList(T bean) {
		Element rootElement=getDoc().getRootElement();
		String xml=out.outputString(rootElement);
		return (List<T>)xStream.fromXML(xml);
	}

	@Override
	public T selectOne(Long key) {
		Element rootElement=getDoc().getRootElement();
		Element element =selectOneById(key,rootElement);
		return (T)xStream.fromXML(out.outputString(element));  
	}
	private Element selectOneById(Long key,Element rootElement){
		try {
			XPathExpression<Object> exporssion=factory.compile(beanName+"[@"+idField+"="+key+"]");
			Element element =(Element)exporssion.evaluateFirst(rootElement);
			return element;
		} catch (Exception e) {
			throw new RuntimeException("查询id为【"+key+"】的数据失败："+e.getMessage());
		}  
	}
	@Override
	public int update(T bean) {
		Document doc= getDoc();
		Element rootElement=doc.getRootElement();
		Element element =selectOneById(doGetId(bean),rootElement);
		int index=rootElement.indexOf(element);
		rootElement.getContent().remove(index);
		String xml=xStream.toXML(bean);
		Element elementUp =changeToElement(xml);
		rootElement.addContent(index,elementUp.detach());
		writeFile(doc);
		return 0;
	}

	@Override
	public int selectCount(T bean) {
		return -1;
	}
	/**
	 * 获取xml的根
	 * @return
	 */
	private Document getDoc(){
		String filePath=XmlDataPath.getDataXmlPath(fileName);
		Document doc = null;
		SAXBuilder builder = new SAXBuilder(); 
		try {
			/*1.获取文件 如果不存在新建*/
			File file=new File(filePath) ;
			if(!file.exists()){
				synchronized (this) {
					if(file.exists()){
						doc=builder.build(file);
					}else{
						doc = this.createNewXml(file);
					}
					return doc;
				}
			}
			/*2.读取数据*/
			doc=builder.build(file);
			return doc;
		} catch (Exception e) {
			throw new RuntimeException("读取【"+fileName+"】xml文件失败："+e.getMessage());
		} 
	}
	/**
	 * 生成一个空白的xml文件
	 * @param file
	 * @return
	 */
	private Document createNewXml(File file){
		FileUtil.createNewFile(file);
		Element rootElement = new Element(ConfigUtil.changeUnderLineToHumpAndFontUp(getBeanName())+"s");// 主节点
		Document doc = new Document(rootElement);
		try {
			out.output(doc, new FileOutputStream(file));
		} catch (IOException e) {
			throw new RuntimeException("生成一个空白【"+file.getPath()+"】xml失败："+e.getMessage());
		}
		return doc;
	}
	private Element changeToElement(String xml){
		try {
			Document documnet=builder.build(new StringReader(xml));
			Element element=documnet.getRootElement();
			return element;
		} catch (Exception e) {
			throw new RuntimeException("转化xml失败："+e.getMessage());
		} 
	}
	private void writeFile(Document doc){
		String filePath=XmlDataPath.getDataXmlPath(fileName);
		try {
			out.output(doc, new FileOutputStream(filePath));
		} catch (Exception e) {
			throw new RuntimeException("保存xml失败："+e.getMessage());
		}
	}
	/**
	 * 获取最后一个节点
	 * @param rootElement
	 * @return
	 */
	private Element getLastElement(Element rootElement){
		XPathExpression<Object> exporssion=factory.compile(beanName+"[last()]");
		return (Element)exporssion.evaluateFirst(rootElement);
	}
	/**
	 * 设置id
	 * @param id
	 * @param bean
	 */
	protected abstract void doSetId(long id,T bean);
	/**
	 * 获取id
	 */
	protected abstract Long doGetId(T bean);
	public static void main(String[] args) {
		List<DataSourceBean> rows=new ArrayList<DataSourceBean>();
		DataSourceBean bean=new DataSourceBean();
		bean.setDriverClassName("driver");
		bean.setPassword("11111");
		bean.setId(1l);
		bean.setUrl("mysql://ddasss");
		bean.setUserName("root");
		rows.add(bean);
		bean=new DataSourceBean();
		bean.setDriverClassName("driver");
		bean.setPassword("2222");
		bean.setId(2l);
		bean.setUrl("mysql://2ddasss");
		bean.setUserName("root2");
		rows.add(bean);
		XStream xStream = new XStream(); 
		xStream.alias("DataSource", DataSourceBean.class); 
		xStream.useAttributeFor("id", Long.class);
		String xml=xStream.toXML(rows);
		System.out.println(xml);
		SAXBuilder builder = new SAXBuilder(); 
		try {
			Document documnet=builder.build(new StringReader(xml));
			Element root=documnet.getRootElement();
			System.out.println(out.outputString(root));
			XPathFactory factory=JaxenXPathFactory.instance();
			XPathExpression<Object> exporssion=factory.compile("DataSource[@id=3]");
			Element element =(Element)exporssion.evaluateFirst(root);
			System.out.println(out.outputString(element));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
