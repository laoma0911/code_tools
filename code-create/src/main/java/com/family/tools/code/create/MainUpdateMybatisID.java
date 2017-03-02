package com.family.tools.code.create;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.jaxen.JaxenXPathFactory;

import com.family.util.FileUtil;

public class MainUpdateMybatisID {
	public static void main(String[] args) {
		String dirPath = "/home/wuzl/dayimaworkspace/uic/uic-provider/src/main/resources/mybatis";
		List<File> fileList = FileUtil.getDirectFileList(dirPath);
		XPathFactory factory=JaxenXPathFactory.instance();
		XPathExpression<Object> exporssion=factory.compile("select[@id='getByPK']");
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		for (File file : fileList) {
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			try {
				doc = builder.build(file);
				Element root=doc.getRootElement();
//				List<Element> rows=root.getChildren();
//				for(Element element:rows){
//					if(element.getAttributeValue("id").equals("selectOne")){
//						element.setAttribute("id", "getByPK");
//					}else if(element.getAttributeValue("id").equals("update")){
//						element.setAttribute("id", "updateByBean");
//					}
//					
//				}
//				System.out.println(out.outputString(root));;
				List<Object> rows=exporssion.evaluate(root);
				for(Object obj:rows){
					Element element=(Element)obj;
					System.out.println(element.getText());
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
