package com.family.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnyGetter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * http访问的工具类
 * 
 * @date Feb 28, 2014 9:43:40 AM
 * @author wuzl
 * @comment
 */
public class HttpClientUtil {
	private static final Logger log = Logger.getLogger(HttpClientUtil.class);

	/**
	 * 多次访问页面
	 * 
	 * @param url
	 * @param visitCout
	 * @return
	 * @date Feb 28, 2014 9:47:04 AM
	 * @author wuzl
	 * @comment如果visitcount 小于0一直访问知道1w次
	 */
	public static void visitPageByCount(String url, int visitCout) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.setURI(new URI(httpget.getURI().toString()));
			/* 1.访问次数 */
			int visitNum = 0;
			/* 2.循环访问 如果visitCout小于0一直访问 */
			while (visitCout < 0 || visitNum++ < visitCout) {
				HttpResponse httpresponse = httpClient.execute(httpget);
				HttpEntity entity = httpresponse.getEntity();
				String body = EntityUtils.toString(entity);
				log.error("请求url：" + url + "第" + visitNum + "次返回结果:" + body);
				if (visitNum >= 10000) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 发送一次post请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 * @date Mar 8, 2014 4:19:27 PM
	 * @author wuzl
	 * @comment
	 */
	public static String sendHttpPostRequest(String url,
			Map<String, String> paramMap) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (paramMap != null) {
				// 循环加入请求参数
				for (String paramName : paramMap.keySet()) {
					nvps.add(new BasicNameValuePair(paramName, paramMap
							.get(paramName)));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				throw new RuntimeException("服务器异常稍后再试:" + url);
			}
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 发送一个带文件的post请求
	 * 
	 * @param url
	 * @param paramMap
	 * @param fileParamMap
	 * @return
	 */
	public static String sendHttpPostRequestHaveFile(String url,
			Map<String, String> paramMap, Map<String, File> fileParamMap) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			MultipartEntity reqEntity = new MultipartEntity();
			if (paramMap != null) {
				// 循环加入请求参数
				for (String paramName : paramMap.keySet()) {
					reqEntity.addPart(
							paramName,
							new StringBody(paramMap.get(paramName), Charset
									.forName("UTF-8")));
				}
			}
			if (fileParamMap != null) {
				// 循环加入请求文件参数
				for (String fileName : fileParamMap.keySet()) {
					reqEntity.addPart(fileName,
							new FileBody(fileParamMap.get(fileName)));
				}
			}
			httpPost.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(httpPost);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				throw new RuntimeException("服务器异常稍后再试:" + url);
			}
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 发送一个带文件的post请求
	 * 
	 * @param url
	 * @param paramMap
	 * @param fileParamMap
	 * @return
	 */
	public static String sendHttpPostRequestHaveFileInputStream(String url,
			Map<String, String> paramMap, Map<String, InputStream> fileParamMap) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			MultipartEntity reqEntity = new MultipartEntity();
			if (paramMap != null) {
				// 循环加入请求参数
				for (String paramName : paramMap.keySet()) {
					reqEntity.addPart(
							paramName,
							new StringBody(paramMap.get(paramName), Charset
									.forName("UTF-8")));
				}
			}
			if (fileParamMap != null) {
				// 循环加入请求文件参数
				for (String fileName : fileParamMap.keySet()) {
					reqEntity.addPart(fileName, new InputStreamBody(
							fileParamMap.get(fileName), fileName));
				}
			}
			httpPost.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(httpPost);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				throw new RuntimeException("服务器异常稍后再试:" + url);
			}
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 发送一次get请求带header
	 * 
	 * @param url
	 * @param headerMap
	 * @param paramMap
	 * @return
	 */
	public static String sendHttpGetRequestAndHeader(String url,
			Map<String, String> headerMap, Map<String, String> paramMap) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String paramStr = "";
			if (paramMap != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 循环加入请求参数
				for (String paramName : paramMap.keySet()) {
					// params.add(new BasicNameValuePair(paramName,
					// URLEncoder.encode(paramMap
					// .get(paramName), "utf-8")));
					params.add(new BasicNameValuePair(paramName, paramMap
							.get(paramName)));
				}
				paramStr = EntityUtils
						.toString(new UrlEncodedFormEntity(params));
			}
			HttpGet httpget = new HttpGet(url + "?" + paramStr);
			httpget.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
			if (headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse response = httpClient.execute(httpget);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				throw new RuntimeException("服务器异常稍后再试:" + url);
			}
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 发送一次post请求，并且对请求参数进行加密
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 * @date Mar 8, 2014 4:19:27 PM
	 * @author wuzl
	 * @comment
	 */
	public static String sendHttpPostRequestEncryptRequstParam(String url,
			Map<String, String> paramMap) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (paramMap != null) {
				// 循环加入请求参数
				for (String paramName : paramMap.keySet()) {
					nvps.add(new BasicNameValuePair(paramName, Java3DESUtil
							.encryptThreeDESECB(paramMap.get(paramName))));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpClient.execute(httpPost);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				throw new RuntimeException("服务器异常稍后再试:" + url);
			}
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 发送一个post请求，带有header
	 * 
	 * @param url
	 * @param headerMap
	 * @param paramMap
	 * @return
	 */
	public static String sendHttpPostRequestAndHeader(String url,
			Map<String, String> headerMap, Map<String, String> paramMap) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			if (headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (paramMap != null) {
				// 循环加入请求参数
				for (String paramName : paramMap.keySet()) {
					nvps.add(new BasicNameValuePair(paramName, paramMap
							.get(paramName)));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
					return EntityUtils.toString(response.getEntity());
				}
				throw new RuntimeException("服务器异常稍后再试:" + url);
			}
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 发送一个post json请求，带有header
	 * 
	 * @param url
	 * @param headerMap
	 * @param obj
	 *            请求参数
	 * @return
	 */
	public static String sendHttpJsonPostRequestAndHeader(String url,
			Map<String, String> headerMap, Object obj) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			if (headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			StringEntity entity = new StringEntity(JSON.toJSONString(obj),
					"utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
					return EntityUtils.toString(response.getEntity());
				}
				throw new RuntimeException("服务器异常稍后再试:" + url);
			}
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException {
		Map paramMap = new HashMap();
		// paramMap.put("uid", "1");
		// paramMap.put("token", "1#addd");
		// 已经过期的
		// paramMap.put("token", "93372#addd");
		// 还没过期的
		// 测试登陆
		paramMap.put("username", "1");
		// paramMap.put("uid", "37888283");
		paramMap.put("password", "123456");
		paramMap.put("invitationcode", "8378992256");
		paramMap.put("captcha", "captcha");

		paramMap.put("link", "http://www.dayima.com/products/index");

		paramMap.put("ruleKey", "Upgrade");
		paramMap.put("softsource", "ubaby");
		// paramMap.put("uid", "1");
		paramMap.put("platform", "android");
		paramMap.put("ver", "188");
		paramMap.put("sign", "1.65");
		paramMap.put("timestamp", System.currentTimeMillis() + "");
		paramMap.put("token", "40#137fd05b88a23021e6d4570a8a91a37d");
		paramMap.put("age", "22");
		paramMap.put("period", "99");
		paramMap.put("period_index", "199");
		paramMap.put("cycle", "33");
		paramMap.put("menarche", "55");
		paramMap.put("last_period", "66");
		paramMap.put("code", "2455");
		paramMap.put("channel", "dayima sasd");
		paramMap.put("noinit", "0");
		paramMap.put("password", "2455");
		// System.out.println(HttpClientUtil.sendHttpPostRequest(
		// "http://uicapi.test.yoloho.com/user/reg",
		// paramMap));
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateline", "20150822");
		map.put("eventtype", "1");
		map.put("mtime", "1407393303");
		map.put("data", "111");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("dateline", "20150823");
		map.put("eventtype", "1");
		map.put("mtime", "1407393305");
		map.put("data", "222");
		data.add(map);
		// paramMap.put("data", JSON.toJSONString(data))
		paramMap.put("str",
				"lPgGew12NKVVyQdeWoKJs8/bS4s7Da6y4KQfgVKuGDmZAN6UIGbMvAnlCpRkmWfh+Ku4c0uX6SM=");
		// paramMap.put("is_all", "1");
		// System.out.println(HttpClientUtil.sendHttpPostRequest(
		// "http://localhost:8080/forum-consumer/group/admin/permit",
		// paramMap));
//		paramMap.put("topic_id", "8318632");
//		 System.out.println(HttpClientUtil.sendHttpPostRequest("http://localhost:8080/forum-consumer/group/topic/getwithextra",paramMap));
		paramMap.put("uid", "1");
		paramMap.put("reason", "男性用户");
		paramMap.put("content", "8317100");
		//进入聊天室
		 paramMap.put("isDesc", "0");
		 paramMap.put("lastId", "46");
		 paramMap.put("broadcastId", "1");
		 System.out.println(HttpClientUtil.sendHttpPostRequest(
		 "http://localhost:8080/live-consumer/broadcast/msg/list",
		 paramMap));
		//查询消息列表
		paramMap.put("conversationId", "27000031");
		paramMap.put("groupId", "2");
//		System.out.println(HttpClientUtil.sendHttpPostRequest("http://localhost:8080/forum-consumer/admin/group/info",paramMap));
		// paramMap.put("token", "197195003#1eba9704d62ff9ac7cb474b5f8720068");
		// Map<String, File> fileParamMap=new HashMap<String, File>();
		// fileParamMap.put("img", new
		// File("/home/wuzl/下载/c9487270574c9eff3c3f099a654d6e8a.jpg"));
		// System.out.println(HttpClientUtil.sendHttpPostRequestHaveFile("https://uicapi.yoloho.com/user/im/send",paramMap,fileParamMap));
		
	}

	public static String outDoc(Map<String, Object> dto, String parent) {
		String FILE_SEPARATOR = System.getProperty("line.separator");
		StringBuilder result = new StringBuilder();
		Set<String> keys = dto.keySet();
		for (String key : keys) {
			Object value = dto.get(key);
			if (value != null && value instanceof Map) {
				result.append(outDoc((Map<String, Object>) value, key));
			} else {
				result.append("<tr>").append(FILE_SEPARATOR);
				result.append("\t<td>" + key + "<br></td>").append(
						FILE_SEPARATOR);
				result.append("\t<td>" + getType(value) + "<br></td>").append(
						FILE_SEPARATOR);
				result.append("\t<td>否<br></td>").append(FILE_SEPARATOR);
				if (parent != null && !"".equals(parent)) {
					result.append("\t<td>" + parent + "节点内<br></td>").append(
							FILE_SEPARATOR);
				} else {
					result.append("\t<td><br></td>").append(FILE_SEPARATOR);
				}
				result.append("<tr>").append(FILE_SEPARATOR);
			}

		}
		return result.toString();
	}

	private static String getType(Object value) {
		try {
			Integer.parseInt(value.toString());
			return "int";
		} catch (NumberFormatException e) {
			return "string";
		}
	}
}
