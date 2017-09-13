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
    public static final int TYPE_TO_ENTERING=7;//去录入投资人


    public static final int TYPE_QUESTION=5;//题目
    public static final int TYPE_OPTION=6;//选项

    public static final int TYPE_TOPIC_CONTENT=0x009;//题目
    public static final int TYPE_TOPIC_COMMENT=0x00b;//选项

    public static final int TYPE_FOOTER=11;//投资机构

}
