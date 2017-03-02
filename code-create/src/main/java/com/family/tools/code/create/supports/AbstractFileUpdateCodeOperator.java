package com.family.tools.code.create.supports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.family.util.FileUtil;

public abstract class AbstractFileUpdateCodeOperator extends AbstractCodeOperator{
	private Log log = LogFactory.getLog(AbstractFileUpdateCodeOperator.class);
	@Override
	public void doOperatorCodeFile(ParamBean paramBean) {
		this.doSetFilePath(paramBean);
		String filePath=paramBean.getFilePath();
		String fileName=paramBean.getFileName();
		String fileNamePath=filePath+FILE_SEPARATOR+fileName;
		/*1.检查文件是否存在*/
		if(!checkFilePath(paramBean,fileNamePath)){
			throw new RuntimeException("指定文件【"+fileNamePath+"】不存在");
		}
		/*2.获取旧的代码*/
		String oldCode=FileUtil.getFileContent(fileNamePath);
		/*3.修改具体代码*/
		String code=this.doUpdateCode(paramBean,oldCode);
		/*4.写入文件*/
		FileUtil.writeAndValidateFile(code, fileNamePath);
		log.info("文件【"+fileNamePath+"】修改成功");
	}
	/**
	 * 设置修改文件路径
	 * 
	 * @return
	 */
	public abstract void doSetFilePath(ParamBean paramBean);
	/**
	 * 修改代码内容
	 * @param paramBean
	 * @param oldCode
	 * @return
	 */
	public abstract String doUpdateCode(ParamBean paramBean,String oldCode);

}
