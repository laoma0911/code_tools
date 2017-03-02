package com.family.tools.code.create.supports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.family.util.FileUtil;

/**
 * 对xml代码的操作
 * 
 * @author wuzl
 * 
 */
public abstract class AbstractXmlFileCreateCodeOperator extends AbstractCodeOperator {
	private Log log = LogFactory.getLog(AbstractXmlFileCreateCodeOperator.class);
	@Override
	public void doOperatorCodeFile(ParamBean paramBean) {
		/* 1.生成具体xml */
		Document doc = this.doCreateNewXmlCode(paramBean);
		String filePath=paramBean.getFilePath();
		String fileName=paramBean.getFileName();
		/* 2.检查目录 */
		if (!checkFilePath(paramBean,filePath)) {
			throw new RuntimeException("指定目录【" + filePath + "】不存在");
		}
		/* 3.检查文件是否存在 */
		String fileNamePath = filePath + FILE_SEPARATOR + fileName;
		if (FileUtil.validateFileOrDirectoryHave(fileNamePath)) {
			throw new RuntimeException("要生成的文件【" + fileNamePath + "】在目录下已经存在");
		}
		/* 4.写入文件 */
		XMLOutputter XMLOut = new XMLOutputter(Format.getPrettyFormat());   
		try {
			XMLOut.output(doc, new FileOutputStream(fileNamePath));
			log.info("写入xml【"+fileNamePath+"】成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("写入xml【"+fileNamePath+"】失败"+e.getMessage());
		} 
	}


	/**
	 * 生成新的代码
	 *  一定要注意设置filetPath和fileName
	 * @return
	 */
	public abstract Document doCreateNewXmlCode(ParamBean paramBean);

	
}	
