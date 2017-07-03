package com.qtin.sexyvc.utils;

/**
 * Created by ls on 17/6/29.
 */

public class ConstantUtil {
    //对象类型 1：评论；2：专题；3：回复
    public static final int OBJECT_TYPE_COMMENT=1;
    public static final int OBJECT_TYPE_SUBJECT=2;
    public static final int OBJECT_TYPE_REPLY=3;

    //数据来源，本地or服务器
    public static final String DATA_FROM_TYPE="DATA_FROM_TYPE";
    public static final int DATA_FROM_LOCAL=1;
    public static final int DATA_FROM_WEB=0;
    //路演评价or撰写评论
    public static final String COMMENT_TYPE_INTENT="COMMENT_TYPE_INTENT";
    public static final int COMMENT_TYPE_ROAD=1;
    public static final int COMMENT_TYPE_EDIT=2;
    public static final int COMMENT_TYPE_NONE=3;

    //机构或者投资人
    public static final String TYPE_INVESTOR_FUND_INTENT="TYPE_INVESTOR_FUND_INTENT";
    public static final int TYPE_INVESTOR=1;
    public static final int TYPE_FUND=2;
}
