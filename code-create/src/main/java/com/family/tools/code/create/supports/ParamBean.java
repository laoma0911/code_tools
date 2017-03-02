package com.family.tools.code.create.supports;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.family.tools.db.operator.bean.ColumnBean;
import com.family.util.StringUtil;

public class ParamBean {
	private String tableName;//表名
	private Map<String,String> pathMap=new HashMap<String,String>();//各种路径的保存
	private Map<String,String> confMap=new HashMap<String,String>();//配置参数
	private List<ColumnBean> columns;//列信息
	private ColumnBean priColumn;//主键
	private Set<String> codeTaskChain=new HashSet<String>();//代码任务链 用来防止死锁
	private String fileName;
	private String filePath;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Map<String, String> getPathMap() {
		return pathMap;
	}
	public void setPathMap(Map<String, String> pathMap) {
		this.pathMap = pathMap;
	}
	public Map<String, String> getConfMap() {
		return confMap;
	}
	public void setConfMap(Map<String, String> confMap) {
		this.confMap = confMap;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<ColumnBean> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnBean> columns) {
		this.columns = columns;
	}
	public Set<String> getCodeTaskChain() {
		return codeTaskChain;
	}
	public void setCodeTaskChain(Set<String> codeTaskChain) {
		if(codeTaskChain==null){
			throw new RuntimeException("不可以设置任务链为null");
		}
		this.codeTaskChain = codeTaskChain;
	}
	/**
	 * 获取路径 没有的话抛出异常
	 * @param pathKey
	 * @return
	 */
	public String getPath(String pathKey){
		return getPath(pathKey,true);
	}
	public String getPath(String pathKey,boolean throwError){
		String path=pathMap.get(pathKey);
		if(StringUtil.isBlank(path)&&throwError){
			throw new RuntimeException("【"+pathKey+"】不可以为空");
		}
		return path;
	}
	public String getPath(String pathKey,String defaultPath){
		String path=getPath(pathKey,false);
		if(StringUtil.isBlank(path)){
			return defaultPath;
		}
		return path;
	}
	/**
	 * 获取java的路径 是工程与包之间的路径
	 * @return
	 */
	public String getJAVAPath(){
		String isMaven=getConfigParam(Constants.Config.IS_MAVEN,"true");
		if(isMaven.equals("true")){
			return "/src/main/java";
		}
		return "";
	}
	/**
	 * 获取resources的路径 是工程与包之间的路径
	 * @return
	 */
	public String getResourcesPath(){
		String isMaven=getConfigParam(Constants.Config.IS_MAVEN,"true");
		if(isMaven.equals("true")){
			return "/src/main/resources";
		}
		return "";
	}
	public String getConfigParam(String key){
		return confMap.get(key);
	}
	public String getConfigParam(String key,String defaultValue){
		String value=confMap.get(key);
		if(StringUtil.isBlank(value)){
			return defaultValue;
		}
		return value;
	}
	/**
	 * 设置配置值
	 * @param key
	 * @param value
	 */
	public void setConfParam(String key,String value){
		confMap.put(key, value);
	}
	/**
	 * 获取主键
	 * @return
	 */
	public ColumnBean getPriColumn() {
		if(priColumn!=null){
			return priColumn;
		}
		if(columns!=null&&!columns.isEmpty()){
			for(ColumnBean bean:columns){
				if(bean.isIs_pri()){
					priColumn=bean;
					return bean;
				}
			}
		}
		return priColumn;
	}
}
