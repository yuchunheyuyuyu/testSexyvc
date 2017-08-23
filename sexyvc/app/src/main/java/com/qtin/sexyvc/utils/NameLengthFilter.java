package com.qtin.sexyvc.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ls on 17/8/22.
 */

public class NameLengthFilter implements InputFilter {

    private onFullListener listener;

    int MAX_EN;// 最大英文/数字长度 一个汉字算两个字母
    String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

    public NameLengthFilter(int mAX_EN) {
        super();
        this.MAX_EN = mAX_EN;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        /**int dindex = 0;
        int count = 0;

        while (count <= MAX_EN && dindex < dest.length()) {
            char charValue = dest.charAt(dindex++);
            if (charValue >= 33 && charValue <= 126 || charValue == 32) {
                count = count + 1;//半角
            } else {
                count = count + 2;
            }
        }
        if (count > MAX_EN) {
            return dest.subSequence(0, dindex - 1);
        }
        int sindex = 0;
        while (count <= MAX_EN && sindex < source.length()) {
            char charValue = source.charAt(sindex++);
            if (charValue >= 33 && charValue <= 126 || charValue == 32) {
                count = count + 1;//半角
            } else {
                count = count + 2;
            }
        }
        if (count > MAX_EN) {
            sindex--;
        }

        int keep = MAX_EN - (dest.length() - (dend - dstart));
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null; // keep original
        } else {
            keep += start;
            if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                --keep;
                if (keep == start) {
                    return "";
                }
            }
            return source.subSequence(start, keep);
        }


        return source.subSequence(0, sindex);
         */


        /**int destCount = dest.toString().length()+ getChineseCount(dest.toString());
        int sourceCount = source.toString().length()+ getChineseCount(source.toString());
        if (destCount + sourceCount > MAX_EN) {
            return "";
        } else {
            return source;
        }*/

        int mlength = getLength(dest.subSequence(dstart, dend).toString());// 修改字符串的长度
        int dlength = getLength(dest.toString());// 已有字符串的长度
        int slength = getLength(source.subSequence(start, end).toString());// 要增加的字符串的长度
        int keep = MAX_EN - (dlength - mlength);// 还差多少字符到最大长度
        if (keep <= 0) {
            // 已经到达最大长度
            if (null != listener) {
                listener.isFull();
            }
            return "";
        } else if (keep >= slength) {
            // 还没到达最大长度
            return null;
        } else {
            // 超出最大长度
            int tmp = 0;
            int index;
            for (index = start; index <= end; index++) {
                if (isFullwidthCharacter(source.charAt(index))) {
                    tmp += 2;
                } else {
                    tmp += 1;
                }
                if (tmp > keep) {
                    break;
                }
            }
            if (null != listener) {
                listener.isFull();
            }
            return source.subSequence(start, index);
        }
    }

    public void setOnFullListener(onFullListener listener) {
        this.listener = listener;
    }

    public interface onFullListener {

        /**
         * 这个方法会在输入字符串超出极限时被调用
         */
        void isFull();
    }

    /**
     * 判断字符串是否为空或空串
     *
     * @param str
     *            待判断的字符串
     * @return true：字符串为空或空串
     */
    public static boolean isNull(final String str) {
        if (null == str || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取字符串长度（半角算1、全角算2）
     *
     * @param str
     *            字符串
     * @return 字符串长度
     */
    public static int getLength(final String str) {
        if (isNull(str)) {
            return 0;
        }
        int len = str.length();
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                len = len + 1;
            }
        }
        return len;
    }

    /**
     * 获取字符串的全角字符数
     *
     * @param str
     *            待计算的字符串
     * @return 字符串的全角字符数
     */
    public static int getFwCharNum(final String str) {
        if (isNull(str)) {
            return 0;
        }
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                num++;
            }
        }
        return num;
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

    private int getChineseCount(String str) {
        int count = 0;
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }
}
