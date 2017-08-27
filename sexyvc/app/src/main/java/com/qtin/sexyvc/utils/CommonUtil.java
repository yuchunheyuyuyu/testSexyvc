package com.qtin.sexyvc.utils;

import com.qtin.sexyvc.mvp.model.api.Api;

/**
 * Created by ls on 17/5/12.
 */

public class CommonUtil {
    public static boolean isAbsolutePath(String path){
        return path==null?false:(path.startsWith("http:")||path.startsWith("https:"));
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
                "<style>.ui-article{font-size:14px;width:100%;height:100%;overflow:hidden;font-family:PingFang SC,Lantinghei SC,Helvetica Neue,Helvetica,Arial,Microsoft YaHei,\\\\5FAE\\8F6F\\96C5\\9ED1,STHeitiSC-Light,simsun,\\\\5B8B\\4F53,WenQuanYi Zen Hei,WenQuanYi Micro Hei,\"sans-serif\"}.ui-article{opacity:.9;font-size:14px;font-weight:normal;font-style:normal;font-stretch:normal;line-height:1.43;letter-spacing:.5px;color:#333;outline:0;word-wrap:break-word;padding:0 16px;font-family:arial,sans-serif,PingFangSC;overflow-x:hidden;overflow-y:auto}.ui-article:first-child{margin-top:0!important}a{color:#55b1f3;text-decoration:none;word-break:break-all}a:visited{color:#55b1f3}a:hover{color:#0f769f}a:active{color:#9e792e}a:hover,a:active{outline:0}h1,h2,h3,h4,h5,h6{font-weight:normal;margin:40px 0 20px;color:#000}h1{font-size:24px}h2{font-size:22px}h3{font-size:20px}h4{font-size:18px}h5{font-size:16px}h6{font-size:16px}.ui-article p,.ui-article div{color:rgba(51,51,51,.9);margin:0 0 15px 0;word-wrap:break-word;line-height:1.43}b,strong{font-weight:bold}i,em{font-style:italic}u{text-decoration:underline}strike,del{text-decoration:line-through}ul,ol{list-style:disc outside none;margin:15px 0;padding:0 0 0 20px;line-height:1.6}ul ul,ul ol,ol ul,ol ol{padding-left:30px}ul ul,ol ul{list-style:circle outside none}ul ul ul,ol ul ul{list-style:square outside none}ol{list-style:decimal}blockquote{border-left:4px solid #ddd;padding:5px 0 5px 10px}blockquote p,blockquote span{color:rgba(51,51,51,.5);font-size:14px}blockquote>:first-child{margin-top:0}code{display:inline-block;padding:0 4px;margin:0 5px;background:#eee;border-radius:3px;font-size:13px;font-family:'monaco','Consolas',\"Liberation Mono\",Courier,monospace}pre{padding:10px 5px 10px 10px;margin:15px 0;display:block;line-height:18px;background:#f0f0f0;border-radius:3px;font-size:13px;font-family:'monaco','Consolas',\"Liberation Mono\",Courier,monospace;white-space:pre;word-wrap:normal;overflow-x:auto}pre code{display:block;padding:0;margin:0;background:0;border-radius:0}hr{height:0;border:0;padding:0;margin:15px 0;display:block;border-top:1px solid #ccc}table{width:100%;table-layout:fixed;border-collapse:collapse;border-spacing:0;margin:15px 0}table thead{background-color:#f9f9f9}table td,table th{min-width:40px;height:30px;border:1px solid #ccc;vertical-align:top;padding:2px 4px;text-align:left;box-sizing:border-box}table td.active,table th.active{background-color:#ffe}.ui-article img{margin:0 5px;max-width:100%;vertical-align:middle}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
