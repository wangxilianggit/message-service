package com.panshi.hujin2.message.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author shenJianKang
 * @date 2019/7/4 14:18
 */
public class MD5Util {



//    public static void main(String[] args) {
//        MD5("sunzn");
//    }


    /**
     *@Description:         md5加密
     *@Param:  * @param sourceStr
     *@Author: shenJianKang
     *@date: 2019/7/4 14:19
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
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
            result = buf.toString();
//            System.out.println("MD5(" + sourceStr + ",32) = " + result);
//            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

}
