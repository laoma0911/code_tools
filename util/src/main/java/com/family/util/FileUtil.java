package com.family.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.family.util.constaints.Constants;




/**
 * 操作文件工具类
 * 
 * @date Jul 29, 2013 8:11:02 PM
 * @author wuzl
 * @comment
 */
public class FileUtil {
	private final static String FILE_SEPARATOR =System.getProperty("line.separator");  
	private static final Logger log = Logger.getLogger(FileUtil.class);
	/**
	 * 验证后写入文件
	 * @param msg
	 * @param fileName
	 * @return
	 */
	public static boolean   writeAndValidateFile(String msg,String fileName){
		return writeFile(msg, fileName, true);
	}
	/**
	 * 写入文件可以覆盖
	 * @param msg
	 * @param fileName
	 * @return
	 * @comment
	 */
	public static boolean   writeFile(String msg,String fileName){
		return writeFile(msg, fileName, false);
	}
	/**
	 * 写入文件
	 * @param msg
	 * @param fileName
	 * @param checkFileHave
	 * @return
	 */
	public static boolean   writeFile(String msg,String fileName,boolean checkFileHave){
		boolean relust=true;
		File file = new File(fileName);
		if(checkFileHave&&file.exists()){
			throw new RuntimeException("文件已经存在，请不要覆盖!");
		}
		FileOutputStream outSTr = null;    
		OutputStreamWriter write=null;
        try {    
        	long begin = System.currentTimeMillis(); 
            outSTr = new FileOutputStream(file);   
            write=new OutputStreamWriter(outSTr,Constants.DEFAULT_ENCODED);
            write.write(msg);
            long end = System.currentTimeMillis();    
            log.error("生成文件耗时:" + (end - begin) + " 豪秒");    
        } catch (Exception e) {   
        	relust=false;
        	throw e;    
        }finally {   
        	try{
            	if(write!=null){
            		write.close();
            	}
            }catch (Exception e) {    
            	throw new RuntimeException(e.getMessage());    
            }
            try {    
                if(outSTr!=null){
                	outSTr.close();
                }
            } catch (Exception e) {    
            	throw new RuntimeException(e.getMessage());     
            }    
            return relust;
        } 
	}
	/**
	 * 获取文件内容
	 * @param filePath
	 * @return
	 */
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
	/**
	 * 验证文件所在文件夹是否存在
	 * @param filePath
	 * @return
	 * @date Jul 31, 2013 2:09:44 PM
	 * @author wuzl
	 * @throws Exception 
	 * @comment
	 */
	public static boolean validateDirectoryHave(String filePath) throws RuntimeException{
		String breakSign="/";
		if(filePath.indexOf(breakSign)<0){
			breakSign="\\";
		}
		if(filePath.indexOf(breakSign)<0){
			throw new RuntimeException("文件路径有问题");
		}
		String directoryPath=filePath.substring(0,filePath.lastIndexOf(breakSign));
		File directory=new File(directoryPath);
		return directory.exists();
	}
	/**
	 * 检查文件或文件夹是否存在
	 * @param filePath
	 * @return
	 * @throws RuntimeException
	 */
	public static boolean validateFileOrDirectoryHave(String filePath) throws RuntimeException{
		File directory=new File(filePath);
		return directory.exists();
	}
	/**
     * 获取路径下的直接文件或者文件本身，不报错路径下目录内的文件
     * @param upFilePath
     * @return
     * @date Nov 3, 2013 12:39:02 PM
     * @author wuzl
     * @comment
     */
    public static List<File> getDirectFileList(String upFilePath){  
    	return FileUtil.getDirectFileList(new File(upFilePath));
    }
    /**
     * 获取路径下所有文件及文件夹下的文件（递归出所有）
     * @param upFilePath
     * @return
     * @date Dec 9, 2013 2:30:35 PM
     * @author wuzl
     * @comment
     */
    public static List<File> getPathAllFileList(File upFilePath){
    	List<File> filelList=new ArrayList<File>();
    	if(upFilePath.isFile()){
    		/*1.如果指定路径是文件，直接返回文件路径*/
    		filelList.add(upFilePath);
    	}else if(upFilePath.isDirectory()){
    		/*2.如果指定路径是文件夹，直接返回文件夹所有的文件*/
    		File[] listFiles= upFilePath.listFiles();
    		for(File file:listFiles){
    			if(file.isFile()){
    				/*2.1 把指定路径下的文件加入到返回列表中*/
    				filelList.add(file);
    			}else{
    				/*2.2 把指定路径下的文件夹下的文件递归加入到返回列表中*/
    				filelList.addAll(getPathAllFileList(file));
    			}
    		}
    	}
    	return filelList;
    }
    /**
     * 获取路径下所有指定类型的文件 文件夹下的（递归出所有）
     * @param upFilePath
     * @param fileSuffix
     * @return
     * @date Dec 9, 2013 2:48:45 PM
     * @author wuzl
     * @comment
     */
    public static List<File> getPathAllFileListByFileSuffixIgnoreCase(File upFilePath,String fileSuffix){
    	List<File> filelList=new ArrayList<File>();
    	if(upFilePath.isFile()){
    		/*1.如果指定路径是文件，并且后缀正确*/
    		if(checkFileSuffixIgnoreCase(upFilePath, fileSuffix)){
    			filelList.add(upFilePath);
    		}
    	}else if(upFilePath.isDirectory()){
    		/*2.如果指定路径是文件夹，直接返回文件夹所有的文件*/
    		File[] listFiles= upFilePath.listFiles();
    		for(File file:listFiles){
    			if(file.isFile()){
    				/*2.1 把指定路径下的文件加入到返回列表中，并且后缀正确*/
    	    		if(checkFileSuffixIgnoreCase(file, fileSuffix)){
    	    			filelList.add(file);
    	    		}
    			}else{
    				/*2.2 把指定路径下的文件夹下的带有指定后缀的文件递归加入到返回列表中*/
    				filelList.addAll(getPathAllFileListByFileSuffixIgnoreCase(file,fileSuffix));
    			}
    		}
    	}
    	return filelList;
    }
    /**
     * 检查文件的后缀名（不区分大小写）
     * @param file
     * @param fileSuffix
     * @return
     * @date Dec 9, 2013 2:51:12 PM
     * @author wuzl
     * @comment
     */
    public static boolean checkFileSuffixIgnoreCase(File file,String fileSuffix){
    	String fileName=file.getName();
    	return fileName.toLowerCase().endsWith("."+fileSuffix.toLowerCase());
    }
    /**
     * 检查文件的后缀名（区分大小写）
     * @param file
     * @param fileSuffix
     * @return
     * @date Dec 9, 2013 2:51:12 PM
     * @author wuzl
     * @comment
     */
    public static boolean checkFileSuffix(File file,String fileSuffix){
    	String fileName=file.getName();
    	return fileName.endsWith("."+fileSuffix);
    }
	/**
     * 获取路径下的直接文件或者文件本身，不获取路径下目录内的文件
     * @param upFilePath
     * @return
     * @date Nov 3, 2013 12:39:02 PM
     * @author wuzl
     * @comment
     */
    public static List<File> getDirectFileList(File upFilePath){  
    	List<File> filelList=new ArrayList<File>();
    	if(upFilePath.isFile()){
    		/*1.如果指定路径是文件，直接返回文件路径*/
    		filelList.add(upFilePath);
    	}else if(upFilePath.isDirectory()){
    		/*2.如果指定路径是文件夹，直接返回文件夹所有的文件*/
    		File[] listFiles= upFilePath.listFiles();
    		/*2.1 把指定路径下的所有文件加入到返回列表中*/
    		for(File file:listFiles){
    			if(file.isFile()){
    				filelList.add(file);
    			}
    		}
    		/*2.2 如果指定路径下没有文件 返回错误信息*/
    		if(filelList.isEmpty()){
    			throw new RuntimeException("指定的路径下，没有文件，请指定要上传的文件的上层目录");
    		}
    	}
    	return filelList;
    }
    /**
     * 新建文件 如果没有上层文件夹递归建立
     * @param filePath
     */
    public static void createNewFile(String filePath){
    	File newFile=new File(filePath);
    	if(newFile.exists()){
    		return ;
    	}
    	File parentFile=newFile.getParentFile();
    	if(!parentFile.exists()){
    		parentFile.mkdirs();
    	}
    	try {
			newFile.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException("新建文件【"+filePath+"】失败："+e.getMessage());
		}
    }
    /**
     * 新建文件 如果没有上层文件夹递归建立
     * @param filePath
     */
    public static void createNewFile(File newFile){
    	if(newFile.exists()){
    		return ;
    	}
    	File parentFile=newFile.getParentFile();
    	if(!parentFile.exists()){
    		parentFile.mkdirs();
    	}
    	try {
			newFile.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException("新建文件【"+newFile.getPath()+"】失败："+e.getMessage());
		}
    }
	public static void main(String[] args) throws Exception {
//		String filePath="E:/workspace/service";
//		List<File> fileList=FileUtil.getPathAllFileListByFileSuffixIgnoreCase(new File(filePath),"java");
//		for(File file:fileList){
//			System.out.println(file.getName());
//		}
//		System.out.println(FileUtil.checkFileSuffixIgnoreCase(new File("E:/workspace/util/src/main/java/com/family/util/Constants.Java"), "java"));
		System.out.println(getFileContent("/home/wuzl/桌面/WuzlStartService.java"));
//		createNewFile("/home/wuzl/asdfad/asdf/asdf.txt");
	}
	
}
