package com.qtin.sexyvc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ls on 17/6/23.
 */
public class DateUtil {
    public static String getDate(long  timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日",Locale.getDefault());
        return format.format(new Date(timestamp*1000));
    }

    public static String getLongDate(long  timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());
        return format.format(new Date(timestamp*1000));
    }

    public static String getTime(long  timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.getDefault());
        return format.format(new Date(timestamp*1000));
    }

    public static boolean isSameDate(long  timestamp1,long  timestamp2){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        String date1=format.format(new Date(timestamp1*1000));
        String date2=format.format(new Date(timestamp2*1000));
        if(date1.equals(date2)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isNeedShow(long  timestamp1,long  timestamp2){
        if(isSameDate(timestamp1,timestamp2)){
            return false;
        }else if(isSameDate(timestamp1,System.currentTimeMillis()/1000)){
            return false;
        }
        return true;
    }

    public static String getDateExpression(long  timestamp) {

        if(isSameDate(timestamp, System.currentTimeMillis()/1000)){
            return "今天";
        }else if(isSameDate(timestamp, (System.currentTimeMillis()-24*60*60*1000)/1000)){
            return "昨天";
        }else{
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日",Locale.getDefault());
            return format.format(new Date(timestamp*1000));
        }
    }

    public static String getCommentDate(long timestamp){
        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis() - (timestamp*1000);
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) (time/60/1000.0f);// 分钟前
        long hour = (long) (time/60/60/1000.0f);// 小时
        long day = (long)(time/24/60/60/1000.0f);// 天前
        long year=(long) (time/365/24/60/60/1000.0f);// 年

        if(year>0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());
            sb.append(format.format(new Date(timestamp*1000)));
        }else if (day>0) {
            if(day==1){
                sb.append("昨天");
            }else{
                SimpleDateFormat format = new SimpleDateFormat("MM月dd日",Locale.getDefault());
                sb.append(format.format(new Date(timestamp*1000)));
            }
        } else if (hour> 0) {
                sb.append(hour + "小时前");
        } else if (minute> 0) {
            sb.append(minute + "分钟前");
        } else {
            sb.append("刚刚");
        }
        return sb.toString();
    }
}
