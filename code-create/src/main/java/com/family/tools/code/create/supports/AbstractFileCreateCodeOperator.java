package com.family.tools.code.create.supports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.family.util.FileUtil;

public abstract class AbstractFileCreateCodeOperator extends AbstractCodeOperator {
	private Log log = LogFactory.getLog(AbstractFileCreateCodeOperator.class);
	@Override
	public void doOperatorCodeFile(ParamBean paramBean) {
		/*1.生成具体代码*/
		String code=this.doCreateNewCode(paramBean);
		String filePath=paramBean.getFilePath();
		String fileName=paramBean.getFileName();
		/*2.检查目录*/
		if(!checkFilePath(paramBean,filePath)){
			throw new RuntimeException("指定目录【"+filePath+"】不存在");
		}
		/*3.检查文件是否存在*/
		String fileNamePath=filePath+FILE_SEPARATOR+fileName;
		if(FileUtil.validateFileOrDirectoryHave(fileNamePath)){
			throw new RuntimeException("要生成的文件【"+fileNamePath+"】在目录下已经存在"); 
		}
		/*4.写入文件*/
		FileUtil.writeAndValidateFile(code, fileNamePath);
		log.info("文件【"+fileNamePath+"】写入成功");
	}
	/**
	 * 生成新的代码
	 * 一定要注意设置filetPath和fileName
	 * @param paramBean
	 * @return
	 */
	public abstract String doCreateNewCode(ParamBean paramBean);
}
