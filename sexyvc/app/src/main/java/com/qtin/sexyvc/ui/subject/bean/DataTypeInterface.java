package com.qtin.sexyvc.ui.subject.bean;

/**
 * Created by ls on 17/6/23.
 */
public interface DataTypeInterface {
    int getType();
    public static final int TYPE_CONTENT=0;//主体内容
    public static final int TYPE_COMMENT=1;//评论
    public static final int TYPE_ADDITIONAL=2;//追加评论

    public static final int TYPE_INVESTOR=3;//投资人
    public static final int TYPE_FUND=4;//投资机构
}
