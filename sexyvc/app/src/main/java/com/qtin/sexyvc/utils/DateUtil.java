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

    public static String getSpecialDate2(long timestamp){
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        int year1=calendar1.get(Calendar.YEAR);

        Calendar calendar2=Calendar.getInstance();
        calendar2.setTimeInMillis(timestamp*1000);
        int year2=calendar2.get(Calendar.YEAR);

        //不在同一年
        if(year1!=year2){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            return format.format(new Date(timestamp*1000));
        }else{
            SimpleDateFormat format = new SimpleDateFormat("MM-dd",Locale.getDefault());
            return format.format(new Date(timestamp*1000));
        }
    }

    /**
     * 格式化时间
     * @return
     */
    public static String getSpecialDate(long timestamp){

        Calendar calendar1=Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        int year1=calendar1.get(Calendar.YEAR);
        int month1=calendar1.get(Calendar.MONTH);
        int day1=calendar1.get(Calendar.DAY_OF_YEAR);
        int hour1=calendar1.get(Calendar.HOUR_OF_DAY);
        int minute1=calendar1.get(Calendar.MINUTE);

        Calendar calendar2=Calendar.getInstance();
        calendar2.setTimeInMillis(timestamp*1000);
        int year2=calendar2.get(Calendar.YEAR);
        int month2=calendar2.get(Calendar.MONTH);
        int day2=calendar2.get(Calendar.DAY_OF_YEAR);
        int hour2=calendar2.get(Calendar.HOUR_OF_DAY);
        int minute2=calendar2.get(Calendar.MINUTE);

        //不在同一年
        if(year1!=year2){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());
            return format.format(new Date(timestamp*1000));
        }

        //在同一年,不同月份
        if(month1!=month2){
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日",Locale.getDefault());
            return format.format(new Date(timestamp*1000));
        }
        //不同的日,区分是不是昨天
        if(day1!=day2){
            if(day2-day1==-1){
                return "昨天";
            }else{
                SimpleDateFormat format = new SimpleDateFormat("MM月dd日",Locale.getDefault());
                return format.format(new Date(timestamp*1000));
            }
        }

        //同一天内，不同小时
        if(hour1!=hour2){
            return hour1-hour2+"小时前";
        }

        //同一小时
        if(minute1!=minute2){
            return minute1-minute2+"分钟前";
        }else{
            return "刚刚";
        }
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
