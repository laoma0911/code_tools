package com.family.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

public class CompareJson {
	public static void main(String[] args) {
		String json="{\"data\": {\"id\": \"23974\",\"topic_id\": \"8312325\",\"uid\": \"37906185\",\"reply_id\": \"0\",\"content\": \"第一天冬天\",\"status\": \"0\",\"dateline\": \"1433919656\",\"floor\": \"125\",\"flag\": \"0\",\"is_secret\": 0,\"user_avatar\": \"\",\"user_icon\": \"\",\"nick\": \"ios1\",\"user_group_id\": \"2\",\"is_owner\": 0,\"pic\": [{\"id\": \"2077\",\"topic_id\": \"8312325\",\"reply_id\": \"23974\",\"memo\": \"\",\"status\": \"-1\",\"dateline\": \"1433919656\",\"ori_pic\": \"http://a.dayima.com/dayima/group_topic/2015/06/10/7a4bea492ad1c82c43dba2b8bc6dbdb5.jpeg\",\"op_uid\": \"0\",\"reason\": \"\",\"width\": \"72\",\"height\": \"72\",\"imagehash\": \"45bd6d5f73bd56a5361c85e74a3aa74a\",\"pic\": \"http://a.dayima.com/dayima/group_topic/2015/06/10/7a4bea492ad1c82c43dba2b8bc6dbdb5.jpeg\"}],\"emotion\": \"\",\"group_id\": \"115\",\"is_ban\": 0,\"current_user_identity\": 0}}";
		String cJson="{\"timestamp\":1434678295,\"errno\":\"0\",\"data\":{\"uid\":\"37906185\",\"dateline\":1433919656,\"replied_pic\":[],\"is_emotion\":0,\"id\":23974,\"user_icon\":\"\",\"topic_id\":8312325,\"user_group_id\":2,\"is_pic\":0,\"status\":0,\"is_ban\":0,\"current_user_identity\":0,\"group_id\":115,\"pic\":[{\"op_uid\":0,\"memo\":\"\",\"status\":-1,\"reason\":\"\",\"width\":72,\"pic\":\"http://a.dayima.com/dayima/group_topic/2015/06/10/7a4bea492ad1c82c43dba2b8bc6dbdb5.jpeg\",\"dateline\":1433919656,\"id\":2077,\"height\":72,\"imagehash\":\"45bd6d5f73bd56a5361c85e74a3aa74a\",\"reply_id\":23974,\"ori_pic\":\"http://a.dayima.com/dayima/group_topic/2015/06/10/7a4bea492ad1c82c43dba2b8bc6dbdb5.jpeg\",\"topic_id\":8312325}],\"is_secret\":0,\"emotion\":\"\",\"content\":\"第一天冬天\",\"flag\":0,\"is_owner\":0,\"floor\":125,\"nick\":\"ios1\",\"reply_id\":0,\"user_avatar\":\"\"},\"errdesc\":\"\"}";
		Map<String,Map<String,Object>> result=compare(json, cJson);
		System.out.println("缺少的key"+result.get("noHave"));
		System.out.println("不对的key"+result.get("noEq"));
	}
	public static Map<String,Map<String,Object>> compare(String json,String cJson){
		Map<String,Map<String,Object>> result=new HashMap<String,Map<String,Object>>();
		Map<String,Object> sourceMap=JSONObject.parseObject(json, Map.class);
		Map<String,Object> cMap=JSONObject.parseObject(cJson, Map.class);
		Map<String,Object> noHave=new HashMap<String,Object>();
		Map<String,Object> noEq=new HashMap<String,Object>();
		compare(sourceMap,cMap,noHave,noEq);
		result.put("noHave", noHave);
		result.put("noEq", noEq);
		return result;
	}
	public static void compare(Map<String,Object> sourceMap,Map<String,Object> cMap,Map<String,Object> noHave,Map<String,Object> noEq){
		for(Entry<String,Object> entry:sourceMap.entrySet()){
			String key=entry.getKey();
			if(cMap.containsKey(key)){
				Object sourceValue=entry.getValue();
				Object value=cMap.get(key);
				if(sourceValue instanceof String|| sourceValue instanceof Integer ||sourceValue instanceof Long){
					if(!sourceValue.toString().equals(value.toString())){
						noEq.put(key, key);
					}
				}else{
					if(sourceValue instanceof Map){
						if(!(value instanceof Map)){
							noEq.put(key, key);
						}else{
							compare((Map<String,Object>)sourceValue,(Map<String,Object>)value,noHave,noEq);
						}
					}else{
						if(sourceValue instanceof List){
							if(!(value instanceof List)){
								noEq.put(key, key);
							}else{
								List<Map<String,Object>> sourceRows=(List<Map<String,Object>>)sourceValue;
								List<Map<String,Object>> rows=(List<Map<String,Object>>)value;
								if(sourceRows.size()!=rows.size()){
									noEq.put(key, key);
								}else{
									for(int i=0;i<sourceRows.size();i++){
										compare(sourceRows.get(i),rows.get(i),noHave,noEq);
									}
								}
							}
						}
					}
				}
			}else{
				noHave.put(key, key);
			}
		}
	}
}
