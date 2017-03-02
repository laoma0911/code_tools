package com.family.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 操作java文件的工具类
 * 
 * @date Dec 9, 2013 2:39:49 PM
 * @author wuzl
 * @comment
 */
public class JavaFileUtil {
	/**
	 * 更改package为文件路径
	 * @param packagePath
	 * @return
	 * @date Dec 9, 2013 2:40:37 PM
	 * @author wuzl
	 * @comment
	 */
	public static String changePackageToFilePath(String packagePath){
		return packagePath.replace(".", "/");
	}
	/**
	 * 更改package为文件路径
	 * @param packagePath
	 * @return
	 * @date Dec 9, 2013 2:40:37 PM
	 * @author wuzl
	 * @comment
	 */
	public static String changePackageToFilePath(String packagePath,String workspacePath){
		return workspacePath+"/"+changePackageToFilePath(packagePath);
	}
	/**
	 * 更改package为正则可以操作的text
	 * @param packagePath
	 * @return
	 * @date Dec 9, 2013 2:44:20 PM
	 * @author wuzl
	 * @comment
	 */
	public static String changePackageToRegexText(String packagePath){
		return packagePath.replace(".", "\\.");
	}
	/**
	 * 判断是否是实体类 不是接口和抽象类
	 * @param javaFile
	 * @return
	 * @date Dec 9, 2013 5:41:53 PM
	 * @author wuzl
	 * @comment
	 */
	public static boolean checkClass(File javaFile){
		boolean isClass=false;
		FileReader fileReader=null;
		BufferedReader br = null;
		try {
			fileReader= new FileReader(javaFile);
			br = new BufferedReader(fileReader);
			String code;
			/*1.循环读取代码数据*/
			while((code=br.readLine())!=null){
				/*1.1 到了类定义处*/
				Pattern pattern=Pattern.compile("public\\s+(class|interface|abstract).+");
				Matcher matcher=pattern.matcher(code);
				while(matcher.find()){
					if(matcher.group(1).toLowerCase().equals("class")){
						isClass=true;
					}
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br. close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fileReader!=null){
				try {
					fileReader. close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return isClass;
		}
		
	}
	/**
	 * 查询java文件中引入某个包下文件及使用的位置
	 * @param javaFile
	 * @param importPackagePath
	 * @return
	 * @date Dec 9, 2013 3:16:57 PM
	 * @author wuzl
	 * @comment 返回
	 *              完整信息importPackageAndJava
	 *  			引入的包名importPackage 
	 * 				类名importClassName 
	 * 				当前包名 package
	 * 				当前类名 className
	 * 				使用的方法名 userMethodName
	 *           	及相应的方法 methodName
	 *           如没有引入返回null
	 */
	public static List<Map> searchPackageJavasImportSeat(File javaFile,String importPackagePath){
		List<Map> rows=new ArrayList<Map>();
		/*1.转换成正则可以解析的格式*/
		String importPackagePathRegex=changePackageToRegexText(importPackagePath);
		/*2.获取所有非空代码行*/
		List<Map> lineCodes=getAllLineCodeFromJava(javaFile);
		boolean importBegin=false;//标记是否import了
		/*3.获取包名*/
		String packageName=getPackageName(lineCodes.get(0).get("code").toString());
		/*4.查找所有可能引用的地方*/
		for(int i=1;i<lineCodes.size();i++){
			Map codeMsg=lineCodes.get(i);
			String code=(String) codeMsg.get("code");
			/*4.1 判断是否引用 import语句 */
			if(code.matches("\\s*import.+")){
				importBegin=true;
				Pattern pattern=Pattern.compile("\\s*import\\s+("+importPackagePathRegex+"(\\w+|\\.)+);");
				Matcher matcher=pattern.matcher(code);
				while(matcher.find()){
					Map dto=new HashMap();
					String importMSG=matcher.group(1);
					dto.put("package", packageName);
					dto.put("importPackageAndJava",importMSG);
					dto.put("importPackage",importMSG.substring(0,importMSG.lastIndexOf(".")));
					dto.put("importClassName", importMSG.substring(importMSG.lastIndexOf(".")+1,importMSG.length()));
					dto.put("className",javaFile.getName().split("\\.")[0]);
					rows.add(dto);
					break;
				}
			}else{
				if(importBegin){
					/*4.2 终止判断 */	
					break;
				}
			}
		}
		return rows;
	}
	/**
	 * 获取包名
	 * @param packageAllMsg
	 * @return
	 * @date Dec 10, 2013 9:51:03 AM
	 * @author wuzl
	 * @comment
	 */
	public static String  getPackageName(String packageAllMsg){
		Pattern pattern=Pattern.compile("package\\s+((\\w+|\\.)+);");
		Matcher matcher=pattern.matcher(packageAllMsg);
		while(matcher.find()){
			return matcher.group(1);
		}
		throw new RuntimeException("找不到包名");
	}
	/**
	 * 获取java文件所有非空行的代码 
	 * @param javaFile
	 * @return
	 * @date Dec 9, 2013 3:50:07 PM
	 * @author wuzl
	 * @comment返回值中map包括code :一行的代码 isMethod ：是否是一个方法的定义 index行号
	 */
	public static List<Map> getAllLineCodeFromJava(File javaFile){
		List<Map> lineCodes=new ArrayList<Map>();
		FileReader fileReader=null;
		BufferedReader br = null;
		try {
			fileReader= new FileReader(javaFile);
			br = new BufferedReader(fileReader);
			String code;
			int index=1;//代码行数
			/*1.循环读取代码数据*/
			while((code=br.readLine())!=null){
				/*1.1 如果不是空行*/
				if(!code.matches("\\s+")&&!"".equals(code)){
					/*1.2 放入代码信息*/
					Map codeMsg=new HashMap();
					codeMsg.put("index", index);
					codeMsg.put("code", code);
					codeMsg.put("isMethod", checkIsJavaMethod(code));
					lineCodes.add(codeMsg);
				}
				index++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br. close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fileReader!=null){
				try {
					fileReader. close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return lineCodes;
	}
	/**
	 * 验证改行代码是否是一个java方法的命令(不是很可靠)
	 * @param code
	 * @return
	 * @date Dec 9, 2013 4:30:52 PM
	 * @author wuzl
	 * @comment
	 */
	public static boolean checkIsJavaMethod(String code){
		return code.matches("\\s+(public|private)\\s+[^(=]+\\(.*");
	}
	/**
	 * 从文件路径中获取package
	 * @param filePath
	 * @return
	 */
	public static String getPackageFormFilePath(String filePath){
		Pattern pattern=Pattern.compile(".*src/main/java(.*)");
		Matcher matcher=pattern.matcher(filePath);
		while(matcher.find()){
			String packageAndJava= matcher.group(1);
			if(packageAndJava.endsWith(".java")){
				packageAndJava=packageAndJava.substring(0, packageAndJava.lastIndexOf("/"));
			}
			if(packageAndJava.startsWith("/")){
				packageAndJava=packageAndJava.substring(1, packageAndJava.length());
			}
			return packageAndJava.replaceAll("/", ".");
		}
		throw new RuntimeException("找不到包名");
	}
	public static void main(String[] args) {
//		System.out.println(JavaFileUtil.changePackageToRegexText("com.family.util.constaints"));
//		System.out.println(JavaFileUtil.checkIsJavaMethod("private static void addMethod(CtClass ctClass)"));
//		System.out.println(JavaFileUtil.searchPackageJavasImportSeat(new File("E:/workspace/selfservice_new/src/com/ailk/uchannel/selfservice/service/impl/IPhoneExamineSVImpl.java"),"java.io"));
//		System.out.println(JavaFileUtil.checkClass(new File("E:/workspace/selfservice_new/src/com/ailk/uchannel/selfservice/service/interfaces/IIPhoneExamineSV.java")));
		System.out.println(getPackageFormFilePath("/home/wuzl/wuzl/workspace/dayimaworkspace/dayimaservice/uic-consumer/src/main/java/com/dayima/uic/consumer/action/asd.java"));
	}
}

