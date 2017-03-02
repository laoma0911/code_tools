package com.family.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.family.util.constaints.Constants;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 操作SFTP操作类
 * 
 * @date Nov 2, 2013 8:57:13 PM
 * @author wuzl
 * @comment
 */
public class SFTPOperate {
	private static final Logger log = Logger.getLogger(SFTPOperate.class);
	private String host = "192.168.109.130";  
    private String username="wuzl";  
    private String password="19871221";  
    private int port = 22;  
    private String localPath = "E:/workspace/myhadoop/target";  
    private String remotePath = "/home/wuzl/";  
    //默认只上传文件
    private boolean isFile=true;
    //默认不允许覆盖已有文件
    private boolean cover=false;
    private ChannelSftp sftp = null; 
    /**
     * 有参构造函数
     * @param host
     * @param port
     * @param username
     * @param password
     * @date Nov 2, 2013 9:56:35 PM
     * @author wuzl
     * @comment
     */
    public  SFTPOperate(String host,int port,String username,String password){
    	this.host=host;
    	this.port=port;
    	this.username=username;
    	this.password=password;
    }
    public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getRemotePath() {
		return remotePath;
	}
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}
	public boolean isCover() {
		return cover;
	}
	public void setCover(boolean cover) {
		this.cover = cover;
	}
	/**
     * 无参数构造
     * 
     * @date Nov 2, 2013 9:59:04 PM
     * @author wuzl
     * @comment
     */
    public  SFTPOperate(){
    	
    }
    /**
     * 连接
     * 
     * @date Nov 2, 2013 9:56:48 PM
     * @author wuzl
     * @comment
     */
    public void connect() {  
        try {  
            if(sftp != null){  
                return;
            }  
            JSch jsch = new JSch();  
            jsch.getSession(username, host, port);  
            Session sshSession = jsch.getSession(username, host, port);  
            sshSession.setPassword(password);  
            Properties sshConfig = new Properties();  
            sshConfig.put("StrictHostKeyChecking", "no");  
            sshSession.setConfig(sshConfig);  
            sshSession.connect();  
            Channel channel = sshSession.openChannel("sftp");  
            channel.connect();  
            sftp = (ChannelSftp) channel;  
            log.error("sftp连接成功");
        } catch (Exception e) {  
            e.printStackTrace(); 
            throw new RuntimeException("连接SFTP失败，失败原因："+e.getMessage());
        }  
    }  
    /**
     * 关闭连接
     * 
     * @date Nov 2, 2013 9:59:25 PM
     * @author wuzl
     * @comment
     */
    public void disconnect() {  
        if(this.sftp != null){  
            if(this.sftp.isConnected()){  
            	try {
					this.sftp.getSession().disconnect();
				} catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                this.sftp.disconnect();  
                log.error("sftp关闭成功");
            }else if(this.sftp.isClosed()){  
            	throw new RuntimeException("SFTP已经关闭!"); 
            }  
        }  
  
    } 
    /**
     * 上传文件
     * 
     * @date Nov 3, 2013 12:17:18 PM
     * @author wuzl
     * @comment
     */
    public void upload() {  
    	File upFilePath=new File(this.localPath);  
    	/*1.如果上传路径是文件夹且要求上传的是文件，提示错误*/
    	if(upFilePath.isDirectory()&&this.isFile==true){
    		throw new RuntimeException("请指定上传的文件，如果要上传文件夹，请设置isFile为false");
    	}
    	/*2 进入指定的上传路径*/
		try {
			this.sftp.cd(this.remotePath);
		} catch (SftpException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的上传路径不存在");
		}
    	/*3.获取指定路径下的所有直接文件，或本件本身*/
    	List<File> fileList=FileUtil.getDirectFileList(upFilePath);
    	for(File upFile:fileList){
    		/*3.1 拼接上传的路径*/
    		String remoteFile = remotePath +Constants.FILE_SEPERATOR + upFile.getName();  
    		/*3.2 判断是否可以覆盖*/
    		if(!this.cover){
    			try {
    				/*3.2.1 如果不可以覆盖判断文件是否已经存在*/
    				Vector existFileVector=sftp.ls(remoteFile);
    				if(!existFileVector.isEmpty()){
    					throw new RuntimeException("要上传的文件已经存在，如果想要覆盖，请设置covre为true");
    				}
    			} catch (SftpException e1) {
    				/*3.2.2 如果catch到说明是文件没有存在 不处理*/
    			}
    		}
    		FileInputStream fileInputStream=null;
    		String errmsg=null;
    		/*3.3 上传*/
    		try {
				fileInputStream = new FileInputStream(upFile);
				sftp.put(fileInputStream, remoteFile);
				log.error("sftp上传成功");
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				errmsg=e.getMessage();
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(fileInputStream!=null){
					try {
						fileInputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(!StringUtil.isBlank(errmsg)){
					throw new RuntimeException(errmsg);
				}
			}
    	}
          
    }
    /**
     * 下载文件
     * @param remotePath
     * @param downPath
     * @date Nov 3, 2013 4:57:07 PM
     * @author wuzl
     * @comment
     */
    public void downFile(String remotePath,String downFileName,String downPath){
    	try {
			this.sftp.cd(remotePath);
		} catch (SftpException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的下载路径不存在");
		}
		File downFile = new File(downPath+Constants.FILE_SEPERATOR+downFileName);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream =new FileOutputStream(downFile);
			sftp.get(downFileName, fileOutputStream );
			log.error("sftp下载成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    public static void main(String[] args) {
    	SFTPOperate operete=new SFTPOperate();
    	operete.setFile(false);
    	operete.setCover(true);
    	operete.connect();
    	try {
			operete.upload();
//    		operete.downFile("/home/wuzl/test","test.text","e:");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	operete.disconnect();
	}
	
	
   
}
