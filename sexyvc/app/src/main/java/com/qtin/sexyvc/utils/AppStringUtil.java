package com.qtin.sexyvc.utils;

import android.content.Context;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;

/**
 * Created by ls on 17/5/3.
 */

public class AppStringUtil {

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

    public static boolean isShowVStatus(int is_anon,int u_auth_type, int u_auth_state){
        if(is_anon==1){
            return false;
        }else{
            if(u_auth_state==ConstantUtil.AUTH_STATE_PASS){
                if(u_auth_type==ConstantUtil.AUTH_TYPE_FOUNDER
                        ||u_auth_type==ConstantUtil.AUTH_TYPE_INVESTOR
                        ||u_auth_type==ConstantUtil.AUTH_TYPE_FA){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    public static int getVStatusResourceId(int u_auth_type){
        if(u_auth_type==ConstantUtil.AUTH_TYPE_FOUNDER){
            return R.drawable.logo_approve_fc;
        }else if(u_auth_type==ConstantUtil.AUTH_TYPE_INVESTOR){
            return R.drawable.logo_approve_vc;
        }else{
            return R.drawable.logo_approve_fa;
        }
    }

    /**
     * 判断字符是否为全角字符
     *
     * @param ch
     *            待判断的字符
     * @return true：全角； false：半角
     */
    public static boolean isFullwidthCharacter(final char ch) {
        if (ch >= 32 && ch <= 127) {
            // 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
            return false;
        } else if (ch >= 65377 && ch <= 65439) {
            // 日文半角片假名和符号
            return false;
        } else {
            return true;
        }
    }
    public static final int MAX_STRING_LENGTH=26;

    public static String getLimitString(String string){
        if(StringUtil.isBlank(string)){
             return "";
        }
        int tmp=0;//全角算2，半角算1
        int index=0;//截断的位置
        for (int i = 0; i <string.length(); i++) {
            if (isFullwidthCharacter(string.charAt(i))) {
                tmp += 2;
            } else {
                tmp += 1;
            }
            if(tmp<=MAX_STRING_LENGTH){
                index++;
            }
        }
        if(tmp<=MAX_STRING_LENGTH){
            return string;
        }else{
            return string.substring(0,index-1)+"...";
        }
    }
    //去掉数字末尾无意义的0
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
