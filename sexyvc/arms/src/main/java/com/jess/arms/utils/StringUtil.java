package com.jess.arms.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ls on 17/5/3.
 */

public class StringUtil {

    public static int getStringLastIndex(String str){
        return isBlank(str)?0:str.length();
    }

    /**
     * 验证 str 是否一个合法手机格式
     *
     * @param str
     *            以 1 开头 11 位数字
     * @return
     */
    public static boolean isMobile(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        // 1\\d{10}
        String check = "^1\\d{10}$";
        // String check =
        // "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|2|3|5|6|7|8|9])\\d{8}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str.trim());
        return matcher.matches();
    }

    public static String formatString(String str){
        return str == null ?"":str ;
    }
    public static String formatMoneyString(String str){
        return str == null ?"":"￥"+str ;
    }

    public static String addZero(int num){
        return num<10?"0"+num:""+num;
    }

    public static boolean isSuccess(int code){
        return code==1?true:false;
    }

    /**
     * 1 --> A, 2 --> B ... 26 --> Z
     *
     * @param index
     * @return
     */
    public static String getUpperAlphaChar(int index) {
        if (index > 26 || index < 1) {
            return String.valueOf(index);
        }
        int s = 'A';
        int e = s + index - 1;
        char c = (char) e;
        return String.valueOf(c);
    }

    /**
     * 判断字母是否为 A ... Z
     *
     * @param c
     * @return
     */
    public static boolean isUpperAlphaChar(char c) {
        if ('A' <= c && c <= 'Z') {
            return true;
        }
        return false;
    }

    /**
     * 是否为空字符串
     *
     * @param str
     *            null is true, "" is true , "  " is true , other false
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 是否为空字符串
     *
     * @param str
     *            null is true, "" is true , other false
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    // 截取字符串，根据字节长度
    public static String subStr(String str, int subLength)
            throws UnsupportedEncodingException {
        if (str != null) {
            int tempSubLength = subLength;// 截取字节数
            String subStr = str.substring(0,
                    str.length() < subLength ? str.length() : subLength);// 截取的子串
            int subStrByetsL = subStr.getBytes("GBK").length;// 截取子串的字节长度
            // 说明截取的字符串中包含有汉字
            while (subStrByetsL > tempSubLength) {
                int subSLengthTemp = --subLength;
                subStr = str.substring(0,
                        subSLengthTemp > str.length() ? str.length()
                                : subSLengthTemp);
                subStrByetsL = subStr.getBytes("GBK").length;
            }
            return subStr;
        }
        return "";
    }

    // TextView会自动换行，而且排版文字参差不齐
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // 获取用户图像地址
    public static String getPathByUid(String uid) {
        try {
            if (uid.length() < 9) {
                uid = "0" + uid;
                return getPathByUid(uid);
            }
            return uid.substring(0, 3) + "/" + uid.substring(3, 6) + "/"
                    + uid.substring(6, 9) + "/source.jpg";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断邮箱是否合法
     * @param strEmail
     * @return
     */
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean isEmail(String email) {
        if(isBlank(email)){
            return false;
        }
        return Pattern.matches(REGEX_EMAIL, email);
    }

    public static String getFormatPhone(String origin){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < origin.length(); i++) {
            if (i != 3 && i != 8 && origin.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(origin.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        return sb.toString();
    }
}
