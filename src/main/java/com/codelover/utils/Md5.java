package com.codelover.utils;
import java.security.MessageDigest;
public class Md5 {

	public static String MD5(String plainText) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(plainText.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            return buf.toString().toLowerCase();  
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
  
    }  
	

}
