package com.qtin.sexyvc.utils;

import com.qtin.sexyvc.mvp.model.api.Api;

/**
 * Created by ls on 17/5/12.
 */

public class CommonUtil {
    public static boolean isAbsolutePath(String path){
        return path==null?false:path.startsWith("http:");
    }

    public static String getAbsolutePath(String path){
        if(isAbsolutePath(path)){
            return path;
        }else{
            return Api.IMAGE_URL+path;
        }
    }

    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
