package com.qtin.sexyvc.utils;

import android.content.Context;

import com.qtin.sexyvc.R;

/**
 * Created by ls on 17/5/3.
 */

public class StringUtil {

    public static String formatNoKnown(Context context,String str){
        if(isBlank(str)){
            return context.getResources().getString(R.string.no_known);
        }
        return str;
    }

    public static String formatNoData(Context context,String str){
        if(isBlank(str)){
            return context.getResources().getString(R.string.no_data);
        }
        return str;
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
}
