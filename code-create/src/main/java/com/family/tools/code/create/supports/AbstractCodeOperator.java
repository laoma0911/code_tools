package com.family.tools.code.create.supports;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.family.tools.code.create.supports.Constants.Config;
import com.family.tools.code.create.supports.interfaces.CodeOperator;
import com.family.tools.db.operator.supports.DataSourceUtil;
import com.family.util.FileUtil;

/**
 * 注意前置后置任务 容易出现死循环 后期改正
 * 
 * @author wuzl
 * 
 */
public abstract class AbstractCodeOperator implements CodeOperator {
	private Log log = LogFactory.getLog(AbstractCodeOperator.class);
	protected final static String FILE_SEPARATOR = System
			.getProperty("file.separator");
	protected final static String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private AbstractCodeOperator preCodeOperator;// 前置的
	private AbstractCodeOperator nextCodeOperator;// 后置的

	public AbstractCodeOperator getPreCodeOperator() {
		return preCodeOperator;
	}

	public void setPreCodeOperator(AbstractCodeOperator preCodeOperator) {
		this.preCodeOperator = preCodeOperator;
	}

	public AbstractCodeOperator getNextCodeOperator() {
		return nextCodeOperator;
	}

	public void setNextCodeOperator(AbstractCodeOperator nextCodeOperator) {
		this.nextCodeOperator = nextCodeOperator;
	}

	@Override
	public void operatorCodeFile(ParamBean paramBean) {
		Set<String> codeTaskChain=paramBean.getCodeTaskChain();//获取任务链
		codeTaskChain.add(this.getClass().getName());//加入到任务中 标记
		String executeChain=paramBean.getConfigParam(Config.EXECUTE_CHAIN, "true");
		if(executeChain.equals("true")){
			if (preCodeOperator != null) {// 前置任务
				String preCodeOperatorClassName=preCodeOperator.getClass().getName();
				if(!codeTaskChain.contains(preCodeOperatorClassName)){
					log.info(this.getClass() + "开始前置任务" + preCodeOperatorClassName);
					preCodeOperator.operatorCodeFile(paramBean);
					log.info(this.getClass() + "开始前置任务" +preCodeOperatorClassName
							+ "成功");
				}
			}
			doOperatorCodeFile(paramBean);//执行真正需要的代码
			if (nextCodeOperator != null) {// 后置任务
				String nextCodeOperatorClassName=nextCodeOperator.getClass().getName();
				if(!codeTaskChain.contains(nextCodeOperatorClassName)){
					log.info(this.getClass() + "开始后置任务" + nextCodeOperatorClassName);
					nextCodeOperator.operatorCodeFile(paramBean);
					log.info(this.getClass() + "开始后置任务" + nextCodeOperatorClassName
							+ "成功");
				}
			}
		}
	}
	/**
	 * 关闭操作
	 * @param paramBean
	 */
	protected void close(ParamBean paramBean){
		String isCloseDb=paramBean.getConfigParam(Config.CLOSE_DB, "false");
		if(isCloseDb.equals("true")){
			//关闭数据库
			DataSourceUtil.closeDataSource();
		}
	}
	/**
	 * 检查目录是否存在
	 * @param filePath
	 * @return
	 */
	protected boolean checkFilePath(ParamBean paramBean,String filePath){
		String isCheckFile=paramBean.getConfigParam(Config.CHECK_FILE, "true");
		/*1.是否需要检查目录*/
		if(isCheckFile.equals("true")){
			return FileUtil.validateFileOrDirectoryHave(filePath);
		}
		return true;
	}
	/**
	 * 子类的操作
	 */
	protected abstract void doOperatorCodeFile(ParamBean paramBean);
}
