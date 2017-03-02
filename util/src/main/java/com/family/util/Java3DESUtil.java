package com.family.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * java 3des加密解密
 * 
 * @date Mar 11, 2014 10:37:31 AM
 * @author wuzl
 * @comment
 */
public class Java3DESUtil {
	public final static String key = "4BD634432ERRDF432FFSDDSFAQSDF3E83A361FA75FA446933F90D384C6F6520F29FCD8EA";

	/**
	 * 加密
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 * @date Mar 11, 2014 10:41:13 AM
	 * @author wuzl
	 * @comment
	 */
	public static String encryptThreeDESECB(String src, String key)
			throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes());

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

	}
	/**
	 * 加密
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 * @date Mar 11, 2014 10:41:13 AM
	 * @author wuzl
	 * @comment
	 */
	public static String encryptThreeDESECB(String src)
			throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes());

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

	}
	/**
	 * 解密
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 * @date Mar 11, 2014 10:41:07 AM
	 * @author wuzl
	 * @comment
	 */
	public static String decryptThreeDESECB(String src, String key)
			throws Exception {
		// --通过base64,将字符串转成byte数组
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytesrc = decoder.decodeBuffer(src);
		// --解密的key
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		// --Chipher对象解密
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Java3DESUtil.encryptThreeDESECB("17098061686", key));
//		System.out.println(Java3DESUtil.decryptThreeDESECB("Ca1nioSPdnA3 m1vOXEZnw==", key));
		System.out.println(Java3DESUtil.encryptThreeDESECB("阿迪飞234523452345", key));
		System.out.println(Java3DESUtil.decryptThreeDESECB("AiJ3yH7oNdOXnzymRXxAU/8i6ei1wfmO", key));
	}
}
