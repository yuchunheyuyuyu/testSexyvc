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

    public static String getTime(long  timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.getDefault());
        return format.format(new Date(timestamp*1000));
    }
}
