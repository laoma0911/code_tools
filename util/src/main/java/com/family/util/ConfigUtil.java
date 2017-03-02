package com.family.util;



/**
 * 配置及转化字符串格式的工具类
 * 
 * @date Jun 19, 2013 1:35:13 AM
 * @author wuzl
 * @comment
 */
public class ConfigUtil {
	/**
	 * 转化字段的格式 下划线转成驼峰
	 * @param oldName
	 * @return
	 * @date May 23, 2013 2:40:49 PM
	 * @author wuzl
	 * @comment
	 */
	public static String changeUnderLineToHump(String oldName){
		if(StringUtil.isBlank(oldName)){
			return "";
		}
		oldName=oldName.toLowerCase();
		String[] nameArray=oldName.split("_");
		StringBuilder result=new StringBuilder(nameArray[0]);
		for(int i=1;i<nameArray.length;i++){
			String namePart=nameArray[i];
			result.append(namePart.substring(0,1).toUpperCase());
			result.append(namePart.substring(1,namePart.length()));
		}
		return result.toString();
	}
	/**
	 * 转化字段的格式 下划线转成驼峰并首字母大写
	 * @param oldName
	 * @return
	 * @date May 23, 2013 2:40:49 PM
	 * @author wuzl
	 * @comment
	 */
	public static String changeUnderLineToHumpAndFontUp(String oldName){
		String humpName=ConfigUtil.changeUnderLineToHump(oldName);
		humpName=humpName.substring(0,1).toUpperCase()+humpName.substring(1,humpName.length());
		return humpName;
	}
	/**
	 * 转化首字母为大写，要求输入字符串大于1的长度，并且首字母是小写字母
	 * @param oldText
	 * @return
	 * @throws Exception
	 * @date Jun 19, 2013 1:50:13 AM
	 * @author wuzl
	 * @comment
	 */
	public static String changeFirstToUp(String oldText) throws Exception{
		if(StringUtil.isBlank(oldText)){
			throw new Exception("输入字段长度必须大于1！");
		}
		String firstLetter=oldText.substring(0,1);
		if(!firstLetter.matches("[a-z]")){
			throw new Exception("输入字段首字母必须为小写字母！");
		}
		return firstLetter.toUpperCase()+oldText.substring(1,oldText.length());
	}
	/**
	 * 生成set格式
	 * @param field
	 * @return
	 * @date Jun 19, 2013 1:45:25 PM
	 * @author wuzl
	 * @throws Exception 
	 * @comment
	 */
	public static String changeToGetter(String field) throws Exception{
		return "get" + ConfigUtil.changeFirstToUp(field);
	}
	/**
	 * 转化字段的格式 下划线转成驼峰并首字母大写,其他字母为小写
	 * @param oldName
	 * @return
	 * @date May 23, 2013 2:40:49 PM
	 * @author wuzl
	 * @comment
	 */
	public static String changeUnderLineToHumpAndFontUpOtherLower(String oldName){
		String humpName=ConfigUtil.changeUnderLineToHump(oldName.toLowerCase());
		humpName=humpName.substring(0,1).toUpperCase()+humpName.substring(1,humpName.length());
		return humpName;
	}
	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
	public static String fontCharToLower(String str){
		return str.substring(0,1).toLowerCase()+str.substring(1,str.length());
	}
	/**
	 * 生成set格式
	 * @param field
	 * @return
	 * @throws Exception
	 * @date Jun 19, 2013 1:46:03 PM
	 * @author wuzl
	 * @comment
	 */
	public static String changeToSetter(String field) throws Exception{
		return "set" + ConfigUtil.changeFirstToUp(field);
	}
	/**
	 * 包路径转相对路径
	 * @param packageName
	 * @return
	 */
	public static String changePackageToRelativePath(String packageName){
		String FILE_SEPARATOR =System.getProperty("file.separator"); 
		return  FILE_SEPARATOR+packageName.replace(".", FILE_SEPARATOR);
	}
	public static void main(String[] args) {
		System.out.println(ConfigUtil.changeUnderLineToHumpAndFontUpOtherLower("asdfGFSADF_ASD"));;
		System.out.println(ConfigUtil.changePackageToRelativePath("com.family.util"));;
	}
}
