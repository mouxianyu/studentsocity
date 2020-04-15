package com.mouxianyu.studentsociety.common.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public class MD5Util {
    public static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String encode = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return encode;
    }

    /**
     * 判断用户密码是否正确
     * newpasswd 用户输入的密码
     * oldpasswd 正确密码
     */
    public static boolean checkPassword(String newPassword, String oldPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return !encoderByMd5(newPassword).equals(oldPassword);
    }
}
