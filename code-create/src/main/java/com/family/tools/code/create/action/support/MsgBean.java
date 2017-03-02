package com.family.tools.code.create.action.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 返回json信息的bean
 * @author dell
 *
 */
public class MsgBean {
	private boolean success;
	private String status;
	private String msg;
	private Map dto;
	public MsgBean() {
		//默认的数据
		this.success=true;
		this.msg="操作成功";
		this.status=Constants.AjaxStatus.STATUS_SUCCESS;
		dto=new HashMap();
		dto.put(Constants.AjaxReturn.SUCCESS, this.success);
		dto.put(Constants.AjaxReturn.STATUS_INFO, msg);
		dto.put(Constants.AjaxReturn.STATUS_CODE, this.status);
	}
	public MsgBean(boolean success,String msg,String status) {
		//默认的数据
		this.success=success;
		this.msg="msg";
		this.status=status;
		dto=new HashMap();
		dto.put(Constants.AjaxReturn.SUCCESS, this.success);
		dto.put(Constants.AjaxReturn.STATUS_INFO, msg);
		dto.put(Constants.AjaxReturn.STATUS_CODE, this.status);
	}
	/**
	 * 公开的插入数据的接口
	 * @param key
	 * @param value
	 * @date May 11, 2013 9:20:24 PM
	 * @author wuzl
	 * @comment
	 */
	public void put(String key,Object value){
		dto.put(key, value);
	}
	/**
	 * map的键值对放入到返回数据
	 * @param dto
	 * @date Aug 31, 2013 10:17:42 AM
	 * @author wuzl
	 * @comment
	 */
	public void putMap(Map msg){
		Set<String> keySet=msg.keySet();
		for(String key:keySet){
			dto.put(key, msg.get(key));
		}
	}
	/**
	 * 设置错误信息 标记为后台出错 自定义错误信息
	 * @param errorMsg
	 * @date Jun 13, 2013 5:05:00 PM
	 * @author wuzl
	 * @comment
	 */
	public void setFailure(String errorMsg){
		dto.put(Constants.AjaxReturn.STATUS_INFO, errorMsg);
		dto.put(Constants.AjaxReturn.STATUS_CODE, Constants.AjaxStatus.STATUS_FAILURE);
	}
	/**
	 * 返回给前台信息
	 * @return
	 * @date Jun 13, 2013 5:04:02 PM
	 * @author wuzl
	 * @comment
	 */
	public Map returnMsg(){
		return dto;
	}
	
}
