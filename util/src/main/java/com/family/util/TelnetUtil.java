package com.family.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;

/**
 * telnet工具类
 * 有bug只能查看一条语句的结果
 * 如果一条运行语句返回多个结果也只能看到第一个
 * @date Mar 10, 2014 10:02:30 AM
 * @author wuzl
 * @comment
 */
public class TelnetUtil {
	/** 新建一个TelnetClient对象 */
	private TelnetClient telnetClient = new TelnetClient();
	/** 系统标示符号 */
	private final String osTag = "\r\n";
	/** get Value 系统标示符号 */
	private final String getValOsTag = "END\r\n";
	/** 输入流，接收返回信息 */
	private InputStream in;
	/** 向 服务器写入 命令 */
	private PrintStream out;
	private static final Logger log = Logger.getLogger(TelnetUtil.class);
	/**
	 * 初始化连接
	 * 
	 * @param ip
	 * @param port
	 */
	public TelnetUtil(String ip, Integer port) {
		try {
			telnetClient.connect(ip, port);
			in = telnetClient.getInputStream();
			out = new PrintStream(telnetClient.getOutputStream());
			log.error("[telnet]打开telnet连接");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("telnet连接错误：" + e.getMessage());
		}
	}

	/**
	 * 调用默认端口
	 * 
	 * @param ip
	 */
	public TelnetUtil(String ip) {
		try {
			telnetClient.connect(ip, 11211);
			in = telnetClient.getInputStream();
			out = new PrintStream(telnetClient.getOutputStream());
			log.error("[telnet]打开telnet连接");
		} catch (Exception e) {
			throw new RuntimeException("telnet连接错误：" + e.getMessage());
		}
	}

	/**
	 * 执行telnet命令
	 * 
	 * @param command
	 * @return
	 */
	public String execute(String command) {
		write(command);
		StringBuffer sb = new StringBuffer();
		String osTagX = osTag;
		if (command.startsWith("get")) {
			osTagX = getValOsTag;
		}
		try {
			char ch = (char) in.read();
			int isEnd = 0;
			while (true) {
				sb.append(ch);
				if (ch == osTagX.charAt(isEnd)) {
					isEnd++;
					if (sb.toString().endsWith(osTagX)
							&& isEnd == osTagX.length())
						return sb.toString();
				} else {
					isEnd = 0;
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("执行telnet命令错误："+e.getMessage());
		}
	}

	/**
	 * 向telnet命令行输入命令
	 * 
	 * @param command
	 */
	public void write(String command) {
		try {
			out.println(command);
			out.flush();
			log.error("[telnet]本次执行的telnet命令:" + command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭Telnet连接
	 */
	public void disconnect() {
		try {
			Thread.sleep(10);
			telnetClient.disconnect();
			log.error("[telnet]关闭telnet连接");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("关闭telnet错误:"+e.getMessage());
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		TelnetUtil telnet=new TelnetUtil("localhost",20880);
		System.out.println(telnet.execute("invoke sayHello('test')"));
		System.out.println(telnet.execute("invoke sayHelloMap({'name':'testmap'})"));
	}
}
