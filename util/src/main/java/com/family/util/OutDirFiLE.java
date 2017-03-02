package com.family.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class OutDirFiLE {
	private final static String FILE_SEPARATOR =System.getProperty("line.separator");  
	public static void main(String[] args) {
		String dirPath="/home/wuzl/dayimaworkspace/uic/uic-provider/src/main/resources/mybatis";
		List<File> fileList=FileUtil.getDirectFileList(dirPath);
		for(File file:fileList){
			System.out.println("<mapper resource=\"mybatis/"+file.getName()+"\" />");
		}
	}
}
