package com.family.tools.code.create.dao.xmlimplate.base;

import java.io.File;

import com.family.util.StringUtil;

public class XmlDataPath {
	private static String xmlPath;

	public static void setXmlPath(String xmlPath) {
		XmlDataPath.xmlPath = xmlPath;
	}
	public static String getDataXmlPath(String fileName){
		/*判断文件路径是否存在*/
		File filePath=new File(xmlPath);
		if(!filePath.exists()){//不存在新建
			filePath.mkdirs();
		}
		/*获取配置文件路径*/
		if(StringUtil.isBlank(fileName)){
			throw new RuntimeException("没有配置文件名称");
		}
		if(fileName.lastIndexOf(".xml")==-1){
			fileName+=".xml";
		}
		fileName=xmlPath+"/"+fileName;;
		return fileName;
				
	}
}
