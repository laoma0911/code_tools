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

public class UpdateDirFiLE {
	private final static String FILE_SEPARATOR = System
			.getProperty("line.separator");

	public static void main(String[] args) {
//		String dirPath = "/home/wuzl/dayimaworkspace/uic/uic-api/src/main/java/com/dayima/uic/api/interfaces";
		String dirPath = "/home/wuzl/dayimaworkspace/uic/uic-provider/src/main/java/com/dayima/uic/provider/service/impl";
		List<File> fileList = FileUtil.getDirectFileList(dirPath);
		for (File file : fileList) {
			FileReader fr = null;
			BufferedReader br = null;
			BufferedWriter out = null;
			StringBuilder sb = new StringBuilder();
			try {
				fr = new FileReader(file);
//				if (file.getName().indexOf("Example") < 0) {
//					continue;
//				}
				br = new BufferedReader(fr);
				String data = br.readLine();
				int line = 1;
				while (data != null) {
					// //更改包名
					// if(line==1){
					// sb.append("package com.dayima.uic.api.model;"+FILE_SEPARATOR);
					// }else{
					// sb.append(data+FILE_SEPARATOR);
					// }
					// //加入base继承
					// if(data.indexOf("class")>0&&line==1){
					// line++;
					// sb.append(data.replace("{",
					// "extends BaseBean{")+FILE_SEPARATOR);
					// sb.append("\tprivate static final long serialVersionUID = 1L;"+FILE_SEPARATOR);
					// }else{
					// sb.append(data+FILE_SEPARATOR);
					// }
					// line++;
//					if(line==1){
//						sb.append("package com.dayima.uic.provider.dao;"+FILE_SEPARATOR);
//						line++;
//					}else{
//						if (data.indexOf("package") > 0&&data.indexOf("import") >= 0 ) {
//							sb.append(data.replace("package", "") + FILE_SEPARATOR);
//						} else {
//							sb.append(data + FILE_SEPARATOR);
//						}
//					}
					
					if (data.indexOf("com.dayima.core.command.QueryFilter") > 0 ) {
						sb.append(data.replace("com.dayima.core.command.QueryFilter", "com.dayima.core.mybatis.filter.QueryFilter") + FILE_SEPARATOR);
					} else {
						sb.append(data + FILE_SEPARATOR);
					}
					data = br.readLine(); // 接着读下一行
				}
			} catch (Exception e) {
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
				try {
					if (fr != null) {
						fr.close();
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}

			}
			try {
				out = new BufferedWriter(new FileWriter(file));
				out.write(sb.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
