package com.family.tools.code.create.action;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSON;
import com.family.tools.code.create.action.support.MsgBean;
import com.family.util.constaints.Constants;

/**
 * 生成api信息
 * 
 * @author wuzl
 * 
 */
@Controller
@RequestMapping("/api")
public class CreateApiAction implements ApplicationContextAware {
	private WebApplicationContext applicationContext;
	static String PROJECT_PATH = "/home/wuzl/dayimaworkspace/forumapi/forum-consumer";
	static String  SRC_PATH = PROJECT_PATH + "/src/main/java/";
	private final static String FILE_SEPARATOR =System.getProperty("line.separator");  
	
	static Pattern REQUEST_PARAM_PATTERN=Pattern.compile("\\s*@RequestParam\\s*\\(\\s*(value\\s*=\\s*)?\"([^\"]+)\"\\s*");
	/**
	 * 保存请求信息
	 * @author wuzl
	 *
	 */
	class RequestVo{
		private String requestUrl;
		private Class clazz;
		private String method;
		private Class<?>[] methodParamTypes;
		public String getRequestUrl() {
			return requestUrl;
		}
		public void setRequestUrl(String requestUrl) {
			this.requestUrl = requestUrl;
		}
		public Class getClazz() {
			return clazz;
		}
		public void setClazz(Class clazz) {
			this.clazz = clazz;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public Class<?>[] getMethodParamTypes() {
			return methodParamTypes;
		}
		public void setMethodParamTypes(Class<?>[] methodParamTypes) {
			this.methodParamTypes = methodParamTypes;
		}
	}
	/**
	 * java方法类的信息 
	 * @author wuzl
	 *
	 */
	static class JavaMethodMsg{
		private String note;
		private String params;
		private Map<String,String> paramNoteMaps;
		public Map<String, String> getParamNoteMaps() {
			return paramNoteMaps;
		}
		public void setParamNoteMaps(Map<String, String> paramNoteMaps) {
			this.paramNoteMaps = paramNoteMaps;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		public String getParams() {
			return params;
		}
		public void setParams(String params) {
			this.params = params;
		}
	}
	/**
	 * 生成api
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/craete")
	public @ResponseBody
	Object createApi(HttpServletRequest request) {
		MsgBean msg = new MsgBean();
		List<Map<String,String>> jsonArray=new ArrayList<Map<String,String>>();//保存json
		try {
			/*1.获取所有请求合计*/
			Map<String, List<RequestVo>> allRequestMap=this.getAllRequetMap();
			/*2.循环处理数据*/
			for(String className:allRequestMap.keySet()){
				List<RequestVo> rows=allRequestMap.get(className);
				/*2.1 获取java类文件内容*/
				String javaContent=this.getJavaFileContent(className);
				/*2.2处理一个控制器下边的所有请求api*/
				for(RequestVo vo:rows){
					String methodName=vo.getMethod();
					String requstUrl=vo.getRequestUrl();
					Class<?>[] methodTypes=vo.getMethodParamTypes();
					JavaMethodMsg methodMsg=this.getJavaMethodMsg(javaContent, methodName);//获取文件里的注释和方法名称
					this.createApiForLab(jsonArray,requstUrl,methodMsg.getParams(),methodMsg.getNote(),methodMsg.getNote(),methodMsg.getParamNoteMaps());//生成api的json
				}
			}
			Map<String,Object> apiResult=new HashMap<String,Object>();
			apiResult.put("success", true);
			apiResult.put("rows", jsonArray);
			System.out.println(JSON.toJSON(apiResult));
			msg.put("apiJson", apiResult);
		} catch (Exception e) {
			msg.setFailure( e.getMessage());
			msg.put("total", "0");
			msg.put("rows", "[]");
			e.printStackTrace();
		}
		return msg.returnMsg();
	}
	private JavaMethodMsg getJavaMethodMsg(String javaContent,String methodName){
		JavaMethodMsg methodMsg=new JavaMethodMsg();
		Pattern pattern=Pattern.compile("/\\*([^/]+)\\*/\\s+@RequestMapping\\([^\\)]+\\)\\s+public\\s+@ResponseBody\\s+Object\\s+"
		+methodName+"\\s*(\\([^{]*\\))");
		Matcher mather=pattern.matcher(javaContent);
		if(mather.find()){
			String noteMatcher=mather.group(1).replaceAll("\\s+(\\*)", "$1");
			String[] noteArray=noteMatcher.split("\\*");
			String title=null;
			Map<String,String> paramNoteMap=new HashMap<String,String>();
			for(String s:noteArray){
				if(s!=null&&!s.equals("")&&!s.matches("\\s+")){
					if(title==null){
						title=s;
					}else{
						if(!(s.indexOf("@return")>-1)){
							String[] paramNoteArray=s.split("@param");
							for(String paramFullNote:paramNoteArray){
								if(paramFullNote!=null&&!paramFullNote.equals("")&&!paramFullNote.matches("\\s+")){
									String[] paramFullNoteArray=paramFullNote.split("\\s+");
									String param=null;
									String paramNote="";
									for(String paramMsg:paramFullNoteArray){
										if(paramMsg!=null&&!paramMsg.equals("")&&!paramMsg.matches("\\s+")){
											if(param==null){
												param=paramMsg;
											}else{
												paramNote+=paramMsg;
											}
										}
									}
									if(!paramNote.equals("")){
										paramNoteMap.put(param, paramNote);
									}
								}
							}
						}
					}
				}
					
			}
			methodMsg.setNote(title);
			Map<String, String> paramNoteMapTrue=new HashMap<String, String> ();
			String paramMatcher=mather.group(2);
			if(isNotEmpty(paramMatcher)&&paramMatcher.length()>2){
				paramMatcher=paramMatcher.substring(1);
				paramMatcher=paramMatcher.substring(0,paramMatcher.length()-1);
				String[] paramArray=paramMatcher.split(",");
				StringBuilder params=new StringBuilder();
				for(String param:paramArray){
					if(isNotEmpty(param)){
						if(!param.matches("\\s+BaseVo\\s+.*")){
							Matcher matcher=REQUEST_PARAM_PATTERN.matcher(param);
							if(matcher.find()){
								String resultParam=matcher.group(2);
								params.append(resultParam).append(",");
								if(paramNoteMap.containsKey(resultParam)){
									paramNoteMapTrue.put(resultParam, paramNoteMap.get(resultParam));
								}
							}else {
								if(!param.contains("(")&&!param.contains(")")){
									String[] simpleParams=param.split("\\s+");
									boolean haveType=false;
									for(String simpleParam:simpleParams){
										if(isNotEmpty(simpleParam)){
											if(!haveType){
												haveType=true;
											}else{
												params.append(simpleParam).append(",");
												if(paramNoteMap.containsKey(simpleParam)){
													paramNoteMapTrue.put(simpleParam, paramNoteMap.get(simpleParam));
												}
											}
										}
									}
									
								}
							}
						}
					}
				}
				
				methodMsg.setParamNoteMaps(paramNoteMapTrue);
				params.deleteCharAt(params.lastIndexOf(","));
				methodMsg.setParams(params.toString());
			}
			
		}else{
			
		}
		return methodMsg;
	}
	private Map<String, List<RequestVo>> getAllRequetMap(){
		Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext,
						HandlerMapping.class, true, false);
		Map<String ,List<RequestVo>> allRequestMap=new HashMap<String ,List<RequestVo>>();// 保存所有请求的分组 key是所在类名称 
		for (HandlerMapping handlerMapping : allRequestMappings.values()) {
			if (handlerMapping instanceof RequestMappingHandlerMapping) {
				RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
				Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping
						.getHandlerMethods();
				for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods
						.entrySet()) {
					RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry
							.getKey();
					HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry
							.getValue();

					RequestMethodsRequestCondition methodCondition = requestMappingInfo
							.getMethodsCondition();
					// Set<RequestMethod>
					// methods=methodCondition.getMethods();//请求方法类型
					PatternsRequestCondition patternsCondition = requestMappingInfo
							.getPatternsCondition();
					Set<String> requestUrls = patternsCondition
							.getPatterns();// 请求url[/my/group/have]
					String apiUrl=requestUrls.iterator().next();
					Class controllerClass = mappingInfoValue.getBeanType();// 控制器名称class com.yoloho.forum.action.MyAction
					
					String requestMethodName = mappingInfoValue.getMethod()
							.getName();// 请求具体方法hasGroup
					
					Class<?>[] methodParamTypes = mappingInfoValue
							.getMethod().getParameterTypes();// 请求参数类型
					RequestVo vo=new RequestVo();
					vo.setClazz(controllerClass);
					vo.setRequestUrl(apiUrl);
					vo.setMethod(requestMethodName);
					vo.setMethodParamTypes(methodParamTypes);
					this.putMap(allRequestMap, controllerClass.getName(), vo);
				}
				break;
			}
		}
		return allRequestMap;
	}
	private void putMap(Map<String ,List<RequestVo>> allRequestMap,String clazzName,RequestVo vo){
		List<RequestVo>  rows=allRequestMap.get(clazzName);
		if(rows!=null){
			rows.add(vo);
		}else{
			rows=new ArrayList<RequestVo>();
			rows.add(vo);
			allRequestMap.put(clazzName, rows);
		}
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = (WebApplicationContext) applicationContext;
	}

	private void createApiForLab(List<Map<String,String>>  jsonArray,String apiUrl,String paramMsg,String apiName,String desc,Map<String, String> paramNotes) {
		Map<String,String> dto=new HashMap<String,String>();
		dto.put("api", apiUrl);
		dto.put("arg", paramMsg==null?"":paramMsg);
		dto.put("name", apiName);
		StringBuilder note;
		note = new StringBuilder(apiName==null?"":apiName).append("\n");
		if(paramNotes!=null){
			for(String param:paramNotes.keySet()){
				note.append(param).append(":").append(paramNotes.get(param)).append("\n");
			}
		}
		dto.put("desc", note.toString());
		
		jsonArray.add(dto);
	}
	/**
	 * 获取文件信息
	 * @param clazz
	 * @return
	 */
	private String getJavaFileContent(String clazzName){
		StringBuilder content=new StringBuilder();
		String javaPath=clazzName.replace(".", "/");
		return getFileContent(SRC_PATH+javaPath+".java");
	}
	public static String getFileContent(String filePath){
		StringBuilder content=new StringBuilder();
		File file = new File(filePath);
		if(!file.exists()){
			throw new RuntimeException("文件【"+filePath+"】不存在!");
		}
		FileReader  fr= null;
		BufferedReader br =null;
		try{
			fr=new FileReader(file);
			br=new BufferedReader(fr);
			String data = br.readLine();
			while(data!=null){
				content.append(data+FILE_SEPARATOR);
				data = br.readLine(); //接着读下一行  
			}
		}catch (Exception e) {   
        	throw e;    
        }finally {   
        	try {    
                if(br!=null){
                	br.close();
                }
            } catch (Exception e) {    
            	throw new RuntimeException(e.getMessage());     
            } 
        	try{
            	if(fr!=null){
            		fr.close();
            	}
            }catch (Exception e) {    
            	throw new RuntimeException(e.getMessage());    
            }
            return content.toString();
        } 
	}
	public static boolean isNotEmpty(String s){
		return s!=null&&!s.equals("")&&!s.matches("\\s+");
	}
}
