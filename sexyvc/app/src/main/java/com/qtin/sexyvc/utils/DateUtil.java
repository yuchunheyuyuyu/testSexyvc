package com.qtin.sexyvc.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**设置每个阶段时间*/
    private static final int seconds_of_1minute = 60;
    private static final int seconds_of_1hour = 60 * 60;
    private static final int seconds_of_1day = 24 * 60 * 60;
    private static final int seconds_of_2day = seconds_of_1day*2;
    private static final int seconds_of_30days = seconds_of_1day * 30;
    private static final int seconds_of_1year = seconds_of_30days * 12;

    /**
     * 格式化时间
     * @return
     */
    public static String getSpecialDate(long timestamp){

        long between=System.currentTimeMillis()/1000- timestamp;

        if (between < seconds_of_1minute) {
            return "刚刚";
        }
        if (between < seconds_of_1hour) {
            return between / seconds_of_1minute + "分钟前";
        }
        if (between < seconds_of_1day) {
            return between / seconds_of_1hour + "小时前";
        }

        if(!isSameYear(timestamp,System.currentTimeMillis())){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());
            return format.format(new Date(timestamp*1000));
        }

        if (between < seconds_of_2day) {
            return "昨天";
        }

        if (between < seconds_of_1year) {
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日",Locale.getDefault());
            return format.format(new Date(timestamp*1000));
        }
        return "";
    }

    /**
     * 判断是不是在一年
     * @param timestamp1 毫秒
     * @param timestamp2 毫秒
     * @return
     */
    private static boolean isSameYear(long timestamp1, long timestamp2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(timestamp1));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(timestamp2));

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameYear;
    }
}
