package com.family.util;

import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

public class CreateDocByJson {
	public static void main(String[] args) {
		String json="{\"replied_pic\":{\"op_uid\":0,\"memo\":\"\",\"status\":-1,\"reason\":\"\",\"width\":200,\"pic\":\"dayima/group_topic/2015/05/21/414a0b7a7c0da9d507c505f559bc6c8f.jpg\",\"dateline\":1432191505,\"id\":1948,\"height\":200,\"imagehash\":\"e13305665f35cec4d981f6f206822870\",\"reply_id\":20147,\"ori_pic\":\"http://a.dayima.com/dayima/group_topic/2015/05/21/414a0b7a7c0da9d507c505f559bc6c8f.jpg\",\"topic_id\":1001450}}";

		System.out.println(outDoc(JSONObject.parseObject(json,Map.class),null));;
	}
	public static String outDoc(Map<String,Object> dto,String parent){
		String FILE_SEPARATOR =System.getProperty("line.separator");  
		StringBuilder result=new StringBuilder();
		Set<String> keys=dto.keySet();
		for(String key:keys){
			Object value=dto.get(key);
			if(value!=null&&value instanceof Map){
				result.append(outDoc((Map<String, Object>) value,key));
			}else{
				result.append("<tr>").append(FILE_SEPARATOR);
				result.append("\t<td>"+key+"<br></td>").append(FILE_SEPARATOR);
				result.append("\t<td>"+getType(value)+"<br></td>").append(FILE_SEPARATOR);
				result.append("\t<td>否<br></td>").append(FILE_SEPARATOR);
				if(parent!=null&&!"".equals(parent)){
					result.append("\t<td>"+parent+"节点内<br></td>").append(FILE_SEPARATOR);
				}else{
					result.append("\t<td><br></td>").append(FILE_SEPARATOR);
				}
				result.append("<tr>").append(FILE_SEPARATOR);
			}
			
		}
		return result.toString();
	}
	private static String getType(Object value){
		try {
			Integer.parseInt(value.toString());
			return "int";
		} catch (NumberFormatException e) {
			return "string";
		}
	}
}
