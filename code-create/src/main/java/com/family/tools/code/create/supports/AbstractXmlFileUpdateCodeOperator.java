package com.family.tools.code.create.supports;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public abstract class AbstractXmlFileUpdateCodeOperator  extends AbstractCodeOperator{
	private Log log = LogFactory.getLog(AbstractXmlFileUpdateCodeOperator.class);
	@Override
	public void doOperatorCodeFile(ParamBean paramBean) {
		this.doSetFilePath(paramBean);
		String filePath=paramBean.getFilePath();
		String fileName=paramBean.getFileName();
		String fileNamePath = filePath + FILE_SEPARATOR + fileName;
		/* 1.检查文件是否存在 */
		if (!checkFilePath(paramBean,fileNamePath)) {
			throw new RuntimeException("指定文件【" + fileNamePath + "】不存在");
		}
		/* 2.获取旧的xml */
		File file = new File(fileNamePath);  
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = null;
        try {
        	doc=builder.build(file);
		} catch(Exception e) {
			throw new RuntimeException("读取xml【"+fileNamePath+"】失败:"+e.getMessage());
		}
        /* 3.修改具体xml */
		doc=doUpdateXmlCode(paramBean,doc);
		/* 4.写入文件 */
		XMLOutputter XMLOut = new XMLOutputter(Format.getPrettyFormat());   
		try {
			XMLOut.output(doc, new FileOutputStream(fileNamePath));
			log.info("修改xml【"+fileNamePath+"】成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改xml【"+fileNamePath+"】失败"+e.getMessage());
		} 
		
	}
	/**
	 * 设置修改文件路径
	 * 
	 * @return
	 */
	public abstract void doSetFilePath(ParamBean paramBean);
	/**
	 *  修改代码内容
	 * @param paramBean
	 * @param doc
	 * @return
	 */
	public abstract Document doUpdateXmlCode(ParamBean paramBean,Document doc);
}
