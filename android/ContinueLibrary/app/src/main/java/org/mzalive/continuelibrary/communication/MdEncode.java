package org.mzalive.continuelibrary.communication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MdEncode {
	//方法说明：32位加密md
	public static String encode(String str) {
		try {
			//32位大写加密md
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes()); //更新
			byte[] bt=md.digest();     //摘要
			//保留结果的字符串
			StringBuilder sb=new StringBuilder();
			int p;
			for(int i=0;i<bt.length;i++){
				p=bt[i];
				if(p<0) p +=256;        //负值时的处理
				if(p<16) sb.append("0");
				sb.append(Integer.toHexString(p));//转换成16进制
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return str;
		}
	}
}
