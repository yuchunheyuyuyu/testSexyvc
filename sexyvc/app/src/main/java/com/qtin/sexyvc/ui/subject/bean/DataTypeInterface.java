package com.qtin.sexyvc.ui.subject.bean;

/**
 * Created by ls on 17/6/23.
 */
public interface DataTypeInterface {
    int getType();
    public static int TYPE_CONTENT=0;//主体内容
    public static int TYPE_COMMENT=1;//评论
    public static int TYPE_ADDITIONAL=2;//追加评论
    public static int TYPE_ROAD=3;//路演评价
    public static int TYPE_BASE_INFO=4;//基本信息
    public static int TYPE_TAG=5;//标签
    public static int TYPE_CASE=6;//案例分析


}
